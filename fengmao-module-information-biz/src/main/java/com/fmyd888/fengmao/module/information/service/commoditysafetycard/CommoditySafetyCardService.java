package com.fmyd888.fengmao.module.information.service.commoditysafetycard;

import java.util.*;
import javax.validation.*;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.commoditysafetycard.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.commoditysafetycard.CommoditySafetyCardDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;

/**
 * 安全告知卡 Service 接口
 *
 * @author 巫晨旭
 */
public interface CommoditySafetyCardService {

    /**
     * 创建安全告知卡
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommoditySafetyCard(@Valid CommoditySafetyCardSaveReqVO createReqVO);

    /**
     * 更新安全告知卡
     *
     * @param updateReqVO 更新信息
     */
    void updateCommoditySafetyCard(@Valid CommoditySafetyCardSaveReqVO updateReqVO);

    /**
     * 删除安全告知卡
     *
     * @param id 编号
     */
    void deleteCommoditySafetyCard(Long id);

    /**
     * 获得安全告知卡
     *
     * @param id 编号
     * @return 安全告知卡
     */
    CommoditySafetyCardDO getCommoditySafetyCard(Long id);

    /**
     * 获得安全告知卡列表
     *
     * @param ids 编号
     * @return 安全告知卡列表
     */
    List<CommoditySafetyCardDO> getCommoditySafetyCardList(Collection<Long> ids);

    /**
    * 获得安全告知卡分页
    *
    * @param pageReqVO 分页查询
    * @return 安全告知卡分页
    */
    PageResult<CommoditySafetyCardDO> getCommoditySafetyCardPage(CommoditySafetyCardPageReqVO pageReqVO);


    /**
     * 获得安全告知卡列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 安全告知卡列表
     */
    List<CommoditySafetyCardDO> getCommoditySafetyCardList(CommoditySafetyCardListReqVO exportReqVO);

    /**
    * 批量更新安全告知卡列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateCommoditySafetyCard(List<CommoditySafetyCardSaveReqVO> updateReqVOList);

    /**
    * 批量删除安全告知卡列表
    *
    * @param ids 编号列表
    */
    void batchDeleteCommoditySafetyCard(List<Long> ids);

    /**
    * 导入安全告知卡返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<CommoditySafetyCardExcelVO> importPreviewList(List<CommoditySafetyCardExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入安全告知卡列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(CommoditySafetyCardExcelVO importReqVo);
}