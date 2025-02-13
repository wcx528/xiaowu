package com.fmyd888.fengmao.module.information.dal.mysql.employee;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.employee.EmployeeDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.*;

/**
 * 员工信息表 Mapper
 *
 * @author 丰茂企业
 */
@Mapper
public interface EmployeeMapper extends BaseMapperX<EmployeeDO> {


    default EmployeeDO selectByEmployeeId(String employeeId){
        return selectOne(EmployeeDO::getDescription,employeeId);
    }
    default PageResult<EmployeeDO> selectPage(EmployeePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EmployeeDO>()
                .likeIfPresent(EmployeeDO::getName, reqVO.getName())
                .eqIfPresent(EmployeeDO::getType, reqVO.getType())
                .eqIfPresent(EmployeeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(EmployeeDO::getDescription, reqVO.getDescription())
                .eqIfPresent(EmployeeDO::getEmployeeId, reqVO.getEmployeeId())
                .eqIfPresent(EmployeeDO::getEmail, reqVO.getEmail())
                .betweenIfPresent(EmployeeDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(EmployeeDO::getUsersId, reqVO.getUsersId())
                .orderByDesc(EmployeeDO::getId));
    }

    default List<EmployeeDO> selectList(EmployeeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EmployeeDO>()
                .likeIfPresent(EmployeeDO::getName, reqVO.getName())
                .eqIfPresent(EmployeeDO::getType, reqVO.getType())
                .eqIfPresent(EmployeeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(EmployeeDO::getDescription, reqVO.getDescription())
                .eqIfPresent(EmployeeDO::getEmployeeId, reqVO.getEmployeeId())
                .eqIfPresent(EmployeeDO::getEmail, reqVO.getEmail())
                .betweenIfPresent(EmployeeDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(EmployeeDO::getUsersId, reqVO.getUsersId())
                .orderByDesc(EmployeeDO::getId));
    }

}
