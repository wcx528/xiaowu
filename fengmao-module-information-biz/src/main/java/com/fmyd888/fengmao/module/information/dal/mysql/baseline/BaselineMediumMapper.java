package com.fmyd888.fengmao.module.information.dal.mysql.baseline;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineMediumDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 基线与运输介质关系 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface BaselineMediumMapper extends BaseMapperX<BaselineMediumDO> {

    default List<BaselineMediumDO> selectListByEntityIds(List<Long> ids) {
        MPJLambdaWrapper<BaselineMediumDO> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper
                .select(BaselineMediumDO::getId, BaselineMediumDO::getEntityId,BaselineMediumDO::getCommodityId)
                .selectAs(CommodityDO::getName,BaselineMediumDO::getCommodityName)
                .leftJoin(CommodityDO.class, CommodityDO::getId,BaselineMediumDO::getCommodityId)
                .in(BaselineMediumDO::getEntityId, ids);
        return selectJoinList(BaselineMediumDO.class,queryWrapper);
    }
}