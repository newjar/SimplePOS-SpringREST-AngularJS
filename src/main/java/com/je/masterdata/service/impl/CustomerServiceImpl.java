/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.masterdata.service.impl;


import com.je.masterdata.dao.CustomerDao;
import com.je.masterdata.model.Customer;
import com.je.masterdata.service.CustomerService;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerDao customerDao;


    public List<Customer> getAll(int start, int limit, String order, Map<String, String> params) {
       return customerDao.getAll(start, limit, order, params);
    }

    public Customer getById(String id) {
       return customerDao.getById(id);
    }

    public long insert(Customer customer) {
        return customerDao.insert(customer);
    }

    public long update(Customer customer) {
        return customerDao.update(customer);
    }

    public long delete(Customer customer) {
        return customerDao.delete(customer);
    }
}
