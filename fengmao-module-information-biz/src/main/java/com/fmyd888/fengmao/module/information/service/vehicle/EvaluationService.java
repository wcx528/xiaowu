package com.fmyd888.fengmao.module.information.service.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.EvaluationVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 2---车辆检测好评定登记表 Service 接口
 *
 * @author luomuyou
 */
public interface EvaluationService {

    /**
     * 创建
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEvaluation(@Valid EvaluationVO createReqVO);

    /**
     * 删除
     *
     * @param mainVehicleId 删除id
     */
    void deleteEvaluation(Long mainVehicleId,Long evaluationId);

    /**
     * 删除
     *
     * @param trailerId 删除id
     */
    void deleteEvaluation02(Long trailerId,Long evaluationId);


    /**
     * 通过车头id,获得车牌变更记录列表
     *
     * @param mainVehicleId 车头id
     * @return 车牌变更记录列表
     */
    List<EvaluationVO> getEvaluationList01(Long mainVehicleId);

    /**
     * 通过车挂id,获得车牌变更记录列表
     *
     * @param trailerId 车挂id
     * @return 车牌变更记录列表
     */
    List<EvaluationVO> getEvaluationList02(Long trailerId);
}


