package com.fmyd888.fengmao.module.information.api.taxrates.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Misaka
 * date: 2024/7/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaxratesRespDTO extends TaxratesBaseDTO {

    /**
     * 编号
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 税率已分配组织
     */
    private List<Long> organization;
}
