package com.fmyd888.fengmao.module.information.api.fleet;

import cn.hutool.core.util.ObjectUtil;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.fleet.dto.FleetDTO;
import com.fmyd888.fengmao.module.information.api.fleet.dto.FleetSyncDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.fleet.FleetDO;
import com.fmyd888.fengmao.module.information.dal.mysql.fleet.FleetMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类功能描述：车队API实现类
 *
 * @author 小逺
 * @date 2024/06/14
 */
@Service
public class FleetApiImpl implements FleetApi {
    @Resource
    private FleetMapper fleetMapper;

    @Override
    public FleetDTO getFleetById(Long id) {
        if (ObjectUtil.isEmpty(id))
            return null;
        FleetDO fleetDO = fleetMapper.selectById(id);
        return BeanUtils.toBean(fleetDO, FleetDTO.class);
    }

    @Override
    public List<FleetDTO> getAllFleet() {
        List<FleetDO> fleetDOS = fleetMapper.selectList();
        return BeanUtils.toBean(fleetDOS, FleetDTO.class);
    }

    @Override
    public void syncFleet(List<FleetSyncDTO> fleetList) {
        if (ObjectUtil.isEmpty(fleetList))
            return;
        List<FleetDO> bean = BeanUtils.toBean(fleetList, FleetDO.class);
        List<FleetDO> dbFleetList = fleetMapper.selectList();
        List<List<FleetDO>> lists = CollectionUtils.diffList(dbFleetList, bean, (o1, o2) -> ObjectUtil.equals(o1.getRealId(), o2.getRealId()));
        if (lists.get(0).size() > 0)
            fleetMapper.batchInsertX(lists.get(0));
        if (lists.get(1).size() > 0)
            fleetMapper.updateBatch(lists.get(1));
//        if (lists.get(2).size() > 0)
//            fleetMapper.deleteBatchIds(lists.get(2));
    }
}
