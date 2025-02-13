package com.fmyd888.fengmao.module.information.dal.mysql.salesman;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDeptDO;
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
public interface SalesmanDeptMapper extends BaseMapperX<SalesmanDeptDO> {
    default List<SalesmanDeptDO> selectListByIds(List<Long> ids) {
        return selectJoinList(SalesmanDeptDO.class,new MPJLambdaWrapper<SalesmanDeptDO>()
                .select(SalesmanDeptDO::getEntityId)
                .selectAs(DeptDO::getName, SalesmanDeptDO::getDeptName)
                .leftJoin(DeptDO.class, DeptDO::getId, SalesmanDeptDO::getDeptId)
                .in(MeasurementDeptDO::getEntityId, ids)
                .disableSubLogicDel());
    }
}
