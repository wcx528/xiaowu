package com.fmyd888.fengmao.module.information.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Title: CardPage
 * @Author: huanhuan
 * @Date: 2024-01-17 11:33
 * @description:
 */
@Data
public class CardPage<T> extends Page<T> {

    @Schema(description = "进行中")
    private Long inProgressContract1;

    @Schema(description = "已完成")
    private Long completedContract2;

    @Schema(description = "证件到期提醒")
    private Long expirationReminderContract3;


    public CardPage(long current, long size) {
        super(current, size);
    }
}
