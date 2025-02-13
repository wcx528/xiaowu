package com.fmyd888.fengmao.module.information.job;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.quartz.core.handler.JobHandler;
import com.fmyd888.fengmao.framework.tenant.core.job.TenantJob;
import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.dto.LocationCarInfoDTO;
import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.dto.LocationDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LatestLocationRecordDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LocationRecordDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.locationRecord.LatestLocationRecordMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.locationRecord.LocationRecordMapper;
import com.fmyd888.fengmao.module.information.framework.location.config.LocationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类功能描述：车辆实时位置坐标拉取定时调度
 *
 * @author 小逺
 * @date 2024/06/19
 */
@Slf4j
@Component
public class CarPositionPullJob implements JobHandler {
    @Resource
    private CarMapper carMapper;
    @Resource
    private LocationRecordMapper locationRecordMapper;
    @Resource
    private LocationProperties locationProperties;
    @Resource
    private LatestLocationRecordMapper latestLocationRecordMapper;


    @Override
    @TenantJob
    public String execute(String param) throws Exception {
        //获取所有车辆
        List<CarDO> carList = carMapper.selectEffectiveCarList();
        carList = carList.stream().filter(p -> p.getMotorvehicleNumber() != null).collect(Collectors.toList());
        if (carList.isEmpty()) {
            log.info("没有需要拉取位置的车辆");
            return "没有需要拉取位置的车辆";
        }
        log.info("开始获取车辆gps,param参数值：" + param);
        List<List<CarDO>> carNoLists = CollectionUtils.splitList(carList, 10);
        //获取车辆最新位置信息，用于判断是否需要更新
        //List<LocationCarInfoDTO> locationCarInfos = locationRecordMapper.selectLatestAddresses(carList.stream().map(CarDO::getId).collect(Collectors.toList()));
        LambdaQueryWrapper<LatestLocationRecordDO> wrapper = Wrappers.lambdaQuery();
        wrapper.select(LatestLocationRecordDO::getId, LatestLocationRecordDO::getCarId, LatestLocationRecordDO::getLongitude, LatestLocationRecordDO::getLatitude);
        List<LatestLocationRecordDO> locationCarInfos = latestLocationRecordMapper.selectList(wrapper);
        for (List<CarDO> item : carNoLists) {
            pullPositions(item, locationCarInfos, param);
        }
        return null;
    }

