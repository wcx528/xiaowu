package com.fmyd888.fengmao.module.information.api.repairprojects;

import com.fmyd888.fengmao.module.information.api.repairprojects.dto.RepairProjectDTO;

import java.util.List;

/**
 * @author Misaka
 * date: 2024/8/12
 */
public interface RepairProjectApi {
    /**
     * 根据维修项目名称列表获取维修项目列表
     */
    List<RepairProjectDTO> getRepairProjectListByNames(List<String> names);
}
