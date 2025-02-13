package com.fmyd888.fengmao.module.information.service.invoice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fmyd888.fengmao.framework.common.exception.ErrorCode;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import com.fmyd888.fengmao.framework.security.core.LoginUser;
import com.fmyd888.fengmao.framework.security.core.util.SecurityFrameworkUtils;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;

import java.util.*;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.invoice.InvoiceDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import com.fmyd888.fengmao.module.information.convert.invoice.InvoiceConvert;
import com.fmyd888.fengmao.module.information.dal.mysql.invoice.InvoiceMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 开票信息 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    @Override
    public Long createInvoice(InvoiceCreateReqVO createReqVO) {
        // 插入
        InvoiceDO invoice = InvoiceConvert.INSTANCE.convert(createReqVO);
        invoiceMapper.insert(invoice);
        // 返回
        return invoice.getId();
    }

    @Override
    public void updateInvoice(InvoiceUpdateReqVO updateReqVO) {
        // 校验存在
        validateInvoiceExists(updateReqVO.getId());
        // 更新
        InvoiceDO updateObj = InvoiceConvert.INSTANCE.convert(updateReqVO);
        invoiceMapper.updateById(updateObj);
    }

    @Override
    public void deleteInvoice(Long id) {
        // 校验存在
        validateInvoiceExists(id);
        // 删除
        invoiceMapper.deleteById(id);
    }

    private void validateInvoiceExists(Long id) {
        if (invoiceMapper.selectById(id) == null) {
            throw exception(INVOICE_EXISTS);
        }
    }

    @Override
    public InvoiceDO getInvoice(Long id) {
        return invoiceMapper.selectById(id);
    }

    @Override
    public List<InvoiceDO> getInvoiceList(Collection<Long> ids) {
        return invoiceMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<InvoiceDO> getInvoicePage(InvoicePageReqVO pageReqVO) {
        return invoiceMapper.selectPage(pageReqVO);
    }

    @Override
    public List<InvoiceDO> getInvoiceList(InvoiceExportReqVO exportReqVO) {
        return invoiceMapper.selectList(exportReqVO);
    }
    @Override
    public void batchUpdateInvoice(List<InvoiceUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (InvoiceUpdateReqVO updateReqVO : updateReqVOList) {
            updateInvoice(updateReqVO);
        }
    }

    @Override
    public void batchDeleteInvoice(List<Long> ids) {
        // 在这里处理批量删除逻辑
        invoiceMapper.deleteBatchIds(ids);
    }

    @Override
    public void batchImportInvoice(List<InvoiceDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        invoiceMapper.insertBatch(importReqVOList);
    }

    @Override
    public InvoiceRespVO getInvoiceByCustomerId(Long customerId) {
        LambdaQueryWrapper<InvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InvoiceDO::getCustomerId, customerId)
                .eq(InvoiceDO::getDeleted,0);
        InvoiceDO invoiceDO = invoiceMapper.selectOne(queryWrapper);
        InvoiceRespVO invoiceRespVO = InvoiceConvert.INSTANCE.convert(invoiceDO);
        return invoiceRespVO;
    }
}
