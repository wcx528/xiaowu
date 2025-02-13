package com.fmyd888.fengmao.module.information.service.taxrates;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.web.core.util.WebFrameworkUtils;
import com.fmyd888.fengmao.module.information.dal.dataobject.importLogs.ImportLogDO;
import com.fmyd888.fengmao.module.information.dal.mysql.importLogs.ImportLogMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.taxrates.TaxratesDeptMapper;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.*;
import com.fmyd888.fengmao.module.information.convert.taxrates.TaxratesConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.taxrates.TaxratesMapper;
import com.fmyd888.fengmao.module.information.dal.redis.RedisKeyConstants;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesServiceImpl;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 税率 Service 实现类
 *
 * @author 丰茂企业
 */
@Service
@Validated
@Slf4j
public class TaxratesServiceImpl implements TaxratesService {

    @Resource
    private TaxratesMapper taxratesMapper;
    @Resource
    private TaxratesDeptService taxratesDeptService;
    @Resource
    private EncodingRulesService encodingRulesService;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private TaxratesDeptMapper taxratesDeptMapper;
    @Resource
    private EncodingRulesServiceImpl encodingRulesServiceImpl;
    @Resource
    private ImportLogMapper importLogMapper;
    @Autowired
    private PlatformTransactionManager transactionManager;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTaxrates(TaxratesCreateReqVO createReqVO) {

        // 插入
        TaxratesDO taxrates = TaxratesConvert.INSTANCE.convert(createReqVO);
        //新增前校验
        validateTaxratesIsExists(taxrates);
        //获取税率编码组装
        String code = EncodingRulesEnum.TAX_CODE.getBusinessCode();
        String taxCode = encodingRulesService.getCodeByRuleType(code);
        taxrates.setTaxCode(taxCode);

        taxratesMapper.insert(taxrates);
        Set<Long> dept = createReqVO.getOrganization();
        taxratesDeptService.bindDeptsToEntity(taxrates.getId(), dept);
        return taxrates.getId();
    }

