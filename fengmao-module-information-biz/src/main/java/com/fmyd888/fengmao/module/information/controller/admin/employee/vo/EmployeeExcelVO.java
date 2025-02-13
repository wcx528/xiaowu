package com.fmyd888.fengmao.module.information.controller.admin.employee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;


/**
 * 员工信息表 Excel VO
 *
 * @author 丰茂企业
 */
@Data
public class EmployeeExcelVO {

    @ExcelProperty("员工编号")
    private Long id;

    @ExcelProperty("员工名称")
    private String name;

    @ExcelProperty(value = "员工类型", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte type;

    @ExcelProperty(value = "员工状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty(value = "员工职位", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String description;

    @ExcelProperty("员工工号")
    private String employeeId;

    @ExcelProperty("员工邮箱")
    private String email;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("用户编号")
    private Long usersId;

}
