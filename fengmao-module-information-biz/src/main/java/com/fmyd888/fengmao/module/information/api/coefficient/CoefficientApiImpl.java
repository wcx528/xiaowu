package com.fmyd888.fengmao.module.information.api.coefficient;

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.coefficient.dto.CoefficientDetailsDTO;
import com.fmyd888.fengmao.module.information.api.coefficient.dto.LoadingRateDTO;
import com.fmyd888.fengmao.module.information.api.coefficient.dto.MaintenanceCostsDTO;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.CoefficientDetailDTO;
import com.fmyd888.fengmao.module.information.service.coefficient.CoefficientService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 系数维护API实现
 * @author Misaka
 * date: 2024/8/27
 */
@Service
public class CoefficientApiImpl implements CoefficientApi {
    @Resource
    private CoefficientService coefficientService;
    @Override
    public CoefficientDetailsDTO getCoefficientDetailsByCompanyId(Long companyId) {
        CoefficientDetailDTO coefficientDetailDTO = coefficientService.coefficientDetails(companyId);
        if (coefficientDetailDTO==null){
            return null;
        }
        CoefficientDetailsDTO coefficientDetailsDTO= BeanUtils.toBean(coefficientDetailDTO,CoefficientDetailsDTO.class);
        List<LoadingRateDTO> loadingRateDTOS = BeanUtils.toBean(coefficientDetailDTO.getLoadingRates(), LoadingRateDTO.class);
        List<MaintenanceCostsDTO> maintenanceCostsDTOS = BeanUtils.toBean(coefficientDetailDTO.getMaintenanceCostss(), MaintenanceCostsDTO.class);
        coefficientDetailsDTO.setLoadingRates(loadingRateDTOS);
        coefficientDetailsDTO.setMaintenanceCosts(maintenanceCostsDTOS);
        return coefficientDetailsDTO;
    }

    @Override
    public List<CoefficientDetailsDTO> getCoefficientDetailsList(List<Long> companyIds) {
        List<CoefficientDetailsDTO> coefficientDetailsDTOList= Lists.newArrayList();
        for (Long companyId : companyIds) {
            CoefficientDetailsDTO coefficientDetailsDTO = getCoefficientDetailsByCompanyId(companyId);
            if (coefficientDetailsDTO==null){
                continue;
            }
            coefficientDetailsDTOList.add(coefficientDetailsDTO);
        }
        return coefficientDetailsDTOList;
    }
}
