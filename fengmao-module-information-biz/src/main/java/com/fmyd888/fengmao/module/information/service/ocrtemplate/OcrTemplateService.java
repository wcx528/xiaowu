package com.fmyd888.fengmao.module.information.service.ocrtemplate;

import java.util.*;
import javax.validation.*;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.ocrtemplate.OcrTemplateDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;

/**
 * fm_ocr_template Service 接口
 *
 * @author 丰茂
 */
public interface OcrTemplateService {

    /**
     * 创建fm_ocr_template
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOcrTemplate(@Valid OcrTemplateSaveReqVO createReqVO);

    /**
     * 更新fm_ocr_template
     *
     * @param updateReqVO 更新信息
     */
    void updateOcrTemplate(@Valid OcrTemplateSaveReqVO updateReqVO);

    /**
     * 删除fm_ocr_template
     *
     * @param id 编号
     */
    void deleteOcrTemplate(Long id);

    /**
     * 获得fm_ocr_template
     *
     * @param id 编号
     * @return fm_ocr_template
     */
    OcrTemplateDO getOcrTemplate(Long id);

    /**
     * 获得fm_ocr_template列表
     *
     * @param ids 编号
     * @return fm_ocr_template列表
     */
    List<OcrTemplateDO> getOcrTemplateList(Collection<Long> ids);

    /**
    * 获得fm_ocr_template分页
    *
    * @param pageReqVO 分页查询
    * @return fm_ocr_template分页
    */
    PageResult<OcrTemplateDO> getOcrTemplatePage(OcrTemplatePageReqVO pageReqVO);


    /**
     * 获得fm_ocr_template列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return fm_ocr_template列表
     */
    List<OcrTemplateDO> getOcrTemplateList(OcrTemplateListReqVO exportReqVO);

    /**
    * 批量更新fm_ocr_template列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateOcrTemplate(List<OcrTemplateSaveReqVO> updateReqVOList);

    /**
    * 批量删除fm_ocr_template列表
    *
    * @param ids 编号列表
    */
    void batchDeleteOcrTemplate(List<Long> ids);

    /**
    * 导入fm_ocr_template返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<OcrTemplateExcelVO> importPreviewList(List<OcrTemplateExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入fm_ocr_template列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(OcrTemplateExcelVO importReqVo);
}
