package com.fmyd888.fengmao.module.information.service.invoice;

import java.util.*;
import javax.validation.*;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.invoice.InvoiceDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;

/**
 * 开票信息 Service 接口
 *
 * @author 丰茂
 */
public interface InvoiceService {

    /**
     * 创建开票信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInvoice(@Valid InvoiceCreateReqVO createReqVO);

    /**
     * 更新开票信息
     *
     * @param updateReqVO 更新信息
     */
    void updateInvoice(@Valid InvoiceUpdateReqVO updateReqVO);

    /**
     * 删除开票信息
     *
     * @param id 编号
     */
    void deleteInvoice(Long id);

    /**
     * 获得开票信息
     *
     * @param id 编号
     * @return 开票信息
     */
    InvoiceDO getInvoice(Long id);

    /**
     * 获得开票信息列表
     *
     * @param ids 编号
     * @return 开票信息列表
     */
    List<InvoiceDO> getInvoiceList(Collection<Long> ids);

    /**
     * 获得开票信息分页
     *
     * @param pageReqVO 分页查询
     * @return 开票信息分页
     */
    PageResult<InvoiceDO> getInvoicePage(InvoicePageReqVO pageReqVO);

    /**
     * 获得开票信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 开票信息列表
     */
    List<InvoiceDO> getInvoiceList(InvoiceExportReqVO exportReqVO);

    /**
    * 批量更新开票信息列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateInvoice(List<InvoiceUpdateReqVO> updateReqVOList);

    /**
    * 批量删除开票信息列表
    *
    * @param ids 编号列表
    */
    void batchDeleteInvoice(List<Long> ids);

    /**
    * 批量新增导入开票信息列表
    *
    * @param importReqVOList 批量新增导入信息列表
    */
    void batchImportInvoice(List<InvoiceDO> importReqVOList);

    /**
     * 通过客户id,获得开票信息
     *
     * @param customerId 客户id,
     * @return 开票信息
     */
    InvoiceRespVO getInvoiceByCustomerId(Long customerId);
}
