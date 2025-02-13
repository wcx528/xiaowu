package com.fmyd888.fengmao.module.information.controller.admin.commodity;

import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.*;
import com.fmyd888.fengmao.module.information.convert.commodity.CommodityConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.information.service.commodity.CommodityDeptService;
import com.fmyd888.fengmao.module.information.service.commodity.CommodityService;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import com.fmyd888.fengmao.module.system.convert.dept.DeptConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.COMMODITY_IDS_NOT_EXISTS;

@Tag(name = "管理后台 - 货物管理表")
@Slf4j
@RestController
@RequestMapping("/information/commodity")
@Validated
public class CommodityController {

    @Resource
    private CommodityService commodityService;
    @Resource
    private CommodityDeptService commodityDeptService;
    @Resource
    private DeptService deptService;

    @PostMapping("/create")
    @Operation(summary = "创建货物管理表")
    @PreAuthorize("@ss.hasPermission('information:commodity:create')")
    public CommonResult<Long> createCommodity(@Valid @RequestBody CommodityCreateReqVO createReqVO) {
        log.info("createCommodity创建入参：{}",createReqVO);
        CommonResult<Long> result = success(commodityService.createCommodity(createReqVO));
        return result;
    }

    @GetMapping("/updateCommodityStatus")
    @Operation(summary = "更新货物状态")
    public CommonResult<Void> updateStatus(@Param("id") Long id){
        commodityService.updateStatus(id);
        return success(null);
    }

    @PutMapping("/update")
    @Operation(summary = "更新货物管理表")
    @PreAuthorize("@ss.hasPermission('information:commodity:update')")
    public CommonResult<Boolean> updateCommodity(@Valid @RequestBody CommodityUpdateReqVO updateReqVO) {
        log.info("updateCommodity更新货物管理表入参：{}",updateReqVO);
        commodityService.updateCommodity(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除货物管理表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:commodity:delete')")
    public CommonResult<Boolean> deleteCommodity(@RequestParam("id") Long id) {
        log.info("deleteCommodity删除货物管理表入参：{}",id);
        commodityService.deleteCommodity(id);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除其他合同资料列表")
    @PreAuthorize("@ss.hasPermission('information:commodity:batch-delete')")
    public CommonResult<Boolean> batchDeleteContract(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        commodityService.batchDeleteCommodity(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得货物管理表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:commodity:query')")
    public CommonResult<CommodityRespVO> getCommodity(@RequestParam("id") Long id) {
        log.info("getCommodity获得货物管理表入参：{}",id);
        CommodityRespVO commodity = commodityService.getCommodity(id);
        CommonResult<CommodityRespVO> result = success(commodity);
        log.info("getCommodity获得货物管理表返回：{}",result);
        return result;
    }

    @GetMapping("/list")
    @Operation(summary = "获得货物管理表列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:commodity:query')")
    public CommonResult<List<CommodityRespVO>> getCommodityList(@RequestParam("ids") Collection<Long> ids) {
        log.info("getCommodityList获得货物管理表列表入参：{}",ids);
        List<CommodityRespVO> list = commodityService.getCommodityList(ids);
        CommonResult<List<CommodityRespVO>> result = success(list);
        log.info("getCommodityList获得货物管理表列表返回：{}",result);
        return result;
    }

    @GetMapping("/page")
    @Operation(summary = "获得货物管理表分页")
    @PreAuthorize("@ss.hasPermission('information:commodity:query')")
    public CommonResult<PageResult<CommodityRespVO>> getCommodityPage(@Valid CommodityPageReqVO pageVO) {
        log.info("getCommodityPage获得货物管理表分页入参：{}",pageVO);
        PageResult<CommodityRespVO> pageResult = commodityService.getCommodityPage(pageVO);
        CommonResult<PageResult<CommodityRespVO>> result = success(pageResult);
        log.info("getCommodityPage获得货物管理表分页返回：{}",result);
        return result;
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出货物管理表 Excel")
    @PreAuthorize("@ss.hasPermission('information:commodity:export')")
    @OperateLog(type = EXPORT)
    public void exportCommodityExcel(@Valid CommodityExportReqVO exportReqVO,
                                     HttpServletResponse response) throws IOException {
        List<CommodityExcelVO> datas = commodityService.getCommodityList(exportReqVO);
        //不要安全告知卡字段
        exportReqVO.setExportFileds(exportReqVO.getExportFileds().stream().filter(s -> !"notifyCar".equals(s)).collect(Collectors.toList()));
        ExcelUtils.write(response, "货物管理表.xls", "数据", CommodityExcelVO.class, datas,exportReqVO.getExportFileds());
    }
    @PostMapping("/getCommodityDeptByCommodityId")
    @Operation(summary = "根据id查询已分配的部门组织列表")
    public CommonResult<List<DeptRespVO>> getCommodityDeptByCommodityId(Long commodityId) {
        log.info("getCommodityDeptByCommodityId将用户添分配到组织下，入参：{}",commodityId);
        Set<Long> deptIdSet = commodityDeptService.findDeptIdsByEntityId(commodityId);
        List<Long> deptIdList = new ArrayList<>(deptIdSet);
        List<DeptDO> deptList = deptService.getDeptList(deptIdList);
        List<DeptRespVO> deptRespList = DeptConvert.INSTANCE.convertList(deptList);
        return success(deptRespList);
    }

    @PostMapping("/assignToDept")
    @Operation(summary = "将货物分配到组织下")
    public CommonResult<Void> assignToDept(@Valid @RequestBody CommodityDeptReqVO commodityDeptReqVO) {
        log.info("getDeptInfo根据货物id获得货物所属的部门组织信息,入参:{}",commodityDeptReqVO);
        commodityService.assignCommodityToDept(commodityDeptReqVO);
        return success(null);
    }

    @GetMapping("/getSimpleCommodityList")
    @Operation(summary = "获得精简货物信息")
    public CommonResult<List<HashMap<String, Object>>> getSimpleCommodityList(@Valid ComodityQueryParam param){
        List<HashMap<String, Object>> list = commodityService.getSimpleCommodityList(param);
        return success(list);
    }

    @GetMapping("/downloadSafeCard")
    @Operation(summary = "下载安全卡")
    public void downloadSafeCard(@RequestParam(value = "ids", required = false) String idsStr, HttpServletResponse response) {
        if (idsStr == null || idsStr.isEmpty()) {
            throw exception(COMMODITY_IDS_NOT_EXISTS);
        }
        List<Long> ids = Arrays.stream(idsStr.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        commodityService.downloadSafeCard(ids, response);
    }



}

