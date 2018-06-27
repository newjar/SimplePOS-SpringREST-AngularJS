package com.je.masterdata.controller;

import com.je.masterdata.model.Customer;
import com.je.masterdata.service.CustomerService;
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
public class CustomerController {
	
    private static final String CUSTOMER_KEY = "customer";

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/masterdata/customer", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
    	
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, customerService.getAll(start, limit, orderBy, assocParams));
        responseMap.put(Constants.TOTAL, responseMap.size());
        return responseMap;
    }
    
    @RequestMapping(value = "/masterdata/customer/{kode}", method = RequestMethod.DELETE, produces = "application/json")
    public Map<String, Object> delete(@PathVariable("kode") final String kode) {    	
    	
    	 Customer customer = new Customer();
         customer.setKode(kode);

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(Constants.STATUS, (customerService.delete(customer) != 0) ? Constants.OK : Constants.ERROR);

         return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/customer", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) {

    	
    	Map<String, Object> customerMap = (Map<String, Object>) params.get(CUSTOMER_KEY);
        Customer customer = new Customer();

        customer.setKode((String) customerMap.get("kode"));
        customer.setNama((String) customerMap.get("nama"));
        customer.setAlamat((String) customerMap.get("alamat"));
        customer.setNo_telp((String) customerMap.get("no_telp"));
        customer.setEmail((String) customerMap.get("email"));
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (customerService.insert(customer) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    }

    @RequestMapping(value = "/masterdata/customer/{kode}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("kode") String kode) {
        
    	 Customer customer = customerService.getById(kode);
         
         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(CUSTOMER_KEY, customer);

         return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/customer/{kode}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("kode") final String kode,
        @RequestBody final Map<String, Object> params) {
        
    	Map<String, Object> customerMap = (Map<String, Object>) params.get(CUSTOMER_KEY);
        Customer customer = new Customer();

        customer.setKode((String) customerMap.get("kode"));
        customer.setNama((String) customerMap.get("nama"));
        customer.setAlamat((String) customerMap.get("alamat"));
        customer.setNo_telp((String) customerMap.get("no_telp"));
        customer.setEmail((String) customerMap.get("email"));        

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (customerService.update(customer) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    }
}
