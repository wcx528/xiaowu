package com.fmyd888.fengmao.module.information.dal.mysql.salaryrule;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule.SalaryRuleDeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 其他合同资料 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface SalaryRuleDeptMapper extends BaseMapperX<SalaryRuleDeptDO> {
    default List<SalaryRuleDeptDO> selectListByIds(List<Long> ids) {
        return selectJoinList(SalaryRuleDeptDO.class,new MPJLambdaWrapper<SalaryRuleDeptDO>()
                .select(SalaryRuleDeptDO::getEntityId)
                .selectAs(DeptDO::getName, SalaryRuleDeptDO::getDeptName)
                .leftJoin(DeptDO.class, DeptDO::getId, SalaryRuleDeptDO::getDeptId)
                .in(MeasurementDeptDO::getEntityId, ids)
                .disableSubLogicDel());
    }
}
