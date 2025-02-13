package com.fmyd888.fengmao.module.information.service.store;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fmyd888.fengmao.framework.mybatis.core.service.BaseDeptServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.store.StoreDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: lmy
 * @Date: 2023/11/23 10:15
 * @Version: 1.0
 * @Description:
 */
@Service
public class StoreDeptServiceImpl extends BaseDeptServiceImpl<StoreDeptMapper, StoreDeptDO>
        implements StoreDeptService {


    //@Override
    //public StoreDeptDO createEntity() {
    //    StoreDeptDO storeDeptDO = new StoreDeptDO();
    //    storeDeptDO.setRemark("自定义remark");
    //    return storeDeptDO;
    //}
}
