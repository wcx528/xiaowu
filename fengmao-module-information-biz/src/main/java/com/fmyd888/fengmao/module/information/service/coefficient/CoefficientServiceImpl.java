package com.fmyd888.fengmao.module.information.service.coefficient;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.CoefficientDetailDTO;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.LoadingRateDTO;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.MaintenanceCostsDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.CoefficientDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.LoadingRateDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MaintenanceCostsDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MileageAccountingDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.information.dal.mysql.coefficient.LoadingRateMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.coefficient.MaintenanceCostsMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.coefficient.MileageAccountingMapper;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.fmyd888.fengmao.module.information.controller.admin.coefficient.vo.*;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import com.fmyd888.fengmao.module.information.dal.mysql.coefficient.CoefficientMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 系数维护 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class CoefficientServiceImpl implements CoefficientService {

    @Resource
    private CoefficientMapper coefficientMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private LoadingRateMapper loadingRateMapper;
    @Resource
    private MileageAccountingMapper mileageAccountingMapper;
    @Resource
    private MaintenanceCostsMapper maintenanceCostsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCoefficient(CoefficientSaveReqVO createReqVO) {
        // 插入
        CoefficientDO coefficient = BeanUtils.toBean(createReqVO, CoefficientDO.class);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        coefficient.setDeptId(loginUserDeptId);
        coefficientMapper.insert(coefficient);

        //获取系数维护表id
        Long coefficientId = coefficient.getId();

        //2.插入子表:装载率系数明细列表
        for (LoadingRateDO loadingRateDO   : createReqVO.getLoadingRates()) {
            // 将业务计划ID与单据明细ID关联
            loadingRateDO.setCoefficientId(coefficientId);
            //获取部门
            loadingRateDO.setDeptId(loginUserDeptId);
            // 插入装载率系数明细
            loadingRateMapper.insert(loadingRateDO);
        }

//        //3.插入子表:工资里程核算系数明细列表
//        for (MileageAccountingDO mileageAccountingDO   : createReqVO.getMileageAccountings()) {
//            // 将业务计划ID与单据明细ID关联
//            mileageAccountingDO.setCoefficientId(coefficientId);
//            //获取部门
//            mileageAccountingDO.setDeptId(loginUserDeptId);
//            // 插入工资里程核算系数明细
//            mileageAccountingMapper.insert(mileageAccountingDO);
//        }

        //4.插入子表:维修费用系数维护明细列表
        for (MaintenanceCostsDO maintenanceCostsDO   : createReqVO.getMaintenanceCostss()) {
            // 将业务计划ID与单据明细ID关联
            maintenanceCostsDO.setCoefficientId(coefficientId);
            //获取部门
            maintenanceCostsDO.setDeptId(loginUserDeptId);
            // 插入装载率系数明细
            maintenanceCostsMapper.insert(maintenanceCostsDO);
        }

//        // 插入子表
//        createLoadingRateList(coefficient.getId(), createReqVO.getLoadingRates(), loginUserDeptId);
//        createMileageAccountingList(coefficient.getId(), createReqVO.getMileageAccountings(), loginUserDeptId);
//        createMaintenanceCostsList(coefficient.getId(), createReqVO.getMaintenanceCostss(), loginUserDeptId);

        // 返回
        return coefficient.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCoefficient(CoefficientSaveReqVO updateReqVO) {
        // 校验存在
        validateCoefficientExists(updateReqVO.getId());
        // 更新
        CoefficientDO updateObj = BeanUtils.toBean(updateReqVO, CoefficientDO.class);
        coefficientMapper.updateById(updateObj);

        Long id = updateObj.getId();

        // 删除旧的装载率系数明细
        List<LoadingRateDO> loadingRateDOS = loadingRateMapper.selectListByCoefficientId(id);
        List<Long> loadingIds = loadingRateDOS.stream().map(LoadingRateDO::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(loadingIds)){
            loadingRateMapper.deleteBatchIds(loadingIds);
        }

        //2.插入新的装载率系数明细
        List<LoadingRateDO> reqVOList = updateReqVO.getLoadingRates();
        if (CollectionUtils.isNotEmpty(loadingIds)) {
            for (LoadingRateDO loadingRateDO : reqVOList) {
                loadingRateDO.setCoefficientId(id);
                // 执行插入操作
                loadingRateMapper.insert(loadingRateDO);
            }
        }

        // 删除旧的维修费用系数维护明细
        List<MaintenanceCostsDO> maintenanceCostsDOS = maintenanceCostsMapper.selectListByCoefficientId(id);
        List<Long> maintenanceCostsIds = maintenanceCostsDOS.stream().map(MaintenanceCostsDO::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(maintenanceCostsIds)){
            maintenanceCostsMapper.deleteBatchIds(maintenanceCostsIds);
        }

        //3.插入新的维修费用系数维护明细
        List<MaintenanceCostsDO> maintenanceCostss = updateReqVO.getMaintenanceCostss();
        if (CollectionUtils.isNotEmpty(maintenanceCostsIds)) {
            for (MaintenanceCostsDO maintenanceCostsDO : maintenanceCostss) {
                maintenanceCostsDO.setCoefficientId(id);
                // 执行插入操作
                maintenanceCostsMapper.insert(maintenanceCostsDO);
            }
        }
            // 更新子表
//        updateLoadingRateList(updateReqVO.getId(), updateReqVO.getLoadingRates());
//        updateMileageAccountingList(updateReqVO.getId(), updateReqVO.getMileageAccountings());
//        updateMaintenanceCostsList(updateReqVO.getId(), updateReqVO.getMaintenanceCostss());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCoefficient(Long id) {
        // 校验存在
        validateCoefficientExists(id);
        // 删除
        coefficientMapper.deleteById(id);
    }

    private void validateCoefficientExists(Long id) {
        if (coefficientMapper.selectById(id) == null) {
        throw exception(COEFFICIENT_NOT_EXISTS);
        }
    }


    @Override
    public CoefficientDO getCoefficient(Long id) {
        return coefficientMapper.selectById(id);
    }

    @Override
    public CoefficientDetailDTO coefficientDetails(Long subordinateCompaniesId) {
           //根据所属公司id查询主表信息
        CoefficientDetailDTO coefficientDetailDTO = coefficientMapper.selectBySubordinateCompaniesId(subordinateCompaniesId);

            if (coefficientDetailDTO != null){
                //        //查询当前主表id
                Long coefficientId = coefficientDetailDTO.getId();

                //        //查询子表信息：装载率系数明细列表
                List<LoadingRateDTO> loadingRateDTOS = coefficientMapper.selectLoadingRateByCoefficientId(coefficientId);
                coefficientDetailDTO.setLoadingRates(loadingRateDTOS);
//
//        //查询子表信息：装载率系数明细列表
                List<MaintenanceCostsDTO> maintenanceCostsDTOS = coefficientMapper.selectMaintenanceCostsByCoefficientId(coefficientId);
                coefficientDetailDTO.setMaintenanceCostss(maintenanceCostsDTOS);

                return coefficientDetailDTO;
            }
        CoefficientDetailDTO emptyDTO = new CoefficientDetailDTO();
        // 创建一个只包含一个空LoadingRateDTO对象的列表
        List<LoadingRateDTO> emptyLoadingRates = new ArrayList<>();
        emptyLoadingRates.add(new LoadingRateDTO()); // 创建一个新的LoadingRateDTO实例，所有字段默认值为null
        emptyDTO.setLoadingRates(emptyLoadingRates);
        // 创建一个只包含一个空MaintenanceCostsDTO对象的列表
        List<MaintenanceCostsDTO> maintenanceCostsDTOS = new ArrayList<>();
        maintenanceCostsDTOS.add(new MaintenanceCostsDTO()); // 创建一个新的MaintenanceCostsDTO实例，所有字段默认值为null
        emptyDTO.setMaintenanceCostss(maintenanceCostsDTOS);

        emptyDTO.setSubordinateCompaniesId(subordinateCompaniesId);
        return emptyDTO;
    }

    @Override
    public List<CoefficientDO> getCoefficientList(Collection<Long> ids) {
        return coefficientMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CoefficientDO> getCoefficientPage(CoefficientPageReqVO pageReqVO) {
        return coefficientMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CoefficientDO> getCoefficientList(CoefficientListReqVO listReqVO) {
        return coefficientMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateCoefficient(List<CoefficientSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteCoefficient(List<Long> ids) {
        // 在这里处理批量删除逻辑
        coefficientMapper.deleteBatchIds(ids);
    }

    @Override
    public List<CoefficientExcelVO> importPreviewList(List<CoefficientExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(COEFFICIENT_IMPORT_LIST_IS_EMPTY);
        }

        List<CoefficientExcelVO> excelVo = BeanUtils.toBean(importDatas, CoefficientExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(COEFFICIENT_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(CoefficientExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
            throw exception(COEFFICIENT_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(COEFFICIENT_IMPORT_PORT_REQUIRE);
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

// ==================== 子表（装载率系数明细） ====================

    @Override
    public PageResult<LoadingRateDO> getLoadingRatePage(PageParam pageReqVO, Long coefficientId) {
        return loadingRateMapper.selectPage(pageReqVO, coefficientId);
    }

    @Override
    public List<LoadingRateDO> getLoadingRateListByCoefficientId(Long coefficientId) {
        return loadingRateMapper.selectListByCoefficientId(coefficientId);
    }

    @Override
    public Long createLoadingRate(LoadingRateDO loadingRate) {
            //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        loadingRate.setDeptId(loginUserDeptId);
        loadingRateMapper.insert(loadingRate);
        return loadingRate.getId();
    }

    @Override
    public void updateLoadingRate(LoadingRateDO loadingRate) {
        // 校验存在
        validateLoadingRateExists(loadingRate.getId());
        // 更新
        loadingRateMapper.updateById(loadingRate);
    }

    @Override
    public void deleteLoadingRate(Long id) {
        // 校验存在
        validateLoadingRateExists(id);
        // 删除
        loadingRateMapper.deleteById(id);
    }

    @Override
    public LoadingRateDO getLoadingRate(Long id) {
        return loadingRateMapper.selectById(id);
    }

    private void validateLoadingRateExists(Long id) {
        if (loadingRateMapper.selectById(id) == null) {
        throw exception(LOADING_RATE_NOT_EXISTS);
    }
    }

    private void createLoadingRateList(Long coefficientId, List<LoadingRateDO> list) {
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        list.forEach(o -> {
            o.setCoefficientId(coefficientId);
            o.setDeptId(loginUserDeptId);
        });
        loadingRateMapper.insertBatch(list);
    }

    private void updateLoadingRateList(Long coefficientId, List<LoadingRateDO> list) {
        if (list.size() <= 0)
            return;
        //获取所有子数据
        List<LoadingRateDO> loadingRateDOS = loadingRateMapper.selectListByCoefficientId(coefficientId);
        //如果没有直接插入
        if (loadingRateDOS.size() <= 0)
            createLoadingRateList(coefficientId, list);
        else {
            //找出新增、修改、删除的数据
            BiFunction<LoadingRateDO, LoadingRateDO, Boolean> sameFunc = (oldObj, newObj) -> {
                boolean same = oldObj.getId().equals(newObj.getId());
                return same;
            };

            // 调用
            List<List<LoadingRateDO>> result = CollectionUtils.diffList(loadingRateDOS, list, sameFunc);

            //新增
            if (result.get(0).size() >= 0)
                createLoadingRateList(coefficientId, result.get(0));

            //修改
            if (result.get(1).size() >= 0)
                loadingRateMapper.updateBatch(list);

            //删除
            if (result.get(2).size() >= 0)
                loadingRateMapper.deleteBatchIds(CollectionUtils.convertList(result.get(2), p -> p.getId()));
        }
    }

// ==================== 子表（工资里程核算系数明细） ====================

    @Override
    public PageResult<MileageAccountingDO> getMileageAccountingPage(PageParam pageReqVO, Long coefficientId) {
        return mileageAccountingMapper.selectPage(pageReqVO, coefficientId);
    }

    @Override
    public List<MileageAccountingDO> getMileageAccountingListByCoefficientId(Long coefficientId) {
        return mileageAccountingMapper.selectListByCoefficientId(coefficientId);
    }

    @Override
    public Long createMileageAccounting(MileageAccountingDO mileageAccounting) {
            //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        mileageAccounting.setDeptId(loginUserDeptId);
        mileageAccountingMapper.insert(mileageAccounting);
        return mileageAccounting.getId();
    }

    @Override
    public void updateMileageAccounting(MileageAccountingDO mileageAccounting) {
        // 校验存在
        validateMileageAccountingExists(mileageAccounting.getId());
        // 更新
        mileageAccountingMapper.updateById(mileageAccounting);
    }

    @Override
    public void deleteMileageAccounting(Long id) {
        // 校验存在
        validateMileageAccountingExists(id);
        // 删除
        mileageAccountingMapper.deleteById(id);
    }

    @Override
    public MileageAccountingDO getMileageAccounting(Long id) {
        return mileageAccountingMapper.selectById(id);
    }

    private void validateMileageAccountingExists(Long id) {
        if (mileageAccountingMapper.selectById(id) == null) {
        throw exception(MILEAGE_ACCOUNTING_NOT_EXISTS);
    }
    }

    private void createMileageAccountingList(Long coefficientId, List<MileageAccountingDO> list) {
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        list.forEach(o -> {
            o.setCoefficientId(coefficientId);
            o.setDeptId(loginUserDeptId);
        });
        mileageAccountingMapper.insertBatch(list);
    }

    private void updateMileageAccountingList(Long coefficientId, List<MileageAccountingDO> list) {
        if (list.size() <= 0)
            return;
        //获取所有子数据
        List<MileageAccountingDO> mileageAccountingDOS = mileageAccountingMapper.selectListByCoefficientId(coefficientId);
        //如果没有直接插入
        if (mileageAccountingDOS.size() <= 0)
            createMileageAccountingList(coefficientId, list);
        else {
            //找出新增、修改、删除的数据
            BiFunction<MileageAccountingDO, MileageAccountingDO, Boolean> sameFunc = (oldObj, newObj) -> {
                boolean same = oldObj.getId().equals(newObj.getId());
                return same;
            };

            // 调用
            List<List<MileageAccountingDO>> result = CollectionUtils.diffList(mileageAccountingDOS, list, sameFunc);

            //新增
            if (result.get(0).size() >= 0)
                createMileageAccountingList(coefficientId, result.get(0));

            //修改
            if (result.get(1).size() >= 0)
                mileageAccountingMapper.updateBatch(list);

            //删除
            if (result.get(2).size() >= 0)
                mileageAccountingMapper.deleteBatchIds(CollectionUtils.convertList(result.get(2), p -> p.getId()));
        }
    }

// ==================== 子表（维修费用系数维护明细） ====================

    @Override
    public PageResult<MaintenanceCostsDO> getMaintenanceCostsPage(PageParam pageReqVO, Long coefficientId) {
        return maintenanceCostsMapper.selectPage(pageReqVO, coefficientId);
    }

    @Override
    public List<MaintenanceCostsDO> getMaintenanceCostsListByCoefficientId(Long coefficientId) {
        return maintenanceCostsMapper.selectListByCoefficientId(coefficientId);
    }

    @Override
    public Long createMaintenanceCosts(MaintenanceCostsDO maintenanceCosts) {
            //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        maintenanceCosts.setDeptId(loginUserDeptId);
        maintenanceCostsMapper.insert(maintenanceCosts);
        return maintenanceCosts.getId();
    }

    @Override
    public void updateMaintenanceCosts(MaintenanceCostsDO maintenanceCosts) {
        // 校验存在
        validateMaintenanceCostsExists(maintenanceCosts.getId());
        // 更新
        maintenanceCostsMapper.updateById(maintenanceCosts);
    }

    @Override
    public void deleteMaintenanceCosts(Long id) {
        // 校验存在
        validateMaintenanceCostsExists(id);
        // 删除
        maintenanceCostsMapper.deleteById(id);
    }

    @Override
    public MaintenanceCostsDO getMaintenanceCosts(Long id) {
        return maintenanceCostsMapper.selectById(id);
    }

    private void validateMaintenanceCostsExists(Long id) {
        if (maintenanceCostsMapper.selectById(id) == null) {
        throw exception(MAINTENANCE_COSTS_NOT_EXISTS);
    }
    }

    private void createMaintenanceCostsList(Long coefficientId, List<MaintenanceCostsDO> list) {
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        list.forEach(o -> {
            o.setCoefficientId(coefficientId);
            o.setDeptId(loginUserDeptId);
        });
        maintenanceCostsMapper.insertBatch(list);
    }

    private void updateMaintenanceCostsList(Long coefficientId, List<MaintenanceCostsDO> list) {
        if (list.size() <= 0)
            return;
        //获取所有子数据
        List<MaintenanceCostsDO> maintenanceCostsDOS = maintenanceCostsMapper.selectListByCoefficientId(coefficientId);
        //如果没有直接插入
        if (maintenanceCostsDOS.size() <= 0)
            createMaintenanceCostsList(coefficientId, list);
        else {
            //找出新增、修改、删除的数据
            BiFunction<MaintenanceCostsDO, MaintenanceCostsDO, Boolean> sameFunc = (oldObj, newObj) -> {
                boolean same = oldObj.getId().equals(newObj.getId());
                return same;
            };

            // 调用
            List<List<MaintenanceCostsDO>> result = CollectionUtils.diffList(maintenanceCostsDOS, list, sameFunc);

            //新增
            if (result.get(0).size() >= 0)
                createMaintenanceCostsList(coefficientId, result.get(0));

            //修改
            if (result.get(1).size() >= 0)
                maintenanceCostsMapper.updateBatch(list);

            //删除
            if (result.get(2).size() >= 0)
                maintenanceCostsMapper.deleteBatchIds(CollectionUtils.convertList(result.get(2), p -> p.getId()));
        }
    }

}