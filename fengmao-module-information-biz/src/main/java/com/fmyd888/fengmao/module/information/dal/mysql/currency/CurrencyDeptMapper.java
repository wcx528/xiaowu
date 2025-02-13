package com.fmyd888.fengmao.module.information.dal.mysql.currency;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/11/22 16:04
 * @Version: 1.0
 * @Description: 部门与部门组织 Mapper
 */
@Mapper
public interface CurrencyDeptMapper extends BaseMapperX<CurrencyDeptDO> {
    default List<CurrencyDeptDO> selectListByIds(List<Long> ids) {
        return selectJoinList(CurrencyDeptDO.class,new MPJLambdaWrapper<CurrencyDeptDO>()
                .select(CurrencyDeptDO::getEntityId)
                .selectAs(DeptDO::getName, CurrencyDeptDO::getDeptName)
                .leftJoin(DeptDO.class, DeptDO::getId, CurrencyDeptDO::getDeptId)
                .in(CurrencyDeptDO::getEntityId, ids)
                .disableSubLogicDel());
    }
}
