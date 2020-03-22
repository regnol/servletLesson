package org.wzl.yanyan.ch05;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GreetServlet2
 */
public class GreetServlet2 extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length != 0) {

            String name = null;
            String password = null;

            for (int i = 0; i < cookies.length; i++) {
                Cookie cook = cookies[i];
                String cName = cook.getName();
                System.out.println("cName:" + cName);
                if ("userinfo".equals(cName)) {
                    String[] value = cook.getValue().split("&");
                    System.out.println(Arrays.toString(value));
                    for (int j = 0; j < value.length; j++) {
                        String [] n = value[j].split("-");
                        System.out.println(Arrays.toString(n));
                        if ("name".equals(n[0])) {
                            name = n[1];
                        }
                        if ("password".equals(n[0])) {
                            password = n[1];
                        }
                    }
                    break;
                }
            }
            System.out.println("name;" + name);
            System.out.println("password:" + password);
            if ("zhangsan".equals(name) && "123".equals(password)) {

                resp.setContentType("text/html;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.println("<html>");
                out.println("<mate http-equiv=\"Pragma\" content=\"nocache\">");
                out.println("<head><title>欢迎页面</title></head>");
                out.println("<body>");

                out.println(name);
                out.println(":<div>欢迎你</div>");
                out.println("<a href=login2>重新登录</a>");
                out.println("</tr>");
                out.println("</body></html>");

                out.close();
                return;
            }
        }

        RequestDispatcher rd = req.getRequestDispatcher("login2");
        rd.forward(req, resp);
    }
}