package com.fmyd888.fengmao.module.information.service.ocrtemplate;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.ocrtemplate.OcrTemplateDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import com.fmyd888.fengmao.module.information.dal.mysql.ocrtemplate.OcrTemplateMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * fm_ocr_template Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class OcrTemplateServiceImpl implements OcrTemplateService {

    @Resource
    private OcrTemplateMapper ocrTemplateMapper;
    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private AdminUserMapper adminUserMapper;



    @Override
    public Long createOcrTemplate(OcrTemplateSaveReqVO createReqVO) {
        // 插入
        OcrTemplateDO ocrTemplate = BeanUtils.toBean(createReqVO, OcrTemplateDO.class);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        ocrTemplate.setDeptId(loginUserDeptId);

        AdminUserDO adminUserDO = adminUserMapper.selectById(loginUserDeptId);
        if(ObjUtil.isNotEmpty(adminUserDO)){
            ocrTemplate.setCreator(adminUserDO.getUpdater());
        }
        ocrTemplateMapper.insert(ocrTemplate);
        // 返回
        return ocrTemplate.getId();
    }

    @Override
    public void updateOcrTemplate(OcrTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateOcrTemplateExists(updateReqVO.getId());
        // 更新
        OcrTemplateDO updateObj = BeanUtils.toBean(updateReqVO, OcrTemplateDO.class);
        ocrTemplateMapper.updateById(updateObj);

        }

    @Override
    public void deleteOcrTemplate(Long id) {
        // 校验存在
        validateOcrTemplateExists(id);
        // 删除
        ocrTemplateMapper.deleteById(id);
    }

    private void validateOcrTemplateExists(Long id) {
        if (ocrTemplateMapper.selectById(id) == null) {
//        throw exception(OCR_TEMPLATE_NOT_EXISTS);
        }
    }


    @Override
    public OcrTemplateDO getOcrTemplate(Long id) {
        return ocrTemplateMapper.selectById(id);
    }

    @Override
    public List<OcrTemplateDO> getOcrTemplateList(Collection<Long> ids) {
        return ocrTemplateMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<OcrTemplateDO> getOcrTemplatePage(OcrTemplatePageReqVO pageReqVO) {
        PageResult<OcrTemplateDO> pageResult = ocrTemplateMapper.selectPage(pageReqVO);
        pageResult.getList().forEach(iterm ->{
            //转化创建更新信息为名称
            AdminUserDO createUserDO = adminUserMapper.selectById(iterm.getUpdater());
            if(ObjUtil.isNotEmpty(createUserDO)){
                iterm.setCreator(createUserDO.getNickname());
            }
            AdminUserDO updateUserDO = adminUserMapper.selectById(iterm.getUpdater());
            if(ObjUtil.isNotEmpty(updateUserDO)){
                iterm.setUpdater(updateUserDO.getUpdater());
//                iterm.setCreator(updateUserDO.getNickname());
            }
        });
        return pageResult;
    }

    @Override
    public List<OcrTemplateDO> getOcrTemplateList(OcrTemplateListReqVO listReqVO) {
        return ocrTemplateMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateOcrTemplate(List<OcrTemplateSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteOcrTemplate(List<Long> ids) {
        // 在这里处理批量删除逻辑
        ocrTemplateMapper.deleteBatchIds(ids);
    }

    @Override
    public List<OcrTemplateExcelVO> importPreviewList(List<OcrTemplateExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
//            throw exception(OCR_TEMPLATE_IMPORT_LIST_IS_EMPTY);
        }

        List<OcrTemplateExcelVO> excelVo = BeanUtils.toBean(importDatas, OcrTemplateExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
//        throw exception(OCR_TEMPLATE_IMPORT_PREVIEW_REQUIRE);
        return excelVo;
    }

    @Override
    public ImportResult importData(OcrTemplateExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
//            throw exception(OCR_TEMPLATE_IMPORT_LIST_IS_EMPTY)
                    ;
//        此处写入导入逻辑，最终返回导入结果
//        TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
//        throw exception(OCR_TEMPLATE_IMPORT_PORT_REQUIRE);
//        以下是示例，补充逻辑时请替换成自己书写的逻辑
        ImportResult result = ImportResult.builder()
        .total(importReqVo.getImportDatas().size())
        .importCount(0)
        .failCount(importReqVo.getImportDatas().size())//假设这里假设都导入失败
        .success(false)
//        .data(importReqVo.getImportDatas())
        .build();
        return result;
    }

}