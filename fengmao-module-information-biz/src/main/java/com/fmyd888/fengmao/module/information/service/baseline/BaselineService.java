package com.fmyd888.fengmao.module.information.service.baseline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.route.TransRouteReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.settlebaseline.SettleBaselineReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基线 Service 接口
 *
 * @author 丰茂
 */
public interface BaselineService extends IService<BaselineDO> {

    /**
     * 创建基线
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBaseline(@Valid BaselineSaveReqVO createReqVO);

    /**
     * 更新基线
     *
     * @param updateReqVO 更新信息
     */
    void updateBaseline(@Valid BaselineSaveReqVO updateReqVO);

    /**
     * 删除基线
     *
     * @param id 编号
     */
    void deleteBaseline(Long id);

    /**
     * 获得基线
     *
     * @param id 编号
     * @return 基线
     */
    BaselineRespVO getBaseline(Long id);

    /**
     * 获得基线列表
     *
     * @param ids 编号
     * @return 基线列表
     */
    List<BaselineDO> getBaselineList(Collection<Long> ids);

    /**
    * 获得基线分页
    *
    * @param pageReqVO 分页查询
    * @return 基线分页
    */
    PageResult<BaselineRespVO> getBaselinePage(BaselinePageReqVO pageReqVO);

    /**
     * 获得基线列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 基线列表
     */
    List<BaselineExcelVO> getBaselineList(BaselineListReqVO exportReqVO);

    /**
    * 批量更新基线列表
     * 基本上也没有这个操作，
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateBaseline(List<BaselineSaveReqVO> updateReqVOList);

    /**
    * 批量删除基线列表
    *
    * @param ids 编号列表
    */
    void batchDeleteBaseline(List<Long> ids);

    /**
    * 导入基线返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<BaselineExcelVO> importPreviewList(List<BaselineExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入基线列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(List<BaselineExcelVO> importReqVo);

    /**
     * 功能描述：获取路线信息
     *
     * @param reqVO
     * @return {@link Map }<{@link String }, {@link Object }>
     * @author 小逺
     * @date 2024/06/12
     */
    List<Map<String, Object>> getRouteInfo(TransRouteReqVO reqVO);

    /**
     * 根据所属公司和结算方查询运价和计量单位
     */
    HashMap<String, Object> getFuelConsStandardInfo(Long companyId, Long settlementId);

    /**
     * 获取结算基线
     */
    BaselineRespVO getSettleBaseline(SettleBaselineReqVO reqVO);
}
