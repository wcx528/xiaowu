package com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.carpersonreplace.CarPersonReplaceDO;
import com.fmyd888.fengmao.module.information.service.carpersonreplace.CarPersonReplaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 车辆人员更换记录")
@RestController
@RequestMapping("/information/car-person-replace")
@Validated
@Slf4j
public class CarPersonReplaceController {

    @Resource
    private CarPersonReplaceService carPersonReplaceService;

    @PostMapping("/create")
    @Operation(summary = "创建车辆人员更换记录")
    @PreAuthorize("@ss.hasPermission('information:car-person-replace:create')")
    public CommonResult<Long> createCarPersonReplace(@Valid @RequestBody CarPersonReplaceSaveReqVO createReqVO) {
        return success(carPersonReplaceService.createCarPersonReplace(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新车辆人员更换记录")
    @PreAuthorize("@ss.hasPermission('information:car-person-replace:update')")
    public CommonResult<Boolean> updateCarPersonReplace(@Valid @RequestBody CarPersonReplaceSaveReqVO updateReqVO) {
        carPersonReplaceService.updateCarPersonReplace(updateReqVO);
        return success(true);
    }


    @GetMapping("/page")
    @Operation(summary = "用过通用关键字查询获得车辆人员更换记录分页")
    @PreAuthorize("@ss.hasPermission('information:car-person-replace:query')")
    public CommonResult<Page<CarPersonReplaceRespVO>> getCarPersonReplaceKeywordPage(@Valid CarPersonReplacePageReqVO pageReqVO) {
        Page<CarPersonReplaceRespVO> carPersonReplaceVOPage = carPersonReplaceService.selectPageByKeyword(pageReqVO);

        return success(carPersonReplaceVOPage);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出车辆人员更换记录 Excel")
    @PreAuthorize("@ss.hasPermission('information:car-person-replace:export')")
    @OperateLog(type = EXPORT)
    public void exportCarPersonReplaceExcel(@Valid CarPersonReplacePageReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        Page<CarPersonReplaceRespVO> carPersonReplaceRespVOPage = carPersonReplaceService.selectPageByKeyword(exportReqVO);
        List<CarPersonReplaceRespVO> excelVo = carPersonReplaceRespVOPage.getRecords();
        // 导出 Excel
        //List<CarPersonReplaceRespVO> excelVo = BeanUtils.toBean(list, CarPersonReplaceRespVO.class);
        ExcelUtils.write(response, "车辆人员更换记录.xls", "数据",
                CarPersonReplaceRespVO.class, excelVo,
                exportReqVO.getExportFileds());
    }



}
