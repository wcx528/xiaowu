package com.fmyd888.fengmao.module.information.service.measurement;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.*;
import com.fmyd888.fengmao.module.information.convert.measurement.MeasurementConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.measurement.MeasurementDeptMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.measurement.MeasurementMapper;
import com.fmyd888.fengmao.module.information.dal.redis.RedisKeyConstants;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.DUPLICATE_MEASUREMENT_NAME;

/**
 * 计量单位表 Service 实现类
 *
 * @author 丰茂企业
 */
@Service
@Validated
public class MeasurementServiceImpl implements MeasurementService {

    @Resource
    private MeasurementMapper measurementMapper;

    //@Resource
    //private EncodingRulesService encodingRulesService;

    @Resource
    private MeasurementDeptService measurementDeptService;

    @Resource
    private MeasurementDeptMapper measurementDeptMapper;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMeasurement(MeasurementCreateReqVO createReqVO) {
        validateContractExists(null, createReqVO.getName());
        // 插入
        MeasurementDO measurement = MeasurementConvert.INSTANCE.convert(createReqVO);
        //前端设置计量单位编码，不用自动生成
//        String unitCode = EncodingRulesEnum.UNIT_CODE.getBusinessCode();
//        String code = encodingRulesService.getCodeByRuleType(unitCode);
//        measurement.setCode(code);
        measurementMapper.insert(measurement);
        Long id = measurement.getId();
        Set<Long> dept = createReqVO.getDeptIds();
        measurementDeptService.bindDeptsToEntity(id, dept);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMeasurement(MeasurementUpdateReqVO updateReqVO) {
        // 校验存在
        Long id = updateReqVO.getId();
        String name = updateReqVO.getName();
        validateContractExists(id, name);
        // 更新
        MeasurementDO updateObj = MeasurementConvert.INSTANCE.convert(updateReqVO);
        measurementMapper.updateById(updateObj);
        Set<Long> deptIds = updateReqVO.getDeptIds();
        measurementDeptService.updateBindDeptsToEntity(id, deptIds);
    }

    @Override
    public void deleteMeasurement(Long id) {
        // 删除
        measurementMapper.deleteById(id);
    }

    /**
     * 判断仓库名称是否重复,用于新建仓库时重复校验
     *
     * @param id   id
     * @param name 仓库名称
     */
    private void validateContractExists(Long id, String name) {
        LambdaQueryWrapper<MeasurementDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(id != null, MeasurementDO::getId, id);
        queryWrapper.eq(MeasurementDO::getName, name);
        boolean exists = measurementMapper.exists(queryWrapper);
        if (exists) {
            throw exception(DUPLICATE_MEASUREMENT_NAME, name);
        }
    }

    @Override
    public MeasurementRespVO getMeasurement(Long id) {
        MeasurementDO measurementDO = measurementMapper.selectById(id);
        MeasurementRespVO measurementRespVO = MeasurementConvert.INSTANCE.convert(measurementDO);
        setDeptInfo(measurementRespVO);
        return measurementRespVO;
    }

    private void setDeptInfo(MeasurementRespVO respVO) {
        //3、获取计量单位对应的部门组织信息
        Long id = respVO.getId();
        Set<Long> deptIdSet = measurementDeptService.findDeptIdsByEntityId(id);
        List<Long> deptIdList = new ArrayList<>(deptIdSet);
        respVO.setDeptIds(deptIdList);
        Long parentId = respVO.getParentId();
        MeasurementDO measurementDO = measurementMapper.selectById(parentId);
        if (ObjUtil.isNotEmpty(measurementDO)) {
            respVO.setParentName(measurementDO.getName());
            respVO.setParentMeasurementCode(measurementDO.getCode());
        }
        String creatorId = respVO.getCreator();
        String updaterId = respVO.getUpdater();
        AdminUserDO creatorUserDO = adminUserMapper.selectById(creatorId);
        AdminUserDO updaterUserDO = adminUserMapper.selectById(updaterId);
        respVO.setCreator(creatorUserDO.getNickname());
        respVO.setUpdater(updaterUserDO.getNickname());
    }

    @Override
    public List<MeasurementRespVO> getMeasurementList(Collection<Long> ids) {
        List<MeasurementDO> list = measurementMapper.selectBatchIds(ids);
        List<MeasurementRespVO> measurementRespVOS = MeasurementConvert.INSTANCE.convertList(list);
        measurementRespVOS.forEach(iterm -> {
            setDeptInfo(iterm);
        });
        return measurementRespVOS;
    }

    @Override
    public PageResult<MeasurementRespVO> getMeasurementPage(MeasurementPageReqVO pageReqVO) {
        PageResult<MeasurementDO> pageResult = measurementMapper.selectJoinTileList(pageReqVO);
        pageResult.getList().forEach(p -> {
            p.setCreator(p.getCreatorName());
            p.setUpdater(p.getUpdaterName());
        });
        PageResult<MeasurementRespVO> result = BeanUtils.toBean(pageResult, MeasurementRespVO.class);
        List<MeasurementRespVO> records = result.getList();
        if (ObjectUtil.isNotEmpty(records)) {
            List<Long> ids = records.stream().map(MeasurementRespVO::getId).collect(Collectors.toList());
            List<MeasurementDeptDO> details = measurementDeptService.findDeptIdsByEntityIds(ids);
            List<MeasurementDO> measurements = measurementMapper.selectAllInfos();
            records.forEach(p -> {
                List<Long> list = details.stream().filter(q -> ObjectUtil.equal(q.getEntityId(), p.getId())).map(MeasurementDeptDO::getDeptId).collect(Collectors.toList());
                p.setDeptIds(list);
                MeasurementDO entity = measurements.stream().filter(q -> ObjectUtil.equal(q.getId(), p.getParentId())).findFirst().orElse(null);
                if (ObjectUtil.isNotEmpty(entity)) {
                    p.setParentName(entity.getName());
                    p.setParentMeasurementCode(entity.getCode());
                }
            });
        }
        return result;
    }

    @Override
    public List<HashMap<String, Object>> getExportList() {
        List<HashMap<String, Object>> exportList = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Field[] fields = DownMeasureExcelVO.class.getDeclaredFields();
        for (Field field : fields) {
            // 获取字段的注解
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                String[] values = excelProperty.value();
                if (values.length > 0) {
                    String key = field.getName(); // 使用字段名称作为键
                    String value = values[0]; // 使用 ExcelProperty 注解的第一个值作为值
                    map.put(key, value);
                }
            }
        }

        exportList.add(map);
        return exportList;
    }

