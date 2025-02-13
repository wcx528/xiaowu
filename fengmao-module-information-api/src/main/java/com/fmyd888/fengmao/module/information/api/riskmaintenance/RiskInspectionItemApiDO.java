package com.fmyd888.fengmao.module.information.api.riskmaintenance;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 检查类型表(子表) DO
 *
 * @author 丰茂
 */
@TableName("fm_risk_inspection_item")
@KeySequence("fm_risk_inspection_item_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskInspectionItemApiDO extends TenantBaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 状态
     */
    private Byte status;
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

    /**
     * 隐患附件id
     */
    private List<Long> dangerFileId;
    /**
     * 隐患详情描述
     */
    private String remark;

}