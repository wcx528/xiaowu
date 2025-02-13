package com.fmyd888.fengmao.module.information.dal.mysql.trailer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 车挂档案 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface TrailerMapper extends BaseMapperX<TrailerDO> {

    default PageResult<TrailerDO> selectPage(TrailerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrailerDO>()
                .eqIfPresent(TrailerDO::getVehicleTrailerNo, reqVO.getVehicleTrailerNo())
                .eqIfPresent(TrailerDO::getVehicleType, reqVO.getVehicleType())
                .eqIfPresent(TrailerDO::getTrailerBrand, reqVO.getTrailerBrand())
                .eqIfPresent(TrailerDO::getVehicleColor, reqVO.getVehicleColor())
                .eqIfPresent(TrailerDO::getTankType, reqVO.getTankType())
                .betweenIfPresent(TrailerDO::getBodyReporttime, reqVO.getBodyReporttime())
                .eqIfPresent(TrailerDO::getIsOut, reqVO.getIsOut())
                .eqIfPresent(TrailerDO::getDeptId, reqVO.getDeptId())
                .likeIfPresent(TrailerDO::getDeptName, reqVO.getDeptName())
                .orderByDesc(TrailerDO::getId));
    }

    default List<TrailerDO> selectList(TrailerExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TrailerDO>()
                .eqIfPresent(TrailerDO::getVehicleTrailerNo, reqVO.getVehicleTrailerNo())
                .eqIfPresent(TrailerDO::getVehicleType, reqVO.getVehicleType())
                .eqIfPresent(TrailerDO::getTrailerBrand, reqVO.getTrailerBrand())
                .eqIfPresent(TrailerDO::getVehicleColor, reqVO.getVehicleColor())
                .eqIfPresent(TrailerDO::getTankType, reqVO.getTankType())
                .betweenIfPresent(TrailerDO::getBodyReporttime, reqVO.getBodyReporttime())
                .eqIfPresent(TrailerDO::getIsOut, reqVO.getIsOut())
                .eqIfPresent(TrailerDO::getDeptId, reqVO.getDeptId())
                .likeIfPresent(TrailerDO::getDeptName, reqVO.getDeptName())
                .orderByDesc(TrailerDO::getId));
    }

    Page<TrailerRespVO> selectPageByKeyword(@Param("page") Page<TrailerRespVO> page,
                                        @Param("pageReqVO") TrailerKeywordPageReqVO pageReqVO);

    List<FileDO> listHistoryFile(@Param("sourceId") Long sourceId,
                                 @Param("codeBusinessTypes") List<String> codeBusinessTypes);

    /**
     * 车挂导出功能
     */
    List<DownTrailerExcelVO> getTrailerList(DownTrailerExcelVO exportReqVO);

    /**
     * 查询车挂关联的介质
     */
    List<Map<String, Object>> getTrailerWithCommoditys(@Param("ids") List<Long> ids);


    /**
     *  查询车挂对应的介质列表
     * @param trailerId
     * @return
     */
    List<Map<String, Object>> selectCommodityByTrailerId(@Param("trailerId") Long trailerId);

    /**
     * 根据 ID 列表批量更新 isIdle 字段
     * @param ids ID 列表
     */
    void batchUpdateIsIdleByIds(@Param("ids") List<Long> ids);
}
