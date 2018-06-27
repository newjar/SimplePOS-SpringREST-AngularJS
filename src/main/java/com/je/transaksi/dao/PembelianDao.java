/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaksi.dao;

import com.je.transaksi.model.Pembelian;
import java.util.List;
import java.util.Map;

public interface PembelianDao {
    
    public List<Pembelian> getAll(int start, int limit, String order, Map<String, String> params);
    public Pembelian getById(String id);
    public long insert(Pembelian pembelian);
    public long update(Pembelian pembelian);
    public long delete(Pembelian pembelian);
    
}
