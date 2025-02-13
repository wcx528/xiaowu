package com.fmyd888.fengmao.module.information.dal.mysql.store;

import java.util.*;
import java.util.function.Consumer;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.*;

/**
 * 仓库 Mapper
 *
 * @author 丰茂企业
 */
@Mapper
public interface StoreMapper extends BaseMapperX<StoreDO> {

    default PageResult<StoreDO> selectPage(StorePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StoreDO>()
                .likeIfPresent(StoreDO::getStoreCode, reqVO.getStoreCode())
                .likeIfPresent(StoreDO::getStoreName, reqVO.getStoreName())
                .eqIfPresent(StoreDO::getStoreType, reqVO.getStoreType())
                .eqIfPresent(StoreDO::getStatus, reqVO.getStatus())
                .likeIfPresent(StoreDO::getRemark, reqVO.getRemark())
                .eqIfPresent(StoreDO::getStoreAddressId, reqVO.getStoreAddressId())
                .betweenIfPresent(StoreDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(StoreDO::getId));
    }

    default List<StoreDO> selectList(StoreExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<StoreDO>()
                .eqIfPresent(StoreDO::getStoreCode, reqVO.getStoreCode())
                .likeIfPresent(StoreDO::getStoreName, reqVO.getStoreName())
                .eqIfPresent(StoreDO::getStoreType, reqVO.getStoreType())
                .eqIfPresent(StoreDO::getStatus, reqVO.getStatus())
                .eqIfPresent(StoreDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(StoreDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(StoreDO::getId));
    }

    default  List<StoreDO> selectStoreExportList(StoreExportReqVO exportReqVO) {
        return selectJoinList(StoreDO.class, new MPJLambdaWrapper<StoreDO>()
                .selectAll(StoreDO.class)
                .selectAs(AddressDO::getFullAddress,StoreDO::getStoreAddressName)
                .selectAs(AdminUserDO::getNickname,StoreDO::getCreatorName)
                .leftJoin(AddressDO.class, AddressDO::getId, StoreDO::getStoreAddressId)
                .leftJoin(AdminUserDO.class, AdminUserDO::getId, StoreDO::getCreator)
                .like(StrUtil.isNotBlank(exportReqVO.getStoreCode()),StoreDO::getStoreCode, exportReqVO.getStoreCode())
                .like(StrUtil.isNotBlank(exportReqVO.getStoreName()),StoreDO::getStoreName, exportReqVO.getStoreName())
                .eq(ObjectUtil.isNotEmpty(exportReqVO.getStoreType()),StoreDO::getStoreType, exportReqVO.getStoreType())
                .eq(ObjectUtil.isNotEmpty(exportReqVO.getStatus()),StoreDO::getStatus, exportReqVO.getStatus())
                .eq(StrUtil.isNotBlank(exportReqVO.getRemark()),StoreDO::getRemark, exportReqVO.getRemark())
                .between(ArrayUtil.isNotEmpty(exportReqVO.getCreateTime()),StoreDO::getCreateTime,  ArrayUtils.get(exportReqVO.getCreateTime(), 0), ArrayUtils.get(exportReqVO.getCreateTime(), 1))
                .disableSubLogicDel()
                .orderByDesc(StoreDO::getId));
    }

    default PageResult<StoreDO> selectJoinTileList(StorePageReqVO reqVO){
          Page<StoreDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), StoreDO.class,
                  new MPJLambdaWrapper<StoreDO>()
                          .selectAll(StoreDO.class)
                          .selectAs(AddressDO::getFullAddress,StoreDO::getStoreAddressName)
                          .selectAs(AdminUserDO::getNickname,StoreDO::getCreatorName)
                          .leftJoin(AddressDO.class, AddressDO::getId, StoreDO::getStoreAddressId)
                          .leftJoin(AdminUserDO.class, AdminUserDO::getId, StoreDO::getCreator)
                          .like(StrUtil.isNotBlank(reqVO.getStoreCode()),StoreDO::getStoreCode, reqVO.getStoreCode())
                          .like(StrUtil.isNotBlank(reqVO.getStoreName()),StoreDO::getStoreName, reqVO.getStoreName())
                          .eq(ObjectUtil.isNotEmpty(reqVO.getStoreType()),StoreDO::getStoreType, reqVO.getStoreType())
                          .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),StoreDO::getStatus, reqVO.getStatus())
                          .eq(StrUtil.isNotBlank(reqVO.getRemark()),StoreDO::getRemark, reqVO.getRemark())
                          .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),StoreDO::getCreateTime,  ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                          .disableSubLogicDel()
                          .orderByDesc(StoreDO::getId)
          );
        return new PageResult<StoreDO>(resultPage.getRecords(), resultPage.getTotal());
    }
}
