package com.fmyd888.fengmao.module.information.convert.transportManger;

import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportdetail.TransportDetailDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class TransportDetailConvertImpl implements TransportDetailConvert {

    @Override
    public TransportDetailDO convert(TransportDetailSaveReqVO res) {
        if ( res == null ) {
            return null;
        }

        TransportDetailDO transportDetailDO = new TransportDetailDO();

        transportDetailDO.setId( res.getId() );
        transportDetailDO.setTransportId( res.getTransportId() );
        transportDetailDO.setTransportTonnage( res.getTransportTonnage() );
        transportDetailDO.setTransportCode( res.getTransportCode() );
        transportDetailDO.setIsOut( res.getIsOut() );
        List<Long> list = res.getTransportCarIds();
        if ( list != null ) {
            transportDetailDO.setTransportCarIds( new ArrayList<Long>( list ) );
        }

        return transportDetailDO;
    }

    @Override
    public TransportDetailSaveReqVO convert(TransportDetailDO res) {
        if ( res == null ) {
            return null;
        }

        TransportDetailSaveReqVO transportDetailSaveReqVO = new TransportDetailSaveReqVO();

        transportDetailSaveReqVO.setId( res.getId() );
        transportDetailSaveReqVO.setTransportId( res.getTransportId() );
        transportDetailSaveReqVO.setTransportCode( res.getTransportCode() );
        transportDetailSaveReqVO.setTransportTonnage( res.getTransportTonnage() );
        transportDetailSaveReqVO.setIsOut( res.getIsOut() );
        List<Long> list = res.getTransportCarIds();
        if ( list != null ) {
            transportDetailSaveReqVO.setTransportCarIds( new ArrayList<Long>( list ) );
        }

        return transportDetailSaveReqVO;
    }

    @Override
    public List<TransportDetailSaveReqVO> convertList(List<TransportDetailDO> res) {
        if ( res == null ) {
            return null;
        }

        List<TransportDetailSaveReqVO> list = new ArrayList<TransportDetailSaveReqVO>( res.size() );
        for ( TransportDetailDO transportDetailDO : res ) {
            list.add( convert( transportDetailDO ) );
        }

        return list;
    }

    @Override
    public List<TransportDetailSaveReqVO> convertList2(List<TransportDetailDO> transportDetailDOS) {
        if ( transportDetailDOS == null ) {
            return null;
        }

        List<TransportDetailSaveReqVO> list = new ArrayList<TransportDetailSaveReqVO>( transportDetailDOS.size() );
        for ( TransportDetailDO transportDetailDO : transportDetailDOS ) {
            list.add( convert( transportDetailDO ) );
        }

        return list;
    }
}
