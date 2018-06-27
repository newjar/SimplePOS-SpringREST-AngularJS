/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaksi.controller;

import com.je.masterdata.model.Customer;
import com.je.masterdata.model.Barang;
import com.je.masterdata.service.BarangService;
import com.je.masterdata.service.CustomerService;
import com.je.masterdata.service.BarangService;
import com.je.transaksi.model.Penjualan;
import com.je.transaksi.service.PenjualanService;
import com.je.util.Constants;
import com.je.util.Utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PenjualanController {
    private static final String PENJUALAN_KEY = "penjualan";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PenjualanService penjualanService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private BarangService barangService;

    @RequestMapping(value = "/transaksi/penjualan", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
    	
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, penjualanService.getAll(start, limit, orderBy, assocParams));
        responseMap.put(Constants.TOTAL, responseMap.size());
        return responseMap;
    }
    
    
    @RequestMapping(value = "/transaksi/penjualan/{kode}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("kode") String kode) {

    	
    	 Penjualan penjualan = penjualanService.getById(kode);

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(PENJUALAN_KEY, penjualan);

         return responseMap;
    }
   
    @RequestMapping(value = "/transaksi/penjualan/{kode}", method = RequestMethod.DELETE, produces = "application/json")
    public Map<String, Object> delete(@PathVariable("kode") final String kode) {
    	
    	
    	 Penjualan penjualan = new Penjualan();
         penjualan.setKode(kode);

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(Constants.STATUS, (penjualanService.delete(penjualan) != 0) ? Constants.OK : Constants.ERROR);

         return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/transaksi/penjualan", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) throws ParseException {

        Date waktu = new Date();
        SimpleDateFormat frmWaktuKode = new SimpleDateFormat("yyyyMMddHHmmss");
	String kode = "NP"+frmWaktuKode.format(waktu);
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
    	
    	Map<String, Object> penjualanMap = (Map<String, Object>) params.get(PENJUALAN_KEY);
        Penjualan penjualan = new Penjualan();
        
        Date tgl_penjualan = null;
        if(penjualanMap.get("tgl_penjualan") instanceof String){
        	 String tgl_penjualanString = (String)penjualanMap.get("tgl_penjualan");
        	 tgl_penjualan = formatter.parse(tgl_penjualanString);
        }else{
        	 Long tgl_penjualanLong = (Long)penjualanMap.get("tgl_penjualan");
                 tgl_penjualan = new Date(tgl_penjualanLong);
        }

        Map<String, Object> customerMap = (Map<String, Object>) penjualanMap.get("customer");
        Map<String, Object> barangMap = (Map<String, Object>) penjualanMap.get("barang");

        // Validasi Customer
        Customer customer= customerService.getById((String)customerMap.get("kode"));
        if(customer == null){
            responseMap.put(Constants.ERROR, "Customer "+(String)customerMap.get("kode")+" Tidak Ada Di Database");
            return responseMap;
        }
        // Validasi Barang
        Barang barang= barangService.getById((String)barangMap.get("kode"));
        if(barang == null){
            responseMap.put(Constants.ERROR, "Barang "+(String)barangMap.get("kode")+" Tidak Ada Di Database");
            return responseMap;
        }

        penjualan.setKode(kode);
        penjualan.setTgl_penjualan(new Timestamp(tgl_penjualan.getTime()));
        penjualan.setJumlah((Integer) penjualanMap.get("jumlah"));
        penjualan.setHarga_barang((Integer) penjualanMap.get("harga_barang"));
        penjualan.setSub_total_harga((Integer) penjualanMap.get("sub_total_harga"));        
        penjualan.setCustomer(customer);
        penjualan.setBarang(barang);
       
        responseMap.put(Constants.STATUS, (penjualanService.insert(penjualan) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/transaksi/penjualan/{kode}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("kode") final String kode,
            @RequestBody final Map<String, Object> params) throws ParseException {
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
    	
    	Map<String, Object> penjualanMap = (Map<String, Object>) params.get(PENJUALAN_KEY);
        Penjualan penjualan = new Penjualan();
        
        Date tgl_penjualan = null;
        if(penjualanMap.get("tgl_penjualan") instanceof String){
        	 String tgl_penjualanString = (String)penjualanMap.get("tgl_penjualan");
        	 tgl_penjualan = formatter.parse(tgl_penjualanString);
        }else{
        	 Long tgl_penjualanLong = (Long)penjualanMap.get("tgl_penjualan");
                 tgl_penjualan = new Date(tgl_penjualanLong);
        }
        
        Map<String, Object> customerMap = (Map<String, Object>) penjualanMap.get("customer");
        Map<String, Object> barangMap = (Map<String, Object>) penjualanMap.get("barang");

        // Validasi Customer
        Customer customer= customerService.getById((String)customerMap.get("kode"));
        if(customer == null){
            responseMap.put(Constants.ERROR, "Customer "+(String)customerMap.get("kode")+" Tidak Ada Di Database");
            return responseMap;
        }
        // Validasi Barang
        Barang barang= barangService.getById((String)barangMap.get("kode"));
        if(barang == null){
            responseMap.put(Constants.ERROR, "Barang "+(String)barangMap.get("kode")+" Tidak Ada Di Database");
            return responseMap;
        }

        penjualan.setKode(kode);
        penjualan.setTgl_penjualan(new Timestamp(tgl_penjualan.getTime()));
        penjualan.setJumlah((Integer) penjualanMap.get("jumlah"));
        penjualan.setHarga_barang((Integer) penjualanMap.get("harga_barang"));
        penjualan.setSub_total_harga((Integer) penjualanMap.get("sub_total_harga"));        
        penjualan.setCustomer(customer);
        penjualan.setBarang(barang);
       
        responseMap.put(Constants.STATUS, (penjualanService.update(penjualan) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    
    }    
}
