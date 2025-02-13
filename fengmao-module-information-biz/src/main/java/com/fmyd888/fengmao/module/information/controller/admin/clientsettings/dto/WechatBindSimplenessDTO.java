package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto;

import lombok.Data;

/**
 * @author:wu
 * @create: 2024-04-30 11:34
 * @Description: 微信用户精简DTO
 */
@Data
public class WechatBindSimplenessDTO {
    /**
     * id
     */
    private Long id;
    /**
     *微信昵称
     */
    private String nickname;
}
