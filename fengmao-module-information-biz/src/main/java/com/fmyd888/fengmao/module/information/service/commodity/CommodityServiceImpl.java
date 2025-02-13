package com.fmyd888.fengmao.module.information.service.commodity;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.dal.dataobject.commoditysafetycard.CommoditySafetyCardDO;
import com.fmyd888.fengmao.module.information.dal.mysql.commodity.CommodityDeptMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.commoditysafetycard.CommoditySafetyCardMapper;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.*;
import com.fmyd888.fengmao.module.information.convert.commodity.CommodityConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import com.fmyd888.fengmao.module.information.dal.mysql.commodity.CommodityMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.measurement.MeasurementMapper;
import com.fmyd888.fengmao.module.information.enums.commodity.CommodityEnum;
import com.fmyd888.fengmao.module.information.service.commoditysafetycard.CommoditySafetyCardService;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.infra.api.file.DTO.FileDTO;
import com.fmyd888.fengmao.module.infra.api.file.FileApi;
import com.fmyd888.fengmao.module.infra.dal.mysql.file.FileMapper;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import com.fmyd888.fengmao.module.system.convert.dept.DeptConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import io.lettuce.core.protocol.CommandType;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 货物 Service 实现类
 *
 * @author 丰茂企业
 */
@Service
@Validated
public class CommodityServiceImpl implements CommodityService {

    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private CommodityDeptMapper commodityDeptMapper;
    @Resource
    private MeasurementMapper measurementMapper;
    @Resource
    private DeptService deptService;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private CommodityDeptService commodityDeptService;
    @Resource
    private EncodingRulesService encodingRulesService;
    @Resource
    private CommoditySafetyCardMapper commoditySafetyCardMapper;
    @Resource
    private FileApi fileApi;
    @Resource
    private CommoditySafetyCardService commoditySafetyCardService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCommodity(CommodityCreateReqVO createReqVO) {

        //校验
        validateRepetition(null, createReqVO.getName(), createReqVO.getSpecification());

//        //处理安全告知卡
//        if (createReqVO.getCardSaveReqVO() != null){
//            commoditySafetyCardService.createCommoditySafetyCard(createReqVO.getCardSaveReqVO());
//        }

        String name = createReqVO.getName();
        validateCommondityExists(null, name);
        Integer materialType = createReqVO.getCategory();//货物类别
        Integer normalMaterialCode = CommodityEnum.NORMAL_MATERIAL.getCode();
        Integer hazardousMaterialCode = CommodityEnum.HAZARDOUS_MATERIAL.getCode();
        String commodityCode = null;
        if (materialType.equals(normalMaterialCode)) {
            commodityCode = EncodingRulesEnum.NORMAL_MATERIAL_CODE.getBusinessCode();
        } else if (materialType.equals(hazardousMaterialCode)) {
            commodityCode = EncodingRulesEnum.HAZARDOUS_MATERIAL_CODE.getBusinessCode();
        } else {
            commodityCode = null;
        }
        if (createReqVO.getCode() == null) {
            String code = encodingRulesService.getCodeByRuleType(commodityCode);
            createReqVO.setCode(code);
        }

        // 插入
        CommodityDO commodity = CommodityConvert.INSTANCE.convert(createReqVO);
        commodityMapper.insert(commodity);

        Long id = commodity.getId();
        Set<Long> depts = createReqVO.getDeptIds();
        commodityDeptService.bindDeptsToEntity(id, depts);
        return id;
    }

    private void validateRepetition(Long id, String name, String specification) {
        LambdaQueryWrapper<CommodityDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommodityDO::getName, name)
                .eq(CommodityDO::getSpecification, specification)
                .eq(CommodityDO::getDeleted, 0);
        if (id != null) {
            queryWrapper.ne(CommodityDO::getId, id);
        }
        if (CollectionUtils.isNotEmpty(commodityMapper.selectList(queryWrapper))) {
            throw exception(DUPLICATE_GOODS_NAME_SPECIFICATION);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCommodity(CommodityUpdateReqVO updateReqVO) {

        //校验重复数据
        validateRepetition(updateReqVO.getId(), updateReqVO.getName(), updateReqVO.getSpecification());

        // 校验存在
        Long id = updateReqVO.getId();
        String goodsName = updateReqVO.getName();
        validateCommondityExists(id, goodsName);
        // 更新
        CommodityDO updateObj = CommodityConvert.INSTANCE.convert(updateReqVO);
        LambdaUpdateWrapper<CommodityDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommodityDO::getId, id);
        if (updateReqVO.getNotifyCar() == null) {
            updateWrapper.set(CommodityDO::getNotifyCar, null);
        }
        commodityMapper.update(updateObj, updateWrapper);

        Set<Long> deptIds = updateReqVO.getDeptIds();
        commodityDeptService.updateBindDeptsToEntity(id, deptIds);
    }

