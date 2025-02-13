package com.fmyd888.fengmao.module.information.dal.mysql.riskmaintenance;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskInspectionItemDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskMaintenanceDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 隐患排查项目维护表(主表) Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface RiskMaintenanceMapper extends BaseMapperX<RiskMaintenanceDO> {

    default PageResult<RiskMaintenanceDO> selectPage(RiskMaintenancePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RiskMaintenanceDO>()
                .eqIfPresent(RiskMaintenanceDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(RiskMaintenanceDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RiskMaintenanceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RiskMaintenanceDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(RiskMaintenanceDO::getCheckType, reqVO.getCheckType())
                .eqIfPresent(RiskMaintenanceDO::getCheckCategory, reqVO.getCheckCategory())
                .eqIfPresent(RiskMaintenanceDO::getType, reqVO.getType())
                .orderByDesc(RiskMaintenanceDO::getId));
    }

    default List<RiskMaintenanceDO> selectList(RiskMaintenanceListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RiskMaintenanceDO>()
                .eqIfPresent(RiskMaintenanceDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(RiskMaintenanceDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RiskMaintenanceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RiskMaintenanceDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(RiskMaintenanceDO::getCheckType, reqVO.getCheckType())
                .eqIfPresent(RiskMaintenanceDO::getCheckCategory, reqVO.getCheckCategory())
                .eqIfPresent(RiskMaintenanceDO::getType, reqVO.getType())
                .orderByDesc(RiskMaintenanceDO::getId));
    }


    /**
    * 此方法是以字段【平铺】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<RiskMaintenanceDO> selectJoinTilePage(RiskMaintenancePageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<RiskMaintenanceDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), RiskMaintenanceDO.class,
        new MPJLambdaWrapper<RiskMaintenanceDO>()
            .selectAll(RiskMaintenanceDO.class)//查询的主表字段
            //.selectAs(DeptDO::getName, RiskMaintenanceDO::getDeptName)//查询关联表字段，此处是取组织名称
            //.leftJoin(DeptDO.class, DeptDO::getId, RiskMaintenanceDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),RiskMaintenanceDO::getDeptId, reqVO.getDeptId())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),RiskMaintenanceDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),RiskMaintenanceDO::getStatus, reqVO.getStatus())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()),RiskMaintenanceDO::getCompanyId, reqVO.getCompanyId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCheckType()),RiskMaintenanceDO::getCheckType, reqVO.getCheckType())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCheckCategory()),RiskMaintenanceDO::getCheckCategory, reqVO.getCheckCategory())
            .eq(ObjectUtil.isNotEmpty(reqVO.getType()),RiskMaintenanceDO::getType, reqVO.getType())
            .disableSubLogicDel()
            .orderByDesc(RiskMaintenanceDO::getId));
        return new PageResult<RiskMaintenanceDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
    * 此方法是以字段【内联】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<RiskMaintenanceDO> selectJoinInlinePage(RiskMaintenancePageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<RiskMaintenanceDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), RiskMaintenanceDO.class,
        new MPJLambdaWrapper<RiskMaintenanceDO>()
            .selectAll(RiskMaintenanceDO.class)//查询的主表字段
            //.selectAssociation(DeptDO.class, RiskMaintenanceDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
            //.leftJoin(DeptDO.class, DeptDO::getId, RiskMaintenanceDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),RiskMaintenanceDO::getDeptId, reqVO.getDeptId())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),RiskMaintenanceDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),RiskMaintenanceDO::getStatus, reqVO.getStatus())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()),RiskMaintenanceDO::getCompanyId, reqVO.getCompanyId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCheckType()),RiskMaintenanceDO::getCheckType, reqVO.getCheckType())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCheckCategory()),RiskMaintenanceDO::getCheckCategory, reqVO.getCheckCategory())
            .eq(ObjectUtil.isNotEmpty(reqVO.getType()),RiskMaintenanceDO::getType, reqVO.getType())
            .disableSubLogicDel()
            .orderByDesc(RiskMaintenanceDO::getId));
        return new PageResult<RiskMaintenanceDO>(resultPage.getRecords(), resultPage.getTotal());
    }



    //根据check_type, type, company_id查询外层的数据
    List<RiskMaintenanceOuterDTO> selectRiskMaintenanceDetailsById(@Param("companyId") Long companyId, @Param("checkType")Integer checkType,@Param("type") Integer type);

    // 根据id查询关联的子表隐患排查数据
    List<RiskInspectionItemDO> selectRiskInspectionItemDetailsById(Long id);
    //根据id查询关联的子表趟检数据
    List<RiskInspectionItemDO> selectRiskInspectionItemDetailsById2(Long id);

    //根据id查询关联表的介质id
    List<Long> selectCommodityIdsById(Long entityId);

    /**
     * 查询详情的接口
     * @param companyId
     * @param checkType
     * @param type
     * @return
     */
    RiskInspectionItemOuterDTO selectRiskInspectionItemById(@Param("companyId") Long companyId, @Param("checkType")Integer checkType,@Param("type") Integer type);

    /**
     * 更新时，查询存在数据库的数据
     * @param companyId
     * @param checkType
     * @param type
     * @return
     */
    List<Long> selectRiskMaintenanceIds(@Param("companyId") Long companyId, @Param("checkType")Integer checkType,@Param("type") Integer type);

    /**
     * 更新时，查询存在数据库的关联数据
     * @return
     */
    List<Long> selectOldItemIdsByEntityId(Long entityId);
}
