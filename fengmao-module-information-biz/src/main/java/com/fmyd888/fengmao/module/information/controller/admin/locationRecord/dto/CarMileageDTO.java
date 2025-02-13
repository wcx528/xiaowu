package com.fmyd888.fengmao.module.information.controller.admin.locationRecord.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 类功能描述：车辆里程接口返回数据
 *
 * @author 小逺
 * @date 2024/07/24
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarMileageDTO {
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
     * 里程信息
     */
    @JsonProperty("Data")
    private List<MileAgeContentDTO> data;

//    @JsonProperty("Datal")
//    private List<MileAgeContentDTO> datal;

    /**
     * 类功能描述：里程信息
     *
     * @author 小逺
     * @date 2024/07/24
     */
    @Data
    public static class MileAgeContentDTO {

        /**
         * 车牌号
         */
        @JsonProperty("PlateNum")
        private String plateNum;

        /**
         * 里程
         */
        @JsonProperty("Mileage")
        private BigDecimal mileage;

        /**
         * 日期
         */
        @JsonProperty("Date")
        private String date;

        @JsonProperty("ColorCode")
        private String colorCode;
    }
}
