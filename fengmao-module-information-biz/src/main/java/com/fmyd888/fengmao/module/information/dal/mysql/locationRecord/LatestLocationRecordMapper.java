package com.fmyd888.fengmao.module.information.dal.mysql.locationRecord;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.vo.LocationRecordPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.vo.LocationRecordSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.fleet.FleetDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LatestLocationRecordDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LocationRecordDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆最新GPS定位 Mapper
 *
 * @author 小逺
 */
@Mapper
public interface LatestLocationRecordMapper extends BaseMapperX<LatestLocationRecordDO> {
    /**
     * 自定义批量插入方法
     *
     * @param list 插入的数据
     */
    void batchInsertX(@Param("list") List<LatestLocationRecordDO> list);

    default PageResult<LatestLocationRecordDO> selectLatestLocationRecordPage(LocationRecordPageReqVO reqVO) {
        Page<LatestLocationRecordDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), LatestLocationRecordDO.class,
                new MPJLambdaWrapper<LatestLocationRecordDO>()
                        .selectAll(LatestLocationRecordDO.class)
                        .selectAs(MainVehicleDO::getPlateNumber, LatestLocationRecordDO::getCarName)
                        .selectAs(FleetDO::getName, LatestLocationRecordDO::getFleetName)
                        .selectAs(DeptDO::getName, LatestLocationRecordDO::getCompanyName)
                        .selectAs("t5", AdminUserDO::getNickname, LatestLocationRecordDO::getMainName)
                        .selectAs("t6", AdminUserDO::getNickname, LatestLocationRecordDO::getDeputyName)
                        .selectAs("t7", AdminUserDO::getNickname, LatestLocationRecordDO::getEscortName)
                        .leftJoin(CarDO.class, CarDO::getId, LatestLocationRecordDO::getCarId)
                        .leftJoin(MainVehicleDO.class, MainVehicleDO::getId, CarDO::getMainVehicleId)
                        .leftJoin(FleetDO.class, FleetDO::getId, CarDO::getFleetId)
                        .leftJoin(DeptDO.class, DeptDO::getId, CarDO::getDeptId)
                        .leftJoin(AdminUserDO.class, "t5", AdminUserDO::getId, CarDO::getMainId)
                        .leftJoin(AdminUserDO.class, "t6", AdminUserDO::getId, CarDO::getDeptId)
                        .leftJoin(AdminUserDO.class, "t7", AdminUserDO::getId, CarDO::getEscortId)
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCarId()), LatestLocationRecordDO::getCarId, reqVO.getCarId())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()), CarDO::getDeptId, reqVO.getCompanyId())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), LatestLocationRecordDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .and(StrUtil.isNotBlank(reqVO.getKeyword()), p -> p.like(DeptDO::getName, reqVO.getKeyword()).or().like(FleetDO::getName, reqVO.getKeyword()).or().like(MainVehicleDO::getPlateNumber, reqVO.getKeyword()).or().like("t5.nickname", reqVO.getKeyword()).or().like("t6.nickname", reqVO.getKeyword()).or().like("t7.nickname", reqVO.getKeyword()).or().like(LatestLocationRecordDO::getAddress, reqVO.getKeyword()))
                        .disableSubLogicDel()
                        .orderByDesc(LocationRecordDO::getId));
        return new PageResult<LatestLocationRecordDO>(resultPage.getRecords(), resultPage.getTotal());
    }
}
