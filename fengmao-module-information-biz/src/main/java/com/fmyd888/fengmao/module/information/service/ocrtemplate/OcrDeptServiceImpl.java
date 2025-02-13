package com.fmyd888.fengmao.module.information.service.ocrtemplate;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseDeptServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.ocrtemplate.OcrDeptDo;
import com.fmyd888.fengmao.module.information.dal.mysql.ocrtemplate.OcrDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @author:申清源 2024/5/4
 */
@Service
public class OcrDeptServiceImpl
        extends BaseDeptServiceImpl<OcrDeptMapper, OcrDeptDo>
         implements OcrDeptService {
}
