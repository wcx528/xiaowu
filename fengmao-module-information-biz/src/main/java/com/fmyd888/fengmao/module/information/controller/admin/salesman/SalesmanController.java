package com.fmyd888.fengmao.module.information.controller.admin.salesman;

import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.salesman.vo.*;
import com.fmyd888.fengmao.module.information.convert.salesman.SalesmanConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDO;
import com.fmyd888.fengmao.module.information.service.salesman.SalesmanDeptService;
import com.fmyd888.fengmao.module.information.service.salesman.SalesmanService;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import com.fmyd888.fengmao.module.system.convert.dept.DeptConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
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
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 业务员表 ")
@Slf4j
@RestController
@RequestMapping("/information/salesman")
@Validated
public class SalesmanController {

    @Resource
    private SalesmanService salesmanService;
    @Resource
    private SalesmanDeptService salesmanDeptService;
    @Resource
    private DeptService deptService;

    @PostMapping("/create")
    @Operation(summary = "创建业务员表 ")
    @PreAuthorize("@ss.hasPermission('information:salesman:create')")
    public CommonResult<Long> createSalesman(@Valid @RequestBody SalesmanCreateReqVO createReqVO) {
        log.info("createSalesman创建业务员表 入参：{}", createReqVO);
        CommonResult<Long> result = success(salesmanService.createSalesman(createReqVO));
        return result;
    }

