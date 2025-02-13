package com.fmyd888.fengmao.module.information.controller.admin.measurement.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.DownStoreExcelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author:wu
 * @create: 2024-09-04 11:21
 * @Description: 计量单位导入数据/导出字段选择
 */
@Data
public class DownMeasurementExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("使用组织")
    private String useOrganization;

    @ExcelProperty("计量单位名称")
    private String name;

    @ExcelProperty(" 计量单位编码")
    private String code;

    @ExcelProperty("上级计量单位名称")
    private String parentName;

    @ExcelProperty("上级计量单位编码")
    private String parentCode;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("创建人")
    private String creator;

    @ExcelProperty("创建时间")
    private String createTime;

    @ExcelProperty("更新人")
    private String updater;

    @ExcelProperty("更新时间")
    private String updateTime;

    @ExcelProperty("备注")
    private String remarks;

    @Schema(description ="是否更新")
    private Boolean isUpdateSupport;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<DownStoreExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;

    @Schema(description = "需导出字段")
    private List<String> exportFileds;

}
