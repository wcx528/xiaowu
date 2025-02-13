package com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author:wu
 * @create: 2024-04-25 15:49
 * @Description:
 */
//@TableName("fm_loading_rate")
//@KeySequence("fm_loading_rate_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
public class LoadingRateDTO {

//    /**
//     * id
//     */
//    private Long id;
//    /**
//     * 系数维护表id
//     */
//    private Long coefficientId;
    /**
     * 介质id
     */
    private Long commodityId;
    /**
     * 介质名称
     */
    private String commodityName;
    /**
     * 标准装载率
     */
    private Integer standardizedStowage;
}
