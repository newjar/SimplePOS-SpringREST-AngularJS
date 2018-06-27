package com.je.masterdata.dao;

import java.util.List;
import java.util.Map;

import com.je.masterdata.model.Customer;

public interface CustomerDao {
	
	 public List<Customer> getAll(int start, int limit, String order, Map<String, String> params);
	 public Customer getById(String id);
	 public long insert(Customer customer);
	 public long update(Customer customer);
	 public long delete(Customer customer);
}
