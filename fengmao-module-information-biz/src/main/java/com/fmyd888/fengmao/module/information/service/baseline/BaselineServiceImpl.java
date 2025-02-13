package com.fmyd888.fengmao.module.information.service.baseline;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.common.util.object.ObjectUtils;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.contract.enums.carriageContract.dal.dal.CarriageContractDOApi;
import com.fmyd888.fengmao.module.contract.enums.carriageContract.dal.dal.CarriageInvoicesDOApi;
import com.fmyd888.fengmao.module.contract.enums.carriageContract.dal.mapper.CarriageContractMapper1;
import com.fmyd888.fengmao.module.contract.enums.carriageContract.dal.mapper.CarriageInvoicesMapper1;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineMedium.BaselineMediumSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineMediumType.BaselineMediumTypeSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineRoute.BaselineRouteSalaryReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineRoute.BaselineRouteSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.fuelConsStandar.FuelConsStandardSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.route.TransRouteReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.settlebaseline.SettleBaselineReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import com.fmyd888.fengmao.module.information.dal.mysql.address.AddressMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.baseline.BaselineMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.baseline.BaselineMediumMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.baseline.FuelConsStandardMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.commodity.CommodityMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.measurement.MeasurementMapper;
import com.fmyd888.fengmao.module.information.enums.baseline.RouteTypeEnum;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 基线 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class BaselineServiceImpl extends ServiceImpl<BaselineMapper, BaselineDO> implements BaselineService {

    @Resource
    private BaselineMapper baselineMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private FuelConsStandardService fuelConsStandardService;
    @Resource
    private BaselineMediumTypeService baselineMediumTypeService;
    @Resource
    private BaselineMediumService baselineMediumService;
    @Resource
    private BaselineMediumMapper baselineMediumMapper;
    @Resource
    private BaselineRouteService baselineRouteService;
    @Resource
    private CarriageContractMapper1 contractMapper1;
    @Resource
    private CarriageInvoicesMapper1 carriageInvoicesMapper1;
    @Resource
    private FuelConsStandardMapper fuelConsStandardMapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private MeasurementMapper measurementMapper;
    @Resource
    private CommodityMapper commodityMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBaseline(BaselineSaveReqVO createReqVO) {
        //新增前先进行校验
        duplicationCheck(createReqVO);
        saveOrUpdateCheck(createReqVO);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(false);
        createReqVO.setDeptId(loginUserDeptId);
//        validateBaselineExists(createReqVO);
        // 1、插入
        BaselineDO baseline = BeanUtils.toBean(createReqVO, BaselineDO.class);
        parseBaselineSalaryExt(createReqVO,baseline);
        if (CollectionUtils.isNotEmpty(createReqVO.getCarBrands())){
            baseline.setCarBrands(StringUtils.join(createReqVO.getCarBrands(), ","));
        }else {
            baseline.setCarBrands("");
        }
         baselineMapper.insert(baseline);
        // 2、插入子表
        Long baselineId = baseline.getId();
        List<FuelConsStandardSaveVO> fuelConsStandarList = createReqVO.getFuelConsStandarList();
         buildFuelConsStandardDOList(fuelConsStandarList,baselineId,false);

        /*
            2.2 业务要求：判断【重车】或者【空车】？
                空车没有【运输类型】和【运输介质】
         */
        RouteTypeEnum loadedVehicle = RouteTypeEnum.LOADEDVEHICLE;
        Integer routeType = baseline.getRouteType();
        if (loadedVehicle.getCode().equals(routeType)) {
            List<Long> baselineMediumIds = createReqVO.getBaselineMediumIds();
            List<Long> baselineMediumTypeIds = createReqVO.getBaselineMediumTypeIds();
            List<BaselineMediumDO> baselineMediumDOList = buildBaselineMediumDOList(baselineMediumIds);
            List<BaselineMediumTypeDO> baselineMediumTypeDOList = buildBaselineMediumTypeDOList(baselineMediumTypeIds);
            baselineMediumTypeService.createSlavesBindEntity(baselineId, baselineMediumTypeDOList);
            baselineMediumService.createSlavesBindEntity(baselineId, baselineMediumDOList);
        }
        //2.3 基线路径规划表 插入
//        String transportRoutes = createReqVO.getTransportRoutes().trim();
        BaselineRouteSaveVO baselineRouteInfo = createReqVO.getBaselineRouteInfo();
        if (ObjectUtils.isNotEmpty(baselineRouteInfo)) {
            BaselineRouteDO baselineRouteDO = BeanUtils.toBean(baselineRouteInfo, BaselineRouteDO.class);
            baselineRouteDO.setDeptId(loginUserDeptId);
            baselineRouteDO.setLoadingAddressId(createReqVO.getLoadingAddressId());
            baselineRouteDO.setUnloadingAddressId(createReqVO.getUnloadingAddressId());
            List<BaselineRouteDO> baselineRouteDOS = Collections.singletonList(baselineRouteDO);
            baselineRouteService.createSlavesBindEntity(baselineId, baselineRouteDOS);
        }
        return baselineId;
    }

    private void parseBaselineSalaryExt(BaselineSaveReqVO createReqVO, BaselineDO baseline) {
        if (CollectionUtils.isEmpty(createReqVO.getBaselineRouteSalaryList())){
            baseline.setSalaryExt("");
        }
        String baselineExt = JSON.toJSONString(createReqVO.getBaselineRouteSalaryList());
        baseline.setSalaryExt(baselineExt);
    }

    /**
     * 构建基线与运输介质关系BaselineMediumDO集合
     *
     * @param baselineMediumIds
     * @return
     */
    List<BaselineMediumDO> buildBaselineMediumDOList(List<Long> baselineMediumIds) {
        List<BaselineMediumDO> baselineMediumDOList = new ArrayList<>();
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        baselineMediumIds.forEach(commodityId -> {  //设置默认的部门
            BaselineMediumDO baselineMediumDO = new BaselineMediumDO();
            baselineMediumDO.setDeptId(loginUserDeptId);
            baselineMediumDO.setCommodityId(commodityId);
            baselineMediumDOList.add(baselineMediumDO);
        });
        return baselineMediumDOList;
    }

    /**
     * 构建基线与运输介质类型关系BaselineMediumTypeDO集合
     *
     * @param baselineMediumTypeIds
     * @return
     */
    List<BaselineMediumTypeDO> buildBaselineMediumTypeDOList(List<Long> baselineMediumTypeIds) {
        List<BaselineMediumTypeDO> baselineMediumTypeDOList = new ArrayList<>();
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        baselineMediumTypeIds.forEach(commodityId -> {  //设置默认的部门
            BaselineMediumTypeDO baselineMediumTypeDO = new BaselineMediumTypeDO();
            baselineMediumTypeDO.setDeptId(loginUserDeptId);
            baselineMediumTypeDO.setCommodityId(commodityId);
            baselineMediumTypeDOList.add(baselineMediumTypeDO);
        });
        return baselineMediumTypeDOList;
    }

    /**
     * 返回单个【基线-油耗标准维护明细表DO s】,使用List包装
     *
     * @param fuelConsStandarList
     * @return
     */
    private void buildFuelConsStandardDOList(List<FuelConsStandardSaveVO> fuelConsStandarList,Long id,Boolean isUpdate) {
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        if (isUpdate){
            //根据id查询油耗标准明细表
            LambdaQueryWrapper<FuelConsStandardDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FuelConsStandardDO::getEntityId,id)
                    .eq(FuelConsStandardDO::getDeleted,0);
            List<FuelConsStandardDO> fuelConsStandardDOS = fuelConsStandardMapper.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(fuelConsStandardDOS)){
                fuelConsStandardMapper.deleteBatchIds(fuelConsStandardDOS);
            }
        }
        //获取车辆品牌数组
        for (FuelConsStandardSaveVO fuelConsStandardSaveVO : fuelConsStandarList) {
            List<Integer> vehicleBrand = fuelConsStandardSaveVO.getVehicleBrand();
            //循环遍历vehicleBrand
            vehicleBrand.forEach(iterm -> {
                FuelConsStandardDO fuelConsStandardDO = new FuelConsStandardDO();
                fuelConsStandardDO.setFuelStandard(fuelConsStandardSaveVO.getFuelStandard());
                fuelConsStandardDO.setFuelConsumptionUnit(fuelConsStandardSaveVO.getFuelConsumptionUnit());
                fuelConsStandardDO.setVehicleBrand(iterm);
                fuelConsStandardDO.setEntityId(id);
                fuelConsStandardDO.setDeptId(loginUserDeptId);
                fuelConsStandardMapper.insert(fuelConsStandardDO);
            });
        }

    }

    private void  duplicationCheck(BaselineSaveReqVO createReqVO) {

        Integer result = baselineMapper.selectValidBaseline(createReqVO);
        if (result == null) {
            result = 0;
        }
        if (result > 0) {
            throw exception(CREATE_BASELINE_NOT_EXISTS);
        }

    }

    private void saveOrUpdateCheck(BaselineSaveReqVO createReqVO){
        if (createReqVO.getSettlementId() != null){
            //根据结算方id和承运公司查询运输合同
            LambdaQueryWrapper<CarriageContractDOApi> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(CarriageContractDOApi::getCompanyId,createReqVO.getCompanyId())
                    .eq(CarriageContractDOApi::getConsignCompanyId,createReqVO.getSettlementId())
                    .eq(CarriageContractDOApi::getDeleted,0)
                    .select(CarriageContractDOApi::getId,CarriageContractDOApi::getMeasurementId);
            CarriageContractDOApi carriageContractDOApi1 = contractMapper1.selectOne(queryWrapper1);
            if (carriageContractDOApi1 == null){
                throw exception(BASELINE_CARR_CONTRACT_EXISTS);
            }
           else if (carriageContractDOApi1 != null){
               //运输合同有数据，更新前端传过来的计量单位
                LambdaUpdateWrapper<CarriageContractDOApi> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(CarriageContractDOApi::getId,carriageContractDOApi1.getId())
                        .set(CarriageContractDOApi::getMeasurementId,createReqVO.getMeasurementId());
                contractMapper1.update(null,updateWrapper);

//                LambdaQueryWrapper<CarriageInvoicesDOApi> queryWrapper = new LambdaQueryWrapper<>();
//               queryWrapper.eq(CarriageInvoicesDOApi::getCarriageId,carriageContractDOApi1.getId())
//                       .eq(CarriageInvoicesDOApi::getDeleted,0)
//                       .select(CarriageInvoicesDOApi::getId,CarriageInvoicesDOApi::getContractPrice,CarriageInvoicesDOApi::getLoadingManufacturerId,CarriageInvoicesDOApi::getLoadingAddressId,
//                               CarriageInvoicesDOApi::getUnloadManufacturerId,CarriageInvoicesDOApi::getUnloadAddressId);
//                List<CarriageInvoicesDOApi> carriageInvoicesDOApi = carriageInvoicesMapper1.selectList(queryWrapper);
//                if (carriageInvoicesDOApi != null){
//                    for (CarriageInvoicesDOApi invoicesDOApi : carriageInvoicesDOApi) {
//                        LambdaUpdateWrapper<CarriageInvoicesDOApi> updateWrapper1 = new LambdaUpdateWrapper<>();
//                        updateWrapper1.eq(CarriageInvoicesDOApi::getId,invoicesDOApi.getId())
//                                .eq(CarriageInvoicesDOApi::getDeleted,0)
//                                .set(CarriageInvoicesDOApi::getContractPrice,createReqVO.getFareRate())
//                                .set(CarriageInvoicesDOApi::getLoadingManufacturerId,createReqVO.getLoadingManufacturerId())
//                                .set(CarriageInvoicesDOApi::getLoadingAddressId,createReqVO.getLoadingAddressId())
//                                .set(CarriageInvoicesDOApi::getUnloadManufacturerId,createReqVO.getUnloadingManufacturerId())
//                                .set(CarriageInvoicesDOApi::getUnloadAddressId,createReqVO.getUnloadingAddressId());
//                        carriageInvoicesMapper1.update(null,updateWrapper1);
//                    }
//
//
//               }
           }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBaseline(BaselineSaveReqVO updateReqVO) {
        duplicationCheck(updateReqVO);
        saveOrUpdateCheck(updateReqVO);
        // 校验存在
        Long baselineId = updateReqVO.getId();
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        updateReqVO.setDeptId(loginUserDeptId);
        validateBaselineExists(updateReqVO);
        // 更新
        BaselineDO updateObj = BeanUtils.toBean(updateReqVO, BaselineDO.class);
        parseBaselineSalaryExt(updateReqVO,updateObj);
        if (CollectionUtils.isNotEmpty(updateReqVO.getCarBrands())){
            updateObj.setCarBrands(StringUtils.join(updateReqVO.getCarBrands(), ","));
        }
        int affractRow = baselineMapper.updateById(updateObj);
        Long id = updateObj.getId();
        if (affractRow > 0) {
            // 更新子表
            List<Long> baselineMediumIds = updateReqVO.getBaselineMediumIds();
            List<FuelConsStandardSaveVO> fuelConsStandardList = updateReqVO.getFuelConsStandarList();

             buildFuelConsStandardDOList(fuelConsStandardList,id,true);

            List<Long> baselineMediumTypeIds = updateReqVO.getBaselineMediumTypeIds();
            List<BaselineMediumDO> baselineMediumDOList = buildBaselineMediumDOList(baselineMediumIds);
            List<BaselineMediumTypeDO> baselineMediumTypeDOList = buildBaselineMediumTypeDOList(baselineMediumTypeIds);
            RouteTypeEnum loadedVehicle = RouteTypeEnum.LOADEDVEHICLE;
            RouteTypeEnum emptyVehicle = RouteTypeEnum.EMPTYVEHICLE;
            Integer routeType = updateReqVO.getRouteType();
            if (loadedVehicle.getCode().equals(routeType)) {    //重车，可修改
                baselineMediumTypeService.updateSlavesToEntity(baselineId, baselineMediumTypeDOList);
                baselineMediumService.updateSlavesToEntity(baselineId, baselineMediumDOList);
            } else if (emptyVehicle.getCode().equals(routeType)) { //空车，直接删除
                baselineMediumTypeService.deleteSlavesByEntityId(baselineId);
                baselineMediumService.deleteSlavesByEntityId(baselineId);
            }

            BaselineRouteSaveVO baselineRouteInfo = updateReqVO.getBaselineRouteInfo();
            BaselineRouteDO baselineRouteDO = BeanUtils.toBean(baselineRouteInfo, BaselineRouteDO.class);
            baselineRouteDO.setDeptId(loginUserDeptId);
            baselineRouteDO.setLoadingAddressId(updateReqVO.getLoadingAddressId());
            baselineRouteDO.setUnloadingAddressId(updateReqVO.getUnloadingAddressId());
            List<BaselineRouteDO> baselineRouteDOS = Collections.singletonList(baselineRouteDO);
            baselineRouteService.updateSlavesToEntity(baselineId, baselineRouteDOS);
        } else {
            throw exception(BASELINE_EXISTS);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBaseline(Long id) {
        // 删除
        baselineMapper.deleteById(id);
        //删除从表,主表逻辑删除，从表保留
        fuelConsStandardService.deleteSlavesByEntityId(id);
        baselineMediumTypeService.deleteSlavesByEntityId(id);
        //baselineMediumService.deleteSlavesByEntityId(id);
        //baselineRouteService.deleteSlavesByEntityId(id);
    }


    /**
     * 创建或更新校验是否重复:
     * 所属公司、装卸货厂家、装卸货地址、时间段
     * 运输类型、运输介质、
     *
     * @param createReqVO
     */
    private void validateBaselineExists(BaselineSaveReqVO createReqVO) {
        Long id = createReqVO.getId();
        Long companyId = createReqVO.getCompanyId();
        Long loadingManufacturerId = createReqVO.getLoadingManufacturerId();
        Long unloadingManufacturerId = createReqVO.getUnloadingManufacturerId();
        Long loadingAddressId = createReqVO.getLoadingAddressId();
        Long unloadingAddressId = createReqVO.getUnloadingAddressId();
        LocalDateTime startTime = createReqVO.getStartTime();
        LocalDateTime endTime = createReqVO.getEndTime();

// 构建查询条件
        LambdaQueryWrapper<BaselineDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.ne(id != null, BaselineDO::getId, id)  // 排除自身记录
                .eq(BaselineDO::getCompanyId, companyId)
                .eq(BaselineDO::getLoadingManufacturerId, loadingManufacturerId)
                .eq(BaselineDO::getUnloadingManufacturerId, unloadingManufacturerId)
                .eq(BaselineDO::getLoadingAddressId, loadingAddressId)
                .eq(BaselineDO::getUnloadingAddressId, unloadingAddressId)
                .eq(BaselineDO::getDeleted,0)
                .between(BaselineDO::getStartTime, startTime, endTime);


// 检查是否存在相同条件的记录
        Boolean exists = baselineMapper.exists(queryWrapper);
        if (exists) {
            throw exception(CREATE_BASELINE_NOT_EXISTS);
        } else {
            //2、校验：运输类型、运输介质  【这部分是基线与无聊中间表中的字段属性，需要这样校验】
            //List<Long> baselineMediumIds = createReqVO.getBaselineMediumIds();
            //List<Long> baselineMediumTypeIds = createReqVO.getBaselineMediumTypeIds();
            //Boolean validateFlag1 = baselineMediumService.validateBaselineMediumExists(id, baselineMediumIds);
            //Boolean validateFlag2 = baselineMediumTypeService.validateBaselineMediumTypeExists(id, baselineMediumTypeIds);
            ////只要有一个符合那就重复了，报异常
            //if (validateFlag1 || validateFlag2) {
            //    throw exception(UPDATE_BASELINE_NOT_EXISTS, id);
            //}
        }
    }


    @Override
    public BaselineRespVO getBaseline(Long id) {
        BaselineDO baselineDO = baselineMapper.selectById(id);
        BaselineRespVO baselineRespVO = BeanUtils.toBean(baselineDO, BaselineRespVO.class);
        parseBaselineSalaryList(baselineRespVO, baselineDO.getSalaryExt());
        fillEntityName(baselineRespVO);
        fillBaselineMediumInfo(baselineRespVO);
        fillFuelConsStandardInfo(baselineRespVO);
        fillBaselineRouteInfo(baselineRespVO);
        if (StringUtils.isNotBlank(baselineDO.getCarBrands())){
            List<Long> carBrands = Arrays.stream(baselineDO.getCarBrands().split(",")).map(Long::valueOf).collect(Collectors.toList());
            baselineRespVO.setCarBrands(carBrands);
        }
        return baselineRespVO;
    }

    private void parseBaselineSalaryList(BaselineRespVO baselineRespVO, String salaryExt) {
        if (StringUtils.isNotBlank(salaryExt)){
            List<BaselineRouteSalaryReqVO> baselineRouteSalaryList = JSON.parseArray(salaryExt, BaselineRouteSalaryReqVO.class);
            baselineRespVO.setBaselineRouteSalaryList(baselineRouteSalaryList);
        }
    }

    @Override
    public List<BaselineDO> getBaselineList(Collection<Long> ids) {
        List<BaselineDO> baselineDOS = baselineMapper.selectBatchIds(ids);
        return baselineDOS;
    }

    @Override
    public PageResult<BaselineRespVO> getBaselinePage(BaselinePageReqVO pageReqVO) {
        PageResult<BaselineDO> pageResult = baselineMapper.selectPage(pageReqVO);
        PageResult<BaselineRespVO> pageVoResult = BeanUtils.toBean(pageResult, BaselineRespVO.class);
        if(ObjectUtil.isNotEmpty(pageVoResult.getList())){
            List<Long> ids = pageVoResult.getList().stream().map(BaselineRespVO::getId).collect(Collectors.toList());
            List<BaselineMediumDO> details = baselineMediumMapper.selectListByEntityIds(ids);
            pageVoResult.getList().forEach(p->{
                List<String> str = details.stream().filter(p1 -> ObjectUtil.equal(p1.getEntityId(), p.getId())).map(BaselineMediumDO::getCommodityName).collect(Collectors.toList());
                if(ObjectUtil.isNotEmpty(str))
                    p.setBaselineMediumName(String.join(",",str));
            });
        }
//        pageVoResult.getList().parallelStream().forEach(baselineRespVO -> {
//            fillBaselineMediumInfo(baselineRespVO);
//            fillFuelConsStandardInfo(baselineRespVO);
//        });
        return pageVoResult;
    }

    /**
     * 填充其他主体表表名称等信息
     *
     * @param respVO
     */
    void fillEntityName(BaselineRespVO respVO) {
        Long companyId = respVO.getCompanyId();
        DeptDO deptDO = deptMapper.selectById(companyId);
        if (ObjectUtils.isNotEmpty(deptDO)) {
            respVO.setCompanyName(deptDO.getName());
        }
        Long loadingManufacturerId = respVO.getLoadingManufacturerId();
        CustomerDO customerDO = customerMapper.selectById(loadingManufacturerId);
        if (ObjectUtils.isNotEmpty(customerDO)) {
            respVO.setLoadingManufacturerName(customerDO.getCustomerName());
        }
        Long unloadingManufacturerId = respVO.getUnloadingManufacturerId();
        CustomerDO customerDO2 = customerMapper.selectById(unloadingManufacturerId);
        if (ObjectUtils.isNotEmpty(customerDO2)) {
            respVO.setUnloadingManufacturerName(customerDO2.getCustomerName());
        }
        Long loadingAddressId = respVO.getLoadingAddressId();
        AddressDO addressDO = addressMapper.selectById(loadingAddressId);
        if (ObjectUtils.isNotEmpty(addressDO)) {
            respVO.setLoadingAddressName(addressDO.getFullAddress());
        }
        Long unloadingAddressId = respVO.getUnloadingAddressId();
        AddressDO addressDO2 = addressMapper.selectById(unloadingAddressId);
        if (ObjectUtils.isNotEmpty(addressDO2)) {
            respVO.setUnloadingAddressName(addressDO2.getFullAddress());
        }
        Long settlementId = respVO.getSettlementId();
        CustomerDO customerDO1 = customerMapper.selectById(settlementId);
        if (ObjectUtils.isNotEmpty(customerDO1)) {
            respVO.setSettlementName(customerDO1.getCustomerName());
        }
        Long measurementId = respVO.getMeasurementId();
        MeasurementDO measurementDO = measurementMapper.selectById(measurementId);
        if (ObjectUtils.isNotEmpty(measurementDO)) {
            respVO.setMeasurementName(measurementDO.getName());
        }

    }

    /**
     * 填充基线相关的信息
     *
     * @param baselineRespVO
     */
    void fillBaselineMediumInfo(BaselineRespVO baselineRespVO) {
        Long id = baselineRespVO.getId();

        List<BaselineMediumTypeDO> baselineMediumTypeRespDOList = baselineMediumTypeService.getSlavesByEntityId(id);
        if (CollectionUtils.isNotEmpty(baselineMediumTypeRespDOList)) {
            List<Long> baselineMediumIds = baselineMediumTypeRespDOList.stream()
                    .map(BaselineMediumTypeDO::getCommodityId).collect(Collectors.toList());
            List<BaselineMediumTypeSaveVO> baselineMediumTypeRespVOList = BeanUtils.toBean(baselineMediumTypeRespDOList, BaselineMediumTypeSaveVO.class);
            baselineRespVO.setBaselineMediumTypeIds(baselineMediumIds);
            baselineRespVO.setBaselineMediumTypes(baselineMediumTypeRespVOList);
        }

        List<BaselineMediumDO> baselineMediumDOList = baselineMediumService.getSlavesByEntityId(id);
        if (CollectionUtils.isNotEmpty(baselineMediumDOList)) {
            List<Long> baselineMediumTypeIds = baselineMediumDOList.stream()
                    .map(BaselineMediumDO::getCommodityId).collect(Collectors.toList());
            List<BaselineMediumSaveVO> baselineMediumRespList = BeanUtils.toBean(baselineMediumDOList, BaselineMediumSaveVO.class);
            LambdaQueryWrapper<CommodityDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(CommodityDO::getId, baselineMediumTypeIds)
                            .eq(CommodityDO::getDeleted,0);
            List<CommodityDO> commodityList = commodityMapper.selectList(queryWrapper);
            List<String> collect = commodityList.stream().map(CommodityDO::getName).collect(Collectors.toList());
            List<Long> collect1 = commodityList.stream().map(CommodityDO::getId).collect(Collectors.toList());
            baselineRespVO.setBaselineMediumIds(collect1);
            String join = StringUtils.join(collect, "，");
            baselineRespVO.setBaselineMediumName(join);
            baselineRespVO.setBaselineMediums(baselineMediumRespList);
        }


    }

    /**
     * 手动填充【油耗标准维护明细】返回
     *
     * @param baselineRespVO
     */
    void fillFuelConsStandardInfo(BaselineRespVO baselineRespVO) {
        Long id = baselineRespVO.getId();
        List<FuelConsStandardDO> FuelConsStandardDOList = fuelConsStandardService.getSlavesByEntityId(id);
        //一条基线记录有且只有一个对应的油耗标准维护明细记录，一对一关系判断
        if (CollectionUtils.isNotEmpty(FuelConsStandardDOList)) {
            List<FuelConsStandardSaveVO> bean = BeanUtils.toBean(FuelConsStandardDOList, FuelConsStandardSaveVO.class);
            baselineRespVO.setFuelConsStandarList(bean);
        }
    }

    /**
     * 填充基线路线规划相关信息
     * 只有一个元素，一对一关系
     */
    void fillBaselineRouteInfo(BaselineRespVO baselineRespVO) {
        Long id = baselineRespVO.getId();
        List<BaselineRouteDO> baselineRouteDOS = baselineRouteService.getSlavesByEntityId(id);
        if (CollectionUtils.isNotEmpty(baselineRouteDOS)) {
            BaselineRouteDO baselineRouteDO = baselineRouteDOS.get(0);
            BaselineRouteSaveVO baselineRouteRespVO = BeanUtils.toBean(baselineRouteDO, BaselineRouteSaveVO.class);
            baselineRespVO.setRouteRespInfo(baselineRouteRespVO);
        }
    }

    @Override
    public List<BaselineExcelVO> getBaselineList(BaselineListReqVO listReqVO) {
        List<BaselineDO> datas = baselineMapper.selectList(listReqVO);
        List<BaselineExcelVO> exportDatas = BeanUtils.toBean(datas, BaselineExcelVO.class);
        if(ObjectUtil.isNotEmpty(exportDatas)){
            List<Long> ids = exportDatas.stream().map(BaselineExcelVO::getId).collect(Collectors.toList());
            List<BaselineMediumDO> details = baselineMediumMapper.selectListByEntityIds(ids);
            exportDatas.forEach(p->{
                List<String> str = details.stream().filter(p1 -> ObjectUtil.equal(p1.getEntityId(), p.getId())).map(BaselineMediumDO::getCommodityName).collect(Collectors.toList());
                //设置运输介质名称
                if(ObjectUtil.isNotEmpty(str))
                    p.setBaselineMediumName(String.join(",",str));
                //设置油耗核算区间

                //设置过路费标准区间
                    p.setTolliInterval(p.getTollStart()+"-"+p.getTollEnd());
            });
        }
        return exportDatas;
    }

    @Override
    public void batchUpdateBaseline(List<BaselineSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
        //    这个项目sqlserver批量操作控制台会报错
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteBaseline(List<Long> ids) {
        // 在这里处理批量删除逻辑
        //baselineMapper.deleteBatchIds(ids);  //这个项目sqlserver批量删除会报错
        if (CollectionUtils.isNotEmpty(ids)) {
            for (Long id : ids) {
                baselineMapper.deleteById(id);
                //fuelConsStandardService.deleteSlavesByEntityId(id);
                //baselineMediumTypeService.deleteSlavesByEntityId(id);
                //baselineMediumService.deleteSlavesByEntityId(id);
            }
        }
    }

    @Override
    public List<BaselineExcelVO> importPreviewList(List<BaselineExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            //throw exception(BASELINE_IMPORT_LIST_IS_EMPTY);
        }

        List<BaselineExcelVO> excelVo = BeanUtils.toBean(importDatas, BaselineExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        //throw exception(BASELINE_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
        return null;
    }

    @Override
    public ImportResult importData(List<BaselineExcelVO> importReqVo) {

        if (ObjectUtils.isEmpty(importReqVo)) {
             throw exception(BASELINE_IMPORT_EXISTS);
        }

        List<BaselineExcelVO> importDatas = importReqVo.get(0).getImportDatas();
        int total = importDatas.size();
        int importCount = 0;
        int failCount = 0;
        List<BaselineExcelVO> failedData = new ArrayList<>();

        for (BaselineExcelVO data : importDatas) {
            try {
                processImportData(data);
                importCount++;
            } catch (Exception e) {
                failCount++;
                data.setHasError(true);
                Map<String, String> failDataMap = new HashMap<>();
                failDataMap.put("Error", e.getMessage());
                data.setFailData(failDataMap);
                failedData.add(data);
                // logger.error("导入数据失败: " + data, e);
            }
        }

        return buildImportResult(total, importCount, failCount, failedData);
    }
    private void processImportData(BaselineExcelVO data) throws Exception {
        BaselineSaveReqVO baselineSaveReqVO = BeanUtils.toBean(data, BaselineSaveReqVO.class);
//        //新增前校验
//        duplicationCheck(baselineSaveReqVO);
//        saveOrUpdateCheck(baselineSaveReqVO);
        BaselineDO baselineDO = BeanUtils.toBean(baselineSaveReqVO, BaselineDO.class);
        baselineMapper.insert(baselineDO);
    }

    private ImportResult<BaselineExcelVO> buildImportResult(int total, int importCount, int failCount, List<BaselineExcelVO> failedData) {
        return ImportResult.<BaselineExcelVO>builder()
                .total(total)
                .importCount(importCount)
                .failCount(failCount)
                .success(failCount == 0)
                .data(failedData)
                .build();
    }

    @Override
    public List<Map<String, Object>> getRouteInfo(TransRouteReqVO reqVO) {
        LambdaQueryWrapper<BaselineDO> wrapper = Wrappers.lambdaQuery();
        wrapper.select(BaselineDO::getId, BaselineDO::getTransportRoutes)
                .eq(BaselineDO::getLoadingManufacturerId, reqVO.getLoadingManufacturerId())
                .eq(BaselineDO::getUnloadingManufacturerId, reqVO.getUnloadingManufacturerId())
                .eq(BaselineDO::getLoadingAddressId, reqVO.getLoadingAddressId())
                .eq(BaselineDO::getUnloadingAddressId, reqVO.getUnloadingAddressId())
                .eq(BaselineDO::getCompanyId, reqVO.getCompanyId())
                .eq(BaselineDO::getCompanyId, reqVO.getMediumId())
                .eq(BaselineDO::getRouteType, reqVO.getRouteType())
                .le(BaselineDO::getStartTime, reqVO.getDate().atStartOfDay())
                .ge(BaselineDO::getEndTime, reqVO.getDate().atStartOfDay());
        List<BaselineDO> baselineDOS = baselineMapper.selectList(wrapper);
        return baselineDOS.stream().map(p -> new HashMap<String, Object>() {{
            put("id", p.getId());
            put("transportRoute", p.getTransportRoutes());
        }}).collect(Collectors.toList());
    }

    @Override
    public HashMap<String, Object> getFuelConsStandardInfo(Long companyId, Long settlementId) {
        HashMap<String, Object> result = new HashMap<>();
        // 查询运输合同
        LambdaQueryWrapper<CarriageContractDOApi> contractQueryWrapper = new LambdaQueryWrapper<>();
        contractQueryWrapper.eq(CarriageContractDOApi::getForwardingCompanyId, companyId)
                .eq(CarriageContractDOApi::getConsignCompanyId, settlementId)
                .select(CarriageContractDOApi::getId, CarriageContractDOApi::getMeasurementId);
        CarriageContractDOApi carriageContractDOApi = contractMapper1.selectOne(contractQueryWrapper);

        // 如果运输合同存在，继续查询运输合同明细
        if (carriageContractDOApi != null) {
            LambdaQueryWrapper<CarriageInvoicesDOApi> invoicesQueryWrapper = new LambdaQueryWrapper<>();
            invoicesQueryWrapper.eq(CarriageInvoicesDOApi::getCarriageId, carriageContractDOApi.getId())
                    .orderByDesc(CarriageInvoicesDOApi::getCreateTime)
                    .select(CarriageInvoicesDOApi::getId, CarriageInvoicesDOApi::getContractPrice);

            List<CarriageInvoicesDOApi> carriageInvoicesDOApis = carriageInvoicesMapper1.selectList(invoicesQueryWrapper);
            CarriageInvoicesDOApi carriageInvoicesDOApi = carriageInvoicesDOApis.get(0);
            if (carriageInvoicesDOApi != null){
                result.put("fareRate",carriageInvoicesDOApi.getContractPrice());
                result.put("measurementId", carriageContractDOApi.getMeasurementId());
            }

        }

        return result;
    }

    @Override
    public BaselineRespVO getSettleBaseline(SettleBaselineReqVO reqVO){
        List<BaselineDO> baselineDOList = baselineMapper.getSettleBaselineList(reqVO);
        for (BaselineDO baselineDO : baselineDOList) {
            List<BaselineMediumTypeDO> baselineMediumTypeRespDOList = baselineMediumTypeService.getSlavesByEntityId(baselineDO.getId());
            List<Long> baselineMediumTypeIds = baselineMediumTypeRespDOList.stream().map(BaselineMediumTypeDO::getId).collect(Collectors.toList());
            if (!baselineMediumTypeIds.contains(reqVO.getMediumTypeId())){
                continue;
            }
            List<BaselineMediumDO> baselineMediumDOList = baselineMediumService.getSlavesByEntityId(baselineDO.getId());
            List<Long> baselineMediumIds = baselineMediumDOList.stream().map(BaselineMediumDO::getId).collect(Collectors.toList());
            if (!baselineMediumIds.contains(reqVO.getMediumId())){
                continue;
            }
            return getBaseline(baselineDO.getId());
        }
        return null;
    }


}