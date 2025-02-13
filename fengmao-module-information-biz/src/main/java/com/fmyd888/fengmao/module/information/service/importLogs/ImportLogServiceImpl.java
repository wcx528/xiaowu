package com.fmyd888.fengmao.module.information.service.importLogs;

import cn.hutool.core.collection.CollUtil;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiFunction;
import com.fmyd888.fengmao.module.information.controller.admin.importLogs.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.importLogs.ImportLogDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import com.fmyd888.fengmao.module.information.dal.mysql.importLogs.ImportLogMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 导入日志 Service 实现类
 *
 * @author 小逺
 */
@Service
@Validated
public class ImportLogServiceImpl implements ImportLogService {

    @Resource
    private ImportLogMapper importLogMapper;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Long createImportLog(ImportLogSaveReqVO createReqVO) {
        // 插入
        ImportLogDO importLog = BeanUtils.toBean(createReqVO, ImportLogDO.class);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        importLog.setDeptId(loginUserDeptId);
        importLogMapper.insert(importLog);

    
        // 返回
        return importLog.getId();
    }

    @Override
    public void updateImportLog(ImportLogSaveReqVO updateReqVO) {
        // 校验存在
        validateImportLogExists(updateReqVO.getId());
        // 更新
        ImportLogDO updateObj = BeanUtils.toBean(updateReqVO, ImportLogDO.class);
        importLogMapper.updateById(updateObj);

        }

    @Override
    public void deleteImportLog(Long id) {
        // 校验存在
        validateImportLogExists(id);
        // 删除
        importLogMapper.deleteById(id);
    }

    private void validateImportLogExists(Long id) {
        if (importLogMapper.selectById(id) == null) {
        throw exception(IMPORT_LOG_NOT_EXISTS);
        }
    }


    @Override
    public ImportLogDO getImportLog(Long id) {
        return importLogMapper.selectById(id);
    }

    @Override
    public List<ImportLogDO> getImportLogList(Collection<Long> ids) {
        return importLogMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ImportLogDO> getImportLogPage(ImportLogPageReqVO pageReqVO) {
        return importLogMapper.selectJoinTilePage(pageReqVO);
    }

    @Override
    public List<ImportLogDO> getImportLogList(ImportLogListReqVO listReqVO) {
        return importLogMapper.selectList(listReqVO);
    }

}