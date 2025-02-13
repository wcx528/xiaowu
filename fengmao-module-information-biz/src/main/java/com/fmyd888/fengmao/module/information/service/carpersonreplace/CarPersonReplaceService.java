package com.fmyd888.fengmao.module.information.service.carpersonreplace;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplaceListReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplacePageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplaceRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplaceSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.carpersonreplace.CarPersonReplaceDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 车辆人员更换记录 Service 接口
 *
 * @author 丰茂
 */
public interface CarPersonReplaceService {

    /**
     * 创建车辆人员更换记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCarPersonReplace(@Valid CarPersonReplaceSaveReqVO createReqVO);

    /**
     * 更新车辆人员更换记录
     *
     * @param updateReqVO 更新信息
     */
    void updateCarPersonReplace(@Valid CarPersonReplaceSaveReqVO updateReqVO);


    /**
    * 获得车辆人员更换记录分页
    *
    * @param pageReqVO 分页查询
    * @return 车辆人员更换记录分页
    */
    Page<CarPersonReplaceRespVO> selectPageByKeyword(CarPersonReplacePageReqVO pageReqVO);


    /**
     * 获得车辆人员更换记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 车辆人员更换记录列表
     */
    List<CarPersonReplaceDO> getCarPersonReplaceList(CarPersonReplaceListReqVO exportReqVO);


}
