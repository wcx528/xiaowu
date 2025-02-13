package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;

/**
* 车辆档案 Excel导入预览结果 VO
*
* @author 丰茂
*/
@Schema(description = "管理后台 - 车辆档案 Excel 导入 VO，用于返回导入预览结果")
@Data
public class CarImportPreviewRespVO {

    @ExcelProperty("副驾驶")
    private Long deputyId;

    @ExcelProperty("押运员")
    private Long escortId;

    @ExcelProperty("副驾驶手机号")
    private String deputyPhone;

    @ExcelProperty("押运员手机号")
    private String escortPhone;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("运输货物")
    private String commodityId;

    @ExcelProperty("可装载吨位")
    private BigDecimal ableTonnage;

    @ExcelProperty("实际装载吨位")
    private BigDecimal actualTonnage;

    @ExcelProperty(value = "车辆普危类型", converter = DictConvert.class)
    @DictFormat("god_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte godType;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty("主车号")
    private String mainVehicleId;

    @ExcelProperty("车挂号")
    private String trailerId;

    @ExcelProperty("车队长")
    private Long captainId;

    @ExcelProperty("主驾驶")
    private Long mainId;

    @ExcelProperty("车队长手机号")
    private String captainPhone;

    @ExcelProperty("主驾驶手机号")
    private String mainPhone;

    @ExcelProperty(value = "车队", converter = DictConvert.class)
    @DictFormat("fleet_id") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Long fleetId;

    @ExcelProperty("标识该车辆的维修申请权限是否转交给了副驾/押运员")
    private Boolean isTurnRepair;

    @ExcelProperty(value = "部门组织id", converter = DictConvert.class)
    @DictFormat("dept_dict") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Long deptId;


    @ExcelProperty("是否存在错误")
    private Boolean hasError = false;

    @Schema(description = "导入失败的字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failData;
}
