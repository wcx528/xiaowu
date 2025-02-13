package com.fmyd888.fengmao.module.information.dal.mysql.clientsettings;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto.*;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.CustomerDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.ClientSettingsDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.StatementTemplateDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.WechatBindDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.foreigntrailerclient.ForeignTrailerClientDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 客户端设置 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface ClientSettingsMapper extends BaseMapperX<ClientSettingsDO> {

    default PageResult<ClientSettingsDO> selectPage(ClientSettingsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ClientSettingsDO>()
                .eqIfPresent(ClientSettingsDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(ClientSettingsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ClientSettingsDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ClientSettingsDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(ClientSettingsDO::getVehicleRepairer, reqVO.getVehicleRepairer())
                .eqIfPresent(ClientSettingsDO::getPassVehicleRepairer, reqVO.getPassVehicleRepairer())
                .eqIfPresent(ClientSettingsDO::getOutsourceCarrier, reqVO.getOutsourceCarrier())
                .eqIfPresent(ClientSettingsDO::getPassOutsourceCarrier, reqVO.getPassOutsourceCarrier())
                .eqIfPresent(ClientSettingsDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ClientSettingsDO::getCardId, reqVO.getCardId())
                .orderByDesc(ClientSettingsDO::getId));
    }

    default List<ClientSettingsDO> selectList(ClientSettingsListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ClientSettingsDO>()
                .eqIfPresent(ClientSettingsDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(ClientSettingsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ClientSettingsDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ClientSettingsDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(ClientSettingsDO::getVehicleRepairer, reqVO.getVehicleRepairer())
                .eqIfPresent(ClientSettingsDO::getPassVehicleRepairer, reqVO.getPassVehicleRepairer())
                .eqIfPresent(ClientSettingsDO::getOutsourceCarrier, reqVO.getOutsourceCarrier())
                .eqIfPresent(ClientSettingsDO::getPassOutsourceCarrier, reqVO.getPassOutsourceCarrier())
                .eqIfPresent(ClientSettingsDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ClientSettingsDO::getCardId, reqVO.getCardId())
                .orderByDesc(ClientSettingsDO::getId));
    }


    /**
    * 此方法是以字段【平铺】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<ClientSettingsDO> selectJoinTilePage(ClientSettingsPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<ClientSettingsDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), ClientSettingsDO.class,
        new MPJLambdaWrapper<ClientSettingsDO>()
            .selectAll(ClientSettingsDO.class)//查询的主表字段
            //.selectAs(DeptDO::getName, ClientSettingsDO::getDeptName)//查询关联表字段，此处是取组织名称
            //.leftJoin(DeptDO.class, DeptDO::getId, ClientSettingsDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),ClientSettingsDO::getDeptId, reqVO.getDeptId())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),ClientSettingsDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),ClientSettingsDO::getStatus, reqVO.getStatus())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCustomerId()),ClientSettingsDO::getCustomerId, reqVO.getCustomerId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getVehicleRepairer()),ClientSettingsDO::getVehicleRepairer, reqVO.getVehicleRepairer())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPassVehicleRepairer()),ClientSettingsDO::getPassVehicleRepairer, reqVO.getPassVehicleRepairer())
            .eq(ObjectUtil.isNotEmpty(reqVO.getOutsourceCarrier()),ClientSettingsDO::getOutsourceCarrier, reqVO.getOutsourceCarrier())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPassOutsourceCarrier()),ClientSettingsDO::getPassOutsourceCarrier, reqVO.getPassOutsourceCarrier())
            .eq(ObjectUtil.isNotEmpty(reqVO.getRemark()),ClientSettingsDO::getRemark, reqVO.getRemark())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCardId()),ClientSettingsDO::getCardId, reqVO.getCardId())
            .disableSubLogicDel()
            .orderByDesc(ClientSettingsDO::getId));
        return new PageResult<ClientSettingsDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
    * 此方法是以字段【内联】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<ClientSettingsDO> selectJoinInlinePage(ClientSettingsPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<ClientSettingsDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), ClientSettingsDO.class,
        new MPJLambdaWrapper<ClientSettingsDO>()
            .selectAll(ClientSettingsDO.class)//查询的主表字段
            //.selectAssociation(DeptDO.class, ClientSettingsDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
            //.leftJoin(DeptDO.class, DeptDO::getId, ClientSettingsDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .eq(ObjectUtil.isNotEmpty(reqVO.getDeptId()),ClientSettingsDO::getDeptId, reqVO.getDeptId())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),ClientSettingsDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),ClientSettingsDO::getStatus, reqVO.getStatus())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCustomerId()),ClientSettingsDO::getCustomerId, reqVO.getCustomerId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getVehicleRepairer()),ClientSettingsDO::getVehicleRepairer, reqVO.getVehicleRepairer())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPassVehicleRepairer()),ClientSettingsDO::getPassVehicleRepairer, reqVO.getPassVehicleRepairer())
            .eq(ObjectUtil.isNotEmpty(reqVO.getOutsourceCarrier()),ClientSettingsDO::getOutsourceCarrier, reqVO.getOutsourceCarrier())
            .eq(ObjectUtil.isNotEmpty(reqVO.getPassOutsourceCarrier()),ClientSettingsDO::getPassOutsourceCarrier, reqVO.getPassOutsourceCarrier())
            .eq(ObjectUtil.isNotEmpty(reqVO.getRemark()),ClientSettingsDO::getRemark, reqVO.getRemark())
            .eq(ObjectUtil.isNotEmpty(reqVO.getCardId()),ClientSettingsDO::getCardId, reqVO.getCardId())
            .disableSubLogicDel()
            .orderByDesc(ClientSettingsDO::getId));
        return new PageResult<ClientSettingsDO>(resultPage.getRecords(), resultPage.getTotal());
    }
    /**
     * 客户端设置分页查询
     */
    ClientSettingsPage<ClientSettingsRespVO> selectClientSettingsPage(@Param("page") ClientSettingsPage<ClientSettingsRespVO> page,
                                                                      @Param("pageReqVO") ClientSettingsPageReqVO pageReqVO);


    /**
     * 客户端设置表详情
     * @param id
     * @return
     */
    ClientSettingsDetailDTO selectClientSettingsDetail(Long id);
    /**
     * 根据客户端设置表id查看微信客户详情
     */
    List<Long> selectWechatBindSimpleness(Long clientId);
    /**
     * 根据客户端设置表id查看对账模板详情
     */
    List<StatementTemplateSimplenessDTO> selectStatementSimpleness(Long clientId);
    /**
     * 根据客户端设置表id查看外援车头详情
     */
    List<Long> selectVehicleSimpleness(Long clientId);
    /**
     * 根据客户端设置表id查看外援车挂详情
     */
    List<Long> selectTrailerSimpleness(Long clientId);


    /**
     * 根据客户端id删除关联表中的数据（车挂）
     * @param clientId
     */
    void deleteTrailerByClientId(Long clientId);
    /**
     * 根据客户端id删除关联表中的数据（车头）
     * @param clientId
     */
    void deleteVehicleByClientId(Long clientId);
    /**
     * 根据客户端id删除关联表中的数据（微信）
     * @param clientId
     */
    void deleteWechatByClientId(Long clientId);
//    /**
//     * 根据客户端id删除关联表中的数据（子表对账模板）
//     * @param clientId
//     */
    void deleteStatementTemplateByClientId(Long clientId);
    //==========================微信绑定========================================

    /**
     * 获得外援微信用户与客户设置关系分页
     *
     * @return 外援微信用户与客户设置关系分页
     */
    ClientSettingsPage<WechatBindRespVO> getForeignWechatClientPage(@Param("page") ClientSettingsPage<WechatBindRespVO> page,
                                                                             @Param("pageReqVO") WechatBindPageReqVO pageReqVO);



    /**
     * 查看当前微信绑定详情
     * @param id
     * @return
     */
    WechatBindDetailsDTO selectWechatBindDetailsByWechatId(@Param("id")Long id);

    /**
     * 根据微信客户id查询绑定的客户
     * @param wechatId 微信客户id
     * @return
     */
    List<CustomerDTO> selectListByWechatId(Long wechatId);

    /**
     * 根据微信绑定id查询关联表中关联的客户(用于更新操作)
     * @param wechatId
     * @return ids
     */
    List<Long> selectCustomerIdByWechatId(Long wechatId);


    List<String> selectClientSettingWechat(@Param("id") Long id);


    List<String> selectClientSettingMain(@Param("id") Long id);

    List<String> selectClientSettingTrailer(@Param("id") Long id);


}
