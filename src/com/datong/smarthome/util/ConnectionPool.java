package com.datong.smarthome.util;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;

public class ConnectionPool
{
  private String jdbcDriver = "";
  
  private String dbUrl = "";
  
  private String dbUsername = "";
  
  private String dbPassword = "";
  
  private String testTable = "";
  
  private int initialConnections = 10;
  
  private int incrementalConnections = 5;
  
  private int maxConnections = 50;
  
  private Vector<ConnectionPool.PooledConnection> connections = null;
  









  public ConnectionPool() {}
  









  public ConnectionPool(String jdbcDriver, String dbUrl, String dbUsername, String dbPassword)
  {
    this.jdbcDriver = jdbcDriver;
    
    this.dbUrl = dbUrl;
    
    this.dbUsername = dbUsername;
    
    this.dbPassword = dbPassword;
  }
  








  public synchronized void createPool()
    throws Exception
  {
    if (connections != null)
    {
      return;
    }
    



    java.sql.Driver driver = (java.sql.Driver)Class.forName(jdbcDriver).newInstance();
    
    DriverManager.registerDriver(driver);
    


    connections = new Vector();
    


    createConnections(initialConnections);
    
    System.out.println(" 数据库连接池创建成功！ ");
  }
  
















  private void createConnections(int numConnections)
    throws SQLException
  {
    for (int x = 0; x < numConnections; x++)
    {






      if ((maxConnections > 0) && 
        (connections.size() >= maxConnections)) {
        break;
      }
      






      try
      {
        connections.addElement(new ConnectionPool.PooledConnection(this, newConnection()));
      }
      catch (SQLException e)
      {
        System.out.println(" 创建数据库连接失败！ " + e.getMessage());
        
        throw new SQLException();
      }
      

      System.out.println(" 数据库连接己创建 ......");
    }
  }
  













  private Connection newConnection()
    throws SQLException
  {
    Connection conn = DriverManager.getConnection(dbUrl, dbUsername, 
      dbPassword);
    






    if (connections.size() == 0)
    {
      DatabaseMetaData metaData = conn.getMetaData();
      
      int driverMaxConnections = metaData.getMaxConnections();
      


      if ((driverMaxConnections > 0) && 
        (maxConnections > driverMaxConnections))
      {
        maxConnections = driverMaxConnections;
      }
    }
    


    return conn;
  }
  












  public synchronized Connection getConnection()
    throws SQLException
  {
    if (connections == null)
    {
      return null;
    }
    

    Connection conn = getFreeConnection();
    


    while (conn == null)
    {


      wait(250);
      
      conn = getFreeConnection();
    }
    





    return conn;
  }
  
















  private Connection getFreeConnection()
    throws SQLException
  {
    Connection conn = findFreeConnection();
    
    if (conn == null)
    {




      createConnections(incrementalConnections);
      


      conn = findFreeConnection();
      
      if (conn == null)
      {


        return null;
      }
    }
    


    return conn;
  }
  












  private Connection findFreeConnection()
    throws SQLException
  {
    Connection conn = null;
    
    ConnectionPool.PooledConnection pConn = null;
    


    Enumeration<ConnectionPool.PooledConnection> enumerate = connections.elements();
    


    while (enumerate.hasMoreElements())
    {
      pConn = (ConnectionPool.PooledConnection)enumerate.nextElement();
      
      if (!pConn.isBusy())
      {


        conn = pConn.getConnection();
        
        pConn.setBusy(true);
        


        if (testConnection(conn)) {
          break;
        }
        


        try
        {
          conn = newConnection();
        }
        catch (SQLException e)
        {
          System.out.println(" 创建数据库连接失败！ " + e.getMessage());
          
          return null;
        }
        

        pConn.setConnection(conn);
        


        break;
      }
    }
    


    return conn;
  }
  











  private boolean testConnection(Connection conn)
  {
    try
    {
      if (testTable.equals(""))
      {






        conn.setAutoCommit(true);

      }
      else
      {

        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery("select count(*) from " + 
          testTable);
        
        rs.next();
        
        System.out.println(testTable + "：表的记录数为：" + rs.getInt(1));
      }
      

    }
    catch (SQLException e)
    {
      e.printStackTrace();
      
      closeConnection(conn);
      
      return false;
    }
    



    return true;
  }
  











  public void returnConnection(Connection conn)
  {
    if (connections == null)
    {
      System.out.println(" 连接池不存在，无法返回此连接到连接池中 !");
      
      return;
    }
    

    ConnectionPool.PooledConnection pConn = null;
    
    Enumeration<ConnectionPool.PooledConnection> enumerate = connections.elements();
    


    while (enumerate.hasMoreElements())
    {
      pConn = (ConnectionPool.PooledConnection)enumerate.nextElement();
      


      if (conn == pConn.getConnection())
      {
        try
        {
          if (conn.isClosed()) {
            pConn.setConnection(newConnection());
          }
        }
        catch (SQLException e) {
          e.printStackTrace();
        }
        pConn.setBusy(false);
        
        break;
      }
    }
  }
  








  public synchronized void refreshConnections()
    throws SQLException
  {
    if (connections == null)
    {
      System.out.println(" 连接池不存在，无法刷新 !");
      
      return;
    }
    

    ConnectionPool.PooledConnection pConn = null;
    
    Enumeration<ConnectionPool.PooledConnection> enumerate = connections.elements();
    
    while (enumerate.hasMoreElements())
    {


      pConn = (ConnectionPool.PooledConnection)enumerate.nextElement();
      


      if (pConn.isBusy())
      {
        wait(5000);
      }
      



      closeConnection(pConn.getConnection());
      
      pConn.setConnection(newConnection());
      
      pConn.setBusy(false);
    }
  }
  







  public synchronized void closeConnectionPool()
    throws SQLException
  {
    if (connections == null)
    {
      System.out.println(" 连接池不存在，无法关闭 !");
      
      return;
    }
    

    ConnectionPool.PooledConnection pConn = null;
    
    Enumeration<ConnectionPool.PooledConnection> enumerate = connections.elements();
    
    while (enumerate.hasMoreElements())
    {
      pConn = (ConnectionPool.PooledConnection)enumerate.nextElement();
      


      if (pConn.isBusy())
      {
        wait(5000);
      }
      



      closeConnection(pConn.getConnection());
      


      connections.removeElement(pConn);
    }
    



    connections = null;
  }
  







  private void closeConnection(Connection conn)
  {
    try
    {
      conn.close();
    }
    catch (SQLException e)
    {
      System.out.println(" 关闭数据库连接出错： " + e.getMessage());
    }
  }
  








  private void wait(int mSeconds)
  {
    try
    {
      Thread.sleep(mSeconds);
    }
    catch (InterruptedException localInterruptedException) {}
  }
  









  public int getInitialConnections()
  {
    return initialConnections;
  }
  







  public void setInitialConnections(int initialConnections)
  {
    this.initialConnections = initialConnections;
  }
  







  public int getIncrementalConnections()
  {
    return incrementalConnections;
  }
  







  public void setIncrementalConnections(int incrementalConnections)
  {
    this.incrementalConnections = incrementalConnections;
  }
  







  public int getMaxConnections()
  {
    return maxConnections;
  }
  







  public void setMaxConnections(int maxConnections)
  {
    this.maxConnections = maxConnections;
  }
  







  public String getTestTable()
  {
    return testTable;
  }
  








  public void setTestTable(String testTable)
  {
    this.testTable = testTable;
  }
}
