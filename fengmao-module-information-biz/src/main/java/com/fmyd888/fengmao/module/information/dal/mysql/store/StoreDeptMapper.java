package com.fmyd888.fengmao.module.information.dal.mysql.store;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/11/23 10:08
 * @Version: 1.0
 * @Description:
 */
@Mapper
public interface StoreDeptMapper extends BaseMapperX<StoreDeptDO> {

    // 批量新增方法
    void batchInsertStoreDept(@Param("deptIds") List<Long> deptIds, @Param("storeId") Long storeId);

    // 批量删除方法
    void batchDeleteStoreDept(@Param("deptIds") List<Long> deptIds, @Param("storeId") Long storeId);

    /**
     * 功能描述：根据id集合获取仓库部门信息
     *
     * @param storeIds
     * @return {@link List }<{@link StoreDeptDO }>
     * @author 小逺
     * @date 2024/09/24
     */
    default List<StoreDeptDO> selectListByStoreIds(List<Long> storeIds) {
        return selectJoinList(StoreDeptDO.class,new MPJLambdaWrapper<StoreDeptDO>()
                .select(StoreDeptDO::getEntityId)
                .selectAs(DeptDO::getName, StoreDeptDO::getDeptName)
                .leftJoin(DeptDO.class, DeptDO::getId, StoreDeptDO::getDeptId)
                .in(StoreDeptDO::getEntityId, storeIds)
                .disableSubLogicDel());
    }
}
