package com.fmyd888.fengmao.module.information.api.repairprojects;

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.repairprojects.dto.RepairProjectDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.repairprojects.RepairProjectsDO;
import com.fmyd888.fengmao.module.information.service.repairprojects.RepairProjectsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 维修项目接口实现类
 * @author Misaka
 * date: 2024/8/12
 */
@Service
public class RepairProjectApiImpl implements RepairProjectApi{
    @Resource
    private RepairProjectsService repairProjectsService;

    @Override
    public List<RepairProjectDTO> getRepairProjectListByNames(List<String> names) {
        List<RepairProjectsDO> repairProjectsDOList = repairProjectsService.getRepairProjectListByNames(names);
        return BeanUtils.toBean(repairProjectsDOList, RepairProjectDTO.class);
    }
}
