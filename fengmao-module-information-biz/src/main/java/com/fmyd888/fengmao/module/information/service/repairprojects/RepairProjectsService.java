package com.fmyd888.fengmao.module.information.service.repairprojects;

import java.util.*;
import javax.validation.*;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.repairprojects.RepairProjectsDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;

/**
 * 维修项目 Service 接口
 *
 * @author 丰茂
 */
public interface RepairProjectsService {

    /**
     * 创建维修项目
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRepairProjects(@Valid RepairProjectsSaveReqVO createReqVO);

    /**
     * 更新维修项目
     *
     * @param updateReqVO 更新信息
     */
    void updateRepairProjects(@Valid RepairProjectsSaveReqVO updateReqVO);

    /**
     * 删除维修项目
     *
     * @param id 编号
     */
    void deleteRepairProjects(Long id);

    /**
     * 获得维修项目
     *
     * @param id 编号
     * @return 维修项目
     */
    RepairProjectsDO getRepairProjects(Long id);

    /**
     * 获得维修项目列表
     *
     * @param ids 编号
     * @return 维修项目列表
     */
    List<RepairProjectsDO> getRepairProjectsList(Collection<Long> ids);

    /**
    * 获得维修项目分页
    *
    * @param pageReqVO 分页查询
    * @return 维修项目分页
    */
    PageResult<RepairProjectsDO> getRepairProjectsPage(RepairProjectsPageReqVO pageReqVO);


    /**
     * 获得维修项目列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 维修项目列表
     */
    List<RepairProjectsDO> getRepairProjectsList(RepairProjectsListReqVO exportReqVO);

    /**
    * 批量更新维修项目列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateRepairProjects(List<RepairProjectsSaveReqVO> updateReqVOList);

    /**
    * 批量删除维修项目列表
    *
    * @param ids 编号列表
    */
    void batchDeleteRepairProjects(List<Long> ids);

    /**
    * 导入维修项目返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<RepairProjectsExcelVO> importPreviewList(List<RepairProjectsExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入维修项目列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(RepairProjectsExcelVO importReqVo);

    /**
    * 获取维修项目列表
    *
    * @param reqVO 查询条件
    * @return 维修项目列表
    */
    List<RepairProjectsDO> getRepairProjectsAllList(RepairProjectBaseReqVO reqVO);

    /**
    * 获取维修项目列表
    *
    * @param names 维修项目名称列表
    * @return 维修项目列表
    */
    List<RepairProjectsDO> getRepairProjectListByNames(List<String> names);
}
