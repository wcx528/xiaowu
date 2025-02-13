package com.fmyd888.fengmao.module.information.service.car;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.aliyun.dingtalkworkflow_1_0.models.StartProcessInstanceRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmyd888.fengmao.framework.common.exception.ErrorCode;
import com.fmyd888.fengmao.framework.common.pojo.CommonInformationQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.common.util.object.ObjectUtils;
import com.fmyd888.fengmao.framework.dingtalk.core.dingtalk.BaseProcessInstanceRequest;
import com.fmyd888.fengmao.framework.dingtalk.core.dingtalk.DingTalkProperties;
import com.fmyd888.fengmao.framework.dingtalk.core.dingtalk.DingTalkUtils;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.api.dingProcessCode.DingProcessCodeApi;
import com.fmyd888.fengmao.module.information.api.dingProcessCode.dto.DingProcessCodeDTO;
import com.fmyd888.fengmao.module.information.api.roster.RosterApi;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.common.vo.CarCyclePage;
import com.fmyd888.fengmao.module.information.controller.admin.car.dto.*;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.DownMainVehicleExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesExcelVO;
import com.fmyd888.fengmao.module.information.convert.car.CarConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.carprocesscode.CarProcessCodeDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.carpersonreplace.CarPersonReplaceDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.fleet.FleetDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.roster.RosterDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.car.carprocesscode.CarProcessCodeMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.carpersonreplace.CarPersonReplaceMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.commodity.CommodityMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.fleet.FleetMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.mainvehicle.MainVehicleMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.roster.RosterMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.trailer.TrailerMapper;
import com.fmyd888.fengmao.module.information.dal.redis.RedisKeyConstants;
import com.fmyd888.fengmao.module.information.enums.CarPersonReplaceEnum;
import com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants;
import com.fmyd888.fengmao.module.information.enums.car.GodTypeEnum;
import com.fmyd888.fengmao.module.information.enums.dingProcessCode.DingProcessCodeEnum;
import com.fmyd888.fengmao.module.information.enums.roster.InsuranceTypeEnum;
import com.fmyd888.fengmao.module.information.enums.vehicle.ApprovalStatusEnum;
import com.fmyd888.fengmao.module.information.enums.vehicle.StatusEnum;
import com.fmyd888.fengmao.module.information.enums.vehicle.VehicleEnum;
import com.fmyd888.fengmao.module.information.service.trailer.TrailerService;
import com.fmyd888.fengmao.module.infra.api.dingCallback.DingCallbackApi;
import com.fmyd888.fengmao.module.infra.api.dingCallback.dto.DingCallbackDTO;
import com.fmyd888.fengmao.module.process.api.ProcessInstanceApi;
import com.fmyd888.fengmao.module.process.enums.module.TemplateModuleEnum;
import com.fmyd888.fengmao.module.system.api.dict.DictDataApi;
import com.fmyd888.fengmao.module.system.api.dict.dto.DictDataRespDTO;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.api.user.dto.AdminUserRespDTO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dict.DictDataDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dict.DictTypeDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.dict.DictDataMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.fmyd888.fengmao.module.system.service.dict.DictDataService;
import com.fmyd888.fengmao.ssodemo.framework.core.LoginUser;
import com.fmyd888.fengmao.ssodemo.framework.core.util.SecurityUtils;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 车辆档案 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
@Slf4j
public class CarServiceImpl extends ServiceImpl<CarMapper, CarDO> implements CarService {

    @Resource
    private DingTalkProperties dingTalkProperties;

