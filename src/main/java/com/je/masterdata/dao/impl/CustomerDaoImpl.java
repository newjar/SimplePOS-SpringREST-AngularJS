/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.masterdata.dao.impl;

import com.je.masterdata.dao.CustomerDao;
import com.je.masterdata.model.Customer;
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


@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao {

    private static final String SQL_SELECT_CUSTOMER= "SELECT KODE,NAMA,ALAMAT,NO_TELP,EMAIL FROM CUSTOMER";
    private static final String SQL_SELECT_CUSTOMER_BY_ID = "SELECT KODE,NAMA,ALAMAT,NO_TELP,EMAIL FROM CUSTOMER WHERE KODE = ?";
    private static final String SQL_DELETE_CUSTOMER_BY_ID = "DELETE FROM CUSTOMER WHERE KODE = ?";
    private static final String SQL_INSERT_CUSTOMER = "INSERT INTO CUSTOMER (KODE,NAMA,ALAMAT,NO_TELP,EMAIL) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE_CUSTOMER = "UPDATE CUSTOMER SET NAMA=?, ALAMAT=?, NO_TELP=?, EMAIL=?  WHERE KODE=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> getAll(int start, int limit, String order, Map<String, String> params) {        
        List<Customer> result = null;
        String where = Utils.getClauseWhere(params);
        String orderBy = Utils.getOrderBy(order);
        if (start > -1 && limit > 0) {
            result =  jdbcTemplate.query(SQL_SELECT_CUSTOMER + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, new BeanPropertyRowMapper<Customer>(Customer.class));
        } else {
            result =  jdbcTemplate.query(SQL_SELECT_CUSTOMER + where + " ORDER BY " + orderBy, new Object[]{}, new BeanPropertyRowMapper<Customer>(Customer.class));          
        }        
        return result;
    }

    public Customer getById(String id) {
        Customer result = null;
    	try{     		  
            result = (Customer) jdbcTemplate.queryForObject(SQL_SELECT_CUSTOMER_BY_ID,
                new Object[]{id},new RowMapper<Customer>() {
                    public Customer mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                            Customer customer = new Customer();
                            customer.setKode(rs.getString("kode"));
                            customer.setNama(rs.getString("nama"));
                            customer.setAlamat(rs.getString("alamat"));
                            customer.setNo_telp(rs.getString("no_telp"));
                            customer.setEmail(rs.getString("email"));
                        return customer;
                    }
                }
            );    		
    	}catch(Exception e){
            e.printStackTrace();
    	}    	
    	return result;
    }

    public long insert(Customer customer) {
     long result = 0;
        try{
            result = jdbcTemplate.update(SQL_INSERT_CUSTOMER, new Object[]{customer.getKode(),customer.getNama(),customer.getAlamat(),customer.getNo_telp(),customer.getEmail()});  	
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public long update(Customer customer){
    long result = 0;
        try{
            result = jdbcTemplate.update(SQL_UPDATE_CUSTOMER, new Object[]{customer.getNama(),customer.getAlamat(),customer.getNo_telp(),customer.getEmail(),customer.getKode()}); 	
        }catch(Exception e){
                e.printStackTrace();
        }
        return result;
    }
    
    public long delete(Customer customer) {
        long result = 0;
        try{
            result = jdbcTemplate.update(SQL_DELETE_CUSTOMER_BY_ID, new Object[]{customer.getKode()});
        }catch(Exception e){
                e.printStackTrace();
        }
        return result;
    }   
}
