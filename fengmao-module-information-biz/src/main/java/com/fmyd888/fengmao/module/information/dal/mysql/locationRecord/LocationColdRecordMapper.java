package com.fmyd888.fengmao.module.information.dal.mysql.locationRecord;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LocationColdRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆gps轨迹冷 Mapper
 *
 * @author 小逺
 */
@Mapper
public interface LocationColdRecordMapper extends BaseMapperX<LocationColdRecordDO> {
    /**
     * 自定义批量插入方法
     * 此方法是mybatis方法，没有拦截给creator，updater，dept_id这三个字段赋值，插入前必须先给穿入的list中这些字段先赋值再执行此方法
     *
     * @param list 插入的数据
     */
    void batchInsertX(@Param("list") List<LocationColdRecordDO> list);

    /**
     * 数据备份
     * @param list 插入的数据
     */
    void batchBack(@Param("list") List<LocationColdRecordDO> list);

}
