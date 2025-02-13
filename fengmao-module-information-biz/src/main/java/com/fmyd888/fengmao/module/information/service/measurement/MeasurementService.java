package com.fmyd888.fengmao.module.information.service.measurement;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 计量单位表 Service 接口
 *
 * @author 丰茂企业
 */
public interface MeasurementService {

    /**
     * 创建计量单位表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMeasurement(@Valid MeasurementCreateReqVO createReqVO);

    /**
     * 更新计量单位表
     *
     * @param updateReqVO 更新信息
     */
    void updateMeasurement(@Valid MeasurementUpdateReqVO updateReqVO);

    /**
     * 删除计量单位表
     *
     * @param id 编号
     */
    void deleteMeasurement(Long id);

    /**
     * 获得计量单位表
     *
     * @param id 编号
     * @return 计量单位表
     */
    MeasurementRespVO getMeasurement(Long id);

    /**
     * 获得计量单位表列表
     *
     * @param ids 编号
     * @return 计量单位表列表
     */
    List<MeasurementRespVO> getMeasurementList(Collection<Long> ids);

    /**
     * 获得计量单位表分页
     *
     * @param pageReqVO 分页查询
     * @return 计量单位表分页
     */
    PageResult<MeasurementRespVO> getMeasurementPage(MeasurementPageReqVO pageReqVO);


    /**
     * 计量单位导出字段选择
     */
    List<HashMap<String,Object>> getExportList();

    /**
     * 获得计量单位表列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 计量单位表列表
     */
    List<MeasurementExcelVO> getMeasurementList(MeasurementExportReqVO exportReqVO);

    /**
     * 查询计量单位的name
     *
     * @param
     */

    List<MeasurementDO> getNameListById();

    void updateStatus(Long id);

    void assignMeasurementToDept(MeasurementDeptReqVO storeDeptReqVO);

    /**
     * 获得计量单位精简信息
     *
     * @return
     */
    List<HashMap<String, Object>> getSimpleMeasurementList(CommonQueryParam param);
}
