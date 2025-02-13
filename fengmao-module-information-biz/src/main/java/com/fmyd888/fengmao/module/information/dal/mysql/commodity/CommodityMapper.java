package com.fmyd888.fengmao.module.information.dal.mysql.commodity;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.*;

/**
 * 货物管理表 Mapper
 *
 * @author 丰茂企业
 */
@Mapper
public interface CommodityMapper extends BaseMapperX<CommodityDO> {

    default PageResult<CommodityDO> selectPage(CommodityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CommodityDO>()
                .likeIfPresent(CommodityDO::getCode, reqVO.getCode())
                .eqIfPresent(CommodityDO::getCategory, reqVO.getCategory())
                .eqIfPresent(CommodityDO::getStatus, reqVO.getStatus())
                .likeIfPresent(CommodityDO::getName, reqVO.getName())
                .eqIfPresent(CommodityDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(CommodityDO::getParentId, reqVO.getParentId())
                .eqIfPresent(CommodityDO::getNotifyCar, reqVO.getNotifyCar())
                .betweenIfPresent(CommodityDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CommodityDO::getRemarks, reqVO.getRemarks())
                .orderByDesc(CommodityDO::getId));
    }

    default List<CommodityDO> selectList(CommodityExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CommodityDO>()
                .likeIfPresent(CommodityDO::getCode, reqVO.getCode())
                .eqIfPresent(CommodityDO::getCategory, reqVO.getCategory())
                .eqIfPresent(CommodityDO::getStatus, reqVO.getStatus())
                .likeIfPresent(CommodityDO::getName, reqVO.getName())
                .eqIfPresent(CommodityDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(CommodityDO::getParentId, reqVO.getParentId())
                .eqIfPresent(CommodityDO::getNotifyCar, reqVO.getNotifyCar())
                .betweenIfPresent(CommodityDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CommodityDO::getRemarks, reqVO.getRemarks())
                .orderByDesc(CommodityDO::getId));
    }

    default List<CommodityDO> getCommodityListByNamesAndCategory(List<String> commodityNames,int category){
        return selectList(new LambdaQueryWrapperX<CommodityDO>()
                .inIfPresent(CommodityDO::getName, commodityNames)
                .eqIfPresent(CommodityDO::getCategory, category));
    }

    /**
     * 功能描述：获取所有名称信息
     *
     * @return {@link List }<{@link CommodityDO }>
     * @author 小逺
     * @date 2024/09/24
     */
    default List<CommodityDO> selectAllInfos() {
        LambdaQueryWrapper<CommodityDO> query = Wrappers.lambdaQuery();
        query.select(CommodityDO::getId, CommodityDO::getName);
        return selectList(query);
    }
}
