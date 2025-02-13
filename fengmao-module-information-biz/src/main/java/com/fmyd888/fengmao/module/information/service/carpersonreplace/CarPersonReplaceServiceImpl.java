package com.fmyd888.fengmao.module.information.service.carpersonreplace;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplaceListReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplacePageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplaceRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplaceSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.carpersonreplace.CarPersonReplaceDO;
import com.fmyd888.fengmao.module.information.dal.mysql.car.CarMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.carpersonreplace.CarPersonReplaceMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.CAR_PERSON_REPLACE_NOT_EXISTS;

/**
 * 车辆人员更换记录 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class CarPersonReplaceServiceImpl implements CarPersonReplaceService {

    @Resource
    private CarPersonReplaceMapper carPersonReplaceMapper;
    @Resource
    private CarMapper carMapper;

    @Override
    public Long createCarPersonReplace(CarPersonReplaceSaveReqVO createReqVO) {
        // 插入
        CarPersonReplaceDO carPersonReplace = BeanUtils.toBean(createReqVO, CarPersonReplaceDO.class);
        carPersonReplaceMapper.insert(carPersonReplace);

        // 返回
        return carPersonReplace.getId();
    }

    @Override
    public void updateCarPersonReplace(CarPersonReplaceSaveReqVO updateReqVO) {
        // 校验存在
        validateCarPersonReplaceExists(updateReqVO.getId());
        // 更新
        CarPersonReplaceDO updateObj = BeanUtils.toBean(updateReqVO, CarPersonReplaceDO.class);
        carPersonReplaceMapper.updateById(updateObj);


    }

    private void validateCarPersonReplaceExists(Long id) {
        if (carPersonReplaceMapper.selectById(id) == null) {
            throw exception(CAR_PERSON_REPLACE_NOT_EXISTS);
        }
    }

    @Override
    public Page<CarPersonReplaceRespVO> selectPageByKeyword(CarPersonReplacePageReqVO pageReqVO) {
        Page<CarPersonReplaceRespVO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        String collationCode = pageReqVO.getCollationCode();
        if ("0".equals(collationCode)) {
            pageReqVO.setCollationValue("asc");
        } else if ("1".equals(collationCode)) {
            pageReqVO.setCollationValue("desc");
        }
        Page<CarPersonReplaceRespVO> pageResultVO = carPersonReplaceMapper.selectPageByKeyword(page, pageReqVO);
        return pageResultVO;
    }

    @Override
    public List<CarPersonReplaceDO> getCarPersonReplaceList(CarPersonReplaceListReqVO listReqVO) {
//        return carPersonReplaceMapper.selectList(listReqVO);  //不用代码自动生成这个方法
        LambdaQueryWrapper<CarPersonReplaceDO> up = new LambdaQueryWrapper<>();
        up.eq(listReqVO.getCarId() != null,CarPersonReplaceDO::getCarId,listReqVO.getCarId()) ; //自己写条件，不用代码自动生成方法
        up.eq(listReqVO.getFleetId()!= null,CarPersonReplaceDO::getFleetId,listReqVO.getFleetId()); //自己写条件，不用代码自动生成方法
        up.eq(listReqVO.getDeptId() != null,CarPersonReplaceDO::getDeptId,listReqVO.getDeptId()) ;//自己写条件，不用代码自动生成方法
        up.eq(listReqVO.getStatus() != null,CarPersonReplaceDO::getStatus,listReqVO.getStatus()) ;//自己写条件，不用代码自动生成方法

        List<CarPersonReplaceDO> carPersonReplaceDOS = carPersonReplaceMapper.selectList(up);
        return carPersonReplaceDOS;
    }

}