package com.fmyd888.fengmao.module.information.service.mainvehicle;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.io.IoUtils;
import com.fmyd888.fengmao.framework.common.util.string.StrUtils;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.security.core.LoginUser;
import com.fmyd888.fengmao.framework.security.core.util.SecurityFrameworkUtils;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.*;
import com.fmyd888.fengmao.module.information.convert.mainvehicle.MainVehicleConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.mainvehicle.MainVehicleMapper;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.enums.vehicle.VehicleMainFileEnum;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.information.service.vehicle.*;
import com.fmyd888.fengmao.module.information.utils.VehicleUtils;
import com.fmyd888.fengmao.module.infra.controller.admin.file.vo.file.FileSimpleRespVO;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import com.fmyd888.fengmao.module.infra.dal.mysql.file.FileMapper;
import com.fmyd888.fengmao.module.system.api.dict.DictDataApi;
import com.fmyd888.fengmao.module.system.api.dict.dto.DictDataRespDTO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.dict.DictDataMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import io.micrometer.core.lang.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 车头档案 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class MainVehicleServiceImpl implements MainVehicleService {

    @Resource
    private MainVehicleMapper mainVehicleMapper;
    @Resource
    private VehicleLicenseService vehicleLicenseService;
    @Resource
    private VehicleOwnershipService vehicleOwnershipService;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private CarMapper carMapper;
    @Resource
    private DeptMapper deptMapper;
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
    @Autowired
    private DictDataApi dictDataApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMainVehicle(MainVehicleCreateReqVO createReqVO) {
        String plateNumber = createReqVO.getPlateNumber();
        String vehicleFrame = createReqVO.getVehicleFrame();
        String vehicleCode = createReqVO.getVehicleCode();
        validateMainVehicleExists(null, plateNumber,vehicleFrame,vehicleCode);
        // 1、插入车头基础信息
        MainVehicleDO mainVehicle = MainVehicleConvert.INSTANCE.convert(createReqVO);

        // 1.2 车头编码当前端传过来直接使用，不传则自动生成
        String code = EncodingRulesEnum.VEHICLE_MAIN_CODE.getBusinessCode();
        Boolean modifiable = encodingRulesService.isFillOverWrite(code);
        // 前端传值 and 并且可以修改
        if(StrUtils.isEmpty(vehicleCode) && modifiable){
            String vehicleCode1 = encodingRulesService.getCodeByRuleType(code);
            mainVehicle.setVehicleCode(vehicleCode1);
        }
        // 1.2 使用年限 = 当前日期 - 登记日期    ，
        LocalDateTime registerTime = mainVehicle.getRegisterTime();
        int userYears = useYearCompute(registerTime);
        mainVehicle.setUserYears(userYears);
        mainVehicleMapper.insert(mainVehicle);
        Long mainVehicleId = mainVehicle.getId();
        //2、插入车头的车牌变更记录信息
        List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVO = createReqVO.getVehicleLicenseSimpleVO();
        if ( CollectionUtils.isNotEmpty(vehicleLicenseSimpleVO)) {
            vehicleLicenseSimpleVO.forEach(iterm -> {
                iterm.setMainVehicleId(mainVehicleId);
            });
            vehicleLicenseService.createVehicleLicenseList(vehicleLicenseSimpleVO);
        }

        //3、插入业户名称变更记录信息
        List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVO = createReqVO.getVehicleOwnershipSimpleVO();
        if ( CollectionUtils.isNotEmpty(vehicleOwnershipSimpleVO)) {
            vehicleOwnershipSimpleVO.forEach(iterm -> {
                iterm.setMainVehicleId(mainVehicleId);
            });
            vehicleOwnershipService.createVehicleOwnershipList(vehicleOwnershipSimpleVO);
        }

        // 4、插入附件信息
        VehicleUtils.saveVehicleFile(mainVehicleId, createReqVO);

        // 返回
        return mainVehicleId;
    }

    /**
     * 使用年限计算 使用年限 = 当前日期 - 登记日期
     * @param registerTime
     * @return
     */
    private Integer useYearCompute(LocalDateTime registerTime){
        LocalDateTime now = LocalDateTime.now();
        // 计算年、月、日的差异
        Period period = Period.between(registerTime.toLocalDate(), now.toLocalDate());
        int years = period.getYears();//相差的年
        int months = period.getMonths(); //相差的月
        //相差月份大于等于半年（6月）是算一年
        if(months >= 6){
            years++;
        }
        return years;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMainVehicle(MainVehicleUpdateReqVO updateReqVO) {
        // 校验存在
        Long mainVehicleId = updateReqVO.getId();
        String plateNumber = updateReqVO.getPlateNumber();
        String vehicleFrame = updateReqVO.getVehicleFrame();
        String vehicleCode = updateReqVO.getVehicleCode();
        validateMainVehicleExists(mainVehicleId, plateNumber,vehicleFrame,vehicleCode);
        // 1、更新车头表基本信息
        MainVehicleDO updateObj = MainVehicleConvert.INSTANCE.convert(updateReqVO);
        LocalDateTime registerTime = updateObj.getRegisterTime();
        int userYears = useYearCompute(registerTime);
        updateObj.setUserYears(userYears);
        Long outCompanyId = updateReqVO.getOutCompanyId();
        updateObj.setOutCompanyId(outCompanyId);
        mainVehicleMapper.updateById(updateObj);
        if(!ObjectUtil.equal(updateObj.getIsOut(),true))
        {
            LambdaUpdateWrapper<MainVehicleDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(MainVehicleDO::getOutCompanyId,null).eq(MainVehicleDO::getId, mainVehicleId);
            mainVehicleMapper.update(null, updateWrapper);
        }

        //2、更新(插入)车头的车牌变更记录信息
        List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVO = updateReqVO.getVehicleLicenseSimpleVO();
        vehicleLicenseSimpleVO.forEach(iterm -> {
            iterm.setMainVehicleId(mainVehicleId);
        });
        if ( CollectionUtils.isNotEmpty(vehicleLicenseSimpleVO)) {
            vehicleLicenseService.deleteVehicleLicense(mainVehicleId);
            vehicleLicenseService.createVehicleLicenseList(vehicleLicenseSimpleVO);
        } else {
            vehicleLicenseService.deleteVehicleLicense(mainVehicleId);
        }

        //3、更新(插入)业户名称变更记录信息
        List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVO = updateReqVO.getVehicleOwnershipSimpleVO();
        vehicleOwnershipSimpleVO.forEach(iterm -> {
            iterm.setMainVehicleId(mainVehicleId);
        });
        if ( CollectionUtils.isNotEmpty(vehicleOwnershipSimpleVO)) {
            vehicleOwnershipService.deleteVehicleOwnership(mainVehicleId);
            vehicleOwnershipService.createVehicleOwnershipList(vehicleOwnershipSimpleVO);
        } else {
            vehicleOwnershipService.deleteVehicleOwnership(mainVehicleId);
        }

        // 4、更新车头表对应附件信息
        VehicleUtils.saveVehicleFile(mainVehicleId, updateReqVO);
    }

    @Override
    public void deleteMainVehicle(Long id) {
        // 删除
        mainVehicleMapper.deleteById(id);
    }

    /**
     * @param id                 id主键，用于update校验
     * @param plateNumber        车牌
     * @param vehicleFrame       车架号
     * @param vehicleCode       车头编码
     */
    private void validateMainVehicleExists(Long id, String plateNumber,String vehicleFrame,String vehicleCode) {
        // 检查车牌是否重复
        if (StrUtils.isNotEmpty(plateNumber)) {
            LambdaQueryWrapper<MainVehicleDO> plateNumberQuery = new LambdaQueryWrapperX<>();
            plateNumberQuery.ne(id != null, MainVehicleDO::getId, id)
                    .eq(MainVehicleDO::getPlateNumber, plateNumber);
            boolean plateNumberExists = mainVehicleMapper.exists(plateNumberQuery);
            if (plateNumberExists) {
                throw exception(VEHICLE_ALREADY_EXISTS02);
            }
        }

        // 检查车架号是否重复
        if (StrUtils.isNotEmpty(vehicleFrame)) {
            LambdaQueryWrapper<MainVehicleDO> vehicleFrameQuery = new LambdaQueryWrapperX<>();
            vehicleFrameQuery.ne(id != null, MainVehicleDO::getId, id)
                    .eq(MainVehicleDO::getVehicleFrame, vehicleFrame);
            MainVehicleDO mainVehicleDO = mainVehicleMapper.selectOne(vehicleFrameQuery);
            if (mainVehicleDO != null) {
                throw exception(VEHICLE_ALREADY_EXISTS03);
            }
        }

        // 检查车头编码是否重复
        if (StrUtils.isNotEmpty(vehicleCode)) {
            LambdaQueryWrapper<MainVehicleDO> vehicleCodeQuery = new LambdaQueryWrapperX<>();
            vehicleCodeQuery.ne(id != null, MainVehicleDO::getId, id)
                    .eq(MainVehicleDO::getVehicleCode, vehicleCode);
            MainVehicleDO mainVehicleDO = mainVehicleMapper.selectOne(vehicleCodeQuery);
            if (mainVehicleDO != null) {
                throw exception(VEHICLE_ALREADY_EXISTS01);
            }
        }
    }

    @Override
    public MainVehicleRespVO getMainVehicle(Long id) {

        //1、车头基本信息获取
        MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(id);
        MainVehicleRespVO mainVehicleRespVO = new MainVehicleRespVO();
        com.fmyd888.fengmao.framework.common.util.object.BeanUtils.copyPropertiesIgnoreNull(mainVehicleRespVO, mainVehicleDO);

        //2、车头的车牌变更记录信息获取
        List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVOS = vehicleLicenseService.getVehicleLicenseList01(id);
        mainVehicleRespVO.setVehicleLicenseSimpleVO(vehicleLicenseSimpleVOS);

        //3、车头的业户变更记录信息获取
        List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVOS = vehicleOwnershipService.getVehicleOwnershipList01(id);
        mainVehicleRespVO.setVehicleOwnershipSimpleVO(vehicleOwnershipSimpleVOS);

        //4、填充附件信息
        saveFileInfo(mainVehicleRespVO);
        return mainVehicleRespVO;
    }


    /**
     * 初始化设置映射关系和消费方法
     *
     * @param fileRespVO
     * @return
     */
    Map<VehicleMainFileEnum, Consumer<List<FileSimpleRespVO>>> initVehicleMainFileEnum(MainVehicleRespVO.FileRespVO fileRespVO) {
        // 1、创建一个HashMap，用于存储车头附件枚举值，和对应mainVehicleRespVO的消费操作方法
        Map<VehicleMainFileEnum, Consumer<List<FileSimpleRespVO>>> vehicleMainFileMap = new HashMap<>();
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_1, fileRespVO::setRegistrationBook); // 出厂合格证
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_2, fileRespVO::setFactoryQualificationCertificate); // 出厂合格证
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_3, fileRespVO::setRoadTransportPermit); // 上传道路运输许可证(主页)
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_4, fileRespVO::setConformityCertificate); // 出厂一致性证书
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_5, fileRespVO::setVehicleRegistrationCertificate); // 机动车行驶证(主页)
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_6, fileRespVO::setFactoryPackingList); // 出厂随车清单
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_7, fileRespVO::setAttachments); // 车头附件
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_8, fileRespVO::setOtherFactoryDocuments); // 出厂的其他资料
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_9, fileRespVO::setPurchaseInvoice); // 购置发票
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_10, fileRespVO::setTaxDeclarationForm); // 纳税申报表
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_11, fileRespVO::setPurchaseTaxCertificate); // 购置税完税证明
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_12, fileRespVO::setVehicleCheckRecord); // 标车辆核查记录表
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_13, fileRespVO::setGpsCertificate); // GPS证明
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_14, fileRespVO::setGradeAssessmentReport); // 等级评定报告
        vehicleMainFileMap.put(VehicleMainFileEnum.CT_15, fileRespVO::setInsuranceCertificate); // 保险证明
        return vehicleMainFileMap;
    }

    /**
     * 车头档案中填充附件简单信息返回
     * 根据不同车头的枚举，进行对应的消费
     *
     * @param mainVehicleRespVO
     */
    private void saveFileInfo(MainVehicleRespVO mainVehicleRespVO) {
        MainVehicleRespVO.FileRespVO fileRespVO = new MainVehicleRespVO.FileRespVO();
        mainVehicleRespVO.setFileRespVO(fileRespVO);
        Map<VehicleMainFileEnum, Consumer<List<FileSimpleRespVO>>> setterMap = initVehicleMainFileEnum(fileRespVO);
        // 遍历所有的枚举值
        for (VehicleMainFileEnum fileEnum : VehicleMainFileEnum.values()) {
            LambdaQueryWrapper<FileDO> queryWrapper = new LambdaQueryWrapper<>();
            List<FileSimpleRespVO> fileSimpleRespVOS = new ArrayList<>();
            queryWrapper.eq(FileDO::getCodeBusinessType, fileEnum.getFileCode())
                    .eq(FileDO::getSourceId, mainVehicleRespVO.getId());
            List<FileDO> fileDOS = fileMapper.selectList(queryWrapper);
            if ( CollectionUtils.isNotEmpty(fileDOS)) {
                fileDOS.forEach(iterm -> {
                    FileSimpleRespVO fileSimpleRespVO = new FileSimpleRespVO();
                    fileSimpleRespVO.setName(iterm.getName());
                    fileSimpleRespVO.setUrl(iterm.getUrl());
                    fileSimpleRespVOS.add(fileSimpleRespVO);
                });
            }
            Consumer<List<FileSimpleRespVO>> setter = setterMap.get(fileEnum);
            Consumer<List<FileSimpleRespVO>> listConsumer = setterMap.get(fileEnum);
            if (setter != null) {
                // 2、自动对应上各自的setXxx方法，
                setter.accept(fileSimpleRespVOS);
                listConsumer.accept(fileSimpleRespVOS);
            } else {
                throw exception(MAIN_VEHICLE_NO_CODEBUSINESSTYPE);
            }
        }
    }

    @Override
    public List<MainVehicleDO> getMainVehicleList(Collection<Long> ids) {
        return mainVehicleMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MainVehicleRespVO> getMainVehiclePage(MainVehiclePageReqVO pageReqVO) {
        PageResult<MainVehicleDO> page1 = mainVehicleMapper.selectPage(pageReqVO);
        PageResult<MainVehicleRespVO> page2 = MainVehicleConvert.INSTANCE.convertPage(page1);
        page2.getList().forEach(this::saveFileInfo);
        return page2;
    }

    @Override
    public List<DownMainVehicleExcelVO> getMainVehicleList(MainVehicleExportReqVO exportReqVO) {
        List<DownMainVehicleExcelVO> downMainVehicleExcelVOS = mainVehicleMapper.listExcelData(exportReqVO);
        return downMainVehicleExcelVOS;
    }

    @Override
    public List<HashMap<String, Object>> getExportList() {

        List<HashMap<String, Object>> exportList = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Field[] fields = DownMainVehicleExcelVO.class.getDeclaredFields();

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
    public void batchUpdateMainVehicle(List<MainVehicleUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (MainVehicleUpdateReqVO updateReqVO : updateReqVOList) {
            updateMainVehicle(updateReqVO);
        }
    }

    @Override
    public void batchDeleteMainVehicle(List<Long> ids) {
        // 在这里处理批量删除逻辑
        mainVehicleMapper.deleteBatchIds(ids);
    }

    @Override
    public void batchImportMainVehicle(List<MainVehicleDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        mainVehicleMapper.insertBatch(importReqVOList);
    }

    @Override
    public Page<MainVehicleRespVO> selectPageByKeyword(MainVehicleKeywordPageReqVO pageReqVO) {
        Page<MainVehicleRespVO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        Page<MainVehicleRespVO> pageResultDO = mainVehicleMapper.selectPageByKeyword(page, pageReqVO);
//        Page<MainVehicleRespVO> pageResultVO = MainVehicleConvert.INSTANCE.convertPage02(pageResultDO);
        return pageResultDO;
    }

    @Override
    public MainVehicleDownloadVO getMainVehicleCertificateFile(MainVehicleDownloadReqVO vehicleDonwloadReqVO) {
        Map<String, Map<String, List<FileDO>>> resultMap = getVehicleStructInfo(vehicleDonwloadReqVO);
        return VehicleUtils.createZip(resultMap);
    }

    @Override
    public void deleteFiles(MainVehicleDownloadVO mainVehicleDownloadVO) {
        VehicleUtils.deleteFiles(mainVehicleDownloadVO);
    }

    /**
     * @param vehicleDonwloadReqVO
     * @return 数据库数据关系结构集合对象
     */
    private Map<String, Map<String, List<FileDO>>> getVehicleStructInfo(MainVehicleDownloadReqVO vehicleDonwloadReqVO) {
        Map<String, Map<String, List<FileDO>>> resultMap = new HashMap<>();
        //获得车头资源id列表和车头附件类型
        List<Long> mainVehicleIds = vehicleDonwloadReqVO.getMainVehicleIds();
        List<Long> mainVehicleFileTypes = vehicleDonwloadReqVO.getMainVehicleFileTypes();
        //1、车头车牌
        //List<MainVehicleDO> mainVehicleDOS = mainVehicleMapper.selectBatchIds(mainVehicleIds);
        LambdaQueryWrapper<MainVehicleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in( CollectionUtils.isNotEmpty(mainVehicleIds), MainVehicleDO::getId, mainVehicleIds);
        List<MainVehicleDO> mainVehicleDOS = mainVehicleMapper.selectList(wrapper);

        mainVehicleDOS.parallelStream().forEach(mainVehicleDO -> {
            Long mainVehicleId = mainVehicleDO.getId();
            String plateNumber = mainVehicleDO.getPlateNumber();
            // 2、车头车牌下的所有选定附件
            Map<String, List<FileDO>> typeMap = new HashMap<>();
            QueryWrapper<FileDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("source_id", mainVehicleId)
                    .in("code_business_type", mainVehicleFileTypes);
            List<FileDO> fileList = fileMapper.selectList(queryWrapper);
            for (Long mainVehicleFileType : mainVehicleFileTypes) {
                //查询出来则插入
                if ( CollectionUtils.isNotEmpty(fileList)) {
                    List<FileDO> filteredList = fileList.stream()
                            .filter(file -> String.valueOf(mainVehicleFileType).equals(file.getCodeBusinessType()))
                            .collect(Collectors.toList());
                    typeMap.put(String.valueOf(mainVehicleFileType), filteredList);
                }
            }
            resultMap.put(plateNumber, typeMap);
        });
        return resultMap;
    }







    @Override
    public void updateStatus(Long id) {
        LambdaUpdateWrapper<MainVehicleDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MainVehicleDO::getId, id);

        // 查询主车辆记录
        MainVehicleDO mainVehicleDO = mainVehicleMapper.selectOne(updateWrapper);

        // 如果记录不存在，抛出异常
        if (mainVehicleDO == null) {
            throw exception(MAIN_VEHICLE_NO_EXISTS);
        }

        // 状态取反
        Integer currentStatus = mainVehicleDO.getVehicleStatus();
        Integer newStatus = null;

        // 使用 if-else 判断并设置新状态
        if (currentStatus != null) {
            if (currentStatus == 1) {
                newStatus = 0;
            } else {
                newStatus = 1;
                //禁用 同时更新车辆管理的数据
                LambdaUpdateWrapper<CarDO> queryWrapper = new LambdaUpdateWrapper<>();
                queryWrapper.eq(CarDO::getMainVehicleId, id)
                        .eq(CarDO::getDeleted, 0);
                CarDO carDO = carMapper.selectOne(queryWrapper);
                if (carDO != null){
                    queryWrapper.set(CarDO::getMainVehicleId, null);
                    carMapper.update(null, queryWrapper);
                }

                if (!mainVehicleDO.getIsIdle()){
                    updateWrapper.set(MainVehicleDO::getIsIdle, true);
                }
            }
        }

        updateWrapper.set(MainVehicleDO::getVehicleStatus, newStatus);

        mainVehicleMapper.update(null, updateWrapper);
    }



    @Override
    public MainVehicleBasicRespVO getMainVehicle02(Long id) {
        LambdaQueryWrapper<MainVehicleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(MainVehicleDO::getStatus)
                .eq(MainVehicleDO::getId, id);
        MainVehicleDO mainVehicleDO = mainVehicleMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(mainVehicleDO)) {
            throw exception(MAIN_VEHICLE_NO_EXISTS);
        }
        MainVehicleBasicRespVO mainVehicleBasicRespVO = MainVehicleConvert.INSTANCE.convert05(mainVehicleDO);
        return mainVehicleBasicRespVO;
    }

    @Override
    public List<MainVehicleSimpleRespVO> getMainVehicleDetails(@Nullable Long companyId,@Nullable Boolean isOut) {
        List<MainVehicleSimpleRespVO> list = new ArrayList<>();

        MPJLambdaWrapper<MainVehicleDO> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper
                .leftJoin(CarDO.class, CarDO::getMainVehicleId, MainVehicleDO::getId)
                .leftJoin(TrailerDO.class, TrailerDO::getId, CarDO::getTrailerId)
                .eq(MainVehicleDO::getVehicleStatus, 0)
                .select(MainVehicleDO::getPlateNumber, MainVehicleDO::getId)
                .selectAs(TrailerDO::getVehicleTrailerNo, "vehicleTrailerNo");
        if (companyId != null){
            mpjLambdaWrapper.eq(MainVehicleDO::getCompanyId, companyId);
        }
        if (isOut != null){
            mpjLambdaWrapper.eq(MainVehicleDO::getIsOut, isOut);
        }
        List<MainVehicleDO> mainVehicleDOS = mainVehicleMapper.selectJoinList(MainVehicleDO.class, mpjLambdaWrapper);

        //2、已经绑定的车头
        List<CarDO> carDOS = carMapper.selectList();
        List<Long> ids = carDOS.stream().map(CarDO::getMainVehicleId).collect(Collectors.toList());
        //组装返回
        for (MainVehicleDO iterm : mainVehicleDOS) {
            MainVehicleSimpleRespVO mainVehicleSimpleRespVO = new MainVehicleSimpleRespVO();
            mainVehicleSimpleRespVO.setId(iterm.getId());
            mainVehicleSimpleRespVO.setPlantNumber(iterm.getPlateNumber());
            mainVehicleSimpleRespVO.setDeptName(iterm.getDeptName());
            mainVehicleSimpleRespVO.setVehicleType(iterm.getVehicleType());
            mainVehicleSimpleRespVO.setVehicleTrailerNo(iterm.getVehicleTrailerNo());
            list.add(mainVehicleSimpleRespVO);
        }
        //3、绑定的车头标识
        list.stream()
                .forEach(vehicle -> {
                    if (ids.contains(vehicle.getId())) {
                        vehicle.setHasUse(true);
                    } else {
                        vehicle.setHasUse(false);
                    }
                });
        return list;
    }

    @Override
    public void getMainVehicleArchivetDownload(Long id, HttpServletResponse response) {
        try {
            // 获取模板路径和文件名
            URL resource = getClass().getClassLoader().getResource("template/mainVehicleTemplate.xlsx");
            String templatePath = resource.getPath();
            String outputFileName = "车头档案.xlsx";
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
            throw exception(MAIN_VEHICLE_NO_DOCMENT_DOWNLOAD);
        }
    }

    @Override
    public void getMainVehicleArchivetDownload02(List<Long> ids, HttpServletResponse response) {
        try {
            // 创建临时文件夹
            String tempFolderName = "tempFolder";
            //File tempFolder = createTempFolder(tempFolderName);
            File tempFolder = VehicleUtils.createTempFolder(tempFolderName);
            // 生成多个表格文件
            for (Long id : ids) {
                generateExcelFile(tempFolder, id);
            }

            // 临时文件夹下的文件压缩文件夹
            String tempFolderZipPath = "mainVehicleTempFolder.zip";
            File zipFile = zipFolder(tempFolder, tempFolderZipPath);

            String zipName = "车头档案";
            VehicleUtils.zipFileWriteToResponse(zipFile, zipName, response);
            // 删除临时文件和文件夹
            deleteTempFolderAsync(zipFile, tempFolder);
        } catch (Exception e) {
            throw exception(MAIN_VEHICLE_NO_DOCMENT_DOWNLOAD);
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
        URL resource = getClass().getClassLoader().getResource("template/mainVehicleTemplate.xlsx");
        String templatePath = resource.getPath();
        MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(id);
        // 生成Excel文件
        File outputFile = new File(folder, "车头档案_" + mainVehicleDO.getPlateNumber() + ".xlsx");
        FileOutputStream fos = new FileOutputStream(outputFile);
        fillExcelTemplate(templatePath, id, fos);
    }

    /**
     * 创建本地临时文件
     *
     * @return 创建成功的临时文件对象
     */
    private File createTempFolder(String tempFolderName) {
        File tempFolder = new File(tempFolderName);  // TODO: 指定临时文件夹的路径
        tempFolder.mkdirs();
        return tempFolder;
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
        //1、删除本地临时文件
        deleteTempFolder(tempFolder);
        //2、删除本地压缩包
        zipFile.delete();
        return AsyncResult.forValue(null);
    }

    /**
     * 删除本地临时文件（填充后的模板文件）
     *
     * @param tempFolder
     */
    private void deleteTempFolder(File tempFolder) {
        if (tempFolder.isDirectory()) {
            for (File file : tempFolder.listFiles()) {
                deleteTempFolder(file);
            }
        }
        tempFolder.delete();
    }

    /**
     * 压缩文件夹
     *
     * @param folder
     * @param tempFolderZip 指定压缩文件的路径（压缩文件名称）
     * @return 压缩文件夹对象
     */
    private File zipFolder(File folder, String tempFolderZip) {
        File zipFile = new File(tempFolderZip);  // TODO:
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zipFile(folder, folder.getName(), zos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zipFile;
    }

    /**
     * 压缩文件夹具体操作
     *
     * @param file
     * @param fileName
     * @param zos
     * @throws IOException
     */
    private void zipFile(File file, String fileName, ZipOutputStream zos) throws IOException {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                zipFile(subFile, fileName + File.separator + subFile.getName(), zos);
            }
        } else {
            try (FileInputStream fis = new FileInputStream(file)) {
                ZipEntry zipEntry = new ZipEntry(fileName);
                zos.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
                zos.closeEntry();
            }
        }
    }

    /**
     * 填充数据01
     *
     * @param templatePath
     * @param id
     * @param outputStream
     * @return
     */
    private void fillExcelTemplate(String templatePath, Long id, OutputStream outputStream) {
        //本地存储查看
        //EasyExcel.write("车头档案测试.xlsx").withTemplate(templatePath).build();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(templatePath).build();
        MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(id);
        if (ObjectUtils.isEmpty(mainVehicleDO)) {
            throw exception(MAIN_VEHICLE_NOT_EXISTS);
        }
        saveSheet1(excelWriter, mainVehicleDO);
        saveSheet2(excelWriter, mainVehicleDO);
        saveSheet3(excelWriter, mainVehicleDO);
        saveSheet4(excelWriter, mainVehicleDO);
        saveSheet5(excelWriter, mainVehicleDO);
        saveSheet6(excelWriter, mainVehicleDO);
        saveSheet7(excelWriter, mainVehicleDO);
        saveSheet8(excelWriter, mainVehicleDO);

        excelWriter.finish();
    }

    /**
     * 封面Sheet填充
     *
     * @param excelWriter
     */
    private void saveSheet1(ExcelWriter excelWriter, MainVehicleDO mainVehicleDO) {
        HashMap<String, String> map1 = new HashMap<>();
        if (ObjectUtils.isEmpty(mainVehicleDO)) {
            throw exception(MAIN_VEHICLE_NO_QUREY);
        }
        WriteSheet writeSheet1 = EasyExcel.writerSheet("封面").build();
        map1.put("motorvehicleNumber", mainVehicleDO.getPlateNumber());
        excelWriter.fill(map1, writeSheet1);
    }

    /**
     * 车辆基本信息Sheet填充
     *
     * @param excelWriter
     */
    private void saveSheet2(ExcelWriter excelWriter, MainVehicleDO mainVehicleDO) {
        List<VehicleLicenseSimpleVO> vehicleLicenseList = vehicleLicenseService.getVehicleLicenseList01(mainVehicleDO.getId());
        if (!CollectionUtil.isEmpty(vehicleLicenseList)) {
            for (int i = 0; i < vehicleLicenseList.size(); i++) {
                if (i == 0) {
                    vehicleLicenseList.get(i).setLicensePlateChangeCount("首次核发");
                } else {
                    vehicleLicenseList.get(i).setLicensePlateChangeCount("牌号变更" + i);
                }
            }
        }
        //2.道路运输证信息
        List<VehicleOwnershipSimpleVO> ownershipList = vehicleOwnershipService.getVehicleOwnershipList01(mainVehicleDO.getId());
        if (!CollectionUtil.isEmpty(vehicleLicenseList)) {
            for (int i = 0; i < ownershipList.size(); i++) {
                VehicleOwnershipSimpleVO vehicleOwnershipSimpleVO = ownershipList.get(i);
                if (i == 0) {
                    vehicleOwnershipSimpleVO.setNumber("初次登记");
                } else {
                    vehicleOwnershipSimpleVO.setNumber("变更次数" + i);
                }
            }
        }
        WriteSheet writeSheet = EasyExcel.writerSheet("1---车辆基本信息").build();
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).direction(WriteDirectionEnum.VERTICAL).build();
        // 车辆号牌信息填充
        excelWriter.fill(new FillWrapper("data1", vehicleLicenseList), fillConfig, writeSheet);
        // 道路运输证信息填充
        excelWriter.fill(new FillWrapper("data2", ownershipList), fillConfig, writeSheet);
        //其他车头的基本信息
        //mainVehicleDO
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        HashMap<String, Object> map = new HashMap<>();

        LambdaQueryWrapper<FileDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileDO::getSourceId, mainVehicleDO.getId());
        List<FileDO> fileDOS = fileMapper.selectList(queryWrapper);
