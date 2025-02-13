package com.fmyd888.fengmao.module.information.dal.mysql.maintenancecoefficients;

import java.util.*;

import cn.hutool.core.util.StrUtil;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.dal.dataobject.maintenancecoefficients.MaintenanceCoefficientsDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.repairprojects.RepairProjectsDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.maintenancecoefficients.vo.*;
import org.springframework.util.StringUtils;

/**
 * 保养系数维护 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface MaintenanceCoefficientsMapper extends BaseMapperX<MaintenanceCoefficientsDO> {

    default PageResult<MaintenanceCoefficientsDO> selectPage(MaintenanceCoefficientsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintenanceCoefficientsDO>()
                .eqIfPresent(MaintenanceCoefficientsDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(MaintenanceCoefficientsDO::getRepairSubject, reqVO.getRepairSubject())
                .eqIfPresent(MaintenanceCoefficientsDO::getVehicleBrand, reqVO.getVehicleBrand())
                .eqIfPresent(MaintenanceCoefficientsDO::getMaintenanceItem, reqVO.getMaintenanceItem())
                .orderByDesc(MaintenanceCoefficientsDO::getId));
    }

    default List<MaintenanceCoefficientsDO> selectList(MaintenanceCoefficientsListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MaintenanceCoefficientsDO>()
                .eqIfPresent(MaintenanceCoefficientsDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(MaintenanceCoefficientsDO::getRepairSubject, reqVO.getRepairSubject())
                .eqIfPresent(MaintenanceCoefficientsDO::getVehicleBrand, reqVO.getVehicleBrand())
                .eqIfPresent(MaintenanceCoefficientsDO::getMaintenanceItem, reqVO.getMaintenanceItem())
                .orderByDesc(MaintenanceCoefficientsDO::getId));
    }


    /**
     * 此方法是以字段【平铺】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<MaintenanceCoefficientsDO> selectJoinTilePage(MaintenanceCoefficientsPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<MaintenanceCoefficientsDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), MaintenanceCoefficientsDO.class,
                new MPJLambdaWrapper<MaintenanceCoefficientsDO>()
                        .selectAll(MaintenanceCoefficientsDO.class)//查询的主表字段
                        .selectAs(DeptDO::getName, MaintenanceCoefficientsDO::getCompanyName)//查询关联表字段，此处是取组织名称
                        .selectAs(AdminUserDO::getNickname, MaintenanceCoefficientsDO::getCreatorName)
                        .leftJoin(DeptDO.class, DeptDO::getId, MaintenanceCoefficientsDO::getCompanyId)
                        .leftJoin(AdminUserDO.class, AdminUserDO::getId, MaintenanceCoefficientsDO::getCreator)
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()), MaintenanceCoefficientsDO::getCompanyId, reqVO.getCompanyId())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getRepairSubject()), MaintenanceCoefficientsDO::getRepairSubject, reqVO.getRepairSubject())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getVehicleBrand()), MaintenanceCoefficientsDO::getVehicleBrand, reqVO.getVehicleBrand())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getMaintenanceItem()), MaintenanceCoefficientsDO::getMaintenanceItem, reqVO.getMaintenanceItem())
                        .and(StrUtil.isNotBlank(reqVO.getKeyword()), p -> p.like(DeptDO::getName, reqVO.getKeyword()).or().like(AdminUserDO::getNickname, reqVO.getKeyword()))
                        .disableSubLogicDel()
                        .orderByDesc(MaintenanceCoefficientsDO::getId));
        return new PageResult<MaintenanceCoefficientsDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 此方法是以字段【内联】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<MaintenanceCoefficientsDO> selectJoinInlinePage(MaintenanceCoefficientsPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<MaintenanceCoefficientsDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), MaintenanceCoefficientsDO.class,
                new MPJLambdaWrapper<MaintenanceCoefficientsDO>()
                        .selectAll(MaintenanceCoefficientsDO.class)//查询的主表字段
                        //.selectAssociation(DeptDO.class, MaintenanceCoefficientsDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
                        //.leftJoin(DeptDO.class, DeptDO::getId, MaintenanceCoefficientsDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()), MaintenanceCoefficientsDO::getCompanyId, reqVO.getCompanyId())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getRepairSubject()), MaintenanceCoefficientsDO::getRepairSubject, reqVO.getRepairSubject())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getVehicleBrand()), MaintenanceCoefficientsDO::getVehicleBrand, reqVO.getVehicleBrand())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getMaintenanceItem()), MaintenanceCoefficientsDO::getMaintenanceItem, reqVO.getMaintenanceItem())
                        .disableSubLogicDel()
                        .orderByDesc(MaintenanceCoefficientsDO::getId));
        return new PageResult<MaintenanceCoefficientsDO>(resultPage.getRecords(), resultPage.getTotal());
    }
}
