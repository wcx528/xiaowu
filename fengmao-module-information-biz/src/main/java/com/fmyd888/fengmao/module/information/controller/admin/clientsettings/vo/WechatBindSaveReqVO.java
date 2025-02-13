package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 绑定微信用户新增/修改 Request VO")
@Data
public class WechatBindSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4644")
    private Long id;

    @Schema(description = "部门id", example = "4060")
    private Long deptId;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "微信用户id", example = "18225")
    private String openId;

    @Schema(description = "昵称", example = "王五")
    private String nickname;

    @Schema(description = "用户名称", example = "张三")
    private String name;

    @Schema(description = "用户性别（1.男，2.女）")
    private Integer sex;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "语言")
    private String language;

    @Schema(description = "头像", example = "https://www.iocoder.cn")
    private String headImgUrl;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "来源（1.公众号，2.小程序）", example = "2")
    private Integer sourceType;


    @Schema(description = "客户信息id", example = "2")
    private List<Long> customerIds;
}