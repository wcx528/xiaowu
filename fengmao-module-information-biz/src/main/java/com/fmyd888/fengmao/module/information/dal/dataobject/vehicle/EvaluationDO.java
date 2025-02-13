package com.fmyd888.fengmao.module.information.dal.dataobject.vehicle;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 检测评定登记 DO
 *
 * @author 丰茂
 */
@TableName("fm_evaluation")
@KeySequence("fm_evaluation_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDO extends TenantBaseDO {

    /**
     * 编码
     */
    @TableId
    private Long id;
    /**
     * 关联车头id
     */
    private Long mainVehicleId;
    /**
     * 关联车挂号
     */
    private Long trailerId;

    /**
     * 评定类型
     */
    private String testType;

    /**
     * 评定单位
     */
    private String testUnit;
    /**
     * 评定日期
     */
    private LocalDateTime testDate;
    /**
     * 有效日期
     */
    private LocalDateTime effectiveDate;
    /**
     * 报告编号
     */
    private String reportCode;
    /**
     * 备注
     */
    private String remark;

}
