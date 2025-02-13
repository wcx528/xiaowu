package com.fmyd888.fengmao.module.information.controller.admin.transportmanger;

import cn.hutool.core.io.IoUtil;
import com.fmyd888.fengmao.framework.common.pojo.*;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressExportReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportdetail.TransportDetailDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportmanger.TransportMangerDO;
import com.fmyd888.fengmao.module.information.service.transportmanger.TransportMangerService;
import com.fmyd888.fengmao.module.infra.enums.OssDirectoryEnums;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.infra.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
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

@Tag(name = "管理后台 - 运输证管理")
@RestController
@RequestMapping("/information/transport-manger")
@Validated
@Slf4j
public class TransportMangerController {

    @Resource
    private TransportMangerService transportMangerService;
    @Resource
    private FileService fileService;

    @PostMapping("/create")
    @Operation(summary = "创建运输证管理")
    @PreAuthorize("@ss.hasPermission('information:transport-manger:create')")
    public CommonResult<Long> createTransportManger(@Valid @RequestBody TransportMangerSaveReqVO createReqVO) {
        return success(transportMangerService.createTransportManger(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运输证管理")
    @PreAuthorize("@ss.hasPermission('information:transport-manger:update')")
    public CommonResult<Boolean> updateTransportManger(@Valid @RequestBody TransportMangerSaveReqVO updateReqVO) {
        transportMangerService.updateTransportManger(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运输证管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:transport-manger:delete')")
    public CommonResult<Boolean> deleteTransportManger(@RequestParam("id") Long id) {
        transportMangerService.deleteTransportManger(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运输证管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:transport-manger:query')")
    public CommonResult<TransportMangerRespVO> getTransportManger(@RequestParam("id") Long id) {
        TransportMangerRespVO transportManger = transportMangerService.getTransportManger(id);
        return success(BeanUtils.toBean(transportManger, TransportMangerRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得运输证管理列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:transport-manger:query')")
    public CommonResult<List<TransportMangerRespVO>> getTransportMangerList(@RequestParam("ids") Collection<Long> ids) {
        List<TransportMangerDO> list = transportMangerService.getTransportMangerList(ids);
        return success(BeanUtils.toBean(list, TransportMangerRespVO.class));
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出运输证管理 Excel")
    @PreAuthorize("@ss.hasPermission('information:transport-manger:export')")
    @OperateLog(type = EXPORT)
    public void exportTransportMangerExcel(@Valid TransportMangerKeywordPageReqVO pageReqVO,
                                           HttpServletResponse response) throws IOException {
        //List<TransportMangerDO> list = transportMangerService.getTransportMangerList(exportReqVO);
        //List<TransportMangerExcelVO> excelVo = BeanUtils.toBean(list, TransportMangerExcelVO.class);

        CardPage<TransportMangerRespVO> list = transportMangerService.selectPageByKeyword(pageReqVO);
        List<TransportMangerRespVO> excelVo = list.getRecords();  // 导出 Excel
        ExcelUtils.write2(response, "运输证管理.xls", "数据", TransportMangerRespVO.class, excelVo, pageReqVO.getExportFileds());
    }

    @GetMapping("/download-excel")
    @Operation(summary = "下载运输证相关Excel")
    @Parameter(name = "id", description = "运输证id", required = true)
    @PreAuthorize("@ss.hasPermission('information:transport-manger:download')")
    public void exportAddressExcel(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        List<TransportDownloadExcelVO> datas = transportMangerService.transportDownload(id);
        String filename = "运输证.xls";
        String sheetName = "数据";
        ExcelUtils.write2(response, filename, sheetName, TransportDownloadExcelVO.class, datas,null);
    }
    @GetMapping("/page2")
    @Operation(summary = "用过通用关键字查询获得运输证管理分页")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public CommonResult<CardPage<TransportMangerRespVO>> getTransportMangerKeywordPage(@Valid TransportMangerKeywordPageReqVO pageVO) {
        CardPage<TransportMangerRespVO> transportMangerVOPage = transportMangerService.selectPageByKeyword(pageVO);
        CommonResult<CardPage<TransportMangerRespVO>> result = success(transportMangerVOPage);
        return result;
    }

    @GetMapping("/getBasicTransportMangerById")
    @Operation(summary = "根据id自动带出")
    public CommonResult<TransportMangerSaveReqVO> getBasicTransportMangerById(@Param("id") Long id) {
        // 获用户列表，只要开启状态的
        TransportMangerSaveReqVO transportManger = transportMangerService.getTransportManger02(id);
        // 排序后，返回给前端
        return success(transportManger);
    }

    @PostMapping("/upload")
    @Operation(summary = "运输证上传文件")
    public CommonResult<Map<String, Object>> uploadFile(@Valid TransportMangerFileUploadRespVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String originalFilename = file.getOriginalFilename();
        String path = uploadReqVO.getPath();
        OssDirectoryEnums transportMangerUpload = OssDirectoryEnums.PURCHASE_CONTRACT_UPLOAD;
        String codeBusinessType = FileEnums.TRANSPORT_MANGER_FILE_EUM.getCodeBusinessType();
        byte[] content = IoUtil.readBytes(file.getInputStream());
        Map<String, Object> map = fileService.createFile02(originalFilename, transportMangerUpload, path, codeBusinessType, content);
        return success(map);
    }

    @GetMapping("/listAllTransportManger")
    @Operation(summary = "获取运输证编号精简信息", description = "只包含被开启的运输证，主要用于前端的下拉选项")
    public CommonResult<List<TransportMangerSimpleRespVO>> getBasicTransportMangerList() {
        List<TransportMangerSimpleRespVO> transportMangerList = transportMangerService.getTransportMangerList();
        return success(transportMangerList);
    }


    @PutMapping("/batch-update")
    @Operation(summary = "批量更新运输证管理列表")
    @PreAuthorize("@ss.hasPermission('information:transport-manger:batch-update')")
    public CommonResult<Boolean> batchUpdateTransportManger(@Valid @RequestBody List<TransportMangerSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        transportMangerService.batchUpdateTransportManger(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除运输证管理列表")
    @PreAuthorize("@ss.hasPermission('information:transport-manger:batch-delete')")
    public CommonResult<Boolean> batchDeleteTransportManger(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        transportMangerService.batchDeleteTransportManger(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载运输证管理导入模板")
    @PreAuthorize("@ss.hasPermission('information:transport-manger:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<TransportMangerExcelVO> list = Arrays.asList();

        // 输出
        ExcelUtils.write(response, "运输证管理模板.xls", "运输证管理列表", TransportMangerExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "运输证管理预览")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:transport-manger:import')")
    public CommonResult<List<TransportMangerExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
                                                                    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<TransportMangerExcelVO> list = ExcelUtils.read(file, TransportMangerExcelVO.class);
        return success(transportMangerService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入运输证管理")
    @Parameters({
            @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:transport-manger:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody TransportMangerExcelVO importReqVo) throws Exception {
        ImportResult result = transportMangerService.importData(importReqVo);
        return success(result);
    }

// ==================== 子表（运输证明细） ====================

    @GetMapping("/transport-detail/page")
    @Operation(summary = "获得运输证明细分页")
    @Parameter(name = "transportId", description = "运输证Id")
    @PreAuthorize("@ss.hasPermission('information:transport-manger:query')")
    public CommonResult<PageResult<TransportDetailDO>> getTransportDetailPage(PageParam pageReqVO,
                                                                              @RequestParam("transportId") Long transportId) {
        return success(transportMangerService.getTransportDetailPage(pageReqVO, transportId));
    }

    @GetMapping("/transport-detail/list-by-transport-id")
    @Operation(summary = "获得运输证明细列表")
    @Parameter(name = "transportId", description = "运输证Id")
    @PreAuthorize("@ss.hasPermission('information:transport-manger:query')")
    public CommonResult<List<TransportDetailDO>> getTransportDetailListByTransportId(@RequestParam("transportId") Long transportId) {
        return success(transportMangerService.getTransportDetailListByTransportId(transportId));
    }

    @GetMapping("/transport-carCode")
    @Operation(summary = "获取运输证车辆信息")
    public CommonResult<List<TransportCarRespVO>> getTransportCarList(@Valid CommonQueryBaseParam param) {
        List<TransportCarRespVO> list = transportMangerService.getTransportCarList(param);
        return success(list);
    }

    @GetMapping("/selectTransportNo")
    @Operation(summary = "运输证编号接口")
    public CommonResult<List<HashMap<String, Object>>> getTransportCarList() {
        List<HashMap<String, Object>> list = transportMangerService.selectTransportNo();
        return success(list);
    }
}
