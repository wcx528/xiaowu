package com.fmyd888.fengmao.module.information.service.importLogs;

import java.util.*;
import javax.validation.*;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.importLogs.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.importLogs.ImportLogDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;

/**
 * 导入日志 Service 接口
 *
 * @author 小逺
 */
public interface ImportLogService {

    /**
     * 创建导入日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createImportLog(@Valid ImportLogSaveReqVO createReqVO);

    /**
     * 更新导入日志
     *
     * @param updateReqVO 更新信息
     */
    void updateImportLog(@Valid ImportLogSaveReqVO updateReqVO);

    /**
     * 删除导入日志
     *
     * @param id 编号
     */
    void deleteImportLog(Long id);

    /**
     * 获得导入日志
     *
     * @param id 编号
     * @return 导入日志
     */
    ImportLogDO getImportLog(Long id);

    /**
     * 获得导入日志列表
     *
     * @param ids 编号
     * @return 导入日志列表
     */
    List<ImportLogDO> getImportLogList(Collection<Long> ids);

    /**
    * 获得导入日志分页
    *
    * @param pageReqVO 分页查询
    * @return 导入日志分页
    */
    PageResult<ImportLogDO> getImportLogPage(ImportLogPageReqVO pageReqVO);


    /**
     * 获得导入日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 导入日志列表
     */
    List<ImportLogDO> getImportLogList(ImportLogListReqVO exportReqVO);
}