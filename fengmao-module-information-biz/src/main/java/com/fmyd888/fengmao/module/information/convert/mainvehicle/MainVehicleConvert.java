package com.fmyd888.fengmao.module.information.convert.mainvehicle;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 车头档案 Convert
 *  MapStruct映射转换工具类
 * @author 丰茂
 */
@Mapper
public interface MainVehicleConvert {

    MainVehicleConvert INSTANCE = Mappers.getMapper(MainVehicleConvert.class);

    MainVehicleDO convert(MainVehicleCreateReqVO bean);

    MainVehicleDO convert(MainVehicleUpdateReqVO bean);

    MainVehicleRespVO convert(MainVehicleDO bean);

    MainVehicleExcelVO convert02(MainVehicleDO bean);

//    MainVehiclePrintingVO convert03(MainVehicleDO bean);

    List<MainVehicleRespVO> convertList(List<MainVehicleDO> list);

    List<MainVehicleExcelVO> convertList02(List<MainVehicleDO> list);

    PageResult<MainVehicleRespVO> convertPage(PageResult<MainVehicleDO> page);

    Page<MainVehicleRespVO> convertPage02(Page<MainVehicleDO> page);

    MainVehicleDO convert(MainVehicleImportExcelVO excelVO);
    MainVehicleBasicRespVO convert05(MainVehicleDO bean);

}
