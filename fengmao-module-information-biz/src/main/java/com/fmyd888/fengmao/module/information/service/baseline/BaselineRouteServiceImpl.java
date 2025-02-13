package com.fmyd888.fengmao.module.information.service.baseline;


import com.fmyd888.fengmao.framework.mybatis.core.service.BaseSlaveServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineRouteDO;
import com.fmyd888.fengmao.module.information.dal.mysql.baseline.BaselineRouteMapper;
import org.springframework.stereotype.Service;

/**
 * @Title: BaselineRouteServiceImpl
 * @Author: huanhuan
 * @Date: 2024-05-16
 * @Description:
 */
@Service
public class BaselineRouteServiceImpl
        extends BaseSlaveServiceImpl<BaselineRouteMapper, BaselineRouteDO>
        implements BaselineRouteService {

}
