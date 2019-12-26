package org.westos.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.westos.domain.Users;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: ShenMouMou
 * @CreateTime: 2019-12-20 10:01
 * @Company:西部开源教育科技有限公司
 * @Description:爱生活，爱Java!
 */
public class JDBCUtils {
    public static boolean login(Users user) throws Exception {
        //JDBC代码
        Properties properties = new Properties();
        InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(in);
        DataSource dataSource = new DruidDataSourceFactory().createDataSource(properties);
        QueryRunner queryRunner = new QueryRunner(dataSource);
        //查询是否有页面输入的用户名和密码；
        Users users = queryRunner.query("select * from users where username=? and password=?", new BeanHandler<Users>(Users.class), user.getUsername(), user.getPassword());
        return (users!=null);
    }

    public static boolean register(Users user) throws Exception {
        //JDBC代码
        Properties properties = new Properties();
        InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(in);
        DataSource dataSource = new DruidDataSourceFactory().createDataSource(properties);
        QueryRunner queryRunner = new QueryRunner(dataSource);
        //查询是否有页面输入的用户名和密码；
        Users users = queryRunner.query("select * from users where username=? ", new BeanHandler<Users>(Users.class), user.getUsername());
        return (users!=null);
    }
}
