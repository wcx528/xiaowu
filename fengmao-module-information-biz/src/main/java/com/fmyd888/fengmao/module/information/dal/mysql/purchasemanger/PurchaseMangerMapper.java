package com.fmyd888.fengmao.module.information.dal.mysql.purchasemanger;

import java.time.LocalDateTime;
import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.dal.dataobject.purchasemanger.PurchaseMangerDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 购买证管理 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface PurchaseMangerMapper extends BaseMapperX<PurchaseMangerDO> {

    default PageResult<PurchaseMangerDO> selectPage(PurchaseMangerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PurchaseMangerDO>()
                .eqIfPresent(PurchaseMangerDO::getPurchaseCode, reqVO.getPurchaseCode())
                .eqIfPresent(PurchaseMangerDO::getPurchaseUnit, reqVO.getPurchaseUnit())
                .eqIfPresent(PurchaseMangerDO::getSalseUnit, reqVO.getSalseUnit())
                .eqIfPresent(PurchaseMangerDO::getType, reqVO.getType())
                .eqIfPresent(PurchaseMangerDO::getDeptId, reqVO.getDeptId())
                .orderByDesc(PurchaseMangerDO::getId));
    }

    default List<PurchaseMangerDO> selectList(PurchaseMangerListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PurchaseMangerDO>()
                .eqIfPresent(PurchaseMangerDO::getPurchaseCode, reqVO.getPurchaseCode())
                .eqIfPresent(PurchaseMangerDO::getPurchaseUnit, reqVO.getPurchaseUnit())
                .eqIfPresent(PurchaseMangerDO::getSalseUnit, reqVO.getSalseUnit())
                .eqIfPresent(PurchaseMangerDO::getType, reqVO.getType())
                .eqIfPresent(PurchaseMangerDO::getDeptId, reqVO.getDeptId())
                .orderByDesc(PurchaseMangerDO::getId));
    }


    /**
    * 此方法是以字段【平铺】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default IPage<PurchaseMangerDO> selectJoinTilePage(PurchaseMangerPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        return selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), PurchaseMangerDO.class,
        new MPJLambdaWrapper<PurchaseMangerDO>()
            .selectAll(PurchaseMangerDO.class)//查询的主表字段
            //.selectAs(DeptDO::getName, PurchaseMangerDO::getDeptName)//查询关联表字段，此处是取组织名称
            //.leftJoin(DeptDO.class, DeptDO::getId, PurchaseMangerDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getPurchaseCode()),PurchaseMangerDO::getPurchaseCode, reqVO.getPurchaseCode())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPurchaseUnit()),PurchaseMangerDO::getPurchaseUnit, reqVO.getPurchaseUnit())
            .eq(ObjectUtil.isNotEmpty(reqVO.getSalseUnit()),PurchaseMangerDO::getSalseUnit, reqVO.getSalseUnit())
            .eq(ObjectUtil.isNotEmpty(reqVO.getType()),PurchaseMangerDO::getType, reqVO.getType())
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),PurchaseMangerDO::getDeptId, reqVO.getDeptId())
            .orderByDesc(PurchaseMangerDO::getId));
    }

    /**
    * 此方法是以字段【内联】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default IPage<PurchaseMangerDO> selectJoinInlinePage(PurchaseMangerPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        return selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), PurchaseMangerDO.class,
        new MPJLambdaWrapper<PurchaseMangerDO>()
            .selectAll(PurchaseMangerDO.class)//查询的主表字段
            //.selectAssociation(DeptDO.class, PurchaseMangerDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
            //.leftJoin(DeptDO.class, DeptDO::getId, PurchaseMangerDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getPurchaseCode()),PurchaseMangerDO::getPurchaseCode, reqVO.getPurchaseCode())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPurchaseUnit()),PurchaseMangerDO::getPurchaseUnit, reqVO.getPurchaseUnit())
            .eq(ObjectUtil.isNotEmpty(reqVO.getSalseUnit()),PurchaseMangerDO::getSalseUnit, reqVO.getSalseUnit())
            .eq(ObjectUtil.isNotEmpty(reqVO.getType()),PurchaseMangerDO::getType, reqVO.getType())
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),PurchaseMangerDO::getDeptId, reqVO.getDeptId())
            .orderByDesc(PurchaseMangerDO::getId));
    }


    CardPage<PurchaseMangerRespVO> selectPageByKeyword(@Param("page") CardPage<PurchaseMangerDO> page,
                                                   @Param("pageReqVO") PurchaseMangerKeywordPageReqVO pageReqVO);
    /**
     * 进行中
     *
     * @return
     */
    Long selectCountInProgress(int status);

    /**
     * 证件到期前7天产生预警
     *
     * @return
     */
    Long selectExpirationReminder();
}
