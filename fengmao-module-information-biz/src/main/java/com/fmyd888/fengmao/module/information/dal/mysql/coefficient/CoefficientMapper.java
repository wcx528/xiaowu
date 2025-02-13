package com.fmyd888.fengmao.module.information.dal.mysql.coefficient;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.CoefficientDetailDTO;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.LoadingRateDTO;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.MaintenanceCostsDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.CoefficientDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

/**
 * 系数维护 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface CoefficientMapper extends BaseMapperX<CoefficientDO> {

    default PageResult<CoefficientDO> selectPage(CoefficientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CoefficientDO>()
                .eqIfPresent(CoefficientDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(CoefficientDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CoefficientDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CoefficientDO::getDeputySubsidySalary, reqVO.getDeputySubsidySalary())
                .eqIfPresent(CoefficientDO::getEscortSubsidySalary, reqVO.getEscortSubsidySalary())
                .eqIfPresent(CoefficientDO::getAwardLoadingMoney, reqVO.getAwardLoadingMoney())
                .eqIfPresent(CoefficientDO::getAssessLoadingMoney, reqVO.getAssessLoadingMoney())
                .eqIfPresent(CoefficientDO::getDeputySubsidySubsidy, reqVO.getDeputySubsidySubsidy())
                .eqIfPresent(CoefficientDO::getEscortSubsidySubsidy, reqVO.getEscortSubsidySubsidy())
                .eqIfPresent(CoefficientDO::getConsumptionAward, reqVO.getConsumptionAward())
                .eqIfPresent(CoefficientDO::getConsumptionAssess, reqVO.getConsumptionAssess())
                .eqIfPresent(CoefficientDO::getSubordinateCompaniesId, reqVO.getSubordinateCompaniesId())
                .orderByDesc(CoefficientDO::getId));
    }

    default List<CoefficientDO> selectList(CoefficientListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CoefficientDO>()
                .eqIfPresent(CoefficientDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(CoefficientDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CoefficientDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CoefficientDO::getDeputySubsidySalary, reqVO.getDeputySubsidySalary())
                .eqIfPresent(CoefficientDO::getEscortSubsidySalary, reqVO.getEscortSubsidySalary())
                .eqIfPresent(CoefficientDO::getAwardLoadingMoney, reqVO.getAwardLoadingMoney())
                .eqIfPresent(CoefficientDO::getAssessLoadingMoney, reqVO.getAssessLoadingMoney())
                .eqIfPresent(CoefficientDO::getDeputySubsidySubsidy, reqVO.getDeputySubsidySubsidy())
                .eqIfPresent(CoefficientDO::getEscortSubsidySubsidy, reqVO.getEscortSubsidySubsidy())
                .eqIfPresent(CoefficientDO::getConsumptionAward, reqVO.getConsumptionAward())
                .eqIfPresent(CoefficientDO::getConsumptionAssess, reqVO.getConsumptionAssess())
                .eqIfPresent(CoefficientDO::getSubordinateCompaniesId, reqVO.getSubordinateCompaniesId())
                .orderByDesc(CoefficientDO::getId));
    }


    /**
    * 此方法是以字段【平铺】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<CoefficientDO> selectJoinTilePage(CoefficientPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<CoefficientDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), CoefficientDO.class,
        new MPJLambdaWrapper<CoefficientDO>()
            .selectAll(CoefficientDO.class)//查询的主表字段
            //.selectAs(DeptDO::getName, CoefficientDO::getDeptName)//查询关联表字段，此处是取组织名称
            //.leftJoin(DeptDO.class, DeptDO::getId, CoefficientDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),CoefficientDO::getDeptId, reqVO.getDeptId())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),CoefficientDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),CoefficientDO::getStatus, reqVO.getStatus())
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeputySubsidySalary()),CoefficientDO::getDeputySubsidySalary, reqVO.getDeputySubsidySalary())
            .eq(ObjectUtil.isNotEmpty(reqVO.getEscortSubsidySalary()),CoefficientDO::getEscortSubsidySalary, reqVO.getEscortSubsidySalary())
            .eq(ObjectUtil.isNotEmpty(reqVO.getAwardLoadingMoney()),CoefficientDO::getAwardLoadingMoney, reqVO.getAwardLoadingMoney())
            .eq(ObjectUtil.isNotEmpty(reqVO.getAssessLoadingMoney()),CoefficientDO::getAssessLoadingMoney, reqVO.getAssessLoadingMoney())
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeputySubsidySubsidy()),CoefficientDO::getDeputySubsidySubsidy, reqVO.getDeputySubsidySubsidy())
            .eq(ObjectUtil.isNotEmpty(reqVO.getEscortSubsidySubsidy()),CoefficientDO::getEscortSubsidySubsidy, reqVO.getEscortSubsidySubsidy())
            .eq(ObjectUtil.isNotEmpty(reqVO.getConsumptionAward()),CoefficientDO::getConsumptionAward, reqVO.getConsumptionAward())
            .eq(ObjectUtil.isNotEmpty(reqVO.getConsumptionAssess()),CoefficientDO::getConsumptionAssess, reqVO.getConsumptionAssess())
            .eq(ObjectUtil.isNotEmpty(reqVO.getSubordinateCompaniesId()),CoefficientDO::getSubordinateCompaniesId, reqVO.getSubordinateCompaniesId())
            .disableSubLogicDel()
            .orderByDesc(CoefficientDO::getId));
        return new PageResult<CoefficientDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
    * 此方法是以字段【内联】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<CoefficientDO> selectJoinInlinePage(CoefficientPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<CoefficientDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), CoefficientDO.class,
        new MPJLambdaWrapper<CoefficientDO>()
            .selectAll(CoefficientDO.class)//查询的主表字段
            //.selectAssociation(DeptDO.class, CoefficientDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
            //.leftJoin(DeptDO.class, DeptDO::getId, CoefficientDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),CoefficientDO::getDeptId, reqVO.getDeptId())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),CoefficientDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),CoefficientDO::getStatus, reqVO.getStatus())
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeputySubsidySalary()),CoefficientDO::getDeputySubsidySalary, reqVO.getDeputySubsidySalary())
            .eq(ObjectUtil.isNotEmpty(reqVO.getEscortSubsidySalary()),CoefficientDO::getEscortSubsidySalary, reqVO.getEscortSubsidySalary())
            .eq(ObjectUtil.isNotEmpty(reqVO.getAwardLoadingMoney()),CoefficientDO::getAwardLoadingMoney, reqVO.getAwardLoadingMoney())
            .eq(ObjectUtil.isNotEmpty(reqVO.getAssessLoadingMoney()),CoefficientDO::getAssessLoadingMoney, reqVO.getAssessLoadingMoney())
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeputySubsidySubsidy()),CoefficientDO::getDeputySubsidySubsidy, reqVO.getDeputySubsidySubsidy())
            .eq(ObjectUtil.isNotEmpty(reqVO.getEscortSubsidySubsidy()),CoefficientDO::getEscortSubsidySubsidy, reqVO.getEscortSubsidySubsidy())
            .eq(ObjectUtil.isNotEmpty(reqVO.getConsumptionAward()),CoefficientDO::getConsumptionAward, reqVO.getConsumptionAward())
            .eq(ObjectUtil.isNotEmpty(reqVO.getConsumptionAssess()),CoefficientDO::getConsumptionAssess, reqVO.getConsumptionAssess())
            .eq(ObjectUtil.isNotEmpty(reqVO.getSubordinateCompaniesId()),CoefficientDO::getSubordinateCompaniesId, reqVO.getSubordinateCompaniesId())
            .disableSubLogicDel()
            .orderByDesc(CoefficientDO::getId));
        return new PageResult<CoefficientDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 查询主表详情
     * @param subordinateCompaniesId
     * @return
     */
    CoefficientDetailDTO selectBySubordinateCompaniesId(@Param("subordinateCompaniesId") Long subordinateCompaniesId);


    /**
     *
     * @param CoefficientId
     * @return 查询子表信息：装载率系数明细列表
     */
    List<LoadingRateDTO> selectLoadingRateByCoefficientId(@Param("CoefficientId") Long CoefficientId);

    /**
     *
     * @param CoefficientId
     * @return 查询子表信息：装载率系数明细列表
     */
    List<MaintenanceCostsDTO> selectMaintenanceCostsByCoefficientId(@Param("CoefficientId") Long CoefficientId);
}
