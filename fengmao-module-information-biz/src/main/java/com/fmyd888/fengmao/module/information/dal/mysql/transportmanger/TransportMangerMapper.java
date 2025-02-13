package com.fmyd888.fengmao.module.information.dal.mysql.transportmanger;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportmanger.TransportMangerDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 运输证管理 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface TransportMangerMapper extends BaseMapperX<TransportMangerDO> {

    default PageResult<TransportMangerDO> selectPage(TransportMangerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TransportMangerDO>()
                //.eqIfPresent(TransportMangerDO::gettransportCode, reqVO.gettransportCode())
                .eqIfPresent(TransportMangerDO::getUpstreamPurchaseId, reqVO.getUpstreamPurchaseId())
                .eqIfPresent(TransportMangerDO::getDownstreamPurchaseId, reqVO.getDownstreamPurchaseId())
                .eqIfPresent(TransportMangerDO::getApplicantId, reqVO.getApplicantId())
                .eqIfPresent(TransportMangerDO::getCarrierId, reqVO.getCarrierId())
                .eqIfPresent(TransportMangerDO::getLoadFactoryId, reqVO.getLoadFactoryId())
                .eqIfPresent(TransportMangerDO::getUnloadFactoryId, reqVO.getUnloadFactoryId())
//                .likeIfPresent(TransportMangerDO::getCommodityId, reqVO.get())
                .eqIfPresent(TransportMangerDO::getTransportTonnage, reqVO.getTransportTonnage())
                .betweenIfPresent(TransportMangerDO::getTransportSdate, reqVO.getTransportSdate())
                .eqIfPresent(TransportMangerDO::getTransportEdae, reqVO.getTransportEdae())
                .betweenIfPresent(TransportMangerDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TransportMangerDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(TransportMangerDO::getStatus, reqVO.getStatus())
                //.eqIfPresent(TransportMangerDO::getCarId, reqVO.getCarId())
                // .eqIfPresent(TransportMangerDO::getLocalTransportIs, reqVO.getLocalTransportIs())
                .orderByDesc(TransportMangerDO::getId));
    }

    default List<TransportMangerDO> selectList(TransportMangerListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TransportMangerDO>()
                //.eqIfPresent(TransportMangerDO::gettransportCode, reqVO.gettransportCode())
                .eqIfPresent(TransportMangerDO::getUpstreamPurchaseId, reqVO.getUpstreamPurchaseId())
                .eqIfPresent(TransportMangerDO::getDownstreamPurchaseId, reqVO.getDownstreamPurchaseId())
                .eqIfPresent(TransportMangerDO::getApplicantId, reqVO.getApplicantId())
                .eqIfPresent(TransportMangerDO::getCarrierId, reqVO.getApplicantId())
                .eqIfPresent(TransportMangerDO::getLoadFactoryId, reqVO.getLoadFactoryName())
                .eqIfPresent(TransportMangerDO::getUnloadFactoryId, reqVO.getUnloadFactory())
//                .likeIfPresent(TransportMangerDO::getMediumName, reqVO.getMediumName())
                .eqIfPresent(TransportMangerDO::getTransportTonnage, reqVO.getTransportTonnage())
                .betweenIfPresent(TransportMangerDO::getTransportSdate, reqVO.getTransportSdate())
                .eqIfPresent(TransportMangerDO::getTransportEdae, reqVO.getTransportEdae())
                .betweenIfPresent(TransportMangerDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TransportMangerDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(TransportMangerDO::getStatus, reqVO.getStatus())
                //.eqIfPresent(TransportMangerDO::getCarId, reqVO.getCarId())
                .eqIfPresent(TransportMangerDO::getLocalTransportIs, reqVO.getLocalTransportIs())
                .orderByDesc(TransportMangerDO::getId));
    }


    /**
    * 此方法是以字段【平铺】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<TransportMangerDO> selectJoinTilePage(TransportMangerPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<TransportMangerDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), TransportMangerDO.class,
        new MPJLambdaWrapper<TransportMangerDO>()
            .selectAll(TransportMangerDO.class)//查询的主表字段
            //.selectAs(DeptDO::getName, TransportMangerDO::gSetDeptName)//查询关联表字段，此处是取组织名称
            //.leftJoin(DeptDO.class, DeptDO::getId, TransportMangerDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            //.eq(ObjectUtil.isNotEmpty(reqVO.gettransportCode()),TransportMangerDO::gettransportCode, reqVO.gettransportCode())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUpstreamPurchaseId()),TransportMangerDO::getUpstreamPurchaseId, reqVO.getUpstreamPurchaseId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getDownstreamPurchaseId()),TransportMangerDO::getDownstreamPurchaseId, reqVO.getDownstreamPurchaseId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getApplicantId()),TransportMangerDO::getApplicantId, reqVO.getApplicantId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCarrierId()),TransportMangerDO::getCarrierId, reqVO.getCarrierId())
            .eq(reqVO.getLoadFactoryId() != null,TransportMangerDO::getLoadFactoryId, reqVO.getLoadFactoryId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUnloadFactoryId()),TransportMangerDO::getUnloadFactoryId, reqVO.getUnloadFactoryId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getTransportTonnage()),TransportMangerDO::getTransportTonnage, reqVO.getTransportTonnage())
            .between(ArrayUtil.isNotEmpty(reqVO.getTransportSdate()),TransportMangerDO::getTransportSdate,ArrayUtils.get(reqVO.getTransportSdate(),0), ArrayUtils.get(reqVO.getTransportSdate(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getTransportEdae()),TransportMangerDO::getTransportEdae, reqVO.getTransportEdae())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),TransportMangerDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),TransportMangerDO::getDeptId, reqVO.getDeptId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),TransportMangerDO::getStatus, reqVO.getStatus())
            //.eq(ObjectUtil.isNotEmpty(reqVO.getCarId()),TransportMangerDO::getCarId, reqVO.getCarId())
            // .eq(ObjectUtil.isNotEmpty(reqVO.getLocalTransportIs()),TransportMangerDO::getLocalTransportIs, reqVO.getLocalTransportIs())
            .disableSubLogicDel()
            .orderByDesc(TransportMangerDO::getId));
        return new PageResult<TransportMangerDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
    * 此方法是以字段【内联】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<TransportMangerDO> selectJoinInlinePage(TransportMangerPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<TransportMangerDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), TransportMangerDO.class,
        new MPJLambdaWrapper<TransportMangerDO>()
            .selectAll(TransportMangerDO.class)//查询的主表字段
            //.selectAssociation(DeptDO.class, TransportMangerDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
            //.leftJoin(DeptDO.class, DeptDO::getId, TransportMangerDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            //.eq(ObjectUtil.isNotEmpty(reqVO.gettransportCode()),TransportMangerDO::gettransportCode, reqVO.gettransportCode())
            .eq(ObjectUtil.isNotEmpty(reqVO.getUpstreamPurchaseId()),TransportMangerDO::getUpstreamPurchaseId, reqVO.getUpstreamPurchaseId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getDownstreamPurchaseId()),TransportMangerDO::getDownstreamPurchaseId, reqVO.getDownstreamPurchaseId())
            //.eq(ObjectUtil.isNotEmpty(reqVO.getApplicant()),TransportMangerDO::getApplicant, reqVO.getApplicant())
            //.eq(ObjectUtil.isNotEmpty(reqVO.getCarrier()),TransportMangerDO::getCarrier, reqVO.getCarrier())
                .eq(reqVO.getLoadFactoryId() != null,TransportMangerDO::getLoadFactoryId, reqVO.getLoadFactoryId())
                .eq(ObjectUtil.isNotEmpty(reqVO.getUnloadFactoryId()),TransportMangerDO::getUnloadFactoryId, reqVO.getUnloadFactoryId())
            //    .like(StringUtils.hasText(reqVO.getMediumName()),TransportMangerDO::getMediumName, reqVO.getMediumName())
            .eq(ObjectUtil.isNotEmpty(reqVO.getTransportTonnage()),TransportMangerDO::getTransportTonnage, reqVO.getTransportTonnage())
            .between(ArrayUtil.isNotEmpty(reqVO.getTransportSdate()),TransportMangerDO::getTransportSdate,ArrayUtils.get(reqVO.getTransportSdate(),0), ArrayUtils.get(reqVO.getTransportSdate(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getTransportEdae()),TransportMangerDO::getTransportEdae, reqVO.getTransportEdae())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),TransportMangerDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),TransportMangerDO::getDeptId, reqVO.getDeptId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),TransportMangerDO::getStatus, reqVO.getStatus())
            //.eq(ObjectUtil.isNotEmpty(reqVO.getCarId()),TransportMangerDO::getCarId, reqVO.getCarId())
            // .eq(ObjectUtil.isNotEmpty(reqVO.getLocalTransportIs()),TransportMangerDO::getLocalTransportIs, reqVO.getLocalTransportIs())
            .disableSubLogicDel()
            .orderByDesc(TransportMangerDO::getId));
        return new PageResult<TransportMangerDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    CardPage<TransportMangerRespVO> selectPageByKeyword(@Param("page") CardPage<TransportMangerDO> page,
                                                        @Param("pageReqVO") TransportMangerKeywordPageReqVO pageReqVO);

    List<TransportDownloadExcelVO> transportDownload(@Param("id") Long id);


    /**
     * 更新运输证的状态为已完成
     * @param ids
     */
    void updateStatusToEnded(@Param("ids")List<Long> ids);

    /**
     * 更新运输证的状态为进行中
     * @param ids
     */
    void updateStatusToEnded2(@Param("ids")List<Long> ids);
}
