package com.datong.smarthome.servlet;

import com.datong.smarthome.bean.User;
import com.datong.smarthome.dao.UserDAO;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;




public class LoginServlet
  extends HttpServlet
{
  private static final long serialVersionUID = -4639635650390096462L;
  private static Logger logger = Logger.getLogger(LogServlet.class);
  
  public LoginServlet() {}
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { String name = req.getParameter("form-username");
    String pwd = req.getParameter("form-password");
    
    UserDAO udao = new UserDAO();
    User u = udao.selectUser(name);
    logger.info("hehe" + name + pwd);
    System.out.println("hehe" + name + pwd);
    
    if (!pwd.trim().equals(u.getPwd().trim())) {
      resp.getOutputStream().write("用户名或密码错误,跳转登录页面。。。".getBytes());
      
      resp.sendRedirect("index.html");
    }
    else {
      resp.getOutputStream().write("登录成功，正在跳转...".getBytes());
      resp.sendRedirect("monitor.html");
    }
  }
}
