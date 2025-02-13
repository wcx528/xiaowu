package com.fmyd888.fengmao.module.information.controller.admin.commoditysafetycard;

import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.*;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;

import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.fmyd888.fengmao.module.information.controller.admin.commoditysafetycard.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.commoditysafetycard.CommoditySafetyCardDO;
import com.fmyd888.fengmao.module.information.service.commoditysafetycard.CommoditySafetyCardService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 安全告知卡")
@RestController
@RequestMapping("/information/commodity-safety-card")
@Validated
public class CommoditySafetyCardController {

    @Resource
    private CommoditySafetyCardService commoditySafetyCardService;

    @PostMapping("/create")
    @Operation(summary = "创建安全告知卡")
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:create')")
    public CommonResult<Long> createCommoditySafetyCard(@Valid @RequestBody CommoditySafetyCardSaveReqVO createReqVO) {
        return success(commoditySafetyCardService.createCommoditySafetyCard(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新安全告知卡")
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:update')")
    public CommonResult<Boolean> updateCommoditySafetyCard(@Valid @RequestBody CommoditySafetyCardSaveReqVO updateReqVO) {
        commoditySafetyCardService.updateCommoditySafetyCard(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除安全告知卡")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:delete')")
    public CommonResult<Boolean> deleteCommoditySafetyCard(@RequestParam("id") Long id) {
        commoditySafetyCardService.deleteCommoditySafetyCard(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得安全告知卡")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:query')")
    public CommonResult<CommoditySafetyCardRespVO> getCommoditySafetyCard(@RequestParam("id") Long id) {
            CommoditySafetyCardDO commoditySafetyCard = commoditySafetyCardService.getCommoditySafetyCard(id);
            return success(BeanUtils.toBean(commoditySafetyCard, CommoditySafetyCardRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得安全告知卡列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:query')")
    public CommonResult<List<CommoditySafetyCardRespVO>> getCommoditySafetyCardList(@RequestParam("ids") Collection<Long> ids) {
        List<CommoditySafetyCardDO> list = commoditySafetyCardService.getCommoditySafetyCardList(ids);
        return success(BeanUtils.toBean(list, CommoditySafetyCardRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得安全告知卡分页")
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:query')")
    public CommonResult<PageResult<CommoditySafetyCardRespVO>> getCommoditySafetyCardPage(@Valid CommoditySafetyCardPageReqVO pageReqVO) {
        PageResult<CommoditySafetyCardDO> pageResult = commoditySafetyCardService.getCommoditySafetyCardPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CommoditySafetyCardRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出安全告知卡 Excel")
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:export')")
    @OperateLog(type = EXPORT)
    public void exportCommoditySafetyCardExcel(@Valid CommoditySafetyCardListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<CommoditySafetyCardDO> list = commoditySafetyCardService.getCommoditySafetyCardList(exportReqVO);
        // 导出 Excel
        List<CommoditySafetyCardExcelVO> excelVo = BeanUtils.toBean(list, CommoditySafetyCardExcelVO.class);
        ExcelUtils.write(response, "安全告知卡.xls", "数据", CommoditySafetyCardExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新安全告知卡列表")
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:batch-update')")
    public CommonResult<Boolean> batchUpdateCommoditySafetyCard(@Valid @RequestBody List<CommoditySafetyCardSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        commoditySafetyCardService.batchUpdateCommoditySafetyCard(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除安全告知卡列表")
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:batch-delete')")
    public CommonResult<Boolean> batchDeleteCommoditySafetyCard(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        commoditySafetyCardService.batchDeleteCommoditySafetyCard(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载安全告知卡导入模板")
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<CommoditySafetyCardExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "安全告知卡模板.xls", "安全告知卡列表", CommoditySafetyCardExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "安全告知卡预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:import')")
    public CommonResult<List<CommoditySafetyCardExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<CommoditySafetyCardExcelVO> list = ExcelUtils.read(file, CommoditySafetyCardExcelVO.class);
    return success(commoditySafetyCardService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入安全告知卡")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:commodity-safety-card:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody CommoditySafetyCardExcelVO importReqVo) throws Exception {
        ImportResult result = commoditySafetyCardService.importData(importReqVo);
        return success(result);
    }

}