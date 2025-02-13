package com.fmyd888.fengmao.module.information.service.commoditysafetycard;

import cn.hutool.core.collection.CollUtil;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.infra.api.file.DTO.FileDTO;
import com.fmyd888.fengmao.module.infra.api.file.FileApi;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.fmyd888.fengmao.module.information.controller.admin.commoditysafetycard.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.commoditysafetycard.CommoditySafetyCardDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import com.fmyd888.fengmao.module.information.dal.mysql.commoditysafetycard.CommoditySafetyCardMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 安全告知卡 Service 实现类
 *
 * @author 巫晨旭
 */
@Service
@Validated
public class CommoditySafetyCardServiceImpl implements CommoditySafetyCardService {

    @Resource
    private CommoditySafetyCardMapper commoditySafetyCardMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private FileApi fileApi;

    @Override
    public Long createCommoditySafetyCard(CommoditySafetyCardSaveReqVO createReqVO) {


        // 插入
        CommoditySafetyCardDO commoditySafetyCard = BeanUtils.toBean(createReqVO, CommoditySafetyCardDO.class);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        commoditySafetyCard.setDeptId(loginUserDeptId);
        commoditySafetyCardMapper.insert(commoditySafetyCard);

        if (createReqVO.getFileId() != null){
            Long id = commoditySafetyCard.getId();
            fileApi.bindAttachment(createReqVO.getFileId(),id);
        }
    
        // 返回
        return commoditySafetyCard.getId();
    }

    @Override
    public void updateCommoditySafetyCard(CommoditySafetyCardSaveReqVO updateReqVO) {
        // 校验存在
        validateCommoditySafetyCardExists(updateReqVO.getId());

        //更新文件绑定
        Long id = updateReqVO.getId();
        String codeBusinessType = FileEnums.COMMODITY_SAFETY_CARD.getCodeBusinessType();
        List<FileDTO> fileDTOS = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(codeBusinessType, id);
        if (!fileDTOS.isEmpty()){
            FileDTO fileDTO = fileDTOS.get(0);
                //删除绑定
                fileApi.unBindAttachment(fileDTO.getId());
                //重新绑定
                fileApi.bindAttachment(updateReqVO.getFileId(),id);
        }

        // 更新
        CommoditySafetyCardDO updateObj = BeanUtils.toBean(updateReqVO, CommoditySafetyCardDO.class);
        commoditySafetyCardMapper.updateById(updateObj);

        }

    @Override
    public void deleteCommoditySafetyCard(Long id) {
        // 校验存在
        validateCommoditySafetyCardExists(id);
        // 删除
        commoditySafetyCardMapper.deleteById(id);
    }

    private void validateCommoditySafetyCardExists(Long id) {
        if (commoditySafetyCardMapper.selectById(id) == null) {
        throw exception(COMMODITY_SAFETY_CARD_NOT_EXISTS);
        }
    }


    @Override
    public CommoditySafetyCardDO getCommoditySafetyCard(Long id) {
        CommoditySafetyCardDO commoditySafetyCardDO = commoditySafetyCardMapper.selectById(id);
        if (commoditySafetyCardDO != null){
            List<FileDTO> fileDTOS = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(FileEnums.COMMODITY_SAFETY_CARD.getCodeBusinessType(), id);
            HashMap<String, Object> map = new HashMap<>();
            if (!fileDTOS.isEmpty()){
                FileDTO fileDTO = fileDTOS.get(0);
                map.put("id",fileDTO.getId());
                map.put("name",fileDTO.getName());
                map.put("url",fileDTO.getUrl());
                commoditySafetyCardDO.setFileMaps(map);
            }

        }
        return commoditySafetyCardDO;
    }

    @Override
    public List<CommoditySafetyCardDO> getCommoditySafetyCardList(Collection<Long> ids) {
        return commoditySafetyCardMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CommoditySafetyCardDO> getCommoditySafetyCardPage(CommoditySafetyCardPageReqVO pageReqVO) {
        PageResult<CommoditySafetyCardDO> page = commoditySafetyCardMapper.selectJoinTilePage(pageReqVO);
        List<CommoditySafetyCardDO> list = page.getList();

        // 提取所有的ID
        List<Long> ids = list.stream()
                .map(CommoditySafetyCardDO::getId)
                .collect(Collectors.toList());

        // 批量获取文件信息
        List<FileDTO> fileDTOS = fileApi.getFileUrlByCodeBusinessTypeAndSourceIds(FileEnums.COMMODITY_SAFETY_CARD.getCodeBusinessType(), ids);

        // 将 List<FileDTO> 按 sourceId 分组
        Map<Long, List<FileDTO>> filesMap = fileDTOS.stream()
                .collect(Collectors.groupingBy(FileDTO::getSourceId));

        // 遍历列表，设置文件信息
        for (CommoditySafetyCardDO commoditySafetyCardDO : list) {
            List<FileDTO> filesForCommodity = filesMap.get(commoditySafetyCardDO.getId());
            if (filesForCommodity != null && !filesForCommodity.isEmpty()) {
                FileDTO fileDTO = filesForCommodity.get(0); // 获取第一个文件信息
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", fileDTO.getId());
                map.put("name", fileDTO.getName());
                map.put("url", fileDTO.getUrl());
                commoditySafetyCardDO.setFileMaps(map);
            }
        }

        return page;
    }

    @Override
    public List<CommoditySafetyCardDO> getCommoditySafetyCardList(CommoditySafetyCardListReqVO listReqVO) {
        return commoditySafetyCardMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateCommoditySafetyCard(List<CommoditySafetyCardSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteCommoditySafetyCard(List<Long> ids) {
        // 在这里处理批量删除逻辑
        commoditySafetyCardMapper.deleteBatchIds(ids);
    }

    @Override
    public List<CommoditySafetyCardExcelVO> importPreviewList(List<CommoditySafetyCardExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(COMMODITY_SAFETY_CARD_IMPORT_LIST_IS_EMPTY);
        }

        List<CommoditySafetyCardExcelVO> excelVo = BeanUtils.toBean(importDatas, CommoditySafetyCardExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(COMMODITY_SAFETY_CARD_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(CommoditySafetyCardExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
            throw exception(COMMODITY_SAFETY_CARD_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(COMMODITY_SAFETY_CARD_IMPORT_PORT_REQUIRE);
        //以下是示例，补充逻辑时请替换成自己书写的逻辑
        //ImportResult result = ImportResult.builder()
        //.total(importReqVo.getImportDatas().size())
        //.importCount(0)
        //.failCount(importReqVo.getImportDatas().size())//假设这里假设都导入失败
        //.success(false)
        //.data(importReqVo.getImportDatas())
        //.build();
        //return result;
    }

}