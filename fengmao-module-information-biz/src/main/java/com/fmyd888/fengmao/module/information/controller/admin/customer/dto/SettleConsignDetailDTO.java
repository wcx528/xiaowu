package com.fmyd888.fengmao.module.information.controller.admin.customer.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author:wu
 * @create: 2024-03-25 11:29
 * @Description: 结算方托运合同、承运合同
 */
@Data
public class SettleConsignDetailDTO {
    /**
     *运输公司承运合同编号id
     */
    private Long carriageAcceptContractId;
    /**
     * 结算方托运合同id
     */
    private Long consignContractId;
    /**
     * 结算方承运纸质合同号id
     */
    private Long acceptContractId;
    /**
     *二次结算方托运纸质合同号id
     */
    private Long secondConsignContractId;
    /**
     * 二次结算方承运纸质合同号id
     */
    private Long secondAcceptContractId;

    /**
     * 合同编号
     */
    private String contractNum;
    /**
     * 承运公司
     */
    private String forwardingCompanyName;
    /**
     * 托运公司
     */
    private String consignCompanyName;
    /**
     * 合同开始时间
     */
    private LocalDateTime startTime;
    /**
     * 合同结束时间
     */
    private LocalDateTime endTime;
    /**
     * 纸质合同号
     */
    private String paperContractNumber;


}
