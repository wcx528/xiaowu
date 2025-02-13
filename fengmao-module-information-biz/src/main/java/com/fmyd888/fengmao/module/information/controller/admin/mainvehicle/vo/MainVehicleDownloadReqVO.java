package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/12/08 14:26
 * @Version: 1.0
 * @Description:  要下载的车量档案信息对象
 */
@Data
@ToString
public class MainVehicleDownloadReqVO {
    @Schema(description = "车头id编号列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> mainVehicleIds;

    /**
     * 注意不要把车头的附件类型值 与车挂附件类型值搞混了
     */
    @Schema(description = "车头附件类型列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> mainVehicleFileTypes;
}
