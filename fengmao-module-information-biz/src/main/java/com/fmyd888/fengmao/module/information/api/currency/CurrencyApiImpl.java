package com.fmyd888.fengmao.module.information.api.currency;

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.currency.dto.CurrencyDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDO;
import com.fmyd888.fengmao.module.information.dal.mysql.currency.CurrencyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.cache.annotation.CacheResult;
import java.util.List;

/**
 * 类功能描述：币别API实现类
 *
 * @author 小逺
 * @date 2024/05/09
 */
@Service
public class CurrencyApiImpl implements CurrencyApi {
    @Resource
    private CurrencyMapper currencyMapper;

    @Override
    public CurrencyDTO getCurrencyById(Long currencyId) {
        CurrencyDO entity = currencyMapper.selectById(currencyId);
        return BeanUtils.toBean(entity, CurrencyDTO.class);
    }

    @Override
    public List<CurrencyDTO> getCurrencyByIds(List<Long> currencyIds) {
        List<CurrencyDO> currencyDOList = currencyMapper.selectList(CurrencyDO::getId,currencyIds);
        return BeanUtils.toBean(currencyDOList, CurrencyDTO.class);
    }
}
