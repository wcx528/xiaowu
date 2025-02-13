package com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import lombok.experimental.Accessors;


/**
 * 税率 Excel VO
 *
 * @author 丰茂企业
 */
@Data
@Accessors(chain = false)
public class TaxratesExcelVO {
    @ExcelProperty("id标识")
    private Long id;

    @ExcelProperty("所属公司id")
    private Long companyId;

    @ExcelProperty("所属公司")
    private String companyName;

    @ExcelProperty("税率名称")
    private String taxName;

    @ExcelProperty("税率")
    private BigDecimal taxRate;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("税率编码")
    private String taxCode;

    @ExcelProperty("使用组织")
    private String organization;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表，实际导入接口用到", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<TaxratesExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}
