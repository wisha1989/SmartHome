package com.datong.smarthome.servlet;

import com.datong.smarthome.util.SingletonBlockingQueueT;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MonitorServlet
  extends HttpServlet
{
  private static final long serialVersionUID = -9020128648699629890L;
  
  public MonitorServlet() {}
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    String ss = req.getParameter("room");
    String type = req.getParameter("type");
    String re = "";
    for (int i = 0; i < 250; i++) {
      re = re + (Math.random() * 40.0D + Math.random() * 40.0D);
      if (i != 249) {
        re = re + ",";
      }
    }
    
    int dev = Integer.valueOf(ss).intValue() - 1;
    int typeVal = 0;
    if ("H".equals(type.trim())) {
      typeVal = 1;
    }
    re = SingletonBlockingQueueT.get(dev, typeVal);
    resp.getOutputStream().write(re.getBytes());
    System.out.println("Room:" + ss);
  }
}
