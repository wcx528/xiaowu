package com.fmyd888.fengmao.module.information.dal.mysql.coefficient;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MaintenanceCostsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 维修费用系数维护明细 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface MaintenanceCostsMapper extends BaseMapperX<MaintenanceCostsDO> {

    default PageResult<MaintenanceCostsDO> selectPage(PageParam reqVO, Long coefficientId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintenanceCostsDO>()
            .eq(MaintenanceCostsDO::getCoefficientId, coefficientId)
            .orderByDesc(MaintenanceCostsDO::getId));
    }

    default List<MaintenanceCostsDO> selectListByCoefficientId(Long coefficientId) {
        return selectList(MaintenanceCostsDO::getCoefficientId, coefficientId);
    }

    default MaintenanceCostsDO selectByCoefficientId(Long coefficientId) {
        return selectOne(MaintenanceCostsDO::getCoefficientId, coefficientId);
    }

    default int deleteByCoefficientId(Long coefficientId) {
        return delete(MaintenanceCostsDO::getCoefficientId, coefficientId);
    }

}