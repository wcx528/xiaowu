package com.fmyd888.fengmao.module.information.dal.mysql.contract;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.controller.admin.contract.vo.ContractExportReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.contract.vo.ContractPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.contract.vo.ContractRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.contract.ContractDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 其他合同资料 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface ContractMapper extends BaseMapperX<ContractDO> {

    //default PageResult<ContractDO> selectPage(ContractPageReqVO reqVO) {
    //    return selectPage(reqVO, new LambdaQueryWrapperX<ContractDO>()
    //            .eqIfPresent(ContractDO::getContractTypeName, reqVO.getContractTypeName())
    //            .eqIfPresent(ContractDO::getPrincipalType, reqVO.getPrincipalType())
    //            .betweenIfPresent(ContractDO::getCreateTime, reqVO.getCreateTime())
    //            .orderByDesc(ContractDO::getId));
    //}

    default List<ContractDO> selectList(ContractExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ContractDO>()
                .eqIfPresent(ContractDO::getCode, reqVO.getContractCoding())
                .eqIfPresent(ContractDO::getContractTypeName, reqVO.getContractTypeName())
                .eqIfPresent(ContractDO::getPrincipalType, reqVO.getPrincipalType())
                .betweenIfPresent(ContractDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ContractDO::getId));
    }

    Page<ContractRespVO> selectByPage(@Param("page") Page<ContractRespVO> page,
                                    @Param("pageReqVO") ContractPageReqVO reqVO);
}