    @Override
    public void deleteCommodity(Long id) {
        // 删除
        commodityMapper.deleteById(id);
    }

    /**
     * 前端校验 + 后端最好也检验一下
     * 货物类别为【危化货品】或【普通货品】时才并必填，不是的时候不填【货物规格】和【运输对应】
     *
     * @param createReqVO
     */
    private Boolean validateMaterialTypeExists(CommodityCreateReqVO createReqVO) {
        Integer materialType = createReqVO.getCategory();//货物类别
        String specification = createReqVO.getSpecification();
        Long parentId = createReqVO.getParentId();
        Integer normalMaterialCode = CommodityEnum.NORMAL_MATERIAL.getCode();
        Integer hazardousMaterialCode = CommodityEnum.HAZARDOUS_MATERIAL.getCode();
        if (materialType.equals(normalMaterialCode) || materialType.equals(hazardousMaterialCode) &&
                StringUtils.isNotEmpty(specification) && parentId != null) {
            return true;
        } else if (!materialType.equals(normalMaterialCode) && !materialType.equals(hazardousMaterialCode) &&
                StringUtils.isNotEmpty(specification) || parentId != null) {
            throw exception(COMMODITY_TYPE_NOT_EXISTS);
        }
        return false;
    }


    /**
     * 用于创建或者更新时候校验货物名称是否重复
     *
     * @param id
     * @param name
     */
    private void validateCommondityExists(Long id, String name) {
        LambdaQueryWrapper<CommodityDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommodityDO::getName, name)
                .ne(id != null, CommodityDO::getId, id);
        boolean exists = commodityMapper.exists(queryWrapper);
        if (exists) {
            throw exception(DUPLICATE_GOODS_NAME, name);
        }
    }

    @Override
    public CommodityRespVO getCommodity(Long id) {
        CommodityDO commodityDO = commodityMapper.selectById(id);
        CommodityRespVO commodityVO = CommodityConvert.INSTANCE.convert(commodityDO);
        Long measurementId = commodityVO.getMeasurementId();
        MeasurementDO measurementDO = measurementMapper.selectById(measurementId);
        if (ObjUtil.isNotEmpty(measurementDO)) {
            commodityVO.setMeasurementName(measurementDO.getName());

        }
        Long parentId = commodityDO.getParentId();
        if (parentId != null && parentId != 0) {
            CommodityDO parentcommodityDO = commodityMapper.selectById(parentId);
            if (ObjUtil.isNotEmpty(parentcommodityDO)) {
                commodityVO.setParentCommodityName(parentcommodityDO.getName());
            }
        }

        if (commodityDO.getNotifyCar() != null) {
            String codeBusinessType = FileEnums.COMMODITY_SAFETY_CARD.getCodeBusinessType();
            List<FileDTO> fileDTOS = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(codeBusinessType, commodityDO.getNotifyCar());
            HashMap<String, Object> map = new HashMap<>();
            if (CollectionUtils.isNotEmpty(fileDTOS)) {
                map.put("id", fileDTOS.get(0).getId());
                map.put("name", fileDTOS.get(0).getName());
                map.put("url", fileDTOS.get(0).getUrl());
            }
            commodityVO.setFileMaps(map);
        }
        // 获取部门信息
        List<CommodityDeptDO> deptInfos = commodityDeptService.findDeptIdsByEntityIds(Collections.singletonList(id));
        //获取用户
        List<AdminUserDO> adminInfos = adminUserMapper.selectAllUsers();
        //获取货物信息
        List<CommodityDO> commodityInfos = commodityMapper.selectAllInfos();
        //获取计量单位信息
        List<MeasurementDO> measurementInfos = measurementMapper.selectAllInfos();
        saveDeptInfo(commodityVO, deptInfos, adminInfos, commodityInfos, measurementInfos);
        return commodityVO;
    }

    @Override
    public List<CommodityRespVO> getCommodityList(Collection<Long> ids) {
        List<CommodityDO> list = commodityMapper.selectBatchIds(ids);
        List<CommodityRespVO> commodityRespVOS = CommodityConvert.INSTANCE.convertList(list);
        // 获取部门信息
        List<CommodityDeptDO> deptInfos = commodityDeptService.findDeptIdsByEntityIds(new ArrayList<>(ids));
        //获取用户
        List<AdminUserDO> adminInfos = adminUserMapper.selectAllUsers();
        //获取货物信息
        List<CommodityDO> commodityInfos = commodityMapper.selectAllInfos();
        //获取计量单位信息
        List<MeasurementDO> measurementInfos = measurementMapper.selectAllInfos();
        if (!CollectionUtils.isAnyEmpty(commodityRespVOS)) {
            commodityRespVOS.forEach(item -> saveDeptInfo(item, deptInfos, adminInfos, commodityInfos, measurementInfos));
        }

        return commodityRespVOS;
    }

    private void saveDeptInfo(CommodityRespVO respVO, List<CommodityDeptDO> deptInfos, List<AdminUserDO> userInfos, List<CommodityDO> commodityInfos, List<MeasurementDO> measurementInfos) {
        List<Long> list = deptInfos.stream().filter(p -> ObjectUtil.equal(respVO.getId(), p.getEntityId())).map(CommodityDeptDO::getDeptId).collect(Collectors.toList());
        respVO.setDeptIds(list);

        AdminUserDO creator = userInfos.stream().filter(p -> ObjectUtil.equal(p.getId(), respVO.getCreator())).findFirst().orElse(null);
        if (ObjUtil.isNotEmpty(creator)) {
            respVO.setCreator(creator.getNickname());
        }
        AdminUserDO updater = userInfos.stream().filter(p -> ObjectUtil.equal(p.getId(), respVO.getUpdater())).findFirst().orElse(null);
        if (ObjUtil.isNotEmpty(updater)) {
            respVO.setUpdater(updater.getNickname());
        }

        CommodityDO commodity = commodityInfos.stream().filter(p -> ObjectUtil.equal(p.getId(), respVO.getParentId())).findFirst().orElse(null);
        if (ObjUtil.isNotEmpty(commodity)) {
            respVO.setParentCommodityName(commodity.getName());
        }
        MeasurementDO measurement = measurementInfos.stream().filter(p -> ObjectUtil.equal(p.getId(), respVO.getMeasurementId())).findFirst().orElse(null);
        if (ObjUtil.isNotEmpty(measurement)) {
            respVO.setMeasurementName(measurement.getName());
        }
    }

    @Override
    public PageResult<CommodityRespVO> getCommodityPage(CommodityPageReqVO pageReqVO) {
        PageResult<CommodityDO> commodityDOPageResult = commodityMapper.selectPage(pageReqVO);
        PageResult<CommodityRespVO> commodityRespVOPageResult = CommodityConvert.INSTANCE.convertPage(commodityDOPageResult);
        List<CommodityRespVO> list = commodityRespVOPageResult.getList();

        if (!CollectionUtils.isAnyEmpty(list)) {
            List<Long> ids = list.stream().map(CommodityRespVO::getId).collect(Collectors.toList());
            // 获取部门信息
            List<CommodityDeptDO> deptInfos = commodityDeptService.findDeptIdsByEntityIds(ids);
            //获取用户
            List<AdminUserDO> adminInfos = adminUserMapper.selectAllUsers();
            //获取货物信息
            List<CommodityDO> commodityInfos = commodityMapper.selectAllInfos();
            //获取计量单位信息
            List<MeasurementDO> measurementInfos = measurementMapper.selectAllInfos();
            //获取安全告知卡
            List<Long> cardIds = list.stream().filter(p -> ObjectUtil.isNotEmpty(p.getNotifyCar())).map(CommodityRespVO::getNotifyCar).distinct().collect(Collectors.toList());
            List<FileDTO> files = new ArrayList<>();
            if (ObjectUtil.isNotEmpty(cardIds)) {
                String codeBusinessType = FileEnums.COMMODITY_SAFETY_CARD.getCodeBusinessType();
                files = fileApi.getFileUrlByCodeBusinessTypeAndSourceIds(codeBusinessType, cardIds);
            }
            for (CommodityRespVO item : list) {
                saveDeptInfo(item, deptInfos, adminInfos, commodityInfos, measurementInfos);
                if (ObjectUtil.isNotEmpty(item.getNotifyCar()) && ObjectUtil.isNotEmpty(files)) {
                    FileDTO entity = files.stream().filter(p -> ObjectUtil.equal(item.getNotifyCar(), p.getSourceId())).findFirst().orElse(null);
                    if (ObjectUtil.isEmpty(entity)) {
                        continue;
                    }
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", entity.getId());
                    map.put("name", entity.getName());
                    map.put("url", entity.getUrl());
                    item.setFileMaps(map);
                }
            }
        }
        return commodityRespVOPageResult;
    }

    @Override
    public List<CommodityExcelVO> getCommodityList(CommodityExportReqVO exportReqVO) {
        List<CommodityDO> list = commodityMapper.selectList(exportReqVO);
        LambdaQueryWrapper<CommodityDO> queryWrapper = Wrappers.lambdaQuery(CommodityDO.class);
        queryWrapper.select(CommodityDO::getId, CommodityDO::getName);
        List<CommodityDO> allList = commodityMapper.selectList(queryWrapper);
        LambdaQueryWrapper<MeasurementDO> queryWrapper1 = Wrappers.lambdaQuery(MeasurementDO.class);
        queryWrapper1.select(MeasurementDO::getId, MeasurementDO::getName);
        List<MeasurementDO> allList1 = measurementMapper.selectList(queryWrapper1);
        List<CommodityExcelVO> exportList = BeanUtils.toBean(list, CommodityExcelVO.class);
        if (ObjectUtil.isNotEmpty(list)) {
            List<Long> ids = list.stream().map(CommodityDO::getId).collect(Collectors.toList());
            List<CommodityDeptDO> details = commodityDeptMapper.selectListByIds(ids);
            for (CommodityExcelVO item : exportList) {
                //赋值parentCommodityName
                CommodityDO entity = allList.stream().filter(commodityDO -> commodityDO.getId().equals(item.getParentId())).findFirst().orElse(null);
                if (ObjectUtil.isNotEmpty(entity))
                    item.setParentCommodityName(entity.getName());
                //赋值组织
                List<String> deptStrs = details.stream().filter(detail -> detail.getEntityId().equals(item.getId())).map(CommodityDeptDO::getDeptName).collect(Collectors.toList());
                if (ObjectUtil.isNotEmpty(deptStrs))
                    item.setOrganization(String.join(",", deptStrs));
                //赋值measurementName
                MeasurementDO entity1 = allList1.stream().filter(measurementDO -> measurementDO.getId().equals(item.getMeasurementId())).findFirst().orElse(null);
                if (ObjectUtil.isNotEmpty(entity1))
                    item.setMeasurementName(entity1.getName());
            }
        }
        return exportList;
    }

    @Override
    public void batchUpdateCommodity(List<CommodityUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (CommodityUpdateReqVO updateReqVO : updateReqVOList) {
            updateCommodity(updateReqVO);
        }
    }

    @Override
    public void batchDeleteCommodity(List<Long> ids) {
        // 在这里处理批量删除逻辑
        commodityMapper.deleteBatchIds(ids);
    }

    @Override
    public void batchImportCommodity(List<CommodityDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        commodityMapper.insertBatch(importReqVOList);
    }

    @Override
    public void updateStatus(Long id) {
        UpdateWrapper<CommodityDO> updateWrapper = new UpdateWrapper<>();
        CommodityDO commodityDO = commodityMapper.selectById(id);
        updateWrapper.eq("id", id);
        Byte status = commodityDO.getStatus();
        //状态修改取反
        if (status == 0) {
            updateWrapper.set("status", 1); // 设置要更新的字段和值
        } else if (status == 1) {
            updateWrapper.set("status", 0);
        } else {
            throw new RuntimeException("货物状态错误！");
        }
        int rowsAffected = commodityMapper.update(null, updateWrapper); // 使用update方法更新字段

    }

    @Override
    public List<DeptSimpleRespVO> getDeptInfoList(Long commodityId) {
        Set<Long> deptIdSet = commodityDeptService.findDeptIdsByEntityId(commodityId);
        List<Long> deptIdList = new ArrayList<>(deptIdSet);
        List<DeptDO> deptList = deptService.getDeptList(deptIdList);
        List<DeptSimpleRespVO> convertList02 = DeptConvert.INSTANCE.convertList02(deptList);
        return convertList02;
    }

    @Override
    public void assignCommodityToDept(CommodityDeptReqVO commodityDeptReqVO) {
        Long commodityId = commodityDeptReqVO.getCommodityId();
        Set<Long> deptIds = commodityDeptReqVO.getDeptIds();
        commodityDeptService.updateBindDeptsToEntity(commodityId, deptIds);
    }

    @Override
    public List<HashMap<String, Object>> getSimpleCommodityList(ComodityQueryParam param) {
        if (ObjectUtil.isEmpty(param.getStatus())) {
            param.setStatus(0);
        }
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        Integer status = param.getStatus();
        List<Long> parentIds = param.getParentIds();
        Integer[] category = param.getType();
        MPJLambdaWrapper<CommodityDO> queryWrapper = new MPJLambdaWrapper<CommodityDO>()
                .select(CommodityDO::getId, CommodityDO::getName)
                .leftJoin(CommodityDeptDO.class, CommodityDeptDO::getEntityId, CommodityDO::getId)
                .eq(status != null, CommodityDO::getStatus, status)
                .in((category != null && category.length >= 1), CommodityDO::getCategory, category)
                .in(CollectionUtils.isNotEmpty(parentIds), CommodityDO::getParentId, parentIds)
                .eq(ObjectUtil.isNotEmpty(param.getId()), CommodityDO::getId, param.getId())
                .in(ObjectUtil.isNotEmpty(param.getCompanyIds()), CommodityDeptDO::getDeptId, param.getCompanyIds())
                .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(CommodityDO::getName, param.getSearchKey()).or().like(CommodityDO::getCode, param.getSearchKey()))
                .eq(ObjectUtil.isNotEmpty(param.getCategory()), CommodityDO::getCategory, param.getCategory())
                .groupBy(CommodityDO::getId, CommodityDO::getName)
                .disableSubLogicDel();

        List<CommodityDO> commodityDOList = commodityMapper.selectJoinList(CommodityDO.class, queryWrapper);
        commodityDOList.forEach(iterm -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("name", iterm.getName());
            list.add(map);
        });

        return list;
    }

    @Override
    public void downloadSafeCard(List<Long> ids, HttpServletResponse response) {
        String codeBusinessType = FileEnums.COMMODITY_SAFETY_CARD.getCodeBusinessType();

        if (CollectionUtils.isEmpty(ids)) {
            throw exception(COMMODITY_IDS_NOT_EXISTS);
        }

        List<FileDTO> fileDTOs = new ArrayList<>();
        for (Long id : ids) {
            List<FileDTO> fileDTOList = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(codeBusinessType, id);
            if (CollectionUtils.isNotEmpty(fileDTOList)) {
                fileDTOs.addAll(fileDTOList);
            }
        }

        try {
            downloadAsZip(fileDTOs, response);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("文件下载失败: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void downloadAsZip(List<FileDTO> fileDTOs, HttpServletResponse response) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"files.zip\"");

        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            for (FileDTO fileDTO : fileDTOs) {
                String fileUrl = fileDTO.getUrl();
                String fileName = fileDTO.getName();

                if (StringUtils.isBlank(fileUrl) || StringUtils.isBlank(fileName)) {
                    continue;
                }

                try (InputStream inputStream = new URL(fileUrl).openStream()) {
                    zos.putNextEntry(new ZipEntry(fileName));
                    IOUtils.copy(inputStream, zos);
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            zos.finish();
        }
    }


}

