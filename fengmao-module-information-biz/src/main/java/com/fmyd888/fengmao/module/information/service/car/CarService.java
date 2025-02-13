package com.fmyd888.fengmao.module.information.service.car;

import java.util.*;
import javax.validation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fmyd888.fengmao.framework.common.pojo.CommonInformationQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.common.vo.CarCyclePage;
import com.fmyd888.fengmao.module.information.controller.admin.car.dto.CarChangeRespDTO;
import com.fmyd888.fengmao.module.information.controller.admin.car.dto.CarScrapOrCancelProcessReqDTO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.carpersonreplace.CarPersonReplaceDO;

/**
 * 车辆档案 Service 接口
 *
 * @author 丰茂
 */
public interface CarService extends IService<CarDO> {

    /**
     * 创建车辆档案
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createCar(@Valid CarSaveReqVO createReqVO);


    /**
     * 更新车辆档案
     *
     * @param updateReqVO 更新信息
     */
    void updateCar2(@Valid CarSaveReqVO updateReqVO);

    /**
     * 车挂车队更换审批
     *
     * @param carPersonDO
     */
    void trailerAndFleetProcessApproval(CarPersonReplaceDO carPersonDO);

    /**
     * 删除车辆档案
     *
     * @param id 编号
     */
    void deleteCar(Long id);

    /**
     * 获得车辆档案
     *
     * @param id 编号
     * @return 车辆档案
     */
    CarRespVO getCar(Long id);

    /**
     * 获得车辆档案列表
     *
     * @param ids 编号
     * @return 车辆档案列表
     */
    List<CarRespVO> getCarList(Collection<Long> ids);


    /**
     * 获得车辆档案列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 车辆档案列表
     */
    List<CarExcelVO> getCarList(CarExportReqVO exportReqVO);

    /**
     * 导出字段列表
     */
    List<HashMap<String,Object>> getExportList();

//    /**
//     * 批量更新车辆档案列表
//     *
//     * @param updateReqVOList 批量更新信息列表
//     */
//    void batchUpdateCar(List<CarSaveReqVO> updateReqVOList);

    /**
     * 批量删除车辆档案列表
     *
     * @param ids 编号列表
     */
    void batchDeleteCar(List<Long> ids);

    /**
     * 批量新增导入车辆档案列表
     *
     * @param importReqVOList 批量新增导入信息列表
     */
    void batchImportCar(List<CarDO> importReqVOList);

    /**
     * 导入车辆档案返回预览结果
     *
     * @param list     导入的excel数据
     * @param isUpdateSupport 是否支持更新
     */
    List<CarImportExcelVO> importPreviewList(List<CarImportExcelVO> list, boolean isUpdateSupport);

    /**
     * 导入车辆档案列表
     *
     * @param importReqVo 导入的excel数据
     */
    ImportResult importData(CarImportExcelVO importReqVo);


    /**
     * 条件分页查询
     * @param pageReqVO
     * @return
     */
    Page<CarRespVO> selectPageByKeyword(CarKeywordPageReqVO pageReqVO);

    /**
     * 功能描述：根据条件获取所有车辆精简信息列表
     *
     * @param param
     * @return {@link List }<{@link CarBasicRespVO }>
     * @author 小逺
     * @date 2024/07/03
     */
    List<CarBasicRespVO> getAllCarList(CommonInformationQueryParam param);

    /**
     * 功能描述：获取所有车辆详细信息（所有字段，缓存时间5分钟）
     *
     * @return {@link List }<{@link CarDO }>
     * @author 小逺
     * @date 2024/07/03
     */
    List<CarDO> getAllCarList();

    /**
     * 用通用关键字查询获得车辆周期管理分页
     *
     * @param pageReqVO
     * @return
     */
    CarCyclePage<CarCycleRespVO> selectCarCyclePage(CarCyclePageReqVO pageReqVO);

    /**
     * 车辆周期数据导出
     *
     * @param pageReqVO
     * @return
     */
    List<CarCycleRespVO> exportCarCycle(CarCyclePageReqVO pageReqVO);

    /**
     * 发起【报废】或【注销】审批流程
     *
     * @param processRespVO
     * @return 创建成功的流程实例
     */
    String createProcessInstance(CarProcessRespVO processRespVO);

    /**
     * 车队车挂更换审批回调
     */
    void carFleetFinishDing();

    /**
     * 驾押人员更换审批
     *
     * @param personnelChangeDO
     */
    void personnelProcessApproval(CarPersonReplaceDO personnelChangeDO);

    /**
     * 根据前端传过来的车头id查询更换的记录信息
     *
     * @return
     */
    ClientSettingsPage<CarChangeRespDTO> selectCarChangeByVehicleId(CarPageReqVO pageReqVO);

    /**
     * 车头、车挂报废/注销流程
     */
    void carScrapOrCancelProcessApproval(CarScrapOrCancelProcessReqDTO reqDTO);


    /**
     * 车头、车挂的注销/报废审批回调数据及处理
     */
    void updateCarScrapOrCancelProcess(String processId, Integer processStatus);


    /**
     * 驾押人员的更换审批回调数据
     */
    void carPersonProcess(String processId, Integer processStatus);

    /**
     * 车挂、车队的更换审批回调数据
     */
    void carTrailerFleetProcess(String processId, Integer processStatus);



}

