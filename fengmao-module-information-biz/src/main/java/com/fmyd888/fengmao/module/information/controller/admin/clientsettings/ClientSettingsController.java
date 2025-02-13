package com.fmyd888.fengmao.module.information.controller.admin.clientsettings;

import cn.hutool.core.io.IoUtil;
import cn.hutool.db.Page;
import com.fmyd888.fengmao.framework.common.enums.FileBussinessTypeEnum;
import com.fmyd888.fengmao.framework.common.pojo.*;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto.ClientSettingsDetailDTO;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto.WechatBindDetailsDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.OilCardDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.StatementTemplateDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.WechatBindDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.foreignwechatclient.ForeignWechatClientDO;
import com.fmyd888.fengmao.module.infra.api.file.FileApi;
import com.fmyd888.fengmao.module.infra.enums.OssDirectoryEnums;
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

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;

import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.ClientSettingsDO;
import com.fmyd888.fengmao.module.information.service.clientsettings.ClientSettingsService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 客户端设置")
@RestController
@RequestMapping("/information/client-settings")
@Validated
public class ClientSettingsController {

    @Resource
    private ClientSettingsService clientSettingsService;




    @PostMapping("/create")
    @Operation(summary = "创建客户端设置")
    @PreAuthorize("@ss.hasPermission('information:client-settings:create')")
    public CommonResult<Long> createClientSettings(@Valid @RequestBody ClientSettingsSaveReqVO createReqVO) {
        return success(clientSettingsService.createClientSettings(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户端设置")
    @PreAuthorize("@ss.hasPermission('information:client-settings:update')")
    public CommonResult<Boolean> updateClientSettings(@Valid @RequestBody ClientSettingsSaveReqVO updateReqVO) {
        clientSettingsService.updateClientSettings(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客户端设置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:client-settings:delete')")
    public CommonResult<Boolean> deleteClientSettings(@RequestParam("id") Long id) {
        clientSettingsService.deleteClientSettings(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客户端设置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:client-settings:query')")
    public CommonResult<ClientSettingsRespVO> getClientSettings(@RequestParam("id") Long id) {
            ClientSettingsDO clientSettings = clientSettingsService.getClientSettings(id);
            return success(BeanUtils.toBean(clientSettings, ClientSettingsRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得客户端设置列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:client-settings:query')")
    public CommonResult<List<ClientSettingsRespVO>> getClientSettingsList(@RequestParam("ids") Collection<Long> ids) {
        List<ClientSettingsDO> list = clientSettingsService.getClientSettingsList(ids);
        return success(BeanUtils.toBean(list, ClientSettingsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得客户端设置分页")
    @PreAuthorize("@ss.hasPermission('information:client-settings:query')")
    public CommonResult<ClientSettingsPage<ClientSettingsRespVO>> getClientSettingsPage(@Valid ClientSettingsPageReqVO pageReqVO) {
        ClientSettingsPage<ClientSettingsRespVO> pageResult = clientSettingsService.getClientSettingsPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/getClientSettingsDetail")
    @Operation(summary = "获得客户端设置详情信息")
    @PreAuthorize("@ss.hasPermission('information:client-settings:getClientSettingsDetail')")
    public CommonResult<ClientSettingsDetailDTO> getClientSettingsDetail(Long id) {
        ClientSettingsDetailDTO clientSettingsDetail = clientSettingsService.getClientSettingsDetail(id);
        return success(clientSettingsDetail);
    }

    @GetMapping("/selectOilCardDetailsMap")
    @Operation(summary = "油卡精简接口")
    public CommonResult<List<HashMap<String,Object>>> selectOilCardDetailsMap(@Valid CommonQueryParam param) {
        List<HashMap<String, Object>> list = clientSettingsService.selectOilCardDetailsMap(param);
        return success(list);
    }

    @GetMapping("/selectWechatBindDetailsMap")
    @Operation(summary = "微信绑定精简接口")
    public CommonResult<List<HashMap<String, Object>>> selectWechatBindDetailsMap(@RequestParam(value = "searchKey",required = false) String nickname) {
        List<HashMap<String, Object>> list = clientSettingsService.selectWechatBindDetailsMap(nickname);
        return success(list);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户端设置 Excel")
    @PreAuthorize("@ss.hasPermission('information:client-settings:export')")
    @OperateLog(type = EXPORT)
    public void exportClientSettingsExcel(@Valid ClientSettingsListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ClientSettingsDO> list = clientSettingsService.getClientSettingsList(exportReqVO);
        // 导出 Excel
        List<ClientSettingsExcelVO> excelVo = BeanUtils.toBean(list, ClientSettingsExcelVO.class);
        ExcelUtils.write(response, "客户端设置.xls", "数据", ClientSettingsExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新客户端设置列表")
    @PreAuthorize("@ss.hasPermission('information:client-settings:batch-update')")
    public CommonResult<Boolean> batchUpdateClientSettings(@Valid @RequestBody List<ClientSettingsSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        clientSettingsService.batchUpdateClientSettings(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除客户端设置列表")
    @PreAuthorize("@ss.hasPermission('information:client-settings:batch-delete')")
    public CommonResult<Boolean> batchDeleteClientSettings(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        clientSettingsService.batchDeleteClientSettings(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载客户端设置导入模板")
    @PreAuthorize("@ss.hasPermission('information:client-settings:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<ClientSettingsExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "客户端设置模板.xls", "客户端设置列表", ClientSettingsExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "客户端设置预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:client-settings:import')")
    public CommonResult<List<ClientSettingsExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<ClientSettingsExcelVO> list = ExcelUtils.read(file, ClientSettingsExcelVO.class);
    return success(clientSettingsService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入客户端设置")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:client-settings:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody ClientSettingsExcelVO importReqVo) throws Exception {
        ImportResult result = clientSettingsService.importData(importReqVo);
        return success(result);
    }

// ==================== 子表（子表-对账单模板） ====================

    @GetMapping("/statement-template/page")
    @Operation(summary = "获得子表-对账单模板分页")
    @Parameter(name = "entityId", description = "客户端设置id，父id")
    @PreAuthorize("@ss.hasPermission('information:client-settings:query')")
    public CommonResult<PageResult<StatementTemplateDO>> getStatementTemplatePage(PageParam pageReqVO,
                                                                                  @RequestParam("entityId") Long entityId) {
        return success(clientSettingsService.getStatementTemplatePage(pageReqVO, entityId));
    }

    @GetMapping("/statement-template/list-by-entity-id")
    @Operation(summary = "获得子表-对账单模板列表")
    @Parameter(name = "entityId", description = "客户端设置id，父id")
    @PreAuthorize("@ss.hasPermission('information:client-settings:query')")
    public CommonResult<List<StatementTemplateDO>> getStatementTemplateListByEntityId(@RequestParam("entityId") Long entityId) {
        return success(clientSettingsService.getStatementTemplateListByEntityId(entityId));
    }

    @GetMapping("/getForeignWechatClientPage")
    @Operation(summary = "获得微信绑定分页页面")
    public CommonResult<ClientSettingsPage<WechatBindRespVO>> getForeignWechatClientPage(@Valid WechatBindPageReqVO pageReqVO) {
        ClientSettingsPage<WechatBindRespVO> foreignWechatClientPage = clientSettingsService.getForeignWechatClientPage(pageReqVO);
        return success(foreignWechatClientPage);
    }

    @GetMapping("/selectWechatBindDetailsByWechatId")
    @Operation(summary = "获得外援微信用户与客户设置关系详情")
    public CommonResult<WechatBindDetailsDTO> selectWechatBindDetailsByWechatId(@Valid Long id) {
        WechatBindDetailsDTO wechatBindDetailsDTO = clientSettingsService.selectWechatBindDetailsByWechatId(id);
        return success(wechatBindDetailsDTO);
    }

    @PutMapping("/updateWechatBind")
    @Operation(summary = "更新绑定微信用户")
    @PreAuthorize("@ss.hasPermission('information:wechat-bind:update')")
    public CommonResult<Boolean> updateWechatBind(Long id,List<Long> customerIds) {
        clientSettingsService.updateWechatBind(id,customerIds);
        return success(true);
    }

    @DeleteMapping("/deleteWechatBind")
    @Operation(summary = "删除绑定微信用户")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:wechat-bind:delete')")
    public CommonResult<Boolean> deleteWechatBind(@RequestParam("id") Long id) {
        clientSettingsService.deleteWechatBind(id);
        return success(true);
    }



}
