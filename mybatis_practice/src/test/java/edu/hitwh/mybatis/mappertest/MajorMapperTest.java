package edu.hitwh.mybatis.mappertest;

import edu.hitwh.mybatis.mappers.MajorMapper;
import edu.hitwh.mybatis.pojo.Major;
import edu.hitwh.mybatis.utils.SqlSessionBuilder;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MajorMapperTest {

    @Test
    public void addMajorTest(){
//        过程中出现报错 报错点：应该是 #{}
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        MajorMapper majorMapper = sqlSession.getMapper(MajorMapper.class);
        Major major = new Major("软件工程","小明","这是软件工程专业");
//        System.out.println(major);
        majorMapper.addMajor(major);
        System.out.println();
    }

    @Test
    public void getCertainMajorTest(){
        /*
        过程中出现的错误：
            1.给pojo 声明别名 即简称
            2.自定义resultMap映射 因为实体类的属性名和数据库中的字段名不一致
        */
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        MajorMapper majorMapper = sqlSession.getMapper(MajorMapper.class);
        Major major = majorMapper.getCertainMajor("软件工程");
        System.out.println(major);
        System.out.println();
    }

    @Test
    public void deleteMajorTest(){
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        MajorMapper majorMapper = sqlSession.getMapper(MajorMapper.class);
        majorMapper.deleteMajor("软件工程");
        System.out.println();
    }

    @Test
    public void updateMajorTest(){
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        MajorMapper majorMapper = sqlSession.getMapper(MajorMapper.class);
        majorMapper.updateMajor("软件工程");
    }

    @Test
    public void addMajorMoreTest(){
        Major major1 = new Major("软件工程","小明","这是软件工程专业");
        Major major2 = new Major("信息科学","小红","这是信息科学专业");
        Major major3 = new Major("环境工程","小刘","这是环境工程专业");
        List<Major> list = Arrays.asList(major1,major2,major3);
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        MajorMapper majorMapper = sqlSession.getMapper(MajorMapper.class);
        majorMapper.addMajorMore(list);
        System.out.println();
    }

    @Test
    public void getAllMajorTest(){
        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        MajorMapper majorMapper = sqlSession.getMapper(MajorMapper.class);
        List<Major> majors = majorMapper.getAllMajor();
        System.out.println(majors);
        System.out.println();
    }

}
