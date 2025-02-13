package com.fmyd888.fengmao.module.information.api.salesman.dto;

import lombok.Data;

/**
 * 类功能描述：业务员DTO
 *
 * @author 小逺
 * @date 2024/05/09
 */
@Data
public class SalesmanDTO {

    /**
     * 业务员id
     */
    private Long id;
    /**
     * 业务员编码
     */
    private String salesmanCode;
    /**
     * 业务员名称
     */
    private String username;
    /**
     * 业务员类型
     */
    private String salesmanType;
    /**
     * 岗位
     */
    private Long positionId;
    /**
     * 描述
     */
    private String describe;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 用户id
     */
    private Long userId;
}
