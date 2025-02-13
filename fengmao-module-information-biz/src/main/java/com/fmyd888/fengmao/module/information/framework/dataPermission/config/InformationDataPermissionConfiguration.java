package com.fmyd888.fengmao.module.information.framework.dataPermission.config;

import com.fmyd888.fengmao.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * information 模块的数据权限 Configuration
 *
 * @author  fengmao
 */
@Configuration(proxyBeanMethods = false)
public class InformationDataPermissionConfiguration {

    @Bean(name = "informationDeptDataPermissionRuleCustomizer")
    public DeptDataPermissionRuleCustomizer informationDeptDataPermissionRuleCustomizer() {
        return rule -> {
            rule.addSubTable(TaxratesDO.class, TaxratesDeptDO.class);
            rule.addSubTable(CommodityDO.class, CommodityDeptDO.class);
            rule.addSubTable(CurrencyDO.class, CurrencyDeptDO.class);
            rule.addSubTable(CustomerDO.class, CustomerDeptDO.class);
            rule.addSubTable(SalesmanDO.class, SalesmanDeptDO.class);
            rule.addSubTable(MeasurementDO.class, MeasurementDeptDO.class);
            rule.addSubTable(StoreDO.class, StoreDeptDO.class);
            rule.addDeptColumn(AddressDO.class,"dept_id");
            rule.addDeptColumn(MainVehicleDO.class,"dept_id");
            rule.addUserColumn(MainVehicleDO.class,"creator");
            rule.addDeptColumn(TrailerDO.class,"dept_id");
            rule.addDeptColumn(TrailerDO.class,"creator");
        };
    }

}
