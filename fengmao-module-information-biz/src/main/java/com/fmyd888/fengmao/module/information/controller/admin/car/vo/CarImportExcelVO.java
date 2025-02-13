package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import lombok.experimental.Accessors;

/**
* 车辆档案 Excel导入(预览、下载) VO
*
* @author 丰茂
*/
@Schema(description = "管理后台 - 车辆档案 Excel 导入 VO，用于接收excel的信息以及是生成导入模板的依据")
@Data
@Accessors(chain = false)
public class CarImportExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty(value = "车辆普危类型", converter = DictConvert.class)
    @DictFormat("god_type")
    private String godType;

    @ExcelProperty("主车号")
    private String mainVehicleName;

    @ExcelProperty("车挂号")
    private String trailerName;

    @ExcelProperty("运输介质")
    private String commodityNames;

    @ExcelProperty("所属公司")
    private String companyName;

    @ExcelProperty("车队")
    private String fleetName;

    @ExcelProperty("车队长")
    private String captainName;

    @ExcelProperty("车队长手机号")
    private String captainPhone;

    @ExcelProperty("主驾驶")
    private String mainName;

    @ExcelProperty("主驾驶手机号")
    private String mainPhone;

    @ExcelProperty("副驾驶")
    private String deputyName;

    @ExcelProperty("副驾驶手机号")
    private String deputyPhone;

    @ExcelProperty("押运员")
    private String escortName;

    @ExcelProperty("押运员手机号")
    private String escortPhone;

    @Schema(description ="是否更新")
    private Boolean isUpdateSupport;

    /**
     * 主车号id
     */
    @Schema(description = "主车号id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long mainVehicleId;
    /**
     * 车挂号id
     */
    @Schema(description = "车挂号id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long trailerId;

    @Schema(description ="运输介质", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String commodityIds;
    /**
     * 所属公司id
     */
    @Schema(description = "所属公司id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long companyId;
    /**
     * 车队id
     */
    @Schema(description = "车队id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long fleetId;
    /**
     * 车队长id
     */
    @Schema(description = "车队长id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long captainId;
    /**
     * 主驾驶id
     */
    @Schema(description = "主驾驶id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long mainId;
    /**
     * 副驾驶id
     */
    @Schema(description = "副驾驶id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long deputyId;
    /**
     * 押运员id
     */
    @Schema(description = "押运员id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long escortId;





    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表，实际导入接口用到", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<CarImportExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;

//    @Schema(description ="导入的数据id",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
//    private CarImportDTO carImportDTOS;

}
