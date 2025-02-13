package com.fmyd888.fengmao.module.information.controller.admin.currency;

import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyImportRespVO;
import com.fmyd888.fengmao.module.information.convert.currency.CurrencyConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDO;
import com.fmyd888.fengmao.module.information.service.currency.CurrencyService;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

import com.fmyd888.fengmao.module.information.controller.admin.currency.CurrencyDeptReqVO;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 货币")
@RestController
@RequestMapping("/information/currency")
@Validated
@Slf4j
public class CurrencyController {

    @Resource
    private CurrencyService currencyService;

    @PostMapping("/create")
    @Operation(summary = "创建货币")
    @PreAuthorize("@ss.hasPermission('information:currency:create')")
    public CommonResult<Long> createCurrency(@Valid @RequestBody CurrencyCreateReqVO createReqVO) {
        Long currencyId = currencyService.createCurrency(createReqVO);
        return success(currencyId);
    }

    @PutMapping("/update")
    @Operation(summary = "更新货币")
    @PreAuthorize("@ss.hasPermission('information:currency:update')")
    public CommonResult<Boolean> updateCurrency(@Valid @RequestBody CurrencyUpdateReqVO updateReqVO) {
        currencyService.updateCurrency(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除货币")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:currency:delete')")
    public CommonResult<Boolean> deleteCurrency(@RequestParam("id") Long id) {
        currencyService.deleteCurrency(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得货币")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:currency:query')")
    public CommonResult<CurrencyRespVO> getCurrency(@RequestParam("id") Long id) {
        CurrencyRespVO currency = currencyService.getCurrency(id);
        return success(currency);
    }

    @GetMapping("/list")
    @Operation(summary = "获得货币列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:currency:query')")
    public CommonResult<List<CurrencyRespVO>> getCurrencyList(@RequestParam("ids") Collection<Long> ids) {
        List<CurrencyRespVO> list = currencyService.getCurrencyList(ids);
        return success(list);
    }

    @GetMapping("/page")
    @Operation(summary = "获得货币分页")
    @PreAuthorize("@ss.hasPermission('information:currency:query')")
    public CommonResult<PageResult<CurrencyRespVO>> getCurrencyPage(@Valid CurrencyPageReqVO pageVO) {
        PageResult<CurrencyRespVO> pageResult = currencyService.getCurrencyPage(pageVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出货币 Excel")
    @PreAuthorize("@ss.hasPermission('information:currency:export')")
    @OperateLog(type = EXPORT)
    public void exportCurrencyExcel(@Valid CurrencyExportReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        List<CurrencyExcelVO> list = currencyService.getCurrencyList(exportReqVO);
        ExcelUtils.write(response, "货币.xls", "数据", CurrencyExcelVO.class, list, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新货币列表")
    @PreAuthorize("@ss.hasPermission('information:currency:batch-update')")
    public CommonResult<Boolean> batchUpdateCurrency(@Valid @RequestBody List<CurrencyUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        currencyService.batchUpdateCurrency(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除货币列表")
    @PreAuthorize("@ss.hasPermission('information:currency:batch-delete')")
    public CommonResult<Boolean> batchDeleteCurrency(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        currencyService.batchDeleteCurrency(body.get("ids"));
        return success(true);
    }

    @PostMapping("/batch-import")
    @Operation(summary = "批量新增导入货币列表")
    @PreAuthorize("@ss.hasPermission('information:currency:batch-import')")
    public CommonResult<Boolean> batchImportCurrency(@RequestBody List<CurrencyDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        currencyService.batchImportCurrency(importReqVOList);
        return success(true);
    }

    @GetMapping("/getDeptInfo")
    @Operation(summary = "根据货币id获得货币所属的部门组织信息")
    public CommonResult<List<DeptSimpleRespVO>> getDeptInfo(Long currencyId) {
        log.info("getDeptInfo根据货币id获得货币所属的部门组织信息,入参:{}", currencyId);
        List<DeptSimpleRespVO> deptInfoList = currencyService.getDeptInfoList(currencyId);
        log.info("getDeptInfo根据货币id获得货币所属的部门组织信息,返回:{}", deptInfoList);
        return success(deptInfoList);
    }

    @PostMapping("/assignToDept")
    @Operation(summary = "将货币分配到组织下")
    public CommonResult<Void> assignToDept(@Valid @RequestBody CurrencyDeptReqVO currencyDeptReqVO) {
        log.info("getDeptInfo根据货币id获得货币所属的部门组织信息,入参:{}", currencyDeptReqVO);
        currencyService.assignCurrencyToDept(currencyDeptReqVO);
        return success(null);
    }

    @GetMapping("/updateCurrencyStatus")
    @Operation(summary = "更新货币状态")
    public CommonResult<Void> updateCurrencyStatus(@Param("id") Long id) {
        currencyService.updateStatus(id);
        return success(null);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入货币模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<CurrencyImportExcelVO> exampleData = Arrays.asList(
                CurrencyImportExcelVO.builder()
                        .currencyName("测试币")
                        .currencyIdentify("RMB")
                        .currencySymbol("￥")
                        .currencyCode("HB001")
                        .remark("货币001号备注").build()
        );

        // 输出
        ExcelUtils.write(response, "货币导入模板.xls", "货币列表", CurrencyImportExcelVO.class, exampleData);
    }

    @PostMapping("/import")
    @Operation(summary = "导入货币")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:currency:import')")
    public CommonResult<CurrencyImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                          @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<CurrencyImportExcelVO> list = ExcelUtils.read(file, CurrencyImportExcelVO.class);
        CurrencyImportRespVO currencyImportRespVO = currencyService.importCurrencyList(list, updateSupport);
        return success(currencyImportRespVO);
    }

    @GetMapping("/getSimpleCurrencyList")
    @Operation(summary = "获得精简货币信息")
    public CommonResult<List<HashMap<String, Object>>> getSimpleCurrencyList(@Valid CommonQueryParam param) {
        List<HashMap<String, Object>> simpleCurrencyList = currencyService.getSimpleCurrencyList(param);
        return success(simpleCurrencyList);
    }

}
