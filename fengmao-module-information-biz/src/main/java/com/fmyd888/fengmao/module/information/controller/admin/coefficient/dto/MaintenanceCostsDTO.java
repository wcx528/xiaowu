package com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author:wu
 * @create: 2024-04-25 15:49
 * @Description:
 */
//@TableName("fm_maintenance_costs")
//@KeySequence("fm_maintenance_costs_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
public class MaintenanceCostsDTO {
//    /**
//     * id
//     */
//    private Long id;
//    /**
//     * 系数维护表id
//     */
//    private Long coefficientId;
    /**
     * 维修种类
     */
    private Long maintainType;
    /**
     * 每公里系数
     */
    private Integer kilometreCoefficient;
    /**
     * 奖励系数(项目占比)
     */
    private BigDecimal awardProject;
    /**
     * 考核系数(项目占比)
     */
    private BigDecimal assessProject;
}
