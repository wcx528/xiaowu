package com.fmyd888.fengmao.module.information.convert.transportManger;

import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailSaveReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportMangerBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportMangerRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportMangerSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportdetail.TransportDetailDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportmanger.TransportMangerDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Title: TransportMangerConvert
 * @Author: huanhuan
 * @Date: 2024-01-17 10:12
 * @description:
 */
@Mapper
public interface TransportDetailConvert {

    TransportDetailConvert INSTANCE = Mappers.getMapper(TransportDetailConvert.class);

    TransportDetailDO convert(TransportDetailSaveReqVO res);

    TransportDetailSaveReqVO convert(TransportDetailDO res);

    List<TransportDetailSaveReqVO> convertList(List<TransportDetailDO> res);

    List<TransportDetailSaveReqVO> convertList2(List<TransportDetailDO> transportDetailDOS);



}
