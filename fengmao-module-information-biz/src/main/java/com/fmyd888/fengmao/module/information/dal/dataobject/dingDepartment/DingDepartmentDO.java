package com.fmyd888.fengmao.module.information.dal.dataobject.dingDepartment;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Title: DingDepartment01
 * @Author: huanhuan
 * @Date: 2024-04-05
 * @Description:
 *  钉钉部门表实体
 */
@TableName("fm_ding_department")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DingDepartmentDO extends BaseDO {
    /**
     * 部门ID
     */
    @TableId
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
     * 部门主要负责人ID
     */
    private String managerId;

    /**
     * 部门级次
     */
    private Integer grade;

    /**
     * 成立日期
     */
    private LocalDateTime departmentBeginDate;
    /**
     * 撤销日期
     */
    private LocalDateTime departmentEndDate;

    /**
     * 备注
     */
    private String remark;
    /**
     * 部门状态
     */
    private Long status;

}
