/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaksi.service.impl;

import com.je.transaksi.model.Penjualan;
import com.je.transaksi.dao.PenjualanDao;
import com.je.transaksi.service.PenjualanService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("penjualanService")
@Transactional
public class PenjualanServiceImpl implements PenjualanService{
    
    @Autowired
    PenjualanDao penjualanDao;

    public List<Penjualan> getAll(int start, int limit, String order, Map<String, String> params) {
        return penjualanDao.getAll(start, limit, order, params);
    }

    public Penjualan getById(String id) {
       return penjualanDao.getById(id);
    }

    public long insert(Penjualan penjualan) {
        return penjualanDao.insert(penjualan);
    }

    public long update(Penjualan penjualan) {
        return penjualanDao.update(penjualan);
    }

    public long delete(Penjualan penjualan) {
        return penjualanDao.delete(penjualan);
    }
    
}
