package com.fmyd888.fengmao.module.information.service.purchasemanger;

import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.purchasemanger.PurchaseMangerDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 购买证管理 Service 接口
 *
 * @author 丰茂
 */
public interface PurchaseMangerService {

    /**
     * 创建购买证管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPurchaseManger(@Valid PurchaseMangerSaveReqVO createReqVO);

    /**
     * 更新购买证管理
     *
     * @param updateReqVO 更新信息
     */
    void updatePurchaseManger(@Valid PurchaseMangerSaveReqVO updateReqVO);

    /**
     * 删除购买证管理
     *
     * @param id 编号
     */
    void deletePurchaseManger(Long id);

    /**
     * 获得购买证管理
     *
     * @param id 编号
     * @return 购买证管理
     */
    PurchaseMangerRespVO getPurchaseManger(Long id);

    /**
     * 获得购买证管理列表
     *
     * @param ids 编号
     * @return 购买证管理列表
     */
    List<PurchaseMangerDO> getPurchaseMangerList(Collection<Long> ids);



    /**
     * 获得购买证管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 购买证管理列表
     */
    List<PurchaseMangerDO> getPurchaseMangerList(PurchaseMangerListReqVO exportReqVO);




    /**
     * @param PageReqVO 关键字分页查询信息
     * @return 关键字分页查询结果
     */
    CardPage<PurchaseMangerRespVO> selectPageByKeyword(PurchaseMangerKeywordPageReqVO PageReqVO);
    /**
     * 根据id查询到自动带出的信息
     *
     * @return
     */
    List<PurchaseMangerBasicRespVO>  getPurchaseManger02(Long id);
    /**
     *
     *
     * @return
     */
    /**
     * 获取购买证精简信息
     * @param type 购买证类型，空为查询所有
     * @param type 购买证所属公司
     * @return
     */
    List<PurchaseMangerSimpleRespVO> getPurchaseMangerList(Long type,Long deptId);

    /**
     * 销售单位精简信息
     * @return
     */
    List<PurchaseMangerSalseSimpleRespVO> getPurchaseMangerList1();

    /**
     * 购买单位精简信息
     * @return
     */
    List<PurchaseMangerUnitSimpleRespVO> getPurchaseMangerList2();

}
