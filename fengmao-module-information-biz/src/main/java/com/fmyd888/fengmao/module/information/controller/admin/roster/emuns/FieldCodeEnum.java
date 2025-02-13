package com.fmyd888.fengmao.module.information.controller.admin.roster.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Title: FiledCodeEnum
 * @Author: huanhuan
 * @Date: 2024-03-27
 * @Description: 花名册自定义字段业务code枚举
 */
@Getter
@AllArgsConstructor
public enum FieldCodeEnum {
    //钉钉基础字段业务定义
    SYS00_NAME("sys00-name", "姓名"),
    SYS00_EMAIL("sys00-email", "邮箱"),
    SYS00_DEPT_IDS("sys00-deptIds", "部门id列表"),
    SYS00_MAIN_DEPT_ID("sys00-mainDeptId", "主部门id"),
    SYS00_DEPT("sys00-dept", "部门"),
    SYS00_REPORTMANAGER_ID("sys00-reportManagerId", "直属主管Id"),
    SYS00_REPORTMANAGER("sys00-reportManager", "直属主管"),
    SYS00_MAIN_DEPT("sys00-mainDept", "主部门"),
    SYS00_POSITION("sys00-position", "职位"),
    SYS00_MOBILE("sys00-mobile", "手机号"),
    SYS00_JOB_NUMBER("sys00-jobNumber", "工号"),
    SYS00_TEL("sys00-tel", "分机号"),
    SYS00_WORK_PLACE("sys00-workPlace", "办公地点"),
    SYS00_REMARK("sys00-remark", "备注"),
    SYS00_CONFIRM_JOIN_TIME("sys00-confirmJoinTime", "入职时间"),
    SYS01_EMPLOYEE_TYPE("sys01-employeeType", "员工类型"),
    SYS01_EMPLOYEE_STATUS("sys01-employeeStatus", "员工状态"),
    SYS01_PROBATION_PERIOD_TYPE("sys01-probationPeriodType", "试用期"),
    SYS01_REGULAR_TIME("sys01-regularTime", "转正日期"),
    SYS01_POSITION_LEVEL("sys01-positionLevel", "岗位职级"),
    SYS02_REAL_NAME("sys02-realName", "身份证姓名"),
    SYS02_CERT_NO("sys02-certNo", "证件号码"),
    SYS02_BIRTH_TIME("sys02-birthTime", "出生日期"),
    SYS02_SEX_TYPE("sys02-sexType", "性别"),
    SYS02_NATION_TYPE("sys02-nationType", "民族"),
    SYS02_CERT_ADDRESS("sys02-certAddress", "身份证地址"),
    SYS02_CERT_END_TIME("sys02-certEndTime", "证件有效期"),
    SYS02_MARRIAGE("sys02-marriage", "婚姻状况"),
    SYS02_JOIN_WORKING_TIME("sys02-joinWorkingTime", "首次参加工作时间"),
    SYS02_RESIDENCE_TYPE("sys02-residenceType", "户籍类型"),
    SYS02_ADDRESS("sys02-address", "住址"),
    SYS02_POLITICAL_STATUS("sys02-politicalStatus", "政治面貌"),
    SYS09_PERSONAL_SI("sys09-personalSi", "个人社保账号"),
    SYS09_PERSONAL_HF("sys09-personalHf", "个人公积金账号"),
    SYS03_HIGHEST_EDU("sys03-highestEdu", "最高学历"),
    SYS03_GRADUATE_SCHOOL("sys03-graduateSchool", "毕业院校"),
    SYS03_GRADUATION_TIME("sys03-graduationTime", "毕业时间"),
    SYS03_MAJOR("d16b084b-196a-401a-b444-0f3852392fff", "所学专业"),
    SYS04_BANK_ACCOUNT_NO("sys04-bankAccountNo", "银行卡号"),
    SYS04_ACCOUNT_BANK("sys04-accountBank", "开户行"),
    SYS05_CONTRACT_COMPANY_NAME("sys05-contractCompanyName", "合同公司"),
    SYS05_CONTRACT_TYPE("sys05-contractType", "合同类型"),
    SYS05_FIRST_CONTRACT_START_TIME("sys05-firstContractStartTime", "首次合同起始日"),
    SYS05_FIRST_CONTRACT_END_TIME("sys05-firstContractEndTime", "首次合同到期日"),
    SYS05_NOW_CONTRACT_START_TIME("sys05-nowContractStartTime", "现合同起始日"),
    SYS05_NOW_CONTRACT_END_TIME("sys05-nowContractEndTime", "现合同到期日"),
    SYS05_CONTRACT_PERIOD_TYPE("sys05-contractPeriodType", "合同期限"),
    SYS05_CONTRACT_RENEW_COUNT("sys05-contractRenewCount", "续签次数"),
    SYS06_URGENT_CONTACTS_NAME("sys06-urgentContactsName", "紧急联系人姓名"),
    SYS06_URGENT_CONTACTS_RELATION("sys06-urgentContactsRelation", "联系人关系"),
    SYS06_URGENT_CONTACTS_PHONE("sys06-urgentContactsPhone", "联系人电话"),
    SYS07_HAVE_CHILD("sys07-haveChild", "有无子女"),
    SYS07_CHILD_NAME("sys07-childName", "子女姓名"),
    SYS07_CHILD_SEX("sys07-childSex", "子女性别"),
    SYS07_CHILD_BIRTH_DATE("sys07-childBirthDate", "子女出生日期"),

