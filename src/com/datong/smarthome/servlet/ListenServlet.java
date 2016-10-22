package com.datong.smarthome.servlet;

import com.datong.smarthome.bean.Message;
import com.datong.smarthome.dao.MessageDAO;
import com.datong.smarthome.util.JedisUtil;
import com.datong.smarthome.util.SMSservice;
import com.datong.smarthome.util.SingletonBlockingQueueT;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;




public class ListenServlet
  extends HttpServlet
{
  private static final long serialVersionUID = -5889003614314521947L;
  private static long last_time = 0L;
  private static final long interval = 3600L;
  private static final boolean[] tempAlarm = new boolean[4];
  private static final boolean[] waterAlarm = new boolean[3];
  private static boolean elecAlarm = false;
  private MessageDAO mdao = new MessageDAO();
  




  public ListenServlet() {}
  



  public void destroy()
  {
    super.destroy();
  }
  















  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    String type = req.getParameter("type");
    int intType = Integer.parseInt(type);
    System.out.println("listen type:" + type);
    String re = "";
    





























    String record = "/root/smart-home/history.msg";
    
    re = readReverse(record, 29, intType);
    System.out.println(re);
    resp.getOutputStream().write(re.getBytes());
  }
  






  public void init()
    throws ServletException
  {
    tempAlarm[0] = false;
    tempAlarm[1] = false;
    tempAlarm[2] = false;
    tempAlarm[3] = false;
    Jedis jedis = JedisUtil.createJedis();
    
    new Thread(new ListenServlet.1(this, jedis))
    
























      .start();
  }
  
  private void checkTempAlarm(String data, int devIdx) {
    String tStr = data.trim();
    String valString = "";
    int beginIdx = 8 + devIdx * 8;
    int endIdx = beginIdx + 2;
    
    valString = valString + tStr.substring(beginIdx, endIdx);
    int val = Integer.parseInt(valString, 16);
    if ((val > 30) && (tempAlarm[devIdx] == 0)) {
      tempAlarm[devIdx] = true;
      SMSservice.sendSMS(devIdx + 1, val);
    }
    else if ((val < 30) && (tempAlarm[devIdx] != 0)) {
      tempAlarm[devIdx] = false;
      SMSservice.sendSMS(devIdx + 1, val);
    }
  }
  


  private void setRealTime(String data)
  {
    String tStr = data.trim();
    String valString = "";
    int dev = 0;
    int type = 0;
    int beginIdx = 8 + dev * 8;
    int endIdx = beginIdx + 2;
    valString = tStr.substring(beginIdx, endIdx);
    int val = Integer.valueOf(valString, 16).intValue();
    System.out.println("dev:" + beginIdx + "\t" + "type:" + endIdx + "\t" + "val:" + valString);
    SingletonBlockingQueueT.push(dev, type, Float.valueOf(val));
    
    type = 1;
    beginIdx += 2;
    endIdx = beginIdx + 2;
    valString = tStr.substring(beginIdx, endIdx);
    val = Integer.valueOf(valString, 16).intValue();
    System.out.println("dev:" + beginIdx + "\t" + "type:" + endIdx + "\t" + "val:" + valString);
    SingletonBlockingQueueT.push(dev, type, Float.valueOf(val));
    

    type = 0;
    dev = 1;
    beginIdx = 8 + dev * 8;
    endIdx = beginIdx + 2;
    valString = tStr.substring(beginIdx, endIdx);
    val = Integer.valueOf(valString, 16).intValue();
    System.out.println("dev:" + beginIdx + "\t" + "type:" + endIdx + "\t" + "val:" + valString);
    SingletonBlockingQueueT.push(dev, type, Float.valueOf(val));
    
    type = 1;
    beginIdx += 2;
    endIdx = beginIdx + 2;
    valString = tStr.substring(beginIdx, endIdx);
    val = Integer.valueOf(valString, 16).intValue();
    System.out.println("dev:" + beginIdx + "\t" + "type:" + endIdx + "\t" + "val:" + valString);
    SingletonBlockingQueueT.push(dev, type, Float.valueOf(val));
    

    type = 0;
    dev = 2;
    beginIdx = 8 + dev * 8;
    endIdx = beginIdx + 2;
    valString = tStr.substring(beginIdx, endIdx);
    val = Integer.valueOf(valString, 16).intValue();
    System.out.println("dev:" + beginIdx + "\t" + "type:" + endIdx + "\t" + "val:" + val);
    SingletonBlockingQueueT.push(dev, type, Float.valueOf(val));
    
    type = 1;
    beginIdx += 2;
    endIdx = beginIdx + 2;
    valString = tStr.substring(beginIdx, endIdx);
    val = Integer.valueOf(valString, 16).intValue();
    System.out.println("dev:" + beginIdx + "\t" + "type:" + endIdx + "\t" + "val:" + val);
    SingletonBlockingQueueT.push(dev, type, Float.valueOf(val));
    

    type = 0;
    dev = 3;
    beginIdx = 8 + dev * 8;
    endIdx = beginIdx + 2;
    valString = tStr.substring(beginIdx, endIdx);
    val = Integer.valueOf(valString, 16).intValue();
    System.out.println("dev:" + beginIdx + "\t" + "type:" + endIdx + "\t" + "val:" + val);
    SingletonBlockingQueueT.push(dev, type, Float.valueOf(val));
    
    type = 1;
    beginIdx += 2;
    endIdx = beginIdx + 2;
    valString = tStr.substring(beginIdx, endIdx);
    val = Integer.valueOf(valString, 16).intValue();
    System.out.println("dev:" + beginIdx + "\t" + "type:" + endIdx + "\t" + "val:" + val);
    SingletonBlockingQueueT.push(dev, type, Float.valueOf(val));
  }
  



  private void checkELecAlarm(String data, int dev)
  {
    String tStr = data.trim();
    int bitIdx = 8 + dev * 8 + 7;
    int realDev = dev + 1;
    if ((tStr.substring(bitIdx, bitIdx + 1).equals("0")) && 
      (!elecAlarm))
    {
      Message msg = new Message();
      Date date = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String time = dateFormat.format(date);
      
      msg.setDevId(realDev);
      msg.setType("elec");
      msg.setTime(time);
      mdao.insertMsg(msg);
      SMSservice.sendElecAlarm(realDev);
      elecAlarm = true;
      System.out.println("Elec:" + realDev);
    } else if ((tStr.substring(bitIdx, bitIdx + 1).equals("1")) && 
      (elecAlarm)) {
      elecAlarm = false;
    }
  }
  
  private void checkWaterAlarm(String data, int dev)
  {
    String tStr = data.trim();
    int bitIdx = 8 + dev * 8 + 7;
    int realDev = dev + 1;
    if ((tStr.substring(bitIdx, bitIdx + 1).equals("0")) && 
      (waterAlarm[(realDev - 6)] == 0))
    {
      Message msg = new Message();
      Date date = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String time = dateFormat.format(date);
      
      msg.setDevId(realDev);
      msg.setType("water");
      msg.setTime(time);
      mdao.insertMsg(msg);
      SMSservice.sendWaterAlarm(realDev);
      waterAlarm[(realDev - 6)] = true;
      System.out.println("Water:" + realDev);
    } else if ((tStr.substring(bitIdx, bitIdx + 1).equals("1")) && 
      (waterAlarm[(realDev - 6)] != 0)) {
      waterAlarm[(realDev - 6)] = false;
    }
  }
  
  private void recvAndProc(String str)
  {
    String tmp = str.trim();
    String record = "/root/smart-home/history.msg";
    try
    {
      if (tmp.contains("3a00ff01"))
      {

        String[] arr = tmp.split("\t");
        checkTempAlarm(tmp, 0);
        checkTempAlarm(tmp, 1);
        checkTempAlarm(tmp, 2);
        checkTempAlarm(tmp, 3);
        
        checkWaterAlarm(tmp, 5);
        checkWaterAlarm(tmp, 6);
        checkWaterAlarm(tmp, 7);
        
        checkELecAlarm(tmp, 8);
        setRealTime(tmp);
        long cur_time = Long.parseLong(arr[(arr.length - 1)]);
        if (cur_time - last_time > 3600L) {
          last_time = cur_time;
          int devIdx = 0;
          String valString = "";
          
          int beginIdx = 8 + devIdx * 8;
          int endIdx = beginIdx + 2;
          
          valString = valString + tmp.substring(beginIdx, endIdx) + ",";
          beginIdx = 8 + devIdx * 8 + 2;
          endIdx = beginIdx + 2;
          valString = valString + tmp.substring(beginIdx, endIdx) + "\t";
          
          devIdx = 1;
          beginIdx = 8 + devIdx * 8;
          endIdx = beginIdx + 2;
          
          valString = valString + tmp.substring(beginIdx, endIdx) + ",";
          beginIdx = 8 + devIdx * 8 + 2;
          endIdx = beginIdx + 2;
          valString = valString + tmp.substring(beginIdx, endIdx) + "\t";
          
          devIdx = 2;
          beginIdx = 8 + devIdx * 8;
          endIdx = beginIdx + 2;
          
          valString = valString + tmp.substring(beginIdx, endIdx) + ",";
          beginIdx = 8 + devIdx * 8 + 2;
          endIdx = beginIdx + 2;
          valString = valString + tmp.substring(beginIdx, endIdx) + "\t";
          

          devIdx = 3;
          beginIdx = 8 + devIdx * 8;
          endIdx = beginIdx + 2;
          
          valString = valString + tmp.substring(beginIdx, endIdx) + ",";
          beginIdx = 8 + devIdx * 8 + 2;
          endIdx = beginIdx + 2;
          valString = valString + tmp.substring(beginIdx, endIdx) + "\n";
          System.out.println("valstring:\t" + valString);
          
          FileWriter fWriter = new FileWriter(record, true);
          fWriter.write(valString);
          fWriter.flush();
          fWriter.close();
        }
        
      }
      

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static String readReverse(String filename, int lines, int type)
  {
    List<String> re = new ArrayList();
    RandomAccessFile rf = null;
    try {
      rf = new RandomAccessFile(filename, "r");
      long fileLength = rf.length();
      long start = rf.getFilePointer();
      long readIndex = start + fileLength - 1L;
      
      rf.seek(readIndex);
      int c = -1;
      int lineIdx = 0;
      do {
        c = rf.read();
        if ((c == 10) || (c == 13)) {
          String line = rf.readLine();
          
          if (line != null) {
            lineIdx++;
            re.add(line);
            System.out.println(line);
          }
          

          readIndex -= 1L;
        }
        readIndex -= 1L;
        rf.seek(readIndex);
        if (readIndex == 0L) {
          System.out.println(rf.readLine());
        }
        if (readIndex <= start) break; } while (lineIdx < lines);









    }
    catch (FileNotFoundException e)
    {








      e.printStackTrace();
      

      try
      {
        if (rf != null)
          rf.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
      try
      {
        if (rf != null)
          rf.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    finally
    {
      try
      {
        if (rf != null)
          rf.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    String dev1Re = "";
    String dev2Re = "";
    String dev3Re = "";
    String dev4Re = "";
    
    for (int i = 0; i < re.size(); i++) {
      String[] strs = ((String)re.get(i)).split("\t");
      
      dev1Re = dev1Re + Integer.parseInt(strs[0].trim().split(",")[type], 16);
      dev2Re = dev2Re + Integer.parseInt(strs[1].trim().split(",")[type], 16);
      dev3Re = dev3Re + Integer.parseInt(strs[2].trim().split(",")[type], 16);
      dev4Re = dev4Re + Integer.parseInt(strs[3].trim().split(",")[type], 16);
      

      if (i != re.size() - 1) {
        dev1Re = dev1Re + ",";
        dev2Re = dev2Re + ",";
        dev3Re = dev3Re + ",";
        dev4Re = dev4Re + ",";
      }
    }
    

    return dev1Re + "\t" + dev2Re + "\t" + dev3Re + "\t" + dev4Re;
  }
}
