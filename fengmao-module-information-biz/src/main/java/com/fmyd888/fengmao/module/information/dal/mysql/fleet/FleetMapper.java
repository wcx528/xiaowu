package com.fmyd888.fengmao.module.information.dal.mysql.fleet;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.dal.dataobject.fleet.FleetDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.fleet.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

/**
 * 车队表 Mapper
 *
 * @author 小逺
 */
@Mapper
public interface FleetMapper extends BaseMapperX<FleetDO> {

    default PageResult<FleetDO> selectPage(FleetPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FleetDO>()
                .likeIfPresent(FleetDO::getName, reqVO.getName())
                .betweenIfPresent(FleetDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FleetDO::getCaptainId, reqVO.getCaptainId())
                .orderByDesc(FleetDO::getId));
    }

    default List<FleetDO> selectList(FleetListReqVO reqVO) {
        return selectJoinList(FleetDO.class,
                new MPJLambdaWrapper<FleetDO>()
                        .selectAll(FleetDO.class)//查询的主表字段
                        .selectAs(AdminUserDO::getNickname, FleetDO::getCaptainName)//查询关联表字段，此处是取组织名称
                        .selectAs(AdminUserDO::getMobile, FleetDO::getCaptainPhone)
                        .selectAs(DeptDO::getName, FleetDO::getDeptName)
                        .leftJoin(AdminUserDO.class, AdminUserDO::getId, FleetDO::getCaptainId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
                        .leftJoin(DeptDO.class, DeptDO::getId, FleetDO::getDeptId)
                        .like(StringUtils.hasText(reqVO.getName()), FleetDO::getName, reqVO.getName())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), FleetDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCaptainId()), FleetDO::getCaptainId, reqVO.getCaptainId())
                        .disableSubLogicDel()
                        .orderByDesc(FleetDO::getId));
    }


    /**
     * 此方法是以字段【平铺】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<FleetDO> selectJoinTilePage(FleetPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<FleetDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), FleetDO.class,
                new MPJLambdaWrapper<FleetDO>()
                        .selectAll(FleetDO.class)//查询的主表字段
                        .selectAs(AdminUserDO::getNickname, FleetDO::getCaptainName)//查询关联表字段，此处是取组织名称
                        .selectAs(AdminUserDO::getMobile, FleetDO::getCaptainPhone)
                        .selectAs(DeptDO::getName, FleetDO::getDeptName)
                        .leftJoin(AdminUserDO.class, AdminUserDO::getId, FleetDO::getCaptainId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
                        .leftJoin(DeptDO.class, DeptDO::getId, FleetDO::getDeptId)
                        .like(StringUtils.hasText(reqVO.getName()), FleetDO::getName, reqVO.getName())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), FleetDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCaptainId()), FleetDO::getCaptainId, reqVO.getCaptainId())
                        .disableSubLogicDel()
                        .orderByDesc(FleetDO::getId));
        return new PageResult<FleetDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 此方法是以字段【内联】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<FleetDO> selectJoinInlinePage(FleetPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<FleetDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), FleetDO.class,
                new MPJLambdaWrapper<FleetDO>()
                        .selectAll(FleetDO.class)//查询的主表字段
                        //.selectAssociation(DeptDO.class, FleetDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
                        //.leftJoin(DeptDO.class, DeptDO::getId, FleetDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
                        .like(StringUtils.hasText(reqVO.getName()), FleetDO::getName, reqVO.getName())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), FleetDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCaptainId()), FleetDO::getCaptainId, reqVO.getCaptainId())
                        .disableSubLogicDel()
                        .orderByDesc(FleetDO::getId));
        return new PageResult<FleetDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 自定义批量插入方法
     * 此方法是mybatis方法，没有拦截给creator，updater，dept_id这三个字段赋值，插入前必须先给穿入的list中这些字段先赋值再执行此方法
     * @param list 插入的数据
     */
    void batchInsertX(@Param("list") List<FleetDO> list);
}