package com.fmyd888.fengmao.module.information.controller.admin.encodingrules;

import cn.hutool.json.JSONObject;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.*;
import com.fmyd888.fengmao.module.information.convert.encodingrules.EncodingRulesConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.encodingrules.EncodingRulesDO;
import com.fmyd888.fengmao.module.information.properties.QCCProperties;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.QCC_FAIL;

@Tag(name = "管理后台 - 编码规则设置")
@Slf4j
@RestController
@RequestMapping("/information/encoding-rules")
@Validated
public class EncodingRulesController {

    @Resource
    private EncodingRulesService encodingRulesService;

    @PostMapping("/create")
    @Operation(summary = "创建编码规则设置")
    @PreAuthorize("@ss.hasPermission('information:encoding-rules:create')")
    public CommonResult<Long> createEncodingRules(@Valid @RequestBody EncodingRulesCreateReqVO createReqVO) {
        log.info("createEncodingRules创建编码规则设置入参：{}",createReqVO);
        CommonResult<Long> result = success(encodingRulesService.createEncodingRules(createReqVO));
        log.info("createEncodingRules创建编码规则设置出参：{}",result);
        return result;
    }

    @PutMapping("/update")
    @Operation(summary = "更新编码规则设置")
    @PreAuthorize("@ss.hasPermission('information:encoding-rules:update')")
    public CommonResult<Boolean> updateEncodingRules(@Valid @RequestBody EncodingRulesUpdateReqVO updateReqVO) {
        log.info("updateEncodingRules更新编码规则设置：{}",updateReqVO);
        encodingRulesService.updateEncodingRules(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除编码规则设置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:encoding-rules:delete')")
    public CommonResult<Boolean> deleteEncodingRules(@RequestParam("id") Long id) {
        log.info("deleteEncodingRules删除编码规则设置：{}",id);
        encodingRulesService.deleteEncodingRules(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得编码规则设置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:encoding-rules:query')")
    public CommonResult<EncodingRulesRespVO> getEncodingRules(@RequestParam("id") Long id) {
        log.info("getEncodingRules获得编码规则设置入参：{}",id);
        EncodingRulesDO encodingRules = encodingRulesService.getEncodingRules(id);
        CommonResult<EncodingRulesRespVO> result = success(EncodingRulesConvert.INSTANCE.convert(encodingRules));
        log.info("getEncodingRules获得编码规则设置返回：{}",result);
        return result;
    }

    @GetMapping("/list")
    @Operation(summary = "获得编码规则设置列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:encoding-rules:query')")
    public CommonResult<List<EncodingRulesRespVO>> getEncodingRulesList(@RequestParam("ids") Collection<Long> ids) {
        log.info("getEncodingRulesList获得编码规则设置列表入参：{}",ids);
        List<EncodingRulesDO> list = encodingRulesService.getEncodingRulesList(ids);
        CommonResult<List<EncodingRulesRespVO>> result = success(EncodingRulesConvert.INSTANCE.convertList(list));
        log.info("getEncodingRulesList获得编码规则设置列表返回：{}",result);
        return result;
    }

    @GetMapping("/page")
    @Operation(summary = "获得编码规则设置分页")
    @PreAuthorize("@ss.hasPermission('information:encoding-rules:query')")
    public CommonResult<PageResult<EncodingRulesRespVO>> getEncodingRulesPage(@Valid EncodingRulesPageReqVO pageVO) {
        log.info("getEncodingRulesPage获得编码规则设置分页入参：{}",pageVO);
        PageResult<EncodingRulesDO> pageResult = encodingRulesService.getEncodingRulesPage(pageVO);
        CommonResult<PageResult<EncodingRulesRespVO>> result = success(EncodingRulesConvert.INSTANCE.convertPage(pageResult));
        log.info("getEncodingRulesPage获得编码规则设置分页返回：{}",result);
        return result;
    }

    @GetMapping("/getCodeByRuleType")
    @Operation(summary = "通过业务规则类型值获得对应的编码字符串")
    //@PreAuthorize("@ss.hasPermission('information:encoding-rules:query')")
    public CommonResult<String> getCodeByRuleType(String ruleType) {
        log.info("getCodeByRuleType通过业务规则类型值获得对应的编码字符串入参：{}",ruleType);
        String result = encodingRulesService.getCodeByRuleType(ruleType);
        log.info("getCodeByRuleType通过业务规则类型值获得对应的编码字符串返回：{}",result);
        return success(result);
    }





    @GetMapping(value = "/getCodingRuleList")
    @Operation(summary = "预留用于新增编码规则时筛选未使用的业务对象，")
    @Parameter(name = "type", description = "1:已使用，2：未使用，其他为全部查询", required = true, example = "1024")
    public CommonResult<List<Map<String,Object>>> getCodingRuleList(@RequestParam("type") Integer type) {
        List<Map<String,Object>> codingRuleList = encodingRulesService.getCodingRuleList(type);
        return success(codingRuleList);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出编码规则设置 Excel")
    @PreAuthorize("@ss.hasPermission('information:encoding-rules:export')")
    @OperateLog(type = EXPORT)
    public void exportEncodingRulesExcel(@Valid EncodingRulesListReqVO exportReqVO,
                                         HttpServletResponse response) throws IOException {
        List<EncodingRulesDO> list = encodingRulesService.getEncodingRulesExcel(exportReqVO);
        // 导出 Excel
        List<EncodingRulesExcelVO> excelVo = BeanUtils.toBean(list, EncodingRulesExcelVO.class);
        ExcelUtils.write(response, "编码规则设置.xls", "数据", EncodingRulesExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @GetMapping("/getSimpleCodingRuleList")
    @Operation(summary = "获得编码规则下拉数据源信息")
    public CommonResult<List<HashMap<String, Object>>> getSimpleCodingRuleList(@Valid CommonQueryParam param) {
        List<HashMap<String, Object>> res = encodingRulesService.getSimpleCodingRuleList(param);
        return success(res);
    }
}
