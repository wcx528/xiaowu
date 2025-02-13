package com.fmyd888.fengmao.module.information.enums;

import com.fmyd888.fengmao.framework.common.exception.ErrorCode;

/**
 * @Title: ErrorCodeConstants
 * @Author: huanhuan
 * @Date: 2023 102023/10/23 18:31
 * @description: 模块 information 错误码区间 [1_015_000_000 ~ 1_016_000_000)
 */
public interface ErrorCodeConstants {

    //车头 1_015_001
    ErrorCode MAIN_VEHICLE_NO_EXISTS = new ErrorCode(1_015_001_001, "车头不存在");
    ErrorCode MAIN_VEHICLE_NO_CODEBUSINESSTYPE = new ErrorCode(1_015_001_003, "没有找到附件数据类型");
    ErrorCode MAIN_VEHICLE_NO_DOCMENT_DOWNLOAD = new ErrorCode(1_015_001_004, "车头档案下载出错！");
    ErrorCode MAIN_VEHICLE_NO_QUREY = new ErrorCode(1_015_001_005, "查询不到车头信息");
    ErrorCode MAIN_VEHICLE_NOT_EXISTS = new ErrorCode(1_015_001_006, "车头信息不存在更新失败！");
    ErrorCode MAIN_VEHICLE_BAND_CAR = new ErrorCode(1_015_001_007, "车头{}已绑定车辆信息不能禁用");
    ErrorCode MAIN_VEHICLE_ERROR01 = new ErrorCode(1_015_001_008, "机动车登记编号:{}已存在！");
    ErrorCode MAIN_VEHICLE_ERROR02 = new ErrorCode(1_015_001_009, "车牌号:{}已存在！");
    ErrorCode MAIN_VEHICLE_ERROR03 = new ErrorCode(1_015_001_010, "编号:{}已存在！");
    ErrorCode MAIN_VEHICLE_IMPORT = new ErrorCode(1_015_001_011, "导入车头数据不能为空");
    ErrorCode MAIN_VEHICLE_IMPORT_DEPT = new ErrorCode(1_015_001_012, "导入车头数据中的部门{}不存在");
    ErrorCode VEHICLE_ALREADY_EXISTS01 = new ErrorCode(1_015_001_013, "该信息已存在注意查看是否已有相同的编码");
    ErrorCode VEHICLE_ALREADY_EXISTS02 = new ErrorCode(1_015_001_014, "该信息已存在注意查看是否已有相同的车牌");
    ErrorCode VEHICLE_ALREADY_EXISTS03 = new ErrorCode(1_015_001_015, "该信息已存在注意查看是否已有相同的车架号");

    //车挂错误枚举  1_015_002
    ErrorCode TRAILER_IS_REPLACE = new ErrorCode(1_015_002_001, "车挂已绑定，不许重新覆盖");
    ErrorCode TRAILER_NO_REPLACE = new ErrorCode(1_015_002_002, "车挂不允许覆盖请重新选择");
    ErrorCode TRAILER_NO_CODEBUSINESSTYPE = new ErrorCode(1_015_002_003, "车挂没有找到附件数据类型");
    ErrorCode TRAILER_ALREADY_EXISTS = new ErrorCode(1_015_002_004, "车挂信息已经存在" +
            ""+"注意查看是否已有相同的编码、车牌或车架号");
    ErrorCode TRAILER_NO_DOCMENT_DOWNLOAD = new ErrorCode(1_015_002_005, "车挂档案下载出错！");
    ErrorCode TRAILER_NO_QUREY = new ErrorCode(1_015_002_006, "查询不到车挂信息");
    ErrorCode TRAILER_NOT_EXISTS = new ErrorCode(1_015_002_007, "{}车挂档案不存在");
    ErrorCode TRAILER_IMPORT = new ErrorCode(1_015_002_008, "导入车挂数据不能为空");


