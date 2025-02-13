package com.fmyd888.fengmao.module.information.service.container;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.exception.ServiceException;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.common.util.string.StrUtils;
import com.fmyd888.fengmao.framework.datapermission.core.util.DataPermissionUtils;
import com.fmyd888.fengmao.framework.security.core.util.SecurityFrameworkUtils;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesRespVO;
import com.fmyd888.fengmao.module.information.convert.container.ContainerConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.container.ContainerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.container.ContainerDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.container.ContainerMapper;
import com.fmyd888.fengmao.module.information.dal.redis.RedisKeyConstants;
import com.fmyd888.fengmao.module.infra.api.file.DTO.FileDTO;
import com.fmyd888.fengmao.module.infra.api.file.FileApi;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;


/**
 * 集装箱 Service 实现类
 *
 * @author 丰茂超管
 */
@Service
@Validated
public class ContainerServiceImpl implements ContainerService {

    @Resource
    private ContainerMapper containerMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private FileApi fileApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createContainer(ContainerCreateReqVO createReqVO) {
        String containerNumber = createReqVO.getContainerNumber();
        validateContainerExists(null, containerNumber);
        // 插入
        ContainerDO container = BeanUtils.toBean(createReqVO, ContainerDO.class);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        container.setDeptId(loginUserDeptId);
        containerMapper.insert(container);
        //绑定附件
        if (ObjectUtil.isNotEmpty(createReqVO.getFileIds()))
            fileApi.bindAttachment(createReqVO.getFileIds(), container.getId());
        // 返回
        return container.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContainer(ContainerUpdateReqVO updateReqVO) {
        // 校验存在
        Long id = updateReqVO.getId();
        String containerNumber = updateReqVO.getContainerNumber();
        validateContainerExists(id, containerNumber);
        // 更新
        ContainerDO updateObj = BeanUtils.toBean(updateReqVO, ContainerDO.class);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        updateObj.setDeptId(loginUserDeptId);
        //更新绑定附件
        List<FileDTO> dbFiles = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(FileEnums.CONTAINER.getCodeBusinessType(), updateObj.getId());
        List<Long> dbIds = dbFiles.stream().map(FileDTO::getId).collect(Collectors.toList());
        List<List<Long>> lists = CollectionUtils.diffList(dbIds, updateReqVO.getFileIds(), Objects::equals);

        if (lists.get(0).size() > 0)
            fileApi.bindAttachment(lists.get(0), updateReqVO.getId());
        if (lists.get(2).size() > 0)
            fileApi.unBindAttachment(lists.get(2));

        containerMapper.updateById(updateObj);
    }

    @Override
    public void deleteContainer(Long id) {
        // 删除
        containerMapper.deleteById(id);
    }

    /**
     * @param id
     * @param containerNumber
     */
    private void validateContainerExists(Long id, String containerNumber) {
        LambdaQueryWrapper<ContainerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(id != null, ContainerDO::getId, id);
        queryWrapper.eq(ContainerDO::getContainerNumber, containerNumber);
        boolean exists = containerMapper.exists(queryWrapper);
        if (exists) {
            throw exception(DUPLICATE_CONTAINER_NUNMBER, containerNumber);
        }
    }

    @Override
    public ContainerRespVO getContainer(Long id) {
        ContainerRespVO bean = BeanUtils.toBean(containerMapper.selectById(id), ContainerRespVO.class);
        List<FileDTO> fileInfo = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(FileEnums.CONTAINER.getCodeBusinessType(), id);
        bean.setFileList(fileInfo);
        return bean;
    }

    @Override
    public List<ContainerDO> getContainerList(Collection<Long> ids) {
        return containerMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ContainerRespVO> getContainerPage(ContainerPageReqVO pageReqVO) {
        return BeanUtils.toBean(containerMapper.selectJoinTilePage(pageReqVO), ContainerRespVO.class);
    }

    @Override
    public List<ContainerDO> getContainerList(ContainerExportReqVO exportReqVO) {
        return containerMapper.selectJoinList(exportReqVO);
    }

    @Override
    public void batchUpdateContainer(List<ContainerUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (ContainerUpdateReqVO updateReqVO : updateReqVOList) {
            updateContainer(updateReqVO);
        }
    }

    @Override
    public void batchDeleteContainer(List<Long> ids) {
        // 在这里处理批量删除逻辑
        containerMapper.deleteBatchIds(ids);
    }

    @Override
    public void batchImportContainer(List<ContainerDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        containerMapper.insertBatch(importReqVOList);
    }

    @Override
    public void updateContainerFileUrl(Long id, String fileUrl) {
        UpdateWrapper<ContainerDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("file_url", fileUrl);
        containerMapper.update(null, updateWrapper);
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.CONTAINER_SIMPLE_LIST + "#120", key = "#param.status+'_'+#param.searchKey+'_'+#param.id+'_'+#param.companyIds")
    public List<HashMap<String, Object>> getSimpleContainerList(CommonQueryParam param) {
        if (ObjectUtil.isEmpty(param.getStatus()))
            param.setStatus(0);
        List<HashMap<String, Object>> list = new ArrayList<>();
        MPJLambdaWrapper<ContainerDO> queryWrapper = new MPJLambdaWrapper<ContainerDO>()
                .select(ContainerDO::getId, ContainerDO::getContainerNumber)
                .leftJoin(ContainerDeptDO.class, ContainerDeptDO::getEntityId, ContainerDO::getId)
                .eq(ContainerDO::getStatus, param.getStatus())
                .eq(ObjectUtil.isNotEmpty(param.getId()), ContainerDO::getId, param.getId())
                .in(ArrayUtil.isNotEmpty(param.getCompanyIds()), ContainerDeptDO::getDeptId, param.getCompanyIds())
                .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(ContainerDO::getContainerNumber, param.getSearchKey()))
                .groupBy(ContainerDO::getId, ContainerDO::getContainerNumber)
                .disableSubLogicDel();

        List<ContainerDO> ContainerDOS = containerMapper.selectJoinList(ContainerDO.class, queryWrapper);
        ContainerDOS.forEach(iterm -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("name", iterm.getContainerNumber());
            list.add(map);
        });
        return list;
    }

