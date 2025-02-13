package com.fmyd888.fengmao.module.information.convert.measurement;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.MeasurementCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.MeasurementExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.MeasurementRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.MeasurementUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class MeasurementConvertImpl implements MeasurementConvert {

    @Override
    public MeasurementDO convert(MeasurementCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        MeasurementDO.MeasurementDOBuilder measurementDO = MeasurementDO.builder();

        measurementDO.code( bean.getCode() );
        measurementDO.name( bean.getName() );
        measurementDO.parentId( bean.getParentId() );
        measurementDO.remarks( bean.getRemarks() );

        return measurementDO.build();
    }

    @Override
    public MeasurementDO convert(MeasurementUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        MeasurementDO.MeasurementDOBuilder measurementDO = MeasurementDO.builder();

        measurementDO.id( bean.getId() );
        measurementDO.code( bean.getCode() );
        measurementDO.name( bean.getName() );
        measurementDO.parentId( bean.getParentId() );
        measurementDO.remarks( bean.getRemarks() );

        return measurementDO.build();
    }

    @Override
    public MeasurementRespVO convert(MeasurementDO bean) {
        if ( bean == null ) {
            return null;
        }

        MeasurementRespVO measurementRespVO = new MeasurementRespVO();

        measurementRespVO.setName( bean.getName() );
        measurementRespVO.setParentId( bean.getParentId() );
        measurementRespVO.setCode( bean.getCode() );
        measurementRespVO.setRemarks( bean.getRemarks() );
        measurementRespVO.setId( bean.getId() );
        measurementRespVO.setCreateTime( bean.getCreateTime() );
        measurementRespVO.setCreator( bean.getCreator() );
        measurementRespVO.setUpdater( bean.getUpdater() );
        measurementRespVO.setUpdateTime( bean.getUpdateTime() );
        measurementRespVO.setStatus( bean.getStatus() );

        return measurementRespVO;
    }

    @Override
    public List<MeasurementRespVO> convertList(List<MeasurementDO> list) {
        if ( list == null ) {
            return null;
        }

        List<MeasurementRespVO> list1 = new ArrayList<MeasurementRespVO>( list.size() );
        for ( MeasurementDO measurementDO : list ) {
            list1.add( convert( measurementDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<MeasurementRespVO> convertPage(PageResult<MeasurementDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<MeasurementRespVO> pageResult = new PageResult<MeasurementRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<MeasurementExcelVO> convertList02(List<MeasurementDO> list) {
        if ( list == null ) {
            return null;
        }

        List<MeasurementExcelVO> list1 = new ArrayList<MeasurementExcelVO>( list.size() );
        for ( MeasurementDO measurementDO : list ) {
            list1.add( measurementDOToMeasurementExcelVO( measurementDO ) );
        }

        return list1;
    }

    protected MeasurementExcelVO measurementDOToMeasurementExcelVO(MeasurementDO measurementDO) {
        if ( measurementDO == null ) {
            return null;
        }

        MeasurementExcelVO measurementExcelVO = new MeasurementExcelVO();

        measurementExcelVO.setId( measurementDO.getId() );
        measurementExcelVO.setCode( measurementDO.getCode() );
        measurementExcelVO.setName( measurementDO.getName() );
        measurementExcelVO.setParentId( measurementDO.getParentId() );
        if ( measurementDO.getStatus() != null ) {
            measurementExcelVO.setStatus( String.valueOf( measurementDO.getStatus() ) );
        }
        measurementExcelVO.setRemarks( measurementDO.getRemarks() );
        measurementExcelVO.setCreator( measurementDO.getCreator() );
        measurementExcelVO.setCreateTime( measurementDO.getCreateTime() );
        measurementExcelVO.setUpdater( measurementDO.getUpdater() );
        measurementExcelVO.setUpdateTime( measurementDO.getUpdateTime() );

        return measurementExcelVO;
    }
}
