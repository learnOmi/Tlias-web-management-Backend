package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Dept;

import java.util.List;

@Mapper
public interface DeptMapper {

    /**
     * 查询所有部门
     */
    @Select("select id,name,create_time,update_time from dept")
    List<Dept> selectAll();
}
