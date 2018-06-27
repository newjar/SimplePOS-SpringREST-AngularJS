package com.je.masterdata.dao;

import java.util.List;
import java.util.Map;

import com.je.masterdata.model.Barang;

public interface BarangDao {
	
	 public List<Barang> getAll(int start, int limit, String order, Map<String, String> params);
	 public Barang getById(String id);
	 public long insert(Barang barang);
	 public long update(Barang barang);
	 public long delete(Barang barang);
}
