/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.masterdata.service.impl;


import com.je.masterdata.dao.SupplierDao;
import com.je.masterdata.model.Supplier;
import com.je.masterdata.service.SupplierService;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





@Service("supplierService")
@Transactional
public class SupplierServiceImpl implements SupplierService{

    @Autowired
    private SupplierDao supplierDao;


    public List<Supplier> getAll(int start, int limit, String order, Map<String, String> params) {
       return supplierDao.getAll(start, limit, order, params);
    }

    public Supplier getById(String id) {
       return supplierDao.getById(id);
    }

    public long insert(Supplier supplier) {
        return supplierDao.insert(supplier);
    }

    public long update(Supplier supplier) {
        return supplierDao.update(supplier);
    }

    public long delete(Supplier supplier) {
        return supplierDao.delete(supplier);
    }
}
