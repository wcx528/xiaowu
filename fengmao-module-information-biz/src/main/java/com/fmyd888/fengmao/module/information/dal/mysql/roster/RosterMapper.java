package com.fmyd888.fengmao.module.information.dal.mysql.roster;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.roster.RosterDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @Title: RosterMapper
 * @Author: huanhuan
 * @Date: 2024-04-04
 * @Description:
 */
@Mapper
public interface RosterMapper extends BaseMapperX<RosterDO> {

    /**
     *  删除原先导入的信息
     * @param currentTime 当前时间
     * @return
     */
    @Delete("DELETE FROM fm_roster WHERE create_time < #{currentTime}")
    int deleteDuplicateRecords(@Param("currentTime") LocalDateTime currentTime);

    /**
     * 新的方法，用于删除重复的 userId，只保留每个 userId 最小的 id
     * @return
     */
    @Delete("DELETE FROM fm_roster WHERE id NOT IN (SELECT MIN(id) FROM fm_roster GROUP BY user_id)")
    int deleteDuplicateUserIds();

}
