package org.example.service;

import org.example.pojo.Dept;

import java.util.List;

public interface DeptService {

    /**
    * 查询所有部门
    */
    List<Dept> findAll();

    /**
     * 删除部门
     */
    void deleteById(Integer id);

    /**
     * 添加部门
     */
    void add(Dept dept);

    /**
     * 根据ID查询部门
     */
    Dept getById(Integer id);

    /**
     * 修改部门
     */
    void update(Dept dept);
}
