package com.fmyd888.fengmao.module.information.dal.dataobject.roster;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Title: RosterDO
 * @Author: huanhuan
 * @Date: 2024-04-04
 * @Description:
 *  对应钉钉员工花名册表实体类
 */
@TableName("fm_roster")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RosterDO extends BaseDO {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 姓名
     */
    private String name;
    /**
     * 用户id
     */
    private String userId;
    /**
     *
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 直属主管id
     */
    private String reportManagerId;
    /**
     * 直属主管名称
     */
    private String reportManagerName;

    /**
     * 入职时间
     */
    private LocalDateTime confirmJoinTime;

    /**
     * 办公地点
     */
    private String workPlace;

    /**
     * 职位
     */
    private String position;

    /**
     * 岗位职级
     */
    private String positionLevel;

    /**
     * label
     * 政治面貌
     */
    private String politicalStatus;

    /**
     * 证件号码（身份证号）
     */
    private String certNo;

    /**
     * 现合同到期日  所以这里就不需要把LocalDateTime这个类型转换为String字符串类型
     */
    private LocalDateTime nowContractEndTime;

    /**
     * 实际转正日期
     */
    private LocalDateTime regularTime;

    /**
     * 出生日期
     */
    private LocalDateTime birthTime;

    /**
     * 首次合同起始日
     */
    private LocalDateTime firstContractStartTime;

    /**
     * 现合同起始日
     */
    private LocalDateTime nowContractStartTime;

    /**
     * 毕业院校
     */
    private String graduateSchool;

    /**
     * 身份证地址
     */
    private String certAddress;

    /**
     * 住址
     */
    private String address;

    /**
     * label
     * 户籍类型
     */
    private String residenceType;

    /**
     * 计划转正日期
     */
    private LocalDateTime planRegularTime;

    /**
     * 身份证姓名
     */
    private String realName;

    /**
     * label
     * 性别
     */
    private String sexType;

    /**
     * label
     * 员工类型
     */
    private String employeeType;

    /**
     * 试用期
     */
    private Integer probationPeriodType;

    /**
     * 首次参加工作时间
     */
    private LocalDateTime joinWorkingTime;

    /**
     * 证件有效期
     */
    private LocalDateTime certEndTime;

    /**
     * label
     * 员工状态
     */
    private String employeeStatus;

    /**
     * label
     * 民族
     */
    private String nationType;

    /**
     * 银行卡号
     */
    private String bankAccountNo;

    /**
     * 首次合同到期日
     */
    private LocalDateTime firstContractEndTime;

    /**
     * 合同公司
     */
    private String contractCompanyName;

    /**
     * 部门id(钉钉允许一个员工归属于多个部门下【deptId1|deptId2|...】的格式存储 )
     */
    private String deptIds;

    /**
     * 部门
     */
    private String dept;

    /**
     * 主部门id
     */
    private String mainDeptId;

    /**
     * 主部门
     */
    private String mainDept;

    /**
     * 主车号
     */
    private String mainCarNo;

    /**
     * 挂车号
     */
    private String trailerCarNo;

    /**
     * 员工在职情况
     */
    private Integer employmentStatus;

    /**
     * 身份证发证机关
     */
    private String certIssuingAuthority;

    /**
     * 身份证证件起始日期
     */
    private LocalDateTime certStartDate;

    /**
     * 是否有残疾证
     */
    private Boolean hasDisabilityCertificate;

    /**
     * 残疾证编号
     */
    private String disabilityCertificateNo;

    /**
     * 是否是退伍军人
     */
    private Boolean isVeteran;

    /**
     * 退伍证编号
     */
    private String veteranCertificateNo;

    /**
     * 从事货运行业年限
     */
    private Integer yearsInFreightIndustry;

    /**
     * 婚姻状况
     */
    private Integer maritalStatus;

    /**
     * 学历
     */
    private String education;

    /**
     * 专业
     */
    private String major;

    /**
     * 获得职称
     */
    private String professionalTitle;

    /**
     * 职称全称
     */
    private String fullProfessionalTitle;
    /**
     * 银行名称及开户行
     */
    private String bankNameAndBranch;

    /**
     * 保险缴纳类型
     */
    private String insurancePaymentType;

    /**
     * 保险档级
     */
    private String insuranceLevel;

    /**
     * 保险缴纳公司
     */
    private String insuranceCompany;

    /**
     * 保险缴纳地
     */
    private String insuranceLocation;

    /**
     * 缴纳保险时间
     */
    private String insurancePaymentTime;

    /**
     * 医保异地备案
     */
    private String medicalInsuranceRegistration;

    /**
     * 缴纳情况备注
     */
    private String paymentRemark;

    /**
     * 社保卡号
     */
    private String socialSecurityCardNumber;

    /**
     * 是否为借调人员
     */
    private String onSecondment;

    /**
     * 借调情况备注
     */
    private String secondmentRemark;

    /**
     * 工资发放公司
     */
    private String salaryCompany;

    /**
     * 紧急联系人姓名
     */
    private String emergencyContactName;

    /**
     * 与本人关系
     */
    private String relationshipWithUser;

    /**
     * 紧急联系人电话
     */
    private String emergencyContactPhone;

    /**
     * 家庭情况备注
     */
    private String familySituationRemark;

    /**
     * 从业资格证发证机关
     */
    private String certificateIssuingAuthority;

    /**
     * 从业资格证领证日期
     */
    private String certificateIssueDate;

    /**
     * 从业资格证有效期
     */
    private String certificateValidityPeriod;

    /**
     * 资格证诚信考核下一次盖章日期
     */
    private String integrityAssessmentNextSealDate;

    /**
     * 押运证发证机关
     */
    private String escortLicenseIssuingAuthority;

    /**
     * 押运证领证日期
     */
    private String escortLicenseIssueDate;

    /**
     * 押运证有效期
     */
    private String escortLicenseValidityPeriod;

    /**
     * 驾驶证发证机关
     */
    private String driverLicenseIssuingAuthority;

    /**
     * 驾驶证领证日期
     */
    private String driverLicenseIssueDate;

    /**
     * 驾驶证有效期
     */
    private String driverLicenseValidityPeriod;

    /**
     * 状态
     */
    private Byte status;

}

