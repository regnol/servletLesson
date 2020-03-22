package org.wzl.yanyan.ch05;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LogoutServlet
 */
public class LogoutServlet extends HttpServlet{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        HttpSession session = req.getSession();

        session.invalidate();
        
        PrintWriter out = resp.getWriter();

        out.println("<htm><head><title>退出登录</title</head>");

        out.println("<body>");
        out.println("<p>已经退出登录</p>");
        out.println("<a href=login>重新登录</a>");
        out.println("</body></html>");

        out.close();
    }
}