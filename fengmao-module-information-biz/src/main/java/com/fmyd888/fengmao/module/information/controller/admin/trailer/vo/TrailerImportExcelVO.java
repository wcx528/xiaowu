package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleExcelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Title: TrailerImportExcelVO
 * @Author: huanhuan
 * @Date: 2023-12-11 17:34
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class TrailerImportExcelVO {
    //该字段不会被导出。在导入时，该值也会被忽略
    @ExcelIgnore
    private Long deptId;

    @ExcelProperty("公司名称")
    private String deptName;

    @ExcelProperty("车牌号")
    private String vehicleTrailerNo;

    @ExcelProperty("登记日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime certificatTime;

    @ExcelProperty("车辆类型")
    private String vehicleType;

    @ExcelProperty("挂车品牌")
    private String trailerBrand;

    @ExcelProperty("车辆识别代号")
    private String vehicleIdenCode;

    @ExcelProperty("车身颜色")
    private String vehicleColor;

    @ExcelProperty("车辆型号")
    private String vehicleMode;

    @ExcelProperty("罐体类型")
    private String tankType;

    @ExcelProperty("制造厂名称")
    private String manufacturerName;

    @ExcelProperty("轮胎数")
    private Integer tyrenumber;

    @ExcelProperty("装备质量")
    private BigDecimal equipmentmass;

    @ExcelProperty("总质量")
    private BigDecimal totalmass;

    @ExcelProperty("核定载质量")
    private BigDecimal verificationmass;

    @ExcelProperty("外廓尺寸")
    private String outside;

    @ExcelProperty("货箱内部尺寸")
    private String innerside;

    @ExcelProperty("罐检报告日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bodyReporttime;

    @ExcelProperty("使用性质")
    private String useNature;

    @ExcelProperty("运输证有效期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transporttime;

    @ExcelProperty("行驶证有效期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime drivingDate;

    @ExcelProperty("原价")
    private BigDecimal originalPrice;

    @ExcelProperty("使用年限")
    private Integer userYears;

    @ExcelProperty("残值率")
    private Integer residualRate;

    @ExcelProperty("是否闲置")
    private Boolean isFree;

    @ExcelProperty("是否外援车")
    private Boolean isOut;

    @ExcelProperty("状态（注销、报废）")
    private Byte status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty("是否闲置")
    private Boolean isIdle;

    @ExcelProperty("违章次数")
    private Byte violationCount;

    @ExcelProperty("注销日期")
    private LocalDateTime deactivationDate;

    @ExcelProperty("报废日期")
    private LocalDateTime scrapDate;

    @ExcelProperty("审批实例")
    private Long processId;

    @ExcelProperty("审批实例地址")
    private String processUrl;

    @ExcelProperty("审批时间")
    private LocalDateTime approvalTime;

    @ExcelProperty("审批状态")
    private Byte approvalStatus;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<TrailerExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}
