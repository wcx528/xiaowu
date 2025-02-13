package com.fmyd888.fengmao.module.information.controller.admin.container.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: lmy
 * @Date: 2023/11/29 14:57
 * @Version: 1.0
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ContainerImportExcelVO {

    @ExcelProperty("集装箱号")
    private String containerNumber;

    @ExcelProperty("集装箱所属公司组织")
    private String deptName;

    @ExcelProperty("集装箱备注")
    private String remark;
}
