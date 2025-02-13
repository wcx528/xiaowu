package com.fmyd888.fengmao.module.information.dal.mysql.measurement;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/11/23 12:01
 * @Version: 1.0
 * @Description:
 */
@Mapper
public interface MeasurementDeptMapper extends BaseMapperX<MeasurementDeptDO> {
    default List<MeasurementDeptDO> selectListByIds(List<Long> ids) {
        return selectJoinList(MeasurementDeptDO.class,new MPJLambdaWrapper<MeasurementDeptDO>()
                .select(MeasurementDeptDO::getEntityId)
                .selectAs(DeptDO::getName, MeasurementDeptDO::getDeptName)
                .leftJoin(DeptDO.class, DeptDO::getId, MeasurementDeptDO::getDeptId)
                .in(MeasurementDeptDO::getEntityId, ids)
                .disableSubLogicDel());
    }
}
