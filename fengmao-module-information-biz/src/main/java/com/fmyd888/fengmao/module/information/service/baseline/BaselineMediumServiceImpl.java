package com.fmyd888.fengmao.module.information.service.baseline;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.service.BaseSlaveServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineMediumDO;
import com.fmyd888.fengmao.module.information.dal.mysql.baseline.BaselineMediumMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基线 Service 实现类
 *
 * @author 丰茂
 */
@Service
public class BaselineMediumServiceImpl
        extends BaseSlaveServiceImpl<BaselineMediumMapper, BaselineMediumDO>
        implements BaselineMediumService {

    @Resource
    private BaselineMediumMapper baselineMediumMapper;

    @Override
    public Boolean validateBaselineMediumExists(Long baselineId, List<Long> commodityIdList) {
        LambdaQueryWrapper<BaselineMediumDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(BaselineMediumDO::getEntityId,baselineId)
                .in(BaselineMediumDO::getCommodityId,commodityIdList);
        boolean exists = baselineMediumMapper.exists(queryWrapper);
        return exists;
    }
}