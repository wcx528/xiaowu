package com.fmyd888.fengmao.module.information.dal.mysql.clientsettings;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.util.collection.ArrayUtils;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.ClientSettingsPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.ClientSettingsRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.WechatBindPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.WechatBindRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customerwechat.CustomerWechatDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

/**
 * 外援微信用户与客户信息管理 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface CustomerWechatMapper extends BaseMapperX<CustomerWechatDO> {



}
