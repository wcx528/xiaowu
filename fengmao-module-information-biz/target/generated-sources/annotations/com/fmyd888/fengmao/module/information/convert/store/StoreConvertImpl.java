package com.fmyd888.fengmao.module.information.convert.store;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.StoreCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.StoreExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.StoreImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.StoreRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.StoreUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class StoreConvertImpl implements StoreConvert {

    @Override
    public StoreDO convert(StoreCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        StoreDO.StoreDOBuilder storeDO = StoreDO.builder();

        storeDO.storeCode( bean.getStoreCode() );
        storeDO.storeName( bean.getStoreName() );
        storeDO.storeType( bean.getStoreType() );
        storeDO.status( bean.getStatus() );
        storeDO.remark( bean.getRemark() );
        storeDO.storeAddressId( bean.getStoreAddressId() );
        storeDO.deptId( bean.getDeptId() );
        storeDO.storeAddressName( bean.getStoreAddressName() );

        return storeDO.build();
    }

    @Override
    public StoreDO convert(StoreUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        StoreDO.StoreDOBuilder storeDO = StoreDO.builder();

        storeDO.id( bean.getId() );
        storeDO.storeCode( bean.getStoreCode() );
        storeDO.storeName( bean.getStoreName() );
        storeDO.storeType( bean.getStoreType() );
        storeDO.status( bean.getStatus() );
        storeDO.remark( bean.getRemark() );
        storeDO.storeAddressId( bean.getStoreAddressId() );
        storeDO.deptId( bean.getDeptId() );

        return storeDO.build();
    }

    @Override
    public StoreRespVO convert(StoreDO bean) {
        if ( bean == null ) {
            return null;
        }

        StoreRespVO storeRespVO = new StoreRespVO();

        storeRespVO.setStoreCode( bean.getStoreCode() );
        storeRespVO.setStoreName( bean.getStoreName() );
        storeRespVO.setStoreType( bean.getStoreType() );
        storeRespVO.setStatus( bean.getStatus() );
        storeRespVO.setRemark( bean.getRemark() );
        storeRespVO.setDeptId( bean.getDeptId() );
        storeRespVO.setId( bean.getId() );
        storeRespVO.setCreateTime( bean.getCreateTime() );
        storeRespVO.setCreator( bean.getCreator() );
        storeRespVO.setUpdater( bean.getUpdater() );
        storeRespVO.setStoreAddressName( bean.getStoreAddressName() );
        storeRespVO.setStoreAddressId( bean.getStoreAddressId() );

        return storeRespVO;
    }

    @Override
    public List<StoreRespVO> convertList(List<StoreDO> list) {
        if ( list == null ) {
            return null;
        }

        List<StoreRespVO> list1 = new ArrayList<StoreRespVO>( list.size() );
        for ( StoreDO storeDO : list ) {
            list1.add( convert( storeDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<StoreRespVO> convertPage(PageResult<StoreDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<StoreRespVO> pageResult = new PageResult<StoreRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<StoreExcelVO> convertList02(List<StoreDO> list) {
        if ( list == null ) {
            return null;
        }

        List<StoreExcelVO> list1 = new ArrayList<StoreExcelVO>( list.size() );
        for ( StoreDO storeDO : list ) {
            list1.add( storeDOToStoreExcelVO( storeDO ) );
        }

        return list1;
    }

    @Override
    public StoreDO convert(StoreImportExcelVO excelVO) {
        if ( excelVO == null ) {
            return null;
        }

        StoreDO.StoreDOBuilder storeDO = StoreDO.builder();

        storeDO.storeName( excelVO.getStoreName() );
        storeDO.storeType( excelVO.getStoreType() );
        storeDO.remark( excelVO.getRemark() );

        return storeDO.build();
    }

    protected StoreExcelVO storeDOToStoreExcelVO(StoreDO storeDO) {
        if ( storeDO == null ) {
            return null;
        }

        StoreExcelVO storeExcelVO = new StoreExcelVO();

        if ( storeDO.getId() != null ) {
            storeExcelVO.setId( String.valueOf( storeDO.getId() ) );
        }
        storeExcelVO.setStoreCode( storeDO.getStoreCode() );
        storeExcelVO.setStoreName( storeDO.getStoreName() );
        storeExcelVO.setStatus( storeDO.getStatus() );
        storeExcelVO.setRemark( storeDO.getRemark() );
        storeExcelVO.setDeptName( storeDO.getDeptName() );
        storeExcelVO.setStoreType( storeDO.getStoreType() );
        storeExcelVO.setStoreAddressName( storeDO.getStoreAddressName() );
        storeExcelVO.setCreateTime( storeDO.getCreateTime() );
        storeExcelVO.setCreator( storeDO.getCreator() );

        return storeExcelVO;
    }
}
