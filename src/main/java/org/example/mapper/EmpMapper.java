package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.PageResult;

import java.util.List;

/**
 * 员工DAO
 */
@Mapper
public interface EmpMapper {

    /**
     * 分页查询员工
     */
    List<Emp> selectByPage(EmpQueryParam empQueryParam);

    /**
     * 查询员工总记录数
     */
    @Select("select count(*) from emp left join dept on emp.dept_id = dept.id")
    Long selectCount();
}
