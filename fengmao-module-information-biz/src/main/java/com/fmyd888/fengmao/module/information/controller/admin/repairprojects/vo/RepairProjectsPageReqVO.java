package com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 维修项目分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RepairProjectsPageReqVO extends PageParam {

    @Schema(description = "所属公司id", example = "2927")
    private Long companyId;

    @Schema(description = "维修类型", example = "2")
    private Integer repairType;

    @Schema(description = "维修主体")
    private Integer repairSubject;

    @Schema(description = "费用类型", example = "2")
    private Integer costType;

    @Schema(description = "维修种类")
    private Integer maintainType;

    @Schema(description = "维修项目名称", example = "张三")
    private String repairItemName;


//    @Schema(description = "创建者id", example = "2")
//    private String creator;






    @Schema(description = "材料图片", example = "")
    private String imgUrl;

}