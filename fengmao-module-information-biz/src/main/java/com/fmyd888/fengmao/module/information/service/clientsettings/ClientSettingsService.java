package com.fmyd888.fengmao.module.information.service.clientsettings;

import java.util.*;
import javax.validation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto.ClientSettingsDetailDTO;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto.WechatBindDetailsDTO;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.ClientSettingsDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.StatementTemplateDO;
import org.apache.ibatis.annotations.Param;

/**
 * 客户端设置 Service 接口
 *
 * @author 丰茂
 */
public interface ClientSettingsService {

    /**
     * 创建客户端设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createClientSettings(@Valid ClientSettingsSaveReqVO createReqVO);

    /**
     * 更新客户端设置
     *
     * @param updateReqVO 更新信息
     */
    void updateClientSettings(@Valid ClientSettingsSaveReqVO updateReqVO);

    /**
     * 删除客户端设置
     *
     * @param id 编号
     */
    void deleteClientSettings(Long id);

    /**
     * 获得客户端设置
     *
     * @param id 编号
     * @return 客户端设置
     */
    ClientSettingsDO getClientSettings(Long id);

    /**
     * 获得客户端设置列表
     *
     * @param ids 编号
     * @return 客户端设置列表
     */
    List<ClientSettingsDO> getClientSettingsList(Collection<Long> ids);

    /**
     * 获得客户端设置分页
     *
     * @param pageReqVO 分页查询
     * @return 客户端设置分页
     */
    ClientSettingsPage<ClientSettingsRespVO> getClientSettingsPage(ClientSettingsPageReqVO pageReqVO);

    /**
     * 查询客户设置表详情
     */
    ClientSettingsDetailDTO getClientSettingsDetail(Long id);

    // ==================== 子表（子表-对账单模板） ====================

    /**
    * 获得子表-对账单模板分页
    *
    * @param pageReqVO 分页查询
    * @param entityId 客户端设置id，父id
    * @return 子表-对账单模板分页
    */
    PageResult<StatementTemplateDO> getStatementTemplatePage(PageParam pageReqVO, Long entityId);

    /**
    * 获得子表-对账单模板列表
    *
    * @param entityId 客户端设置id，父id
    * @return 子表-对账单模板列表
    */
    List<StatementTemplateDO> getStatementTemplateListByEntityId(Long entityId);


    /**
     * 获得客户端设置列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 客户端设置列表
     */
    List<ClientSettingsDO> getClientSettingsList(ClientSettingsListReqVO exportReqVO);

    /**
    * 批量更新客户端设置列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateClientSettings(List<ClientSettingsSaveReqVO> updateReqVOList);

    /**
    * 批量删除客户端设置列表
    *
    * @param ids 编号列表
    */
    void batchDeleteClientSettings(List<Long> ids);

    /**
    * 导入客户端设置返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<ClientSettingsExcelVO> importPreviewList(List<ClientSettingsExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入客户端设置列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(ClientSettingsExcelVO importReqVo);


    /**
     * 油卡精简接口
     */
    List<HashMap<String, Object>> selectOilCardDetailsMap(CommonQueryParam param);

    /**
     * 微信绑定精简接口
     */
    List<HashMap<String, Object>> selectWechatBindDetailsMap(@Param("searchKey") String nickname);


    //==========================微信绑定========================================
    /**
     * 获得外援微信用户与客户设置关系分页
     *
     * @return 外援微信用户与客户设置关系分页
     */
    ClientSettingsPage<WechatBindRespVO> getForeignWechatClientPage(WechatBindPageReqVO pageReqVO);

    /**
     * 查看当前微信绑定详情
     * @param id
     * @return
     */
    WechatBindDetailsDTO selectWechatBindDetailsByWechatId(@Param("id")Long id);

    /**
     * 更新绑定微信用户
     *
     * @param customerIds 更新信息
     * @param id 当前微信绑定id
     *
     */
    void updateWechatBind(Long id,List<Long> customerIds);


    /**
     * 删除绑定微信用户
     *
     * @param id 编号
     */
    void deleteWechatBind(Long id);

}
