package com.datong.smarthome.dao;

import com.datong.smarthome.bean.Message;
import com.datong.smarthome.util.ConnectionPoolSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageDAO
{
  public MessageDAO() {}
  
  public void insertMsg(Message msg)
  {
    try
    {
      Connection conn = ConnectionPoolSingleton.getConnection();
      String sql = "insert into alarm(type,devid,time)value(?,?,?)";
      PreparedStatement ptmt = conn.prepareStatement(sql);
      ptmt.setString(1, msg.getType());
      ptmt.setInt(2, msg.getDevId());
      ptmt.setString(3, msg.getTime());
      ptmt.executeUpdate();
      ptmt.close();
      ConnectionPoolSingleton.returnConnection(conn);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public java.util.List<Message> getMsg(int offset, int count) {
    Connection conn = null;
    PreparedStatement ptmt = null;
    java.util.List<Message> re = new java.util.ArrayList();
    try {
      conn = ConnectionPoolSingleton.getConnection();
      String sql = "select * from (select * from alarm  order by aid desc)temp_table limit ?,?;";
      ptmt = conn.prepareStatement(sql);
      ptmt.setInt(1, offset);
      ptmt.setInt(2, count);
      ResultSet rs = ptmt.executeQuery();
      while (rs.next()) {
        Message msg = new Message();
        msg.setType(rs.getString(2));
        msg.setDevId(rs.getInt(3));
        msg.setTime(rs.getString(4));
        re.add(msg);
      }
      ptmt.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      ConnectionPoolSingleton.returnConnection(conn);
    }
    
    return re;
  }
  
  public int getItemCount()
  {
    Connection conn = null;
    PreparedStatement ptmt = null;
    int re = 0;
    try {
      conn = ConnectionPoolSingleton.getConnection();
      String sql = "select count(1) from alarm";
      ptmt = conn.prepareStatement(sql);
      
      ResultSet rs = ptmt.executeQuery();
      while (rs.next()) {
        re = rs.getInt(1);
      }
      ptmt.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      ConnectionPoolSingleton.returnConnection(conn);
    }
    
    return re;
  }
}
