package com.fmyd888.fengmao.module.information.controller.admin.trailer;

import cn.hutool.core.io.IoUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.excel.core.util.ZipUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.common.vo.ImportRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.DownMainVehicleExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleDownloadVO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.dto.TrailerListDTO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.*;
import com.fmyd888.fengmao.module.information.convert.trailer.TrailerConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;
import com.fmyd888.fengmao.module.information.service.mainvehicle.MainVehicleService;
import com.fmyd888.fengmao.module.information.service.trailer.TrailerService;
import com.fmyd888.fengmao.module.infra.controller.admin.file.vo.file.FileUploadReqVO;
import com.fmyd888.fengmao.module.infra.enums.OssDirectoryEnums;
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
import java.lang.reflect.Field;
import java.util.*;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 车挂档案")
@RestController
@RequestMapping("/information/trailer")
@Validated
@Slf4j
public class TrailerController {

    @Resource
    private TrailerService trailerService;

    @Resource
    private MainVehicleService mainVehicleService;

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    @OperateLog(logArgs = false) // 上传文件，没有记录操作日志的必要
    public CommonResult<Map<String, Object>> uploadFile(FileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String originalFilename = file.getOriginalFilename();
        String path = uploadReqVO.getPath();
        String codeBusinessType = uploadReqVO.getCodeBusinessType();
        byte[] content = IoUtil.readBytes(file.getInputStream());
        OssDirectoryEnums trailerFiles = OssDirectoryEnums.TRAILER_FILES;
        Map<String, Object> map = fileService.createFile02(originalFilename,trailerFiles, path, codeBusinessType, content);
        return success(map);
    }