    @PutMapping("/update")
    @Operation(summary = "更新业务员表 ")
    @PreAuthorize("@ss.hasPermission('information:salesman:update')")
    public CommonResult<Boolean> updateSalesman(@Valid @RequestBody SalesmanUpdateReqVO updateReqVO) {
        log.info("updateSalesman更新业务员表 入参：{}", updateReqVO);
        salesmanService.updateSalesman(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除业务员表 ")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:salesman:delete')")
    public CommonResult<Boolean> deleteSalesman(@RequestParam("id") Long id) {
        log.info("deleteSalesman删除业务员表 入参：{}", id);
        salesmanService.deleteSalesman(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得业务员表 ")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:salesman:query')")
    public CommonResult<SalesmanRespVO> getSalesman(@RequestParam("id") Long id) {
        SalesmanRespVO salesman = salesmanService.getSalesman(id);
        CommonResult<SalesmanRespVO> result = success(salesman);
        return result;
    }

    @GetMapping("/list")
    @Operation(summary = "获得业务员表 列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:salesman:query')")
    public CommonResult<List<SalesmanRespVO>> getSalesmanList(@RequestParam("ids") Collection<Long> ids) {
        log.info("getSalesmanList获得业务员表 列表入参：{}", ids);
        List<SalesmanRespVO> list = salesmanService.getSalesmanList(ids);
        CommonResult<List<SalesmanRespVO>> result = success(list);
        log.info("getSalesmanList获得业务员表 列表返回：{}", result);
        return result;
    }

    @GetMapping("/page")
    @Operation(summary = "获得业务员表 分页")
    @PreAuthorize("@ss.hasPermission('information:salesman:query')")
    public CommonResult<PageResult<SalesmanRespVO>> getSalesmanPage(@Valid SalesmanPageReqVO pageVO) {
        PageResult<SalesmanRespVO> pageResult = salesmanService.getSalesmanPage(pageVO);
        CommonResult<PageResult<SalesmanRespVO>> result = success(pageResult);
        return result;
    }

    @GetMapping("/updateSalesmanStatus")
    @Operation(summary = "更新业务员状态")
    public CommonResult<Void> updateStatus(@Param("id") Long id) {
        salesmanService.updateStatus(id);
        return success(null);

    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出业务员表  Excel")
    @PreAuthorize("@ss.hasPermission('information:salesman:export')")
    @OperateLog(type = EXPORT)
    public void exportSalesmanExcel(@Valid SalesmanExportReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        List<SalesmanExcelVO> datas = salesmanService.getSalesmanList(exportReqVO);
        ExcelUtils.write(response, "业务员表 .xls", "数据", SalesmanExcelVO.class, datas, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新业务员表 列表")
    @PreAuthorize("@ss.hasPermission('information:salesman:batch-update')")
    public CommonResult<Boolean> batchUpdateSalesman(@Valid @RequestBody List<SalesmanUpdateReqVO> updateReqVOList) {
        log.info("batchUpdateSalesman批量更新业务员表 入参：{}", updateReqVOList);
        // 在这里处理批量更新逻辑
        salesmanService.batchUpdateSalesman(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除业务员表 列表")
    @PreAuthorize("@ss.hasPermission('information:salesman:batch-delete')")
    public CommonResult<Boolean> batchDeleteSalesman(@RequestBody Map<String, List<Long>> body) {
        log.info("batchDeleteSalesman批量删除业务员表 列表入参：{}", body);
        // 在这里处理批量删除逻辑
        salesmanService.batchDeleteSalesman(body.get("ids"));
        return success(true);
    }


    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入业务员模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<SalesmanImportExcelVO> list = Arrays.asList(
                SalesmanImportExcelVO.builder().username("fenmao").salesmanType("销售员/采购员").positionName("业务主管/硫酸主管/业务员").build());

        // 输出
        ExcelUtils.write(response, "员工导入模板.xls", "员工列表", SalesmanImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入员工")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为false", example = "ture"),
    })
    @PreAuthorize("@ss.hasPermission('system:salesman:import')")
    public CommonResult<SalesmanImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                          @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<SalesmanImportExcelVO> list = ExcelUtils.read(file, SalesmanImportExcelVO.class);
        return success(salesmanService.importSalesmanList(list, updateSupport));

    }

    @GetMapping("/getNameList")
    @Operation(summary = "获得业务员name详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<List<SalesmanRespVO>> getNameList() {
        List<SalesmanDO> salesmanDOList = salesmanService.getNameListById();
        List<SalesmanRespVO> measurementRespVOS = salesmanDOList.stream()
                .map(SalesmanConvert.INSTANCE::convert)
                .collect(Collectors.toList());
        return CommonResult.success(measurementRespVOS);
    }


    @PostMapping("/getSalesmanDeptBySalesmanId")
    @Operation(summary = "根据id查询已分配的部门组织列表")
    public CommonResult<List<DeptRespVO>> getSalesmanDeptBySalesmanId(Long salesmanId) {
        log.info("getSalesmanDeptBySalesmanId将业务员分配组织，入参{}", salesmanId);
        Set<Long> deptIds = salesmanDeptService.findDeptIdsByEntityId(salesmanId);
        List<DeptDO> deptDOList1 = deptService.getDeptList(deptIds);
        List<DeptRespVO> deptRespList = DeptConvert.INSTANCE.convertList(deptDOList1);
        return success(deptRespList);
    }

    @PostMapping("/assignToDept")
    @Operation(summary = "将业务员分配到组织下")
    public CommonResult<Void> assignToDept(@Valid @RequestBody SalesmanDeptReqVO salesmanDeptReqVO) {
        log.info("getDeptInfo根据业务员id获得所属部门信息，入参{}", salesmanDeptReqVO);
        salesmanService.assignSalesmanToDept(salesmanDeptReqVO);
        return success(null);

    }

    @GetMapping("/getSimpleSalesmanList")
    @Operation(summary = "获得精简业务员信息")
    public CommonResult<List<HashMap<String, Object>>> getSimpleSalesmanList(CommonQueryParam param) {
        List<HashMap<String, Object>> list = salesmanService.getSimpleSalesmanList(param.getSearchKey(),param.getId(),param.getStatus(),param.getType(),param.getCompanyIds());
        return success(list);
    }

    @GetMapping("/listExcludeUser")
    @Operation(summary = "获取未绑定业务员的用户精简信息列表")
    public CommonResult<List<HashMap<String, Object>>> getExcludeUserList() {
        // 获货物列表，只要开启状态的
        List<HashMap<String, Object>> excludeUserList = salesmanService.getExcludeUserList();
        // 排序后，返回给前端
        return success(excludeUserList);
    }

}