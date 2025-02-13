package com.fmyd888.fengmao.module.information.controller.admin.salesman.vo;

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
 * @ClassName SalesmanImportExcelVO
 * @Description ToDo
 * @Author 巫晨旭
 * @Date 2023/12/4 15:34
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) //设置chain = false，避免用户导入有问题
public class SalesmanImportExcelVO {


    @ExcelProperty("业务员名称")
    private String username;

    @ExcelProperty("业务员岗位")
    private Long positionId;

    @ExcelProperty("业务员岗位")
    private String positionName;
    @ExcelProperty("业务员对应userId")
    private Long userId;

    @ExcelProperty(value = "业务员类型", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private String salesmanType;

}
