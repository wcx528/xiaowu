package com.fmyd888.fengmao.module.information.api.car;

import cn.hutool.core.util.ObjectUtil;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.car.dto.CarDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.service.car.CarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类功能描述：CarApi实现类
 *
 * @author 小逺
 * @date 2024/04/20
 */
@Service
public class CarApiImpl implements CarApi {
    @Resource
    private CarMapper carMapper;
    @Resource
    private CarService carService;

    @Override
    public List<CarDTO> getCarListById(List<Long> ids) {
        if (ids.isEmpty())
            return new ArrayList<>();
        List<CarDO> carDOS = carMapper.selectCarInfos(ids);
        return BeanUtils.toBean(carDOS, CarDTO.class);
    }

    @Override
    public List<CarDTO> selectCarNameInfos(List<Long> ids) {
        if (ids.isEmpty())
            return new ArrayList<>();
        List<CarDO> carDOS = carMapper.selectCarNameInfos(ids);
        return BeanUtils.toBean(carDOS, CarDTO.class);
    }

    @Override
    public CarDTO getCarById(Long id) {
        if (ObjectUtil.isEmpty(id))
            return null;
        List<CarDO> carDOS = carMapper.selectCarInfos(Arrays.asList(id));
        CarDO carDO = carDOS.stream().findFirst().orElse(null);
        return BeanUtils.toBean(carDO, CarDTO.class);
    }

    @Override
    public List<CarDTO> getAllEffectiveCarList() {
        List<CarDO> carList = carMapper.selectEffectiveCarList();

        return BeanUtils.toBean(carList, CarDTO.class);
    }

    @Override
    public void updateCarScrapOrCancelProcess(String processId, Integer processStatus) {
        carService.updateCarScrapOrCancelProcess(processId, processStatus);
    }

    @Override
    public void carPersonProcess(String processId, Integer processStatus) {
        carService.carPersonProcess(processId, processStatus);
    }

    @Override
    public void carTrailerFleetProcess(String processId, Integer processStatus) {
        carService.carTrailerFleetProcess(processId, processStatus);
    }

    @Override
    public List<CarDTO> getCarListByNames(List<String> plateNumbers) {
        if (plateNumbers.isEmpty())
            return new ArrayList<>();
        List<CarDO> carDOS = carMapper.selectCarInfosByNames(plateNumbers);
        return BeanUtils.toBean(carDOS, CarDTO.class);
    }
}
