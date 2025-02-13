package com.fmyd888.fengmao.module.information.controller.admin.locationRecord.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 车辆GPS定位 Response VO")
@Data
@ExcelIgnoreUnannotated
public class LocationRecordRespVO {
    @Schema(description = "id标识")
    private Long id;

    @Schema(description = "车辆id", example = "7548")
    @ExcelProperty("车辆id")
    private Long carId;

    @Schema(description = "经度")
    @ExcelProperty("经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    @ExcelProperty("纬度")
    private BigDecimal latitude;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String address;

    @Schema(description = "总里程")
    @ExcelProperty("总里程")
    private BigDecimal totalMileage;

    @Schema(description = "车辆速度")
    @ExcelProperty("车辆速度")
    private BigDecimal speed;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;

    @Schema(description = "车辆名称")
    @ExcelProperty("车辆名称")
    private String carName;

    @Schema(description = "所属公司")
    @ExcelProperty("所属公司")
    private String companyName;
}