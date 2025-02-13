package com.fmyd888.fengmao.module.information.convert.encodingrules;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.EncodingRulesCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.EncodingRulesExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.EncodingRulesRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.EncodingRulesUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.encodingrules.EncodingRulesDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class EncodingRulesConvertImpl implements EncodingRulesConvert {

    @Override
    public EncodingRulesDO convert(EncodingRulesCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        EncodingRulesDO.EncodingRulesDOBuilder encodingRulesDO = EncodingRulesDO.builder();

        encodingRulesDO.ruleName( bean.getRuleName() );
        encodingRulesDO.ruleType( bean.getRuleType() );
        encodingRulesDO.prefix( bean.getPrefix() );
        encodingRulesDO.timeFormat( bean.getTimeFormat() );
        encodingRulesDO.ruleSeparator( bean.getRuleSeparator() );
        encodingRulesDO.serialNumber( bean.getSerialNumber() );
        encodingRulesDO.suffix( bean.getSuffix() );
        encodingRulesDO.status( bean.getStatus() );
        encodingRulesDO.remark( bean.getRemark() );
        encodingRulesDO.modifiable( bean.getModifiable() );
        encodingRulesDO.manuallyGenerated( bean.getManuallyGenerated() );
        encodingRulesDO.dictDateId( bean.getDictDateId() );

        return encodingRulesDO.build();
    }

    @Override
    public EncodingRulesDO convert(EncodingRulesUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        EncodingRulesDO.EncodingRulesDOBuilder encodingRulesDO = EncodingRulesDO.builder();

        encodingRulesDO.id( bean.getId() );
        encodingRulesDO.ruleName( bean.getRuleName() );
        encodingRulesDO.ruleType( bean.getRuleType() );
        encodingRulesDO.prefix( bean.getPrefix() );
        encodingRulesDO.timeFormat( bean.getTimeFormat() );
        encodingRulesDO.ruleSeparator( bean.getRuleSeparator() );
        encodingRulesDO.serialNumber( bean.getSerialNumber() );
        encodingRulesDO.suffix( bean.getSuffix() );
        encodingRulesDO.status( bean.getStatus() );
        encodingRulesDO.remark( bean.getRemark() );
        encodingRulesDO.modifiable( bean.getModifiable() );
        encodingRulesDO.manuallyGenerated( bean.getManuallyGenerated() );
        encodingRulesDO.dictDateId( bean.getDictDateId() );

        return encodingRulesDO.build();
    }

    @Override
    public EncodingRulesRespVO convert(EncodingRulesDO bean) {
        if ( bean == null ) {
            return null;
        }

        EncodingRulesRespVO encodingRulesRespVO = new EncodingRulesRespVO();

        encodingRulesRespVO.setRuleName( bean.getRuleName() );
        encodingRulesRespVO.setRuleType( bean.getRuleType() );
        encodingRulesRespVO.setPrefix( bean.getPrefix() );
        encodingRulesRespVO.setSerialNumber( bean.getSerialNumber() );
        encodingRulesRespVO.setTimeFormat( bean.getTimeFormat() );
        encodingRulesRespVO.setSuffix( bean.getSuffix() );
        encodingRulesRespVO.setRuleSeparator( bean.getRuleSeparator() );
        encodingRulesRespVO.setStatus( bean.getStatus() );
        encodingRulesRespVO.setRemark( bean.getRemark() );
        encodingRulesRespVO.setModifiable( bean.getModifiable() );
        encodingRulesRespVO.setManuallyGenerated( bean.getManuallyGenerated() );
        encodingRulesRespVO.setDictDateId( bean.getDictDateId() );
        encodingRulesRespVO.setId( bean.getId() );
        encodingRulesRespVO.setCreateTime( bean.getCreateTime() );

        return encodingRulesRespVO;
    }

    @Override
    public List<EncodingRulesRespVO> convertList(List<EncodingRulesDO> list) {
        if ( list == null ) {
            return null;
        }

        List<EncodingRulesRespVO> list1 = new ArrayList<EncodingRulesRespVO>( list.size() );
        for ( EncodingRulesDO encodingRulesDO : list ) {
            list1.add( convert( encodingRulesDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<EncodingRulesRespVO> convertPage(PageResult<EncodingRulesDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<EncodingRulesRespVO> pageResult = new PageResult<EncodingRulesRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<EncodingRulesExcelVO> convertList02(List<EncodingRulesDO> list) {
        if ( list == null ) {
            return null;
        }

        List<EncodingRulesExcelVO> list1 = new ArrayList<EncodingRulesExcelVO>( list.size() );
        for ( EncodingRulesDO encodingRulesDO : list ) {
            list1.add( encodingRulesDOToEncodingRulesExcelVO( encodingRulesDO ) );
        }

        return list1;
    }

    protected EncodingRulesExcelVO encodingRulesDOToEncodingRulesExcelVO(EncodingRulesDO encodingRulesDO) {
        if ( encodingRulesDO == null ) {
            return null;
        }

        EncodingRulesExcelVO encodingRulesExcelVO = new EncodingRulesExcelVO();

        encodingRulesExcelVO.setId( encodingRulesDO.getId() );
        encodingRulesExcelVO.setRuleName( encodingRulesDO.getRuleName() );
        encodingRulesExcelVO.setRuleType( encodingRulesDO.getRuleType() );
        encodingRulesExcelVO.setPrefix( encodingRulesDO.getPrefix() );
        encodingRulesExcelVO.setSerialNumber( encodingRulesDO.getSerialNumber() );
        encodingRulesExcelVO.setTimeFormat( encodingRulesDO.getTimeFormat() );
        encodingRulesExcelVO.setSuffix( encodingRulesDO.getSuffix() );
        encodingRulesExcelVO.setRuleSeparator( encodingRulesDO.getRuleSeparator() );
        encodingRulesExcelVO.setStatus( encodingRulesDO.getStatus() );
        encodingRulesExcelVO.setRemark( encodingRulesDO.getRemark() );
        encodingRulesExcelVO.setCreateTime( encodingRulesDO.getCreateTime() );
        encodingRulesExcelVO.setModifiable( encodingRulesDO.getModifiable() );
        encodingRulesExcelVO.setManuallyGenerated( encodingRulesDO.getManuallyGenerated() );

        return encodingRulesExcelVO;
    }
}
