package com.fmyd888.fengmao.module.information.api.dingProcessCode;

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.dingProcessCode.dto.DingProcessCodeDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.dingProcessCode.DingProcessCodeDO;
import com.fmyd888.fengmao.module.information.dal.mysql.dingProcessCode.DingProcessCodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类功能描述：钉钉审批模板API实现类
 *
 * @author 小逺
 * @date 2024/06/07 23:11
 */
@Service
public class DingProcessCodeApiImpl implements DingProcessCodeApi {
    @Resource
    private DingProcessCodeMapper dingProcessCodeMapper;

    @Override
    public DingProcessCodeDTO getDingProcessCode(Integer businessType) {
        DingProcessCodeDO dingProcessCodeDO = dingProcessCodeMapper.selectOne(DingProcessCodeDO::getBusinessType, businessType, DingProcessCodeDO::getIsEnable, true);
        return BeanUtils.toBean(dingProcessCodeDO, DingProcessCodeDTO.class);
    }
}
