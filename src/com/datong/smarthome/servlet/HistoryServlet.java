package com.datong.smarthome.servlet;

import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;









public class HistoryServlet
  extends HttpServlet
{
  private static final long serialVersionUID = -6524022083873495136L;
  
  public HistoryServlet() {}
  
  public void destroy()
  {
    super.destroy();
  }
  











  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String ss = request.getParameter("room");
    System.out.println("history:" + ss);
  }
  
  public void init()
    throws ServletException
  {}
}
