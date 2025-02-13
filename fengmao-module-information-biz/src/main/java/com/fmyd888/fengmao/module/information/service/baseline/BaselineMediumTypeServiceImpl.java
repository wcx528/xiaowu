package com.fmyd888.fengmao.module.information.service.baseline;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.service.BaseSlaveServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineMediumTypeDO;
import com.fmyd888.fengmao.module.information.dal.mysql.baseline.BaselineMediumtypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基线 Service 实现类
 *
 * @author 丰茂
 */
@Service
public class BaselineMediumTypeServiceImpl
        extends BaseSlaveServiceImpl<BaselineMediumtypeMapper, BaselineMediumTypeDO>
        implements BaselineMediumTypeService {

    @Resource
    private BaselineMediumtypeMapper baselineMediumtypeMapper;

    @Override
    public Boolean validateBaselineMediumTypeExists(Long baselineId, List<Long> commodityIdList) {
        LambdaQueryWrapper<BaselineMediumTypeDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(BaselineMediumTypeDO::getEntityId,baselineId)
                        .in(BaselineMediumTypeDO::getCommodityId,commodityIdList);
        boolean exists = baselineMediumtypeMapper.exists(queryWrapper);
        return exists;
    }
}