package com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @author:wu
 * @create: 2024-05-20 16:04
 * @Description:
 */
@Data
public class RiskInspectionItemDetailsDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 主表ID
     */
    private Long entityId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目分数
     */
    private Integer itemScore;
    /**
     * 是否上传图片
     */
    private Boolean isUploadPictures;
}
