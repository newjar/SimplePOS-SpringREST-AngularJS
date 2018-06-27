package com.je.masterdata.controller;

import com.je.masterdata.model.Supplier;
import com.je.masterdata.service.SupplierService;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.je.util.Constants;
import com.je.util.Utils;

@Controller
public class SupplierController {
	
    private static final String SUPPLIER_KEY = "supplier";

    @Autowired
    private SupplierService supplierService;

    @RequestMapping(value = "/masterdata/supplier", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
    	
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, supplierService.getAll(start, limit, orderBy, assocParams));
        responseMap.put(Constants.TOTAL, responseMap.size());
        return responseMap;
    }
    
    @RequestMapping(value = "/masterdata/supplier/{kode}", method = RequestMethod.DELETE, produces = "application/json")
    public Map<String, Object> delete(@PathVariable("kode") final String kode) {    	
    	
    	 Supplier supplier = new Supplier();
         supplier.setKode(kode);

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(Constants.STATUS, (supplierService.delete(supplier) != 0) ? Constants.OK : Constants.ERROR);

         return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/supplier", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) {

    	
    	Map<String, Object> supplierMap = (Map<String, Object>) params.get(SUPPLIER_KEY);
        Supplier supplier = new Supplier();

        supplier.setKode((String) supplierMap.get("kode"));
        supplier.setNama((String) supplierMap.get("nama"));
        supplier.setAlamat((String) supplierMap.get("alamat"));
        supplier.setNo_telp((String) supplierMap.get("no_telp"));
        supplier.setEmail((String) supplierMap.get("email"));
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (supplierService.insert(supplier) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    }

    @RequestMapping(value = "/masterdata/supplier/{kode}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("kode") String kode) {
        
    	 Supplier supplier = supplierService.getById(kode);
         
         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(SUPPLIER_KEY, supplier);

         return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/supplier/{kode}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("kode") final String kode,
        @RequestBody final Map<String, Object> params) {
        
    	Map<String, Object> supplierMap = (Map<String, Object>) params.get(SUPPLIER_KEY);
        Supplier supplier = new Supplier();

        supplier.setKode((String) supplierMap.get("kode"));
        supplier.setNama((String) supplierMap.get("nama"));
        supplier.setAlamat((String) supplierMap.get("alamat"));
        supplier.setNo_telp((String) supplierMap.get("no_telp"));
        supplier.setEmail((String) supplierMap.get("email"));        

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (supplierService.update(supplier) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    }
}
