package com.fmyd888.fengmao.module.information.service.riskmaintenance;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto.RiskInspectionItemDetailsDTO;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto.RiskInspectionItemOuterDTO;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto.RiskMaintenanceDetailsDTO;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto.RiskMaintenanceOuterDTO;
import com.fmyd888.fengmao.module.information.dal.mysql.riskmaintenance.RiskMaintenanceCommodityMapper;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiFunction;
//import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskMaintenanceDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskInspectionItemDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskMaintenanceCommodityDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import com.fmyd888.fengmao.module.information.dal.mysql.riskmaintenance.RiskMaintenanceMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.riskinspectionitem.RiskInspectionItemMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 隐患排查项目维护表(主表) Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class RiskMaintenanceServiceImpl implements RiskMaintenanceService {

    @Resource
    private RiskMaintenanceMapper riskMaintenanceMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private RiskInspectionItemMapper riskInspectionItemMapper;
    @Resource
    private RiskMaintenanceCommodityMapper riskMaintenanceCommodityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRiskMaintenance(RiskMaintenanceSaveReqVO createReqVO) {

//        // 插入
        RiskMaintenanceDO riskMaintenance = BeanUtils.toBean(createReqVO, RiskMaintenanceDO.class);

        Long companyId = riskMaintenance.getCompanyId();
        Integer checkType = riskMaintenance.getCheckType();
        Integer type = riskMaintenance.getType();
        List<RiskMaintenanceOuterDTO> outerVO = riskMaintenance.getOuterVO();

        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        riskMaintenance.setDeptId(loginUserDeptId); // 设置部门ID，所有记录都使用这个值

        List<Long> commodityId = riskMaintenance.getCommodityId();
        Long maintenanceId = null; // 用于存储主表ID的变量
        //介质没有值说明是隐患排查
        if (commodityId == null) {
            for (RiskMaintenanceOuterDTO riskMaintenanceOuterDTO : outerVO) {
                String checkCategory = riskMaintenanceOuterDTO.getCheckCategory();
                RiskMaintenanceDO riskMaintenanceDOForInsert = new RiskMaintenanceDO();
                riskMaintenanceDOForInsert.setCheckCategory(checkCategory);
                riskMaintenanceDOForInsert.setCompanyId(companyId);
                riskMaintenanceDOForInsert.setCheckType(checkType);
                riskMaintenanceDOForInsert.setType(type);

                //新增主表数据
                riskMaintenanceMapper.insert(riskMaintenanceDOForInsert);
                // 获取当前新增的主表id
                Long id = riskMaintenanceDOForInsert.getId();
                if (maintenanceId == null) {
                    // 第一次循环时设置maintenanceId
                    maintenanceId = id;
                }

                // 获取riskMaintenanceOuterDTO（outerVO）中的子表数据
                List<RiskInspectionItemDO> riskInspectionItemDOS = riskMaintenanceOuterDTO.getRiskInspectionItemDOS();
                // 获取到数据后再循环遍历新增，以及和主表的关联关系id
                for (RiskInspectionItemDO riskInspectionItemDO : riskInspectionItemDOS) {
                    riskInspectionItemDO.setEntityId(id);
                    riskInspectionItemMapper.insert(riskInspectionItemDO);
                }
            }
        }
        //介质不为空则为趟检
        for (RiskMaintenanceOuterDTO riskMaintenanceOuterDTO : outerVO) {
            String checkCategory = riskMaintenanceOuterDTO.getCheckCategory();
            RiskMaintenanceDO riskMaintenanceDOForInsert = new RiskMaintenanceDO();
            riskMaintenanceDOForInsert.setCheckCategory(checkCategory);
            riskMaintenanceDOForInsert.setCompanyId(companyId);
            riskMaintenanceDOForInsert.setCheckType(checkType);
            riskMaintenanceDOForInsert.setType(type);

            // 新增主表数据
            riskMaintenanceMapper.insert(riskMaintenanceDOForInsert);
            // 获取当前新增的主表id
            Long id = riskMaintenanceDOForInsert.getId();
            if (maintenanceId == null) {
                // 第一次循环时设置maintenanceId
                maintenanceId = id;
            }

            // 这里将commodityId的循环移动到当前riskMaintenanceOuterDTO的循环内部
            for (Long commodityIdItem : commodityId) {
                RiskMaintenanceCommodityDO riskMaintenanceCommodityDO = new RiskMaintenanceCommodityDO();
                riskMaintenanceCommodityDO.setCommodityId(commodityIdItem);
                riskMaintenanceCommodityDO.setEntityId(id);
                riskMaintenanceCommodityMapper.insert(riskMaintenanceCommodityDO);
            }

            // 获取riskMaintenanceOuterDTO（outerVO）中的子表数据
            List<RiskInspectionItemDO> riskInspectionItemDOS = riskMaintenanceOuterDTO.getRiskInspectionItemDOS();
            // 获取到数据后再循环遍历新增，以及和主表的关联关系id
            for (RiskInspectionItemDO riskInspectionItemDO : riskInspectionItemDOS) {
                riskInspectionItemDO.setEntityId(id);
                riskInspectionItemMapper.insert(riskInspectionItemDO);
            }
        }

        // 返回主表ID：maintenanceId
        return (outerVO != null && !outerVO.isEmpty()) ? maintenanceId : null;


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRiskMaintenance(RiskMaintenanceSaveReqVO updateReqVO) {
        Integer type = updateReqVO.getType();
        Integer checkType = updateReqVO.getCheckType();
        Long companyId = updateReqVO.getCompanyId();

        // 1. 查询数据库中的旧数据ID
        List<Long> oldIds = riskMaintenanceMapper.selectRiskMaintenanceIds(companyId, checkType, type);

        // 2. 获取前端传入的新数据
        List<RiskMaintenanceOuterDTO> outerVO = updateReqVO.getOuterVO();
        Set<Long> newBigIdSet = outerVO.stream()
                .map(RiskMaintenanceOuterDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 删除的数据
        List<Long> toDelete = oldIds.stream()
                .filter(oldId -> !newBigIdSet.contains(oldId))
                .collect(Collectors.toList());

        // 新增的数据
        List<RiskMaintenanceOuterDTO> toInsert = outerVO.stream()
                .filter(outer -> outer.getId() == null)
                .collect(Collectors.toList());

        //更新的数据
        List<RiskMaintenanceOuterDTO> toUpdate = outerVO.stream()
                .filter(outer -> outer.getId() != null)
                .collect(Collectors.toList());

        // 3. 批量删除旧数据和关联的子表数据
        if (!toDelete.isEmpty()) {
            riskMaintenanceMapper.deleteBatchIds(toDelete);
            riskInspectionItemMapper.delete(new LambdaQueryWrapper<RiskInspectionItemDO>()
                    .in(RiskInspectionItemDO::getEntityId, toDelete));
        }

        // 存储新增数据的ID，用于后续插入关联表(介质)数据
        List<Long> newInsertedIds = new ArrayList<>();

        // 4. 批量新增数据
        if (!toInsert.isEmpty()) {
            for (RiskMaintenanceOuterDTO riskMaintenanceOuterDTO : toInsert) {
                RiskMaintenanceDO riskMaintenanceDO = new RiskMaintenanceDO();
                riskMaintenanceDO.setCompanyId(companyId);
                riskMaintenanceDO.setCheckType(checkType);
                riskMaintenanceDO.setType(type);
                riskMaintenanceDO.setCheckCategory(riskMaintenanceOuterDTO.getCheckCategory());
                BeanUtils.copyPropertiesIgnoreNull(riskMaintenanceDO, riskMaintenanceOuterDTO);
                riskMaintenanceMapper.insert(riskMaintenanceDO);

                if (riskMaintenanceOuterDTO.getRiskInspectionItemDOS() != null){
                    for (RiskInspectionItemDO riskInspectionItemDO : riskMaintenanceOuterDTO.getRiskInspectionItemDOS()) {
                        riskInspectionItemDO.setEntityId(riskMaintenanceDO.getId());
                        riskInspectionItemDO.setItemScore(riskInspectionItemDO.getItemScore());
                        riskInspectionItemDO.setIsUploadPictures(riskInspectionItemDO.getIsUploadPictures());
                        riskInspectionItemMapper.insert(riskInspectionItemDO);
                    }

                }
                newInsertedIds.add(riskMaintenanceDO.getId());
            }
        }

        // 5. 批量更新数据
        if (!toUpdate.isEmpty()) {
            for (RiskMaintenanceOuterDTO outer : toUpdate) {
                RiskMaintenanceDO riskMaintenanceDO = new RiskMaintenanceDO();
                riskMaintenanceDO.setCompanyId(companyId);
                riskMaintenanceDO.setCheckType(checkType);
                riskMaintenanceDO.setType(type);
                riskMaintenanceDO.setCheckCategory(outer.getCheckCategory());
                BeanUtils.copyPropertiesIgnoreNull(riskMaintenanceDO, outer);
                riskMaintenanceMapper.updateById(riskMaintenanceDO);
                List<RiskInspectionItemDO> riskInspectionItemDOS = outer.getRiskInspectionItemDOS();
                if (riskInspectionItemDOS != null){

                    riskInspectionItemMapper.delete(new LambdaQueryWrapper<RiskInspectionItemDO>()
                            .eq(RiskInspectionItemDO::getEntityId, outer.getId()));

                    for (RiskInspectionItemDO riskInspectionItemDO : riskInspectionItemDOS) {
                        RiskInspectionItemDO riskInspectionItemDO1 = new RiskInspectionItemDO();
                        org.springframework.beans.BeanUtils.copyProperties(riskInspectionItemDO,riskInspectionItemDO1,"id");
                        riskInspectionItemDO1.setEntityId(outer.getId());
                        riskInspectionItemMapper.insert(riskInspectionItemDO1);
                    }}
            }
        }

        // 6. 处理type == 2(趟检列检)
        if (type == 2) {
            // 删除旧的介质关联数据
            LambdaUpdateWrapper<RiskMaintenanceCommodityDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(RiskMaintenanceCommodityDO::getEntityId, oldIds)
                    .eq(RiskMaintenanceCommodityDO::getDeleted, 0);
            riskMaintenanceCommodityMapper.delete(updateWrapper);

            // 插入新的介质数据
            List<Long> commodityIds = updateReqVO.getCommodityId();
            if (commodityIds != null && !commodityIds.isEmpty()) {
                List<Long> allRelevantIds = new ArrayList<>(newBigIdSet);
                allRelevantIds.addAll(newInsertedIds);
                for (Long entityId : allRelevantIds) {
                    riskMaintenanceCommodityMapper.batchInsertRiskCommodity(commodityIds, entityId);
                }
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRiskMaintenance(Long id) {
        // 校验存在
        validateRiskMaintenanceExists(id);
        // 删除
        riskMaintenanceMapper.deleteById(id);
    }

    private void validateRiskMaintenanceExists(Long id) {
        if (riskMaintenanceMapper.selectById(id) == null) {
        throw exception(RISK_MAINTENANCE_NOT_EXISTS);
        }
    }


    @Override
    public RiskMaintenanceDO getRiskMaintenance(Long id) {
        return riskMaintenanceMapper.selectById(id);
    }


    @Override
    public RiskInspectionItemOuterDTO selectRiskInspectionItemById(Long companyId, Integer checkType, Integer type) {
        if (type == 1 && checkType != null && companyId != null) { //隐患排查
            RiskInspectionItemOuterDTO riskInspectionItemOuterDTO = riskMaintenanceMapper.selectRiskInspectionItemById(companyId, checkType, type);
            if (riskInspectionItemOuterDTO == null){
                return null;
            }
            // 根据查询到的对象id查询id、类别名称、type
                Long companyId1 = riskInspectionItemOuterDTO.getCompanyId();
                Integer checkType1 = riskInspectionItemOuterDTO.getCheckType();
                Integer type1 = riskInspectionItemOuterDTO.getType();
                List<RiskMaintenanceOuterDTO> riskMaintenanceOuterDTOS = riskMaintenanceMapper.selectRiskMaintenanceDetailsById(companyId1, checkType1, type1);
            riskInspectionItemOuterDTO.setOuterVO(riskMaintenanceOuterDTOS);

                // 根据上面对象查询到的id，获取子表数据。
                for (RiskMaintenanceOuterDTO riskMaintenanceOuterVO : riskMaintenanceOuterDTOS) {
                    Long id = riskMaintenanceOuterVO.getId();
                    List<RiskInspectionItemDO> riskInspectionItemDetails = riskMaintenanceMapper.selectRiskInspectionItemDetailsById(id);
                    int itemScoreAll = 0;

                    // 类别总分数
                    for (RiskInspectionItemDO item : riskInspectionItemDetails) {
                        itemScoreAll +=  item.getItemScore();
                    }
                    riskMaintenanceOuterVO.setItemScoreAll(itemScoreAll);
                    riskMaintenanceOuterVO.setRiskInspectionItemDOS(riskInspectionItemDetails);
                }

            return  riskInspectionItemOuterDTO;

        } else if(type == 2 && checkType != null && companyId != null){ //趟检
            RiskInspectionItemOuterDTO riskInspectionItemOuterDTO = riskMaintenanceMapper.selectRiskInspectionItemById(companyId, checkType, type);
            if (riskInspectionItemOuterDTO == null){
                return null;
            }
            // 根据查询到的对象id查询id、类别名称、type
            // 根据查询到的对象id查询id、类别名称、type
            Long companyId1 = riskInspectionItemOuterDTO.getCompanyId();
            Integer checkType1 = riskInspectionItemOuterDTO.getCheckType();
            Integer type1 = riskInspectionItemOuterDTO.getType();
                List<RiskMaintenanceOuterDTO> riskMaintenanceOuterDTOS = riskMaintenanceMapper.selectRiskMaintenanceDetailsById(companyId1, checkType1, type1);
                //查询介质id
            Long id1 = riskInspectionItemOuterDTO.getId();
            List<Long> longs = riskMaintenanceMapper.selectCommodityIdsById(id1);
            riskInspectionItemOuterDTO.setCommodityId(longs);
            riskInspectionItemOuterDTO.setOuterVO(riskMaintenanceOuterDTOS);
                // 根据上面对象查询到的id，获取子表数据。
                for (RiskMaintenanceOuterDTO riskMaintenanceOuterVO : riskMaintenanceOuterDTOS) {
                    Long id = riskMaintenanceOuterVO.getId();
                    List<RiskInspectionItemDO> riskInspectionItemDetails = riskMaintenanceMapper.selectRiskInspectionItemDetailsById2(id);
                    riskMaintenanceOuterVO.setRiskInspectionItemDOS(riskInspectionItemDetails);
                }

            return riskInspectionItemOuterDTO;
        }
        return null;
    }


    private List<RiskInspectionItemDO> fetchRiskInspectionItemDOS(Long entityId) {
        LambdaQueryWrapper<RiskInspectionItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RiskInspectionItemDO::getEntityId, entityId)
                .eq(RiskInspectionItemDO::getDeleted, 0)
                .select(RiskInspectionItemDO::getProjectName, RiskInspectionItemDO::getItemScore);
        return riskInspectionItemMapper.selectList(queryWrapper);
    }

    private List<RiskInspectionItemDO> fetchRiskInspectionItemByCommodityDOS(Long entityId) {
        LambdaQueryWrapper<RiskInspectionItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RiskInspectionItemDO::getEntityId, entityId)
                    .eq(RiskInspectionItemDO::getDeleted, 0)
                .select(RiskInspectionItemDO::getProjectName, RiskInspectionItemDO::getIsUploadPictures,RiskInspectionItemDO::getId);
        return riskInspectionItemMapper.selectList(queryWrapper);
    }

    @Override
    public List<RiskMaintenanceDO> getRiskMaintenanceList(Collection<Long> ids) {
        return riskMaintenanceMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<RiskMaintenanceDO> getRiskMaintenancePage(RiskMaintenancePageReqVO pageReqVO) {
        return riskMaintenanceMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RiskMaintenanceDO> getRiskMaintenanceList(RiskMaintenanceListReqVO listReqVO) {
        return riskMaintenanceMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateRiskMaintenance(List<RiskMaintenanceSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteRiskMaintenance(List<Long> ids) {
        // 在这里处理批量删除逻辑
        riskMaintenanceMapper.deleteBatchIds(ids);
    }

    @Override
    public List<RiskMaintenanceExcelVO> importPreviewList(List<RiskMaintenanceExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(RISK_MAINTENANCE_IMPORT_LIST_IS_EMPTY);
        }

        List<RiskMaintenanceExcelVO> excelVo = BeanUtils.toBean(importDatas, RiskMaintenanceExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(RISK_MAINTENANCE_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(RiskMaintenanceExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
            throw exception(RISK_MAINTENANCE_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(RISK_MAINTENANCE_IMPORT_PORT_REQUIRE);
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

    @Override
    public RiskInspectionItemOuterDTO getRiskMaintenanceSimpleList(Integer checkType,Long companyId) {

        RiskInspectionItemOuterDTO resultDTO = new RiskInspectionItemOuterDTO();

        LambdaQueryWrapper<RiskMaintenanceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RiskMaintenanceDO::getDeleted, 0)
                .eq(RiskMaintenanceDO::getType, 1) // 隐患排查
                .eq(RiskMaintenanceDO::getCompanyId, companyId)
                .in(RiskMaintenanceDO::getCheckType, checkType)
                .select(RiskMaintenanceDO::getId, RiskMaintenanceDO::getCheckCategory, RiskMaintenanceDO::getCheckType, RiskMaintenanceDO::getType);

        List<RiskMaintenanceDO> riskMaintenanceDOS = riskMaintenanceMapper.selectList(queryWrapper);

        if (CollUtil.isNotEmpty(riskMaintenanceDOS)) {
            List<Long> riskIds = riskMaintenanceDOS.stream()
                    .map(RiskMaintenanceDO::getId)
                    .collect(Collectors.toList());

            LambdaQueryWrapper<RiskInspectionItemDO> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(RiskInspectionItemDO::getDeleted, 0)
                    .in(RiskInspectionItemDO::getEntityId, riskIds)
                    .select(RiskInspectionItemDO::getEntityId,RiskInspectionItemDO::getId, RiskInspectionItemDO::getItemScore,RiskInspectionItemDO::getProjectName);

            List<RiskInspectionItemDO> riskInspectionItemDOS = riskInspectionItemMapper.selectList(queryWrapper1);

            List<RiskMaintenanceOuterDTO> outerDTOList = riskMaintenanceDOS.stream()
                    .map(riskMaintenanceDO -> {
                        // 获取当前主表对应的所有子表
                        List<RiskInspectionItemDO> relatedItems = riskInspectionItemDOS.stream()
                                .filter(item -> item.getEntityId().equals(riskMaintenanceDO.getId()))
                                .collect(Collectors.toList());


                        Integer itemScoreAll = relatedItems.stream()
                                .map(RiskInspectionItemDO::getItemScore)
                                .filter(Objects::nonNull)
                                .reduce(0, Integer::sum);

                        RiskMaintenanceOuterDTO maintenanceOuterDTO = new RiskMaintenanceOuterDTO();
                        maintenanceOuterDTO.setId(riskMaintenanceDO.getId());
                        maintenanceOuterDTO.setCheckCategory(riskMaintenanceDO.getCheckCategory());
                        maintenanceOuterDTO.setCheckType(riskMaintenanceDO.getCheckType());
                        maintenanceOuterDTO.setType(riskMaintenanceDO.getType());
                        maintenanceOuterDTO.setRiskInspectionItemDOS(relatedItems);
                        maintenanceOuterDTO.setItemScoreAll(itemScoreAll);

                        return maintenanceOuterDTO;
                    }).collect(Collectors.toList());

            resultDTO.setOuterVO(outerDTOList);
        }

        resultDTO.setOuterVO(CollUtil.isEmpty(resultDTO.getOuterVO()) ? Collections.emptyList() : resultDTO.getOuterVO());

        return resultDTO;
    }




// ==================== 子表（检查类型表(子表)） ====================

    @Override
    public PageResult<RiskInspectionItemDO> getRiskInspectionItemPage(PageParam pageReqVO, Long entityId) {
        return riskInspectionItemMapper.selectPage(pageReqVO, entityId);
    }

    @Override
    public List<RiskInspectionItemDO> getRiskInspectionItemListByEntityId(Long entityId) {
        return riskInspectionItemMapper.selectListByEntityId(entityId);
    }

//    @Override
//    public Long createRiskInspectionItem(RiskInspectionItemDO riskInspectionItem) {
//            //获取部门id
//        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
//        riskInspectionItem.setDeptId(loginUserDeptId);
//        riskInspectionItemMapper.insert(riskInspectionItem);
//        return riskInspectionItem.getId();
//    }
//
//    @Override
//    public void updateRiskInspectionItem(RiskInspectionItemDO riskInspectionItem) {
//        // 校验存在
//        validateRiskInspectionItemExists(riskInspectionItem.getId());
//        // 更新
//        riskInspectionItemMapper.updateById(riskInspectionItem);
//    }
//
//    @Override
//    public void deleteRiskInspectionItem(Long id) {
//        // 校验存在
//        validateRiskInspectionItemExists(id);
//        // 删除
//        riskInspectionItemMapper.deleteById(id);
//    }
//
//    @Override
//    public RiskInspectionItemDO getRiskInspectionItem(Long id) {
//        return riskInspectionItemMapper.selectById(id);
//    }
//
//    private void validateRiskInspectionItemExists(Long id) {
//        if (riskInspectionItemMapper.selectById(id) == null) {
//        throw exception(RISK_INSPECTION_ITEM_NOT_EXISTS);
//    }
//    }

    private void createRiskInspectionItemList(Long entityId, List<RiskInspectionItemDO> list) {
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        list.forEach(o -> {
            o.setEntityId(entityId);
            o.setDeptId(loginUserDeptId);
        });
        riskInspectionItemMapper.insertBatch(list);
    }

    private void updateRiskInspectionItemList(Long entityId, List<RiskInspectionItemDO> list) {
        if (list.size() <= 0)
            return;
        //获取所有子数据
        List<RiskInspectionItemDO> riskInspectionItemDOS = riskInspectionItemMapper.selectListByEntityId(entityId);
        //如果没有直接插入
        if (riskInspectionItemDOS.size() <= 0)
            createRiskInspectionItemList(entityId, list);
        else {
            //找出新增、修改、删除的数据
            BiFunction<RiskInspectionItemDO, RiskInspectionItemDO, Boolean> sameFunc = (oldObj, newObj) -> {
                boolean same = oldObj.getId().equals(newObj.getId());
                return same;
            };

            // 调用
            List<List<RiskInspectionItemDO>> result = CollectionUtils.diffList(riskInspectionItemDOS, list, sameFunc);

            //新增
            if (result.get(0).size() >= 0)
                createRiskInspectionItemList(entityId, result.get(0));

            //修改
            if (result.get(1).size() >= 0)
                riskInspectionItemMapper.updateBatch(result.get(1));

            //删除
            if (result.get(2).size() >= 0)
                riskInspectionItemMapper.deleteBatchIds(CollectionUtils.convertList(result.get(2), p -> p.getId()));
        }
    }

// ==================== 子表（隐患排查项目维护与介质关联） ====================

    @Override
    public PageResult<RiskMaintenanceCommodityDO> getRiskMaintenanceCommodityPage(PageParam pageReqVO, Long entityId) {
        return riskMaintenanceCommodityMapper.selectPage(pageReqVO, entityId);
    }

    @Override
    public List<RiskMaintenanceCommodityDO> getRiskMaintenanceCommodityListByEntityId(Long entityId) {
        return riskMaintenanceCommodityMapper.selectListByEntityId(entityId);
    }

//    @Override
//    public Long createRiskMaintenanceCommodity(RiskMaintenanceCommodityDO riskMaintenanceCommodity) {
//            //获取部门id
//        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
//        riskMaintenanceCommodity.setDeptId(loginUserDeptId);
//        riskMaintenanceCommodityMapper.insert(riskMaintenanceCommodity);
//        return riskMaintenanceCommodity.getId();
//    }
//
//    @Override
//    public void updateRiskMaintenanceCommodity(RiskMaintenanceCommodityDO riskMaintenanceCommodity) {
//        // 校验存在
//        validateRiskMaintenanceCommodityExists(riskMaintenanceCommodity.getId());
//        // 更新
//        riskMaintenanceCommodityMapper.updateById(riskMaintenanceCommodity);
//    }
//
//    @Override
//    public void deleteRiskMaintenanceCommodity(Long id) {
//        // 校验存在
//        validateRiskMaintenanceCommodityExists(id);
//        // 删除
//        riskMaintenanceCommodityMapper.deleteById(id);
//    }
//
//    @Override
//    public RiskMaintenanceCommodityDO getRiskMaintenanceCommodity(Long id) {
//        return riskMaintenanceCommodityMapper.selectById(id);
//    }

    private void validateRiskMaintenanceCommodityExists(Long id) {
        if (riskMaintenanceCommodityMapper.selectById(id) == null) {
        throw exception(RISK_MAINTENANCE_COMMODITY_NOT_EXISTS);
    }
    }

    private void createRiskMaintenanceCommodityList(Long entityId, List<RiskMaintenanceCommodityDO> list) {
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        list.forEach(o -> {
            o.setEntityId(entityId);
            o.setDeptId(loginUserDeptId);
        });
        riskMaintenanceCommodityMapper.insertBatch(list);
    }

    private void updateRiskMaintenanceCommodityList(Long entityId, List<RiskMaintenanceCommodityDO> list) {
        if (list.size() <= 0)
            return;
        //获取所有子数据
        List<RiskMaintenanceCommodityDO> riskMaintenanceCommodityDOS = riskMaintenanceCommodityMapper.selectListByEntityId(entityId);
        //如果没有直接插入
        if (riskMaintenanceCommodityDOS.size() <= 0)
            createRiskMaintenanceCommodityList(entityId, list);
        else {
            //找出新增、修改、删除的数据
            BiFunction<RiskMaintenanceCommodityDO, RiskMaintenanceCommodityDO, Boolean> sameFunc = (oldObj, newObj) -> {
                boolean same = oldObj.getId().equals(newObj.getId());
                return same;
            };

            // 调用
            List<List<RiskMaintenanceCommodityDO>> result = CollectionUtils.diffList(riskMaintenanceCommodityDOS, list, sameFunc);

            //新增
            if (result.get(0).size() >= 0)
                createRiskMaintenanceCommodityList(entityId, result.get(0));

            //修改
            if (result.get(1).size() >= 0)
                riskMaintenanceCommodityMapper.updateBatch(result.get(1));

            //删除
            if (result.get(2).size() >= 0)
                riskMaintenanceCommodityMapper.deleteBatchIds(CollectionUtils.convertList(result.get(2), p -> p.getId()));
        }
    }

}