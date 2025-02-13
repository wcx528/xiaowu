package com.fmyd888.fengmao.module.information.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author:wu
 * @create: 2024-04-29 10:18
 * @Description:
 */
@Data
public class ClientSettingsPage<T> extends Page<T> {
        public ClientSettingsPage(long current, long size) {
            super(current, size);
        }
}
