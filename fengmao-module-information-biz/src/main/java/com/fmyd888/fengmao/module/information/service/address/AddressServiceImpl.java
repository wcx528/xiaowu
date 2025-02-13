package com.fmyd888.fengmao.module.information.service.address;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerAdressDO;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerAddressMapper;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.controller.admin.address.dto.AddressByCustomerIdDTO;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.*;
import com.fmyd888.fengmao.module.information.convert.address.AddressConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.address.AddressMapper;
import com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants;
import com.fmyd888.fengmao.module.information.properties.GaoDeProperties;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.FULL_ADDRESS_IS_EXISTS;


/**
 * 地址 Service 实现类
 *
 * @author 丰茂企业
 */
@Service
@Validated
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressMapper addressMapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private EncodingRulesService encodingRulesService;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private CustomerAddressMapper customerAddressMapper;

    @Override
    public Long createAddress(AddressCreateReqVO createReqVO) {
              // 校验地址是否重复
        if (createReqVO.getFullAddress() != null) {
            validateAddress(createReqVO.getFullAddress());
        }
        // 1、转换填充创建信息数据
        AddressDO address = AddressConvert.INSTANCE.convert(createReqVO);
        //2、运输地址编码填充
        String code2 = EncodingRulesEnum.ADDRESS_CODE.getBusinessCode();
        String code = encodingRulesService.getCodeByRuleType(code2);
        address.setAddressCode(code);
        //3、拆分经纬度再插入填充
        String longitudeLatitude = createReqVO.getLongitudeLatitude();
        String[] split = longitudeLatitude.split(",");
        if (2 == split.length) {
            address.setLongitude(new BigDecimal(split[0]));
            address.setLatitude(new BigDecimal(split[1]));
        }

        addressMapper.insert(address);
        // 返回
        return address.getId();
    }
    private void validateAddress(String fullAddress) {
        AddressDO addressDO = addressMapper.selectFirst(new QueryWrapper<AddressDO>().eq("full_address", fullAddress)
                .eq("deleted", 0));
        if (!ObjectUtils.isEmpty(addressDO)) {
            throw exception(FULL_ADDRESS_IS_EXISTS);
        }
    }

    @Override
    public void updateAddress(AddressUpdateReqVO updateReqVO) {
        // 校验存在
        validateAddressExists(updateReqVO.getId());
        // 更新
        AddressDO updateObj = AddressConvert.INSTANCE.convert(updateReqVO);
        //拆分经纬度再插入
        String longitudeLatitude = updateReqVO.getLongitudeLatitude();
        String[] split = longitudeLatitude.split(",");
        if (2 == split.length) {
            updateObj.setLongitude(new BigDecimal(split[0]));
            updateObj.setLatitude(new BigDecimal(split[1]));
        }
        addressMapper.updateById(updateObj);
    }

    @Override
    public void deleteAddress(Long id) {
        // 校验存在
        validateAddressExists(id);
        // 删除
        addressMapper.deleteById(id);
    }

    private void validateAddressExists(Long id) {
        if (addressMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.ADDRESS_NOT_EXISTS);
        }
    }


    @Override
    public AddressRespVO getAddress(Long id) {
        AddressDO addressDO = addressMapper.selectById(id);
        AddressRespVO addressRespVO = AddressConvert.INSTANCE.convert(addressDO);
        //地址对应部门信息判断
        DeptDO deptDO = deptMapper.selectById(addressDO.getDeptId());
        if (!ObjectUtils.isEmpty(deptDO)) {
            addressRespVO.setDeptName(deptDO.getName());
        }
        AdminUserDO creator = adminUserMapper.selectById(addressDO.getCreator());
        AdminUserDO updater = adminUserMapper.selectById(addressDO.getUpdater());
        addressRespVO.setCreatorName(creator.getNickname());
        addressRespVO.setUpdaterName(updater.getNickname());
        //地址对应客户信息判断
        //QueryWrapper<CustomerAdressDO> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("address_id",addressDO.getId());
        //List<CustomerAdressDO> customerAdressDOS = customerAddressMapper.selectList(queryWrapper);
        //Set<Long> collect = customerAdressDOS.stream()
        //        .map(CustomerAdressDO::getCustomerId)
        //        .collect(Collectors.toSet());
        //collect.forEach( iterm -> {
        //    CustomerDO customerDO = customerMapper.selectById(iterm);
        //});
        //地址的拼接经纬度信息
        String LongitudeLatitude = addressRespVO.getLongitude() + "," + addressRespVO.getLatitude();
        addressRespVO.setLongitudeLatitude(LongitudeLatitude);
        return addressRespVO;
    }

    @Override
    public List<AddressRespVO> getAddressList(Collection<Long> ids) {
        List<AddressDO> addressDOS = addressMapper.selectBatchIds(ids);
        List<AddressRespVO> addressRespVOS = AddressConvert.INSTANCE.convertList(addressDOS);
        List<Long> deptIds = addressRespVOS.stream().map(AddressRespVO::getDeptId).distinct().collect(Collectors.toList());
        List<DeptDO> deptList = deptMapper.selectBatchIds(deptIds);
        addressRespVOS.forEach(iterm -> {
            DeptDO deptDO = deptList.stream().filter(item -> item.getId().equals(iterm.getDeptId())).findFirst().orElse(null);
            if (!ObjectUtils.isEmpty(deptDO)) {
                iterm.setDeptName(deptDO.getName());
            }else {
                iterm.setDeptName(null);
            }
            //拼接经纬度信息
            String LongitudeLatitude = iterm.getLongitude() + "," + iterm.getLatitude();
            iterm.setLongitudeLatitude(LongitudeLatitude);
        });
        return addressRespVOS;
    }

    @Override
    public Page<AddressRespVO> getAddressPage(AddressPageReqVO pageReqVO) {
        Page<AddressRespVO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        Page<AddressRespVO> addressRespVOPageResult = addressMapper.selectPageByKeyword(page, pageReqVO);
        addressRespVOPageResult.getRecords().forEach(iterm -> {
            //拼接经纬度信息
            String LongitudeLatitude = iterm.getLongitude() + "," + iterm.getLatitude();
            iterm.setLongitudeLatitude(LongitudeLatitude);
        });
        Long total = addressMapper.selectCount();
        addressRespVOPageResult.setTotal(total);
        return addressRespVOPageResult;
    }

    @Override
    public List<AddressExcelVO> getAddressList(AddressExportReqVO exportReqVO) {
        List<AddressExcelVO> datas = addressMapper.selectExportList(exportReqVO);
        return datas;
    }

    @Override
    public void batchUpdateAddress(List<AddressUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (AddressUpdateReqVO updateReqVO : updateReqVOList) {
            updateAddress(updateReqVO);
        }
    }

    @Override
    public void batchDeleteAddress(List<Long> ids) {
        // 在这里处理批量删除逻辑
        addressMapper.deleteBatchIds(ids);
    }

    @Override
    public List<AddressDO> getAddressList(Long customerId) {
        QueryWrapper<AddressDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", customerId);  //代办
        List<AddressDO> list = addressMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public void updateStatus(Long id) {
        UpdateWrapper<AddressDO> updateWrapper = new UpdateWrapper<>();
        AddressDO addressDO = addressMapper.selectById(id);
        updateWrapper.eq("id", id);
        Byte status = addressDO.getStatus();
        //状态修改取反
        if (status == 0) {
            // 设置要更新的字段和值
            updateWrapper.set("status", 1);
        } else if (status == 1) {
            updateWrapper.set("status", 0);
        } else {
            throw new RuntimeException("货币状态错误！");
        }
        int rowsAffected = addressMapper.update(null, updateWrapper); // 使用update方法更新字段
    }

    @Override
    public List<AddressRespVO> getAddressByFull(String addressName) {
        QueryWrapper<AddressDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("full_address", addressName);
        List<AddressDO> addressDOS = addressMapper.selectList(queryWrapper);
        List<AddressRespVO> addressRespVOS = AddressConvert.INSTANCE.convertList(addressDOS);
        return addressRespVOS;
    }

    @Override
    public Map<String, Object> getAddressDistrict(GaoDeProperties gaoDeProperties) throws JsonProcessingException {
        String key = gaoDeProperties.getKey();
        String keywords = gaoDeProperties.getKeywords();
        String subdistrict = gaoDeProperties.getSubdistrict();
        // url信息
        String url = "https://restapi.amap.com/v3/config/district";
        StringBuilder builder = new StringBuilder();
        String requestUrl = builder.append(url)
                .append("?key=" + key)
                .append("&keywords=" + keywords)
                .append("&subdistrict=" + subdistrict).toString();
        // 使用 exchange 方法来发送带有 HttpHeaders 的请求
        JSONObject response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, JSONObject.class).getBody();
        Map<String, Object> result = response.toBean(Map.class);
        //ObjectMapper objectMapper = new ObjectMapper();
        //List<DistrictsDto> districtsDtoList = new ArrayList<>();
        //JsonNode jsonNode = null;
        //if("1".equals(result.get("status"))){
        //    jsonNode = objectMapper.readTree(result.get("districts").toString());
        //    Iterator<JsonNode> iterator = jsonNode.iterator();
        //    while (iterator.hasNext()) {
        //        JsonNode districtJson = iterator.next();
        //        DistrictsDto districtsDto2 = objectMapper.treeToValue(districtJson, DistrictsDto.class);
        //        districtsDtoList.add(districtsDto2);
        //    }
        //}
        return result;
    }

    @Override
    public List<AddressRespVO> getCustomerAndAddress(String customerName) {

        return addressMapper.getLoadingCustomerAndAddress(customerName);
    }

    @Override
    public List<AddressRespVO> getUnloadCustomerAndAddress(String customerName) {
        return addressMapper.getUnloadCustomerAndAddress(customerName);
    }

    @Override
    public List<AddressByCustomerIdDTO> getAddressByCustomerId(Long customerId) {

        return addressMapper.getAddressByCustomerId(customerId);
    }

    @Override
    public List<Map<String, Object>> getAddressListByCustomerId(CommonQueryParam param) {
        List<AddressDO> addressList = addressMapper.getAddressListByCustomerId(param);
        List<Map<String, Object>> list = new ArrayList<>();
        for (AddressDO addressDO : addressList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", addressDO.getId());
            map.put("customerId", addressDO.getCustomerId());
            map.put("address", addressDO.getFullAddress());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> selectAddressNameDetailsMap() {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        List<CustomerDO> addressDOS = addressMapper.selectAddressNameDetailsMap();
        for (CustomerDO customerDO : addressDOS) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", customerDO.getId());
            map.put("customerName", customerDO.getCustomerName());
            map.put("customerType", customerDO.getCustomerType());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> selectAddressByCustomerId(Long customerId) {

        MPJLambdaWrapper<CustomerAdressDO> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.leftJoin(CustomerDO.class, CustomerDO::getId, CustomerAdressDO::getCustomerId)
                .leftJoin(AddressDO.class, AddressDO::getId, CustomerAdressDO::getAddressId)
                .select(AddressDO::getFullAddress)
                .eq(CustomerDO::getDeleted, 0)
                .eq(AddressDO::getDeleted, 0)
                .eq(CustomerAdressDO::getCustomerId, customerId);

        List<Map<String, Object>> resultList = customerAddressMapper.selectMaps(mpjLambdaWrapper);

        if (CollectionUtils.isNotEmpty(resultList)) {
            return resultList;
        }
        return Collections.emptyList();
    }
}
