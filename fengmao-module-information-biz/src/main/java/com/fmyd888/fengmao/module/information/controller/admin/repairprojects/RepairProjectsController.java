package com.fmyd888.fengmao.module.information.controller.admin.repairprojects;

import cn.hutool.core.io.IoUtil;
import com.fmyd888.fengmao.framework.common.enums.FileBussinessTypeEnum;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.StatementTemplateUploadRespVO;
import com.fmyd888.fengmao.module.infra.api.file.FileApi;
import com.fmyd888.fengmao.module.infra.enums.OssDirectoryEnums;
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

import com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.repairprojects.RepairProjectsDO;
import com.fmyd888.fengmao.module.information.service.repairprojects.RepairProjectsService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 维修项目")
@RestController
@RequestMapping("/information/repair-projects")
@Validated
public class RepairProjectsController {

    @Resource
    private RepairProjectsService repairProjectsService;

    @Resource
    private FileApi fileApi;




    @PostMapping("/create")
    @Operation(summary = "创建维修项目")
    @PreAuthorize("@ss.hasPermission('information:repair-projects:create')")
    public CommonResult<Long> createRepairProjects(@Valid @RequestBody RepairProjectsSaveReqVO createReqVO) {
        return success(repairProjectsService.createRepairProjects(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新维修项目")
    @PreAuthorize("@ss.hasPermission('information:repair-projects:update')")
    public CommonResult<Boolean> updateRepairProjects(@Valid @RequestBody RepairProjectsSaveReqVO updateReqVO) {
        repairProjectsService.updateRepairProjects(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除维修项目")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:repair-projects:delete')")
    public CommonResult<Boolean> deleteRepairProjects(@RequestParam("id") Long id) {
        repairProjectsService.deleteRepairProjects(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得维修项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:repair-projects:query')")
    public CommonResult<RepairProjectsRespVO> getRepairProjects(@RequestParam("id") Long id) {
            RepairProjectsDO repairProjects = repairProjectsService.getRepairProjects(id);
            return success(BeanUtils.toBean(repairProjects, RepairProjectsRespVO.class));
    }



    @GetMapping("/list")
    @Operation(summary = "获得维修项目列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:repair-projects:query')")
    public CommonResult<List<RepairProjectsRespVO>> getRepairProjectsList(@RequestParam("ids") Collection<Long> ids) {
        List<RepairProjectsDO> list = repairProjectsService.getRepairProjectsList(ids);
        return success(BeanUtils.toBean(list, RepairProjectsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得维修项目分页")
    @PreAuthorize("@ss.hasPermission('information:repair-projects:query')")
    public CommonResult<PageResult<RepairProjectsRespVO>> getRepairProjectsPage(@Valid RepairProjectsPageReqVO pageReqVO) {
        PageResult<RepairProjectsDO> pageResult = repairProjectsService.getRepairProjectsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RepairProjectsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出维修项目 Excel")
    @PreAuthorize("@ss.hasPermission('information:repair-projects:export')")
    @OperateLog(type = EXPORT)
    public void exportRepairProjectsExcel(@Valid RepairProjectsListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<RepairProjectsDO> list = repairProjectsService.getRepairProjectsList(exportReqVO);
        // 导出 Excel
        List<RepairProjectsExcelVO> excelVo = BeanUtils.toBean(list, RepairProjectsExcelVO.class);
        ExcelUtils.write(response, "维修项目.xls", "数据", RepairProjectsExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新维修项目列表")
    @PreAuthorize("@ss.hasPermission('information:repair-projects:batch-update')")
    public CommonResult<Boolean> batchUpdateRepairProjects(@Valid @RequestBody List<RepairProjectsSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        repairProjectsService.batchUpdateRepairProjects(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除维修项目列表")
    @PreAuthorize("@ss.hasPermission('information:repair-projects:batch-delete')")
    public CommonResult<Boolean> batchDeleteRepairProjects(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        repairProjectsService.batchDeleteRepairProjects(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载维修项目导入模板")
    @PreAuthorize("@ss.hasPermission('information:repair-projects:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<RepairProjectsExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "维修项目模板.xls", "维修项目列表", RepairProjectsExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "维修项目预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:repair-projects:import')")
    public CommonResult<List<RepairProjectsExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<RepairProjectsExcelVO> list = ExcelUtils.read(file, RepairProjectsExcelVO.class);
    return success(repairProjectsService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入维修项目")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:repair-projects:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody RepairProjectsExcelVO importReqVo) throws Exception {
        ImportResult result = repairProjectsService.importData(importReqVo);
        return success(result);
    }

    @PostMapping("all/list")
    @Operation(summary = "获取维修项目列表")
    public CommonResult<List<RepairProjectBaseVO>> getRepairProjectsAllList(@RequestBody RepairProjectBaseReqVO reqVO) {
        List<RepairProjectsDO> repairProjectsDOList = repairProjectsService.getRepairProjectsAllList(reqVO);
        return success(BeanUtils.toBean(repairProjectsDOList, RepairProjectBaseVO.class));
    }

}
