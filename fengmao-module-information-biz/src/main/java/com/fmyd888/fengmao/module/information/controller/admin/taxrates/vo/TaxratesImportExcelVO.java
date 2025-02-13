package com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author: lmy
 * @Date: 2023/11/29 11:03
 * @Version: 1.0
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class TaxratesImportExcelVO {

    //@ExcelProperty("税率编码")  //默认生成
    //private String taxCode;

    @ExcelProperty("税率名称")
    private String taxName;

    @ExcelProperty("税率")
    private BigDecimal taxRate;

    @ExcelProperty("备注")
    private String remark;
}
