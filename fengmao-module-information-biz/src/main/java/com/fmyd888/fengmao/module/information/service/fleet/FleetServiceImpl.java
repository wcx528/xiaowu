package com.fmyd888.fengmao.module.information.service.fleet;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.dal.redis.RedisKeyConstants;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiFunction;

import com.fmyd888.fengmao.module.information.controller.admin.fleet.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.fleet.FleetDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import com.fmyd888.fengmao.module.information.dal.mysql.fleet.FleetMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 车队表 Service 实现类
 *
 * @author 小逺
 */
@Service
@Validated
public class FleetServiceImpl implements FleetService {

    @Resource
    private FleetMapper fleetMapper;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Long createFleet(FleetSaveReqVO createReqVO) {
        // 插入
        FleetDO fleet = BeanUtils.toBean(createReqVO, FleetDO.class);
        //获取部门id
//        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
//        fleet.setDeptId(loginUserDeptId);
        fleetMapper.insert(fleet);


        // 返回
        return fleet.getId();
    }

    @Override
    public void updateFleet(FleetSaveReqVO updateReqVO) {
        // 校验存在
        validateFleetExists(updateReqVO.getId());
        // 更新
        FleetDO updateObj = BeanUtils.toBean(updateReqVO, FleetDO.class);
        fleetMapper.updateById(updateObj);

    }

    @Override
    public void deleteFleet(Long id) {
        // 校验存在
        validateFleetExists(id);
        // 删除
        fleetMapper.deleteById(id);
    }

    private void validateFleetExists(Long id) {
        if (fleetMapper.selectById(id) == null) {
            throw exception(FLEET_NOT_EXISTS);
        }
    }


    @Override
    public FleetDO getFleet(Long id) {
        return fleetMapper.selectById(id);
    }

    @Override
    public List<FleetDO> getFleetList(Collection<Long> ids) {
        return fleetMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FleetDO> getFleetPage(FleetPageReqVO pageReqVO) {
        return fleetMapper.selectJoinTilePage(pageReqVO);
    }

    @Override
    public List<FleetDO> getFleetList(FleetListReqVO listReqVO) {
        return fleetMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateFleet(List<FleetSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteFleet(List<Long> ids) {
        // 在这里处理批量删除逻辑
        fleetMapper.deleteBatchIds(ids);
    }

    @Override
    public List<FleetExcelVO> importPreviewList(List<FleetExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(FLEET_IMPORT_LIST_IS_EMPTY);
        }

        List<FleetExcelVO> excelVo = BeanUtils.toBean(importDatas, FleetExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(FLEET_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(FleetExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
            throw exception(FLEET_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(FLEET_IMPORT_PORT_REQUIRE);
        //以下是示例，补充逻辑时请替换成自己书写的逻辑
        //ImportResult result = ImportResult.builder()
        //.total(importReqVo.getImportDatas().size())
        //.importCount(0)
        //.failCount(importReqVo.getImportDatas().size())//假设这里假设都导入失败
        //.success(false)
        //.data(importReqVo.getImportDatas())
        //.build();
        //return result;
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.FLEET_SIMPLE_LIST + "#120", key = "#param.searchKey+'_'+#param.id+'_'+#param.companyIds")
    public List<HashMap<String, Object>> getFleetSimpleList(CommonQueryParam param) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        MPJLambdaWrapper<FleetDO> queryWrapper = new MPJLambdaWrapper<FleetDO>()
                .select(FleetDO::getId, FleetDO::getName, FleetDO::getCaptainId)
                .selectAs(AdminUserDO::getNickname, FleetDO::getCaptainName)
                .leftJoin(AdminUserDO.class, AdminUserDO::getId, FleetDO::getCaptainId)
                .eq(ObjectUtil.isNotEmpty(param.getId()), FleetDO::getId, param.getId())
                .in(ArrayUtil.isNotEmpty(param.getCompanyIds()), FleetDO::getDeptId, param.getCompanyIds())
                .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(FleetDO::getName, param.getSearchKey()).or().like(AdminUserDO::getNickname, param.getSearchKey()))
                .groupBy("t.id", "t.name", "t.captain_id","t1.nickname")
                .disableSubLogicDel();

        List<FleetDO> fleetDOS = fleetMapper.selectJoinList(FleetDO.class, queryWrapper);
        fleetDOS.forEach(iterm -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("name", iterm.getName());
            map.put("captainId", iterm.getCaptainId());
            map.put("captainName", iterm.getCaptainName());
            list.add(map);
        });
        return list;
    }

}