package org.example.service;

import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.PageResult;

public interface EmpService {

    PageResult<Emp> getByPage(EmpQueryParam empQueryParam);

    void save(Emp emp) throws Exception;
}
