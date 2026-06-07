package org.example.mapper;

import org.apache.ibatis.annotations.*;
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

    /**
     * 新增员工基本信息
     */
    @Options(useGeneratedKeys = true, keyProperty = "id") //获取到生成的主键 -- 主键返回
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            " values (#{username}, #{name}, #{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);
}
