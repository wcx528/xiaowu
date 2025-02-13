package com.fmyd888.fengmao.module.information.api.roster.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述：钉钉部门DTO
 *
 * @author 小逺
 * @date 2024/06/30
 */
@Data
public class DingDepartmentDTO {
    /**
     * 部门ID
     */
    private Long id;
    /**
     * 部门deptIdId
     */
    private Long deptId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门编码
     */
    private String departmentCode;
    /**
     * 上级部门ID
     */
    private Long parentDepartmentId;

    /**
     * 子部门列表
     */
    private List<DingDepartmentDTO> children = new ArrayList<>();
}
