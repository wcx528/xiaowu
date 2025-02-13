package com.fmyd888.fengmao.module.information.controller.admin.container;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.*;
import com.fmyd888.fengmao.module.information.convert.container.ContainerConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.container.ContainerDO;
import com.fmyd888.fengmao.module.information.service.container.ContainerService;
import com.fmyd888.fengmao.module.infra.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 集装箱")
@Slf4j
@RestController
@RequestMapping("/information/container")
@Validated
public class ContainerController {

    @Resource
    private ContainerService containerService;

    @Resource
    private FileService fileService;

    @PostMapping("/create")
    @Operation(summary = "创建集装箱")
    @PreAuthorize("@ss.hasPermission('information:container:create')")
    public CommonResult<Long> createContainer(@Valid @RequestBody ContainerCreateReqVO createReqVO) {
        CommonResult<Long> result = success(containerService.createContainer(createReqVO));
        return result;
    }

    @PutMapping("/update")
    @Operation(summary = "更新集装箱")
    @PreAuthorize("@ss.hasPermission('information:container:update')")
    public CommonResult<Boolean> updateContainer(@Valid @RequestBody ContainerUpdateReqVO updateReqVO) {
        containerService.updateContainer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除集装箱")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:container:delete')")
    public CommonResult<Boolean> deleteContainer(@RequestParam("id") Long id) {
        containerService.deleteContainer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得集装箱")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:container:query')")
    public CommonResult<ContainerRespVO> getContainer(@RequestParam("id") Long id) {
        ContainerRespVO container = containerService.getContainer(id);
        CommonResult<ContainerRespVO> result = success(container);
        return result;
    }

    @GetMapping("/list")
    @Operation(summary = "获得集装箱列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:container:query')")
    public CommonResult<List<ContainerRespVO>> getContainerList(@RequestParam("ids") Collection<Long> ids) {
        List<ContainerDO> list = containerService.getContainerList(ids);
        CommonResult<List<ContainerRespVO>> result = success(ContainerConvert.INSTANCE.convertList(list));
        return result;
    }

    @GetMapping("/page")
    @Operation(summary = "获得集装箱分页")
    @PreAuthorize("@ss.hasPermission('information:container:query')")
    public CommonResult<PageResult<ContainerRespVO>> getContainerPage(@Valid ContainerPageReqVO pageVO) {
        PageResult<ContainerRespVO> pageResult = containerService.getContainerPage(pageVO);
        return success(pageResult);
    }

    @PutMapping("/upload")
    @Operation(summary = "上传集装箱附件")
    @PreAuthorize("@ss.hasPermission('information:container:upload')")
    public CommonResult<Boolean> uploadeContainer(@Valid @RequestBody ContainerUploadReqVO uploadReqVO) {
        containerService.uploadContainer(uploadReqVO);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出集装箱 Excel")
    @PreAuthorize("@ss.hasPermission('information:container:export')")
    @OperateLog(type = EXPORT)
    public void exportContainerExcel(@Valid ContainerExportReqVO exportReqVO,
                                     HttpServletResponse response) throws IOException {
        List<ContainerDO> list = containerService.getContainerList(exportReqVO);
        // 导出 Excel
        List<ContainerExcelVO> datas = ContainerConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "集装箱.xls", "数据", ContainerExcelVO.class, datas, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新集装箱列表")
    @PreAuthorize("@ss.hasPermission('information:container:batch-update')")
    public CommonResult<Boolean> batchUpdateContainer(@Valid @RequestBody List<ContainerUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        containerService.batchUpdateContainer(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除集装箱列表")
    @PreAuthorize("@ss.hasPermission('information:container:batch-delete')")
    public CommonResult<Boolean> batchDeleteContainer(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        containerService.batchDeleteContainer(body.get("ids"));
        return success(true);
    }

    @PostMapping("/batch-import")
    @Operation(summary = "批量新增导入集装箱列表")
    @PreAuthorize("@ss.hasPermission('information:container:batch-import')")
    public CommonResult<Boolean> batchImportContainer(@RequestBody List<ContainerDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        containerService.batchImportContainer(importReqVOList);
        return success(true);
    }

    @PostMapping("/upload")
    @Operation(summary = "集装箱附件上传")
    public CommonResult<String> uploadFile(@Valid ContainerFileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        //存储路径默认加上
        String pathName = uploadReqVO.getPathName();
        String fileUrl = fileService.createFile(file.getOriginalFilename(), pathName, IoUtil.readBytes(file.getInputStream()));
        containerService.updateContainerFileUrl(uploadReqVO.getContainerId(), fileUrl);
        return success(fileUrl);
    }


    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入集装箱模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<ContainerImportExcelVO> exampleData = Arrays.asList(
                ContainerImportExcelVO.builder()
                        .containerNumber("JZX1001")
                        .deptName("扶南物流")
                        .remark("集装箱备注").build()
        );
        // 输出
        ExcelUtils.write(response, "集装箱导入模板.xls", "集装箱列表", ContainerImportExcelVO.class, exampleData);
    }

    @PostMapping("/import")
    @Operation(summary = "导入集装箱")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:container:import')")
    public CommonResult<ContainerImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                           @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<ContainerImportExcelVO> list = ExcelUtils.read(file, ContainerImportExcelVO.class);
        ContainerImportRespVO containerImportRespVO = containerService.importContainerList(list, updateSupport);
        return success(containerImportRespVO);
    }

    @GetMapping("/getSimpleContainerList")
    @Operation(summary = "获得精简集装箱信息")
    public CommonResult<List<HashMap<String, Object>>> getSimpleContainerList(@Valid CommonQueryParam param) {
        List<HashMap<String, Object>> simpleCurrencyList = containerService.getSimpleContainerList(param);
        return success(simpleCurrencyList);
    }

}
