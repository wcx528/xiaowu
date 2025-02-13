package com.fmyd888.fengmao.module.information.service.commodity;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseDeptServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.commodity.CommodityDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: lmy
 * @Date: 2023/11/22 16:14
 * @Version: 1.0
 * @Description:
 */
@Service
public class CommodityrDeptServiceImpl extends BaseDeptServiceImpl<CommodityDeptMapper, CommodityDeptDO>
        implements CommodityDeptService {
    
}
