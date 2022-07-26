import edu.hitwh.mybatis.mapper.UserMapper;
import edu.hitwh.mybatis.utils.SqlSessionBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class UserMapperTest {
    @Test
    public void insertTest() throws IOException {
         /*      在此对获得SqlSession做了封装  SqlSessionBuilder
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int valid = userMapper.insertUser();
        sqlSession.commit();   openSession时 若不设true 则需要在调用接口方法后，要提交事务*/

        SqlSession sqlSession = SqlSessionBuilder.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int res = userMapper.insertUser();
        System.out.println("操作结果为"+res);
    }
}
