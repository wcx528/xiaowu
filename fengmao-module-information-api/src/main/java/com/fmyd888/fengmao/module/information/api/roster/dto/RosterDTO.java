package com.fmyd888.fengmao.module.information.api.roster.dto;

import lombok.Data;

/**
 * 类功能描述：花名册DTO
 *
 * @author 小逺
 * @date 2024/06/09 00:07
 */
@Data
public class RosterDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 主部门id
     */
    private String mainDeptId;

    /**
     * 主部门
     */
    private String mainDept;

    /**
     * 职位
     */
    private String position;

    /**
     * label
     * 性别
     */
    private String sexType;

    /**
     *邮件
     */
    private String email;
}
