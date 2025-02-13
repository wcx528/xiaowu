package com.fmyd888.fengmao.module.information.convert.employee;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.EmployeeBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.EmployeeCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.EmployeeExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.EmployeeImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.EmployeeRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.EmployeeUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.employee.EmployeeDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class EmployeeConvertImpl implements EmployeeConvert {

    @Override
    public EmployeeDO convert(EmployeeCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        EmployeeDO.EmployeeDOBuilder employeeDO = EmployeeDO.builder();

        employeeDO.name( bean.getName() );
        employeeDO.type( bean.getType() );
        employeeDO.status( bean.getStatus() );
        employeeDO.description( bean.getDescription() );
        employeeDO.employeeId( bean.getEmployeeId() );
        employeeDO.email( bean.getEmail() );
        employeeDO.usersId( bean.getUsersId() );

        return employeeDO.build();
    }

    @Override
    public EmployeeDO convert(EmployeeUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        EmployeeDO.EmployeeDOBuilder employeeDO = EmployeeDO.builder();

        employeeDO.id( bean.getId() );
        employeeDO.name( bean.getName() );
        employeeDO.type( bean.getType() );
        employeeDO.status( bean.getStatus() );
        employeeDO.description( bean.getDescription() );
        employeeDO.employeeId( bean.getEmployeeId() );
        employeeDO.email( bean.getEmail() );
        employeeDO.usersId( bean.getUsersId() );

        return employeeDO.build();
    }

    @Override
    public EmployeeRespVO convert(EmployeeDO bean) {
        if ( bean == null ) {
            return null;
        }

        EmployeeRespVO employeeRespVO = new EmployeeRespVO();

        employeeRespVO.setName( bean.getName() );
        employeeRespVO.setType( bean.getType() );
        employeeRespVO.setStatus( bean.getStatus() );
        employeeRespVO.setDescription( bean.getDescription() );
        employeeRespVO.setEmployeeId( bean.getEmployeeId() );
        employeeRespVO.setEmail( bean.getEmail() );
        employeeRespVO.setUsersId( bean.getUsersId() );
        employeeRespVO.setId( bean.getId() );
        employeeRespVO.setCreateTime( bean.getCreateTime() );

        return employeeRespVO;
    }

    @Override
    public List<EmployeeRespVO> convertList(List<EmployeeDO> list) {
        if ( list == null ) {
            return null;
        }

        List<EmployeeRespVO> list1 = new ArrayList<EmployeeRespVO>( list.size() );
        for ( EmployeeDO employeeDO : list ) {
            list1.add( convert( employeeDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<EmployeeRespVO> convertPage(PageResult<EmployeeDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<EmployeeRespVO> pageResult = new PageResult<EmployeeRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<EmployeeExcelVO> convertList02(List<EmployeeDO> list) {
        if ( list == null ) {
            return null;
        }

        List<EmployeeExcelVO> list1 = new ArrayList<EmployeeExcelVO>( list.size() );
        for ( EmployeeDO employeeDO : list ) {
            list1.add( employeeDOToEmployeeExcelVO( employeeDO ) );
        }

        return list1;
    }

    @Override
    public EmployeeDO convert(EmployeeImportExcelVO bean) {
        if ( bean == null ) {
            return null;
        }

        EmployeeDO.EmployeeDOBuilder employeeDO = EmployeeDO.builder();

        employeeDO.name( bean.getName() );
        employeeDO.description( bean.getDescription() );
        employeeDO.employeeId( bean.getEmployeeId() );
        employeeDO.email( bean.getEmail() );

        return employeeDO.build();
    }

    @Override
    public List<EmployeeBasicRespVO> convertList05(List<EmployeeDO> list) {
        if ( list == null ) {
            return null;
        }

        List<EmployeeBasicRespVO> list1 = new ArrayList<EmployeeBasicRespVO>( list.size() );
        for ( EmployeeDO employeeDO : list ) {
            list1.add( convert05( employeeDO ) );
        }

        return list1;
    }

    @Override
    public EmployeeBasicRespVO convert05(EmployeeDO employeeDO) {
        if ( employeeDO == null ) {
            return null;
        }

        EmployeeBasicRespVO employeeBasicRespVO = new EmployeeBasicRespVO();

        employeeBasicRespVO.setType( employeeDO.getType() );
        employeeBasicRespVO.setStatus( employeeDO.getStatus() );
        employeeBasicRespVO.setDescription( employeeDO.getDescription() );
        employeeBasicRespVO.setEmployeeId( employeeDO.getEmployeeId() );
        employeeBasicRespVO.setUsersId( employeeDO.getUsersId() );
        employeeBasicRespVO.setId( employeeDO.getId() );
        employeeBasicRespVO.setName( employeeDO.getName() );
        employeeBasicRespVO.setEmail( employeeDO.getEmail() );

        return employeeBasicRespVO;
    }

    protected EmployeeExcelVO employeeDOToEmployeeExcelVO(EmployeeDO employeeDO) {
        if ( employeeDO == null ) {
            return null;
        }

        EmployeeExcelVO employeeExcelVO = new EmployeeExcelVO();

        employeeExcelVO.setId( employeeDO.getId() );
        employeeExcelVO.setName( employeeDO.getName() );
        employeeExcelVO.setType( employeeDO.getType() );
        employeeExcelVO.setStatus( employeeDO.getStatus() );
        employeeExcelVO.setDescription( employeeDO.getDescription() );
        employeeExcelVO.setEmployeeId( employeeDO.getEmployeeId() );
        employeeExcelVO.setEmail( employeeDO.getEmail() );
        employeeExcelVO.setCreateTime( employeeDO.getCreateTime() );
        employeeExcelVO.setUsersId( employeeDO.getUsersId() );

        return employeeExcelVO;
    }
}
