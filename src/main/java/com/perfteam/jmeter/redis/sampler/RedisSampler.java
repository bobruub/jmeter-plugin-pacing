package com.perfteam.jmeter.redis.sampler;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.*;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RedisSampler extends AbstractSampler {

  private static final Logger LOG = LoggerFactory.getLogger(RedisSampler.class);

  private static final String LABEL = "Redis.label";
  private static final String REDIS_SERVER = "Redis.redisServer";
  private static final String REDIS_PORT = "Redis.redisPort";
  private static final String REDIS_SET = "Redis.redisSet";
  private static final String REDIS_FILE_TO_LOAD = "Redis.redisFileToLoad";
  private static final String REDIS_TYPE = "Redis.redisType";
  private static final String REDIS_READ_METHOD = "Redis.redisReadMethod";
  private static final String REDIS_WRITE_DATA = "Redis.redisWriteData";
  private static final String KEEP_DATA = "Redis.keepData";
  private static final String REDIS_HASH = "Redis.redisHash";
  private static final String REDIS_HASH_KEY = "Redis.redisHashKey";
  private static final String REDIS_HASH_VALUE = "Redis.redisHashValue";
  private static final String REDIS_FILE_DELIMITER = "Redis.redisFileDelimiter";
  private static final String REDIS_KEY = "Redis.redisKey";
  JedisPool pool;
  Jedis jedis;

  public void setRedisKey(String redisKey) {
    setProperty(REDIS_KEY, redisKey);
  }

  public String getRedisKey() {
    return getPropertyAsString(REDIS_KEY, Strings.EMPTY);
  }

  public void setKeepData(boolean keepData) {
    setProperty(KEEP_DATA, keepData);
  }

  public boolean getKeepData() {
    return getPropertyAsBoolean(KEEP_DATA, true);
  }

  public void setLabel(String label) {
    setProperty(LABEL, label);
  }

  public String getLabel() {
    return getPropertyAsString(LABEL, Strings.EMPTY);
  }

  public void setRedisWriteData(String redisWriteData) {
    setProperty(REDIS_WRITE_DATA, redisWriteData);
  }

  public String getRedisWriteData() {
    return getPropertyAsString(REDIS_WRITE_DATA, Strings.EMPTY);
  }

  public void setRedisReadMethod(String redisReadMethod) {
    setProperty(REDIS_READ_METHOD, redisReadMethod);
  }

  public String getRedisReadMethod() {
    return getPropertyAsString(REDIS_READ_METHOD, Strings.EMPTY);
  }

  public void setRedisType(String redisType) {
    setProperty(REDIS_TYPE, redisType);
  }

  public String getRedisType() {
    return getPropertyAsString(REDIS_TYPE, Strings.EMPTY);
  }

  public void setRedisServer(String redisServer) {
    setProperty(REDIS_SERVER, redisServer);
  }

  public String getRedisServer() {
    return getPropertyAsString(REDIS_SERVER, Strings.EMPTY);
  }

  public void setRedisPort(String redisPort) {
    setProperty(REDIS_PORT, redisPort);
  }

  public String getRedisPort() {
    return getPropertyAsString(REDIS_PORT, Strings.EMPTY);
  }
  
  public void setRedisHash(String redisHash) {
    setProperty(REDIS_HASH, redisHash);
  }

  public String getRedisHash() {
    return getPropertyAsString(REDIS_HASH, Strings.EMPTY);
  }

  public void setRedisHashValue(String redisHashValue) {
    setProperty(REDIS_HASH_VALUE, redisHashValue);
  }

  public String getRedisHashValue() {
    return getPropertyAsString(REDIS_HASH_VALUE, Strings.EMPTY);
  }

  public void setRedisHashKey(String redisHashKey) {
    setProperty(REDIS_HASH_KEY, redisHashKey);
  }

  public String getRedisHashKey() {
    return getPropertyAsString(REDIS_HASH_KEY, Strings.EMPTY);
  }
 
  public void setRedisSet(String redisSet) {
    setProperty(REDIS_SET, redisSet);
  }

  public String getRedisSet() {
    return getPropertyAsString(REDIS_SET, Strings.EMPTY);
  }

  public void setRedisFileToLoad(String redisFileToLoad) {
    setProperty(REDIS_FILE_TO_LOAD, redisFileToLoad);
  }

  public String getRedisFileToLoad() {
    return getPropertyAsString(REDIS_FILE_TO_LOAD, Strings.EMPTY);
  }

  public void setRedisDelimiter(String redisFileDelimiter) {
    setProperty(REDIS_FILE_DELIMITER, redisFileDelimiter);
  }

  public String getRedisDelimiter() {
    return getPropertyAsString(REDIS_FILE_DELIMITER, Strings.EMPTY);
  }

  public String getCheckRedisServer() {

    String pRedisHost = getRedisServer();

    String redisStatus = "not found";
    int pRedisPort = Integer.parseInt(getRedisPort());
    String redisPong;
    try {
      JedisPoolConfig config = new JedisPoolConfig();
      pool = new JedisPool(config, pRedisHost, pRedisPort);
      jedis = null;
      jedis = pool.getResource();
      redisPong = jedis.ping();
      redisStatus = getRedisServer() + ":" + getRedisPort() + " - " + redisPong;
      pool.close();
      jedis.close();
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
    }
    return redisStatus;
  }

  /**
   * @return
   */
    
   public String loadHashRedisServer() {

    String pRedisHost = getRedisServer();
    int pRedisPort = Integer.parseInt(getRedisPort());
    String pRedisHash = getRedisHash();
    String pRedisFileToLoad = getRedisFileToLoad();
    String pRedisFileDelimiter = getRedisDelimiter();
    LOG.debug("pRedisType: " + getRedisType());
    LOG.debug("pRedisHash: " + pRedisHash);
    LOG.debug("pRedisFileToLoad: " + pRedisFileToLoad);
    LOG.debug("pRedisFileDelimiter: " + pRedisFileDelimiter);

    int x = 0;
    String redisStatus = null;
    String fieldName = null;
    String fieldValue = null;
    String line = null;
    BufferedReader br = null;
    try {
      JedisPoolConfig config = new JedisPoolConfig();
      pool = new JedisPool(config, pRedisHost, pRedisPort);
      jedis = null;
      jedis = pool.getResource();
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
    }
    try {
      jedis.del(pRedisHash);
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
    }
    Pipeline pipeline = jedis.pipelined();
    try {
      File file = new File(pRedisFileToLoad); // creates a new file instance
      br = new BufferedReader(new FileReader(file));
      while ((line = br.readLine()) != null) {
        x++;
        String[] lineArray = line.split(pRedisFileDelimiter);
        fieldName = lineArray[0];
        fieldValue = lineArray[1];
        pipeline.hset(pRedisHash, fieldName, fieldValue);
      }
      pipeline.sync();
      br.close();
     } catch (Exception ex) {
      redisStatus = ex.getMessage();
    }

    redisStatus = getRedisServer() + ":" + getRedisPort();
    redisStatus += "\n" + pRedisHash + " - " + pRedisFileToLoad;
    redisStatus += "\nrecords loaded: " + x;
    pool.close();
    jedis.close();
    return redisStatus;
  }
  
  public String loadRedisServer() {

    String pRedisHost = getRedisServer();
    int pRedisPort = Integer.parseInt(getRedisPort());
    String pRedisSet = getRedisSet();
    String pRedisFileToLoad = getRedisFileToLoad();
    LOG.debug("pRedisType: " + getRedisType());
    LOG.debug("pRedisSet: " + pRedisSet);
    LOG.debug("pRedisFileToLoad: " + pRedisFileToLoad);
    int x = 0;

    String redisStatus;

    try {
      JedisPoolConfig config = new JedisPoolConfig();
      pool = new JedisPool(config, pRedisHost, pRedisPort);
      jedis = null;
      jedis = pool.getResource();
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
    }
    try {
      jedis.del(pRedisSet);
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
    }
    Pipeline pipeline = jedis.pipelined();
    try {
      File file = new File(pRedisFileToLoad); // creates a new file instance
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line;
      while ((line = br.readLine()) != null) {
        pipeline.sadd(pRedisSet, line);
        x++;
      }
      pipeline.sync();
      br.close();
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
    }

    redisStatus = getRedisServer() + ":" + getRedisPort();
    redisStatus += "\n" + pRedisSet + " - " + pRedisFileToLoad;
    redisStatus += "\nrecords loaded: " + x;
    pool.close();
    jedis.close();

    return redisStatus;
  }

   public String writeHashRedisServer() {

    String pRedisHost = getRedisServer();
    int pRedisPort = Integer.parseInt(getRedisPort());
    String pRedisHash = getRedisHash();
    String pRedisHashKey = getRedisHashKey();
    String pRedisHashValue = getRedisHashValue();
    LOG.debug("pRedisType: " + getRedisType());
    LOG.debug("pRedisHash: " + pRedisHash);
    LOG.debug("pRedisHashKey: " + pRedisHashKey);
    LOG.debug("pRedisHashValue: " + pRedisHashValue);
    String redisStatus = null;

    try {
      JedisPoolConfig config = new JedisPoolConfig();
      pool = new JedisPool(config, pRedisHost, pRedisPort);
      jedis = pool.getResource();
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
      jedis.close();
      pool.close();
    } finally {
      try {
        jedis.hset(pRedisHash, pRedisHashKey,pRedisHashValue);
      } catch (Exception ex) {
        redisStatus = ex.getMessage();
      }
      redisStatus = "\n" + pRedisHash + ":" + pRedisHashKey + " - " + pRedisHashValue + " loaded";
      pool.close();
      jedis.close();
    }
    return redisStatus;
  }

  public String writeRedisServer() {

    String pRedisHost = getRedisServer();
    int pRedisPort = Integer.parseInt(getRedisPort());
    String pRedisSet = getRedisSet();
    String pRedisWriteData = getRedisWriteData();
    LOG.debug("pRedisType: " + getRedisType());
    LOG.debug("pRedisSet: " + pRedisSet);
    LOG.debug("pRedisWriteData: " + pRedisWriteData);
    String redisStatus;

    try {
      JedisPoolConfig config = new JedisPoolConfig();
      pool = new JedisPool(config, pRedisHost, pRedisPort);
      jedis = pool.getResource();
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
      jedis.close();
      pool.close();
    } finally {
      try {
        jedis.sadd(pRedisSet, pRedisWriteData);
      } catch (Exception ex) {
        redisStatus = ex.getMessage();
      }

      redisStatus = getRedisServer() + ":" + getRedisPort();
      redisStatus += "\n" + pRedisSet + " - " + pRedisWriteData + " loaded";
      pool.close();
      jedis.close();
    }
    return redisStatus;
  }

  public String readHashRedisServer() {

    String pRedisHost = getRedisServer();
    int pRedisPort = Integer.parseInt(getRedisPort());
    String pRedisHash = getRedisHash();
    String pRedisHashKey = getRedisHashKey();
    LOG.debug("pRedisType: " + getRedisType());
    LOG.debug("pRedisHash: " + pRedisHash);
    LOG.debug("pRedisHashKey: " + pRedisHashKey);
    String redisStatus = null;

    try {
      JedisPoolConfig config = new JedisPoolConfig();
      pool = new JedisPool(config, pRedisHost, pRedisPort);
      //jedis = null;
      jedis = pool.getResource();
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
      jedis.close();
      pool.close();
    } finally {
      redisStatus = jedis.hget(pRedisHash, pRedisHashKey);

      pool.close();
      jedis.close();

      if (redisStatus == null) {
        redisStatus = "no more records in " + pRedisHash;
      }
    }
    return redisStatus;
  }

  public String deleteKeyRedisServer() {
    String pRedisHost = getRedisServer();
    int pRedisPort = Integer.parseInt(getRedisPort());
    String pRedisKey = getRedisKey();
    LOG.debug("pRedisType: " + getRedisType());
    LOG.debug("pRedisKey: " + pRedisKey);

    String redisStatus = "";

    try {
      JedisPoolConfig config = new JedisPoolConfig();
      pool = new JedisPool(config, pRedisHost, pRedisPort);
      //jedis = null;
      jedis = pool.getResource();
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
      jedis.close();
      pool.close();
    } finally {
    
    long redisDelStatus = jedis.del(pRedisKey);
    
    pool.close();
    jedis.close();

    redisStatus = "key: " + pRedisKey + " status: " + redisDelStatus;
    
    }
    return redisStatus;
  }

  public String readRedisServer() {

    String pRedisHost = getRedisServer();
    int pRedisPort = Integer.parseInt(getRedisPort());
    String pRedisSet = getRedisSet();
    String pRedisReadMethod = getRedisReadMethod();
    LOG.debug("pRedisType: " + getRedisType());
    LOG.debug("pRedisSet: " + pRedisSet);
    LOG.debug("pRedisReadMethod: " + pRedisReadMethod);

    String redisStatus = "";

    try {
      JedisPoolConfig config = new JedisPoolConfig();
      pool = new JedisPool(config, pRedisHost, pRedisPort);
      //jedis = null;
      jedis = pool.getResource();
    } catch (Exception ex) {
      redisStatus = ex.getMessage();
      jedis.close();
      pool.close();
    } finally {
      if (getKeepData()) {
        redisStatus = jedis.srandmember(pRedisSet);
      } else {
        redisStatus = jedis.spop(pRedisSet);
      }

      pool.close();
      jedis.close();

      if (redisStatus == null) {
        redisStatus = "no more records in " + pRedisSet;
      }
    }
    return redisStatus;
  }

  public SampleResult sample(Entry entry) {
    SampleResult result = new SampleResult();
    result.setSampleLabel(getLabel());
    LOG.debug("---------------------------------------------------------");
    LOG.debug("pRedisHost: " + getRedisServer() + ":" + getRedisPort());
    result.setResponseCode("200");
    result.setSuccessful(true);
    if (getRedisType().equals("HASHLOAD")) {
      String loadResponseStatus = loadHashRedisServer();
      result.setResponseData(loadResponseStatus, "UTF-8");
      result.setResponseMessage(loadResponseStatus);
    } else if (getRedisType().equals("LOAD")) {
      String loadResponseStatus = loadRedisServer();
      result.setResponseData(loadResponseStatus, "UTF-8");
      result.setResponseMessage(loadResponseStatus);
    } else if (getRedisType().equals("READ")) {
      String readResponseStatus = readRedisServer();
      result.setResponseData(readResponseStatus, "UTF-8");
      result.setResponseMessage(readResponseStatus);
    } else if (getRedisType().equals("READHASH")) {
      String readResponseStatus = readHashRedisServer();
      result.setResponseData(readResponseStatus, "UTF-8");
      result.setResponseMessage(readResponseStatus);
    } else if (getRedisType().equals("WRITEHASH")) {
      String readHashResponseStatus = writeHashRedisServer();
      result.setResponseData(readHashResponseStatus, "UTF-8");
      result.setResponseMessage(readHashResponseStatus);
    } else if (getRedisType().equals("WRITE")) {
      String writeResponseStatus = writeRedisServer();
      result.setResponseData(writeResponseStatus, "UTF-8");
      result.setResponseMessage(writeResponseStatus);
    } else if (getRedisType().equals("DELETEKEY")) {
      String readDeleteResponseStatus = deleteKeyRedisServer();
      result.setResponseData(readDeleteResponseStatus, "UTF-8");
      result.setResponseMessage(readDeleteResponseStatus);
    
    }  
     else if (getRedisType().equals("PING")) {
      result.setResponseData(getCheckRedisServer(), "UTF-8");
      result.setResponseMessage(getCheckRedisServer());
      if (getCheckRedisServer().contains("PONG")) {
        result.setResponseCode("200");
        result.setSuccessful(true);
      } else {
        result.setResponseCode("500");
        result.setSuccessful(false);
      }
    }
    result.sampleStart();
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      result.setResponseCode("200");
      result.setSuccessful(false);
      result.setResponseMessage(e.getMessage());
      LOG.error("Error while sleep", e);
      Thread.currentThread().interrupt();
    }
    result.sampleEnd();
    LOG.debug("---------------------------------------------------------");
    return result;
  }

}
