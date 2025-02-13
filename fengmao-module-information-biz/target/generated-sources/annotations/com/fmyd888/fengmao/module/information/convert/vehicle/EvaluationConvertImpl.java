package com.fmyd888.fengmao.module.information.convert.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.EvaluationVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.EvaluationDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class EvaluationConvertImpl implements EvaluationConvert {

    @Override
    public EvaluationDO convert(EvaluationVO resource) {
        if ( resource == null ) {
            return null;
        }

        EvaluationDO.EvaluationDOBuilder evaluationDO = EvaluationDO.builder();

        evaluationDO.id( resource.getId() );
        evaluationDO.mainVehicleId( resource.getMainVehicleId() );
        evaluationDO.trailerId( resource.getTrailerId() );
        evaluationDO.testType( resource.getTestType() );
        evaluationDO.testUnit( resource.getTestUnit() );
        evaluationDO.testDate( resource.getTestDate() );
        evaluationDO.effectiveDate( resource.getEffectiveDate() );
        evaluationDO.reportCode( resource.getReportCode() );
        evaluationDO.remark( resource.getRemark() );

        return evaluationDO.build();
    }

    @Override
    public List<EvaluationDO> convertList(List<EvaluationVO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<EvaluationDO> list = new ArrayList<EvaluationDO>( resource.size() );
        for ( EvaluationVO evaluationVO : resource ) {
            list.add( convert( evaluationVO ) );
        }

        return list;
    }

    @Override
    public List<EvaluationVO> convertList02(List<EvaluationDO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<EvaluationVO> list = new ArrayList<EvaluationVO>( resource.size() );
        for ( EvaluationDO evaluationDO : resource ) {
            list.add( evaluationDOToEvaluationVO( evaluationDO ) );
        }

        return list;
    }

    protected EvaluationVO evaluationDOToEvaluationVO(EvaluationDO evaluationDO) {
        if ( evaluationDO == null ) {
            return null;
        }

        EvaluationVO evaluationVO = new EvaluationVO();

        evaluationVO.setId( evaluationDO.getId() );
        evaluationVO.setTestType( evaluationDO.getTestType() );
        evaluationVO.setTestUnit( evaluationDO.getTestUnit() );
        evaluationVO.setTestDate( evaluationDO.getTestDate() );
        evaluationVO.setEffectiveDate( evaluationDO.getEffectiveDate() );
        evaluationVO.setReportCode( evaluationDO.getReportCode() );
        evaluationVO.setRemark( evaluationDO.getRemark() );
        evaluationVO.setMainVehicleId( evaluationDO.getMainVehicleId() );
        evaluationVO.setTrailerId( evaluationDO.getTrailerId() );
        evaluationVO.setCreator( evaluationDO.getCreator() );

        return evaluationVO;
    }
}
