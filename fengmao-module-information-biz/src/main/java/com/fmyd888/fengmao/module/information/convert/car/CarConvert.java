package com.fmyd888.fengmao.module.information.convert.car;

import java.util.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import com.fmyd888.fengmao.module.information.controller.admin.car.dto.CarChangeRespDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;

/**
 * 车辆档案 Convert
 *
 * @author 丰茂
 */
@Mapper
public interface CarConvert {

    CarConvert INSTANCE = Mappers.getMapper(CarConvert.class);

    CarDO convert(CarSaveReqVO bean);

    CarRespVO convert(CarDO bean);

//    List<CarRespVO> convertList(List<CarDO> list);
//
//    PageResult<CarRespVO> convertPage(PageResult<CarDO> page);

    List<CarRespVO> convertList(List<CarDO> carDOList);

    default PageResult<CarRespVO> convertPage(PageResult<CarDO> carDOPageResult) {
        PageResult<CarRespVO> carRespVOPageResult = new PageResult<>(
                convertList(carDOPageResult.getList()),
                carDOPageResult.getTotal(),
                carDOPageResult.getPageNo(),
                carDOPageResult.getPageSize()
        );
        return carRespVOPageResult;
    }

    List<CarChangeRespDTO> convertList2(List<CarChangeRespDTO> carDOList);
    default PageResult<CarChangeRespDTO> convertCarChangePage(PageResult<CarChangeRespDTO> carDOPageResult) {
        PageResult<CarChangeRespDTO> carRespVOPageResult = new PageResult<>(
                convertList2(carDOPageResult.getList()),
                carDOPageResult.getTotal(),
                carDOPageResult.getPageNo(),
                carDOPageResult.getPageSize()
        );
        return carRespVOPageResult;
    }

    List<CarExcelVO> convertList02(List<CarDO> list);

    List<CarImportPreviewRespVO> convertList03(List<CarImportExcelVO> list);

    Page<CarRespVO> convertPage02(Page<CarDO> page);

    List<CarBasicRespVO> convertList05(List<MainVehicleDO> list);
}
