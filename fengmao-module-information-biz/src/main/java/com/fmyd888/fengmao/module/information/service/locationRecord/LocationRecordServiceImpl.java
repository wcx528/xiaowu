package com.fmyd888.fengmao.module.information.service.locationRecord;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.web.core.util.WebFrameworkUtils;
import com.fmyd888.fengmao.module.information.api.car.CarApi;
import com.fmyd888.fengmao.module.information.api.car.dto.CarDTO;
import com.fmyd888.fengmao.module.information.controller.admin.car.dto.CarInfoDTO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.dto.LocationDTO;
import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.dto.LocationGaodeInfo;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportCarRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LatestLocationRecordDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.MobileLocationRecordDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.locationRecord.LatestLocationRecordMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.locationRecord.MobileLocationRecordMapper;
import com.fmyd888.fengmao.module.information.framework.location.config.GoDeProperties;
import com.fmyd888.fengmao.module.information.service.car.CarService;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.api.user.dto.AdminUserRespDTO;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LocationRecordDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import com.fmyd888.fengmao.module.information.dal.mysql.locationRecord.LocationRecordMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 车辆GPS定位 Service 实现类
 *
 * @author 小逺
 */
@Service
@Validated
public class LocationRecordServiceImpl implements LocationRecordService {

    @Resource
    private LocationRecordMapper locationRecordMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private CarMapper carMapper;
    @Resource
    private GoDeProperties goDeProperties;
    @Resource
    private LatestLocationRecordMapper latestLocationRecordMapper;
    @Resource
    private CarService carService;
    @Resource
    private MobileLocationRecordMapper mobileLocationRecordMapper;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Long createLocationRecord(LocationRecordSaveReqVO createReqVO) {
        // 插入
        LocationRecordDO locationRecord = BeanUtils.toBean(createReqVO, LocationRecordDO.class);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        locationRecord.setDeptId(loginUserDeptId);
        locationRecordMapper.insert(locationRecord);


        // 返回
        return locationRecord.getId();
    }

    @Override
    public void updateLocationRecord(LocationRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateLocationRecordExists(updateReqVO.getId());
        // 更新
        LocationRecordDO updateObj = BeanUtils.toBean(updateReqVO, LocationRecordDO.class);
        locationRecordMapper.updateById(updateObj);

    }

    @Override
    public void deleteLocationRecord(Long id) {
        // 校验存在
        validateLocationRecordExists(id);
        // 删除
        locationRecordMapper.deleteById(id);
    }

    private void validateLocationRecordExists(Long id) {
        if (locationRecordMapper.selectById(id) == null) {
            throw exception(LOCATION_RECORD_NOT_EXISTS);
        }
    }


    @Override
    public LocationRecordDO getLocationRecord(Long id) {
        return locationRecordMapper.selectById(id);
    }

