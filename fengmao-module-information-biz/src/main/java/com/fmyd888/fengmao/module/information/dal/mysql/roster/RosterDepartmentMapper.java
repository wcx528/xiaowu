package com.fmyd888.fengmao.module.information.dal.mysql.roster;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.roster.RosterDepartmentDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @Title: EmployeeDepartmentMapper
 * @Author: huanhuan
 * @Date: 2024-03-28 23:55
 * @Description:
 */
@Mapper
public interface RosterDepartmentMapper extends BaseMapperX<RosterDepartmentDO> {

    @Delete("DELETE FROM fm_roster_department WHERE create_time < #{currentTime}")
    int deleteDuplicateRecords(@Param("currentTime") LocalDateTime currentTime);
}
