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

public class ListServlet extends HttpServlet {
	
	private String url ;
	private String name ;
	private String password ;
	
	@Override
	public void init() throws ServletException {
		
		ServletContext sc = getServletContext();
		
		String driverClass = sc.getParameter("driverClass"):
		
		try {
			
			Class.forName(driverClass);
			
			url = sc.getParameter("url");
			name = sc.getParameter("name");
			password = sc.getParameter("password");
		} catch(ClassNotFoundException e) {
			throw new ServletException("加载数据库驱动失败");
		}
		
		
	}
	
	
	@Override
	public void doGet(HttpServletRequest rq,HttpServletResponse rp) throws ServletException,IOException{
		Connection conn = null;
		Statement stmt = null;
		
		ResultSet rs = null;
		
		String condition  = req.getParameter("condition");
		
		if(null == condition || "".equals(condition)) {
			resp.sendRedirect("search.html"):
			return;
		}
		
		
		
		try {
			conn = DriverManager.getConnection(url,name,password);
			stmt = conn.createStatement();
			
			
			stmt.
		} catch (SQLException se) {
			// se.printStackTrace():
			throw new ServletException(se):
		}
	}
}