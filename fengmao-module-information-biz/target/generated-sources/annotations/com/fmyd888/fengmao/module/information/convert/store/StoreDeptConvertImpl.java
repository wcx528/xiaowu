package com.fmyd888.fengmao.module.information.convert.store;

import com.fmyd888.fengmao.module.information.controller.admin.store.vo.StoreDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDeptDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class StoreDeptConvertImpl implements StoreDeptConvert {

    @Override
    public StoreDeptReqVO convert(StoreDeptDO bean) {
        if ( bean == null ) {
            return null;
        }

        StoreDeptReqVO storeDeptReqVO = new StoreDeptReqVO();

        return storeDeptReqVO;
    }

    @Override
    public List<StoreDeptReqVO> convert(List<StoreDeptDO> list) {
        if ( list == null ) {
            return null;
        }

        List<StoreDeptReqVO> list1 = new ArrayList<StoreDeptReqVO>( list.size() );
        for ( StoreDeptDO storeDeptDO : list ) {
            list1.add( convert( storeDeptDO ) );
        }

        return list1;
    }
}
