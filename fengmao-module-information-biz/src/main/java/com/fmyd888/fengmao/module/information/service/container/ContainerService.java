package com.fmyd888.fengmao.module.information.service.container;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.container.ContainerDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 集装箱 Service 接口
 *
 * @author 丰茂超管
 */
public interface ContainerService {

    /**
     * 创建集装箱
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createContainer(@Valid ContainerCreateReqVO createReqVO);

    /**
     * 更新集装箱
     *
     * @param updateReqVO 更新信息
     */
    void updateContainer(@Valid ContainerUpdateReqVO updateReqVO);

    /**
     * 删除集装箱
     *
     * @param id 编号
     */
    void deleteContainer(Long id);

    /**
     * 获得集装箱
     *
     * @param id 编号
     * @return 集装箱
     */
    ContainerRespVO getContainer(Long id);

    /**
     * 获得集装箱列表
     *
     * @param ids 编号
     * @return 集装箱列表
     */
    List<ContainerDO> getContainerList(Collection<Long> ids);

    /**
     * 获得集装箱分页
     *
     * @param pageReqVO 分页查询
     * @return 集装箱分页
     */
    PageResult<ContainerRespVO> getContainerPage(ContainerPageReqVO pageReqVO);

    /**
     * 获得集装箱列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 集装箱列表
     */
    List<ContainerDO> getContainerList(ContainerExportReqVO exportReqVO);

    /**
    * 批量更新集装箱列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateContainer(List<ContainerUpdateReqVO> updateReqVOList);

    /**
    * 批量删除集装箱列表
    *
    * @param ids 编号列表
    */
    void batchDeleteContainer(List<Long> ids);

    /**
    * 批量新增导入集装箱列表
    *
    * @param importReqVOList 批量新增导入信息列表
    */
    void batchImportContainer(List<ContainerDO> importReqVOList);

    /**
     *
     * @param id  集装箱id
     * @param fileUrl 更新集装箱的附件地址记录字段
     */
    void updateContainerFileUrl(Long id, String fileUrl);
    
    /**
     * 批量导入集装箱
     *
     * @param importContainer     导入集装箱列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    ContainerImportRespVO importContainerList(List<ContainerImportExcelVO> importContainer, boolean isUpdateSupport);

    /**
     * 功能描述：获得精简集装箱信息
     *
     * @param param 参数
     * @return {@link List }<{@link HashMap }<{@link String }, {@link Object }>>
     * @author 小逺
     * @date 2024/04/30 23:51
     */
    List<HashMap<String, Object>> getSimpleContainerList(CommonQueryParam param);

    /**
     * 功能描述：
     *
     * @param uploadReqVO
     * @author 小逺
     * @date 2024/07/06 14:14
     */
    void uploadContainer(ContainerUploadReqVO uploadReqVO);
}
