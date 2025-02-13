package com.fmyd888.fengmao.module.information.controller.admin.container.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 集装箱创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContainerCreateReqVO extends ContainerBaseVO {

}
