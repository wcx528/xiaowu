package com.fmyd888.fengmao.module.information.common.vo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: CardPage
 * @Author: huanhuan
 * @Date: 2024-01-17 11:33
 * @description:
 */
@Data
@NoArgsConstructor
public class CarCyclePage<T> extends Page<T> {

    @Schema(description = "使用中")
    private Integer inUse;

    @Schema(description = "空闲")
    private Integer free;

    @Schema(description = "注销")
    private Integer deactivation;

    @Schema(description = "报废")
    private Integer scrap;


    public CarCyclePage(long current, long size) {
        super(current, size);
    }
}
