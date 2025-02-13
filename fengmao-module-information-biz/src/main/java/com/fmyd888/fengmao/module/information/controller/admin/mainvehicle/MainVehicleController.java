package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.excel.core.util.ZipUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.common.vo.ImportRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.*;
import com.fmyd888.fengmao.module.information.convert.mainvehicle.MainVehicleConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.service.mainvehicle.MainVehicleService;
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
import java.time.LocalDateTime;
import java.util.*;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 车头档案")
@Slf4j
@RestController
@RequestMapping("/information/main-vehicle")
@Validated
public class MainVehicleController {

    @Resource
    private MainVehicleService mainVehicleService;
    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    @OperateLog(logArgs = false) // 上传文件，没有记录操作日志的必要
    public CommonResult<Map<String, Object>> uploadFile(@Valid FileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String originalFilename = file.getOriginalFilename();
        String path = uploadReqVO.getPath();
        String codeBusinessType = uploadReqVO.getCodeBusinessType();
        byte[] content = IoUtil.readBytes(file.getInputStream());
        OssDirectoryEnums mainVehicleFiles = OssDirectoryEnums.MAIN_VEHICLE_FILES;
        Map<String, Object> map = fileService.createFile02(originalFilename, mainVehicleFiles, path, codeBusinessType, content);
        return success(map);
    }

    @PostMapping("/create")
    @Operation(summary = "创建车头档案")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:create')")
    public CommonResult<Long> createMainVehicle(@Valid @RequestBody MainVehicleCreateReqVO createReqVO) {
        log.info("createMainVehicle创建车头档案入参：{}", createReqVO);
        CommonResult<Long> result = success(mainVehicleService.createMainVehicle(createReqVO));
        return result;
    }

