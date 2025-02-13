package com.fmyd888.fengmao.module.information.service.repairprojects;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.StatementTemplateDO;
import com.fmyd888.fengmao.module.infra.api.file.DTO.FileDTO;
import com.fmyd888.fengmao.module.infra.api.file.FileApi;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import com.fmyd888.fengmao.module.infra.dal.mysql.file.FileMapper;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.repairprojects.RepairProjectsDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import com.fmyd888.fengmao.module.information.dal.mysql.repairprojects.RepairProjectsMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 维修项目 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class RepairProjectsServiceImpl implements RepairProjectsService {

    @Resource
    private RepairProjectsMapper repairProjectsMapper;
    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private FileApi fielApi;

    @Resource
    private FileApi fileApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRepairProjects(RepairProjectsSaveReqVO createReqVO) {
        // 插入
        RepairProjectsDO repairProjects = BeanUtils.toBean(createReqVO, RepairProjectsDO.class);

        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        repairProjects.setDeptId(loginUserDeptId);
        repairProjects.setCreateTime(LocalDateTime.now());
        repairProjects.setUpdateTime(LocalDateTime.now());
        repairProjectsMapper.insert(repairProjects);

        List<Long> fileId = repairProjects.getFileIds();
        if (ObjectUtil.isNotEmpty(fileId)) fileApi.bindAttachment(fileId, repairProjects.getId());

        // 返回
        return repairProjects.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRepairProjects(RepairProjectsSaveReqVO updateReqVO) {
        // 校验存在
        validateRepairProjectsExists(updateReqVO.getId());
        // 更新
        RepairProjectsDO updateObj = BeanUtils.toBean(updateReqVO, RepairProjectsDO.class);

        //更新绑定图片
        List<FileDTO> dbFiles = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(FileEnums.MATERIAL_IMAGE.getCodeBusinessType(), updateObj.getId());
        List<Long> dbIds = dbFiles.stream().map(FileDTO::getId).collect(Collectors.toList());
        List<List<Long>> lists = com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils.diffList(dbIds, updateReqVO.getFileIds(), Objects::equals);

        if (lists.get(0).size() > 0) fileApi.bindAttachment(lists.get(0), updateReqVO.getId());
        if (lists.get(2).size() > 0) fileApi.unBindAttachment(lists.get(2));
        repairProjectsMapper.updateById(updateObj);
    }

    @Override
    public void deleteRepairProjects(Long id) {
        // 校验存在
        validateRepairProjectsExists(id);
        // 删除
        repairProjectsMapper.deleteById(id);
    }

    private void validateRepairProjectsExists(Long id) {
        if (repairProjectsMapper.selectById(id) == null) {
            throw exception(REPAIR_PROJECTS_NOT_EXISTS);
        }
    }


    @Override
    public RepairProjectsDO getRepairProjects(Long id) {
        RepairProjectsDO repairProjectsDO = repairProjectsMapper.selectById(id);
        String materialImageCodeBusinessType = FileEnums.MATERIAL_IMAGE.getCodeBusinessType();
        if (repairProjectsDO.getId() != null) {
            List<FileDTO> fileUrl = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(materialImageCodeBusinessType, repairProjectsDO.getId());
            // 填充文件信息
            List<Map<String, Object>> maps = fillFileResInfo(fileUrl);
            repairProjectsDO.setFileId(maps);
        }
        return repairProjectsDO;
    }


    /**
     * 填充文件资源信息
     *
     * @param fileList 文件列表
     * @return 填充了文件资源信息的列表
     */
    private List<Map<String, Object>> fillFileResInfo(List<FileDTO> fileList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fileList)) {
            fileList.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("name", item.getName());
                map.put("url", item.getUrl());
                list.add(map);
            });
        }
        return list;
    }

    @Override
    public List<RepairProjectsDO> getRepairProjectsList(Collection<Long> ids) {
        return repairProjectsMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<RepairProjectsDO> getRepairProjectsPage(RepairProjectsPageReqVO pageReqVO) {
        PageResult<RepairProjectsDO> pageResult = repairProjectsMapper.selectJoinTilePage(pageReqVO);
        if (!pageResult.getList().isEmpty()) {
            List<Long> entityIds = pageResult.getList().stream().map(RepairProjectsDO::getId).collect(Collectors.toList());
            List<FileDTO> fileInfos = fileApi.getFileUrlByCodeBusinessTypeAndSourceIds(FileEnums.MATERIAL_IMAGE.getCodeBusinessType(), entityIds);
            pageResult.getList().forEach(item->{
                List<FileDTO> files = fileInfos.stream().filter(p -> ObjectUtil.equals(p.getSourceId(), item.getId())).collect(Collectors.toList());
                if(ObjectUtil.isNotEmpty(files)) {
                    List<Map<String, Object>> maps = fillFileResInfo(files);
                    item.setFileId(maps);
                }
            });
        }

        return pageResult;
    }

    @Override
    public List<RepairProjectsDO> getRepairProjectsList(RepairProjectsListReqVO listReqVO) {
        return repairProjectsMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateRepairProjects(List<RepairProjectsSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteRepairProjects(List<Long> ids) {
        // 在这里处理批量删除逻辑
        repairProjectsMapper.deleteBatchIds(ids);
    }

    @Override
    public List<RepairProjectsExcelVO> importPreviewList(List<RepairProjectsExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(REPAIR_PROJECTS_IMPORT_LIST_IS_EMPTY);
        }

        List<RepairProjectsExcelVO> excelVo = BeanUtils.toBean(importDatas, RepairProjectsExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(REPAIR_PROJECTS_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(RepairProjectsExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0) throw exception(REPAIR_PROJECTS_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(REPAIR_PROJECTS_IMPORT_PORT_REQUIRE);
        //以下是示例，补充逻辑时请替换成自己书写的逻辑
        //ImportResult result = ImportResult.builder()
        //.total(importReqVo.getImportDatas().size())
        //.importCount(0)
        //.failCount(importReqVo.getImportDatas().size())//假设这里假设都导入失败
        //.success(false)
        //.data(importReqVo.getImportDatas())
        //.build();
        //return result;
    }

    @Override
    public List<RepairProjectsDO> getRepairProjectsAllList(RepairProjectBaseReqVO reqVO) {
        return repairProjectsMapper.selecAllList(reqVO);
    }

    @Override
    public List<RepairProjectsDO> getRepairProjectListByNames(List<String> names) {
        return repairProjectsMapper.selectByNames(names);
    }

}