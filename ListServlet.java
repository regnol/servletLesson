package org.wzl.yanyan.ch04.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class ListServlet extends HttpServlet {
	
	private String url ;
	private String name ;
	private String password ;
	
	@Override
	public void init() throws ServletException {
		
		ServletContext sc = getServletContext();
		
		String driverClass = sc.getInitParameter("driverClass");
		
		try {
			
			Class.forName(driverClass);
			
			url = sc.getInitParameter("url");
			name = sc.getInitParameter("user");
			password = sc.getInitParameter("password");
		} catch(ClassNotFoundException e) {
			throw new ServletException("加载数据库驱动失败");
		}
		
		
	}
	
	
	@Override
	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		Connection conn = null;
		Statement stmt = null;
		
		ResultSet rs = null;
		
		String condition  = req.getParameter("cond");
		
		if(null == condition || "".equals(condition)) {
			resp.sendRedirect("search.html");
			return;
		}
		
		
		
		try {
			
			System.out.println("url:"+url);
			System.out.println("name:"+name);
			System.out.println("password:"+password);
			
			conn = DriverManager.getConnection(url,name,password);
			stmt = conn.createStatement();
			
			resp.setContentType("text/html;charset=utf-8");
			
			StringBuffer sql = new StringBuffer();
			
			switch(condition) {
				
				case "all": 
							sql.append("select * from actor");
							break;
				case "precision": String first_name = req.getParameter("first_name");
								  String last_name = req.getParameter("last_name");
								  if(first_name == null || "".equals(first_name)){
									  break;
								  }
								  if(last_name == null || "".equals(last_name)){
									  break;
								  }
								  sql.append("select * from actor where last_name=");
								  sql.append("'"+last_name+"'");
								  sql.append("and first_name = ");
								  sql.append("'"+first_name+"'");
								  break;
				case "key" : String keyworld = req.getParameter("keyworld");System.out.println("keyworld:"+ keyworld);
							if(null == keyworld || "".equals(keyworld)){
								break;
							}
							sql.append("select * from actor where first_name like ");
							sql.append("'%");
							sql.append(keyworld);
							sql.append("%'");
							break;
							
				default:break;
			}
			
			String l = sql.toString();
			
			if(l == null || "".equals(l)){
				resp.sendRedirect("search.html");
				return;
			}
			
			rs = stmt.executeQuery(l);
			System.out.println(l);
			PrintWriter out = resp.getWriter();
			printActorInfo(out,rs);
			out.close();
			
		} catch (SQLException se) {
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
	
	@Override
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		doGet(req,resp);
	}
	
	private void printActorInfo(PrintWriter out,ResultSet rs) throws SQLException {
		
		out.println("<html><head><title>演员信息表</title></head><body>");
		out.println("<table border=1> <caption>演员表</caption>");
		out.println("<tr><th>id</th><th>first_name</th><th>last_name</th><th>日期</th></tr>");
		
		while (rs.next()){
			out.println("<tr>");
			
			out.println("<td>"+rs.getString("actor_id")+"</td>");
			out.println("<td>"+rs.getString("first_name")+"</td>");
			out.println("<td>"+rs.getString("last_name")+"</td>");
			out.println("<td>"+rs.getString("last_update")+"</td>");
			
			out.println("</tr>");
		}
	}
}