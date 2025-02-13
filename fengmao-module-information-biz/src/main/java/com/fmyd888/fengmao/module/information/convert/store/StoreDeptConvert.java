package com.fmyd888.fengmao.module.information.convert.store;

import com.fmyd888.fengmao.module.information.controller.admin.store.vo.StoreDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDeptDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/11/23 10:04
 * @Version: 1.0
 * @Description:
 */
@Mapper
public interface StoreDeptConvert {
    StoreDeptConvert INSTANCE = Mappers.getMapper(StoreDeptConvert.class);

    StoreDeptReqVO convert(StoreDeptDO bean);

    List<StoreDeptReqVO> convert(List<StoreDeptDO> list);
}
