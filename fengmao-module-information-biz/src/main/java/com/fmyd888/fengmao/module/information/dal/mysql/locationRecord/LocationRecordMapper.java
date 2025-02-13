package com.fmyd888.fengmao.module.information.dal.mysql.locationRecord;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.dto.LocationCarInfoDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LocationRecordDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

/**
 * 车辆GPS定位 Mapper
 *
 * @author 小逺
 */
@Mapper
public interface LocationRecordMapper extends BaseMapperX<LocationRecordDO> {

    default PageResult<LocationRecordDO> selectPage(LocationRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LocationRecordDO>()
                .eqIfPresent(LocationRecordDO::getCarId, reqVO.getCarId())
                //.eqIfPresent(LocationRecordDO::getAddress, reqVO.getAddress())
                .betweenIfPresent(LocationRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(LocationRecordDO::getId));
    }

    default List<LocationRecordDO> selectList(LocationRecordListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<LocationRecordDO>()
                .eqIfPresent(LocationRecordDO::getCarId, reqVO.getCarId())
                .eqIfPresent(LocationRecordDO::getAddress, reqVO.getAddress())
                .betweenIfPresent(LocationRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(LocationRecordDO::getId));
    }


    /**
     * 此方法是以字段【平铺】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<LocationRecordDO> selectJoinTilePage(LocationRecordPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<LocationRecordDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), LocationRecordDO.class,
                new MPJLambdaWrapper<LocationRecordDO>()
                        .selectAll(LocationRecordDO.class)//查询的主表字段
                        //.selectAs(DeptDO::getName, LocationRecordDO::getDeptName)//查询关联表字段，此处是取组织名称
                        //.leftJoin(DeptDO.class, DeptDO::getId, LocationRecordDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCarId()), LocationRecordDO::getCarId, reqVO.getCarId())
                        //.eq(ObjectUtil.isNotEmpty(reqVO.getAddress()), LocationRecordDO::getAddress, reqVO.getAddress())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), LocationRecordDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .disableSubLogicDel()
                        .orderByDesc(LocationRecordDO::getId));
        return new PageResult<LocationRecordDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 此方法是以字段【内联】形式查询外键字段信息
     *
     * @param reqVO 查询实体
     * @return
     */
    default PageResult<LocationRecordDO> selectJoinInlinePage(LocationRecordPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<LocationRecordDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), LocationRecordDO.class,
                new MPJLambdaWrapper<LocationRecordDO>()
                        .selectAll(LocationRecordDO.class)//查询的主表字段
                        //.selectAssociation(DeptDO.class, LocationRecordDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
                        //.leftJoin(DeptDO.class, DeptDO::getId, LocationRecordDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCarId()), LocationRecordDO::getCarId, reqVO.getCarId())
                        //.eq(ObjectUtil.isNotEmpty(reqVO.getAddress()), LocationRecordDO::getAddress, reqVO.getAddress())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), LocationRecordDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .disableSubLogicDel()
                        .orderByDesc(LocationRecordDO::getId));
        return new PageResult<LocationRecordDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 功能描述：根据车号获取最新车辆位置信息
     *
     * @param carNos
     * @return {@link List }<{@link LocationCarInfoDTO }>
     * @author 小逺
     * @date 2024/06/20
     */
    List<LocationCarInfoDTO> selectLatestAddresses(@Param("carIds") List<Long> carNos);

    /**
     * 功能描述：批量插入位置数据
     *
     * @param list
     * @author 小逺
     * @date 2024/06/21
     */
    void batchInsertLocation(List<LocationRecordDO> list);

    /**
     * 功能描述：获取车辆最新位置
     *
     * @param carId
     * @return {@link List }<{@link LocationRecordDO }>
     * @author 小逺
     * @date 2024/06/21
     */
    default LocationRecordDO selectLocationByCarId(Long carId) {
        LambdaQueryWrapper<LocationRecordDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LocationRecordDO::getCarId, carId)
                .orderByDesc(LocationRecordDO::getId);
        return selectFirst(wrapper);
    }
    /**
     * 自定义批量插入方法
     * @param list 插入的数据
     */
    void batchInsertX(@Param("list") List<LocationRecordDO> list);

    /**
     * 物理删除
     * @param idList
     * @return
     */
    int deletePhysicallyByIds(@Param("idList") List<Long> idList);
}