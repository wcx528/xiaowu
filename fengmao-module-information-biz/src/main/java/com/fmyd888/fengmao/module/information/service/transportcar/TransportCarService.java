package com.fmyd888.fengmao.module.information.service.transportcar;

import java.util.*;
import javax.validation.*;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.transportcar.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportcar.TransportCarDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;

/**
 * 运输证办理车辆关联 Service 接口
 *
 * @author 丰茂
 */
public interface TransportCarService {

    /**
     * 创建运输证办理车辆关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTransportCar(@Valid TransportCarSaveReqVO createReqVO);

    /**
     * 插入多条运输证办理车辆关联
     * @param transportDetailId
     * @param transportCarIds
     */
    void insertTransportCarList(Long transportDetailId,List<Long> transportCarIds);

    /**
     * 更新多条运输证办理车辆关联
     * @param transportDetailId
     * @param transportCarIds
     */
    void updateTransportCarList(Long transportDetailId,List<Long> transportCarIds);

    /**
     * 更新运输证办理车辆关联
     *
     * @param updateReqVO 更新信息
     */
    void updateTransportCar(@Valid TransportCarSaveReqVO updateReqVO);

    /**
     * 删除运输证办理车辆关联
     *
     * @param id 编号
     */
    void deleteTransportCar(Long id);

    /**
     * 获得运输证办理车辆关联
     *
     * @param id 编号
     * @return 运输证办理车辆关联
     */
    TransportCarDO getTransportCar(Long id);

    /**
     * 通过明细id获得车辆信息
     * @param detailId
     * @return
     */
    List<TransportCarDO> getCarListByDetailId(Long detailId);

    /**
     * 获得运输证办理车辆关联列表
     *
     * @param ids 编号
     * @return 运输证办理车辆关联列表
     */
    List<TransportCarDO> getTransportCarList(Collection<Long> ids);

    /**
    * 获得运输证办理车辆关联分页
    *
    * @param pageReqVO 分页查询
    * @return 运输证办理车辆关联分页
    */
    PageResult<TransportCarDO> getTransportCarPage(TransportCarPageReqVO pageReqVO);


    /**
     * 获得运输证办理车辆关联列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 运输证办理车辆关联列表
     */
    List<TransportCarDO> getTransportCarList(TransportCarListReqVO exportReqVO);

    /**
    * 批量更新运输证办理车辆关联列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateTransportCar(List<TransportCarSaveReqVO> updateReqVOList);

    /**
    * 批量删除运输证办理车辆关联列表
    *
    * @param ids 编号列表
    */
    void batchDeleteTransportCar(List<Long> ids);

    /**
    * 导入运输证办理车辆关联返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<TransportCarExcelVO> importPreviewList(List<TransportCarExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入运输证办理车辆关联列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(TransportCarExcelVO importReqVo);
}
