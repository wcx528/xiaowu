package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import com.fmyd888.fengmao.module.information.enums.vehicle.VehicleMainFileEnum;
import com.fmyd888.fengmao.module.information.enums.vehicle.VehicleTrailerFileEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 车头历史附件对象
 */
@Data
public class MainVehicleHistoryVO {


    @Schema(description = "车头id编号列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sourceId;

    /**
     * 对象中 的key参考 {@link VehicleMainFileEnum}
     */
    @Schema(description = "附件类型对应的集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String,List<FileInfo>> fileInfo;

    @Data
    public static class FileInfo {
        @Schema(description = "历史图片名", requiredMode = Schema.RequiredMode.REQUIRED)
        private String name;

        @Schema(description = "路径", requiredMode = Schema.RequiredMode.REQUIRED)
        private String url;

        @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDateTime createTime;

        @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED)
        private String codeBusinessType;
    }
}
