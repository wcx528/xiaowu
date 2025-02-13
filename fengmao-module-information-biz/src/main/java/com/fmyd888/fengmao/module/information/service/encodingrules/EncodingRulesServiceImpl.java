package com.fmyd888.fengmao.module.information.service.encodingrules;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.*;
import com.fmyd888.fengmao.module.information.convert.encodingrules.EncodingRulesConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.encodingrules.EncodingRulesDO;
import com.fmyd888.fengmao.module.information.dal.mysql.encodingrules.EncodingRulesMapper;
import com.fmyd888.fengmao.module.information.utils.EncodingRulesGeneratorUtils;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.api.user.dto.AdminUserRespDTO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dict.DictDataDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.dict.DictDataMapper;
import com.fmyd888.fengmao.ssodemo.framework.core.util.SecurityUtils;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 编码规则设置 Service 实现类
 *
 * @author fengmao
 */
@Service
@Validated
public class EncodingRulesServiceImpl implements EncodingRulesService {

    @Resource
    private EncodingRulesMapper encodingRulesMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private DictDataMapper dictDataMapper;

    @Override
    public Long createEncodingRules(EncodingRulesCreateReqVO createReqVO) {
        // 插入
        EncodingRulesDO encodingRules = EncodingRulesConvert.INSTANCE.convert(createReqVO);
        encodingRulesMapper.insert(encodingRules);
        // 返回
        return encodingRules.getId();
    }

    @Override
    public void updateEncodingRules(EncodingRulesUpdateReqVO updateReqVO) {
        // 校验存在
        validateEncodingRulesExists(updateReqVO.getId());
        // 更新
        EncodingRulesDO updateObj = EncodingRulesConvert.INSTANCE.convert(updateReqVO);
        encodingRulesMapper.updateById(updateObj);
    }

    @Override
    public void deleteEncodingRules(Long id) {
        // 校验存在
        validateEncodingRulesExists(id);
        // 删除
        encodingRulesMapper.deleteById(id);
    }

    private void validateEncodingRulesExists(Long id) {
        if (encodingRulesMapper.selectById(id) == null) {
            //throw exception(ENCODING_RULES_NOT_EXISTS);
        }
    }

    @Override
    public EncodingRulesDO getEncodingRules(Long id) {
        return encodingRulesMapper.selectById(id);
    }

