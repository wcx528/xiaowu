package com.fmyd888.fengmao.module.information.service.employee;

import java.util.*;
import javax.validation.*;
import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.employee.EmployeeDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;

/**
 * 员工信息表 Service 接口
 *
 * @author 丰茂企业
 */
public interface EmployeeService {

    /**
     * 创建员工信息表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEmployee(Long userId,@Valid EmployeeCreateReqVO createReqVO);

    /**
     * 更新员工信息表
     *
     * @param updateReqVO 更新信息
     */
    void updateEmployee(@Valid EmployeeUpdateReqVO updateReqVO);

    /**
     * 删除员工信息表
     *
     * @param id 编号
     */
    void deleteEmployee(Long id);

    /**
     * 获得员工信息表
     *
     * @param id 编号
     * @return 员工信息表
     */
    EmployeeDO getEmployee(Long id);

    /**
     * 获得员工信息表列表
     *
     * @param ids 编号
     * @return 员工信息表列表
     */
    List<EmployeeDO> getEmployeeList(Collection<Long> ids);

    /**
     * 获得员工信息表分页
     *
     * @param pageReqVO 分页查询
     * @return 员工信息表分页
     */
    PageResult<EmployeeDO> getEmployeePage(EmployeePageReqVO pageReqVO);

    /**
     * 获得员工信息表列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 员工信息表列表
     */
    List<EmployeeDO> getEmployeeList(EmployeeExportReqVO exportReqVO);

    /**
     * 获得员工姓名信息,
     *
     * @return 员工信息表列表
     */
    List<EmployeeDO> getEmployeeNameListById();

    /**
     *
     *导入
     */

    EmployeeImportRespVO importEmployeeList(List<EmployeeImportExcelVO> importEmployee,boolean isUpdateSupport);

    /**
     * 批量删除
     * @param ids
     */
    void batchDeleteContract(List<Long> ids);

    EmployeeSimpleRespVO selectById(Long id);

    List<EmployeeDO> getEmployeeListByStatus(Integer status);

    EmployeeBasicRespVO getEmployee02(Long id);
}
