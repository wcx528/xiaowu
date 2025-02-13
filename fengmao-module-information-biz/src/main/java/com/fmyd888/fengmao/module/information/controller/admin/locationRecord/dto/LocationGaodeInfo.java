package com.fmyd888.fengmao.module.information.controller.admin.locationRecord.dto;

import lombok.Data;

/**
 * 类功能描述：高德地图转换地址结果
 *
 * @author 小逺
 * @date 2024/06/21
 */
@Data
public class LocationGaodeInfo {
    public String status;
    public String info;
    public String infocode;
    public String count;
    public String locations;
}
