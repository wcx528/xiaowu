package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author:wu
 * @create: 2024-04-28 19:55
 * @Description: 客户端设置分页查询
 */
@Data
public class ClientSettingsPageDTO {
    /**
     * 客户名称
     */
    private String customerName;
    /**
     *客户分组(是否外援)
     */
    private Boolean customerGroup;
    /**
     * 外援密码
     */
    private String passOutsourceCarrier;
    /**
     * 车辆维修商(是、否)
     */
    private Boolean vehicleRepairer;
    /**
     *维修密码
     */
    private String vehicleRepairerId;
    /**
     * 微信用户
     */
    private String wechatName;
    /**
     * 外援车头号
     */
    private String mainVehicleIdentifier;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 备注
     */
    private String remark;
}
