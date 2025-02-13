package com.fmyd888.fengmao.module.information.dal.mysql.riskmaintenance;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskMaintenanceCommodityDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 隐患排查项目维护与介质关联 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface RiskMaintenanceCommodityMapper extends BaseMapperX<RiskMaintenanceCommodityDO> {

    default PageResult<RiskMaintenanceCommodityDO> selectPage(PageParam reqVO, Long entityId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RiskMaintenanceCommodityDO>()
            .eq(RiskMaintenanceCommodityDO::getEntityId, entityId)
            .orderByDesc(RiskMaintenanceCommodityDO::getId));
    }

    default List<RiskMaintenanceCommodityDO> selectListByEntityId(Long entityId) {
        return selectList(RiskMaintenanceCommodityDO::getEntityId, entityId);
    }

    default RiskMaintenanceCommodityDO selectByEntityId(Long entityId) {
        return selectOne(RiskMaintenanceCommodityDO::getEntityId, entityId);
    }

    default int deleteByEntityId(Long entityId) {
        return delete(RiskMaintenanceCommodityDO::getEntityId, entityId);
    }

    // 批量新增方法
    void batchInsertRiskCommodity(@Param("commodityIds") List<Long> commodityIds, @Param("entityId") Long entityId);

}