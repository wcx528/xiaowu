package com.fmyd888.fengmao.module.information.service.trailer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.exception.ErrorCode;
import com.fmyd888.fengmao.framework.common.exception.ServiceException;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.io.IoUtils;
import com.fmyd888.fengmao.framework.common.util.string.StrUtils;
import com.fmyd888.fengmao.framework.datapermission.core.util.DataPermissionUtils;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.security.core.LoginUser;
import com.fmyd888.fengmao.framework.security.core.util.SecurityFrameworkUtils;
import com.fmyd888.fengmao.module.information.common.vo.ImportRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.CustomerDTO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.DownMainVehicleExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleDownloadVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleLicenseSimpleVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleOwnershipSimpleVO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.dto.TrailerListDTO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.*;
import com.fmyd888.fengmao.module.information.convert.trailer.TrailerConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerCommodityDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.commodity.CommodityMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.trailer.TrailerCommodityMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.trailer.TrailerMapper;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.enums.vehicle.VehicleTrailerFileEnum;
import com.fmyd888.fengmao.module.information.service.customer.CustomerService;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.information.service.mainvehicle.VehicleLicenseService;
import com.fmyd888.fengmao.module.information.service.mainvehicle.VehicleOwnershipService;
import com.fmyd888.fengmao.module.information.service.vehicle.*;
import com.fmyd888.fengmao.module.information.utils.VehicleUtils;
import com.fmyd888.fengmao.module.infra.controller.admin.file.vo.file.FileSimpleRespVO;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import com.fmyd888.fengmao.module.infra.dal.mysql.file.FileMapper;
import com.fmyd888.fengmao.module.system.api.dict.DictDataApi;
import com.fmyd888.fengmao.module.system.api.dict.dto.DictDataRespDTO;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.api.user.dto.AdminUserRespDTO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dict.DictDataDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.dict.DictDataMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 车挂档案 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class TrailerServiceImpl implements TrailerService {

    @Resource
    private TrailerMapper trailerMapper;
    @Resource
    private CarMapper carMapper;
    @Resource
    private VehicleLicenseService vehicleLicenseService;
    @Resource
    private VehicleOwnershipService vehicleOwnershipService;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private DictDataMapper dictDataMapper;
    @Autowired
    private EvaluationService evaluationService;
    @Autowired
    private MaintenanceRepairService maintenanceRepairService;
    @Autowired
    private AccidentService accidentService;
    @Autowired
    private CarChangeService carChangeService;
    @Autowired
    private MileageService mileageService;
    @Autowired
    private PartReplacementService partReplacementService;
    @Autowired
    private EncodingRulesService encodingRulesService;
    @Resource
    private TrailerCommodityMapper trailerCommodityMapper;
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private DictDataApi dictDataApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTrailer(TrailerCreateReqVO createReqVO) {
        String vehicleTrailerNo = createReqVO.getVehicleTrailerNo();
        String trailerCode = createReqVO.getTrailerCode();
        String vehicleIdenCode = createReqVO.getVehicleIdenCode();
        validateTrailerExists(null, vehicleTrailerNo, vehicleIdenCode, trailerCode);
        // 1、插入车挂基础信息
        TrailerDO trailer = TrailerConvert.INSTANCE.convert(createReqVO);
        // 1.1 车头编码当前端传过来直接使用，不传则自动生成
        String code = EncodingRulesEnum.VEHICLE_TRAILER_CODE.getBusinessCode();
        String vehicleCode = createReqVO.getTrailerCode();
        Boolean modifiable = encodingRulesService.isFillOverWrite(code);
        //
        if (StrUtils.isEmpty(vehicleCode) && modifiable) {
            String vehicleCode1 = encodingRulesService.getCodeByRuleType(code);
            trailer.setTrailerCode(vehicleCode1);
        }
        // 1.2 使用年限 = 当前日期 - 登记日期    ，
//        LocalDateTime certificatTime = trailer.getCertificatTime();
//        int userYears = useYearCompute(certificatTime);
//        trailer.setUserYears(userYears);
        trailerMapper.insert(trailer);

        Long trailerId = trailer.getId();

        //2、插入车挂的车牌变更记录信息
        List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVO = createReqVO.getVehicleLicenseSimpleVO();
        vehicleLicenseSimpleVO.forEach(iterm -> {
            iterm.setTrailerId(trailerId);
        });
        if (CollectionUtils.isNotEmpty(vehicleLicenseSimpleVO)) {
            vehicleLicenseService.createVehicleLicenseList(vehicleLicenseSimpleVO);
        }

        //3、插入业户名称变更记录信息
        List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVO = createReqVO.getVehicleOwnershipSimpleVO();
        if (CollectionUtils.isNotEmpty(vehicleOwnershipSimpleVO)) {
            vehicleOwnershipSimpleVO.forEach(iterm -> {
                iterm.setTrailerId(trailerId);
            });
            vehicleOwnershipService.createVehicleOwnershipList(vehicleOwnershipSimpleVO);
        }
        // 4、插入附件信息
        VehicleUtils.saveVehicleFile2(trailerId, createReqVO);

        // 5、可装货物集合
        List<Long> commodityIds = createReqVO.getCommodityIds();
        trailerCommodityMapper.insertBatchX(commodityIds, trailerId);
        // 返回
        return trailerId;
    }

    /**
     * 批量插入可装货物
     *
     * @param trailerId
     * @param commodityIds
     */
    private void saveCommoditys(Long trailerId, List<Long> commodityIds) {
        if (CollectionUtils.isNotEmpty(commodityIds)) {
//            LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
//            String loginUserId = loginUser.getId().toString();
//            AdminUserRespDTO loginUserInfo = adminUserApi.getLoginUserInfo();
//            Long deptId = loginUserInfo.getDeptId();
//            List<TrailerCommodityDO> trailerCommodityDOList = commodityIds.stream()
//                    .map(commodityId -> {
//                        TrailerCommodityDO trailerCommodityDO = new TrailerCommodityDO();
//                        trailerCommodityDO.setEntityId(trailerId);
//                        trailerCommodityDO.setCommodityId(commodityId);
//                        trailerCommodityDO.setCreator(loginUserId);
//                        trailerCommodityDO.setUpdater(loginUserId);
//                        trailerCommodityDO.setDeptId(deptId);
//                        return trailerCommodityDO;
//                    })
//                    .collect(Collectors.toList());
            trailerCommodityMapper.insertBatchX(commodityIds, trailerId);
        }
    }

    /**
     * 使用年限计算 使用年限 = 当前日期 - 登记日期
     *
     * @param registerTime
     * @return
     */
    private Integer useYearCompute(LocalDateTime registerTime) {
        LocalDateTime now = LocalDateTime.now();
        // 计算年、月、日的差异
        Period period = Period.between(registerTime.toLocalDate(), now.toLocalDate());
        int years = period.getYears();//相差的年
        int months = period.getMonths(); //相差的月
        //相差月份大于等于半年（6月）是算一年
        if (months >= 6) {
            years++;
        }
        return years;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTrailer(TrailerUpdateReqVO updateReqVO) {
        // 校验存在
        Long trailerId = updateReqVO.getId();
        String vehicleTrailerNo = updateReqVO.getVehicleTrailerNo();
        String trailerCode = updateReqVO.getTrailerCode();
        String vehicleIdenCode = updateReqVO.getVehicleIdenCode();
        validateTrailerExists(trailerId, vehicleTrailerNo, vehicleIdenCode, trailerCode);
        // 1、更新车挂表基本信息
        TrailerDO updateObj = TrailerConvert.INSTANCE.convert(updateReqVO);
        LocalDateTime certificatTime = updateObj.getCertificatTime();
        int userYears = useYearCompute(certificatTime);
        updateObj.setUserYears(userYears);
        trailerMapper.updateById(updateObj);
        if(!ObjectUtil.equal(updateObj.getIsOut(),true))
        {
            LambdaUpdateWrapper<TrailerDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(TrailerDO::getOutCompanyId,null).eq(TrailerDO::getId, trailerId);
            trailerMapper.update(null, updateWrapper);
        }

        //然后把关联表的数据先删除再更新
        List<Long> commodityIds = updateReqVO.getCommodityIds();
        if (CollectionUtils.isNotEmpty(commodityIds)) {
            LambdaUpdateWrapper<TrailerCommodityDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(TrailerCommodityDO::getEntityId, trailerId)
                    .set(TrailerCommodityDO::getDeleted, 1);
            trailerCommodityMapper.update(null, updateWrapper);
            trailerCommodityMapper.insertBatchX(commodityIds, trailerId);
        }

        //2、更新(插入)车挂的车牌变更记录信息
        List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVO = updateReqVO.getVehicleLicenseSimpleVO();
        vehicleLicenseSimpleVO.forEach(iterm -> {
            iterm.setTrailerId(trailerId);
        });
        if (CollectionUtils.isNotEmpty(vehicleLicenseSimpleVO)) {
            vehicleLicenseService.deleteVehicleLicense02(trailerId);
            vehicleLicenseService.createVehicleLicenseList(vehicleLicenseSimpleVO);
        } else {
            vehicleLicenseService.deleteVehicleLicense02(trailerId);
        }

        //3、更新(插入)业户名称变更记录信息
        List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVO = updateReqVO.getVehicleOwnershipSimpleVO();
        vehicleOwnershipSimpleVO.forEach(iterm -> {
            iterm.setTrailerId(trailerId);
        });
        if (CollectionUtils.isNotEmpty(vehicleOwnershipSimpleVO)) {
            vehicleOwnershipService.deleteVehicleOwnership02(trailerId);
            vehicleOwnershipService.createVehicleOwnershipList(vehicleOwnershipSimpleVO);
        } else {
            vehicleOwnershipService.deleteVehicleOwnership02(trailerId);
        }

        // 4、更新车挂表对应附件信息
        VehicleUtils.saveVehicleFile2(trailerId, updateReqVO);
    }

    @Override
    public void deleteTrailer(Long id) {
        // 删除
        trailerMapper.deleteById(id);
    }

    /**
     * 校验挂车号、挂车号编码、车架号不重复
     *
     * @param id               id
     * @param vehicleTrailerNo 挂车车牌
     * @param vehicleIdenCode  车架
     * @param trailerCode      挂车号编码
     */
    private void validateTrailerExists(Long id, String vehicleTrailerNo, String vehicleIdenCode, String trailerCode) {
        // 检查车牌是否重复
        if (StrUtils.isNotEmpty(vehicleTrailerNo)) {
            LambdaQueryWrapper<TrailerDO> vehicleTrailerNoQuery = new LambdaQueryWrapperX<>();
            vehicleTrailerNoQuery.ne(id != null, TrailerDO::getId, id)
                    .eq(TrailerDO::getVehicleTrailerNo, vehicleTrailerNo);
            TrailerDO trailerDO = trailerMapper.selectFirst(vehicleTrailerNoQuery);
            if (trailerDO != null) {
                throw exception(VEHICLE_ALREADY_EXISTS02);
            }
        }

        // 检查车辆识别代号是否重复
        if (StrUtils.isNotEmpty(vehicleIdenCode)) {
            LambdaQueryWrapper<TrailerDO> vehicleIdenCodeQuery = new LambdaQueryWrapperX<>();
            vehicleIdenCodeQuery.ne(id != null, TrailerDO::getId, id)
                    .eq(TrailerDO::getVehicleIdenCode, vehicleIdenCode);
            TrailerDO trailerDO = trailerMapper.selectFirst(vehicleIdenCodeQuery);
            if (trailerDO != null) {
                throw exception(VEHICLE_ALREADY_EXISTS03);
            }
        }

        // 检查车挂编码是否重复
        if (StrUtils.isNotEmpty(trailerCode)) {
            LambdaQueryWrapper<TrailerDO> trailerCodeQuery = new LambdaQueryWrapperX<>();
            trailerCodeQuery.ne(id != null, TrailerDO::getId, id)
                    .eq(TrailerDO::getTrailerCode, trailerCode);
            if (id != null) {
                trailerCodeQuery.ne(TrailerDO::getId, id);
            }
            TrailerDO trailerDO = trailerMapper.selectFirst(trailerCodeQuery);
            if (trailerDO != null) {
                throw exception(VEHICLE_ALREADY_EXISTS01);
            }
        }
    }


    @Override
    public TrailerRespVO getTrailer(Long id) {
        TrailerDO trailerDO = trailerMapper.selectById(id);
        TrailerRespVO trailerRespVO = TrailerConvert.INSTANCE.convert(trailerDO);
        Long companyId = trailerRespVO.getCompanyId();
        DeptDO deptDO = deptMapper.selectById(companyId);
        if (deptDO != null) {
            trailerRespVO.setCompanyName(deptDO.getName());
        }
        //2、车挂的车牌变更记录信息获取
        List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVOS = vehicleLicenseService.getVehicleLicenseList02(id);
        trailerRespVO.setVehicleLicenseSimpleVO(vehicleLicenseSimpleVOS);

        //3、车挂的业户变更记录信息获取
        List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVOS = vehicleOwnershipService.getVehicleOwnershipList02(id);
        trailerRespVO.setVehicleOwnershipSimpleVO(vehicleOwnershipSimpleVOS);

        //4、填充附件信息
        saveFileInfo(trailerRespVO);

        // 5、可装货物
        LambdaQueryWrapper<TrailerCommodityDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrailerCommodityDO::getEntityId, id)
                .eq(TrailerCommodityDO::getDeleted, 0);
        List<TrailerCommodityDO> trailerCommodityDOS = trailerCommodityMapper.selectList(queryWrapper);

        if (CollectionUtils.isNotEmpty(trailerCommodityDOS)) {
            List<Long> commodityIds = trailerCommodityDOS.stream()
                    .map(TrailerCommodityDO::getCommodityId)
                    .collect(Collectors.toList());

            List<CommodityDO> commodityDOS = commodityMapper.selectBatchIds(commodityIds);

            List<String> commodityNames = commodityDOS.stream()
                    .map(CommodityDO::getName)
                    .collect(Collectors.toList());


            trailerRespVO.setCommodityIds(commodityIds);
            trailerRespVO.setCommodityNames(String.join(", ", commodityNames));
        } else {

            trailerRespVO.setCommodityIds(Collections.emptyList());
            trailerRespVO.setCommodityNames("");
        }

        return trailerRespVO;
    }


    /**
     * 初始化设置映射关系和消费方法
     *
     * @param fileRespVO
     * @return
     */
    Map<VehicleTrailerFileEnum, Consumer<List<FileSimpleRespVO>>> initVehicleTrailerFileEnum(TrailerRespVO.FileRespVO fileRespVO) {
        // 创建一个HashMap，用于存储车挂附件枚举值，和对应mainVehicleRespVO的消费操作方法
        Map<VehicleTrailerFileEnum, Consumer<List<FileSimpleRespVO>>> vehicleTrailerFileMap = new HashMap<>();
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_21, fileRespVO::setRegistrationBook);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_22, fileRespVO::setFactoryQualificationCertificate);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_23, fileRespVO::setRoadTransportPermit);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_24, fileRespVO::setConformityCertificate);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_25, fileRespVO::setVehicleRegistrationCertificate);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_26, fileRespVO::setFactoryPackingList);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_27, fileRespVO::setAttachment);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_28, fileRespVO::setOtherFactoryDocuments);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_29, fileRespVO::setPurchaseInvoice);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_30, fileRespVO::setTaxDeclarationForm);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_31, fileRespVO::setPurchaseTaxCertificate);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_32, fileRespVO::setTransportationCheckRecord);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_33, fileRespVO::setTaxPaymentCertificate);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_34, fileRespVO::setTankInspectionReport);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_35, fileRespVO::setVehicleSafetyInspectionReport);
        vehicleTrailerFileMap.put(VehicleTrailerFileEnum.CG_36, fileRespVO::setOtherDocuments);
        return vehicleTrailerFileMap;
    }


    /**
     * 根据不同车挂的枚举，进行对应的消费
     *
     * @param trailerRespVO
     */
    private void saveFileInfo(TrailerRespVO trailerRespVO) {
        Long id = trailerRespVO.getId();
        TrailerRespVO.FileRespVO fileRespVO = new TrailerRespVO.FileRespVO();
        trailerRespVO.setFileRespVO(fileRespVO);
        Map<VehicleTrailerFileEnum, Consumer<List<FileSimpleRespVO>>> setterMap = initVehicleTrailerFileEnum(fileRespVO);
        // 遍历所有的枚举值
        for (VehicleTrailerFileEnum fileEnum : VehicleTrailerFileEnum.values()) {
            LambdaQueryWrapper<FileDO> queryWrapper = new LambdaQueryWrapper<>();
            List<FileSimpleRespVO> fileSimpleRespVOS = new ArrayList<>();
            queryWrapper.eq(FileDO::getCodeBusinessType, fileEnum.getFileCode())
                    .eq(FileDO::getSourceId, id);
            List<FileDO> fileDOS = fileMapper.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(fileDOS)) {
                fileDOS.forEach(iterm -> {
                    FileSimpleRespVO fileSimpleRespVO = new FileSimpleRespVO();
                    fileSimpleRespVO.setName(iterm.getName());
                    fileSimpleRespVO.setUrl(iterm.getUrl());
                    fileSimpleRespVOS.add(fileSimpleRespVO);
                });
            }
            Consumer<List<FileSimpleRespVO>> setter = setterMap.get(fileEnum);
            if (setter != null) {
                // 2、自动对应上各自的setXxx方法，
                setter.accept(fileSimpleRespVOS);
            } else {
                throw exception(TRAILER_NOT_EXISTS, id);
            }
        }
    }

    @Override
    public List<TrailerDO> getTrailerList(Collection<Long> ids) {
        return trailerMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TrailerRespVO> getTrailerPage(TrailerPageReqVO pageReqVO) {
        PageResult<TrailerDO> page1 = trailerMapper.selectPage(pageReqVO);
        PageResult<TrailerRespVO> page2 = TrailerConvert.INSTANCE.convertPage(page1);
        page2.getList().forEach(this::saveFileInfo);
        return page2;
    }

    @Override
    public List<DownTrailerExcelVO> getTrailerList(DownTrailerExcelVO exportReqVO) {
        List<DownTrailerExcelVO> trailerList = trailerMapper.getTrailerList(exportReqVO);
        if (CollectionUtils.isNotEmpty(trailerList)) {
            // 获取所有挂车的ID列表
            List<Long> trailerIds = trailerList.stream().map(DownTrailerExcelVO::getId).collect(Collectors.toList());

            // 查询并处理结果，将其转换为 Map<Long, List<String>>
            Map<Long, List<String>> trailerWithCommodityMap = trailerMapper.getTrailerWithCommoditys(trailerIds)
                    .stream()
                    .collect(Collectors.groupingBy(
                            map -> (Long) map.get("trailerId"),
                            Collectors.mapping(map -> (String) map.get("commodityName"), Collectors.toList())
                    ));

            trailerList.forEach(item -> {
                List<String> commodities = trailerWithCommodityMap.get(item.getId());
                if (CollectionUtils.isNotEmpty(commodities)) {
                    String commodityNames = String.join(",", commodities);
                    item.setCommodityNames(commodityNames);
                }
            });
        }
        return trailerList;
    }

    @Override
    public void batchUpdateTrailer(List<TrailerUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (TrailerUpdateReqVO updateReqVO : updateReqVOList) {
            updateTrailer(updateReqVO);
        }
    }

    @Override
    public void batchDeleteTrailer(List<Long> ids) {
        // 在这里处理批量删除逻辑
        trailerMapper.deleteBatchIds(ids);
    }

    @Override
    public Page<TrailerRespVO> selectPageByKeyword(TrailerKeywordPageReqVO pageReqVO) {
        // 1. 分页查询 Trailer 数据
        Page<TrailerRespVO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        Page<TrailerRespVO> pageResult = trailerMapper.selectPageByKeyword(page, pageReqVO);
        List<TrailerRespVO> records = pageResult.getRecords();

        // 2. 判断记录是否为空
        if (CollectionUtils.isNotEmpty(records)) {
            // 收集所有 Trailer 的 ID
            List<Long> trailerIds = records.stream()
                    .map(TrailerRespVO::getId)
                    .collect(Collectors.toList());

            // 3. 查询所有未删除的 CommodityDO 数据，并转换为 Map<Long, String>
            LambdaQueryWrapper<CommodityDO> commodityQueryWrapper = new LambdaQueryWrapper<>();
            commodityQueryWrapper.eq(CommodityDO::getDeleted, 0);
            List<CommodityDO> commodityDOS = commodityMapper.selectList(commodityQueryWrapper);

            // 将所有的 CommodityDO 转换为 id 和 name 的 Map
            Map<Long, String> commodityMap = CollectionUtils.isNotEmpty(commodityDOS)
                    ? commodityDOS.stream().collect(Collectors.toMap(CommodityDO::getId, CommodityDO::getName))
                    : new HashMap<>();

            // 4. 查询所有 TrailerCommodityDO，一次性获取所有车挂id对应的介质id
            LambdaQueryWrapper<TrailerCommodityDO> trailerCommodityQueryWrapper = new LambdaQueryWrapper<>();
            trailerCommodityQueryWrapper.eq(TrailerCommodityDO::getDeleted, 0)
                    .in(TrailerCommodityDO::getEntityId, trailerIds);
            List<TrailerCommodityDO> trailerCommodityDOS = trailerCommodityMapper.selectList(trailerCommodityQueryWrapper);

            // 5. 根据 trailerId 对 TrailerCommodityDO 进行分组
            Map<Long, List<Long>> trailerToCommodityIdsMap = trailerCommodityDOS.stream()
                    .collect(Collectors.groupingBy(TrailerCommodityDO::getEntityId,
                            Collectors.mapping(TrailerCommodityDO::getCommodityId, Collectors.toList())));

            // 6. 填充每个 TrailerRespVO 的 CommodityNames
            for (TrailerRespVO trailer : records) {
                List<Long> commodityIds = trailerToCommodityIdsMap.get(trailer.getId());

                if (CollectionUtils.isNotEmpty(commodityIds)) {
                    // 通过 commodityMap 过滤并获取对应的 commodityNames
                    String commodityNames = commodityIds.stream()
                            .map(commodityMap::get)
                            .filter(Objects::nonNull) // 过滤掉找不到名称的 commodity
                            .collect(Collectors.joining(", "));

                    trailer.setCommodityNames(commodityNames);
                }
            }
        }

        return pageResult;
    }


    //车挂附件对应关系Map
    private Map<String, String> trailerFileDictDataMap;

    /**
     * 初始化查询出车挂附件对应关系Map,方便后续使用不用反复查询
     */
    @PostConstruct
    private void init() {
        QueryWrapper<DictDataDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", "trailer_file_type");
        List<DictDataDO> dictDataDOS = dictDataMapper.selectList(queryWrapper);
        // 添加逻辑，确保 dictDataDOS 不为 null
        if (dictDataDOS == null) {
            throw new RuntimeException("获取车挂档案附加字典数据失败！");
        }
        // 使用toMap时提供合并函数，处理重复键的情况
        trailerFileDictDataMap = dictDataDOS.stream().collect(
                Collectors.toMap(DictDataDO::getValue, DictDataDO::getLabel,
                        (existing, replacement) -> {
                            // 处理重复键的逻辑，这里简单地选择使用 replacement
                            return replacement;
                        }));

    }

    @Override
    public MainVehicleDownloadVO getTrailerCertificateFile(TrailerDonwloadVO vehicleDonwloadVO) {
        Map<String, Map<String, List<FileDO>>> resultMap = getVehicleStructInfo(vehicleDonwloadVO);
        return VehicleUtils.createZip(resultMap);
    }

    @Override
    public List<HashMap<String, Object>> getExportList() {
        List<HashMap<String, Object>> exportList = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Field[] fields = DownTrailerExcelVO.class.getDeclaredFields();

        for (Field field : fields) {
            // 获取字段的注解
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                String[] values = excelProperty.value();
                if (values.length > 0) {
                    String key = field.getName(); // 使用字段名称作为键
                    String value = values[0]; // 使用 ExcelProperty 注解的第一个值作为值
                    map.put(key, value);
                }
            }
        }

        exportList.add(map);
        return exportList;
    }

    @Override
    public List<DownTrailerExcelVO> importPreviewList(List<DownTrailerExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(TRAILER_IMPORT);
        }

        //查询所有车挂
        LambdaQueryWrapper<TrailerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrailerDO::getDeleted, 0)
                .isNull(TrailerDO::getStatus);
        List<TrailerDO> trailerDOS = trailerMapper.selectList(queryWrapper);
        List<String> trailerTrailerNos = null;
        if (CollUtil.isNotEmpty(trailerDOS)) {
            trailerTrailerNos = trailerDOS.stream().map(TrailerDO::getVehicleTrailerNo).collect(Collectors.toList());
        }

        // 查询所有的所属公司并将公司名和公司ID存入companyMap
        Map<String, Long> companyMap = deptMapper.selectList(new LambdaQueryWrapper<DeptDO>()
                        .eq(DeptDO::getDeleted, 0)
                        .eq(DeptDO::getParentId, 498))
                .stream()
                .collect(Collectors.toMap(DeptDO::getName, DeptDO::getId, (existing, replacement) -> existing));

        //查询所有的外援公司
        LambdaQueryWrapper<CustomerDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(CustomerDO::getDeleted, 0)
                .eq(CustomerDO::getCustomerType, 2)
                .eq(CustomerDO::getIsOut, true);
        List<CustomerDO> customerDOS = customerMapper.selectList(queryWrapper1);

        Map<String, Long> outCompanyMap = null;
        if (CollUtil.isNotEmpty(customerDOS)) {
            outCompanyMap = customerDOS.stream()
                    .collect(Collectors.toMap(CustomerDO::getCustomerName, CustomerDO::getId, (existing, replacement) -> existing));
        }

        //查询所有的介质
        LambdaQueryWrapper<CommodityDO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(CommodityDO::getDeleted, 0);
        List<CommodityDO> commodityDOS = commodityMapper.selectList(queryWrapper2);
        Map<String, Long> commodityMap = null;
        if (CollUtil.isNotEmpty(commodityDOS)) {
            commodityMap = commodityDOS.stream()
                    .collect(Collectors.toMap(CommodityDO::getName, CommodityDO::getId, (existing, replacement) -> existing));
        }

        //查询所有的 车头类型 字典
        List<DictDataRespDTO> vehicleType = dictDataApi.getDictDatas("vehicle_type");
        Map<String, String> vehicleTypeMaps = null;
        if (!CollUtil.isEmpty(vehicleType)) {
            vehicleTypeMaps = vehicleType.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 车挂品牌 字典
        List<DictDataRespDTO> trailerBrand = dictDataApi.getDictDatas("trailer_brand");
        Map<String, String> trailerBrandMaps = null;
        if (!CollUtil.isEmpty(trailerBrand)) {
            trailerBrandMaps = trailerBrand.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 罐体类型 字典
        List<DictDataRespDTO> tankType = dictDataApi.getDictDatas("tank_type");
        Map<String, String> tankTypeMaps = null;
        if (!CollUtil.isEmpty(tankType)) {
            tankTypeMaps = tankType.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 管道连接方式 字典
        List<DictDataRespDTO> pipeConnectionType = dictDataApi.getDictDatas("pipe_connection_type");
        Map<String, String> pipeConnectionTypeMaps = null;
        if (!CollUtil.isEmpty(pipeConnectionType)) {
            pipeConnectionTypeMaps = pipeConnectionType.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 卸货方式 字典
        List<DictDataRespDTO> unloadingType = dictDataApi.getDictDatas("unloading_type");
        Map<String, String> unloadingTypeMaps = null;
        if (!CollUtil.isEmpty(unloadingType)) {
            unloadingTypeMaps = unloadingType.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }


        // 遍历导入的数据进行校验
        for (DownTrailerExcelVO item : importDatas) {
            if (item.getFailData() == null) {
                item.setFailData(new HashMap<>()); // 确保 failData 已初始化
            }

            if (item.getId() == null) {
                item.setIsUpdateSupport(false); // 设置为新增
            } else {
                TrailerDO trailerDO = trailerMapper.selectById(item.getId());
                if (trailerDO == null) {
                    item.setIsUpdateSupport(false); // 设置为新增
                } else {
                    item.setIsUpdateSupport(true); // 设置为更新
                }
            }

            if ((!item.getIsOut().equals("是") && item.getOutCompanyName() != null) ||
                    (item.getIsOut().equals("是") && item.getOutCompanyName() == null)) {
                item.getFailData().put("isOut", "导入的外援承运商选择”是“，以及有对应的外援公司才能进行导入！");
                item.getFailData().put("outCompanyName", "导入的外援承运商选择”是“，以及有对应的外援公司才能进行导入！");
                item.setHasError(true); // 设置为有错误
            }

            //校验字典
            if (item.getVehicleType() != null && !vehicleTypeMaps.containsKey(item.getVehicleType())) {
                String vehicleTypeValue = item.getVehicleType();
                String vehicleTypeErrorMessage = String.format("导入的 \"%s\" 的字典不存在", vehicleTypeValue);
                item.getFailData().put("vehicleType", vehicleTypeErrorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getTrailerBrand() != null && !trailerBrandMaps.containsKey(item.getTrailerBrand())) {
                String trailerBrandValue = item.getTrailerBrand();
                String trailerBrandErrorMessage = String.format("导入的 \"%s\" 的字典不存在", trailerBrandValue);
                item.getFailData().put("trailerBrand", trailerBrandErrorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getTankType() != null && !tankTypeMaps.containsKey(item.getTankType())) {
                String tankTypeValue = item.getTankType();
                String tankTypeErrorMessage = String.format("导入的 \"%s\" 的字典不存在", tankTypeValue);
                item.getFailData().put("tankType", tankTypeErrorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getPipeConnectionType() != null && !pipeConnectionTypeMaps.containsKey(item.getPipeConnectionType())) {
                String pipeConnectionTypeValue = item.getPipeConnectionType();
                String pipeConnectionTypeErrorMessage = String.format("导入的 \"%s\" 的字典不存在", pipeConnectionTypeValue);
                item.getFailData().put("pipeConnectionType", pipeConnectionTypeErrorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getUnloadingType() != null && !unloadingTypeMaps.containsKey(item.getUnloadingType())) {
                String unloadingTypeValue = item.getUnloadingType();
                String unloadingTypeErrorMessage = String.format("导入的 \"%s\" 的字典不存在", unloadingTypeValue);
                item.getFailData().put("unloadingType", unloadingTypeErrorMessage);
                item.setHasError(true); // 设置为有错误
            }


            // 2.校验车牌号是否存在于数据库中的车牌号列表中
            if (item.getVehicleTrailerNo() != null && trailerTrailerNos.contains(item.getVehicleTrailerNo())) {
                item.setHasError(true);
                item.getFailData().put("vehicleTrailerNo", "挂车号已存在");
            }

            // 2.转换日期字段并处理错误
            validateDateFormat(item, "drivingDate", item.getDrivingDate());
            validateDateFormat(item, "transporttime", item.getTransporttime());
            validateDateFormat(item, "bodyReporttime", item.getBodyReporttime());
            validateDateFormat(item, "certificatTime", item.getCertificatTime());


            //3.非空校验
            checkAndSetError(item, item.getCompanyName(), "companyName", "所属公司不能为空");
            checkAndSetError(item, item.getVehicleTrailerNo(), "vehicleTrailerNo", "车挂号不能为空");
            checkAndSetError(item, item.getCertificatTime(), "certificatTime", "登记日期不能为空");
            checkAndSetError(item, item.getVehicleType(), "vehicleType", "车挂类型不能为空");
            checkAndSetError(item, item.getTrailerBrand(), "trailerBrand", "车挂品牌不能为空");
            checkAndSetError(item, item.getVehicleIdenCode(), "vehicleIdenCode", "车辆识别代号不能为空");
            checkAndSetError(item, item.getVehicleColor(), "vehicleColor", "车身颜色不能为空");
            checkAndSetError(item, item.getVehicleMode(), "vehicleMode", "车辆型号不能为空");
            checkAndSetError(item, item.getTankType(), "tankType", "罐体类型不能为空");
            checkAndSetError(item, item.getPipeConnectionType(), "pipeConnectionType", "管道连接方式不能为空");
            checkAndSetError(item, item.getTrailerWeight(), "trailerWeight", "车挂自重(含罐体)不能为空");
            checkAndSetError(item, item.getVerificationmass(), "verificationmass", "核定载质量不能为空");
            checkAndSetError(item, item.getBodyReporttime(), "bodyReporttime", "罐检报告日期不能为空");
            checkAndSetError(item, item.getTransporttime(), "transporttime", "运输证有效期不能为空");
            checkAndSetError(item, item.getDrivingDate(), "drivingDate", "行驶证有效期不能为空");
            checkAndSetError(item, item.getUnloadingType(), "unloadingType", "卸货方式不能为空");
            checkAndSetError(item, item.getCommodityNames(), "commodityNames", "可装货物不能为空");


            // 校验
            checkoutMainVehicle(item, companyMap, outCompanyMap, commodityMap);
        }

        return importDatas;
    }

    private void validateDateFormat(DownTrailerExcelVO item, String fieldName, String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return;
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/M/d");

        try {
            //解析日期字符串
            LocalDate.parse(dateString, inputFormatter);
        } catch (DateTimeParseException e) {
            // 日期格式错误，记录错误信息
            item.getFailData().put(fieldName, "日期格式错误");
            item.setHasError(true);
        }
    }

    // 校验字段是否为空，并设置错误信息
    private void checkAndSetError(DownTrailerExcelVO item, String fieldValue, String fieldName, String errorMessage) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            item.setHasError(true); // 设置为 true
            item.getFailData().put(fieldName, errorMessage); // 添加到 failData 中
        }
    }

    // 针对 BigDecimal 类型的校验方法
    private void checkAndSetError(DownTrailerExcelVO item, BigDecimal fieldValue, String fieldName, String errorMessage) {
        if (fieldValue == null) {
            item.setHasError(true); // 设置为 true
            item.getFailData().put(fieldName, errorMessage); // 添加到 failData 中
        }
    }

    private void checkoutMainVehicle(DownTrailerExcelVO data, Map<String, Long> companyMap, Map<String, Long> outCompanyMap, Map<String, Long> commodityMap) {
        if (data.getFailData() == null) {
            data.setFailData(new HashMap<>()); // 初始化 failData
        }

        // 校验所属公司
        String companyName = data.getCompanyName();
        if (StrUtil.isNotEmpty(companyName)) {
            if (companyMap.containsKey(companyName)) {
                data.setCompanyId(companyMap.get(companyName));
            } else {
                data.setHasError(true);
                data.getFailData().put("companyName", "所属公司不存在");
            }
        }

        // 校验外援承运商
        String outCompanyName = data.getOutCompanyName();
        if (StrUtil.isNotEmpty(outCompanyName)) {
            if (outCompanyMap.containsKey(outCompanyName)) {
                data.setOutCompanyId(outCompanyMap.get(outCompanyName));
            } else {
                data.setHasError(true);
                data.getFailData().put("outCompanyName", "外援承运商不存在");
            }
        }

        // 校验介质
        String commodityNames = data.getCommodityNames();
        if (StrUtil.isNotEmpty(commodityNames)) {
            String[] commodities = commodityNames.split(",");
            for (String commodity : commodities) {
                String trimmedCommodity = commodity.trim();
                if (!commodityMap.containsKey(trimmedCommodity)) {
                    data.setHasError(true);
                    data.getFailData().put("commodityNames", "介质 '" + trimmedCommodity + "' 不存在");
                }
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public ImportResult importTrailerList(DownTrailerExcelVO importReqVo) {
        List<DownTrailerExcelVO> importDatas = importReqVo.getImportDatas();
        // 过滤有效数据
        List<DownTrailerExcelVO> importListData = importDatas.stream()
                .filter(item -> !item.getHasError() || item.getHasError() == null)
                .collect(Collectors.toList());

        List<DownTrailerExcelVO> importListUpdate = new ArrayList<>();
        List<DownTrailerExcelVO> importListInsert = new ArrayList<>();
        //进行分类
        for (DownTrailerExcelVO item : importListData) {
            if (item.getIsUpdateSupport()) {
                importListUpdate.add(item);
            } else {
                importListInsert.add(item);
            }
        }

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 异步处理更新操作
        Future<?> updateFuture = executor.submit(() -> handleUpdates(importListUpdate));

        // 异步处理新增操作
        Future<?> insertFuture = executor.submit(() -> handleInserts(importListInsert));


        // 等待所有任务完成
        try {
            updateFuture.get();
            insertFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导入失败，请重试", e); // 处理异常
        } finally {
            executor.shutdown(); // 关闭线程池
        }

        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // 任务超时，尝试强制关闭
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt(); // 重新设置中断状态
        }

        // 计算导入结果
        int total = importDatas.size();
        int importCount = importListData.size();
        int failCount = total - importCount;

        // 如果有一条成功导入，success 就为 true, 否则为 false
        boolean success = importCount > 0;

        // 构建并返回 ImportResult
        return ImportResult.<DownTrailerExcelVO>builder()
                .total(total)  // 设置导入数据总行数
                .importCount(importCount)  // 设置成功行数
                .failCount(failCount)  // 设置失败行数
                .success(success)  // 设置导入状态
                .data(importListData)  // 返回有效数据
                .build();
    }

    private void handleUpdates(List<DownTrailerExcelVO> updates) {
        if (CollectionUtils.isEmpty(updates)) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/M/d");

            //查询所有的介质
            LambdaQueryWrapper<CommodityDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(CommodityDO::getDeleted, 0);
            List<CommodityDO> commodityDOS = commodityMapper.selectList(queryWrapper2);
            Map<String, Long> commodityMap = null;
            if (CollUtil.isNotEmpty(commodityDOS)) {
                commodityMap = commodityDOS.stream()
                        .collect(Collectors.toMap(CommodityDO::getName, CommodityDO::getId, (existing, replacement) -> existing));
            }

            for (DownTrailerExcelVO update : updates) {

                //获取传过来的介质ids
                String commodityNames = update.getCommodityNames();
                String[] commodities = commodityNames.split(",");
                List<Long> commodityIds = Arrays.stream(commodities)
                        .map(commodityMap::get)
                        .filter(Objects::nonNull) //过滤掉不存在的
                        .collect(Collectors.toList());

                //获取对应的介质ids
                LambdaQueryWrapper<TrailerCommodityDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(TrailerCommodityDO::getEntityId, update.getId())
                        .eq(TrailerCommodityDO::getDeleted, 0);
                List<TrailerCommodityDO> trailerCommodityDOS = trailerCommodityMapper.selectList(queryWrapper);

                if (CollUtil.isNotEmpty(trailerCommodityDOS)) {
                    List<Long> DbCommodityIds = trailerCommodityDOS
                            .stream()
                            .map(TrailerCommodityDO::getCommodityId)
                            .collect(Collectors.toList());

                    //获取需要删除和需要新增的介质
                    List<Long> toDeleteIds = DbCommodityIds.stream()
                            .filter(id -> !commodityIds.contains(id))
                            .collect(Collectors.toList());

                    List<Long> toAddIds = commodityIds.stream()
                            .filter(id -> !DbCommodityIds.contains(id))
                            .collect(Collectors.toList());

                    //批量删除
                    if (CollUtil.isNotEmpty(toDeleteIds)) {
                        trailerCommodityMapper.deleteTrailerCommodityBatch(toDeleteIds, update.getId());
                    }

                    //批量新增
                    if (CollUtil.isNotEmpty(toAddIds)) {
                        trailerCommodityMapper.insertTrailerCommodityBatch(toAddIds, update.getId());
                    }
                }


                // 将字符串日期转换为 LocalDate
                LocalDate certificatTime = LocalDate.parse(update.getCertificatTime(), dateFormatter);
                LocalDate bodyReportTime = LocalDate.parse(update.getBodyReporttime(), dateFormatter);
                LocalDate transportTime = LocalDate.parse(update.getTransporttime(), dateFormatter);
                LocalDate drivingDate = LocalDate.parse(update.getDrivingDate(), dateFormatter);

                // 将 LocalDate 转换为 LocalDateTime，时间部分设为 00:00
                LocalDateTime certificatTimeFormatted = certificatTime.atStartOfDay();
                LocalDateTime bodyReportTimeFormatted = bodyReportTime.atStartOfDay();
                LocalDateTime transportTimeFormatted = transportTime.atStartOfDay();
                LocalDateTime drivingDateFormatted = drivingDate.atStartOfDay();
                TrailerDO trailerDO = trailerMapper.selectById(update.getId());
                //复制并排除不要要的字段
                BeanUtils.copyProperties(trailerDO, update, "companyName", "id", "outCompanyName", "registerTime", "transportDate", "drivingDate", "secondaryMaintenance");

                // 手动设置转换后的 LocalDateTime 字段
                trailerDO.setCertificatTime(certificatTimeFormatted);
                trailerDO.setBodyReporttime(bodyReportTimeFormatted);
                trailerDO.setTransporttime(transportTimeFormatted);
                trailerDO.setDrivingDate(drivingDateFormatted);

                trailerMapper.updateById(trailerDO);
            }
        }
    }

    // 新增操作
    private void handleInserts(List<DownTrailerExcelVO> inserts) {
        if (CollectionUtils.isNotEmpty(inserts)) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/M/d");

            //查询所有的介质
            LambdaQueryWrapper<CommodityDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(CommodityDO::getDeleted, 0);
            List<CommodityDO> commodityDOS = commodityMapper.selectList(queryWrapper2);
            Map<String, Long> commodityMap = null;
            if (CollUtil.isNotEmpty(commodityDOS)) {
                commodityMap = commodityDOS.stream()
                        .collect(Collectors.toMap(CommodityDO::getName, CommodityDO::getId, (existing, replacement) -> existing));
            }

            for (DownTrailerExcelVO insert : inserts) {
                // 将字符串日期转换为 LocalDate
                LocalDate certificatTime = LocalDate.parse(insert.getCertificatTime(), dateFormatter);
                LocalDate bodyReportTime = LocalDate.parse(insert.getBodyReporttime(), dateFormatter);
                LocalDate transportTime = LocalDate.parse(insert.getTransporttime(), dateFormatter);
                LocalDate drivingDate = LocalDate.parse(insert.getDrivingDate(), dateFormatter);

                // 将 LocalDate 转换为 LocalDateTime，时间部分设为 00:00
                LocalDateTime certificatTimeFormatted = certificatTime.atStartOfDay();
                LocalDateTime bodyReportTimeFormatted = bodyReportTime.atStartOfDay();
                LocalDateTime transportTimeFormatted = transportTime.atStartOfDay();
                LocalDateTime drivingDateFormatted = drivingDate.atStartOfDay();

                // 创建 MainVehicleDO 对象
                TrailerDO trailerDO = new TrailerDO();

                // 复制并排除不需要的字段
                BeanUtils.copyProperties(insert, trailerDO, "companyName", "outCompanyName", "id", "registerTime", "transportDate", "drivingDate", "secondaryMaintenance");
                trailerDO.setTankType(Integer.parseInt(insert.getTankType()));
                trailerDO.setTankType(Integer.parseInt(insert.getTankType()));
                trailerDO.setIsOut("是".equals(insert.getIsOut()));
                // 手动设置转换后的 LocalDateTime 字段
                trailerDO.setCertificatTime(certificatTimeFormatted);
                trailerDO.setBodyReporttime(bodyReportTimeFormatted);
                trailerDO.setTransporttime(transportTimeFormatted);
                trailerDO.setDrivingDate(drivingDateFormatted);

                String code = EncodingRulesEnum.VEHICLE_TRAILER_CODE.getBusinessCode();
                String codeByRuleType = encodingRulesService.getCodeByRuleType(code);
                trailerDO.setTrailerCode(codeByRuleType);

                trailerMapper.insert(trailerDO);

                //获取介质，然后找到对应的id新增到关联表
                String[] commodityNamesArray = insert.getCommodityNames().split(",");
                List<Long> commodityIds = Arrays.stream(commodityNamesArray)
                        .map(commodityMap::get)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                //批量新增
                trailerCommodityMapper.insertTrailerCommodityBatch(commodityIds, trailerDO.getId());

                Long id = trailerDO.getId();
                TrailerCommodityDO trailerCommodityDO = new TrailerCommodityDO();
                trailerCommodityDO.setEntityId(id);
                trailerCommodityMapper.insert(trailerCommodityDO);

            }
        }
    }


    /**
     * @param vehicleDonwloadVO
     * @return 数据库数据关系结构集合对象
     */
    private Map<String, Map<String, List<FileDO>>> getVehicleStructInfo(TrailerDonwloadVO vehicleDonwloadVO) {
        Map<String, Map<String, List<FileDO>>> resultMap = new HashMap<>();
        //获得车挂资源id列表和车挂附件类型
        List<Long> trailerIds = vehicleDonwloadVO.getTrailerIds();
        List<Long> trailerFileTypes = vehicleDonwloadVO.getTrailerFileTypes();
        //1、车挂车牌
        List<TrailerDO> trailerDOS = trailerMapper.selectBatchIds(trailerIds);

        trailerDOS.parallelStream().forEach(trailerDO -> {
            Long trailerId = trailerDO.getId();
            String vehicleTrailerNo = trailerDO.getVehicleTrailerNo();
            // 2、车挂车牌下的所有选定附件
            Map<String, List<FileDO>> typeMap = new HashMap<>();
            QueryWrapper<FileDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("source_id", trailerId)
                    .in("code_business_type", trailerFileTypes);
            List<FileDO> fileList = fileMapper.selectList(queryWrapper);
            for (Long trailerFileType : trailerFileTypes) {
                //查询出来则插入
                if (CollectionUtils.isNotEmpty(fileList)) {
                    List<FileDO> filteredList = fileList.stream()
                            .filter(file -> String.valueOf(trailerFileType).equals(file.getCodeBusinessType()))
                            .collect(Collectors.toList());
                    typeMap.put(String.valueOf(trailerFileType), filteredList);
                }
            }
            resultMap.put(vehicleTrailerNo, typeMap);
        });
        return resultMap;
    }


    private void validateTrailer(TrailerImportExcelVO excelVO) {
        DataPermissionUtils.executeIgnore(() -> {
            String vehicleTrailerNo = excelVO.getVehicleTrailerNo();
            TrailerDO trailerDO = trailerMapper.selectOne(TrailerDO::getVehicleTrailerNo, vehicleTrailerNo);
            if (!ObjectUtils.isEmpty(trailerDO)) {
                throw new RuntimeException("车挂号xxx:" + vehicleTrailerNo + "已存在！");
            }
            String vehicleIdenCode = excelVO.getVehicleIdenCode();
            TrailerDO trailerDO1 = trailerMapper.selectOne(TrailerDO::getVehicleIdenCode, vehicleIdenCode);
            if (!ObjectUtils.isEmpty(trailerDO1)) {
                throw new RuntimeException("车辆识别代号:" + vehicleIdenCode + "已存在！");
            }

            String vehicleType = excelVO.getVehicleType();
            TrailerDO trailerDO2 = trailerMapper.selectOne(TrailerDO::getVehicleType, vehicleType);
            if (!ObjectUtils.isEmpty(trailerDO2)) {
                throw new RuntimeException("车辆类型xxx:" + vehicleType + "已存在！");
            }
            String vehicleBrand = excelVO.getTrailerBrand();
            TrailerDO trailerDO3 = trailerMapper.selectOne(TrailerDO::getTrailerBrand, vehicleBrand);
            if (!ObjectUtils.isEmpty(trailerDO3)) {
                throw new RuntimeException("车辆品牌:" + vehicleBrand + "已存在！");
            }
            String vehicleColor = excelVO.getVehicleColor();
            TrailerDO trailerDO4 = trailerMapper.selectOne(TrailerDO::getVehicleColor, vehicleColor);
            if (!ObjectUtils.isEmpty(trailerDO4)) {
                throw new RuntimeException("车身颜色:" + vehicleColor + "已存在！");
            }
            String vehicleMode = excelVO.getVehicleMode();
            TrailerDO trailerDO5 = trailerMapper.selectOne(TrailerDO::getVehicleMode, vehicleMode);
            if (!ObjectUtils.isEmpty(trailerDO5)) {
                throw new RuntimeException("车辆型号:" + vehicleMode + "已存在！");
            }
            String tankType = excelVO.getTankType();
            TrailerDO trailerDO6 = trailerMapper.selectOne(TrailerDO::getTankType, tankType);
            if (!ObjectUtils.isEmpty(trailerDO6)) {
                throw new RuntimeException("罐体类型:" + tankType + "已存在！");
            }
        });
    }

    @Override
    public void updateStatus(Long id) {
        UpdateWrapper<TrailerDO> updateWrapper = new UpdateWrapper<>();
        TrailerDO trailerDO = trailerMapper.selectById(id);
        if (ObjectUtils.isEmpty(trailerDO)) {
            throw exception(TRAILER_NO_QUREY);
        }
        updateWrapper.eq("id", id);
        Integer status = trailerDO.getTrailerStatus();
        //状态修改取反
        if (ObjectUtil.isEmpty(status) || status.equals(0)) {
            updateWrapper.set("trailer_status", 1);
            //禁用 同时更新车辆管理的数据
            LambdaUpdateWrapper<CarDO> queryWrapper = new LambdaUpdateWrapper<>();
            queryWrapper.eq(CarDO::getTrailerId, id);
            CarDO carDO = carMapper.selectOne(queryWrapper);
            if (carDO != null) {
                queryWrapper.set(CarDO::getTrailerId, null);
                carMapper.update(null, queryWrapper);
            }

            //如果是使用中的状态，更新为空闲
            if (!trailerDO.getIsIdle()) {
                updateWrapper.set("is_idle", true);
            }

        } else {
            updateWrapper.set("trailer_status", 0).set("is_idle", false);
        }
        trailerMapper.update(null, updateWrapper);
    }

    @Override
    public TrailerBasicRespVO getTrailerById(Long id) {
        LambdaQueryWrapper<TrailerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(TrailerDO::getStatus)
                .eq(TrailerDO::getId, id);
        TrailerDO trailerDO = trailerMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(trailerDO)) {
            throw exception(new ErrorCode(234386542, "车挂不存在"));
        }
        TrailerBasicRespVO trailerBasicRespVO = TrailerConvert.INSTANCE.convert05(trailerDO);
        List<Map<String, Object>> commodityInfoList = this.getCommodityInfoById(id);
        trailerBasicRespVO.setCmmodityInfoList(commodityInfoList);
        List<Long> commodityIds = commodityInfoList.stream()
                .map(map -> (Long) map.get("id"))
                .collect(Collectors.toList());
        trailerBasicRespVO.setCmmodityIds(commodityIds);
        return trailerBasicRespVO;
    }

    @Override
    public List<TrailerListDTO> getTrailerList(TrailerListReqVO param) {
        MPJLambdaWrapper<TrailerDO> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.select(TrailerDO::getId, TrailerDO::getVehicleTrailerNo, TrailerDO::getIsIdle)
                .leftJoin(DeptDO.class, DeptDO::getId, TrailerDO::getCompanyId)
                .eq(ObjectUtil.isNotEmpty(param.getIsIdle()), TrailerDO::getIsIdle, param.getIsIdle())
                .eq(ObjectUtil.isNotEmpty(param.getCompanyId()), TrailerDO::getCompanyId, param.getCompanyId())
                .eq(ObjectUtil.isNotEmpty(param.getDeptId()), TrailerDO::getDeptId, param.getDeptId())
                .eq(ObjectUtil.isNotEmpty(param.getIsOut()), TrailerDO::getIsOut, param.getIsOut())
                .eq(TrailerDO::getDeleted, 0)
                .eq(TrailerDO::getTrailerStatus, 0)
                .isNull(TrailerDO::getStatus)
                .disableSubLogicDel();


        if (ObjectUtil.isNotEmpty(param.getDeptId())) {
            mpjLambdaWrapper.eq(TrailerDO::getDeptId, param.getDeptId())
                    .eq(DeptDO::getDeleted, 0)
                    .selectAs(DeptDO::getName, "deptName");
        }

        List<TrailerDO> trailerDOS = trailerMapper.selectList(mpjLambdaWrapper);

        List<TrailerListDTO> trailerListDTOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(trailerDOS)) {
            trailerDOS.forEach(trailerDO -> {
                TrailerListDTO trailerListDTO = new TrailerListDTO();
                trailerListDTO.setId(trailerDO.getId());
                trailerListDTO.setVehicleTrailerNo(trailerDO.getVehicleTrailerNo());
                trailerListDTO.setDeptName(trailerDO.getDeptName());
                trailerListDTO.setIsIdle(trailerDO.getIsIdle());
                trailerListDTOS.add(trailerListDTO);
            });
        }

        return trailerListDTOS;
    }

    @Override
    public void getTrailerArchivetDownload(Long id, HttpServletResponse response) {
        try {
            // 获取模板路径和文件名
            //String templatePath = "fengmao-module-information/fengmao-module-information-biz/src/main/resources/template/mainVehicleTemplate.xlsx";
            URL resource = getClass().getClassLoader().getResource("template/trailerTemplate.xlsx");
            String templatePath = resource.getPath();
            String outputFileName = "车挂档案.xlsx";
            // 设置响应头
            // 设置响应的内容类型和字符编码，指定为Excel文件
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 设置文件名
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(outputFileName, "UTF-8"));
            // 填充模板数据
            fillExcelTemplate(templatePath, id, response.getOutputStream());
            // 关闭输出流
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            throw exception(new ErrorCode(1254239, "车挂档案下载出错！"));
        }
    }

    @Override
    public void getTrailerArchiveDownload02(List<Long> ids, HttpServletResponse response) {
        try {
            // 创建临时文件夹
            String tempFolderName = "车挂档案";
            File tempFolder = VehicleUtils.createTempFolder(tempFolderName);

            // 生成多个表格文件
            for (Long id : ids) {
                generateExcelFile(tempFolder, id);
            }

            // 临时文件夹下的文件压缩文件夹
            String tempFolderZipPath = "trailerTempFolder.zip";
            File zipFile = VehicleUtils.zipFolder(tempFolder, tempFolderZipPath);


            String zipName = "车挂档案";
            VehicleUtils.zipFileWriteToResponse(zipFile, zipName, response);

            // 删除临时文件和文件夹
            //tempFolder.delete();
            //zipFile.delete();
            deleteTempFolderAsync(zipFile, tempFolder);
        } catch (Exception e) {
            throw exception(TRAILER_NO_DOCMENT_DOWNLOAD);
        }
    }

    /**
     * 生成构建Excel附件
     *
     * @param folder
     * @param id
     */
    private void generateExcelFile(File folder, Long id) throws FileNotFoundException {
        // 获取模板路径和文件名
        URL resource = getClass().getClassLoader().getResource("template/trailerTemplate.xlsx");
        String templatePath = resource.getPath();
        TrailerDO trailerDO = trailerMapper.selectById(id);
        // 生成Excel文件
        File outputFile = new File(folder, "车挂档案_" + trailerDO.getVehicleTrailerNo() + ".xlsx");
        FileOutputStream fos = new FileOutputStream(outputFile);
        fillExcelTemplate(templatePath, id, fos);
    }

    /**
     * 异步删除本地临时附件
     *
     * @param zipFile    保存在本地的压缩包对象
     * @param tempFolder 附件对象
     * @return
     */
    @Async
    public Future<Void> deleteTempFolderAsync(File zipFile, File tempFolder) {
        //2、删除本地压缩包
        zipFile.delete();
        //1、删除本地临时文件
        deleteTempFolder(tempFolder);
        return AsyncResult.forValue(null);
    }

    /**
     * 删除本地临时文件（填充后的模板文件）
     *
     * @param tempFolder
     */
    public static void deleteTempFolder(File tempFolder) {
        if (tempFolder.isDirectory()) {
            for (File file : tempFolder.listFiles()) {
                deleteTempFolder(file);
            }
        }
        tempFolder.delete();
    }

    /**
     * 填充数据02
     *
     * @param templatePath
     * @param id
     * @param outputStream
     * @return
     */
    private void fillExcelTemplate(String templatePath, Long id, OutputStream outputStream) {
        //本地存储查看
        //EasyExcel.write("车挂档案测试.xlsx").withTemplate(templatePath).build();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(templatePath).build();
        TrailerDO trailerDO = trailerMapper.selectById(id);
        if (ObjectUtils.isEmpty(trailerDO)) {
            throw exception(TRAILER_NOT_EXISTS);
        }
        saveSheet1(excelWriter, trailerDO);
        saveSheet2(excelWriter, trailerDO);
        saveSheet3(excelWriter, trailerDO);
        saveSheet4(excelWriter, trailerDO);
        saveSheet5(excelWriter, trailerDO);
        saveSheet6(excelWriter, trailerDO);
        saveSheet7(excelWriter, trailerDO);

        excelWriter.finish();
    }

    /**
     * 封面Sheet填充
     *
     * @param excelWriter
     * @param
     */
    private void saveSheet1(ExcelWriter excelWriter, TrailerDO trailerDO) {
        HashMap<String, String> map1 = new HashMap<>();

        WriteSheet writeSheet1 = EasyExcel.writerSheet("封面").build();
        map1.put("vehicleTrailerNo", trailerDO.getVehicleTrailerNo());
        excelWriter.fill(map1, writeSheet1);
    }

    /**
     * 车辆基本信息Sheet填充
     *
     * @param excelWriter
     * @param
     */
    private void saveSheet2(ExcelWriter excelWriter, TrailerDO trailerDO) {
        LambdaQueryWrapper<FileDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileDO::getSourceId, trailerDO.getId());
        List<FileDO> fileDOS = fileMapper.selectList(queryWrapper);
        //粘贴初次办理或变更《道路运输证》时，车辆正面偏右侧45°的3寸彩色照片
        String imageUrl = null;
        if (CollectionUtils.isNotEmpty(fileDOS)) {
            imageUrl = fileDOS.get(0).getUrl();
        }

        List<VehicleLicenseSimpleVO> vehicleLicenseList = vehicleLicenseService.getVehicleLicenseList02(trailerDO.getId());
        for (int i = 0; i < vehicleLicenseList.size(); i++) {
            if (i == 0) {
                vehicleLicenseList.get(i).setLicensePlateChangeCount("首次核发");
            } else {
                vehicleLicenseList.get(i).setLicensePlateChangeCount("牌号变更" + i);
            }
        }
        //2.道路运输证信息
        List<VehicleOwnershipSimpleVO> ownershipList = vehicleOwnershipService.getVehicleOwnershipList02(trailerDO.getId());
        for (int i = 0; i < ownershipList.size(); i++) {
            VehicleOwnershipSimpleVO vehicleOwnershipSimpleVO = ownershipList.get(i);
            if (i == 0) {
                vehicleOwnershipSimpleVO.setNumber("初次登记");
            } else {
                vehicleOwnershipSimpleVO.setNumber("变更次数" + i);
            }
        }
        WriteSheet writeSheet = EasyExcel.writerSheet("1---车辆基本信息").build();
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).direction(WriteDirectionEnum.VERTICAL).build();
        // 车辆号牌信息填充
        excelWriter.fill(new FillWrapper("data1", vehicleLicenseList), fillConfig, writeSheet);
        // 道路运输证信息填充
        excelWriter.fill(new FillWrapper("data2", ownershipList), fillConfig, writeSheet);
        //其他车挂的基本信息
        //trailerDO
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 格式化LocalDateTime
        HashMap<String, Object> map = new HashMap<>();
        //处理图片
        try {
            byte[] fileBytesFromUrl = IoUtils.getFileBytesFromUrl(imageUrl);
            if (fileBytesFromUrl.length > 0) {
                map.put("imageUrl", fileBytesFromUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        map.put("vehicleTrailerNo", trailerDO.getVehicleTrailerNo());
        map.put("originalPrice", trailerDO.getOriginalPrice());
        map.put("userYears", trailerDO.getUserYears());
        map.put("residualRate", trailerDO.getResidualRate());
        map.put("vehicleType", trailerDO.getVehicleType());
        map.put("vehicleBrand", trailerDO.getTrailerBrand());
        map.put("manufacturerName", trailerDO.getManufacturerName());
        map.put("outside", trailerDO.getOutside());
        map.put("innerside", trailerDO.getInnerside());
        map.put("totalmass", trailerDO.getTotalmass());
        map.put("verificationmass", trailerDO.getVerificationmass());
        excelWriter.fill(map, writeSheet);
    }

    private void saveSheet3(ExcelWriter excelWriter, TrailerDO trailerDO) {
        //
        WriteSheet writeSheet3 = EasyExcel.writerSheet("2---车辆检测好评定登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("vehicleTrailerNo", trailerDO.getVehicleTrailerNo());
        excelWriter.fill(map2, writeSheet3);
        List<EvaluationVO> evaluationList02 = evaluationService.getEvaluationList02(trailerDO.getId());
        for (int i = 0; i < evaluationList02.size(); i++) {
            EvaluationVO evaluationVO = evaluationList02.get(i);
            evaluationVO.setOrder(i + 1);
        }
        excelWriter.fill(evaluationList02, writeSheet3);

    }

    private void saveSheet4(ExcelWriter excelWriter, TrailerDO trailerDO) {
        //
        WriteSheet writeSheet3 = EasyExcel.writerSheet("3---车辆维护和修理登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("vehicleTrailerNo", trailerDO.getVehicleTrailerNo());
        excelWriter.fill(map2, writeSheet3);
        List<MaintenanceRepairVO> maintenanceRepairList02 = maintenanceRepairService.getMaintenanceRepairList02(trailerDO.getId());
        for (int i = 0; i < maintenanceRepairList02.size(); i++) {
            MaintenanceRepairVO maintenanceRepairVO = maintenanceRepairList02.get(i);
            maintenanceRepairVO.setOrder(i + 1);
        }
        excelWriter.fill(maintenanceRepairList02, writeSheet3);
    }

    private void saveSheet5(ExcelWriter excelWriter, TrailerDO trailerDO) {
        //
        WriteSheet writeSheet3 = EasyExcel.writerSheet("4---车辆主要部件更换登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("vehicleTrailerNo", trailerDO.getVehicleTrailerNo());
        excelWriter.fill(map2, writeSheet3);
        List<PartReplacementVO> partReplacementList02 = partReplacementService.getPartReplacementList02(trailerDO.getId());
        for (int i = 0; i < partReplacementList02.size(); i++) {
            PartReplacementVO partReplacementVO = partReplacementList02.get(i);
            partReplacementVO.setOrder(i + 1);
        }
        excelWriter.fill(partReplacementList02, writeSheet3);
    }

    private void saveSheet6(ExcelWriter excelWriter, TrailerDO trailerDO) {
        //
        WriteSheet writeSheet3 = EasyExcel.writerSheet("5---车辆变更登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("vehicleTrailerNo", trailerDO.getVehicleTrailerNo());
        excelWriter.fill(map2, writeSheet3);
        List<CarChangeVO> carChangeList02 = carChangeService.getCarChangeList02(trailerDO.getId());
        for (int i = 0; i < carChangeList02.size(); i++) {
            CarChangeVO carChangeVO = carChangeList02.get(i);
            carChangeVO.setOrder(i + 1);
        }
        excelWriter.fill(carChangeList02, writeSheet3);
    }

    private void saveSheet7(ExcelWriter excelWriter, TrailerDO trailerDO) {
        //
        WriteSheet writeSheet3 = EasyExcel.writerSheet("6--车辆机损事故登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("vehicleTrailerNo", trailerDO.getVehicleTrailerNo());
        excelWriter.fill(map2, writeSheet3);
        List<AccidentVO> accidentList02 = accidentService.getAccidentList02(trailerDO.getId());
        for (int i = 0; i < accidentList02.size(); i++) {
            AccidentVO accidentVO = accidentList02.get(i);
            accidentVO.setOrder(i + 1);
        }
        excelWriter.fill(accidentList02, writeSheet3);
    }


    @Override
    public TrailerPrintingVO getTrailerArchivetPrinting(Long id) {

        TrailerDO trailerDO = trailerMapper.selectById(id);
//        TrailerPrintingVO trailerPrintingVO = TrailerConvert.INSTANCE.convert03(trailerDO);
        TrailerPrintingVO trailerPrintingVO = new TrailerPrintingVO();
        BeanUtils.copyProperties(trailerDO, trailerPrintingVO);
        LambdaQueryWrapper<FileDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileDO::getSourceId, id);
        List<FileDO> fileDOS = fileMapper.selectList(queryWrapper);
//        粘贴初次办理或变更《道路运输证》时，车辆正面偏右侧45°的3寸彩色照片
        String imageUrl = null;
        if (CollectionUtils.isNotEmpty(fileDOS)) {
            imageUrl = fileDOS.get(0).getUrl();
            trailerPrintingVO.setImageUrl(imageUrl);
        }

        List<VehicleLicenseSimpleVO> vehicleLicenseList = vehicleLicenseService.getVehicleLicenseList02(id);
        for (int i = 0; i < vehicleLicenseList.size(); i++) {
            if (i == 0) {
                vehicleLicenseList.get(i).setLicensePlateChangeCount("首次核发");
            } else {
                vehicleLicenseList.get(i).setLicensePlateChangeCount("牌号变更" + i);
            }
        }
        trailerPrintingVO.setVehicleLicenseSimpleVO(vehicleLicenseList);
        //2.道路运输证信息
        List<VehicleOwnershipSimpleVO> ownershipList = vehicleOwnershipService.getVehicleOwnershipList02(id);
        for (int i = 0; i < ownershipList.size(); i++) {
            VehicleOwnershipSimpleVO vehicleOwnershipSimpleVO = ownershipList.get(i);
            if (i == 0) {
                vehicleOwnershipSimpleVO.setNumber("初次登记");
            } else {
                vehicleOwnershipSimpleVO.setNumber("变更次数" + i);
            }
        }
        trailerPrintingVO.setVehicleOwnershipSimpleVO(ownershipList);
        return trailerPrintingVO;
    }


    @Override
    public List<EvaluationVO> getTrailerPrintingList2(Long id) {
        List<EvaluationVO> evaluationList02 = evaluationService.getEvaluationList02(id);
        for (int i = 0; i < evaluationList02.size(); i++) {
            EvaluationVO evaluationVO = evaluationList02.get(i);
            evaluationVO.setOrder(i + 1);
        }
        return evaluationList02;
    }

    @Override
    public List<MaintenanceRepairVO> getTrailerPrintingList3(Long id) {
        List<MaintenanceRepairVO> maintenanceRepairList03 = maintenanceRepairService.getMaintenanceRepairList02(id);
        for (int i = 0; i < maintenanceRepairList03.size(); i++) {
            MaintenanceRepairVO maintenanceRepairVO = maintenanceRepairList03.get(i);
            maintenanceRepairVO.setOrder(i + 1);
        }
        return maintenanceRepairList03;
    }

    @Override
    public List<PartReplacementVO> getTrailerPrintingList4(Long id) {
        List<PartReplacementVO> partReplacementList04 = partReplacementService.getPartReplacementList02(id);
        for (int i = 0; i < partReplacementList04.size(); i++) {
            PartReplacementVO partReplacementVO = partReplacementList04.get(i);
            partReplacementVO.setOrder(i + 1);
        }
        return partReplacementList04;
    }

    @Override
    public List<CarChangeVO> getTrailerPrintingList5(Long id) {
        List<CarChangeVO> maintenanceRepairList05 = carChangeService.getCarChangeList02(id);
        for (int i = 0; i < maintenanceRepairList05.size(); i++) {
            CarChangeVO carChangeVO = maintenanceRepairList05.get(i);
            carChangeVO.setOrder(i + 1);
        }
        return maintenanceRepairList05;
    }


    @Override
    public List<AccidentVO> getTrailerPrintingList6(Long id) {
        List<AccidentVO> accidentList06 = accidentService.getAccidentList02(id);
        for (int i = 0; i < accidentList06.size(); i++) {
            AccidentVO accidentVO = accidentList06.get(i);
            accidentVO.setOrder(i + 1);
        }
        return accidentList06;
    }

    //2---车辆检测好评定登记表
    @Override
    public Long insertEvaluation(EvaluationVO createReqVO) {
        Long evaluation = evaluationService.createEvaluation(createReqVO);
        return evaluation;
    }

    @Override
    public void deletEvaluation(Long id, Long evaluationId) {
        evaluationService.deleteEvaluation(id, evaluationId);
    }

    @Override
    public List<EvaluationVO> getEvaluationList02(Long id) {
        List<EvaluationVO> evaluationList02 = evaluationService.getEvaluationList02(id);
        for (int i = 0; i < evaluationList02.size(); i++) {
            EvaluationVO evaluationVO = evaluationList02.get(i);
            evaluationVO.setOrder(i + 1);
        }
        return evaluationList02;
    }

    //     6--车辆机损事故登记
    @Override
    public Long insertAccident(AccidentVO createReqVO) {
        Long accident = accidentService.createAccident(createReqVO);
        return accident;
    }

    @Override
    public void deletAccident(Long id, Long accidentId) {
        accidentService.deleteAccident(id, accidentId);
    }

    @Override
    public List<AccidentVO> getAccidentList02(Long id) {
        List<AccidentVO> accidentList02 = accidentService.getAccidentList02(id);
        for (int i = 0; i < accidentList02.size(); i++) {
            AccidentVO accidentVO = accidentList02.get(i);
            accidentVO.setOrder(i + 1);
        }
        return accidentList02;
    }

    //     5---车辆变更登记表
    @Override
    public Long insertCarChange(CarChangeVO createReqVO) {
        Long carChange = carChangeService.createCarChange(createReqVO);
        return carChange;
    }

    @Override
    public void deletCarChange(Long id, Long carChangeId) {
        carChangeService.deleteCarChange02(id, carChangeId);
    }

    @Override
    public List<CarChangeVO> getCarChangeList02(Long id) {
        List<CarChangeVO> carChangeList02 = carChangeService.getCarChangeList02(id);
        for (int i = 0; i < carChangeList02.size(); i++) {
            CarChangeVO carChangeVO = carChangeList02.get(i);
            carChangeVO.setOrder(i + 1);
        }
        return carChangeList02;
    }

    //    3---车辆维护和修理登记表
    @Override
    public Long insertMaintenanceRepair(MaintenanceRepairVO createReqVO) {
        Long maintenanceRepair = maintenanceRepairService.createMaintenanceRepair(createReqVO);
        return maintenanceRepair;
    }

    @Override
    public void deletMaintenanceRepair(Long id, Long maintenanceRepairId) {
        maintenanceRepairService.deleteMaintenanceRepair02(id, maintenanceRepairId);
    }

    @Override
    public List<MaintenanceRepairVO> getMaintenanceRepairList02(Long id) {
        List<MaintenanceRepairVO> maintenanceRepairList02 = maintenanceRepairService.getMaintenanceRepairList02(id);
        for (int i = 0; i < maintenanceRepairList02.size(); i++) {
            MaintenanceRepairVO maintenanceRepairVO = maintenanceRepairList02.get(i);
            maintenanceRepairVO.setOrder(i + 1);
        }
        return maintenanceRepairList02;
    }

    //    4---车辆主要部件更换登记表
    @Override
    public Long insertPartReplacement(PartReplacementVO createReqVO) {
        Long partReplacement = partReplacementService.createPartReplacement(createReqVO);
        return partReplacement;
    }

    @Override
    public void deletPartReplacement(Long id, Long partReplacementId) {
        partReplacementService.deletePartReplacement02(id, partReplacementId);
    }

    @Override
    public List<PartReplacementVO> getPartReplacementList02(Long id) {
        List<PartReplacementVO> partReplacementList02 = partReplacementService.getPartReplacementList02(id);
        for (int i = 0; i < partReplacementList02.size(); i++) {
            PartReplacementVO partReplacementVO = partReplacementList02.get(i);
            partReplacementVO.setOrder(i + 1);
        }
        return partReplacementList02;
    }

    @Override
    public TrailerHistoryVO listHistoryFile(Long trailerId) {
        TrailerHistoryVO trailerHistoryVO = new TrailerHistoryVO();
        List<String> trailerCodeBusinessTypes = getTrailerCodeBusinessTypes();
        //获得历史附件
        List<FileDO> fileDOList = trailerMapper.listHistoryFile(trailerId, trailerCodeBusinessTypes);
        //附件对应
        Map<String, List<TrailerHistoryVO.FileInfo>> fileInfoList = new HashMap<>(22);
        buildFileInfoList(fileDOList, fileInfoList);
        trailerHistoryVO.setSourceId(trailerId);
        trailerHistoryVO.setFileInfo(fileInfoList);
        return trailerHistoryVO;
    }


    /**
     * 对应不同类型填充设置
     *
     * @param fileInfoList
     */
    private void buildFileInfoList(List<FileDO> fileDOList,
                                   Map<String, List<TrailerHistoryVO.FileInfo>> fileInfoList) {
        List<TrailerHistoryVO.FileInfo> fileInfo = BeanUtil.copyToList(fileDOList, TrailerHistoryVO.FileInfo.class);
        List<VehicleTrailerFileEnum> vehicleTrailerFileEnums = new ArrayList<>(22);
        Collections.addAll(vehicleTrailerFileEnums, VehicleTrailerFileEnum.values());

        for (VehicleTrailerFileEnum vehicleMainFileEnum : vehicleTrailerFileEnums) {
            List<TrailerHistoryVO.FileInfo> list = new ArrayList<>(22);
            fileInfo.forEach(item -> {
                if (vehicleMainFileEnum.getFileCode().equals(item.getCodeBusinessType())) {
                    list.add(item);
                }
            });
            //
            fileInfoList.put(vehicleMainFileEnum.getName(), list);
        }
    }

    /**
     * 获得属于车头附件的唯一标识集合
     *
     * @return
     */
    private List<String> getTrailerCodeBusinessTypes() {
        List<String> list = new ArrayList<>(22);
        List<VehicleTrailerFileEnum> vehicleMainFileEnumList = new ArrayList<>(22);
        Collections.addAll(vehicleMainFileEnumList, VehicleTrailerFileEnum.values());

        for (VehicleTrailerFileEnum fileEnum : vehicleMainFileEnumList) {
            list.add(fileEnum.getFileCode());
        }
        return list;
    }


    @Override
    public List<Map<String, Object>> getCommodityInfoById(Long id) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        TrailerDO trailerDO = trailerMapper.selectById(id);
        if (ObjectUtil.isNotEmpty(trailerDO)) {
            LambdaQueryWrapperX<TrailerCommodityDO> queryWrapper = new LambdaQueryWrapperX<>();
            queryWrapper.eq(TrailerCommodityDO::getEntityId, id);
            List<TrailerCommodityDO> trailerCommodityDOS = trailerCommodityMapper.selectList(queryWrapper);
            List<Long> commodityIds = trailerCommodityDOS.stream()
                    .map(TrailerCommodityDO::getCommodityId)
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(commodityIds)) {
                LambdaQueryWrapperX<CommodityDO> queryWrapper1 = new LambdaQueryWrapperX<>();
                queryWrapper1.in(CommodityDO::getId, commodityIds);
                List<CommodityDO> commodityDOS = commodityMapper.selectList(queryWrapper1);
                commodityDOS.stream().forEach(commodityDO -> {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("id", commodityDO.getId());
                    resultMap.put("commodityName", commodityDO.getName());
                    resultList.add(resultMap);
                });
            }
        }

        return resultList;
    }
}


