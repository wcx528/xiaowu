package com.fmyd888.fengmao.module.information.convert.salaryRule;

import com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo.SalaryRuleRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo.SalaryRuleSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule.SalaryRuleDO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class SalaryRuleConvertImpl implements SalaryRuleConvert {

    @Override
    public SalaryRuleDO convert(SalaryRuleSaveReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        SalaryRuleDO.SalaryRuleDOBuilder salaryRuleDO = SalaryRuleDO.builder();

        salaryRuleDO.id( bean.getId() );
        salaryRuleDO.position( bean.getPosition() );
        salaryRuleDO.basicSalary( bean.getBasicSalary() );
        salaryRuleDO.attendanceAward( bean.getAttendanceAward() );
        salaryRuleDO.positionSubsidy( bean.getPositionSubsidy() );
        salaryRuleDO.structuralSubsidy( bean.getStructuralSubsidy() );
        salaryRuleDO.other( bean.getOther() );
        salaryRuleDO.salaryTotal( bean.getSalaryTotal() );
        salaryRuleDO.grade( bean.getGrade() );
        salaryRuleDO.remark( bean.getRemark() );
        salaryRuleDO.gradeStandard( bean.getGradeStandard() );
        salaryRuleDO.annualSalary( bean.getAnnualSalary() );

        return salaryRuleDO.build();
    }

    @Override
    public SalaryRuleRespVO convert(SalaryRuleDO bean) {
        if ( bean == null ) {
            return null;
        }

        SalaryRuleRespVO salaryRuleRespVO = new SalaryRuleRespVO();

        salaryRuleRespVO.setId( bean.getId() );
        salaryRuleRespVO.setCreateTime( bean.getCreateTime() );
        salaryRuleRespVO.setPosition( bean.getPosition() );
        salaryRuleRespVO.setBasicSalary( bean.getBasicSalary() );
        salaryRuleRespVO.setAttendanceAward( bean.getAttendanceAward() );
        salaryRuleRespVO.setPositionSubsidy( bean.getPositionSubsidy() );
        salaryRuleRespVO.setStructuralSubsidy( bean.getStructuralSubsidy() );
        salaryRuleRespVO.setOther( bean.getOther() );
        salaryRuleRespVO.setSalaryTotal( bean.getSalaryTotal() );
        salaryRuleRespVO.setGrade( bean.getGrade() );
        salaryRuleRespVO.setRemark( bean.getRemark() );
        salaryRuleRespVO.setGradeStandard( bean.getGradeStandard() );
        salaryRuleRespVO.setAnnualSalary( bean.getAnnualSalary() );
        salaryRuleRespVO.setDeptName( bean.getDeptName() );
        salaryRuleRespVO.setCreatorName( bean.getCreatorName() );
        salaryRuleRespVO.setUpdaterName( bean.getUpdaterName() );

        return salaryRuleRespVO;
    }
}
