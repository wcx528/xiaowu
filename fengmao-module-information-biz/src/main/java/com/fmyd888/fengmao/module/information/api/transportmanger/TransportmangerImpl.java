package com.fmyd888.fengmao.module.information.api.transportmanger;

import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.transportmanger.dto.TransportmangerDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportcar.TransportCarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportdetail.TransportDetailDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportmanger.TransportMangerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.transportcar.TransportCarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.transportdetail.TransportDetailMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.transportmanger.TransportMangerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类功能描述：运输证API实现类
 *
 * @author 小逺
 * @date 2024/04/26
 */
@Service
public class TransportmangerImpl implements TransportmangerApi{
    @Resource
    private TransportDetailMapper transportDetailMapper;

    @Resource
    private TransportMangerMapper transportMangerMapper;

    @Resource
    private TransportCarMapper transportCarMapper;
    @Override
    public TransportmangerDTO getTransportmangerById(Long id) {
        TransportMangerDO transportManger = transportMangerMapper.selectById(id);
        if(transportManger== null)
            return null;
        TransportmangerDTO dto = BeanUtils.toBean(transportManger, TransportmangerDTO.class);
        List<TransportDetailDO> detailList = transportDetailMapper.selectList(TransportDetailDO::getTransportId, transportManger.getId());
        if(detailList.isEmpty())
            return dto;
        List<TransportCarDO> carList = transportCarMapper.selectList(TransportCarDO::getTransportDetailId, detailList.stream().map(TransportDetailDO::getId).collect(Collectors.toList()));
        List<Long> carIds = carList.stream().map(TransportCarDO::getCarId).collect(Collectors.toList());
        dto.setCarIds(carIds);
        return dto;
    }
}
