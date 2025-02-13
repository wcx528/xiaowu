package com.fmyd888.fengmao.module.information.properties;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: lmy
 * @Date: 2024/01/05 17:37
 * @Version: 1.0
 * @Description:
 */
@Data
@ToString
public class GaoDeProperties {
    /**
     * 请求服务权限标识
     * 用户在高德地图官网申请Web服务API类型KEY
     */
    private String key = "b3acb32a52df7886bf17bff009948e8f";
    /**
     * 查询关键字
     */
    private String keywords;
    /**
     * 子级行政区
     * 规则：设置显示下级行政区级数（行政区级别包括：国家、省/直辖市、市、区/县、乡镇/街道多级数据）
     *
     * 可选值：0、1、2、3等数字，并以此类推
     *
     * 0：不返回下级行政区；
     *
     * 1：返回下一级行政区；
     *
     * 2：返回下两级行政区；
     *
     * 3：返回下三级行政区；
     */
    private String subdistrict;
    /**
     * 需要第几页数据
     * 最外层的districts最多会返回20个数据，若超过限制，请用page请求下一页数据。
     * 例如page=2；page=3。默认page=1
     */
    private String page;
    /**
     * 最外层返回数据个数
     */
    private String offset;
    /**
     * 返回结果控制
     */
    private String extensions;
    /**
     * 根据区划过滤
     * 按照指定行政区划进行过滤，填入后则只返回该省/直辖市信息
     * 需填入adcode，为了保证数据的正确，强烈建议填入此参数
     */
    private String filter;
    /**
     * 回调函数
     * callback值是用户定义的函数名称，此参数只在output=JSON时有效
     */
    private String callback;
    /**
     * 返回数据格式类型,可选值：JSON，XML
     */
    private String output;
}
