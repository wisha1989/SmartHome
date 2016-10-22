package com.datong.smarthome.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import java.util.HashMap;

public class SMSservice
{
  public SMSservice() {}
  
  public static void sendSMS(int dev, int temp)
  {
    HashMap<String, Object> result = null;
    

    CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
    


    restAPI.init("app.cloopen.com", "8883");
    

    restAPI.setAccount("aaf98f89488d0aad0148b617a0a41333", "6a921c7e0918440c8b4d19cbe8899405");
    

    restAPI.setAppId("aaf98f89488d0aad0148b61a4d79133d");
    
    result = restAPI.sendTemplateSMS("13568881620,13015393870", "106879", new String[] { Integer.valueOf(dev).toString(), Integer.valueOf(temp).toString() });
    System.out.println("SDKTestGetSubAccounts result=" + result);
    if ("000000".equals(result.get("statusCode")))
    {

      HashMap<String, Object> data = (HashMap)result.get("data");
      java.util.Set<String> keySet = data.keySet();
      for (String key : keySet) {
        Object object = data.get(key);
        System.out.println(key + " = " + object);
      }
    }
    else {
      System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
    }
  }
  

  public static void sendWaterAlarm(int dev)
  {
    HashMap<String, Object> result = null;
    

    CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
    


    restAPI.init("app.cloopen.com", "8883");
    

    restAPI.setAccount("aaf98f89488d0aad0148b617a0a41333", "6a921c7e0918440c8b4d19cbe8899405");
    

    restAPI.setAppId("aaf98f89488d0aad0148b61a4d79133d");
    

    result = restAPI.sendTemplateSMS("13568881620,13015393870", "111171", new String[] { Integer.valueOf(dev).toString() });
    System.out.println("SDKTestGetSubAccounts result=" + result);
    if ("000000".equals(result.get("statusCode")))
    {

      HashMap<String, Object> data = (HashMap)result.get("data");
      java.util.Set<String> keySet = data.keySet();
      for (String key : keySet) {
        Object object = data.get(key);
        System.out.println(key + " = " + object);
      }
    }
    else {
      System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
    }
  }
  

  public static void sendElecAlarm(int dev)
  {
    HashMap<String, Object> result = null;
    

    CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
    






    restAPI.init("app.cloopen.com", "8883");
    




    restAPI.setAccount("aaf98f89488d0aad0148b617a0a41333", "6a921c7e0918440c8b4d19cbe8899405");
    






    restAPI.setAppId("aaf98f89488d0aad0148b61a4d79133d");
    















    result = restAPI.sendTemplateSMS("13568881620,13015393870", "111172", new String[] { Integer.valueOf(dev).toString() });
    System.out.println("SDKTestGetSubAccounts result=" + result);
    if ("000000".equals(result.get("statusCode")))
    {

      HashMap<String, Object> data = (HashMap)result.get("data");
      java.util.Set<String> keySet = data.keySet();
      for (String key : keySet) {
        Object object = data.get(key);
        System.out.println(key + " = " + object);
      }
    }
    else {
      System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
    }
  }
}
