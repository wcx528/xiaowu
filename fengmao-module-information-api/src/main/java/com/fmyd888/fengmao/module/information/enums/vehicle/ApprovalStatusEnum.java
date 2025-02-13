package com.fmyd888.fengmao.module.information.enums.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Title: ApprovalStatusEnum
 * @Author: huanhuan
 * @Date: 2024-04-09 10:29
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum ApprovalStatusEnum {
    //
    PENDING(0, "待审批"),
    APPROVED(1, "审批通过"),
    CANCELED(2, "撤销"),
    REJECTED(3, "审批拒绝"),
    OTHER(-1, "其他");

    private final Integer code;
    private final String description;
}
