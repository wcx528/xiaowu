package com.fmyd888.fengmao.module.information.dal.mysql.repairprojects;

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
import com.fmyd888.fengmao.module.information.dal.dataobject.repairprojects.RepairProjectsDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo.*;
import org.springframework.util.StringUtils;

/**
 * 维修项目 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface RepairProjectsMapper extends BaseMapperX<RepairProjectsDO> {

    default PageResult<RepairProjectsDO> selectPage(RepairProjectsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RepairProjectsDO>()
                .eqIfPresent(RepairProjectsDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(RepairProjectsDO::getRepairType, reqVO.getRepairType())
                .eqIfPresent(RepairProjectsDO::getRepairSubject, reqVO.getRepairSubject())
                .eqIfPresent(RepairProjectsDO::getCostType, reqVO.getCostType())
                .eqIfPresent(RepairProjectsDO::getMaintainType, reqVO.getMaintainType())
                .likeIfPresent(RepairProjectsDO::getRepairItemName, reqVO.getRepairItemName())
                .eqIfPresent(RepairProjectsDO::getImgUrl, reqVO.getImgUrl())
                .orderByDesc(RepairProjectsDO::getId));
    }

    default List<RepairProjectsDO> selectList(RepairProjectsListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RepairProjectsDO>()
                .eqIfPresent(RepairProjectsDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(RepairProjectsDO::getRepairType, reqVO.getRepairType())
                .eqIfPresent(RepairProjectsDO::getRepairSubject, reqVO.getRepairSubject())
                .eqIfPresent(RepairProjectsDO::getCostType, reqVO.getCostType())
                .eqIfPresent(RepairProjectsDO::getMaintainType, reqVO.getMaintainType())
                .likeIfPresent(RepairProjectsDO::getRepairItemName, reqVO.getRepairItemName())
                .orderByDesc(RepairProjectsDO::getId));
    }


    /**
     * 此方法是以字段【平铺】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<RepairProjectsDO> selectJoinTilePage(RepairProjectsPageReqVO reqVO) {
        Page<RepairProjectsDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), RepairProjectsDO.class,
                new MPJLambdaWrapper<RepairProjectsDO>()
                        .selectAll(RepairProjectsDO.class)//查询的主表字段
                        .selectAs(DeptDO::getName, RepairProjectsDO::getCompanyName)//查询关联表字段，此处是取组织名称
                        .selectAs(AdminUserDO::getNickname, RepairProjectsDO::getCreatorName)
                        .leftJoin(DeptDO.class, DeptDO::getId, RepairProjectsDO::getCompanyId)
                        .leftJoin(AdminUserDO.class, AdminUserDO::getId, RepairProjectsDO::getCreator)
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()), RepairProjectsDO::getCompanyId, reqVO.getCompanyId())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getRepairType()), RepairProjectsDO::getRepairType, reqVO.getRepairType())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getRepairSubject()), RepairProjectsDO::getRepairSubject, reqVO.getRepairSubject())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCostType()), RepairProjectsDO::getCostType, reqVO.getCostType())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getMaintainType()), RepairProjectsDO::getMaintainType, reqVO.getMaintainType())
                        .like(StringUtils.hasText(reqVO.getRepairItemName()), RepairProjectsDO::getRepairItemName, reqVO.getRepairItemName())
                        .and(StrUtil.isNotBlank(reqVO.getKeyword()), p -> p.like(DeptDO::getName, reqVO.getKeyword()).or().like(AdminUserDO::getNickname, reqVO.getKeyword()).or().like(RepairProjectsDO::getRepairItemName, reqVO.getKeyword()))
                        .disableSubLogicDel()
                        .orderByDesc(RepairProjectsDO::getId));
        return new PageResult<RepairProjectsDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 此方法是以字段【内联】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<RepairProjectsDO> selectJoinInlinePage(RepairProjectsPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<RepairProjectsDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), RepairProjectsDO.class,
                new MPJLambdaWrapper<RepairProjectsDO>()
                        .selectAll(RepairProjectsDO.class)//查询的主表字段
                        //.selectAssociation(DeptDO.class, RepairProjectsDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
                        //.leftJoin(DeptDO.class, DeptDO::getId, RepairProjectsDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()), RepairProjectsDO::getCompanyId, reqVO.getCompanyId())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getRepairType()), RepairProjectsDO::getRepairType, reqVO.getRepairType())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getRepairSubject()), RepairProjectsDO::getRepairSubject, reqVO.getRepairSubject())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCostType()), RepairProjectsDO::getCostType, reqVO.getCostType())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getMaintainType()), RepairProjectsDO::getMaintainType, reqVO.getMaintainType())
                        .like(StringUtils.hasText(reqVO.getRepairItemName()), RepairProjectsDO::getRepairItemName, reqVO.getRepairItemName())
                        .disableSubLogicDel()
                        .orderByDesc(RepairProjectsDO::getId));
        return new PageResult<RepairProjectsDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    default List<RepairProjectsDO> selecAllList(RepairProjectBaseReqVO reqVO){
        return selectList(new LambdaQueryWrapperX<RepairProjectsDO>()
                .eq(ObjectUtil.isNotEmpty(reqVO.getRepairType()), RepairProjectsDO::getRepairType, reqVO.getRepairType())
                .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()), RepairProjectsDO::getCompanyId, reqVO.getCompanyId())
                .like(StringUtils.hasText(reqVO.getKeyword()), RepairProjectsDO::getRepairItemName, reqVO.getKeyword())
        );
    }

    default List<RepairProjectsDO> selectByNames(List<String> names){
        return selectList(new LambdaQueryWrapperX<RepairProjectsDO>()
                .in(RepairProjectsDO::getRepairItemName, names)
        );
    }
}
