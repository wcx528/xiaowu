package com.fmyd888.fengmao.module.information.service.transportcar;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.controller.admin.transportcar.vo.TransportCarExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportcar.vo.TransportCarListReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportcar.vo.TransportCarPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportcar.vo.TransportCarSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportcar.TransportCarDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.mainvehicle.MainVehicleMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.transportcar.TransportCarMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 运输证办理车辆关联 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class TransportCarServiceImpl implements TransportCarService {

    @Resource
    private TransportCarMapper transportCarMapper;
    @Resource
    private CarMapper carMapper;
    @Resource
    private MainVehicleMapper mainVehicleMapper;

    @Override
    public Long createTransportCar(TransportCarSaveReqVO createReqVO) {
        // 插入
        TransportCarDO transportCar = BeanUtils.toBean(createReqVO, TransportCarDO.class);
        transportCarMapper.insert(transportCar);

        // 返回
        return transportCar.getId();
    }

    @Override
    public void insertTransportCarList(Long transportDetailId, List<Long> transportCarIds) {
        List<CarDO> carDOS = carMapper.selectList();
        List<Long> carIds = carDOS.stream().map(CarDO::getId).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(transportCarIds)) {
            for (Long carId : transportCarIds) {
                if (carIds.contains(carId)) {
                    TransportCarDO transportCarDO = new TransportCarDO();
                    transportCarDO.setCarId(carId);
                    transportCarDO.setTransportDetailId(transportDetailId);
                    transportCarMapper.insert(transportCarDO);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTransportCarList(Long transportDetailId, List<Long> transportCarIds) {
        LambdaQueryWrapper<TransportCarDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransportCarDO::getTransportDetailId, transportDetailId);
        transportCarMapper.delete(queryWrapper);
        if (CollectionUtils.isNotEmpty(transportCarIds)) {
            for (Long transportCarId : transportCarIds) {
                TransportCarDO transportCarDO = TransportCarDO.builder()
                        .transportDetailId(transportDetailId)
                        .carId(transportCarId)
                        .build();
                transportCarMapper.insert(transportCarDO);
            }
        }
    }

    @Override
    public void updateTransportCar(TransportCarSaveReqVO updateReqVO) {
        // 校验存在
        validateTransportCarExists(updateReqVO.getId());
        // 更新
        TransportCarDO updateObj = BeanUtils.toBean(updateReqVO, TransportCarDO.class);
        transportCarMapper.updateById(updateObj);

    }

    @Override
    public void deleteTransportCar(Long id) {
        // 校验存在
        validateTransportCarExists(id);
        // 删除
        transportCarMapper.deleteById(id);
    }

    private void validateTransportCarExists(Long id) {
        if (transportCarMapper.selectById(id) == null) {
            throw exception(TRANSPORT_CAR_NOT_EXISTS);
        }
    }


    @Override
    public TransportCarDO getTransportCar(Long id) {
        return transportCarMapper.selectById(id);
    }

    @Override
    public List<TransportCarDO> getTransportCarList(Collection<Long> ids) {
        return transportCarMapper.selectBatchIds(ids);
    }

    @Override
    public List<TransportCarDO> getCarListByDetailId(Long detailId) {
        LambdaQueryWrapper<TransportCarDO> up = new LambdaQueryWrapper<>();
        up.eq(TransportCarDO::getTransportDetailId, detailId);
        List<TransportCarDO> transportCarDOS = transportCarMapper.selectList(up);
        return transportCarDOS;
    }

    @Override
    public PageResult<TransportCarDO> getTransportCarPage(TransportCarPageReqVO pageReqVO) {
        return transportCarMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TransportCarDO> getTransportCarList(TransportCarListReqVO listReqVO) {
        return transportCarMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateTransportCar(List<TransportCarSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteTransportCar(List<Long> ids) {
        // 在这里处理批量删除逻辑
        transportCarMapper.deleteBatchIds(ids);
    }

    @Override
    public List<TransportCarExcelVO> importPreviewList(List<TransportCarExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(TRANSPORT_CAR_IMPORT_LIST_IS_EMPTY);
        }

        List<TransportCarExcelVO> excelVo = BeanUtils.toBean(importDatas, TransportCarExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(TRANSPORT_CAR_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(TransportCarExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
            throw exception(TRANSPORT_CAR_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(TRANSPORT_CAR_IMPORT_PORT_REQUIRE);
        //以下是示例，补充逻辑时请替换成自己书写的逻辑
        //ImportResult result = ImportResult.builder()
        //.total(importReqVo.getImportDatas().size())
        //.importCount(0)
        //.failCount(importReqVo.getImportDatas().size())//假设这里假设都导入失败
        //.success(false)
        //.data(importReqVo.getImportDatas())
        //.build();
        //return result;
    }

}