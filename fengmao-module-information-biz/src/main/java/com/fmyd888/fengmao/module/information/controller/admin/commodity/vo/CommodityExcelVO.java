package com.fmyd888.fengmao.module.information.controller.admin.commodity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;


/**
 * 货物管理表 Excel VO
 *
 * @author 丰茂企业
 */
@Data
public class CommodityExcelVO {

    @ExcelProperty("货物信息表id")
    private Long id;


    @ExcelProperty("货物编码")
    private String code;

    @ExcelProperty(value = "货物类别", converter = DictConvert.class)
    @DictFormat("goods_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte category;

    @ExcelProperty("货物名称")
    private String name;

    @ExcelProperty("货物规格")
    private String specification;

    @ExcelProperty("父id")
    private Long parentId;

    @ExcelProperty("单位id")
    private Long measurementId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("备注")
    private String remarks;

    @ExcelProperty(value = "货物状态", converter = DictConvert.class)
    @DictFormat("commont_status")
    private Byte status;

    @ExcelProperty("使用组织")
    private String organization;

    @ExcelProperty("运输对应")
    private String parentCommodityName;

    @ExcelProperty("计量单位")
    private String measurementName;

}
