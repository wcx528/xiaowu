package com.fmyd888.fengmao.module.information.convert.transportManger;

import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailSaveReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportMangerBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportMangerRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportMangerSaveReqVO;
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
public interface TransportMangerConvert {

    TransportMangerConvert INSTANCE = Mappers.getMapper(TransportMangerConvert.class);

    TransportMangerDO convert(TransportMangerSaveReqVO res);
    TransportMangerRespVO convert(TransportMangerDO res);
    TransportMangerSaveReqVO convert02(TransportMangerDO res);

    CardPage<TransportMangerRespVO> convertPage02(CardPage<TransportMangerDO> page);

    TransportMangerBasicRespVO convert05(TransportMangerDO transportMangerDO);

    List<TransportMangerBasicRespVO> convertList(List<TransportMangerDO> transportMangerDO);

}
