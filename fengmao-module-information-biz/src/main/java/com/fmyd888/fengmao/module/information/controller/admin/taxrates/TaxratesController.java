package com.fmyd888.fengmao.module.information.controller.admin.taxrates;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.TrailerRespVO;
import com.fmyd888.fengmao.module.information.convert.taxrates.TaxratesConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDO;
import com.fmyd888.fengmao.module.information.service.taxrates.TaxratesDeptService;
import com.fmyd888.fengmao.module.information.service.taxrates.TaxratesService;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import com.fmyd888.fengmao.module.system.convert.dept.DeptConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 税率")
@RestController
@RequestMapping("/information/taxrates")
@Validated
@Slf4j
public class TaxratesController {

    @Resource
    private TaxratesService taxratesService;
    @Autowired
    private DeptService deptService;
    @Resource
    private TaxratesDeptService taxratesDeptService;


    @PostMapping("/create")
    @Operation(summary = "创建税率")
    @PreAuthorize("@ss.hasPermission('information:taxrates:create')")
    public CommonResult<Long> createTaxrates(@Valid @RequestBody TaxratesCreateReqVO createReqVO) {
        return success(taxratesService.createTaxrates(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新税率")
    @PreAuthorize("@ss.hasPermission('information:taxrates:update')")
    public CommonResult<Boolean> updateTaxrates(@Valid @RequestBody TaxratesUpdateReqVO updateReqVO) {
        taxratesService.updateTaxrates(updateReqVO);
        return success(true);
    }

    @GetMapping("/updateTaxratesStatus")
    @Operation(summary = "更新税率状态")
    public CommonResult<Void> updateTaxratesStatus(@Param("id") Long id) {
        taxratesService.updateStatus(id);
        return success(null);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除税率")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:taxrates:delete')")
    public CommonResult<Boolean> deleteTaxrates(@RequestParam("id") Long id) {
        taxratesService.deleteTaxrates(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得税率")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('information:taxrates:query')")
    public CommonResult<TaxratesRespVO> getTaxrates(@RequestParam("id") Long id) {
        TaxratesRespVO result = taxratesService.getTaxrates(id);
        return success(result);
    }

    @GetMapping("/list")
    @Operation(summary = "获得税率列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:taxrates:query')")
    public CommonResult<List<TaxratesRespVO>> getTaxratesList(@RequestParam("ids") Collection<Long> ids) {
        List<TaxratesRespVO> list = taxratesService.getTaxratesList(ids);
        CommonResult<List<TaxratesRespVO>> result = success(list);
        return result;
    }

    @GetMapping("/page")
    @Operation(summary = "获得税率分页")
    @PreAuthorize("@ss.hasPermission('information:taxrates:query')")
    public CommonResult<PageResult<TaxratesRespVO>> getTaxratesPage(@Valid TaxratesPageReqVO pageVO) {
        PageResult<TaxratesRespVO> pageResult = taxratesService.getTaxratesPage(pageVO);
        CommonResult<PageResult<TaxratesRespVO>> result = success(pageResult);
        return result;
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出税率 Excel")
    @PreAuthorize("@ss.hasPermission('information:taxrates:export')")
    @OperateLog(type = EXPORT)
    public void exportTaxratesExcel(@Valid TaxratesExportReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        List<TaxratesDO> list = taxratesService.getTaxratesList(exportReqVO);
        // 导出 Excel
        List<TaxratesExcelVO> datas = BeanUtils.toBean(list, TaxratesExcelVO.class);
        List<String> exportFileds = new ArrayList<>();
        for (String item : exportReqVO.getExportFileds()) {
            exportFileds.add(item.replace("organization", "companyName"));
        }
        ExcelUtils.write(response, "税率.xls", "数据", TaxratesExcelVO.class, datas, exportFileds);
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新税率列表")
    @PreAuthorize("@ss.hasPermission('information:taxrates:batch-update')")
    public CommonResult<Boolean> batchUpdateTaxrates(@Valid @RequestBody List<TaxratesUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        taxratesService.batchUpdateTaxrates(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除税率列表")
    @PreAuthorize("@ss.hasPermission('information:taxrates:batch-delete')")
    public CommonResult<Boolean> batchDeleteTaxrates(@RequestBody Map<String, List<Integer>> body) {
        // 在这里处理批量删除逻辑
        taxratesService.batchDeleteTaxrates(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入税率模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<TaxratesImportExcelVO> exampleData = Arrays.asList(
                TaxratesImportExcelVO.builder()
                        .taxName("税率名称")
                        .taxRate(new BigDecimal("0.04"))
                        .remark("税率备注").build()
        );

        // 输出
        ExcelUtils.write(response, "税率导入模板.xls", "税率列表", TaxratesImportExcelVO.class, exampleData);
    }

    @PostMapping("/batch-import")
    @Operation(summary = "批量新增导入税率列表")
    @PreAuthorize("@ss.hasPermission('information:taxrates:batch-import')")
    public CommonResult<Boolean> batchImportTaxrates(@RequestBody List<TaxratesDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        taxratesService.batchImportTaxrates(importReqVOList);
        return success(true);
    }

    @PostMapping("/getTaxratesDeptByTaxratesId")
    @PreAuthorize("@ss.hasPermission('information:taxrates:selectTaxratesDeptByTaxratesId')")
    @Operation(summary = "根据税率id查询已分配的部门组织列表")
    public CommonResult<List<DeptRespVO>> getTaxratesDeptByTaxratesId(Long taxratesId) {
        log.info("getTaxratesDeptByTaxratesId将用户添分配到组织下，入参：{}", taxratesId);
        Set<Long> deptIdSet = taxratesDeptService.findDeptIdsByEntityId(taxratesId);
        List<Long> deptIdList = new ArrayList<>(deptIdSet);
        List<DeptDO> deptList = deptService.getDeptList(deptIdList);
        List<DeptRespVO> deptRespList = DeptConvert.INSTANCE.convertList(deptList);
        return success(deptRespList);
    }

    @PostMapping("/assignToDept")
    @Operation(summary = "将税率分配到组织下")
    @PreAuthorize("@ss.hasPermission('information:taxrates:assignToDept')")
    public CommonResult<Void> assignToDept(@Valid @RequestBody TaxratesDeptReqVO taxratesDeptReqVO) {
        log.info("getDeptInfo根据税率id获得税率所属的部门组织信息,入参:{}", taxratesDeptReqVO);
        taxratesService.assignTaxratesToDept(taxratesDeptReqVO);
        return success(null);
    }

    @GetMapping("/getSimpleList")
    @Operation(summary = "获得税率精简信息")
    @Parameters({
            @Parameter(name = "searchKey", description = "关键字", required = false),
            @Parameter(name = "id", description = "单位id", required = false),
            @Parameter(name = "status", description = "状态", required = false)
    })
    public CommonResult<List<HashMap<String, Object>>> getSimpleStoreList(@Valid CommonQueryParam param) {
        List<HashMap<String, Object>> list = taxratesService.getSimpleStoreList(param);
        return success(list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "税率导入预览")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:taxrates:import')")
    public CommonResult<List<TaxratesExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
                                                             @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<TaxratesExcelVO> list = ExcelUtils.read(file, TaxratesExcelVO.class);
        return success(taxratesService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入税率")
    @Parameters({
            @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:taxrates:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody TaxratesExcelVO importReqVo) throws Exception {
        ImportResult result = taxratesService.importData(importReqVo);
        return success(result);
    }
}
