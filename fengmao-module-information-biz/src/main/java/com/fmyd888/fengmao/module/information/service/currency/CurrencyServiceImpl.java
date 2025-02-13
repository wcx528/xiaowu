package com.fmyd888.fengmao.module.information.service.currency;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fmyd888.fengmao.framework.common.exception.ServiceException;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.datapermission.core.util.DataPermissionUtils;
import com.fmyd888.fengmao.module.information.dal.mysql.currency.CurrencyDeptMapper;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.controller.admin.currency.CurrencyDeptReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.*;
import com.fmyd888.fengmao.module.information.convert.currency.CurrencyConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.currency.CurrencyMapper;
import com.fmyd888.fengmao.module.information.dal.redis.RedisKeyConstants;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import com.fmyd888.fengmao.module.system.convert.dept.DeptConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 货币 Service 实现类
 *
 * @author 丰茂企业
 */
@Service
@Validated
public class CurrencyServiceImpl implements CurrencyService {

    @Resource
    private CurrencyMapper currencyMapper;
    @Resource
    private CurrencyDeptMapper currencyDeptMapper;
    @Resource
    private DeptService deptService;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private EncodingRulesService encodingRulesService;
    @Resource
    private CurrencyDeptService currencyDeptService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCurrency(CurrencyCreateReqVO createReqVO) {
        // 插入
        createReqVO.setId(null);
        String currencyName = createReqVO.getCurrencyName();
        validateExists(null, currencyName);
        CurrencyDO currency = CurrencyConvert.INSTANCE.convert(createReqVO);
        String currencyCode = EncodingRulesEnum.CURRENCY_CODE.getBusinessCode();
        String code = encodingRulesService.getCodeByRuleType(currencyCode);
        currency.setCurrencyCode(code);
        currencyMapper.insert(currency);

        Long id = currency.getId();
        Set<Long> dept = createReqVO.getDeptIds();
        currencyDeptService.bindDeptsToEntity(id, dept);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCurrency(CurrencyUpdateReqVO updateReqVO) {
        // 校验存在
        Long id = updateReqVO.getId();
        String currencyName = updateReqVO.getCurrencyName();
        validateExists(id, currencyName);
        CurrencyDO updateObj = CurrencyConvert.INSTANCE.convert(updateReqVO);
        currencyMapper.updateById(updateObj);
        Set<Long> deptIds = updateReqVO.getDeptIds();
        currencyDeptService.updateBindDeptsToEntity(id, deptIds);
    }

    private void validateExists(Long id, String currencyName) {
        LambdaQueryWrapper<CurrencyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(id != null, CurrencyDO::getId, id); // 排除当前要修改的记录
        queryWrapper.eq(CurrencyDO::getCurrencyName, currencyName);
        boolean exists = currencyMapper.exists(queryWrapper);
        if (exists) {
            throw exception(DUPLICATE_CURRENCY_NAME, currencyName);
        }
    }

    @Override
    public void deleteCurrency(Long id) {
        // 删除
        currencyMapper.deleteById(id);
    }

    @Override
    public CurrencyRespVO getCurrency(Long id) {
        CurrencyDO currencyDO = currencyMapper.selectById(id);
        CurrencyRespVO currencyVO = CurrencyConvert.INSTANCE.convert(currencyDO);
        setDeptInfo(currencyVO);
        return currencyVO;
    }

    @Override
    public List<CurrencyRespVO> getCurrencyList(Collection<Long> ids) {
        List<CurrencyDO> list = currencyMapper.selectListByIds(new ArrayList<>(ids));
        list.forEach(p -> {
            p.setCreator(p.getCreatorName());
            p.setUpdater(p.getUpdaterName());
        });
        List<CurrencyRespVO> currencys = CurrencyConvert.INSTANCE.convertList(list);
        if (ObjectUtil.isNotEmpty(currencys)) {
            List<CurrencyDeptDO> details = currencyDeptMapper.selectListByIds(new ArrayList<>(ids));
            currencys.forEach(p -> {
                List<Long> collect = details.stream().filter(q -> ObjectUtil.equal(p.getId(), q.getEntityId())).map(q -> q.getDeptId()).collect(Collectors.toList());
                if (ObjUtil.isNotEmpty(collect)) {
                    p.setOrganization(collect);
                }
            });
        }
        return currencys;
    }

    private void setDeptInfo(CurrencyRespVO respVO) {
        //3、获取客户对应的部门组织信息
        //List<CurrencyDeptDO> customerDept = currencyDeptService.getCurrencyDeptByCurrencyId(respVO.getId());
        //List<Long> ids = customerDept.stream().map(CurrencyDeptDO::getDeptId).collect(Collectors.toList());

        Set<Long> deptSet = currencyDeptService.findDeptIdsByEntityId(respVO.getId());
        List<Long> deptList = new ArrayList<>(deptSet);
        respVO.setOrganization(deptList);
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
    public PageResult<CurrencyRespVO> getCurrencyPage(CurrencyPageReqVO pageReqVO) {
        PageResult<CurrencyDO> currencyDOPageResult = currencyMapper.selectJoinTileList(pageReqVO);
        currencyDOPageResult.getList().forEach(p -> {
            p.setCreator(p.getCreatorName());
            p.setUpdater(p.getUpdaterName());
        });
        PageResult<CurrencyRespVO> currencyRespVOPageResult = CurrencyConvert.INSTANCE.convertPage(currencyDOPageResult);
        if (ObjectUtil.isNotEmpty(currencyRespVOPageResult.getList())) {
            List<Long> ids = currencyRespVOPageResult.getList().stream().map(CurrencyRespVO::getId).collect(Collectors.toList());
            List<CurrencyDeptDO> details = currencyDeptMapper.selectListByIds(ids);
            currencyRespVOPageResult.getList().forEach(p -> {
                List<Long> collect = details.stream().filter(q -> ObjectUtil.equal(p.getId(), q.getEntityId())).map(q -> q.getDeptId()).collect(Collectors.toList());
                if (ObjUtil.isNotEmpty(collect)) {
                    p.setOrganization(collect);
                }
            });
        }
        return currencyRespVOPageResult;
    }

    @Override
    public List<CurrencyExcelVO> getCurrencyList(CurrencyExportReqVO exportReqVO) {
        List<CurrencyDO> currencyDOS = currencyMapper.selectStoreExportList(exportReqVO);
        currencyDOS.forEach(p -> {
            p.setCreator(p.getCreatorName());
            p.setUpdater(p.getUpdaterName());
        });
        // 导出 Excel
        List<CurrencyExcelVO> datas = BeanUtils.toBean(currencyDOS, CurrencyExcelVO.class);
        if (ObjectUtil.isNotEmpty(datas)) {
            List<Long> ids = datas.stream().map(CurrencyExcelVO::getId).collect(Collectors.toList());
            List<CurrencyDeptDO> list = currencyDeptMapper.selectListByIds(ids);
            datas.forEach(iterm -> {
                List<String> str = list.stream().filter(q -> ObjectUtil.equal(iterm.getId(), q.getEntityId())).map(CurrencyDeptDO::getDeptName).collect(Collectors.toList());
                if (ObjectUtil.isNotEmpty(str)) {
                    iterm.setOrganization(String.join(",", str));
                }
            });
        }
        return datas;
    }

    @Override
    public void batchUpdateCurrency(List<CurrencyUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (CurrencyUpdateReqVO updateReqVO : updateReqVOList) {
            updateCurrency(updateReqVO);
        }
    }

    @Override
    public void batchDeleteCurrency(List<Long> ids) {
        // 在这里处理批量删除逻辑
        currencyMapper.deleteBatchIds(ids);
    }

    @Override
    public void batchImportCurrency(List<CurrencyDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        currencyMapper.insertBatch(importReqVOList);
    }

    @Override
    public void updateStatus(Long id) {
        UpdateWrapper<CurrencyDO> updateWrapper = new UpdateWrapper<>();
        CurrencyDO currencyDO = currencyMapper.selectById(id);
        updateWrapper.eq("id", id);
        Byte status = currencyDO.getStatus();
        //状态修改取反
        if (status == 0) {
            updateWrapper.set("status", 1); // 设置要更新的字段和值
        } else if (status == 1) {
            updateWrapper.set("status", 0);
        } else {
            throw new RuntimeException("货币状态错误！");
        }
        int rowsAffected = currencyMapper.update(null, updateWrapper); // 使用update方法更新字段

        if (rowsAffected > 0) {
            System.out.println("货币状态成功");
        } else {
            System.out.println("货币状态失败");
        }
    }

    @Override
    public List<DeptSimpleRespVO> getDeptInfoList(Long currencyId) {
        Set<Long> deptIdSet = currencyDeptService.findDeptIdsByEntityId(currencyId);
        List<DeptDO> deptList = deptService.getDeptList(deptIdSet);
        List<DeptSimpleRespVO> convertList02 = DeptConvert.INSTANCE.convertList02(deptList);
        return convertList02;
    }

    @Override
    public void assignCurrencyToDept(CurrencyDeptReqVO currencyDeptReqVO) {
        Long currencyId = currencyDeptReqVO.getCurrencyId();
        Set<Long> deptIds = currencyDeptReqVO.getDeptIds();
        currencyDeptService.updateBindDeptsToEntity(currencyId, deptIds);
    }


    /**
     * @param importCurrency
     * @param isUpdateSupport
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public CurrencyImportRespVO importCurrencyList(List<CurrencyImportExcelVO> importCurrency, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importCurrency)) {
            throw new RuntimeException("导入货币数据不能为空");
        }
        CurrencyImportRespVO respVO = CurrencyImportRespVO.builder().createCurrencynames(new ArrayList<>())
                .updateCurrencynames(new ArrayList<>())
                .failureCurrencynames(new LinkedHashMap<>()).build();
        //获得创建者和更新者信息
        importCurrency.forEach(iterm -> {
            // 校验，判断是否有不符合的原因
            try {
                validateCurrencyForCreateOrUpdate(iterm);
            } catch (ServiceException ex) {
                respVO.getFailureCurrencynames().put(iterm.getCurrencyName(), ex.getMessage());
                return;
            }
            // 1、根据货币名称查询判断如果不存在，在进行插入返回
            CurrencyDO currencyDO = currencyMapper.selectOne(CurrencyDO::getCurrencyName, iterm.getCurrencyName());
            if (ObjectUtils.isEmpty(currencyDO)) {
                CurrencyDO currencyDO1 = CurrencyConvert.INSTANCE.convert(iterm);
                //获取货币编码组装
                String code = EncodingRulesEnum.DINGTALK_DEPT_CODE.getBusinessCode();
                String codeByRuleType = encodingRulesService.getCodeByRuleType(code);
                currencyDO1.setCurrencyCode(codeByRuleType);
                currencyMapper.insert(currencyDO1);
                respVO.getCreateCurrencynames().add(iterm.getCurrencyName());
                return;
            }

            //2、如果货币名称存在，判断是否允许更新返回
            if (!isUpdateSupport) {
                respVO.getFailureCurrencynames().put(iterm.getCurrencyName(), "货币名称已经存在！");
                return;
            }
            // 3、货币名称存在，进行更新返回
            CurrencyDO convert = CurrencyConvert.INSTANCE.convert(iterm);
            convert.setId(currencyDO.getId());
            currencyMapper.updateById(convert);
            respVO.getUpdateCurrencynames().add(iterm.getCurrencyName());
        });
        return respVO;

    }

    private void validateCurrencyForCreateOrUpdate(CurrencyImportExcelVO excelVO) {
        DataPermissionUtils.executeIgnore(() -> {
            String currencyName = excelVO.getCurrencyName();
            CurrencyDO currencyDO = currencyMapper.selectOne(CurrencyDO::getCurrencyName, currencyName);
            if (!ObjectUtils.isEmpty(currencyDO)) {
                throw exception(DUPLICATE_CURRENCY_NAME, currencyName);
            }
            //String currencyCode = excelVO.getCurrencyCode();
            //CurrencyDO currencyDOCode = currencyMapper.selectOne(CurrencyDO::getCurrencyCode, currencyCode);
            //if (!ObjectUtils.isEmpty(currencyDOCode)) {
            //    throw new RuntimeException("货币编码:" + currencyCode + "已存在！");
            //}
            String currencySymbol = excelVO.getCurrencySymbol();
            CurrencyDO currencyDOSymbol = currencyMapper.selectOne(CurrencyDO::getCurrencySymbol, currencySymbol);
            if (!ObjectUtils.isEmpty(currencyDOSymbol)) {
                throw exception(DUPLICATE_SYMBOL_NAME, currencySymbol);
            }
            String currencyIdentify = excelVO.getCurrencyIdentify();
            CurrencyDO currencyDOIdentify = currencyMapper.selectOne(CurrencyDO::getCurrencyIdentify, currencyIdentify);
            if (!ObjectUtils.isEmpty(currencyDOIdentify)) {
                throw exception(DUPLICATE_IDENTIFY_NAME, currencyIdentify);
            }
        });
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.CURRENCY_SIMPLE_LIST + "#120", key = "#param.status+'_'+#param.searchKey+'_'+#param.id+'_'+#param.companyIds")
    public List<HashMap<String, Object>> getSimpleCurrencyList(CommonQueryParam param) {
        if (ObjectUtil.isEmpty(param.getStatus()))
            param.setStatus(0);
        List<HashMap<String, Object>> list = new ArrayList<>();
        MPJLambdaWrapper<CurrencyDO> queryWrapper = new MPJLambdaWrapper<CurrencyDO>()
                .select(CurrencyDO::getId, CurrencyDO::getCurrencyName, CurrencyDO::getCurrencySymbol)
                .leftJoin(CurrencyDeptDO.class, CurrencyDeptDO::getEntityId, CurrencyDO::getId)
                .eq(CurrencyDO::getStatus, param.getStatus())
                .eq(ObjectUtil.isNotEmpty(param.getId()), CurrencyDO::getId, param.getId())
                .in(ArrayUtil.isNotEmpty(param.getCompanyIds()), CurrencyDeptDO::getDeptId, param.getCompanyIds())
                .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(CurrencyDO::getCurrencyName, param.getSearchKey()).or().like(CurrencyDO::getCurrencyCode, param.getSearchKey()))
                .groupBy(CurrencyDO::getId, CurrencyDO::getCurrencyName, CurrencyDO::getCurrencySymbol)
                .disableSubLogicDel();

        List<CurrencyDO> currencyDOS = currencyMapper.selectJoinList(CurrencyDO.class, queryWrapper);
        currencyDOS.forEach(iterm -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("name", iterm.getCurrencyName());
            map.put("currencySymbol", iterm.getCurrencySymbol());
            list.add(map);
        });
        return list;
    }
}


