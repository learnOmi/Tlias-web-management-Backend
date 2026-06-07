package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.EmpMapper;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.PageResult;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageResult<Emp> getByPage(EmpQueryParam empQueryParam) {

        /*

        if (empQueryParam.getPage() == null || empQueryParam.getPage() <= 0) {
            empQueryParam.setPage(1);
        }
        if (empQueryParam.getPageSize() == null || empQueryParam.getPageSize() <= 0) {
            empQueryParam.setPageSize(10);
        }
        empQueryParam.setPage((empQueryParam.getPage() - 1) * empQueryParam.getPageSize());

        Long total = empMapper.selectCount();
        List<Emp> resultList = empMapper.selectByPage(empQueryParam);

         */

        // 使用PageHelper实现分页查询
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        List<Emp> resultList = empMapper.selectByPage(empQueryParam);
        Page<Emp> page = (Page<Emp>) resultList;

        return new PageResult<>(page.getTotal(), page.getResult());
    }
}
