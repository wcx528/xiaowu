package com.fmyd888.fengmao.module.information.controller.admin.fleet;

import com.fmyd888.fengmao.framework.common.pojo.*;
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

import com.fmyd888.fengmao.module.information.controller.admin.fleet.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.fleet.FleetDO;
import com.fmyd888.fengmao.module.information.service.fleet.FleetService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 车队表")
@RestController
@RequestMapping("/information/fleet")
@Validated
public class FleetController {

    @Resource
    private FleetService fleetService;

    @PostMapping("/create")
    @Operation(summary = "创建车队表")
    @PreAuthorize("@ss.hasPermission('information:fleet:create')")
    public CommonResult<Long> createFleet(@Valid @RequestBody FleetSaveReqVO createReqVO) {
        return success(fleetService.createFleet(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新车队表")
    @PreAuthorize("@ss.hasPermission('information:fleet:update')")
    public CommonResult<Boolean> updateFleet(@Valid @RequestBody FleetSaveReqVO updateReqVO) {
        fleetService.updateFleet(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除车队表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:fleet:delete')")
    public CommonResult<Boolean> deleteFleet(@RequestParam("id") Long id) {
        fleetService.deleteFleet(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得车队表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:fleet:query')")
    public CommonResult<FleetRespVO> getFleet(@RequestParam("id") Long id) {
            FleetDO fleet = fleetService.getFleet(id);
            return success(BeanUtils.toBean(fleet, FleetRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得车队表列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:fleet:query')")
    public CommonResult<List<FleetRespVO>> getFleetList(@RequestParam("ids") Collection<Long> ids) {
        List<FleetDO> list = fleetService.getFleetList(ids);
        return success(BeanUtils.toBean(list, FleetRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得车队表分页")
    @PreAuthorize("@ss.hasPermission('information:fleet:query')")
    public CommonResult<PageResult<FleetRespVO>> getFleetPage(@Valid FleetPageReqVO pageReqVO) {
        PageResult<FleetDO> pageResult = fleetService.getFleetPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, FleetRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出车队表 Excel")
    @PreAuthorize("@ss.hasPermission('information:fleet:export')")
    @OperateLog(type = EXPORT)
    public void exportFleetExcel(@Valid FleetListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FleetDO> list = fleetService.getFleetList(exportReqVO);
        // 导出 Excel
        List<FleetExcelVO> excelVo = BeanUtils.toBean(list, FleetExcelVO.class);
        ExcelUtils.write(response, "车队表.xls", "数据", FleetExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新车队表列表")
    @PreAuthorize("@ss.hasPermission('information:fleet:batch-update')")
    public CommonResult<Boolean> batchUpdateFleet(@Valid @RequestBody List<FleetSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        fleetService.batchUpdateFleet(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除车队表列表")
    @PreAuthorize("@ss.hasPermission('information:fleet:batch-delete')")
    public CommonResult<Boolean> batchDeleteFleet(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        fleetService.batchDeleteFleet(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载车队表导入模板")
    @PreAuthorize("@ss.hasPermission('information:fleet:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<FleetExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "车队表模板.xls", "车队表列表", FleetExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "车队表预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:fleet:import')")
    public CommonResult<List<FleetExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<FleetExcelVO> list = ExcelUtils.read(file, FleetExcelVO.class);
    return success(fleetService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入车队表")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:fleet:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody FleetExcelVO importReqVo) throws Exception {
        ImportResult result = fleetService.importData(importReqVo);
        return success(result);
    }


    @GetMapping("/getFleetSimpleList")
    @Operation(summary = "获得车队精简信息")
    public CommonResult<List<HashMap<String, Object>>> getFleetSimpleList(@Valid CommonQueryParam param) {
        List<HashMap<String, Object>> simpleCurrencyList = fleetService.getFleetSimpleList(param);
        return success(simpleCurrencyList);
    }
}