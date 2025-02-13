package com.fmyd888.fengmao.module.information.service.mainvehicle;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import io.micrometer.core.lang.Nullable;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 车头档案 Service 接口
 *
 * @author 丰茂
 */
public interface MainVehicleService {

    /**
     * 创建车头档案
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMainVehicle(@Valid MainVehicleCreateReqVO createReqVO);

    /**
     * 更新车头档案
     *
     * @param updateReqVO 更新信息
     */
    void updateMainVehicle(@Valid MainVehicleUpdateReqVO updateReqVO);

    /**
     * 删除车头档案
     *
     * @param id 编号
     */
    void deleteMainVehicle(Long id);

    /**
     * 获得车头档案
     *
     * @param id 编号
     * @return 车头档案
     */
    MainVehicleRespVO getMainVehicle(Long id);

    /**
     * 获得车头档案列表
     *
     * @param ids 编号
     * @return 车头档案列表
     */
    List<MainVehicleDO> getMainVehicleList(Collection<Long> ids);

    /**
     * 获得车头档案分页
     *
     * @param pageReqVO 分页查询
     * @return 车头档案分页
     */
    PageResult<MainVehicleRespVO> getMainVehiclePage(MainVehiclePageReqVO pageReqVO);

    /**
     * 车头Excel 导出
     *
     * @return 车头档案列表
     */
    List<DownMainVehicleExcelVO> getMainVehicleList(MainVehicleExportReqVO exportReqVO);

    /**
     * 车头导出的字段列表
     */
    List<HashMap<String,Object>> getExportList();

    /**
     * 批量更新车头档案列表
     *
     * @param updateReqVOList 批量更新信息列表
     */
    void batchUpdateMainVehicle(List<MainVehicleUpdateReqVO> updateReqVOList);

    /**
     * 批量删除车头档案列表
     *
     * @param ids 编号列表
     */
    void batchDeleteMainVehicle(List<Long> ids);

    /**
     * 批量新增导入车头档案列表
     *
     * @param importReqVOList 批量新增导入信息列表
     */
    void batchImportMainVehicle(List<MainVehicleDO> importReqVOList);

    /**
     * @param pageReqVO 关键字分页查询信息
     * @return 关键字分页查询结果
     */
    Page<MainVehicleRespVO> selectPageByKeyword(MainVehicleKeywordPageReqVO pageReqVO);

    /**
     * 证件档案下载
     *
     * @param vehicleDonwloadReqVO 车头列表和文件类型列表
     */
    MainVehicleDownloadVO getMainVehicleCertificateFile(MainVehicleDownloadReqVO vehicleDonwloadReqVO);

    /**
     * 删除下载的临时附件寄生成的压缩包文件
     *
     * @param mainVehicleDownloadVO 路径信息
     */
    void deleteFiles(MainVehicleDownloadVO mainVehicleDownloadVO);

    /**
     * 车头导入预览结果
     *
     * @param importDatas 导入的excel数据
     * @param isUpdateSupport 是否支持更新
     */
    List<DownMainVehicleExcelVO> importPreviewList(List<DownMainVehicleExcelVO> importDatas, boolean isUpdateSupport);

    /**
     * 批量导入车头数据
     *
     * @param importMainVehicle 导入车头列表
     * @return 导入结果
     */
    ImportResult importMainVehicleList(DownMainVehicleExcelVO importMainVehicle);

    /**
     * 车头禁用/启用
     *
     * @param id
     */
    void updateStatus(Long id);

    /**
     * 根据id查询到自动带出的信息
     *
     * @return
     */
    MainVehicleBasicRespVO getMainVehicle02(Long id);

    /**
     * 获取车头精简信息
     *
     * @return
     */
    List<MainVehicleSimpleRespVO> getMainVehicleDetails(@Nullable Long companyId,@Nullable Boolean isOut);

    /**
     * 车头文档的下载
     *
     * @param id       车头id
     * @param response 相应头对象
     */
    void getMainVehicleArchivetDownload(Long id, HttpServletResponse response);

    /**
     *  多个车头附件下载
     * @param ids 车头ids集合
     * @param response 响应体
     */
    void getMainVehicleArchivetDownload02(List<Long> ids, HttpServletResponse response);

    /**
     * 车头文档的预览与打印
     *
     * @param id 车头id
     */
    MainVehiclePrintingVO getMainVehicleArchivetPrinting(Long id);

    /**
     * 车头2---车辆检测好评定登记表文档的预览与打印
     *
     * @param
     */
    List<EvaluationVO> getMainVehiclePrintingList2(Long id);

    /**
     * 3---车辆维护和修理登记表的预览与打印
     *
     * @param
     */
    List<MaintenanceRepairVO> getMainVehiclePrintingList3(Long id);

    /**
     * 4---车辆主要部件更换登记表的预览与打印
     *
     * @param
     */
    List<PartReplacementVO> getMainVehiclePrintingList4(Long id);

    /**
     * 5---车辆变更登记表的预览与打印
     *
     * @param
     */
    List<CarChangeVO> getMainVehiclePrintingList5(Long id);

    /**
     * 6---车辆行驶里程登记表的预览与打印
     *
     * @param
     */
    List<MileageVO> getMainVehiclePrintingList6(Long id);

    /**
     * 7--车辆机损事故登记表的预览与打印
     *
     * @param
     */
    List<AccidentVO> getMainVehiclePrintingList7(Long id);

    /**
     * 2---车辆检测好评定登记表
     *
     * @param createReqVO
     * @return
     */
    Long insertEvaluation(EvaluationVO createReqVO);

    void deletEvaluation(Long id, Long evaluationId);

    List<EvaluationVO> getEvaluationList01(Long id);

    /**
     * 7--插入车辆机损事故登记记录
     *
     * @param createReqVO 插入的信息
     * @return 插入成功ID
     */
    Long insertAccident(AccidentVO createReqVO);

    void deletAccident(Long id, Long accidentId);

    List<AccidentVO> getAccidentList01(Long id);

    /**
     * 5---车辆变更登记表
     *
     * @param createReqVO
     * @return
     */
    Long insertCarChange(CarChangeVO createReqVO);

    void deletCarChange(Long id, Long carChangeId);

    List<CarChangeVO> getCarChangeList01(Long id);

    /**
     * 3---车辆维护和修理登记表
     *
     * @param createReqVO
     * @return
     */
    Long insertMaintenanceRepair(MaintenanceRepairVO createReqVO);

    void deletMaintenanceRepair(Long id, Long maintenanceRepairId);

    List<MaintenanceRepairVO> getMaintenanceRepairList01(Long id);

    /**
     * 4---车辆主要部件更换登记表
     *
     * @param createReqVO
     * @return
     */

    Long insertPartReplacement(PartReplacementVO createReqVO);

    void deletPartReplacement(Long id, Long partReplacementId);

    List<PartReplacementVO> getPartReplacementList01(Long id);

    /**
     * 6---车辆行驶里程登记表
     *
     * @param createReqVO
     * @return
     */

    Long insertMileage(MileageVO createReqVO);

    void deletMileage(Long id, Long mileageId);

    List<MileageVO> getMileageList01(Long id);

    /**
     * 车头的历史附件信息
     * @param sourceId 车头id
     * @return
     */
    MainVehicleHistoryVO listHistoryFile(Long sourceId);


    /**
     * 车头禁用/启用
     */
    void updateMainVehicleStatus(Long id);




}
