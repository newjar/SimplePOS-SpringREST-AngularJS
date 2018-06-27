/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaksi.dao.impl;

import com.je.masterdata.model.Barang;
import com.je.masterdata.model.Customer;
import com.je.transaksi.dao.PenjualanDao;
import com.je.transaksi.model.Penjualan;
import com.je.util.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("penjualanDao")
public class PenjualanDaoImpl implements PenjualanDao{
    
    private static final String SQL_SELECT_PENJUALAN= "select p.kode as kode , p.tgl_penjualan as tgl_penjualan, p.jumlah as jumlah, p.harga_barang as harga_barang, p.sub_total_harga as sub_total_harga, "
            + "c.kode as kode_customer,c.nama as nama_customer, "
            + "b.kode as kode_barang, b.nama as nama_barang, b.harga_jual as harga_barang, b.stok as stok "
            + "from penjualan p inner join customer c on c.kode=p.kode_customer inner join barang b on b.kode=p.kode_barang";
    private static final String SQL_SELECT_PENJUALAN_BY_ID = "select p.kode as kode ,p.tgl_penjualan as tgl_penjualan, p.jumlah as jumlah, p.harga_barang as harga_barang, p.sub_total_harga as sub_total_harga, "
            + "c.kode as kode_customer,c.nama as nama_customer, "
            + "b.kode as kode_barang, b.nama as nama_barang, b.harga_jual as harga_barang, b.stok as stok "
            + "from penjualan  p inner join customer c on c.kode=p.kode_customer inner join barang b on b.kode=p.kode_barang where p.kode = ?";
    private static final String SQL_DELETE_PENJUALAN_BY_ID = "delete from penjualan where kode = ?";
    private static final String SQL_INSERT_PENJUALAN = "insert into penjualan (kode, kode_customer, kode_barang, tgl_penjualan, jumlah, harga_barang, sub_total_harga) "
            + "VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_PENJUALAN = "update penjualan SET tgl_penjualan=?, jumlah=?, harga_barang=?, sub_total_harga=? "
            + "where kode=? and kode_customer=? and kode_barang=?";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;



    public List<Penjualan> getAll(int start, int limit, String order, Map<String, String> params) {
        List<Penjualan> result = null;
        String where = Utils.getClauseWhere(params);
        String orderBy = Utils.getOrderBy(order);
        if (start > -1 && limit > 0) {
            result =  jdbcTemplate.query(SQL_SELECT_PENJUALAN + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, getRowMapper());
        } else {
            result =  jdbcTemplate.query(SQL_SELECT_PENJUALAN + where + " ORDER BY " + orderBy, new Object[]{}, getRowMapper());
          
        }        
        return result;
    }   
    
    public Penjualan getById(String id) {
          Penjualan result = null;
    	try{
            result = (Penjualan) jdbcTemplate.queryForObject(SQL_SELECT_PENJUALAN_BY_ID,new Object[]{id},getRowMapper());    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return result;
    }

    public long insert(Penjualan penjualan) {
      long result = 0;
    	 try{
                result = jdbcTemplate.update(SQL_INSERT_PENJUALAN, new Object[]{penjualan.getKode(),penjualan.getCustomer().getKode(),penjualan.getBarang().getKode(),penjualan.getTgl_penjualan(),penjualan.getJumlah(),penjualan.getHarga_barang(),penjualan.getSub_total_harga()});  	
    	 }catch(Exception e){
                e.printStackTrace();
    	 }
    	 return result;
    }

    public long update(Penjualan penjualan) {
        long result = 0;
    	 try{
                result = jdbcTemplate.update(SQL_UPDATE_PENJUALAN, new Object[]{penjualan.getTgl_penjualan(),penjualan.getJumlah(),penjualan.getHarga_barang(),penjualan.getSub_total_harga(),penjualan.getKode(),penjualan.getCustomer().getKode(),penjualan.getBarang().getKode()});  	
    	 }catch(Exception e){
                e.printStackTrace();
    	 }
    	 return result;
    }

    public long delete(Penjualan penjualan) {
      long result = 0;
		 try{
                    result = jdbcTemplate.update(SQL_DELETE_PENJUALAN_BY_ID, new Object[]{penjualan.getKode()});
		 }catch(Exception e){
                    e.printStackTrace();
		 }
		 return result;
    }
    
    
    private RowMapper<Penjualan> getRowMapper(){
           return new RowMapper<Penjualan>(){
                   @Override
                   public Penjualan mapRow(ResultSet rs, int rowNum) throws SQLException{
                           Penjualan penjualan = new Penjualan();
                           penjualan.setKode(rs.getString("kode"));
                           penjualan.setTgl_penjualan(rs.getTimestamp("tgl_penjualan"));
                           penjualan.setJumlah(rs.getInt("jumlah"));
                           penjualan.setHarga_barang(rs.getInt("harga_barang"));
                           penjualan.setSub_total_harga(rs.getInt("sub_total_harga"));

                           Customer customer = new Customer();
                           customer.setKode(rs.getString("kode_customer"));
                           customer.setNama(rs.getString("nama_customer"));
                           penjualan.setCustomer(customer);
                           
                           Barang barang = new Barang();
                           barang.setKode(rs.getString("kode_barang"));
                           barang.setNama(rs.getString("nama_barang"));
                           barang.setHarga_jual(rs.getInt("harga_barang"));
                           barang.setStok(rs.getInt("stok"));
                           penjualan.setBarang(barang);

                           return penjualan;
                   }
           };
   }
    
}
