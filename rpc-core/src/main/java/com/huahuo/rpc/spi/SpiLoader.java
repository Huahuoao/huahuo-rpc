package com.huahuo.rpc.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import com.huahuo.rpc.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SPI 加载器
 */
@Slf4j
public class SpiLoader {
  private static Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();
  private static Map<String, Object> instanceCache = new ConcurrentHashMap<>();

  private static final String RPC_DIR = "META-INF/rpc/";
  private static final List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);

  public static void loadAll() {
    log.info("加载SPI类...");
    for (Class<?> aClass : LOAD_CLASS_LIST) {
      load(aClass);
    }
  }
public static <T> T getInstance(Class<?> tClass,String key){
    String tClassName = tClass.getName();
    Map<String,Class<?>> keyClassMap = loaderMap.get(tClassName);
  System.out.println(keyClassMap);
  if (keyClassMap == null){
      throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型",tClassName));
    }
    if (!keyClassMap.containsKey(key)){
      throw new RuntimeException(String.format("SpiLoader 的 %s 不存在 key=%s 的类型",tClassName,key));

    }
    Class<?> implClass = keyClassMap.get(key);
    String implClassName = implClass.getName();
    if(!instanceCache.containsKey(implClassName)){
      try{
        instanceCache.put(implClassName,implClass.newInstance());
      }catch (InstantiationException|IllegalAccessException e){
        String errorMsg = String.format("%s 类实例化失败",implClassName);
        throw new RuntimeException(errorMsg,e);
      }
    }
    return (T) instanceCache.get(implClassName);
}
  public static Map<String, Class<?>> load(Class<?> loadClass) {
    log.info("加载类型为{}的SPI", loadClass.getName());
    Map<String, Class<?>> keyClassMap = new HashMap<>();
    List<URL> resources = ResourceUtil.getResources(RPC_DIR+loadClass.getName());
    log.info("SPI资源文件路径：{}", RPC_DIR+loadClass.getName());
    log.info("SPI资源文件数量：{}", resources.size());
    for (URL resource : resources) {
      log.info("加载SPI资源文件：{}", resource.toString());
      try {
        InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          String[] strArray = line.split("=");
          if (strArray.length > 1) {
            String key = strArray[0];
            String className = strArray[1];
            keyClassMap.put(key, Class.forName(className));
          }
        }
      } catch (Exception e) {
        log.error("spi resource load error", e);
      }
    }
    loaderMap.put(loadClass.getName(), keyClassMap);
    return keyClassMap;
  }
}
