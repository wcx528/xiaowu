package com.fmyd888.fengmao.module.information.service.fleet;

import java.util.*;
import javax.validation.*;

import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.fleet.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.fleet.FleetDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;

/**
 * 车队表 Service 接口
 *
 * @author 小逺
 */
public interface FleetService {

    /**
     * 创建车队表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFleet(@Valid FleetSaveReqVO createReqVO);

    /**
     * 更新车队表
     *
     * @param updateReqVO 更新信息
     */
    void updateFleet(@Valid FleetSaveReqVO updateReqVO);

    /**
     * 删除车队表
     *
     * @param id 编号
     */
    void deleteFleet(Long id);

    /**
     * 获得车队表
     *
     * @param id 编号
     * @return 车队表
     */
    FleetDO getFleet(Long id);

    /**
     * 获得车队表列表
     *
     * @param ids 编号
     * @return 车队表列表
     */
    List<FleetDO> getFleetList(Collection<Long> ids);

    /**
    * 获得车队表分页
    *
    * @param pageReqVO 分页查询
    * @return 车队表分页
    */
    PageResult<FleetDO> getFleetPage(FleetPageReqVO pageReqVO);


    /**
     * 获得车队表列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 车队表列表
     */
    List<FleetDO> getFleetList(FleetListReqVO exportReqVO);

    /**
    * 批量更新车队表列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateFleet(List<FleetSaveReqVO> updateReqVOList);

    /**
    * 批量删除车队表列表
    *
    * @param ids 编号列表
    */
    void batchDeleteFleet(List<Long> ids);

    /**
    * 导入车队表返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<FleetExcelVO> importPreviewList(List<FleetExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入车队表列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(FleetExcelVO importReqVo);


    /**
     * 功能描述：获取车队精简信息
     *
     * @param param
     * @return {@link List }<{@link HashMap }<{@link String }, {@link Object }>>
     * @author 小逺
     * @date 2024/06/23 15:32
     */
    List<HashMap<String, Object>> getFleetSimpleList(CommonQueryParam param);
}