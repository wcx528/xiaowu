package com.fmyd888.fengmao.module.information.dal.mysql.customer;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/11/22 16:04
 * @Version: 1.0
 * @Description: 部门与部门组织 Mapper
 */
@Mapper
public interface CustomerDeptMapper extends BaseMapperX<CustomerDeptDO> {
    default List<CustomerDeptDO> selectListByCustomerIds(List<Long> companyIds) {
        return selectJoinList(CustomerDeptDO.class, new MPJLambdaWrapper<CustomerDeptDO>()
                .selectAll(CustomerDeptDO.class)
                .selectAs(DeptDO::getName, CustomerDeptDO::getDeptName)
                .leftJoin(DeptDO.class, DeptDO::getId, CustomerDeptDO::getDeptId)
                .disableSubLogicDel());
    }

}
