package com.datong.smarthome.util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolSingleton
{
  private static ConnectionPool cp;
  private static final String URL = "jdbc:mysql://121.42.179.29/skyeye?useUnicode=true&amp;characterEncoding=utf-8";
  private static final String USER = "root";
  private static final String PASSWORD = "root123";
  
  private static void init()
  {
    if (cp == null) {
      cp = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://121.42.179.29/skyeye?useUnicode=true&amp;characterEncoding=utf-8", "root", "root123");
      try {
        cp.createPool();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  

  private ConnectionPoolSingleton() {}
  
  public static synchronized Connection getConnection()
    throws SQLException
  {
    if (cp == null) {
      init();
    }
    return cp.getConnection();
  }
  
  public static synchronized void returnConnection(Connection conn)
  {
    cp.returnConnection(conn);
  }
}
