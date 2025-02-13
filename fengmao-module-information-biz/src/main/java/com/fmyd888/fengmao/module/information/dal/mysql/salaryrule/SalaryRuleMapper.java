package com.fmyd888.fengmao.module.information.dal.mysql.salaryrule;

import java.util.*;

import cn.hutool.core.util.StrUtil;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule.SalaryRuleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 薪资规则配置 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface SalaryRuleMapper extends BaseMapperX<SalaryRuleDO> {

    default PageResult<SalaryRuleDO> selectPage(SalaryRulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SalaryRuleDO>()
//                .betweenIfPresent(SalaryRuleDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(SalaryRuleDO::getPosition, reqVO.getPosition())
                .eqIfPresent(SalaryRuleDO::getBasicSalary, reqVO.getBasicSalary())
                .eqIfPresent(SalaryRuleDO::getAttendanceAward, reqVO.getAttendanceAward())
                .eqIfPresent(SalaryRuleDO::getPositionSubsidy, reqVO.getPositionSubsidy())
                .eqIfPresent(SalaryRuleDO::getStructuralSubsidy, reqVO.getStructuralSubsidy())
                .eqIfPresent(SalaryRuleDO::getOther, reqVO.getOther())
                .eqIfPresent(SalaryRuleDO::getSalaryTotal, reqVO.getSalaryTotal())
                .eqIfPresent(SalaryRuleDO::getGrade, reqVO.getGrade())
                .eqIfPresent(SalaryRuleDO::getRemark, reqVO.getRemark())
                .eqIfPresent(SalaryRuleDO::getGradeStandard, reqVO.getGradeStandard())
                .eqIfPresent(SalaryRuleDO::getAnnualSalary, reqVO.getAnnualSalary())
                .orderByDesc(SalaryRuleDO::getId));
    }

    default List<SalaryRuleDO> selectList(SalaryRuleListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SalaryRuleDO>()
                .betweenIfPresent(SalaryRuleDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(SalaryRuleDO::getPosition, reqVO.getPosition())
                .eqIfPresent(SalaryRuleDO::getBasicSalary, reqVO.getBasicSalary())
                .eqIfPresent(SalaryRuleDO::getAttendanceAward, reqVO.getAttendanceAward())
                .eqIfPresent(SalaryRuleDO::getPositionSubsidy, reqVO.getPositionSubsidy())
                .eqIfPresent(SalaryRuleDO::getStructuralSubsidy, reqVO.getStructuralSubsidy())
                .eqIfPresent(SalaryRuleDO::getOther, reqVO.getOther())
                .eqIfPresent(SalaryRuleDO::getSalaryTotal, reqVO.getSalaryTotal())
                .eqIfPresent(SalaryRuleDO::getGrade, reqVO.getGrade())
                .eqIfPresent(SalaryRuleDO::getRemark, reqVO.getRemark())
                .eqIfPresent(SalaryRuleDO::getGradeStandard, reqVO.getGradeStandard())
                .eqIfPresent(SalaryRuleDO::getAnnualSalary, reqVO.getAnnualSalary())
                .orderByDesc(SalaryRuleDO::getId));
    }


    /**
     * 此方法是以字段【平铺】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<SalaryRuleDO> selectJoinTilePage(SalaryRulePageReqVO reqVO) {
        Page<SalaryRuleDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), SalaryRuleDO.class,
                new MPJLambdaWrapper<SalaryRuleDO>()
                        .selectAll(SalaryRuleDO.class)
                        .selectAs(AdminUserDO::getNickname,SalaryRuleDO::getCreatorName)
                        .selectAs(AdminUserDO::getNickname,SalaryRuleDO::getUpdaterName)
                        .leftJoin(AdminUserDO.class, "t1", AdminUserDO::getId, SalaryRuleDO::getCreator)
                        .leftJoin(AdminUserDO.class, "t2", AdminUserDO::getId, SalaryRuleDO::getCreator)
                        .exists(ObjectUtil.isNotEmpty(reqVO.getDeptId()), "select id from fm_salary_rule_dept where entity_id = t.id and dept_id = "+reqVO.getDeptId())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getPosition()), SalaryRuleDO::getPosition, reqVO.getPosition())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getGrade()), SalaryRuleDO::getGrade, reqVO.getGrade())
                        .like(ObjectUtil.isNotEmpty(reqVO.getRemark()), SalaryRuleDO::getRemark, reqVO.getRemark())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), SalesmanDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .and(StrUtil.isNotBlank(reqVO.getSearchKey()), wrapper ->
                                wrapper.like(SalaryRuleDO::getRemark, reqVO.getSearchKey())
                                        .or().like(SalaryRuleDO::getGradeStandard, reqVO.getSearchKey())
                                        .or().like("t1.nickname", reqVO.getSearchKey())
                                        .or().like("t2.nickname", reqVO.getSearchKey()))
                        .disableSubLogicDel()
                        .orderByDesc(SalaryRuleDO::getId));
        return new PageResult<SalaryRuleDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 分页多条件查询
     *
     * @param page
     * @param
     * @return
     */
    Page<SalaryRuleRespVO> selectByPage(@Param("page") Page<SalaryRuleRespVO> page,
                                        @Param("pageReqVO") SalaryRulePageReqVO reqVO);
}