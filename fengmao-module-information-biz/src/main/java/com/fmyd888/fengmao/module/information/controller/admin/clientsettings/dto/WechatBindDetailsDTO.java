package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.CustomerDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author:wu
 * @create: 2024-05-06 19:10
 * @Description:
 */
@Data
public class WechatBindDetailsDTO {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户性别（1.男，2.女）
     */
    private Integer sex;
    /**
     * 头像
     */
    private String headImgUrl;
    /**
     * 备注
     */
    private String remark;
    /**
     * 绑定时间
     */
    private LocalDateTime createTime;

    /**
     * 关联表中的客户列表
     */
    private List<CustomerDTO> customerDTOList;
}
