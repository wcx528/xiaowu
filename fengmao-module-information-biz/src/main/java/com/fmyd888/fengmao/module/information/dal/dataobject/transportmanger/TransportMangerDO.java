package com.fmyd888.fengmao.module.information.dal.dataobject.transportmanger;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 运输证管理 DO
 *
 * @author 丰茂
 */
@TableName("fm_transport_manger")
@KeySequence("fm_transport_manger_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportMangerDO extends TenantBaseDO {

    /**
     * 标识
     */
    @TableId
    private Long id;

    /**
     * 运输证编码
     */
    private String transportCode;

    /**
     * 上游购买证编号
     */
    private Long upstreamPurchaseId;
    /**
     * 下游购买证编号
     */
    private Long downstreamPurchaseId;
    /**
     * 申请单位
     */
    private Long applicantId;
    /**
     * 承运单位
     */
    private Long carrierId;
    /**
     * 装货厂家
     */
    private Long loadFactoryId;
    /**
     * 卸货厂家
     */
    private Long unloadFactoryId;
    /**
     * 介质id
     */
    private Long commodityId;
    /**
     * 运输证数量
     */
    private BigDecimal transportTonnage;
    /**
     * 运输证开始时间
     */
    private LocalDateTime transportSdate;
    /**
     * 运输证结束时间
     */
    private LocalDateTime transportEdae;
    /**
     * 部门组织id
     */
    private Long deptId;
    /**
     * 状态
     * <p>
     * 枚举 {@link TODO certificate_status 对应的类}
     */
    private Byte status;
    /**
     * 车号
     */
    //private Long carId;
    /**
     * 是否同城运
     * 输
     */
    private Boolean localTransportIs;

    /**
     * 所属公司id
     */
    private Long companyId;



    //===================按需添加不映射数据表字段===========================
    /**
     * 组织名称
     */
    @TableField(exist = false)
    private String deptName;
}