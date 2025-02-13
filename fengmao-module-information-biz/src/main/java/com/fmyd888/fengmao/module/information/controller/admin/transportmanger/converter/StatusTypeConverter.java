package com.fmyd888.fengmao.module.information.controller.admin.transportmanger.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @Title: StatusTypeConverter
 * @Author: huanhuan
 * @Date: 2024-04-02 11:39
 * @Description:
 *  状态类型转化器:
 *      状态类型，1:进行中，2：已完成，3：购买证快到期
 */

public class StatusTypeConverter implements Converter<Integer> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }


    @Override
    public WriteCellData<String> convertToExcelData(Integer date, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if(date == 1){
            return new WriteCellData<>("进行中");
        }
        else if(date == 2){
            return new WriteCellData<>("已完成");
        }
        else if(date == 3){
            return new WriteCellData<>("购买证快到期");
        }
        else{
            return new WriteCellData<>("其他");
        }
    }

}
