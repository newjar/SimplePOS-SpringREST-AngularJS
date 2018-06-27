/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaksi.service.impl;

import com.je.transaksi.model.Pembelian;
import com.je.transaksi.dao.PembelianDao;
import com.je.transaksi.service.PembelianService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("pembelianService")
@Transactional
public class PembelianServiceImpl implements PembelianService{
    
    @Autowired
    PembelianDao pembelianDao;

    public List<Pembelian> getAll(int start, int limit, String order, Map<String, String> params) {
        return pembelianDao.getAll(start, limit, order, params);
    }

    public Pembelian getById(String id) {
       return pembelianDao.getById(id);
    }

    public long insert(Pembelian pembelian) {
        return pembelianDao.insert(pembelian);
    }

    public long update(Pembelian pembelian) {
        return pembelianDao.update(pembelian);
    }

    public long delete(Pembelian pembelian) {
        return pembelianDao.delete(pembelian);
    }
    
}
