package com.fmyd888.fengmao.module.information.dal.mysql.locationRecord;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LocationRecordDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.MobileLocationRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 手机GPS定位 Mapper
 *
 * @author 小逺
 */
@Mapper
public interface MobileLocationRecordMapper extends BaseMapperX<MobileLocationRecordDO> {

    /**
     * 自定义批量插入方法
     * @param list 插入的数据
     */
    void batchInsertX(@Param("list") List<MobileLocationRecordDO> list);
}