    @Resource
    private CarMapper carMapper;
    @Resource
    private FleetMapper fleetMapper;
    @Resource
    private MainVehicleMapper mainVehicleMapper;
    @Resource
    private TrailerMapper trailerMapper;
    @Resource
    private TrailerService trailerService;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private AdminUserMapper userMapper;
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private CarPersonReplaceMapper carPersonReplaceMapper;
    @Resource
    private DingProcessCodeApi dingProcessCodeApi;
    @Resource
    private RosterApi rosterApi;
    @Resource
    private RosterMapper rosterMapper;
    @Resource
    private DingCallbackApi dingCallbackApi;
    @Resource
    private ProcessInstanceApi processInstanceApi;
    @Resource
    private CarProcessCodeMapper carProcessCodeMapper;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private DictDataApi dictDataApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createCar(CarSaveReqVO createReqVO) {
        validateCarVO(createReqVO);
        // 1、检查主驾、车挂、押运、和副驾    是否替换
        Long mainId = createReqVO.getMainId();
        Long trailerId = createReqVO.getTrailerId();
        Long escortId = createReqVO.getEscortId();
        Long deputyId = createReqVO.getDeputyId();
        Boolean replay = createReqVO.getReplay();

        // 校验主驾、副驾、押运员是否一样，非空才进行比较
        if ((mainId != null && deputyId != null && mainId.equals(deputyId)) ||
                (mainId != null && escortId != null && mainId.equals(escortId)) ||
                (deputyId != null && escortId != null && deputyId.equals(escortId))) {
            throw exception(TRANSPORT_CAR_ERROR05);
        }
        //1.2、前端确认需要覆盖
        if (replay) {
            LambdaQueryWrapper<CarDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(CarDO::getTrailerId, trailerId);  //车挂
            CarDO carDO2 = carMapper.selectOne(queryWrapper2);
            if (ObjectUtils.isNotEmpty(carDO2)) {
                LambdaUpdateWrapper<CarDO> updateWrapper2 = new LambdaUpdateWrapper<>();
                updateWrapper2.eq(CarDO::getId, carDO2.getId())
                        .set(CarDO::getTrailerId, null)
                        .set(CarDO::getActualTonnage, null);
                carMapper.update(null, updateWrapper2);
            }

            clearRoleAssignments(mainId, escortId, deputyId);

        } else {
//            checkPositions(createReqVO, mainId, trailerId, escortId, deputyId);
            LambdaQueryWrapper<CarDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(CarDO::getStatus, 0)
                    .eq(CarDO::getTrailerId, trailerId);  //车挂
            CarDO carDO2 = carMapper.selectOne(queryWrapper2);
            if (ObjectUtils.isNotEmpty(carDO2)) {
                Long mainVehicleId = carDO2.getMainVehicleId();
                MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(mainVehicleId);
                throw exception(TRANSPORT_CAR_ERROR, mainVehicleDO.getPlateNumber());
            }
            // 检查主驾驶、副驾驶、押运员是否已分配给其他车辆
            checkPosition(mainId, ErrorCodeConstants.TRANSPORT_CAR_ERROR01);
            checkPosition(escortId, ErrorCodeConstants.TRANSPORT_CAR_ERROR03);
            checkPosition(deputyId, ErrorCodeConstants.TRANSPORT_CAR_ERROR02);
        }

        //
        List<Long> commodityIds = createReqVO.getCommodityIds();
        String commodityId = commodityIds.stream().map(Object::toString).collect(Collectors.joining(","));
        // 没有重复才插入记录
        CarDO car = CarConvert.INSTANCE.convert(createReqVO);
        car.setCommodityId(commodityId);
        //Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        //car.setDeptId(loginUserDeptId);
        //car.setActualTonnage(getActualTonnage(car));
        //根据传过来的车头、车挂号更新对应的车头、车挂表的是否闲置状态
        LambdaUpdateWrapper<MainVehicleDO> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(MainVehicleDO::getId, car.getMainVehicleId())
                        .set(MainVehicleDO::getIsIdle, 0);
        mainVehicleMapper.update(null, queryWrapper);
        LambdaUpdateWrapper<TrailerDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TrailerDO::getId, car.getTrailerId())
                        .set(TrailerDO::getIsIdle, 0);
        trailerMapper.update(null, updateWrapper);
        carMapper.insert(car);
        return null;
    }

    private void checkPosition(Long id, ErrorCode errorCode) {
        LambdaQueryWrapper<CarDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarDO::getStatus, 0)
                .and(wrapper -> wrapper.eq(CarDO::getMainId, id)
                        .or().eq(CarDO::getDeputyId, id)
                        .or().eq(CarDO::getEscortId, id));

        CarDO carDO = carMapper.selectOne(queryWrapper);
        if (ObjectUtils.isNotEmpty(carDO)) {
            Long mainVehicleId = carDO.getMainVehicleId();
            MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(mainVehicleId);
            throw exception(errorCode, mainVehicleDO.getPlateNumber());
        }
    }


    private void clearRoleAssignments(Long mainId, Long escortId, Long deputyId) {
        List<Long> roleIds = Arrays.asList(mainId, escortId, deputyId);

        LambdaQueryWrapper<CarDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CarDO::getMainId, roleIds)
                .or().in(CarDO::getDeputyId, roleIds)
                .or().in(CarDO::getEscortId, roleIds);

        List<CarDO> carDOList = carMapper.selectList(queryWrapper);

        for (CarDO carDO : carDOList) {
            LambdaUpdateWrapper<CarDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(CarDO::getId, carDO.getId()).eq(CarDO::getStatus, 0);

            if (carDO.getMainId() != null && roleIds.contains(carDO.getMainId())) {
                updateWrapper.set(CarDO::getMainId, null);
            }
            if (carDO.getDeputyId() != null && roleIds.contains(carDO.getDeputyId())) {
                updateWrapper.set(CarDO::getDeputyId, null);
            }
            if (carDO.getEscortId() != null && roleIds.contains(carDO.getEscortId())) {
                updateWrapper.set(CarDO::getEscortId, null);
            }

            carMapper.update(null, updateWrapper);
        }
    }

    /**
     * 新增:④实际装载吨位   49-车头自重-车挂自重
     */
    private BigDecimal getActualTonnage(CarDO carDO) {
        BigDecimal actualTonnage = null;
        Long mainVehicleId = carDO.getMainVehicleId();
        Long trailerId = carDO.getTrailerId();
        if (mainVehicleId != null && trailerId != null) {
            MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(mainVehicleId);
            TrailerDO trailerDO = trailerMapper.selectById(trailerId);
            BigDecimal bigDecimal = BigDecimal.valueOf(49);
            BigDecimal frontWeight = mainVehicleDO.getFrontWeight();
            BigDecimal trailerWeight = trailerDO.getTrailerWeight();
            actualTonnage = bigDecimal.subtract(frontWeight).subtract(trailerWeight);
        }
        return actualTonnage;
    }

    private CarPersonReplaceDO createPersonnelChangeDO(CarDO updateObj, CarDO carDO) {
        CarPersonReplaceDO personnelChangeDO = new CarPersonReplaceDO();
        personnelChangeDO.setDeptId(updateObj.getDeptId());
        personnelChangeDO.setCarId(updateObj.getMainVehicleId());
        personnelChangeDO.setCaptainId(updateObj.getCaptainId());
        personnelChangeDO.setMainId(updateObj.getMainId());
        personnelChangeDO.setDeputyId(updateObj.getDeputyId());
        personnelChangeDO.setEscortId(updateObj.getEscortId());
        personnelChangeDO.setReplaceTime(LocalDateTime.now());
        AdminUserRespDTO loginUserInfo = adminUserApi.getLoginUserInfo();
        personnelChangeDO.setApplyUserId(loginUserInfo.getId());
        personnelChangeDO.setApplyUserTime(LocalDateTime.now());
        personnelChangeDO.setStatus(0);
        personnelChangeDO.setDeptId(loginUserInfo.getDeptId());
        personnelChangeDO.setTrailerId(updateObj.getTrailerId());
        personnelChangeDO.setFleetId(updateObj.getFleetId());
        personnelChangeDO.setMainId(updateObj.getMainId());
        personnelChangeDO.setDeputyId(updateObj.getDeputyId());
        personnelChangeDO.setEscortId(updateObj.getEscortId());
        if (!Objects.equals(updateObj.getTrailerId(), carDO.getTrailerId())) {
            personnelChangeDO.setOldTrailerId(carDO.getTrailerId());//前车挂
        }
        if (!Objects.equals(updateObj.getFleetId(), carDO.getFleetId())) {
            personnelChangeDO.setOldFleetId(carDO.getFleetId());//前车队
        }
        if (!Objects.equals(updateObj.getMainId(), carDO.getMainId())) {
            personnelChangeDO.setOldMainId(carDO.getMainId());//前主驾
        }
        if (!Objects.equals(updateObj.getDeputyId(), carDO.getDeputyId())) {
            personnelChangeDO.setOldDeputyId(carDO.getDeputyId());//前副驾
        }
        if (!Objects.equals(updateObj.getEscortId(), carDO.getEscortId())) {
            personnelChangeDO.setOldEscortId(carDO.getEscortId());//前押运员
        }
        return personnelChangeDO;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCar2(CarSaveReqVO updateReqVO) {

        // 1、基本校验合法性
        validateCarVO(updateReqVO);

        // 2、从数据库获取当前车辆数据
        LambdaQueryWrapper<CarDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarDO::getId, updateReqVO.getId())
                .eq(BaseDO::getDeleted, 0);
        CarDO carDO = carMapper.selectOne(queryWrapper);

        if (carDO == null) {
            throw exception(CAR_NOT_EXISTS);
        }

        // 3、检查各字段是否发生变化
        boolean trailerOrFleetChanged = !Objects.equals(updateReqVO.getTrailerId(), carDO.getTrailerId()) ||
                !Objects.equals(updateReqVO.getFleetId(), carDO.getFleetId());
        boolean personnelChanged = !Objects.equals(updateReqVO.getMainId(), carDO.getMainId()) ||
                !Objects.equals(updateReqVO.getDeputyId(), carDO.getDeputyId()) ||
                !Objects.equals(updateReqVO.getEscortId(), carDO.getEscortId());

        // 3、检查各字段是否发生变化
        boolean mainIdChanged = !Objects.equals(updateReqVO.getMainId(), carDO.getMainId());
        boolean deputyIdChanged = !Objects.equals(updateReqVO.getDeputyId(), carDO.getDeputyId());
        boolean escortIdChanged = !Objects.equals(updateReqVO.getEscortId(), carDO.getEscortId());
        boolean trailerIdChanged = !Objects.equals(updateReqVO.getTrailerId(), carDO.getTrailerId());


        if (updateReqVO.getReplay()) {

            // 发起审批流程
            if (trailerOrFleetChanged || personnelChanged) {
                CarPersonReplaceDO personnelChangeDO = createPersonnelChangeDO(CarConvert.INSTANCE.convert(updateReqVO), carDO);

                if (trailerOrFleetChanged && personnelChanged) {
                    // 如果车队车挂和人员都发生变化
                    personnelChangeDO.setReplaceType(3); // 车队车挂和人员全部更换
                    carPersonReplaceMapper.insert(personnelChangeDO);
                    // 发起两个审批
                    trailerAndFleetProcessApproval(personnelChangeDO); // 车队车挂的审批
                    personnelProcessApproval(personnelChangeDO); // 驾押人员的审批
                } else if (trailerOrFleetChanged) {
                    // 如果只有车队和车挂发生变化
                    personnelChangeDO.setReplaceType(2); // 车队车挂更换
                    carPersonReplaceMapper.insert(personnelChangeDO);
                    // 发起车队车挂的审批
                    trailerAndFleetProcessApproval(personnelChangeDO);
                } else {
                    // 如果只有人员发生变化
                    personnelChangeDO.setReplaceType(1); // 人员更换
                    carPersonReplaceMapper.insert(personnelChangeDO);
                    // 发起驾押人员的审批
                    personnelProcessApproval(personnelChangeDO);
                }
            }


            // 只要字段发生了变化，不管replay的值，都要更新carDO的processStatus为0(审批中)
            if (trailerOrFleetChanged || personnelChanged) {
                LambdaUpdateWrapper<CarDO> queryWrapper1 = new LambdaUpdateWrapper<>();
                queryWrapper1.eq(CarDO::getId, updateReqVO.getId())
                        .eq(CarDO::getDeleted, 0)
                        .set(CarDO::getProcessStatus, 0);
                carMapper.update(null, queryWrapper1);
            }

        } else {

            // 只要字段发生了变化，不管replay的值，都要更新carDO的processStatus为0(审批中)，并查询记录表中有没有审批的数据
            if (trailerOrFleetChanged || personnelChanged) {
                LambdaUpdateWrapper<CarDO> queryWrapper1 = new LambdaUpdateWrapper<>();
                queryWrapper1.eq(CarDO::getId, updateReqVO.getId())
                        .eq(CarDO::getDeleted, 0)
                        .set(CarDO::getProcessStatus, 0);
                carMapper.update(null, queryWrapper1);

                //先去校验记录表中有没有这辆车头和车挂的审批中的数据，有的话则报错，报错信息是申请人和车挂
                LambdaQueryWrapper<CarPersonReplaceDO> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(CarPersonReplaceDO::getStatus, 0)
                        .eq(CarPersonReplaceDO::getCarId, updateReqVO.getMainVehicleId())
                        .eq(CarPersonReplaceDO::getTrailerId, updateReqVO.getTrailerId())
                        .nested(wrapper -> wrapper
                                .eq(CarPersonReplaceDO::getReplaceType, 2)
                                .or()
                                .eq(CarPersonReplaceDO::getReplaceType, 3)
                        );
                CarPersonReplaceDO carPersonReplaceDO = carPersonReplaceMapper.selectOne(queryWrapper2);
                if (carPersonReplaceDO != null) {
                    Long applyUserId = carPersonReplaceDO.getApplyUserId();
                    LambdaQueryWrapper<AdminUserDO> queryWrapper3 = new LambdaQueryWrapper<>();
                    queryWrapper3.eq(AdminUserDO::getId, applyUserId)
                            .select(AdminUserDO::getNickname);
                    AdminUserDO adminUserDO = adminUserMapper.selectOne(queryWrapper3);

                    Long trailerId = carPersonReplaceDO.getTrailerId();
                    LambdaQueryWrapper<TrailerDO> queryWrapper7 = new LambdaQueryWrapper<>();
                    queryWrapper7.eq(TrailerDO::getId, trailerId)
                            .select(TrailerDO::getVehicleTrailerNo);
                    TrailerDO trailerDO = trailerMapper.selectOne(queryWrapper7);

                    throw exception(TRANSPORT_CAR_ERROR06,trailerDO.getVehicleTrailerNo(),adminUserDO.getNickname());
                }

            }

            //更新车辆的车队长，车辆普危类型
            LambdaUpdateWrapper<CarDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(CarDO::getId, updateReqVO.getId())
                    .set(CarDO::getCaptainId, updateReqVO.getCaptainId())
                    .set(CarDO::getGodType, updateReqVO.getGodType());
            carMapper.update(null, updateWrapper);

            if (!trailerOrFleetChanged && !personnelChanged){//如果没做改变，什么改变都没有，直接返回
                return;
            }
            // 动态校验被占用的情况
            checkPositions(updateReqVO,carDO, mainIdChanged, deputyIdChanged, escortIdChanged, trailerIdChanged);
            // 3.2 检查是否所有相关人员都已经购买了保险和占用情况
            checkInsurance(updateReqVO);
            // 创建审批对象
            if (trailerOrFleetChanged || personnelChanged) {
                CarPersonReplaceDO personnelChangeDO = createPersonnelChangeDO(CarConvert.INSTANCE.convert(updateReqVO), carDO);

                if (trailerOrFleetChanged && personnelChanged) {
                    // 如果车队车挂和人员都发生变化
                    personnelChangeDO.setReplaceType(3); // 车队车挂和人员全部更换
                    carPersonReplaceMapper.insert(personnelChangeDO);
                    // 发起两个审批
                    trailerAndFleetProcessApproval(personnelChangeDO); // 车队车挂的审批
                    personnelProcessApproval(personnelChangeDO); // 驾押人员的审批
                } else if (trailerOrFleetChanged) {
                    // 如果只有车队和车挂发生变化
                    personnelChangeDO.setReplaceType(2); // 车队车挂更换
                    carPersonReplaceMapper.insert(personnelChangeDO);
                    // 发起车队车挂的审批
                    trailerAndFleetProcessApproval(personnelChangeDO);
                } else {
                    // 如果只有人员发生变化
                    personnelChangeDO.setReplaceType(1); // 人员更换
                    carPersonReplaceMapper.insert(personnelChangeDO);
                    // 发起驾押人员的审批
                    personnelProcessApproval(personnelChangeDO);
                }



            }
        }
    }

    /**
     * 车队车挂更换审批
     * @param carPersonDO
     */
    public void trailerAndFleetProcessApproval(CarPersonReplaceDO carPersonDO) {
        try {
            Long id = carPersonDO.getId();
            DingCarDetailsRespDTO dingCarDetailsRespDTO = carMapper.selectDingCarDetailsById(id);
            Map<String, Object> carchangeMap = getCarchangeMap2(dingCarDetailsRespDTO);
            String processId = processInstanceApi.submitForm2(carchangeMap, TemplateModuleEnum.TRAILER_FLEET_CHANGE);
            LambdaUpdateWrapper<CarPersonReplaceDO> updateWrapper1 = new LambdaUpdateWrapper<>();
            updateWrapper1.eq(CarPersonReplaceDO::getId, id)
                    .set(CarPersonReplaceDO::getProcessId, processId);
            carPersonReplaceMapper.update(null,updateWrapper1);

        } catch (Exception e) {
            log.info(LocalDateTime.now().toString() + "发起审批,异常信息:" + e.getMessage());
            throw exception(APPROVAL_FAILURE, e.getMessage());
        }
    }

    /**
     * 车队车挂更换表单数据
     * @param carDetailsRespDTO
     * @return
     * @throws JsonProcessingException
     */
    private Map<String, Object> getCarchangeMap2(DingCarDetailsRespDTO carDetailsRespDTO) throws JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> result = new HashMap<>();
        result.put("所属公司", carDetailsRespDTO.getDeptName() != null ? carDetailsRespDTO.getDeptName() : "_");
        result.put("车牌号", carDetailsRespDTO.getPlateNumber() != null ? carDetailsRespDTO.getPlateNumber() : "_");
        result.put("挂车号", carDetailsRespDTO.getVehicleTrailerNo() != null ? carDetailsRespDTO.getVehicleTrailerNo() : "_");
        result.put("车队", carDetailsRespDTO.getFleetName() != null ? carDetailsRespDTO.getFleetName() : "_");
        result.put("车队长", carDetailsRespDTO.getCaptainNickname() != null ? carDetailsRespDTO.getCaptainNickname() : "_");
        result.put("当前主驾", carDetailsRespDTO.getMainDriverNickname() != null ? carDetailsRespDTO.getMainDriverNickname() : "_");
        result.put("当前副驾", carDetailsRespDTO.getDeputyDriverNickname() != null ? carDetailsRespDTO.getDeputyDriverNickname() : "_");
        result.put("当前押运员", carDetailsRespDTO.getEscortNickname() != null ? carDetailsRespDTO.getEscortNickname() : "_");
        result.put("申请时间", carDetailsRespDTO.getApplyUserTime() != null ? carDetailsRespDTO.getApplyUserTime().format(formatter) : "_");
        result.put("申请人", carDetailsRespDTO.getApplyUserName() != null ? carDetailsRespDTO.getApplyUserName() : "_");
        result.put("更换车队", carDetailsRespDTO.getOldFleetName() != null ? carDetailsRespDTO.getOldFleetName() : "_");
        result.put("更换车挂", carDetailsRespDTO.getOldVehicleTrailerNo() != null ? carDetailsRespDTO.getOldVehicleTrailerNo() : "_");
        result.put("更换时间", carDetailsRespDTO.getReplaceTime() != null ? carDetailsRespDTO.getReplaceTime().format(formatter) : "_");
        result.put("备注", carDetailsRespDTO.getRemark() != null ? carDetailsRespDTO.getRemark() : "_");
        result.put("车辆停放位置", carDetailsRespDTO.getParkingPosition() != null ? carDetailsRespDTO.getParkingPosition() : "_");
        return result;
    }


    /**
     * 驾押人员更换审批
     * @param carPersonDO
     */
    @Override
    public void personnelProcessApproval(CarPersonReplaceDO carPersonDO) {
        try {
            Long id1 = carPersonDO.getId();
            CarChangeRespDTO carChangeRespDTO = carMapper.selectCarChangeById(id1);
            // 根据 oldMainId、oldDeputyId 和 oldEscortId 的值设置 mainName、deputyName 和 escortName
            setDriverNames(carPersonDO, carChangeRespDTO);

            System.out.println("carChangeRespDTO:" + carChangeRespDTO);
            Map<String, Object> carchangeMap = getCarchangeMap1(carChangeRespDTO);
            String processId = processInstanceApi.submitForm2(carchangeMap, TemplateModuleEnum.ESCORT_DEPUTY_CHANGE);
            LambdaUpdateWrapper<CarPersonReplaceDO> updateWrapper1 = new LambdaUpdateWrapper<>();
            updateWrapper1.eq(CarPersonReplaceDO::getId, id1)
                    .set(CarPersonReplaceDO::getProcessId, processId);
            carPersonReplaceMapper.update(null,updateWrapper1);

        } catch (Exception e) {
            e.printStackTrace();
            log.info(LocalDateTime.now().toString() + "发起审批,异常信息:" + e.getMessage());
            throw exception(APPROVAL_FAILURE, e.getMessage());
        }
    }

    private void setDriverNames(CarPersonReplaceDO carPersonDO, CarChangeRespDTO carChangeRespDTO) {
        // 设置主驾名称
        if (carPersonDO.getOldMainId() == null) {
            carChangeRespDTO.setMainName(carChangeRespDTO.getMainName());
        } else {
            carChangeRespDTO.setMainName(carChangeRespDTO.getMainName());
            carChangeRespDTO.setOldMainName(carChangeRespDTO.getNewMainName());
        }

        // 设置副驾名称
        if (carPersonDO.getOldDeputyId() == null) {
            carChangeRespDTO.setDeputyName(carChangeRespDTO.getDeputyName());
        } else {
            carChangeRespDTO.setDeputyName(carChangeRespDTO.getDeputyName());
            carChangeRespDTO.setOldDeputyName(carChangeRespDTO.getNewDeputyName());
        }

        // 设置押运员名称
        if (carPersonDO.getOldEscortId() == null) {
            carChangeRespDTO.setEscortName(carChangeRespDTO.getEscortName());
        } else {
            carChangeRespDTO.setEscortName(carChangeRespDTO.getEscortName());
            carChangeRespDTO.setOldEscortName(carChangeRespDTO.getNewEscortName());
        }
    }


    /**
     * 驾押人员表单数据
     * @param carChange
     * @return
     * @throws JsonProcessingException
     */
    private Map<String, Object> getCarchangeMap1(CarChangeRespDTO carChange) throws JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> result = new HashMap<>();
        result.put("所属公司", carChange.getDeptName() != null ? carChange.getDeptName() : "_");
        result.put("车牌号", carChange.getPlateNumber() != null ? carChange.getPlateNumber() : "_");
        result.put("车队", carChange.getFleetName() != null ? carChange.getFleetName() : "_");
        result.put("车队长", carChange.getCaptainName() != null ? carChange.getDeptName() : "_");
        result.put("当前主驾", carChange.getMainName() != null ? carChange.getMainName() : "_");
        result.put("当前副驾", carChange.getDeputyName() != null ? carChange.getDeputyName() : "_");
        result.put("当前押运员", carChange.getEscortName() != null ? carChange.getEscortName() : "_");
        result.put("申请时间", carChange.getApplyUserTime() != null ? carChange.getApplyUserTime().format(formatter) : "_");
        result.put("申请人", carChange.getApplyUserName() != null ? carChange.getApplyUserName() : "_");
        result.put("更换主驾", carChange.getOldMainName() != null ? carChange.getOldMainName() : "_");
        result.put("更换副驾", carChange.getOldDeputyName() != null ? carChange.getOldDeputyName() : "_");
        result.put("更换押运员", carChange.getOldEscortName() != null ? carChange.getOldEscortName() : "_");
        result.put("更换时间", carChange.getReplaceTime() != null ? carChange.getReplaceTime().format(formatter) : "_");
        result.put("备注", carChange.getRemark() != null ? carChange.getRemark() : "_");
        return result;
    }



    /**
     * 几个人员是否买了保险
     *
     * @param updateReqVO
     */
    private void checkInsurance(CarSaveReqVO updateReqVO) {
        List<Long> userIds = Arrays.asList(
                updateReqVO.getMainId(), updateReqVO.getTrailerId(),
                updateReqVO.getEscortId(), updateReqVO.getDeputyId()
        );
        for (Long userId : userIds) {
            if (userId == null) {
                continue;
            }
            AdminUserDO adminUserDO = userMapper.selectById(userId);
            if (ObjectUtils.isNotEmpty(adminUserDO)) {
                LambdaQueryWrapperX<RosterDO> queryWrapper = new LambdaQueryWrapperX<>();
                queryWrapper.eq(RosterDO::getUserId, adminUserDO.getDingUserId());
                RosterDO rosterDO = rosterMapper.selectOne(queryWrapper);
                if (ObjectUtils.isNotEmpty(rosterDO)) {
                    String insurancePaymentType = rosterDO.getInsurancePaymentType();
                    if (ObjectUtils.isEmpty(InsuranceTypeEnum.fromCode(insurancePaymentType))) {
                        throw exception(TRANSPORT_CAR_ERROR04, rosterDO.getName());
                    }
                }
            }
        }
    }

    /**
     * 主驾驶、车挂、副驾驶和押运员位置是否重复，为后续的更新修改收集准备
     *
     * @param updateReqVO
     */
        private void checkPositions(CarSaveReqVO updateReqVO,CarDO currentCar, boolean mainIdChanged, boolean deputyIdChanged, boolean escortIdChanged, boolean trailerIdChanged) {
            Long id = updateReqVO.getId();
            Long newMainId = updateReqVO.getMainId(); // 新主驾
            Long newTrailerId = updateReqVO.getTrailerId(); // 新车挂
            Long newEscortId = updateReqVO.getEscortId(); // 新押运员
            Long newDeputyId = updateReqVO.getDeputyId(); // 新副驾

            // 记录变更数量
            int changeCount = (mainIdChanged ? 1 : 0) + (deputyIdChanged ? 1 : 0) + (escortIdChanged ? 1 : 0);

            if (changeCount >= 2) {
                // 如果有两个或以上的角色改变，进行相同性校验
                if ((mainIdChanged && deputyIdChanged && newMainId != null && newDeputyId != null && newMainId.equals(newDeputyId)) ||
                        (mainIdChanged && escortIdChanged && newMainId != null && newEscortId != null && newMainId.equals(newEscortId)) ||
                        (deputyIdChanged && escortIdChanged && newDeputyId != null && newEscortId != null && newDeputyId.equals(newEscortId)) ||
                        (deputyIdChanged && escortIdChanged  && newEscortId != null && newEscortId.equals(newMainId)) ||
                        (deputyIdChanged && escortIdChanged  && newDeputyId != null && newDeputyId.equals(newMainId))) {
                    throw exception(TRANSPORT_CAR_ERROR05);
                }
            } else if (changeCount == 1) {
                // 如果只有一个角色改变
                if (mainIdChanged) {
                    if (newDeputyId != null && newMainId.equals(newDeputyId)) {
                        throw exception(TRANSPORT_CAR_ERROR05);
                    }
                    if (newEscortId != null && newMainId.equals(newEscortId)) {
                        throw exception(TRANSPORT_CAR_ERROR05);
                    }
                }
                if (deputyIdChanged) {
                    if (newDeputyId != null  && newDeputyId.equals(newMainId)) {
                        throw exception(TRANSPORT_CAR_ERROR05);
                    }
                }
                if (escortIdChanged) {
                    if (newEscortId != null &&  newMainId.equals(newEscortId)) {
                        throw exception(TRANSPORT_CAR_ERROR05);
                    }
                }
            }





            // 查询需要校验的记录
        LambdaQueryWrapper<CarDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.ne(CarDO::getId, id).eq(CarDO::getStatus, 0).eq(CarDO::getDeleted,0)
                .and(qw1 -> qw1.eq(newMainId != null, CarDO::getMainId, newMainId)
                        .or().eq(newMainId != null, CarDO::getDeputyId, newMainId)
                        .or().eq(newMainId != null, CarDO::getEscortId, newMainId)
                        .or().eq(newDeputyId != null, CarDO::getMainId, newDeputyId)
                        .or().eq(newDeputyId != null, CarDO::getDeputyId, newDeputyId)
                        .or().eq(newDeputyId != null, CarDO::getEscortId, newDeputyId)
                        .or().eq(newEscortId != null, CarDO::getMainId, newEscortId)
                        .or().eq(newEscortId != null, CarDO::getDeputyId, newEscortId)
                        .or().eq(newEscortId != null, CarDO::getEscortId, newEscortId)
                        .or().eq(newTrailerId != null, CarDO::getTrailerId, newTrailerId));

        List<CarDO> otherCars = carMapper.selectList(queryWrapper1);

        if (CollectionUtils.isNotEmpty(otherCars)) {
            for (CarDO otherCar : otherCars) {
                MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(otherCar.getMainVehicleId());
                if (ObjectUtils.isNotEmpty(mainVehicleDO)) {
                    if (mainIdChanged) {
                        if (Objects.equals(otherCar.getMainId(), newMainId) || Objects.equals(otherCar.getDeputyId(), newMainId) || Objects.equals(otherCar.getEscortId(), newMainId)) {
                            throw exception(TRANSPORT_CAR_ERROR01, mainVehicleDO.getPlateNumber());
                        }
                    }
                    if (deputyIdChanged) {
                        if (Objects.equals(otherCar.getMainId(), newDeputyId) || Objects.equals(otherCar.getDeputyId(), newDeputyId) || Objects.equals(otherCar.getEscortId(), newDeputyId)) {
                            throw exception(TRANSPORT_CAR_ERROR02, mainVehicleDO.getPlateNumber());
                        }
                    }
                    if (escortIdChanged) {
                        if (Objects.equals(otherCar.getMainId(), newEscortId) || Objects.equals(otherCar.getDeputyId(), newEscortId) || Objects.equals(otherCar.getEscortId(), newEscortId)) {
                            throw exception(TRANSPORT_CAR_ERROR03, mainVehicleDO.getPlateNumber());
                        }
                    }
                    if (Objects.equals(otherCar.getTrailerId(), newTrailerId)) {
                        throw exception(TRANSPORT_CAR_ERROR, mainVehicleDO.getPlateNumber());
                    }
                }
            }
        }
    }

    /**
     * 填充位置列表，为后续更新做准备
     *
     * @param updateReqVO
     * @param mainIdList
     * @param trailerIdList
     * @param escortIdList
     * @param deputyIdList
     */
    private void fillPositions(CarSaveReqVO updateReqVO, List<Long> mainIdList, List<Long> trailerIdList, List<Long> escortIdList, List<Long> deputyIdList, List<Long> fleetIdList) {
        Long id = updateReqVO.getId();
        Long mainId = updateReqVO.getMainId();
        Long trailerId = updateReqVO.getTrailerId();
        Long escortId = updateReqVO.getEscortId();
        Long deputyId = updateReqVO.getDeputyId();
        Long fleetId = updateReqVO.getFleetId();

        LambdaQueryWrapper<CarDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(CarDO::getId, id).eq(CarDO::getStatus, 0)
                .and(qw -> qw.eq(mainId != null, CarDO::getMainId, mainId)
                        .or().eq(trailerId != null, CarDO::getTrailerId, trailerId)
                        .or().eq(escortId != null, CarDO::getEscortId, escortId)
                        .or().eq(deputyId != null, CarDO::getDeputyId, deputyId)
                        .or().eq(fleetId != null, CarDO::getFleetId, fleetId));

        List<CarDO> carDOList = carMapper.selectList(queryWrapper);

        if (CollectionUtils.isNotEmpty(carDOList)) {
            for (CarDO carDO : carDOList) {
                MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(carDO.getMainVehicleId());
                if (Objects.equals(carDO.getMainId(), mainId)) {
                    if (ObjectUtils.isNotEmpty(mainVehicleDO)) {
                        mainIdList.add(carDO.getId());
                    }
                }
                if (Objects.equals(carDO.getTrailerId(), trailerId)) {
                    if (ObjectUtils.isNotEmpty(mainVehicleDO)) {
                        trailerIdList.add(carDO.getId());
                    }
                }
                if (Objects.equals(carDO.getEscortId(), escortId)) {
                    if (ObjectUtils.isNotEmpty(mainVehicleDO)) {
                        escortIdList.add(carDO.getId());
                    }
                }
                if (Objects.equals(carDO.getDeputyId(), deputyId)) {
                    if (ObjectUtils.isNotEmpty(mainVehicleDO)) {
                        deputyIdList.add(carDO.getId());
                    }
                }
                if (Objects.equals(carDO.getFleetId(), fleetId)) {
                    if (ObjectUtils.isNotEmpty(mainVehicleDO)) {
                        fleetIdList.add(carDO.getId());
                    }
                }
            }
        }
    }

    /**
     * 更新重复的主驾驶、车挂、副驾驶和押运员，被覆盖之后修改为默认值
     *
     * @param mainIdList
     * @param trailerIdList
     * @param escortIdList
     * @param deputyIdList
     */
        private void updateOccupiedPositions(List<Long> mainIdList, List<Long> trailerIdList, List<Long> escortIdList, List<Long> deputyIdList, List<Long> fleetIdList) {
        if (CollectionUtils.isNotEmpty(mainIdList)) {
            LambdaUpdateWrapper<CarDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(CarDO::getMainId, mainIdList)
                    .eq(CarDO::getStatus, 0)
                    .set(CarDO::getMainId, null);
            carMapper.update(null, updateWrapper);
        }
        if (CollectionUtils.isNotEmpty(trailerIdList)) {
            LambdaUpdateWrapper<CarDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(CarDO::getTrailerId, trailerIdList)
                    .eq(CarDO::getStatus, 0)
                    .set(CarDO::getTrailerId, null)
                    .set(CarDO::getActualTonnage, null); // 实际装载吨位 计算得到的
            carMapper.update(null, updateWrapper);
        }
        if (CollectionUtils.isNotEmpty(escortIdList)) {
            LambdaUpdateWrapper<CarDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(CarDO::getEscortId, escortIdList)
                    .eq(CarDO::getStatus, 0)
                    .set(CarDO::getEscortId, null);
            carMapper.update(null, updateWrapper);
        }
        if (CollectionUtils.isNotEmpty(deputyIdList)) {
            LambdaUpdateWrapper<CarDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(CarDO::getDeputyId, deputyIdList)
                    .eq(CarDO::getStatus, 0)
                    .set(CarDO::getDeputyId, null);
            carMapper.update(null, updateWrapper);
        }
//        if (CollectionUtils.isNotEmpty(fleetIdList)) {
//            LambdaUpdateWrapper<CarDO> updateWrapper = new LambdaUpdateWrapper<>();
//            updateWrapper.in(CarDO::getId, fleetIdList)
//                    .eq(CarDO::getStatus, 0)
//                    .set(CarDO::getFleetId, null);
//            carMapper.update(null, updateWrapper);
//        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCar(Long id) {
        // 校验存在
        validateCarExists(id);
        CarDO car = carMapper.selectById(id);
        //删除前更新车头车挂表的闲置状态
        LambdaUpdateWrapper<MainVehicleDO> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(MainVehicleDO::getId, car.getMainVehicleId())
                .set(MainVehicleDO::getIsIdle, 1);
        mainVehicleMapper.update(null, queryWrapper);
        LambdaUpdateWrapper<TrailerDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TrailerDO::getId, car.getTrailerId())
                .set(TrailerDO::getIsIdle, 1);
        trailerMapper.update(null, updateWrapper);
        // 删除
        carMapper.deleteById(id);
    }

    private void validateCarExists(Long id) {
        if (carMapper.selectById(id) == null) {
            throw exception(CAR_NOT_EXISTS);
        }
    }

    /**
     * 校验数据合法
     *
     * @param createReqVO
     */
    private void validateCarVO(CarSaveReqVO createReqVO) {
        Long id = createReqVO.getId();
        // 一辆车由一个车头+一个车挂组成，已绑定的车头或车挂不能重复绑定，注意校验
        Long mainVehicleId = createReqVO.getMainVehicleId();
        // 1、检查车头是否已存在绑定
        LambdaQueryWrapper<CarDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(id != null, CarDO::getId, id)
                .eq(CarDO::getMainVehicleId, mainVehicleId)
                .eq(CarDO::getStatus, 0);
        boolean exists = carMapper.exists(queryWrapper);
        if (exists) {
            throw exception(CAR_ERROR_05);
        }

        //3、【普危类型】 判断
        Long deputyId = createReqVO.getDeputyId();
        Long escortId = createReqVO.getEscortId();
        Integer godType = createReqVO.getGodType();
        Integer code1 = GodTypeEnum.HAZARDOUS_GOODS.getCode();
        Integer code2 = GodTypeEnum.MIXED_GENERAL_HAZARDOUS_GOODS.getCode();
        // 3.1 【危货】或【普危对流】时,必须有副驾或押运员必填其中一个
        if (godType.equals(code1) || godType.equals(code2)) {
            if ((escortId == null && deputyId == null) || (escortId != null && deputyId != null)) {
                throw exception(CAR_ERROR_01);
            }
        } else {
            //3.2 其他情况可以为空，但不能全部选择
            if (escortId != null && deputyId != null) {
                throw exception(CAR_ERROR_01);
            }
        }

    }

    @Override
    public CarRespVO getCar(Long id) {
        CarDO carDO = carMapper.selectById(id);
        if (carDO == null) {
            throw exception(CAR_NOT_EXISTS);
        }
        CarRespVO convert = CarConvert.INSTANCE.convert(carDO);
        Long mainVehicleId = carDO.getMainVehicleId();
        MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(mainVehicleId);
        if (mainVehicleDO != null) {
            convert.setVehicleBrand(mainVehicleDO.getVehicleBrand());
            convert.setUserYears(mainVehicleDO.getUserYears());
            convert.setFrontWeight(mainVehicleDO.getFrontWeight());
            convert.setMainVehicleName(mainVehicleDO.getPlateNumber());
        }

        Long trailerId = carDO.getTrailerId();
        TrailerDO trailerDO = trailerMapper.selectById(trailerId);
        if (trailerDO != null) {
            convert.setTUserYears(trailerDO.getUserYears());
            convert.setTankType(trailerDO.getTankType());
            convert.setTrailerWeight(trailerDO.getTrailerWeight());
            convert.setTrailerName(trailerDO.getVehicleTrailerNo());
            convert.setVerificationmass(trailerDO.getVerificationmass());
            Duration duration1 = Duration.between(trailerDO.getCertificatTime(), LocalDateTime.now());
            Integer years1 = Math.toIntExact(duration1.toDays() / 365); // 计算总共的使用年限，假设每年有365天
            convert.setTUserYears(years1);
        }

        String commodityId = carDO.getCommodityId();
        if (!StringUtils.isEmpty(commodityId)){

            List<Long> commodityIds = Arrays.stream(commodityId.split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            convert.setCommodityIds(commodityIds);

            LambdaQueryWrapper<CommodityDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(CommodityDO::getId, commodityIds)
                    .eq(CommodityDO::getDeleted,0)
                    .select(CommodityDO::getId,CommodityDO::getName);
            List<CommodityDO> commodityDOS = commodityMapper.selectList(queryWrapper);

            if (!commodityDOS.isEmpty()){
                List<String> commodityNames = commodityDOS.stream().map(CommodityDO::getName).collect(Collectors.toList());
                String collect = commodityNames.stream().map(Object::toString).collect(Collectors.joining(","));
                convert.setCommodityNames(collect);
            }

            //主驾、副驾、押运员、车队长手机号返回
            Long captainId = carDO.getCaptainId();
            if (captainId != null){
                AdminUserRespDTO user = adminUserApi.getUser(captainId);
                if (user != null){
                    convert.setCaptainPhone(user.getMobile());
                }
            }

            Long deputyId = carDO.getDeputyId();
            if (deputyId != null){
                AdminUserRespDTO user = adminUserApi.getUser(deputyId);
                if (user != null){
                    convert.setDeputyPhone(user.getMobile());
                }
            }

            Long escortId = carDO.getEscortId();
            if (escortId != null){
                AdminUserRespDTO user = adminUserApi.getUser(escortId);
                if (user != null){
                    convert.setEscortPhone(user.getMobile());
                }
            }

            Long mainId = carDO.getMainId();
            if (mainId != null){
                AdminUserRespDTO user = adminUserApi.getUser(mainId);
                if (user != null){
                    convert.setMainPhone(user.getMobile());
                }
            }
        }

        Long companyId = convert.getCompanyId();
        if (companyId != null){
            DeptDO deptDO = deptMapper.selectById(companyId);
            convert.setCompanyName(deptDO.getName());
        }

        return convert;
    }


    @Override
    public List<CarRespVO> getCarList(Collection<Long> ids) {
        List<CarDO> carDOS = carMapper.selectBatchIds(ids);

        if (carDOS.isEmpty()) {
            // 处理车辆不存在的情况...
            throw exception(CAR_NOT_EXISTS);
        }
        List<Long> collectId = carDOS.stream().map(CarDO::getDeptId).collect(Collectors.toList());
        List<DeptDO> deptDOs = deptMapper.selectBatchIds(collectId);
        List<CarRespVO> carRespVOS = CarConvert.INSTANCE.convertList(carDOS);
        Map<Long, String> deptNameMap = deptDOs.stream().collect(Collectors.toMap(DeptDO::getId, DeptDO::getName));
//        carRespVOS.forEach(item -> item.getCompanyName(deptNameMap.get(item.getCompanyId())));
        return carRespVOS;
    }


    @Override
    public List<CarExcelVO> getCarList(CarExportReqVO exportReqVO) {

        List<CarExcelVO> carRespVOS = carMapper.exportList(exportReqVO);
        if (ObjectUtils.isNotEmpty(carRespVOS)){
            carRespVOS.forEach(record -> {

                List<Map<String, Object>> commodityInfo = trailerService.getCommodityInfoById(record.getTrailerId());
                List<String> commodityNames = commodityInfo.stream()
                        .map(map -> (String) map.get("commodityName"))
                        .collect(Collectors.toList());
                record.setCommodityNames(String.join(",", commodityNames));
            });
        }
        return carRespVOS;
    }

    @Override
    public List<HashMap<String, Object>> getExportList() {

        List<HashMap<String, Object>> exportList = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Field[] fields = CarExcelVO.class.getDeclaredFields();

        for (Field field : fields) {
            // 获取字段的注解
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                String[] values = excelProperty.value();
                if (values.length > 0) {
                    String key = field.getName(); // 使用字段名称作为键
                    String value = values[0]; // 使用 ExcelProperty 注解的第一个值作为值
                    map.put(key, value);
                }
            }
        }

        exportList.add(map);
        return exportList;
    }


