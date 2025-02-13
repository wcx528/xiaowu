package com.fmyd888.fengmao.module.information.convert.CarPersonReplace;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplaceRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.carpersonreplace.CarPersonReplaceDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 车辆人员更换记录 Convert
 *  MapStruct映射转换工具类
 * @author 丰茂
 */
@Mapper
public interface CarPersonReplaceConvert {
    CarPersonReplaceConvert INSTANCE = Mappers.getMapper(CarPersonReplaceConvert.class);

    Page<CarPersonReplaceRespVO> convertPage02(Page<CarPersonReplaceDO> page);
}