package com.fmyd888.fengmao.module.information.service.riskmaintenance;

import java.util.*;
import javax.validation.*;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto.RiskInspectionItemOuterDTO;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto.RiskMaintenanceOuterDTO;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskMaintenanceDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskInspectionItemDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskMaintenanceCommodityDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 隐患排查项目维护表(主表) Service 接口
 *
 * @author 丰茂
 */
public interface RiskMaintenanceService {

    /**
     * 创建隐患排查项目维护表(主表)
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRiskMaintenance(@Valid RiskMaintenanceSaveReqVO createReqVO);

    /**
     * 更新隐患排查项目维护表(主表)
     *
     * @param updateReqVO 更新信息
     */
    void updateRiskMaintenance(@Valid RiskMaintenanceSaveReqVO updateReqVO);

    /**
     * 删除隐患排查项目维护表(主表)
     *
     * @param id 编号
     */
    void deleteRiskMaintenance(Long id);

    /**
     * 获得隐患排查项目维护表(主表)
     *
     * @param id 编号
     * @return 隐患排查项目维护表(主表)
     */
    RiskMaintenanceDO getRiskMaintenance(Long id);

    /**
     * 查看详情
     * @param companyId 所属公司id
     * @param checkType 检查类型
     * @param type (1.隐患排查2.趟检)
     * @return
     */
    RiskInspectionItemOuterDTO selectRiskInspectionItemById(@Param("companyId") Long companyId, @Param("checkType")Integer checkType, @Param("type") Integer type);

    /**
     * 获得隐患排查项目维护表(主表)列表
     *
     * @param ids 编号
     * @return 隐患排查项目维护表(主表)列表
     */
    List<RiskMaintenanceDO> getRiskMaintenanceList(Collection<Long> ids);

    /**
    * 获得隐患排查项目维护表(主表)分页
    *
    * @param pageReqVO 分页查询
    * @return 隐患排查项目维护表(主表)分页
    */
    PageResult<RiskMaintenanceDO> getRiskMaintenancePage(RiskMaintenancePageReqVO pageReqVO);

    // ==================== 子表（检查类型表(子表)） ====================

    /**
    * 获得检查类型表(子表)分页
    *
    * @param pageReqVO 分页查询
    * @param entityId 主表ID
    * @return 检查类型表(子表)分页
    */
    PageResult<RiskInspectionItemDO> getRiskInspectionItemPage(PageParam pageReqVO, Long entityId);

    /**
    * 获得检查类型表(子表)列表
    *
    * @param entityId 主表ID
    * @return 检查类型表(子表)列表
    */
    List<RiskInspectionItemDO> getRiskInspectionItemListByEntityId(Long entityId);

    // ==================== 子表（隐患排查项目维护与介质关联） ====================

    /**
    * 获得隐患排查项目维护与介质关联分页
    *
    * @param pageReqVO 分页查询
    * @param entityId 主表ID
    * @return 隐患排查项目维护与介质关联分页
    */
    PageResult<RiskMaintenanceCommodityDO> getRiskMaintenanceCommodityPage(PageParam pageReqVO, Long entityId);

    /**
    * 获得隐患排查项目维护与介质关联列表
    *
    * @param entityId 主表ID
    * @return 隐患排查项目维护与介质关联列表
    */
    List<RiskMaintenanceCommodityDO> getRiskMaintenanceCommodityListByEntityId(Long entityId);


    /**
     * 获得隐患排查项目维护表(主表)列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 隐患排查项目维护表(主表)列表
     */
    List<RiskMaintenanceDO> getRiskMaintenanceList(RiskMaintenanceListReqVO exportReqVO);

    /**
    * 批量更新隐患排查项目维护表(主表)列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateRiskMaintenance(List<RiskMaintenanceSaveReqVO> updateReqVOList);

    /**
    * 批量删除隐患排查项目维护表(主表)列表
    *
    * @param ids 编号列表
    */
    void batchDeleteRiskMaintenance(List<Long> ids);

    /**
    * 导入隐患排查项目维护表(主表)返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<RiskMaintenanceExcelVO> importPreviewList(List<RiskMaintenanceExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入隐患排查项目维护表(主表)列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(RiskMaintenanceExcelVO importReqVo);

    /**
     * 隐患排查精简接口
     */
    RiskInspectionItemOuterDTO getRiskMaintenanceSimpleList(Integer checkType,Long companyId);
}
