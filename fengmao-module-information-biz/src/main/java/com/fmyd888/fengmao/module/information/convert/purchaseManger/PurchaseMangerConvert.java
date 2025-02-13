package com.fmyd888.fengmao.module.information.convert.purchaseManger;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.PurchaseMangerBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.PurchaseMangerRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.PurchaseMangerSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.purchasemanger.PurchaseMangerDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Title: PurchaseMangerConvert
 * @Author: huanhuan
 * @Date: 2024-01-17 10:12
 * @description:
 */
@Mapper
public interface PurchaseMangerConvert {

    PurchaseMangerConvert INSTANCE = Mappers.getMapper(PurchaseMangerConvert.class);

    PurchaseMangerDO convert(PurchaseMangerSaveReqVO res);
    PurchaseMangerRespVO convert(PurchaseMangerDO res);

    CardPage<PurchaseMangerRespVO> convertPage02(CardPage<PurchaseMangerDO> page);

    PurchaseMangerBasicRespVO convert05(PurchaseMangerDO purchaseMangerDO);

    List<PurchaseMangerBasicRespVO> convertList(List<PurchaseMangerDO> purchaseMangerDO);

}
