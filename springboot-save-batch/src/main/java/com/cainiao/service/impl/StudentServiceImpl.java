package com.cainiao.service.impl;

import com.cainiao.entity.Student;
import com.cainiao.dao.StudentDao;
import com.cainiao.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author peishunwu
 * @since 2022-12-12
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

}
