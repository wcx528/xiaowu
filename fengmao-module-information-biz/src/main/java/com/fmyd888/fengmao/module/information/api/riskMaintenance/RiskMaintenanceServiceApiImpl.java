package com.fmyd888.fengmao.module.information.api.riskMaintenance;


import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.riskmaintenance.RiskInspectionItemOuterApiDTO;
import com.fmyd888.fengmao.module.information.api.riskmaintenance.serviceApi.RiskMaintenanceApiService;

import com.fmyd888.fengmao.module.information.service.riskmaintenance.RiskMaintenanceService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;


/**
 * 隐患排查项目维护表(主表) Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class RiskMaintenanceServiceApiImpl implements RiskMaintenanceApiService {

    @Resource
    private RiskMaintenanceService riskMaintenanceService;

    @Override
    public RiskInspectionItemOuterApiDTO getRiskMaintenanceSimpleList(Integer checkType, Long companyId) {
        if (checkType != null && companyId != null){
            return BeanUtils.toBean(riskMaintenanceService.getRiskMaintenanceSimpleList(checkType, companyId), RiskInspectionItemOuterApiDTO.class);
        }
        return null;
    }
}