package com.fmyd888.fengmao.module.information.service.measurement;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseDeptServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.measurement.MeasurementDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: lmy
 * @Date: 2023/11/23 12:02
 * @Version: 1.0
 * @Description:
 */
@Service
public class MeasurementDeptServiceImmpl
        extends BaseDeptServiceImpl<MeasurementDeptMapper, MeasurementDeptDO>
        implements MeasurementDeptService {

}
