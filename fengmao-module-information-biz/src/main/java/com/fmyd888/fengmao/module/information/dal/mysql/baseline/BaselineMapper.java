package com.fmyd888.fengmao.module.information.dal.mysql.baseline;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.BaselineListReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.BaselinePageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.BaselineSaveReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.settlebaseline.SettleBaselineReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基线 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface BaselineMapper extends BaseMapperX<BaselineDO> {

    Page<BaselineDO> selectByPage(@Param("page") Page<BaselineDO> page, @Param("pageReqVO") BaselinePageReqVO pageReqVO);

    default PageResult<BaselineDO> selectPage(BaselinePageReqVO reqVO) {

        MPJLambdaWrapper<BaselineDO> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.leftJoin(DeptDO.class, "t1", DeptDO::getId, BaselineDO::getCompanyId)
                .leftJoin(CustomerDO.class, "t2", CustomerDO::getId, BaselineDO::getLoadingManufacturerId)
                .leftJoin(CustomerDO.class, "t3", CustomerDO::getId, BaselineDO::getUnloadingManufacturerId)
                .leftJoin(AddressDO.class, "t4", AddressDO::getId, BaselineDO::getLoadingAddressId)
                .leftJoin(AddressDO.class, "t5", AddressDO::getId, BaselineDO::getUnloadingAddressId)
                .leftJoin(CustomerDO.class, "t6", CustomerDO::getId, BaselineDO::getSettlementId)
                .leftJoin(MeasurementDO.class, "t7", MeasurementDO::getId, BaselineDO::getMeasurementId)
                .select(BaselineDO::getId, BaselineDO::getStartTime, BaselineDO::getEndTime, BaselineDO::getFareRate,
                        BaselineDO::getCalculationRoute, BaselineDO::getCalculationFuel, BaselineDO::getTollStart,
                        BaselineDO::getTollEnd, BaselineDO::getCreateTime, BaselineDO::getWagesRoute)
                .selectAs(DeptDO::getName, "companyName")
                .selectAs("t2",CustomerDO::getCustomerName, BaselineDO::getLoadingManufacturerName)
                .selectAs("t3",CustomerDO::getCustomerName, BaselineDO::getUnloadingManufacturerName)
                .selectAs("t4",AddressDO::getFullAddress, BaselineDO::getLoadingAddressName)
                .selectAs("t5",AddressDO::getFullAddress, BaselineDO::getUnloadingAddressName)
                .selectAs("t6",CustomerDO::getCustomerName,BaselineDO::getSettlementName)
                .selectAs("t7",MeasurementDO::getName,BaselineDO::getMeasurementName )
                        .orderByDesc(BaselineDO::getCreateTime);

        queryWrapper.and(wrapper -> wrapper
                .and(w -> w.eq("t1.deleted", 0).or().isNull("t1.deleted"))
                .and(w -> w.eq("t2.deleted", 0).or().isNull("t2.deleted"))
                .and(w -> w.eq("t3.deleted", 0).or().isNull("t3.deleted"))
                .and(w -> w.eq("t4.deleted", 0).or().isNull("t4.deleted"))
                .and(w -> w.eq("t5.deleted", 0).or().isNull("t5.deleted"))
                .and(w -> w.eq("t6.deleted", 0).or().isNull("t6.deleted"))
                .and(w -> w.eq("t7.deleted", 0).or().isNull("t7.deleted"))
        );

        //关键词查询
        String keyword = reqVO.getKeyword();
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.and(wrapper -> wrapper
                    .like(BaselineDO::getCalculationRoute, keyword)
                    .or().like(BaselineDO::getCalculationFuel, keyword)
                    .or().like(DeptDO::getName, keyword)
                    .or().like(CustomerDO::getCustomerName, keyword)
                    .or().like(AddressDO::getFullAddress, keyword)
                    .or().like(MeasurementDO::getName, keyword)
            );
        }

        // 条件查询
        Long deptId = reqVO.getCompanyId();
        if (deptId != null) {
            queryWrapper.eq("t1.id", deptId);
        }

        Long settlementId = reqVO.getSettlementId();
        if (settlementId != null) {
            queryWrapper.eq("t6.id", settlementId);
        }

        Long loadingAddressId = reqVO.getLoadingAddressId();
        Long unloadingAddressId = reqVO.getUnloadingAddressId();
        if (loadingAddressId != null) {
            queryWrapper.eq("t4.id", loadingAddressId);
        }
        if (unloadingAddressId != null) {
            queryWrapper.eq("t5.id", unloadingAddressId);
        }

        Long loadingManufacturerId = reqVO.getLoadingManufacturerId();
        Long unloadingManufacturerId = reqVO.getUnloadingManufacturerId();
        if (loadingManufacturerId != null) {
            queryWrapper.eq("t2.id", loadingManufacturerId);
        }
        if (unloadingManufacturerId != null) {
            queryWrapper.eq("t3.id", unloadingManufacturerId);
        }


        List<String> timeRange = reqVO.getTimeRange();
        if (timeRange != null && timeRange.size() == 2) {
            String startTime = timeRange.get(0);
            String endTime = timeRange.get(1);
            if (startTime != null && !startTime.isEmpty() && endTime != null && !endTime.isEmpty()) {
                queryWrapper.and(wrapper -> wrapper
                        .between(BaselineDO::getStartTime, startTime, endTime)
                        .or().between(BaselineDO::getEndTime, startTime, endTime)
                );
            }
        }

        return selectPage(reqVO, queryWrapper);
    }

    default List<BaselineDO> selectList(BaselineListReqVO reqVO) {
        MPJLambdaWrapper<BaselineDO> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.leftJoin(DeptDO.class, "t1", DeptDO::getId, BaselineDO::getCompanyId)
                .leftJoin(CustomerDO.class, "t2", CustomerDO::getId, BaselineDO::getLoadingManufacturerId)
                .leftJoin(CustomerDO.class, "t3", CustomerDO::getId, BaselineDO::getUnloadingManufacturerId)
                .leftJoin(AddressDO.class, "t4", AddressDO::getId, BaselineDO::getLoadingAddressId)
                .leftJoin(AddressDO.class, "t5", AddressDO::getId, BaselineDO::getUnloadingAddressId)
                .leftJoin(CustomerDO.class, "t6", CustomerDO::getId, BaselineDO::getSettlementId)
                .leftJoin(MeasurementDO.class, "t7", MeasurementDO::getId, BaselineDO::getMeasurementId)
                .select(BaselineDO::getId, BaselineDO::getStartTime, BaselineDO::getEndTime, BaselineDO::getFareRate,
                        BaselineDO::getCalculationRoute, BaselineDO::getCalculationFuel, BaselineDO::getTollStart,
                        BaselineDO::getTollEnd, BaselineDO::getCreateTime, BaselineDO::getWagesRoute)
                .selectAs(DeptDO::getName, "companyName")
                .selectAs("t2",CustomerDO::getCustomerName, BaselineDO::getLoadingManufacturerName)
                .selectAs("t3",CustomerDO::getCustomerName, BaselineDO::getUnloadingManufacturerName)
                .selectAs("t4",AddressDO::getFullAddress, BaselineDO::getLoadingAddressName)
                .selectAs("t5",AddressDO::getFullAddress, BaselineDO::getUnloadingAddressName)
                .selectAs("t6",CustomerDO::getCustomerName,BaselineDO::getSettlementName)
                .selectAs("t7",MeasurementDO::getName,BaselineDO::getMeasurementName )
                .orderByDesc(BaselineDO::getCreateTime);

        queryWrapper.and(wrapper -> wrapper
                .and(w -> w.eq("t1.deleted", 0).or().isNull("t1.deleted"))
                .and(w -> w.eq("t2.deleted", 0).or().isNull("t2.deleted"))
                .and(w -> w.eq("t3.deleted", 0).or().isNull("t3.deleted"))
                .and(w -> w.eq("t4.deleted", 0).or().isNull("t4.deleted"))
                .and(w -> w.eq("t5.deleted", 0).or().isNull("t5.deleted"))
                .and(w -> w.eq("t6.deleted", 0).or().isNull("t6.deleted"))
                .and(w -> w.eq("t7.deleted", 0).or().isNull("t7.deleted"))
        );

        //关键词查询
        String keyword = reqVO.getKeyword();
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.and(wrapper -> wrapper
                    .like(BaselineDO::getCalculationRoute, keyword)
                    .or().like(BaselineDO::getCalculationFuel, keyword)
                    .or().like(DeptDO::getName, keyword)
                    .or().like(CustomerDO::getCustomerName, keyword)
                    .or().like(AddressDO::getFullAddress, keyword)
                    .or().like(MeasurementDO::getName, keyword)
            );
        }

        // 条件查询
        Long deptId = reqVO.getCompanyId();
        if (deptId != null) {
            queryWrapper.eq("t1.id", deptId);
        }

        Long settlementId = reqVO.getSettlementId();
        if (settlementId != null) {
            queryWrapper.eq("t6.id", settlementId);
        }

        Long loadingAddressId = reqVO.getLoadingAddressId();
        Long unloadingAddressId = reqVO.getUnloadingAddressId();
        if (loadingAddressId != null) {
            queryWrapper.eq("t4.id", loadingAddressId);
        }
        if (unloadingAddressId != null) {
            queryWrapper.eq("t5.id", unloadingAddressId);
        }

        Long loadingManufacturerId = reqVO.getLoadingManufacturerId();
        Long unloadingManufacturerId = reqVO.getUnloadingManufacturerId();
        if (loadingManufacturerId != null) {
            queryWrapper.eq("t2.id", loadingManufacturerId);
        }
        if (unloadingManufacturerId != null) {
            queryWrapper.eq("t3.id", unloadingManufacturerId);
        }


        List<String> timeRange = reqVO.getTimeRange();
        if (timeRange != null && timeRange.size() == 2) {
            String startTime = timeRange.get(0);
            String endTime = timeRange.get(1);
            if (startTime != null && !startTime.isEmpty() && endTime != null && !endTime.isEmpty()) {
                queryWrapper.and(wrapper -> wrapper
                        .between(BaselineDO::getStartTime, startTime, endTime)
                        .or().between(BaselineDO::getEndTime, startTime, endTime)
                );
            }
        }

        return selectList(queryWrapper);
    }


    /**
    * 此方法是以字段【平铺】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<BaselineDO> selectJoinTilePage(BaselinePageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<BaselineDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), BaselineDO.class,
        new MPJLambdaWrapper<BaselineDO>()
            .selectAll(BaselineDO.class)//查询的主表字段
            //.selectAs(DeptDO::getName, BaselineDO::getDeptName)//查询关联表字段，此处是取组织名称
            //.leftJoin(DeptDO.class, DeptDO::getId, BaselineDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),BaselineDO::getDeptId, reqVO.getDeptId())
            .between(ArrayUtil.isNotEmpty(reqVO.getStartTime()),BaselineDO::getStartTime,ArrayUtils.get(reqVO.getStartTime(),0), ArrayUtils.get(reqVO.getStartTime(),1))
            .between(ArrayUtil.isNotEmpty(reqVO.getEndTime()),BaselineDO::getEndTime,ArrayUtils.get(reqVO.getEndTime(),0), ArrayUtils.get(reqVO.getEndTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getLoadingManufacturerId()),BaselineDO::getLoadingManufacturerId, reqVO.getLoadingManufacturerId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnloadingManufacturerId()),BaselineDO::getUnloadingManufacturerId, reqVO.getUnloadingManufacturerId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getLoadingAddressId()),BaselineDO::getLoadingAddressId, reqVO.getLoadingAddressId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnloadingAddressId()),BaselineDO::getUnloadingAddressId, reqVO.getUnloadingAddressId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getMeasurementId()),BaselineDO::getMeasurementId, reqVO.getMeasurementId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCalculationRoute()),BaselineDO::getCalculationRoute, reqVO.getCalculationRoute())
            .eq(ObjectUtil.isNotEmpty(reqVO.getWagesRoute()),BaselineDO::getWagesRoute, reqVO.getWagesRoute())
            .eq(ObjectUtil.isNotEmpty(reqVO.getSettlementId()),BaselineDO::getSettlementId, reqVO.getSettlementId())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),BaselineDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()),BaselineDO::getCompanyId, reqVO.getCompanyId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCalculationFuel ()),BaselineDO::getCalculationFuel , reqVO.getCalculationFuel ())
            .eq(ObjectUtil.isNotEmpty(reqVO.getFareRate()),BaselineDO::getFareRate, reqVO.getFareRate())
            .eq(ObjectUtil.isNotEmpty(reqVO.getTollStart()),BaselineDO::getTollStart, reqVO.getTollStart())
            .eq(ObjectUtil.isNotEmpty(reqVO.getTollEnd()),BaselineDO::getTollEnd, reqVO.getTollEnd())
            .disableSubLogicDel()
            .orderByDesc(BaselineDO::getId));
        return new PageResult<BaselineDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
    * 此方法是以字段【内联】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<BaselineDO> selectJoinInlinePage(BaselinePageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<BaselineDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), BaselineDO.class,
        new MPJLambdaWrapper<BaselineDO>()
            .selectAll(BaselineDO.class)//查询的主表字段
            //.selectAssociation(DeptDO.class, BaselineDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
            //.leftJoin(DeptDO.class, DeptDO::getId, BaselineDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),BaselineDO::getDeptId, reqVO.getDeptId())
            .between(ArrayUtil.isNotEmpty(reqVO.getStartTime()),BaselineDO::getStartTime,ArrayUtils.get(reqVO.getStartTime(),0), ArrayUtils.get(reqVO.getStartTime(),1))
            .between(ArrayUtil.isNotEmpty(reqVO.getEndTime()),BaselineDO::getEndTime,ArrayUtils.get(reqVO.getEndTime(),0), ArrayUtils.get(reqVO.getEndTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getLoadingManufacturerId()),BaselineDO::getLoadingManufacturerId, reqVO.getLoadingManufacturerId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnloadingManufacturerId()),BaselineDO::getUnloadingManufacturerId, reqVO.getUnloadingManufacturerId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getLoadingAddressId()),BaselineDO::getLoadingAddressId, reqVO.getLoadingAddressId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnloadingAddressId()),BaselineDO::getUnloadingAddressId, reqVO.getUnloadingAddressId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getMeasurementId()),BaselineDO::getMeasurementId, reqVO.getMeasurementId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCalculationRoute()),BaselineDO::getCalculationRoute, reqVO.getCalculationRoute())
            .eq(ObjectUtil.isNotEmpty(reqVO.getWagesRoute()),BaselineDO::getWagesRoute, reqVO.getWagesRoute())
            .eq(ObjectUtil.isNotEmpty(reqVO.getSettlementId()),BaselineDO::getSettlementId, reqVO.getSettlementId())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),BaselineDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()),BaselineDO::getCompanyId, reqVO.getCompanyId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCalculationFuel ()),BaselineDO::getCalculationFuel , reqVO.getCalculationFuel ())
            .eq(ObjectUtil.isNotEmpty(reqVO.getFareRate()),BaselineDO::getFareRate, reqVO.getFareRate())
            .eq(ObjectUtil.isNotEmpty(reqVO.getTollStart()),BaselineDO::getTollStart, reqVO.getTollStart())
            .eq(ObjectUtil.isNotEmpty(reqVO.getTollEnd()),BaselineDO::getTollEnd, reqVO.getTollEnd())
            .disableSubLogicDel()
            .orderByDesc(BaselineDO::getId));
        return new PageResult<BaselineDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 校验
     */
    Integer selectValidBaseline(@Param("reqVo") BaselineSaveReqVO createReqVO);


    default List<BaselineDO> getSettleBaselineList(SettleBaselineReqVO reqVO){
        LambdaQueryWrapperX<BaselineDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(BaselineDO::getCompanyId,reqVO.getCompanyId())
                .eq(BaselineDO::getSettlementId,reqVO.getSettlementId())
                .eq(BaselineDO::getLoadingManufacturerId,reqVO.getLoadFactoryId())
                .eq(BaselineDO::getUnloadingManufacturerId,reqVO.getUnloadFactoryId())
                .eq(BaselineDO::getLoadingAddressId,reqVO.getLoadAddressId())
                .eq(BaselineDO::getUnloadingAddressId,reqVO.getUnloadAddressId())
                .and(wrapper -> wrapper.eq(BaselineDO::getRouteType, reqVO.getRouteType())
                        .or()
                        .eq(BaselineDO::getRouteType, 3))
                .eq(BaselineDO::getLoadingAddressId, reqVO.getLoadAddressId())
                .eq(BaselineDO::getUnloadingAddressId, reqVO.getUnloadAddressId())
                .and(wrapper ->
                        wrapper.apply("CHARINDEX(',' + CAST({0} AS VARCHAR) + ',', ',' + carBrands + ',') > 0", reqVO.getCarBrand()))
                .le(BaselineDO::getStartTime, reqVO.getUnloadTime())
                .ge(BaselineDO::getEndTime, reqVO.getUnloadTime());;
        return selectList(queryWrapper);
    }
}