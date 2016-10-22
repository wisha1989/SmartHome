package com.datong.smarthome.util;

import redis.clients.jedis.Jedis;

public class JedisUtil { public JedisUtil() {}
  
  public static Jedis createJedis() { Jedis jedis = new Jedis("121.42.179.29");
    return jedis;
  }
  
  public static Jedis createJedis(String host, int port) {
    Jedis jedis = new Jedis(host, port);
    
    return jedis;
  }
}
