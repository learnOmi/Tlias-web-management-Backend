package org.example.service;

import org.example.pojo.Dept;

import java.util.List;

public interface DeptService {

    /**
    * 查询所有部门
    */
    List<Dept> findAll();
}
