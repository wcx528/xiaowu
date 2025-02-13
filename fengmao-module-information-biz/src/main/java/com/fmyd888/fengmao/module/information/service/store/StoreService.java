package com.fmyd888.fengmao.module.information.service.store;

import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.DownMainVehicleExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.DownTrailerExcelVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDO;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 仓库 Service 接口
 *
 * @author 丰茂企业
 */
public interface StoreService {

    /**
     * 创建仓库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStore(@Valid StoreCreateReqVO createReqVO);

    /**
     * 更新仓库
     *
     * @param updateReqVO 更新信息
     */
    void updateStore(@Valid StoreUpdateReqVO updateReqVO);

    /**
     * 删除仓库
     *
     * @param id 编号
     */
    void deleteStore(Long id);

    /**
     * 获得仓库
     *
     * @param id 编号
     * @return 仓库
     */
    StoreRespVO getStore(Long id);

    /**
     * 获得仓库列表
     *
     * @param ids 编号
     * @return 仓库列表
     */
    List<StoreRespVO> getStoreList(Collection<Integer> ids);

    /**
     * 获得仓库分页
     *
     * @param pageReqVO 分页查询
     * @return 仓库分页
     */
    PageResult<StoreRespVO> getStorePage(StorePageReqVO pageReqVO);

    /**
     * 仓库导出字段选择
     */
    List<HashMap<String,Object>> getExportList();

    /**
     * 仓库导入预览
     */
    List<DownStoreExcelVO> importPreviewList(List<DownStoreExcelVO> importDatas, boolean isUpdateSupport);

    /**
     * 批量新增导入仓库列表
     *
     * @param importStore 批量新增导入信息列表
     */
    ImportResult importMainVehicleList(DownStoreExcelVO importStore);

    /**
     * 获得仓库列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 仓库列表
     */
    List<StoreDO> getStoreList(StoreExportReqVO exportReqVO);

    /**
    * 批量更新仓库列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateStore(List<StoreUpdateReqVO> updateReqVOList);

    /**
    * 批量删除仓库列表
    *
    * @param ids 编号列表
    */
    void batchDeleteStore(List<Integer> ids);



    /**
     * 修改仓库状态
     * @param id
     */
    void updateStatus(Long id);

    /**
     * 获得仓库所属组织信息集合
     * @param storeId
     * @return
     */
    List<DeptSimpleRespVO> getDeptInfoList(Long storeId);

    /**
     *
     * @param storeDeptReqVO  分配的组织仓库和组织信息对象
     */
    void assignStoreToDept(StoreDeptReqVO storeDeptReqVO);

    /**
     * 批量导入仓库
     * @param importStore  导入仓库列表
     * @param isUpdateSupport  是否支持更新
     * @return  导入结果
     */
    StoreImportRespVO importStoreList(List<StoreImportExcelVO> importStore, boolean isUpdateSupport);

    /**
     * 仓库精简列表获得
     * @return
     */
    List<HashMap<String, Object>> getSimpleStoreList(CommonQueryParam param);
}
