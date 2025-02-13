package com.fmyd888.fengmao.module.information.controller.admin.baseline;

import com.fmyd888.fengmao.framework.common.pojo.*;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.route.TransRouteReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportCarRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineMediumDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineMediumTypeDO;
import com.fmyd888.fengmao.module.information.service.baseline.BaselineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 基线")
@RestController
@RequestMapping("/information/baseline")
@Validated
public class BaselineController {

    @Resource
    private BaselineService baselineService;

    @PostMapping("/create")
    @Operation(summary = "创建基线")
    @PreAuthorize("@ss.hasPermission('information:baseline:create')")
    public CommonResult<Long> createBaseline(@Valid @RequestBody BaselineSaveReqVO createReqVO) {
        return success(baselineService.createBaseline(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新基线")
    @PreAuthorize("@ss.hasPermission('information:baseline:update')")
    public CommonResult<Boolean> updateBaseline(@Valid @RequestBody BaselineSaveReqVO updateReqVO) {
        baselineService.updateBaseline(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除基线")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:baseline:delete')")
    public CommonResult<Boolean> deleteBaseline(@RequestParam("id") Long id) {
        baselineService.deleteBaseline(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得基线")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:baseline:query')")
    public CommonResult<BaselineRespVO> getBaseline(@RequestParam("id") Long id) {
        BaselineRespVO baseline = baselineService.getBaseline(id);
        return success(baseline);
    }

    @GetMapping("/list")
    @Operation(summary = "获得基线列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:baseline:query')")
    public CommonResult<List<BaselineRespVO>> getBaselineList(@RequestParam("ids") Collection<Long> ids) {
        List<BaselineDO> list = baselineService.getBaselineList(ids);
        return success(BeanUtils.toBean(list, BaselineRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得基线分页")
    @PreAuthorize("@ss.hasPermission('information:baseline:query')")
    public CommonResult<PageResult<BaselineRespVO>> getBaselinePage(@Valid BaselinePageReqVO pageReqVO) {
        PageResult<BaselineRespVO> pageResult = baselineService.getBaselinePage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出基线 Excel")
    @PreAuthorize("@ss.hasPermission('information:baseline:export')")
    @OperateLog(type = EXPORT)
    public void exportBaselineExcel(@Valid BaselineListReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        List<BaselineExcelVO> list = baselineService.getBaselineList(exportReqVO);
        ExcelUtils.write(response, "基线.xls", "数据", BaselineExcelVO.class, list, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新基线列表")
    @PreAuthorize("@ss.hasPermission('information:baseline:batch-update')")
    public CommonResult<Boolean> batchUpdateBaseline(@Valid @RequestBody List<BaselineSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        baselineService.batchUpdateBaseline(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除基线列表")
    @PreAuthorize("@ss.hasPermission('information:baseline:batch-delete')")
    public CommonResult<Boolean> batchDeleteBaseline(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        baselineService.batchDeleteBaseline(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载基线导入模板")
    @PreAuthorize("@ss.hasPermission('information:baseline:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<BaselineExcelVO> list = Arrays.asList();

        // 输出
        ExcelUtils.write(response, "基线模板.xls", "基线列表", BaselineExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "基线预览")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:baseline:import')")
    public CommonResult<List<BaselineExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
                                                             @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<BaselineExcelVO> list = ExcelUtils.read(file, BaselineExcelVO.class);
        return success(baselineService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入基线")
    @Parameters({
            @Parameter(name = "file", description = "导入的excel文件", required = true)
    })
    @PreAuthorize("@ss.hasPermission('information:baseline:import')")
    public CommonResult<ImportResult<BaselineExcelVO>> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        // 将Excel文件转换为BaselineExcelVO对象列表
        List<BaselineExcelVO> importReqVo = convertExcelToBaselineExcelVO(file);

        // 调用服务层方法处理导入逻辑
        ImportResult<BaselineExcelVO> result = baselineService.importData(importReqVo);
        return CommonResult.success(result);
    }

    private List<BaselineExcelVO> convertExcelToBaselineExcelVO(MultipartFile file) throws Exception {
        List<BaselineExcelVO> importReqVo = new ArrayList<>();

//        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
//            Sheet sheet = workbook.getSheetAt(0);
//            List<BaselineExcelVO> importDatas = new ArrayList<>();
//            for (Row row : sheet) {
//                // 跳过表头
//                if (row.getRowNum() == 0) {
//                    continue;
//                }
//
//                BaselineExcelVO vo = new BaselineExcelVO();
//                vo.setDeptId((long) row.getCell(0).getNumericCellValue());
//                vo.setStartTime(row.getCell(1).getLocalDateTimeCellValue());
//                vo.setEndTime(row.getCell(2).getLocalDateTimeCellValue());
//                vo.setLoadingManufacturerId((long) row.getCell(3).getNumericCellValue());
//                vo.setUnloadingManufacturerId((long) row.getCell(4).getNumericCellValue());
//                vo.setLoadingAddressId((long) row.getCell(5).getNumericCellValue());
//                vo.setUnloadingAddressId((long) row.getCell(6).getNumericCellValue());
//                vo.setRouteType((int) row.getCell(7).getNumericCellValue());
//                vo.setShippingMileage((int) row.getCell(8).getNumericCellValue());
//                vo.setTransportRoutes(row.getCell(9).getStringCellValue());
//                vo.setMeasurementId((long) row.getCell(10).getNumericCellValue());
//                vo.setCalculationRoute((int) row.getCell(11).getNumericCellValue());
//                vo.setWagesRoute(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));
//                vo.setSettlementId((long) row.getCell(13).getNumericCellValue());
//                vo.setCreateTime(row.getCell(14).getLocalDateTimeCellValue());
//                vo.setCompanyId((long) row.getCell(15).getNumericCellValue());
//                vo.setCalculationFuel((int) row.getCell(16).getNumericCellValue());
//                vo.setFareRate(BigDecimal.valueOf(row.getCell(17).getNumericCellValue()));
//                vo.setTollStart(BigDecimal.valueOf(row.getCell(18).getNumericCellValue()));
//                vo.setTollEnd(BigDecimal.valueOf(row.getCell(19).getNumericCellValue()));
//
//                importDatas.add(vo);
//            }
//
//            BaselineExcelVO reqVo = new BaselineExcelVO();
//            reqVo.setImportDatas(importDatas);
//            importReqVo.add(reqVo);
//        }

        return importReqVo;
    }

    @GetMapping("/route")
    @Operation(summary = "获取路线信息")
    public CommonResult<List<Map<String, Object>>> getRouteInfo(@Valid TransRouteReqVO reqVO) {
        List<Map<String, Object>> result = baselineService.getRouteInfo(reqVO);
        return success(result);
    }

    @GetMapping("/getFuelConsStandardInfo")
    @Operation(summary = "根据所属公司和结算方查询运价和计量单位")
    public CommonResult<HashMap<String, Object>> getFuelConsStandardInfo(Long companyId, Long settlementId) {
        HashMap<String, Object> fuelConsStandardInfo = baselineService.getFuelConsStandardInfo(companyId, settlementId);
        return success(fuelConsStandardInfo);
    }

// ==================== 子表（基线与运输介质关系） ====================

    @GetMapping("/baseline-medium/page")
    @Operation(summary = "获得基线与运输介质关系分页")
    @Parameter(name = "entityId", description = "货物ID")
    @PreAuthorize("@ss.hasPermission('information:baseline:query')")
    public CommonResult<PageResult<BaselineMediumDO>> getBaselineMediumPage(PageParam pageReqVO, @RequestParam("entityId") Long entityId) {
        //return success(baselineMediumService.getBaselineMediumPage(pageReqVO, entityId));
        return null;
    }

    @GetMapping("/baseline-medium/list-by-entity-id")
    @Operation(summary = "获得基线与运输介质关系列表")
    @Parameter(name = "entityId", description = "货物ID")
    @PreAuthorize("@ss.hasPermission('information:baseline:query')")
    public CommonResult<List<BaselineMediumDO>> getBaselineMediumListByEntityId(@RequestParam("entityId") Long entityId) {
        //return success(baselineService.getBaselineMediumListByEntityId(entityId));
        return null;
    }

// ==================== 子表（基线与运输类型关系） ====================

    @GetMapping("/baseline-mediumtype/page")
    @Operation(summary = "获得基线与运输类型关系分页")
    @Parameter(name = "entityId", description = "货物ID")
    @PreAuthorize("@ss.hasPermission('information:baseline:query')")
    public CommonResult<PageResult<BaselineMediumTypeDO>> getBaselineMediumtypePage(PageParam pageReqVO,
                                                                                    @RequestParam("entityId") Long entityId){
        //return success(baselineService.getBaselineMediumtypePage(pageReqVO, entityId));
        return null;
    }

    @GetMapping("/baseline-mediumtype/list-by-entity-id")
    @Operation(summary = "获得基线与运输类型关系列表")
    @Parameter(name = "entityId", description = "货物ID")
    @PreAuthorize("@ss.hasPermission('information:baseline:query')")
    public CommonResult<List<BaselineMediumTypeDO>> getBaselineMediumtypeListByEntityId(@RequestParam("entityId") Long entityId) {
        //return success(baselineService.getBaselineMediumtypeListByEntityId(entityId));
        return null;
    }

}
