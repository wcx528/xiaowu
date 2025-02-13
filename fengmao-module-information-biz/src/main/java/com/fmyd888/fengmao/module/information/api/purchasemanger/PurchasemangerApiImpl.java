package com.fmyd888.fengmao.module.information.api.purchasemanger;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.purchasemanger.dto.PurchasemangerDTO;
import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.PurchaseMangerRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.purchasemanger.PurchaseMangerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.purchasemanger.PurchaseMangerMapper;
import com.fmyd888.fengmao.module.information.service.purchasemanger.PurchaseMangerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述：购买证API实现类
 *
 * @author 小逺
 * @date 2024/04/27 12:50
 */
@Service
public class PurchasemangerApiImpl implements PurchasemangerApi {
    @Resource
    private PurchaseMangerService purchaseMangerService;

    @Resource
    private PurchaseMangerMapper purchaseMangerMapper;

    @Override
    public PurchasemangerDTO getPurchasemangerById(Long id) {
        if (ObjectUtil.isEmpty(id))
            return new PurchasemangerDTO();
        PurchaseMangerRespVO purchaseManger = purchaseMangerService.getPurchaseManger(id);
        PurchasemangerDTO bean = BeanUtils.toBean(purchaseManger, PurchasemangerDTO.class);
        return bean;
    }

    @Override
    public List<PurchasemangerDTO> getPurchasemangerByIds(List<Long> ids) {
        if (ids.isEmpty())
            return new ArrayList<>();
        List<PurchaseMangerDO> list = purchaseMangerService.getPurchaseMangerList(ids);
        List<PurchasemangerDTO> result = BeanUtils.toBean(list, PurchasemangerDTO.class);
        return result;
    }

    @Override
    public PurchasemangerDTO getPurchasemangerByCode(String code) {
        if(StrUtil.isBlank(code))
            return null;
        PurchaseMangerDO purchaseMangerDO = purchaseMangerMapper.selectOne(PurchaseMangerDO::getPurchaseCode, code);
        return BeanUtils.toBean(purchaseMangerDO, PurchasemangerDTO.class);
    }
}