    @Override
    public void uploadContainer(ContainerUploadReqVO uploadReqVO) {
        long count = containerMapper.selectCount(ContainerDO::getId, uploadReqVO.getId());
        if (count == 0)
            throw exception(CONTAINER_NOT_EXISTS1);
        //更新绑定附件
        List<FileDTO> dbFiles = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(FileEnums.CONTAINER.getCodeBusinessType(), uploadReqVO.getId());
        List<Long> dbIds = dbFiles.stream().map(FileDTO::getId).collect(Collectors.toList());
        List<List<Long>> lists = CollectionUtils.diffList(dbIds, uploadReqVO.getFileIds(), Objects::equals);

        if (lists.get(0).size() > 0)
            fileApi.bindAttachment(lists.get(0), uploadReqVO.getId());
        if (lists.get(2).size() > 0)
            fileApi.unBindAttachment(lists.get(2));
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public ContainerImportRespVO importContainerList(List<ContainerImportExcelVO> importContainer, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importContainer)) {
            throw new RuntimeException("导入集装箱数据不能为空");
        }
        ContainerImportRespVO respVO = ContainerImportRespVO.builder().createContainerNumbers(new ArrayList<>())
                .updateContainerNumbers(new ArrayList<>())
                .failureContainerNumbers(new LinkedHashMap<>()).build();
        //获得创建者和更新者信息
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        importContainer.forEach(iterm -> {
            // 校验，判断是否有不符合的原因
            try {
                validateContainerForCreateOrUpdate(iterm);
            } catch (ServiceException ex) {
                respVO.getFailureContainerNumbers().put(iterm.getContainerNumber(), ex.getMessage());
                return;
            }
            String deptName = iterm.getDeptName();
            DeptDO deptDO = deptMapper.selectOne(DeptDO::getName, deptName);
            // 1、根据集装箱名称查询判断如果不存在，在进行插入返回
            ContainerDO containerDO = containerMapper.selectOne(ContainerDO::getContainerNumber, iterm.getContainerNumber());
            if (ObjectUtils.isEmpty(containerDO)) {
                ContainerDO containerDO1 = ContainerConvert.INSTANCE.convert(iterm);
                //1.1获得对应的组织id填充
                //if(!ObjectUtils.isEmpty(deptDO)){
                //    containerDO1.setDeptId(deptDO.getId());
                //}else{
                //    throw new RuntimeException("找不到名为："+deptName+"的公司,请检查！");
                //}
                //1.3插入用户
                containerMapper.insert(containerDO1);
                respVO.getCreateContainerNumbers().add(iterm.getContainerNumber());
                return;
            }
            //2、如果集装箱名称存在，判断是否允许更新返回
            if (!isUpdateSupport) {
                respVO.getFailureContainerNumbers().put(iterm.getContainerNumber(), "集装箱名称已经存在！");
                return;
            }
            // 3、集装箱名称存在，进行更新返回
            ContainerDO convert = ContainerConvert.INSTANCE.convert(iterm);
            convert.setId(containerDO.getId());
            //1.1获得对应的组织id填充
            //if(!ObjectUtils.isEmpty(deptDO)){
            //    convert.setDeptId(deptDO.getId());
            //}else{
            //    throw new RuntimeException("找不到名为："+deptName+"的公司,请检查！");
            //}
            containerMapper.updateById(convert);
            respVO.getUpdateContainerNumbers().add(iterm.getContainerNumber());

        });
        return respVO;
    }

    private void validateContainerForCreateOrUpdate(ContainerImportExcelVO excelVO) {
        // 关闭数据权限，避免因为没有数据权限，查询不到数据，进而导致唯一校验不正确
        DataPermissionUtils.executeIgnore(() -> {
            String containerNumber = excelVO.getContainerNumber();
            ContainerDO containerDO = containerMapper.selectOne(ContainerDO::getContainerNumber, containerNumber);
            if (!ObjectUtils.isEmpty(containerDO)) {
                throw new RuntimeException("集装箱号:" + containerNumber + "已存在！");
            }
        });
    }
}
