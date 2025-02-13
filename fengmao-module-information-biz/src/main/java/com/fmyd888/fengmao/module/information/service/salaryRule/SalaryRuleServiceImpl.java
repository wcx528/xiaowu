package com.fmyd888.fengmao.module.information.service.salaryRule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo.*;
import com.fmyd888.fengmao.module.information.convert.salaryRule.SalaryRuleConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule.SalaryRuleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule.SalaryRuleDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.salaryrule.SalaryRuleDeptMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.salaryrule.SalaryRuleMapper;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 薪资规则配置 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class SalaryRuleServiceImpl implements SalaryRuleService {

    @Resource
    private SalaryRuleMapper salaryRuleMapper;
    @Resource
    private SalaryRuleDeptMapper salaryRuleDeptMapper;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private SalaryRuleDeptService salaryRuleDeptService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSalaryRule(SalaryRuleSaveReqVO createReqVO) {
        SalaryRuleDO salaryRule = SalaryRuleConvert.INSTANCE.convert(createReqVO);
        // 计算 annual_salary
        BigDecimal salaryTotal = salaryRule.getSalaryTotal(); // 假设这里是工资合计
        //合集工资 
        BigDecimal yearMoth = BigDecimal.valueOf(13);
        BigDecimal annualSalary = salaryTotal.multiply(yearMoth);
        salaryRule.setAnnualSalary(annualSalary); // 设置 annual_salary

        //获取部门id
        salaryRuleMapper.insert(salaryRule);
        Long id = salaryRule.getId();
        // 1.2插入绑定组织表
        Set<Long> deptIds = createReqVO.getDeptIds();
        salaryRuleDeptService.bindDeptsToEntity(id, deptIds);
        // 返回
        return id;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSalaryRule(SalaryRuleSaveReqVO updateReqVO) {
        // 校验存在
        Long id = updateReqVO.getId();
        validateSalaryRuleExists(id);
        // 更新
        SalaryRuleDO updateObj = BeanUtils.toBean(updateReqVO, SalaryRuleDO.class);
        Set<Long> deptIds = updateReqVO.getDeptIds();

        salaryRuleMapper.updateById(updateObj);
        salaryRuleDeptService.updateBindDeptsToEntity(id, deptIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSalaryRule(Long id) {
        // 校验存在
        validateSalaryRuleExists(id);
        // 删除
        salaryRuleMapper.deleteById(id);
        salaryRuleDeptService.deleteBindDeptsByEntityId(id);
    }

    private void validateSalaryRuleExists(Long id) {
        if (salaryRuleMapper.selectById(id) == null) {
            throw exception(SALARY_RULE_NOT_EXISTS);
        }
    }


    @Override
    public SalaryRuleRespVO getSalaryRule(Long id) {
        SalaryRuleDO salaryRuleDO = salaryRuleMapper.selectById(id);
        SalaryRuleRespVO salaryRuleVO = SalaryRuleConvert.INSTANCE.convert(salaryRuleDO);
        Set<Long> deptIdSet = salaryRuleDeptService.findDeptIdsByEntityId(id);
        HashSet<Long> deptIdList = new HashSet<>(deptIdSet);
        salaryRuleVO.setDeptIds(deptIdList);
        return salaryRuleVO;
    }

    @Override
    public List<SalaryRuleDO> getSalaryRuleList(Collection<Long> ids) {
        return salaryRuleMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<SalaryRuleRespVO> getSalaryRulePage(SalaryRulePageReqVO pageReqVO) {
        PageResult<SalaryRuleDO> pageResult = salaryRuleMapper.selectJoinTilePage(pageReqVO);
        PageResult<SalaryRuleRespVO> result = BeanUtils.toBean(pageResult, SalaryRuleRespVO.class);
        if (!CollUtil.isEmpty(result.getList())) {
            List<Long> ids = result.getList().stream().map(SalaryRuleRespVO::getId).collect(Collectors.toList());
            List<SalaryRuleDeptDO> details = salaryRuleDeptMapper.selectListByIds(ids);
            for (SalaryRuleRespVO iterm : result.getList()) {
                List<Long> deptIds = details.stream().filter(detail -> Objects.equals(detail.getEntityId(), iterm.getId())).map(SalaryRuleDeptDO::getDeptId).collect(Collectors.toList());
                if (ObjectUtil.isNotEmpty(deptIds))
                    iterm.setDeptIds(new HashSet<>(deptIds));
                List<String> deptNames = details.stream().filter(detail -> Objects.equals(detail.getEntityId(), iterm.getId())).map(SalaryRuleDeptDO::getDeptName).collect(Collectors.toList());
                iterm.setDeptName(String.join(",", deptNames));
            }
        }
        return result;
    }


    @Override
    public List<SalaryRuleExcelVO> getSalaryRuleList(SalaryRuleListReqVO listReqVO) {
        SalaryRulePageReqVO req = BeanUtils.toBean(listReqVO, SalaryRulePageReqVO.class);
        req.setPageNo(1);
        req.setPageSize(9999);
        PageResult<SalaryRuleRespVO> pageResult = getSalaryRulePage(req);
        List<SalaryRuleRespVO> datas = pageResult.getList();
        List<SalaryRuleExcelVO> exportData = BeanUtils.toBean(datas, SalaryRuleExcelVO.class);
        return exportData;
    }

    @Override
    public void batchUpdateSalaryRule(List<SalaryRuleSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
        for (SalaryRuleSaveReqVO salaryRuleSaveReqVO : updateReqVOList) {
            updateSalaryRule(salaryRuleSaveReqVO);
        }

    }

    @Override
    public void batchDeleteSalaryRule(List<Long> ids) {
        // 在这里处理批量删除逻辑
        salaryRuleMapper.deleteBatchIds(ids);
    }

    @Override
    public List<SalaryRuleExcelVO> importPreviewList(List<SalaryRuleExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(SALARY_RULE_IMPORT_LIST_IS_EMPTY);
        }

        List<SalaryRuleExcelVO> excelVo = BeanUtils.toBean(importDatas, SalaryRuleExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(SALARY_RULE_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(SalaryRuleExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
            throw exception(SALARY_RULE_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(SALARY_RULE_IMPORT_PORT_REQUIRE);
        //以下是示例，补充逻辑时请替换成自己书写的逻辑
        //ImportResult result = ImportResult.builder()
        //.total(importReqVo.getImportDatas().size())
        //.importCount(0)
        //.failCount(importReqVo.getImportDatas().size())//假设这里假设都导入失败
        //.success(false)
        //.data(importReqVo.getImportDatas())
        //.build();
        //return result;
    }

    @Override
    public void assignSalaryRuleToDept(SalaryRuleDeptReqVO salaryRuleDeptReqVO) {

        Long salaryRuleId = salaryRuleDeptReqVO.getSalaryRuleId();
        Set<Long> deptIds = salaryRuleDeptReqVO.getDeptIds();
        salaryRuleDeptService.updateBindDeptsToEntity(salaryRuleId, deptIds);
    }


}
