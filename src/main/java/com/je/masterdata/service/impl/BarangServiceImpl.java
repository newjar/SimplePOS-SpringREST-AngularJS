/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.masterdata.service.impl;


import com.je.masterdata.dao.BarangDao;
import com.je.masterdata.model.Barang;
import com.je.masterdata.service.BarangService;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





@Service("barangService")
@Transactional
public class BarangServiceImpl implements BarangService{

    @Autowired
    private BarangDao barangDao;


    public List<Barang> getAll(int start, int limit, String order, Map<String, String> params) {
       return barangDao.getAll(start, limit, order, params);
    }

    public Barang getById(String id) {
       return barangDao.getById(id);
    }

    public long insert(Barang barang) {
        return barangDao.insert(barang);
    }

    public long update(Barang barang) {
        return barangDao.update(barang);
    }

    public long delete(Barang barang) {
        return barangDao.delete(barang);
    }
}
