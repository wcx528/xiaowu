package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Title: TrailerDonwloadVO
 * @Author: huanhuan
 * @Date: 2023-12-11 9:57
 * @description:要下载的车量档案信息对象
 */
@Data
@ToString
public class TrailerDonwloadVO {
    @Schema(description = "车挂id编号列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> trailerIds;

    /**
     * 车挂的附件类型值
     */
    @Schema(description = "车挂附件类型列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> trailerFileTypes;
}
