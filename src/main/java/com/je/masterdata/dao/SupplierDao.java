package com.je.masterdata.dao;

import java.util.List;
import java.util.Map;

import com.je.masterdata.model.Supplier;

public interface SupplierDao {
	
	 public List<Supplier> getAll(int start, int limit, String order, Map<String, String> params);
	 public Supplier getById(String id);
	 public long insert(Supplier supplier);
	 public long update(Supplier supplier);
	 public long delete(Supplier supplier);
}
