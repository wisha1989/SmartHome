package com.datong.smarthome.bean;

public class Message {
  public static String INFO_TYPE = "INFO";
  public static String CMD_TYPE = "CMD";
  int DevId;
  
  public Message() {}
  
  public int getDevId() { return DevId; }
  
  public void setDevId(int devId) {
    DevId = devId;
  }
  
  public String getType() { return type; }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getTime() { return time; }
  
  public void setTime(String time) {
    this.time = time;
  }
  
  String type;
  String time;
}
