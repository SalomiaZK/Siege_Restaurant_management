package com.example.local.Repository.dao;

import java.util.ArrayList;

public interface CrudOperation <T>{
 public ArrayList<T> findAll(int page);// done
  public T create(T toadd);
 public boolean delete(String id);// done
    public T findById(String id);

}