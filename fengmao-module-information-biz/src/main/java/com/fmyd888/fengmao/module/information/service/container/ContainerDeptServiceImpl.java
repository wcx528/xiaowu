package com.fmyd888.fengmao.module.information.service.container;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseDeptServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.container.ContainerDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.container.ContainerDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @Author:
 * @Date:
 * @Version: 1.0
 * @Description:
 */
@Service
public class ContainerDeptServiceImpl
        extends BaseDeptServiceImpl<ContainerDeptMapper, ContainerDeptDO>
        implements ContainerDeptService {

}
