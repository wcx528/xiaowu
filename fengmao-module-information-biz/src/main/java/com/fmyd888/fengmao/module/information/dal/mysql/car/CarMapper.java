package com.fmyd888.fengmao.module.information.dal.mysql.car;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.car.dto.CarChangeRespDTO;
import com.fmyd888.fengmao.module.information.controller.admin.car.dto.CarInfoDTO;
import com.fmyd888.fengmao.module.information.controller.admin.car.dto.DingCarDetailsRespDTO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportCarRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.fleet.FleetDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆档案 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface CarMapper extends BaseMapperX<CarDO> {

    default PageResult<CarDO> selectPage(CarPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CarDO>()
                .eqIfPresent(CarDO::getDeputyId, reqVO.getDeputyId())
                .eqIfPresent(CarDO::getEscortId, reqVO.getEscortId())
                .eqIfPresent(CarDO::getDeputyPhone, reqVO.getDeputyPhone())
                .eqIfPresent(CarDO::getEscortPhone, reqVO.getEscortPhone())
                .betweenIfPresent(CarDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CarDO::getAbleTonnage, reqVO.getAbleTonnage())
                .eqIfPresent(CarDO::getActualTonnage, reqVO.getActualTonnage())
                .eqIfPresent(CarDO::getGodType, reqVO.getGodType())
                .eqIfPresent(CarDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CarDO::getMainVehicleId, reqVO.getMainVehicleId())
                .eqIfPresent(CarDO::getTrailerId, reqVO.getTrailerId())
                .likeIfPresent(CarDO::getCaptainId, reqVO.getCaptainId())
                .likeIfPresent(CarDO::getMainId, reqVO.getMainId())
                .eqIfPresent(CarDO::getCaptainPhone, reqVO.getCaptainPhone())
                .eqIfPresent(CarDO::getMainPhone, reqVO.getMainPhone())
                .eqIfPresent(CarDO::getFleetId, reqVO.getFleetId())
                .eqIfPresent(CarDO::getIsTurnRepair, reqVO.getIsTurnRepair())
                .eqIfPresent(CarDO::getDeptId, reqVO.getDeptId())
                .orderByDesc(CarDO::getId));
    }

    default List<CarDO> selectList(CarExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CarDO>()
                .eqIfPresent(CarDO::getDeputyId, reqVO.getDeputyId())
                .eqIfPresent(CarDO::getEscortId, reqVO.getEscortId())
                .eqIfPresent(CarDO::getDeputyPhone, reqVO.getDeputyPhone())
                .eqIfPresent(CarDO::getEscortPhone, reqVO.getEscortPhone())
                .betweenIfPresent(CarDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CarDO::getAbleTonnage, reqVO.getAbleTonnage())
                .eqIfPresent(CarDO::getActualTonnage, reqVO.getActualTonnage())
                .eqIfPresent(CarDO::getGodType, reqVO.getGodType())
                .eqIfPresent(CarDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CarDO::getMainVehicleId, reqVO.getMainVehicleId())
                .eqIfPresent(CarDO::getTrailerId, reqVO.getTrailerId())
                .eqIfPresent(CarDO::getCaptainId, reqVO.getCaptainId())
                .eqIfPresent(CarDO::getMainId, reqVO.getMainId())
                .eqIfPresent(CarDO::getCaptainPhone, reqVO.getCaptainPhone())
                .eqIfPresent(CarDO::getMainPhone, reqVO.getMainPhone())
                .eqIfPresent(CarDO::getFleetId, reqVO.getFleetId())
                .eqIfPresent(CarDO::getIsTurnRepair, reqVO.getIsTurnRepair())
                .eqIfPresent(CarDO::getDeptId, reqVO.getDeptId())
                .orderByDesc(CarDO::getId));
    }

    Page<CarRespVO> selectPageByKeyword(@Param("page") Page<CarDO> page,
                                        @Param("pageReqVO") CarKeywordPageReqVO pageReqVO);


    /**
     * 车辆周期分页查询
     *
     * @param pageReqVO
     * @return
     */
    List<CarCycleRespVO> selectCarCyclePageByKeyword(@Param("pageReqVO") CarCyclePageReqVO pageReqVO);



    default List<CarDO> selectCarInfos(List<Long>... ids) {
        return selectJoinList(CarDO.class,
                new MPJLambdaWrapper<CarDO>()
                        .selectAll(CarDO.class)
                        .selectAs(MainVehicleDO::getPlateNumber, CarDO::getMotorvehicleNumber)
                        .selectAs(TrailerDO::getVehicleTrailerNo, CarDO::getVehicleTrailerNo)
                        .selectAs(TrailerDO::getVerificationmass, CarDO::getVerificationmass)
                        .selectAs(MainVehicleDO::getVehicleBrand, CarDO::getBrand)
                        .selectAs(MainVehicleDO::getFrontWeight, CarDO::getFrontWeight)
                        .selectAs("t4", AdminUserDO::getNickname, CarDO::getMainName)
                        .selectAs("t5", AdminUserDO::getNickname, CarDO::getDeputyName)
                        .selectAs("t6", AdminUserDO::getNickname, CarDO::getEscortName)
                        .leftJoin(MainVehicleDO.class, MainVehicleDO::getId, CarDO::getMainVehicleId)
                        .leftJoin(TrailerDO.class, TrailerDO::getId, CarDO::getTrailerId)
                        .leftJoin(AdminUserDO.class, "t4", AdminUserDO::getId, CarDO::getMainId)
                        .leftJoin(AdminUserDO.class, "t5", AdminUserDO::getId, CarDO::getDeptId)
                        .leftJoin(AdminUserDO.class, "t6", AdminUserDO::getId, CarDO::getEscortId)
                        .in(ids.length > 0, CarDO::getId, ids.length > 0 ? ids[0] : null)
                        .disableSubLogicDel()
        );
    }

    /**
     * 功能描述：根据车辆id，仅获取车牌号信息列表
     *
     * @param ids
     * @return {@link List }<{@link CarDO }>
     * @author 小逺
     * @date 2024/06/19 21:37
     */
    default List<CarDO> selectCarNameInfos(List<Long>... ids) {
        return selectJoinList(CarDO.class,
                new MPJLambdaWrapper<CarDO>()
                        .select(CarDO::getId)
                        .selectAs(MainVehicleDO::getPlateNumber, CarDO::getMotorvehicleNumber)
                        .selectAs(MainVehicleDO::getVehicleBrand, CarDO::getBrand)
                        .selectAs(MainVehicleDO::getFrontWeight, CarDO::getFrontWeight)
                        .leftJoin(MainVehicleDO.class, MainVehicleDO::getId, CarDO::getMainVehicleId)
                        .in(ids.length > 0, CarDO::getId, ids.length > 0 ? ids[0] : null)
                        .disableSubLogicDel()
        );
    }

    /**
     * 功能描述：根据车辆id，仅获取车牌号信息列表
     *
     * @param id
     * @return {@link List }<{@link CarDO }>
     * @author 小逺
     * @date 2024/06/19 21:37
     */
    default CarDO selectCarInfos(Long id) {
        return selectJoinOne(CarDO.class,
                new MPJLambdaWrapper<CarDO>()
                        .select(CarDO::getId, CarDO::getMainVehicleId)
                        .selectAs(MainVehicleDO::getCode, CarDO::getCode)
                        .selectAs(MainVehicleDO::getPlateNumber, CarDO::getMotorvehicleNumber)
                        .selectAs(TrailerDO::getVerificationmass, CarDO::getVehicleTrailerNo)
                        .selectAs(CarDO::getDeptId, CarDO::getCompanyId)
                        .selectAs(DeptDO::getName, CarDO::getCompanyName)
                        .leftJoin(MainVehicleDO.class, MainVehicleDO::getId, CarDO::getMainVehicleId)
                        .leftJoin(TrailerDO.class, TrailerDO::getId, CarDO::getTrailerId)
                        .leftJoin(DeptDO.class, DeptDO::getId, CarDO::getDeptId)
                        .eq(CarDO::getId, id)
                        .disableSubLogicDel()
        );
    }

    /**
     * 功能描述：获取全部车辆部分字段详细信息
     *
     * @return {@link List }<{@link TransportCarRespVO }>
     * @author 小逺
     * @date 2024/06/28
     */
    default List<TransportCarRespVO> selectCarInfos() {
        List<CarDO> carInfos = selectJoinList(CarDO.class,
                new MPJLambdaWrapper<CarDO>()
                        .select(CarDO::getId, CarDO::getMainVehicleId, CarDO::getFleetId)
                        .selectAs(MainVehicleDO::getCode, CarDO::getCode)
                        .selectAs(MainVehicleDO::getPlateNumber, CarDO::getMotorvehicleNumber)
                        .selectAs(TrailerDO::getVerificationmass, CarDO::getVehicleTrailerNo)
                        .selectAs(CarDO::getDeptId, CarDO::getCompanyId)
                        .selectAs(DeptDO::getName, CarDO::getCompanyName)
                        .selectAs(FleetDO::getName, CarDO::getFleetName)
                        .selectAs(MainVehicleDO::getVehicleBrand, CarDO::getBrand)
                        .leftJoin(MainVehicleDO.class, MainVehicleDO::getId, CarDO::getMainVehicleId)
                        .leftJoin(TrailerDO.class, TrailerDO::getId, CarDO::getTrailerId)
                        .leftJoin(FleetDO.class, FleetDO::getId, CarDO::getFleetId)
                        .leftJoin(DeptDO.class, DeptDO::getId, CarDO::getDeptId)
                        .isNotNull(CarDO::getMainVehicleId)
                        .disableSubLogicDel()
        );
        List<TransportCarRespVO> result = new ArrayList<>();
        carInfos.forEach(p -> {
            TransportCarRespVO build = TransportCarRespVO.builder().id(p.getId()).code(p.getCode()).brand(p.getBrand()).minVehicleId(p.getMainVehicleId()).name(p.getMotorvehicleNumber()).trailerNo(p.getVehicleTrailerNo()).companyId(p.getCompanyId()).companyName(p.getCompanyName()).fleetId(p.getFleetId()).fleetName(p.getFleetName()).build();
            result.add(build);
        });
        return result;
    }

    /**
     * 功能描述：根据车辆id集合获取车辆部分详细信息列表
     *
     * @param carIds
     * @return {@link List }<{@link CarInfoDTO }>
     * @author 小逺
     * @date 2024/06/28
     */
    default List<CarInfoDTO> selectCarInfosByIds(List<Long> carIds) {
        List<CarDO> carInfos = selectJoinList(CarDO.class,
                new MPJLambdaWrapper<CarDO>()
                        .select(CarDO::getId, CarDO::getMainVehicleId, CarDO::getFleetId, CarDO::getMainId, CarDO::getDeputyId, CarDO::getEscortId)
                        .selectAs(MainVehicleDO::getCode, CarDO::getCode)
                        .selectAs(MainVehicleDO::getPlateNumber, CarDO::getMotorvehicleNumber)
                        .selectAs(TrailerDO::getVerificationmass, CarDO::getVehicleTrailerNo)
                        .selectAs(CarDO::getDeptId, CarDO::getCompanyId)
                        .selectAs(DeptDO::getName, CarDO::getCompanyName)
                        .selectAs(FleetDO::getName, CarDO::getFleetName)
                        .selectAs(MainVehicleDO::getVehicleBrand, CarDO::getBrand)
                        .leftJoin(MainVehicleDO.class, MainVehicleDO::getId, CarDO::getMainVehicleId)
                        .leftJoin(TrailerDO.class, TrailerDO::getId, CarDO::getTrailerId)
                        .leftJoin(FleetDO.class, FleetDO::getId, CarDO::getFleetId)
                        .leftJoin(DeptDO.class, DeptDO::getId, CarDO::getDeptId)
                        .isNotNull(CarDO::getMainVehicleId)
                        .in(CarDO::getId, carIds)
                        .disableSubLogicDel()
        );
        List<CarInfoDTO> result = new ArrayList<>();
        carInfos.forEach(p -> {
            CarInfoDTO build = CarInfoDTO.builder().id(p.getId()).code(p.getCode()).brand(p.getBrand()).minVehicleId(p.getMainVehicleId()).name(p.getMotorvehicleNumber()).trailerNo(p.getVehicleTrailerNo()).companyId(p.getCompanyId()).companyName(p.getCompanyName()).fleetId(p.getFleetId()).fleetName(p.getFleetName()).mainId(p.getMainId()).deputyId(p.getDeputyId()).escortId(p.getEscortId()).build();
            result.add(build);
        });
        return result;
    }

    default List<CarDO> selectEffectiveCarList() {
        return selectJoinList(CarDO.class,
                new MPJLambdaWrapper<CarDO>()
                        .selectAll(CarDO.class)
                        .selectAs(MainVehicleDO::getPlateNumber, CarDO::getMotorvehicleNumber)
                        .selectAs(TrailerDO::getVehicleTrailerNo, CarDO::getVehicleTrailerNo)
                        .selectAs(TrailerDO::getVerificationmass, CarDO::getVerificationmass)
                        .leftJoin(MainVehicleDO.class, MainVehicleDO::getId, CarDO::getMainVehicleId)
                        .leftJoin(TrailerDO.class, TrailerDO::getId, CarDO::getTrailerId)
                        .eq(CarDO::getStatus, false)
                        .isNotNull(CarDO::getMainVehicleId)
                        .isNotNull(CarDO::getTrailerId)
                        .disableSubLogicDel()
        );
    }

    /**
     * 根据车头id查询车辆信息管理表
     */
    CarDO selectCarByVehicleId(Long vehicleId);

    /**
     * 更新车辆 -》车辆人员更换记录表表新增后，车队车挂更换审批返回查询
     */
    DingCarDetailsRespDTO selectDingCarDetailsById(Long id);

    /**
     * 更新车辆 -》车辆人员更换记录表新增完,驾押人员更换审批返回查询
     */
    CarChangeRespDTO selectCarChangeById(Long id);

    /**
     * 根据前端传过来的车头id查询更换的记录信息
     *
     * @return
     */
    ClientSettingsPage<CarChangeRespDTO> selectCarChangeByVehicleId(@Param("page") ClientSettingsPage<CarChangeRespDTO> page,
                                                                    @Param("pageReqVO") CarPageReqVO pageReqVO);

    /**
     * 根据车挂id查询car表
     */

    @Select("select * from fm_car where trailer_id = #{trailerId} and deleted = 0")
    CarDO selectCarByTrailerId(Long trailerId);

    /**
     * 根据车头id查询car表
     */

    @Select("select * from fm_car where main_vehicle_id = #{trailerId} and deleted = 0")
    CarDO selectCarByMainVehicleId(Long mainVehicleId);

    /**
     * 使用中数量
     */
    Integer countInUse(@Param("vehicleType") Integer vehicleType,@Param("deptId") Long deptId);
    /**
     * 空闲数量
     */
    Integer countIdle(@Param("vehicleType") Integer vehicleType,@Param("deptId") Long deptId);
    /**
     * 注销数量
     */
    Integer countDeactivated(@Param("vehicleType") Integer vehicleType,@Param("deptId") Long deptId);
    /**
     * 报废数量
     */
    Integer countScrapped(@Param("vehicleType") Integer vehicleType,@Param("deptId") Long deptId);


    default List<CarDO> selectCarInfosByNames(List<String> plateNumbers){
        return selectJoinList(CarDO.class,
                new MPJLambdaWrapper<CarDO>()
                        .select(CarDO::getId)
                        .selectAs(MainVehicleDO::getPlateNumber, CarDO::getMotorvehicleNumber)
                        .selectAs(MainVehicleDO::getVehicleBrand, CarDO::getBrand)
                        .selectAs(MainVehicleDO::getFrontWeight, CarDO::getFrontWeight)
                        .leftJoin(MainVehicleDO.class, MainVehicleDO::getId, CarDO::getMainVehicleId)
                        .in(CollectionUtils.isNotEmpty(plateNumbers), MainVehicleDO::getPlateNumber, plateNumbers)
                        .disableSubLogicDel());
    }

    /**
     * 车辆信息导出数据
     * @param pageReqVO
     * @return
     */
    List<CarExcelVO> exportList(CarExportReqVO pageReqVO);


    /**
     * 导入时重复校验的数据
     */
    List<CarRespVO> exportCheckData();

}

