package com.fmyd888.fengmao.module.information.service.encodingrules;

import java.util.*;
import javax.validation.*;

import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.encodingrules.EncodingRulesDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;

/**
 * 编码规则设置 Service 接口
 *
 * @author fengmao
 */
public interface EncodingRulesService {

    /**
     * 创建编码规则设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEncodingRules(@Valid EncodingRulesCreateReqVO createReqVO);

    /**
     * 更新编码规则设置
     *
     * @param updateReqVO 更新信息
     */
    void updateEncodingRules(@Valid EncodingRulesUpdateReqVO updateReqVO);

    /**
     * 删除编码规则设置
     *
     * @param id 编号
     */
    void deleteEncodingRules(Long id);

    /**
     * 获得编码规则设置
     *
     * @param id 编号
     * @return 编码规则设置
     */
    EncodingRulesDO getEncodingRules(Long id);

    /**
     * 获得编码规则设置列表
     *
     * @param ids 编号
     * @return 编码规则设置列表
     */
    List<EncodingRulesDO> getEncodingRulesList(Collection<Long> ids);

    /**
     * 获得编码规则设置分页
     *
     * @param pageReqVO 分页查询
     * @return 编码规则设置分页
     */
    PageResult<EncodingRulesDO> getEncodingRulesPage(EncodingRulesPageReqVO pageReqVO);

    /**
     * 获得编码规则设置列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 编码规则设置列表
     */
    List<EncodingRulesDO> getEncodingRulesList(EncodingRulesExportReqVO exportReqVO);

    /**
     * 通用根据业务规则类型对应的编码规则返回
     * @param ruleType  规则类型
     * @return 获取的编码规则结果
     */
    String getCodeByRuleType(String ruleType);

    /**
     * 功能描述：
     *
     * @param ruleType  规则类型
     * @param length    生成长度
     * @return {@link List }<{@link String }>
     * @author 小逺
     * @date 2024/08/02
     */
    List<String> getBatchCodeByRuleType(String ruleType,Integer length);

    /**
     * （车辆专用）根据业务规则类型对应的编码规则返回
     * @param ruleType  规则类型
     * @return 获取的编码规则结果
     */
    String getVehicleCodeByRuleType(String ruleType);

    /**
     * 是否可以手动填充覆盖，(默认自动生成)
     * @param ruleType
     * @return
     */
    Boolean isFillOverWrite(String ruleType);
    /**
     * 获得编码规则,通过编码名称
     *
     * @param ruleName  编码名称
     * @return  获得编码规则
     */
    EncodingRulesDO getByRuleName(String ruleName);


    /**
     * 预留用于新增编码规则时筛选未使用的业务对象
     * @param type  1:已使用，2：未使用，其他为全部查询
     * @return
     */
    List<Map<String,Object>> getCodingRuleList(Integer type);

    /**
     * 根据编码规则导出
     * @param exportReqVO
     * @return
     */
    List<EncodingRulesDO> getEncodingRulesExcel(EncodingRulesListReqVO exportReqVO);

    /**
     * 功能描述：获得编码规则下拉数据源信息
     *
     * @param param
     * @return {@link List }<{@link HashMap }<{@link String }, {@link Object }>>
     * @author 小逺
     * @date 2024/08/02
     */
    List<HashMap<String, Object>> getSimpleCodingRuleList(CommonQueryParam param);
}
