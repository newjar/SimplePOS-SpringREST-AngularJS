/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.masterdata.service;

import com.je.masterdata.model.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface UserService {
    
    public List<User> getAll(int start, int limit, String order, Map<String, String> params);
    public User getById(String id);
    public void insert(User user);
    public void update(User user);
    public void delete(User user);    
}
