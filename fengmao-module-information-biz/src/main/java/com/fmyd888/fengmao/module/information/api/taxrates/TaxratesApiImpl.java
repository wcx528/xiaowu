package com.fmyd888.fengmao.module.information.api.taxrates;

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.taxrates.DTO.TaxratesBaseDTO;
import com.fmyd888.fengmao.module.information.api.taxrates.DTO.TaxratesRespDTO;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDO;
import com.fmyd888.fengmao.module.information.service.taxrates.TaxratesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 税率API
 * @author Misaka
 * date: 2024/7/15
 */
@Service
public class TaxratesApiImpl implements TaxratesApi {

    @Resource
    private TaxratesService taxratesService;

    @Override
    public TaxratesRespDTO getTaxrates(Long id) {
        TaxratesRespVO taxratesRespVO = taxratesService.getTaxrates(id);
        return BeanUtils.toBean(taxratesRespVO, TaxratesRespDTO.class);
    }

    @Override
    public List<TaxratesRespDTO> getTaxratesList(List<Long> ids) {
        List<TaxratesRespVO> taxratesRespVOList = taxratesService.getTaxratesList(ids);
        return BeanUtils.toBean(taxratesRespVOList, TaxratesRespDTO.class);
    }

    @Override
    public TaxratesRespDTO getTaxratesByName(String taxName) {
        TaxratesRespVO taxratesRespVO = taxratesService.getTaxratesByName(taxName);
        return BeanUtils.toBean(taxratesRespVO, TaxratesRespDTO.class);
    }
}
