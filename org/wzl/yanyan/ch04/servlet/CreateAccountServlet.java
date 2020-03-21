package org.wzl.yanyan.ch04.servlet;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.sql.*;

public class CreateAccountServlet extends HttpServlet{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 139349841127287424L;

	private String name;
	
	private String password;
	private String url;
	
	
	public void init()throws ServletException {
		ServletContext sc = getServletContext();
		
		String driverClass = sc.getInitParameter("driverClass");
		
		url = sc.getInitParameter("url");
		name = sc.getInitParameter("user");
		password = sc.getInitParameter("password");

		System.out.println("url:" + url);
		System.out.println("name:" + name);
		System.out.println("password:" +password );
		
		try {
			Class.forName(driverClass);	
		} catch(ClassNotFoundException ex){
			throw new ServletException("加载数据失败");
		}
	}
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		Connection conn = null;
		PreparedStatement pre = null;

		Statement stmt = null;
		
		try {
			
			conn = DriverManager.getConnection(url,name,password);
			
			stmt = conn.createStatement();
			

			String sql1 = "create table account (userid varchar(10) not null primary key,blance Float(6,2))";

			String sql2 = "insert into account values(?,?)";

			stmt.executeUpdate(sql1);

			pre = conn.prepareStatement(sql2);

			pre.setString(1,"甲");
			pre.setFloat(2, 200.00f);

			pre.setString(1,"乙");
			pre.setFloat(2, 600.00f);

			pre.executeUpdate();

			res.setContentType("text/html;charset=utf-8");

			PrintWriter out = res.getWriter();

			out.println("创建表成功");

			out.close();

			
		} catch (SQLException se){
			se.printStackTrace();
		} finally {

			if(stmt != null){
				try {
					stmt.close();
				} catch(SQLException se){
					se.printStackTrace();
				}
				stmt = null;
			}
			
			if(conn != null){
				try{
					conn.close();
				} catch(SQLException se){
					se.printStackTrace();
				}
				conn = null;
			}

			if(pre != null){
				try{
					pre.close();
				} catch(SQLException se){
					se.printStackTrace();
				}
				pre = null;
			}
		}
	}

	@Override
	public void  doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {
		doGet(req,res);
	}
}