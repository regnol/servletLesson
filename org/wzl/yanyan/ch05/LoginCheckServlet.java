package org.wzl.yanyan.ch05;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LoginCheckServlet
 */
public class LoginCheckServlet extends HttpServlet{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String name = req.getParameter("user");

        String password = req.getParameter("password");

        if (null == name || "".equals(name) || null == password || "".equals(password)) {
            resp.sendRedirect("login");
            return;
        }

        HttpSession session = req.getSession();

        session.setAttribute("user", name);

        resp.sendRedirect("greeting");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}