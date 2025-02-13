package com.fmyd888.fengmao.module.information.dal.mysql.measurement;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.MeasurementExportReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.MeasurementPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.MeasurementRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.StoreExportReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.StorePageReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 计量单位表 Mapper
 *
 * @author 丰茂企业
 */
@Mapper
public interface MeasurementMapper extends BaseMapperX<MeasurementDO> {

    default PageResult<MeasurementDO> selectPage(MeasurementPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MeasurementDO>()
                .eqIfPresent(MeasurementDO::getCode, reqVO.getCode())
                .likeIfPresent(MeasurementDO::getName, reqVO.getName())
                .betweenIfPresent(MeasurementDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MeasurementDO::getRemarks, reqVO.getRemarks())
                .orderByDesc(MeasurementDO::getId));
    }

    default List<MeasurementDO> selectList(MeasurementExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MeasurementDO>()
                .eqIfPresent(MeasurementDO::getCode, reqVO.getCode())
                .likeIfPresent(MeasurementDO::getName, reqVO.getName())
                .betweenIfPresent(MeasurementDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MeasurementDO::getRemarks, reqVO.getRemarks())
                .orderByDesc(MeasurementDO::getId));
    }

    Page<MeasurementRespVO> selectByPage(@Param("page") Page<MeasurementRespVO> page,
                                     @Param("pageReqVO") MeasurementPageReqVO reqVO);

    /**
     * 功能描述：获取所有名称信息
     *
     * @return {@link List }<{@link MeasurementDO }>
     * @author 小逺
     * @date 2024/09/24
     */
    default List<MeasurementDO> selectAllInfos() {
        LambdaQueryWrapper<MeasurementDO> query = Wrappers.lambdaQuery();
        query.select(MeasurementDO::getId, MeasurementDO::getName, MeasurementDO::getCode);
        return selectList(query);
    }

    default  List<MeasurementDO> selectStoreExportList(MeasurementExportReqVO reqVO) {
        return selectJoinList(MeasurementDO.class, new MPJLambdaWrapper<MeasurementDO>()
                .selectAll(MeasurementDO.class)
                .selectAs(AdminUserDO::getNickname,MeasurementDO::getCreatorName)
                .selectAs(AdminUserDO::getNickname,MeasurementDO::getUpdaterName)
                .leftJoin(AdminUserDO.class,"t1", AdminUserDO::getId, MeasurementDO::getCreator)
                .leftJoin(AdminUserDO.class,"t2", AdminUserDO::getId, MeasurementDO::getCreator)
                .like(StrUtil.isNotBlank(reqVO.getCode()),MeasurementDO::getCode, reqVO.getCode())
                .like(StrUtil.isNotBlank(reqVO.getName()),MeasurementDO::getName, reqVO.getName())
                .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),MeasurementDO::getStatus, reqVO.getStatus())
                .eq(StrUtil.isNotBlank(reqVO.getRemarks()),MeasurementDO::getRemarks, reqVO.getRemarks())
                .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),MeasurementDO::getCreateTime,  ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                .exists(ObjectUtil.isNotEmpty(reqVO.getDeptId()), "select id from fm_measurement_dept where entity_id = t.id and dept_id = "+reqVO.getDeptId())
                .disableSubLogicDel()
                .orderByDesc(MeasurementDO::getId));
    }

    default PageResult<MeasurementDO> selectJoinTileList(MeasurementPageReqVO reqVO){
        Page<MeasurementDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), MeasurementDO.class,
                new MPJLambdaWrapper<MeasurementDO>()
                        .selectAll(MeasurementDO.class)
                        .selectAs(AdminUserDO::getNickname,MeasurementDO::getCreatorName)
                        .selectAs(AdminUserDO::getNickname,MeasurementDO::getUpdaterName)
                        .leftJoin(AdminUserDO.class,"t1", AdminUserDO::getId, MeasurementDO::getCreator)
                        .leftJoin(AdminUserDO.class,"t2", AdminUserDO::getId, MeasurementDO::getCreator)
                        .like(StrUtil.isNotBlank(reqVO.getCode()),MeasurementDO::getCode, reqVO.getCode())
                        .like(StrUtil.isNotBlank(reqVO.getName()),MeasurementDO::getName, reqVO.getName())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),MeasurementDO::getStatus, reqVO.getStatus())
                        .eq(StrUtil.isNotBlank(reqVO.getRemarks()),MeasurementDO::getRemarks, reqVO.getRemarks())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),MeasurementDO::getCreateTime,  ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .exists(ObjectUtil.isNotEmpty(reqVO.getDeptId()), "select id from fm_measurement_dept where entity_id = t.id and dept_id = "+reqVO.getDeptId())
                        .disableSubLogicDel()
                        .orderByDesc(MeasurementDO::getId)
        );
        return new PageResult<MeasurementDO>(resultPage.getRecords(), resultPage.getTotal());
    }
}