    private void validateTaxratesIsExists(TaxratesDO taxrates) {
        LambdaQueryWrapper<TaxratesDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaxratesDO::getTaxName, taxrates.getTaxName())
                .eq(TaxratesDO::getTaxRate, taxrates.getTaxRate())
                .eq(TaxratesDO::getDeleted, 0);
        if (taxrates.getId() != null) {
            queryWrapper.ne(TaxratesDO::getId, taxrates.getId());
        }
        if (ObjectUtil.isNotNull(taxratesMapper.selectOne(queryWrapper))) {
            throw exception(DUPLICATE_TAXRATES_NAME);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaxrates(TaxratesUpdateReqVO updateReqVO) {
        // 校验存在
        Long id = updateReqVO.getId();
        // 更新
        TaxratesDO updateObj = TaxratesConvert.INSTANCE.convert(updateReqVO);
        //修改前校验
        validateTaxratesIsExists(updateObj);
        taxratesMapper.updateById(updateObj);
        Set<Long> deptIds = updateReqVO.getOrganization();
        taxratesDeptService.updateBindDeptsToEntity(id, deptIds);
    }

    @Override
    public void deleteTaxrates(Long id) {
        // 删除
        taxratesMapper.deleteById(id);
    }


    @Override
    public TaxratesRespVO getTaxrates(Long id) {
        TaxratesDO taxratesDO = taxratesMapper.selectById(id);
        TaxratesRespVO taxratesVO = TaxratesConvert.INSTANCE.convert(taxratesDO);
        Set<Long> deptIdSet = taxratesDeptService.findDeptIdsByEntityId(id);
        List<Long> deptIdList = new ArrayList<>(deptIdSet);
        taxratesVO.setOrganization(deptIdList);
        return taxratesVO;
    }

    @Override
    public List<TaxratesRespVO> getTaxratesList(Collection<Long> ids) {
        List<TaxratesDO> list = taxratesMapper.selectBatchIds(ids);
        List<TaxratesRespVO> taxratesRespVOS = TaxratesConvert.INSTANCE.convertList(list);
        taxratesRespVOS.forEach(iterm -> {
            Set<Long> deptIdSet = taxratesDeptService.findDeptIdsByEntityId(iterm.getId());
            List<Long> deptIdList = new ArrayList<>(deptIdSet);
            iterm.setOrganization(deptIdList);
        });
        return taxratesRespVOS;
    }

    @Override
    public PageResult<TaxratesRespVO> getTaxratesPage(TaxratesPageReqVO pageReqVO) {
        PageResult<TaxratesDO> pageRessult = taxratesMapper.selectJoinTileList(pageReqVO);
        pageRessult.getList().forEach(p->{
            p.setCreator(p.getCreatorName());
            p.setUpdater(p.getUpdaterName());
        });
        PageResult<TaxratesRespVO> result = BeanUtils.toBean(pageRessult, TaxratesRespVO.class);
        List<TaxratesRespVO> records = result.getList();
        if(ObjectUtil.isNotEmpty(records)){
            List<TaxratesDeptDO> deptLists = taxratesDeptService.findDeptIdsByEntityIds(records.stream().map(TaxratesRespVO::getId).collect(Collectors.toList()));
            for (TaxratesRespVO iterm : records) {
                List<Long> deptIdList = deptLists.stream().filter(p -> ObjectUtil.equal(p.getEntityId(), iterm.getId())).map(TaxratesDeptDO::getDeptId).collect(Collectors.toList());
                iterm.setOrganization(deptIdList);
            }
        }

        return result;
    }

    @Override
    public List<TaxratesDO> getTaxratesList(TaxratesExportReqVO exportReqVO) {
        List<TaxratesDO> taxratesDOS = taxratesMapper.selectExportList(exportReqVO);
        return taxratesDOS;
    }

    @Override
    public void batchUpdateTaxrates(List<TaxratesUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (TaxratesUpdateReqVO updateReqVO : updateReqVOList) {
            updateTaxrates(updateReqVO);
        }
    }

    @Override
    public void batchDeleteTaxrates(List<Integer> ids) {
        // 在这里处理批量删除逻辑
        taxratesMapper.deleteBatchIds(ids);
    }

    @Override
    public void batchImportTaxrates(List<TaxratesDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        taxratesMapper.insertBatch(importReqVOList);
    }

    @Override
    public void updateStatus(Long id) {
        UpdateWrapper<TaxratesDO> updateWrapper = new UpdateWrapper<>();
        TaxratesDO taxratesDO = taxratesMapper.selectById(id);
        updateWrapper.eq("id", id);
        Byte status = taxratesDO.getStatus();
        //状态修改取反
        if (status == 0) {
            updateWrapper.set("status", 1); // 设置要更新的字段和值
        } else if (status == 1) {
            updateWrapper.set("status", 0);
        } else {
            throw new RuntimeException("税率状态错误！");
        }
        int rowsAffected = taxratesMapper.update(null, updateWrapper); // 使用update方法更新字段

        if (rowsAffected > 0) {
            System.out.println("税率状态成功");
        } else {
            System.out.println("税率状态失败");
        }
    }


    @Override
    public void assignTaxratesToDept(TaxratesDeptReqVO taxratesDeptReqVO) {
        Long taxratesId = taxratesDeptReqVO.getTaxratesId();
        Set<Long> deptIds = taxratesDeptReqVO.getDeptIds();
        taxratesDeptService.updateBindDeptsToEntity(taxratesId, deptIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public TaxratesImportRespVO importTaxratesList(List<TaxratesImportExcelVO> importTaxrates, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importTaxrates)) {
            throw new RuntimeException("导入税率数据不能为空");
        }
        TaxratesImportRespVO respVO = TaxratesImportRespVO.builder().createTaxratesnames(new ArrayList<>())
                .updateTaxratesnames(new ArrayList<>())
                .failureTaxratesnames(new LinkedHashMap<>()).build();
        importTaxrates.forEach(iterm -> {
            // 1、根据税率名称查询判断如果不存在，在进行插入返回
            TaxratesDO taxratesDO = taxratesMapper.selectOne(TaxratesDO::getTaxName, iterm.getTaxName());
            if (ObjectUtils.isEmpty(taxratesDO)) {
                TaxratesDO taxratesDO1 = TaxratesConvert.INSTANCE.convert(iterm);
                //获取税率编码组装
                String code = EncodingRulesEnum.TAX_CODE.getBusinessCode();
                String codeByRuleType = encodingRulesService.getCodeByRuleType(code);
                taxratesDO1.setTaxCode(codeByRuleType);

                taxratesMapper.insert(taxratesDO1);
                respVO.getCreateTaxratesnames().add(iterm.getTaxName());
                return;
            }
            //2、如果税率名称存在，判断是否允许更新返回
            if (!isUpdateSupport) {
                respVO.getFailureTaxratesnames().put(iterm.getTaxName(), "税率名称已经存在！");
                return;
            }
            // 3、税率名称存在，进行更新返回
            TaxratesDO convert = TaxratesConvert.INSTANCE.convert(iterm);
            convert.setId(taxratesDO.getId());
            taxratesMapper.updateById(convert);
            respVO.getUpdateTaxratesnames().add(iterm.getTaxName());

        });
        return respVO;
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.TAXRATES_SIMPLE_LIST + "#120", key = "#param.status+'_'+#param.searchKey+'_'+#param.id+'_'+#param.companyIds")
    public List<HashMap<String, Object>> getSimpleStoreList(CommonQueryParam param) {
        if (ObjectUtil.isEmpty(param.getStatus()))
            param.setStatus(0);
        List<HashMap<String, Object>> list = new ArrayList<>();
        MPJLambdaWrapper<TaxratesDO> queryWrapper = new MPJLambdaWrapper<TaxratesDO>()
                .select(TaxratesDO::getId, TaxratesDO::getTaxName, TaxratesDO::getTaxRate)
                .leftJoin(TaxratesDeptDO.class, TaxratesDeptDO::getEntityId, TaxratesDO::getId)
                .eq(TaxratesDO::getStatus, param.getStatus())
                .eq(ObjectUtil.isNotEmpty(param.getId()), TaxratesDO::getId, param.getId())
                .in(ArrayUtil.isNotEmpty(param.getCompanyIds()), TaxratesDeptDO::getDeptId, param.getCompanyIds())
                .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(TaxratesDO::getTaxName, param.getSearchKey()).or().like(TaxratesDO::getTaxCode, param.getSearchKey()))
                .groupBy(TaxratesDO::getId, TaxratesDO::getTaxName, TaxratesDO::getTaxRate)
                .disableSubLogicDel();
        List<TaxratesDO> storeDOList = taxratesMapper.selectJoinList(TaxratesDO.class, queryWrapper);
        storeDOList.forEach(iterm -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("name", iterm.getTaxName());
            //小数点格式化成%的方式返回
            DecimalFormat df = new DecimalFormat("0.##%");
            BigDecimal taxRate = iterm.getTaxRate().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
            map.put("taxRate", df.format(taxRate.doubleValue()));
            list.add(map);
        });
        return list;
    }

    @Override
    public TaxratesRespVO getTaxratesByName(String taxName) {
        TaxratesDO taxratesDO = taxratesMapper.selectFirst(TaxratesDO::getTaxName, taxName);
        return TaxratesConvert.INSTANCE.convert(taxratesDO);
    }

    @Override
    public List<TaxratesExcelVO> importPreviewList(List<TaxratesExcelVO> importDatas, Boolean updateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(TAXRATES_IMPORT_LIST_IS_EMPTY);
        }
        try {
            //定义需要返回的列表
            List<TaxratesExcelVO> list = new ArrayList<>();
            //导入必须要确定判定重复的字段，此例是以“税率名称”字段作为判定重复，看数据库是否已存在此条数据
            //1.1 查询数据库中已存在的数据，此处只要id与重复列信息即可
            LambdaQueryWrapper<TaxratesDO> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.select(TaxratesDO::getId, TaxratesDO::getTaxName);
            List<TaxratesDO> dbTaxList = taxratesMapper.selectList(queryWrapper);
            //查询好转换处理需要用到的数据
            LambdaQueryWrapper<DeptDO> queryWrapper1 = Wrappers.lambdaQuery();
            queryWrapper1.select(DeptDO::getId, DeptDO::getName);
            List<DeptDO> deptList = deptMapper.selectList(queryWrapper1);
            //为了提升数据匹配速度，先将List转为Map
            Map<String, Long> dbTaxMap = dbTaxList.stream().collect(Collectors.toMap(TaxratesDO::getTaxName, TaxratesDO::getId, (existing, replacement) -> existing));
            Map<String, Long> deptMap = deptList.stream().collect(Collectors.toMap(DeptDO::getName, DeptDO::getId, (existing, replacement) -> existing));
            //1.2 循环导入的excel数据
            for (TaxratesExcelVO item : importDatas) {
                Map<String, String> errMap = new HashMap<>();
                TaxratesExcelVO vo = new TaxratesExcelVO();
                if (StrUtil.isBlank(item.getTaxName())) {
                    errMap.put("taxName", "税率名称不能为空");
                    //判重列都为空，就没必要后面的逻辑了，直接下一条
                    continue;
                }

                //1.3 根据判重列，判断是否存在于数据库
                if (dbTaxMap.containsKey(item.getTaxName())) {
                    //1.4 再判断updateSupport是否为true，为true则需要更新，保留数据；为false证明不需更新，直接跳过
                    if (!updateSupport)
                        continue;
                    //很关键，在实际导入接口中，就是根据id去判断是新增还是更新
                    vo.setId(dbTaxMap.get(item.getTaxName()));
                    //1.5 转换数据，如一些引用的，传入的是名称，需转成id；“是/否”等布尔需转成true/false...等等，此例中只有所属公司是引用，只选转换此字段即可，字典类型在excel导入处理时通过@DictFormat处理了
                    handleTran(item, deptMap, errMap, vo);
                } else {
                    //1.7 不存在于数据库中，需要新增
                    handleTran(item, deptMap, errMap, vo);
                }

                //1.8 得到最终导入预览数据
                vo.setFailData(errMap);
                if (MapUtil.isNotEmpty(errMap))
                    vo.setHasError(true);
                list.add(vo);
            }
            return list;
        } catch (Exception e) {
            throw exception(TAXRATES_IMPORT_ERR, e.getMessage());
        }
    }

    @Override
    public ImportResult importData(TaxratesExcelVO importReqVo) {
        List<TaxratesExcelVO> importDatas = importReqVo.getImportDatas();
        if (ObjectUtil.isEmpty(importDatas))
            throw exception(TAXRATES_IMPORT_LIST_IS_EMPTY);
        handleImport(importDatas);

        return null;
    }

    /**
     * 功能描述：导入处理方法
     *
     * @param importDatas
     * @author 小逺
     * @date 2024/08/09
     */
    public void handleImport(List<TaxratesExcelVO> importDatas) {
        //因为异步任务中不走主线程，拿不到web全局上下文，得不到loginUserId和tenantId信息，需要线程外获取
        Long loginUserId = WebFrameworkUtils.getLoginUserId();
        //写入导入记录表
        ImportLogDO importLogDO = new ImportLogDO();
        String key = StrUtil.uuid();
        importLogDO.setTaskId(key);
        importLogDO.setStatus(1);
        importLogDO.setCreator(loginUserId.toString());
        importLogDO.setUpdater(loginUserId.toString());
        importLogMapper.insert(importLogDO);
        //考虑到导入可能非常耗时，开启没有返回值的异步任务，不阻塞主进程
        CompletableFuture.runAsync(() -> {
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
            try {
                ececuteImport(importDatas, loginUserId);
                transactionManager.commit(status);  // 手动提交事务
                //更新导入记录表状态
                importLogDO.setStatus(2);
                importLogMapper.updateById(importLogDO);
            } catch (Exception e) {
                transactionManager.rollback(status);  // 手动回滚事务
                //更新导入记录表失败状态
                LambdaUpdateWrapper<ImportLogDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(ImportLogDO::getStatus, 3).set(ImportLogDO::getErrMessage,e.getMessage()).eq(ImportLogDO::getTaskId, key);
                importLogMapper.update(null, updateWrapper);
            }
        });
    }

    /**
     * 功能描述：执行导入
     *
     * @param importDatas
     * @author 小逺
     * @date 2024/08/09 20:29
     */
    public void ececuteImport(List<TaxratesExcelVO> importDatas, Long loginUserId) {
        LocalDateTime now = LocalDateTime.now();
        //获取子表关键数据
        LambdaQueryWrapper<TaxratesDeptDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TaxratesDeptDO::getId, TaxratesDeptDO::getEntityId, TaxratesDeptDO::getDeptId);
        List<TaxratesDeptDO> dbDtlList = taxratesDeptMapper.selectList(queryWrapper);
        //因为导入的是主子表数据，需要把主表与子表数据分出来;如果是单表，直接根据id区分导入还是更新，比主子表要省事
        Map<String, Map<TaxratesDO, List<TaxratesDeptDO>>> map = new HashMap<>();
        for (TaxratesExcelVO data : importDatas) {
            if (map.containsKey(data.getTaxName())) {
                //已添加过主信息，直接处理子表信息
                Map<TaxratesDO, List<TaxratesDeptDO>> itemMap = map.get(data.getTaxName());
                // 获取唯一的 key，因为itemMap中只会有一个key
                TaxratesDO key = itemMap.keySet().iterator().next();
                // 获取对应的 value 列表
                List<TaxratesDeptDO> valueList = itemMap.get(key);
                //主表新增，子表必新增
                if (ObjectUtil.isEmpty(key.getId())) {
                    TaxratesDeptDO item = new TaxratesDeptDO();
                    item.setDeptId(data.getCompanyId());
                    item.setStatus(Byte.valueOf("0"));
                    item.setCreator(loginUserId.toString());
                    item.setUpdater(loginUserId.toString());
                    valueList.add(item);
                }
                //id有值，主表更新，子表可能更新可能插入，需要根据dbDtlList判断是否存在，存在则是更新，不存在则是插入
                else {
                    TaxratesDeptDO item = dbDtlList.stream().filter(p -> ObjectUtil.equals(p.getEntityId(), key.getId()) && ObjectUtil.equals(p.getDeptId(), data.getCompanyId())).findFirst().orElse(null);
                    //子表插入
                    if (ObjectUtil.isEmpty(item)) {
                        item = new TaxratesDeptDO();
                        item.setEntityId(key.getId());
                        item.setDeptId(data.getCompanyId());
                        item.setStatus(Byte.valueOf("0"));
                        item.setCreator(loginUserId.toString());
                        item.setUpdater(loginUserId.toString());
                        valueList.add(item);
                    }
                    //子表更新
                    else {
                        //这里分配表就一个deptId字段，还是拿来判断重复的字段，没有其他字段需要更新了，所以可不添加
//                        item.setDeptId(data.getCompanyId());
//                        item.setUpdater(loginUserId.toString());
//                        item.setUpdateTime(now);
                        //....其他字段
//                        valueList.add(item);
                    }
                }
            } else {
                //未添加主表信息
                Map<TaxratesDO, List<TaxratesDeptDO>> dtlMap = new HashMap<>();
                //id不存在,主表新增,子表也只可能新增
                if (ObjectUtil.isEmpty(data.getId())) {
                    TaxratesDO main = BeanUtils.toBean(data, TaxratesDO.class);
                    //生成编码,这里直接写死5不太严谨，应该根据名称找到对应的字典建值
                    String code = encodingRulesServiceImpl.getCodeByRuleType("5");
                    main.setTaxCode(code);
                    main.setStatus(Byte.valueOf("0"));
                    main.setCreator(loginUserId.toString());
                    main.setUpdater(loginUserId.toString());
                    //构建子表数据
                    TaxratesDeptDO detail = new TaxratesDeptDO();
                    detail.setDeptId(data.getCompanyId());
                    detail.setStatus(Byte.valueOf("0"));
                    detail.setCreator(loginUserId.toString());
                    detail.setUpdater(loginUserId.toString());
                    List<TaxratesDeptDO> valueList = new ArrayList<>();
                    valueList.add(detail);
                    dtlMap.put(main, valueList);
                    map.put(data.getTaxName(), dtlMap);
                }
                //id存在，主表修改，子表新增或修改
                else {
                    TaxratesDO main = BeanUtils.toBean(data, TaxratesDO.class);
                    main.setId(data.getId());
                    main.setUpdater(loginUserId.toString());
                    main.setUpdateTime(now);
                    //构建子表
                    TaxratesDeptDO item = dbDtlList.stream().filter(p -> ObjectUtil.equals(p.getEntityId(), main.getId()) && ObjectUtil.equals(p.getDeptId(), data.getCompanyId())).findFirst().orElse(null);
                    //子表插入
                    List<TaxratesDeptDO> valueList = new ArrayList<>();
                    if (ObjectUtil.isEmpty(item)) {
                        item = new TaxratesDeptDO();
                        item.setEntityId(data.getId());
                        item.setDeptId(data.getCompanyId());
                        item.setStatus(Byte.valueOf("0"));
                        item.setCreator(loginUserId.toString());
                        item.setUpdater(loginUserId.toString());
                        valueList.add(item);
                        dtlMap.put(main, valueList);
                        map.put(data.getTaxName(), dtlMap);
                    }
                    //子表更新
                    else {
                        //这里分配表就一个deptId字段，还是拿来判断重复的字段，没有其他字段需要更新了，所以可不添加
                        dtlMap.put(main, valueList);
                        map.put(data.getTaxName(), dtlMap);
                    }
                }
            }
        }
        List<Map<TaxratesDO, List<TaxratesDeptDO>>> collect = map.values().stream().collect(Collectors.toList());
        for (Map<TaxratesDO, List<TaxratesDeptDO>> item : collect) {
            for (Map.Entry<TaxratesDO, List<TaxratesDeptDO>> entry : item.entrySet()) {
                TaxratesDO main = entry.getKey();
                List<TaxratesDeptDO> valueList = entry.getValue();
                //插入主表
                if (ObjectUtil.isEmpty(main.getId())) {
                    taxratesMapper.insert(main);
                    valueList.forEach(p -> p.setEntityId(main.getId()));
                    if (ObjectUtil.isNotEmpty(valueList))
                        taxratesDeptMapper.batchInsertX(valueList);
                }
                //更新主表
                else {
                    taxratesMapper.updateById(main);
                    //因为这里子表没有更新，都是插入，所以就不判断更新了
                    if (ObjectUtil.isNotEmpty(valueList))
                        taxratesDeptMapper.batchInsertX(valueList);
                }
            }
        }
    }

    /**
     * 功能描述：转换处理
     *
     * @param item    excel数据
     * @param deptMap 所属公司
     * @param errMap  错误信息
     * @param vo      转换后信息
     * @author 小逺
     * @date 2024/08/08 19:29
     */
    private static void handleTran(TaxratesExcelVO
                                           item, Map<String, Long> deptMap, Map<String, String> errMap, TaxratesExcelVO vo) {
        //所属公司
        if (!deptMap.containsKey(item.getCompanyName()))
            errMap.put("companyName", "所属公司不存在");
        vo.setCompanyId(deptMap.get(item.getCompanyName()));
        vo.setCompanyName(item.getCompanyName());
        //不需转换的字段直接赋值
        vo.setTaxName(item.getTaxName());
        vo.setTaxRate(item.getTaxRate());
        vo.setStatus(item.getStatus());
        vo.setRemark(item.getRemark());
        //税率编码不需要赋值，因为这是根据编码规则生成的，不管导入填的啥，都不管，在实际导入接口中，如过数据是新增再根据编码规则生成
        //vo.setTaxCode(item.getTaxCode());
    }
}

