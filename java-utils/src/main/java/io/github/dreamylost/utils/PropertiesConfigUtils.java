/* All Contributors (C) 2020 */
package io.github.dreamylost.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 该类负责加载指定个数的properties文件
 *
 * @author 梦境迷离
 * @since 2020/11/26
 */
public class PropertiesConfigUtils {

    private static Map<String, Properties> propertiesConfigMap = new HashMap<>();

    /** 加载propertiesConfig.properties文件 */
    public static Properties loadConfig(String propertiesKey) {
        Properties config = null;
        String fileName = propertiesKey;
        if (!propertiesKey.endsWith(".properties")) {
            fileName = propertiesKey + ".properties";
        }
        try (InputStream input =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            // 获得properties文件名称
            assert input != null;
            try (Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                config = new Properties();
                config.load(reader);
                propertiesConfigMap.put(propertiesKey, config);
            } catch (Exception e) {
                Logger.getGlobal().log(Level.WARNING, propertiesKey + ".properties error:", e);
            }
        } catch (Exception e) {
            Logger.getGlobal()
                    .log(Level.WARNING, "propertiesConfig.properties load failed, reason:", e);
            return config;
        }
        return config;
    }

    /**
     * 获得某一具体key所对应的值
     *
     * @param fileKey 该文件的名称（不包含后缀.properties）
     * @param key 该文件中配置的key
     * @return String
     */
    public static String getProperty(String fileKey, String key) {
        Properties thisConfig = propertiesConfigMap.get(fileKey);
        if (null == thisConfig) {
            thisConfig = loadConfig(fileKey);
        }

        if (null == thisConfig) {
            return null;
        } else {
            return thisConfig.getProperty(key);
        }
    }

    /**
     * 获得某一具体key所对应的值
     *
     * @param fileKey 该文件的名称（不包含后缀.properties）
     * @param key 该文件中配置的key
     * @param defaultValue 当有对应配置文件，但是查询值为null，则返回defaultValue
     * @return String
     */
    public static String getProperty(String fileKey, String key, String defaultValue) {
        String result = getProperty(fileKey, key);
        if (null == result) {
            return defaultValue;
        } else {
            return result;
        }
    }

    /**
     * 通过文件名称（不包含后缀.properties）获得Properties对象
     *
     * @param fileKey 文件的名称（不包含后缀.properties）
     * @return Properties
     */
    public static Properties getPropertyObject(String fileKey) {
        Properties thisConfig = propertiesConfigMap.get(fileKey);
        if (null == thisConfig) {
            thisConfig = loadConfig(fileKey);
        }
        return thisConfig;
    }
}
