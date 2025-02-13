package com.fmyd888.fengmao.module.information.service.baseline;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseSlaveService;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineMediumDO;

import java.util.List;

/**
 * 基线 Service 接口
 *
 * @author 丰茂
 */
public interface BaselineMediumService extends BaseSlaveService<BaselineMediumDO> {

    /**
     * 校验基线与运输介质关系是否重复
     * @param baselineId
     * @param baselineMediums
     * @return
     */
    Boolean validateBaselineMediumExists(Long baselineId, List<Long> baselineMediums);
}
