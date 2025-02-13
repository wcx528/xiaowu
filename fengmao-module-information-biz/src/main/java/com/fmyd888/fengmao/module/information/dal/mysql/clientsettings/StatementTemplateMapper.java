package com.fmyd888.fengmao.module.information.dal.mysql.clientsettings;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.StatementTemplateSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.StatementTemplateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 子表-对账单模板 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface StatementTemplateMapper extends BaseMapperX<StatementTemplateDO> {

    default PageResult<StatementTemplateDO> selectPage(PageParam reqVO, Long entityId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StatementTemplateDO>()
            .eq(StatementTemplateDO::getEntityId, entityId)
            .orderByDesc(StatementTemplateDO::getId));
    }

    default List<StatementTemplateDO> selectListByEntityId(Long entityId) {
        return selectList(StatementTemplateDO::getEntityId, entityId);
    }

    default StatementTemplateDO selectByEntityId(Long entityId) {
        return selectOne(StatementTemplateDO::getEntityId, entityId);
    }

    default int deleteByEntityId(Long entityId) {
        return delete(StatementTemplateDO::getEntityId, entityId);
    }

}