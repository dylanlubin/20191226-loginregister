package org.westos.demo;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.westos.domain.Users;
import org.westos.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //获取用户名和密码
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password").trim();
            //获取验证码
            String userCode = request.getParameter("checkcode");
            //比对用户输入的验证码跟我们服务器后台生成的验证码是否一致
            String serverCode = (String) request.getSession().getAttribute("code");
            //清空掉旧的验证码
            request.getSession().removeAttribute("code");
            if (userCode.equalsIgnoreCase(serverCode)) {
                //System.out.println(rember);
                Users user = new Users(username, password);
                //调用JDBC
                boolean b = JDBCUtils.register(user);
                if (!b) {
                    //跳转到主页
                    Properties properties = new Properties();
                    InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
                    properties.load(in);
                    DataSource dataSource = new DruidDataSourceFactory().createDataSource(properties);
                    QueryRunner queryRunner = new QueryRunner(dataSource);
                    int i = queryRunner.update("insert into users values(?,?)", username, password);
                    if (i>0) {
                        request.setAttribute("user", user.getUsername());
                        request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                    }else {
                        request.setAttribute("msg", "注册失败，请重新注册！");
                        request.getRequestDispatcher("/register.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("msg", "用户名已存在，请重新输入");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                }
            } else {
                //跳到页面，并提升验证码错误
                request.getSession().setAttribute("msg", "验证码输入错误，请重新输入");
                response.sendRedirect(request.getContextPath() + "/register.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
