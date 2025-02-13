package com.fmyd888.fengmao.module.information.service.baseline;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseSlaveService;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineMediumTypeDO;

import java.util.List;

/**
 * 基线 Service 接口
 *
 * @author 丰茂
 */
public interface BaselineMediumTypeService extends BaseSlaveService<BaselineMediumTypeDO> {

    /**
     * 校验基线与运输介质类型关系是否重复
     * @param baselineId
     * @param baselineMediumTypes
     * @return
     */
    Boolean validateBaselineMediumTypeExists(Long baselineId, List<Long> baselineMediumTypes);
}
