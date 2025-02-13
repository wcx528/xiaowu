package com.fmyd888.fengmao.module.information.controller.admin.car;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonInformationQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.common.vo.CarCyclePage;
import com.fmyd888.fengmao.module.information.controller.admin.car.dto.CarChangeRespDTO;
import com.fmyd888.fengmao.module.information.controller.admin.car.dto.CarScrapOrCancelProcessReqDTO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.DownTrailerExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.DownTrailerImportVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.service.car.CarService;
import com.fmyd888.fengmao.module.system.api.dict.DictDataApi;
import com.fmyd888.fengmao.module.system.api.dict.dto.DictDataRespDTO;
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

@Tag(name = "管理后台 - 车辆档案")
@RestController
@RequestMapping("/information/car")
@Validated
@Slf4j
public class CarController {

    @Resource
    private CarService carService;
    @Resource
    private DictDataApi dictDataApi;

    @PostMapping("/create")
    @Operation(summary = "创建车辆档案")
    @PreAuthorize("@ss.hasPermission('information:car:create')")
    public CommonResult<String> createCar(@Valid @RequestBody CarSaveReqVO createReqVO) {
        String reslutStr = carService.createCar(createReqVO);
        return CommonResult.success(reslutStr);

    }

    @PutMapping("/update")
    @Operation(summary = "更新车辆档案")
    @PreAuthorize("@ss.hasPermission('information:car:update')")
    public CommonResult<Boolean> updateCar(@Valid @RequestBody CarSaveReqVO updateReqVO) {
         carService.updateCar2(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除车辆档案")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:car:delete')")
    public CommonResult<Boolean> deleteCar(@RequestParam("id") Long id) {
        carService.deleteCar(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得车辆档案")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:car:query')")
    public CommonResult<CarRespVO> getCar(@RequestParam("id") Long id) {
        CarRespVO car = carService.getCar(id);
        return success(car);
    }

    @GetMapping("/list")
    @Operation(summary = "获得车辆档案列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:car:query')")
    public CommonResult<List<CarRespVO>> getCarList(@RequestParam("ids") Collection<Long> ids) {
        List<CarRespVO> list = carService.getCarList(ids);
        return success(list);
    }

//    @GetMapping("/page")
//    @Operation(summary = "获得车辆档案分页")
//    @PreAuthorize("@ss.hasPermission('information:car:query')")
//    public CommonResult<PageResult<CarRespVO>> getCarPage(@Valid CarPageReqVO pageVO) {
//        log.info("getCarPage获得车辆档案分页入参：{}", pageVO);
//        PageResult<CarRespVO> pageResult = carService.getCarPage(pageVO);
//        CommonResult<PageResult<CarRespVO>> result = success(pageResult);
//        log.info("getCarPage获得车辆档案分页返回：{}", result);
//        return result;
//    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出车辆档案 Excel")
    @PreAuthorize("@ss.hasPermission('information:car:export')")
    @OperateLog(type = EXPORT)
    public void exportCarExcel(@Valid CarExportReqVO exportReqVO,
                               HttpServletResponse response) throws IOException {
        List<CarExcelVO> list = carService.getCarList(exportReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "车辆档案.xls", "数据", CarExcelVO.class, list,exportReqVO.getExportFileds());
    }

    @GetMapping("/getExportList")
    @Operation(summary = "导出车辆档案的字段列表")
    @PreAuthorize("@ss.hasPermission('information:car:getExportList')")
    @OperateLog(type = EXPORT)
    public CommonResult<List<HashMap<String, Object>>> getExportList()  {
        List<HashMap<String, Object>> exportList = carService.getExportList();
       return success(exportList);
    }

//    @PutMapping("/batch-update")
//    @Operation(summary = "批量更新车辆档案列表")
//    @PreAuthorize("@ss.hasPermission('information:car:batch-update')")
//    public CommonResult<Boolean> batchUpdateCar(@Valid @RequestBody List<CarSaveReqVO> updateReqVOList) {
//        // 在这里处理批量更新逻辑
//        carService.batchUpdateCar(updateReqVOList);
//        return success(true);
//    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除车辆档案列表")
    @PreAuthorize("@ss.hasPermission('information:car:batch-delete')")
    public CommonResult<Boolean> batchDeleteCar(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        carService.batchDeleteCar(body.get("ids"));
        return success(true);
    }

    @PostMapping("/batch-import")
    @Operation(summary = "批量新增导入车辆档案列表")
    @PreAuthorize("@ss.hasPermission('information:car:batch-import')")
    public CommonResult<Boolean> batchImportCar(@RequestBody List<CarDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        carService.batchImportCar(importReqVOList);
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载车辆档案导入模板")
    @PreAuthorize("@ss.hasPermission('information:car:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<DownCarloadImportVO> dataExample = new ArrayList<>();
        // 必填字段
        List<String> requiredFields = Arrays.asList(
                "companyName", "mainVehicleName", "trailerName",
                "fleetName", "captainName", "mainName","commodityNames",
                "deputyName", "escortName"
        );
        //日期格式示例
        List<String> exampleDateFields = Arrays.asList();
        // 输出
        ExcelUtils.write3(response, "车辆档案模板.xls", "车辆档案列表",
                DownCarloadImportVO.class,dataExample,requiredFields,exampleDateFields);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "车辆档案预览")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:car:import')")
    public CommonResult<List<CarImportExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
                                                                    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<CarImportExcelVO> list = ExcelUtils.readExcelAndRemoveAsterisk(file, CarImportExcelVO.class);
        return success(carService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入车辆档案")
    @Parameters({
            @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:car:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody CarImportExcelVO importDatas) throws Exception {
        ImportResult importResult = carService.importData(importDatas);
        return success(importResult);
    }

    @PostMapping("/page2")
    @Operation(summary = "用通用关键字查询获得车辆信息分页")
    @PreAuthorize("@ss.hasPermission('information:car:query2')")
    public CommonResult<Page<CarRespVO>> getCarKeywordPage(@Valid @RequestBody CarKeywordPageReqVO pageVO) {
        Page<CarRespVO> carVOPage = carService.selectPageByKeyword(pageVO);
        CommonResult<Page<CarRespVO>> result = success(carVOPage);
        return result;
    }

    @PostMapping("/carCyclePage")
    @Operation(summary = "用通用关键字查询获得车辆周期管理分页")
    @PreAuthorize("@ss.hasPermission('information:car:query3')")
    public CommonResult<CarCyclePage<CarCycleRespVO>> getCarCyclePage(@Valid @RequestBody CarCyclePageReqVO pageReqVO) {
//        CarCyclePage<CarCycleRespVO> carVOPage =carService.selectCarCyclePage(pageReqVO);
        CarCyclePage<CarCycleRespVO> carVOPage = carService.selectCarCyclePage(pageReqVO);
        CommonResult<CarCyclePage<CarCycleRespVO>> result = success(carVOPage);
        return result;
    }

    @GetMapping("/exportCarCycle")
    @Operation(summary = "导出车辆周期管理数据")
    @PreAuthorize("@ss.hasPermission('information:car:export')")
    @OperateLog(type = EXPORT)
    public void exportCarCycle(@Valid CarCyclePageReqVO pageReqVO,
                               HttpServletResponse response) throws IOException {
        List<CarCycleRespVO> carCycleRespVOS = carService.exportCarCycle(pageReqVO);
        ExcelUtils.write(response, "车辆周期.xls", "数据",
                CarCycleRespVO.class, carCycleRespVOS,pageReqVO.getExportFileds());
    }

    @PostMapping("/createProcessInstance")
    @Operation(summary = "车头或车挂的注销/报废")
    @PreAuthorize("@ss.hasPermission('information:car:query3')")
    public CommonResult<String> createProcessInstance(@Valid @RequestBody CarProcessRespVO processRespVO) {
        String processInstance = carService.createProcessInstance(processRespVO);
        return success(processInstance);
    }

    @GetMapping("/listAllBasicCar")
    @Operation(summary = "获取车辆精简信息列表")
    public CommonResult<List<CarBasicRespVO>> getBasicCarList(@Valid CommonInformationQueryParam param) {
        // 获货物列表，只要开启状态的
        List<DictDataRespDTO> vehicleBrand = dictDataApi.getDictDatas("vehicle_brand");
        List<CarBasicRespVO> list = carService.getAllCarList(param);
        for (CarBasicRespVO carBasicRespVO : list) {
            DictDataRespDTO vehicleBrandDict = vehicleBrand
                    .stream()
                    .filter(dictDataRespDTO -> dictDataRespDTO.getValue().equals(carBasicRespVO.getBrand()))
                    .findFirst().orElse(null);
            if (vehicleBrandDict != null) {
                carBasicRespVO.setBrand(vehicleBrandDict.getLabel());
            }
        }
        // 排序后，返回给前端
        return success(list);
    }

    @GetMapping("/selectCarChangeByVehicleId")
    @Operation(summary = "查询更换的记录信息 ")
    public CommonResult<ClientSettingsPage<CarChangeRespDTO>> selectCarChangeByVehicleId(@Valid CarPageReqVO pageReqVO) {
        ClientSettingsPage<CarChangeRespDTO> carChangeRespDTO = carService.selectCarChangeByVehicleId(pageReqVO);
        return success(carChangeRespDTO);
    }

    @PostMapping("/carScrapOrCancelProcess")
    @Operation(summary = "车头、车挂报废/注销流程 ")
    public CommonResult<Void> carScrapOrCancelProcessApproval(@Valid @RequestBody CarScrapOrCancelProcessReqDTO reqDTO) {
        carService.carScrapOrCancelProcessApproval(reqDTO);
        return success(null);
    }
}
