package com.fmyd888.fengmao.module.information.service.employee;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fmyd888.fengmao.framework.common.exception.ErrorCode;
import com.fmyd888.fengmao.framework.common.exception.ServiceException;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.datapermission.core.util.DataPermissionUtils;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.system.controller.admin.user.vo.user.UserBasicRespVO;
import com.fmyd888.fengmao.module.system.convert.user.UserConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.employee.EmployeeDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import com.fmyd888.fengmao.module.information.convert.employee.EmployeeConvert;
import com.fmyd888.fengmao.module.information.dal.mysql.employee.EmployeeMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 员工信息表 Service 实现类
 *
 * @author 丰茂企业
 */
@Service
@Validated
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private EncodingRulesService encodingRulesService;

    @Override
    public Long createEmployee(Long userId,EmployeeCreateReqVO createReqVO) {
        //获取员工编码类型
        String code=encodingRulesService.getCodeByRuleType("9");

        // 插入
        EmployeeDO employee = EmployeeConvert.INSTANCE.convert(createReqVO).setUsersId(userId).setEmployeeId(code);
        employeeMapper.insert(employee);
        // 返回
        return employee.getId();
    }

    @Override
    public void updateEmployee(EmployeeUpdateReqVO updateReqVO) {
        // 校验存在
        validateEmployeeExists(updateReqVO.getId());
        // 更新
        EmployeeDO updateObj = EmployeeConvert.INSTANCE.convert(updateReqVO);
        employeeMapper.updateById(updateObj);
    }

    @Override
    public void deleteEmployee(Long id) {
        // 校验存在
        validateEmployeeExists(id);
        // 删除
        employeeMapper.deleteById(id);
    }


    private void validateEmployeeForCreateOrUpdate(Long id, String EmployeeId) {
        // 关闭数据权限，避免因为没有数据权限，查询不到数据，进而导致唯一校验不正确
        DataPermissionUtils.executeIgnore(() -> {
            // 校验用户存在
            validateEmployeeExists(id);
            // 校验邮箱唯一
            validateEmployeeDescription(id, EmployeeId);

        });
    }
    @VisibleForTesting
     void validateEmployeeExists(Long id) {
        if (id ==null){
            return;
        }
        EmployeeDO employeeDO=employeeMapper.selectById(id);
        if (employeeDO == null) {
            throw exception(EMPLOYEE_NOT_EXISTS);
        }
    }
    @VisibleForTesting
    void validateEmployeeDescription(Long id,String employeeId){
        if (StrUtil.isBlank(employeeId)){
            return;
        }
        EmployeeDO employee=employeeMapper.selectByEmployeeId(employeeId);
        if (employee == null){
            return;
        }
        //如果id为空，说明不用比较是否为相同 id 的用户
        if (id ==null){
            throw exception(EMPLOYEE_DESCRIPTION_EXISTS);
        }
        if (!employee.getId().equals(id)){
            throw exception(EMPLOYEE_DESCRIPTION_EXISTS);
        }
    }

    @Override
    public EmployeeDO getEmployee(Long id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public List<EmployeeDO> getEmployeeList(Collection<Long> ids) {
        return employeeMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<EmployeeDO> getEmployeePage(EmployeePageReqVO pageReqVO) {
        return employeeMapper.selectPage(pageReqVO);
    }


    @Override
    public List<EmployeeDO> getEmployeeList(EmployeeExportReqVO exportReqVO) {
        return employeeMapper.selectList(exportReqVO);
    }

    @Override
    public List<EmployeeDO> getEmployeeNameListById() {
        //查询
        LambdaQueryWrapper<EmployeeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(EmployeeDO::getId,EmployeeDO::getName,EmployeeDO::getEmployeeId);

        return employeeMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) //添加事务，异常则回滚所有导入
    public EmployeeImportRespVO importEmployeeList(List<EmployeeImportExcelVO> importEmployee, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importEmployee)){
            throw exception(EMPLOYEE_IMPORT_LIST_IS_EMPTY);
        }
        EmployeeImportRespVO respVO=EmployeeImportRespVO.builder().createEmployeeNames(new ArrayList<>())
                .updateEmployeeNames(new ArrayList<>()).failureEmployeeNames(new LinkedHashMap<>()).build();
        importEmployee.forEach(importEmployees -> {
            // 校验，判断是否有不符合的原因
            try {
                validateEmployeeForCreateOrUpdate(null,importEmployees.getEmployeeId());
            } catch (ServiceException ex) {
                respVO.getFailureEmployeeNames().put(importEmployees.getName(), ex.getMessage());
                return;
            }
        //判断如果不存在，再进行插入
            EmployeeDO existEmployee=employeeMapper.selectByEmployeeId(importEmployees.getEmployeeId());
            if (existEmployee == null){
                employeeMapper.insert(EmployeeConvert.INSTANCE.convert(importEmployees));
                respVO.getCreateEmployeeNames().add(importEmployees.getName());
                return;
            }
            //如果存在，判断是否允许更新
            if (!isUpdateSupport){
                respVO.getFailureEmployeeNames().put(importEmployees.getName(),EMPLOYEE_NOT_EXISTS.getMsg());
                return;
            }
            EmployeeDO updateEmp=EmployeeConvert.INSTANCE.convert(importEmployees);
            updateEmp.setId(existEmployee.getId());
            employeeMapper.updateById(updateEmp);
            respVO.getUpdateEmployeeNames().add(importEmployees.getName());
        });
        return respVO;
    }

    @Override
    public void batchDeleteContract(List<Long> ids) {
        //批量删除
        employeeMapper.deleteBatchIds(ids);
    }

    @Override
    public EmployeeSimpleRespVO selectById(Long id) {
        EmployeeDO employeeDO = employeeMapper.selectById(id);
        EmployeeSimpleRespVO employeeSimpleRespVO = new EmployeeSimpleRespVO();
        if (!ObjectUtil.isEmpty(employeeDO)) {
            employeeSimpleRespVO.setId(employeeDO.getId());
            employeeSimpleRespVO.setEmployeeName(employeeDO.getName());
            employeeSimpleRespVO.setEmail(employeeDO.getEmail());

        }
        return employeeSimpleRespVO;
    }

    @Override
    public List<EmployeeDO> getEmployeeListByStatus(Integer status) {
        //LambdaQueryWrapper<EmployeeDO> up = new LambdaQueryWrapper<>();
        //up.eq(EmployeeDO::getStatus,0);
        return employeeMapper.selectList();
    }

    @Override
    public EmployeeBasicRespVO getEmployee02(Long id){
        LambdaQueryWrapper<EmployeeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EmployeeDO::getStatus,0)
                .eq(EmployeeDO::getId, id);
        EmployeeDO employeeDO = employeeMapper.selectOne(queryWrapper);
        if(ObjectUtils.isEmpty(employeeDO)){
//            throw exception(EMPLOYEE_EXISTS);
        }
        EmployeeBasicRespVO employeeBasicRespVO = EmployeeConvert.INSTANCE.convert05(employeeDO);
        return employeeBasicRespVO;
    }


}