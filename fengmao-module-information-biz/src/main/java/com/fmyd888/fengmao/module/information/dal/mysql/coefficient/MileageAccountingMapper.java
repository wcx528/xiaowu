package com.fmyd888.fengmao.module.information.dal.mysql.coefficient;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MileageAccountingDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工资里程核算系数明细 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface MileageAccountingMapper extends BaseMapperX<MileageAccountingDO> {

    default PageResult<MileageAccountingDO> selectPage(PageParam reqVO, Long coefficientId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MileageAccountingDO>()
            .eq(MileageAccountingDO::getCoefficientId, coefficientId)
            .orderByDesc(MileageAccountingDO::getId));
    }

    default List<MileageAccountingDO> selectListByCoefficientId(Long coefficientId) {
        return selectList(MileageAccountingDO::getCoefficientId, coefficientId);
    }

    default MileageAccountingDO selectByCoefficientId(Long coefficientId) {
        return selectOne(MileageAccountingDO::getCoefficientId, coefficientId);
    }

    default int deleteByCoefficientId(Long coefficientId) {
        return delete(MileageAccountingDO::getCoefficientId, coefficientId);
    }

}