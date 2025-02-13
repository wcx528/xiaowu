package com.fmyd888.fengmao.module.information.api.locationRecord;

import cn.hutool.core.util.ObjectUtil;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.locationRecord.dto.LatestLocationRecordDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LatestLocationRecordDO;
import com.fmyd888.fengmao.module.information.dal.mysql.locationRecord.LatestLocationRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述： 位置记录接口实现
 *
 * @author 小逺
 * @date 2024/07/03 22:50
 */
@Service
public class LocationRecordApiImpl implements LocationRecordApi {
    @Resource
    private LatestLocationRecordMapper latestLocationRecordMapper;

    @Override
    public LatestLocationRecordDTO getLatestLocationRecordByCarId(Long carId) {
        if (ObjectUtil.isEmpty(carId))
            return null;
        LatestLocationRecordDO latestLocationRecord = latestLocationRecordMapper.selectFirst(LatestLocationRecordDO::getCarId, carId);
        return BeanUtils.toBean(latestLocationRecord, LatestLocationRecordDTO.class);
    }

    @Override
    public List<LatestLocationRecordDTO> getLatestLocationRecordByCarIds(List<Long> carIds) {
        if (ObjectUtil.isEmpty(carIds))
            return new ArrayList<>();
        List<LatestLocationRecordDO> latestLocationRecords = latestLocationRecordMapper.selectList(LatestLocationRecordDO::getCarId, carIds);
        return BeanUtils.toBean(latestLocationRecords, LatestLocationRecordDTO.class);
    }
}
