package com.fmyd888.fengmao.module.information.service.store;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fmyd888.fengmao.framework.common.exception.ServiceException;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.datapermission.core.util.DataPermissionUtils;
import com.fmyd888.fengmao.framework.security.core.util.SecurityFrameworkUtils;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.mysql.address.AddressMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.store.StoreDeptMapper;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.*;
import com.fmyd888.fengmao.module.information.convert.store.StoreConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.store.StoreMapper;
import com.fmyd888.fengmao.module.information.dal.redis.RedisKeyConstants;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.system.api.dict.DictDataApi;
import com.fmyd888.fengmao.module.system.api.dict.dto.DictDataRespDTO;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import com.fmyd888.fengmao.module.system.convert.dept.DeptConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import com.fmyd888.fengmao.module.system.service.user.AdminUserService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 仓库 Service 实现类
 *
 * @author 丰茂企业
 */
@Service
@Validated
public class StoreServiceImpl implements StoreService {
    @Resource
    private StoreMapper storeMapper;
    @Resource
    private DeptService deptService;

    @Resource
    private EncodingRulesService encodingRulesService;

    @Resource
    private StoreDeptService storeDeptService;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private AddressMapper addressMapper;

    @Resource
    private DeptMapper deptMapper;
    @Resource
    private DictDataApi dictDataApi;
    @Resource
    private StoreDeptMapper storeDeptMapper;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private AdminUserApi adminUserApi;

