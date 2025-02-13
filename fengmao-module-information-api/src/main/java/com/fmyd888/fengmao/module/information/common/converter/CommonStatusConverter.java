package com.fmyd888.fengmao.module.information.common.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @Title: enabledConverter
 * @Author: huanhuan
 * @Date: 2024-04-02 14:43
 * @Description:
 *  通用状态转化器，用于导出转换
 */

public class CommonStatusConverter implements Converter<Boolean> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<String> convertToExcelData(Boolean date, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return date ? new WriteCellData<>("是") : new WriteCellData<>("否");
    }

}
