package com.fmyd888.fengmao.module.information.api.saleman;

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.salesman.SalesmanApi;
import com.fmyd888.fengmao.module.information.api.salesman.dto.SalesmanDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDO;
import com.fmyd888.fengmao.module.information.dal.mysql.salesman.SalesmanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类功能描述：业务员API实现类
 *
 * @author 小逺
 * @date 2024/05/09
 */
@Service
public class SalesmanApiImpl implements SalesmanApi {

    @Resource
    private SalesmanMapper salesmanMapper;

    @Override
    public SalesmanDTO getSalesmanById(Long id) {
        SalesmanDO entity = salesmanMapper.selectById(id);
        return BeanUtils.toBean(entity, SalesmanDTO.class);
    }

    @Override
    public List<SalesmanDTO> getSalesmanByIds(List<Long> salesmanIds) {
        List<SalesmanDO> entityList = salesmanMapper.selectList(SalesmanDO::getId, salesmanIds);
        return BeanUtils.toBean(entityList, SalesmanDTO.class);
    }
}