    @PostMapping("/create")
    @Operation(summary = "创建车挂档案")
    @PreAuthorize("@ss.hasPermission('information:trailer:create')")
    public CommonResult<Long> createTrailer(@Valid @RequestBody TrailerCreateReqVO createReqVO) {
        return success(trailerService.createTrailer(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新车挂档案")
    @PreAuthorize("@ss.hasPermission('information:trailer:update')")
    public CommonResult<Boolean> updateTrailer(@Valid @RequestBody TrailerUpdateReqVO updateReqVO) {
        trailerService.updateTrailer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除车挂档案")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:trailer:delete')")
    public CommonResult<Boolean> deleteTrailer(@RequestParam("id") Long id) {
        trailerService.deleteTrailer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得车挂档案")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:trailer:query')")
    public CommonResult<TrailerRespVO> getTrailer(@RequestParam("id") Long id) {
        TrailerRespVO trailer = trailerService.getTrailer(id);
        CommonResult<TrailerRespVO> result = success(trailer);
        return result;
    }

    @GetMapping("/list")
    @Operation(summary = "获得车挂档案列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:trailer:query')")
    public CommonResult<List<TrailerRespVO>> getTrailerList(@RequestParam("ids") Collection<Long> ids) {
        List<TrailerDO> list = trailerService.getTrailerList(ids);
        return success(TrailerConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得车挂档案分页")
    @PreAuthorize("@ss.hasPermission('information:trailer:query')")
    public CommonResult<PageResult<TrailerRespVO>> getTrailerPage(@Valid TrailerPageReqVO pageVO) {
        PageResult<TrailerRespVO> pageResult = trailerService.getTrailerPage(pageVO);
        CommonResult<PageResult<TrailerRespVO>> result = success(pageResult);
        return result;
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出车挂档案 Excel")
    @PreAuthorize("@ss.hasPermission('information:trailer:export')")
    @OperateLog(type = EXPORT)
    public void exportTrailerExcel(@Valid DownTrailerExcelVO exportReqVO,
                                   HttpServletResponse response) throws IOException {
        List<DownTrailerExcelVO> list = trailerService.getTrailerList(exportReqVO);

        ExcelUtils.write2(response, "车挂档案.xls", "数据", DownTrailerExcelVO.class, list,exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新车挂档案列表")
    @PreAuthorize("@ss.hasPermission('information:trailer:batch-update')")
    public CommonResult<Boolean> batchUpdateTrailer(@Valid @RequestBody List<TrailerUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        trailerService.batchUpdateTrailer(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除车挂档案列表")
    @PreAuthorize("@ss.hasPermission('information:trailer:batch-delete')")
    public CommonResult<Boolean> batchDeleteTrailer(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        trailerService.batchDeleteTrailer(body.get("ids"));
        return success(true);
    }


    @PostMapping("/page2")
    @Operation(summary = "用过通用关键字查询获得车挂档案分页")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public CommonResult<Page<TrailerRespVO>> getTrailerKeywordPage(@Valid @RequestBody TrailerKeywordPageReqVO pageVO) {
        Page<TrailerRespVO> trailerVOPage = trailerService.selectPageByKeyword(pageVO);
        CommonResult<Page<TrailerRespVO>> result = success(trailerVOPage);
        return result;
    }

    @GetMapping("/getCertificateFiles")
    @Operation(summary = "车挂档案证件下载")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public void getTrailerCertificateFile(@Valid TrailerDonwloadVO vehicleDonwloadVO,
                                        HttpServletResponse response) throws IOException {
        log.info("getTrailerCertificateFile车挂档案证件下载入参：{}", vehicleDonwloadVO);

        MainVehicleDownloadVO vo = trailerService.getTrailerCertificateFile(vehicleDonwloadVO);
        ZipUtils.write(response, "车挂证件.zip", vo.getZipContents());
        //删除临时文件夹及zip压缩包
        mainVehicleService.deleteFiles(vo);
    }

    @GetMapping("/getExportList")
    @Operation(summary = "车挂的字段列表")
    @PreAuthorize("@ss.hasPermission('information:trailer:export')")
    @OperateLog(type = EXPORT)
    public CommonResult<List<HashMap<String, Object>>> getExportList()  {
        List<HashMap<String, Object>> exportList = trailerService.getExportList();
        return success(exportList);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入车挂模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        List<DownTrailerImportVO> dataExample = new ArrayList<>();

        // 必填字段
        List<String> requiredFields = Arrays.asList(
                "companyName", "vehicleTrailerNo", "certificatTime",
                "vehicleType", "trailerBrand", "vehicleIdenCode",
                "vehicleColor", "vehicleMode", "tankType", "pipeConnectionType",
                "trailerWeight", "verificationmass", "bodyReporttime",
                "transporttime", "drivingDate", "unloadingType", "commodityNames"
        );

        List<String> exampleDateFields = Arrays.asList("certificatTime", "bodyReporttime", "transporttime", "drivingDate");

        // 输出
        ExcelUtils.write3(response, "车挂档案导入模板.xls", "车挂档案列表",
                DownTrailerImportVO.class,dataExample,requiredFields,exampleDateFields);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "车挂导入预览")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:mainVehicle:import')")
    public CommonResult<List<DownTrailerExcelVO>> importExcel(@RequestParam("file") MultipartFile file,
                                                                  @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws IOException {
        List<DownTrailerExcelVO> list = ExcelUtils.readExcelAndRemoveAsterisk(file, DownTrailerExcelVO.class);
        return success(trailerService.importPreviewList(list, updateSupport));
    }


    @PostMapping("/import")
    @Operation(summary = "导入车挂")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true)
    })
    @PreAuthorize("@ss.hasPermission('information:trailer:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody DownTrailerExcelVO trailerExcelVO) throws Exception {

        ImportResult importResult = trailerService.importTrailerList(trailerExcelVO);
        return success(importResult);
    }

    @GetMapping("/updateTrailerStatus")
    @Operation(summary = "车挂禁用/启用")
    public CommonResult<Void> updateTrailerStatus(@Param("id") Long id){
        trailerService.updateStatus(id);
        return success(null);
    }
    @GetMapping("/listAllTrailer")
    @Operation(summary = "获取车挂精简信息", description = "只包含被开启的车挂，主要用于前端的下拉选项")
    public CommonResult<List<TrailerListDTO> > getBasicTrailerList(@Valid TrailerListReqVO param) {
        List<TrailerListDTO> trailerList = trailerService.getTrailerList(param);
        return success(trailerList);
    }

    @GetMapping("/getBasicTrailerById")
    @Operation(summary = "根据id自动带出")
    public CommonResult<TrailerBasicRespVO> getBasicTrailerById(@Param("id") Long id) {
        // 获用户列表，只要开启状态的
        TrailerBasicRespVO trailer02 = trailerService.getTrailerById(id);
        // 排序后，返回给前端
        return success(trailer02);
    }

    @GetMapping("/getTrailerArchivetDownload")
    @Operation(summary = "车挂档案下载")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public void getTrailerArchivetDownload(@RequestParam("id") Long id,HttpServletResponse response) {
        trailerService.getTrailerArchivetDownload(id,response);
    }

    @GetMapping("/getTrailerArchiveDownload02")
    @Operation(summary = "多个车挂档案下载02")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public void getTrailerArchiveDownload02(@RequestParam("ids") List<Long> ids, HttpServletResponse response) {
        trailerService.getTrailerArchiveDownload02(ids, response);
    }

    @GetMapping("/getTrailerArchivetPrinting1")
    @Operation(summary = "车挂档案打印")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public CommonResult<TrailerPrintingVO> getTrailerArchivetPrinting(@RequestParam("id") Long id) {
        TrailerPrintingVO trailerArchivetPrinting = trailerService.getTrailerArchivetPrinting(id);
        return success(trailerArchivetPrinting);
    }

    @GetMapping("/getTrailerArchivetPrinting2")
    @Operation(summary = "2---车辆检测好评定登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:Print ')")
    public CommonResult<List<EvaluationVO>> getTrailerPrinting2(@RequestParam("id") Long id) {
        List<EvaluationVO> trailerPrintingList2 = trailerService.getTrailerPrintingList2(id);
        return success(trailerPrintingList2);
    }

    @GetMapping("/getTrailerArchivetPrinting3")
    @Operation(summary = "3---车辆维护和修理登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:Print')")
    public CommonResult<List<MaintenanceRepairVO>> getTrailerPrinting3(@RequestParam("id") Long id) {
        List<MaintenanceRepairVO> trailerPrintingList3 = trailerService.getTrailerPrintingList3(id);
        return success(trailerPrintingList3);
    }

    @GetMapping("/getTrailerArchivetPrinting4")
    @Operation(summary = "4---车辆主要部件更换登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:Print')")
    public CommonResult<List<PartReplacementVO>> getTrailerPrinting4(@RequestParam("id") Long id) {
        List<PartReplacementVO> trailerPrintingList4 = trailerService.getTrailerPrintingList4(id);
        return success(trailerPrintingList4);
    }

    @GetMapping("/getTrailerArchivetPrinting5")
    @Operation(summary = "5---车辆变更登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:Print')")
    public CommonResult<List<CarChangeVO>> getTrailerPrinting5(@RequestParam("id") Long id) {
        List<CarChangeVO> trailerPrintingList5 = trailerService.getTrailerPrintingList5(id);
        return success(trailerPrintingList5);
    }

    @GetMapping("/getTrailerArchivetPrinting6")
    @Operation(summary = "6--车辆机损事故登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
//    @PreAuthorize("@ss.hasPermission('information:main-vehicle:Print')")
    public CommonResult<List<AccidentVO>> getTrailerPrinting6(@RequestParam("id") Long id) {
        List<AccidentVO> trailerPrintingList6 = trailerService.getTrailerPrintingList6(id);
        return success(trailerPrintingList6);
    }

    @PostMapping("/insertEvaluation")
    @Operation(summary = "新增车辆检测好评定登记表")
    public CommonResult<Long> insertEvaluation(@RequestBody EvaluationVO createReqVO) {
        log.info("insertEvaluation新增车辆检测好评定登记表入参：{}", createReqVO);
        Long id = trailerService.insertEvaluation(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deleteEvaluation")
    @Operation(summary = "删除车辆检测好评定登记表")
    public CommonResult<Boolean> deleteEvaluation(Long id,Long evaluationId) {
        log.info("insertEvaluation删除车辆检测好评定登记表入参：{}", id);
        trailerService.deletEvaluation(id,evaluationId);
        return success(true);
    }

    @GetMapping("/getEvaluation")
    @Operation(summary = "查询车辆检测好评定登记表")
    public CommonResult<List<EvaluationVO>> getEvaluationList02(Long id) {
        log.info("getEvaluation删除车辆检测好评定登记表入参：{}", id);
        List<EvaluationVO> evaluationList02 = trailerService.getEvaluationList02(id);
        return success(evaluationList02);
    }

    @PostMapping("/insertAccident")
    @Operation(summary = "新增车辆机损事故登记表")
    public CommonResult<Long> insertAccident(@RequestBody AccidentVO createReqVO) {
        log.info("insertAccident新增车辆机损事故登记表入参：{}", createReqVO);
        Long id = trailerService.insertAccident(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deleteAccident")
    @Operation(summary = "删除车辆机损事故登记表")
    public CommonResult<Boolean> deleteAccident(Long id,Long accidentId) {
        log.info("insertAccident删除车辆机损事故登记表入参：{}", id);
        trailerService.deletAccident(id,accidentId);
        return success(true);
    }

    @GetMapping("/getAccident")
    @Operation(summary = "查询车辆机损事故登记表")
    public CommonResult<List<AccidentVO>> getAccidentList02(Long id) {
        log.info("getAccident删除车辆机损事故登记表入参：{}", id);
        List<AccidentVO> accidentList02 = trailerService.getAccidentList02(id);
        return success(accidentList02);
    }


    @PostMapping("/insertCarChange")
    @Operation(summary = "新增车辆变更登记表")
    public CommonResult<Long> insertCarChange(@RequestBody CarChangeVO createReqVO) {
        log.info("insertCarChange新增车辆变更登记表入参：{}", createReqVO);
        Long id = trailerService.insertCarChange(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deleteCarChange")
    @Operation(summary = "删除车辆变更登记表")
    public CommonResult<Boolean> deleteCarChange(Long id,Long carChangeId) {
        log.info("insertCarChange删除车辆变更登记表入参：{}{}", id,carChangeId);
        trailerService.deletCarChange(id,carChangeId);
        return success(true);
    }

    @GetMapping("/getCarChange")
    @Operation(summary = "查询车辆变更登记表")
    public CommonResult<List<CarChangeVO>> getCarChangeList02(Long id) {
        log.info("getCarChange删除车辆变更登记表入参：{}", id);
        List<CarChangeVO> carChangeList02 = trailerService.getCarChangeList02(id);
        return success(carChangeList02);
    }
    @PostMapping("/insertMaintenanceRepair")
    @Operation(summary = "新增车辆维护和修理登记表")
    public CommonResult<Long> insertMaintenanceRepair(@RequestBody MaintenanceRepairVO createReqVO) {
        log.info("insertMaintenanceRepair新增车辆维护和修理登记表入参：{}", createReqVO);
        Long id = trailerService.insertMaintenanceRepair(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deleteMaintenanceRepair")
    @Operation(summary = "删除车辆维护和修理登记表")
    public CommonResult<Boolean> deleteMaintenanceRepair(Long id,Long maintenanceRepairId) {
        log.info("insertMaintenanceRepair删除车辆维护和修理登记表入参：{}", id);
        trailerService.deletMaintenanceRepair(id,maintenanceRepairId);
        return success(true);
    }

    @GetMapping("/getMaintenanceRepair")
    @Operation(summary = "查询车辆维护和修理登记表")
    public CommonResult<List<MaintenanceRepairVO>> getMaintenanceRepairList02(Long id) {
        log.info("getMaintenanceRepair删除车辆维护和修理登记表入参：{}", id);
        List<MaintenanceRepairVO> maintenanceRepairList02 = trailerService.getMaintenanceRepairList02(id);
        return success(maintenanceRepairList02);
    }

    @PostMapping("/insertPartReplacement")
    @Operation(summary = "新增车辆主要部件更换登记表")
    public CommonResult<Long> insertPartReplacement(@RequestBody PartReplacementVO createReqVO) {
        log.info("insertPartReplacement新增车辆主要部件更换登记表入参：{}", createReqVO);
        Long id = trailerService.insertPartReplacement(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deletePartReplacement")
    @Operation(summary = "删除车辆主要部件更换登记表")
    public CommonResult<Boolean> deletePartReplacement(Long id,Long partReplacementId) {
        log.info("insertPartReplacement删除车辆主要部件更换登记表入参：{}", id);
        trailerService.deletPartReplacement(id,partReplacementId);
        return success(true);
    }

    @GetMapping("/getPartReplacement")
    @Operation(summary = "查询车辆主要部件更换登记表")
    public CommonResult<List<PartReplacementVO>> getPartReplacementList02(Long id) {
        log.info("getPartReplacement删除车辆主要部件更换登记表入参：{}", id);
        List<PartReplacementVO> partReplacementList02 = trailerService.getPartReplacementList02(id);
        return success(partReplacementList02);
    }

    @GetMapping("/history")
    @Operation(summary = "车头历史附件查询")
    public CommonResult<TrailerHistoryVO> listHistoryFile(Long id) {
        TrailerHistoryVO trailerHistoryVO = trailerService.listHistoryFile(id);
        return success(trailerHistoryVO);
    }
    @GetMapping("/getCommodityInfoById")
    @Operation(summary = "用于car车辆新增时，根据车挂查询到可选择的【运输介质】")
    public CommonResult<List<Map<String, Object>>> getCommodityInfoById(@RequestParam("id") Long id) {
        List<Map<String, Object>> commodityInfo = trailerService.getCommodityInfoById(id);
        return success(commodityInfo);
    }

}


