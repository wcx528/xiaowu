package com.fmyd888.fengmao.module.information.dal.mysql.salesman;

import java.util.*;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyExportReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyPageReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.PostDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.salesman.vo.*;

/**
 * 业务员表  Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface SalesmanMapper extends BaseMapperX<SalesmanDO> {

    default PageResult<SalesmanDO> selectPage(SalesmanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SalesmanDO>()
                .eqIfPresent(SalesmanDO::getSalesmanCode, reqVO.getSalesmanCode())
                .likeIfPresent(SalesmanDO::getUsername, reqVO.getUsername())
                .eqIfPresent(SalesmanDO::getSalesmanType, reqVO.getSalesmanType())
                .eqIfPresent(SalesmanDO::getPositionId, reqVO.getPositionId())
                .eqIfPresent(SalesmanDO::getDescribe, reqVO.getDescribe())
                .betweenIfPresent(SalesmanDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(SalesmanDO::getSalesmanId, reqVO.getSalesmanId())
                .orderByDesc(SalesmanDO::getId));
    }

    default List<SalesmanDO> selectList(SalesmanExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SalesmanDO>()
                .eqIfPresent(SalesmanDO::getSalesmanCode, reqVO.getSalesmanCode())
                .likeIfPresent(SalesmanDO::getUsername, reqVO.getUsername())
                .eqIfPresent(SalesmanDO::getSalesmanType, reqVO.getSalesmanType())
                .eqIfPresent(SalesmanDO::getPositionId, reqVO.getPositionId())
                .eqIfPresent(SalesmanDO::getDescribe, reqVO.getDescribe())
                .betweenIfPresent(SalesmanDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(SalesmanDO::getSalesmanId, reqVO.getSalesmanId())
                .orderByDesc(SalesmanDO::getId));
    }

    default List<SalesmanDO> selectStoreExportList(SalesmanExportReqVO reqVO) {
        return selectJoinList(SalesmanDO.class, new MPJLambdaWrapper<SalesmanDO>()
                .selectAll(SalesmanDO.class)
                .selectAs("t1",AdminUserDO::getNickname, SalesmanDO::getCreatorName)
                .selectAs("t2",AdminUserDO::getNickname, SalesmanDO::getUpdaterName)
                .selectAs(PostDO::getName, SalesmanDO::getPositionName)
                .selectAs("t4",AdminUserDO::getNickname, SalesmanDO::getUsername)
                .leftJoin(AdminUserDO.class, "t1", AdminUserDO::getId, SalesmanDO::getCreator)
                .leftJoin(AdminUserDO.class, "t2", AdminUserDO::getId, SalesmanDO::getUpdater)
                .leftJoin(PostDO.class, "t3", PostDO::getId, SalesmanDO::getPositionId)
                .leftJoin(AdminUserDO.class, "t4", AdminUserDO::getId, SalesmanDO::getSalesmanId)
                .like(StrUtil.isNotBlank(reqVO.getSalesmanCode()), SalesmanDO::getSalesmanCode, reqVO.getSalesmanCode())
                .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()), SalesmanDO::getStatus, reqVO.getStatus())
                .eq(ObjectUtil.isNotEmpty(reqVO.getPositionId()), SalesmanDO::getPositionId, reqVO.getPositionId())
                .eq(ObjectUtil.isNotEmpty(reqVO.getSalesmanType()), SalesmanDO::getSalesmanType, reqVO.getSalesmanType())
                .like(StrUtil.isNotBlank(reqVO.getDescribe()), SalesmanDO::getDescribe, reqVO.getDescribe())
                .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), SalesmanDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                .disableSubLogicDel()
                .orderByDesc(SalesmanDO::getId));
    }

    default PageResult<SalesmanDO> selectJoinTileList(SalesmanPageReqVO reqVO) {
        Page<SalesmanDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), SalesmanDO.class,
                new MPJLambdaWrapper<SalesmanDO>()
                        .selectAll(SalesmanDO.class)
                        .selectAs("t1",AdminUserDO::getNickname, SalesmanDO::getCreatorName)
                        .selectAs("t2",AdminUserDO::getNickname, SalesmanDO::getUpdaterName)
                        .selectAs(PostDO::getName, SalesmanDO::getPositionName)
                        .selectAs("t4",AdminUserDO::getNickname, SalesmanDO::getUsername)
                        .leftJoin(AdminUserDO.class, "t1", AdminUserDO::getId, SalesmanDO::getCreator)
                        .leftJoin(AdminUserDO.class, "t2", AdminUserDO::getId, SalesmanDO::getUpdater)
                        .leftJoin(PostDO.class, "t3", PostDO::getId, SalesmanDO::getPositionId)
                        .leftJoin(AdminUserDO.class, "t4", AdminUserDO::getId, SalesmanDO::getSalesmanId)
                        .like(StrUtil.isNotBlank(reqVO.getSalesmanCode()), SalesmanDO::getSalesmanCode, reqVO.getSalesmanCode())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()), SalesmanDO::getStatus, reqVO.getStatus())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getPositionId()), SalesmanDO::getPositionId, reqVO.getPositionId())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getSalesmanType()), SalesmanDO::getSalesmanType, reqVO.getSalesmanType())
                        .like(StrUtil.isNotBlank(reqVO.getDescribe()), SalesmanDO::getDescribe, reqVO.getDescribe())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), SalesmanDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .disableSubLogicDel()
                        .orderByDesc(SalesmanDO::getId)
        );
        return new PageResult<SalesmanDO>(resultPage.getRecords(), resultPage.getTotal());
    }
}
