package org.westos.demo;

import org.westos.domain.Users;
import org.westos.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ShenMouMou
 * @CreateTime: 2019-12-20 09:41
 * @Company:西部开源教育科技有限公司
 * @Description:爱生活，爱Java!
 */
@WebServlet(name = "LoginServlet",value = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //获取用户名和密码
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password").trim();
            String rember= request.getParameter("rember");
            //获取验证码
            String userCode= request.getParameter("checkcode");
            //比对用户输入的验证码跟我们服务器后台生成的验证码是否一致
            String serverCode = (String) request.getSession().getAttribute("code");
            //清空掉旧的验证码
            request.getSession().removeAttribute("code");
            if(userCode.equalsIgnoreCase(serverCode)){
                //System.out.println(rember);
                Users user = new Users(username, password);
                //调用JDBC
                boolean b = JDBCUtils.login(user);
                if (b) {
                    if (rember != null) {
                        Cookie uCookie = new Cookie("username", user.getUsername());
                        Cookie pCookie = new Cookie("password", user.getPassword());
                        //设置cookie的存活时间
                        uCookie.setMaxAge(60 * 60 * 24 * Integer.parseInt(rember));
                        pCookie.setMaxAge(60 * 60 * 24 * Integer.parseInt(rember));
                        response.addCookie(uCookie);
                        response.addCookie(pCookie);
                    }
                    //跳转到主页
                    request.setAttribute("user", user.getUsername());
                    request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", "用户名或密码错误");
                    //this.getServletContext().setAttribute("msg", "用户名或密码错误");
                    //继续跳转登录页面
                    //response.setHeader("refresh", "3;url=/20191220_JSP__war_exploded/login.jsp");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    // this.getServletContext().removeAttribute("msg");
                }
            }else{
               //跳到登录页面，并提升验证码错误
                request.getSession().setAttribute("msg", "验证码输入错误，请重新输入");
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
