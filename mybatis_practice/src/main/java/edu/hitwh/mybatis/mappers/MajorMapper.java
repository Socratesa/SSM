package edu.hitwh.mybatis.mappers;

import edu.hitwh.mybatis.pojo.Major;
import edu.hitwh.mybatis.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MajorMapper {

    /**
     * 添加一个专业
     * @param major
     */
    void addMajor(Major major);

    /**
     * 查找某个专业
     * @param majorName
     * @return
     */
    Major getCertainMajor(@Param("majorName") String majorName);

    /**
     * 删除指定专业
     * @param majorName
     */
    void deleteMajor(@Param("majorName") String majorName);

    /**
     * 更新指定专业信息
     * @param majorName
     */
    void updateMajor(@Param("majorName") String majorName);

    /**
     * 添加多个专业
     * @param majors
     */
    void addMajorMore(@Param("majors") List<Major> majors);

    /**
     * 获取所有专业信息
     * @return
     */
    List<Major> getAllMajor();




}
