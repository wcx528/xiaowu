package com.fmyd888.fengmao.module.information.controller.admin.locationRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 类功能描述：gps定位接口返回信息接收类
 *
 * @author 小逺
 * @date 2024/06/20
 */
@Data
public class LocationDTO {
    /**
     *返回状态
     */
    @JsonProperty("Ret")
    private Integer ret;

    /**
     * 返回信息
     */
    @JsonProperty("Msg")
    private String msg;

    /**
     * 位置信息
     */
    @JsonProperty("Data")
    private List<LocationContentDTO> data;

    @JsonProperty("Datal")
    private List<String> datal;

    /**
     * 类功能描述：位置信息
     *
     * @author 小逺
     * @date 2024/06/20
     */
    @Data
    public static class LocationContentDTO{
        /**
         * 车牌号
         */
        @JsonProperty("PlateNum")
        private String plateNum;

        @JsonProperty("ColorCode")
        private String colorCode;

        /**
         * 时间
         */
        @JsonProperty("GpsTime")
        private String gpsTime;

        /**
         * 总里程
         */
        @JsonProperty("Mileage")
        private BigDecimal mileage;

        /**
         * 速度
         */
        @JsonProperty("Speed")
        private BigDecimal speed;

        /**
         * 经度
         */
        @JsonProperty("Longitude")
        private BigDecimal longitude;

        /**
         * 纬度
         */
        @JsonProperty("Latitude")
        private BigDecimal latitude;

        /**
         * 方向
         */
        @JsonProperty("Direction")
        private BigDecimal direction;

        /**
         * 海拔
         */
        @JsonProperty("Altitude")
        private BigDecimal altitude;

        @JsonProperty("StateFlag")
        private String stateFlag;

        @JsonProperty("AlarmFlag")
        private String alarmFlag;

        /**
         * 地址名称
         */
        @JsonProperty("AddressInfo")
        private String addressInfo;
    }
}
