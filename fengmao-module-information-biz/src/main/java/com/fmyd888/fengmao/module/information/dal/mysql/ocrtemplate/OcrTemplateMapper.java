package com.fmyd888.fengmao.module.information.dal.mysql.ocrtemplate;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.module.information.dal.dataobject.ocrtemplate.OcrTemplateDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate.vo.*;
import org.springframework.util.StringUtils;

/**
 * fm_ocr_template Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface OcrTemplateMapper extends BaseMapperX<OcrTemplateDO> {

    default PageResult<OcrTemplateDO> selectPage(OcrTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OcrTemplateDO>()
                .likeIfPresent(OcrTemplateDO::getName, reqVO.getName())
                .eqIfPresent(OcrTemplateDO::getOcrType, reqVO.getOcrType())
                .eqIfPresent(OcrTemplateDO::getOcrCode, reqVO.getOcrCode())
//                .likeIfPresent(OcrTemplateDO::getOcrType, reqVO.getOcrType())
                .orderByDesc(OcrTemplateDO::getId));
//        return selectPage(reqVO, new MPJLambdaWrapper<OcrTemplateDO>());
    }

    default List<OcrTemplateDO> selectList(OcrTemplateListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OcrTemplateDO>()
                .likeIfPresent(OcrTemplateDO::getName, reqVO.getName())
                .eqIfPresent(OcrTemplateDO::getOcrType, reqVO.getOcrType())
//               .eqIfPresent(OcrTemplateDO::getOcrCode, reqVO.getOcrCode())
                .orderByDesc(OcrTemplateDO::getId));
    }


    /**
    * 此方法是以字段【平铺】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<OcrTemplateDO> selectJoinTilePage(OcrTemplatePageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<OcrTemplateDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), OcrTemplateDO.class,
        new MPJLambdaWrapper<OcrTemplateDO>()
            .selectAll(OcrTemplateDO.class)//查询的主表字段
            //.selectAs(DeptDO::getName, OcrTemplateDO::getDeptName)//查询关联表字段，此处是取组织名称
            //.leftJoin(DeptDO.class, DeptDO::getId, OcrTemplateDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .like(StringUtils.hasText(reqVO.getName()),OcrTemplateDO::getName, reqVO.getName())
            .like(ObjectUtil.isNotNull(reqVO.getOcrType()),OcrTemplateDO::getOcrType, reqVO.getOcrType())
            .disableSubLogicDel()
            .orderByDesc(OcrTemplateDO::getId));
        return new PageResult<OcrTemplateDO>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
    * 此方法是以字段【内联】形式查询外键字段信息
    * @param reqVO 查询实体
    * @return
    */
    default PageResult<OcrTemplateDO> selectJoinInlinePage(OcrTemplatePageReqVO reqVO) {
        //第二个参数为查询结果对应的DO类
        //MPJLambdaWrapper中的泛型为我们要查询的DO类，即实际映射数据库的DO类
        Page<OcrTemplateDO> resultPage = selectJoinPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), OcrTemplateDO.class,
        new MPJLambdaWrapper<OcrTemplateDO>()
            .selectAll(OcrTemplateDO.class)//查询的主表字段
            //.selectAssociation(DeptDO.class, OcrTemplateDO::getOrg)//查询关联表字段，并赋值给此DO中的对象，如此处举例的org
            //.leftJoin(DeptDO.class, DeptDO::getId, OcrTemplateDO::getDeptId)//关联条件，根据实际需求改，可以多个.leftJoin 相当于 left join system_dept t1 on t1.id=t.dept_id
            .like(StringUtils.hasText(reqVO.getName()),OcrTemplateDO::getName, reqVO.getName())
            .like(ObjectUtil.isNotNull(reqVO.getOcrType()),OcrTemplateDO::getOcrType, reqVO.getOcrType())
            .disableSubLogicDel()
            .orderByDesc(OcrTemplateDO::getId));
        return new PageResult<OcrTemplateDO>(resultPage.getRecords(), resultPage.getTotal());
    }
}
