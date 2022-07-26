package edu.hitwh.mybatis.mappertest;

import edu.hitwh.mybatis.mappers.StudentMapper;
import edu.hitwh.mybatis.pojo.Major;
import edu.hitwh.mybatis.pojo.Student;
import edu.hitwh.mybatis.utils.SqlSessionBuilder;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentMapperTest {

    @Test
    public void getCertainStudentTest(){
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Major major = new Major();
        major.setMajorName("软件工程");
        Student student = new Student("a",10,"111");
        student.setMajor(major);
        Student certainStudent = studentMapper.getCertainStudent(student);
        System.out.println(certainStudent);
    }

    @Test
    public void getLikeStudentsTest(){
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> likeStudents = studentMapper.getLikeStudents("a");
        likeStudents.forEach(System.out::println);
    }

    @Test
    public void getStudentAllMessageTest(){
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = studentMapper.getStudentAllMessage("a");
        System.out.println(student);
    }

    @Test
    public void findStudentsByMajorTest(){
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> studentsByMajor = studentMapper.findStudentsByMajor("环境工程");
        studentsByMajor.forEach(System.out::println);
    }

    @Test
    public void getIdTest(){
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Integer res = studentMapper.getId("a");
        System.out.println(res);
    }

    @Test
    public void getStudentByMapTest(){
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("studentName","a");
        paramMap.put("age",10);
        Student studentByMap = studentMapper.getStudentByMap(paramMap);
        System.out.println(studentByMap);
    }

    @Test
    public void getStudentByTwoPojoParamsTest(){
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = new Student();
        student.setStudentName("a");
        Major major = new Major();
        major.setId(1);
        Student studentByTwoPojoParams = studentMapper.getStudentByTwoPojoParams(student, major);
        System.out.println(studentByTwoPojoParams);
    }
}
