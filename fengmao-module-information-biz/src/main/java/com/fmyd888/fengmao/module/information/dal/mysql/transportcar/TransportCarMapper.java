package com.fmyd888.fengmao.module.information.dal.mysql.transportcar;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportcar.TransportCarDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.transportcar.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

/**
 * 运输证办理车辆关联 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface TransportCarMapper extends BaseMapperX<TransportCarDO> {

    default PageResult<TransportCarDO> selectPage(TransportCarPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TransportCarDO>()
                .betweenIfPresent(TransportCarDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TransportCarDO::getId));
    }

    default List<TransportCarDO> selectList(TransportCarListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TransportCarDO>()
                .betweenIfPresent(TransportCarDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TransportCarDO::getId));
    }


    /**
    * 此方法是以字段【平铺】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<TransportCarDO> selectJoinTilePage(TransportCarPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<TransportCarDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), TransportCarDO.class,
        new MPJLambdaWrapper<TransportCarDO>()
            .selectAll(TransportCarDO.class)//查询的主表字段
            //.selectAs(DeptDO::getName, TransportCarDO::getDeptName)//查询关联表字段，此处是取组织名称
            //.leftJoin(DeptDO.class, DeptDO::getId, TransportCarDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),TransportCarDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .disableSubLogicDel()
            .orderByDesc(TransportCarDO::getId));
        return new PageResult<TransportCarDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
    * 此方法是以字段【内联】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<TransportCarDO> selectJoinInlinePage(TransportCarPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<TransportCarDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), TransportCarDO.class,
        new MPJLambdaWrapper<TransportCarDO>()
            .selectAll(TransportCarDO.class)//查询的主表字段
            //.selectAssociation(DeptDO.class, TransportCarDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
            //.leftJoin(DeptDO.class, DeptDO::getId, TransportCarDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),TransportCarDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .disableSubLogicDel()
            .orderByDesc(TransportCarDO::getId));
        return new PageResult<TransportCarDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 批量新增
     */
    void insertBatchTransportCar(@Param("carIds") List<Long> carIds,@Param("transportDetailId") Long transportDetailId);
}
