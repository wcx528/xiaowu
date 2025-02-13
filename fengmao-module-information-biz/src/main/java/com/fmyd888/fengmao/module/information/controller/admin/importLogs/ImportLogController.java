package com.fmyd888.fengmao.module.information.controller.admin.importLogs;

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

import com.fmyd888.fengmao.module.information.controller.admin.importLogs.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.importLogs.ImportLogDO;
import com.fmyd888.fengmao.module.information.service.importLogs.ImportLogService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 导入日志")
@RestController
@RequestMapping("/information/import-log")
@Validated
public class ImportLogController {

    @Resource
    private ImportLogService importLogService;

    @PostMapping("/create")
    @Operation(summary = "创建导入日志")
    @PreAuthorize("@ss.hasPermission('information:import-log:create')")
    public CommonResult<Long> createImportLog(@Valid @RequestBody ImportLogSaveReqVO createReqVO) {
        return success(importLogService.createImportLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新导入日志")
    @PreAuthorize("@ss.hasPermission('information:import-log:update')")
    public CommonResult<Boolean> updateImportLog(@Valid @RequestBody ImportLogSaveReqVO updateReqVO) {
        importLogService.updateImportLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除导入日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:import-log:delete')")
    public CommonResult<Boolean> deleteImportLog(@RequestParam("id") Long id) {
        importLogService.deleteImportLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得导入日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:import-log:query')")
    public CommonResult<ImportLogRespVO> getImportLog(@RequestParam("id") Long id) {
            ImportLogDO importLog = importLogService.getImportLog(id);
            return success(BeanUtils.toBean(importLog, ImportLogRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得导入日志列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:import-log:query')")
    public CommonResult<List<ImportLogRespVO>> getImportLogList(@RequestParam("ids") Collection<Long> ids) {
        List<ImportLogDO> list = importLogService.getImportLogList(ids);
        return success(BeanUtils.toBean(list, ImportLogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得导入日志分页")
    @PreAuthorize("@ss.hasPermission('information:import-log:query')")
    public CommonResult<PageResult<ImportLogRespVO>> getImportLogPage(@Valid ImportLogPageReqVO pageReqVO) {
        PageResult<ImportLogDO> pageResult = importLogService.getImportLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ImportLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出导入日志 Excel")
    @PreAuthorize("@ss.hasPermission('information:import-log:export')")
    @OperateLog(type = EXPORT)
    public void exportImportLogExcel(@Valid ImportLogListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ImportLogDO> list = importLogService.getImportLogList(exportReqVO);
        // 导出 Excel
        List<ImportLogExcelVO> excelVo = BeanUtils.toBean(list, ImportLogExcelVO.class);
        ExcelUtils.write(response, "导入日志.xls", "数据", ImportLogExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }
}