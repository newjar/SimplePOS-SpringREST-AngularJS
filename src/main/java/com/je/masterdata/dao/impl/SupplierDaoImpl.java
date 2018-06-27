/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.masterdata.dao.impl;

import com.je.masterdata.dao.SupplierDao;
import com.je.masterdata.model.Supplier;
import com.je.util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("supplierDao")
public class SupplierDaoImpl implements SupplierDao {

    private static final String SQL_SELECT_SUPPLIER= "SELECT KODE,NAMA,ALAMAT,NO_TELP,EMAIL FROM SUPPLIER";
    private static final String SQL_SELECT_SUPPLIER_BY_ID = "SELECT KODE,NAMA,ALAMAT,NO_TELP,EMAIL FROM SUPPLIER WHERE KODE = ?";
    private static final String SQL_DELETE_SUPPLIER_BY_ID = "DELETE FROM SUPPLIER WHERE KODE = ?";
    private static final String SQL_INSERT_SUPPLIER = "INSERT INTO SUPPLIER (KODE,NAMA,ALAMAT,NO_TELP,EMAIL) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE_SUPPLIER = "UPDATE SUPPLIER SET NAMA=?, ALAMAT=?, NO_TELP=?, EMAIL=?  WHERE KODE=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Supplier> getAll(int start, int limit, String order, Map<String, String> params) {        
        List<Supplier> result = null;
        String where = Utils.getClauseWhere(params);
        String orderBy = Utils.getOrderBy(order);
        if (start > -1 && limit > 0) {
            result =  jdbcTemplate.query(SQL_SELECT_SUPPLIER + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, new BeanPropertyRowMapper<Supplier>(Supplier.class));
        } else {
            result =  jdbcTemplate.query(SQL_SELECT_SUPPLIER + where + " ORDER BY " + orderBy, new Object[]{}, new BeanPropertyRowMapper<Supplier>(Supplier.class));          
        }        
        return result;
    }

    public Supplier getById(String id) {
        Supplier result = null;
    	try{     		  
            result = (Supplier) jdbcTemplate.queryForObject(SQL_SELECT_SUPPLIER_BY_ID,
                new Object[]{id},new RowMapper<Supplier>() {
                    public Supplier mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                            Supplier supplier = new Supplier();
                            supplier.setKode(rs.getString("kode"));
                            supplier.setNama(rs.getString("nama"));
                            supplier.setAlamat(rs.getString("alamat"));
                            supplier.setNo_telp(rs.getString("no_telp"));
                            supplier.setEmail(rs.getString("email"));
                        return supplier;
                    }
                }
            );    		
    	}catch(Exception e){
            e.printStackTrace();
    	}    	
    	return result;
    }

    public long insert(Supplier supplier) {
     long result = 0;
        try{
            result = jdbcTemplate.update(SQL_INSERT_SUPPLIER, new Object[]{supplier.getKode(),supplier.getNama(),supplier.getAlamat(),supplier.getNo_telp(),supplier.getEmail()});  	
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public long update(Supplier supplier){
    long result = 0;
        try{
            result = jdbcTemplate.update(SQL_UPDATE_SUPPLIER, new Object[]{supplier.getNama(),supplier.getAlamat(),supplier.getNo_telp(),supplier.getEmail(),supplier.getKode()}); 	
        }catch(Exception e){
                e.printStackTrace();
        }
        return result;
    }
    
    public long delete(Supplier supplier) {
        long result = 0;
        try{
            result = jdbcTemplate.update(SQL_DELETE_SUPPLIER_BY_ID, new Object[]{supplier.getKode()});
        }catch(Exception e){
                e.printStackTrace();
        }
        return result;
    }   
}
