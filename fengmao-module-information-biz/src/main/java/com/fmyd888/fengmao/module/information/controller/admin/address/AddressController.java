package com.fmyd888.fengmao.module.information.controller.admin.address;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.address.dto.AddressByCustomerIdDTO;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.*;
import com.fmyd888.fengmao.module.information.properties.GaoDeProperties;
import com.fmyd888.fengmao.module.information.service.address.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
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

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 地址")
@Slf4j
@RestController
@RequestMapping("/information/address")
@Validated
public class AddressController {

    @Resource
    private AddressService addressService;

    @Resource
    private RestTemplate restTemplate;

    //固定密钥，临时调式接口
    final String key = "b3acb32a52df7886bf17bff009948e8f";

    @PostMapping("/create")
    @Operation(summary = "创建地址")
    @PreAuthorize("@ss.hasPermission('information:address:create')")
    public CommonResult<Long> createAddress(@Valid @RequestBody AddressCreateReqVO createReqVO) {
        CommonResult<Long> result = success(addressService.createAddress(createReqVO));
        return result;
    }

    @PutMapping("/update")
    @Operation(summary = "更新地址")
    @PreAuthorize("@ss.hasPermission('information:address:update')")
    public CommonResult<Boolean> updateAddress(@Valid @RequestBody AddressUpdateReqVO updateReqVO) {
        addressService.updateAddress(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除地址")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:address:delete')")
    public CommonResult<Boolean> deleteAddress(@RequestParam("id") Long id) {
        addressService.deleteAddress(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得地址")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:address:query')")
    public CommonResult<AddressRespVO> getAddress(@RequestParam("id") Long id) {
        AddressRespVO address = addressService.getAddress(id);
        CommonResult<AddressRespVO> result = success(address);
        return result;
    }

    @GetMapping("/list")
    @Operation(summary = "获得地址列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:address:query')")
    public CommonResult<List<AddressRespVO>> getAddressList(@RequestParam("ids") Collection<Long> ids) {
        List<AddressRespVO> list = addressService.getAddressList(ids);
        CommonResult<List<AddressRespVO>> success = success(list);
        return success;
    }

    @GetMapping("/page")
    @Operation(summary = "获得地址分页")
    @PreAuthorize("@ss.hasPermission('information:address:query')")
    public CommonResult<Page<AddressRespVO>> getAddressPage(@Valid AddressPageReqVO pageVO) {
        Page<AddressRespVO> pageResult = addressService.getAddressPage(pageVO);
        CommonResult<Page<AddressRespVO>> result = success(pageResult);
        return result;
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出地址 Excel")
    @PreAuthorize("@ss.hasPermission('information:address:export')")
    @OperateLog(type = EXPORT)
    public void exportAddressExcel(@Valid AddressExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        // 可考虑使用分页返回的结果集合
        //PageResult<AddressRespVO> datas = addressService.getAddressPage(pageVO);

        List<AddressExcelVO> datas = addressService.getAddressList(exportReqVO);
        String filename = "地址.xls";
        String sheetName = "数据";
        List<String> exportFileds = exportReqVO.getExportFileds();
        //ExcelUtils.write(response, filename, sheetName, AddressExcelVO.class, datas,exportFileds);
        ExcelUtils.write2(response, filename, sheetName, AddressExcelVO.class, datas,exportFileds);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除地址列表")
    @PreAuthorize("@ss.hasPermission('information:address:batch-delete')")
    public CommonResult<Boolean> batchDeleteAddress(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        addressService.batchDeleteAddress(body.get("ids"));
        return success(true);
    }

    @GetMapping("/updateAddressStatus")
    @Operation(summary = "更新地址状态")
    public CommonResult<Void> updateAddressStatus(@Param("id") Long id){
        addressService.updateStatus(id);
        return success(null);
    }

    @GetMapping("/getMapDetailByLocation")
    @Operation(summary = "根据经纬度获取位置详情")
    public CommonResult<Map<String,Object>> getMapDetailByLocation(@Param("location") String location){
        // url信息
        String url = "https://restapi.amap.com/v3/geocode/regeo" +
                "?key="+key+"&location="+location;
        // 使用 exchange 方法来发送带有 HttpHeaders 的请求
        JSONObject response = restTemplate.exchange(url, HttpMethod.GET, null, JSONObject.class).getBody();
        Map<String, Object> result = response.toBean(Map.class);
        return success(result);
    }

    @GetMapping("/getMapDetailByAddress")
    @Operation(summary = "逆地理编码查询")
    public CommonResult<Map<String,Object>> getMapDetailByAddress(@Param("address") String address){
        // url信息
        String url = "https://restapi.amap.com/v3/geocode/geo" +
                "?key="+key+"&address="+address;
        // 使用 exchange 方法来发送带有 HttpHeaders 的请求
        JSONObject response = restTemplate.exchange(url, HttpMethod.GET, null, JSONObject.class).getBody();
        Map<String, Object> result = response.toBean(Map.class);
        return success(result);
    }

    @GetMapping("/getAddressByFull")
    @Operation(summary = "根据地址全称模糊匹配获得地址信息")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('information:address:query')")
    public CommonResult<List<AddressRespVO>> getAddressByFull(@RequestParam("addressName") String addressName) {
        List<AddressRespVO> addressList = addressService.getAddressByFull(addressName);
        CommonResult<List<AddressRespVO>> result = success(addressList);
        return result;
    }

    @GetMapping("/getAddressDistrict")
    @Operation(summary = "高德行政区域查询")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('information:address:query')")
    public CommonResult<Map<String, Object>> getAddressDistrict(@Validated GaoDeProperties gaoDeProperties) throws JsonProcessingException {
        Map<String, Object> objectMap = addressService.getAddressDistrict(gaoDeProperties);
        CommonResult<Map<String, Object>> result = success(objectMap);
        return result;
    }

    @GetMapping("/getUnloadCustomerAndAddress")
    @Operation(summary = "模糊查询卸货公司，并带出卸货地址")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:address:getCustomerAndAddres')")
    public CommonResult<List<AddressRespVO>> getUnloadCustomerAndAddress(@RequestParam("customerName") String customerName) {
        List<AddressRespVO> address = addressService.getUnloadCustomerAndAddress(customerName);
        CommonResult<List<AddressRespVO>> result = success(address);
        return result;
    }


    @GetMapping("/getAddressByCustomerId")
    @Operation(summary = "根据装卸货公司id，并带出装卸货地址")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:address:getCustomerAndAddres')")
    public CommonResult<List<AddressByCustomerIdDTO>> getAddressByCustomerId(@RequestParam("customerId") Long customerId) {
        List<AddressByCustomerIdDTO> address = addressService.getAddressByCustomerId(customerId);
        CommonResult<List<AddressByCustomerIdDTO>> result = success(address);
        return result;
    }

    @GetMapping("/getAddressList")
    @Operation(summary = "获取客户地址列表（通用数据源接口）")
    @PreAuthorize("@ss.hasPermission('information:address:query')")
    public CommonResult<List<Map<String, Object>>> getAddressListByCustomerId(@Valid CommonQueryParam param) {
        return success(addressService.getAddressListByCustomerId(param));
    }

    @GetMapping("/selectAddressNameDetailsMap")
    @Operation(summary = "地址精简接口(客户信息表中：客户类型维护有地址的(车辆去向接口))")
    @PreAuthorize("@ss.hasPermission('information:address:query')")
    public CommonResult<List<HashMap<String, Object>>> selectAddressNameDetailsMap() {
        return success(addressService.selectAddressNameDetailsMap());
    }

    @GetMapping("/selectAddressByCustomerId")
    @Operation(summary = "根据客户/供应商id查询地址")
    public CommonResult<List<Map<String, Object>>> selectAddressByCustomerId(Long customerId) {
        return success(addressService.selectAddressByCustomerId(customerId));
    }

}
