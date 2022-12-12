package com.cainiao.dao;

import com.cainiao.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author peishunwu
 * @since 2022-12-12
 */
@Mapper
public interface StudentDao extends BaseMapper<Student> {

    void saveList(@Param("list") List<Student> studentList);
}
