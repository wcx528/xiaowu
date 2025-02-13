package com.fmyd888.fengmao.module.information.controller.admin.employee.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import com.fmyd888.fengmao.module.system.enums.DictTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName EmployeeImportExcelVO
 * @Description ToDo
 * @Author 巫晨旭
 * @Date 2023/11/17 10:43
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) //设置chain = false，避免用户导入有问题
public class EmployeeImportExcelVO {
    @ExcelProperty("员工名称")
    private String name;

//    @ExcelProperty("员工状态")
//    private String status;


    @ExcelProperty("员工工号")
    private String employeeId;


    @ExcelProperty("员工邮箱")
    private String email;



//    @ExcelProperty(value = "员工状态", converter = DictConvert.class)
//    @DictFormat(DictTypeConstants.COMMON_STATUS)
//    private Integer status;

    @ExcelProperty(value = "员工职位", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private String Description;


}
