package edu.hitwh.mybatis.mappers;

import edu.hitwh.mybatis.pojo.Major;
import edu.hitwh.mybatis.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentMapper {
    /**
     * 查询某个学生
     * @param student
     * @return
     */
    Student getCertainStudent(Student student);

    /**
     * 查询学生所在专业
     * @param studentName
     * @return
     */
    Student getStudentAllMessage(@Param("studentName")String studentName);

    /**
     * 查询符合某种特征的学生
     * @param like
     * @return
     */
    List<Student> getLikeStudents(@Param("like") String like);

    /**
     * 根据专业名称返回对应专业学生信息
     * @param majorName
     * @return
     */
    List<Student> findStudentsByMajor(@Param("majorName")String majorName);

    /**
     * 根据学生姓名 获取id 目的：测试 select 语句是否必须要写resultType或ResultMap 必须写
     * @param studentName
     * @return
     */
    Integer getId(@Param("studentName")String studentName);

//    通过Map集合获取参数
    Student getStudentByMap(Map<String,Object> paramMap);

//    若两个参数均为实体类对象
    Student getStudentByTwoPojoParams(@Param("student") Student student,@Param("major") Major major);
}
