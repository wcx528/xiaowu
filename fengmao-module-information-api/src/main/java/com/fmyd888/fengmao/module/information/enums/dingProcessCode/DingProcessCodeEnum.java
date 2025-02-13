package com.fmyd888.fengmao.module.information.enums.dingProcessCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类功能描述：钉钉审批模板业务类型枚举类
 * 每个模块index预留10个，如合同模块从1-10，调度模块从11-20，以此类推
 *
 * @author 小逺
 * @date 2024/06/07 23:18
 */
@Getter
@AllArgsConstructor
public enum DingProcessCodeEnum {
    //===============合同管理(1-10)====================
    MYHT(1, "贸易合同"),
    YSHT(2, "运输合同"),
    //============调度、费用管理(11-20)=================
    ZHBD(11,"装货磅单"),
    XHBD(12,"卸货磅单"),
    FYSP(13,"费用审批"),
    RYWCQ(14,"人员未出勤"),
    //============车辆、人员变更管理(21-30)=================
    RYBG(21,"人员变更"),
    CLCDBG(22,"车辆车队变更");


    private final Integer index;
    private final String description;
}
