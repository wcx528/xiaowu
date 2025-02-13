package com.fmyd888.fengmao.module.information.service.currency;

import java.util.*;
import javax.validation.*;

import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.module.information.controller.admin.currency.CurrencyDeptReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;

/**
 * 货币 Service 接口
 *
 * @author 丰茂企业
 */
public interface CurrencyService {

    /**
     * 创建货币
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCurrency(@Valid CurrencyCreateReqVO createReqVO);

    /**
     * 更新货币
     *
     * @param updateReqVO 更新信息
     */
    void updateCurrency(@Valid CurrencyUpdateReqVO updateReqVO);

    /**
     * 删除货币
     *
     * @param id 编号
     */
    void deleteCurrency(Long id);

    /**
     * 获得货币
     *
     * @param id 编号
     * @return 货币
     */
    CurrencyRespVO getCurrency(Long id);

    /**
     * 获得货币列表
     *
     * @param ids 编号
     * @return 货币列表
     */
    List<CurrencyRespVO> getCurrencyList(Collection<Long> ids);

    /**
     * 获得货币分页
     *
     * @param pageReqVO 分页查询
     * @return 货币分页
     */
    PageResult<CurrencyRespVO> getCurrencyPage(CurrencyPageReqVO pageReqVO);

    /**
     * 获得货币列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 货币列表
     */
    List<CurrencyExcelVO> getCurrencyList(CurrencyExportReqVO exportReqVO);

    /**
     * 批量更新货币列表
     *
     * @param updateReqVOList 批量更新信息列表
     */
    void batchUpdateCurrency(List<CurrencyUpdateReqVO> updateReqVOList);

    /**
     * 批量删除货币列表
     *
     * @param ids 编号列表
     */
    void batchDeleteCurrency(List<Long> ids);

    /**
     * 批量新增导入货币列表
     *
     * @param importReqVOList 批量新增导入信息列表
     */
    void batchImportCurrency(List<CurrencyDO> importReqVOList);

    /**
     * 修改货币状态
     * @param id
     */
    void updateStatus(Long id);

    /**
     * 获得货币所属组织信息集合
     * @param currencyId
     * @return
     */
    List<DeptSimpleRespVO> getDeptInfoList(Long currencyId);

    /**
     *
     * @param currencyDeptReqVO  分配的组织货币和组织信息对象
     */
    void assignCurrencyToDept(CurrencyDeptReqVO currencyDeptReqVO);

    /**
     * 导入货币
     * @param importCurrency
     * @param isUpdateSupport
     * @return
     */
    CurrencyImportRespVO importCurrencyList(List<CurrencyImportExcelVO> importCurrency, boolean isUpdateSupport);

    /**
     * 计量单位精简接口
     * @return
     */
    List<HashMap<String, Object>>getSimpleCurrencyList(CommonQueryParam param);
}
