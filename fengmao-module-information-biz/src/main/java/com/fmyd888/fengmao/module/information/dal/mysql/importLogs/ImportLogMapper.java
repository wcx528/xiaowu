package com.fmyd888.fengmao.module.information.dal.mysql.importLogs;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.dal.dataobject.importLogs.ImportLogDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fmyd888.fengmao.module.information.controller.admin.importLogs.vo.*;
import org.springframework.util.StringUtils;

/**
 * 导入日志 Mapper
 *
 * @author 小逺
 */
@Mapper
public interface ImportLogMapper extends BaseMapperX<ImportLogDO> {

    default PageResult<ImportLogDO> selectPage(ImportLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ImportLogDO>()
                .eqIfPresent(ImportLogDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(ImportLogDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(ImportLogDO::getUpdateTime, reqVO.getUpdateTime())
                .eqIfPresent(ImportLogDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(ImportLogDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ImportLogDO::getErrMessage, reqVO.getErrMessage())
                .orderByDesc(ImportLogDO::getId));
    }

    default List<ImportLogDO> selectList(ImportLogListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ImportLogDO>()
                .eqIfPresent(ImportLogDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(ImportLogDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(ImportLogDO::getUpdateTime, reqVO.getUpdateTime())
                .eqIfPresent(ImportLogDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(ImportLogDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ImportLogDO::getErrMessage, reqVO.getErrMessage())
                .orderByDesc(ImportLogDO::getId));
    }


    /**
    * 获取分页信息【平铺】
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<ImportLogDO> selectJoinTilePage(ImportLogPageReqVO reqVO) {
        Page<ImportLogDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), ImportLogDO.class,
        new MPJLambdaWrapper<ImportLogDO>()
            .selectAll(ImportLogDO.class)
            .selectAs("t1.nickname", ImportLogDO::getCreatorName)
            .leftJoin("system_users t1 on t1.id = t.creator")
            .eq(ObjectUtil.isNotEmpty(reqVO.getCreator()),ImportLogDO::getCreator, reqVO.getCreator())
            .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()),ImportLogDO::getCreateTime,ArrayUtils.get(reqVO.getCreateTime(),0), ArrayUtils.get(reqVO.getCreateTime(),1))
            .between(ArrayUtil.isNotEmpty(reqVO.getUpdateTime()),ImportLogDO::getUpdateTime,ArrayUtils.get(reqVO.getUpdateTime(),0), ArrayUtils.get(reqVO.getUpdateTime(),1))
            .eq(ObjectUtil.isNotEmpty(reqVO.getTaskId()),ImportLogDO::getTaskId, reqVO.getTaskId())
            .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()),ImportLogDO::getStatus, reqVO.getStatus())
            .eq(ObjectUtil.isNotEmpty(reqVO.getErrMessage()),ImportLogDO::getErrMessage, reqVO.getErrMessage())
            .disableSubLogicDel()
            .orderByDesc(ImportLogDO::getId));
        return new PageResult<ImportLogDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
    * 自定义批量插入方法
    * 此方法是mybatis方法，没有拦截给creator，updater，dept_id这三个字段赋值，插入前必须先给穿入的list中这些字段先赋值再执行此方法
    * @param list 插入的数据
    */
    void batchInsertX(@Param("list") List<ImportLogDO> list);
}