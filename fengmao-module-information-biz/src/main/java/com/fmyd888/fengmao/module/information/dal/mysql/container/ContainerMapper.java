package com.fmyd888.fengmao.module.information.dal.mysql.container;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.ContainerExportReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.ContainerPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.ContainerRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.container.ContainerDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 集装箱 Mapper
 *
 * @author 丰茂超管
 */
@Mapper
public interface ContainerMapper extends BaseMapperX<ContainerDO> {

    //default PageResult<ContainerDO> selectPage(ContainerPageReqVO reqVO) {
    //    return selectPage(reqVO, new LambdaQueryWrapperX<ContainerDO>()
    //            .eqIfPresent(ContainerDO::getContainerNumber, reqVO.getContainerNumber())
    //            .eqIfPresent(ContainerDO::getStatus, reqVO.getStatus())
    //            .eqIfPresent(ContainerDO::getCreator, reqVO.getCreator())
    //            .betweenIfPresent(ContainerDO::getCreateTime, reqVO.getCreateTime())
    //            .orderByDesc(ContainerDO::getId));
    //}

    default List<ContainerDO> selectList(ContainerExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ContainerDO>()
                .eqIfPresent(ContainerDO::getContainerNumber, reqVO.getContainerNumber())
                .eqIfPresent(ContainerDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ContainerDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(ContainerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ContainerDO::getId));
    }

    /**
     * 分页多条件查询
     *
     * @param page
     * @param reqVO
     * @return
     */
    Page<ContainerRespVO> selectByPage(@Param("page") Page<ContainerRespVO> page,
                                       @Param("pageReqVO") ContainerPageReqVO reqVO);

    default PageResult<ContainerDO> selectJoinTilePage(ContainerPageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<ContainerDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), ContainerDO.class,
                new MPJLambdaWrapper<ContainerDO>()
                        .selectAll(ContainerDO.class)//查询的主表字段
                        .selectAs(AdminUserDO::getNickname, ContainerDO::getCreatorName)
                        .selectAs(DeptDO::getName, ContainerDO::getCompanyName)
                        .leftJoin(AdminUserDO.class, AdminUserDO::getId, ContainerDO::getCreator)
                        .leftJoin(DeptDO.class, DeptDO::getId, ContainerDO::getCompanyId)
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()), ContainerDO::getCompanyId, reqVO.getCompanyId())
                        .like(StrUtil.isNotBlank(reqVO.getContainerNumber()), ContainerDO::getContainerNumber, reqVO.getContainerNumber())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()), ContainerDO::getStatus, reqVO.getStatus())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), ContainerDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .like(ObjectUtil.isNotEmpty(reqVO.getKeyword()), ContainerDO::getContainerNumber, reqVO.getKeyword())
                        .disableSubLogicDel()
                        .orderByDesc(ContainerDO::getId));
        return new PageResult<ContainerDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    default List<ContainerDO> selectJoinList(ContainerExportReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        return selectJoinList(ContainerDO.class,
                new MPJLambdaWrapper<ContainerDO>()
                        .selectAll(ContainerDO.class)//查询的主表字段
                        .selectAs(AdminUserDO::getNickname, ContainerDO::getCreatorName)
                        .selectAs(DeptDO::getName, ContainerDO::getCompanyName)
                        .leftJoin(AdminUserDO.class, AdminUserDO::getId, ContainerDO::getCreator)
                        .leftJoin(DeptDO.class, DeptDO::getId, ContainerDO::getCompanyId)
                        .eq(ObjectUtil.isNotEmpty(reqVO.getCompanyId()), ContainerDO::getCompanyId, reqVO.getCompanyId())
                        .like(StrUtil.isNotBlank(reqVO.getContainerNumber()), ContainerDO::getContainerNumber, reqVO.getContainerNumber())
                        .eq(ObjectUtil.isNotEmpty(reqVO.getStatus()), ContainerDO::getStatus, reqVO.getStatus())
                        .between(ArrayUtil.isNotEmpty(reqVO.getCreateTime()), ContainerDO::getCreateTime, ArrayUtils.get(reqVO.getCreateTime(), 0), ArrayUtils.get(reqVO.getCreateTime(), 1))
                        .like(ObjectUtil.isNotEmpty(reqVO.getKeyword()), ContainerDO::getContainerNumber, reqVO.getKeyword())
                        .disableSubLogicDel()
                        .orderByDesc(ContainerDO::getId));
    }

    default ContainerDO selectById(Long id) {
        return selectJoinOne(ContainerDO.class,
                new MPJLambdaWrapper<ContainerDO>()
                        .selectAll(ContainerDO.class)//查询的主表字段
                        .selectAs(AdminUserDO::getNickname, ContainerDO::getCreatorName)
                        .selectAs(DeptDO::getName, ContainerDO::getCompanyName)
                        .leftJoin(AdminUserDO.class, AdminUserDO::getId, ContainerDO::getCreator)
                        .leftJoin(DeptDO.class, DeptDO::getId, ContainerDO::getCompanyId)
                        .eq(ContainerDO::getId, id)
                        .disableSubLogicDel()
                        .orderByDesc(ContainerDO::getId));
    }
}
