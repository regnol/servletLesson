package org.wzl.yanyan.ch04.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetDBInfoServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1031825676495351961L;

    private String name;

    private String url;

    private String password;

    private String driverName;
    

    @Override
    public void init() throws ServletException {

        ServletContext context =  getServletContext();

         name = context.getInitParameter("user");

         url = context.getInitParameter("url");

         password = context.getInitParameter("password");

         driverName = context.getInitParameter("driverClass");

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new ServletException("初始化数据库失败");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;

        Statement stmt = null;

        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, name, password);

            resp.setContentType("text/html;charset=utf-8");

            PrintWriter out = resp.getWriter();

            out.println("<html><head><title>数据库表的信息</title>");
            out.println("</head><body>");

            String tableName = req.getParameter("tableName");

            if (null == tableName || "".equals(tableName)){
                DatabaseMetaData dbmate = conn.getMetaData();

                rs = dbmate.getTables(null, null, null, new String[]{"TABLE"});

                out.println("<form action=\"getdbinfo\" method=\"get\">");

                out.println("<select size=1 name=tableName>");

                while (rs.next()){
                    out.println("<option value = "+ rs.getString("TABLE_NAME") +">");
                    out.println(rs.getString("TABLE_NAME"));
                    
                    out.println("</option>");
                }
                out.println("</select>");
                out.println("<input type=\"submit\" value=\"提交\" />");
                out.print("</form>");
                return;
            }

            stmt = conn.createStatement();

            rs = stmt.executeQuery("select * from " + tableName);

            ResultSetMetaData mate = rs.getMetaData();

            int columnCount = mate.getColumnCount();

            out.println("<table border=1> <caption>表结构</caption>");
            out.println("<tr><th>字段名</th><th>字段类型</th><th>最大字符宽</th></tr>");

            ArrayList<String> al = new ArrayList<>();

            
            for (int i = 1; i <= columnCount; i++) {

                out.println("<tr>");
                String columnName = mate.getColumnName(i);
                out.println("<td>" +columnName+ "</td>");
                al.add(columnName);
                out.println("<td>" +mate.getColumnTypeName(i)+ "</td>");
                out.println("<td>" +mate.getColumnDisplaySize(i) + "</td>");
                out.println("</tr>");
            }
            out.println("</table><p>");

            out.println("<table border=1>");
            out.println("<caption>表中的数据</caption>");

            out.println("<tr>");
            for (int i = 0; i < columnCount; i++) {
                out.println("<th>" + al.get(i) + "</th>");
            }
            out.println("</tr>");

            while(rs.next()){
                out.println("<tr>");
                for (int i = 1; i <= columnCount; i++) {
                    out.println("<td>" +rs.getString(i)+ "</td");
                }
                out.print("</tr>");
            }
            out.println("</table>");

            out.println("</body></html>");
            out.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null){
				try {
					rs.close();
				} catch(SQLException se){
					se.printStackTrace();
				}
				rs = null;
			}
			
			if(conn != null){
				try{
					conn.close();
				} catch(SQLException se){
					se.printStackTrace();
				}
				conn = null;
			}

			if(stmt != null){
				try{
					stmt.close();
				} catch(SQLException se){
					se.printStackTrace();
				}
				stmt = null;
			}
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}