package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 绑定微信用户 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WechatBindRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4644")
    @ExcelProperty("id")
    private Long id;


    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "昵称", example = "王五")
    @ExcelProperty("昵称")
    private String nickname;

    @Schema(description = "用户名称", example = "张三")
    @ExcelProperty("用户名称")
    private String name;

    @Schema(description = "用户性别（1.男，2.女）")
    @ExcelProperty("用户性别（1.男，2.女）")
    private Integer sex;



    @Schema(description = "头像", example = "https://www.iocoder.cn")
    @ExcelProperty("头像")
    private String headImgUrl;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;



    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;

    @Schema(description = "客户供应商名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("客户供应商名称")
    private String customerName;
}