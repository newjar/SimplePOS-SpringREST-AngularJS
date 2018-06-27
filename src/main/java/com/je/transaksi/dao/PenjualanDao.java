/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaksi.dao;

import com.je.transaksi.model.Penjualan;
import java.util.List;
import java.util.Map;

public interface PenjualanDao {
    
    public List<Penjualan> getAll(int start, int limit, String order, Map<String, String> params);
    public Penjualan getById(String id);
    public long insert(Penjualan penjualan);
    public long update(Penjualan penjualan);
    public long delete(Penjualan penjualan);
    
}
