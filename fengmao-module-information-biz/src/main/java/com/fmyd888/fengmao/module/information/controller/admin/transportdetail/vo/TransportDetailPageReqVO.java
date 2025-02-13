package com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo;

import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "管理后台 - 运输证明细分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransportDetailPageReqVO extends PageParam {


    @Schema(description = "装货厂家")
    private Long loadFactoryId;

    @Schema(description = "卸货厂家")
    private Long unloadFactoryId;

    @Schema(description = "车牌号")
    private Long mainVehicleId;

    @Schema(description = "运输证号")
    private String transportCode;

    @Schema(description = "所属公司")
    private Long deptId;

    @Schema(description = "车头id")
    private Long carId;


    @Schema(description = "开始时间-结束时间")
    @Size(min = 2,max = 2,message = "传入的createTime集合个数报错,必须传入两个")
    private List<String> queryDate;

    @Schema(description = "其他查询值")
    private Integer status;

}