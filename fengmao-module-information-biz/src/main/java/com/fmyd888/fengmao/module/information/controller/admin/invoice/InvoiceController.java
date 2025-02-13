package com.fmyd888.fengmao.module.information.controller.admin.invoice;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;

import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.invoice.InvoiceDO;
import com.fmyd888.fengmao.module.information.convert.invoice.InvoiceConvert;
import com.fmyd888.fengmao.module.information.service.invoice.InvoiceService;

@Tag(name = "管理后台 - 开票信息")
@RestController
@RequestMapping("/information/invoice")
@Validated
public class    InvoiceController {

    @Resource
    private InvoiceService invoiceService;

    @PostMapping("/create")
    @Operation(summary = "创建开票信息")
    @PreAuthorize("@ss.hasPermission('information:invoice:create')")
    public CommonResult<Long> createInvoice(@Valid @RequestBody InvoiceCreateReqVO createReqVO) {
        return success(invoiceService.createInvoice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新开票信息")
    @PreAuthorize("@ss.hasPermission('information:invoice:update')")
    public CommonResult<Boolean> updateInvoice(@Valid @RequestBody InvoiceUpdateReqVO updateReqVO) {
        invoiceService.updateInvoice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除开票信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:invoice:delete')")
    public CommonResult<Boolean> deleteInvoice(@RequestParam("id") Long id) {
        invoiceService.deleteInvoice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得开票信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:invoice:query')")
    public CommonResult<InvoiceRespVO> getInvoice(@RequestParam("id") Long id) {
        InvoiceDO invoice = invoiceService.getInvoice(id);
        return success(InvoiceConvert.INSTANCE.convert(invoice));
    }

    @GetMapping("/list")
    @Operation(summary = "获得开票信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:invoice:query')")
    public CommonResult<List<InvoiceRespVO>> getInvoiceList(@RequestParam("ids") Collection<Long> ids) {
        List<InvoiceDO> list = invoiceService.getInvoiceList(ids);
        return success(InvoiceConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得开票信息分页")
    @PreAuthorize("@ss.hasPermission('information:invoice:query')")
    public CommonResult<PageResult<InvoiceRespVO>> getInvoicePage(@Valid InvoicePageReqVO pageVO) {
        PageResult<InvoiceDO> pageResult = invoiceService.getInvoicePage(pageVO);
        return success(InvoiceConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出开票信息 Excel")
    @PreAuthorize("@ss.hasPermission('information:invoice:export')")
    @OperateLog(type = EXPORT)
    public void exportInvoiceExcel(@Valid InvoiceExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<InvoiceDO> list = invoiceService.getInvoiceList(exportReqVO);
        // 导出 Excel
        List<InvoiceExcelVO> datas = InvoiceConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "开票信息.xls", "数据", InvoiceExcelVO.class, datas,exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新开票信息列表")
    @PreAuthorize("@ss.hasPermission('information:invoice:batch-update')")
    public CommonResult<Boolean> batchUpdateInvoice(@Valid @RequestBody List<InvoiceUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        invoiceService.batchUpdateInvoice(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除开票信息列表")
    @PreAuthorize("@ss.hasPermission('information:invoice:batch-delete')")
    public CommonResult<Boolean> batchDeleteInvoice(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        invoiceService.batchDeleteInvoice(body.get("ids"));
        return success(true);
    }

    @PostMapping("/batch-import")
    @Operation(summary = "批量新增导入开票信息列表")
    @PreAuthorize("@ss.hasPermission('information:invoice:batch-import')")
    public CommonResult<Boolean> batchImportInvoice(@RequestBody List<InvoiceDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        invoiceService.batchImportInvoice(importReqVOList);
        return success(true);
    }

}
