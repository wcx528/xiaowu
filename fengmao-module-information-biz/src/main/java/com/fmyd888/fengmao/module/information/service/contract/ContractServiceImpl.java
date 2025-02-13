package com.fmyd888.fengmao.module.information.service.contract;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.controller.admin.contract.vo.*;
import com.fmyd888.fengmao.module.information.convert.contract.ContractConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.contract.ContractDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.contract.ContractDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.contract.ContractMapper;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.DUPLICATE_CONTRACT_NAME;

/**
 * 其他合同资料 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class ContractServiceImpl implements ContractService {

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private ContractDeptService contractDeptService;

    @Resource
    private EncodingRulesService encodingRulesService;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createContract(ContractCreateReqVO createReqVO) {
        String contractTypeName = createReqVO.getContractTypeName();
        validateContractExists(null, contractTypeName);

        ContractDO contract = ContractConvert.INSTANCE.convert(createReqVO);
        //查询数据是否重复
        LambdaQueryWrapper<ContractDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContractDO::getContractTypeName,contract.getContractTypeName())
                .eq(ContractDO::getPrincipalType,contract.getPrincipalType())
                .eq(ContractDO::getDeleted,0);
        ContractDO contractDO = contractMapper.selectOne(queryWrapper);
        if (contractDO != null){
            throw exception(DUPLICATE_CONTRACT_NAME);
        }
        // 1.1插入主体
        String contractCode = EncodingRulesEnum.OTHER_TRADE_CODE.getBusinessCode();
        String code = encodingRulesService.getCodeByRuleType(contractCode);
        contract.setCode(code);
        contractMapper.insert(contract);
        Long id = contract.getId();
        // 1.2插入绑定组织表
        Set<Long> deptIds = createReqVO.getDeptIds();
        contractDeptService.bindDeptsToEntity(id, deptIds);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContract(ContractUpdateReqVO updateReqVO) {
        // 校验存在
        Long id = updateReqVO.getId();
        String contractTypeName = updateReqVO.getContractTypeName();
        validateContractExists(id, contractTypeName);
        // 更新
        ContractDO updateObj = ContractConvert.INSTANCE.convert(updateReqVO);
        contractMapper.updateById(updateObj);

        Set<Long> deptIds = updateReqVO.getDeptIds();
        contractDeptService.updateBindDeptsToEntity(id, deptIds);
    }

    @Override
    public void deleteContract(Long id) {
        // 删除
        contractMapper.deleteById(id);
    }

    /**
     * 判断仓库名称是否重复,用于新建仓库时重复校验
     *
     * @param id   id
     * @param name 仓库名称
     */
    private void validateContractExists(Long id, String name) {
        LambdaQueryWrapper<ContractDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(id != null, ContractDO::getId, id);
        queryWrapper.eq(ContractDO::getContractTypeName, name);
        boolean exists = contractMapper.exists(queryWrapper);
        if (exists) {
            throw exception(DUPLICATE_CONTRACT_NAME, name);
        }
    }

    @Override
    public ContractRespVO getContract(Long id) {
        ContractDO contractDO = contractMapper.selectById(id);
        ContractRespVO contractRespVO = ContractConvert.INSTANCE.convert(contractDO);
        saveDeptInfo(contractRespVO);
        return contractRespVO;
    }

    @Override
    public List<ContractRespVO> getContractList(Collection<Long> ids) {
        List<ContractDO> list = contractMapper.selectBatchIds(ids);
        List<ContractRespVO> contractRespVOS = ContractConvert.INSTANCE.convertList(list);
        if (!CollectionUtils.isAnyEmpty(contractRespVOS)) {
            contractRespVOS.forEach(this::saveDeptInfo);
        }
        return contractRespVOS;
    }

    private void saveDeptInfo(ContractRespVO respVO) {
        //获取客户对应的部门组织信息
        Set<Long> deptIdSet = contractDeptService.findDeptIdsByEntityId(respVO.getId());
        Set<Long> deptIdList = new HashSet<>(deptIdSet);
        respVO.setDeptIds(deptIdList);

        AdminUserDO createUserDO = adminUserMapper.selectById(respVO.getCreator());
        if (ObjUtil.isNotEmpty(createUserDO)) {
            respVO.setCreator(createUserDO.getNickname());
        }
        AdminUserDO updateUserDO = adminUserMapper.selectById(respVO.getUpdater());
        if (ObjUtil.isNotEmpty(updateUserDO)) {
            respVO.setUpdater(updateUserDO.getNickname());
        }
    }

    @Override
    public Page<ContractRespVO> getContractPage(ContractPageReqVO pageReqVO) {
        Page<ContractRespVO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        Page<ContractRespVO> contractDOPageResult = contractMapper.selectByPage(page, pageReqVO);
        List<ContractRespVO> records = contractDOPageResult.getRecords();
        if (!CollUtil.isEmpty(records)) {
            //时间倒序
            List<ContractRespVO> collect = records.parallelStream()
                    .sorted(Comparator.comparing(ContractRespVO::getCreateTime).reversed())
                    .collect(Collectors.toList());
            //设置创建人、更新人和绑定的组织信息
            collect.forEach(this::saveDeptInfo);
            contractDOPageResult.setRecords(collect);  //重新设置排序之后的
        }
        Long total = contractMapper.selectCount();
        contractDOPageResult.setTotal(total);
        return contractDOPageResult;
    }

    @Override
    public List<ContractDO> getContractList(ContractExportReqVO exportReqVO) {
        return contractMapper.selectList(exportReqVO);
    }

    @Override
    public void batchUpdateContract(List<ContractUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (ContractUpdateReqVO updateReqVO : updateReqVOList) {
            updateContract(updateReqVO);
        }
    }

    @Override
    public void batchDeleteContract(List<Long> ids) {
        // 在这里处理批量删除逻辑
        contractMapper.deleteBatchIds(ids);
    }

    @Override
    public void assignContractToDept(ContractDeptReqVO contractDeptReqVO) {
        Long contractId = contractDeptReqVO.getContractId();
        Set<Long> deptIds = contractDeptReqVO.getDeptIds();
        contractDeptService.updateBindDeptsToEntity(contractId, deptIds);
    }

    @Override
    public List<HashMap<String, Object>> selectDetailsByDeptId(Long companyId) {

        List<HashMap<String, Object>> list = new ArrayList<>();

        MPJLambdaWrapper<ContractDO> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectAll(ContractDO.class)
                .leftJoin(ContractDeptDO.class, ContractDeptDO::getEntityId, ContractDO::getId) // 关联查询
                .eq(ContractDeptDO::getDeptId, companyId) // 设置关联条件
                .eq(ContractDO::getDeleted, 0)
                .eq(ContractDeptDO::getDeleted, 0);
        //查询
        List<ContractDO> contractDOList = contractMapper.selectList(mpjLambdaWrapper);

        contractDOList.forEach(contractDO -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("contractDataId",contractDO.getId());//当前数据id
            map.put("contractTypeName", contractDO.getContractTypeName()); //合同类型
            list.add(map);
        });

         return list;
    }

    @Override
    public HashMap<String,Object> selectTypeByContractTypeId(Long contractDataId) {
        LambdaQueryWrapper<ContractDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContractDO::getId, contractDataId)
                .eq(ContractDO::getDeleted, 0);
        ContractDO contractDO = contractMapper.selectOne(queryWrapper);
        if (contractDO != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("principalType", contractDO.getPrincipalType());
            return map;
        }
        return new HashMap<>();
    }

    @Override
    public List<String> getOtherContractTypeList() {
        LambdaQueryWrapper<ContractDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContractDO::getDeleted, 0)
                .select(ContractDO::getContractTypeName);
        List<ContractDO> contractDOList = contractMapper.selectList(queryWrapper);
        if (contractDOList != null){
            return contractDOList.stream().map(ContractDO::getContractTypeName).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void batchImportContract(List<ContractDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        contractMapper.insertBatch(importReqVOList);
    }
}
