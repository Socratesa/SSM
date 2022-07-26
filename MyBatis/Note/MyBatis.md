# MyBatis



### 1. 概述

---

基本思想：

​		一张表对应一个配置文件，对应一个pojo（普通java对象），对应一个mapper接口

​		SQL 和 Java 编码分开，功能边界清晰。Java代码专注业务、SQL语句专注数据

MyBatis下载地址：https://github.com/mybatis/mybatis-3

如遇到问题，建议阅读上述网址内的官方文档和尚硅谷文档，可搭配demo查看



### 2. 与其他持久化层技术对比：

---

- JDBC
  - SQL夹杂在 Java 代码中，耦合度高，导致硬编码内伤
  - 维护不易且实际开发需求中SQL有变化，频繁修改情况多见
  - 代码冗长，开发效率低
- Hibernate 和 JPA
  - 操作简便，开发效率高
  - 程序中的长难复杂 SQL 需要绕过框架
  - 内部自动生产的 SQL，不容易做特殊优化
  - 基于全映射的全自动框架，大量字段的 POJO 进行部分映射时比较困难
  - 反射操作太多，导致数据库性能下降
- MyBatis
  - 轻量级，性能出色
  - SQL 和 Java 编码分开，功能边界清晰。Java代码专注业务、SQL语句专注数据
  - 开发效率稍逊于HIbernate，但是完全能够接受



### 3. MyBatis环境搭建

---

#### 3.1 创建Maven工程

打包方式：jar包

引入依赖

```xml
<!-- 声明打包方式 -->
<packaging>jar</packaging>

    <dependencies>
        <!-- Mybatis核心 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.7</version>
        </dependency>
        <!-- junit测试 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!-- MySQL驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.16</version>
        </dependency>
        <!-- log4j日志 -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
    </dependencies>
```

#### 3.2 MyBatis核心配置文件

>习惯上命名为 mybatis-config.xml，并非强制要求
>
>该核心配置文件已内置为idea模板

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!--
        MyBatis核心配置文件中，标签的顺序：
        properties?,settings?,typeAliases?,typeHandlers?,
        objectFactory?,objectWrapperFactory?,reflectorFactory?,
        plugins?,environments?,databaseIdProvider?,mappers?
     -->

    <!--引入properties文件-->
    <properties resource="jdbc.properties"/>

    <!--
        处理字段名和实体类中属性的映射关系：
        字段名符合数据库命名规则，使用 _ ，实体类属性名符合Java规则，使用驼峰
    -->
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>

    <!--设置类型别名-->
    <typeAliases>
        <!--
        typeAlias：设置某个类型的别名
        属性：
            type：设置需要设置别名的类型 全类名
            alias：设置某个类型的别名，若不设置该属性，那么该类型拥有默认的别名，即类名 且不区分大小写
        -->
        <!--<typeAlias type="edu.hitwh.mybatis.pojo.Student"></typeAlias>-->
        <!--以包为单位，将包下所有的类型设置默认的类型别名，即类名且不区分大小写   通常情况下，用此种方式-->
        <package name="edu.hitwh.mybatis.pojo"/>
    </typeAliases>

    <!--
        environments：配置多个连接数据库的环境
        属性：
			default：设置默认使用的环境的id
    -->
    <environments default="development">
        <!--
            environment：配置某个具体的环境
            属性：id：表示连接数据库的环境的唯一标识，不能重复
        -->
        <!--开发环境-->
        <environment id="development">
            <!--
				transactionManager：设置事务管理方式
 				属性：
					type="JDBC|MANAGED" 
					JDBC：表示当前环境中，执行SQL时，使用的是JDBC中原生的事务管理方式，事务的提交或回滚需要手动处理
					MANAGED：被管理，例如Spring
		-->
            <transactionManager type="JDBC"/>
            <!--
				dataSource：配置数据源 
				属性：
					type：设置数据源的类型 
					type="POOLED|UNPOOLED|JNDI" 
					POOLED：表示使用数据库连接池缓存数据库连接 
					UNPOOLED：表示不使用数据库连接池
 					JNDI：表示使用上下文中的数据源 
			-->
            <dataSource type="POOLED">
                <!--设置连接数据库的驱动-->
                <property name="driver" value="${jdbc.driver}"/>
                <!--设置连接数据库的连接地址-->
                <property name="url" value="${jdbc.url}"/>
                <!--设置连接数据库的用户名-->
                <property name="username" value="${jdbc.username}"/>
                <!--设置连接数据库的密码-->
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <!--导入映射文件-->
    <mappers>
        <!--以文件为单位导入-->
        <!--<mapper resource="edu/hitwh/mybatis/mappers/MajorMapper.xml"/>-->
        <!--<mapper resource="edu/hitwh/mybatis/mappers/StudentMapper.xml"/>-->
        <!--
            以包为单位导入
            要求：
            1.mapper接口所在的包要和映射文件所在的包一致
            2.mapper接口要和映射文件名字一致
        -->
        <package name="edu/hitwh/mybatis/mappers"/>
    </mappers>
