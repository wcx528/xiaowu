package com.fmyd888.fengmao.module.information.service.trailer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.common.vo.ImportRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.DownMainVehicleExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleDownloadVO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.dto.TrailerListDTO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车挂档案 Service 接口
 *
 * @author 丰茂
 */
public interface TrailerService {

    /**
     * 创建车挂档案
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTrailer(@Valid TrailerCreateReqVO createReqVO);

    /**
     * 更新车挂档案
     *
     * @param updateReqVO 更新信息
     */
    void updateTrailer(@Valid TrailerUpdateReqVO updateReqVO);

    /**
     * 删除车挂档案
     *
     * @param id 编号
     */
    void deleteTrailer(Long id);

    /**
     * 获得车挂档案
     *
     * @param id 编号
     * @return 车挂档案
     */
    TrailerRespVO getTrailer(Long id);

    /**
     * 获得车挂档案列表
     *
     * @param ids 编号
     * @return 车挂档案列表
     */
    List<TrailerDO> getTrailerList(Collection<Long> ids);

    /**
     * 获得车挂档案分页
     *
     * @param pageReqVO 分页查询
     * @return 车挂档案分页
     */
    PageResult<TrailerRespVO> getTrailerPage(TrailerPageReqVO pageReqVO);
    /**
     * 获得车挂档案列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 车挂档案列表
     */
    List<DownTrailerExcelVO> getTrailerList(DownTrailerExcelVO exportReqVO);

    /**
    * 批量更新车挂档案列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateTrailer(List<TrailerUpdateReqVO> updateReqVOList);

    /**
    * 批量删除车挂档案列表
    *
    * @param ids 编号列表
    */
    void batchDeleteTrailer(List<Long> ids);

    /**
     *
     * @param pageReqVO  关键字分页查询信息
     * @return  关键字分页查询结果
     */
    Page<TrailerRespVO> selectPageByKeyword(TrailerKeywordPageReqVO pageReqVO);
    /**
     * 证件档案下载
     *
     * @param vehicleDonwloadVO 车挂列表和文件类型列表
     */
    MainVehicleDownloadVO getTrailerCertificateFile(TrailerDonwloadVO vehicleDonwloadVO);

    /**
     * 车挂的字段列表
     */
    List<HashMap<String,Object>> getExportList();

    /**
     * 车挂导入预览结果
     *
     * @param importDatas 导入的excel数据
     * @param isUpdateSupport 是否支持更新
     */
    List<DownTrailerExcelVO> importPreviewList(List<DownTrailerExcelVO> importDatas, boolean isUpdateSupport);


    /**
     * 批量导入车挂数据
     * @return  导入结果
     */
    ImportResult importTrailerList(DownTrailerExcelVO importTrailer);

    /**
     * 修改车挂记录状态
     * @param id
     */
    void updateStatus(Long id);
    /**
     * 根据id查询到自动带出的信息
     * @return
     */
    TrailerBasicRespVO getTrailerById(Long id);

    /**
     * 车挂精简接口
     * @param param
     * @return
     */
    List<TrailerListDTO> getTrailerList(TrailerListReqVO param);
    /**
     * 车挂文档的下载
     * @param id  车挂id
     * @param response 相应头对象
     */
     void getTrailerArchivetDownload(Long id, HttpServletResponse response);


    /**
     *  多个车挂附件下载
     * @param ids 车挂ids集合
     * @param response 响应体
     */
    void getTrailerArchiveDownload02(List<Long> ids, HttpServletResponse response);

    /**
     * 车挂文档的预览与打印
     * @param id  车挂id
     */
    TrailerPrintingVO getTrailerArchivetPrinting( Long id);
    /**
     * 车挂2---车辆检测好评定登记表文档的预览与打印
     * @param
     */
    List<EvaluationVO> getTrailerPrintingList2(Long id);
    /**
     * 3---车辆维护和修理登记表的预览与打印
     * @param
     */
    List<MaintenanceRepairVO> getTrailerPrintingList3(Long id);
    /**
     * 4---车辆主要部件更换登记表的预览与打印
     * @param
     */
    List<PartReplacementVO> getTrailerPrintingList4(Long id);
    /**
     * 5---车辆变更登记表的预览与打印
     * @param
     */
    List<CarChangeVO> getTrailerPrintingList5(Long id);

    /**
     *6--车辆机损事故登记表的预览与打印
     * @param
     */
    List<AccidentVO> getTrailerPrintingList6(Long id);
    /**
     * 2---车辆检测好评定登记表
     * @param createReqVO
     * @return
     */
    Long insertEvaluation(EvaluationVO createReqVO);

    void deletEvaluation(Long id,Long evaluationId);

    List<EvaluationVO> getEvaluationList02(Long id);

    /**
     * 6--车辆机损事故登记表
     * @param createReqVO
     * @return
     */
    Long insertAccident(AccidentVO createReqVO);

    void deletAccident(Long id,Long accidentId);

    List<AccidentVO> getAccidentList02(Long id);
    /**
     * 5---车辆变更登记表
     * @param createReqVO
     * @return
     */
    Long insertCarChange(CarChangeVO createReqVO);

    void deletCarChange(Long id,Long carChangeId);

    List<CarChangeVO> getCarChangeList02(Long id);
    /**
     * 3---车辆维护和修理登记表
     * @param createReqVO
     * @return
     */
    Long insertMaintenanceRepair(MaintenanceRepairVO createReqVO);

    void deletMaintenanceRepair(Long id,Long maintenanceRepairId);

    List<MaintenanceRepairVO> getMaintenanceRepairList02(Long id);
    /**
     * 4---车辆主要部件更换登记表
     * @param createReqVO
     * @return
     */
    Long insertPartReplacement(PartReplacementVO createReqVO);

    void deletPartReplacement(Long id,Long partReplacementId);

    List<PartReplacementVO> getPartReplacementList02(Long id);

    /**
     * 车挂的历史附件信息
     * @param sourceId 车挂id
     * @return
     */
    TrailerHistoryVO listHistoryFile(Long sourceId);


    /**
     * 根据id查询到自动带出"可装卸货物"的信息
     * @return
     */
    List<Map<String, Object>> getCommodityInfoById(Long id);
}
