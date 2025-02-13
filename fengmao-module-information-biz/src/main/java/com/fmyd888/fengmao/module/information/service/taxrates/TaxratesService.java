package com.fmyd888.fengmao.module.information.service.taxrates;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 税率 Service 接口
 *
 * @author 丰茂企业
 */
public interface TaxratesService {

    /**
     * 创建税率
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTaxrates(@Valid TaxratesCreateReqVO createReqVO);

    /**
     * 更新税率
     *
     * @param updateReqVO 更新信息
     */
    void updateTaxrates(@Valid TaxratesUpdateReqVO updateReqVO);

    /**
     * 删除税率
     *
     * @param id 编号
     */
    void deleteTaxrates(Long id);

    /**
     * 获得税率
     *
     * @param id 编号
     * @return 税率
     */
    TaxratesRespVO getTaxrates(Long id);

    /**
     * 获得税率列表
     *
     * @param ids 编号
     * @return 税率列表
     */
    List<TaxratesRespVO> getTaxratesList(Collection<Long> ids);

    /**
     * 获得税率分页
     *
     * @param pageReqVO 分页查询
     * @return 税率分页
     */
    PageResult<TaxratesRespVO> getTaxratesPage(TaxratesPageReqVO pageReqVO);

    /**
     * 获得税率列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 税率列表
     */
    List<TaxratesDO> getTaxratesList(TaxratesExportReqVO exportReqVO);

    /**
     * 批量更新税率列表
     *
     * @param updateReqVOList 批量更新信息列表
     */
    void batchUpdateTaxrates(List<TaxratesUpdateReqVO> updateReqVOList);

    /**
     * 批量删除税率列表
     *
     * @param ids 编号列表
     */
    void batchDeleteTaxrates(List<Integer> ids);

    /**
     * 批量新增导入税率列表
     *
     * @param importReqVOList 批量新增导入信息列表
     */
    void batchImportTaxrates(List<TaxratesDO> importReqVOList);

    /**
     * 修改税率状态
     *
     * @param id
     */
    void updateStatus(Long id);

    /**
     * @param taxratesDeptReqVO 分配的组织税率和组织信息对象
     */
    void assignTaxratesToDept(TaxratesDeptReqVO taxratesDeptReqVO);

    /**
     * 批量导入税率
     *
     * @param importTaxrates  导入税率列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    TaxratesImportRespVO importTaxratesList(List<TaxratesImportExcelVO> importTaxrates, boolean isUpdateSupport);

    /**
     * 功能描述：税率精简列表获得
     *
     * @param param
     * @return {@link List }<{@link HashMap }<{@link String },{@link Object }>>
     * @author 小逺
     * @date 2024/04/30 22:02
     */
    List<HashMap<String, Object>> getSimpleStoreList(CommonQueryParam param);

    /**
     * 功能描述：根据税率名称获得税率
     *
     * @param taxratesName 税率名称
     * @return {@link TaxratesRespVO }
     * @author 小是企业
     * @date 2024/04/30 22:02
     */
    TaxratesRespVO getTaxratesByName(String taxratesName);

    /**
     * 功能描述：税率导入预览
     *
     * @param list
     * @param updateSupport
     * @return {@link List }<{@link TaxratesExcelVO }>
     * @author 小逺
     * @date 2024/08/08 20:10
     */
    List<TaxratesExcelVO> importPreviewList(List<TaxratesExcelVO> list, Boolean updateSupport);

    /**
     * 功能描述：导入税率
     *
     * @param importReqVo   导入的数据
     * @return {@link ImportResult }
     * @author 小逺
     * @date 2024/08/08 21:01
     */
    ImportResult importData(TaxratesExcelVO importReqVo);
}
