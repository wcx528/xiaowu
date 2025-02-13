package com.fmyd888.fengmao.module.information.api.commodity;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.commodity.dto.CommodityDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.information.dal.mysql.commodity.CommodityMapper;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述：货物管理API实现类
 *
 * @author 小逺
 * @date 2024/04/20
 */
@Service
public class CommodityApiImpl implements CommodityApi {
    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public CommodityDTO getParentCommodityById(Long parentId) {
        if (ObjectUtil.isEmpty(parentId))
            return null;
        CommodityDO entity = commodityMapper.selectOne(CommodityDO::getId, parentId);
        CommodityDTO dto = BeanUtils.toBean(entity, CommodityDTO.class);
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public CommodityDTO getCommodityById(Long id) {
        if (ObjectUtil.isEmpty(id))
            return null;
        CommodityDO entity = commodityMapper.selectOne(CommodityDO::getId, id);
        return BeanUtils.toBean(entity, CommodityDTO.class);
    }

    @Override
    public List<CommodityDTO> getCommodityByIds(List<Long> ids) {
        if (ObjectUtil.isEmpty(ids))
            return new ArrayList<>();
        List<CommodityDO> commoditys = commodityMapper.selectList(CommodityDO::getId, ids);
        return BeanUtils.toBean(commoditys, CommodityDTO.class);
    }

    @Override
    public CommodityDTO getCommodityByName(String name) {
        if (StrUtil.isBlank(name))
            return null;
        CommodityDO commodityDO = commodityMapper.selectOne(CommodityDO::getName, name, CommodityDO::getStatus, 0);
        return BeanUtils.toBean(commodityDO, CommodityDTO.class);
    }

    @Override
    public List<CommodityDTO> getCommodityListByNamesAndCategory(List<String> commodityNames, int category) {
        if (CollectionUtils.isEmpty(commodityNames))
            return Lists.newArrayList();
        List<CommodityDO> commodityList = commodityMapper.getCommodityListByNamesAndCategory(commodityNames,category);
        return BeanUtils.toBean(commodityList, CommodityDTO.class);
    }
}
