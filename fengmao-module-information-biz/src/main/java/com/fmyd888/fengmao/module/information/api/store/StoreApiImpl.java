package com.fmyd888.fengmao.module.information.api.store;

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.store.dto.StoreDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDO;
import com.fmyd888.fengmao.module.information.dal.mysql.store.StoreMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类功能描述：仓库API实现类
 *
 * @author 小逺
 * @date 2024/05/09
 */
@Service
public class StoreApiImpl implements StoreApi {
    @Resource
    private StoreMapper storeMapper;

    @Override
    public StoreDTO getStoreById(Long id) {
        StoreDO entity = storeMapper.selectById(id);
        return BeanUtils.toBean(entity, StoreDTO.class);
    }

    @Override
    public List<StoreDTO> getStoreByIds(List<Long> storeIds) {
        List<StoreDO> entityList = storeMapper.selectList(StoreDO::getId,storeIds);
        return BeanUtils.toBean(entityList, StoreDTO.class);
    }
}
