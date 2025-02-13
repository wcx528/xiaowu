package com.fmyd888.fengmao.module.information.controller.admin.commoditysafetycard.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 安全告知卡 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CommoditySafetyCardRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24391")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "状态", example = "1")
    @ExcelProperty("状态")
    private Byte status;

    @Schema(description = "安全卡名称", example = "张三")
    @ExcelProperty("安全卡名称")
    private String name;

    @Schema(description = "危险性")
    @ExcelProperty("危险性")
    private String risk;

    @Schema(description = "储运要求")
    @ExcelProperty("储运要求")
    private String storageClaim;

    @Schema(description = "泄露处理")
    @ExcelProperty("泄露处理")
    private String leakDispose;

    @Schema(description = "急救措施", example = "7505")
    @ExcelProperty("急救措施")
    private String firstAid;

    @Schema(description = "灭火措施")
    @ExcelProperty("灭火措施")
    private String fire;

    @Schema(description = "防火措施")
    @ExcelProperty("防火措施")
    private String protection;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;


    //===================按需添加不映射数据表字段===========================
    @TableField(exist = false)
    private HashMap<String,Object> fileMaps;
}