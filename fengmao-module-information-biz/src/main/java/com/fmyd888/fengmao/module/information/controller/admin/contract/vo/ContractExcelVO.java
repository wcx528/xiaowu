package com.fmyd888.fengmao.module.information.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;


/**
 * 其他合同资料 Excel VO
 *
 * @author 丰茂
 */
@Data
public class ContractExcelVO {

    @ExcelProperty("合同编号")
    private Long id;

    @ExcelProperty(value = "合同类型编码", converter = DictConvert.class)
    @DictFormat("encoding_rules") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String contractCoding;

    @ExcelProperty(value = "合同类型名称", converter = DictConvert.class)
    @DictFormat("contract_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String contractType;

    @ExcelProperty(value = "我方主体类型", converter = DictConvert.class)
    @DictFormat("contract_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String principalType;

    @ExcelProperty("所属公司")
    private String corporation;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
