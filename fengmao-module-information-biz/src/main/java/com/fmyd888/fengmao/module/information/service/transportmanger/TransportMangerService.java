package com.fmyd888.fengmao.module.information.service.transportmanger;

import com.fmyd888.fengmao.framework.common.pojo.CommonQueryBaseParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportdetail.TransportDetailDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportmanger.TransportMangerDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运输证管理 Service 接口
 *
 * @author 丰茂
 */
public interface TransportMangerService {

    /**
     * 创建运输证管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTransportManger(@Valid TransportMangerSaveReqVO createReqVO);

    /**
     * 更新运输证管理
     *
     * @param updateReqVO 更新信息
     */
    void updateTransportManger(@Valid TransportMangerSaveReqVO updateReqVO);

    /**
     * 删除运输证管理
     *
     * @param id 编号
     */
    void deleteTransportManger(Long id);

    /**
     * 获得运输证管理
     *
     * @param id 编号
     * @return 运输证管理
     */
    TransportMangerRespVO getTransportManger(Long id);

    /**
     * 获得运输证管理列表
     *
     * @param ids 编号
     * @return 运输证管理列表
     */
    List<TransportMangerDO> getTransportMangerList(Collection<Long> ids);


    // ==================== 子表（运输证明细） ====================

    /**
     * 获得运输证明细分页
     *
     * @param pageReqVO   分页查询
     * @param transportId 运输证Id
     * @return 运输证明细分页
     */
    PageResult<TransportDetailDO> getTransportDetailPage(PageParam pageReqVO, Long transportId);

    /**
     * 获得运输证明细列表
     *
     * @param transportId 运输证Id
     * @return 运输证明细列表
     */
    List<TransportDetailDO> getTransportDetailListByTransportId(Long transportId);


    /**
     * 获得运输证管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 运输证管理列表
     */
    List<TransportMangerDO> getTransportMangerList(TransportMangerListReqVO exportReqVO);

    /**
     * 批量更新运输证管理列表
     *
     * @param updateReqVOList 批量更新信息列表
     */
    void batchUpdateTransportManger(List<TransportMangerSaveReqVO> updateReqVOList);

    /**
     * 批量删除运输证管理列表
     *
     * @param ids 编号列表
     */
    void batchDeleteTransportManger(List<Long> ids);

    /**
     * 导入运输证管理返回预览结果
     *
     * @param importDatas     导入的excel数据
     * @param isUpdateSupport 是否支持更新
     */
    List<TransportMangerExcelVO> importPreviewList(List<TransportMangerExcelVO> importDatas, boolean isUpdateSupport);

    /**
     * 导入运输证管理列表
     *
     * @param importReqVo 导入的excel数据
     */
    ImportResult importData(TransportMangerExcelVO importReqVo);

    /**
     * @return 关键字分页查询结果
     * @param关键字分页查询信息
     */
    CardPage<TransportMangerRespVO> selectPageByKeyword(TransportMangerKeywordPageReqVO pageReqVO);

    /**
     *  运输证下载
     * @param id  运输证id
     * @return
     */
    List<TransportDownloadExcelVO> transportDownload(Long id);

    /**
     * 根据id查询到自动带出的信息
     *
     * @return
     */
    TransportMangerSaveReqVO getTransportManger02(Long id);

    /**
     * 获取运输证精简信息
     *
     * @return
     */
    List<TransportMangerSimpleRespVO> getTransportMangerList();

    /**
     * 功能描述：获取运输证车辆信息
     *
     * @param param
     * @return {@link List }<{@link CarBasicRespVO }>
     * @author 小逺
     * @date 2024/06/12
     */
    List<TransportCarRespVO> getTransportCarList(CommonQueryBaseParam param);

    /**
     * 运输证编号接口
     */
    List<HashMap<String, Object>> selectTransportNo();

    /**
     *定时任务--》根据数量或者结束日期更新状态
     */
    void updateTransportDetailStatus();
}
