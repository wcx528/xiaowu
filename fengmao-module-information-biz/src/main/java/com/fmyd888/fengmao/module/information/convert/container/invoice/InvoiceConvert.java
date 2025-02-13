package com.fmyd888.fengmao.module.information.convert.container.invoice;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.invoice.InvoiceDO;

/**
 * 开票信息 Convert
 *
 * @author 丰茂
 */
@Mapper
public interface InvoiceConvert {

    InvoiceConvert INSTANCE = Mappers.getMapper(InvoiceConvert.class);

    InvoiceDO convert(InvoiceCreateReqVO bean);

    InvoiceDO convert(InvoiceUpdateReqVO bean);

    InvoiceRespVO convert(InvoiceDO bean);

    List<InvoiceRespVO> convertList(List<InvoiceDO> list);

    PageResult<InvoiceRespVO> convertPage(PageResult<InvoiceDO> page);

    List<InvoiceExcelVO> convertList02(List<InvoiceDO> list);

}
