package com.fmyd888.fengmao.module.information.service.salesman;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fmyd888.fengmao.framework.common.exception.ServiceException;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.datapermission.core.util.DataPermissionUtils;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.salesman.SalesmanDeptMapper;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.controller.admin.salesman.vo.*;
import com.fmyd888.fengmao.module.information.convert.salesman.SalesmanConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.salesman.SalesmanMapper;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import com.fmyd888.fengmao.module.system.convert.dept.DeptConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.PostDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.PostMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import com.fmyd888.fengmao.module.system.service.dept.PostService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 业务员表  Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class SalesmanServiceImpl implements SalesmanService {

    @Resource
    private SalesmanMapper salesmanMapper;

    @Resource
    private SalesmanDeptMapper salesmanDeptMapper;

    @Resource
    private DeptService deptService;

    @Resource
    private SalesmanDeptService salesmanDeptService;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private EncodingRulesService encodingRulesService;

    @Resource
    private PostMapper postMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSalesman(SalesmanCreateReqVO createReqVO) {
        //先进行校验，如果新增的数据不为空
        if (createReqVO != null) {
            LambdaQueryWrapper<SalesmanDO> queryWrapper = new LambdaQueryWrapper<>();
            // eq查询条件：查看userid重复
            queryWrapper.eq(SalesmanDO::getSalesmanId, createReqVO.getSalesmanId());
            List<SalesmanDO> salesmanDO = salesmanMapper.selectList(queryWrapper);
            //结果如果不为空或者大于0，说明已经被绑定 直接抛出异常信息
            if (salesmanDO != null && salesmanDO.size() > 0) {
                throw exception(DUPLICATE_SALESMAN_NAME);
            }
        }
        // 插入
        SalesmanDO salesman = SalesmanConvert.INSTANCE.convert(createReqVO);
        String salesmanCode = EncodingRulesEnum.SALESMAN_CODE.getBusinessCode();
        String salesmanCodeValue = encodingRulesService.getCodeByRuleType(salesmanCode);
        salesman.setSalesmanCode(salesmanCodeValue);
        salesmanMapper.insert(salesman);
        Long id = salesman.getId();

        Set<Long> deptIds = createReqVO.getOrganization();
        salesmanDeptService.bindDeptsToEntity(id, deptIds);
        // 返回
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSalesman(SalesmanUpdateReqVO updateReqVO) {

        Long id = updateReqVO.getId();
        Long salesmanId = updateReqVO.getSalesmanId();

        // 校验存在
        validateSalesmanExists(id);

        LambdaQueryWrapper<SalesmanDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalesmanDO::getSalesmanId, salesmanId)
                .eq(SalesmanDO::getDeleted, 0);

        SalesmanDO salesmanDO = salesmanMapper.selectOne(queryWrapper);

        //结果如果不为空或者大于0，说明已经被绑定 直接抛出异常信息
        if (salesmanDO != null && !Objects.equals(salesmanDO.getId(), id)) {
            throw exception(DUPLICATE_SALESMAN_NAME);
        }

        // 更新
        SalesmanDO updateObj = SalesmanConvert.INSTANCE.convert(updateReqVO);
        salesmanMapper.updateById(updateObj);
        Set<Long> deptIds = updateReqVO.getOrganization();
        salesmanDeptService.updateBindDeptsToEntity(id, deptIds);

    }

    @Override
    public void deleteSalesman(Long id) {
        // 删除
        salesmanMapper.deleteById(id);
    }

    @Override
    public SalesmanRespVO getSalesman(Long id) {
        SalesmanDO salesmanDO = salesmanMapper.selectById(id);
        SalesmanRespVO salesmanRespVO = SalesmanConvert.INSTANCE.convert(salesmanDO);
        saveDeptInfo(salesmanRespVO);
        return salesmanRespVO;
    }

    @Override
    public List<SalesmanRespVO> getSalesmanList(Collection<Long> ids) {
        List<SalesmanDO> list = salesmanMapper.selectBatchIds(ids);
        List<SalesmanRespVO> salesmanRespVOS = SalesmanConvert.INSTANCE.convertList(list);
        if (!CollectionUtils.isAnyEmpty(salesmanRespVOS)) {
            List<SalesmanDeptDO> deptList = salesmanDeptService.findDeptIdsByEntityIds(salesmanRespVOS.stream().map(SalesmanRespVO::getId).collect(Collectors.toList()));
            List<AdminUserDO> userList = adminUserMapper.selectAllUsers();
            salesmanRespVOS.forEach(salesmanRespVO -> {
                List<Long> deptIds = deptList.stream().filter(p -> ObjectUtil.equal(salesmanRespVO.getId(), p.getEntityId())).map(BaseDeptDO::getDeptId).collect(Collectors.toList());
                salesmanRespVO.setOrganization(deptIds);
                AdminUserDO creator = userList.stream().filter(p -> ObjectUtil.equal(p.getId(), salesmanRespVO.getCreator())).findFirst().orElse(null);
                if (ObjectUtil.isNotEmpty(creator))
                    salesmanRespVO.setCreator(creator.getNickname());
                AdminUserDO updater = userList.stream().filter(p -> ObjectUtil.equal(p.getId(), salesmanRespVO.getUpdater())).findFirst().orElse(null);
                if (ObjectUtil.isNotEmpty(updater))
                    salesmanRespVO.setUpdater(updater.getNickname());
            });
        }
        return salesmanRespVOS;
    }

    /**
     * @param id
     */
    private void validateSalesmanExists(Long id) {
        LambdaQueryWrapper<SalesmanDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalesmanDO::getId, id);
        SalesmanDO salesmanDO = salesmanMapper.selectOne(queryWrapper);
        if (salesmanDO == null) {
            throw exception(SALESMAN_NOT_EXISTS);
        }
    }

    private void validateSalesmanForCreatOrUpdate(Long userId) {
        // 关闭数据权限，避免因为没有数据权限，查询不到数据，进而导致唯一校验不正确
        DataPermissionUtils.executeIgnore(() -> {
            //检验业务员是否存在
            LambdaQueryWrapper<SalesmanDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SalesmanDO::getSalesmanId, userId);
            boolean exists = salesmanMapper.exists(queryWrapper);
            if (exists) {
                throw exception(DUPLICATE_SALESMAN_USERID_NAME, userId);
            }
        });
    }

    private void saveDeptInfo(SalesmanRespVO respVO) {
        //获取客户对应的部门组织信息
        Set<Long> deptIdSet = salesmanDeptService.findDeptIdsByEntityId(respVO.getId());
        List<Long> deptIdList = new ArrayList<>(deptIdSet);
        respVO.setOrganization(deptIdList);

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
    public PageResult<SalesmanRespVO> getSalesmanPage(SalesmanPageReqVO pageReqVO) {
        PageResult<SalesmanDO> pageResult = salesmanMapper.selectJoinTileList(pageReqVO);
        pageResult.getList().forEach(p -> {
            p.setCreator(p.getCreatorName());
            p.setUpdater(p.getUpdaterName());
        });
        PageResult<SalesmanRespVO> result = BeanUtils.toBean(pageResult, SalesmanRespVO.class);

        if (!CollUtil.isEmpty(result.getList())) {
            List<SalesmanDeptDO> deptList = salesmanDeptService.findDeptIdsByEntityIds(result.getList().stream().map(SalesmanRespVO::getId).collect(Collectors.toList()));
            result.getList().forEach(salesmanRespVO -> {
                List<Long> deptIds = deptList.stream().filter(p -> ObjectUtil.equal(salesmanRespVO.getId(), p.getEntityId())).map(BaseDeptDO::getDeptId).collect(Collectors.toList());
                salesmanRespVO.setOrganization(deptIds);
            });

        }
        return result;
    }


    @Override
    public List<SalesmanExcelVO> getSalesmanList(SalesmanExportReqVO exportReqVO) {
        List<SalesmanDO> datas = salesmanMapper.selectStoreExportList(exportReqVO);
        datas.forEach(p -> {
            p.setCreator(p.getCreatorName());
            p.setUpdater(p.getUpdaterName());
        });;
        List<SalesmanExcelVO> exportDatas = BeanUtils.toBean(datas, SalesmanExcelVO.class);
        if(ObjectUtil.isNotEmpty(exportDatas)){
            List<SalesmanDeptDO> deptList = salesmanDeptMapper.selectListByIds(exportDatas.stream().map(SalesmanExcelVO::getId).collect(Collectors.toList()));
            exportDatas.forEach(iterm -> {
                List<String> str = deptList.stream().filter(q -> ObjectUtil.equal(iterm.getId(), q.getEntityId())).map(SalesmanDeptDO::getDeptName).collect(Collectors.toList());
                if (ObjectUtil.isNotEmpty(str)) {
                    iterm.setOrganization(String.join(",", str));
                }
            });
        }
        return exportDatas;
    }

    @Override
    public void batchUpdateSalesman(List<SalesmanUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (SalesmanUpdateReqVO updateReqVO : updateReqVOList) {
            updateSalesman(updateReqVO);
        }
    }

    @Override
    public void batchDeleteSalesman(List<Long> ids) {
        // 在这里处理批量删除逻辑
        salesmanMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)//添加事务，异常则回滚所有导入
    public SalesmanImportRespVO importSalesmanList(List<SalesmanImportExcelVO> importReqVOList, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importReqVOList)) {
            throw exception(SALESMAN_IMPORT_LIST_IS_EMPTY);
        }
        SalesmanImportRespVO respVO = SalesmanImportRespVO.builder().createSalesmanNames(new ArrayList<>())
                .updateSalesmanNames(new ArrayList<>()).failureSalesmanNames(new HashMap<>()).build();
        importReqVOList.forEach(importSalesman -> {
            //校验，判断是否又不符合的原因
            try {
                validateSalesmanForCreatOrUpdate(importSalesman.getUserId());
            } catch (ServiceException ex) {
                respVO.getFailureSalesmanNames().put(importSalesman.getUsername(), ex.getMessage());
                return;
            }
            //如果用户id不存在，再插入
            SalesmanDO salesmanDO = salesmanMapper.selectById(importSalesman.getUserId());
            if (salesmanDO == null) {
                salesmanMapper.insert(SalesmanConvert.INSTANCE.convert(importSalesman));
                respVO.getCreateSalesmanNames().add(importSalesman.getUsername());
                return;
            }
            //如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureSalesmanNames().put(importSalesman.getUsername(), SALESMAN_NOT_EXISTS.getMsg());
                return;
            }
            SalesmanDO updateSalesman = SalesmanConvert.INSTANCE.convert(importSalesman);
            updateSalesman.setSalesmanId(salesmanDO.getSalesmanId());
            salesmanMapper.updateById(updateSalesman);
            respVO.getUpdateSalesmanNames().add(importSalesman.getUsername());
        });
        return respVO;
    }

    @Override
    public List<SalesmanDO> getNameListById() {
        MPJLambdaWrapper<SalesmanDO> queryWrapper = new MPJLambdaWrapper<SalesmanDO>()
                .select(SalesmanDO::getId)
                .selectAs(SalesmanDO::getUsername, SalesmanDO::getUsername);

        return salesmanMapper.selectList(queryWrapper);
    }

    @Override
    public void updateStatus(Long id) {
        UpdateWrapper<SalesmanDO> updateWrapper = new UpdateWrapper<>();
        SalesmanDO salesmanDO = salesmanMapper.selectById(id);
        updateWrapper.eq("id", id);
        Byte status = salesmanDO.getStatus();
        //状态修改
        if (status == 0) {
            updateWrapper.set("status", 1);
        } else if (status == 1) {
            updateWrapper.set("status", 0);
        } else {
            throw new RuntimeException("业务员状态错误！");
        }
        salesmanMapper.update(null, updateWrapper);
    }


    @Override
    public List<DeptSimpleRespVO> getDeptInfoList(Long salesmanId) {
        Set<Long> deptIds = salesmanDeptService.findDeptIdsByEntityId(salesmanId);
        List<DeptDO> deptDOList = deptService.getDeptList(deptIds);
        List<DeptSimpleRespVO> deptSimpleRespVOS = DeptConvert.INSTANCE.convertList02(deptDOList);
        return deptSimpleRespVOS;
    }

    @Override
    public void assignSalesmanToDept(SalesmanDeptReqVO salesmanDeptReqVO) {
        Long salesmanId = salesmanDeptReqVO.getSalesmanId();
        Set<Long> deptIds = salesmanDeptReqVO.getDeptIds();
        salesmanDeptService.updateBindDeptsToEntity(salesmanId, deptIds);
    }

    @Override
    public List<HashMap<String, Object>> getSimpleSalesmanList(String searchKey, Long id, Integer status, Integer[] type, Long[] companyIds) {
        if (ObjectUtil.isEmpty(status))
            status = 0;
        List<HashMap<String, Object>> list = new ArrayList<>();
        MPJLambdaWrapper<SalesmanDO> queryWrapper = new MPJLambdaWrapper<SalesmanDO>()
                .select(SalesmanDO::getId)
                .selectAs(AdminUserDO::getNickname,SalesmanDO::getUsername)
                .leftJoin(SalesmanDeptDO.class,"t1", SalesmanDeptDO::getEntityId, SalesmanDO::getId)
                .leftJoin(AdminUserDO.class,"t2", AdminUserDO::getId, SalesmanDO::getSalesmanId)
                .eq(SalesmanDO::getStatus, status)
                .eq(ObjectUtil.isNotEmpty(id), SalesmanDO::getId, id)
                .in(ArrayUtil.isNotEmpty(type), SalesmanDO::getSalesmanType, type)
                .in(ArrayUtil.isNotEmpty(companyIds), SalesmanDeptDO::getDeptId, companyIds)
                .and(StrUtil.isNotBlank(searchKey), p -> p.like(AdminUserDO::getNickname, searchKey).or().like(SalesmanDO::getSalesmanCode, searchKey))
                .groupBy("t.id", "t2.nickname")
                .disableSubLogicDel();
        List<SalesmanDO> salesmanDOS = salesmanMapper.selectJoinList(SalesmanDO.class, queryWrapper);
        salesmanDOS.forEach(iterm -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("name", iterm.getUsername());
            list.add(map);
        });
        return list;
    }

    @Override
    public List<HashMap<String, Object>> getExcludeUserList() {
        List<HashMap<String, Object>> userList = new ArrayList<>();
        List<SalesmanDO> salesmanDOS = salesmanMapper.selectList();
        Set<Long> hasUserIds = salesmanDOS.stream().map(SalesmanDO::getSalesmanId).collect(Collectors.toSet());
        LambdaQueryWrapper<AdminUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUserDO::getStatus, 0);
        List<AdminUserDO> adminUserDOS = adminUserMapper.selectList(queryWrapper);
        adminUserDOS.forEach(iterm -> {
            HashMap<String, Object> map = new HashMap<>();
            Long id = iterm.getId();
            map.put("id", id);
            map.put("name", iterm.getNickname());
            boolean used = hasUserIds.contains(id) ? true : false;
            map.put("used", used);  //是否已绑定
            userList.add(map);
        });
        return userList;
    }
}
