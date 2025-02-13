package com.fmyd888.fengmao.module.information.controller.admin.address.vo;

import lombok.Data;

/**
 * @Author: lmy
 * @Date: 2024/01/05 17:47
 * @Version: 1.0
 * @Description:
 */
@Data
public class AddressGaoDeDistrictVO {
    /**
     * 返回结果状态值,值为0或1，0表示失败；1表示成功
     */
    private String statue;
    /**
     * 返回状态说明,返回状态说明，status为0时，info返回错误原因，否则返回“OK”
     */
    private String info;
    /**
     * 状态码  返回状态说明，10000代表正确，详情参阅info状态表
     */
    private String infocode;
}
