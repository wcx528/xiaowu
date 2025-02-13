package com.fmyd888.fengmao.module.information.job;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.mybatis.core.util.MyBatisUtils;
import com.fmyd888.fengmao.framework.quartz.core.handler.JobHandler;
import com.fmyd888.fengmao.framework.tenant.core.job.TenantJob;
import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.dto.CarMileageDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.CarMileageDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LocationColdRecordDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LocationRecordDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.locationRecord.CarMileageMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.locationRecord.LatestLocationRecordMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.locationRecord.LocationColdRecordMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.locationRecord.LocationRecordMapper;
import com.fmyd888.fengmao.module.information.framework.location.config.LocationProperties;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类功能描述：车辆里程及轨迹记录冷备份定时任务
 *
 * @author 小逺
 * @date 2024/07/24
 */
@Slf4j
@Component
public class CarMileAgeRecordPullJob implements JobHandler {
    @Resource
    private CarMileageMapper carMileageMapper;
    @Resource
    private CarMapper carMapper;
    @Resource
    private LocationProperties locationProperties;
    @Resource
    private LocationRecordMapper locationRecordMapper;
    @Resource
    private LocationColdRecordMapper locationColdRecordMapper;

    @Override
    @TenantJob
    public String execute(String param) throws Exception {
        //轨迹表数据备份冷表
        try {
            LocalDateTime seventyDaysAgo = LocalDate.now().minusDays(7).atStartOfDay();
            LambdaQueryWrapper<LocationRecordDO> wrapper = Wrappers.lambdaQuery();
            wrapper.lt(LocationRecordDO::getCreateTime, seventyDaysAgo);
            List<LocationRecordDO> copyData = locationRecordMapper.selectList(wrapper);
            if (ObjectUtil.isNotEmpty(copyData)) {
                //备份
                List<LocationColdRecordDO> copyDatas = BeanUtils.toBean(copyData, LocationColdRecordDO.class);
                copyDatas.forEach(p -> p.setId(null));
                MyBatisUtils.processInBatches(copyDatas, 145, sublist -> {
                    locationColdRecordMapper.batchBack(sublist);
                });
                List<Long> ids = copyData.stream().map(LocationRecordDO::getId).collect(Collectors.toList());
                MyBatisUtils.processInBatches(ids, 2000, sublist -> {
                    locationRecordMapper.deletePhysicallyByIds(sublist);
                });
            }
        } catch (Exception ex) {
            log.info("备份轨迹表热表数据失败，详细信息：" + ex.getMessage());
        }
        //获取所有车辆
        try {
            List<CarDO> carList = carMapper.selectEffectiveCarList();
            carList = carList.stream().filter(p -> p.getMotorvehicleNumber() != null).collect(Collectors.toList());
            if (carList.isEmpty()) {
                log.info("没有需要拉取位置的车辆");
                return "没有需要拉取位置的车辆";
            }
            List<CarMileageDO> carMileageList = carMileageMapper.selectList(CarMileageDO::getRecordDate, LocalDate.now().minusDays(1).atStartOfDay());
            List<List<CarDO>> carNoLists = CollectionUtils.splitList(carList, 10);
            for (List<CarDO> item : carNoLists) {
                pullMileages(item, carMileageList, param);
            }
        } catch (Exception ex) {
            log.info("获取里程失败，详细信息：" + ex.getMessage());
        }
        return null;
    }

    @Async
    public void pullMileages(List<CarDO> carList, List<CarMileageDO> carMileageList, String param) {
        List<String> carNos = carList.stream().map(CarDO::getMotorvehicleNumber).collect(Collectors.toList());
        //从昨天查起
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        CarMileageDTO pullResult = mileageApiPost(carNos, "GetVehicleMileage", "2", yesterday.format(formatter));
        if (!pullResult.getRet().equals(0)) {
            log.error("查询GPS位置信息报错：" + pullResult.getMsg());
            return;
        }
        List<CarMileageDO> addRecords = new ArrayList<>();
        List<CarMileageDO> updateRecords = new ArrayList<>();
        for (CarDO item : carList) {
            try {
                //查找本地记录，本地记录存在则更新，不存在则插入
                Optional<CarMileageDO> dbMileageOpt = carMileageList.stream()
                        .filter(p -> p.getCarId().equals(item.getId()))
                        .findFirst();

                //获取车辆里程
                Optional<CarMileageDTO.MileAgeContentDTO> mileageOpt = pullResult.getData().stream()
                        .filter(p -> p.getPlateNum().equals(item.getMotorvehicleNumber()))
                        .findFirst();
                if (!mileageOpt.isPresent()) {
                    continue;
                }
                CarMileageDTO.MileAgeContentDTO mileage = mileageOpt.get();
                //本地已有记录，更新
                if (dbMileageOpt.isPresent()) {
                    CarMileageDO carMileage = dbMileageOpt.get();
                    carMileage.setMileage((mileage.getMileage()));
                    updateRecords.add(carMileage);
                    continue;
                }
                CarMileageDO entity = new CarMileageDO();
                entity.setMileage(mileage.getMileage());
                entity.setRecordDate(yesterday.atStartOfDay());
                entity.setCarId(item.getId());
                entity.setCreator(param);
                entity.setUpdater(param);
                addRecords.add(entity);
            } catch (Exception ex) {
                //只记录日志，不影响后续运行
                log.error(ex.getMessage());
            }
        }

        //写入数据库
        try {
            if (!addRecords.isEmpty())
                carMileageMapper.batchInsertX(addRecords);
            if (!updateRecords.isEmpty())
                carMileageMapper.updateBatch(updateRecords);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

    }

    public CarMileageDTO mileageApiPost(List<String> carNos, String type, String carColor, String queryDate) {
        try {
            String requestStr = getPostInfos(carNos, type, carColor, queryDate);
            HttpResponse response = HttpRequest.post(locationProperties.getUrl())
                    .setConnectionTimeout(-1)
                    .form("request", requestStr)
                    .execute();

            String responseStr = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseStr, CarMileageDTO.class);
        } catch (Exception ex) {
            log.error("获取gps里程信息-封装接口请求体报错，详细信息：" + ex.getMessage());
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
            log.error("获取gps里程信息-封装接口请求体报错，详细信息：" + ex.getMessage());
            return "获取gps里程信息-封装接口请求体报错，详细信息：" + ex.getMessage();
        }
    }
}
