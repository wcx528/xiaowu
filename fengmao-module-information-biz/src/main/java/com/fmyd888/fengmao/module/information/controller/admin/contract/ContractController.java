package com.fmyd888.fengmao.module.information.controller.admin.contract;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.contract.vo.*;
import com.fmyd888.fengmao.module.information.convert.contract.ContractConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.contract.ContractDO;
import com.fmyd888.fengmao.module.information.service.contract.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 其他合同资料")
@Slf4j
@RestController
@RequestMapping("/information/contract")
@Validated
public class ContractController {

    @Resource
    private ContractService contractService;

    @PostMapping("/create")
    @Operation(summary = "创建其他合同资料")
    @PreAuthorize("@ss.hasPermission('information:contract:create')")
    public CommonResult<Long> createContract(@Valid @RequestBody ContractCreateReqVO createReqVO) {
        log.info("createContract创建其他合同资料入参：{}",createReqVO);
        CommonResult<Long> result = success(contractService.createContract(createReqVO));
        return result;
    }

    @PutMapping("/update")
    @Operation(summary = "更新其他合同资料")
    @PreAuthorize("@ss.hasPermission('information:contract:update')")
    public CommonResult<Boolean> updateContract(@Valid @RequestBody ContractUpdateReqVO updateReqVO) {
        log.info("updateContract更新其他合同资料入参：{}",updateReqVO);
        contractService.updateContract(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除其他合同资料")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:contract:delete')")
    public CommonResult<Boolean> deleteContract(@RequestParam("id") Long id) {
        log.info("deleteContract删除其他合同资料入参：{}",id);
        contractService.deleteContract(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得其他合同资料")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:contract:query')")
    public CommonResult<ContractRespVO> getContract(@RequestParam("id") Long id) {
        log.info("getContract获得其他合同资料入参：{}",id);
        ContractRespVO contract = contractService.getContract(id);
        log.info("getContract获得其他合同资料返回：{}",contract);
        return success(contract);
    }

    @GetMapping("/list")
    @Operation(summary = "获得其他合同资料列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:contract:query')")
    public CommonResult<List<ContractRespVO>> getContractList(@RequestParam("ids") Collection<Long> ids) {
        log.info("getContractList获得其他合同资料列表入参：{}",ids);
        List<ContractRespVO> list = contractService.getContractList(ids);
        CommonResult<List<ContractRespVO>> result = success((list));
        log.info("getContractList获得其他合同资料列表返回：{}",result);
        return result;
    }

    @GetMapping("/page")
    @Operation(summary = "获得其他合同资料分页")
    @PreAuthorize("@ss.hasPermission('information:contract:query')")
    public CommonResult<Page<ContractRespVO>> getContractPage(@Valid ContractPageReqVO pageVO) {
        log.info("getContractPage获得其他合同资料分页入参：{}",pageVO);
        Page<ContractRespVO> pageResult = contractService.getContractPage(pageVO);
        CommonResult<Page<ContractRespVO>> result = success(pageResult);
        log.info("getContractPage获得其他合同资料分页返回：{}",result);
        return result;
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出其他合同资料 Excel")
    @PreAuthorize("@ss.hasPermission('information:contract:export')")
    @OperateLog(type = EXPORT)
    public void exportContractExcel(@Valid ContractExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        log.info("exportContractExcel导出其他合同资料入参：{}",response);
        List<ContractDO> list = contractService.getContractList(exportReqVO);
        // 导出 Excel
        List<ContractExcelVO> datas = ContractConvert.INSTANCE.convertList02(list);
        log.info("exportContractExcel导出其他合同资料返回：{}",datas);
        ExcelUtils.write(response, "其他合同资料.xls", "数据", ContractExcelVO.class, datas,exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新其他合同资料列表")
    @PreAuthorize("@ss.hasPermission('information:contract:batch-update')")
    public CommonResult<Boolean> batchUpdateContract(@Valid @RequestBody List<ContractUpdateReqVO> updateReqVOList) {
        log.info("batchUpdateContract批量更新其他合同资料入参：{}",updateReqVOList);
        // 在这里处理批量更新逻辑
        contractService.batchUpdateContract(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除其他合同资料列表")
    @PreAuthorize("@ss.hasPermission('information:contract:batch-delete')")
    public CommonResult<Boolean> batchDeleteContract(@RequestBody Map<String, List<Long>> body) {
        log.info("batchDeleteContract批量删除其他合同资料列表入参：{}",body);
        // 在这里处理批量删除逻辑
        contractService.batchDeleteContract(body.get("ids"));
        return success(true);
    }

    @PostMapping("/file")
    @Operation(summary = "批量新增导入其他合同资料列表")
    @PreAuthorize("@ss.hasPermission('information:contract:batch-import')")
    public CommonResult<Boolean> batchImportContract(@RequestBody List<ContractDO> importReqVOList) {
        log.info("batchImportContract批量新增导入其他合同资料列表入参：{}",importReqVOList);
        // 在这里处理批量新增导入逻辑
        contractService.batchImportContract(importReqVOList);
        return success(true);
    }


    @PostMapping("/assignToDept")
    @Operation(summary = "将合同资料分配到组织下")
    public CommonResult<Void> assignToDept(@Valid @RequestBody ContractDeptReqVO contractDeptReqVO) {
        log.info("assignToDept将合同资料分配到组织下，入参{}", contractDeptReqVO);
        contractService.assignContractToDept(contractDeptReqVO);
        return success(null);

    }
    @GetMapping("/selectDetailsByDeptId")
    @Operation(summary = "根据我方单位ID查询合同资料列表的合同类型")
    public CommonResult<List<HashMap<String,Object>>> selectDetailsByDeptId(@RequestParam("companyId") Long companyId) {
        List<HashMap<String, Object>> list = contractService.selectDetailsByDeptId(companyId);
        return success(list);
    }

    @GetMapping("/selectTypeByContractTypeId")
    @Operation(summary = "根据合同类型id查询我方主体类型")
    public CommonResult<HashMap<String, Object>> selectTypeByContractTypeId(@RequestParam("contractDataId") Long contractDataId) {
        HashMap<String, Object> map = contractService.selectTypeByContractTypeId(contractDataId);

        return success(map);
    }

    @GetMapping("/getOtherContractTypeList")
    @Operation(summary = "查询其他合同类型列表")
    public CommonResult<List<String>> getOtherContractTypeList() {
        List<String> otherContractTypeList = contractService.getOtherContractTypeList();
        return success(otherContractTypeList);
    }

}
