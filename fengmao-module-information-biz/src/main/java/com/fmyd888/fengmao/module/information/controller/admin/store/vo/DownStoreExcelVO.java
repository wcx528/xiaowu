package com.fmyd888.fengmao.module.information.controller.admin.store.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.DownTrailerExcelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * @author:wu
 * @create: 2024-08-31 09:41
 * @Description: 仓库导入数据/导出字段选择
 */
@Data
@Accessors(chain = false)
public class DownStoreExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("使用组织")
    private String useOrganization;

    @ExcelProperty("仓库编码")
    private String storeCode;

    @ExcelProperty("仓库名称")
    private String storeName;

    @ExcelProperty(value = "仓库类别",converter = DictConvert.class)
    @DictFormat("fm_store_type")
    private String storeType;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("仓库地址")
    private String storeAddress;

    @ExcelProperty("创建人")
    private String creator;

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