    //自定义业务字段code,
    FM_FIELD_BUSINESS_01("4e2e0f5b-d365-4ba7-a4d1-a4ba185cb490", "银行名称及开户行"),
    FM_FIELD_BUSINESS_02("ac6088f7-b436-4316-99c0-700b3ab6dc65", "保险缴纳类型"),
    FM_FIELD_BUSINESS_03("23c3210d-737f-4709-a239-4d8fcd7a7a50", "保险档级"),
    FM_FIELD_BUSINESS_04("70444523-7c2e-4f17-ba08-60dfce96d5eb", "保险缴纳公司"),
    FM_FIELD_BUSINESS_05("554e60a3-ede1-4011-a0b8-ef19e5e011a0", "保险缴纳地"),
    FM_FIELD_BUSINESS_06("e3cfcd8a-485d-4610-902a-b5ed94d0e324", "缴纳保险时间"),
    FM_FIELD_BUSINESS_07("64f36cd6-8759-4cf6-b26f-d861e79a6367", "医保异地备案"),
    FM_FIELD_BUSINESS_08("672b5fbf-060e-4b68-800c-be4d46670e99", "缴纳情况备注"),
    FM_FIELD_BUSINESS_09("e57af3ca-f9db-42af-bb94-0b9c293b32dc", "社保卡号"),
    FM_FIELD_BUSINESS_10("4c8194ea-c745-49c4-934f-905cfc8b2da9", "是否为借调人员"),
    FM_FIELD_BUSINESS_11("efad5a81-05bb-4e06-930d-c62921b03792", "借调情况备注"),
    FM_FIELD_BUSINESS_12("19dfb979-ea5f-4812-a7bb-91d3b58422b3", "工资发放公司"),
    FM_FIELD_BUSINESS_13("6849fc26-cd77-4dbd-a20f-14ea60a7e980", "紧急联系人姓名"),
    FM_FIELD_BUSINESS_14("09d3d0e7-7391-48c5-9721-f46dc2f3de00", "与本人关系"),
    FM_FIELD_BUSINESS_15("2e1e36e4-73e5-4a50-bd64-209f5409e44f", "紧急联系人电话"),
    FM_FIELD_BUSINESS_16("ffd84997-9e25-4f59-8699-93b09a7c1abc", "家庭情况备注"),
    FM_FIELD_BUSINESS_17("6750d5c9-dd7a-4466-89ed-a48954849a3f", "从业资格证发证机关"),
    FM_FIELD_BUSINESS_18("1ddc17fd-0b81-4827-aa9b-6d2bcb8fe671", "从业资格证领证日期"),
    FM_FIELD_BUSINESS_19("d08e6217-7669-4012-b1ab-347ec884aafd", "从业资格证有效期"),
    FM_FIELD_BUSINESS_20("764bf55a-5047-454e-928a-0bc39c04af54", "资格证诚信考核下一次盖章日期"),
    FM_FIELD_BUSINESS_21("cd1aa169-8432-40f0-92bc-95cd9d457c27", "押运证发证机关"),
    FM_FIELD_BUSINESS_22("67d77565-587b-4fe3-acb4-0b63ee5747ad", "押运证领证日期"),
    FM_FIELD_BUSINESS_23("290cc321-a209-4ac6-9e5a-fc3d06cf8525", "押运证有效期"),
    FM_FIELD_BUSINESS_24("0cb71034-1a07-4b62-b9db-949b07d6504b", "驾驶证发证机关"),
    FM_FIELD_BUSINESS_25("4888e57c-b335-422f-8f6a-b032b3d2c515", "驾驶证领证日期"),
    FM_FIELD_BUSINESS_26("cccc99bb-b98d-46c1-9a90-03ee82db6ecf", "驾驶证有效期"),
    FM_FIELD_BUSINESS_27("1bd653f2-0f31-4660-8c7c-eb83297e2fb0", "学历"),


    OTHER("error", "错误业务含义");
    /**
     * 字段code
     */
    private final String filedCode;
    /**
     * 业务含义
     */
    private final String bzusinessImplications;
}
