package com.fmyd888.fengmao.module.information.dal.redis;

/**
 * 类功能描述：Information Redis Key 枚举类
 *
 * @author 小逺
 * @date 2024/04/25
 */
public interface RedisKeyConstants {

    /**
     * 客户精简列表缓存名称
     */
    String CUSTOMER_SIMPLE_LIST = "customer_tg_list";

    /**
     * 货物精简列表缓存名称
     */
    String COMMODITY_SIMPLE_LIST = "commodity_simple_list";

    /**
     * 货币精简列表缓存名称
     */
    String CURRENCY_SIMPLE_LIST = "currency_simple_list";

    /**
     * 车队精简列表缓存名称
     */
    String FLEET_SIMPLE_LIST = "fleet_simple_list";

    /**
     * 仓库精简列表缓存名称
     */
    String STORE_SIMPLE_LIST = "store_simple_list";

    /**
     * 税率精简列表缓存名称
     */
    String TAXRATES_SIMPLE_LIST = "taxrates_simple_list";

    /**
     * 计量单位精简列表缓存名称
     */
    String MEASUREMENT_SIMPLE_LIST = "measurement_simple_list";

    /**
     * 集装箱精简列表缓存名称
     */
    String CONTAINER_SIMPLE_LIST = "container_simple_list";

    /**
     * 油卡缓存名称
     */
    String OIL_CARD_LIST = "oil_card_list";

    /**
     * 维修客户缓存名称
     */
    String REPAIR_CUST_LIST = "repair_cust_list";

    /**
     * 缓存车辆数据
     */
    String CAR_LIST = "car_list";
}
