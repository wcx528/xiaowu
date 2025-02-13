package com.fmyd888.fengmao.module.information.service.coefficient;

import java.util.*;
import javax.validation.*;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.CoefficientDetailDTO;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.CoefficientDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.LoadingRateDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MaintenanceCostsDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MileageAccountingDO;
import org.apache.ibatis.annotations.Param;

/**
 * 系数维护 Service 接口
 *
 * @author 丰茂
 */
public interface CoefficientService {

    /**
     * 创建系数维护
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCoefficient(@Valid CoefficientSaveReqVO createReqVO);

    /**
     * 更新系数维护
     *
     * @param updateReqVO 更新信息
     */
    void updateCoefficient(@Valid CoefficientSaveReqVO updateReqVO);

    /**
     * 删除系数维护
     *
     * @param id 编号
     */
    void deleteCoefficient(Long id);

    /**
     * 获得系数维护
     *
     * @param id 编号
     * @return 系数维护
     */
    CoefficientDO getCoefficient(Long id);

    /**
     * 获得系数维护详情
     *
     * @param subordinateCompaniesId 所属公司id
     * @return 系数维护
     */
    CoefficientDetailDTO coefficientDetails(@Param("subordinateCompaniesId") Long subordinateCompaniesId);

    /**
     * 获得系数维护列表
     *
     * @param ids 编号
     * @return 系数维护列表
     */
    List<CoefficientDO> getCoefficientList(Collection<Long> ids);

    /**
    * 获得系数维护分页
    *
    * @param pageReqVO 分页查询
    * @return 系数维护分页
    */
    PageResult<CoefficientDO> getCoefficientPage(CoefficientPageReqVO pageReqVO);

    // ==================== 子表（装载率系数明细） ====================

    /**
    * 获得装载率系数明细分页
    *
    * @param pageReqVO 分页查询
    * @param coefficientId 系数维护表id
    * @return 装载率系数明细分页
    */
    PageResult<LoadingRateDO> getLoadingRatePage(PageParam pageReqVO, Long coefficientId);

    /**
    * 获得装载率系数明细列表
    *
    * @param coefficientId 系数维护表id
    * @return 装载率系数明细列表
    */
    List<LoadingRateDO> getLoadingRateListByCoefficientId(Long coefficientId);

    // ==================== 子表（工资里程核算系数明细） ====================

    Long createLoadingRate(LoadingRateDO loadingRate);

    void updateLoadingRate(LoadingRateDO loadingRate);

    void deleteLoadingRate(Long id);

    LoadingRateDO getLoadingRate(Long id);

    /**
    * 获得工资里程核算系数明细分页
    *
    * @param pageReqVO 分页查询
    * @param coefficientId 系数维护表id
    * @return 工资里程核算系数明细分页
    */
    PageResult<MileageAccountingDO> getMileageAccountingPage(PageParam pageReqVO, Long coefficientId);

    /**
    * 获得工资里程核算系数明细列表
    *
    * @param coefficientId 系数维护表id
    * @return 工资里程核算系数明细列表
    */
    List<MileageAccountingDO> getMileageAccountingListByCoefficientId(Long coefficientId);

    // ==================== 子表（维修费用系数维护明细） ====================

    Long createMileageAccounting(MileageAccountingDO mileageAccounting);

    void updateMileageAccounting(MileageAccountingDO mileageAccounting);

    void deleteMileageAccounting(Long id);

    MileageAccountingDO getMileageAccounting(Long id);

    /**
    * 获得维修费用系数维护明细分页
    *
    * @param pageReqVO 分页查询
    * @param coefficientId 系数维护表id
    * @return 维修费用系数维护明细分页
    */
    PageResult<MaintenanceCostsDO> getMaintenanceCostsPage(PageParam pageReqVO, Long coefficientId);

    /**
    * 获得维修费用系数维护明细列表
    *
    * @param coefficientId 系数维护表id
    * @return 维修费用系数维护明细列表
    */
    List<MaintenanceCostsDO> getMaintenanceCostsListByCoefficientId(Long coefficientId);


    /**
     * 获得系数维护列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 系数维护列表
     */
    List<CoefficientDO> getCoefficientList(CoefficientListReqVO exportReqVO);

    /**
    * 批量更新系数维护列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateCoefficient(List<CoefficientSaveReqVO> updateReqVOList);

    /**
    * 批量删除系数维护列表
    *
    * @param ids 编号列表
    */
    void batchDeleteCoefficient(List<Long> ids);

    /**
    * 导入系数维护返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<CoefficientExcelVO> importPreviewList(List<CoefficientExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入系数维护列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(CoefficientExcelVO importReqVo);

    Long createMaintenanceCosts(MaintenanceCostsDO maintenanceCosts);

    void updateMaintenanceCosts(MaintenanceCostsDO maintenanceCosts);

    void deleteMaintenanceCosts(Long id);

    MaintenanceCostsDO getMaintenanceCosts(Long id);
}
