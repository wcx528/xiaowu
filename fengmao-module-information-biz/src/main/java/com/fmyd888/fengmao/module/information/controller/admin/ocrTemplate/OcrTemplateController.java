package com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate;

import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.*;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;

import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.ocrtemplate.OcrTemplateDO;
import com.fmyd888.fengmao.module.information.service.ocrtemplate.OcrTemplateService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - OCR模块维护")
@RestController
@RequestMapping("/information/ocr-template")
@Validated
public class OcrTemplateController {

    @Resource
    private OcrTemplateService ocrTemplateService;

    @PostMapping("/create")
    @Operation(summary = "创建OCR模块维护")
    @PreAuthorize("@ss.hasPermission('information:ocr-template:create')")
    public CommonResult<Long> createOcrTemplate(@Valid @RequestBody OcrTemplateSaveReqVO createReqVO) {
        return success(ocrTemplateService.createOcrTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新OCR模块维护")
    @PreAuthorize("@ss.hasPermission('information:ocr-template:update')")
    public CommonResult<Boolean> updateOcrTemplate(@Valid @RequestBody OcrTemplateSaveReqVO updateReqVO) {
        ocrTemplateService.updateOcrTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除OCR模块维护")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:ocr-template:delete')")
    public CommonResult<Boolean> deleteOcrTemplate(@RequestParam("id") Long id) {
        ocrTemplateService.deleteOcrTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得OCR模块维护")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:ocr-template:query')")
    public CommonResult<OcrTemplateRespVO> getOcrTemplate(@RequestParam("id") Long id) {
            OcrTemplateDO ocrTemplate = ocrTemplateService.getOcrTemplate(id);
            return success(BeanUtils.toBean(ocrTemplate, OcrTemplateRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得OCR模块维护列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:ocr-template:query')")
    public CommonResult<List<OcrTemplateRespVO>> getOcrTemplateList(@RequestParam("ids") Collection<Long> ids) {
        List<OcrTemplateDO> list = ocrTemplateService.getOcrTemplateList(ids);
        return success(BeanUtils.toBean(list, OcrTemplateRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得OCR模块维护分页")
    @PreAuthorize("@ss.hasPermission('information:ocr-template:query')")
    public CommonResult<PageResult<OcrTemplateRespVO>> getOcrTemplatePage(@Valid OcrTemplatePageReqVO pageReqVO) {
        PageResult<OcrTemplateDO> pageResult = ocrTemplateService.getOcrTemplatePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OcrTemplateRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出OCR模块维护 Excel")
    @PreAuthorize("@ss.hasPermission('information:ocr-template:export')")
    @OperateLog(type = EXPORT)
    public void exportOcrTemplateExcel(@Valid OcrTemplateListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<OcrTemplateDO> list = ocrTemplateService.getOcrTemplateList(exportReqVO);
        // 导出 Excel
        List<OcrTemplateExcelVO> excelVo = BeanUtils.toBean(list, OcrTemplateExcelVO.class);
        ExcelUtils.write(response, "fm_ocr_template.xls", "数据", OcrTemplateExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新OCR模块维护列表")
    @PreAuthorize("@ss.hasPermission('information:ocr-template:batch-update')")
    public CommonResult<Boolean> batchUpdateOcrTemplate(@Valid @RequestBody List<OcrTemplateSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        ocrTemplateService.batchUpdateOcrTemplate(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除OCR模块维护列表")
    @PreAuthorize("@ss.hasPermission('information:ocr-template:batch-delete')")
    public CommonResult<Boolean> batchDeleteOcrTemplate(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        ocrTemplateService.batchDeleteOcrTemplate(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载OCR模块维护导入模板")
    @PreAuthorize("@ss.hasPermission('information:ocr-template:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<OcrTemplateExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "fm_ocr_template模板.xls", "fm_ocr_template列表", OcrTemplateExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "OCR模块维护预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:ocr-template:import')")
    public CommonResult<List<OcrTemplateExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<OcrTemplateExcelVO> list = ExcelUtils.read(file, OcrTemplateExcelVO.class);
    return success(ocrTemplateService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入OCR模块维护")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:ocr-template:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody OcrTemplateExcelVO importReqVo) throws Exception {
        ImportResult result = ocrTemplateService.importData(importReqVo);
        return success(result);
    }

}
