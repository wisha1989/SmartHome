package com.datong.smarthome.servlet;

import com.datong.smarthome.bean.Message;
import com.datong.smarthome.dao.MessageDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class AlarmServlet
  extends HttpServlet
{
  private static final long serialVersionUID = -1805376448485420979L;
  private MessageDAO mDAO = new MessageDAO();
  



  public AlarmServlet() {}
  



  public void destroy()
  {
    super.destroy();
  }
  











  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    System.out.println("limit:" + request.getParameter("limit"));
    System.out.println("offset:" + request.getParameter("offset"));
    int offset = Integer.parseInt(request.getParameter("offset"));
    int limit = Integer.parseInt(request.getParameter("limit"));
    int total = mDAO.getItemCount();
    
    List<Message> list = mDAO.getMsg(offset, limit);
    System.out.println("Count:" + list.size());
    Gson gson = new Gson();
    String jsonStr = gson.toJson(list);
    jsonStr = "{\"total\":" + total + ",\"rows\":" + jsonStr + "}";
    response.getOutputStream().write(jsonStr.getBytes());
  }
  
  public void init()
    throws ServletException
  {}
}
