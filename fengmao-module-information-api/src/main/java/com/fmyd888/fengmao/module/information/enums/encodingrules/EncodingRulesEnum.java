package com.fmyd888.fengmao.module.information.enums.encodingrules;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 类功能描述：编码规则设置枚举类
 * 需要与字典设置中【encoding_rules】字典数值对应
 *
 * @author 小逺
 * @date 2024/06/09 14:46
 */
@Getter
@AllArgsConstructor
public enum EncodingRulesEnum {
    //
    TRANSPORTMANGER_CODE("34", "运输证/汽运计划编码"),
    CUSTOMER_CODE("13", "客户编码"),
    SUPPLIER_CODE("14", "供应商编码"),
    TRADE_CONTRACT_CODE("16", "贸易合同编码"),
    CARRIAGE_CONTRACT_CODE("18", "运输合同编码"),
    DINGTALK_DEPT_CODE("15", "钉钉部门编码"),
    TAX_CODE("5", "税率编码"),
    STORE_CODE("3", "仓库编码"),
    CURRENCY_CODE("10", "货币编码"),
    ADDRESS_CODE("12", "地址编码"),
    SALESMAN_CODE("35", "业务员编码"),
    UNIT_CODE("6", "计量单位编码"),
    NORMAL_MATERIAL_CODE("11", "普通货品编码规则"),
    HAZARDOUS_MATERIAL_CODE("36", "危化货品编码"),
    OTHER_TRADE_CODE("8", "其他合同编码"),  //合同类型
    VEHICLE_MAIN_CODE("38", "车头编码"),
    VEHICLE_TRAILER_CODE("39", "车挂编码"),
    OTHER_CODE("-99", "其他编码"),
    DDRW("37", "调度任务"),
    POST("40", "职位"),
    DEPT("41", "部门");
    private final String businessCode;
    private final String businessName;
}