    @Transactional
    @Override
    public Long createStore(StoreCreateReqVO createReqVO) {
        String storeName = createReqVO.getStoreName();
        validateStoreNameExists(null, storeName);
        StoreDO store = StoreConvert.INSTANCE.convert(createReqVO);
        String code = EncodingRulesEnum.STORE_CODE.getBusinessCode();
        String storeCode = encodingRulesService.getCodeByRuleType(code);
        store.setStoreCode(storeCode);
        //1.新增仓库
        storeMapper.insert(store);

        Long id = store.getId();
        //2.绑定组织
        Set<Long> dept = createReqVO.getOrganization();
        storeDeptService.bindDeptsToEntity(id, dept);

        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStore(StoreUpdateReqVO updateReqVO) {
        Long id = updateReqVO.getId();
        String storeName = updateReqVO.getStoreName();
        // 校验存在
        validateUpdateStoreNameExists(id, storeName);
        // 更新
        StoreDO updateObj = StoreConvert.INSTANCE.convert(updateReqVO);
        storeMapper.updateById(updateObj);

        Set<Long> deptIds = updateReqVO.getOrganization();
        storeDeptService.updateBindDeptsToEntity(id, deptIds);

    }

    @Override
    public void deleteStore(Long id) {
        // 删除
        storeMapper.deleteById(id);
    }


    /**
     * 判断仓库名称是否重复,用于新建仓库时重复校验
     *
     * @param storeName 仓库名称
     */
    private void validateStoreNameExists(Long id, String storeName) {
        LambdaQueryWrapper<StoreDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(id != null, StoreDO::getId, id);
        queryWrapper.eq(StoreDO::getStoreName, storeName);
        boolean exists = storeMapper.exists(queryWrapper);
        if (exists) {
            throw exception(DUPLICATE_STORE_NAME, storeName);
        }
    }

    /**
     * 判断仓库名称是否重复,用于更新重复校验
     *
     * @param storeName id
     * @param storeName 仓库名称
     */
    private void validateUpdateStoreNameExists(Long id, String storeName) {
        LambdaQueryWrapper<StoreDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(StoreDO::getId, id);
        queryWrapper.eq(StoreDO::getStoreName, storeName);
        boolean exists = storeMapper.exists(queryWrapper);
        if (exists) {
            throw exception(DUPLICATE_STORE_NAME, storeName);
        }
    }

    @Override
    public StoreRespVO getStore(Long id) {
        StoreDO storeDO = storeMapper.selectById(id);
        StoreRespVO storeVO = StoreConvert.INSTANCE.convert(storeDO);

        setDeptInfo(storeVO);

        return storeVO;
    }

    @Override
    public List<StoreRespVO> getStoreList(Collection<Integer> ids) {
        List<StoreDO> list = storeMapper.selectBatchIds(ids);
        List<StoreRespVO> storeRespVOS = StoreConvert.INSTANCE.convertList(list);
        storeRespVOS.forEach(iterm -> {
            setDeptInfo(iterm);
        });
        return storeRespVOS;
    }

    private void setDeptInfo(StoreRespVO respVO) {
        //3、获取客户对应的部门组织信息
        Set<Long> deptIds = storeDeptService.findDeptIdsByEntityId(respVO.getId());
        ArrayList<Long> deptIds2 = CollectionUtil.newArrayList(deptIds);
        respVO.setOrganization(deptIds2);

        //转化创建更新信息为名称
        AdminUserDO createUserDO = adminUserMapper.selectById(respVO.getCreator());
        if (ObjUtil.isNotEmpty(createUserDO)) {
            respVO.setCreator(createUserDO.getNickname());
        }
        AdminUserDO updateUserDO = adminUserMapper.selectById(respVO.getUpdater());
        if (ObjUtil.isNotEmpty(updateUserDO)) {
            respVO.setUpdater(updateUserDO.getNickname());
        }
    }

    @Override
    public PageResult<StoreRespVO> getStorePage(StorePageReqVO pageReqVO) {
        PageResult<StoreDO> storeDOPageResult = storeMapper.selectJoinTileList(pageReqVO);
        storeDOPageResult.getList().forEach(p->p.setCreator(p.getCreatorName()));
        PageResult<StoreRespVO> storeRespVOPageResult = com.fmyd888.fengmao.framework.common.util.object.BeanUtils.toBean(storeDOPageResult, StoreRespVO.class);
        if (ObjectUtil.isNotEmpty(storeRespVOPageResult.getList())) {

            List<Long> ids = storeRespVOPageResult.getList().stream().map(StoreRespVO::getId).collect(Collectors.toList());
            if (ObjectUtil.isEmpty(ids))
                return storeRespVOPageResult;
            List<StoreDeptDO> details = storeDeptService.findDeptIdsByEntityIds(ids);
            storeRespVOPageResult.getList().forEach(iterm -> {
                List<Long> collect = details.stream().filter(p -> ObjectUtil.equal(iterm.getId(), p.getEntityId())).map(p -> p.getDeptId()).collect(Collectors.toList());
                if (ObjUtil.isNotEmpty(collect)) {
                    iterm.setOrganization(collect);
                }
            });
        }
        return storeRespVOPageResult;
    }

    @Override
    public List<HashMap<String, Object>> getExportList() {
        List<HashMap<String, Object>> exportList = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Field[] fields = DownStoreExcelVO.class.getDeclaredFields();

        for (Field field : fields) {
            // 获取字段的注解
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                String[] values = excelProperty.value();
                if (values.length > 0) {
                    String key = field.getName(); // 使用字段名称作为键
                    String value = values[0]; // 使用 ExcelProperty 注解的第一个值作为值
                    map.put(key, value);
                }
            }
        }

        exportList.add(map);
        return exportList;
    }

    @Override
    public List<DownStoreExcelVO> importPreviewList(List<DownStoreExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(STORE_IMPORT);
        }

