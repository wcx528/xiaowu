package com.fmyd888.fengmao.module.information.dal.mysql.taxrates;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.StorePageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesExportReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 税率 Mapper
 *
 * @author 丰茂企业
 */
@Mapper
public interface TaxratesMapper extends BaseMapperX<TaxratesDO> {

    default PageResult<TaxratesDO> selectPage(TaxratesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaxratesDO>()
                .eqIfPresent(TaxratesDO::getTaxCode, reqVO.getTaxCode())
                .likeIfPresent(TaxratesDO::getTaxName, reqVO.getTaxName())
                .eqIfPresent(TaxratesDO::getTaxRate, reqVO.getTaxRate())
                .eqIfPresent(TaxratesDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaxratesDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TaxratesDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaxratesDO::getId));
    }

    default List<TaxratesDO> selectList(TaxratesExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TaxratesDO>()
                .eqIfPresent(TaxratesDO::getTaxCode, reqVO.getTaxCode())
                .likeIfPresent(TaxratesDO::getTaxName, reqVO.getTaxName())
                .eqIfPresent(TaxratesDO::getTaxRate, reqVO.getTaxRate())
                .eqIfPresent(TaxratesDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaxratesDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TaxratesDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaxratesDO::getId));
    }

    default List<TaxratesDO> selectExportList(TaxratesExportReqVO reqVO) {
        return selectJoinList(TaxratesDO.class, new MPJLambdaWrapper<TaxratesDO>()
                .selectAll(TaxratesDO.class)
                .selectAs(TaxratesDeptDO::getDeptId, TaxratesDO::getCompanyId)
                .selectAs(DeptDO::getName, TaxratesDO::getCompanyName)
                .leftJoin(TaxratesDeptDO.class, TaxratesDeptDO::getEntityId, TaxratesDO::getId)
                .leftJoin(DeptDO.class, DeptDO::getId, TaxratesDeptDO::getDeptId));
    }

    Page<TaxratesRespVO> selectPageByKeyword(@Param("page") Page<TaxratesRespVO> page,
                                             @Param("pageReqVO") TaxratesPageReqVO reqVO);

    default PageResult<TaxratesDO> selectJoinTileList(TaxratesPageReqVO reqVO){
        Page<TaxratesDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), TaxratesDO.class,
                new MPJLambdaWrapper<TaxratesDO>()
                        .selectAll(TaxratesDO.class)
                        .selectAs(AdminUserDO::getNickname,TaxratesDO::getUpdaterName)
                        .selectAs(AdminUserDO::getNickname,TaxratesDO::getCreatorName)
                        .leftJoin(AdminUserDO.class,"t2", AdminUserDO::getId, TaxratesDO::getCreator)
                        .leftJoin(AdminUserDO.class,"t3", AdminUserDO::getId, TaxratesDO::getUpdater)
                        .exists(ObjectUtil.isNotEmpty(reqVO.getDeptId()), "select id from fm_taxrates_dept where entity_id = t.id and dept_id = "+reqVO.getDeptId())
                        .like(StrUtil.isNotBlank(reqVO.getTaxCode()),TaxratesDO::getTaxCode, reqVO.getTaxCode())
                        .like(StrUtil.isNotBlank(reqVO.getTaxName()),TaxratesDO::getTaxName, reqVO.getTaxName())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getTaxRate()),TaxratesDO::getTaxRate, reqVO.getTaxRate())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),TaxratesDO::getStatus, reqVO.getStatus())
                        .eq(StrUtil.isNotBlank(reqVO.getRemark()),TaxratesDO::getRemark, reqVO.getRemark())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),TaxratesDO::getCreateTime,  ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .disableSubLogicDel()
                        .orderByDesc(TaxratesDO::getId)
        );
        return new PageResult<TaxratesDO>(resultPage.getRecords(), resultPage.getTotal());
    }
}
