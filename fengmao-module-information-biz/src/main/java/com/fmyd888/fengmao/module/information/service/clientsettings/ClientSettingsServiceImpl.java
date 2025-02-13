package com.fmyd888.fengmao.module.information.service.clientsettings;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto.*;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.CustomerDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.OilCardDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.StatementTemplateDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.WechatBindDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customerwechat.CustomerWechatDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.foreigntrailerclient.ForeignTrailerClientDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.foreignvehicleclient.ForeignVehicleClientDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.foreignwechatclient.ForeignWechatClientDO;
import com.fmyd888.fengmao.module.information.dal.mysql.clientsettings.*;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerMapper;
import com.fmyd888.fengmao.module.infra.api.file.DTO.FileDTO;
import com.fmyd888.fengmao.module.infra.api.file.FileApi;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import com.fmyd888.fengmao.module.infra.dal.mysql.file.FileMapper;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.ClientSettingsDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 客户端设置 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class ClientSettingsServiceImpl implements ClientSettingsService {

    @Resource
    private ClientSettingsMapper clientSettingsMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private StatementTemplateMapper statementTemplateMapper;

    @Resource
    private ForeignVehicleClientMapper foreignVehicleClientMapper;

    @Resource
    private ForeignTrailerClientMapper foreignTrailerClientMapper;

    @Resource
    private ForeignWechatClientMapper foreignWechatClientMapper;

    @Resource
    private CustomerWechatMapper customerWechatMapper;

    @Resource
    private OilCardMapper oilCardMapper;

    @Resource
    private WechatBindMapper wechatBindMapper;

    @Resource
    private FileApi fileApi;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createClientSettings(ClientSettingsSaveReqVO createReqVO) {

        //检验
        validateClientSettingsNameUnique(createReqVO);
        // 插入
        ClientSettingsDO clientSettings = BeanUtils.toBean(createReqVO, ClientSettingsDO.class);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        clientSettings.setDeptId(loginUserDeptId);

        Long cardId = createReqVO.getCardId(); //油卡id
        String oilEngine = createReqVO.getOilName();//油机名称

        //如果id为空并且oilEngine不为空，说明数据库中没有该油机，需要插入一条新的油卡记录
        if (cardId == null && oilEngine != null){
            OilCardDO oilCardDO = new OilCardDO();
            oilCardDO.setOilEngine(oilEngine);
            oilCardMapper.insert(oilCardDO);
            //新增后插入数据到客户端设置表
            clientSettings.setCardId(oilCardDO.getId());
        }

        clientSettingsMapper.insert(clientSettings);

        if (createReqVO.getOutsourceCarrier() != null){
            LambdaUpdateWrapper<CustomerDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(CustomerDO::getId, createReqVO.getCustomerId())
                    .eq(CustomerDO::getCustomerType, 2)
                    .eq(CustomerDO::getDeleted, 0)
                    .set(CustomerDO::getIsOut, createReqVO.getOutsourceCarrier());
            customerMapper.update(null,updateWrapper);
        }

        //获得客户设置表id
        Long clientId = clientSettings.getId();

        //插入外援车头id和客户端设置表id
        if (CollectionUtils.isNotEmpty(createReqVO.getMainVehicleSimpleRespVOS())){
            List<ForeignVehicleClientDO> vehicleClientList = new ArrayList<>();
            for (Long vehicleId : createReqVO.getMainVehicleSimpleRespVOS()) {
                ForeignVehicleClientDO vehicleClient = new ForeignVehicleClientDO();
                vehicleClient.setClientId(clientId);
                vehicleClient.setForeignVehicleId(vehicleId);
                vehicleClientList.add(vehicleClient);
                foreignVehicleClientMapper.insert(vehicleClient);
            }

        }
        //插入外援车挂id和客户端设置表id
        if (CollectionUtils.isNotEmpty(createReqVO.getTrailerSimpleness())) {
            List<ForeignTrailerClientDO> trailerClientList = new ArrayList<>();
            for (Long trailerId : createReqVO.getTrailerSimpleness()) {
                ForeignTrailerClientDO trailerClient = new ForeignTrailerClientDO();
                trailerClient.setClientId(clientId);
                trailerClient.setForeignTrailerId(trailerId);
                trailerClientList.add(trailerClient);
                foreignTrailerClientMapper.insert(trailerClient);
            }
            // 批量插入车挂与客户设置关系
//            foreignTrailerClientMapper.insertBatch(trailerClientList);
        }

        //如果微信id不为空的话，则插入当前id和微信id
        if ((CollectionUtils.isNotEmpty(createReqVO.getWechatBindSimpleness()))){
            List<ForeignWechatClientDO> customerWechatDOList = new ArrayList<>();
            for (Long foreignWechatId : createReqVO.getWechatBindSimpleness()){
                ForeignWechatClientDO foreignWechatClientDO = new ForeignWechatClientDO();
                foreignWechatClientDO.setClientId(clientId);
                foreignWechatClientDO.setForeignWechatId(foreignWechatId);
                customerWechatDOList.add(foreignWechatClientDO);

            // 批量插入微信id与当前客户端设置的客户id关系
                foreignWechatClientMapper.insert(foreignWechatClientDO);
            }
        }

        List<StatementTemplateDO> statementTemplates = createReqVO.getStatementSimpleness();
        if (!CollectionUtils.isNotEmpty(statementTemplates)){
            List<StatementTemplateDO> statementTemplateDOList = new ArrayList<>();
            for (StatementTemplateDO item : statementTemplates){
                StatementTemplateDO statementTemplateDO =  new StatementTemplateDO();
                statementTemplateDO.setCommodityId(item.getCommodityId());
                statementTemplateDO.setEntityId(clientId);
                statementTemplateDO.setFileIds(item.getFileIds());
                statementTemplateDOList.add(statementTemplateDO);

                // 插入对账模板表
                 statementTemplateMapper.insert(statementTemplateDO);
                Long fileId = statementTemplateDO.getFileIds();
                Long id = statementTemplateDO.getId();
                if (id != null && fileId != null) {
                    // 更新FileDO的sourceId字段
                    FileDO fileDO = fileMapper.selectById(fileId);
                    if (fileDO != null) {
                        // 如果找到了对应的FileDO，更新其sourceId
                        fileDO.setSourceId(id);
                        // 更新FileDO
                        fileMapper.updateById(fileDO);
                    }
                }
            }
        }

        // 返回
        return clientSettings.getId();
    }


    //客户名称重复校验
    private void validateClientSettingsNameUnique(ClientSettingsSaveReqVO updateReqVO) {

        LambdaQueryWrapper<ClientSettingsDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClientSettingsDO::getCustomerId, updateReqVO.getCustomerId());
        if (updateReqVO.getId() != null){
            queryWrapper.ne(ClientSettingsDO::getId, updateReqVO.getId());
        }
        if (clientSettingsMapper.selectOne(queryWrapper) != null){
            throw exception(CLIENT_SETTINGS__EXISTS);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClientSettings(ClientSettingsSaveReqVO updateReqVO) {

        //检验
        validateClientSettingsNameUnique(updateReqVO);

        Long clientId = updateReqVO.getId();
        // 校验存在
        validateClientSettingsExists(clientId);
        // 更新
        ClientSettingsDO updateObj = BeanUtils.toBean(updateReqVO, ClientSettingsDO.class);

        Long cardId = updateReqVO.getCardId();
        String oilEngine = updateReqVO.getOilName();
        //如果id为空并且oilEngine不为空，说明数据库中没有该油机，需要插入一条新的油卡记录
        if (cardId == null && oilEngine != null){
            OilCardDO oilCardDO = new OilCardDO();
            oilCardDO.setOilEngine(oilEngine);
            oilCardMapper.insert(oilCardDO);
            //新增后插入数据到客户端设置表
            updateObj.setCardId(oilCardDO.getId());
        }
        clientSettingsMapper.updateById(updateObj);

        if (updateObj.getOutsourceCarrier() != null){
            LambdaUpdateWrapper<CustomerDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(CustomerDO::getId, updateObj.getCustomerId())
                    .eq(CustomerDO::getCustomerType, 2)
                    .eq(CustomerDO::getDeleted, 0)
                    .set(CustomerDO::getIsOut, updateObj.getOutsourceCarrier());
            customerMapper.update(null,updateWrapper);
        }


        // 获取前端传过来的车挂ID列表
        List<Long> newTrailerIdList = updateReqVO.getTrailerSimpleness();
        // 把传过来的值放入set中，用于快速查找
        Set<Long> newTrailerIdSet = new HashSet<>(newTrailerIdList);

        // 根据clientId查询关联的TrailerId
        LambdaQueryWrapper<ForeignTrailerClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ForeignTrailerClientDO::getClientId, clientId);
        List<ForeignTrailerClientDO> foreignTrailerClientDOS = foreignTrailerClientMapper.selectList(queryWrapper);

        // 提取数据库中已存在的TrailerId，并放入set中
        Set<Long> existingTrailerIdSet = foreignTrailerClientDOS.stream()
                .map(ForeignTrailerClientDO::getForeignTrailerId)
                .collect(Collectors.toSet());

        // 找出需要新增的车挂ID（即前端传过来的ID集合中但不在数据库集合中的ID）
        List<Long> toAdd = new ArrayList<>(newTrailerIdSet);
        toAdd.removeAll(existingTrailerIdSet);

        // 遍历需要新增的车挂ID并执行新增操作
        for (Long trailerIdToAdd : toAdd) {
            ForeignTrailerClientDO foreignTrailerClientDO = new ForeignTrailerClientDO();
            foreignTrailerClientDO.setClientId(clientId);
            foreignTrailerClientDO.setForeignTrailerId(trailerIdToAdd);
            foreignTrailerClientMapper.insert(foreignTrailerClientDO);
        }

        // 找出需要删除的车挂ID（即在现有数据库集合中但不在前端传入的集合中的ID）
        List<Long> toDelete = new ArrayList<>(existingTrailerIdSet);
        toDelete.removeAll(newTrailerIdSet);

        // 批量删除操作
        if (!toDelete.isEmpty()) {
            LambdaQueryWrapper<ForeignTrailerClientDO> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.in(ForeignTrailerClientDO::getForeignTrailerId, toDelete);
            foreignTrailerClientMapper.delete(deleteWrapper);
        }




        //2.获取前端传过来的车头id列表
        List<Long> newVehicleIdList = updateReqVO.getMainVehicleSimpleRespVOS();

        //把传过来的值放入set中，用于快速查找
        Set<Long> newVehicleIdSet = new HashSet<>(newVehicleIdList);

        //根据clientId查询关联的VehicleId
        LambdaQueryWrapper<ForeignVehicleClientDO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(ForeignVehicleClientDO::getClientId, clientId);
        List<ForeignVehicleClientDO> foreignVehicleClientDOS = foreignVehicleClientMapper.selectList(queryWrapper2);

        //提取数据库中已存在的VehicleId，并放入set中
        Set<Long> existingVehicleIdSet = foreignVehicleClientDOS.stream()
                .map(ForeignVehicleClientDO::getForeignVehicleId)
                .collect(Collectors.toSet());


        //找出需要新增的车头（即前端传过来的ID集合中但不在数据库集合中的ID）
        List<Long> toAdd2 = new ArrayList<>(newVehicleIdSet);
        toAdd2.removeAll(existingVehicleIdSet);


        //遍历需要新增的VehicleId并执行新增操作
        for (Long vehicleIdToAdd : toAdd2) {
            ForeignVehicleClientDO foreignVehicleClientDO = new ForeignVehicleClientDO();
            foreignVehicleClientDO.setClientId(clientId);
            foreignVehicleClientDO.setForeignVehicleId(vehicleIdToAdd);
            foreignVehicleClientMapper.insert(foreignVehicleClientDO);
        }

        //找出需要删除的VehicleId（即在现有数据库集合中但不在前端传入的集合中的ID）
        List<Long> toDelete2 = new ArrayList<>(existingVehicleIdSet);
        toDelete2.removeAll(newVehicleIdSet);

        //批量删除操作
        if (!toDelete2.isEmpty()) {
            LambdaQueryWrapper<ForeignVehicleClientDO> deleteWrapper2 = new LambdaQueryWrapper<>();
            deleteWrapper2.in(ForeignVehicleClientDO::getForeignVehicleId, toDelete2);
            foreignVehicleClientMapper.delete(deleteWrapper2);
        }




        //3.获取前端传过来的微信ids
        List<Long> newWechatIdList = updateReqVO.getWechatBindSimpleness();

        //把传过来的值放入set中，用于快速查找
        Set<Long> newWechatIdSet = new HashSet<>(newWechatIdList);

        //根据clientId查询关联的WechatId
        LambdaQueryWrapper<ForeignWechatClientDO> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.eq(ForeignWechatClientDO::getClientId, clientId);
        List<ForeignWechatClientDO> foreignWechatClientDOS = foreignWechatClientMapper.selectList(queryWrapper3);

        //提取数据库中已存在的WechatId，并放入set中
        Set<Long> existingWechatIdSet = foreignWechatClientDOS.stream()
                                .map(ForeignWechatClientDO::getForeignWechatId)
                                .collect(Collectors.toSet());

        //找出需要新增的WechatId（即前端传过来的ID集合中但不在数据库集合中的ID）
        List<Long> toAdd3 = new ArrayList<>(newWechatIdSet);
        toAdd3.removeAll(existingWechatIdSet);

        //遍历需要新增的WechatId并执行新增操作
        for (Long wechatIdToAdd : toAdd3) {
            ForeignWechatClientDO foreignWechatClientDO = new ForeignWechatClientDO();
            foreignWechatClientDO.setClientId(clientId);
            foreignWechatClientDO.setForeignWechatId(wechatIdToAdd);
            foreignWechatClientMapper.insert(foreignWechatClientDO);
        }

        //找出需要删除的WechatId（即在现有数据库集合中但不在前端传入的集合中的ID）
        List<Long> toDelete3 = new ArrayList<>(existingWechatIdSet);
        toDelete3.removeAll(newWechatIdSet);

        //批量删除操作
        if (!toDelete3.isEmpty()) {
            LambdaQueryWrapper<ForeignWechatClientDO> deleteWrapper3 = new LambdaQueryWrapper<>();
            deleteWrapper3.in(ForeignWechatClientDO::getForeignWechatId, toDelete3);
            foreignWechatClientMapper.delete(deleteWrapper3);
        }

        // 获取前端传过来的对账模板账单
        List<StatementTemplateDO> statementSimpleness = updateReqVO.getStatementSimpleness();
        HashSet<StatementTemplateDO> newStatementTemplateDOS = new HashSet<>(statementSimpleness);

        // 根据 clientId 查询数据库中的对账模板
        LambdaQueryWrapper<StatementTemplateDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(StatementTemplateDO::getEntityId, clientId);
        List<StatementTemplateDO> statementTemplateDOS = statementTemplateMapper.selectList(queryWrapper1);

        // 提取数据库中存在的 statementId，并放入 Set 中
        Set<Long> existingStatementIdSet = statementTemplateDOS.stream()
                .map(StatementTemplateDO::getId)
                .collect(Collectors.toSet());

        // 提取前端传来的数据中的 statementId
        Set<Long> newStatementIdSet = newStatementTemplateDOS.stream()
                .map(StatementTemplateDO::getId)
                .collect(Collectors.toSet());

        // 找出需要新增的对账模板（即前端传过来但数据库中不存在的）
        Set<StatementTemplateDO> toAdd1 = new HashSet<>(newStatementTemplateDOS);
        toAdd1.removeIf(statementTemplateDO -> existingStatementIdSet.contains(statementTemplateDO.getId()));

        // 找出需要删除的对账模板（即数据库中存在但前端没有传来的）
        List<Long> toDelete1 = statementTemplateDOS.stream()
                .map(StatementTemplateDO::getId)
                .filter(id -> !newStatementIdSet.contains(id))
                .collect(Collectors.toList());

        // 处理新增的对账模板
        for (StatementTemplateDO statementTemplateDO1 : toAdd1) {
            statementTemplateDO1.setEntityId(clientId);
            statementTemplateMapper.insert(statementTemplateDO1);

            // 更新文件表中源 ID
            Long fileId = statementTemplateDO1.getFileIds();
            if (fileId != null) {
                    FileDO fileDO = fileMapper.selectById(fileId);
                    if (fileDO != null) {
                        fileDO.setSourceId(statementTemplateDO1.getId());
                        fileMapper.updateById(fileDO);
                    }
            }
        }

        // 处理删除的对账模板
        if (!toDelete1.isEmpty()) {
            statementTemplateMapper.deleteBatchIds(toDelete1);

            // 更新文件表，逐个处理
            for (Long id : toDelete1) {
                List<FileDO> fileDOs = fileMapper.selectList(new QueryWrapper<FileDO>().eq("source_id", id));
                for (FileDO fileDO : fileDOs) {
                    if (fileDO != null) {
                        // 更新文件的 source_id 和业务类型
                        fileDO.setSourceId(null);
                        fileDO.setCodeBusinessType(FileEnums.STATEMENT_TEMPLATE_DELETE.getCodeBusinessType());
                        fileMapper.updateById(fileDO);
                    }
                }
            }
        }

        // 更新子表
//        updateStatementTemplateList(updateReqVO.getId(), updateReqVO.getStatementSimpleness());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteClientSettings(Long id) {
        // 校验存在
        validateClientSettingsExists(id);
        // 删除
        clientSettingsMapper.deleteById(id);
    }

    private void validateClientSettingsExists(Long id) {
        if (clientSettingsMapper.selectById(id) == null) {
        throw exception(CLIENT_SETTINGS_NOT_EXISTS);
        }
    }


    @Override
    public ClientSettingsDO getClientSettings(Long id) {
        return clientSettingsMapper.selectById(id);
    }

    @Override
    public List<ClientSettingsDO> getClientSettingsList(Collection<Long> ids) {
        return clientSettingsMapper.selectBatchIds(ids);
    }

    @Override
    public ClientSettingsPage<ClientSettingsRespVO> getClientSettingsPage(ClientSettingsPageReqVO pageReqVO) {
        //分页信息
        ClientSettingsPage<ClientSettingsRespVO> ClientSettingsPage = new ClientSettingsPage<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        //分页内容
        ClientSettingsPage<ClientSettingsRespVO> clientSettingsRespVOClientSettingsPage = clientSettingsMapper.selectClientSettingsPage(ClientSettingsPage, pageReqVO);

        List<ClientSettingsRespVO> resultList = clientSettingsRespVOClientSettingsPage.getRecords();
        if (resultList != null ) {

            for (ClientSettingsRespVO record : resultList) {
                Long recordId = record.getId();
                List<String> wechat = clientSettingsMapper.selectClientSettingWechat(recordId);
                List<String> main = clientSettingsMapper.selectClientSettingMain(recordId);
                List<String> trailer = clientSettingsMapper.selectClientSettingTrailer(recordId);

                if (!wechat.isEmpty()){
                    record.setWechatNames(String.join(",", wechat));
                }else {
                    record.setWechatNames("");
                }

                if (!main.isEmpty()){
                    record.setPlateNumber(String.join(",", main));
                }else {
                    record.setPlateNumber("");
                }

                if (!trailer.isEmpty()){
                    record.setVehicleTrailerNo(String.join(",", trailer));
                }
                else {
                    record.setVehicleTrailerNo("");
                }

            }

        }
        return clientSettingsRespVOClientSettingsPage;
    }

    @Override
    public ClientSettingsDetailDTO getClientSettingsDetail(Long id) {
        // 获得客户端设置信息
        ClientSettingsDetailDTO clientSettingsDetailDTO = clientSettingsMapper.selectClientSettingsDetail(id);
        Long clientId = clientSettingsDetailDTO.getId();

        if (clientId == null){
           throw exception(CLIENT_SETTINGS_NOT_EXISTS);
        }
        //获得微信客户
        List<Long> longs = clientSettingsMapper.selectWechatBindSimpleness(clientId);
        clientSettingsDetailDTO.setWechatBindSimpleness(longs);


        // 获得客户端设置的子表账单模板信息
        List<StatementTemplateSimplenessDTO> statementTemplateSimplenessDTOS = clientSettingsMapper.selectStatementSimpleness(clientId);
        // 如果列表为空，则创建一个只包含一个所有字段为null的StatementTemplateSimplenessDTO的列表
        if (statementTemplateSimplenessDTOS == null || statementTemplateSimplenessDTOS.isEmpty()) {
            StatementTemplateSimplenessDTO emptyTemplate = new StatementTemplateSimplenessDTO(); // 假设有默认的无参构造方法
            // 如果你想手动设置所有字段为null，可以在这里做
            statementTemplateSimplenessDTOS = Collections.singletonList(emptyTemplate);
        }
        // 获取业务类型
        String statementTemplateBusinessType = FileEnums.STATEMENT_TEMPLATE.getCodeBusinessType();

        // 遍历文件信息列表
        statementTemplateSimplenessDTOS.forEach(item->{

            List<FileDTO> fileUrl = fileApi.getFileUrlByCodeBusinessTypeAndSourceId(statementTemplateBusinessType, item.getId());
            // 填充文件信息
            List<Map<String, Object>> maps = fillFileResInfo(fileUrl);
            item.setFileIds(maps);
        });
        clientSettingsDetailDTO.setStatementSimpleness(statementTemplateSimplenessDTOS);


        // 获得客户端设置的外援车头信息
        List<Long> longs1 = clientSettingsMapper.selectVehicleSimpleness(clientId);
        clientSettingsDetailDTO.setMainVehicleSimpleRespVOS(longs1);

        // 获得客户端设置的外援车挂信息
        List<Long> longs2 = clientSettingsMapper.selectTrailerSimpleness(clientId);
        clientSettingsDetailDTO.setTrailerSimpleness(longs2);

        return clientSettingsDetailDTO;
    }


    /**
     * 填充文件资源信息
     *
     * @param fileList 文件列表
     * @return 填充了文件资源信息的列表
     */
    private List<Map<String, Object>> fillFileResInfo(List<FileDTO> fileList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (!com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isEmpty(fileList)) {
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
    public List<ClientSettingsDO> getClientSettingsList(ClientSettingsListReqVO listReqVO) {
        return clientSettingsMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateClientSettings(List<ClientSettingsSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteClientSettings(List<Long> ids) {
        // 在这里处理批量删除逻辑
        clientSettingsMapper.deleteBatchIds(ids);
    }

    @Override
    public List<ClientSettingsExcelVO> importPreviewList(List<ClientSettingsExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(CLIENT_SETTINGS_IMPORT_LIST_IS_EMPTY);
        }

        List<ClientSettingsExcelVO> excelVo = BeanUtils.toBean(importDatas, ClientSettingsExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(CLIENT_SETTINGS_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(ClientSettingsExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
            throw exception(CLIENT_SETTINGS_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(CLIENT_SETTINGS_IMPORT_PORT_REQUIRE);
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
    public List<HashMap<String, Object>> selectOilCardDetailsMap(CommonQueryParam param) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        LambdaQueryWrapper<OilCardDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OilCardDO::getStatus, 0)
                .in(ArrayUtil.isNotEmpty(param.getCompanyIds()), OilCardDO::getCompanyId, param.getCompanyIds())
                .like(StrUtil.isNotBlank(param.getSearchKey()), OilCardDO::getOilEngine, param.getSearchKey());
        List<OilCardDO> oilCardDOS = oilCardMapper.selectList(queryWrapper);
        oilCardDOS.forEach(iterm ->{
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("oilEngine", iterm.getOilEngine());
            list.add(map);
        });
        return list;
    }

    @Override
    public List<HashMap<String, Object>> selectWechatBindDetailsMap(String nickname) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        LambdaQueryWrapper<WechatBindDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WechatBindDO::getStatus, 0)
                .eq(WechatBindDO::getDeleted, 0);
        if (StringUtils.isNotEmpty(nickname)){
            queryWrapper.like(WechatBindDO::getNickname, nickname);
        }
        List<WechatBindDO> wechatBindDOS = wechatBindMapper.selectList(queryWrapper);
        wechatBindDOS.forEach(iterm ->{
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("nickname", iterm.getNickname());
            list.add(map);
        });
        return list;
    }

    @Override
    public ClientSettingsPage<WechatBindRespVO> getForeignWechatClientPage(WechatBindPageReqVO pageReq) {
        ClientSettingsPage<WechatBindRespVO> page = new ClientSettingsPage<>(pageReq.getPageNo(), pageReq.getPageSize());
        ClientSettingsPage<WechatBindRespVO> foreignWechatClientPage = clientSettingsMapper.getForeignWechatClientPage(page, pageReq);
        return foreignWechatClientPage;
    }

    @Override
    public WechatBindDetailsDTO selectWechatBindDetailsByWechatId(Long id) {
        WechatBindDetailsDTO wechatBindDetailsDTO = clientSettingsMapper.selectWechatBindDetailsByWechatId(id);
        Long wechatId = wechatBindDetailsDTO.getId();
        //根据用户id查询关联表中的客户信息
        List<CustomerDTO> customerDTOS = clientSettingsMapper.selectListByWechatId(wechatId);
            wechatBindDetailsDTO.setCustomerDTOList(customerDTOS);
        return wechatBindDetailsDTO;
    }

    @Override
    public void deleteWechatBind(Long id) {
        // 校验存在
        validateWechatBindExists(id);
        // 删除
        wechatBindMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWechatBind(Long id,List<Long> customerIds) {
//        // 校验存在
        validateWechatBindExists(id);
//        // 更新
//        WechatBindDO wechatBindDO = BeanUtils.toBean(updateReqVO, WechatBindDO.class);
//        wechatBindMapper.updateById(wechatBindDO);

        //根据前端传过来的customerIds

        //根据当前微信绑定id，查询关联表中的绑定的客户信息并删除
        List<Long> customerIdByWechatId = clientSettingsMapper.selectCustomerIdByWechatId(id);
        //删除关联表中的客户信息
        clientSettingsMapper.deleteBatchIds(customerIdByWechatId);

        //插入新的客户信息id和当前的微信绑定id
        if (CollectionUtils.isNotEmpty(customerIds)){
            ArrayList<CustomerWechatDO> customerWechatDOS = new ArrayList<>();
            for (Long customerId : customerIds) {
                CustomerWechatDO customerWechatDO = new CustomerWechatDO();
                customerWechatDO.setCustomerId(customerId);
                customerWechatDO.setForeignWechatId(id);
                customerWechatDOS.add(customerWechatDO);
            }
            //批量插入
            customerWechatMapper.insertBatch(customerWechatDOS);
        }

    }


    private void validateWechatBindExists(Long id) {
        if (wechatBindMapper.selectById(id) == null) {
            throw exception(WECHAT_BIND_NOT_EXISTS);
        }
    }


// ==================== 子表（子表-对账单模板） ====================

    @Override
    public PageResult<StatementTemplateDO> getStatementTemplatePage(PageParam pageReqVO, Long entityId) {
        return statementTemplateMapper.selectPage(pageReqVO, entityId);
    }

    @Override
    public List<StatementTemplateDO> getStatementTemplateListByEntityId(Long entityId) {
        return statementTemplateMapper.selectListByEntityId(entityId);
    }

//    @Override
//    public Long createStatementTemplate(StatementTemplateDO statementTemplate) {
//            //获取部门id
//        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
//        statementTemplate.setDeptId(loginUserDeptId);
//        statementTemplateMapper.insert(statementTemplate);
//        return statementTemplate.getId();
//    }

//    @Override
//    public void updateStatementTemplate(StatementTemplateDO statementTemplate) {
//        // 校验存在
//        validateStatementTemplateExists(statementTemplate.getId());
//        // 更新
//        statementTemplateMapper.updateById(statementTemplate);
//    }
//
//    @Override
//    public void deleteStatementTemplate(Long id) {
//        // 校验存在
//        validateStatementTemplateExists(id);
//        // 删除
//        statementTemplateMapper.deleteById(id);
//    }
//
//    @Override
//    public StatementTemplateDO getStatementTemplate(Long id) {
//        return statementTemplateMapper.selectById(id);
//    }
//
//    private void validateStatementTemplateExists(Long id) {
//        if (statementTemplateMapper.selectById(id) == null) {
//        throw exception(STATEMENT_TEMPLATE_NOT_EXISTS);
//    }
//    }

    private void createStatementTemplateList(Long entityId, List<StatementTemplateDO> list) {
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        list.forEach(o -> {
            o.setEntityId(entityId);
            o.setDeptId(loginUserDeptId);
            statementTemplateMapper.insert(o);
            //获取新增的文件id和当前id
            Long fileIds = o.getFileIds();
            Long id = o.getId();

            //文件表绑定
            fileApi.bindAttachment(fileIds, id);
            //查询文件
//            FileDO fileDO = fileMapper.selectById(fileIds);
//            FileDO fileDO1 = fileDO.setSourceId(id);
//            fileMapper.updateById(fileDO1);
        });
//        statementTemplateMapper.insertBatch(list);
    }

    private void updateStatementTemplateList(Long entityId, List<StatementTemplateDO> list) {
        if (list.size() <= 0)
            return;
        //获取所有子数据
        List<StatementTemplateDO> statementTemplateDOS = statementTemplateMapper.selectListByEntityId(entityId);
        //如果没有直接插入
        if (statementTemplateDOS.size() <= 0)
            createStatementTemplateList(entityId, list);
        else {
            //找出新增、修改、删除的数据
            BiFunction<StatementTemplateDO, StatementTemplateDO, Boolean> sameFunc = (oldObj, newObj) -> {
                boolean same = oldObj.getId().equals(newObj.getId());
                return same;
            };

            // 调用
            List<List<StatementTemplateDO>> result = CollectionUtils.diffList(statementTemplateDOS, list, sameFunc);

            //新增
            if (result.get(0).size() >= 0)
                createStatementTemplateList(entityId, result.get(0));

            //修改
            if (result.get(1).size() >= 0){
                for (StatementTemplateDO templateDO : result.get(1)) {
                    //更新账单模板表
                    statementTemplateMapper.updateById(templateDO);
                    Long fileIds = templateDO.getFileIds();
                    if (fileIds != null){
                        FileDO fileDO = fileMapper.selectById(fileIds);
                        //如果文件表的sourceId是null，说明是新增的文件，需要更新sourceId
                        if (fileDO.getSourceId() == null){
                            fileDO.setSourceId(templateDO.getId());
                            fileMapper.updateById(fileDO);
                        }
                    }
                }
                }


            //删除
            if (result.get(2).size() >= 0){
                {
                    List<Long> statementTemplateIds = CollectionUtils.convertList(result.get(2), p -> p.getId());
                    statementTemplateMapper.deleteBatchIds(statementTemplateIds);

                    // 批量更新文件表
                    List<FileDO> fileDOsToUpdate = new ArrayList<>();
                    for (Long statementTemplateId : statementTemplateIds) {
                        FileDO fileDO = fileMapper.selectOne(new QueryWrapper<FileDO>().eq("source_id", statementTemplateId));
                        if (fileDO != null) {
//                            fileApi.unBindAttachment(fileDO.getId());
                            fileDO.setSourceId(null);
                            fileDO.setCodeBusinessType(FileEnums.STATEMENT_TEMPLATE_DELETE.getCodeBusinessType());
                            fileDOsToUpdate.add(fileDO);
                        }
                    }

                    // 批量更新 FileDO 对象
                    if (!fileDOsToUpdate.isEmpty()) {
                        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
                            FileMapper batchFileMapper = sqlSession.getMapper(FileMapper.class);
                            for (FileDO fileDO : fileDOsToUpdate) {
                                batchFileMapper.updateById(fileDO);
                            }
                            sqlSession.commit(); // 提交事务
                        }
                    }

                }
            }
        }
    }



    private void createForeignVehicleClientList(Long entityId, List<ForeignVehicleClientDO> list) {
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        list.forEach(o -> {
            o.setClientId(entityId);
            o.setDeptId(loginUserDeptId);
            foreignVehicleClientMapper.insert(o);
        });
    }

    private void updateForeignVehicleClientList(Long entityId, List<ForeignVehicleClientDO> list) {
        if (list.size() <= 0)
            return;
        //获取所有子数据
        List<ForeignVehicleClientDO> foreignVehicleClientDOS = foreignVehicleClientMapper.selectListByEntityId(entityId);
        //如果没有直接插入
        if (foreignVehicleClientDOS.size() <= 0)
            createForeignVehicleClientList(entityId, list);
        else {
            //找出新增、修改、删除的数据
            BiFunction<ForeignVehicleClientDO, ForeignVehicleClientDO, Boolean> sameFunc = (oldObj, newObj) -> {
                boolean same = oldObj.getId().equals(newObj.getId());
                return same;
            };

            // 调用
            List<List<ForeignVehicleClientDO>> result = CollectionUtils.diffList(foreignVehicleClientDOS, list, sameFunc);

            //新增
            if (result.get(0).size() >= 0)
                createForeignVehicleClientList(entityId, result.get(0));

            //修改
            if (result.get(1).size() >= 0)
                foreignVehicleClientMapper.updateBatch(result.get(1));

            //删除
            if (result.get(2).size() >= 0)
                foreignVehicleClientMapper.deleteBatchIds(CollectionUtils.convertList(result.get(2), p ->p.getId()));
        }
    }

}