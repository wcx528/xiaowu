package com.fmyd888.fengmao.module.information.dal.mysql.trailer;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerCommodityDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.Collection;
import java.util.List;

/**
 * @Title: TrailerCommodityMapper
 * @Author: huanhuan
 * @Date: 2024-06-24
 * @Description:
 */
@Mapper
public interface TrailerCommodityMapper extends BaseMapperX<TrailerCommodityDO> {

    /**
     * 自定义批量插入方法
     */
    void insertBatchX(@Param("commodityIds")List<Long> commodityIds, @Param("trailerId") Long trailerId);

    /**
     * 批量新增方法
     */
    void insertTrailerCommodityBatch(@Param("commodityIds") List<Long> commodityIds, @Param("trailerId") Long trailerId);

    /**
     * 批量删除方法
     */
    void deleteTrailerCommodityBatch(@Param("commodityIds") List<Long> commodityIds, @Param("trailerId") Long trailerId);
}
