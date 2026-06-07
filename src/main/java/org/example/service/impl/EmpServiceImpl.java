package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.EmpExprMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.Emp;
import org.example.pojo.EmpExpr;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.PageResult;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

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

    @Transactional(rollbackFor = {Exception.class}) //事务管理 - 默认出现运行时异常RuntimeException才会回滚
    @Override
    public void save(Emp emp) throws Exception {
        //1. 保存员工基本信息
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);

        //2. 保存员工工作经历信息
        List<EmpExpr> exprList = emp.getEmpExprs();
        if(!CollectionUtils.isEmpty(exprList)){
            //遍历集合, 为empId赋值
            exprList.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(exprList);
        }
    }

}
