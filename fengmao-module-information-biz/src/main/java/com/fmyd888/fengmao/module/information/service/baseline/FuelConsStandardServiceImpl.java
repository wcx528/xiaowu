package com.fmyd888.fengmao.module.information.service.baseline;


import com.fmyd888.fengmao.framework.mybatis.core.service.BaseSlaveServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.FuelConsStandardDO;
import com.fmyd888.fengmao.module.information.dal.mysql.baseline.FuelConsStandardMapper;
import org.springframework.stereotype.Service;

@Service
public class FuelConsStandardServiceImpl
        extends BaseSlaveServiceImpl<FuelConsStandardMapper, FuelConsStandardDO>
        implements FuelConsStandardService {



}