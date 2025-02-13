package com.fmyd888.fengmao.module.information.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @Author: lmy
 * @Date: 2023/12/07 17:26
 * @Version: 1.0
 * @Description: 企查查接口返回状态码枚举 ,详情见： https://openapi.qcc.com/services/after/status
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    ///有效请求状态码
    STATUS_CODE_200(200, "查询成功"),
    STATUS_CODE_201(201, "查询无结果"),
    STATUS_CODE_202(202, "查询参数错误，请检查"),
    STATUS_CODE_205(205, "等待处理中"),
    STATUS_CODE_207(207, "请求数据的条目数超过上限（5000）"),
    STATUS_CODE_208(208, "此接口不支持此公司类型查询"),
    STATUS_CODE_209(209, "企业数量超过上限"),
    STATUS_CODE_213(213, "参数长度不能小于2"),
    STATUS_CODE_215(215, "不支持的查询关键字"),
    STATUS_CODE_218(218, "该企业暂不支持空壳扫描"),
    STATUS_CODE_219(219, "该企业暂不支持准入尽调"),
    STATUS_CODE_105(105, "接口已下线停用"),
    STATUS_CODE_110(110, "当前相同查询连续出错，请等2小时后重试"),

    //常见无效请求状态码------------------------
    STATUS_CODE_101(101, "当前的KEY无效或者还未生效中"),
    STATUS_CODE_102(102, "当前KEY已欠费"),
    STATUS_CODE_103(103, "当前KEY被暂停使用"),
    STATUS_CODE_104(104, "请求KEY异常，请联系管理员"),
    STATUS_CODE_106(106, "非法请求过多，请联系管理员"),
    STATUS_CODE_107(107, "被禁止的IP或者签名错误"),
    STATUS_CODE_108(108, "异常请求过多，请联系管理员"),
    STATUS_CODE_109(109, "请求超过每日系统限制"),
    STATUS_CODE_111(111, "接口权限未开通，请联系管理员"),
    STATUS_CODE_112(112, "您的账号剩余使用量已不足或已过期"),
    STATUS_CODE_113(113, "当前接口已被删除，请重新申请"),
    STATUS_CODE_114(114, "当前接口已被禁用，请联系管理员"),
    STATUS_CODE_115(115, "身份验证错误或者已过期"),
    STATUS_CODE_116(116, "请求超过每日调用总量限制"),
    STATUS_CODE_117(117, "当前不支持的请求参数调用量过多"),
    STATUS_CODE_118(118, "当前接口不支持此方式的调用"),
    STATUS_CODE_119(119, "您的账号出现异常，请联系管理员"),
    STATUS_CODE_120(120, "系统流量异常，请稍后再试"),
    STATUS_CODE_121(121, "数据不能出境"),
    STATUS_CODE_122(122, "请求超过系统并发限制"),
    STATUS_CODE_123(123, "您的请求已达未认证权益上限，请及时认证"),
    STATUS_CODE_124(124, "系统维护中，暂时无法下单"),
    STATUS_CODE_199(199, "系统未知错误，请联系技术客服"),
    STATUS_CODE_203(203, "系统查询有异常，请联系技术人员"),
    STATUS_CODE_214(214, "您还未购买过该接口，请先购买"),
    STATUS_CODE_223(223, "查询参数模糊，无法获取结果"),
    STATUS_CODE_224(224, "查询参数无效"),
    STATUS_CODE_999(999, "其他错误");

    private Integer code;
    private String message;

}
