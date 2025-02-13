package com.fmyd888.fengmao.module.information.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;


/**
 * 客户信息管理
 Excel VO
 *
 * @author 丰茂企业
 */
@Data
public class CustomerExcelVO {
    private Long id;

    @ExcelProperty("客户编码")
    private String customerCode;

    @ExcelProperty("客户名称")
    private String customerName;

    @ExcelProperty(value = "客户类型", converter = DictConvert.class)
    @DictFormat("customer_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer customerType;
    //private CustomerTypeEnum customerType;

    @ExcelProperty("LOGO 路径")
    private String logo;

    @ExcelProperty("联系地址")
    private String contactAddress;

    @ExcelProperty("联系人")
    private String contactPerson;

    @ExcelProperty("联系电话")
    private String contactPhone;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("客户状态")
    private Byte status;

    @ExcelProperty(value = "客户分组", converter = DictConvert.class)
    @DictFormat("customer_group") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    //private CustomerGroupEnum customerGroup;
    private Long customerGroup;

    @ExcelProperty("法定代表人")
    private String legalRepresentative;

    @ExcelProperty("组织")
    private String organization;

}
