package com.fmyd888.fengmao.module.information.dal.mysql.locationRecord;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.CarMileageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆GPS里程 Mapper
 *
 * @author 小逺
 */
@Mapper
public interface CarMileageMapper extends BaseMapperX<CarMileageDO> {
    /**
     * 自定义批量插入方法
     * 此方法是mybatis方法，没有拦截给creator，updater，dept_id这三个字段赋值，插入前必须先给穿入的list中这些字段先赋值再执行此方法
     * @param list 插入的数据
     */
    void batchInsertX(@Param("list") List<CarMileageDO> list);
}
