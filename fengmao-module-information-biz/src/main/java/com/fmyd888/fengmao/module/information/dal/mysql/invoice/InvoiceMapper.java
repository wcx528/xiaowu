package com.fmyd888.fengmao.module.information.dal.mysql.invoice;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.invoice.InvoiceDO;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.*;

/**
 * 开票信息 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface InvoiceMapper extends BaseMapperX<InvoiceDO> {

    default PageResult<InvoiceDO> selectPage(InvoicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InvoiceDO>()
                .eqIfPresent(InvoiceDO::getInvoiceTitle , reqVO.getInvoiceTitle ())
                .eqIfPresent(InvoiceDO::getInvoiceType, reqVO.getInvoiceType())
                .eqIfPresent(InvoiceDO::getEmail, reqVO.getAddress())
                .eqIfPresent(InvoiceDO::getAmount, reqVO.getAmount())
                .eqIfPresent(InvoiceDO::getApplicant , reqVO.getApplicant ())
                .eqIfPresent(InvoiceDO::getBusinessLicenseId, reqVO.getBusinessLicenseId())
                .eqIfPresent(InvoiceDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(InvoiceDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(InvoiceDO::getTaxRegistrationNumber , reqVO.getTaxRegistrationNumber ())
                .eqIfPresent(InvoiceDO::getBankName , reqVO.getBankName ())
                .eqIfPresent(InvoiceDO::getBankAccount , reqVO.getBankAccount ())
                .eqIfPresent(InvoiceDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(InvoiceDO::getUnifiedCreditCode , reqVO.getUnifiedCreditCode ())
                .eqIfPresent(InvoiceDO::getInvoiceContactPhone , reqVO.getContactPhone ())
                .eqIfPresent(InvoiceDO::getInvoiceContactAddress, reqVO.getContactAddress())
                .orderByDesc(InvoiceDO::getId));
    }

    default List<InvoiceDO> selectList(InvoiceExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<InvoiceDO>()
                .eqIfPresent(InvoiceDO::getInvoiceTitle , reqVO.getInvoiceTitle ())
                .eqIfPresent(InvoiceDO::getInvoiceType, reqVO.getInvoiceType())
                .eqIfPresent(InvoiceDO::getEmail, reqVO.getAddress())
                .eqIfPresent(InvoiceDO::getAmount, reqVO.getAmount())
                .eqIfPresent(InvoiceDO::getApplicant , reqVO.getApplicant ())
                .eqIfPresent(InvoiceDO::getBusinessLicenseId, reqVO.getBusinessLicenseId())
                .eqIfPresent(InvoiceDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(InvoiceDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(InvoiceDO::getTaxRegistrationNumber , reqVO.getTaxRegistrationNumber ())
                .eqIfPresent(InvoiceDO::getBankName , reqVO.getBankName ())
                .eqIfPresent(InvoiceDO::getBankAccount , reqVO.getBankAccount ())
                .eqIfPresent(InvoiceDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(InvoiceDO::getUnifiedCreditCode , reqVO.getUnifiedCreditCode ())
                .eqIfPresent(InvoiceDO::getInvoiceContactPhone , reqVO.getContactPhone ())
                .eqIfPresent(InvoiceDO::getInvoiceContactAddress, reqVO.getContactAddress())
                .orderByDesc(InvoiceDO::getId));
    }

}
