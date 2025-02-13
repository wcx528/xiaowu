package com.fmyd888.fengmao.module.information.dal.mysql.socialsecuritybase;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybasedept.SocialSecurityBaseDeptDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 社保基数维护表和部门组织 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface SocialSecurityBaseDeptMapper extends BaseMapperX<SocialSecurityBaseDeptDO> {

    default PageResult<SocialSecurityBaseDeptDO> selectPage(PageParam reqVO, Long entityId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SocialSecurityBaseDeptDO>()
            .eq(SocialSecurityBaseDeptDO::getEntityId, entityId)
            .orderByDesc(SocialSecurityBaseDeptDO::getId));
    }

    default List<SocialSecurityBaseDeptDO> selectListByEntityId(Long entityId) {
        return selectList(SocialSecurityBaseDeptDO::getEntityId, entityId);
    }

    default SocialSecurityBaseDeptDO selectByEntityId(Long entityId) {
        return selectOne(SocialSecurityBaseDeptDO::getEntityId, entityId);
    }

    default int deleteByEntityId(Long entityId) {
        return delete(SocialSecurityBaseDeptDO::getEntityId, entityId);
    }

}