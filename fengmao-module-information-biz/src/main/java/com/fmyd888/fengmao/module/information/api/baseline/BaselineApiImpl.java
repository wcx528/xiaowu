package com.fmyd888.fengmao.module.information.api.baseline;

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.baseline.dto.BaselineDTO;
import com.fmyd888.fengmao.module.information.api.baseline.dto.BaselineReqDTO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.BaselineRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.settlebaseline.SettleBaselineReqVO;
import com.fmyd888.fengmao.module.information.service.baseline.BaselineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能描述：基线API实现类
 * @author Misaka
 * date: 2024/8/24
 */
@Service
public class BaselineApiImpl implements BaselineApi{
    @Resource
    private BaselineService baselineService;
    @Override
    public BaselineDTO getBaseline(BaselineReqDTO baselineReqDTO) {
        SettleBaselineReqVO reqVO= BeanUtils.toBean(baselineReqDTO,SettleBaselineReqVO.class);
        BaselineRespVO baselineRespVO=baselineService.getSettleBaseline(reqVO);
        return BeanUtils.toBean(baselineRespVO,BaselineDTO.class);
    }
}
