package com.datong.smarthome.dao;

import com.datong.smarthome.bean.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO
{
  public UserDAO() {}
  
  public User selectUser(String name)
  {
    User u = new User();
    u.setName(name);
    try {
      java.sql.Connection conn = com.datong.smarthome.util.ConnectionPoolSingleton.getConnection();
      StringBuilder sb = new StringBuilder();
      sb.append("select * from usr where name=?");
      PreparedStatement ptmt = conn.prepareStatement(sb.toString());
      ptmt.setString(1, name);
      ResultSet rs = ptmt.executeQuery();
      if (rs.next()) {
        u.setPwd(rs.getString("pwd"));
      }
      rs.close();
      ptmt.close();
    }
    catch (java.sql.SQLException e)
    {
      e.printStackTrace();
    }
    
    return u;
  }
}
