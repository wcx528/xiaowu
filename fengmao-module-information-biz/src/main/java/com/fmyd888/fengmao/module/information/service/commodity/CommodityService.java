package com.fmyd888.fengmao.module.information.service.commodity;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 货物 Service 接口
 *
 * @author 丰茂企业
 */
public interface CommodityService {

    /**
     * 创建货物
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommodity(@Valid CommodityCreateReqVO createReqVO);

    /**
     * 更新货物
     *
     * @param updateReqVO 更新信息
     */
    void updateCommodity(@Valid CommodityUpdateReqVO updateReqVO);

    /**
     * 删除货物
     *
     * @param id 编号
     */
    void deleteCommodity(Long id);

    /**
     * 获得货物
     *
     * @param id 编号
     * @return 货物
     */
    CommodityRespVO getCommodity(Long id);

    /**
     * 获得货物列表
     *
     * @param ids 编号
     * @return 货物列表
     */
    List<CommodityRespVO> getCommodityList(Collection<Long> ids);

    /**
     * 获得货物分页
     *
     * @param pageReqVO 分页查询
     * @return 货物分页
     */
    PageResult<CommodityRespVO> getCommodityPage(CommodityPageReqVO pageReqVO);

    /**
     * 获得货物列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 货物列表
     */
    List<CommodityExcelVO> getCommodityList(CommodityExportReqVO exportReqVO);

    /**
     * 批量更新货物列表
     *
     * @param updateReqVOList 批量更新信息列表
     */
    void batchUpdateCommodity(List<CommodityUpdateReqVO> updateReqVOList);

    /**
     * 批量删除货物列表
     *
     * @param ids 编号列表
     */
    void batchDeleteCommodity(List<Long > ids);

    /**
     * 批量新增导入货物列表
     *
     * @param importReqVOList 批量新增导入信息列表
     */
    void batchImportCommodity(List<CommodityDO> importReqVOList);

    /**
     * 修改货物状态
     * @param id
     */
    void updateStatus(Long id);

    /**
     * 获得货物所属组织信息集合
     * @param commodityId
     * @return
     */
    List<DeptSimpleRespVO> getDeptInfoList(Long commodityId);

    /**
     *
     * @param commodityDeptReqVO  分配的组织货物和组织信息对象
     */
    void assignCommodityToDept(CommodityDeptReqVO commodityDeptReqVO);
    /**
     * 物料精简列表获得
     * @return
     */
    List<HashMap<String,Object>> getSimpleCommodityList(ComodityQueryParam param);

    /**
     * 下载安全卡
     */
    void downloadSafeCard(List<Long> ids, HttpServletResponse response);
}
