package com.fmyd888.fengmao.module.information.dal.mysql.encodingrules;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.EncodingRulesExportReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.EncodingRulesListReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.EncodingRulesPageReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.encodingrules.EncodingRulesDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 编码规则设置 Mapper
 *
 * @author fengmao
 */
@Mapper
public interface EncodingRulesMapper extends BaseMapperX<EncodingRulesDO> {

    default PageResult<EncodingRulesDO> selectPage(EncodingRulesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EncodingRulesDO>()
                .likeIfPresent(EncodingRulesDO::getRuleName, reqVO.getRuleName())
                .eqIfPresent(EncodingRulesDO::getRuleType, reqVO.getRuleType())
                .eqIfPresent(EncodingRulesDO::getPrefix, reqVO.getPrefix())
                .eqIfPresent(EncodingRulesDO::getSerialNumber, reqVO.getSerialNumber())
                .eqIfPresent(EncodingRulesDO::getTimeFormat, reqVO.getTimeFormat())
                .eqIfPresent(EncodingRulesDO::getSuffix, reqVO.getSuffix())
                .eqIfPresent(EncodingRulesDO::getRuleSeparator, reqVO.getRuleSeparator())
                .eqIfPresent(EncodingRulesDO::getStatus, reqVO.getStatus())
                .eqIfPresent(EncodingRulesDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(EncodingRulesDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EncodingRulesDO::getId));
    }

    default List<EncodingRulesDO> selectList(EncodingRulesExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EncodingRulesDO>()
                .likeIfPresent(EncodingRulesDO::getRuleName, reqVO.getRuleName())
                .eqIfPresent(EncodingRulesDO::getRuleType, reqVO.getRuleType())
                .eqIfPresent(EncodingRulesDO::getPrefix, reqVO.getPrefix())
                .eqIfPresent(EncodingRulesDO::getSerialNumber, reqVO.getSerialNumber())
                .eqIfPresent(EncodingRulesDO::getTimeFormat, reqVO.getTimeFormat())
                .eqIfPresent(EncodingRulesDO::getSuffix, reqVO.getSuffix())
                .eqIfPresent(EncodingRulesDO::getRuleSeparator, reqVO.getRuleSeparator())
                .eqIfPresent(EncodingRulesDO::getStatus, reqVO.getStatus())
                .eqIfPresent(EncodingRulesDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(EncodingRulesDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EncodingRulesDO::getId));
    }

        default List<EncodingRulesDO> selectList(EncodingRulesListReqVO reqVO) {
            return selectList(new LambdaQueryWrapperX<EncodingRulesDO>()
                    .likeIfPresent(EncodingRulesDO::getRuleName, reqVO.getRuleName())
                    .eqIfPresent(EncodingRulesDO::getRuleType, reqVO.getRuleType())
                    .eqIfPresent(EncodingRulesDO::getPrefix, reqVO.getPrefix())
                    .eqIfPresent(EncodingRulesDO::getSerialNumber, reqVO.getSerialNumber())
                    .eqIfPresent(EncodingRulesDO::getTimeFormat, reqVO.getTimeFormat())
                    .eqIfPresent(EncodingRulesDO::getSuffix, reqVO.getSuffix())
                    .eqIfPresent(EncodingRulesDO::getRuleSeparator, reqVO.getRuleSeparator())
                    .eqIfPresent(EncodingRulesDO::getStatus, reqVO.getStatus())
                    .eqIfPresent(EncodingRulesDO::getRemark, reqVO.getRemark())
                    .betweenIfPresent(EncodingRulesDO::getCreateTime, reqVO.getCreateTime())
                    .eqIfPresent(EncodingRulesDO::getModifiable, reqVO.getModifiable())
                    .eqIfPresent(EncodingRulesDO::getManuallyGenerated, reqVO.getManuallyGenerated())
                    .eqIfPresent(EncodingRulesDO::getDictDateId, reqVO.getDictDateId())
                    .orderByDesc(EncodingRulesDO::getId));
        }

}
