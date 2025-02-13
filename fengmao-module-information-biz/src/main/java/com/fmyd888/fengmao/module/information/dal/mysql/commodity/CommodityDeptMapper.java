package com.fmyd888.fengmao.module.information.dal.mysql.commodity;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/11/22 16:04
 * @Version: 1.0
 * @Description: 部门与部门组织 Mapper
 */
@Mapper
public interface CommodityDeptMapper extends BaseMapperX<CommodityDeptDO> {
    default List<CommodityDeptDO> selectListByIds(List<Long> companyIds) {
        return selectJoinList(CommodityDeptDO.class, new MPJLambdaWrapper<CommodityDeptDO>()
                .selectAll(CommodityDeptDO.class)
                .selectAs(DeptDO::getName, CommodityDeptDO::getDeptName)
                .leftJoin(DeptDO.class, DeptDO::getId, CommodityDeptDO::getDeptId)
                .disableSubLogicDel());
    }
}
