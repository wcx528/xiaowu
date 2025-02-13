package com.fmyd888.fengmao.module.information.service.contract;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.module.information.controller.admin.contract.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.contract.ContractDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 其他合同资料 Service 接口
 *
 * @author 丰茂
 */
public interface ContractService {

    /**
     * 创建其他合同资料
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createContract(@Valid ContractCreateReqVO createReqVO);

    /**
     * 更新其他合同资料
     *
     * @param updateReqVO 更新信息
     */
    void updateContract(@Valid ContractUpdateReqVO updateReqVO);

    /**
     * 删除其他合同资料
     *
     * @param id 编号
     */
    void deleteContract(Long id);

    /**
     * 获得其他合同资料
     *
     * @param id 编号
     * @return 其他合同资料
     */
    ContractRespVO getContract(Long id);

    /**
     * 获得其他合同资料列表
     *
     * @param ids 编号
     * @return 其他合同资料列表
     */
    List<ContractRespVO> getContractList(Collection<Long> ids);

    /**
     * 获得其他合同资料分页
     *
     * @param pageReqVO 分页查询
     * @return 其他合同资料分页
     */
    Page<ContractRespVO> getContractPage(ContractPageReqVO pageReqVO);

    /**
     * 获得其他合同资料列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 其他合同资料列表
     */
    List<ContractDO> getContractList(ContractExportReqVO exportReqVO);

    /**
    * 批量更新其他合同资料列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateContract(List<ContractUpdateReqVO> updateReqVOList);

    /**
    * 批量删除其他合同资料列表
    *
    * @param ids 编号列表
    */
    void batchDeleteContract(List<Long> ids);

    /**
    * 批量新增导入其他合同资料列表
    *
    * @param importReqVOList 批量新增导入信息列表
    */
    void batchImportContract(List<ContractDO> importReqVOList);

    /**
     * 将合同资料分配到组织下
     * @param contractDeptReqVO
     */
    void assignContractToDept(ContractDeptReqVO contractDeptReqVO);

    /**
     * 根据我方单位ID查询合同资料列表的合同类型
     * @param companyId
     * @return
     */
    List<HashMap<String,Object>> selectDetailsByDeptId(Long companyId);

    /**
     * 根据合同类型id查询我方主体类型
     */
    HashMap<String,Object> selectTypeByContractTypeId(Long contractDataId);

    /**
     * 查询其他合同类型列表
     */
    List<String> getOtherContractTypeList();
}
