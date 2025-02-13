package com.fmyd888.fengmao.module.information.service.transportdetail;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.common.CardDetailPage;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleSimpleRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailSaveReqVO;
import com.fmyd888.fengmao.module.information.convert.transportManger.TransportDetailConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportcar.TransportCarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportdetail.TransportDetailDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportmanger.TransportMangerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.mainvehicle.MainVehicleMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.transportcar.TransportCarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.transportdetail.TransportDetailMapper;
import com.fmyd888.fengmao.module.information.service.mainvehicle.MainVehicleService;
import com.fmyd888.fengmao.module.information.service.transportcar.TransportCarService;
import com.fmyd888.fengmao.module.infra.api.file.FileApi;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.infra.service.file.FileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.TRANSPORT_DETAIL_NOT_EXISTS;

/**
 * 运输证明细 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class TransportDetailServiceImpl implements TransportDetailService {

    @Resource
    private TransportDetailMapper transportDetailMapper;

    @Resource
    private TransportCarMapper transportCarMapper;
    @Resource
    private TransportCarService transportCarService;
    @Resource
    private FileService fileService;
    @Resource
    private MainVehicleMapper mainVehicleMapper;

    @Resource
    private CarMapper carMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private FileApi fileApi;
    @Resource
    private MainVehicleService mainVehicleService;

    @Override
    public Long createTransportDetail(Long transportId, TransportDetailSaveReqVO createReqVO) {

        List<Long> transportCarIds = createReqVO.getTransportCarIds();

        // 1插入运输明细
        TransportDetailDO transportDetail = BeanUtils.toBean(createReqVO, TransportDetailDO.class);
        transportDetail.setTransportId(transportId);
        transportDetail.setId(null);
        String joinedCarIds = transportCarIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        transportDetail.setCarId(joinedCarIds);
        transportDetailMapper.insert(transportDetail);

        Long transportDetailId = transportDetail.getId();

        //绑定对应的文件信息
        fileApi.bindAttachment(createReqVO.getFileIds(),transportDetailId);

        // 2插入运输证明细明表对应车辆表
        if (CollectionUtil.isNotEmpty(transportCarIds)) {
            //批量添加
            transportDetailMapper.batchInsertTransportCars(transportCarIds, transportDetailId);
        }

        // 返回
        return transportDetailId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTransportDetailList(Long transportId, List<TransportDetailSaveReqVO> list2) {
        // 插入运输证明细等操作
        for (TransportDetailSaveReqVO iterm : list2) {
            createTransportDetail(transportId, iterm);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTransportDetail(Long transportId, TransportDetailSaveReqVO updateReqVO) {
        Long id = updateReqVO.getId();
        // 校验存在，
        //validateTransportDetailExists(id);
        // 1更新明细表
        TransportDetailDO updateObj = BeanUtils.toBean(updateReqVO, TransportDetailDO.class);
        updateObj.setTransportId(transportId);
        transportDetailMapper.updateById(updateObj);
        //2更新运输关联车辆（解绑重新关联）
        List<Long> transportCarIds1 = updateReqVO.getTransportCarIds();
        transportCarService.updateTransportCarList(id, transportCarIds1);

        //3更新运输证附件
        List<Long> fileIds = updateReqVO.getFileIds();
        String businessType = FileEnums.TRANSPORT_MANGER_FILE_EUM.getCodeBusinessType();
        fileService.updateFileBandingSourceId(fileIds, businessType, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTransportDetailList(Long transportId, List<TransportDetailSaveReqVO> list2) {
        //1、全部删除于这个运输证相关的链对象数据
        deleteTransportDetail(transportId);
        list2.forEach(iterm -> {
            this.createTransportDetail(transportId, iterm);
        });
    }

    @Override
    //@Transactional(rollbackFor = Exception.class)
    public void deleteTransportDetail(Long transportId) {
        // 校验存在
        //validateTransportDetailExists(transportId);
        // 删除
        LambdaQueryWrapper<TransportDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransportDetailDO::getTransportId, transportId);
        List<TransportDetailDO> transportDetailDOList = transportDetailMapper.selectList(queryWrapper);
        transportDetailMapper.delete(queryWrapper);

        List<Long> transportDetailIds = transportDetailDOList.stream()
                .map(TransportDetailDO::getId).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(transportDetailIds)){
            LambdaQueryWrapper<TransportCarDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.in(TransportCarDO::getTransportDetailId, transportDetailIds);
            transportCarMapper.delete(queryWrapper2);

        }
    }

    private void validateTransportDetailExists(Long id) {
        LambdaQueryWrapper<TransportDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransportDetailDO::getId, id);
        boolean exists = transportDetailMapper.exists(queryWrapper);
        if (exists) {
            throw exception(TRANSPORT_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public TransportDetailRespVO getTransportDetail(Long id) {
        TransportDetailDO transportDetailDO = transportDetailMapper.selectById(id);
        TransportDetailRespVO transportDetailVO = null;
        return transportDetailVO;
    }

    @Override
    public List<TransportDetailSaveReqVO> getDetailListByTransportId(Long transportId) {
        //首先查询运输证
        // 根据运输ID获取运输详情列表
        LambdaQueryWrapper<TransportDetailDO> updateQuery = new LambdaQueryWrapper<>();
        updateQuery.eq(TransportDetailDO::getTransportId, transportId)
                .eq(TransportDetailDO::getDeleted, 0);
        List<TransportDetailDO> transportDetailDOS = transportDetailMapper.selectList(updateQuery);
        // 将运输详情列表转换为运输详情保存请求列表
        List<TransportDetailSaveReqVO> transportDetailSaveReqVOS = TransportDetailConvert.INSTANCE.convertList(transportDetailDOS);
        // 遍历运输详情保存请求列表
        if (CollUtil.isNotEmpty(transportDetailSaveReqVOS)) {
            String businessType = FileEnums.TRANSPORT_MANGER_FILE_EUM.getCodeBusinessType();
            transportDetailSaveReqVOS.forEach(transportDetailSaveReqVO -> {
                // 获取运输详情ID
                Long detailId = transportDetailSaveReqVO.getId();
//                // 根据运输详情ID获取车辆列表
//                List<TransportCarDO> carListByDetail = transportCarService.getCarListByDetailId(detailId);
//                // 创建字符串缓冲区用于存储车辆信息
//                StringBuffer carStringBuffer = new StringBuffer();
//                // 获取主车辆列表
//                List<MainVehicleDO> mainVehicleDOS = null;
//                if (CollUtil.isNotEmpty(carListByDetail)) {
//                    List<Long> carIds = carListByDetail.stream().map(TransportCarDO::getCarId).collect(Collectors.toList());
//                    LambdaQueryWrapper<CarDO> up2 = new LambdaQueryWrapper<>();
//                    up2.in(CarDO::getId, carIds);
//                    List<CarDO> carDOS = carMapper.selectList(up2);
//                    List<Long> mainVehicleIds = carDOS.stream().map(CarDO::getMainVehicleId).collect(Collectors.toList());
//                    LambdaQueryWrapper<MainVehicleDO> up3 = new LambdaQueryWrapper<>();
//                    up3.in(CollectionUtils.isNotEmpty(mainVehicleIds), MainVehicleDO::getId, mainVehicleIds);
//                    mainVehicleDOS = mainVehicleMapper.selectList(up3);
//                    if (CollUtil.isNotEmpty(mainVehicleDOS)) {
//                        for (MainVehicleDO iterm : mainVehicleDOS) {
//                            carStringBuffer.append(iterm.getPlateNumber()).append(",");
//                        }
//                        if (carStringBuffer.length() > 0) {
//                            carStringBuffer.deleteCharAt(carStringBuffer.length() - 1);
//                        }
//                    }
//                }
//                // 设置车辆信息
//                transportDetailSaveReqVO.setCarCode(carStringBuffer.toString());
//                // 获取车辆ID列表
//                List<Long> transportCarIds = carListByDetail.stream().map(TransportCarDO::getId).collect(Collectors.toList());
//                // 设置车辆ID列表
//                transportDetailSaveReqVO.setTransportCarIds(transportCarIds);
                // 根据代码businessType和源ID获取文件列表
                List<FileDO> fileDOList = fileService.getFileUrlByCodeBusinessTypeAndSourceId(businessType, detailId);
                // 根据文件列表获取文件信息列表
                List<Map<String, Object>> fileInforList = fileService.getFileInforList(fileDOList);
                // 设置文件信息列表
                transportDetailSaveReqVO.setFileList(fileInforList);
            });
        }
        // 返回运输详情保存请求列表
        return transportDetailSaveReqVOS;
    }

    @Override
    public CardDetailPage<TransportDetailRespVO> selectDetailPage(TransportDetailPageReqVO pageReqVO) {
        CardDetailPage<TransportDetailRespVO> page = new CardDetailPage<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        pageReqVO.setOrderByColumn("create_time");
        CardDetailPage<TransportDetailRespVO> pageResultVO = transportDetailMapper.selectDetailPage(page, pageReqVO);

        //
        List<TransportDetailRespVO> records = pageResultVO.getRecords();

        // 查询所有的车头号
        List<MainVehicleSimpleRespVO> mainVehicleDetails = mainVehicleService.getMainVehicleDetails(null, null);
        Map<Long, String> mainVehicleMap = null;
        if (mainVehicleDetails != null) {
            mainVehicleMap = mainVehicleDetails.stream()
                    .collect(Collectors.toMap(
                            MainVehicleSimpleRespVO::getId,
                            MainVehicleSimpleRespVO::getPlantNumber
                    ));
        }

        List<Long> transportDetailIds = records.stream().map(TransportDetailRespVO::getId).collect(Collectors.toList());

        List<TransportCarDO> transportCarDOS = null;
        if (transportDetailIds != null && transportDetailIds.size() > 0){
            // 查询关联的所有车头号
            LambdaQueryWrapper<TransportCarDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(TransportCarDO::getTransportDetailId, transportDetailIds);
             transportCarDOS = transportCarMapper.selectList(queryWrapper);
        }


        Map<Long, List<String>> transportDetailPlantNumberMap = new HashMap<>();
        if (CollUtil.isNotEmpty(transportCarDOS)) {

            for (TransportCarDO transportCarDO : transportCarDOS) {
                Long transportDetailId = transportCarDO.getTransportDetailId();
                Long carId = transportCarDO.getCarId();

                // 根据 carId 获取车头号
                String plantNumber = mainVehicleMap.get(carId);
                if (plantNumber != null) {
                    transportDetailPlantNumberMap
                            .computeIfAbsent(transportDetailId, k -> new ArrayList<>())
                            .add(plantNumber);
                }
            }
        }


        for (TransportDetailRespVO record : records) {
            Long detailId = record.getId();
            List<String> plantNumbers = transportDetailPlantNumberMap.get(detailId);
            if (plantNumbers != null) {
                String plantNumberStr = String.join(",", plantNumbers);
                record.setTransportCarName(plantNumberStr);
            } else {
                record.setTransportCarName("");
            }
        }


        CompletableFuture<Void> cardInfoFuture = setCardInfoAsync(pageResultVO);


        return pageResultVO;
    }

    @Override
    public List<HashMap<String, Object>> selectTransportDetailCode() {

            LambdaQueryWrapper<TransportDetailDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TransportDetailDO::getDeleted, 0)
                    .select(TransportDetailDO::getTransportCode, TransportDetailDO::getId);
        List<TransportDetailDO> transportDetailDOS = transportDetailMapper.selectList(queryWrapper);

        if (CollUtil.isEmpty(transportDetailDOS)){
                return new ArrayList<>();
            }
            List<HashMap<String, Object>> result = transportDetailDOS.stream()
                    .map(transportDetailDO -> {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("transportCode", transportDetailDO.getTransportCode());
                        map.put("id", transportDetailDO.getId());
                        return map;
                    })
                    .collect(Collectors.toList());

            return result;
    }



    private void populateTransportDetail(TransportDetailRespVO respVO, String businessType) {
        Long loadFactoryId = respVO.getLoadFactoryId();
        Long unloadFactoryId = respVO.getUnloadFactoryId();
        CustomerDO customerDO1 = customerMapper.selectById(loadFactoryId);
        CustomerDO customerDO2 = customerMapper.selectById(unloadFactoryId);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if (!ObjectUtil.isEmpty(customerDO1)) {
            respVO.setLoadFactoryName(customerDO1.getCustomerName());
        }
        if (!ObjectUtil.isEmpty(customerDO2)) {
            respVO.setUnloadFactoryName(customerDO2.getCustomerName());
        }
        Long id = respVO.getId();
        List<FileDO> fileList = fileService.getFileUrlByCodeBusinessTypeAndSourceId(businessType, id);
        List<String> collect = fileList.stream().map(FileDO::getUrl).collect(Collectors.toList());
        respVO.setFiles(collect);
        List<Map<String, Object>> fileInforList = fileService.getFileInforList(fileList);
        respVO.setFileInfoList(fileInforList);
        LocalDateTime transportSdate = respVO.getTransportSdate();
        LocalDateTime transportEdae = respVO.getTransportEdae();
        respVO.setTransPermitValidity(transportSdate.format(dateFormat) + "~" + transportEdae.format(dateFormat));

        getTransportCarName(respVO);
    }

    /**
     * 处理办理车号拼接返回前端
     *
     * @param respVO
     */
    private void getTransportCarName(TransportDetailRespVO respVO) {
        List<TransportDetailSaveReqVO> detailListByTransport = this.getDetailListByTransportId(respVO.getTransportId());
        if (CollectionUtil.isNotEmpty(detailListByTransport)) {
            StringBuilder carName = new StringBuilder();
            for (TransportDetailSaveReqVO transportDetailSaveReqVO : detailListByTransport) {
                String carCode = transportDetailSaveReqVO.getCarCode();
                if (StringUtils.isNotEmpty(carCode)) {
                    carName.append(carCode).append(",");
                }
            }
            if (carName.length() > 0) {
                carName.deleteCharAt(carName.length() - 1);
            }
            respVO.setTransportCarName(carName.toString());
        }
    }

    /**
     * 设置填充其他分页信息 (异步)
     *
     * @param transportDetailPage 证件对象
     */
    private CompletableFuture<Void> setCardInfoAsync(CardDetailPage<TransportDetailRespVO> transportDetailPage) {
        CompletableFuture<Long> completedFuture = CompletableFuture.supplyAsync(() -> transportDetailMapper.selectCompleted());
        CompletableFuture<Long> below20TonsFuture = CompletableFuture.supplyAsync(() -> transportDetailMapper.selectBelow20Tons());
        CompletableFuture<Long> expiredFuture = CompletableFuture.supplyAsync(() -> transportDetailMapper.selectExperied());

        return CompletableFuture.allOf(completedFuture, below20TonsFuture, expiredFuture)
                .thenAccept(voided -> {
                    try {
                        transportDetailPage.setCompletedContract1(completedFuture.get());
                        transportDetailPage.setQtyBelow20Tons2(below20TonsFuture.get());
                        transportDetailPage.setExpiration3(expiredFuture.get());
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException("设置填充其他分页信息出错", e);
                    }
                });
    }

}