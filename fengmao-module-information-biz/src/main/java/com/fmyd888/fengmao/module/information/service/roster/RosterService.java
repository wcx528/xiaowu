package com.fmyd888.fengmao.module.information.service.roster;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fmyd888.fengmao.module.information.controller.admin.roster.vo.RosterRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.roster.RosterDO;
import com.taobao.api.ApiException;

import java.util.List;

/**
 * @Title: RosterService
 * @Author: huanhuan
 * @Date: 2024-04-04
 * @Description:
 */

public interface RosterService  extends IService<RosterDO> {

    List<RosterRespVO> getRosterSimpleList();
}
