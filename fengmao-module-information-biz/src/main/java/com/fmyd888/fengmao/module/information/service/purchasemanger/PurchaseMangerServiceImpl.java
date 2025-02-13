package com.fmyd888.fengmao.module.information.service.purchasemanger;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.*;
import com.fmyd888.fengmao.module.information.convert.purchaseManger.PurchaseMangerConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.purchasemanger.PurchaseMangerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.purchasemanger.PurchaseMangerMapper;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.infra.service.file.FileService;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.PURCHASE_MANGER_EXISTS;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.PURCHASE_MANGER_NOT_EXISTS;

/**
 * 购买证管理 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class PurchaseMangerServiceImpl implements PurchaseMangerService {

    @Resource
    private PurchaseMangerMapper purchaseMangerMapper;
    @Resource
    private DeptMapper deptMapper;

    @Resource
    private FileService fileService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPurchaseManger(PurchaseMangerSaveReqVO createReqVO) {
        PurchaseMangerDO convert = PurchaseMangerConvert.INSTANCE.convert(createReqVO);
        List<PurchaseMangerDO> allPurchaseMangerDO = purchaseMangerMapper.selectList();
        for (PurchaseMangerDO purchaseMangerDO : allPurchaseMangerDO) {
            //购买证类型
            Byte type = convert.getType();
            //购买证编号
            String purchaseCode = convert.getPurchaseCode();
            if (type.equals(purchaseMangerDO.getType()) && purchaseCode.equals(purchaseMangerDO.getPurchaseCode())) {
                throw exception(PURCHASE_MANGER_EXISTS, (type == 1 ? "上游" : type == 2 ? "下游" : "其他"), purchaseCode);
            }
        }
        // 插入
        purchaseMangerMapper.insert(convert);
        //文件记录中符合条件的记录的源ID字段更新为转换后的对象的ID
        List<Long> fileIds = createReqVO.getFileIds();
        String codeBusinessType = FileEnums.PURCHASE_MANGER_FILE_EUM.getCodeBusinessType();
        fileService.insertFileBandingSourceId(fileIds, codeBusinessType, convert.getId());

        // 返回
        return convert.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePurchaseManger(PurchaseMangerSaveReqVO updateReqVO) {
        // 校验存在
        Long id = updateReqVO.getId();
        validatePurchaseMangerExists(id);
        // 更新
        PurchaseMangerDO updateObj = BeanUtils.toBean(updateReqVO, PurchaseMangerDO.class);
        List<Long> fileIds = updateReqVO.getFileIds();
        //更新文件信息的操作
        String codeBusinessType = FileEnums.PURCHASE_MANGER_FILE_EUM.getCodeBusinessType();
        fileService.updateFileBandingSourceId(fileIds, codeBusinessType, id);
        purchaseMangerMapper.updateById(updateObj);

    }

    @Override
    public void deletePurchaseManger(Long id) {
        // 校验存在
        validatePurchaseMangerExists(id);
        // 删除
        purchaseMangerMapper.deleteById(id);
    }

    private void validatePurchaseMangerExists(Long id) {
        if (purchaseMangerMapper.selectById(id) == null) {
            throw exception(PURCHASE_MANGER_NOT_EXISTS);
        }
    }


    @Override
    public PurchaseMangerRespVO getPurchaseManger(Long id) {
        PurchaseMangerDO purchaseMangerDO = purchaseMangerMapper.selectById(id);
        PurchaseMangerRespVO purchaseMangerDOResult = PurchaseMangerConvert.INSTANCE.convert(purchaseMangerDO);
        String businessType = FileEnums.PURCHASE_MANGER_FILE_EUM.getCodeBusinessType();
        //文件
        List<FileDO> fileList = fileService.getFileUrlByCodeBusinessTypeAndSourceId(businessType, id);
        List<Map<String, Object>> maps = fillFileResInfo(fileList);
        purchaseMangerDOResult.setFileInfos(maps);
        List<String> files = fileList.stream().map(FileDO::getUrl).collect(Collectors.toList());
        purchaseMangerDOResult.setFiles(files);
        return purchaseMangerDOResult;
    }

    /**
     * 填充文件资源信息
     *
     * @param fileList 文件列表
     * @return 填充了文件资源信息的列表
     */
    private List<Map<String, Object>> fillFileResInfo(List<FileDO> fileList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (!CollectionUtils.isAnyEmpty(fileList)) {
            fileList.stream().forEach(iterm -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", iterm.getId());
                map.put("name", iterm.getName());
                map.put("url", iterm.getUrl());
                list.add(map);
            });
        }
        return list;
    }


    @Override
    public List<PurchaseMangerDO> getPurchaseMangerList(Collection<Long> ids) {
        return purchaseMangerMapper.selectBatchIds(ids);
    }


    @Override
    public List<PurchaseMangerDO> getPurchaseMangerList(PurchaseMangerListReqVO listReqVO) {
        return purchaseMangerMapper.selectList(listReqVO);
    }


    @Override
    public CardPage<PurchaseMangerRespVO> selectPageByKeyword(PurchaseMangerKeywordPageReqVO pageReqVO) {
        CardPage<PurchaseMangerDO> page = new CardPage<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        pageReqVO.setOrderByColumn("create_time");
        CardPage<PurchaseMangerRespVO> pageResultVO = purchaseMangerMapper.selectPageByKeyword(page, pageReqVO);
        setCardInfo(pageResultVO);
        String businessType = FileEnums.PURCHASE_MANGER_FILE_EUM.getCodeBusinessType();
        for (PurchaseMangerRespVO iterm : pageResultVO.getRecords()) {
            List<FileDO> fileList = fileService.getFileUrlByCodeBusinessTypeAndSourceId(businessType, iterm.getId());
            List<String> collect = fileList.stream().map(FileDO::getUrl).collect(Collectors.toList());
            iterm.setFiles(collect);
        }
        return pageResultVO;
    }

    /**
     * @param purchaseManger 证件对象
     */
    private void setCardInfo(CardPage<PurchaseMangerRespVO> purchaseManger) {

        //1、进行中
        //Long inProgressContract1 = purchaseMangerMapper.selectCountInProgress(1);
        LambdaQueryWrapper<PurchaseMangerDO> queryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper<PurchaseMangerDO> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.eq(PurchaseMangerDO::getStatus, 1);
        Long inProgressContract1 = purchaseMangerMapper.selectCount(queryWrapper);
        purchaseManger.setInProgressContract1(inProgressContract1);

        //2、已完成
//        Long completedContract2 = purchaseMangerMapper.selectCountInProgress(2);
        LambdaQueryWrapper<PurchaseMangerDO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(PurchaseMangerDO::getStatus, 2);
        Long completedContract2 = purchaseMangerMapper.selectCount(queryWrapper2);
        purchaseManger.setCompletedContract2(completedContract2);

        //3、证件到期前7天产生预警，购买证剩余数量低于购买证数量的20%产生预警
        Long pendingArchivalContract3 = purchaseMangerMapper.selectExpirationReminder();
        purchaseManger.setExpirationReminderContract3(pendingArchivalContract3);


    }

    @Override
    public List<PurchaseMangerBasicRespVO> getPurchaseManger02(Long id) {
        LambdaQueryWrapper<PurchaseMangerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseMangerDO::getId, id);
        List<PurchaseMangerDO> purchaseMangerDOList = purchaseMangerMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(purchaseMangerDOList)) {
            throw exception(PURCHASE_MANGER_NOT_EXISTS);
        }
        List<PurchaseMangerBasicRespVO> purchaseMangerBasicRespVOS = PurchaseMangerConvert.INSTANCE.convertList(purchaseMangerDOList);
        purchaseMangerBasicRespVOS.forEach(item -> {
            DeptDO deptDO = deptMapper.selectById(item.getDeptId());
            item.setDeptName(deptDO.getName());
        });
        return purchaseMangerBasicRespVOS;
    }

    @Override
    public List<PurchaseMangerSimpleRespVO> getPurchaseMangerList(Long type,Long deptId) {
        List<PurchaseMangerSimpleRespVO> list = new ArrayList<>();
        List<PurchaseMangerDO> purchaseMangerDOS = null;
        LambdaQueryWrapper<PurchaseMangerDO> queryWrapper = new LambdaQueryWrapper<>();
        /// 上下游购买证所属部门相同和未到期的购买证编码
        queryWrapper.eq(type != null,PurchaseMangerDO::getType, type)
                 .eq(deptId != null, PurchaseMangerDO::getDeptId, deptId)
                 .gt(PurchaseMangerDO::getPurchaseEtime, LocalDateTime.now());
        purchaseMangerDOS = purchaseMangerMapper.selectList(queryWrapper);
        if (!CollUtil.isEmpty(purchaseMangerDOS)) {
            list = BeanUtils.toBean(purchaseMangerDOS, PurchaseMangerSimpleRespVO.class);
        }
        return list;
    }

    @Override
    public List<PurchaseMangerSalseSimpleRespVO> getPurchaseMangerList1() {
        List<PurchaseMangerSalseSimpleRespVO> list = new ArrayList<>();
        //1、全部信息
        List<PurchaseMangerDO> purchaseMangerDOS = purchaseMangerMapper.selectList();
        if (!CollUtil.isEmpty(purchaseMangerDOS)) {
            purchaseMangerDOS.forEach(iterm -> {
                PurchaseMangerSalseSimpleRespVO purchaseMangerSalseSimpleRespVO = new PurchaseMangerSalseSimpleRespVO();
                purchaseMangerSalseSimpleRespVO.setId(iterm.getId());
                purchaseMangerSalseSimpleRespVO.setSalseUnit(iterm.getSalseUnit());
                list.add(purchaseMangerSalseSimpleRespVO);
            });
        }
        return list;
    }

    @Override
    public List<PurchaseMangerUnitSimpleRespVO> getPurchaseMangerList2() {
        List<PurchaseMangerUnitSimpleRespVO> list = new ArrayList<>();
        //1、全部信息
        List<PurchaseMangerDO> purchaseMangerDOS = purchaseMangerMapper.selectList();
        if (!CollUtil.isEmpty(purchaseMangerDOS)) {
            purchaseMangerDOS.forEach(iterm -> {
                PurchaseMangerUnitSimpleRespVO purchaseMangerUnitSimpleRespVO = new PurchaseMangerUnitSimpleRespVO();
                purchaseMangerUnitSimpleRespVO.setId(iterm.getId());
                purchaseMangerUnitSimpleRespVO.setPurchaseUnit(iterm.getPurchaseUnit());
                list.add(purchaseMangerUnitSimpleRespVO);
            });
        }
        return list;
    }

}