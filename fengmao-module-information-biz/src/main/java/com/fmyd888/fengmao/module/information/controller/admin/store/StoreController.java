package com.fmyd888.fengmao.module.information.controller.admin.store;

import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.DownMainVehicleExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.DownTrailerExcelVO;
import com.fmyd888.fengmao.module.information.convert.store.StoreConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDO;
import com.fmyd888.fengmao.module.information.service.store.StoreService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 仓库")
@RestController
@RequestMapping("/information/store")
@Validated
@Slf4j
public class StoreController {

    @Resource
    private StoreService storeService;

    @PostMapping("/create")
    @Operation(summary = "创建仓库")
    @PreAuthorize("@ss.hasPermission('information:store:create')")
    public CommonResult<Long> createStore(@Valid @RequestBody StoreCreateReqVO createReqVO) {
        Long storeId = storeService.createStore(createReqVO);
        return success(storeId);
    }

    @PutMapping("/update")
    @Operation(summary = "更新仓库")
    @PreAuthorize("@ss.hasPermission('information:store:update')")
    public CommonResult<Boolean> updateStore(@Valid @RequestBody StoreUpdateReqVO updateReqVO) {
        storeService.updateStore(updateReqVO);
        return success(true);
    }

    @GetMapping("/updateStoreStatus")
    @Operation(summary = "更新仓库状态")
    public CommonResult<Void> updateStoreStatus(@Param("id") Long id) {
        storeService.updateStatus(id);
        return success(null);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除仓库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:store:delete')")
    public CommonResult<Boolean> deleteStore(@RequestParam("id") Long id) {
        storeService.deleteStore(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得仓库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:store:query')")
    public CommonResult<StoreRespVO> getStore(@RequestParam("id") Long id) {
        StoreRespVO store = storeService.getStore(id);
        return success(store);
    }

    @GetMapping("/list")
    @Operation(summary = "获得仓库列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:store:query')")
    public CommonResult<List<StoreRespVO>> getStoreList(@RequestParam("ids") Collection<Integer> ids) {
        List<StoreRespVO> list = storeService.getStoreList(ids);
        return success(list);
    }

    @GetMapping("/page")
    @Operation(summary = "获得仓库分页")
    @PreAuthorize("@ss.hasPermission('information:store:query')")
    public CommonResult<PageResult<StoreRespVO>> getStorePage(@Valid StorePageReqVO pageVO) {
        PageResult<StoreRespVO> pageResult = storeService.getStorePage(pageVO);
        return success(pageResult);
    }

    @GetMapping("/getStorePage")
    @Operation(summary = "仓库导出字段选择")
    @PreAuthorize("@ss.hasPermission('information:store:query')")
    public CommonResult<List<HashMap<String, Object>>> getStorePage() {
        List<HashMap<String, Object>> exportList = storeService.getExportList();
        return success(exportList);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入仓库模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        List<DownStoreImportVO> dataExample = new ArrayList<>();
        // 必填字段
        List<String> requiredFields = Arrays.asList(
                "useOrganization", "storeName", "storeType"
        );
        // 需要添加示例日期的字段列表
        List<String> exampleDateFields = Arrays.asList();
        // 输出
        ExcelUtils.write3(response, "仓库导入模板.xls", "仓库列表",
                        DownStoreImportVO.class, dataExample, requiredFields,exampleDateFields);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "仓库导入预览")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:mainVehicle:import')")
    public CommonResult<List<DownStoreExcelVO>> importPreviewList(@RequestParam("file") MultipartFile file,
                                                                    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<DownStoreExcelVO> list = ExcelUtils.readExcelAndRemoveAsterisk(file, DownStoreExcelVO.class);
        return success(storeService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入仓库")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:mainVehicle:import')")
    public CommonResult<ImportResult> importMainVehicleList(@RequestBody DownStoreExcelVO importMainVehicle) throws Exception {
        ImportResult importResult = storeService.importMainVehicleList(importMainVehicle);
        return success(importResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出仓库 Excel")
    @PreAuthorize("@ss.hasPermission('information:store:export')")
    @OperateLog(type = EXPORT)
    public void exportStoreExcel(@Valid StoreExportReqVO exportReqVO,
                                 HttpServletResponse response) throws IOException {
        List<StoreDO> list = storeService.getStoreList(exportReqVO);
        // 导出 Excel
        List<StoreExcelVO> datas = BeanUtils.toBean(list, StoreExcelVO.class);
        datas.forEach(p->p.setOrganization(p.getDeptName()));
        ExcelUtils.write(response, "仓库.xls", "数据", StoreExcelVO.class, datas, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新仓库列表")
    @PreAuthorize("@ss.hasPermission('information:store:batch-update')")
    public CommonResult<Boolean> batchUpdateStore(@Valid @RequestBody List<StoreUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        storeService.batchUpdateStore(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除仓库列表")
    @PreAuthorize("@ss.hasPermission('information:store:batch-delete')")
    public CommonResult<Boolean> batchDeleteStore(@RequestBody Map<String, List<Integer>> body) {
        // 在这里处理批量删除逻辑
        storeService.batchDeleteStore(body.get("ids"));
        return success(true);
    }

    @GetMapping("/getDeptInfo")
    @Operation(summary = "根据仓库id获得仓库所属的部门组织信息")
    public CommonResult<List<DeptSimpleRespVO>> getDeptInfo(Long storeId) {
        List<DeptSimpleRespVO> deptInfoList = storeService.getDeptInfoList(storeId);
        return success(deptInfoList);
    }

    @PostMapping("/assignToDept")
    @Operation(summary = "将仓库分配到组织下")
    public CommonResult<Void> assignToDept(@Valid @RequestBody StoreDeptReqVO storeDeptReqVO) {
        storeService.assignStoreToDept(storeDeptReqVO);
        return success(null);
    }



//    @PostMapping("/import")
//    @Operation(summary = "导入仓库")
//    @Parameters({
//            @Parameter(name = "file", description = "Excel 文件", required = true),
//            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
//    })
//    @PreAuthorize("@ss.hasPermission('information:store:import')")
//    public CommonResult<StoreImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
//                                                       @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
//        List<StoreImportExcelVO> list = ExcelUtils.read(file, StoreImportExcelVO.class);
//        StoreImportRespVO storeImportRespVO = storeService.importStoreList(list, updateSupport);
//        return success(storeImportRespVO);
//    }

    @GetMapping("/getSimpleStoreList")
    @Operation(summary = "获得精简仓库信息")
    public CommonResult<List<HashMap<String, Object>>> getSimpleStoreList(CommonQueryParam param) {
        List<HashMap<String, Object>> list = storeService.getSimpleStoreList(param);
        return success(list);
    }

}
