package com.fmyd888.fengmao.module.information.controller.admin.commoditysafetycard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* 安全告知卡 Excel VO
*
* @author 巫晨旭
*/
@Data
@ExcelIgnoreUnannotated
public class CommoditySafetyCardExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("状态")
    private Byte status;

    @ExcelProperty("安全卡名称")
    private String name;

    @ExcelProperty("危险性")
    private String risk;

    @ExcelProperty("储运要求")
    private String storageClaim;

    @ExcelProperty("泄露处理")
    private String leakDispose;

    @ExcelProperty("急救措施")
    private String firstAid;

    @ExcelProperty("灭火措施")
    private String fire;

    @ExcelProperty("防火措施")
    private String protection;

    @ExcelProperty("备注")
    private String remark;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<CommoditySafetyCardExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}