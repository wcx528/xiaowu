package com.fmyd888.fengmao.module.information.dal.mysql.customer;

import java.util.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.api.customer.dto.customerDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SettleConsignDetailDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SupplierByCustomerNameReqDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.baseline.BaselineDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 客户信息管理
 Mapper
 *
 * @author 丰茂企业
 */
@Mapper
public interface CustomerMapper extends BaseMapperX<CustomerDO> {

    default PageResult<CustomerDO> selectPage(CustomerPageReqVO reqVO) {

        LambdaQueryWrapperX<CustomerDO> queryWrapper = new LambdaQueryWrapperX<>();

        // 模糊条件查询
        queryWrapper.likeIfPresent(CustomerDO::getCustomerCode, reqVO.getCustomerCode())
                .likeIfPresent(CustomerDO::getCustomerName, reqVO.getCustomerName())
                .likeIfPresent(CustomerDO::getContactAddress, reqVO.getContactAddress())
                .likeIfPresent(CustomerDO::getContactPerson, reqVO.getContactPerson())
                .likeIfPresent(CustomerDO::getContactPhone, reqVO.getContactPhone())
                .likeIfPresent(CustomerDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CustomerDO::getCustomerType, reqVO.getCustomerType())
                .eqIfPresent(CustomerDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CustomerDO::getCustomerGroup, reqVO.getCustomerGroup());


        List<String> createTime = reqVO.getCreateTime();
        if (createTime != null && createTime.size() == 2) {
            String startTime = createTime.get(0);
            String endTime = createTime.get(1);
            queryWrapper.between(CustomerDO::getCreateTime, startTime, endTime);
        }

        // 关键字查询
        String keyword = reqVO.getKeyword();
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.and(wrapper -> wrapper
                    .like(CustomerDO::getCustomerCode, keyword)
                    .or().like(CustomerDO::getCustomerName, keyword)
                    .or().like(CustomerDO::getContactAddress, keyword)
                    .or().like(CustomerDO::getContactPerson, keyword)
                    .or().like(CustomerDO::getContactPhone, keyword)
                    .or().like(CustomerDO::getRemark, keyword));
        }

        queryWrapper.orderByDesc(CustomerDO::getId);

        return selectPage(reqVO, queryWrapper);

    }

    default List<CustomerDO> selectList(CustomerExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CustomerDO>()
                .eqIfPresent(CustomerDO::getCustomerCode, reqVO.getCustomerCode())
                .likeIfPresent(CustomerDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(CustomerDO::getCustomerType, reqVO.getCustomerType())
                .eqIfPresent(CustomerDO::getContactPerson, reqVO.getContactPerson())
                .eqIfPresent(CustomerDO::getContactPhone, reqVO.getContactPhone())
                .betweenIfPresent(CustomerDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CustomerDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CustomerDO::getCustomerGroup, reqVO.getCustomerGroup())
                .orderByDesc(CustomerDO::getId));
    }

    /**
     *
     * @param pageReqVO 分页对象
     * @param pageReqVO  关键字分页查询信息
     * @return  关键字分页查询结果
     */
    Page<CustomerDO> selectPageByKeyword(@Param("page") Page<MainVehicleDO> page,
                                        @Param("pageReqVO") CustomerPageReqVO pageReqVO);



//    /**
//     * 模糊查询供应商name接口
//     * @return type=2 (供应商)
//     */
//    List<CustomerToPlanDTO> getCustomerName2(@Param("searchKey") String customerName);



    /**
     * 模糊查询外部公司
     * @return customer_group=1
     */
    List<customerDTO> selectCustomerDetail(@Param("searchKey") String customerName);

    /**
     * 模糊查询外援公司
     * @return is_out=0
     */
    List<customerDTO> selectOutCustomer();

    /**
     * 根据companyId查询客户信息
     * @param companyId 所属公司id
     * @return 客户信息
     */
    default CustomerDO selectCustomerByIdAndCompanyId(Long companyId, Integer customerGroup){
        List<CustomerDO> customerDOList = selectList(new LambdaQueryWrapperX<CustomerDO>()
                .eq(CustomerDO::getMapperingGroup, companyId)
                .eq(CustomerDO::getCustomerGroup, customerGroup));
        return CollectionUtils.isNotEmpty(customerDOList) ? customerDOList.get(0) : null;
    }


    /**
     * 根据传来的subordinateCompaniesId和settleAccountsId查询合同号
     *结算方托运合同
     * @param companyId 所属公司id（供应商对应组织id）
     * @param subordinateCompaniesId       运输公司
     * @return 结算方托运合同
     */
    List<SettleConsignDetailDTO> selectContractNum2(@Param("companyId") Long companyId, @Param("subordinateCompaniesId") List<Long> subordinateCompaniesId);


    /**
     *结算方承运合同
     * @param secondSettleAccountsId 二次结算方id
     * @param settleAccountsId       结算方id
     * @return 结算方承运合同
     */
    List<SettleConsignDetailDTO> selectContractNumBySettleAccounts(@Param("secondSettleAccountsId") Long secondSettleAccountsId, @Param("settleAccountsId") Long settleAccountsId);

    /**
     *二次结算方托运合同
     * @param companyId       所属公司id(二次结算方对应组织的id)
     * @param settleAccountsId       承运公司（结算方对应供应商的id）
     * @return 二次结算方托运合同
     */
    List<SettleConsignDetailDTO> secondSettleContractNum(@Param("companyId") Long companyId, @Param("settleAccountsId") Long settleAccountsId);

    /**
     * 二次结算方承运合同
     * @param companyId 所属公司(二次结算方对应组织的id)
     * @return 二次结算方承运合同
     */
    List<SettleConsignDetailDTO> secondAcceptContractNum(@Param("companyId") Long companyId);

}
