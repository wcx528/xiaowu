package com.fmyd888.fengmao.module.information.api.measurement;

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.measurement.dto.MeasurementDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import com.fmyd888.fengmao.module.information.dal.mysql.measurement.MeasurementMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类功能描述：计量单位API实现类
 *
 * @author 小逺
 * @date 2024/05/09
 */
@Service
public class MeasurementApiImpl implements MeasurementApi {

    @Resource
    private MeasurementMapper measurementMapper;

    @Override
    public MeasurementDTO getMeasurementById(Long id) {
        MeasurementDO entity = measurementMapper.selectById(id);
        return BeanUtils.toBean(entity, MeasurementDTO.class);
    }

    @Override
    public List<MeasurementDTO> getMeasurementList() {
        List<MeasurementDO> measurementList = measurementMapper.selectList();
        return BeanUtils.toBean(measurementList, MeasurementDTO.class);
    }

    @Override
    public List<MeasurementDTO> getMeasurementByIds(List<Long> measurementIds) {
        List<MeasurementDO> entityList = measurementMapper.selectList(MeasurementDO::getId, measurementIds);
        return BeanUtils.toBean(entityList, MeasurementDTO.class);
    }
}
