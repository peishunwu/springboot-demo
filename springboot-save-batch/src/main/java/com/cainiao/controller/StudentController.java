package com.cainiao.controller;


import com.cainiao.dao.StudentDao;
import com.cainiao.entity.Student;
import com.cainiao.service.StudentService;
import io.swagger.annotations.ApiOperation;
import jdk.internal.dynalink.linker.LinkerServices;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author peishunwu
 * @since 2022-12-12
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private StudentService studentService;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @ApiOperation("for批量插入")
    @GetMapping("for")
    public void saveForStudent(){
        // 开始时间
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++){
            Student student = new Student();
            student.setName("张三" + i);
            student.setAge(i);
            student.setAddr("北京市第"+i+"街道");
            studentDao.insert(student);
        }
        // 结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("插入数据消耗时间：" + (endTime - startTime));
    }


    @ApiOperation("sql批量插入")
    @GetMapping("sql")
    public void saveSqlStudent(){
        // 开始时间
        long startTime = System.currentTimeMillis();
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < 50000; i++){
            Student student = new Student();
            student.setName("李四" + i);
            student.setAge(i);
            student.setAddr("北京市第"+i+"街道");
            studentList.add(student);
        }
        studentDao.saveList(studentList);
        // 结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("插入数据消耗时间：" + (endTime - startTime));
    }




    @ApiOperation("batch批量插入")
    @GetMapping("batch")
    public void saveBatchStudent(){
        // 开始时间
        long startTime = System.currentTimeMillis();
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < 50000; i++){
            Student student = new Student();
            student.setName("王五" + i);
            student.setAge(i);
            student.setAddr("北京市第"+i+"街道");
            studentList.add(student);
        }
        studentService.saveBatch(studentList);
        // 结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("插入数据消耗时间：" + (endTime - startTime));
    }


    @ApiOperation("forSaveBatch批量插入")
    @GetMapping("/forSaveBatch")
    public void forSaveBatch(){
        //  开启批量处理模式 BATCH 、关闭自动提交事务 false
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        //  反射获取，获取Mapper
        StudentDao studentMapper = sqlSession.getMapper(StudentDao.class);
        long startTime = System.currentTimeMillis();
        for (int i = 0 ; i < 50000 ; i++){
            Student student = new Student();
            student.setName("王五" + i);
            student.setAge(i);
            student.setAddr("北京市第"+i+"街道");
            studentMapper.insert(student);
        }
        // 一次性提交事务
        sqlSession.commit();
        // 关闭资源
        sqlSession.close();
        long endTime = System.currentTimeMillis();
        System.out.println("总耗时： " + (endTime - startTime));
    }
}