//    @Override
//    public void batchUpdateCar(List<CarSaveReqVO> updateReqVOList) {
//        // 在这里处理批量更新逻辑
//        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
//        for (CarSaveReqVO updateReqVO : updateReqVOList) {
//            updateCar(updateReqVO);
//        }
//    }

    @Override
    public void batchDeleteCar(List<Long> ids) {
        // 在这里处理批量删除逻辑
        carMapper.deleteBatchIds(ids);
    }

    @Override
    public void batchImportCar(List<CarDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        carMapper.insertBatch(importReqVOList);
    }

    @Override
    public List<CarImportExcelVO> importPreviewList(List<CarImportExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollectionUtil.isEmpty(importDatas)) {
            throw exception(CAR_IMPORT_LIST_IS_EMPTY);
        }

        List<CarImportExcelVO> list = new ArrayList<>();
        Map<String, Long> companyMap = getCompanyMap(); // 获取所有所属公司
        Map<String, Long> mainVehicleMap = new HashMap<>();
        Map<String, Long> trailerMap = new HashMap<>();
        Map<String, Long> driverMap = new HashMap<>();
        Map<String, Long> fleetMap = new HashMap<>();

        //普危类型字典
        List<DictDataRespDTO> godTypes = dictDataApi.getDictDatas("god_type");
        Map<String,String> godTypeMap = null;
        if (CollectionUtil.isNotEmpty(godTypes)){
            godTypeMap = godTypes.stream()
                        .collect(Collectors.toMap(
                            DictDataRespDTO::getValue,DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        for (CarImportExcelVO item : importDatas) {
            item.setFailData(new HashMap<>()); // 初始化失败数据


            // 校验主驾驶、副驾驶和押运员
            boolean hasMainName = StringUtils.isNotBlank(item.getMainName());
            boolean hasDeputyName = StringUtils.isNotBlank(item.getDeputyName());
            boolean hasEscortName = StringUtils.isNotBlank(item.getEscortName());

            if (!hasMainName && !hasDeputyName && !hasEscortName){
                item.getFailData().put("mainName", "主驾不能为空！");
                item.getFailData().put("deputyName", "副驾不能为空！");
                item.getFailData().put("escortName", "押运员不能为空！");
                item.setHasError(true);
            }
            if (hasMainName){
                if (hasDeputyName && hasEscortName){
                    item.getFailData().put("mainName", "主驾、副驾、押运员不可同时存在，请选择主驾和另外两个其中之一！");
                    item.getFailData().put("deputyName", "主驾、副驾、押运员不可同时存在，请选择主驾和另外两个其中之一！");
                    item.getFailData().put("escortName", "主驾、副驾、押运员不可同时存在，请选择主驾和另外两个其中之一！");
                    item.setHasError(true);
                }
                else if (!hasDeputyName && !hasEscortName){
                    item.getFailData().put("deputyName", "副驾驶或押运员不能为空，至少选择一个！");
                    item.getFailData().put("escortName", "副驾驶或押运员不能为空，至少选择一个！");
                    item.setHasError(true);
                }
            }
            else {
                if (hasDeputyName || hasEscortName){
                    item.getFailData().put("mainName", "主驾不能为空，请重新导入！");
                    item.setHasError(true);
                }
            }


            //校验名称是否相同
            if (hasMainName && hasDeputyName){
                if (item.getMainName().equals(item.getDeputyName())){
                    item.getFailData().put("mainName", "主驾和副驾不能相同，请重新导入！");
                    item.getFailData().put("deputyName", "主驾和副驾不能相同，请重新导入！");
                    item.setHasError(true);
                }
            } else if (hasMainName && hasEscortName){
                if (item.getMainName().equals(item.getEscortName())){
                    item.getFailData().put("mainName", "主驾和押运员不能相同，请重新导入！");
                    item.getFailData().put("escortName", "主驾和押运员不能相同，请重新导入！");
                    item.setHasError(true);
                }
            }


            //校验必填字段
            checkAndSetError(item, item.getCompanyName(), "companyName", "所属公司不能为空");
            checkAndSetError(item, item.getGodType(), "godType", "普危类型不能为空");
            checkAndSetError(item, item.getMainVehicleName(), "mainVehicleName", "主车号不能为空");
            checkAndSetError(item, item.getFleetName(), "fleetName", "车队不能为空");
            checkAndSetError(item, item.getTrailerName(), "trailerName", "车挂号不能为空");
            checkAndSetError(item, item.getCaptainName(), "captainName", "车队长不能为空");
            checkAndSetError(item, item.getCommodityNames(), "commodityNames", "运输介质不能为空");


            //校验字典是否存在
            if (item.getGodType() != null &&!godTypeMap.containsKey(item.getGodType())) {
                item.getFailData().put("godType", "车辆普危类型不存在");
                item.setHasError(true); // 设置为有错误
            }

            Long companyId = companyMap.get(item.getCompanyName());
            if (companyId == null) {
                item.getFailData().put("companyName", "所属公司不存在，请先维护！");
                item.setHasError(true);
                continue; // 跳过这个条目
            } else {
                item.setCompanyId(companyId);
            }

            // 根据所属公司ID获取相关的数据
            mainVehicleMap = getMainVehicleMap(companyId); // 获取所有车头号
            trailerMap = getTrailerMap(companyId); // 获取所有车挂号
            driverMap = getDriveMap(companyId); // 获取所有驾驶员（主驾、副驾、押运员、车队长）
            fleetMap = getFleetMap(companyId); // 获取所有车队
            List<CarRespVO> carRespVOS = carMapper.exportCheckData(); // 获取所有车辆数据

            // 将车头号、车挂号和驾驶员转换为集合进行查重
            Set<String> existingMainVehicleSet = carRespVOS.stream()
                    .map(CarRespVO::getMainVehicleName)
                    .collect(Collectors.toSet());

            Set<String> existingTrailerSet = carRespVOS.stream()
                    .map(CarRespVO::getTrailerName)
                    .collect(Collectors.toSet());

            Set<String> existingDriverSet = carRespVOS.stream()
                    .flatMap(car -> Stream.of(car.getMainName(), car.getDeputyName(), car.getEscortName()))
                    .collect(Collectors.toSet());

            if ( item.getMainVehicleName() != null && existingMainVehicleSet.contains(item.getMainVehicleName())) {
                item.getFailData().put("mainVehicleName", "主车号已存在车辆信息管理表中，请重新选择！");
                item.setHasError(true);
            }

            if ( item.getTrailerName() != null && existingTrailerSet.contains(item.getTrailerName())) {
                item.getFailData().put("trailerName", "车挂号已存在车辆信息管理表中，请重新选择！");
                item.setHasError(true);
            }

            if ( item.getMainName() != null && existingDriverSet.contains(item.getMainName())) {
                item.getFailData().put("mainName", "主驾已存在车辆信息管理表中，请重新选择！");
                item.setHasError(true);
            }
            if ( item.getDeputyName() != null && existingDriverSet.contains(item.getDeputyName())) {
                item.getFailData().put("deputyName", "副驾已存在车辆信息管理表中，请重新选择！");
                item.setHasError(true);
            }
            if (item.getEscortName() != null && existingDriverSet.contains( item.getEscortName())) {
                item.getFailData().put("escortName", "押运员已存在车辆信息管理表中，请重新选择！");
                item.setHasError(true);
            }



            if (item.getId() != null) {
                CarDO carDO = carMapper.selectById(item.getId());
                if (carDO == null) {
                    item.setIsUpdateSupport(false); // 设置为新增
                } else {
                    item.setIsUpdateSupport(true); // 设置为更新
                    handleExistingCar(item, mainVehicleMap, trailerMap, driverMap, fleetMap, carRespVOS);
                    checkCommodityExistsInTrailer(item);
                }
            } else {
                item.setIsUpdateSupport(false); // 设置为新增
                handleNewCar(item, mainVehicleMap, trailerMap, driverMap, fleetMap, carRespVOS);
                checkCommodityExistsInTrailer(item);
            }

        }

        return importDatas;
    }

    //校验介质是否存在于当前的车挂
    private void checkCommodityExistsInTrailer(CarImportExcelVO item) {
        if (item.getTrailerId() != null) {
            // 查询车挂对应的介质列表
            List<Map<String, Object>> maps = trailerMapper.selectCommodityByTrailerId(item.getTrailerId());

            Map<Object, String> idToNameMap = maps.stream()
                    .collect(Collectors.toMap(map -> map.get("id"), map -> (String) map.get("name")));

            List<String> dbCommodityNames = new ArrayList<>(idToNameMap.values());

            // 获取前端传过来的介质名称列表
            List<String> frontendCommodityList = Arrays.asList(item.getCommodityNames().split(","));

            // 检查传递的介质是否存在
            boolean allExist = frontendCommodityList.stream()
                    .allMatch(dbCommodityNames::contains);

            if (!allExist) {
                item.getFailData().put("commodityNames", "导入的运输介质不存在于当前公司下的车挂中，请重新选择！");
                item.setHasError(true);
            } else {

                List<Long> commodityIds = frontendCommodityList.stream()
                        .flatMap(name -> idToNameMap.entrySet().stream()
                                .filter(entry -> entry.getValue().equals(name))
                                .map(entry -> (Long) entry.getKey())
                        )
                        .collect(Collectors.toList());

                item.setCommodityNames(item.getCommodityNames());
                item.setCommodityIds(commodityIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
            }
        }
    }

    // 校验字段是否为空，并设置错误信息
    private void checkAndSetError(CarImportExcelVO item, String fieldValue, String fieldName, String errorMessage) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            item.setHasError(true); // 设置为 true
            item.getFailData().put(fieldName, errorMessage); // 添加到 failData 中
        }
    }

    private void handleExistingCar(CarImportExcelVO item, Map<String, Long> mainVehicleMap, Map<String, Long> trailerMap,
                                   Map<String, Long> driverMap, Map<String, Long> fleetMap, List<CarRespVO> carRespVOS) {

        // 校验并更新字段
        validateAndUpdateCar(item, mainVehicleMap, trailerMap, driverMap, fleetMap);
    }

    private void handleNewCar(CarImportExcelVO item, Map<String, Long> mainVehicleMap, Map<String, Long> trailerMap,
                              Map<String, Long> driverMap, Map<String, Long> fleetMap, List<CarRespVO> carRespVOS) {
        // 校验并更新字段
        validateAndUpdateCar(item, mainVehicleMap, trailerMap, driverMap, fleetMap);
    }

    private void validateAndUpdateCar(CarImportExcelVO source, Map<String, Long> mainVehicleMap, Map<String, Long> trailerMap,
                                      Map<String, Long> driverMap, Map<String, Long> fleetMap) {
        // 校验并更新字段
        validateField(source.getMainVehicleName(), mainVehicleMap, "mainVehicleName", "该所属公司下主车号不存在，请先维护！", source, source::setMainVehicleId);
        validateField(source.getTrailerName(), trailerMap, "trailerName", "该所属公司下车挂号不存在，请先维护！", source, source::setTrailerId);
        validateField(source.getMainName(), driverMap, "mainName", "该所属公司下主驾不存在，请先维护！", source, source::setMainId);
        validateField(source.getDeputyName(), driverMap, "deputyName", "该所属公司下副驾不存在，请先维护！", source, source::setDeputyId);
        validateField(source.getEscortName(), driverMap, "escortName", "该所属公司下押运员不存在，请先维护！", source, source::setEscortId);
        validateField(source.getCaptainName(), driverMap, "captainName", "该所属公司下车队长不存在，请先维护！", source, source::setCaptainId);
        validateField(source.getFleetName(), fleetMap, "fleetName", "该所属公司下车队不存在，请先维护！", source, source::setFleetId);
    }

    private boolean validateField(String fieldName, Map<String, Long> map, String fieldKey, String errorMessage, CarImportExcelVO source, Consumer<Long> setter) {
        if (fieldName != null) {
            Long id = map.get(fieldName);
            if (id != null) {
                if (setter != null) {
                    setter.accept(id); // 更新CarImportExcelVO对象的相应字段
                }
                return true;
            } else {
                source.getFailData().put(fieldKey, errorMessage);
                return false;
            }
        }
        return true;
    }

    private Map<String, Long> getCompanyMap() {
        LambdaQueryWrapper<DeptDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptDO::getDeleted,0)
                .eq(DeptDO::getParentId, 498);
        List<DeptDO> deptDOS = deptMapper.selectList(queryWrapper);

        if (deptDOS != null) {
            Map<String, Long> collect = deptDOS.stream()
                    .collect(Collectors.toMap(DeptDO::getName, DeptDO::getId, (existing, replacement) -> existing));
            return collect;
        }
        return Collections.emptyMap();
    }

    private Map<String, Long> getMainVehicleMap(Long companyIds) {
        LambdaQueryWrapper<MainVehicleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(MainVehicleDO::getPlateNumber, MainVehicleDO::getId)
                .eq(MainVehicleDO::getDeleted, 0)
                .isNull(MainVehicleDO::getStatus)
                .in(MainVehicleDO::getCompanyId, companyIds)
                .eq(MainVehicleDO::getVehicleStatus, 0)
                .eq(MainVehicleDO::getIsIdle, 1);
        return mainVehicleMapper.selectList(wrapper).stream()
                .collect(Collectors.toMap(MainVehicleDO::getPlateNumber, MainVehicleDO::getId, (existing, replacement) -> existing));
    }

    private Map<String, Long> getTrailerMap(Long companyIds) {
        LambdaQueryWrapper<TrailerDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(TrailerDO::getVehicleTrailerNo, TrailerDO::getId)
                .eq(TrailerDO::getDeleted, 0)
                .eq(TrailerDO::getCompanyId, companyIds)
                .eq(TrailerDO::getIsIdle, 1)
                .isNull(TrailerDO::getStatus);
        return trailerMapper.selectList(wrapper).stream()
                .collect(Collectors.toMap(TrailerDO::getVehicleTrailerNo, TrailerDO::getId, (existing, replacement) -> existing));
    }

    private Map<String, Long> getDriveMap(Long companyIds) {
        LambdaQueryWrapper<AdminUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(AdminUserDO::getNickname, AdminUserDO::getId)
                .eq(AdminUserDO::getDeleted, 0)
                .in(AdminUserDO::getOrgId, companyIds);

        return adminUserMapper.selectList(wrapper).stream()
                .collect(Collectors.toMap(AdminUserDO::getNickname, AdminUserDO::getId, (existing, replacement) -> existing));
    }

    private Map<String, Long> getFleetMap(Long companyIds) {
        LambdaQueryWrapper<FleetDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(FleetDO::getName, FleetDO::getId)
                .eq(FleetDO::getDeleted, 0)
                .eq(FleetDO::getDeptId, companyIds);
        Map<String, Long> collect = fleetMapper.selectList(wrapper).stream()
                .collect(Collectors.toMap(FleetDO::getName, FleetDO::getId, (existing, replacement) -> existing));
        return collect;
    }




    @Override
    public ImportResult importData(CarImportExcelVO importReqVo) {

        List<CarImportExcelVO> importDatas = importReqVo.getImportDatas();

        // 过滤有效数据
        List<CarImportExcelVO> importListData = importDatas.stream()
                .filter(item -> item.getHasError() == null || !item.getHasError())
                .collect(Collectors.toList());

        List<CarImportExcelVO> importListUpdate = new ArrayList<>();
        List<CarImportExcelVO> importListInsert = new ArrayList<>();

        //进行分类
        for (CarImportExcelVO item : importListData) {
            if (item.getIsUpdateSupport()) {
                importListUpdate.add(item);
            } else {
                importListInsert.add(item);
            }
        }

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 异步处理更新操作
        Future<?> updateFuture = executor.submit(() -> handleUpdates(importListUpdate));

        // 异步处理新增操作
        Future<?> insertFuture = executor.submit(() -> handleInserts(importListInsert));

        // 等待所有任务完成
        try {
            updateFuture.get();
            insertFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // 关闭线程池
        }

        // 构建并返回 ImportResult
        return ImportResult.<CarImportExcelVO>builder()
                .data(importListData) // 返回有效数据
                .build();
    }

    // 更新操作
    private void handleUpdates(List<CarImportExcelVO> updates) {
        //根据id查找数据
        for (CarImportExcelVO update : updates) {
            LambdaUpdateWrapper<CarDO> queryWrapper = new LambdaUpdateWrapper<>();
            queryWrapper.eq(CarDO::getId,update.getId());

            // 检查每个字段是否为null。如果字段不为 null，设置该字段的值；如果字段为 null，将字段更新为 null
            if (update.getMainVehicleId() != null) {
                queryWrapper.set(CarDO::getMainVehicleId, update.getMainVehicleId());
            } else {
                queryWrapper.set(CarDO::getMainVehicleId, (Long) null);
            }

            if (update.getTrailerId() != null) {
                queryWrapper.set(CarDO::getTrailerId, update.getTrailerId());
            } else {
                queryWrapper.set(CarDO::getTrailerId, (Long) null);
            }


            if (update.getGodType() != null) {
                queryWrapper.set(CarDO::getGodType, update.getGodType());
            } else {
                queryWrapper.set(CarDO::getGodType, (Integer) null);
            }

            if (update.getFleetId() != null) {
                queryWrapper.set(CarDO::getFleetId, update.getFleetId());
            } else {
                queryWrapper.set(CarDO::getFleetId, (Long) null);
            }

            if (update.getCaptainId() != null) {
                queryWrapper.set(CarDO::getCaptainId, update.getCaptainId());
            } else {
                queryWrapper.set(CarDO::getCaptainId, (Long) null);
            }

            if (update.getDeputyId() != null) {
                queryWrapper.set(CarDO::getDeputyId, update.getDeputyId());
            } else {
                queryWrapper.set(CarDO::getDeputyId, (Long) null);
            }

            if (update.getEscortId() != null) {
                queryWrapper.set(CarDO::getEscortId, update.getEscortId());
            } else {
                queryWrapper.set(CarDO::getEscortId, (Long) null);
            }

            if (update.getMainId() != null) {
                queryWrapper.set(CarDO::getMainId, update.getMainId());
            } else {
                queryWrapper.set(CarDO::getMainId, (Long) null);
            }
            if (update.getCompanyId() != null) {
                queryWrapper.set(CarDO::getCompanyId, update.getCompanyId());
            } else {
                queryWrapper.set(CarDO::getCompanyId, (Long) null);
            }
            carMapper.update(null, queryWrapper);
        }

    }

    // 新增操作
    @Transactional(rollbackFor = Exception.class)
    void handleInserts(List<CarImportExcelVO> inserts) {
        //获取MainVehicleIds
        List<Long> mainVehicleIds = inserts.stream()
                .map(CarImportExcelVO::getMainVehicleId)
                .collect(Collectors.toList());
        //获取TrailerIds
        List<Long> trailerIds = inserts.stream()
                .map(CarImportExcelVO::getTrailerId)
                .collect(Collectors.toList());

        //批量更新为使用中
        mainVehicleMapper.batchUpdateIsIdleByIds(mainVehicleIds);
        trailerMapper.batchUpdateIsIdleByIds(trailerIds);

        // 新增逻辑
        for (CarImportExcelVO insert : inserts) {
            CarDO carDO = new CarDO();
            carDO.setMainVehicleId(insert.getMainVehicleId());
            carDO.setTrailerId(insert.getTrailerId());
            carDO.setGodType(Integer.valueOf(insert.getGodType()));
            carDO.setFleetId(insert.getFleetId());
            carDO.setCaptainId(insert.getCaptainId());
            carDO.setDeputyId(insert.getDeputyId());
            carDO.setEscortId(insert.getEscortId());
            carDO.setMainId(insert.getMainId());
            carDO.setCompanyId(insert.getCompanyId());
            carDO.setCommodityId(insert.getCommodityIds());
            carMapper.insert(carDO);
        }
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.CAR_LIST + "#120", keyGenerator = "customKeyGenerator")
    public List<CarBasicRespVO> getAllCarList(CommonInformationQueryParam param) {
        if (ObjectUtil.isEmpty(param.getStatus()))
            param.setStatus(0);
        MPJLambdaWrapper<CarDO> queryWrapper = new MPJLambdaWrapper<CarDO>()
                .select(CarDO::getId, CarDO::getMainVehicleId)
                .selectAs(MainVehicleDO::getPlateNumber, CarDO::getMotorvehicleNumber)
                .selectAs(TrailerDO::getVehicleTrailerNo, CarDO::getVehicleTrailerNo)
                .selectAs(MainVehicleDO::getVehicleBrand, CarDO::getBrand)
                .selectAs(MainVehicleDO::getFrontWeight, CarDO::getFrontWeight)
                .selectAs(MainVehicleDO::getUserYears, CarDO::getUserYears)
                .leftJoin(MainVehicleDO.class, "t1", MainVehicleDO::getId, CarDO::getMainVehicleId)
                .leftJoin(TrailerDO.class, "t2", TrailerDO::getId, CarDO::getTrailerId)
                .eq(CarDO::getStatus, param.getStatus())
                .eq(CarDO::getDeleted, 0)
                .eq(MainVehicleDO::getDeleted, 0)
                .eq(MainVehicleDO::getVehicleStatus, 0)
                .eq(TrailerDO::getDeleted, 0)
                .eq(TrailerDO::getTrailerStatus, 0)
                .eq(ObjectUtil.isNotEmpty(param.getId()), CarDO::getId, param.getId())
                .in(ArrayUtil.isNotEmpty(param.getCompanyIds()), CarDO::getDeptId, param.getCompanyIds())
                .in(ArrayUtil.isNotEmpty(param.getFleetIds()), CarDO::getFleetId, param.getFleetIds())
                .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(MainVehicleDO::getPlateNumber, param.getSearchKey()).or().like(TrailerDO::getVehicleTrailerNo, param.getSearchKey()))
                .isNotNull(CarDO::getMainVehicleId)
                .groupBy("t.id", "t1.plate_number", "t2.vehicle_trailer_no", "t.main_vehicle_id", "t1.vehicle_brand","t1.front_weight","t1.user_years")
                .disableLogicDel();
        List<CarDO> result = carMapper.selectJoinList(CarDO.class, queryWrapper);
        List<CarBasicRespVO> list = BeanUtils.toBean(result, CarBasicRespVO.class);
        list.forEach(item -> {
            item.setPlateNumber(result.stream().filter(p -> p.getId().equals(item.getId())).findFirst().orElse(null).getMotorvehicleNumber());
        });
        return list;
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.CAR_LIST + "#300", keyGenerator = "customKeyGenerator")
    public List<CarDO> getAllCarList() {
        LambdaQueryWrapper<CarDO> queryWrapper = new LambdaQueryWrapper<CarDO>()
                .eq(CarDO::getStatus, 0)
                .isNotNull(CarDO::getMainVehicleId);
        List<CarDO> result = carMapper.selectList(queryWrapper);
        return result;
    }

    @Override
    public Page<CarRespVO> selectPageByKeyword(CarKeywordPageReqVO pageReqVO) {
        Page<CarDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        Page<CarRespVO> pageResultVO = carMapper.selectPageByKeyword(page, pageReqVO);
        List<CarRespVO> records = pageResultVO.getRecords();

        if (ObjectUtils.isNotEmpty(records)) {

            records.forEach(record -> {

                //获取车头车挂的使用年限
                if (ObjectUtils.isNotEmpty(record.getRegisterTime())){
                    Duration duration = Duration.between(record.getRegisterTime(), LocalDateTime.now());
                    Integer years = Math.toIntExact(duration.toDays() / 365); // 计算总共的使用年限，假设每年有365天
                    record.setUserYears(years);
                }

                if (ObjectUtils.isNotEmpty(record.getCertificatTime())){
                    Duration duration1 = Duration.between(record.getCertificatTime(), LocalDateTime.now());
                    Integer years1 = Math.toIntExact(duration1.toDays() / 365); // 计算总共的使用年限，假设每年有365天
                    record.setTUserYears(years1);
                }

                // 处理commodityId
                if (ObjectUtils.isNotEmpty(record.getCommodityId())) {
                    List<Long> commodityIds = Arrays.stream(record.getCommodityId().split(","))
                            .map(Long::valueOf).collect(Collectors.toList());
                    List<CommodityDO> commodityDOS = commodityMapper.selectBatchIds(commodityIds);
                    List<String> commodityNames = commodityDOS
                            .stream()
                            .filter(commodity -> !commodity.getDeleted()) // 过滤掉已删除的记录
                            .map(CommodityDO::getName)
                            .collect(Collectors.toList());
                    String collect = commodityNames.stream().map(Object::toString).collect(Collectors.joining(","));
                    record.setCommodityNames(collect);

                }
            });
        }
        return pageResultVO;
    }



    @Override
    public CarCyclePage<CarCycleRespVO> selectCarCyclePage(CarCyclePageReqVO pageReqVO) {
        CarCyclePage<CarCycleRespVO> pageResultVO = new CarCyclePage<>();
        List<CarCycleRespVO> carCycleRespVOS = carMapper.selectCarCyclePageByKeyword(pageReqVO);
        if (carCycleRespVOS == null) {
            carCycleRespVOS = new ArrayList<>();
        }

        Iterator<CarCycleRespVO> iterator = carCycleRespVOS.iterator();
        while (iterator.hasNext()) {
            CarCycleRespVO carCycleRespVO = iterator.next();
            if (carCycleRespVO == null) {
                iterator.remove();
                continue;
            }
            Integer type = carCycleRespVO.getType();
            if (type == 1) {
                carCycleRespVO.setTypeName("车头");
            }
            if (type == 2) {
                carCycleRespVO.setTypeName("挂车");
            }
            //TODO 待计算
//            carCycleRespVO.setTotalMileage(0L);
//            carCycleRespVO.setTotalTonnage(0L);
            Boolean isEnabled = carCycleRespVO.getIsEnabled();
            Byte status = carCycleRespVO.getStatus();
            Boolean isIdle = carCycleRespVO.getIsIdle();

            if (isEnabled != null && status != null && isIdle != null) {
                if (isEnabled && status == 0 && !isIdle) {
                    carCycleRespVO.setStatusType(1);
                }
                if (!isEnabled && status == 0 && isIdle) {
                    carCycleRespVO.setStatusType(2);
                }
                if (!isEnabled && status == 1 && !isIdle) {
                    carCycleRespVO.setStatusType(3);
                }
                if (!isEnabled && status == 2 && !isIdle) {
                    carCycleRespVO.setStatusType(4);
                }
            }
        }
        int inUse = carMapper.countInUse(pageReqVO.getVehicleType(), pageReqVO.getDeptId()); //使用中
        int free = carMapper.countIdle(pageReqVO.getVehicleType(), pageReqVO.getDeptId());   //空闲
        int deactivation = carMapper.countDeactivated(pageReqVO.getVehicleType(), pageReqVO.getDeptId());  //注销
        int scrap = carMapper.countScrapped(pageReqVO.getVehicleType(), pageReqVO.getDeptId());  //报废
        pageResultVO.setInUse(inUse);
        pageResultVO.setFree(free);
        pageResultVO.setDeactivation(deactivation);
        pageResultVO.setScrap(scrap);

        int pageNo = pageReqVO.getPageNo();
        int pageSize = pageReqVO.getPageSize();
        int totalCount = carCycleRespVOS.size();

        pageResultVO.setTotal(totalCount);
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalCount);
        // 分页查询结果
        List<CarCycleRespVO> pageList = carCycleRespVOS.subList(startIndex, endIndex);
        pageResultVO.setRecords(pageList);
        return pageResultVO;

    }

    @Override
    public List<CarCycleRespVO> exportCarCycle(CarCyclePageReqVO pageReqVO) {
        List<CarCycleRespVO> carCycleRespVOS = carMapper.selectCarCyclePageByKeyword(pageReqVO);
        if (CollectionUtil.isNotEmpty(carCycleRespVOS)) {
            for (CarCycleRespVO carCycleRespVO : carCycleRespVOS) {
                carCycleRespVO.setTotalMileage(BigDecimal.valueOf(0));
                carCycleRespVO.setTotalTonnage(BigDecimal.valueOf(0));
                Boolean isEnabled = carCycleRespVO.getIsEnabled();
                Byte status = carCycleRespVO.getStatus();
                Boolean isIdle = carCycleRespVO.getIsIdle();
                //状态名称判断
                if ((isEnabled || status == 0) && !isIdle) {
                    carCycleRespVO.setStatusType(1);
                }

                if (!isEnabled && status == 0 && isIdle) {
                    carCycleRespVO.setStatusType(2);
                }

                if (!isEnabled && status == 1 && !isIdle) {
                    carCycleRespVO.setStatusType(3);
                }
                if (!isEnabled && status == 2 && !isIdle) {
                    carCycleRespVO.setStatusType(4);
                }
            }
        }
        return carCycleRespVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createProcessInstance(CarProcessRespVO processRespVO) {
        //dingTalkProperties.setAppKey("dingq6dnaawgygw15ceg");
        //dingTalkProperties.setAppSecret("inhtBxx91UnzJIstCEIi_QCZjV9P-A0ai1U1JZwFdn_byhpgO3hRS_Bkt18NXMxl");
        //后端校验
        //validateVehicleExists(processRespVO);

        //1.审批信息对象
        BaseProcessInstanceRequest carProcessInstanceRequest = new CarProcessInstanceRequest();
        //1.1、申请人就是当前登录用户
        //String mobile1 = "15773989613";  //周
        String mobile1 = "17113296829658454";  //梁冰
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (ObjUtil.isNotEmpty(loginUser)) {
            AdminUserDO adminUserDO = userMapper.selectById(loginUser.getId());
            mobile1 = adminUserDO.getMobile();
        }
        //carProcessInstanceRequest.setOriginatorUserMobile("15773989613");
        carProcessInstanceRequest.setOriginatorUserMobile(mobile1);
        //1.2.流程名称
        String processCodeName = "TMS车辆报废-注销流程";
        //String processCodeName = "报废申请";  //测试
        carProcessInstanceRequest.setProcessCodeName(processCodeName);
        //1.3.表单信息
        List<StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues> listForms = null;
        Long id = processRespVO.getId();
        Integer carVehicleType = processRespVO.getCarVehicleType();
        Integer processType = processRespVO.getProcessType();
        Integer vehicleCode1 = VehicleEnum.VEHICLE_MAIN.getVehicleCode();
        Integer vehicleCode2 = VehicleEnum.VEHICLE_TRAILER.getVehicleCode();
        if (carVehicleType.equals(vehicleCode1)) {
            LambdaQueryWrapper<MainVehicleDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MainVehicleDO::getId, id)
                    .eq(MainVehicleDO::getStatus, 0);
            MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(id);
            if (ObjUtil.isNotEmpty(mainVehicleDO)) {
                //注销枚举
                StatusEnum canceled = StatusEnum.CANCELED;
                //报废枚举
                StatusEnum scrap = StatusEnum.SCRAP;
                if (canceled.getCode().equals(processType)) {
                    listForms = listFormValues(canceled.getDescription(), mainVehicleDO);
                } else if (scrap.getCode().equals(processType)) {
                    listForms = listFormValues(scrap.getDescription(), mainVehicleDO);
                } else {
                    throw exception(CAR_ERROR_02);
                }
            } else {
                throw exception(MAIN_VEHICLE_NOT_EXISTS, id);
            }

        } else if (carVehicleType.equals(vehicleCode2)) {
            LambdaQueryWrapper<TrailerDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TrailerDO::getId, id)
                    .eq(TrailerDO::getStatus, 0);
            TrailerDO trailerDO = trailerMapper.selectById(id);
            if (ObjUtil.isNotEmpty(trailerDO)) {
                //注销枚举
                StatusEnum canceled = StatusEnum.CANCELED;
                //报废枚举
                StatusEnum scrap = StatusEnum.SCRAP;
                if (canceled.getCode().equals(processType)) {
                    listForms = listFormValues(canceled.getDescription(), trailerDO);
                } else if (scrap.getCode().equals(processType)) {
                    listForms = listFormValues(scrap.getDescription(), trailerDO);
                } else {
                    throw exception(CAR_ERROR_03);
                }
            } else {
                throw exception(MAIN_VEHICLE_NOT_EXISTS, id);
            }
        } else {
            throw exception(CAR_ERROR_04);
        }
        carProcessInstanceRequest.setFormComponentValues(listForms);
        //1.4.审批人信息
