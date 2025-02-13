package com.fmyd888.fengmao.module.information.dal.mysql.clientsettings;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.StatementTemplateDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.foreignvehicleclient.ForeignVehicleClientDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 外援车头与客户设置关系 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface ForeignVehicleClientMapper extends BaseMapperX<ForeignVehicleClientDO> {

    default List<ForeignVehicleClientDO> selectListByEntityId(Long entityId) {
        return selectList(ForeignVehicleClientDO::getClientId, entityId);
    }
}
