package com.fmyd888.fengmao.module.information.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Title: CardPage
 * @Author: huanhuan
 * @Date: 2024-01-17 11:33
 * @description:
 *
 */
@Data
public class CardDetailPage<T> extends Page<T> {



    @Schema(description = "已完成")
    private Long completedContract1;

    @Schema(description = "剩余数量低于20吨")
    private Long qtyBelow20Tons2;

    @Schema(description = "到期")
    private Long expiration3;


    public CardDetailPage(long current, long size) {
        super(current, size);
    }
}
