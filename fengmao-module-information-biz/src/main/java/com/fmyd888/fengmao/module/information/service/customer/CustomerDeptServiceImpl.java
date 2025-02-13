package com.fmyd888.fengmao.module.information.service.customer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fmyd888.fengmao.framework.security.core.LoginUser;
import com.fmyd888.fengmao.framework.security.core.util.SecurityFrameworkUtils;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerDeptReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerDeptMapper;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.github.yulichang.method.SelectJoinList;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @Author: lmy
 * @Date: 2023/11/22 16:14
 * @Version: 1.0
 * @Description:
 */
@Service
public class CustomerDeptServiceImpl implements CustomerDeptService {
    @Resource
    private CustomerDeptMapper customerDeptMapper;

    @Override
    public List<CustomerDeptDO> getCustomerDeptByCustomerId(Long customerId) {
        QueryWrapper<CustomerDeptDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", customerId);
        List<CustomerDeptDO> list = customerDeptMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public List<CustomerDeptDO> getCustomerDeptByDeptId(List<Long> companyIds) {
        QueryWrapper<CustomerDeptDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("dept_id", companyIds);
        List<CustomerDeptDO> list = customerDeptMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public List<CustomerDeptDO> getCustomerDeptByCustomerIds(List<Long> customerIds) {
        LambdaQueryWrapper<CustomerDeptDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CustomerDeptDO::getCustomerId, customerIds);
        return customerDeptMapper.selectList(queryWrapper);
    }

    @Override
    public List<CustomerDeptDO> getCustomerDeptByCustomerIds1(List<Long> customerIds) {
        return customerDeptMapper.selectListByCustomerIds(customerIds);
    }

    @Override
    public void saveCustomerDept(Long customerId, Set<Long> deptSets) {
        //新增客户和组织的关联
        deptSets.forEach(iterm -> {
            CustomerDeptDO customerDeptDO = new CustomerDeptDO();
            customerDeptDO.setCustomerId(customerId);
            customerDeptDO.setDeptId(iterm);
            customerDeptMapper.insert(customerDeptDO);
        });
    }

    @Override
    public void updateCustomerDept(CustomerDeptReqVO updateReqVO) {
        Long id = updateReqVO.getCustomerId();
        Set<Long> organization = updateReqVO.getDeptIds();
        //1 删除原来所属组织
        QueryWrapper<CustomerDeptDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", id);
        customerDeptMapper.delete(queryWrapper);
        //2 添加新的所属组织
        organization.forEach(deptId -> {
            CustomerDeptDO customerDeptDO = new CustomerDeptDO();
            customerDeptDO.setCustomerId(id);
            customerDeptDO.setDeptId(deptId);
            customerDeptMapper.insert(customerDeptDO);
        });
    }

    @Override
    public void assignCustomerToDept(CustomerDeptReqVO updateReqVO) {
        Long id = updateReqVO.getCustomerId();
        Set<Long> organization = updateReqVO.getDeptIds();
        //1 删除原来所属组织
        QueryWrapper<CustomerDeptDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", id);
        customerDeptMapper.delete(queryWrapper);
        //2 添加新的所属组织
        organization.forEach(deptId -> {
            CustomerDeptDO customerDeptDO = new CustomerDeptDO();
            customerDeptDO.setCustomerId(id);
            customerDeptDO.setDeptId(deptId);
            customerDeptMapper.insert(customerDeptDO);
        });
    }
}
