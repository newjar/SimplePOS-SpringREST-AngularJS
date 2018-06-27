/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaksi.dao.impl;

import com.je.masterdata.model.Barang;
import com.je.masterdata.model.Supplier;
import com.je.transaksi.dao.PembelianDao;
import com.je.transaksi.model.Pembelian;
import com.je.util.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("pembelianDao")
public class PembelianDaoImpl implements PembelianDao{
    
    private static final String SQL_SELECT_PEMBELIAN= "select p.kode as kode , p.tgl_pembelian as tgl_pembelian, p.jumlah as jumlah, p.harga_barang as harga_barang, p.sub_total_harga as sub_total_harga, "
            + "c.kode as kode_supplier,c.nama as nama_supplier, "
            + "b.kode as kode_barang, b.nama as nama_barang, b.harga_beli as harga_barang, b.stok as stok "
            + "from pembelian p inner join supplier c on c.kode=p.kode_supplier inner join barang b on b.kode=p.kode_barang";
    private static final String SQL_SELECT_PEMBELIAN_BY_ID = "select p.kode as kode ,p.tgl_pembelian as tgl_pembelian, p.jumlah as jumlah, p.harga_barang as harga_barang, p.sub_total_harga as sub_total_harga, "
            + "c.kode as kode_supplier,c.nama as nama_supplier, "
            + "b.kode as kode_barang, b.nama as nama_barang, b.harga_beli as harga_barang, b.stok as stok "
            + "from pembelian  p inner join supplier c on c.kode=p.kode_supplier inner join barang b on b.kode=p.kode_barang where p.kode = ?";
    private static final String SQL_DELETE_PEMBELIAN_BY_ID = "delete from pembelian where kode = ?";
    private static final String SQL_INSERT_PEMBELIAN = "insert into pembelian (kode, kode_supplier, kode_barang, tgl_pembelian, jumlah, harga_barang, sub_total_harga) "
            + "VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_PEMBELIAN = "update pembelian SET tgl_pembelian=?, jumlah=?, harga_barang=?, sub_total_harga=? "
            + "where kode=? and kode_supplier=? and kode_barang=?";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;



    public List<Pembelian> getAll(int start, int limit, String order, Map<String, String> params) {
        List<Pembelian> result = null;
        String where = Utils.getClauseWhere(params);
        String orderBy = Utils.getOrderBy(order);
        if (start > -1 && limit > 0) {
            result =  jdbcTemplate.query(SQL_SELECT_PEMBELIAN + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, getRowMapper());
        } else {
            result =  jdbcTemplate.query(SQL_SELECT_PEMBELIAN + where + " ORDER BY " + orderBy, new Object[]{}, getRowMapper());
          
        }        
        return result;
    }   
    
    public Pembelian getById(String id) {
          Pembelian result = null;
    	try{
            result = (Pembelian) jdbcTemplate.queryForObject(SQL_SELECT_PEMBELIAN_BY_ID,new Object[]{id},getRowMapper());    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return result;
    }

    public long insert(Pembelian pembelian) {
      long result = 0;
    	 try{
                result = jdbcTemplate.update(SQL_INSERT_PEMBELIAN, new Object[]{pembelian.getKode(),pembelian.getSupplier().getKode(),pembelian.getBarang().getKode(),pembelian.getTgl_pembelian(),pembelian.getJumlah(),pembelian.getHarga_barang(),pembelian.getSub_total_harga()});  	
    	 }catch(Exception e){
                e.printStackTrace();
    	 }
    	 return result;
    }

    public long update(Pembelian pembelian) {
        long result = 0;
    	 try{
                result = jdbcTemplate.update(SQL_UPDATE_PEMBELIAN, new Object[]{pembelian.getTgl_pembelian(),pembelian.getJumlah(),pembelian.getHarga_barang(),pembelian.getSub_total_harga(),pembelian.getKode(),pembelian.getSupplier().getKode(),pembelian.getBarang().getKode()});  	
    	 }catch(Exception e){
                e.printStackTrace();
    	 }
    	 return result;
    }

    public long delete(Pembelian pembelian) {
      long result = 0;
		 try{
                    result = jdbcTemplate.update(SQL_DELETE_PEMBELIAN_BY_ID, new Object[]{pembelian.getKode()});
		 }catch(Exception e){
                    e.printStackTrace();
		 }
		 return result;
    }
    
    
    private RowMapper<Pembelian> getRowMapper(){
           return new RowMapper<Pembelian>(){
                   @Override
                   public Pembelian mapRow(ResultSet rs, int rowNum) throws SQLException{
                           Pembelian pembelian = new Pembelian();
                           pembelian.setKode(rs.getString("kode"));
                           pembelian.setTgl_pembelian(rs.getTimestamp("tgl_pembelian"));
                           pembelian.setJumlah(rs.getInt("jumlah"));
                           pembelian.setHarga_barang(rs.getInt("harga_barang"));
                           pembelian.setSub_total_harga(rs.getInt("sub_total_harga"));

                           Supplier supplier = new Supplier();
                           supplier.setKode(rs.getString("kode_supplier"));
                           supplier.setNama(rs.getString("nama_supplier"));
                           pembelian.setSupplier(supplier);
                           
                           Barang barang = new Barang();
                           barang.setKode(rs.getString("kode_barang"));
                           barang.setNama(rs.getString("nama_barang"));
                           barang.setHarga_beli(rs.getInt("harga_barang"));
                           barang.setStok(rs.getInt("stok"));
                           pembelian.setBarang(barang);

                           return pembelian;
                   }
           };
   }
    
}
