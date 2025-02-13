package com.fmyd888.fengmao.module.information.controller.admin.transportcar;

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

import com.fmyd888.fengmao.module.information.controller.admin.transportcar.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportcar.TransportCarDO;
import com.fmyd888.fengmao.module.information.service.transportcar.TransportCarService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 自动生成接口作废，运输证办理车辆关联表是依附于运输证明细表的，不能单独作为一个独立接口层使用
 */
@Tag(name = "管理后台 - 运输证办理车辆关联")
@RestController
@RequestMapping("/information/transport-car")
@Validated
public class TransportCarController {

    @Resource
    private TransportCarService transportCarService;

    @PostMapping("/create")
    @Operation(summary = "创建运输证办理车辆关联")
    @PreAuthorize("@ss.hasPermission('information:transport-car:create')")
    public CommonResult<Long> createTransportCar(@Valid @RequestBody TransportCarSaveReqVO createReqVO) {
        return success(transportCarService.createTransportCar(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运输证办理车辆关联")
    @PreAuthorize("@ss.hasPermission('information:transport-car:update')")
    public CommonResult<Boolean> updateTransportCar(@Valid @RequestBody TransportCarSaveReqVO updateReqVO) {
        transportCarService.updateTransportCar(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运输证办理车辆关联")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:transport-car:delete')")
    public CommonResult<Boolean> deleteTransportCar(@RequestParam("id") Long id) {
        transportCarService.deleteTransportCar(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运输证办理车辆关联")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:transport-car:query')")
    public CommonResult<TransportCarRespVO> getTransportCar(@RequestParam("id") Long id) {
            TransportCarDO transportCar = transportCarService.getTransportCar(id);
            return success(BeanUtils.toBean(transportCar, TransportCarRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得运输证办理车辆关联列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:transport-car:query')")
    public CommonResult<List<TransportCarRespVO>> getTransportCarList(@RequestParam("ids") Collection<Long> ids) {
        List<TransportCarDO> list = transportCarService.getTransportCarList(ids);
        return success(BeanUtils.toBean(list, TransportCarRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得运输证办理车辆关联分页")
    @PreAuthorize("@ss.hasPermission('information:transport-car:query')")
    public CommonResult<PageResult<TransportCarRespVO>> getTransportCarPage(@Valid TransportCarPageReqVO pageReqVO) {
        PageResult<TransportCarDO> pageResult = transportCarService.getTransportCarPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TransportCarRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出运输证办理车辆关联 Excel")
    @PreAuthorize("@ss.hasPermission('information:transport-car:export')")
    @OperateLog(type = EXPORT)
    public void exportTransportCarExcel(@Valid TransportCarListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TransportCarDO> list = transportCarService.getTransportCarList(exportReqVO);
        // 导出 Excel
        List<TransportCarExcelVO> excelVo = BeanUtils.toBean(list, TransportCarExcelVO.class);
        ExcelUtils.write(response, "运输证办理车辆关联.xls", "数据", TransportCarExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新运输证办理车辆关联列表")
    @PreAuthorize("@ss.hasPermission('information:transport-car:batch-update')")
    public CommonResult<Boolean> batchUpdateTransportCar(@Valid @RequestBody List<TransportCarSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        transportCarService.batchUpdateTransportCar(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除运输证办理车辆关联列表")
    @PreAuthorize("@ss.hasPermission('information:transport-car:batch-delete')")
    public CommonResult<Boolean> batchDeleteTransportCar(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        transportCarService.batchDeleteTransportCar(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载运输证办理车辆关联导入模板")
    @PreAuthorize("@ss.hasPermission('information:transport-car:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<TransportCarExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "运输证办理车辆关联模板.xls", "运输证办理车辆关联列表", TransportCarExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "运输证办理车辆关联预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:transport-car:import')")
    public CommonResult<List<TransportCarExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<TransportCarExcelVO> list = ExcelUtils.read(file, TransportCarExcelVO.class);
    return success(transportCarService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入运输证办理车辆关联")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:transport-car:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody TransportCarExcelVO importReqVo) throws Exception {
        ImportResult result = transportCarService.importData(importReqVo);
        return success(result);
    }

}
