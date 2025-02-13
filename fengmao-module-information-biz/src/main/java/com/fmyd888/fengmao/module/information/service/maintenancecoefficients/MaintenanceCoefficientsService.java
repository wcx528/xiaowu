package com.fmyd888.fengmao.module.information.service.maintenancecoefficients;

import java.util.*;
import javax.validation.*;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.maintenancecoefficients.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.maintenancecoefficients.MaintenanceCoefficientsDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;

/**
 * 保养系数维护 Service 接口
 *
 * @author 丰茂
 */
public interface MaintenanceCoefficientsService {

    /**
     * 创建保养系数维护
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMaintenanceCoefficients(@Valid MaintenanceCoefficientsSaveReqVO createReqVO);

    /**
     * 更新保养系数维护
     *
     * @param updateReqVO 更新信息
     */
    void updateMaintenanceCoefficients(@Valid MaintenanceCoefficientsSaveReqVO updateReqVO);

    /**
     * 删除保养系数维护
     *
     * @param id 编号
     */
    void deleteMaintenanceCoefficients(Long id);

    /**
     * 获得保养系数维护
     *
     * @param id 编号
     * @return 保养系数维护
     */
    MaintenanceCoefficientsDO getMaintenanceCoefficients(Long id);

    /**
     * 获得保养系数维护列表
     *
     * @param ids 编号
     * @return 保养系数维护列表
     */
    List<MaintenanceCoefficientsDO> getMaintenanceCoefficientsList(Collection<Long> ids);

    /**
    * 获得保养系数维护分页
    *
    * @param pageReqVO 分页查询
    * @return 保养系数维护分页
    */
    PageResult<MaintenanceCoefficientsDO> getMaintenanceCoefficientsPage(MaintenanceCoefficientsPageReqVO pageReqVO);


    /**
     * 获得保养系数维护列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 保养系数维护列表
     */
    List<MaintenanceCoefficientsDO> getMaintenanceCoefficientsList(MaintenanceCoefficientsListReqVO exportReqVO);

    /**
    * 批量更新保养系数维护列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateMaintenanceCoefficients(List<MaintenanceCoefficientsSaveReqVO> updateReqVOList);

    /**
    * 批量删除保养系数维护列表
    *
    * @param ids 编号列表
    */
    void batchDeleteMaintenanceCoefficients(List<Long> ids);

    /**
    * 导入保养系数维护返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<MaintenanceCoefficientsExcelVO> importPreviewList(List<MaintenanceCoefficientsExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入保养系数维护列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(MaintenanceCoefficientsExcelVO importReqVo);
}