</configuration>
```

#### 3.3 MyBatis映射文件

> 1、映射文件命名规则：
>
> 表所表示的实体类的类名+Mapper.xml，因此一个映射文件对应一个实体类，对应一张表的操作
>
> eg：表t_user，映射实体类为User，所对应映射文件为UserMapper.xml
>
> 2、MyBatis映射文件用于编写SQL，访问以及操作表中数据
>
> 3、MyBatis映射文件存放位置：src/main/resources/....../mappers
>
> 4、MyBatis中可以面向接口操作数据，要保证两个一致：
>
> a> mapper接口的全类名和映射文件的命名空间（namespace）一致
>
> b> mapper接口方法的方法名和映射文件中编写SQL的标签的id保持一致

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="edu.hitwh.mybatis.mappers.MajorMapper">
    
	<!--void addMajor(Major major);-->
    <insert id="addMajor">
        insert into major values(null,#{majorName},#{majorPrincipal},#{description});
    </insert>
</mapper>
```

#### 3.4其他配置

##### 3.4.1 log4j配置

> 配置文件命名为log4j.xml，可当成模板直接使用，放在src/main/resources目录下

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">

<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">

    <appender name="STDOUT" class="org.apache.log4j.ConsoleAppender">
        <param name="Encoding" value="UTF-8"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%-5p %d{MM-dd HH:mm:ss,SSS} %m (%F:%L) \n"/>
        </layout>
    </appender>
    <logger name="java.sql">
        <level value="debug"/>
    </logger>
    <logger name="org.apache.ibatis">
        <level value="info"/>
    </logger>
    <root>
        <level value="debug"/>
        <appender-ref ref="STDOUT"/>
    </root>
</log4j:configuration>
```

##### 3.4.2 jdbc.properties

> 个人使用 3306作为MySQL 8.x端口号，13306作为MySQL 5.x端口号
>
> 配置文件名为 jdbc.properties，放在src/main/resources目录下
>
> mybatis_practice 为当前数据库名称

###### MySQL 8.x

```xml
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis_practice?serverTimezone=UTC
jdbc.username=root
jdbc.password=123456
```

###### MySQL 5.x

```xml
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:13306/mybatis_practice
jdbc.username=root
jdbc.password=123456
```

### 4. MyBatis获取参数值的方式

---

>获取参数值两种方式：${} 和 #{}
>
>${}的本质就是字符串拼接，#{}的本质就是占位符赋值
>
>${}使用字符串拼接的方式拼接sql，若为字符串类型或日期类型的字段进行赋值时，需要手动加单引号
>
>#{}使用占位符赋值的方式拼接sql，此时为字符串类型或日期类型的字段进行赋值时，可以自动添加单引号
>
>在使用${} 一定注意单引号问题

```mark
若参数为对象
	1.参数仅有一个 --> 可选择不添加@Param注解，在xml中可直接使用属性名方式获取属性对应值（推荐）
				 --> 添加@Param注解，在xml中需要使用注解内容.属性名方式获取属性对应值
	2.参数由多个  --> 添加@Param注解，在xml中需要使用注解内容.属性名方式获取属性对应值
若参数为Map对象
	同参数为对象方式
其他：
	均通过注解进行解决