    //车辆信息错误枚举  1_015_003
    ErrorCode CAR_ERROR_01 = new ErrorCode(1_015_003_001, "车辆非普类型,必须有副驾或押运员其中一个同行！");
    ErrorCode CAR_ERROR_02 = new ErrorCode(1_015_003_002, "不当前车头不允许修改");
    ErrorCode CAR_ERROR_03 = new ErrorCode(1_015_003_003, "不private BigDecimal annualSalary;不允许修改");
    ErrorCode CAR_ERROR_04 = new ErrorCode(1_015_003_004, "传入类型报错");
    ErrorCode CAR_NOT_EXISTS = new ErrorCode(1_015_003_005, "车辆档案不存在");
    ErrorCode CAR_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_003_006, "导入车辆档案数据不能为空！");
    ErrorCode CAR_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_003_007, "未补充导入车辆档案数据预览接口逻辑！");
    ErrorCode CAR_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_003_008, "未补充导入车辆档案数据导入接口逻辑！");
    ErrorCode DEPUTY_AND_ESCORT_SELECTION_ERROR = new ErrorCode(1_015_003_009, "副驾和押运员只能选择一个！");
    ErrorCode CAR_ERROR_05 = new ErrorCode(1_015_003_010, "一辆车由一个车头+一个车挂组成，已绑定的车头不能重复绑定，注意校验");

    //开票信息错误枚举  1_015_004
    ErrorCode INVOICE_EXISTS = new ErrorCode(1_015_004_001, "开票信息不存在!");
    ErrorCode QCC_FAIL = new ErrorCode(1_015_004_002, "企查查请求信息失败，详细信息：{}");

    //证件管理错误枚举  1_015_005
    ErrorCode PURCHASE_MANGER_EXISTS = new ErrorCode(1_015_005_001, "{}购买证编号{}已存在!");
    ErrorCode PURCHASE_MANGER_NOT_EXISTS = new ErrorCode(1_015_005_002, "购买证管理不存在");

    // ========== 运输证管理错误枚举  1_015_006==========
    ErrorCode TRANSPORT_MANGER_NOT_EXISTS = new ErrorCode(1_015_006_001, "运输证管理不存在");
    ErrorCode TRANSPORT_MANGER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_006_002, "导入运输证管理数据不能为空！");
    ErrorCode TRANSPORT_MANGER_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_006_003, "未补充导入运输证管理数据预览接口逻辑！");
    ErrorCode TRANSPORT_MANGER_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_006_004, "未补充导入运输证管理数据导入接口逻辑！");
    ErrorCode TRANSPORT_MANGER_MAIN_VEHICLE_NOT_EXISTS = new ErrorCode(1_015_006_004, "车头号不存在！");

    // ========== 运输证明细错误枚举  1_015_007 ==========
    ErrorCode TRANSPORT_DETAIL_NOT_EXISTS = new ErrorCode(1_015_007_001, "运输证明细不存在");
    ErrorCode TRANSPORT_DETAIL_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_007_002, "导入运输证明细数据不能为空！");
    ErrorCode TRANSPORT_DETAIL_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_007_003, "未补充导入运输证明细数据预览接口逻辑！");
    ErrorCode TRANSPORT_DETAIL_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_007_004, "未补充导入运输证明细数据导入接口逻辑！");

    // ========== 运输证办理车辆关联错误枚举  1_015_08 ==========
    ErrorCode TRANSPORT_CAR_NOT_EXISTS = new ErrorCode(1_015_008_001, "运输证办理车辆关联不存在");
    ErrorCode TRANSPORT_CAR_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_008_002, "导入运输证办理车辆关联数据不能为空！");
    ErrorCode TRANSPORT_CAR_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_008_003, "未补充导入运输证办理车辆关联数据预览接口逻辑！");
    ErrorCode TRANSPORT_CAR_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_008_004, "未补充导入运输证办理车辆关联数据导入接口逻辑！");
    ErrorCode TRANSPORT_CAR_ERROR = new ErrorCode(1_015_008_005, "车挂已经被车辆【{}】占用！，替换后该车可能无法正常运行，是否进行替换;");
    ErrorCode TRANSPORT_CAR_ERROR01 = new ErrorCode(1_015_008_006, "主驾驶已经被车辆【{}】占用！，替换后该车可能无法正常运行，是否进行替换;");
    ErrorCode TRANSPORT_CAR_ERROR02 = new ErrorCode(1_015_008_007, "副驾驶已经被车辆    【{}】占用！，替换后该车可能无法正常运行，是否进行替换;");
    ErrorCode TRANSPORT_CAR_ERROR03 = new ErrorCode(1_015_008_008, "押运已经被车辆【{}】占用！，替换后该车可能无法正常运行，是否进行替换;");
    ErrorCode TRANSPORT_CAR_ERROR04 = new ErrorCode(1_015_008_009, "用户：【{}】 未购买任何保险，有重大安全隐患，不允许更换，请联系人力资源部。");
    ErrorCode TRANSPORT_CAR_ERROR05 = new ErrorCode(1_015_008_010, "主驾/副驾/押运员名称相同，请重新选择！");
    ErrorCode TRANSPORT_CAR_ERROR06 = new ErrorCode(1_015_008_011, "您更换的车挂【{}】已经被【{}】申请更换审批，请重新选择！");

    // ========== 货物错误枚举 1_015_009 ==========
    ErrorCode DUPLICATE_GOODS_NAME = new ErrorCode(1_015_009_001, "货物名称【{}】重复");
    ErrorCode GOODS_NAME_NOT_EXISTS = new ErrorCode(1_015_009_002, "货物类别【{}】无效");
    ErrorCode COMMODITY_NOT_EXISTS = new ErrorCode(1_015_009_003, "货物管理不存在");
    ErrorCode COMMODITY_TYPE_NOT_EXISTS = new ErrorCode(1_015_009_004, "不满足条件：货物类别为【危化货品】或【普通货品】时才必填，不是的时候不填【货物规格】和【运输对应】");
    ErrorCode DUPLICATE_GOODS_NAME_SPECIFICATION = new ErrorCode(1_015_009_005, "添加货物名称、规格和已有的数据重复！请重新选择");
    ErrorCode COMMODITY_IDS_NOT_EXISTS = new ErrorCode(1_015_009_006, "请先勾选安全告知卡再进行下载！");

    // ========== 货物错误枚举 1_015_010 ==========
    ErrorCode STORE_NOT_EXISTS = new ErrorCode(1_015_010_001, "仓库不存在");
    ErrorCode DUPLICATE_STORE_NAME = new ErrorCode(1_015_010_002, "仓库名称【{}】重复");
    ErrorCode STORE_IMPORT = new ErrorCode(1_015_010_002, "仓库名称【{}】重复");

    // ========== 集装箱错误枚举 1_015_011 ==========
    ErrorCode CONTAINER_NOT_EXISTS = new ErrorCode(1_015_011_001, "集装箱【{}】不存在");
    ErrorCode DUPLICATE_CONTAINER_NUNMBER = new ErrorCode(1_015_011_002, "集装箱编号【{}】重复");
    ErrorCode CONTAINER_NOT_EXISTS1 = new ErrorCode(1_015_011_003, "集装箱不存在，请刷新重试！");

    // ========== 其他合同资料错误枚举 1_015_012 ==========
    ErrorCode CONTRACT_NOT_EXISTS = new ErrorCode(1_015_012_001, "合同资料不存在");
    ErrorCode DUPLICATE_CONTRACT_NAME = new ErrorCode(1_015_012_002, "合同类型名称【{}】重复");

    // ========== 其他货币错误枚举 1_015_013 ==========
    ErrorCode CURRENCY_NOT_EXISTS = new ErrorCode(1_015_013_001, "货币已存在");
    ErrorCode DUPLICATE_CURRENCY_NAME = new ErrorCode(1_015_013_002, "货币名称名称【{}】重复");
    ErrorCode DUPLICATE_SYMBOL_NAME = new ErrorCode(1_015_013_003, "货币符号名称【{}】重复");
    ErrorCode DUPLICATE_IDENTIFY_NAME = new ErrorCode(1_015_013_004, "货币代码名称【{}】重复");

    // ========== 其他税率错误枚举 1_015_014 ==========
    ErrorCode TAXRATES_NOT_EXISTS = new ErrorCode(1_015_014_001, "税率已存在");
    ErrorCode DUPLICATE_TAXRATES_NAME = new ErrorCode(1_015_014_002, "税率名称名称后者税率值重复，请重新确认好再输入！");
    ErrorCode TAXRATES_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_014_003, "导入税率数据不能为空！");
    ErrorCode TAXRATES_IMPORT_ERR = new ErrorCode(1_015_014_004, "导入失败！详情：{}");

    // ========== 其他计量单位错误枚举 1_015_015 ==========
    ErrorCode MEASUREMENT_NOT_EXISTS = new ErrorCode(1_015_015_001, "计量单位已存在");
    ErrorCode DUPLICATE_MEASUREMENT_NAME = new ErrorCode(1_015_015_002, "计量单位名称【{}】重复");

    // ========== 其他业务员错误枚举 1_015_016 ==========
    ErrorCode SALESMAN_NOT_EXISTS = new ErrorCode(1_015_016_001, "业务员不存在");
    ErrorCode DUPLICATE_SALESMAN_NAME = new ErrorCode(1_015_016_002, "业务员已被绑定，请重新选择！");
    ErrorCode DUPLICATE_SALESMAN_USERID_NAME = new ErrorCode(1_015_016_003, "业务员UserId：【{}】重复");
    ErrorCode SALESMAN_COUNT_MAX = new ErrorCode(1_015_016_004, "该用户已被绑定，请重新选择！");
    ErrorCode SALESMAN_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_016_005, "导入业务员数据不能为空！");
    ErrorCode SALESMAN_DESCRIPTION_EXISTS = new ErrorCode(1_015_016_006, "导入业务员数据不能为空！");

    // ========== 地址错误枚举 1_015_017 ==========
    ErrorCode ADDRESS_NOT_EXISTS = new ErrorCode(1_015_017_001, "地址不存在");
    ErrorCode FULL_ADDRESS_IS_EXISTS = new ErrorCode(1_015_017_002, "该地址已存在，请重新选择！");

    // ========== 基线相关错误枚举 1_015_018 ==========
    ErrorCode CREATE_BASELINE_NOT_EXISTS = new ErrorCode(1_015_018_001, "创建该基线的所属公司、装卸货厂家、装卸货地址，运输类型、运输介质 ，在该时间段已维护有路线，不允许重复维护");
    ErrorCode UPDATE_BASELINE_NOT_EXISTS = new ErrorCode(1_015_018_002, "更新基线【{}】的所属公司、运输类型、运输介质、装卸货厂家、装卸货地址在该时间段已维护有路线，不允许重复维护");
    ErrorCode BASELINE_EXISTS = new ErrorCode(1_015_018_003, "基线不存在");
    ErrorCode BASELINE_CARR_CONTRACT_EXISTS = new ErrorCode(1_015_018_004, "当前承运公司和托运公司未维护合同，请先维护！");
    ErrorCode BASELINE_IMPORT_EXISTS = new ErrorCode(1_015_018_004, "导入基线数据不能为空！");

    // ========== 员工相关错误枚举 1_015_020 ==========
    ErrorCode EMPLOYEE_NOT_EXISTS = new ErrorCode(1_015_020_001, "员工信息不存在");
    ErrorCode EMPLOYEE_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_020_002, "导入员工数据不能为空！");
    ErrorCode EMPLOYEE_DESCRIPTION_EXISTS = new ErrorCode(1_015_020_003, "导入员工数据不能为空！");

    // ========== 薪资规则配置 1_015_021 ==========
    ErrorCode SALARY_RULE_NOT_EXISTS = new ErrorCode(1_015_021_001, "薪资规则配置不存在");
    ErrorCode SALARY_RULE_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_021_002, "导入薪资规则配置数据不能为空！");
    ErrorCode SALARY_RULE_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_021_003, "未补充导入薪资规则配置数据预览接口逻辑！");
    ErrorCode SALARY_RULE_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_021_004, "未补充导入薪资规则配置数据导入接口逻辑！");


    // ========== 系数维护错误枚举 1_015_011 ==========
    ErrorCode COEFFICIENT_NOT_EXISTS = new ErrorCode(1_015_022_001, "系数维护不存在");
    ErrorCode LOADING_RATE_NOT_EXISTS = new ErrorCode(1_015_022_002, "装载率系数明细不存在");
    ErrorCode LOADING_RATE_EXISTS = new ErrorCode(1_015_022_002, "装载率系数明细已存在");
    ErrorCode MILEAGE_ACCOUNTING_NOT_EXISTS = new ErrorCode(1_015_022_002, "工资里程核算系数明细不存在");
    ErrorCode MILEAGE_ACCOUNTING_EXISTS = new ErrorCode(1_015_022_002, "工资里程核算系数明细已存在");
    ErrorCode MAINTENANCE_COSTS_NOT_EXISTS = new ErrorCode(1_015_022_002, "维修费用系数维护明细不存在");
    ErrorCode MAINTENANCE_COSTS_EXISTS = new ErrorCode(1_015_022_002, "维修费用系数维护明细已存在");
    ErrorCode COEFFICIENT_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_022_002, "导入系数维护数据不能为空！");
    ErrorCode COEFFICIENT_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_022_002, "未补充导入系数维护数据预览接口逻辑！");
    ErrorCode COEFFICIENT_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_022_002, "未补充导入系数维护数据导入接口逻辑！");

    // ========== 客户端设置  ==========
    ErrorCode CLIENT_SETTINGS_NOT_EXISTS = new ErrorCode(1_015_023_001, "客户端设置不存在");
    ErrorCode STATEMENT_TEMPLATE_NOT_EXISTS = new ErrorCode(1_015_023_002, "子表_对账单模板不存在");
    ErrorCode STATEMENT_TEMPLATE_EXISTS = new ErrorCode(1_015_023_003, "子表_对账单模板已存在");
    ErrorCode CLIENT_SETTINGS_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_023_004, "导入客户端设置数据不能为空！");
    ErrorCode CLIENT_SETTINGS_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_023_005, "未补充导入客户端设置数据预览接口逻辑！");
    ErrorCode CLIENT_SETTINGS_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_023_006, "未补充导入客户端设置数据导入接口逻辑！");
    ErrorCode CLIENT_SETTINGS__EXISTS = new ErrorCode(1_015_023_007, "客户端设置已有相同的数据！请重新选择");


    // ========== 绑定微信用户 TODO 补充编号 ==========
    ErrorCode WECHAT_BIND_NOT_EXISTS = new ErrorCode(1_015_024_001, "绑定微信用户不存在");
    ErrorCode WECHAT_BIND_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_024_002, "导入绑定微信用户数据不能为空！");
    ErrorCode WECHAT_BIND_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_024_003, "未补充导入绑定微信用户数据预览接口逻辑！");
    ErrorCode WECHAT_BIND_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_024_004, "未补充导入绑定微信用户数据导入接口逻辑！");

    // ========== 油卡 ==========
    ErrorCode OIL_CARD_NOT_EXISTS = new ErrorCode(1_015_025_001, "油卡不存在");
    ErrorCode OIL_CARD_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_025_002, "导入油卡数据不能为空！");
    ErrorCode OIL_CARD_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_025_003, "未补充导入油卡数据预览接口逻辑！");
    ErrorCode OIL_CARD_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_025_004, "未补充导入油卡数据导入接口逻辑！");

    // ========== 外援车头与客户设置关系  ==========
    ErrorCode FOREIGN_VEHICLE_CLIENT_NOT_EXISTS = new ErrorCode(1_015_026_001, "外援车头与客户设置关系不存在");
    ErrorCode FOREIGN_VEHICLE_CLIENT_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_026_002, "导入外援车头与客户设置关系数据不能为空！");
    ErrorCode FOREIGN_VEHICLE_CLIENT_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_026_003, "未补充导入外援车头与客户设置关系数据预览接口逻辑！");
    ErrorCode FOREIGN_VEHICLE_CLIENT_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_026_004, "未补充导入外援车头与客户设置关系数据导入接口逻辑！");

    // ========== 外援车挂与客户设置关系  ==========
    ErrorCode FOREIGN_TRAILER_CLIENT_NOT_EXISTS = new ErrorCode(1_015_027_001, "外援车挂与客户设置关系不存在");
    ErrorCode FOREIGN_TRAILER_CLIENT_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_027_002, "导入外援车挂与客户设置关系数据不能为空！");
    ErrorCode FOREIGN_TRAILER_CLIENT_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_027_003, "未补充导入外援车挂与客户设置关系数据预览接口逻辑！");
    ErrorCode FOREIGN_TRAILER_CLIENT_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_027_004, "未补充导入外援车挂与客户设置关系数据导入接口逻辑！");

    // ========== 外援微信用户与客户设置关系 ==========
    ErrorCode FOREIGN_WECHAT_CLIENT_NOT_EXISTS = new ErrorCode(1_015_028_001, "外援微信用户与客户设置关系不存在");
    ErrorCode FOREIGN_WECHAT_CLIENT_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_028_002, "导入外援微信用户与客户设置关系数据不能为空！");
    ErrorCode FOREIGN_WECHAT_CLIENT_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_028_003, "未补充导入外援微信用户与客户设置关系数据预览接口逻辑！");
    ErrorCode FOREIGN_WECHAT_CLIENT_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_028_004, "未补充导入外援微信用户与客户设置关系数据导入接口逻辑！");

    // ========== 维修项目  ==========
    ErrorCode REPAIR_PROJECTS_NOT_EXISTS = new ErrorCode(1_015_029_001, "维修项目不存在");
    ErrorCode REPAIR_PROJECTS_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_029_002, "导入维修项目数据不能为空！");
    ErrorCode REPAIR_PROJECTS_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_029_003, "未补充导入维修项目数据预览接口逻辑！");
    ErrorCode REPAIR_PROJECTS_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_029_004, "未补充导入维修项目数据导入接口逻辑！");

    // ========== 社保基数维护 ==========
    ErrorCode SOCIAL_SECURITY_BASE_NOT_EXISTS = new ErrorCode(1_015_030_001, "社保基数维护不存在");
    ErrorCode SOCIAL_SECURITY_BASE_DEPT_NOT_EXISTS = new ErrorCode(1_015_030_002, "社保基数维护表和部门组织不存在");
    ErrorCode SOCIAL_SECURITY_BASE_DEPT_EXISTS = new ErrorCode(1_015_030_003, "社保基数维护表和部门组织已存在");
    ErrorCode SOCIAL_SECURITY_BASE_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_030_004, "导入社保基数维护数据不能为空！");
    ErrorCode SOCIAL_SECURITY_BASE_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_030_005, "未补充导入社保基数维护数据预览接口逻辑！");
    ErrorCode SOCIAL_SECURITY_BASE_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_030_006, "未补充导入社保基数维护数据导入接口逻辑！");

    // ========== 隐患排查项目维护表(主表) TODO 补充编号 ==========
    ErrorCode RISK_MAINTENANCE_NOT_EXISTS = new ErrorCode(1_015_031_001, "隐患排查项目维护表(主表)不存在");
    ErrorCode RISK_INSPECTION_ITEM_NOT_EXISTS = new ErrorCode(1_015_031_002, "检查类型表(子表)不存在");
    ErrorCode RISK_INSPECTION_ITEM_EXISTS = new ErrorCode(1_015_031_003, "检查类型表(子表)已存在");
    ErrorCode RISK_MAINTENANCE_COMMODITY_NOT_EXISTS = new ErrorCode(1_015_031_004, "隐患排查项目维护与介质关联不存在");
    ErrorCode RISK_MAINTENANCE_COMMODITY_EXISTS = new ErrorCode(1_015_031_005, "隐患排查项目维护与介质关联已存在");
    ErrorCode RISK_MAINTENANCE_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_031_006, "导入隐患排查项目维护表(主表)数据不能为空！");
    ErrorCode RISK_MAINTENANCE_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_031_007, "未补充导入隐患排查项目维护表(主表)数据预览接口逻辑！");
    ErrorCode RISK_MAINTENANCE_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_031_010, "未补充导入隐患排查项目维护表(主表)数据导入接口逻辑！");
    // ========== 车队表 TODO 补充编号 ==========
    ErrorCode FLEET_NOT_EXISTS = new ErrorCode(1_015_032_012, "车队表不存在");
    ErrorCode FLEET_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_032_013, "导入车队表数据不能为空！");
    ErrorCode FLEET_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_032_014, "未补充导入车队表数据预览接口逻辑！");
    ErrorCode FLEET_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_032_015, "未补充导入车队表数据导入接口逻辑！");
    // ========== 钉钉审批模板表 ==========
    ErrorCode PROCESS_CODE_EXISTS = new ErrorCode(1_015_033_001, "审批模板编码不存在，请先维护");
    ErrorCode APPROVAL_FAILURE = new ErrorCode(1_015_033_002, "发起审批失败，异常信息：{}");
    // ========== 钉钉审批模板表 ==========
    ErrorCode ROSTER_EXISTS = new ErrorCode(1_015_034_001, "用户关联的员工信息（花名册）不存在！");

    // ========== 车辆GPS定位 1_015_035_001 ==========
    ErrorCode LOCATION_RECORD_NOT_EXISTS = new ErrorCode(1_015_035_001, "车辆GPS定位不存在");
    ErrorCode LOCATION_RECORD_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_035_002, "导入车辆GPS定位数据不能为空！");
    ErrorCode LOCATION_RECORD_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_035_003, "未补充导入车辆GPS定位数据预览接口逻辑！");
    ErrorCode LOCATION_RECORD_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_035_004, "未补充导入车辆GPS定位数据导入接口逻辑！");
    // ========== 车辆人员更换记录  ==========
    ErrorCode CAR_PERSON_REPLACE_NOT_EXISTS = new ErrorCode(1_015_036_000, "车辆人员更换记录不存在");
    ErrorCode CAR_PERSON_REPLACE_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_036_001, "导入车辆人员更换记录数据不能为空！");
    ErrorCode CAR_PERSON_REPLACE_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_036_002, "未补充导入车辆人员更换记录数据预览接口逻辑！");
    ErrorCode CAR_PERSON_REPLACE_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_036_003, "未补充导入车辆人员更换记录数据导入接口逻辑！");

    // ========== 客户信息维护  ==========
    ErrorCode CUSTOMER_NAME_IS_EXITS = new ErrorCode(1_015_037_000, "当前公司名称已存在，请重新输入！");
    ErrorCode CUSTOMER_NAME_CODE_IS_EXITS = new ErrorCode(1_015_037_001, "当前编码已存在，请重新输入！");
    ErrorCode CUSTOMER_NAME_NOT_IS_EXITS = new ErrorCode(1_015_037_002, "找不到对应的供应商，请先维护！");

    // ========== AI  ==========
    ErrorCode AGENT_USER_NOT_EXISTS = new ErrorCode(1_015_040_000, "用户智能体对话不存在");
    ErrorCode AGENT_NOT_EXISTS = new ErrorCode(1_015_040_001, "智能体表不存在");
    // ========== 导入日志 1_015_041_000 ==========
    ErrorCode IMPORT_LOG_NOT_EXISTS = new ErrorCode(1_015_041_001, "导入日志不存在");
    ErrorCode IMPORT_LOG_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_041_002, "导入导入日志数据不能为空！");
    ErrorCode IMPORT_LOG_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_041_003, "未补充导入导入日志数据预览接口逻辑！");
    ErrorCode IMPORT_LOG_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_041_004, "未补充导入导入日志数据导入接口逻辑！");

// ========== 安全告知卡  ==========
    ErrorCode COMMODITY_SAFETY_CARD_NOT_EXISTS = new ErrorCode(1_015_042_000, "安全告知卡不存在");
    ErrorCode COMMODITY_SAFETY_CARD_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_015_042_001, "导入安全告知卡数据不能为空！");
    ErrorCode COMMODITY_SAFETY_CARD_IMPORT_PREVIEW_REQUIRE = new ErrorCode(1_015_042_002, "未补充导入安全告知卡数据预览接口逻辑！");
    ErrorCode COMMODITY_SAFETY_CARD_IMPORT_PORT_REQUIRE = new ErrorCode(1_015_042_003, "未补充导入安全告知卡数据导入接口逻辑！");



}