    @PutMapping("/update")
    @Operation(summary = "更新车头档案")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:update')")
    public CommonResult<Boolean> updateMainVehicle(@Valid @RequestBody MainVehicleUpdateReqVO updateReqVO) {
        mainVehicleService.updateMainVehicle(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除车头档案")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:delete')")
    public CommonResult<Boolean> deleteMainVehicle(@RequestParam("id") Long id) {
        mainVehicleService.deleteMainVehicle(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得车头档案")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public CommonResult<MainVehicleRespVO> getMainVehicle(@RequestParam("id") Long id) {
        MainVehicleRespVO mainVehicle = mainVehicleService.getMainVehicle(id);
        CommonResult<MainVehicleRespVO> result = success(mainVehicle);
        return result;
    }

    @GetMapping("/list")
    @Operation(summary = "获得车头档案列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public CommonResult<List<MainVehicleRespVO>> getMainVehicleList(@RequestParam("ids") Collection<Long> ids) {
        List<MainVehicleDO> list = mainVehicleService.getMainVehicleList(ids);
        CommonResult<List<MainVehicleRespVO>> result = success(MainVehicleConvert.INSTANCE.convertList(list));
        return result;
    }

    //@GetMapping("/page")
    //@Operation(summary = "获得车头档案分页")
    //@PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    //public CommonResult<PageResult<MainVehicleRespVO>> getMainVehiclePage(@Valid MainVehiclePageReqVO pageVO) {
    //    log.info("getMainVehiclePage获得车头档案分页入参：{}", pageVO);
    //    PageResult<MainVehicleRespVO> pageResult = mainVehicleService.getMainVehiclePage(pageVO);
    //    CommonResult<PageResult<MainVehicleRespVO>> result = success(pageResult);
    //    log.info("getMainVehiclePage获得车头档案分页返回：{}", result);
    //    return result;
    //}

    @GetMapping("/export-excel")
    @Operation(summary = "导出车头档案 Excel")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:export')")
    @OperateLog(type = EXPORT)
    public void exportMainVehicleExcel(@Valid MainVehicleExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<DownMainVehicleExcelVO> mainVehicleList = mainVehicleService.getMainVehicleList(exportReqVO);

        ExcelUtils.write2(response, "车头档案.xls", "数据", DownMainVehicleExcelVO.class, mainVehicleList, exportReqVO.getExportFileds());
    }

    @GetMapping("/getExportList")
    @Operation(summary = "车头导出的字段列表")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:export')")
    @OperateLog(type = EXPORT)
    public CommonResult<List<HashMap<String, Object>>> getExportList()  {
        List<HashMap<String, Object>> exportList = mainVehicleService.getExportList();
       return success(exportList);
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新车头档案列表")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:batch-update')")
    public CommonResult<Boolean> batchUpdateMainVehicle(@Valid @RequestBody List<MainVehicleUpdateReqVO> updateReqVOList) {
        log.info("batchUpdateMainVehicle批量更新车头档案入参：{}", updateReqVOList);
        // 在这里处理批量更新逻辑
        mainVehicleService.batchUpdateMainVehicle(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除车头档案列表")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:batch-delete')")
    public CommonResult<Boolean> batchDeleteMainVehicle(@RequestBody Map<String, List<Long>> body) {
        log.info("batchDeleteMainVehicle批量删除车头档案列表入参：{}", body);
        // 在这里处理批量删除逻辑
        mainVehicleService.batchDeleteMainVehicle(body.get("ids"));
        return success(true);
    }

    @PostMapping("/batch-import")
    @Operation(summary = "批量新增导入车头档案列表")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:batch-import')")
    public CommonResult<Boolean> batchImportMainVehicle(@RequestBody List<MainVehicleDO> importReqVOList) {
        log.info("batchImportMainVehicle批量新增导入车头档案列表入参：{}", importReqVOList);
        // 在这里处理批量新增导入逻辑
        mainVehicleService.batchImportMainVehicle(importReqVOList);
        return success(true);
    }

    @PostMapping("/page2")
    @Operation(summary = "用过通用关键字查询获得车头档案分页")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public CommonResult<Page<MainVehicleRespVO>> getMainVehicleKeywordPage(@Valid @RequestBody MainVehicleKeywordPageReqVO pageVO) {
        log.info("getMainVehicleKeywordPage用过通用关键字查询获得车头档案分页入参：{}", pageVO);
        Page<MainVehicleRespVO> mainVehicleVOPage = mainVehicleService.selectPageByKeyword(pageVO);
//        CommonResult<Page<MainVehicleRespVO>> result = success(mainVehicleVOPage);
        return success(mainVehicleVOPage);
    }

    @GetMapping("/getCertificateFiles")
    @Operation(summary = "车头档案证件下载")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public void getMainVehicleCertificateFile(@Valid MainVehicleDownloadReqVO vehicleDonwloadReqVO, HttpServletResponse response) throws IOException {
        log.info("getMainVehicleCertificateFile车头档案证件下载入参：{}", vehicleDonwloadReqVO);

        MainVehicleDownloadVO vo = mainVehicleService.getMainVehicleCertificateFile(vehicleDonwloadReqVO);
        ZipUtils.write(response, "车头档案.zip", vo.getZipContents());
        //删除临时文件夹及zip压缩包
        mainVehicleService.deleteFiles(vo);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载导入车头模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        List<DownMainVehicleImportVO> dataExample = new ArrayList<>();

        // 必填字段
        List<String> requiredFields = Arrays.asList(
                "companyName", "plateNumber", "motorvehicleNumber", "registerTime",
                "vehicleType", "vehicleBrand", "vehicleFrame", "vehicleColor",
                "vehicleModel", "engineCode", "engineType", "fuelType", "power",
                "manufacturerName", "turningForm", "transportDate", "identifier",
                "originalPrice", "frontWeight", "drivingDate"
        );
        // 需要添加示例日期的字段列表
        List<String> exampleDateFields = Arrays.asList("registerTime", "transportDate", "drivingDate", "productionDate");

        // 输出
        ExcelUtils.write3(response, "车头档案导入模板.xls", "车头档案列表",
                DownMainVehicleImportVO.class, dataExample, requiredFields, exampleDateFields);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "车头导入预览")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:mainVehicle:import')")
    public CommonResult<List<DownMainVehicleExcelVO>> importExcel(@RequestParam("file") MultipartFile file,
                                                    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws IOException {
        List<DownMainVehicleExcelVO> list = ExcelUtils.readExcelAndRemoveAsterisk(file, DownMainVehicleExcelVO.class);
        return success(mainVehicleService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入车头")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:mainVehicle:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody DownMainVehicleExcelVO importMainVehicle) throws Exception {
        ImportResult importResult = mainVehicleService.importMainVehicleList(importMainVehicle);
        return success(importResult);
    }

    @GetMapping("/updateMainVehicleStatus")
    @Operation(summary = "车头禁用/启用")
    public CommonResult<Void> updateMainVehicleStatus(@Param("id") Long id) {
        mainVehicleService.updateStatus(id);
        return success(null);
    }

    @GetMapping("/listAllMainVehicle")
    @Operation(summary = "获取车头精简信息", description = "只包含被开启的车头，主要用于前端的下拉选项")
    public CommonResult<List<MainVehicleSimpleRespVO>> getBasicMainVehicleList(@RequestParam(value = "companyId",required = false) Long companyId,
                                                                               @RequestParam(value = "isOut",required = false) Boolean isOut) {
        List<MainVehicleSimpleRespVO> mainVehicleList = mainVehicleService.getMainVehicleDetails(companyId,isOut);
        return success(mainVehicleList);
    }

    @GetMapping("/getBasicMainVehicleById")
    @Operation(summary = "根据id自动带出")
    public CommonResult<MainVehicleBasicRespVO> getBasicMainVehicleById(@Param("id") Long id) {
        // 获用户列表，只要开启状态的
        MainVehicleBasicRespVO mainVehicle02 = mainVehicleService.getMainVehicle02(id);
        // 排序后，返回给前端
        return success(mainVehicle02);
    }


    @GetMapping("/getMainVehicleArchivetDownload")
    @Operation(summary = "车头档案下载")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public void getMainVehicleArchivetDownload(@RequestParam("id") Long id, HttpServletResponse response) {
        log.info("getMainVehicle获得车头档案入参：{}", id);
        mainVehicleService.getMainVehicleArchivetDownload(id, response);
    }

    @GetMapping("/getMainVehicleArchivetDownload02")
    @Operation(summary = "多个车头档案下载02")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public void getMainVehicleArchivetDownload02(@RequestParam("ids") List<Long> ids, HttpServletResponse response) {
        log.info("getMainVehicleArchivetDownload02多个车头档案下载：{}", ids);
        mainVehicleService.getMainVehicleArchivetDownload02(ids, response);
    }

    @GetMapping("/getMainVehicleArchivetPrinting1")
    @Operation(summary = "车头档案打印")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public CommonResult<MainVehiclePrintingVO> getMainVehicleArchivetPrinting(@RequestParam("id") Long id) {
        log.info("getMainVehicle获得车头档案入参：{}", id);
        MainVehiclePrintingVO mainVehicleArchivetPrinting = mainVehicleService.getMainVehicleArchivetPrinting(id);
        return success(mainVehicleArchivetPrinting);
    }

    @GetMapping("/getMainVehicleArchivetPrinting2")
    @Operation(summary = "2---车辆检测好评定登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:Print ')")
    public CommonResult<List<EvaluationVO>> getMainVehiclePrinting2(@RequestParam("id") Long id) {
        log.info("getMainVehicle获得车头档案入参：{}", id);
        List<EvaluationVO> mainVehiclePrintingList2 = mainVehicleService.getMainVehiclePrintingList2(id);
        return success(mainVehiclePrintingList2);
    }

    @GetMapping("/getMainVehicleArchivetPrinting3")
    @Operation(summary = "3---车辆维护和修理登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:Print')")
    public CommonResult<List<MaintenanceRepairVO>> getMainVehiclePrinting3(@RequestParam("id") Long id) {
        log.info("getMainVehicle获得车头档案入参：{}", id);
        List<MaintenanceRepairVO> mainVehiclePrintingList3 = mainVehicleService.getMainVehiclePrintingList3(id);
        return success(mainVehiclePrintingList3);
    }

    @GetMapping("/getMainVehicleArchivetPrinting4")
    @Operation(summary = "4---车辆主要部件更换登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:Print')")
    public CommonResult<List<PartReplacementVO>> getMainVehiclePrinting4(@RequestParam("id") Long id) {
        log.info("getMainVehicle获得车头档案入参：{}", id);
        List<PartReplacementVO> mainVehiclePrintingList4 = mainVehicleService.getMainVehiclePrintingList4(id);
        return success(mainVehiclePrintingList4);
    }

    @GetMapping("/getMainVehicleArchivetPrinting5")
    @Operation(summary = "5---车辆变更登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:Print')")
    public CommonResult<List<CarChangeVO>> getMainVehiclePrinting5(@RequestParam("id") Long id) {
        log.info("getMainVehicle获得车头档案入参：{}", id);
        List<CarChangeVO> mainVehiclePrintingList5 = mainVehicleService.getMainVehiclePrintingList5(id);
        return success(mainVehiclePrintingList5);
    }

    @GetMapping("/getMainVehicleArchivetPrinting6")
    @Operation(summary = "6---车辆行驶里程登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<List<MileageVO>> getMainVehiclePrinting6(@RequestParam("id") Long id) {
        log.info("getMainVehicle获得车头档案入参：{}", id);
        List<MileageVO> mainVehiclePrintingList6 = mainVehicleService.getMainVehiclePrintingList6(id);
        return success(mainVehiclePrintingList6);
    }

    @GetMapping("/getMainVehicleArchivetPrinting7")
    @Operation(summary = "7--车辆机损事故登记表的预览与打印")
    @Parameter(name = "id", description = "编号", required = true)
//    @PreAuthorize("@ss.hasPermission('information:main-vehicle:Print')")
    public CommonResult<List<AccidentVO>> getMainVehiclePrinting7(@RequestParam("id") Long id) {
        log.info("getMainVehicle获得车头档案入参：{}", id);
        List<AccidentVO> mainVehiclePrintingList7 = mainVehicleService.getMainVehiclePrintingList7(id);
        return success(mainVehiclePrintingList7);
    }

    @PostMapping("/insertEvaluation")
    @Operation(summary = "新增车辆检测好评定登记表")
    public CommonResult<Long> insertEvaluation(@RequestBody EvaluationVO createReqVO) {
        log.info("insertEvaluation新增车辆检测好评定登记表入参：{}", createReqVO);
        Long id = mainVehicleService.insertEvaluation(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deleteEvaluation")
    @Operation(summary = "删除车辆检测好评定登记表")
    public CommonResult<Boolean> deleteEvaluation(Long id, Long evaluationId) {
        log.info("insertEvaluation删除车辆检测好评定登记表入参：{}", id);
        mainVehicleService.deletEvaluation(id, evaluationId);
        return success(true);
    }

    @GetMapping("/getEvaluation")
    @Operation(summary = "查询车辆检测好评定登记表")
    public CommonResult<List<EvaluationVO>> getEvaluationList01(Long id) {
        log.info("getEvaluation删除车辆检测好评定登记表入参：{}", id);
        List<EvaluationVO> evaluationList01 = mainVehicleService.getEvaluationList01(id);
        return success(evaluationList01);
    }

    @PostMapping("/insertAccident")
    @Operation(summary = "新增车辆机损事故登记表")
    public CommonResult<Long> insertAccident(@RequestBody AccidentVO createReqVO) {
        log.info("insertAccident新增车辆机损事故登记表入参：{}", createReqVO);
        Long id = mainVehicleService.insertAccident(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deleteAccident")
    @Operation(summary = "删除车辆机损事故登记表")
    public CommonResult<Boolean> deleteAccident(Long id, Long accidentId) {
        log.info("insertAccident删除车辆机损事故登记表入参：{}", id);
        mainVehicleService.deletAccident(id, accidentId);
        return success(true);
    }

    @GetMapping("/getAccident")
    @Operation(summary = "查询车辆机损事故登记表")
    public CommonResult<List<AccidentVO>> getAccidentList01(Long id) {
        log.info("getAccident删除车辆机损事故登记表入参：{}", id);
        List<AccidentVO> accidentList01 = mainVehicleService.getAccidentList01(id);
        return success(accidentList01);
    }

    @PostMapping("/insertCarChange")
    @Operation(summary = "新增车辆变更登记表")
    public CommonResult<Long> insertCarChange(@RequestBody CarChangeVO createReqVO) {
        log.info("insertCarChange新增车辆变更登记表入参：{}", createReqVO);
        Long id = mainVehicleService.insertCarChange(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deleteCarChange")
    @Operation(summary = "删除车辆变更登记表")
    public CommonResult<Boolean> deleteCarChange(Long id, Long carChangeId) {
        log.info("insertCarChange删除车辆变更登记表入参：{}{}", id, carChangeId);
        mainVehicleService.deletCarChange(id, carChangeId);
        return success(true);
    }

    @GetMapping("/getCarChange")
    @Operation(summary = "查询车辆变更登记表")
    public CommonResult<List<CarChangeVO>> getCarChangeList01(Long id) {
        log.info("getCarChange删除车辆变更登记表入参：{}", id);
        List<CarChangeVO> carChangeList01 = mainVehicleService.getCarChangeList01(id);
        return success(carChangeList01);
    }

    @PostMapping("/insertMaintenanceRepair")
    @Operation(summary = "新增车辆维护和修理登记表")
    public CommonResult<Long> insertMaintenanceRepair(@RequestBody MaintenanceRepairVO createReqVO) {
        log.info("insertMaintenanceRepair新增车辆维护和修理登记表入参：{}", createReqVO);
        Long id = mainVehicleService.insertMaintenanceRepair(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deleteMaintenanceRepair")
    @Operation(summary = "删除车辆维护和修理登记表")
    public CommonResult<Boolean> deleteMaintenanceRepair(Long id, Long maintenanceRepairId) {
        log.info("insertMaintenanceRepair删除车辆维护和修理登记表入参：{}", id);
        mainVehicleService.deletMaintenanceRepair(id, maintenanceRepairId);
        return success(true);
    }

    @GetMapping("/getMaintenanceRepair")
    @Operation(summary = "查询车辆维护和修理登记表")
    public CommonResult<List<MaintenanceRepairVO>> getMaintenanceRepairList01(Long id) {
        log.info("getMaintenanceRepair删除车辆维护和修理登记表入参：{}", id);
        List<MaintenanceRepairVO> maintenanceRepairList01 = mainVehicleService.getMaintenanceRepairList01(id);
        return success(maintenanceRepairList01);
    }

    @PostMapping("/insertPartReplacement")
    @Operation(summary = "新增车辆主要部件更换登记表")
    public CommonResult<Long> insertPartReplacement(@RequestBody PartReplacementVO createReqVO) {
        log.info("insertPartReplacement新增车辆主要部件更换登记表入参：{}", createReqVO);
        Long id = mainVehicleService.insertPartReplacement(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deletePartReplacement")
    @Operation(summary = "删除车辆主要部件更换登记表")
    public CommonResult<Boolean> deletePartReplacement(Long id, Long partReplacementId) {
        log.info("insertPartReplacement删除车辆主要部件更换登记表入参：{}", id);
        mainVehicleService.deletPartReplacement(id, partReplacementId);
        return success(true);
    }

    @GetMapping("/getPartReplacement")
    @Operation(summary = "查询辆主要部件更换登记表")
    public CommonResult<List<PartReplacementVO>> getPartReplacementList01(Long id) {
        log.info("getPartReplacement删除行使里程登记表入参：{}", id);
        List<PartReplacementVO> partReplacementList01 = mainVehicleService.getPartReplacementList01(id);
        return success(partReplacementList01);
    }

    @PostMapping("/insertMileage")
    @Operation(summary = "新增行使里程登记表")
    public CommonResult<Long> insertMileage(@RequestBody MileageVO createReqVO) {
        log.info("insertMileage新增行使里程登记表入参：{}", createReqVO);
        Long id = mainVehicleService.insertMileage(createReqVO);
        return success(id);
    }

    @DeleteMapping("/deleteMileage")
    @Operation(summary = "删除行使里程登记表")
    public CommonResult<Boolean> deleteMileage(Long id, Long mileageId) {
        log.info("insertMileage删除行使里程登记表入参：{}", id);
        mainVehicleService.deletMileage(id, mileageId);
        return success(true);
    }

    @GetMapping("/getMileage")
    @Operation(summary = "查询行使里程登记表")
    public CommonResult<List<MileageVO>> getMileageList01(Long id) {
        log.info("getMileage删除行使里程登记表入参：{}", id);
        List<MileageVO> mileageList01 = mainVehicleService.getMileageList01(id);
        return success(mileageList01);
    }

    @GetMapping("/history")
    @Operation(summary = "车头历史附件查询")
    public CommonResult<MainVehicleHistoryVO> listHistoryFile(Long id) {
        MainVehicleHistoryVO mainVehicleHistoryVO = mainVehicleService.listHistoryFile(id);
        return success(mainVehicleHistoryVO);
    }

}
