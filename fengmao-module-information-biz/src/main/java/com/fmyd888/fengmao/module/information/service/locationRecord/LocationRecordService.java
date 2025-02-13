package com.fmyd888.fengmao.module.information.service.locationRecord;

import java.math.BigDecimal;
import java.util.*;
import javax.validation.*;

import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LatestLocationRecordDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LocationRecordDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;

/**
 * 车辆GPS定位 Service 接口
 *
 * @author 小逺
 */
public interface LocationRecordService {

    /**
     * 创建车辆GPS定位
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLocationRecord(@Valid LocationRecordSaveReqVO createReqVO);

    /**
     * 更新车辆GPS定位
     *
     * @param updateReqVO 更新信息
     */
    void updateLocationRecord(@Valid LocationRecordSaveReqVO updateReqVO);

    /**
     * 删除车辆GPS定位
     *
     * @param id 编号
     */
    void deleteLocationRecord(Long id);

    /**
     * 获得车辆GPS定位
     *
     * @param id 编号
     * @return 车辆GPS定位
     */
    LocationRecordDO getLocationRecord(Long id);

    /**
     * 获得车辆GPS定位列表
     *
     * @param ids 编号
     * @return 车辆GPS定位列表
     */
    List<LocationRecordDO> getLocationRecordList(Collection<Long> ids);

    /**
     * 获得车辆GPS定位分页
     *
     * @param pageReqVO 分页查询
     * @return 车辆GPS定位分页
     */
    PageResult<LatestLocationRecordDO> getLocationRecordPage(LocationRecordPageReqVO pageReqVO);


    /**
     * 获得车辆GPS定位列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 车辆GPS定位列表
     */
    List<LocationRecordDO> getLocationRecordList(LocationRecordListReqVO exportReqVO);

    /**
     * 批量更新车辆GPS定位列表
     *
     * @param updateReqVOList 批量更新信息列表
     */
    void batchUpdateLocationRecord(List<LocationRecordSaveReqVO> updateReqVOList);

    /**
     * 批量删除车辆GPS定位列表
     *
     * @param ids 编号列表
     */
    void batchDeleteLocationRecord(List<Long> ids);

    /**
     * 导入车辆GPS定位返回预览结果
     *
     * @param importDatas     导入的excel数据
     * @param isUpdateSupport 是否支持更新
     */
    List<LocationRecordExcelVO> importPreviewList(List<LocationRecordExcelVO> importDatas, boolean isUpdateSupport);

    /**
     * 导入车辆GPS定位列表
     *
     * @param importReqVo 导入的excel数据
     */
    ImportResult importData(LocationRecordExcelVO importReqVo);

    /**
     * 功能描述：获取车辆当前位置
     *
     * @param carId 车辆ID
     * @author 小逺
     * @date 2024/06/21
     */
    LocationRecordRespVO getCarLocation(Long carId);

    /**
     * 功能描述：查询车辆轨迹
     *
     * @param reqVO
     * @return {@link List }<{@link List }<{@link BigDecimal }>>
     * @author 小逺
     * @date 2024/06/21
     */
    List<List<BigDecimal>> getCarTrajectory(LocationRecordListReqVO reqVO);

    /**
     * 功能描述：手机端GPS位置记录
     *
     * @param createReqVO
     * @return {@link Long }
     * @author 小逺
     * @date 2024/07/03
     */
    Long createMobileLocationRecord(List<LocationRecordMobileSaveReqVO> createReqVO);
}