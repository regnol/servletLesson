package org.wzl.yanyan.ch05;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoginServlet2
 */
public class LoginServlet2 extends HttpServlet{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String action = req.getParameter("action");
        if ("chk".equals(action)){
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            if ("zhangsan".equals(name) && "123".equals(password)) {
                StringBuffer sb = new StringBuffer();
                sb.append("name-");
                sb.append(name);
                sb.append("&password-");
                sb.append(password);
                Cookie cookie = new Cookie("userinfo", sb.toString());
                cookie.setMaxAge(1800);
                resp.addCookie(cookie);
                resp.sendRedirect("greet2");
                return ;
            }
            resp.setContentType("text/html;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.println("<div>用户账号或者密码错误,请重<a href=login2>新登录</a></div>");
            return;
        }

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<mate http-equiv=\"Pragma\" content=\"nocache\">");
        out.println("<head><title>登录页面</title></head>");
        out.println("<body>");
        out.println("<p>");

        out.println("<form method=post action=login2?action=chk");

        out.println("<table>");

        out.println("<tr>");
        out.println("<td>请输入用户名</td>");
        out.println("<td><input type=test name=name></td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td>请输入密码</td>");
        out.println("<td><input type=password name=password></td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td><input type=submit value=登录></td>");
        out.println("<td><input type=reset value=重填></td>");
        out.println("</tr>");

        out.println("</table></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}