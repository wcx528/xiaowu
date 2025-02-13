package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleExcelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * 车挂档案 Excel VO
 *
 * @author 丰茂
 */
@Data
public class TrailerExcelVO {

    @ExcelProperty("车牌号")
    private String vehicleTrailerNo;

    @ExcelProperty("登记日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime certificatTime;

    @ExcelProperty(value = "车辆类型", converter = DictConvert.class)
    @DictFormat("vehicle_type")
    private String vehicleType;

    @ExcelProperty(value = "挂车品牌", converter = DictConvert.class)
    @DictFormat("vehicle_brand")
    private String vehicleBrand;

    @ExcelProperty("车辆识别代号")
    private String vehicleIdenCode;

    @ExcelProperty("车身颜色")
    private String vehicleColor;

    @ExcelProperty("车辆型号")
    private String vehicleMode;

    @ExcelProperty(value = "罐体类型", converter = DictConvert.class)
    @DictFormat("tank_type")
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
    private String userYears;

    @ExcelProperty("残值率")
    private BigDecimal residualRate;


    @ExcelProperty(value = "是否外援车", converter = DictConvert.class)
    @DictFormat("infra_boolean_string")  //暂定使用这个当做公共字典
    private Boolean isOut;

    @ExcelProperty("车挂编码")
    private String trailerCode;

    @ExcelProperty("车挂自重")
    private BigDecimal trailerWeight;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty(value = "部门组织", converter = DictConvert.class)
    @DictFormat("dept_dict") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Long deptId;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty("公司名称")
    private String deptName;

    @ExcelProperty(value = "是否闲置" , converter = DictConvert.class)
    @DictFormat("infra_boolean_string")
    private Boolean isIdle;

    @ExcelProperty("违章次数")
    private Byte violationCount;

    @ExcelProperty("注销日期")
    private LocalDateTime deactivationDate;

    @ExcelProperty("报废日期")
    private LocalDateTime scrapDate;

//    @ExcelProperty("审批实例")
    private String processId;

//    @ExcelProperty("审批实例地址")
    private String processUrl;

//    @ExcelProperty("审批时间")
    private LocalDateTime approvalTime;

//    @ExcelProperty("审批状态")
    private Byte approvalStatus;

    /**
     * 是否备用挂
     */
    private Boolean  isStandbyTrailer;
    /**
     * 停放位置
     */
    private String parkingPosition;
    /**
     * 备用挂状态
     */
    private Integer standbyTrailerStatus;
    /**
     * 被更换车挂
     */
    private Long  replacedTrailer;
    /**
     * 被更换车挂
     */
    private String  pipeConnectionType;
    /**
     * 罐体容积
     */
    private String  tankCapacity;
    /**
     * 卸货方式
     */
    private String  unloadingType;

    /**
     * 车架号
     */
    private String vehicleFrame;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<TrailerExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}