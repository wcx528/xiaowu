package com.fmyd888.fengmao.module.information.service.customer;

import java.util.*;
import javax.validation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.module.information.api.customer.dto.customerDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.CustomerDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SettleConsignDetailDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SupplierByCustomerNameReqDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SupplierBySettleDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import org.apache.ibatis.annotations.Param;

/**
 * 客户信息管理
 * Service 接口
 *
 * @author 丰茂企业
 */
public interface CustomerService {

    /**
     * 创建客户信息管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomer(@Valid CustomerCreateReqVO createReqVO);

    /**
     * 更新客户信息管理
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomer(@Valid CustomerUpdateReqVO updateReqVO);

    /**
     * 删除客户信息管理
     *
     * @param id 编号
     */
    void deleteCustomer(Long id);

    /**
     * 获得客户信息管理
     *
     * @param id 编号
     * @return 客户信息管理
     */
    CustomerRespVO getCustomer(Long id);

    /**
     * 获得客户信息管理
     * 列表
     *
     * @param ids 编号
     * @return 客户信息管理
     * 列表
     */
    List<CustomerRespVO> getCustomerList(Collection<Long> ids);

    /**
     * 获得所有客户信息
     *
     * @return 客户信息
     */
    List<CustomerRespVO> getCustomerList();

    /**
     * 获得客户信息管理
     * 分页
     *
     * @param pageReqVO 分页查询
     * @return 客户信息管理
     * 分页
     */
    PageResult<CustomerRespVO> getCustomerPage(CustomerPageReqVO pageReqVO);

    /**
     * 获得客户信息管理
     * 分页
     *
     * @param pageReqVO 关键字多条件分页查询
     * @return 客户信息管理
     * 分页
     */
    Page<CustomerRespVO> getCustomerPage02(CustomerPageReqVO pageReqVO);

    /**
     * 获得客户信息管理
     * 列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 客户信息管理
     * 列表
     */
    List<CustomerDO> getCustomerList(CustomerExportReqVO exportReqVO);

    /**
     * 批量更新客户信息管理
     * 列表
     *
     * @param updateReqVOList 批量更新信息列表
     */
    void batchUpdateCustomer(List<CustomerUpdateReqVO> updateReqVOList);

    /**
     * 批量删除客户信息管理
     * 列表
     *
     * @param ids 编号列表
     */
    void batchDeleteCustomer(List<Long> ids);

    /**
     * 批量新增导入客户信息管理
     * 列表
     *
     * @param importReqVOList 批量新增导入信息列表
     */
    void batchImportCustomer(List<CustomerDO> importReqVOList);

    /**
     * 根据客户类型查询精简信息列表
     * @param customerType
     * @return
     */
    List<HashMap<Object, Object>> getSimpleCustomerList(Integer customerType);

    /**
     * 功能描述：获得客户或供应商精简信息（通用接口）
     *
     * @param customerListReqVo
     * @return {@link List }<{@link CustomerDTO }>
     * @author 小逺
     * @date 2024/04/30 21:56
     */
    List<CustomerDTO> getCustomerList(CustomerListReqVo customerListReqVo);



    /**
     * 模糊查询外部公司
     * @return customer_group=1
     */
    List<customerDTO> selectCustomerDetail(String customerName);

    /**
     * 模糊查询外援公司
     * @return is_out=0
     */
    List<customerDTO> selectOutCustomer();

    /**
     * 根据客户名称获取相应的供应商
     * @return 注意：此接口的功能有以下：
     * 1.根据供应商/客户查询：客户供应商的  2.二次结算方托运合同  3.结算方托运合同  4.二次结算方承运合同
     */
    SupplierBySettleDTO selectSupplierByCustomerName(SupplierByCustomerNameReqDTO reqDTO);


    /**
     * 客户信息管理禁用/启用接口
     */
    void updateCustomerStatus(Long id);

    /**
     * 获取绑定地址的客户信息
     */
    List<HashMap<String, Object>> getCustomerIsBindAddress(String customerType);

    /**
     *结算方承运合同
     * @param secondSettleAccountsId 二次结算方id
     * @param settleAccountsId       结算方id
     * @return 结算方承运合同
     */
    List<SettleConsignDetailDTO> selectContractNumBySettleAccounts(@Param("secondSettleAccountsId") Long secondSettleAccountsId, @Param("settleAccountsId") Long settleAccountsId);

}
