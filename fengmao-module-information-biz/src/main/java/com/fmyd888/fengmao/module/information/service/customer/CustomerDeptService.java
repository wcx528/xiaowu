package com.fmyd888.fengmao.module.information.service.customer;

import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;

import java.util.List;
import java.util.Set;

/**
 * @Author: lmy
 * @Date: 2023/11/22 16:13
 * @Version: 1.0
 * @Description:
 */

public interface CustomerDeptService {

    /**
     * 根据客户id查询已分配的部门组织列表
     *
     * @param customerId
     * @return
     */
    List<CustomerDeptDO> getCustomerDeptByCustomerId(Long customerId);

    /**
     * 功能描述：根据所属公司ID获取客户部门表信息
     *
     * @param deptId
     * @return {@link List }<{@link CustomerDeptDO }>
     * @author 小逺
     * @date 2024/04/30
     */
    List<CustomerDeptDO> getCustomerDeptByDeptId(List<Long> companyIds);

    /**
     * 功能描述： 根据客户id查询已分配的部门组织列表
     *
     * @param customerIds
     * @return {@link List }<{@link CustomerDeptDO }>
     * @author 小逺
     * @date 2024/06/26
     */
    List<CustomerDeptDO> getCustomerDeptByCustomerIds(List<Long> customerIds);

    /**
     * 功能描述：根据客户id查询已分配的部门组织列表（有部门名称）
     *
     * @param customerIds
     * @return {@link List }<{@link CustomerDeptDO }>
     * @author 小逺
     * @date 2024/09/20
     */
    List<CustomerDeptDO> getCustomerDeptByCustomerIds1(List<Long> customerIds);

    /**
     * 客户关联组织表记录
     *
     * @param customerId 客户id
     * @param deptSets   分配的组织集合ids
     */
    void saveCustomerDept(Long customerId, Set<Long> deptSets);

    /**
     * 更新客户及其关联组织表
     *
     * @param updateReqVO
     */
    void updateCustomerDept(CustomerDeptReqVO updateReqVO);

    /**
     * @param updateReqVO
     */
    void assignCustomerToDept(CustomerDeptReqVO updateReqVO);
}
