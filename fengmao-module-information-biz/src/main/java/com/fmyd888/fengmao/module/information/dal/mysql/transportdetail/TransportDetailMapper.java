package com.fmyd888.fengmao.module.information.dal.mysql.transportdetail;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.common.CardDetailPage;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailListReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportdetail.TransportDetailDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运输证明细 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface TransportDetailMapper extends BaseMapperX<TransportDetailDO> {


    default List<TransportDetailDO> selectList(TransportDetailListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TransportDetailDO>()
                .orderByDesc(TransportDetailDO::getId));
    }


    /**
     * 此方法是以字段【平铺】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<TransportDetailDO> selectJoinTilePage(TransportDetailPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<TransportDetailDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), TransportDetailDO.class,
                new MPJLambdaWrapper<TransportDetailDO>()
                        .selectAll(TransportDetailDO.class)//查询的主表字段
                        //.selectAs(DeptDO::getName, TransportDetailDO::getDeptName)//查询关联表字段，此处是取组织名称
                        //.leftJoin(DeptDO.class, DeptDO::getId, TransportDetailDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
                        .disableSubLogicDel()
                        .orderByDesc(TransportDetailDO::getId));
        return new PageResult<TransportDetailDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 此方法是以字段【内联】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<TransportDetailDO> selectJoinInlinePage(TransportDetailPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<TransportDetailDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), TransportDetailDO.class,
                new MPJLambdaWrapper<TransportDetailDO>()
                        .selectAll(TransportDetailDO.class)//查询的主表字段
                        //.selectAssociation(DeptDO.class, TransportDetailDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
                        //.leftJoin(DeptDO.class, DeptDO::getId, TransportDetailDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
                        .disableSubLogicDel()
                        .orderByDesc(TransportDetailDO::getId));
        return new PageResult<TransportDetailDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    CardDetailPage<TransportDetailRespVO> selectDetailPage(@Param("page") CardDetailPage<TransportDetailRespVO> page,
                                                           @Param("pageReqVO") TransportDetailPageReqVO pageReqVO);

    /**
     * 剩余数量低于20吨
     */
    Long selectBelow20Tons();

    /**
     * 已完成
     */
    Long selectCompleted();

    /**
     * 过期
     */
    Long selectExperied();

    /**
     * 批量添加与车头的关联关系
     */
    void batchInsertTransportCars(@Param("mainVehicles") List<Long> mainVehicles,@Param("transportId") Long transportId);

}
