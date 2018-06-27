/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.masterdata.dao.impl;

import com.je.masterdata.dao.BarangDao;
import com.je.masterdata.model.Barang;
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


@Repository("barangDao")
public class BarangDaoImpl implements BarangDao {

    private static final String SQL_SELECT_BARANG= "SELECT KODE,NAMA,STOK,SATUAN,HARGA_BELI,HARGA_JUAL FROM BARANG";
    private static final String SQL_SELECT_BARANG_BY_ID = "SELECT KODE,NAMA,STOK,SATUAN,HARGA_BELI,HARGA_JUAL FROM BARANG WHERE KODE = ?";
    private static final String SQL_DELETE_BARANG_BY_ID = "DELETE FROM BARANG WHERE KODE = ?";
    private static final String SQL_INSERT_BARANG = "INSERT INTO BARANG (KODE,NAMA,STOK,SATUAN,HARGA_BELI,HARGA_JUAL) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE_BARANG = "UPDATE BARANG SET NAMA=?, STOK=?, SATUAN=?, HARGA_BELI=?, HARGA_JUAL=?  WHERE KODE=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Barang> getAll(int start, int limit, String order, Map<String, String> params) {        
        List<Barang> result = null;
        String where = Utils.getClauseWhere(params);
        String orderBy = Utils.getOrderBy(order);
        if (start > -1 && limit > 0) {
            result =  jdbcTemplate.query(SQL_SELECT_BARANG + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, new BeanPropertyRowMapper<Barang>(Barang.class));
        } else {
            result =  jdbcTemplate.query(SQL_SELECT_BARANG + where + " ORDER BY " + orderBy, new Object[]{}, new BeanPropertyRowMapper<Barang>(Barang.class));          
        }        
        return result;
    }

    public Barang getById(String id) {
        Barang result = null;
    	try{     		  
            result = (Barang) jdbcTemplate.queryForObject(SQL_SELECT_BARANG_BY_ID,
                new Object[]{id},new RowMapper<Barang>() {
                    public Barang mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                            Barang barang = new Barang();
                            barang.setKode(rs.getString("kode"));
                            barang.setNama(rs.getString("nama"));
                            barang.setStok(rs.getInt("stok"));
                            barang.setSatuan(rs.getString("satuan"));
                            barang.setHarga_beli(rs.getInt("harga_beli"));
                            barang.setHarga_jual(rs.getInt("harga_jual"));
                        return barang;
                    }
                }
            );    		
    	}catch(Exception e){
            e.printStackTrace();
    	}    	
    	return result;
    }

    public long insert(Barang barang) {
     long result = 0;
        try{
            result = jdbcTemplate.update(SQL_INSERT_BARANG, new Object[]{barang.getKode(),barang.getNama(),barang.getStok(),barang.getSatuan(),barang.getHarga_beli(),barang.getHarga_jual()});  	
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public long update(Barang barang){
    long result = 0;
        try{
            result = jdbcTemplate.update(SQL_UPDATE_BARANG, new Object[]{barang.getNama(),barang.getStok(),barang.getSatuan(),barang.getHarga_beli(),barang.getHarga_jual(),barang.getKode()}); 	
        }catch(Exception e){
                e.printStackTrace();
        }
        return result;
    }
    
    public long delete(Barang barang) {
        long result = 0;
        try{
            result = jdbcTemplate.update(SQL_DELETE_BARANG_BY_ID, new Object[]{barang.getKode()});
        }catch(Exception e){
                e.printStackTrace();
        }
        return result;
    }   
}
