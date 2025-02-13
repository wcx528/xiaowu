package com.fmyd888.fengmao.module.information.controller.admin.store.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author:wu
 * @create: 2024-08-31 09:54
 * @Description: 导入仓库模板
 */
@Data
public class DownStoreImportVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("使用组织")
    private String useOrganization;

    @ExcelProperty("仓库编码")
    private String storeCode;

    @ExcelProperty("仓库名称")
    private String storeName;

    @ExcelProperty("仓库类别")
    private String storeType;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("仓库地址")
    private String storeAddress;

}
