package com.fmyd888.fengmao.module.information.service.transportdetail;

import com.fmyd888.fengmao.module.information.common.CardDetailPage;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailSaveReqVO;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * 运输证明细 Service 接口
 *
 * @author 丰茂
 */
public interface TransportDetailService {

    /**
     * 创建运输证明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTransportDetail(Long transportId, @Valid TransportDetailSaveReqVO createReqVO);

    /**
     * 创建运输证绑定的多条运输明细
     *
     * @param transportId
     * @param list2
     */
    void createTransportDetailList(Long transportId, List<TransportDetailSaveReqVO> list2);

    /**
     * 更新运输证明细
     *
     * @param transportId 主表
     * @param updateReqVO 更新信息
     */
    void updateTransportDetail(Long transportId, TransportDetailSaveReqVO updateReqVO);

    /**
     * 更新多条运输证明细
     *
     * @param transportId
     * @param list2
     */
    void updateTransportDetailList(Long transportId, List<TransportDetailSaveReqVO> list2);

    /**
     * 根据运输证删除运输证明细
     *
     * @param transportId 编号
     */
    void deleteTransportDetail(Long transportId);

    /**
     * 获得运输证明细
     *
     * @param id 编号
     * @return 运输证明细
     */
    TransportDetailRespVO getTransportDetail(Long id);

    /**
     * 通过运输证id获取对应的明细列表
     *
     * @param transportId
     * @return
     */
    List<TransportDetailSaveReqVO> getDetailListByTransportId(Long transportId);

    /**
     * 获得运输证明细分页
     *
     * @param pageReqVO 分页查询
     * @return 运输证明细分页
     */
    CardDetailPage<TransportDetailRespVO> selectDetailPage(TransportDetailPageReqVO pageReqVO);

    /**
     * 运输证明细编码
     */
    List<HashMap<String, Object>> selectTransportDetailCode();

}
