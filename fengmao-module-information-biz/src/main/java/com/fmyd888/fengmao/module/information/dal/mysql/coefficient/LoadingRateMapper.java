package com.fmyd888.fengmao.module.information.dal.mysql.coefficient;

import java.util.*;


import com.fmyd888.fengmao.framework.common.pojo.PageParam;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.LoadingRateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 装载率系数明细 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface LoadingRateMapper extends BaseMapperX<LoadingRateDO> {

    default PageResult<LoadingRateDO> selectPage(PageParam reqVO, Long coefficientId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LoadingRateDO>()
            .eq(LoadingRateDO::getCoefficientId, coefficientId)
            .orderByDesc(LoadingRateDO::getId));
    }

    default List<LoadingRateDO> selectListByCoefficientId(Long coefficientId) {
        return selectList(LoadingRateDO::getCoefficientId, coefficientId);
    }

    default LoadingRateDO selectByCoefficientId(Long coefficientId) {
        return selectOne(LoadingRateDO::getCoefficientId, coefficientId);
    }

    default int deleteByCoefficientId(Long coefficientId) {
        return delete(LoadingRateDO::getCoefficientId, coefficientId);
    }

}