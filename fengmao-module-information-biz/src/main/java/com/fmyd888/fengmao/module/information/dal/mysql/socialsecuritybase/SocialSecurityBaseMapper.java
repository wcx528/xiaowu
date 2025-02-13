package com.fmyd888.fengmao.module.information.dal.mysql.socialsecuritybase;

import java.util.*;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.ClientSettingsPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.ClientSettingsRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybase.SocialSecurityBaseDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 社保基数维护 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface SocialSecurityBaseMapper extends BaseMapperX<SocialSecurityBaseDO> {


    default PageResult<SocialSecurityBaseDO> selectPage(SocialSecurityBasePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SocialSecurityBaseDO>()
                .eqIfPresent(SocialSecurityBaseDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(SocialSecurityBaseDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(SocialSecurityBaseDO::getStatus, reqVO.getStatus())
                .eqIfPresent(SocialSecurityBaseDO::getInsuranceType, reqVO.getInsuranceType())
                .eqIfPresent(SocialSecurityBaseDO::getInsuranceGrade, reqVO.getInsuranceGrade())
                .eqIfPresent(SocialSecurityBaseDO::getSecurityCardinal, reqVO.getSecurityCardinal())
                .eqIfPresent(SocialSecurityBaseDO::getHealthCardina, reqVO.getHealthCardina())
                .eqIfPresent(SocialSecurityBaseDO::getAccumulationCardina, reqVO.getAccumulationCardina())
                .eqIfPresent(SocialSecurityBaseDO::getExplainGrade, reqVO.getExplainGrade())
                .eqIfPresent(SocialSecurityBaseDO::getRemark, reqVO.getRemark())
                .eqIfPresent(SocialSecurityBaseDO::getPersonageAnnuity, reqVO.getPersonageAnnuity())
                .eqIfPresent(SocialSecurityBaseDO::getPersonageMedical, reqVO.getPersonageMedical())
                .eqIfPresent(SocialSecurityBaseDO::getPersonageBigMedical, reqVO.getPersonageBigMedical())
                .eqIfPresent(SocialSecurityBaseDO::getPersonageUnemployment, reqVO.getPersonageUnemployment())
                .eqIfPresent(SocialSecurityBaseDO::getPersonageAccumulation, reqVO.getPersonageAccumulation())
                .eqIfPresent(SocialSecurityBaseDO::getUnitAnnuity, reqVO.getUnitAnnuity())
                .eqIfPresent(SocialSecurityBaseDO::getUnitMedical, reqVO.getUnitMedical())
                .eqIfPresent(SocialSecurityBaseDO::getUnitBigMedical, reqVO.getUnitBigMedical())
                .eqIfPresent(SocialSecurityBaseDO::getUnitUnemployment, reqVO.getUnitUnemployment())
                .eqIfPresent(SocialSecurityBaseDO::getUnitAccumulation, reqVO.getUnitAccumulation())
                .eqIfPresent(SocialSecurityBaseDO::getEmploymentInjury, reqVO.getEmploymentInjury())
                .eqIfPresent(SocialSecurityBaseDO::getLongTerm, reqVO.getLongTerm())
                .orderByDesc(SocialSecurityBaseDO::getId));
    }




    default List<SocialSecurityBaseDO> selectList(SocialSecurityBaseListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SocialSecurityBaseDO>()
                .eqIfPresent(SocialSecurityBaseDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(SocialSecurityBaseDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(SocialSecurityBaseDO::getStatus, reqVO.getStatus())
                .eqIfPresent(SocialSecurityBaseDO::getInsuranceType, reqVO.getInsuranceType())
                .eqIfPresent(SocialSecurityBaseDO::getInsuranceGrade, reqVO.getInsuranceGrade())
                .eqIfPresent(SocialSecurityBaseDO::getSecurityCardinal, reqVO.getSecurityCardinal())
                .eqIfPresent(SocialSecurityBaseDO::getHealthCardina, reqVO.getHealthCardina())
                .eqIfPresent(SocialSecurityBaseDO::getAccumulationCardina, reqVO.getAccumulationCardina())
                .eqIfPresent(SocialSecurityBaseDO::getExplainGrade, reqVO.getExplainGrade())
                .eqIfPresent(SocialSecurityBaseDO::getRemark, reqVO.getRemark())
                .eqIfPresent(SocialSecurityBaseDO::getPersonageAnnuity, reqVO.getPersonageAnnuity())
                .eqIfPresent(SocialSecurityBaseDO::getPersonageMedical, reqVO.getPersonageMedical())
                .eqIfPresent(SocialSecurityBaseDO::getPersonageBigMedical, reqVO.getPersonageBigMedical())
                .eqIfPresent(SocialSecurityBaseDO::getPersonageUnemployment, reqVO.getPersonageUnemployment())
                .eqIfPresent(SocialSecurityBaseDO::getPersonageAccumulation, reqVO.getPersonageAccumulation())
                .eqIfPresent(SocialSecurityBaseDO::getUnitAnnuity, reqVO.getUnitAnnuity())
                .eqIfPresent(SocialSecurityBaseDO::getUnitMedical, reqVO.getUnitMedical())
                .eqIfPresent(SocialSecurityBaseDO::getUnitBigMedical, reqVO.getUnitBigMedical())
                .eqIfPresent(SocialSecurityBaseDO::getUnitUnemployment, reqVO.getUnitUnemployment())
                .eqIfPresent(SocialSecurityBaseDO::getUnitAccumulation, reqVO.getUnitAccumulation())
                .eqIfPresent(SocialSecurityBaseDO::getEmploymentInjury, reqVO.getEmploymentInjury())
                .eqIfPresent(SocialSecurityBaseDO::getLongTerm, reqVO.getLongTerm())
                .orderByDesc(SocialSecurityBaseDO::getId));
    }


    /**
    * 此方法是以字段【平铺】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<SocialSecurityBaseDO> selectJoinTilePage(SocialSecurityBasePageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<SocialSecurityBaseDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), SocialSecurityBaseDO.class,
        new MPJLambdaWrapper<SocialSecurityBaseDO>()
            .selectAll(SocialSecurityBaseDO.class)//查询的主表字段
            //.selectAs(DeptDO::getName, SocialSecurityBaseDO::getDeptName)//查询关联表字段，此处是取组织名称
            //.leftJoin(DeptDO.class, DeptDO::getId, SocialSecurityBaseDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),SocialSecurityBaseDO::getDeptId, reqVO.getDeptId())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),SocialSecurityBaseDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),SocialSecurityBaseDO::getStatus, reqVO.getStatus())
            .eq(ObjectUtil.isNotEmpty(reqVO.getInsuranceType()),SocialSecurityBaseDO::getInsuranceType, reqVO.getInsuranceType())
            .eq(ObjectUtil.isNotEmpty(reqVO.getInsuranceGrade()),SocialSecurityBaseDO::getInsuranceGrade, reqVO.getInsuranceGrade())
            .eq(ObjectUtil.isNotEmpty(reqVO.getSecurityCardinal()),SocialSecurityBaseDO::getSecurityCardinal, reqVO.getSecurityCardinal())
            .eq(ObjectUtil.isNotEmpty(reqVO.getHealthCardina()),SocialSecurityBaseDO::getHealthCardina, reqVO.getHealthCardina())
            .eq(ObjectUtil.isNotEmpty(reqVO.getAccumulationCardina()),SocialSecurityBaseDO::getAccumulationCardina, reqVO.getAccumulationCardina())
            .eq(ObjectUtil.isNotEmpty(reqVO.getExplainGrade()),SocialSecurityBaseDO::getExplainGrade, reqVO.getExplainGrade())
            .eq(ObjectUtil.isNotEmpty(reqVO.getRemark()),SocialSecurityBaseDO::getRemark, reqVO.getRemark())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPersonageAnnuity()),SocialSecurityBaseDO::getPersonageAnnuity, reqVO.getPersonageAnnuity())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPersonageMedical()),SocialSecurityBaseDO::getPersonageMedical, reqVO.getPersonageMedical())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPersonageBigMedical()),SocialSecurityBaseDO::getPersonageBigMedical, reqVO.getPersonageBigMedical())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPersonageUnemployment()),SocialSecurityBaseDO::getPersonageUnemployment, reqVO.getPersonageUnemployment())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPersonageAccumulation()),SocialSecurityBaseDO::getPersonageAccumulation, reqVO.getPersonageAccumulation())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnitAnnuity()),SocialSecurityBaseDO::getUnitAnnuity, reqVO.getUnitAnnuity())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnitMedical()),SocialSecurityBaseDO::getUnitMedical, reqVO.getUnitMedical())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnitBigMedical()),SocialSecurityBaseDO::getUnitBigMedical, reqVO.getUnitBigMedical())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnitUnemployment()),SocialSecurityBaseDO::getUnitUnemployment, reqVO.getUnitUnemployment())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnitAccumulation()),SocialSecurityBaseDO::getUnitAccumulation, reqVO.getUnitAccumulation())
            .eq(ObjectUtil.isNotEmpty(reqVO.getEmploymentInjury()),SocialSecurityBaseDO::getEmploymentInjury, reqVO.getEmploymentInjury())
            .eq(ObjectUtil.isNotEmpty(reqVO.getLongTerm()),SocialSecurityBaseDO::getLongTerm, reqVO.getLongTerm())
            .disableSubLogicDel()
            .orderByDesc(SocialSecurityBaseDO::getId));
        return new PageResult<SocialSecurityBaseDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
    * 此方法是以字段【内联】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<SocialSecurityBaseDO> selectJoinInlinePage(SocialSecurityBasePageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<SocialSecurityBaseDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), SocialSecurityBaseDO.class,
        new MPJLambdaWrapper<SocialSecurityBaseDO>()
            .selectAll(SocialSecurityBaseDO.class)//查询的主表字段
            //.selectAssociation(DeptDO.class, SocialSecurityBaseDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
            //.leftJoin(DeptDO.class, DeptDO::getId, SocialSecurityBaseDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),SocialSecurityBaseDO::getDeptId, reqVO.getDeptId())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),SocialSecurityBaseDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),SocialSecurityBaseDO::getStatus, reqVO.getStatus())
            .eq(ObjectUtil.isNotEmpty(reqVO.getInsuranceType()),SocialSecurityBaseDO::getInsuranceType, reqVO.getInsuranceType())
            .eq(ObjectUtil.isNotEmpty(reqVO.getInsuranceGrade()),SocialSecurityBaseDO::getInsuranceGrade, reqVO.getInsuranceGrade())
            .eq(ObjectUtil.isNotEmpty(reqVO.getSecurityCardinal()),SocialSecurityBaseDO::getSecurityCardinal, reqVO.getSecurityCardinal())
            .eq(ObjectUtil.isNotEmpty(reqVO.getHealthCardina()),SocialSecurityBaseDO::getHealthCardina, reqVO.getHealthCardina())
            .eq(ObjectUtil.isNotEmpty(reqVO.getAccumulationCardina()),SocialSecurityBaseDO::getAccumulationCardina, reqVO.getAccumulationCardina())
            .eq(ObjectUtil.isNotEmpty(reqVO.getExplainGrade()),SocialSecurityBaseDO::getExplainGrade, reqVO.getExplainGrade())
            .eq(ObjectUtil.isNotEmpty(reqVO.getRemark()),SocialSecurityBaseDO::getRemark, reqVO.getRemark())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPersonageAnnuity()),SocialSecurityBaseDO::getPersonageAnnuity, reqVO.getPersonageAnnuity())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPersonageMedical()),SocialSecurityBaseDO::getPersonageMedical, reqVO.getPersonageMedical())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPersonageBigMedical()),SocialSecurityBaseDO::getPersonageBigMedical, reqVO.getPersonageBigMedical())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPersonageUnemployment()),SocialSecurityBaseDO::getPersonageUnemployment, reqVO.getPersonageUnemployment())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPersonageAccumulation()),SocialSecurityBaseDO::getPersonageAccumulation, reqVO.getPersonageAccumulation())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnitAnnuity()),SocialSecurityBaseDO::getUnitAnnuity, reqVO.getUnitAnnuity())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnitMedical()),SocialSecurityBaseDO::getUnitMedical, reqVO.getUnitMedical())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnitBigMedical()),SocialSecurityBaseDO::getUnitBigMedical, reqVO.getUnitBigMedical())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnitUnemployment()),SocialSecurityBaseDO::getUnitUnemployment, reqVO.getUnitUnemployment())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnitAccumulation()),SocialSecurityBaseDO::getUnitAccumulation, reqVO.getUnitAccumulation())
            .eq(ObjectUtil.isNotEmpty(reqVO.getEmploymentInjury()),SocialSecurityBaseDO::getEmploymentInjury, reqVO.getEmploymentInjury())
            .eq(ObjectUtil.isNotEmpty(reqVO.getLongTerm()),SocialSecurityBaseDO::getLongTerm, reqVO.getLongTerm())
            .disableSubLogicDel()
            .orderByDesc(SocialSecurityBaseDO::getId));
        return new PageResult<SocialSecurityBaseDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 查询社保基数信息
     * @param page 返回信息
     * @param pageReqVO 查询信息
     * @return
     */
    ClientSettingsPage<SocialSecurityBaseRespVO> selectClientSettingsPage(@Param("page") ClientSettingsPage<SocialSecurityBaseRespVO> page,
                                                                      @Param("pageReqVO") SocialSecurityBasePageReqVO pageReqVO);

}