    @Override
    public List<EncodingRulesDO> getEncodingRulesList(Collection<Long> ids) {
        return encodingRulesMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<EncodingRulesDO> getEncodingRulesPage(EncodingRulesPageReqVO pageReqVO) {
        return encodingRulesMapper.selectPage(pageReqVO);
    }

    @Override
    public List<EncodingRulesDO> getEncodingRulesList(EncodingRulesExportReqVO exportReqVO) {
        return encodingRulesMapper.selectList(exportReqVO);
    }


    @Override
    public String getCodeByRuleType(String ruleType) {
        QueryWrapper<EncodingRulesDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_type", ruleType);
        EncodingRulesDO encodingRulesDO = encodingRulesMapper.selectOne(queryWrapper);
        if (Objects.isNull(encodingRulesDO)) {
            //或者使用默认的字符串编码规则
            return getDefaultCode();
            //throw new RuntimeException(businessObjectId+"业务对象id不存在设定的编码规则");
        }
        //String code = getCode(encodingRulesDO);
        String code = null;
        synchronized (getLockObject(ruleType)) {
            code = getCode(encodingRulesDO);
        }
        return code;
    }

    @Override
    public List<String> getBatchCodeByRuleType(String ruleType, Integer length) {
        QueryWrapper<EncodingRulesDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_type", ruleType);
        EncodingRulesDO encodingRulesDO = encodingRulesMapper.selectOne(queryWrapper);
        if (Objects.isNull(encodingRulesDO)) {
            return getDefaultCode(length);
        }
        List<String> codes = new ArrayList<>();
        synchronized (getLockObject(ruleType)) {
            codes = getBatchCode(encodingRulesDO, length);
        }
        return codes;
    }

    @Override
    public String getVehicleCodeByRuleType(String ruleType) {
        LambdaQueryWrapperX<EncodingRulesDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(EncodingRulesDO::getRuleType, ruleType);
        EncodingRulesDO encodingRulesDO = encodingRulesMapper.selectOne(queryWrapper);
        if (Objects.isNull(encodingRulesDO)) {
            //throw exception();
        }
        String code = null;
        synchronized (getLockObject(ruleType)) {
            code = getCode2(encodingRulesDO);
        }
        return code;
    }

    @Override
    public Boolean isFillOverWrite(String ruleType) {
        LambdaQueryWrapperX<EncodingRulesDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(EncodingRulesDO::getRuleType, ruleType);
        EncodingRulesDO encodingRulesDO = encodingRulesMapper.selectOne(queryWrapper);
        Integer modifiable = encodingRulesDO.getModifiable();
        return modifiable.equals(0);
    }

    //不同编码类型规则对应的资源定义
    private static final Map<String, Object> locks = new ConcurrentHashMap<>();

    /**
     * 根据ruleType编码类型规则作为key，获得对应的随机对象，当成锁资源
     *
     * @param ruleType
     * @return
     */
    private Object getLockObject(String ruleType) {
        // 使用 ConcurrentHashMap 来确保线程安全
        return locks.computeIfAbsent(ruleType, k -> new Object());
    }

    /**
     * 通用获取编码
     *
     * @param codeRules
     * @return
     */
    private String getCode(EncodingRulesDO codeRules) {

        StringBuilder codeBuilder = new StringBuilder();
        // 1.添加前缀
        if (codeRules.getPrefix() != null) {
            codeBuilder.append(codeRules.getPrefix());
        }

        // 2.添加时间部分
        String timeFormat = codeRules.getTimeFormat();
        if (!StringUtils.isEmpty(timeFormat)) {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
            String formattedTime = currentTime.format(formatter);
            codeBuilder.append(formattedTime);
        }

        // 3.添加中间分隔符
        if (codeRules.getRuleSeparator() != null) {
            codeBuilder.append(codeRules.getRuleSeparator());
        }

        // 4.流水号部分
        String serialNumber = EncodingRulesGeneratorUtils.createAutoID(codeRules.getRuleType(), codeRules.getSerialNumber());
        codeBuilder.append(serialNumber);

        // 5.添加后缀
        if (codeRules.getSuffix() != null) {
            codeBuilder.append(codeRules.getSuffix());
        }

        return codeBuilder.toString();
    }

    /**
     * 功能描述：批量通用获取编码方法
     *
     * @param codeRules 规则
     * @param length    长度
     * @return {@link List }<{@link String }>
     * @author 小逺
     * @date 2024/08/02
     */
    private List<String> getBatchCode(EncodingRulesDO codeRules, Integer length) {
        List<String> codes = new ArrayList<>();
        StringBuilder codeBuilder = new StringBuilder();
        // 1.添加前缀
        if (codeRules.getPrefix() != null) {
            codeBuilder.append(codeRules.getPrefix());
        }

        // 2.添加时间部分
        String timeFormat = codeRules.getTimeFormat();
        if (!StringUtils.isEmpty(timeFormat)) {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
            String formattedTime = currentTime.format(formatter);
            codeBuilder.append(formattedTime);
        }

        // 3.添加中间分隔符
        if (codeRules.getRuleSeparator() != null) {
            codeBuilder.append(codeRules.getRuleSeparator());
        }

        // 4.流水号部分
        List<String> serialNumbers = EncodingRulesGeneratorUtils.createBatchAutoID(codeRules.getRuleType(), codeRules.getSerialNumber(), length);
        for (String serialNumber : serialNumbers) {
            String code = codeBuilder.toString() + serialNumber;
            // 5.添加后缀
            if (codeRules.getSuffix() != null) {
                code += codeRules.getSuffix();
            }
            codes.add(code);
        }
        return codes;
    }

    /**
     * 通用获取编码
     *
     * @param codeRules
     * @return
     */
    private String getCode2(EncodingRulesDO codeRules) {

        StringBuilder codeBuilder = new StringBuilder();
        // 1.添加前缀
        if (codeRules.getPrefix() != null) {
            codeBuilder.append(codeRules.getPrefix());
        }

        // 1.1 公司首字母大写，根据当前登录人所在的公司名称
        if (codeRules.getPrefix() != null) {
            AdminUserRespDTO adminUserRespDTO = adminUserApi.getLoginUserInfo();
            DeptDO deptDO = deptMapper.selectById(adminUserRespDTO.getDeptId());
            codeBuilder.append(deptDO.getName());
        }

        // 2.添加时间部分
        String timeFormat = codeRules.getTimeFormat();
        if (!StringUtils.isEmpty(timeFormat)) {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
            String formattedTime = currentTime.format(formatter);
            codeBuilder.append(formattedTime);
        }

        // 3.添加中间分隔符
        if (codeRules.getRuleSeparator() != null) {
            codeBuilder.append(codeRules.getRuleSeparator());
        }

        // 4.流水号部分
        String serialNumber = EncodingRulesGeneratorUtils.createAutoID(codeRules.getRuleType(), codeRules.getSerialNumber());
        codeBuilder.append(serialNumber);

        // 5.添加后缀
        if (codeRules.getSuffix() != null) {
            codeBuilder.append(codeRules.getSuffix());
        }

        return codeBuilder.toString();
    }

    /**
     * 若没有指定的编码生成规则，默认返回字符串
     *
     * @return
     */
    private String getDefaultCode() {
        String uuid = UUID.randomUUID().toString();
        return "Default" + uuid;
    }

    /**
     * 功能描述：没有指定生成规则返回默认规则编码
     *
     * @param length
     * @return {@link List }<{@link String }>
     * @author 小逺
     * @date 2024/08/02
     */
    private List<String> getDefaultCode(Integer length) {
        return Collections.nCopies(length, getDefaultCode());
    }

    @Override
    public EncodingRulesDO getByRuleName(String ruleName) {
        QueryWrapper<EncodingRulesDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("rule_name", ruleName);
        EncodingRulesDO encodingRulesDO = encodingRulesMapper.selectOne(queryWrapper);
        if (Objects.isNull(encodingRulesDO)) {
            return null;
        }
        return encodingRulesDO;
    }


    @Override
    public List<EncodingRulesDO> getEncodingRulesExcel(EncodingRulesListReqVO listReqVO) {
        return encodingRulesMapper.selectList(listReqVO);
    }

    @Override
    public List<HashMap<String, Object>> getSimpleCodingRuleList(CommonQueryParam param) {
        if (ObjectUtil.isEmpty(param.getStatus()))
            param.setStatus(0);
        List<HashMap<String, Object>> list = new ArrayList<>();
        LambdaQueryWrapper<EncodingRulesDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(EncodingRulesDO::getId, EncodingRulesDO::getRuleName, EncodingRulesDO::getRuleType)
                .eq(EncodingRulesDO::getStatus, param.getStatus())
                .eq(ObjectUtil.isNotEmpty(param.getId()), EncodingRulesDO::getId, param.getId())
                .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(EncodingRulesDO::getRuleName, param.getSearchKey()))
                .groupBy(EncodingRulesDO::getId, EncodingRulesDO::getRuleName, EncodingRulesDO::getRuleType);

        List<EncodingRulesDO> EncodingRulesDOS = encodingRulesMapper.selectList(queryWrapper);
        EncodingRulesDOS.forEach(iterm -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("name", iterm.getRuleName());
            map.put("ruleType", iterm.getRuleType());
            list.add(map);
        });
        return list;
    }

    @Override
    public List<Map<String, Object>> getCodingRuleList(Integer type) {

        Integer statusValue = 0; //状态
        String dictType = "encoding_rules"; //编码规则的字典类型
        LambdaQueryWrapper<DictDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictDataDO::getDictType, dictType)
                .eq(DictDataDO::getStatus, statusValue);
        List<DictDataDO> dictDataDOS = dictDataMapper.selectList(queryWrapper);
        //List<String> allRuleTypeList = dictDataDOS.stream()
        //        .map(DictDataDO::getValue)
        //        .collect(Collectors.toList());

        if (type != null) {
            LambdaQueryWrapper<EncodingRulesDO> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(EncodingRulesDO::getStatus, statusValue);
            List<EncodingRulesDO> encodingRulesDOS = encodingRulesMapper.selectList(queryWrapper1);
            List<String> userRuleTypeList = encodingRulesDOS.stream()
                    .map(EncodingRulesDO::getRuleType)
                    .collect(Collectors.toList());
        }

        return null;
    }
}
