package com.fmyd888.fengmao.module.information.controller.admin.transportdetail;

import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.module.information.common.CardDetailPage;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailRespVO;
import com.fmyd888.fengmao.module.information.service.transportdetail.TransportDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

/**
 * 同理不能作为独立接口使用
 */
@Tag(name = "管理后台 - 运输证明细")
@RestController
@RequestMapping("/information/transport-detail")
@Validated
public class TransportDetailController {

    @Resource
    private TransportDetailService transportDetailService;

    @GetMapping("/get")
    @Operation(summary = "获得运输证明细分页")
    @PreAuthorize("@ss.hasPermission('information:transport-detail:query')")
    public CommonResult<TransportDetailRespVO> getTransportDetail(@Param("id") @RequestParam Long id) {
        TransportDetailRespVO transportDetail = transportDetailService.getTransportDetail(id);
        CommonResult<TransportDetailRespVO> result = success(transportDetail);
        return result;
    }

    @PostMapping("/page")
    @Operation(summary = "获得运输证明细分页")
    @PreAuthorize("@ss.hasPermission('information:transport-detail:query')")
    public CommonResult<CardDetailPage<TransportDetailRespVO>> getTransportDetailPage(@Valid @RequestBody TransportDetailPageReqVO pageVO) {
        CardDetailPage<TransportDetailRespVO> page = transportDetailService.selectDetailPage(pageVO);
        CommonResult<CardDetailPage<TransportDetailRespVO>> result = success(page);
        return result;
    }

    @GetMapping("/selectTransportDetailCode")
    @Operation(summary = "运输证明细编码")
    public CommonResult<List<HashMap<String, Object>>> selectTransportDetailCode() {
        List<HashMap<String, Object>> list = transportDetailService.selectTransportDetailCode();
        return success(list);
    }
}