```

### 5. MyBatis各种查询功能

---

> **查询的标签select必须设置属性resultType或resultMap，用于设置实体类和数据库表的映射关系**
>
> ​	resultType：自动映射，用于属性名和表中字段名一致的情况
>
> ​		若字段名、实体类类名各符合各自命名规则，可通过setting进行统一配置，不必使用resultMap配置
>
> ​	resultMap：自定义映射，用于一对多或多对一或字段名和属性名不一致的情况

```xml
在MyBatis中，对于 Java 中常用的类型都设置了类型别名

例如：java.lang.Integer --> int | integer 
	 int --> _int | _integer   (其他基本类型同理，详细可查看官方文档类型别名)

例如：Map --> map | List --> list
```

```xml
/**
 * 查询所有用户信息为map集合 
 * @return
 * 将表中的数据以map集合的方式查询，一条数据对应一个map；若有多条数据，就会产生多个map集合，此时可以将这些map放在一个list集合中获取 
*/
（常用）
<!--List<Map<String, Object>> getAllUserToMap();-->
<select id="getAllUserToMap" resultType="map"> 
    select * from t_user
</select>


/**
 * 查询所有用户信息为map集合 
 * @return
 * 将表中的数据以map集合的方式查询，一条数据对应一个map；若有多条数据，就会产生多个map集合，并且最终要以一个map的方式返回数据，此时需要通过@MapKey注解设置map集合的键，值是每条数据所对应的 map集合
*/

在Mapper接口里面声明
@MapKey("id") 
Map<String, Object> getAllUserToMap();
    
<!--Map<String, Object> getAllUserToMap();-->
<!-- 
{ 
	1={password=123456, sex=男, id=1, age=23, username=admin},
 	2={password=123456, sex=男, id=2, age=23, username=张三}, 
	3={password=123456, sex=男, id=3, age=23, username=张三}
}
-->
<select id="getAllUserToMap" resultType="map">
    select * from t_user
</select>
```

### 6. 特殊SQL执行

---

模糊查询

批量删除

动态设置表名

添加功能获取自增主键

```java
/**
 * 添加用户信息
 * @param user
 * @return
 * useGeneratedKeys：设置使用自增的主键
 * keyProperty：因为增删改有统一的返回值是受影响的行数，因此只能将获取的自增的主键放在传输的参数user对象的某个属性中 */ 
