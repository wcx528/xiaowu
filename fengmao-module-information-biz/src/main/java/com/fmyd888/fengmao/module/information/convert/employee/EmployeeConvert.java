package com.fmyd888.fengmao.module.information.convert.employee;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.employee.EmployeeDO;

/**
 * 员工信息表 Convert
 *
 * @author 丰茂企业
 */
@Mapper
public interface EmployeeConvert {

    EmployeeConvert INSTANCE = Mappers.getMapper(EmployeeConvert.class);

    EmployeeDO convert(EmployeeCreateReqVO bean);

    EmployeeDO convert(EmployeeUpdateReqVO bean);

    EmployeeRespVO convert(EmployeeDO bean);

    List<EmployeeRespVO> convertList(List<EmployeeDO> list);

    PageResult<EmployeeRespVO> convertPage(PageResult<EmployeeDO> page);

    List<EmployeeExcelVO> convertList02(List<EmployeeDO> list);



    EmployeeDO convert(EmployeeImportExcelVO bean);

    List<EmployeeBasicRespVO> convertList05(List<EmployeeDO> list);

    EmployeeBasicRespVO convert05(EmployeeDO employeeDO);
}