//        String mobile2 = "15761626412";  //流程默认的审批人【昆贤哥】可忽略
//        List<String> userMobiles = Arrays.asList(mobile2);
//        List<String> userMobiles = Arrays.asList("13471898471");  //罗权
        List<String> userMobiles = Arrays.asList("17362188046");  //焕焕
        StartProcessInstanceRequest.StartProcessInstanceRequestApprovers approvers = listApprovers(dingTalkProperties, userMobiles);
        carProcessInstanceRequest.setApprovers(approvers);
        //1.5.抄送人,可忽略
        //String mobile3 = "15761626412";
        //List<String> userMobiles2 = Arrays.asList(mobile3);
        //List<String> ccList = DingTalkUtils01.getUserIdsByUserMobiles(dingTalkProperties, userMobiles2);
        //carProcessInstanceRequest.setCcList(ccList);

        //2、发起(创建)流程实例
        String instanceId = DingTalkUtils.createProcessInstance(dingTalkProperties, carProcessInstanceRequest);
        if (StringUtils.isNotEmpty(instanceId)) {
            Integer approvalStatus = ApprovalStatusEnum.PENDING.getCode();
            if (carVehicleType.equals(vehicleCode1)) {
                LambdaUpdateWrapper<MainVehicleDO> queryWrapper = new LambdaUpdateWrapper<>();
                queryWrapper.eq(MainVehicleDO::getId, id)
                        .set(MainVehicleDO::getProcessId, instanceId)
                        .set(MainVehicleDO::getApprovalStatus, approvalStatus);
                mainVehicleMapper.update(null, queryWrapper);
            } else if (carVehicleType.equals(vehicleCode2)) {
                LambdaUpdateWrapper<TrailerDO> queryWrapper = new LambdaUpdateWrapper<>();
                queryWrapper.eq(TrailerDO::getId, id)
                        .set(TrailerDO::getProcessId, instanceId)
                        .set(TrailerDO::getApprovalStatus, approvalStatus);
                trailerMapper.update(null, queryWrapper);
            }
        }

        //3、回调监听
        return instanceId;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void carFleetFinishDing() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LambdaQueryWrapper<CarPersonReplaceDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CarPersonReplaceDO::getStatus, CarPersonReplaceEnum.WCL.getCode())
                .isNotNull(CarPersonReplaceDO::getProcessId);
        List<CarPersonReplaceDO> carPersonReplaceDOS = carPersonReplaceMapper.selectList(queryWrapper);

        if (CollectionUtils.isNotEmpty(carPersonReplaceDOS)) {
            ArrayList<CarPersonReplaceDO> list = new ArrayList<>();
            ArrayList<Long> carPersonReplaceCallbackList = new ArrayList<>();

            // 获取钉钉审批模板Code
            DingProcessCodeDTO dingProcessCode = dingProcessCodeApi.getDingProcessCode(DingProcessCodeEnum.CLCDBG.getIndex());
            if (dingProcessCode == null) {
                log.error(LocalDateTime.now().format(dateTimeFormatter) + ":钉钉审批回调定时调度失败，未找到运输合同审批模板编码，请先维护");
            } else {
                // 钉钉回调信息
                List<DingCallbackDTO> callbackList = dingCallbackApi.getPendingDingCallback(dingProcessCode.getProcessCode());
                carPersonReplaceDOS.forEach(carPersonReplaceDO -> {
//                    carPersonReplaceDO.setApprovalTime(LocalDateTime.now());
                    DingCallbackDTO info = callbackList.stream()
                            .filter(t -> t.getProcessInstanceId().equals(carPersonReplaceDO.getProcessId()))
                            .findFirst()
                            .orElse(null);
                    if (info != null) {
                        if ("terminate".equals(info.getType())) {
                            carPersonReplaceDO.setStatus(CarPersonReplaceEnum.CANCELED.getCode());
                        } else {
                            switch (info.getResult()) {
                                case "agree":
                                    carPersonReplaceDO.setStatus(CarPersonReplaceEnum.APPROVED.getCode());
                                    Long carId = carPersonReplaceDO.getCarId();

                                    LambdaUpdateWrapper<CarDO> updateWrapper = new LambdaUpdateWrapper<>();
                                    updateWrapper.eq(CarDO::getMainVehicleId, carId);
                                    CarDO carDO = carMapper.selectOne(updateWrapper);

                                    LambdaUpdateWrapper<TrailerDO> updateWrapper1 = new LambdaUpdateWrapper<>();
                                    updateWrapper1.eq(TrailerDO::getId, carDO.getTrailerId());

                                    if (carPersonReplaceDO.getBindingType() != null && carPersonReplaceDO.getBindingType() == 0) {
                                        // 解绑操作
                                        updateWrapper.set(CarDO::getTrailerId, null);
                                        carMapper.update(null, updateWrapper);

                                        updateWrapper1.set(TrailerDO::getParkingPosition, carPersonReplaceDO.getParkingPosition());
                                        trailerMapper.update(null, updateWrapper1);

                                    } else if (carPersonReplaceDO.getBindingType() == null || carPersonReplaceDO.getBindingType() == 1) {
                                        //1.首先更新car表
                                        //更新前看一下car表有没有关联的车挂，有的话需要把这条车挂的状态改变为空闲
                                        if (carDO.getTrailerId() != null) {
                                            updateWrapper1.set(TrailerDO::getStandbyTrailerStatus, 0);
                                        }
                                        updateWrapper1.set(TrailerDO::getParkingPosition, carPersonReplaceDO.getParkingPosition());
                                        trailerMapper.update(null, updateWrapper1);

                                        updateWrapper.set(CarDO::getTrailerId, carPersonReplaceDO.getTrailerId());
                                        carMapper.update(null, updateWrapper);
                                    }

                                    break;
                                case "refuse":
                                    carPersonReplaceDO.setStatus(CarPersonReplaceEnum.REJECTED.getCode());
                                    break;
                                default:
                                    break;
                            }
                        }
                        carPersonReplaceDO.setApprovalUrl(info.getUrl());
                        list.add(carPersonReplaceDO);
                        carPersonReplaceCallbackList.add(info.getId());
                    }
                });
            }

            // 更新数据
            if (CollectionUtils.isNotEmpty(list)) {
                list.forEach(carPersonReplaceDO -> {
                    LambdaUpdateWrapper<CarPersonReplaceDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(CarPersonReplaceDO::getId, carPersonReplaceDO.getId())
                            .set(CarPersonReplaceDO::getStatus, carPersonReplaceDO.getStatus())
                            .set(CarPersonReplaceDO::getApprovalTime, LocalDateTime.now());
//                            .set(CarPersonReplaceDO::getApprovalUrl, carPersonReplaceDO.getApprovalUrl());
//                            .set(CarPersonReplaceDO::getApplyUserTime, carPersonReplaceDO.getApplyUserTime());
                    carPersonReplaceMapper.update(null, updateWrapper);

                    // 获取更新后的数据
                    CarPersonReplaceDO updatedCarPersonReplaceDO = carPersonReplaceMapper.selectById(carPersonReplaceDO.getId());
                    Long trailerId = updatedCarPersonReplaceDO.getTrailerId();
                    LambdaUpdateWrapper<TrailerDO> updateWrapper1 = new LambdaUpdateWrapper<>();
                    updateWrapper1.eq(TrailerDO::getId, trailerId);
                    if (updatedCarPersonReplaceDO.getStatus() == 1) {
                        if (updatedCarPersonReplaceDO.getBindingType() == 1) {
                            // 审批通过、绑定
                            updateWrapper1.set(TrailerDO::getStandbyTrailerStatus, 1);


                            if (updatedCarPersonReplaceDO.getOldTrailerId() != null) {
                                updateWrapper1.set(TrailerDO::getReplacedTrailer, updatedCarPersonReplaceDO.getOldTrailerId());
                            }
                        } else if (updatedCarPersonReplaceDO.getBindingType() == 0) {
                            // 审批通过、解绑
                            updateWrapper1.set(TrailerDO::getStandbyTrailerStatus, 0);
                        }
                    } else if (updatedCarPersonReplaceDO.getStatus() == 2 || updatedCarPersonReplaceDO.getStatus() == 3) {
                        if (updatedCarPersonReplaceDO.getBindingType() == 1) {
                            // 审批拒绝或撤销、绑定
                            updateWrapper1.set(TrailerDO::getStandbyTrailerStatus, 0);
                        } else if (updatedCarPersonReplaceDO.getBindingType() == 0) {
                            // 审批拒绝或撤销、解绑
                            updateWrapper1.set(TrailerDO::getStandbyTrailerStatus, 1);
                        }
                    }
                    trailerMapper.update(null, updateWrapper1);
                });
            }

            // 更新回调表处理状态
            if (CollectionUtils.isNotEmpty(carPersonReplaceCallbackList)) {
                dingCallbackApi.updateCallbackStatusById(carPersonReplaceCallbackList, 2);
            }
        }
    }




    @Override
    public ClientSettingsPage<CarChangeRespDTO> selectCarChangeByVehicleId(CarPageReqVO pageReqVO) {
        String collationCode = pageReqVO.getCollationCode();
        //if ("0".equals(collationCode)) {
        //    pageReqVO.setCollationValue("asc");
        //} else if ("1".equals(collationCode)) {
        //    pageReqVO.setCollationValue("desc");
        //}
        ClientSettingsPage<CarChangeRespDTO> page = new ClientSettingsPage<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        ClientSettingsPage<CarChangeRespDTO> pageResult = carMapper.selectCarChangeByVehicleId(page, pageReqVO);
        return pageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void carScrapOrCancelProcessApproval(CarScrapOrCancelProcessReqDTO reqDTO) {
        //表单数据
        CarScrapOrCancelProcessRespDTO respDTO = new CarScrapOrCancelProcessRespDTO();
        respDTO.setApplyType(reqDTO.getApplyType() == 1 ? "注销" : "报废");
        respDTO.setVehicleType(reqDTO.getVehicleType() == 1 ? "车头" : "车挂");
        respDTO.setRemark(reqDTO.getRemark());

        if (reqDTO.getVehicleType() == 1) {
            MPJLambdaWrapper<MainVehicleDO> mpjLambdaWrapper = new MPJLambdaWrapper<>();
            mpjLambdaWrapper
                    .select(MainVehicleDO::getId)
                    .selectAs(MainVehicleDO::getPlateNumber, "trailerVehicleNumber")
                    .leftJoin(DeptDO.class, DeptDO::getId, MainVehicleDO::getDeptId)
                    .selectAs(DeptDO::getName, "deptName")
                    .eq(MainVehicleDO::getDeleted, 0)
                    .eq(MainVehicleDO::getId, reqDTO.getId())
                    .and(wrapper -> wrapper.eq(DeptDO::getDeleted, 0).isNotNull(DeptDO::getDeleted));
            MainVehicleDO mainVehicleDO = mainVehicleMapper.selectOne(mpjLambdaWrapper);
            if (ObjectUtil.isEmpty(mainVehicleDO)) {
                throw exception(MAIN_VEHICLE_NO_EXISTS);
            }
            BeanUtils.copyPropertiesIgnoreNull(respDTO, mainVehicleDO);
            respDTO.setMainVehicleId(mainVehicleDO.getId());
            //更新车头的审批状态
            LambdaUpdateWrapper<MainVehicleDO> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.eq(MainVehicleDO::getId, reqDTO.getId())
                    .set(MainVehicleDO::getApprovalStatus,0);

            Map<String, Object> mapData = new HashMap<>();
            mapData.put("所属公司",respDTO.getDeptName());
            mapData.put("申请类型",respDTO.getApplyType());
            mapData.put("车辆类型",respDTO.getVehicleType());
            mapData.put("报废/注销车号",respDTO.getTrailerVehicleNumber());
            mapData.put("备注",respDTO.getRemark());

            System.out.println("mapData = " + mapData);
            //发起审批
            String processId = processInstanceApi.submitForm2(mapData, TemplateModuleEnum.CAR_SCRAP_OR_CANCEL);
            updateWrapper.set(MainVehicleDO::getProcessId, processId);
            mainVehicleMapper.update(null, updateWrapper);

            // 新增到编码表用于后续回调
            CarProcessCodeDO carProcessCodeDO = new CarProcessCodeDO();
            carProcessCodeDO.setProcessCode(processId);
            carProcessCodeDO.setEntityId(reqDTO.getId());
            carProcessCodeDO.setApplyType(reqDTO.getApplyType());
            carProcessCodeDO.setVehicleType(reqDTO.getVehicleType());
            carProcessCodeDO.setEntityId(reqDTO.getId());
            carProcessCodeMapper.insert(carProcessCodeDO);


        } else if (reqDTO.getVehicleType() == 2) {
            MPJLambdaWrapper<TrailerDO> mpjLambdaWrapper = new MPJLambdaWrapper<>();
            mpjLambdaWrapper.select(TrailerDO::getId)
                    .selectAs(TrailerDO::getVehicleTrailerNo,"trailerVehicleNumber")
                    .leftJoin(DeptDO.class, DeptDO::getId, TrailerDO::getDeptId)
                    .selectAs(DeptDO::getName, "deptName")
                    .eq(TrailerDO::getDeleted, 0)
                    .eq(TrailerDO::getId, reqDTO.getId())
                    .and(wrapper -> wrapper.eq(DeptDO::getDeleted, 0).isNotNull(DeptDO::getDeleted));
            TrailerDO trailerDO = trailerMapper.selectOne(mpjLambdaWrapper);
            if (ObjectUtil.isEmpty(trailerDO)) {
                throw exception(TRAILER_NO_QUREY);
            }
            BeanUtils.copyPropertiesIgnoreNull(respDTO, trailerDO);
            respDTO.setTrailerId(trailerDO.getId());

            //更新车挂的审批状态
            LambdaUpdateWrapper<TrailerDO> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.eq(TrailerDO::getId, reqDTO.getId())
                    .set(TrailerDO::getApprovalStatus,0);

            Map<String, Object> mapData = new HashMap<>();
            mapData.put("所属公司",respDTO.getDeptName());
            mapData.put("申请类型",respDTO.getApplyType());
            mapData.put("车辆类型",respDTO.getVehicleType());
            mapData.put("报废/注销车号",respDTO.getTrailerVehicleNumber());
            mapData.put("备注",respDTO.getRemark());

            //发起审批
            String processId = processInstanceApi.submitForm2(mapData, TemplateModuleEnum.CAR_SCRAP_OR_CANCEL);
            updateWrapper.set(TrailerDO::getProcessId, processId);
            trailerMapper.update(null, updateWrapper);

            // 新增到编码表用于后续回调
            CarProcessCodeDO carProcessCodeDO = new CarProcessCodeDO();
            carProcessCodeDO.setProcessCode(processId);
            carProcessCodeDO.setEntityId(reqDTO.getId());
            carProcessCodeDO.setApplyType(reqDTO.getApplyType());
            carProcessCodeDO.setVehicleType(reqDTO.getVehicleType());
            carProcessCodeDO.setMainVehicleId(reqDTO.getMainVehicleId());
            carProcessCodeDO.setTrailerId(reqDTO.getTrailerId());
            carProcessCodeMapper.insert(carProcessCodeDO);
        }

    }


    @Override
    public void updateCarScrapOrCancelProcess(String processId, Integer processStatus) {
        LambdaQueryWrapper<CarProcessCodeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarProcessCodeDO::getDeleted, 0)
                .eq(CarProcessCodeDO::getProcessCode, processId);
        CarProcessCodeDO carProcessCodeDO = carProcessCodeMapper.selectOne(queryWrapper);
        if (!ObjectUtil.isEmpty(carProcessCodeDO)){
            switch (processStatus) {
                case 0:
                    // 审批中，不做处理
                    break;  // 使用 break 而不是 continue，以避免编译错误
                case 1:
                    // 同意审批
                    updateStatus(carProcessCodeDO,1);
                    break;
                case 2:
                    // 审批拒绝
                    updateStatus(carProcessCodeDO,2);
                    break;
                case 3:
                    // 审批撤销
                    updateStatus(carProcessCodeDO,3);
                    break;
                default:
                    break;
            }
        }

    }

    private void updateStatus(CarProcessCodeDO codeDO, int processStatus) {

        if (codeDO.getVehicleType() == 1) {
            LambdaUpdateWrapper<MainVehicleDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(MainVehicleDO::getId, codeDO.getEntityId())
                    .set(MainVehicleDO::getApprovalStatus, processStatus)
                    .set(MainVehicleDO::getApprovalTime, LocalDateTime.now());

            if (processStatus == 2 || processStatus == 3) {
                updateWrapper.set(MainVehicleDO::getStatus, null);
//                        .set(MainVehicleDO::getIsIdle, false);
            } else if (processStatus == 1) { // 审批同意
                if (codeDO.getApplyType() == 2) {
                    updateWrapper.set(MainVehicleDO::getScrapDate, LocalDateTime.now())
                            .set(MainVehicleDO::getStatus, 1);
                } else if (codeDO.getApplyType() == 1) {
                    updateWrapper.set(MainVehicleDO::getDeactivationDate, LocalDateTime.now())
                            .set(MainVehicleDO::getStatus, 0);
                }

                //不管是注销还是报废，审批通过都要查询车辆信息是否有这个关联的车头，如果有的话得把对应的车头id设置为null
                LambdaUpdateWrapper<CarDO> updateWrapper1 = new LambdaUpdateWrapper<>();
                updateWrapper1.eq(CarDO::getMainVehicleId, codeDO.getMainVehicleId())
                                .eq(CarDO::getDeleted, 0)
                                        .isNull(CarDO::getProcessStatus);
                CarDO carDO = carMapper.selectOne(updateWrapper1);
                if (!ObjectUtil.isEmpty(carDO)){
                    updateWrapper1.set(CarDO::getMainVehicleId, null);
                    carMapper.update(null, updateWrapper1);
                }
//                updateWrapper.set(MainVehicleDO::getIsIdle, false);
            }
            mainVehicleMapper.update(null, updateWrapper);
        } else if (codeDO.getVehicleType() == 2) {
            LambdaUpdateWrapper<TrailerDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(TrailerDO::getId, codeDO.getEntityId())
                    .set(TrailerDO::getApprovalStatus, processStatus)
                    .set(TrailerDO::getApprovalTime, LocalDateTime.now());

            if (processStatus == 2 || processStatus == 3) {
                updateWrapper.set(TrailerDO::getStatus, null);
//                        .set(TrailerDO::getIsIdle, false);
            } else if (processStatus == 1) { // 审批同意
                if (codeDO.getApplyType() == 2) {
                    updateWrapper.set(TrailerDO::getScrapDate, LocalDateTime.now())
                            .set(TrailerDO::getStatus, 1);
                } else if (codeDO.getApplyType() == 1) {
                    updateWrapper.set(TrailerDO::getDeactivationDate, LocalDateTime.now())
                            .set(TrailerDO::getStatus, 0);
                }
//                updateWrapper.set(TrailerDO::getIsIdle, false);

                //不管是注销还是报废，审批通过都要查询车辆信息是否有这个关联的车头，如果有的话得把对应的车头id设置为null
                LambdaUpdateWrapper<CarDO> updateWrapper1 = new LambdaUpdateWrapper<>();
                updateWrapper1.eq(CarDO::getMainVehicleId, codeDO.getTrailerId())
                        .eq(CarDO::getDeleted, 0)
                        .isNull(CarDO::getProcessStatus);
                CarDO carDO = carMapper.selectOne(updateWrapper1);
                if (!ObjectUtil.isEmpty(carDO)){
                    updateWrapper1.set(CarDO::getTrailerId, null);
                    carMapper.update(null, updateWrapper1);
                }
            }
            trailerMapper.update(null, updateWrapper);
        }

    }

    @Override
    public void carPersonProcess(String processId, Integer processStatus) {
        LambdaQueryWrapper<CarPersonReplaceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarPersonReplaceDO::getProcessId, processId);
        CarPersonReplaceDO carPersonReplaceDO = carPersonReplaceMapper.selectOne(queryWrapper);
        if (!ObjectUtil.isEmpty(carPersonReplaceDO)) {
            //审批后的处理回调数据
            carPersonStatusProcess(carPersonReplaceDO.getId(), processStatus);
        }

    }

    private void carPersonStatusProcess(Long id,Integer processStatus){
        CarPersonReplaceDO carPersonReplaceDO = carPersonReplaceMapper.selectById(id);
        LambdaUpdateWrapper<CarDO> carWrapper = new LambdaUpdateWrapper<>();
        carWrapper.eq(CarDO::getMainVehicleId,carPersonReplaceDO.getCarId())
              .eq(CarDO::getDeleted,0);
        CarDO carDO = carMapper.selectOne(carWrapper);
        switch (processStatus){
            case 0://审批中，不做处理
                break;

            case 1://同意审批
                carPersonReplaceDO.setStatus(1);
                carPersonReplaceMapper.updateById(carPersonReplaceDO);

                List<Long> mainIdList = new ArrayList<>();
                List<Long> trailerIdList = new ArrayList<>();
                List<Long> escortIdList = new ArrayList<>();
                List<Long> deputyIdList = new ArrayList<>();
                List<Long> fleetIdList = new ArrayList<>();
                if (carPersonReplaceDO.getOldMainId() != null){
                    mainIdList.add(carPersonReplaceDO.getMainId());
                }
                if (carPersonReplaceDO.getOldDeputyId() != null){
                    deputyIdList.add(carPersonReplaceDO.getDeputyId());
                }
                if (carPersonReplaceDO.getOldEscortId() != null){
                    escortIdList.add(carPersonReplaceDO.getEscortId());
                }
                if (carPersonReplaceDO.getOldFleetId() != null){
                    fleetIdList.add(carPersonReplaceDO.getFleetId());
                }
                if (carPersonReplaceDO.getOldTrailerId() != null){
                    trailerIdList.add(carPersonReplaceDO.getTrailerId());
                }
                //更新占用的主驾/副驾/押运员/车挂/车队的数据为null
                updateOccupiedPositions(mainIdList, trailerIdList, escortIdList, deputyIdList, fleetIdList);

                //更新car的审批状态
                carWrapper.set(CarDO::getProcessStatus,1);
                if (carPersonReplaceDO.getReplaceType() == 1){//车辆人员变更
                    carWrapper.eq(CarDO::getId, carDO.getId())
                            .set(CarDO::getMainId, carPersonReplaceDO.getMainId())
                            .set(CarDO::getDeputyId, carPersonReplaceDO.getDeputyId())
                            .set(CarDO::getEscortId, carPersonReplaceDO.getEscortId());
                }else if (carPersonReplaceDO.getReplaceType() == 2){//车挂或车队变更
                    carWrapper.eq(CarDO::getId, carDO.getId())
                            .set(CarDO::getFleetId,carPersonReplaceDO.getFleetId())
                            .set(CarDO::getTrailerId,carPersonReplaceDO.getTrailerId());

                    //并更新车挂的闲置状态
                    LambdaUpdateWrapper<TrailerDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(TrailerDO::getId,carDO.getTrailerId())
                            .set(TrailerDO::getIsIdle,0);
                    trailerMapper.update(null,updateWrapper);

                    //还要更新被更换下来的车挂的闲置状态
                    if (carPersonReplaceDO.getOldTrailerId() != null){
                        LambdaUpdateWrapper<TrailerDO> updateWrapper1 = new LambdaUpdateWrapper<>();
                        updateWrapper1.eq(TrailerDO::getId,carPersonReplaceDO.getTrailerId())
                                .set(TrailerDO::getIsIdle,1);
                        trailerMapper.update(null,updateWrapper1);
                    }

                }else if (carPersonReplaceDO.getReplaceType() == 3){//全部
                    carWrapper.eq(CarDO::getId, carDO.getId())
                            .set(CarDO::getMainId, carPersonReplaceDO.getMainId())
                            .set(CarDO::getDeputyId, carPersonReplaceDO.getDeputyId())
                            .set(CarDO::getEscortId, carPersonReplaceDO.getEscortId())
                            .set(CarDO::getFleetId,carPersonReplaceDO.getFleetId())
                            .set(CarDO::getTrailerId,carPersonReplaceDO.getTrailerId());
                }
                        carMapper.update(null,carWrapper);

                break;

            case 2://审批拒绝
                  //更新car的审批状态
                  carWrapper.set(CarDO::getProcessStatus,2);
                  carMapper.update(null,carWrapper);
                carPersonReplaceDO.setStatus(2);
                carPersonReplaceMapper.updateById(carPersonReplaceDO);

               break;
            case 3://审批撤销
                 //更新car的审批状态
                 carWrapper.set(CarDO::getProcessStatus,3);
                 carMapper.update(null,carWrapper);
                carPersonReplaceDO.setStatus(3);
                carPersonReplaceMapper.updateById(carPersonReplaceDO);

               break;

            default:
                break;
        }


    }



    @Override
    public void carTrailerFleetProcess(String processId, Integer processStatus) {
        LambdaQueryWrapper<CarPersonReplaceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarPersonReplaceDO::getProcessId, processId);
        CarPersonReplaceDO carPersonReplaceDO = carPersonReplaceMapper.selectOne(queryWrapper);
        if (!ObjectUtil.isEmpty(carPersonReplaceDO)) {
            //审批后的处理回调数据
            carPersonStatusProcess(carPersonReplaceDO.getId(), processStatus);
        }
    }


    /**
     * 后端校验,看情况前端校验即可
     * 校验发起流程车辆的是否合法，三种车辆状态的转变：
     * 1、【正常】--> 【注销】--> 【报废】
     * 2、【正常】--> 【报废】
     * 3、【注销】--> 【报废】
     *
     * @param processRespVO
     */
    private void validateVehicleExists(CarProcessRespVO processRespVO) {
        Long vechicleId = processRespVO.getId();
        Integer carVehicleType = processRespVO.getCarVehicleType();
        Integer processType = processRespVO.getProcessType();
        Integer vehicleCode1 = VehicleEnum.VEHICLE_MAIN.getVehicleCode();
        Integer vehicleCode2 = VehicleEnum.VEHICLE_TRAILER.getVehicleCode();
        if (carVehicleType.equals(vehicleCode1)) {
            LambdaQueryWrapper<MainVehicleDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(vechicleId != null, MainVehicleDO::getId, vechicleId)
                    .in(MainVehicleDO::getStatus, 0);
            MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(vechicleId);
            if (ObjUtil.isNotEmpty(mainVehicleDO)) {
                Byte approvalStatus = mainVehicleDO.getApprovalStatus();
                //ApprovalStatusEnum.
                //if(){
                //
                //}

            } else {
                throw exception(MAIN_VEHICLE_NOT_EXISTS, vechicleId);
            }

        } else if (carVehicleType.equals(vehicleCode2)) {

        } else {

        }

    }

    /**
     * 车头表单信息
     *
     * @return
     */
    private List<StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues> listFormValues(String processType, MainVehicleDO mainVehicleDO) {
        List<StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues> list = new ArrayList<>();
        if (ObjUtil.isNotEmpty(mainVehicleDO)) {
            Long deptId = mainVehicleDO.getDeptId();
            DeptDO deptDO = deptMapper.selectById(deptId);
            //单选框组件【所属公司】
            StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues1 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                    .setName("所属公司")
                    .setValue(deptDO.getName());
            //单选框组件【申请类型】
            StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues2 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                    .setName("申请类型")
                    .setValue(processType);
            //单选框组件【车辆类型】
            StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues3 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                    .setName("车辆类型")
                    .setValue(VehicleEnum.VEHICLE_MAIN.getVehicleCodeName());
            //单行输入框组件【报废/注销车号】
            StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues4 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                    .setName("报废/注销车号")
                    .setValue(mainVehicleDO.getPlateNumber());
            //单行输入框组件【备注】
            StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues5 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                    .setName("备注")
                    .setValue("备注");
            //部门组件
            StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues6 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                    .setName("所在部门")
                    .setValue("企业信息部1");
            list.add(formComponentValues1);
            list.add(formComponentValues2);
            list.add(formComponentValues3);
            list.add(formComponentValues4);
            list.add(formComponentValues5);
            list.add(formComponentValues6);
        }
        return list;
    }

    /**
     * 车挂表单信息
     *
     * @return
     */
    private List<StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues> listFormValues(String processType, TrailerDO trailerDO) {
        List<StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues> list = new ArrayList<>();
        if (ObjUtil.isNotEmpty(trailerDO)) {
            Long deptId = trailerDO.getDeptId();
            if (deptId != null) {
                DeptDO deptDO = deptMapper.selectById(deptId);
                if (deptDO != null) {
                    // 单选框组件【所属公司】
                    StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues1 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                            .setName("所属公司")
                            .setValue(deptDO.getName());
                    list.add(formComponentValues1);

                    // 单选框组件【申请类型】
                    StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues2 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                            .setName("申请类型")
                            .setValue(processType);
                    list.add(formComponentValues2);

                    // 单选框组件【车辆类型】
                    StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues3 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                            .setName("车辆类型")
                            .setValue(VehicleEnum.VEHICLE_TRAILER.getVehicleCodeName());
                    list.add(formComponentValues3);

                    // 单行输入框组件【报废/注销车号】
                    StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues4 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                            .setName("报废/注销车号")
                            .setValue(trailerDO.getVehicleTrailerNo());
                    list.add(formComponentValues4);

                    // 单行输入框组件【备注】
                    StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues5 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                            .setName("备注")
                            .setValue("备注");
                    list.add(formComponentValues5);

                    // 部门组件
                    StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues6 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                            .setName("所在部门")
                            .setValue("企业信息部1");
                    list.add(formComponentValues6);
                }
            }
        }
        return list;
    }

    /**
     * 审批人员信息
     *
     * @param userMobiles 电话列表
     * @return 审批人员的userId列表
     */
    private StartProcessInstanceRequest.StartProcessInstanceRequestApprovers listApprovers(DingTalkProperties dingTalkProperties, List<String> userMobiles) {
        List<String> userIds = DingTalkUtils.getUserIdsByUserMobiles(dingTalkProperties, userMobiles);

        //1、会签审批:所有人都审核同意才会进入下一个节点
        StartProcessInstanceRequest.StartProcessInstanceRequestApprovers approvers0 = new StartProcessInstanceRequest.StartProcessInstanceRequestApprovers();
        approvers0.setActionType("AND");
        approvers0.setUserIds(userIds);

        //2、或签审批:只需要一个人审核同意可进行下一个节点，反之一个拒绝则结束
        StartProcessInstanceRequest.StartProcessInstanceRequestApprovers approvers1 = new StartProcessInstanceRequest.StartProcessInstanceRequestApprovers();
        approvers1.setActionType("OR")
                .setUserIds(userIds);

        //3、单个设置
        List<StartProcessInstanceRequest.StartProcessInstanceRequestApprovers> approvers2 = new ArrayList<>();
        StartProcessInstanceRequest.StartProcessInstanceRequestApprovers approver = new StartProcessInstanceRequest.StartProcessInstanceRequestApprovers();
        // 相关设置
        approver.setActionType("NONE");
        approver.setUserIds(userIds); // 审批人
        approvers2.add(approver);  // 添加审批人

        return approver;
    }

}

