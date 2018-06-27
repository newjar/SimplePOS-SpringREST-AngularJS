/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.masterdata.service;

import com.je.masterdata.model.Customer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface CustomerService {
    
    public List<Customer> getAll(int start, int limit, String order, Map<String, String> params);
    public Customer getById(String id);
    public long insert(Customer customer);
    public long update(Customer customer);
    public long delete(Customer customer);    
}
