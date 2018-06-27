/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.masterdata.service;

import com.je.masterdata.model.Barang;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface BarangService {
    
    public List<Barang> getAll(int start, int limit, String order, Map<String, String> params);
    public Barang getById(String id);
    public long insert(Barang barang);
    public long update(Barang barang);
    public long delete(Barang barang);   
}