    @Async
    public void pullPositions(List<CarDO> carList, List<LatestLocationRecordDO> locationCarInfos, String param) {
        List<String> carNos = carList.stream().map(CarDO::getMotorvehicleNumber).collect(Collectors.toList());
        LocationDTO pullResult = locationApiPost(carNos, "GetVehicleGpsInfoEx", "2");
        if (!pullResult.getRet().equals(0)) {
            log.error("查询GPS位置信息报错：" + pullResult.getMsg());
            return;
        }
        List<LocationRecordDO> lstRecord = new ArrayList<>();
        List<LatestLocationRecordDO> addRecords = new ArrayList<>();
        List<LatestLocationRecordDO> updateRecords = new ArrayList<>();
        for (CarDO item : carList) {
            // 查询本地重复位置信息
            Optional<LatestLocationRecordDO> dbLocationOpt = locationCarInfos.stream()
                    .filter(p -> p.getCarId().equals(item.getId()))
                    .findFirst();

            Optional<LocationDTO.LocationContentDTO> locationOpt = pullResult.getData().stream()
                    .filter(p -> p.getPlateNum().equals(item.getMotorvehicleNumber()))
                    .findFirst();

            if (!locationOpt.isPresent()) {
                continue;
            }

            LocationDTO.LocationContentDTO location = locationOpt.get();
            if (dbLocationOpt.isPresent()) {
                LatestLocationRecordDO dbLocation = dbLocationOpt.get();
                //坐标相同，不做更新
                if (dbLocation.getLongitude().equals(location.getLongitude()) && dbLocation.getLatitude().equals(location.getLatitude())) {
                    continue;
                }
                //记录更新车辆最新位置记录表
                LatestLocationRecordDO latestRecord = dbLocationOpt.get();
                latestRecord.setCarId(item.getId());
                latestRecord.setLongitude(location.getLongitude());
                latestRecord.setLatitude(location.getLatitude());
                latestRecord.setCreateTime(LocalDateTime.parse(location.getGpsTime(), DateTimeFormatter.ofPattern("uuuu/M/d H:m:s")));
                latestRecord.setAddress(location.getAddressInfo());
                latestRecord.setTotalMileage(location.getMileage());
                latestRecord.setSpeed(location.getSpeed());
                latestRecord.setDrct(location.getDirection());
                latestRecord.setUpdateTime(LocalDateTime.now());
                latestRecord.setUpdater(param);
                updateRecords.add(latestRecord);
            } else {// 最新位置表不存在这辆车，则新增
                //记录车辆最新位置记录表
                LatestLocationRecordDO latestRecord = new LatestLocationRecordDO();
                latestRecord.setCarId(item.getId());
                latestRecord.setLongitude(location.getLongitude());
                latestRecord.setLatitude(location.getLatitude());
                latestRecord.setCreateTime(LocalDateTime.parse(location.getGpsTime(), DateTimeFormatter.ofPattern("uuuu/M/d H:m:s")));
                latestRecord.setAddress(location.getAddressInfo());
                latestRecord.setTotalMileage(location.getMileage());
                latestRecord.setSpeed(location.getSpeed());
                latestRecord.setDrct(location.getDirection());
                latestRecord.setCreator(param);
                latestRecord.setUpdater(param);
                addRecords.add(latestRecord);
            }
            try {
                // 数据写入，记录位置记录
                LocationRecordDO record = new LocationRecordDO();
                record.setCarId(item.getId());
                record.setLongitude(location.getLongitude());
                record.setLatitude(location.getLatitude());
                record.setCreateTime(LocalDateTime.parse(location.getGpsTime(), DateTimeFormatter.ofPattern("uuuu/M/d H:m:s")));
                record.setAddress(location.getAddressInfo());
                record.setTotalMileage(location.getMileage());
                record.setSpeed(location.getSpeed());
                record.setDrct(location.getDirection());
                record.setCreator(param);
                record.setUpdater(param);
                lstRecord.add(record);

            } catch (Exception ex) {
                log.error(ex.getMessage());
            }

        }
        //写入数据库
        try {
            if (!lstRecord.isEmpty())
                locationRecordMapper.batchInsertLocation(lstRecord);
            if (!addRecords.isEmpty())
                latestLocationRecordMapper.batchInsertX(addRecords);
            if (!updateRecords.isEmpty())
                latestLocationRecordMapper.updateBatch(updateRecords);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public LocationDTO locationApiPost(List<String> carNos, String type, String carColor) {
        try {
            String requestStr = getPostInfos(carNos, type, carColor, "");
            HttpResponse response = HttpRequest.post(locationProperties.getUrl())
                    .setConnectionTimeout(-1)
                    .form("request", requestStr)
                    .execute();

            String responseStr = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseStr, LocationDTO.class);
        } catch (Exception ex) {
            log.error("获取gps信息-封装接口请求体报错，详细信息：" + ex.getMessage());
            return null;
        }
    }

    /**
     * 功能描述：封装接口请求体
     *
     * @param carNos    车号
     * @param type      接口类型
     * @param carColor  车辆颜色
     * @param queryDate 查询数据，一般传空s
     * @return {@link String }
     * @author 小逺
     * @date 2024/06/20
     */
    public String getPostInfos(List<String> carNos, String type, String carColor, String queryDate) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode request = objectMapper.createObjectNode();
            request.put("Action", type);
            request.put("UserId", locationProperties.getUserId());
            request.put("Pwd", DigestUtil.md5Hex(locationProperties.getPassword()).toUpperCase());
            request.put("QueryDate", queryDate);

            List<ObjectNode> vehicles = carNos.stream()
                    .map(carNo -> {
                        ObjectNode carInfo = objectMapper.createObjectNode();
                        carInfo.put("PlateNum", carNo);
                        carInfo.put("ColorCode", carColor);
                        return carInfo;
                    }).collect(Collectors.toList());

            request.putPOJO("Vehicles", vehicles);
            return objectMapper.writeValueAsString(request);
        } catch (Exception ex) {
            log.error("获取gps信息-封装接口请求体报错，详细信息：" + ex.getMessage());
            return "获取gps信息-封装接口请求体报错，详细信息：" + ex.getMessage();
        }
    }
}
