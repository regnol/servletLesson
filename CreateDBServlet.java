package org.wzl.yanyan.ch04.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CreateDBServlet extends HttpServlet {
	
	private String user;
	private String url;
	private String password;
	
	public void init() throws ServletException {
		
		String driverClass = getInitParameter("driverClass");
		
		System.out.println(driverClass);
		
		try {
			Class.forName(driverClass);
		} catch(ClassNotFoundException ce){
			throw new ServletException("加载数据失败");
		}
		
		user = getInitParameter("user");
		// url = getInitParameter("url");
		url = "jdbc:mysql://localhost:3306/sakila?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
		password = getInitParameter("password");
		
		
	}
	
	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
		Connection conn = null;
		
		Statement stmt = null;
		
		try {
			conn = DriverManager.getConnection(url,user,password);
			
			stmt = conn.createStatement();
			
			// stmt.executeUpdate("create database bookstore");
			
			stmt.executeUpdate("use sakila");
			
			stmt.addBatch("insert into actor (actor_id,first_name,last_name) values (11112,'yanyan','huang')");
			stmt.addBatch("insert into actor (actor_id,first_name,last_name) values (11113,'yanyan','huang')");
			stmt.addBatch("insert into actor (actor_id,first_name,last_name) values (11114,'yanyan','huang')");
			
			stmt.executeBatch();
			
			resp.setContentType("text/html;charset=utf-8");
			
			PrintWriter out = resp.getWriter();
			
			out.println("创建数据库成功");
			
			out.close();
		} catch (SQLException se){
			throw new ServletException(se);
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
		}
	}
	
}