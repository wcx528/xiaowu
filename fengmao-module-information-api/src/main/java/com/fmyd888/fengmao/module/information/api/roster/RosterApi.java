package com.fmyd888.fengmao.module.information.api.roster;

import com.fmyd888.fengmao.module.information.api.roster.dto.DingDepartmentDTO;
import com.fmyd888.fengmao.module.information.api.roster.dto.RosterDTO;

import java.util.List;

/**
 * 类功能描述：花名册表API
 *
 * @author 小逺
 * @date 2024/06/09 00:06
 */
public interface RosterApi {
    /**
     * 功能描述：根据id获取花名册信息
     *
     * @param id 花名册id
     * @return {@link RosterDTO }
     * @author 小逺
     * @date 2024/06/09 00:09
     */
    RosterDTO getRosterById(Long id);

    /**
     * 功能描述：根据钉钉userId获取花名册信息
     *
     * @param dingUserId
     * @return {@link RosterDTO }
     * @author 小逺
     * @date 2024/06/26
     */
    RosterDTO getRosterBydingUserId(String dingUserId);

    /**
     * 功能描述：获取所有花名册信息
     *
     * @return {@link RosterDTO }
     * @author 小逺
     * @date 2024/06/29 00:09
     */
    List<RosterDTO> getAllRoster();

    /**
     * 功能描述：获取所有钉钉部门
     *
     * @return {@link List }<{@link DingDepartmentDTO }>
     * @author 小逺
     * @date 2024/06/30
     */
    List<DingDepartmentDTO> getAllDepartment();
}
