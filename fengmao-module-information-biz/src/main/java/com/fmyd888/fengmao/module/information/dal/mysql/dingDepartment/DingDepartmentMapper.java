package com.fmyd888.fengmao.module.information.dal.mysql.dingDepartment;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.dingDepartment.DingDepartmentDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @Title: DingDepartmentMapper
 * @Author: huanhuan
 * @Date: 2024-03-27 22:13
 * @Description:
 */
@Mapper
public interface DingDepartmentMapper extends BaseMapperX<DingDepartmentDO> {
    @Delete("DELETE FROM fm_ding_department WHERE create_time < #{currentTime}")
    int deleteDuplicateRecords(@Param("currentTime") LocalDateTime currentTime);
}
