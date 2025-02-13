package com.fmyd888.fengmao.module.information.dal.mysql.commoditysafetycard;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.dal.dataobject.commoditysafetycard.CommoditySafetyCardDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fmyd888.fengmao.module.information.controller.admin.commoditysafetycard.vo.*;
import org.springframework.util.StringUtils;

/**
 * 安全告知卡 Mapper
 *
 * @author 巫晨旭
 */
@Mapper
public interface CommoditySafetyCardMapper extends BaseMapperX<CommoditySafetyCardDO> {

    default PageResult<CommoditySafetyCardDO> selectPage(CommoditySafetyCardPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CommoditySafetyCardDO>()
                .betweenIfPresent(CommoditySafetyCardDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CommoditySafetyCardDO::getStatus, reqVO.getStatus())
                .likeIfPresent(CommoditySafetyCardDO::getName, reqVO.getName())
                .eqIfPresent(CommoditySafetyCardDO::getRisk, reqVO.getRisk())
                .eqIfPresent(CommoditySafetyCardDO::getStorageClaim, reqVO.getStorageClaim())
                .eqIfPresent(CommoditySafetyCardDO::getLeakDispose, reqVO.getLeakDispose())
                .eqIfPresent(CommoditySafetyCardDO::getFirstAid, reqVO.getFirstAid())
                .eqIfPresent(CommoditySafetyCardDO::getFire, reqVO.getFire())
                .eqIfPresent(CommoditySafetyCardDO::getProtection, reqVO.getProtection())
                .eqIfPresent(CommoditySafetyCardDO::getRemark, reqVO.getRemark())
                .orderByDesc(CommoditySafetyCardDO::getId));
    }

    default List<CommoditySafetyCardDO> selectList(CommoditySafetyCardListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CommoditySafetyCardDO>()
                .betweenIfPresent(CommoditySafetyCardDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CommoditySafetyCardDO::getStatus, reqVO.getStatus())
                .likeIfPresent(CommoditySafetyCardDO::getName, reqVO.getName())
                .eqIfPresent(CommoditySafetyCardDO::getRisk, reqVO.getRisk())
                .eqIfPresent(CommoditySafetyCardDO::getStorageClaim, reqVO.getStorageClaim())
                .eqIfPresent(CommoditySafetyCardDO::getLeakDispose, reqVO.getLeakDispose())
                .eqIfPresent(CommoditySafetyCardDO::getFirstAid, reqVO.getFirstAid())
                .eqIfPresent(CommoditySafetyCardDO::getFire, reqVO.getFire())
                .eqIfPresent(CommoditySafetyCardDO::getProtection, reqVO.getProtection())
                .eqIfPresent(CommoditySafetyCardDO::getRemark, reqVO.getRemark())
                .orderByDesc(CommoditySafetyCardDO::getId));
    }


    /**
    * 获取分页信息【平铺】
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<CommoditySafetyCardDO> selectJoinTilePage(CommoditySafetyCardPageReqVO reqVO) {
        Page<CommoditySafetyCardDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), CommoditySafetyCardDO.class,
        new MPJLambdaWrapper<CommoditySafetyCardDO>()
            .selectAll(CommoditySafetyCardDO.class)
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),CommoditySafetyCardDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),CommoditySafetyCardDO::getStatus, reqVO.getStatus())
            .like(StringUtils.hasText(reqVO.getName()),CommoditySafetyCardDO::getName, reqVO.getName())
            .eq(ObjectUtil.isNotEmpty(reqVO.getRisk()),CommoditySafetyCardDO::getRisk, reqVO.getRisk())
            .eq(ObjectUtil.isNotEmpty(reqVO.getStorageClaim()),CommoditySafetyCardDO::getStorageClaim, reqVO.getStorageClaim())
            .eq(ObjectUtil.isNotEmpty(reqVO.getLeakDispose()),CommoditySafetyCardDO::getLeakDispose, reqVO.getLeakDispose())
            .eq(ObjectUtil.isNotEmpty(reqVO.getFirstAid()),CommoditySafetyCardDO::getFirstAid, reqVO.getFirstAid())
            .eq(ObjectUtil.isNotEmpty(reqVO.getFire()),CommoditySafetyCardDO::getFire, reqVO.getFire())
            .eq(ObjectUtil.isNotEmpty(reqVO.getProtection()),CommoditySafetyCardDO::getProtection, reqVO.getProtection())
            .eq(ObjectUtil.isNotEmpty(reqVO.getRemark()),CommoditySafetyCardDO::getRemark, reqVO.getRemark())
            .disableSubLogicDel()
            .orderByDesc(CommoditySafetyCardDO::getId));
        return new PageResult<CommoditySafetyCardDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
    * 自定义批量插入方法
    * 此方法是mybatis方法，没有拦截给creator，updater，dept_id这三个字段赋值，插入前必须先给穿入的list中这些字段先赋值再执行此方法
    * @param list 插入的数据
    */
    void batchInsertX(@Param("list") List<CommoditySafetyCardDO> list);
}