    @Override
    public List<MeasurementExcelVO> getMeasurementList(MeasurementExportReqVO exportReqVO) {
        List<MeasurementExcelVO> datas = new ArrayList<>();
        List<MeasurementDO> measurementList = measurementMapper.selectStoreExportList(exportReqVO);
        if (ObjectUtil.isNotEmpty(measurementList)) {
            measurementList.forEach(p -> {
                p.setCreator(p.getCreatorName());
                p.setUpdater(p.getUpdaterName());
            });
            datas = BeanUtils.toBean(measurementList, MeasurementExcelVO.class);
            List<Long> ids = measurementList.stream().map(MeasurementDO::getId).collect(Collectors.toList());
            List<MeasurementDeptDO> details = measurementDeptMapper.selectListByIds(ids);
            List<MeasurementDO> measurements = measurementMapper.selectAllInfos();
            datas.forEach(p -> {
                MeasurementDO entity = measurements.stream().filter(q -> ObjectUtil.equal(q.getId(), p.getParentId())).findFirst().orElse(null);
                if (ObjectUtil.isNotEmpty(entity)) {
                    p.setParentName(entity.getName());
                    p.setParentMeasurementCode(entity.getCode());
                }
                List<String> collect = details.stream().filter(q -> ObjectUtil.equal(q.getEntityId(), p.getId())).map(MeasurementDeptDO::getDeptName).collect(Collectors.toList());
                if(ObjectUtil.isNotEmpty(collect)){
                    p.setDeptIds(String.join(",", collect));
                }
            });
        }
        return datas;
    }

    @Override
    public List<MeasurementDO> getNameListById() {
        //查询
        QueryWrapper<MeasurementDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(MeasurementDO::getId, MeasurementDO::getName);
        return measurementMapper.selectList(queryWrapper);
    }

    @Override
    public void updateStatus(Long id) {
        UpdateWrapper<MeasurementDO> updateWrapper = new UpdateWrapper<>();
        MeasurementDO measurementDO = measurementMapper.selectById(id);
        updateWrapper.eq("id", id);
        Byte status = measurementDO.getStatus();
        //状态修改取反
        if (status == 0) {
            updateWrapper.set("status", 1); // 设置要更新的字段和值
        } else if (status == 1) {
            updateWrapper.set("status", 0);
        } else {
            throw new RuntimeException("计量单位状态错误！");
        }

        int rowsAffected = measurementMapper.update(null, updateWrapper); // 使用update方法更新字段
    }

    @Override
    public void assignMeasurementToDept(MeasurementDeptReqVO storeDeptReqVO) {
        Long measurementId = storeDeptReqVO.getMeasurementId();
        Set<Long> deptIds = storeDeptReqVO.getDeptIds();
        measurementDeptService.updateBindDeptsToEntity(measurementId, deptIds);
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.MEASUREMENT_SIMPLE_LIST + "#120", key = "#param.status+'_'+#param.searchKey+'_'+#param.id+'_'+#param.companyIds")
    public List<HashMap<String, Object>> getSimpleMeasurementList(CommonQueryParam param) {
        if (ObjectUtil.isEmpty(param.getStatus()))
            param.setStatus(0);
        List<HashMap<String, Object>> list = new ArrayList<>();
        MPJLambdaWrapper<MeasurementDO> queryWrapper = new MPJLambdaWrapper<MeasurementDO>()
                .select(MeasurementDO::getId, MeasurementDO::getName)
                .leftJoin(MeasurementDeptDO.class, MeasurementDeptDO::getEntityId, MeasurementDO::getId)
                .eq(MeasurementDO::getStatus, param.getStatus())
                .eq(ObjectUtil.isNotEmpty(param.getId()), MeasurementDO::getId, param.getId())
                .in(ObjectUtil.isNotEmpty(param.getCompanyIds()), MeasurementDeptDO::getDeptId, param.getCompanyIds())
                .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(MeasurementDO::getName, param.getSearchKey()).or().like(MeasurementDO::getCode, param.getSearchKey()))
                .groupBy(MeasurementDO::getId, MeasurementDO::getName)
                .disableSubLogicDel();
        List<MeasurementDO> measurementDOS = measurementMapper.selectJoinList(MeasurementDO.class, queryWrapper);
        measurementDOS.forEach(iterm -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("name", iterm.getName());
            list.add(map);
        });
        return list;
    }
}
