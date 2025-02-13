package com.fmyd888.fengmao.module.information.service.fleet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.fmyd888.fengmao.framework.test.core.ut.BaseDbUnitTest;

import com.fmyd888.fengmao.module.information.controller.admin.fleet.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.fleet.FleetDO;
import com.fmyd888.fengmao.module.information.dal.mysql.fleet.FleetMapper;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;
import static com.fmyd888.fengmao.framework.test.core.util.AssertUtils.*;
import static com.fmyd888.fengmao.framework.test.core.util.RandomUtils.*;
import static com.fmyd888.fengmao.framework.common.util.date.LocalDateTimeUtils.*;
import static com.fmyd888.fengmao.framework.common.util.object.ObjectUtils.*;
import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link FleetServiceImpl} 的单元测试类
*
* @author 小逺
*/
@Import(FleetServiceImpl.class)
public class FleetServiceImplTest extends BaseDbUnitTest {

@Resource
private FleetServiceImpl fleetService;

@Resource
private FleetMapper fleetMapper;

@Test
public void testCreateFleet_success() {
// 准备参数
FleetSaveReqVO createReqVO = randomPojo(FleetSaveReqVO.class).setId(null);

// 调用
Long fleetId = fleetService.createFleet(createReqVO);
// 断言
assertNotNull(fleetId);
// 校验记录的属性是否正确
FleetDO fleet = fleetMapper.selectById(fleetId);
assertPojoEquals(createReqVO, fleet, "id");
}

@Test
public void testUpdateFleet_success() {
// mock 数据
FleetDO dbFleet = randomPojo(FleetDO.class);
fleetMapper.insert(dbFleet);// @Sql: 先插入出一条存在的数据
// 准备参数
FleetSaveReqVO updateReqVO = randomPojo(FleetSaveReqVO.class, o -> {
o.setId(dbFleet.getId()); // 设置更新的 ID
});

// 调用
fleetService.updateFleet(updateReqVO);
// 校验是否更新正确
FleetDO fleet = fleetMapper.selectById(updateReqVO.getId()); // 获取最新的
assertPojoEquals(updateReqVO, fleet);
}

@Test
public void testUpdateFleet_notExists() {
// 准备参数
FleetSaveReqVO updateReqVO = randomPojo(FleetSaveReqVO.class);

// 调用, 并断言异常
assertServiceException(() -> fleetService.updateFleet(updateReqVO), FLEET_NOT_EXISTS);
}

@Test
public void testDeleteFleet_success() {
// mock 数据
FleetDO dbFleet = randomPojo(FleetDO.class);
fleetMapper.insert(dbFleet);// @Sql: 先插入出一条存在的数据
// 准备参数
Long id = dbFleet.getId();

// 调用
fleetService.deleteFleet(id);
// 校验数据不存在了
assertNull(fleetMapper.selectById(id));
}

@Test
public void testDeleteFleet_notExists() {
// 准备参数
Long id = randomLongId();

// 调用, 并断言异常
assertServiceException(() -> fleetService.deleteFleet(id), FLEET_NOT_EXISTS);
}

@Test
@Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
public void testGetFleetPage() {
// mock 数据
    FleetDO dbFleet = randomPojo(FleetDO.class, o -> { // 等会查询到
        o.setName(null);
        o.setCreateTime(null);
        o.setCaptainId(null);
});
    fleetMapper.insert(dbFleet);
        // 测试 name 不匹配
            fleetMapper.insert(cloneIgnoreId(dbFleet, o -> o.setName(null)));
        // 测试 createTime 不匹配
            fleetMapper.insert(cloneIgnoreId(dbFleet, o -> o.setCreateTime(null)));
        // 测试 captainId 不匹配
            fleetMapper.insert(cloneIgnoreId(dbFleet, o -> o.setCaptainId(null)));
// 准备参数
    FleetPageReqVO reqVO = new FleetPageReqVO();
            reqVO.setName(null);
            reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
            reqVO.setCaptainId(null);

// 调用
PageResult<FleetDO> pageResult = fleetService.getFleetPage(reqVO);
    // 断言
    assertEquals(1, pageResult.getTotal());
    assertEquals(1, pageResult.getList().size());
    assertPojoEquals(dbFleet, pageResult.getList().get(0));
    }

    }