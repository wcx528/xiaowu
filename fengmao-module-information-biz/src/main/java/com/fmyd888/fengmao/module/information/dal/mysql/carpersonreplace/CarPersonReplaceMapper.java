package com.fmyd888.fengmao.module.information.dal.mysql.carpersonreplace;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplaceListReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplacePageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplaceRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.carpersonreplace.CarPersonReplaceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆人员更换记录 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface CarPersonReplaceMapper extends BaseMapperX<CarPersonReplaceDO> {


    default List<CarPersonReplaceDO> selectList(CarPersonReplaceListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CarPersonReplaceDO>()
                .eqIfPresent(CarPersonReplaceDO::getCarId, reqVO.getCarId())
                .eqIfPresent(CarPersonReplaceDO::getFleetId, reqVO.getFleetId())
                .betweenIfPresent(CarPersonReplaceDO::getReplaceTime, reqVO.getReplaceTime())
                .eqIfPresent(CarPersonReplaceDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(CarPersonReplaceDO::getStatus, reqVO.getStatus())
                .orderByDesc(CarPersonReplaceDO::getId));
    }

    Page<CarPersonReplaceRespVO> selectPageByKeyword(@Param("page") Page<CarPersonReplaceRespVO> page,
                                                     @Param("pageReqVO") CarPersonReplacePageReqVO pageReqVO);
}
