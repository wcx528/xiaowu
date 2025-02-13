package com.fmyd888.fengmao.module.information.dal.dataobject.roster;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;
import lombok.Builder;
import lombok.Data;

/**
 * @Title: RosterDepartmentDO
 * @Author: huanhuan
 * @Date: 2024-04-05
 * @Description:
 *  员工花名册与部门组织中间表
 */
@Data
@Builder
@TableName("fm_roster_department")
public class RosterDepartmentDO extends BaseDO {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 可以存储本地数据库部门表中的id或者deptId字段
     *  建议使用deptId,都是唯一的值
     */
    private Long deptId ;
    /**
     * 可以存储本地数据库员工花名册表中的id或者userId字段
     *  建议使用userId
     */
    private String userId ;

    /**
     * 相同的员工在不同部门之前可能不同的职位
     */
    //private String position ;
    /**
     * 状态
     */
    private Byte status;

}
