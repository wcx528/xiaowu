package com.fmyd888.fengmao.module.information.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author:wu
 * @create: 2024-06-13 19:17
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum CarPersonReplaceEnum {

    WCL(0, "未处理"),
    APPROVED(1, "审批通过"),
    REJECTED(2, "审批拒绝"),
    CANCELED(3, "撤销");

    private final Integer code;
    private final String description;
}
