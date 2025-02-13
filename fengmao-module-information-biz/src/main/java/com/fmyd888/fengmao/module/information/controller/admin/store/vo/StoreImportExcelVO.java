package com.fmyd888.fengmao.module.information.controller.admin.store.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Title: StoreImportExcelVO
 * @Author: huanhuan
 * @Date: 2023-11-29 15:04
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class StoreImportExcelVO {


    @ExcelProperty("仓库名称")
    private String storeName;

    @ExcelProperty("仓库")
    private String storeType;

    @ExcelProperty("备注")
    private String remark;
}