int insertUser(User user);
```

```xml
<!--int insertUser(User user);-->
<insert id="insertUser" useGeneratedKeys="true" keyProperty="id">
    insert into t_user values(null,#{username},#{password},#{age},#{sex})
</insert>
```



### 7. 一对多映射处理

---

collection方式

分步查询



### 8. 多对一映射处理

---

级联方式

association方式

分步查询



> 分步查询的优点：可以实现延迟加载
>
> 但是必须在核心配置文件中设置全局配置信息：
>
> lazyLoadingEnabled：延迟加载的全局开关。当开启时，所有关联对象都会延迟加载
>
> aggressiveLazyLoading：当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属
>
> 性会按需加载
>
> 此时就可以实现按需加载，获取的数据是什么，就只会执行相应的sql。此时可通过association和
>
> collection中的fetchType属性设置当前的分步查询是否使用延迟加载， fetchType="lazy(延迟加
>
> 载)|eager(立即加载)"

### 9. 动态SQL

---

>Mybatis框架的动态SQL技术是一种根据特定条件动态拼装SQL语句的功能，它存在的意义是为了解决拼接SQL语句字符串时的痛点问题。

**if**

```xml
<!--List<Emp> getEmpListByCondition(Emp emp);--> 
<select id="getEmpListByMoreTJ" resultType="Emp">
    select * from t_emp where 1=1
    <if test="ename != '' and ename != null">
        and ename = #{ename}
    </if>
    <if test="age != '' and age != null">
        and age = #{age}
    </if>
    <if test="sex != '' and sex != null">
        and sex = #{sex}
    </if>
</select>
```

**where**

>where和if一般结合使用：
>
>a>若where标签中的if条件都不满足，则where标签没有任何功能，即不会添加where关键字
>
>b>若where标签中的if条件满足，则where标签会自动添加where关键字，并将条件最前方多余的
>
>and去掉
>
>注意：where标签不能去掉条件最后多余的and

```xml
<select id="getEmpListByMoreTJ2" resultType="Emp">
    select * from t_emp
    <where>
        <if test="ename != '' and ename != null">
            ename = #{ename}
        </if>
        <if test="age != '' and age != null">
            and age = #{age}
        </if>
        <if test="sex != '' and sex != null">
            and sex = #{sex}
        </if>
    </where>
</select>
```



trim

choose、when、otherwise

**foreach**

```xml
<!--int insertMoreEmp(List<Emp> emps);-->
<insert id="insertMoreEmp">
    insert into t_emp values
    <foreach collection="emps" item="emp" separator=",">
        (null,#{emp.ename},#{emp.age},#{emp.sex},#{emp.email},null)
    </foreach>
</insert>
<!--int deleteMoreByArray(int[] eids);-->
<delete id="deleteMoreByArray">
    delete from t_emp where
    <foreach collection="eids" item="eid" separator="or">
        eid = #{eid}
    </foreach>
</delete>
<!--int deleteMoreByArray(int[] eids);-->
<delete id="deleteMoreByArray">
    delete from t_emp where eid in
    <foreach collection="eids" item="eid" separator="," open="(" close=")">
        #{eid}
    </foreach>
</delete>
```

SQL片段

### 10. MyBatis缓存

---

#### 10.1 MyBatis的一级缓存

一级缓存是SqlSession级别的，通过同一个SqlSession查询的数据会被缓存，下次查询相同的数据，就

会从缓存中直接获取，不会从数据库重新访问

使一级缓存失效的四种情况：

1、不同的SqlSession对应不同的一级缓存

2、同一个SqlSession但是查询条件不同

3、同一个SqlSession两次查询期间执行了任何一次增删改操作

4、同一个SqlSession两次查询期间手动清空了缓存

#### 10.2 MyBatis的二级缓存

二级缓存是SqlSessionFactory级别，通过同一个SqlSessionFactory创建的SqlSession查询的结果会被

缓存；此后若再次执行相同的查询语句，结果就会从缓存中获取

二级缓存开启的条件：

a>在核心配置文件中，设置全局配置属性cacheEnabled="true"，默认为true，不需要设置

b>在映射文件中设置标签<cache/>

c>二级缓存必须在SqlSession关闭或提交之后有效

d>查询的数据所转换的实体类类型必须实现序列化的接口

使二级缓存失效的情况：

两次查询之间执行了任意的增删改，会使一级和二级缓存同时失效

#### 10.3 二级缓存相关配置

在mapper配置文件中添加的cache标签可以设置一些属性：

①eviction属性：缓存回收策略，默认的是 LRU。

LRU（Least Recently Used） – 最近最少使用的：移除最长时间不被使用的对象。

FIFO（First in First out） – 先进先出：按对象进入缓存的顺序来移除它们。

SOFT – 软引用：移除基于垃圾回收器状态和软引用规则的对象。

WEAK – 弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象。

②flushInterval属性：刷新间隔，单位毫秒

默认情况是不设置，也就是没有刷新间隔，缓存仅仅调用语句时刷新

③size属性：引用数目，正整数

代表缓存最多可以存储多少个对象，太大容易导致内存溢出

④readOnly属性：只读， true/false

true：只读缓存；会给所有调用者返回缓存对象的相同实例。因此这些对象不能被修改。这提供了 很重

要的性能优势。

false：读写缓存；会返回缓存对象的拷贝（通过序列化）。这会慢一些，但是安全，因此默认是

false。

#### 10.4 MyBatis缓存查询的顺序

先查询二级缓存，因为二级缓存中可能会有其他程序已经查出来的数据，可以拿来直接使用。

如果二级缓存没有命中，再查询一级缓存

如果一级缓存也没有命中，则查询数据库

SqlSession关闭之后，一级缓存中的数据会写入二级缓存

#### 10.5 整合第三方缓存



### 11. MyBatis逆向工程

---

### 12. 分页插件

---





