package com.fmyd888.fengmao.module.information.service.salesman;

import java.util.*;
import javax.validation.*;

import com.fmyd888.fengmao.module.information.controller.admin.employee.vo.EmployeeImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.salesman.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;

/**
 * 业务员表  Service 接口
 *
 * @author 丰茂
 */
public interface SalesmanService {

    /**
     * 创建业务员表 
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSalesman(@Valid SalesmanCreateReqVO createReqVO);

    /**
     * 更新业务员表 
     *
     * @param updateReqVO 更新信息
     */
    void updateSalesman(@Valid SalesmanUpdateReqVO updateReqVO);

    /**
     * 删除业务员表 
     *
     * @param id 编号
     */
    void deleteSalesman(Long id);

    /**
     * 获得业务员表 
     *
     * @param id 编号
     * @return 业务员表 
     */
    SalesmanRespVO getSalesman(Long id);

    /**
     * 获得业务员表 列表
     *
     * @param ids 编号
     * @return 业务员表 列表
     */
    List<SalesmanRespVO> getSalesmanList(Collection<Long> ids);

    /**
     * 获得业务员表 分页
     *
     * @param pageReqVO 分页查询
     * @return 业务员表 分页
     */
    PageResult<SalesmanRespVO> getSalesmanPage(SalesmanPageReqVO pageReqVO);

    /**
     * 获得业务员表 列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 业务员表 列表
     */
    List<SalesmanExcelVO> getSalesmanList(SalesmanExportReqVO exportReqVO);

    /**
    * 批量更新业务员表 列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateSalesman(List<SalesmanUpdateReqVO> updateReqVOList);

    /**
    * 批量删除业务员表 列表
    *
    * @param ids 编号列表
    */
    void batchDeleteSalesman(List<Long> ids);

    /**
    * 导入业务员表 列表
    *
    * @param importReqVOList 批量新增导入信息列表
    */
   SalesmanImportRespVO importSalesmanList(List<SalesmanImportExcelVO> importReqVOList, boolean isUpdateSupport);

    /**
     * 获得业务员名称
     */
   List<SalesmanDO> getNameListById();
    /**
     * 修改业务员状态
     */
    void updateStatus(Long id);

    /**
     * 获得业务员所属的组织
     */
    List<DeptSimpleRespVO> getDeptInfoList(Long salesmanId);


    /**
     * 分配的组织货物和组织信息对象
     */
    void assignSalesmanToDept(SalesmanDeptReqVO salesmanDeptReqVO);

    /**
     * 业务员精简列表获得
     * @return
     */
    List<HashMap<String,Object>> getSimpleSalesmanList(String searchKey, Long id, Integer status, Integer[] type,Long[] companyIds);


    List<HashMap<String, Object>> getExcludeUserList();

}
