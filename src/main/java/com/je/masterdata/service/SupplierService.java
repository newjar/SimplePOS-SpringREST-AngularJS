/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.masterdata.service;

import com.je.masterdata.model.Supplier;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface SupplierService {
    
    public List<Supplier> getAll(int start, int limit, String order, Map<String, String> params);
    public Supplier getById(String id);
    public long insert(Supplier supplier);
    public long update(Supplier supplier);
    public long delete(Supplier supplier);
}
