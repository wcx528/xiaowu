package com.fmyd888.fengmao.module.information.service.maintenancecoefficients;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiFunction;
import com.fmyd888.fengmao.module.information.controller.admin.maintenancecoefficients.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.maintenancecoefficients.MaintenanceCoefficientsDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import com.fmyd888.fengmao.module.information.dal.mysql.maintenancecoefficients.MaintenanceCoefficientsMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;
import static com.fmyd888.fengmao.module.system.enums.ErrorCodeConstants.*;

/**
 * 保养系数维护 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class MaintenanceCoefficientsServiceImpl implements MaintenanceCoefficientsService {

    @Resource
    private MaintenanceCoefficientsMapper maintenanceCoefficientsMapper;
    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private DeptMapper deptMapper;

    @Override
    public Long createMaintenanceCoefficients(MaintenanceCoefficientsSaveReqVO createReqVO) {
        // 插入
        MaintenanceCoefficientsDO maintenanceCoefficients = BeanUtils.toBean(createReqVO, MaintenanceCoefficientsDO.class);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        maintenanceCoefficients.setDeptId(loginUserDeptId);

        maintenanceCoefficients.setCreateTime(LocalDateTime.now());
//        maintenanceCoefficients.setUpdateTime(LocalDateTime.now());
        maintenanceCoefficientsMapper.insert(maintenanceCoefficients);

    
        // 返回
        return maintenanceCoefficients.getId();
    }

    @Override
    public void updateMaintenanceCoefficients(MaintenanceCoefficientsSaveReqVO updateReqVO) {
        Long id = updateReqVO.getId();
        // 校验存在
        validateMaintenanceCoefficientsExists(id);

        // 更新
        MaintenanceCoefficientsDO updateObj = BeanUtils.toBean(updateReqVO, MaintenanceCoefficientsDO.class);
        if (updateObj != null){
            MaintenanceCoefficientsDO maintenanceCoefficientsDO = updateObj.setId(id);
            maintenanceCoefficientsDO.setUpdateTime(LocalDateTime.now());
            maintenanceCoefficientsMapper.updateById(maintenanceCoefficientsDO);
        }


    }

    @Override
    public void deleteMaintenanceCoefficients(Long id) {
        // 校验存在
        validateMaintenanceCoefficientsExists(id);
        // 删除
        maintenanceCoefficientsMapper.deleteById(id);
    }

    private void validateMaintenanceCoefficientsExists(Long id) {
        if (maintenanceCoefficientsMapper.selectById(id) == null) {
        throw exception(MAINTENANCE_COEFFICIENTS_NOT_EXISTS);
        }
    }


    @Override
    public MaintenanceCoefficientsDO getMaintenanceCoefficients(Long id) {
        return maintenanceCoefficientsMapper.selectById(id);
    }

    @Override
    public List<MaintenanceCoefficientsDO> getMaintenanceCoefficientsList(Collection<Long> ids) {
        return maintenanceCoefficientsMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MaintenanceCoefficientsDO> getMaintenanceCoefficientsPage(MaintenanceCoefficientsPageReqVO pageReqVO) {
        PageResult<MaintenanceCoefficientsDO> pageResult = maintenanceCoefficientsMapper.selectJoinTilePage(pageReqVO);

        return pageResult;
    }

    @Override
    public List<MaintenanceCoefficientsDO> getMaintenanceCoefficientsList(MaintenanceCoefficientsListReqVO listReqVO) {
        return maintenanceCoefficientsMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateMaintenanceCoefficients(List<MaintenanceCoefficientsSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteMaintenanceCoefficients(List<Long> ids) {
        // 在这里处理批量删除逻辑
        maintenanceCoefficientsMapper.deleteBatchIds(ids);
    }

    @Override
    public List<MaintenanceCoefficientsExcelVO> importPreviewList(List<MaintenanceCoefficientsExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(MAINTENANCE_COEFFICIENTS_IMPORT_LIST_IS_EMPTY);
        }

        List<MaintenanceCoefficientsExcelVO> excelVo = BeanUtils.toBean(importDatas, MaintenanceCoefficientsExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(MAINTENANCE_COEFFICIENTS_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(MaintenanceCoefficientsExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
            throw exception(MAINTENANCE_COEFFICIENTS_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(MAINTENANCE_COEFFICIENTS_IMPORT_PORT_REQUIRE);
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