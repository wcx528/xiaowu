package com.fmyd888.fengmao.module.information.controller.admin.purchasemanger;

import cn.hutool.core.io.IoUtil;
import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.infra.enums.OssDirectoryEnums;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.infra.service.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.*;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;

import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;

import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.purchasemanger.PurchaseMangerDO;
import com.fmyd888.fengmao.module.information.service.purchasemanger.PurchaseMangerService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 购买证管理")
@RestController
@RequestMapping("/information/purchase-manger")
@Validated
@Slf4j
public class PurchaseMangerController {

    @Resource
    private PurchaseMangerService purchaseMangerService;
    @Resource
    private FileService fileService;

    @PostMapping("/create")
    @Operation(summary = "创建购买证管理")
    @PreAuthorize("@ss.hasPermission('information:purchase-manger:create')")
    public CommonResult<Long> createPurchaseManger(@Valid @RequestBody PurchaseMangerSaveReqVO createReqVO) {
        return success(purchaseMangerService.createPurchaseManger(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新购买证管理")
    @PreAuthorize("@ss.hasPermission('information:purchase-manger:update')")
    public CommonResult<Boolean> updatePurchaseManger(@Valid @RequestBody PurchaseMangerSaveReqVO updateReqVO) {
        purchaseMangerService.updatePurchaseManger(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除购买证管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:purchase-manger:delete')")
    public CommonResult<Boolean> deletePurchaseManger(@RequestParam("id") Long id) {
        purchaseMangerService.deletePurchaseManger(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得购买证管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:purchase-manger:query')")
    public CommonResult<PurchaseMangerRespVO> getPurchaseManger(@RequestParam("id") Long id) {
        PurchaseMangerRespVO purchaseMangerRespVO = purchaseMangerService.getPurchaseManger(id);
        return success(purchaseMangerRespVO);
    }

    @GetMapping("/list")
    @Operation(summary = "获得购买证管理列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:purchase-manger:query')")
    public CommonResult<List<PurchaseMangerRespVO>> getPurchaseMangerList(@RequestParam("ids") Collection<Long> ids) {
        List<PurchaseMangerDO> list = purchaseMangerService.getPurchaseMangerList(ids);
        return success(BeanUtils.toBean(list, PurchaseMangerRespVO.class));
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出购买证管理 Excel")
    @PreAuthorize("@ss.hasPermission('information:purchase-manger:export')")
    @OperateLog(type = EXPORT)
    public void exportPurchaseMangerExcel(@Valid PurchaseMangerListReqVO exportReqVO,
                                          HttpServletResponse response) throws IOException {
        List<PurchaseMangerDO> list = purchaseMangerService.getPurchaseMangerList(exportReqVO);
        // 导出 Excel
        List<PurchaseMangerExcelVO> excelVo = BeanUtils.toBean(list, PurchaseMangerExcelVO.class);
        ExcelUtils.write(response, "购买证管理.xls", "数据", PurchaseMangerExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @GetMapping("/page2")
    @Operation(summary = "用过通用关键字查询获得购买证管理分页")
    @PreAuthorize("@ss.hasPermission('information:purchase-manger:query')")
    public CommonResult<CardPage<PurchaseMangerRespVO>> getPurchaseMangerKeywordPage(@Valid PurchaseMangerKeywordPageReqVO pageVO) {
        log.info("getPurchaseMangerKeywordPage用过通用关键字查询获得购买证管理分页入参：{}", pageVO);
        CardPage<PurchaseMangerRespVO> purchaseMangerVOPage = purchaseMangerService.selectPageByKeyword(pageVO);
        CommonResult<CardPage<PurchaseMangerRespVO>> result = success(purchaseMangerVOPage);
        log.info("getPurchaseMangerKeywordPage用过通用关键字查询获得购买证管理分页返回：{}", result);
        return result;
    }

    @PostMapping("/upload")
    @Operation(summary = "购买证上传文件")
    public CommonResult<Map<String, Object>> uploadFile(@Valid PurchaseMangerFileUploadRespVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String originalFilename = file.getOriginalFilename();
        String path = uploadReqVO.getPath();
        OssDirectoryEnums purchaseMangerUpload = OssDirectoryEnums.PURCHASE_CONTRACT_UPLOAD;
        String codeBusinessType = FileEnums.PURCHASE_MANGER_FILE_EUM.getCodeBusinessType();
        byte[] content = IoUtil.readBytes(file.getInputStream());
        Map<String, Object> map = fileService.createFile02(originalFilename, purchaseMangerUpload, path, codeBusinessType, content);
        return success(map);
    }

    @GetMapping("/listAllPurchaseManger")
    @Parameter(name = "type", description = "购买证类型（1上游购买证，2下游购买证）",  example = "1,2")
    @Parameter(name = "deptId", description = "购买证所属公司，要求上下购买证相同的公司")
    @Operation(summary = "获取购买证编号精简信息", description = "只包含被开启的购买证，主要用于前端的下拉选项")
    public CommonResult<List<PurchaseMangerSimpleRespVO>> getBasicPurchaseMangerList(@RequestParam(value = "type",required=false) Long type,
                                                                                     @RequestParam(value = "deptId",required=false) Long deptId) {
        List<PurchaseMangerSimpleRespVO> purchaseMangerList = purchaseMangerService.getPurchaseMangerList(type,deptId);
        return success(purchaseMangerList);
    }

    @GetMapping("/listSalsePurchaseManger")
    @Operation(summary = "获取购买证销售单位精简信息", description = "只包含被开启的购买证，主要用于前端的下拉选项")
    public CommonResult<List<PurchaseMangerSalseSimpleRespVO>> getBasicPurchaseMangerList1() {
        List<PurchaseMangerSalseSimpleRespVO> purchaseMangerList1 = purchaseMangerService.getPurchaseMangerList1();
        return success(purchaseMangerList1);
    }

    @GetMapping("/listUnitPurchaseManger")
    @Operation(summary = "获取购买证购买单位精简信息", description = "只包含被开启的购买证，主要用于前端的下拉选项")
    public CommonResult<List<PurchaseMangerUnitSimpleRespVO>> getBasicPurchaseMangerList2() {
        List<PurchaseMangerUnitSimpleRespVO> purchaseMangerList2 = purchaseMangerService.getPurchaseMangerList2();
        return success(purchaseMangerList2);
    }
    @GetMapping("/getBasicPurchaseMangerById")
    @Operation(summary = "根据id自动带出")
    public CommonResult<List<PurchaseMangerBasicRespVO>> getBasicPurchaseMangerById(@Param("id") Long id) {
        // 获用户列表，只要开启状态的
        List<PurchaseMangerBasicRespVO> purchaseManger02 = purchaseMangerService.getPurchaseManger02(id);
        // 排序后，返回给前端
        return success(purchaseManger02);
    }
}
