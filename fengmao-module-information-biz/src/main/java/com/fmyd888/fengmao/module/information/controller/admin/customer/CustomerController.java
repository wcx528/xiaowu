package com.fmyd888.fengmao.module.information.controller.admin.customer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.customer.dto.customerDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.CustomerDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SettleConsignDetailDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SupplierByCustomerNameReqDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SupplierBySettleDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;
import com.fmyd888.fengmao.module.information.properties.QCCProperties;
import com.fmyd888.fengmao.module.information.service.customer.CustomerDeptService;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import com.fmyd888.fengmao.module.system.convert.dept.DeptConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;

import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;

import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.*;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.QCC_FAIL;

import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.convert.customer.CustomerConvert;
import com.fmyd888.fengmao.module.information.service.customer.CustomerService;
import org.springframework.web.client.RestTemplate;

@Tag(name = "管理后台 - 客户信息管理")
@Slf4j
@RestController
@RequestMapping("/information/customer")
@Validated
@RequiredArgsConstructor
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerDeptService customerDeptService;

    @Resource
    private DeptService deptService;

    @PostMapping("/create")
    @Operation(summary = "创建客户信息管理")
    @PreAuthorize("@ss.hasPermission('information:customer:create')")
    public CommonResult<Long> createCustomer(@Valid @RequestBody CustomerCreateReqVO createReqVO) {
        log.info("createCustomer创建客户信息管理入参：{}", createReqVO);
        Long customerId = customerService.createCustomer(createReqVO);
        CommonResult<Long> result = success(customerId);
        log.info("createCustomer创建客户信息管理返回：{}", result);
        return result;
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户信息管理")
    @PreAuthorize("@ss.hasPermission('information:customer:update')")
    public CommonResult<Boolean> updateCustomer(@Valid @RequestBody CustomerUpdateReqVO updateReqVO) {
        log.info("updateCustomer更新客户信息管理入参：{}", updateReqVO);
        customerService.updateCustomer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客户信息管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:customer:delete')")
    public CommonResult<Boolean> deleteCustomer(@RequestParam("id") Long id) {
        log.info("deleteCustomer删除客户信息管理入参：{}", id);
        customerService.deleteCustomer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客户信息管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:customer:query')")
    public CommonResult<CustomerRespVO> getCustomer(@RequestParam("id") Long id) {
//        log.info("getCustomer获得客户信息管理入参：{}", id);
        //1、获取客户信息
        CustomerRespVO customer = customerService.getCustomer(id);
        CommonResult<CustomerRespVO> result = success(customer);
//        log.info("getCustomer获得客户信息管理返回：{}", result);
        return result;
    }

    @GetMapping("/allCustomerData")
    @Operation(summary = "获得所有客户信息")
    @PreAuthorize("@ss.hasPermission('information:customer:query')")
    public CommonResult<List<CustomerRespVO>> getCustomerList() {
        List<CustomerRespVO> list = customerService.getCustomerList();
        CommonResult<List<CustomerRespVO>> result = success(list);
        log.info("getCustomerList获得所有客户信息返回：{}", result);
        return result;
    }

    @GetMapping("/list")
    @Operation(summary = "获得客户信息管理列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:customer:query')")
    public CommonResult<List<CustomerRespVO>> getCustomerList(@RequestParam("ids") Collection<Long> ids) {
        log.info("getCustomerList获得客户信息管理列表入参：{}", ids);
        List<CustomerRespVO> list = customerService.getCustomerList(ids);
        CommonResult<List<CustomerRespVO>> result = success(list);
        log.info("getCustomerList获得客户信息管理列表返回：{}", result);
        return result;
    }


    @GetMapping("/selectCustomerDetail")
    @Operation(summary = "模糊查询外部公司")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:customer:query')")
    public CommonResult<List<customerDTO>> selectCustomerDetail(@RequestParam(value = "searchKey", required = false) String customerName) {
        List<customerDTO> customer = customerService.selectCustomerDetail(customerName);
        return success(customer);
    }

    @GetMapping("/selectOutCustomer")
    @Operation(summary = "模糊查询外援公司")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:customer:query')")
    public CommonResult<List<customerDTO>> selectOutCustomer() {
        List<customerDTO> customer = customerService.selectOutCustomer();
        return success(customer);
    }

    @GetMapping("/page")
    @Operation(summary = "获得客户信息管理分页")
    @PreAuthorize("@ss.hasPermission('information:customer:query')")
    public CommonResult<PageResult<CustomerRespVO>> getCustomerPage(@Valid CustomerPageReqVO pageVO) {
        PageResult<CustomerRespVO> pageResult = customerService.getCustomerPage(pageVO);
        CommonResult<PageResult<CustomerRespVO>> result = success(pageResult);
        return result;
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户信息管理 Excel")
    @PreAuthorize("@ss.hasPermission('information:customer:export')")
    @OperateLog(type = EXPORT)
    public void exportCustomerExcel(@Valid CustomerExportReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        log.info("exportCustomerExcel导出客户信息管理入参：{}", response);
        List<CustomerDO> list = customerService.getCustomerList(exportReqVO);
        // 导出 Excel
        List<CustomerExcelVO> datas = BeanUtils.toBean(list, CustomerExcelVO.class);
        if(ObjectUtil.isNotEmpty(datas)){
            List<CustomerDeptDO> depts = customerDeptService.getCustomerDeptByCustomerIds1(datas.stream().map(CustomerExcelVO::getId).collect(Collectors.toList()));
            datas.forEach(customerDO -> {
                List<String> collect = depts.stream().filter(customerDeptDO -> customerDeptDO.getCustomerId().equals(customerDO.getId())).map(CustomerDeptDO::getDeptName).collect(Collectors.toList());
                customerDO.setOrganization(String.join(",", collect));
            });
        }
        log.info("exportCustomerExcel导出客户信息管理返回：{}", datas);
        ExcelUtils.write(response, "客户信息管理.xls", "数据", CustomerExcelVO.class, datas, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新客户信息管理列表")
    @PreAuthorize("@ss.hasPermission('information:customer:batch-update')")
    public CommonResult<Boolean> batchUpdateCustomer(@Valid @RequestBody List<CustomerUpdateReqVO> updateReqVOList) {
        log.info("batchUpdateCustomer批量更新客户信息管理入参：{}", updateReqVOList);
        // 在这里处理批量更新逻辑
        customerService.batchUpdateCustomer(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除客户信息管理列表")
    @PreAuthorize("@ss.hasPermission('information:customer:batch-delete')")
    public CommonResult<Boolean> batchDeleteCustomer(@RequestBody Map<String, List<Long>> body) {
        log.info("batchDeleteCustomer批量删除客户信息管理列表入参：{}", body);
        // 在这里处理批量删除逻辑
        customerService.batchDeleteCustomer(body.get("ids"));
        return success(true);
    }

    @PostMapping("/batch-import")
    @Operation(summary = "批量新增导入客户信息管理列表")
    @PreAuthorize("@ss.hasPermission('information:customer:batch-import')")
    public CommonResult<Boolean> batchImportCustomer(@RequestBody List<CustomerDO> importReqVOList) {
        log.info("batchImportCustomer批量新增导入客户信息管理列表入参：{}", importReqVOList);
        // 在这里处理批量新增导入逻辑
        customerService.batchImportCustomer(importReqVOList);
        return success(true);
    }

    //@PostMapping("/assignCustomerToDept")
    //@Operation(summary = "将用户添分配到部门组织下面")
    //public CommonResult<Void> assignCustomerToDept(CustomerDpetReqVO customerDpetReqVO) {
    //    log.info("assignCustomerToDept将用户添分配到组织下，入参：{}",customerDpetReqVO);
    //    //customerDeptService.assignCustomerToDept(customerDpetReqVO);
    //    return success(null);
    //}

    @GetMapping("/getDeptByCustomerId")
    @Operation(summary = "根据客户id查询已分配的部门组织列表")
    public CommonResult<List<DeptRespVO>> getDeptByCustomerId(Long customerId) {
        log.info("getCustomerDeptByCustomerId据客户id查询已分配的部门组织列表，入参：{}", customerId);
        List<CustomerDeptDO> customerDept = customerDeptService.getCustomerDeptByCustomerId(customerId);
        List<Long> deptIds = customerDept.stream().map(CustomerDeptDO::getDeptId).collect(Collectors.toList());
        List<DeptDO> deptList = deptService.getDeptList(deptIds);
        List<DeptRespVO> deptRespList = DeptConvert.INSTANCE.convertList(deptList);
        return success(deptRespList);
    }

    @PostMapping("/assignToDept")
    @Operation(summary = "将客户分配到组织下")
    @PreAuthorize("@ss.hasPermission('information:customer:update')")
    public CommonResult<Void> assignToDept(@Valid @RequestBody CustomerDeptReqVO customerDeptReqVO) {
        log.info("getDeptInfo根据客户id获得客户所属的部门组织信息,入参:{}", customerDeptReqVO);
        customerDeptService.assignCustomerToDept(customerDeptReqVO);
        return success(null);
    }

    @GetMapping("/page2")
    @Operation(summary = "通用关键字查询获得客户信息分页")
    @PreAuthorize("@ss.hasPermission('information:main-vehicle:query')")
    public CommonResult<Page<CustomerRespVO>> getCustomerPage02(@Valid CustomerPageReqVO pageVO) {
        log.info("getCustomerPage用过通用关键字查询获得客户分页入参：{}", pageVO);
        Page<CustomerRespVO> customerPage = customerService.getCustomerPage02(pageVO);
        CommonResult<Page<CustomerRespVO>> result = success(customerPage);
        log.info("getCustomerPage用过通用关键字查询获得客户分页返回：{}", result);
        return result;
    }

    @GetMapping("/getCustomerList")
    @Operation(summary = "获得客户或供应商精简信息（通用接口）")
    public CommonResult<List<CustomerDTO>> getInsideCustomerList2(@Valid CustomerListReqVo customerListReqVo) {
        List<CustomerDTO> list = customerService.getCustomerList(customerListReqVo);
        return success(list);
    }

    @GetMapping("/selectSupplier")
    @Operation(summary = "根据客户名称获取相应的供应商")
    public CommonResult<SupplierBySettleDTO> selectSupplierByCustomerName(@Valid SupplierByCustomerNameReqDTO reqDTO) {
        SupplierBySettleDTO supplierBySettleDTO = customerService.selectSupplierByCustomerName(reqDTO);
        return success(supplierBySettleDTO);
    }

    @GetMapping("/updateCustomerStatus")
    @Operation(summary = "客户信息管理禁用/启用接口")
    public CommonResult<Boolean> updateCustomerStatus(Long id) {
        customerService.updateCustomerStatus(id);
        return success(true);
    }

    @GetMapping("/getCustomerIsBindAddress")
    @Operation(summary = "获取绑定地址的客户信息")
    public CommonResult<List<HashMap<String, Object>>> getCustomerIsBindAddress(String customerType) {
        List<HashMap<String, Object>> maps = customerService.getCustomerIsBindAddress(customerType);
        return success(maps);
    }

    @GetMapping("/settleAcceptContract")
    @Operation(summary = "结算方承运合同")
    public CommonResult<List<SettleConsignDetailDTO>> selectContractNumBySettleAccounts(@RequestParam(value = "secondSettleAccountsId") Long secondSettleAccountsId, @RequestParam(value = "settleAccountsId")Long settleAccountsId) {
        List<SettleConsignDetailDTO> settleConsignDetailDTOS = customerService.selectContractNumBySettleAccounts(secondSettleAccountsId, settleAccountsId);
        return success(settleConsignDetailDTOS);
    }

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private QCCProperties qccProperties;

    /**
     * 税号开票信息查询
     * @param keyword  查询关键字（公司名称、注册号）
     * @return 税号开票信息
     */
    @GetMapping("/getBasicDetailsByName")
    @Operation(summary = "税号开票信息：企业税务登记号查询、纳税人识别号、企业名称、企业类型、地址、联系电话、开户行、开户行账号")
    public CommonResult<Map<String,Object>> getBasicDetailsByName(String keyword) {
        log.info("getBasicDetailsByName查询入参keyword={}",keyword);
        String url = qccProperties.getHost() + qccProperties.getDetailsPath()+"?key="+qccProperties.getAppkey()+"&keyword="+keyword;
        // 请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
        String[] authentHeaders = RandomAuthentHeader();
        headers.add("Token", authentHeaders[0]);
        headers.add("Timespan", authentHeaders[1]);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        // 使用 exchange 方法来发送带有 HttpHeaders 的请求
        JSONObject response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JSONObject.class).getBody();
        Map<String, Object> result = response.toBean(Map.class);
        log.info("getBasicDetailsByName返回结果:{}",result);
        String status = (String)result.get("Status");

        //只有请求返回状态编码为200为正确请求，其他的状态码均为错误
        if("200".equals(status)){
            return CommonResult.success(result,"https://openapi.qcc.com/dataApi/271");
        }
        throw exception(QCC_FAIL,(String)result.get("Message"));
    }
    // 获取Auth Code
    private String[] RandomAuthentHeader() {
        String timeSpan = String.valueOf(System.currentTimeMillis() / 1000);
        String[] authentHeaders = new String[] { DigestUtils.md5Hex(qccProperties.getAppkey().concat(timeSpan).concat(qccProperties.getSecretKey()))
                .toUpperCase(), timeSpan };
        return authentHeaders;
    }

}
