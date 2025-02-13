package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @Title: TrailerKeywordPageReqVO
 * @Author: huanhuan
 * @Date: 2023-12-01 11:48
 * @description:
 */
@Schema(description = "管理后台 - 车挂档案关键字查询分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrailerKeywordPageReqVO extends PageParam {

    @Schema(description = "车牌号")
    private String vehicleTrailerNo;

    @Schema(description = "车身颜色")
    private String vehicleColor;

    @Schema(description = "罐体类型")
    private String tankType;

    @ExcelProperty("挂车品牌")
    private String trailerBrand;

    @Schema(description = "罐检报告日期开始时间-结束时间")
    @Size(min = 2,max = 2,message = "传入的createTime集合个数报错,必须传入两个")
    private List<String> bodyReporttime;

    @Schema(description = "车辆类型")
    private String vehicleType;

    @Schema(description = "部门组织名称")
    private String deptName;

    @Schema(description = "所属公司ID")
    private Long companyId;

    @Schema(description = "是否外援车")
    private Boolean isOut;

    @Schema(description = "挂车编码")
    private String trailerCode;

    @Schema(description = "发证日期，开始时间-结束时间")
    @Size(min = 2,max = 2,message = "传入的发证日期集合元素报错,必须传入两个")
    private List<String> certificatTime;

    @Schema(description = "车辆识别代号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleIdenCode;

    @Schema(description = "车辆型号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleMode;

    @Schema(description = "制造厂名")
    private String manufacturerName;

    @Schema(description = "轮胎数")
    private Integer tyrenumber;

    @Schema(description = "装备质量")
    private BigDecimal equipmentmass;

    @Schema(description = "总质量")
    private BigDecimal totalmass;

    @Schema(description = "核定载质量", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal verificationmass;

    @Schema(description = "外廓尺寸")
    private String outside;

    @Schema(description = "货箱内部尺寸")
    private String innerside;

    @Schema(description = "使用性质")
    private String useNature;

    @Schema(description = "车头编码")
    private String vehicleCode;


    @Schema(description = "运输证有效期，开始时间-结束时间")
    @Size(min = 2,max = 2,message = "传入的运输证有效期集合元素报错,必须传入两个")
    private List<String> transporttime;

    @Schema(description = "行驶证有效期，开始时间-结束时间")
    @Size(min = 2,max = 2,message = "传入的行驶证有效期集合元素报错,必须传入两个")
    private List<String> drivingDate;

    @Schema(description = "原价")
    private BigDecimal originalPrice;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "是否闲置")
    private Boolean isIdle;

    @Schema(description = "是否备用挂")
    private Boolean isStandbyTrailer;

    @Schema(description = "停放位置")
    private String parkingPosition;

    @Schema(description = "备用挂状态")
    private Integer standbyTrailerStatus;

    @Schema(description = "被更换车挂")
    private Long replacedTrailer;

    @Schema(description = "管道连接方式")
    private String pipeConnectionType;

    @Schema(description = "罐体容积")
    private String tankCapacity;

    @Schema(description = "卸货方式")
    private String unloadingType;

    @Schema(description = "车架号")
    private String vehicleFrame;

    @Schema(description = "可装货物")
    private List<Long> commodityIds;

    @Schema(description = "车挂自重")
    private BigDecimal trailerWeight;

    @Schema(description = "残值率")
    private BigDecimal residualRate;

}
