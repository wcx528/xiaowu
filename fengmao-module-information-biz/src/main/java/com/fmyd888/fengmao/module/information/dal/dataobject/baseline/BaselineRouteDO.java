package com.fmyd888.fengmao.module.information.dal.dataobject.baseline;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseSlaveDO;
import lombok.*;

/**
 * @Title: BaselineRoute
 * @Author: huanhuan
 * @Date: 2024-05-16
 * @Description:
 */
@TableName("fm_baseline_route")
@KeySequence("fm_baseline_route_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaselineRouteDO extends BaseSlaveDO {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 路线名称：
     * 存储选择的所有途经点的名称，每个途径点之间用分号隔开，格式如“地址1;地址2:地址3'
     *
     */
    private String routeName;
    /**
     * 途经点经纬度：
     *  存储选择的所有途经点经纬度坐标信息，每个途经点坐标之间用分号隔开，格式如“经度1,维度1;经度2,维度2;经度3,维度3'
     */
    private String longitudeLatitude;
    /**
     * 途经点信息：高德地图返回来的所有途经点信息
     */
    private String routeInfo;
    /**
     * 部门组织id
     */
    private Long deptId;
    /**
     * 装货地址ID
     */
    private Long loadingAddressId;
    /**
     * 卸货地址ID
     */
    private Long unloadingAddressId;

}
