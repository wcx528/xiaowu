package com.fmyd888.fengmao.module.information.utils;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fmyd888.fengmao.framework.security.core.LoginUser;
import com.fmyd888.fengmao.framework.security.core.util.SecurityFrameworkUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lmy
 * @Date: 2023/11/14 09:53
 * @Version: 1.0
 * @Description: 业务对象编码规则生成器工具类
 * 主要用于生成当天流水号
 */

public class EncodingRulesGeneratorUtils {
    private static StringRedisTemplate redisTemplate = null;

    static {
        redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
    }

    /**
     * 通过redis自增生成流水号
     *
     * @param ruleType 规则类型
     * @param length   流水号位数，高位补 0
     * @return 生成的流水号
     */
    public static String createAutoID(String ruleType, int length) {
        //时间戳天数 后面拼接流水号 如果需要  可以加上时分秒
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateDay = sdf.format(new Date());
        Long tenantId = null;
        String key = null;
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (ObjUtil.isNotEmpty(loginUser)) {
            tenantId = loginUser.getTenantId();
            //这里是 Redis key的前缀，如: 租户:编号类型:日期  如果不需要去掉表名也可以
            key = MessageFormat.format("{0}:{1}:{2}", tenantId, ruleType, dateDay);
        } else {
            key = MessageFormat.format("{0}:{1}", ruleType, dateDay);
        }


        //查询 key 是否存在， 不存在返回 1 ，存在的话则自增加1
        Long autoID = redisTemplate.opsForValue().increment(key, 1);
        // 设置key过期时间, 保证每天的流水号从1开始
        if (autoID == 1) {
            redisTemplate.expire(key, getSecondsNextEarlyMorning(), TimeUnit.SECONDS);
        }
        //这里是 6 位id，如果位数不够可以自行修改 ，下面的意思是 得到上面 key 的 值，位数为6 ，不够的话在左边补 0 ，比如  110 会变成  000110
        String value = StringUtils.leftPad(String.valueOf(autoID), length, "0");
        //然后把 优化后的 ID 拼接
        String code = MessageFormat.format("{0}", value);
        return code;
    }

    public static List<String> createBatchAutoID(String ruleType, int length, int length1) {
        List<String> codes = new ArrayList<>();
        //时间戳天数 后面拼接流水号 如果需要  可以加上时分秒
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateDay = sdf.format(new Date());
        Long tenantId = null;
        String key = null;
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (ObjUtil.isNotEmpty(loginUser)) {
            tenantId = loginUser.getTenantId();
            //这里是 Redis key的前缀，如: 租户:编号类型:日期  如果不需要去掉表名也可以
            key = MessageFormat.format("{0}:{1}:{2}", tenantId, ruleType, dateDay);
        } else {
            key = MessageFormat.format("{0}:{1}", ruleType, dateDay);
        }


        //查询 key 是否存在， 不存在返回 1 ，存在的话则自增加1
        //获取第一个
        Long firstAutoID = redisTemplate.opsForValue().increment(key, 1);
        //获取最后一个
        Long lastAutoID = redisTemplate.opsForValue().increment(key, length1 - 1);
        // 设置key过期时间, 保证每天的流水号从1开始
        if (firstAutoID == 1) {
            redisTemplate.expire(key, getSecondsNextEarlyMorning(), TimeUnit.SECONDS);
        }
        //这里是 6 位id，如果位数不够可以自行修改 ，下面的意思是 得到上面 key 的 值，位数为6 ，不够的话在左边补 0 ，比如  110 会变成  000110
        for (Long autoID = firstAutoID; autoID <= lastAutoID; autoID++){
            String value = StringUtils.leftPad(String.valueOf(autoID), length, "0");
            codes.add(MessageFormat.format("{0}", value));
        }
        return codes;
    }

    /**
     * 判断当前时间距离第二天凌晨的秒数
     *
     * @return 返回值单位为[s:秒]
     */
    private static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }
}
