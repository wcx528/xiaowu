package com.fmyd888.fengmao.module.information.controller.admin.dingDepartment.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: DeptNodeDto
 * @Author: huanhuan
 * @Date: 2024-03-28 20:41
 * @Description:
 */
@Data
public class DeptNodeDto {
    private String name; // 部门名称
    private Long deptId; // 部门ID
    private List<DeptNodeDto> children; // 子部门列表
    // 构造方法
    public DeptNodeDto() {
        deptId = 1L;
        this.children = new ArrayList<>();
    }
}