        //查询所有的仓库名称 和 所有的编码
        LambdaQueryWrapper<StoreDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StoreDO::getDeleted, 0)
                .select(StoreDO::getId, StoreDO::getStoreName, StoreDO::getStoreType);
        List<StoreDO> storeDOS = storeMapper.selectList(queryWrapper);


        //查询所有的分配组织
        LambdaQueryWrapper<DeptDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(DeptDO::getDeleted, 0)
                .eq(DeptDO::getParentId, 498)
                .select(DeptDO::getId, DeptDO::getName);
        List<DeptDO> deptDOS = deptMapper.selectList(queryWrapper1);
        Map<String, Long> deptMap;
        if (CollUtil.isNotEmpty(deptDOS)) {
            deptMap = deptDOS.stream().
                    collect(Collectors.toMap(DeptDO::getName, DeptDO::getId, (existing, replacement) -> existing));
        } else {
            deptMap = null;
        }

        //查询所有的用户(创建人)
        LambdaQueryWrapper<AdminUserDO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(AdminUserDO::getDeleted, 0)
                .eq(AdminUserDO::getStatus, 0);
        List<AdminUserDO> userDOS = adminUserMapper.selectList(queryWrapper2);
        Map<String, Long> userMap = null;
        if (CollUtil.isNotEmpty(userDOS)) {
            userMap = userDOS.stream().
                    collect(Collectors.toMap(AdminUserDO::getNickname, AdminUserDO::getId, (existing, replacement) -> existing));
        }

        //查询所有的地址
        LambdaQueryWrapper<AddressDO> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.eq(AddressDO::getDeleted, 0)
                .eq(AddressDO::getStatus, 0);
        List<AddressDO> addressDOS = addressMapper.selectList(queryWrapper3);
        Map<String, Long> addressMap = null;
        if (CollUtil.isNotEmpty(addressDOS)) {
            addressMap = addressDOS.stream().
                    collect(Collectors.toMap(AddressDO::getFullAddress, AddressDO::getId, (existing, replacement) -> existing));
        }

        //查询仓库类别字典
        List<DictDataRespDTO> fmStoreType = dictDataApi.getDictDatas("fm_store_type");
        Map<String, String> StoreTypeMaps = null;
        if (CollUtil.isNotEmpty(fmStoreType)) {
            StoreTypeMaps = fmStoreType.stream().
                    collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel, (existing, replacement) -> existing));
        }

        for (DownStoreExcelVO item : importDatas) {
            if (item.getFailData() == null) {
                item.setFailData(new HashMap<>()); // 确保 failData 已初始化
            }

            if (item.getId() == null) {
                item.setIsUpdateSupport(false); // 设置为新增
            } else {
                StoreDO storeDO = storeMapper.selectById(item.getId());
                if (storeDO == null) {
                    item.setIsUpdateSupport(false); // 设置为新增
                } else {
                    item.setIsUpdateSupport(true); // 设置为更新
                }
            }

            //非空校验
            if (item.getStoreName() == null) {
                item.getFailData().put("storeName", "仓库名称不能为空");
                item.setHasError(true);
            }
            if (item.getUseOrganization() == null) {
                item.getFailData().put("useOrganization", "使用组织不能为空");
                item.setHasError(true);
            }
            if (item.getStoreType() == null) {
                item.getFailData().put("storeType", "仓库类别不能为空");
                item.setHasError(true);
            }

            // 初始化一个 StringBuilder 来存储不存在的组织
            StringBuilder nonExistentOrganizations = new StringBuilder();

            // 拆分 useOrganization 字符串并遍历每个组织
            String useOrganization = item.getUseOrganization();
            Stream<String> stringStream = Arrays.stream(useOrganization.split(",")).map(String::trim);

            stringStream.forEach(s -> {
                // 检查组织是否存在于 deptMap 中
                if (s != null && !deptMap.containsKey(s)) {
                    // 如果不存在，则将组织添加到 StringBuilder 中，逗号分隔
                    if (nonExistentOrganizations.length() > 0) {
                        nonExistentOrganizations.append(", ");
                    }
                    nonExistentOrganizations.append(s);
                }
            });

            // 如果有不存在的组织，将它们标记为错误并加入到 failData 中
            if (nonExistentOrganizations.length() > 0) {
                item.getFailData().put("useOrganization", "使用组织不存在: " + nonExistentOrganizations.toString());
                item.setHasError(true);
            }

            if (item.getStoreAddress() != null && !addressMap.containsKey(item.getStoreAddress())) {
                item.getFailData().put("storeAddress", "仓库地址不存在");
                item.setHasError(true);
            }
            if (item.getStoreType() != null && !StoreTypeMaps.containsKey(item.getStoreType())) {
                item.getFailData().put("storeType", "仓库类别不存在");
                item.setHasError(true);
            }
            if (item.getCreator() != null && !userMap.containsKey(item.getCreator())) {
                item.getFailData().put("creator", "创建人不存在");
                item.setHasError(true);
            }

            if (item.getStoreName() != null && item.getStoreType() != null && CollUtil.isNotEmpty(storeDOS)) {
                Set<String> storeSet = storeDOS.stream()
                        .map(store -> store.getStoreName() + "-" + store.getStoreType())
                        .collect(Collectors.toSet());

                String itemKey = item.getStoreName() + "-" + item.getStoreType();
                if (storeSet.contains(itemKey)) {
                    item.getFailData().put("storeName", "仓库名称和类型已存在");
                    item.setHasError(true);
                }
            }

        }

        return importDatas;
    }

    @Override
    public ImportResult<DownStoreExcelVO> importMainVehicleList(DownStoreExcelVO importReqVo) {
        List<DownStoreExcelVO> importDatas = importReqVo.getImportDatas();

        // 过滤有效数据
        List<DownStoreExcelVO> importListData = importDatas.stream()
                .filter(item -> item.getHasError() == null || !item.getHasError())
                .collect(Collectors.toList());

        List<DownStoreExcelVO> importListUpdate = new ArrayList<>();
        List<DownStoreExcelVO> importListInsert = new ArrayList<>();

        // 进行分类
        for (DownStoreExcelVO item : importListData) {
            if (Boolean.TRUE.equals(item.getIsUpdateSupport())) {
                importListUpdate.add(item);
            } else {
                importListInsert.add(item);
            }
        }


        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 异步处理更新操作
        Future<?> updateFuture = executor.submit(() -> handleUpdates(importListUpdate));

        // 异步处理新增操作
        Future<?> insertFuture = executor.submit(() -> handleInserts(importListInsert));

        // 等待所有任务完成
        try {
            updateFuture.get();
            insertFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导入失败，请重试", e); // 处理异常
        } finally {
            executor.shutdown(); // 关闭线程池
        }

        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // 任务超时，尝试强制关闭
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt(); // 重新设置中断状态
        }

        // 计算导入结果
        int total = importDatas.size();
        int importCount = importListData.size();
        int failCount = total - importCount;

        // 如果有一条成功导入，success 就为 true, 否则为 false
        boolean success = importCount > 0;

        // 构建并返回 ImportResult
        return ImportResult.<DownStoreExcelVO>builder()
                .total(total)  // 设置导入数据总行数
                .importCount(importCount)  // 设置成功行数
                .failCount(failCount)  // 设置失败行数
                .success(success)  // 设置导入状态
                .data(importListData)  // 返回有效数据
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    void handleUpdates(List<DownStoreExcelVO> updates) {
        try {
            if (CollectionUtils.isNotEmpty(updates)) {

                // 查询所有的分配组织
                LambdaQueryWrapper<DeptDO> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(DeptDO::getDeleted, 0)
                        .eq(DeptDO::getParentId, 498)  // 根据需求更改 ParentId
                        .select(DeptDO::getId, DeptDO::getName);
                List<DeptDO> deptDOS = deptMapper.selectList(queryWrapper1);
                Map<String, Long> deptMap = null;
                if (CollUtil.isNotEmpty(deptDOS)) {
                    deptMap = deptDOS.stream()
                            .collect(Collectors.toMap(DeptDO::getName, DeptDO::getId, (existing, replacement) -> existing));
                }


                for (DownStoreExcelVO update : updates) {

                    StoreDO storeDO = storeMapper.selectById(update.getId());
                    // 复制并排除不需要的字段
                    BeanUtils.copyProperties(update, storeDO, "useOrganization", "id");
                    //更新前做一下校验
                    validateStoreNameExists(storeDO.getId(), storeDO.getStoreName());
                    storeMapper.updateById(storeDO);

                    String useOrganization = update.getUseOrganization();
                    String[] organizationList = useOrganization.split(",");

                    // 根据名称列表从 Map 中获取对应的部门 ID 列表
                    List<Long> organizationIds = Arrays.stream(organizationList)
                            .map(deptMap::get)  // 从 deptMap 中获取对应的 ID
                            .filter(Objects::nonNull)  // 过滤掉未找到的名称
                            .collect(Collectors.toList());

                    // 查询仓库对应的部门ID
                    LambdaQueryWrapper<StoreDeptDO> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(StoreDeptDO::getEntityId, storeDO.getId())
                            .eq(StoreDeptDO::getDeleted, 0);
                    List<StoreDeptDO> storeDeptDOS = storeDeptMapper.selectList(queryWrapper);

                    if (CollectionUtils.isNotEmpty(storeDeptDOS)) {
                        List<Long> dbDeptIds = storeDeptDOS.stream()
                                .map(StoreDeptDO::getDeptId)
                                .collect(Collectors.toList());

                        // 获取新增和删除的部门 ID 列表
                        List<Long> addDeptIds = organizationIds.stream()
                                .filter(id -> !dbDeptIds.contains(id))
                                .collect(Collectors.toList());

                        List<Long> deleteDeptIds = dbDeptIds.stream()
                                .filter(id -> !organizationIds.contains(id))
                                .collect(Collectors.toList());

                        // 批量新增
                        if (CollectionUtils.isNotEmpty(addDeptIds)) {
                            storeDeptMapper.batchInsertStoreDept(addDeptIds, storeDO.getId());
                        }

                        // 批量删除
                        if (CollectionUtils.isNotEmpty(deleteDeptIds)) {
                            storeDeptMapper.batchDeleteStoreDept(deleteDeptIds, storeDO.getId());
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    void handleInserts(List<DownStoreExcelVO> inserts) {
        try {
            if (CollectionUtils.isNotEmpty(inserts)) {

                // 查询所有的分配组织
                LambdaQueryWrapper<DeptDO> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(DeptDO::getDeleted, 0)
                        .eq(DeptDO::getParentId, 498)  // 根据需求更改 ParentId
                        .select(DeptDO::getId, DeptDO::getName);
                List<DeptDO> deptDOS = deptMapper.selectList(queryWrapper1);

                // 根据名称列表从 Map 中获取对应的部门 ID 列表
                Map<String, Long> deptMap = null;
                if (CollUtil.isNotEmpty(deptDOS)) {
                    deptMap = deptDOS.stream()
                            .collect(Collectors.toMap(DeptDO::getName, DeptDO::getId, (existing, replacement) -> existing));
                }

                //查询所有的地址
                LambdaQueryWrapper<AddressDO> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(AddressDO::getDeleted, 0)
                        .eq(AddressDO::getStatus, 0);
                List<AddressDO> addressDOS = addressMapper.selectList(queryWrapper3);
                Map<String, Long> addressMap = null;
                if (CollUtil.isNotEmpty(addressDOS)) {
                    addressMap = addressDOS.stream().
                            collect(Collectors.toMap(AddressDO::getFullAddress, AddressDO::getId, (existing, replacement) -> existing));
                }

                //获取当前登录用户
                Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
                AdminUserDO adminUser = adminUserService.getUser(loginUserId);
//                Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);

                for (DownStoreExcelVO insert : inserts) {

                    StoreDO storeDO = new StoreDO();
                    if (insert.getStoreAddress() != null) {
                        storeDO.setStoreAddressId(addressMap.get(insert.getStoreAddress()));
                    }
                    BeanUtils.copyProperties(insert, storeDO, "id", "useOrganization", "storeCode");
                    String code = EncodingRulesEnum.STORE_CODE.getBusinessCode();
                    String codeByRuleType = encodingRulesService.getCodeByRuleType(code);
                    storeDO.setStoreCode(codeByRuleType);
//                    storeDO.setDeptId(loginUserDeptId);
                    storeDO.setCreator(String.valueOf(adminUser.getId()));
                    //新增前做一下校验
                    validateStoreNameExists(null, insert.getStoreName());
                    storeMapper.insert(storeDO);

                    String useOrganization = insert.getUseOrganization();
                    // 将字符串按逗号分割成数组并去除前后空格
                    String[] organizationList = useOrganization.split(",");
                    Stream<String> stringStream = Arrays.stream(organizationList).map(String::trim);

                    // 过滤出在 deptMap 中存在的公司名称，并获取对应的 deptId 列表
                    List<Long> organizationIds = stringStream
                            .filter(deptMap::containsKey)    // 过滤掉不在 deptMap 中的公司名称
                            .map(deptMap::get)               // 从 deptMap 中获取对应的 deptId
                            .filter(Objects::nonNull)        // 过滤掉空的 deptId
                            .collect(Collectors.toList());

                    // 检查是否有有效的组织ID，避免空插入
                    if (CollUtil.isNotEmpty(organizationIds)) {
                        // 批量插入到 fm_store_dept 表
                        storeDeptMapper.batchInsertStoreDept(organizationIds, storeDO.getId());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<StoreDO> getStoreList(StoreExportReqVO exportReqVO) {
        List<StoreDO> exportList = storeMapper.selectStoreExportList(exportReqVO);
        List<StoreDeptDO> storeDeptList = storeDeptMapper.selectListByStoreIds(exportList.stream().map(StoreDO::getId).collect(Collectors.toList()));
        exportList.forEach(storeDO -> {
            storeDO.setCreator(storeDO.getCreatorName());
            List<StoreDeptDO> entitys = storeDeptList.stream().filter(storeDeptDO -> storeDeptDO.getEntityId().equals(storeDO.getId())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(entitys)) {
                String str = String.join(",", entitys.stream().map(StoreDeptDO::getDeptName).collect(Collectors.toList()));
                storeDO.setDeptName(str);
            }
        });
        return exportList;
    }

    @Override
    public void batchUpdateStore(List<StoreUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (StoreUpdateReqVO updateReqVO : updateReqVOList) {
            updateStore(updateReqVO);
        }
    }

    @Override
    public void batchDeleteStore(List<Integer> ids) {
        // 在这里处理批量删除逻辑
        storeMapper.deleteBatchIds(ids);
    }


    @Override
    public void updateStatus(Long id) {
        UpdateWrapper<StoreDO> updateWrapper = new UpdateWrapper<>();
        StoreDO storeDO = storeMapper.selectById(id);
        updateWrapper.eq("id", id);
        Byte status = storeDO.getStatus();
        //状态修改取反
        if (status == 0) {
            updateWrapper.set("status", 1); // 设置要更新的字段和值
        } else if (status == 1) {
            updateWrapper.set("status", 0);
        } else {
            throw new RuntimeException("仓库状态错误！");
        }
        int rowsAffected = storeMapper.update(null, updateWrapper); // 使用update方法更新字段

        if (rowsAffected > 0) {
            System.out.println("仓库状态成功");
        } else {
            System.out.println("仓库状态失败");
        }
    }

    @Override
    public List<DeptSimpleRespVO> getDeptInfoList(Long storeId) {
        Set<Long> deptIds = storeDeptService.findDeptIdsByEntityId(storeId);
        List<DeptDO> deptList = deptService.getDeptList(deptIds);
        List<DeptSimpleRespVO> convertList02 = DeptConvert.INSTANCE.convertList02(deptList);
        return convertList02;
    }

    @Override
    public void assignStoreToDept(StoreDeptReqVO storeDeptReqVO) {

        //也可以修改成下面的用法
        Long storeId = storeDeptReqVO.getStoreId();
        Set<Long> deptIds = storeDeptReqVO.getDeptIds();
        storeDeptService.updateBindDeptsToEntity(storeId, deptIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public StoreImportRespVO importStoreList(List<StoreImportExcelVO> importStore, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importStore)) {
            throw new RuntimeException("导入仓库数据不能为空");
        }
        StoreImportRespVO respVO = StoreImportRespVO.builder().createStorenames(new ArrayList<>())
                .updateStorenames(new ArrayList<>())
                .failureStorenames(new LinkedHashMap<>()).build();
        importStore.forEach(iterm -> {
            // 校验，判断是否有不符合的原因
            try {
                validateStoreForCreateOrUpdate(iterm);
            } catch (ServiceException ex) {
                respVO.getFailureStorenames().put(iterm.getStoreName(), ex.getMessage());
                return;
            }
            // 1、根据仓库名称查询判断如果不存在，在进行插入返回
            StoreDO storeDO = storeMapper.selectOne(StoreDO::getStoreName, iterm.getStoreName());
            if (ObjectUtils.isEmpty(storeDO)) {
                StoreDO storeDO1 = StoreConvert.INSTANCE.convert(iterm);
                //获取仓库编码组装
                String code = EncodingRulesEnum.STORE_CODE.getBusinessCode();
                String codeByRuleType = encodingRulesService.getCodeByRuleType(code);
                storeDO1.setStoreCode(codeByRuleType);
                storeMapper.insert(storeDO1);
                respVO.getCreateStorenames().add(iterm.getStoreName());
                return;
            }

            //2、如果仓库名称存在，判断是否允许更新返回
            if (!isUpdateSupport) {
                respVO.getFailureStorenames().put(iterm.getStoreName(), "仓库名称已经存在！");
                return;
            }
            // 3、仓库名称存在，进行更新返回
            StoreDO convert = StoreConvert.INSTANCE.convert(iterm);
            convert.setId(storeDO.getId());
            storeMapper.updateById(convert);
            respVO.getUpdateStorenames().add(iterm.getStoreName());
        });
        return respVO;

    }

    private void validateStoreForCreateOrUpdate(StoreImportExcelVO excelVO) {
        DataPermissionUtils.executeIgnore(() -> {
            String storeName = excelVO.getStoreName();
            StoreDO storeDO = storeMapper.selectOne(StoreDO::getStoreName, storeName);
            if (!ObjectUtils.isEmpty(storeDO)) {
                throw new RuntimeException("仓库名称:" + storeName + "已存在！");
            }
        });
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.STORE_SIMPLE_LIST + "#120", key = "#param.status+'_'+#param.searchKey+'_'+#param.id+'_'+#param.companyIds")
    public List<HashMap<String, Object>> getSimpleStoreList(CommonQueryParam param) {
        if (ObjectUtil.isEmpty(param.getStatus()))
            param.setStatus(0);
        List<HashMap<String, Object>> list = new ArrayList<>();
        MPJLambdaWrapper<StoreDO> queryWrapper = new MPJLambdaWrapper<StoreDO>()
                .select(StoreDO::getId, StoreDO::getStoreName)
                .leftJoin(StoreDeptDO.class, StoreDeptDO::getEntityId, StoreDO::getId)
                .eq(StoreDO::getStatus, param.getStatus())
                .eq(ObjectUtil.isNotEmpty(param.getId()), StoreDO::getId, param.getId())
                .in(ArrayUtil.isNotEmpty(param.getCompanyIds()), StoreDeptDO::getDeptId, param.getCompanyIds())
                .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(StoreDO::getStoreName, param.getSearchKey()).or().like(StoreDO::getStoreCode, param.getSearchKey()))
                .groupBy(StoreDO::getId, StoreDO::getStoreName)
                .disableSubLogicDel();
        List<StoreDO> storeDOList = storeMapper.selectJoinList(StoreDO.class, queryWrapper);
        storeDOList.forEach(iterm -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("name", iterm.getStoreName());
            list.add(map);
        });
        return list;
    }
}





