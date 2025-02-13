package com.fmyd888.fengmao.module.information.dal.mysql.riskinspectionitem;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskInspectionItemDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 检查类型表(子表) Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface RiskInspectionItemMapper extends BaseMapperX<RiskInspectionItemDO> {

    default PageResult<RiskInspectionItemDO> selectPage(PageParam reqVO, Long entityId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RiskInspectionItemDO>()
            .eq(RiskInspectionItemDO::getEntityId, entityId)
            .orderByDesc(RiskInspectionItemDO::getId));
    }

    default List<RiskInspectionItemDO> selectListByEntityId(Long entityId) {
        return selectList(RiskInspectionItemDO::getEntityId, entityId);
    }

    default RiskInspectionItemDO selectByEntityId(Long entityId) {
        return selectOne(RiskInspectionItemDO::getEntityId, entityId);
    }

    default int deleteByEntityId(Long entityId) {
        return delete(RiskInspectionItemDO::getEntityId, entityId);
    }

}