//        粘贴初次办理或变更《道路运输证》时，车辆正面偏右侧45°的3寸彩色照片
        String imageUrl = null;
        if ( CollectionUtils.isNotEmpty(fileDOS)) {
            imageUrl = fileDOS.get(0).getUrl();
            //处理图片
            try {
                byte[] fileBytesFromUrl = IoUtils.getFileBytesFromUrl(imageUrl);
                if (fileBytesFromUrl.length > 0) {
                    map.put("imageUrl", fileBytesFromUrl);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        map.put("motorvehicleNumber", mainVehicleDO.getPlateNumber());
        map.put("originalPrice", mainVehicleDO.getOriginalPrice());
        map.put("userYears", mainVehicleDO.getUserYears());
        map.put("residualRate", mainVehicleDO.getResidualRate());
        map.put("vehicleType", mainVehicleDO.getVehicleType());
        map.put("vehicleBrand", mainVehicleDO.getVehicleBrand());
        map.put("manufacturerName", mainVehicleDO.getManufacturerName());
        map.put("productionDate", mainVehicleDO.getProductionDate());
        map.put("vehicleImport", mainVehicleDO.getVehicleImport());
        map.put("vehicleFrame", mainVehicleDO.getVehicleFrame());
        map.put("chassisCode", mainVehicleDO.getChassisCode());
        map.put("outside", mainVehicleDO.getOutside());
        map.put("innerside", mainVehicleDO.getInnerside());
        map.put("totalmass", mainVehicleDO.getTotalmass());
        map.put("towmass", mainVehicleDO.getTowmass());
        map.put("verificationmass", mainVehicleDO.getVerificationmass());
        map.put("engineType", mainVehicleDO.getEngineType());
        map.put("engineCode", mainVehicleDO.getEngineCode());
        map.put("power", mainVehicleDO.getPower());
        map.put("emissionStandard", mainVehicleDO.getEmissionStandard());
        map.put("batteryType", mainVehicleDO.getBatteryType());
        map.put("electricalPower", mainVehicleDO.getElectricalPower());
        map.put("powerType", mainVehicleDO.getPowerType());
        map.put("springNumber", mainVehicleDO.getSpringNumber());
        map.put("wheelbase", mainVehicleDO.getWheelbase());
        map.put("tyreNumber", mainVehicleDO.getTyreNumber());
        map.put("brakeMode", mainVehicleDO.getBrakeMode());
        map.put("brakeFront", mainVehicleDO.getBrakeFront());
        map.put("antilock", mainVehicleDO.getAntilock());
        map.put("transmission", mainVehicleDO.getTransmission());
        map.put("retarder", mainVehicleDO.getRetarder());
        map.put("conditionSystem", mainVehicleDO.getConditionSystem());
        map.put("gps", mainVehicleDO.getGps());
        excelWriter.fill(map, writeSheet);
    }

    private void saveSheet3(ExcelWriter excelWriter, MainVehicleDO mainVehicleDO) {
        WriteSheet writeSheet3 = EasyExcel.writerSheet("2---车辆检测好评定登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("motorvehicleNumber", mainVehicleDO.getPlateNumber());
        excelWriter.fill(map2, writeSheet3);
        List<EvaluationVO> evaluationList01 = evaluationService.getEvaluationList01(mainVehicleDO.getId());
        for (int i = 0; i < evaluationList01.size(); i++) {
            EvaluationVO evaluationVO = evaluationList01.get(i);
            evaluationVO.setOrder(i + 1);
        }
        excelWriter.fill(evaluationList01, writeSheet3);

    }

    private void saveSheet4(ExcelWriter excelWriter, MainVehicleDO mainVehicleDO) {
        WriteSheet writeSheet3 = EasyExcel.writerSheet("3---车辆维护和修理登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("motorvehicleNumber", mainVehicleDO.getPlateNumber());
        excelWriter.fill(map2, writeSheet3);
        List<MaintenanceRepairVO> maintenanceRepairList01 = maintenanceRepairService.getMaintenanceRepairList01(mainVehicleDO.getId());
        for (int i = 0; i < maintenanceRepairList01.size(); i++) {
            MaintenanceRepairVO maintenanceRepairVO = maintenanceRepairList01.get(i);
            maintenanceRepairVO.setOrder(i + 1);
        }
        excelWriter.fill(maintenanceRepairList01, writeSheet3);
    }

    private void saveSheet5(ExcelWriter excelWriter, MainVehicleDO mainVehicleDO) {
        WriteSheet writeSheet3 = EasyExcel.writerSheet("4---车辆主要部件更换登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("motorvehicleNumber", mainVehicleDO.getPlateNumber());
        excelWriter.fill(map2, writeSheet3);
        List<PartReplacementVO> partReplacementList01 = partReplacementService.getPartReplacementList01(mainVehicleDO.getId());
        for (int i = 0; i < partReplacementList01.size(); i++) {
            PartReplacementVO partReplacementVO = partReplacementList01.get(i);
            partReplacementVO.setOrder(i + 1);
        }
        excelWriter.fill(partReplacementList01, writeSheet3);
    }

    private void saveSheet6(ExcelWriter excelWriter, MainVehicleDO mainVehicleDO) {
        WriteSheet writeSheet3 = EasyExcel.writerSheet("5---车辆变更登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("motorvehicleNumber", mainVehicleDO.getPlateNumber());
        excelWriter.fill(map2, writeSheet3);
        List<CarChangeVO> carChangeList01 = carChangeService.getCarChangeList01(mainVehicleDO.getId());
        for (int i = 0; i < carChangeList01.size(); i++) {
            CarChangeVO carChangeVO = carChangeList01.get(i);
            carChangeVO.setOrder(i + 1);
        }
        excelWriter.fill(carChangeList01, writeSheet3);
    }

    private void saveSheet7(ExcelWriter excelWriter, MainVehicleDO mainVehicleDO) {
        WriteSheet writeSheet3 = EasyExcel.writerSheet("6---车辆行驶里程登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("motorvehicleNumber", mainVehicleDO.getPlateNumber());
        excelWriter.fill(map2, writeSheet3);
        List<MileageVO> mileageList01 = mileageService.getMileageList01(mainVehicleDO.getId());
        for (int i = 0; i < mileageList01.size(); i++) {
            MileageVO mileageVO = mileageList01.get(i);
            mileageVO.setOrder(i + 1);
        }
        excelWriter.fill(mileageList01, writeSheet3);
    }

    private void saveSheet8(ExcelWriter excelWriter, MainVehicleDO mainVehicleDO) {
        //
        WriteSheet writeSheet3 = EasyExcel.writerSheet("7--车辆机损事故登记表").build();
        Map<String, String> map2 = new HashMap<>();
        map2.put("motorvehicleNumber", mainVehicleDO.getPlateNumber());
        excelWriter.fill(map2, writeSheet3);
        List<AccidentVO> accidentList01 = accidentService.getAccidentList01(mainVehicleDO.getId());
        for (int i = 0; i < accidentList01.size(); i++) {
            AccidentVO accidentVO = accidentList01.get(i);
            accidentVO.setOrder(i + 1);
        }
        excelWriter.fill(accidentList01, writeSheet3);
    }


    @Override
    public MainVehiclePrintingVO getMainVehicleArchivetPrinting(Long id) {

        MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(id);
//        MainVehiclePrintingVO mainVehiclePrintingVO = MainVehicleConvert.INSTANCE.convert03(mainVehicleDO);
        MainVehiclePrintingVO mainVehiclePrintingVO = new MainVehiclePrintingVO();
        BeanUtils.copyProperties(mainVehicleDO, mainVehiclePrintingVO);
        LambdaQueryWrapper<FileDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileDO::getSourceId, id);
        List<FileDO> fileDOS = fileMapper.selectList(queryWrapper);
//        粘贴初次办理或变更《道路运输证》时，车辆正面偏右侧45°的3寸彩色照片
        String imageUrl = null;
        if ( CollectionUtils.isNotEmpty(fileDOS)) {
            imageUrl = fileDOS.get(0).getUrl();
            mainVehiclePrintingVO.setImageUrl(imageUrl);
        }

        List<VehicleLicenseSimpleVO> vehicleLicenseList = vehicleLicenseService.getVehicleLicenseList01(id);
        for (int i = 0; i < vehicleLicenseList.size(); i++) {
            if (i == 0) {
                vehicleLicenseList.get(i).setLicensePlateChangeCount("首次核发");
            } else {
                vehicleLicenseList.get(i).setLicensePlateChangeCount("牌号变更" + i);
            }
        }
        mainVehiclePrintingVO.setVehicleLicenseSimpleVO(vehicleLicenseList);
        //2.道路运输证信息
        List<VehicleOwnershipSimpleVO> ownershipList = vehicleOwnershipService.getVehicleOwnershipList01(id);
        for (int i = 0; i < ownershipList.size(); i++) {
            VehicleOwnershipSimpleVO vehicleOwnershipSimpleVO = ownershipList.get(i);
            if (i == 0) {
                vehicleOwnershipSimpleVO.setNumber("初次登记");
            } else {
                vehicleOwnershipSimpleVO.setNumber("变更次数" + i);
            }
        }
        mainVehiclePrintingVO.setVehicleOwnershipSimpleVO(ownershipList);
        return mainVehiclePrintingVO;
    }


    @Override
    public List<EvaluationVO> getMainVehiclePrintingList2(Long id) {
        List<EvaluationVO> evaluationList02 = evaluationService.getEvaluationList01(id);
        for (int i = 0; i < evaluationList02.size(); i++) {
            EvaluationVO evaluationVO = evaluationList02.get(i);
            evaluationVO.setOrder(i + 1);
        }
        return evaluationList02;
    }

    @Override
    public List<MaintenanceRepairVO> getMainVehiclePrintingList3(Long id) {
        List<MaintenanceRepairVO> maintenanceRepairList03 = maintenanceRepairService.getMaintenanceRepairList01(id);
        for (int i = 0; i < maintenanceRepairList03.size(); i++) {
            MaintenanceRepairVO maintenanceRepairVO = maintenanceRepairList03.get(i);
            maintenanceRepairVO.setOrder(i + 1);
        }
        return maintenanceRepairList03;
    }

    @Override
    public List<PartReplacementVO> getMainVehiclePrintingList4(Long id) {
        List<PartReplacementVO> partReplacementList04 = partReplacementService.getPartReplacementList01(id);
        for (int i = 0; i < partReplacementList04.size(); i++) {
            PartReplacementVO partReplacementVO = partReplacementList04.get(i);
            partReplacementVO.setOrder(i + 1);
        }
        return partReplacementList04;
    }

    @Override
    public List<CarChangeVO> getMainVehiclePrintingList5(Long id) {
        List<CarChangeVO> maintenanceRepairList05 = carChangeService.getCarChangeList01(id);
        for (int i = 0; i < maintenanceRepairList05.size(); i++) {
            CarChangeVO carChangeVO = maintenanceRepairList05.get(i);
            carChangeVO.setOrder(i + 1);
        }
        return maintenanceRepairList05;
    }

    @Override
    public List<MileageVO> getMainVehiclePrintingList6(Long id) {
        List<MileageVO> maintenanceRepairList06 = mileageService.getMileageList01(id);
        for (int i = 0; i < maintenanceRepairList06.size(); i++) {
            MileageVO mileageVO = maintenanceRepairList06.get(i);
            mileageVO.setOrder(i + 1);
        }
        return maintenanceRepairList06;
    }

    @Override
    public List<AccidentVO> getMainVehiclePrintingList7(Long id) {
        List<AccidentVO> accidentList07 = accidentService.getAccidentList01(id);
        for (int i = 0; i < accidentList07.size(); i++) {
            AccidentVO accidentVO = accidentList07.get(i);
            accidentVO.setOrder(i + 1);
        }
        return accidentList07;
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
    public List<EvaluationVO> getEvaluationList01(Long id) {
        List<EvaluationVO> evaluationList01 = evaluationService.getEvaluationList01(id);
        for (int i = 0; i < evaluationList01.size(); i++) {
            EvaluationVO evaluationVO = evaluationList01.get(i);
            evaluationVO.setOrder(i + 1);
        }
        return evaluationList01;
    }

    //     7--车辆机损事故登记
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
    public List<AccidentVO> getAccidentList01(Long id) {
        List<AccidentVO> accidentList01 = accidentService.getAccidentList01(id);
        for (int i = 0; i < accidentList01.size(); i++) {
            AccidentVO accidentVO = accidentList01.get(i);
            accidentVO.setOrder(i + 1);
        }
        return accidentList01;
    }

    //     5---车辆变更登记表
    @Override
    public Long insertCarChange(CarChangeVO createReqVO) {
        Long carChange = carChangeService.createCarChange(createReqVO);
        return carChange;
    }

    @Override
    public void deletCarChange(Long id, Long carChangeId) {
        carChangeService.deleteCarChange(id, carChangeId);
    }

    @Override
    public List<CarChangeVO> getCarChangeList01(Long id) {
        List<CarChangeVO> carChangeList01 = carChangeService.getCarChangeList01(id);
        for (int i = 0; i < carChangeList01.size(); i++) {
            CarChangeVO carChangeVO = carChangeList01.get(i);
            carChangeVO.setOrder(i + 1);
        }
        return carChangeList01;
    }

    //    3---车辆维护和修理登记表
    @Override
    public Long insertMaintenanceRepair(MaintenanceRepairVO createReqVO) {
        Long maintenanceRepair = maintenanceRepairService.createMaintenanceRepair(createReqVO);
        return maintenanceRepair;
    }

    @Override
    public void deletMaintenanceRepair(Long id, Long maintenanceRepairId) {
        maintenanceRepairService.deleteMaintenanceRepair(id, maintenanceRepairId);
    }

    @Override
    public List<MaintenanceRepairVO> getMaintenanceRepairList01(Long id) {
        List<MaintenanceRepairVO> maintenanceRepairList01 = maintenanceRepairService.getMaintenanceRepairList01(id);
        for (int i = 0; i < maintenanceRepairList01.size(); i++) {
            MaintenanceRepairVO maintenanceRepairVO = maintenanceRepairList01.get(i);
            maintenanceRepairVO.setOrder(i + 1);
        }
        return maintenanceRepairList01;
    }

    //     6---车辆行驶里程登记表
    @Override
    public Long insertMileage(MileageVO createReqVO) {
        Long mileage = mileageService.createMileage(createReqVO);
        return mileage;
    }

    @Override
    public void deletMileage(Long id, Long mileageId) {
        mileageService.deleteMileage(id, mileageId);
    }

    @Override
    public List<MileageVO> getMileageList01(Long id) {
        List<MileageVO> mileageList01 = mileageService.getMileageList01(id);
        for (int i = 0; i < mileageList01.size(); i++) {
            MileageVO mileageVO = mileageList01.get(i);
            mileageVO.setOrder(i + 1);
        }
        return mileageList01;
    }

    //    4---车辆主要部件更换登记表
    @Override
    public Long insertPartReplacement(PartReplacementVO createReqVO) {
        Long partReplacement = partReplacementService.createPartReplacement(createReqVO);
        return partReplacement;
    }

    @Override
    public void deletPartReplacement(Long id, Long partReplacementId) {
        partReplacementService.deletePartReplacement(id, partReplacementId);
    }

    @Override
    public List<PartReplacementVO> getPartReplacementList01(Long id) {
        List<PartReplacementVO> partReplacementList01 = partReplacementService.getPartReplacementList01(id);
        for (int i = 0; i < partReplacementList01.size(); i++) {
            PartReplacementVO partReplacementVO = partReplacementList01.get(i);
            partReplacementVO.setOrder(i + 1);
        }
        return partReplacementList01;
    }


    @Override
    public MainVehicleHistoryVO listHistoryFile(Long mainVehicleId) {
        MainVehicleHistoryVO mainVehicleHistoryVO = new MainVehicleHistoryVO();
        List<String> mainVehicleCodeBusinessTypes = getMainVehicleCodeBusinessTypes();
        //获得历史附件
        List<FileDO> fileDOList = mainVehicleMapper.listHistoryFile(mainVehicleId, mainVehicleCodeBusinessTypes);
        //附件对应
        Map<String, List<MainVehicleHistoryVO.FileInfo>> fileInfoList = new HashMap<>(22);
        buildFileInfoList(fileDOList, fileInfoList);
        mainVehicleHistoryVO.setSourceId(mainVehicleId);
        mainVehicleHistoryVO.setFileInfo(fileInfoList);
        return mainVehicleHistoryVO;
    }

    @Override
    public void updateMainVehicleStatus(Long id) {
        MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(id);
        if (mainVehicleDO == null){
            throw exception(MAIN_VEHICLE_NO_EXISTS);
        }
        //获取状态并取反
        Integer vehicleStatus = mainVehicleDO.getVehicleStatus();
        if (vehicleStatus == 0){
            mainVehicleDO.setVehicleStatus(1);
        }else {
            mainVehicleDO.setVehicleStatus(0);
        }
        mainVehicleMapper.updateById(mainVehicleDO);
    }

    @Override
    public List<DownMainVehicleExcelVO> importPreviewList(List<DownMainVehicleExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(MAIN_VEHICLE_IMPORT);
        }

        // 查询所有车头信息
        LambdaQueryWrapper<MainVehicleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MainVehicleDO::getDeleted, 0)
                .isNull(MainVehicleDO::getStatus);
        List<MainVehicleDO> mainVehicleDOList = mainVehicleMapper.selectList(queryWrapper);

        // 提取车牌号列表
        List<String> plateNumberList = mainVehicleDOList.stream()
                .map(MainVehicleDO::getPlateNumber)
                .collect(Collectors.toList());

        //提起车架号列表
        List<String> vehicleFrameList = mainVehicleDOList.stream()
                .map(MainVehicleDO::getVehicleFrame)
                .collect(Collectors.toList());

        // 查询所有的所属公司并将公司名和公司ID存入companyMap
        Map<String, Long> companyMap = deptMapper.selectList(new LambdaQueryWrapper<DeptDO>()
                        .eq(DeptDO::getDeleted, 0)
                        .eq(DeptDO::getParentId, 498))
                .stream()
                .collect(Collectors.toMap(DeptDO::getName, DeptDO::getId, (existing, replacement) -> existing));


        //查询所有的 车头类型 字典
        List<DictDataRespDTO> vehicleType = dictDataApi.getDictDatas("vehicle_type");
        Map<String, String> vehicleTypeMaps = null;
        if (!CollUtil.isEmpty(vehicleType)){
            vehicleTypeMaps = vehicleType.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 车头品牌 字典
        List<DictDataRespDTO> vehicleBrand = dictDataApi.getDictDatas("vehicle_brand");
        Map<String, String> vehicleBrandMaps = null;
        if (!CollUtil.isEmpty(vehicleBrand)){
            vehicleBrandMaps = vehicleBrand.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 国产/进口 字典
        List<DictDataRespDTO> vehicleImport = dictDataApi.getDictDatas("vehicle_import");
        Map<String, String> vehicleImportMaps = null;
        if (!CollUtil.isEmpty(vehicleImport)){
            vehicleImportMaps = vehicleImport.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 车辆获得方式 字典
        List<DictDataRespDTO> vehicleAcquisition = dictDataApi.getDictDatas("vehicle_acquisition");
        Map<String, String> vehicleAcquisitionMaps = null;
        if (!CollUtil.isEmpty(vehicleAcquisition)){
            vehicleAcquisitionMaps = vehicleAcquisition.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 制动器形式(前轮) 字典
        List<DictDataRespDTO> brakeFront = dictDataApi.getDictDatas("brake_front");
        Map<String, String> brakeFrontMaps = null;
        if (!CollUtil.isEmpty(brakeFront)){
            brakeFrontMaps = brakeFront.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 制动器形式(后轮) 字典
        List<DictDataRespDTO> brakeAfter = dictDataApi.getDictDatas("brake_front");
        Map<String, String> brakeAfterMaps = null;
        if (!CollUtil.isEmpty(brakeAfter)){
            brakeAfterMaps = brakeAfter.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 行车制动方式 字典
        List<DictDataRespDTO> drivingBrakeMode = dictDataApi.getDictDatas("driving_brake_mode");
        Map<String, String> drivingBrakeModeMaps = null;
        if (!CollUtil.isEmpty(drivingBrakeMode)){
            drivingBrakeModeMaps = drivingBrakeMode.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        //查询所有的 变速器形式 字典
        List<DictDataRespDTO> transmission = dictDataApi.getDictDatas("transmission");
        Map<String, String> transmissionMaps = null;
        if (!CollUtil.isEmpty(transmission)){
            transmissionMaps = transmission.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getValue, DictDataRespDTO::getLabel,
                            (existing, replacement) -> existing));
        }

        // 遍历导入的数据进行校验
        for (DownMainVehicleExcelVO item : importDatas) {


            if (item.getFailData() == null) {
                item.setFailData(new HashMap<>()); // 确保 failData 已初始化
            }

            // 初始化标识为无错误
            item.setHasError(false);

            // 1.非空校验
            checkAndSetError(item, item.getCompanyName(), "companyName", "所属公司不能为空");
            checkAndSetError(item, item.getPlateNumber(), "plateNumber", "车牌号不能为空");
            checkAndSetError(item, item.getRegisterTime(), "registerTime", "登记日期不能为空");
            checkAndSetError(item, item.getVehicleType(), "vehicleType", "车头类型不能为空");
            checkAndSetError(item, item.getVehicleBrand(), "vehicleBrand", "车头品牌不能为空");
            checkAndSetError(item, item.getVehicleFrame(), "vehicleFrame", "车架号不能为空");
            checkAndSetError(item, item.getVehicleColor(), "vehicleColor", "车辆颜色不能为空");
            checkAndSetError(item, item.getVehicleModel(), "vehicleModel", "车辆型号不能为空");
            checkAndSetError(item, item.getPower(), "power", "排量/功率不能为空");
            checkAndSetError(item, item.getManufacturerName(), "manufacturerName", "制造厂名称不能为空");
            checkAndSetError(item, item.getTurningForm(), "turningForm", "转向形式不能为空");
            checkAndSetError(item, item.getTransportDate(), "transportDate", "运输日期不能为空");
            checkAndSetError(item, item.getIdentifier(), "identifier", "行驶证档案编号不能为空");
            checkAndSetError(item, item.getOriginalPrice(), "originalPrice", "原价不能为空");
            checkAndSetError(item, item.getFrontWeight(), "frontWeight", "车头自重不能为空");
            checkAndSetError(item, item.getDrivingDate(), "drivingDate", "行驶证有效期不能为空");

            // 2.设置更新或新增标志
            if (item.getId() == null) {
                item.setIsUpdateSupport(false); // 设置为新增
            } else {
                MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(item.getId());
                item.setIsUpdateSupport(mainVehicleDO != null); // 设置为更新或新增
            }

            // 校验车牌号是否存在于数据库中的车牌号列表中
            if (plateNumberList.contains(item.getPlateNumber())) {
                item.getFailData().put("plateNumber", "车牌号已存在");
                item.setHasError(true); // 设置为有错误
            }

            // 校验车架号是否存在于数据库中的车牌号列表中
            if (vehicleFrameList.contains(item.getVehicleFrame())) {
                item.getFailData().put("vehicleFrame", "车架号已存在");
                item.setHasError(true); // 设置为有错误
            }


            //3.字典校验
            if (item.getVehicleType() != null && !vehicleTypeMaps.containsKey(item.getVehicleType())) {
                String vehicleTypeValue = item.getVehicleType();
                String vehicleTypeErrorMessage = String.format("导入的 \"%s\" 的字典不存在", vehicleTypeValue);
                item.getFailData().put("vehicleType", vehicleTypeErrorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getVehicleBrand() != null && !vehicleBrandMaps.containsKey(item.getVehicleBrand())) {
                String vehicleBrandValue = item.getVehicleBrand();
                String vehicleBrandErrorMessage = String.format("导入的 \"%s\" 的字典不存在", vehicleBrandValue);
                item.getFailData().put("vehicleBrand", vehicleBrandErrorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getVehicleImport() != null && !vehicleImportMaps.containsKey(item.getVehicleImport())) {
                String vehicleImportValue = item.getVehicleImport();
                String errorMessage = String.format("导入的 \"%s\" 的字典不存在", vehicleImportValue);
                item.getFailData().put("vehicleImport", errorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getVehicleAccess() != null && !vehicleAcquisitionMaps.containsKey(item.getVehicleAccess())) {
                String vehicleAccessValue = item.getVehicleAccess();
                String vehicleAccessErrorMessage = String.format("导入的 \"%s\" 的字典不存在", vehicleAccessValue);
                item.getFailData().put("vehicleAccess", vehicleAccessErrorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getBrakeFront() != null && !brakeFrontMaps.containsKey(item.getBrakeFront())) {
                String brakeFrontValue = item.getBrakeFront();
                String brakeFrontErrorMessage = String.format("导入的 \"%s\" 的字典不存在", brakeFrontValue);
                item.getFailData().put("brakeFront", brakeFrontErrorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getBrakeAfter() != null && !brakeAfterMaps.containsKey(item.getBrakeAfter())) {
                String brakeAfterValue = item.getBrakeAfter();
                String brakeAfterErrorMessage = String.format("导入的 \"%s\" 的字典不存在", brakeAfterValue);
                item.getFailData().put("brakeAfter", brakeAfterErrorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getBrakeMode() != null && !drivingBrakeModeMaps.containsKey(item.getBrakeMode())) {
                String brakeModeValue = item.getBrakeMode();
                String brakeModeErrorMessage = String.format("导入的 \"%s\" 的字典不存在", brakeModeValue);
                item.getFailData().put("brakeMode", brakeModeErrorMessage);
                item.setHasError(true); // 设置为有错误
            }

            if (item.getTransmission() != null && !transmissionMaps.containsKey(item.getTransmission())) {
                String transmissionValue = item.getTransmission();
                String transmissionErrorMessage = String.format("导入的 \"%s\" 的字典不存在", transmissionValue);
                item.getFailData().put("transmission", transmissionErrorMessage);
                item.setHasError(true); // 设置为有错误
            }


            // 校验日期字段并处理错误
            validateDateFormat(item, "registerTime", item.getRegisterTime());
            validateDateFormat(item, "transportDate", item.getTransportDate());
            validateDateFormat(item, "drivingDate", item.getDrivingDate());
            validateDateFormat(item, "productionDate", item.getProductionDate());
            validateDateFormat(item, "secondaryMaintenance", item.getSecondaryMaintenance());

            // 校验所属公司是否在companyMap中存在
            checkoutMainVehicle(item, companyMap);

            // 校验所属公司是否存在于公司列表中
            if (item.getCompanyName() != null &&!companyMap.containsKey(item.getCompanyName())) {
                item.getFailData().put("companyName", "所属公司不存在");
                item.setHasError(true); // 设置为有错误
            }
        }

        return importDatas;
    }

    // 校验字段是否为空，并设置错误信息
    private void checkAndSetError(DownMainVehicleExcelVO item, String fieldValue, String fieldName, String errorMessage) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            item.setHasError(true); // 设置为 true
            item.getFailData().put(fieldName, errorMessage); // 添加到 failData 中
        }
    }

    // 针对 BigDecimal 类型的校验方法
    private void checkAndSetError(DownMainVehicleExcelVO item, BigDecimal fieldValue, String fieldName, String errorMessage) {
        if (fieldValue == null) {
            item.setHasError(true); // 设置为 true
            item.getFailData().put(fieldName, errorMessage); // 添加到 failData 中
        }
    }


    private void validateDateFormat(DownMainVehicleExcelVO item, String fieldName, String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return;
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/M/d");

        try {
            //解析日期字符串
            LocalDate.parse(dateString, inputFormatter);
        } catch (DateTimeParseException e) {
            // 日期格式错误，记录错误信息
            item.getFailData().put(fieldName, "日期格式错误,请按照“2020/3/6”的格式导入!");
            item.setHasError(true);
        }
    }



    private void checkoutMainVehicle(DownMainVehicleExcelVO data, Map<String, Long> companyMap) {
        if (data.getFailData() == null) {
            data.setFailData(new HashMap<>()); // 初始化 failData
        }

        String companyName = data.getCompanyName();
        if (companyMap.containsKey(companyName)) {
            data.setCompanyId(companyMap.get(companyName));
            // 不修改 HasError 的状态，保持其他逻辑中的状态
        } else {
            data.setHasError(true);
            data.getFailData().put("companyName", "所属公司不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public ImportResult<DownMainVehicleExcelVO> importMainVehicleList(DownMainVehicleExcelVO importReqVo) {
        List<DownMainVehicleExcelVO> importDatas = importReqVo.getImportDatas();

        // 过滤有效数据
        List<DownMainVehicleExcelVO> importListData = importDatas.stream()
                .filter(item -> item.getHasError() == null || !item.getHasError())
                .collect(Collectors.toList());

        List<DownMainVehicleExcelVO> importListUpdate = new ArrayList<>();
        List<DownMainVehicleExcelVO> importListInsert = new ArrayList<>();

        // 进行分类
        for (DownMainVehicleExcelVO item : importListData) {
            if (Boolean.TRUE.equals(item.getIsUpdateSupport())) {
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
        return ImportResult.<DownMainVehicleExcelVO>builder()
                .total(total)  // 设置导入数据总行数
                .importCount(importCount)  // 设置成功行数
                .failCount(failCount)  // 设置失败行数
                .success(success)  // 设置导入状态
                .data(importListData)  // 返回有效数据
                .build();
    }


    private void handleUpdates(List<DownMainVehicleExcelVO> updates) {
        try {
            if (CollectionUtils.isNotEmpty(updates)) {
                for (DownMainVehicleExcelVO update : updates) {
                    MainVehicleDO mainVehicleDO = mainVehicleMapper.selectById(update.getId());
                    // 复制并排除不需要的字段
                    BeanUtils.copyProperties(update, mainVehicleDO, "companyName", "id");
                    mainVehicleMapper.updateById(mainVehicleDO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 可以在这里添加日志或者处理异常逻辑
        }
    }

    private void handleInserts(List<DownMainVehicleExcelVO> inserts) {
        if (CollectionUtils.isNotEmpty(inserts)) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/M/d");

            for (DownMainVehicleExcelVO insert : inserts) {
                // 创建 MainVehicleDO 对象
                MainVehicleDO mainVehicleDO = new MainVehicleDO();

                // 复制并排除不需要的字段
                BeanUtils.copyProperties(insert, mainVehicleDO, "companyName", "id", "registerTime", "transportDate", "drivingDate", "secondaryMaintenance");

                // 日期字段解析和设置，处理null值情况
                mainVehicleDO.setRegisterTime(parseDate(insert.getRegisterTime(), dateFormatter));
                mainVehicleDO.setTransportDate(parseDate(insert.getTransportDate(), dateFormatter));
                mainVehicleDO.setDrivingDate(parseDate(insert.getDrivingDate(), dateFormatter));
                mainVehicleDO.setSecondaryMaintenance(parseDate(insert.getSecondaryMaintenance(), dateFormatter));

                String code = EncodingRulesEnum.VEHICLE_MAIN_CODE.getBusinessCode();
                String codeByRuleType = encodingRulesService.getCodeByRuleType(code);
                mainVehicleDO.setVehicleCode(codeByRuleType);

                // 插入到数据库
                mainVehicleMapper.insert(mainVehicleDO);
            }
        }
    }

    private LocalDateTime parseDate(String dateStr, DateTimeFormatter dateFormatter) {
        if (dateStr != null) {
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
            return date.atStartOfDay(); // 将 LocalDate 转换为 LocalDateTime，时间部分设为 00:00
        }
        return null; // 如果日期字符串为null，返回null
    }




    /**
     * 对应不同类型填充设置
     *
     * @param fileInfoList
     */
    private void buildFileInfoList(List<FileDO> fileDOList,
                                   Map<String, List<MainVehicleHistoryVO.FileInfo>> fileInfoList) {
        List<MainVehicleHistoryVO.FileInfo> fileInfo = BeanUtil.copyToList(fileDOList, MainVehicleHistoryVO.FileInfo.class);
        List<VehicleMainFileEnum> vehicleMainFileEnumList = new ArrayList<>(22);
        Collections.addAll(vehicleMainFileEnumList, VehicleMainFileEnum.values());

        for (VehicleMainFileEnum vehicleMainFileEnum : vehicleMainFileEnumList) {
            List<MainVehicleHistoryVO.FileInfo> list = new ArrayList<>(22);
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
    private List<String> getMainVehicleCodeBusinessTypes() {
        List<String> list = new ArrayList<>(22);
        List<VehicleMainFileEnum> vehicleMainFileEnumList = new ArrayList<>(22);
        Collections.addAll(vehicleMainFileEnumList, VehicleMainFileEnum.values());

        for (VehicleMainFileEnum fileEnum : vehicleMainFileEnumList) {
            list.add(fileEnum.getFileCode());
        }
        return list;
    }

}

