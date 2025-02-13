package com.fmyd888.fengmao.module.information.dal.mysql.currency;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyExportReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.MeasurementExportReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.MeasurementPageReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 货币 Mapper
 *
 * @author 王五
 */
@Mapper
public interface CurrencyMapper extends BaseMapperX<CurrencyDO> {

    default PageResult<CurrencyDO> selectPage(CurrencyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CurrencyDO>()
                .eqIfPresent(CurrencyDO::getCurrencyCode, reqVO.getCurrencyCode())
                .likeIfPresent(CurrencyDO::getCurrencyName, reqVO.getCurrencyName())
                .eqIfPresent(CurrencyDO::getCurrencySymbol, reqVO.getCurrencySymbol())
                .eqIfPresent(CurrencyDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CurrencyDO::getCurrencyIdentify, reqVO.getCurrencyIdentify())
                .betweenIfPresent(CurrencyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CurrencyDO::getId));
    }

    default List<CurrencyDO> selectList(CurrencyExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CurrencyDO>()
                .eqIfPresent(CurrencyDO::getCurrencyCode, reqVO.getCurrencyCode())
                .likeIfPresent(CurrencyDO::getCurrencyName, reqVO.getCurrencyName())
                .eqIfPresent(CurrencyDO::getCurrencySymbol, reqVO.getCurrencySymbol())
                .eqIfPresent(CurrencyDO::getCurrencyIdentify, reqVO.getCurrencyIdentify())
                .betweenIfPresent(CurrencyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CurrencyDO::getId));
    }

    default List<CurrencyDO> selectListByIds(List<Long> ids) {
        return selectJoinList(CurrencyDO.class, new MPJLambdaWrapper<CurrencyDO>()
                .selectAll(CurrencyDO.class)
                .selectAs(AdminUserDO::getNickname, CurrencyDO::getCreatorName)
                .selectAs(AdminUserDO::getNickname, CurrencyDO::getUpdaterName)
                .leftJoin(AdminUserDO.class, "t1", AdminUserDO::getId, CurrencyDO::getCreator)
                .leftJoin(AdminUserDO.class, "t2", AdminUserDO::getId, CurrencyDO::getCreator)
                .in(CurrencyDO::getId, ids)
                .disableSubLogicDel()
                .orderByDesc(CurrencyDO::getId));
    }

    default List<CurrencyDO> selectStoreExportList(CurrencyExportReqVO reqVO) {
        return selectJoinList(CurrencyDO.class, new MPJLambdaWrapper<CurrencyDO>()
                .selectAll(CurrencyDO.class)
                .selectAs(AdminUserDO::getNickname, CurrencyDO::getCreatorName)
                .selectAs(AdminUserDO::getNickname, CurrencyDO::getUpdaterName)
                .leftJoin(AdminUserDO.class, "t1", AdminUserDO::getId, CurrencyDO::getCreator)
                .leftJoin(AdminUserDO.class, "t2", AdminUserDO::getId, CurrencyDO::getCreator)
                .like(StrUtil.isNotBlank(reqVO.getCurrencyCode()), CurrencyDO::getCurrencyCode, reqVO.getCurrencyCode())
                .like(StrUtil.isNotBlank(reqVO.getCurrencyName()), CurrencyDO::getCurrencyName, reqVO.getCurrencyName())
                .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()), CurrencyDO::getStatus, reqVO.getStatus())
                .like(StrUtil.isNotBlank(reqVO.getCurrencySymbol()), CurrencyDO::getCurrencySymbol, reqVO.getCurrencySymbol())
                .like(StrUtil.isNotBlank(reqVO.getCurrencyIdentify()), CurrencyDO::getCurrencyIdentify, reqVO.getCurrencyIdentify())
                .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), CurrencyDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                .disableSubLogicDel()
                .orderByDesc(CurrencyDO::getId));
    }

    default PageResult<CurrencyDO> selectJoinTileList(CurrencyPageReqVO reqVO) {
        Page<CurrencyDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), CurrencyDO.class,
                new MPJLambdaWrapper<CurrencyDO>()
                        .selectAll(CurrencyDO.class)
                        .selectAs(AdminUserDO::getNickname, CurrencyDO::getCreatorName)
                        .selectAs(AdminUserDO::getNickname, CurrencyDO::getUpdaterName)
                        .leftJoin(AdminUserDO.class, "t1", AdminUserDO::getId, CurrencyDO::getCreator)
                        .leftJoin(AdminUserDO.class, "t2", AdminUserDO::getId, CurrencyDO::getCreator)
                        .like(StrUtil.isNotBlank(reqVO.getCurrencyCode()), CurrencyDO::getCurrencyCode, reqVO.getCurrencyCode())
                        .like(StrUtil.isNotBlank(reqVO.getCurrencyName()), CurrencyDO::getCurrencyName, reqVO.getCurrencyName())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()), CurrencyDO::getStatus, reqVO.getStatus())
                        .like(StrUtil.isNotBlank(reqVO.getCurrencySymbol()), CurrencyDO::getCurrencySymbol, reqVO.getCurrencySymbol())
                        .like(StrUtil.isNotBlank(reqVO.getCurrencyIdentify()), CurrencyDO::getCurrencyIdentify, reqVO.getCurrencyIdentify())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), CurrencyDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .disableSubLogicDel()
                        .orderByDesc(CurrencyDO::getId)
        );
        return new PageResult<CurrencyDO>(resultPage.getRecords(), resultPage.getTotal());
    }

}
