package com.fmyd888.fengmao.module.information.convert.invoice;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.invoice.InvoiceDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class InvoiceConvertImpl implements InvoiceConvert {

    @Override
    public InvoiceDO convert(InvoiceCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        InvoiceDO.InvoiceDOBuilder invoiceDO = InvoiceDO.builder();

        invoiceDO.invoiceTitle( bean.getInvoiceTitle() );
        invoiceDO.invoiceType( bean.getInvoiceType() );
        invoiceDO.email( bean.getEmail() );
        invoiceDO.amount( bean.getAmount() );
        invoiceDO.applicant( bean.getApplicant() );
        invoiceDO.businessLicenseId( bean.getBusinessLicenseId() );
        invoiceDO.status( bean.getStatus() );
        invoiceDO.taxRegistrationNumber( bean.getTaxRegistrationNumber() );
        invoiceDO.bankName( bean.getBankName() );
        invoiceDO.bankAccount( bean.getBankAccount() );
        invoiceDO.customerId( bean.getCustomerId() );
        invoiceDO.unifiedCreditCode( bean.getUnifiedCreditCode() );
        invoiceDO.invoiceContactPhone( bean.getInvoiceContactPhone() );
        invoiceDO.invoiceContactAddress( bean.getInvoiceContactAddress() );

        return invoiceDO.build();
    }

    @Override
    public InvoiceDO convert(InvoiceUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        InvoiceDO.InvoiceDOBuilder invoiceDO = InvoiceDO.builder();

        invoiceDO.id( bean.getId() );
        invoiceDO.invoiceTitle( bean.getInvoiceTitle() );
        invoiceDO.invoiceType( bean.getInvoiceType() );
        invoiceDO.email( bean.getEmail() );
        invoiceDO.amount( bean.getAmount() );
        invoiceDO.applicant( bean.getApplicant() );
        invoiceDO.businessLicenseId( bean.getBusinessLicenseId() );
        invoiceDO.status( bean.getStatus() );
        invoiceDO.taxRegistrationNumber( bean.getTaxRegistrationNumber() );
        invoiceDO.bankName( bean.getBankName() );
        invoiceDO.bankAccount( bean.getBankAccount() );
        invoiceDO.customerId( bean.getCustomerId() );
        invoiceDO.unifiedCreditCode( bean.getUnifiedCreditCode() );
        invoiceDO.invoiceContactPhone( bean.getInvoiceContactPhone() );
        invoiceDO.invoiceContactAddress( bean.getInvoiceContactAddress() );

        return invoiceDO.build();
    }

    @Override
    public InvoiceRespVO convert(InvoiceDO bean) {
        if ( bean == null ) {
            return null;
        }

        InvoiceRespVO invoiceRespVO = new InvoiceRespVO();

        invoiceRespVO.setInvoiceTitle( bean.getInvoiceTitle() );
        invoiceRespVO.setTaxRegistrationNumber( bean.getTaxRegistrationNumber() );
        invoiceRespVO.setBankName( bean.getBankName() );
        invoiceRespVO.setBankAccount( bean.getBankAccount() );
        invoiceRespVO.setCustomerId( bean.getCustomerId() );
        invoiceRespVO.setInvoiceContactPhone( bean.getInvoiceContactPhone() );
        invoiceRespVO.setInvoiceContactAddress( bean.getInvoiceContactAddress() );
        invoiceRespVO.setUnifiedCreditCode( bean.getUnifiedCreditCode() );
        invoiceRespVO.setInvoiceType( bean.getInvoiceType() );
        invoiceRespVO.setEmail( bean.getEmail() );
        invoiceRespVO.setAmount( bean.getAmount() );
        invoiceRespVO.setApplicant( bean.getApplicant() );
        invoiceRespVO.setBusinessLicenseId( bean.getBusinessLicenseId() );
        invoiceRespVO.setStatus( bean.getStatus() );
        invoiceRespVO.setId( bean.getId() );
        invoiceRespVO.setCreateTime( bean.getCreateTime() );

        return invoiceRespVO;
    }

    @Override
    public List<InvoiceRespVO> convertList(List<InvoiceDO> list) {
        if ( list == null ) {
            return null;
        }

        List<InvoiceRespVO> list1 = new ArrayList<InvoiceRespVO>( list.size() );
        for ( InvoiceDO invoiceDO : list ) {
            list1.add( convert( invoiceDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<InvoiceRespVO> convertPage(PageResult<InvoiceDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<InvoiceRespVO> pageResult = new PageResult<InvoiceRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<InvoiceExcelVO> convertList02(List<InvoiceDO> list) {
        if ( list == null ) {
            return null;
        }

        List<InvoiceExcelVO> list1 = new ArrayList<InvoiceExcelVO>( list.size() );
        for ( InvoiceDO invoiceDO : list ) {
            list1.add( invoiceDOToInvoiceExcelVO( invoiceDO ) );
        }

        return list1;
    }

    protected InvoiceExcelVO invoiceDOToInvoiceExcelVO(InvoiceDO invoiceDO) {
        if ( invoiceDO == null ) {
            return null;
        }

        InvoiceExcelVO invoiceExcelVO = new InvoiceExcelVO();

        invoiceExcelVO.setId( invoiceDO.getId() );
        invoiceExcelVO.setInvoiceTitle( invoiceDO.getInvoiceTitle() );
        invoiceExcelVO.setInvoiceType( invoiceDO.getInvoiceType() );
        invoiceExcelVO.setAmount( invoiceDO.getAmount() );
        invoiceExcelVO.setApplicant( invoiceDO.getApplicant() );
        invoiceExcelVO.setBusinessLicenseId( invoiceDO.getBusinessLicenseId() );
        invoiceExcelVO.setStatus( invoiceDO.getStatus() );
        invoiceExcelVO.setCreateTime( invoiceDO.getCreateTime() );
        invoiceExcelVO.setTaxRegistrationNumber( invoiceDO.getTaxRegistrationNumber() );
        invoiceExcelVO.setBankName( invoiceDO.getBankName() );
        invoiceExcelVO.setBankAccount( invoiceDO.getBankAccount() );
        invoiceExcelVO.setCustomerId( invoiceDO.getCustomerId() );
        invoiceExcelVO.setUnifiedCreditCode( invoiceDO.getUnifiedCreditCode() );

        return invoiceExcelVO;
    }
}
