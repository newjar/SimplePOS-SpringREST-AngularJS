/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaksi.controller;

import com.je.masterdata.model.Supplier;
import com.je.masterdata.model.Barang;
import com.je.masterdata.service.BarangService;
import com.je.masterdata.service.SupplierService;
import com.je.masterdata.service.BarangService;
import com.je.transaksi.model.Pembelian;
import com.je.transaksi.service.PembelianService;
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
public class PembelianController {
    private static final String PEMBELIAN_KEY = "pembelian";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PembelianService pembelianService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private BarangService barangService;

    @RequestMapping(value = "/transaksi/pembelian", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
    	
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, pembelianService.getAll(start, limit, orderBy, assocParams));
        responseMap.put(Constants.TOTAL, responseMap.size());
        return responseMap;
    }
    
    
    @RequestMapping(value = "/transaksi/pembelian/{kode}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("kode") String kode) {

    	
    	 Pembelian pembelian = pembelianService.getById(kode);

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(PEMBELIAN_KEY, pembelian);

         return responseMap;
    }
   
    @RequestMapping(value = "/transaksi/pembelian/{kode}", method = RequestMethod.DELETE, produces = "application/json")
    public Map<String, Object> delete(@PathVariable("kode") final String kode) {
    	
    	
    	 Pembelian pembelian = new Pembelian();
         pembelian.setKode(kode);

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(Constants.STATUS, (pembelianService.delete(pembelian) != 0) ? Constants.OK : Constants.ERROR);

         return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/transaksi/pembelian", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) throws ParseException {

        Date waktu = new Date();
        SimpleDateFormat frmWaktuKode = new SimpleDateFormat("yyyyMMddHHmmss");
	String kode = "NB"+frmWaktuKode.format(waktu);
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
    	
    	Map<String, Object> pembelianMap = (Map<String, Object>) params.get(PEMBELIAN_KEY);
        Pembelian pembelian = new Pembelian();
        
        Date tgl_pembelian = null;
        if(pembelianMap.get("tgl_pembelian") instanceof String){
        	 String tgl_pembelianString = (String)pembelianMap.get("tgl_pembelian");
        	 tgl_pembelian = formatter.parse(tgl_pembelianString);
        }else{
        	 Long tgl_pembelianLong = (Long)pembelianMap.get("tgl_pembelian");
                 tgl_pembelian = new Date(tgl_pembelianLong);
        }

        Map<String, Object> supplierMap = (Map<String, Object>) pembelianMap.get("supplier");
        Map<String, Object> barangMap = (Map<String, Object>) pembelianMap.get("barang");

        // Validasi Supplier
        Supplier supplier= supplierService.getById((String)supplierMap.get("kode"));
        if(supplier == null){
            responseMap.put(Constants.ERROR, "Supplier "+(String)supplierMap.get("kode")+" Tidak Ada Di Database");
            return responseMap;
        }
        // Validasi Barang
        Barang barang= barangService.getById((String)barangMap.get("kode"));
        if(barang == null){
            responseMap.put(Constants.ERROR, "Barang "+(String)barangMap.get("kode")+" Tidak Ada Di Database");
            return responseMap;
        }

        pembelian.setKode(kode);
        pembelian.setTgl_pembelian(new Timestamp(tgl_pembelian.getTime()));
        pembelian.setJumlah((Integer) pembelianMap.get("jumlah"));
        pembelian.setHarga_barang((Integer) pembelianMap.get("harga_barang"));
        pembelian.setSub_total_harga((Integer) pembelianMap.get("sub_total_harga"));        
        pembelian.setSupplier(supplier);
        pembelian.setBarang(barang);
       
        responseMap.put(Constants.STATUS, (pembelianService.insert(pembelian) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/transaksi/pembelian/{kode}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("kode") final String kode,
            @RequestBody final Map<String, Object> params) throws ParseException {
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
    	
    	Map<String, Object> pembelianMap = (Map<String, Object>) params.get(PEMBELIAN_KEY);
        Pembelian pembelian = new Pembelian();
        
        Date tgl_pembelian = null;
        if(pembelianMap.get("tgl_pembelian") instanceof String){
        	 String tgl_pembelianString = (String)pembelianMap.get("tgl_pembelian");
        	 tgl_pembelian = formatter.parse(tgl_pembelianString);
        }else{
        	 Long tgl_pembelianLong = (Long)pembelianMap.get("tgl_pembelian");
                 tgl_pembelian = new Date(tgl_pembelianLong);
        }
        
        Map<String, Object> supplierMap = (Map<String, Object>) pembelianMap.get("supplier");
        Map<String, Object> barangMap = (Map<String, Object>) pembelianMap.get("barang");

        // Validasi Supplier
        Supplier supplier= supplierService.getById((String)supplierMap.get("kode"));
        if(supplier == null){
            responseMap.put(Constants.ERROR, "Supplier "+(String)supplierMap.get("kode")+" Tidak Ada Di Database");
            return responseMap;
        }
        // Validasi Barang
        Barang barang= barangService.getById((String)barangMap.get("kode"));
        if(barang == null){
            responseMap.put(Constants.ERROR, "Barang "+(String)barangMap.get("kode")+" Tidak Ada Di Database");
            return responseMap;
        }

        pembelian.setKode(kode);
        pembelian.setTgl_pembelian(new Timestamp(tgl_pembelian.getTime()));
        pembelian.setJumlah((Integer) pembelianMap.get("jumlah"));
        pembelian.setHarga_barang((Integer) pembelianMap.get("harga_barang"));
        pembelian.setSub_total_harga((Integer) pembelianMap.get("sub_total_harga"));        
        pembelian.setSupplier(supplier);
        pembelian.setBarang(barang);
       
        responseMap.put(Constants.STATUS, (pembelianService.update(pembelian) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    
    }    
}
