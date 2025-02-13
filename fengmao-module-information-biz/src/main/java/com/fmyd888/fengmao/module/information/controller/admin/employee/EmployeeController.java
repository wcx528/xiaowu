package com.fmyd888.fengmao.module.information.controller.admin.employee;


import com.fmyd888.fengmao.framework.common.enums.CommonStatusEnum;
import com.fmyd888.fengmao.module.system.controller.admin.user.vo.user.UserBasicRespVO;
import com.fmyd888.fengmao.module.system.convert.user.UserConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;

import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.*;
import static com.fmyd888.fengmao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.employee.EmployeeDO;
import com.fmyd888.fengmao.module.information.convert.employee.EmployeeConvert;
import com.fmyd888.fengmao.module.information.service.employee.EmployeeService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 员工信息表")
@RestController
@RequestMapping("/information/employee")
@Validated
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @PostMapping("/create")
    @Operation(summary = "创建员工信息表")
    @PreAuthorize("@ss.hasPermission('information:employee:create')")
    public CommonResult<Long> createEmployee(@Valid @RequestBody EmployeeCreateReqVO createReqVO) {
        return success(employeeService.createEmployee(getLoginUserId(),createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新员工信息表")
    @PreAuthorize("@ss.hasPermission('information:employee:update')")
    public CommonResult<Boolean> updateEmployee(@Valid @RequestBody EmployeeUpdateReqVO updateReqVO) {
        employeeService.updateEmployee(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除员工信息表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:employee:delete')")
    public CommonResult<Boolean> deleteEmployee(@RequestParam("id") Long id) {
        employeeService.deleteEmployee(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得员工信息表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:employee:query')")
    public CommonResult<EmployeeRespVO> getEmployee(@RequestParam("id") Long id) {
        EmployeeDO employee = employeeService.getEmployee(id);
        return success(EmployeeConvert.INSTANCE.convert(employee));
    }

    @GetMapping("/list")
    @Operation(summary = "获得员工信息表列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:employee:query')")
    public CommonResult<List<EmployeeRespVO>> getEmployeeList(@RequestParam("ids") Collection<Long> ids) {
        List<EmployeeDO> list = employeeService.getEmployeeList(ids);
        return success(EmployeeConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得员工信息表分页")
    @PreAuthorize("@ss.hasPermission('information:employee:query')")
    public CommonResult<PageResult<EmployeeRespVO>> getEmployeePage(@Valid EmployeePageReqVO pageVO) {
        PageResult<EmployeeDO> pageResult = employeeService.getEmployeePage(pageVO);
        return success(EmployeeConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出员工信息表 Excel")
    @PreAuthorize("@ss.hasPermission('information:employee:export')")
    @OperateLog(type = EXPORT)
    public void exportEmployeeExcel(@Valid EmployeeExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<EmployeeDO> list = employeeService.getEmployeeList(exportReqVO);
        // 导出 Excel
        List<EmployeeExcelVO> datas = EmployeeConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "员工信息表.xls", "数据", EmployeeExcelVO.class, datas,exportReqVO.getExportFileds());
    }

    @GetMapping("/queryName")
    @Operation(summary = "员工姓名身份ID ")
    @PreAuthorize("@ss.hasPermission('information:employee:queryName')")
    public CommonResult<List<EmployeeRespVO>> EmployeeNameListById() {
        List<EmployeeDO> employeeDOList = employeeService.getEmployeeNameListById();

        List<EmployeeRespVO> employeeRespVOList = employeeDOList.stream()
                .map(EmployeeConvert.INSTANCE::convert)
                .collect(Collectors.toList());
        return CommonResult.success(employeeRespVOList);
    }
    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入员工模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<EmployeeImportExcelVO> list = Arrays.asList(
                EmployeeImportExcelVO.builder().name("fenmao").email("fenmao@fm.com").employeeId("15601691300").build());

        // 输出
        ExcelUtils.write(response, "员工导入模板.xls", "员工列表", EmployeeImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入员工")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('system:employee:import')")
    public CommonResult<EmployeeImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<EmployeeImportExcelVO> list = ExcelUtils.read(file, EmployeeImportExcelVO.class);
        return success(employeeService.importEmployeeList(list, updateSupport));
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除其他合同资料列表")
    @PreAuthorize("@ss.hasPermission('information:employee:batch-delete')")
    public CommonResult<Boolean> batchDeleteContract(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        employeeService.batchDeleteContract(body.get("ids"));
        return success(true);
    }
    @GetMapping("/listAllBasicEmployee")
    @Operation(summary = "获取员工精简信息列表", description = "只包含被开启的员工，主要用于前端的下拉选项")
    public CommonResult<List<EmployeeBasicRespVO>> getBasicEmployeeList() {
        // 获员工列表，只要开启状态的
        List<EmployeeDO> list = employeeService.getEmployeeListByStatus(CommonStatusEnum.ENABLE.getStatus());
        // 排序后，返回给前端
        return success(EmployeeConvert.INSTANCE.convertList05(list));
    }
    @GetMapping("/getBasicEmployeeById")
    @Operation(summary = "根据员工id查员工")
    public CommonResult<EmployeeBasicRespVO> getBasicEmployeeById(@Param("id") Long id) {
        // 获员工列表，只要开启状态的
        EmployeeBasicRespVO employee02 = employeeService.getEmployee02(id);
        // 排序后，返回给前端
        return success(employee02);
    }
}