    @Override
    public List<LocationRecordDO> getLocationRecordList(Collection<Long> ids) {
        return locationRecordMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<LatestLocationRecordDO> getLocationRecordPage(LocationRecordPageReqVO pageReqVO) {
//        PageResult<LocationRecordDO> pageResult = locationRecordMapper.selectPage(pageReqVO);
//        if (ObjectUtil.isNotEmpty(pageResult.getList())) {
//            List<CarDTO> carNameList = carApi.selectCarNameInfos(pageResult.getList().stream().map(LocationRecordDO::getCarId).collect(Collectors.toList()));
//            for (LocationRecordDO item : pageResult.getList()) {
//                CarDTO obj = carNameList.stream().filter(p -> p.getId() == item.getCarId()).findFirst().orElse(null);
//                if (ObjectUtil.isNotEmpty(obj))
//                    item.setCarName(obj.getMotorvehicleNumber());
//            }
//        }
        PageResult<LatestLocationRecordDO> pageResult = latestLocationRecordMapper.selectLatestLocationRecordPage(pageReqVO);
        if (!pageResult.getList().isEmpty()) {
            List<Long> ids = pageResult.getList().stream().map(p -> p.getCarId()).collect(Collectors.toList());
            List<CarInfoDTO> carInfos = carMapper.selectCarInfosByIds(ids);
            if (carInfos.isEmpty())
                return pageResult;
            List<Long> userIds = carInfos.stream().flatMap(carInfo -> Arrays.stream(new Long[]{carInfo.getMainId(), carInfo.getDeputyId(), carInfo.getEscortId()}))
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            List<AdminUserRespDTO> userList = adminUserApi.getUserList(userIds);
            for (LatestLocationRecordDO item : pageResult.getList()) {
                CarInfoDTO carInfo = carInfos.stream().filter(p -> p.getId().equals(item.getCarId())).findFirst().orElse(null);
                if (ObjectUtil.isEmpty(carInfo))
                    continue;
                item.setCompanyName(carInfo.getCompanyName());
                item.setCarName(carInfo.getName());
                item.setFleetName(carInfo.getFleetName());
                AdminUserRespDTO mainUser = userList.stream().filter(p -> p.getId().equals(carInfo.getMainId())).findFirst().orElse(null);
                AdminUserRespDTO deputyUser = userList.stream().filter(p -> p.getId().equals(carInfo.getDeputyId())).findFirst().orElse(null);
                AdminUserRespDTO escortUser = userList.stream().filter(p -> p.getId().equals(carInfo.getEscortId())).findFirst().orElse(null);
                if (ObjectUtil.isNotEmpty(mainUser))
                    item.setMainName(mainUser.getNickname());
                if (ObjectUtil.isNotEmpty(deputyUser))
                    item.setDeputyName(deputyUser.getNickname());
                if (ObjectUtil.isNotEmpty(escortUser))
                    item.setEscortName(escortUser.getNickname());
            }
            ;
        }
        return pageResult;
    }

    @Override
    public List<LocationRecordDO> getLocationRecordList(LocationRecordListReqVO listReqVO) {
        return locationRecordMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateLocationRecord(List<LocationRecordSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteLocationRecord(List<Long> ids) {
        // 在这里处理批量删除逻辑
        locationRecordMapper.deleteBatchIds(ids);
    }

    @Override
    public List<LocationRecordExcelVO> importPreviewList(List<LocationRecordExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(LOCATION_RECORD_IMPORT_LIST_IS_EMPTY);
        }

        List<LocationRecordExcelVO> excelVo = BeanUtils.toBean(importDatas, LocationRecordExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(LOCATION_RECORD_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(LocationRecordExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
            throw exception(LOCATION_RECORD_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(LOCATION_RECORD_IMPORT_PORT_REQUIRE);
        //以下是示例，补充逻辑时请替换成自己书写的逻辑
        //ImportResult result = ImportResult.builder()
        //.total(importReqVo.getImportDatas().size())
        //.importCount(0)
        //.failCount(importReqVo.getImportDatas().size())//假设这里假设都导入失败
        //.success(false)
        //.data(importReqVo.getImportDatas())
        //.build();
        //return result;
    }

    @Override
    public LocationRecordRespVO getCarLocation(Long carId) {
        LocationRecordDO carLocationInfo = locationRecordMapper.selectLocationByCarId(carId);
        LocationRecordRespVO resp = BeanUtils.toBean(carLocationInfo, LocationRecordRespVO.class);
        if (ObjectUtil.isNotEmpty(carLocationInfo)) {
            //查找车辆
            CarDO car = carMapper.selectCarInfos(carId);
            if (ObjectUtil.isNotEmpty(car)) {
                //resp.setCarName(car.getMotorvehicleNumber());
                //resp.setCompanyName(car.getCompanyName());
            }
        }
        return resp;
    }

    @Override
    public List<List<BigDecimal>> getCarTrajectory(LocationRecordListReqVO reqVO) {
        if (ObjectUtil.isEmpty(reqVO.getCarId()) || ObjectUtil.isEmpty(reqVO.getStartTime()) || ObjectUtil.isEmpty(reqVO.getEndTime()))
            return new ArrayList<>();
        LambdaQueryWrapper<LocationRecordDO> wrapper = Wrappers.lambdaQuery();
        wrapper.select(LocationRecordDO::getLongitude, LocationRecordDO::getLatitude, LocationRecordDO::getCreateTime)
                .eq(LocationRecordDO::getCarId, reqVO.getCarId())
                .between(LocationRecordDO::getCreateTime, reqVO.getStartTime(), reqVO.getEndTime())
                .orderByAsc(LocationRecordDO::getId);
        List<LocationRecordDO> locationRecordList = locationRecordMapper.selectList(wrapper);
        if (locationRecordList.isEmpty())
            return new ArrayList<>();
        List<List<BigDecimal>> coordinates = locationRecordList.stream()
                .map(record -> Arrays.asList(record.getLongitude(), record.getLatitude()))
                .collect(Collectors.toList());
        List<List<BigDecimal>> newCoordinates = GPSToGaoDe(coordinates);
        List<String> collect = newCoordinates.stream().map(p -> p.get(0).toString() + "," + p.get(1).toString()).collect(Collectors.toList());
        if (collect.isEmpty())
            return new ArrayList<>();
        List<List<String>> lists = CollectionUtils.splitList(collect, 320);
        try {
            List<List<BigDecimal>> data = new ArrayList<>();
            for (List<String> item : lists) {
                String origin = item.get(0);
                String destination = item.get(item.size() - 1);
                //取16个途经点
                List<String> elements = CollectionUtils.getEvenlyDistributedElements(item, 16);
                String joinStr = String.join(";", elements);

                String url = String.format("%s?key=%s&origin=%s&destination=%s&show_fields=polyline&waypoints=%s", goDeProperties.getDrivingUrl(), goDeProperties.getKey(), origin, destination, joinStr);
                HttpResponse response = HttpRequest.get(url)
                        .setConnectionTimeout(-1)
                        .contentType("text/html;charset=UTF-8")
                        .execute();
                String retString = response.body();
                JsonNode info = objectMapper.readTree(retString);
                if (!"OK".equals(info.path("info").asText())) {
                    return newCoordinates;
                } else {
                    JsonNode steps = info.path("route").path("paths").get(0).path("steps");
                    for (JsonNode step : steps) {
                        String[] lals = step.path("polyline").asText().split(";");
                        for (String item2 : lals) {
                            String[] lal = item2.split(",");
                            data.add(new ArrayList<>(Arrays.asList(new BigDecimal(lal[0]), new BigDecimal(lal[1]))));
                        }
                    }
                }
            }
            return data;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public Long createMobileLocationRecord(List<LocationRecordMobileSaveReqVO> createReqVO) {
        if (ObjectUtil.isNotEmpty(createReqVO)) {
            List<MobileLocationRecordDO> locationRecordList = BeanUtils.toBean(createReqVO, MobileLocationRecordDO.class);
            Long loginUserId = WebFrameworkUtils.getLoginUserId();
            List<CarDO> allCarList = carService.getAllCarList();
            CarDO car = allCarList.stream().filter(p -> ObjectUtil.equal(p.getMainId(), loginUserId) || ObjectUtil.equal(p.getDeputyId(), loginUserId) || ObjectUtil.equal(p.getEscortId(), loginUserId)).findFirst().orElse(null);
            if (ObjectUtil.isNotEmpty(car)) {
                List<MobileLocationRecordDO> newLocationRecordList = new ArrayList<>();
                for (MobileLocationRecordDO locationRecord : locationRecordList) {
                    locationRecord.setCarId(car.getId());
                    locationRecord.setCreator(loginUserId.toString());
                    locationRecord.setUpdater(loginUserId.toString());
                    newLocationRecordList.add(locationRecord);
                }
                mobileLocationRecordMapper.batchInsertX(newLocationRecordList);
            }
        }
        return null;
    }

    /**
     * 功能描述：GPS坐标转高德地图坐标
     *
     * @param coordinates
     * @return {@link List }<{@link List }<{@link BigDecimal }>>
     * @author 小逺
     * @date 2024/06/21
     */
    private List<List<BigDecimal>> GPSToGaoDe(List<List<BigDecimal>> coordinates) {
        try {
            List<List<BigDecimal>> result = new ArrayList<>();
            String strCoordinate = coordinates.stream()
                    .map(item -> item.get(0) + "," + item.get(1))
                    .collect(Collectors.joining("|"));

            String url = String.format("%s?key=%s&locations=%s&coordsys=gps", goDeProperties.getConvertUrl(), goDeProperties.getKey(), strCoordinate);
            HttpResponse response = HttpRequest.get(url)
                    .setConnectionTimeout(-1)
                    .contentType("text/html;charset=UTF-8")
                    .execute();
            String retString = response.body();
            LocationGaodeInfo resp = JSONUtil.toBean(retString, LocationGaodeInfo.class);

            if (!"1".equals(resp.getStatus())) {
                return result;
            }

            String[] convertedLocations = resp.getLocations().split(";");
            if (convertedLocations.length == coordinates.size()) {
                for (int i = 0; i < convertedLocations.length; i++) {
                    String[] coords = convertedLocations[i].split(",");
                    result.add(Arrays.asList(new BigDecimal(coords[0]), new BigDecimal(coords[1])));
                }
            }
            return result;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
}