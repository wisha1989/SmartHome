package com.datong.smarthome.util;

import java.util.List;

public class SingletonBlockingQueueT
{
  private static List<Float> queue;
  private static List<Integer> idxs;
  
  public SingletonBlockingQueueT() {}
  
  public static synchronized void getQueue() {
    if (queue == null) {
      queue = new java.util.ArrayList();
      for (int i = 0; i < 2000; i++) {
        queue.add(Float.valueOf(0.0F));
      }
    }
    
    if (idxs == null) {
      idxs = new java.util.ArrayList();
      for (int i = 0; i < 8; i++) {
        idxs.add(Integer.valueOf(0));
      }
    }
  }
  
  public static synchronized void push(int dev, int type, Float e) {
    getQueue();
    int idx = ((Integer)idxs.get(type * 4 + dev)).intValue();
    queue.set(type * 1000 + dev * 250 + idx, e);
    idx = (idx + 1) % 250;
    idxs.set(type * 4 + dev, Integer.valueOf(idx));
  }
  
  public static synchronized String get(int dev, int type) {
    getQueue();
    List<Float> data = new java.util.ArrayList();
    int idx = ((Integer)idxs.get(type * 4 + dev)).intValue();
    

    data.addAll(queue.subList(type * 1000 + dev * 250 + idx + 1, type * 1000 + dev * 250 + 250));
    data.addAll(queue.subList(type * 1000 + dev * 250, type * 1000 + dev * 250 + idx));
    
    int size = data.size();
    

    String re = "";
    for (int i = 0; i < size; i++) {
      re = re + data.get(i);
      if (i != size - 1) {
        re = re + ",";
      }
    }
    return re;
  }
}
