package com.fmyd888.fengmao.module.information.api.riskmaintenance.serviceApi;

import com.fmyd888.fengmao.module.information.api.riskmaintenance.RiskInspectionItemOuterApiDTO;

/**
 * 隐患排查项目维护表(主表) Service 实现类
 *
 * @author 巫晨旭
 */
public interface RiskMaintenanceApiService {
    /**
     * 隐患排查精简接口
     */
    RiskInspectionItemOuterApiDTO getRiskMaintenanceSimpleList(Integer checkType, Long companyId);
}
