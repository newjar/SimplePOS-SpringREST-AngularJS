package com.je.masterdata.controller;

import com.je.masterdata.model.Barang;
import com.je.masterdata.service.BarangService;
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
public class BarangController {
	
    private static final String BARANG_KEY = "barang";

    @Autowired
    private BarangService barangService;

    @RequestMapping(value = "/masterdata/barang", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
    	
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, barangService.getAll(start, limit, orderBy, assocParams));
        responseMap.put(Constants.TOTAL, responseMap.size());
        return responseMap;
    }
    
    @RequestMapping(value = "/masterdata/barang/{kode}", method = RequestMethod.DELETE, produces = "application/json")
    public Map<String, Object> delete(@PathVariable("kode") final String kode) {    	
    	
    	 Barang barang = new Barang();
         barang.setKode(kode);

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(Constants.STATUS, (barangService.delete(barang) != 0) ? Constants.OK : Constants.ERROR);

         return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/barang", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) {

    	
    	Map<String, Object> barangMap = (Map<String, Object>) params.get(BARANG_KEY);
        Barang barang = new Barang();

        barang.setKode((String) barangMap.get("kode"));
        barang.setNama((String) barangMap.get("nama"));
        barang.setStok((Integer) barangMap.get("stok"));
        barang.setSatuan((String) barangMap.get("satuan"));
        barang.setHarga_beli((Integer) barangMap.get("harga_beli"));
        barang.setHarga_jual((Integer) barangMap.get("harga_jual"));
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (barangService.insert(barang) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    }

    @RequestMapping(value = "/masterdata/barang/{kode}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("kode") String kode) {
        
    	 Barang barang = barangService.getById(kode);
         
         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(BARANG_KEY, barang);

         return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/barang/{kode}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("kode") final String kode,
        @RequestBody final Map<String, Object> params) {
        
    	Map<String, Object> barangMap = (Map<String, Object>) params.get(BARANG_KEY);
        Barang barang = new Barang();

        barang.setKode((String) barangMap.get("kode"));
        barang.setNama((String) barangMap.get("nama"));
        barang.setStok((Integer) barangMap.get("stok"));
        barang.setSatuan((String) barangMap.get("satuan"));
        barang.setHarga_beli((Integer) barangMap.get("harga_beli"));
        barang.setHarga_jual((Integer) barangMap.get("harga_jual"));        

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (barangService.update(barang) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    }
}
