package com.fmyd888.fengmao.module.information.service.transportmanger;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryBaseParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.common.util.object.ObjectUtils;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.api.transportmanger.TransportmangerApi;
import com.fmyd888.fengmao.module.information.api.transportmanger.dto.TransportmangerDTO;
import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleSimpleRespVO;
import com.fmyd888.fengmao.module.information.convert.transportManger.TransportDetailConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.fleet.FleetDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.purchasemanger.PurchaseMangerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportcar.TransportCarDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.fleet.FleetMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.mainvehicle.MainVehicleMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.purchasemanger.PurchaseMangerMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.transportcar.TransportCarMapper;
import com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailSaveReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailSimRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.*;
import com.fmyd888.fengmao.module.information.convert.transportManger.TransportMangerConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportdetail.TransportDetailDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportmanger.TransportMangerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.commodity.CommodityMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.transportdetail.TransportDetailMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.transportmanger.TransportMangerMapper;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.information.service.mainvehicle.MainVehicleService;
import com.fmyd888.fengmao.module.information.service.transportdetail.TransportDetailService;
import com.fmyd888.fengmao.module.infra.api.file.DTO.FileDTO;
import com.fmyd888.fengmao.module.infra.api.file.FileApi;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.infra.service.file.FileService;

import com.fmyd888.fengmao.module.plan.enums.api.businessPlan.BusinessPlanApi;
import com.fmyd888.fengmao.module.plan.enums.api.businessPlan.dto.BusinessPlanRespDTO;
import com.fmyd888.fengmao.module.plan.enums.api.deliveryPlan.DeliveryPlanApi;
import com.fmyd888.fengmao.module.plan.enums.api.deliveryPlan.dto.DeliveryPlanDTO;
import com.fmyd888.fengmao.module.plan.enums.mapperapi.BusinessPlanDO;
import com.fmyd888.fengmao.module.plan.enums.mapperapi.BusinessPlanMapper1;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.invalidParamException;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 运输证管理 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class TransportMangerServiceImpl implements TransportMangerService {

    @Resource
    private TransportMangerMapper transportMangerMapper;
    @Resource
    private TransportDetailMapper transportDetailMapper;
    @Resource
    private TransportDetailService transportDetailService;
    @Resource
    private FileService fileService;
    @Resource
    private FileApi fileApi;
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private EncodingRulesService encodingRulesService;
    @Resource
    private TransportmangerApi transportmangerApi;
    @Resource
    private DeliveryPlanApi deliveryPlanApi;
    @Resource
    private BusinessPlanApi businessPlanApi;
    @Resource
    private BusinessPlanMapper1 businessPlanMapper1;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private FleetMapper fleetMapper;
    @Resource
    private CarMapper carMapper;
    @Resource
    private MainVehicleMapper mainVehicleMapper;
    @Resource
    private PurchaseMangerMapper purchaseMangerMapper;
    @Resource
    private MainVehicleService mainVehicleService;
    @Resource
    private TransportCarMapper transportCarMapper;

    @Override
    public TransportMangerSaveReqVO getTransportManger02(Long id) {
        TransportMangerDO transportMangerDO = transportMangerMapper.selectById(id);
        if (ObjectUtil.isEmpty(transportMangerDO)) {
            throw exception(PURCHASE_MANGER_NOT_EXISTS);
        }
        TransportMangerSaveReqVO convert = TransportMangerConvert.INSTANCE.convert02(transportMangerDO);
        List<TransportDetailSaveReqVO> transportDetailLis = transportDetailService.getDetailListByTransportId(id);
        convert.setTransportDetails(transportDetailLis);
        return convert;
    }

    @Override
    public List<TransportMangerSimpleRespVO> getTransportMangerList() {
        List<TransportMangerSimpleRespVO> list = new ArrayList<>();
        //1、全部信息
        List<TransportMangerDO> transportMangerDOS = transportMangerMapper.selectList();
        if (!CollUtil.isEmpty(transportMangerDOS)) {
            transportMangerDOS.forEach(iterm -> {
                TransportMangerSimpleRespVO transportMangerSimpleRespVO = new TransportMangerSimpleRespVO();
                transportMangerSimpleRespVO.setId(iterm.getId());
                list.add(transportMangerSimpleRespVO);
            });
        }
        return list;
    }

    @Override
    public List<TransportCarRespVO> getTransportCarList(CommonQueryBaseParam param) {
        if( ObjectUtil.isEmpty(param.getId()))
            return new ArrayList<>();
        //获取所有车辆信息
        List<TransportCarRespVO> carInfos = carMapper.selectCarInfos();
        //获取运输证车辆id列表
        DeliveryPlanDTO plan = deliveryPlanApi.getDeliveryPlanById(param.getId());
        if( ObjectUtil.isEmpty(plan))
            return carInfos;
        BusinessPlanRespDTO businessPlan = businessPlanApi.getBusinessPlanById(plan.getBusinessPlanId());
        if( ObjectUtil.isEmpty(businessPlan))
            return carInfos;
        TransportmangerDTO transportmanger = transportmangerApi.getTransportmangerById(businessPlan.getTransportMangerId());
        if( ObjectUtil.isEmpty(transportmanger))
            return carInfos;
        //筛选出运输证车辆
        List<Long> carIds = transportmanger.getCarIds();
        if(carIds.isEmpty())
            return carInfos;
        return carInfos.stream().filter(p -> carIds.contains(p.getId())).collect(Collectors.toList());
    }

    @Override
    public List<HashMap<String, Object>> selectTransportNo() {
        LambdaQueryWrapper<TransportMangerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransportMangerDO::getDeleted, 0)
                .select(TransportMangerDO::getTransportCode, TransportMangerDO::getId);
        List<TransportMangerDO> transportMangerDOS = transportMangerMapper.selectList(queryWrapper);

        if (CollUtil.isEmpty(transportMangerDOS)){
            return new ArrayList<>();
        }
        List<HashMap<String, Object>> result = transportMangerDOS.stream()
                .map(transportMangerDO -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("transportCode", transportMangerDO.getTransportCode());
                    map.put("id", transportMangerDO.getId());
                    return map;
                })
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public void updateTransportDetailStatus() {
        // 查询所有的运输证
        LambdaQueryWrapper<TransportMangerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransportMangerDO::getDeleted, 0);
        List<TransportMangerDO> transportMangerDOS = transportMangerMapper.selectList(queryWrapper);

        if (CollUtil.isNotEmpty(transportMangerDOS)) {
            List<Long> transportIds = transportMangerDOS.stream()
                    .map(TransportMangerDO::getId)
                    .collect(Collectors.toList());

            // 查询所有相关的运输明细
            LambdaQueryWrapper<TransportDetailDO> detailQueryWrapper = new LambdaQueryWrapper<>();
            detailQueryWrapper.in(TransportDetailDO::getTransportId, transportIds)
                    .eq(TransportDetailDO::getDeleted, 0);
            List<TransportDetailDO> transportDetailDOS = transportDetailMapper.selectList(detailQueryWrapper);

            //分组
            Map<Long, List<TransportDetailDO>> detailsByTransportId = transportDetailDOS.stream()
                    .collect(Collectors.groupingBy(TransportDetailDO::getTransportId));

            Set<Long> transportIdsToUpdateStatus2 = new HashSet<>();
            Set<Long> transportIdsToUpdateStatus1 = new HashSet<>();

            for (TransportMangerDO transportMangerDO : transportMangerDOS) {
                Long transportId = transportMangerDO.getId();
                List<TransportDetailDO> details = detailsByTransportId.getOrDefault(transportId, Collections.emptyList());

                // 计算吨位总和
                BigDecimal sum = details.stream()
                        .map(TransportDetailDO::getTransportTonnage)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                // 如果吨位总和小于0，或结束时间小于当前时间，则标记为需要调用接口
                if (sum.compareTo(BigDecimal.ZERO) <= 0 ||
                        (transportMangerDO.getTransportEdae() != null && transportMangerDO.getTransportEdae().isBefore(LocalDateTime.now()))) {
                    transportIdsToUpdateStatus2.add(transportId);
                }else {
                    transportIdsToUpdateStatus1.add(transportId);
                }
            }

            // 批量更新状态为已完成
            if (!transportIdsToUpdateStatus2.isEmpty()) {
                transportMangerMapper.updateStatusToEnded(new ArrayList<>(transportIdsToUpdateStatus2));
            }

            // 批量更新状态为进行中
            if (!transportIdsToUpdateStatus1.isEmpty()) {
                transportMangerMapper.updateStatusToEnded2(new ArrayList<>(transportIdsToUpdateStatus1));
            }
        }
    }




    private List<HashMap<Object,Object>> selectMainVehicleNoList(String mainVehicleNoList, Map<Long, String> mainVehicleMap) {

        // 初始化返回的List
        List<HashMap<Object, Object>> resultList = new ArrayList<>();

        if (mainVehicleNoList != null){
            // 1. 将前端传入的主车号列表按逗号分割（支持中英文逗号），并去除每个车号的空格
            List<String> inputVehicleNos = Arrays.stream(mainVehicleNoList.split("[,，]")) // 按中英文逗号分割
                    .map(String::trim)               // 去除前后空格
                    .map(vehicleNo -> vehicleNo.replaceAll("\\s+", "")) // 去除车号中的所有空格
                    .collect(Collectors.toList());

            // 检查是否有数据需要处理
            if (!CollUtil.isEmpty(mainVehicleMap) && !CollUtil.isEmpty(inputVehicleNos)) {
                // 初始化两个 HashMap 来存储存在的和不存在的车号
                HashMap<Object, Object> existMap = new HashMap<>();
                HashMap<Object, Object> notExistMap = new HashMap<>();

                // 筛选存在的车号
                List<String> exist = inputVehicleNos.stream()
                        .filter(vehicleNo -> mainVehicleMap.containsValue(vehicleNo)) // 使用 Map 的 value 进行匹配
                        .collect(Collectors.toList());

                // 筛选不存在的车号
                List<String> notExist = inputVehicleNos.stream()
                        .filter(vehicleNo -> !mainVehicleMap.containsValue(vehicleNo)) // 没有匹配到的为不存在
                        .collect(Collectors.toList());

                // 如果存在的车号不为空，将其存储到 existMap 中
                if (!CollUtil.isEmpty(exist)) {
                    existMap.put("exist", String.join("、", exist)); // 将存在的车号合并为一个字符串
                    resultList.add(existMap); // 添加到结果列表中
                }

                // 如果不存在的车号不为空，将其存储到 notExistMap 中
                if (!CollUtil.isEmpty(notExist)) {
                    notExistMap.put("notExist", String.join("、", notExist) + " 不存在，请重新输入！");
                    resultList.add(notExistMap); // 添加到结果列表中
                }
            }
        }

        return resultList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTransportManger(TransportMangerSaveReqVO createReqVO) {
        // 1、插入运输证
        String businessCode = EncodingRulesEnum.TRANSPORTMANGER_CODE.getBusinessCode();
        TransportMangerDO transportManger = BeanUtils.toBean(createReqVO, TransportMangerDO.class);
        String codeByRuleType = encodingRulesService.getCodeByRuleType(businessCode);
        transportManger.setTransportCode(codeByRuleType);
        //BigDecimal transportTonnage = transportManger.getTransportTonnage();
        transportMangerMapper.insert(transportManger);
        Long sportMangerId = transportManger.getId();
        // 2、插入运输证明细子表
        Boolean localTransportIs = createReqVO.getLocalTransportIs();
        if (!localTransportIs) {
            List<TransportDetailSaveReqVO> transportDetails = createReqVO.getTransportDetails();
            BigDecimal transportTonnage = null;
            if (CollectionUtil.isNotEmpty(transportDetails)) {
                //获取总和
                transportTonnage = transportDetails.stream().map(TransportDetailSaveReqVO::getTransportTonnage).reduce(BigDecimal.ZERO, BigDecimal::add);

                //查询所有可用的车头号
                List<MainVehicleSimpleRespVO> mainVehicleDetails = mainVehicleService.getMainVehicleDetails(null,null);

                //以map的形式获取所有车头号，key为id，value为车牌号
                Map<Long, String> mainVehicleMap = mainVehicleDetails.stream()
                        .collect(Collectors.toMap(MainVehicleSimpleRespVO::getId, MainVehicleSimpleRespVO::getPlantNumber));

                for (TransportDetailSaveReqVO transportDetail : transportDetails) {

                    List<Long> transportCarIds = transportDetail.getTransportCarIds();

                    if (transportDetail.getCarCode() != null){

                        //进行校验，校验手动输入的车号是否存在数据库
                        List<HashMap<Object, Object>> hashMaps = selectMainVehicleNoList(transportDetail.getCarCode(),mainVehicleMap);

                        //不存在的直接返回给客户端
                        for (HashMap<Object, Object> map : hashMaps) {
                            // 检查是否包含存在的车号
                            if (map.containsKey("exist")) {
                                String existVehicleNos = (String) map.get("exist");
                                // 将存在的车号拆分为单个车号
                                List<String> existVehicleNoList = Arrays.asList(existVehicleNos.split("、"));
                                // 遍历存在的车号列表，查找对应的 id
                                List<Long> existVehicleIds = existVehicleNoList.stream()
                                        .map(vehicleNo -> mainVehicleMap.entrySet().stream()
                                                .filter(entry -> entry.getValue().equals(vehicleNo)) // 查找对应车号的 id
                                                .map(Map.Entry::getKey) // 获取 id
                                                .findFirst()
                                                .orElse(null))
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toList());

                                List<Long> newTransportCarIds = existVehicleIds.stream()
                                        .filter(id -> !transportCarIds.contains(id)) // 查找不存在于 transportCarIds 的 id
                                        .collect(Collectors.toList());

                                transportCarIds.addAll(newTransportCarIds);

                            }

                            // 检查是否包含不存在的车号
                            if (map.containsKey("notExist")) {
                                String notExistVehicleNos = (String) map.get("notExist");
                                // 抛出异常时，将车号数据作为参数传递
                                throw invalidParamException(notExistVehicleNos, ErrorCodeConstants.TRANSPORT_MANGER_MAIN_VEHICLE_NOT_EXISTS);
                            }
                        }
                    }

                }
                transportDetailService.createTransportDetailList(sportMangerId, transportDetails);
            }
        }
        //新增完成后再向业务计划表插入
        BusinessPlanRespDTO businessPlanRespDTO = new BusinessPlanRespDTO();
        businessPlanRespDTO.setTransportMangerId(transportManger.getId());
        businessPlanRespDTO.setCompanyId(transportManger.getCompanyId());
        businessPlanRespDTO.setCommodityId(transportManger.getCommodityId());
        businessPlanRespDTO.setLoadingManufacturersId(transportManger.getLoadFactoryId());
        businessPlanRespDTO.setUnloadManufacturersId(transportManger.getUnloadFactoryId());
        businessPlanRespDTO.setShipmentsAmount(transportManger.getTransportTonnage());
        businessPlanRespDTO.setEndTime(transportManger.getTransportEdae());
        String transportCode = transportManger.getTransportCode();

        // 去除第一个数据
        if (transportCode != null && transportCode.length() > 1) {
            transportCode = transportCode.substring(1);
        }

        businessPlanRespDTO.setShipmentsPlanCode("H"+transportCode);
        //插入计划状态:待补充计划
        businessPlanRespDTO.setPlanState(2);
        //计划类型：运输证关联计划
        businessPlanRespDTO.setPlanType(1);
        BusinessPlanDO businessPlanDO = new BusinessPlanDO();
        BeanUtils.copyPropertiesIgnoreNull(businessPlanDO, businessPlanRespDTO);
        businessPlanDO.setSubordinateCompaniesId(businessPlanRespDTO.getCompanyId());
        businessPlanMapper1.insert(businessPlanDO);
        // 返回
        return sportMangerId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTransportManger(TransportMangerSaveReqVO updateReqVO) {
        Long transportMangerId = updateReqVO.getId();

        // 校验是否存在
        validateTransportMangerExists(transportMangerId);

        // 1. 更新运输证信息
        TransportMangerDO updateObj = BeanUtils.toBean(updateReqVO, TransportMangerDO.class);
        transportMangerMapper.updateById(updateObj);

        // 2. 查询旧的运输详情
        LambdaQueryWrapper<TransportDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransportDetailDO::getTransportId, transportMangerId)
                .eq(TransportDetailDO::getDeleted, 0);
        List<TransportDetailDO> oldDetails = transportDetailMapper.selectList(queryWrapper);

        List<TransportDetailSaveReqVO> transportDetails = updateReqVO.getTransportDetails();

        //如果是true，则清空所有的明细
        if (updateReqVO.getLocalTransportIs()){
            LambdaUpdateWrapper<TransportDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(TransportDetailDO::getTransportId, transportMangerId)
                    .set(TransportDetailDO::getDeleted, 1);
            transportDetailMapper.update(null, updateWrapper);
            return;
        } else {


            //查询所有可用的车头号
            List<MainVehicleSimpleRespVO> mainVehicleDetails = mainVehicleService.getMainVehicleDetails(null,null);

            //以map的形式获取所有车头号，key为id，value为车牌号
            Map<Long, String> mainVehicleMap = mainVehicleDetails.stream()
                    .collect(Collectors.toMap(MainVehicleSimpleRespVO::getId, MainVehicleSimpleRespVO::getPlantNumber));


            // 3. 处理已有 ID 的明细更新
            List<TransportDetailSaveReqVO> detailsNotNullIds = transportDetails.stream()
                    .filter(detail -> detail.getId() != null)
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(detailsNotNullIds)) {
                detailsNotNullIds.forEach(notNullId -> {
                    List<Long> transportCarIds = notNullId.getTransportCarIds();
                    // 校验手动输入的车号是否存在数据库
                    List<HashMap<Object, Object>> hashMaps = selectMainVehicleNoList(notNullId.getCarCode(), mainVehicleMap);

                    // 处理车号校验
                    for (HashMap<Object, Object> map : hashMaps) {
                        if (map.containsKey("exist")) {
                            String existVehicleNos = (String) map.get("exist");
                            List<String> existVehicleNoList = Arrays.asList(existVehicleNos.split("、"));

                            // 查找与传入的车号不重复的 id，并添加到 transportCarIds
                            List<Long> existVehicleIds = existVehicleNoList.stream()
                                    .map(vehicleNo -> mainVehicleMap.entrySet().stream()
                                            .filter(entry -> entry.getValue().equals(vehicleNo))
                                            .map(Map.Entry::getKey)
                                            .findFirst()
                                            .orElse(null))
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());

                            List<Long> newTransportCarIds = existVehicleIds.stream()
                                    .filter(id -> !transportCarIds.contains(id))
                                    .collect(Collectors.toList());

                            transportCarIds.addAll(newTransportCarIds);
                        }

                        if (map.containsKey("notExist")) {
                            String notExistVehicleNos = (String) map.get("notExist");
                            throw invalidParamException(notExistVehicleNos, ErrorCodeConstants.TRANSPORT_MANGER_MAIN_VEHICLE_NOT_EXISTS);
                        }
                    }

                    // 更新明细数据
                    TransportDetailDO convert = TransportDetailConvert.INSTANCE.convert(notNullId);
                    transportDetailMapper.updateById(convert);

                    // 删除旧的关联记录
                    LambdaUpdateWrapper<TransportCarDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(TransportCarDO::getTransportDetailId, convert.getId())
                            .set(TransportCarDO::getDeleted, 1);
                    transportCarMapper.update(null, updateWrapper);
                    // 批量插入新的关联记录
                    transportCarMapper.insertBatchTransportCar(transportCarIds, convert.getId());

                    // 更新文件绑定
                    updateTransportFiles(notNullId.getId(), notNullId.getFileIds());
                });

                // 比较旧明细，找出需要删除的明细
                List<Long> notNullIds = detailsNotNullIds.stream().map(TransportDetailSaveReqVO::getId).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(oldDetails)) {
                    List<Long> oldDetailIds = oldDetails.stream().map(TransportDetailDO::getId).collect(Collectors.toList());
                    List<Long> idsToDelete = oldDetailIds.stream()
                            .filter(id -> !notNullIds.contains(id))
                            .collect(Collectors.toList());

                    if (CollectionUtils.isNotEmpty(idsToDelete)) {
                        transportDetailMapper.deleteBatchIds(idsToDelete);
                    }
                }
            }

            // 4. 处理没有ID的新增明细
            List<TransportDetailSaveReqVO> detailsNullIds = transportDetails.stream()
                    .filter(detail -> detail.getId() == null)
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(detailsNullIds)) {
                detailsNullIds.forEach(nullId -> {
                    List<Long> transportCarIds = new ArrayList<>(nullId.getTransportCarIds());
                    // 获取 carCode 对应的车辆 ID
                    List<HashMap<Object, Object>> hashMaps = selectMainVehicleNoList(nullId.getCarCode(), mainVehicleMap);

                    // 处理车号校验和消除重复
                    for (HashMap<Object, Object> map : hashMaps) {
                        if (map.containsKey("exist")) {
                            String existVehicleNos = (String) map.get("exist");
                            List<String> existVehicleNoList = Arrays.asList(existVehicleNos.split("、"));

                            // 查找与传入的车号不重复的 id，并添加到 transportCarIds
                            List<Long> existVehicleIds = existVehicleNoList.stream()
                                    .map(vehicleNo -> mainVehicleMap.entrySet().stream()
                                            .filter(entry -> entry.getValue().equals(vehicleNo))
                                            .map(Map.Entry::getKey)
                                            .findFirst()
                                            .orElse(null))
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());

                            List<Long> newTransportCarIds = existVehicleIds.stream()
                                    .filter(id -> !transportCarIds.contains(id))
                                    .collect(Collectors.toList());

                            transportCarIds.addAll(newTransportCarIds);
                        }

                        if (map.containsKey("notExist")) {
                            String notExistVehicleNos = (String) map.get("notExist");
                            throw invalidParamException(notExistVehicleNos, ErrorCodeConstants.TRANSPORT_MANGER_MAIN_VEHICLE_NOT_EXISTS);
                        }
                    }

                    // 插入新增详情数据
                    TransportDetailDO convert = TransportDetailConvert.INSTANCE.convert(nullId);
                    transportDetailMapper.insert(convert);

                    if (CollectionUtils.isNotEmpty(nullId.getTransportCarIds())){
                        // 批量插入新的关联记录
                        transportCarMapper.insertBatchTransportCar(transportCarIds, convert.getId());
                    }


                    // 绑定文件
                    if (CollectionUtils.isNotEmpty(nullId.getFileIds())) {
                        fileApi.bindAttachment(nullId.getFileIds(), convert.getId());
                    }
                });
            }
        }

        // 5. 更新其他细节
        transportDetailService.updateTransportDetailList(transportMangerId, transportDetails);

    }

    /**
     * 更新文件的绑定和解绑操作
     * @param detailId 运输详情 ID
     * @param newFileIds 新的文件 ID 列表
     */
    private void updateTransportFiles(Long detailId, List<Long> newFileIds) {
        if (CollectionUtils.isEmpty(newFileIds)) {
            return; // 如果没有传入新文件 ID，则直接返回
        }

        // 查询数据库中的旧文件
        String codeBusinessType = FileEnums.TRANSPORT_MANGER_FILE_EUM.getCodeBusinessType();
        List<FileDTO> fileDTOS = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(codeBusinessType, detailId);
        if (CollectionUtils.isEmpty(fileDTOS)) {
            // 如果旧文件为空，直接绑定新文件
            fileApi.bindAttachment(newFileIds, detailId);
            return;
        }

        List<Long> oldFileIds = fileDTOS.stream().map(FileDTO::getId).collect(Collectors.toList());

        // 找出需要解绑的文件
        List<Long> deleteFileIds = oldFileIds.stream()
                .filter(id -> !newFileIds.contains(id)) // 旧文件中有但新文件没有的，解绑
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(deleteFileIds)) {
            fileApi.unBindAttachment2(deleteFileIds, detailId);
        }

        // 找出需要绑定的新文件
        List<Long> addFileIds = newFileIds.stream()
                .filter(id -> !oldFileIds.contains(id)) // 新文件中有但旧文件没有的，绑定
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(addFileIds)) {
            fileApi.bindAttachment(addFileIds, detailId);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTransportManger(Long id) {
        // 校验存在
        validateTransportMangerExists(id);
        // 删除
        transportMangerMapper.deleteById(id);
    }

    private void validateTransportMangerExists(Long id) {
        TransportMangerDO transportMangerDO = transportMangerMapper.selectById(id);
        if (ObjectUtil.isEmpty(transportMangerDO)) {
            throw exception(TRANSPORT_MANGER_NOT_EXISTS);
        }
    }


    @Override
    public TransportMangerRespVO getTransportManger(Long id) {
        // 查询运输实体对象
        TransportMangerDO transportMangerDO = transportMangerMapper.selectById(id);
        if (ObjectUtils.isEmpty(transportMangerDO)) {
            return null;
        }

        // 将运输经理实体对象转换为响应 VO 对象
        TransportMangerRespVO transportMangerDOResult = TransportMangerConvert.INSTANCE.convert(transportMangerDO);

        // 查询关联的运输详情
        List<TransportDetailDO> transportDetailDOList = getTransportDetails(id);
        List<TransportDetailSaveReqVO> transportDetailSaveReqVOS = TransportDetailConvert.INSTANCE.convertList2(transportDetailDOList);
        if (CollectionUtils.isEmpty(transportDetailSaveReqVOS)) {
            return transportMangerDOResult; // 如果没有详情，直接返回
        }

        // 获取 transportDetailDO 的 ID 列表
        List<Long> detailIds = transportDetailSaveReqVOS.stream()
                .map(TransportDetailSaveReqVO::getId)
                .collect(Collectors.toList());

        // 获取关联文件
        Map<Long, List<FileDTO>> filesGroupedBySourceId = getFileMapByDetailIds(detailIds);

        // 获取关联的车头信息
        Map<Long, List<Long>> carsGroupedByDetailId = getTransportCarMapByDetailIds(detailIds);

        // 将文件和车头信息填充到每个 TransportDetailDO 中
        fillTransportDetailsWithFilesAndCars(transportDetailSaveReqVOS, filesGroupedBySourceId, carsGroupedByDetailId);

        transportMangerDOResult.setTransportDetails(transportDetailSaveReqVOS);

        return transportMangerDOResult;
    }

    // 查询运输详情
    private List<TransportDetailDO> getTransportDetails(Long transportId) {
        LambdaQueryWrapper<TransportDetailDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(TransportDetailDO::getTransportId, transportId)
                .eq(TransportDetailDO::getDeleted, 0);
        return transportDetailMapper.selectList(queryWrapper);
    }

    // 获取文件并按 sourceId 分类
    private Map<Long, List<FileDTO>> getFileMapByDetailIds(List<Long> detailIds) {
        String businessType = FileEnums.TRANSPORT_MANGER_FILE_EUM.getCodeBusinessType();
        List<FileDTO> fileDTOS = fileService.getFileUrlByCodeBusinessTypeAndSourceIds(businessType, detailIds);
        if (CollectionUtils.isEmpty(fileDTOS)) {
            return new HashMap<>(); // 如果没有文件，返回空 map
        }

        return fileDTOS.stream()
                .filter(fileDTO -> Objects.nonNull(fileDTO.getSourceId()))
                .collect(Collectors.groupingBy(FileDTO::getSourceId));
    }

    // 获取车头信息并按 transportDetailId 分类
    private Map<Long, List<Long>> getTransportCarMapByDetailIds(List<Long> detailIds) {
        LambdaQueryWrapper<TransportCarDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TransportCarDO::getTransportDetailId, detailIds)
                .eq(TransportCarDO::getDeleted, 0);
        List<TransportCarDO> transportCarDOList = transportCarMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(transportCarDOList)) {
            return new HashMap<>();
        }

        return transportCarDOList.stream()
                .filter(car -> Objects.nonNull(car.getTransportDetailId()))
                .collect(Collectors.groupingBy(
                        TransportCarDO::getTransportDetailId,
                        Collectors.mapping(TransportCarDO::getCarId, Collectors.toList())
                ));
    }

    // 填充文件和车头信息到每个 TransportDetailDO 中
    private void fillTransportDetailsWithFilesAndCars(
            List<TransportDetailSaveReqVO> detailSaveRespVOS,
            Map<Long, List<FileDTO>> filesGroupedBySourceId,
            Map<Long, List<Long>> carsGroupedByDetailId) {

        for (TransportDetailSaveReqVO detailSaveReqVO : detailSaveRespVOS) {
            Long detailDOId = detailSaveReqVO.getId();

            // 填充文件信息
            List<FileDTO> filesForThisDetail = filesGroupedBySourceId.get(detailDOId);
            if (CollectionUtils.isNotEmpty(filesForThisDetail)) {
                List<Map<String, Object>> fileList = filesForThisDetail.stream()
                        .map(fileDTO -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", fileDTO.getId());
                            map.put("url", fileDTO.getUrl());
                            map.put("name", fileDTO.getName());
                            return map;
                        }).collect(Collectors.toList());
                detailSaveReqVO.setFileList(fileList);
            }

            // 填充车头信息
            List<Long> carIdsForThisDetail = carsGroupedByDetailId.get(detailDOId);
            if (CollectionUtils.isNotEmpty(carIdsForThisDetail)) {
                detailSaveReqVO.setTransportCarIds(carIdsForThisDetail);
            }
        }
    }


    @Override
    public List<TransportMangerDO> getTransportMangerList(Collection<Long> ids) {
        return transportMangerMapper.selectBatchIds(ids);
    }


    @Override
    public List<TransportMangerDO> getTransportMangerList(TransportMangerListReqVO listReqVO) {
        return transportMangerMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateTransportManger(List<TransportMangerSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteTransportManger(List<Long> ids) {
        // 在这里处理批量删除逻辑
        transportMangerMapper.deleteBatchIds(ids);
    }

    @Override
    public List<TransportMangerExcelVO> importPreviewList(List<TransportMangerExcelVO> importDatas,
                                                          boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(TRANSPORT_MANGER_IMPORT_LIST_IS_EMPTY);
        }

        List<TransportMangerExcelVO> excelVo = BeanUtils.toBean(importDatas, TransportMangerExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(TRANSPORT_MANGER_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(TransportMangerExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0) {
            throw exception(TRANSPORT_MANGER_IMPORT_LIST_IS_EMPTY);
        }

        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(TRANSPORT_MANGER_IMPORT_PORT_REQUIRE);
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
    public CardPage<TransportMangerRespVO> selectPageByKeyword(TransportMangerKeywordPageReqVO pageReqVO) {
        // 创建分页对象
        CardPage<TransportMangerDO> page = new CardPage<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        pageReqVO.setOrderByColumn("create_time");

        // 查询运输经理分页数据
        CardPage<TransportMangerRespVO> pageResultVO = transportMangerMapper.selectPageByKeyword(page, pageReqVO);

        // 异步处理其他信息
        CompletableFuture<Void> completableFuture = setCardInfoAsync(pageResultVO);

        // 获取文件业务类型
        String purchaseBusinessType = FileEnums.PURCHASE_MANGER_FILE_EUM.getCodeBusinessType();

        // 预先获取所有上游和下游采购的文件 ID，避免循环中重复查询
        List<Long> upstreamIds = pageResultVO.getRecords().stream()
                .map(TransportMangerRespVO::getUpstreamPurchaseId)
                .filter(Objects::nonNull) // 过滤掉null的id
                .collect(Collectors.toList());

        List<Long> downstreamIds = pageResultVO.getRecords().stream()
                .map(TransportMangerRespVO::getDownstreamPurchaseId)
                .filter(Objects::nonNull) // 过滤掉null的id
                .collect(Collectors.toList());

        // 批量查询上游和下游采购文件，并将结果存入Map中
        List<FileDTO> upstreamFiles = fileApi.getFileUrlByCodeBusinessTypeAndSourceIds(purchaseBusinessType, upstreamIds);
        List<FileDTO> downstreamFiles = fileApi.getFileUrlByCodeBusinessTypeAndSourceIds(purchaseBusinessType, downstreamIds);

        // 将文件列表转成Map，以sourceId为key，文件URL列表为value
        Map<Long, List<String>> upstreamFilesMap = upstreamFiles.stream()
                .collect(Collectors.groupingBy(FileDTO::getSourceId,
                        Collectors.mapping(FileDTO::getUrl, Collectors.toList())));

        Map<Long, List<String>> downstreamFilesMap = downstreamFiles.stream()
                .collect(Collectors.groupingBy(FileDTO::getSourceId,
                        Collectors.mapping(FileDTO::getUrl, Collectors.toList())));


        // 遍历运输证记录
        for (TransportMangerRespVO item : pageResultVO.getRecords()) {
            fillBindName(item);

            // 获取上游和下游文件
            List<String> upstreamFilesUrls = upstreamFilesMap.getOrDefault(item.getUpstreamPurchaseId(), Collections.emptyList());
            List<String> downstreamFilesUrls = downstreamFilesMap.getOrDefault(item.getDownstreamPurchaseId(), Collections.emptyList());

            // 使用Set去重
            Set<String> uniqueFiles = new HashSet<>();
            uniqueFiles.addAll(upstreamFilesUrls);
            uniqueFiles.addAll(downstreamFilesUrls);

            List<String> allPurchaseFiles = new ArrayList<>(uniqueFiles);
            item.setPurchaseFiles(allPurchaseFiles);

            // 获取运输明细并批量查询文件
            List<TransportDetailSaveReqVO> detailList = transportDetailService.getDetailListByTransportId(item.getId());
            if (CollectionUtils.isNotEmpty(detailList)) {
                List<Long> detailIds = detailList.stream().map(TransportDetailSaveReqVO::getId).collect(Collectors.toList());
                List<FileDTO> fileDTOs = fileApi.getFileUrlByCodeBusinessTypeAndSourceIds(FileEnums.TRANSPORT_MANGER_FILE_EUM.getCodeBusinessType(), detailIds);
                if (CollectionUtils.isNotEmpty(fileDTOs)) {
                    List<String> detailFileUrls = fileDTOs.stream().map(FileDTO::getUrl).collect(Collectors.toList());
                    item.setDetailsFileUrls(detailFileUrls);
                }
                //查询车头号
                LambdaQueryWrapper<TransportCarDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(TransportCarDO::getTransportDetailId, detailIds)
                        .eq(TransportCarDO::getDeleted, 0)
                        .select(TransportCarDO::getId, TransportCarDO::getCarId,TransportCarDO::getTransportDetailId);
                List<TransportCarDO> transportCarDOS = transportCarMapper.selectList(queryWrapper);
                if (CollectionUtils.isNotEmpty(transportCarDOS)){
                    List<Long> mainVehicleIds = transportCarDOS.stream().map(TransportCarDO::getCarId).collect(Collectors.toList());
                    LambdaQueryWrapper<MainVehicleDO> queryWrapper1 = new LambdaQueryWrapper<>();
                    queryWrapper1.in(MainVehicleDO::getId, mainVehicleIds)
                            .eq(MainVehicleDO::getDeleted, 0)
                            .select(MainVehicleDO::getId, MainVehicleDO::getPlateNumber);
                    List<MainVehicleDO> mainVehicleDOS = mainVehicleMapper.selectList(queryWrapper1);
                    if (CollectionUtils.isNotEmpty(mainVehicleDOS)){
                        List<String> mainVehicleList = mainVehicleDOS.stream().map(MainVehicleDO::getPlateNumber).collect(Collectors.toList());
                        String mainVehicleListJoin = String.join(",", mainVehicleList);
                        item.setTransportCarName(mainVehicleListJoin);
                    }
                }

            }
        }

//        try {
//            completableFuture.get();  // 等待异步任务完成
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException("获取详情分页失败", e);
//        }

        return pageResultVO;
    }



    @Override
    public List<TransportDownloadExcelVO> transportDownload(@Validated Long id) {
        List<TransportDownloadExcelVO> transportDownloadExcelList = transportMangerMapper.transportDownload(id);
        if (CollectionUtils.isNotEmpty(transportDownloadExcelList)) {
            // 自定义线程池异步处理
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            List<CompletableFuture<Void>> futures = transportDownloadExcelList.stream()
                    .map(item -> CompletableFuture.runAsync(() -> {
                        Long deptId = item.getDeptId();
                        DeptDO deptDO = deptMapper.selectById(deptId);
                        if (ObjectUtils.isNotEmpty(deptDO)) {
                            item.setDeptName(deptDO.getName());
                        }

                        Long fleetId = item.getFleetId();
                        FleetDO fleetDO = fleetMapper.selectById(fleetId);
                        if (ObjectUtils.isNotEmpty(fleetDO)) {
                            item.setFleetName(fleetDO.getName());
                        }

                        Long carId = item.getCarId();
                        CarDO carDO = carMapper.selectById(carId);
                        if (ObjectUtils.isNotEmpty(carDO)) {
                            MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(carDO.getMainVehicleId());
                            if (ObjectUtils.isNotEmpty(mainVehicleDO)) {
                                item.setPlateNumber(mainVehicleDO.getPlateNumber());
                            }

                        }

                        Long loadFactoryId = item.getLoadFactoryId();
                        CustomerDO customerDO = customerMapper.selectById(loadFactoryId);
                        if (ObjectUtils.isNotEmpty(customerDO)) {
                            item.setLoadFactoryName(customerDO.getCustomerName());
                        }

                        Long unloadFactoryId = item.getUnloadFactoryId();
                        CustomerDO customer = customerMapper.selectById(unloadFactoryId);
                        if (ObjectUtils.isNotEmpty(customer)) {
                            item.setUnloadFactoryName(customer.getCustomerName());
                        }

                        Long upstreamPurchaseId = item.getUpstreamPurchaseId();
                        PurchaseMangerDO purchaseMangerDO = purchaseMangerMapper.selectById(upstreamPurchaseId);
                        if (ObjectUtils.isNotEmpty(purchaseMangerDO)) {
                            item.setUpstreamPurchaseCode(purchaseMangerDO.getPurchaseCode());
                        }

                        Long downstreamPurchaseId = item.getDownstreamPurchaseId();
                        PurchaseMangerDO purchaseMangerDO1 = purchaseMangerMapper.selectById(downstreamPurchaseId);
                        if (ObjectUtils.isNotEmpty(purchaseMangerDO1)) {
                            item.setDownstreamPurchaseCode(purchaseMangerDO1.getPurchaseCode());
                        }

                        Long loadAccountId = item.getLoadAccountId();
                        CustomerDO customerDO1 = customerMapper.selectById(loadAccountId);
                        if (customerDO1 != null && ObjectUtils.isNotEmpty(customerDO1.getCustomerType()) && customerDO1.getCustomerType() == 2) {
                            item.setLoadAccountName(customerDO1.getCustomerName());
                        }

                        Long unloadAccountId = item.getUnloadAccountId();
                        CustomerDO customerDO2 = customerMapper.selectById(unloadAccountId);
                        if (customerDO2 != null && ObjectUtils.isNotEmpty(customerDO2.getCustomerType()) && customerDO2.getCustomerType() == 1) {
                            item.setUnloadAccountName(customerDO2.getCustomerName());
                        }

                    }, executorService))
                    .collect(Collectors.toList());
            //等待所有的元素都执行完成
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            executorService.shutdown();
        }

        return transportDownloadExcelList;
    }

    /**
     * 填充绑定的名称：申请单位、承运单位、装货厂家和卸货厂家
     *
     * @param respVO
     */
    private void fillBindName(TransportMangerRespVO respVO) {
        Long applicantId = respVO.getApplicantId();
        CustomerDO customerDO1 = customerMapper.selectById(applicantId);
        if (ObjectUtils.isNotEmpty(customerDO1)) {
            respVO.setApplicantName(customerDO1.getCustomerName());
        }
        Long carrierId = respVO.getCarrierId();
        CustomerDO customerDO2 = customerMapper.selectById(carrierId);
        if (ObjectUtils.isNotEmpty(customerDO2)) {
            respVO.setCarrierName(customerDO2.getCustomerName());
        }
        Long loadFactoryId = respVO.getLoadFactoryId();
        CustomerDO customerDO3 = customerMapper.selectById(loadFactoryId);
        if (ObjectUtils.isNotEmpty(customerDO3)) {
            respVO.setLoadFactoryName(customerDO3.getCustomerName());
        }
        Long unloadFactoryId = respVO.getUnloadFactoryId();
        CustomerDO customerDO4 = customerMapper.selectById(unloadFactoryId);
        if (ObjectUtils.isNotEmpty(customerDO4)) {
            respVO.setUnloadFactoryName(customerDO4.getCustomerName());
        }

    }

    /**
     * 设置填充其他分页信息 (异步)
     *
     * @param transportManger 证件对象
     */
    private CompletableFuture<Void> setCardInfoAsync(CardPage<TransportMangerRespVO> transportManger) {
        //1、进行中
        CompletableFuture<Long> inProgressContractFuture = CompletableFuture.supplyAsync(() -> {
            LambdaQueryWrapper<TransportMangerDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TransportMangerDO::getStatus, 1);
            Long inProgressContract1 = transportMangerMapper.selectCount(queryWrapper);
            return inProgressContract1;
        });

        //2、已完成
        CompletableFuture<Long> completedContractFuture = CompletableFuture.supplyAsync(() -> {
            LambdaQueryWrapper<TransportMangerDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(TransportMangerDO::getStatus, 2);
            Long completedContract2 = transportMangerMapper.selectCount(queryWrapper2);
            return completedContract2;
        });

        //3、证件到期前7天产生预警，购买证剩余数量低于购买证数量的20%产生预警
        CompletableFuture<Long> expirationReminderContractFuture = CompletableFuture.supplyAsync(() -> {
            LambdaQueryWrapper<TransportMangerDO> queryWrapper3 = new LambdaQueryWrapper<>();
            queryWrapper3.between(TransportMangerDO::getTransportEdae, LocalDateTime.now().minusDays(7), LocalDateTime.now());
            Long pendingArchivalContract3 = transportMangerMapper.selectCount(queryWrapper3);
            return pendingArchivalContract3;
        });

        return CompletableFuture.allOf(inProgressContractFuture, completedContractFuture, expirationReminderContractFuture)
                .thenAccept(voided -> {
                    try {
                        transportManger.setInProgressContract1(inProgressContractFuture.get());
                        transportManger.setCompletedContract2(completedContractFuture.get());
                        transportManger.setExpirationReminderContract3(expirationReminderContractFuture.get());
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException("设置填充其他分页信息出错", e);
                    }
                });
    }


// ==================== 子表（运输证明细） ====================

    @Override
    public PageResult<TransportDetailDO> getTransportDetailPage(PageParam pageReqVO, Long transportId) {
//        return transportDetailMapper.selectPage(pageReqVO, transportId);
        return null;
    }

    @Override
    public List<TransportDetailDO> getTransportDetailListByTransportId(Long transportId) {
//        return transportDetailMapper.selectListByTransportId(transportId);
        return null;
    }

    private void validateTransportDetailExists(Long id) {
        if (transportDetailMapper.selectById(id) == null) {
            throw exception(TRANSPORT_DETAIL_NOT_EXISTS);
        }
    }


}