package com.contact_view.util;

import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.apache.commons.lang3.StringUtils;


public class ConfigUtils {

    public final static ConfigUtils INSTANCE = new ConfigUtils();

    public Properties properties = new Properties();

    public ConfigUtils() {
        loadCommonProperties();
        System.out.println("Config Utils started");
    }

    private void loadCommonProperties() {
        try {
            Resource resource = new ClassPathResource("/application.properties");

            Properties resourceProps = PropertiesLoaderUtils.loadProperties(resource);
            for (Entry<Object, Object> entry : resourceProps.entrySet()) {
                this.properties.put(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getStringValue(String key) {

        String value = this.properties.getProperty(key);
        return value == null ? value : value.trim();
    }

    public int getIntValue(String key) {

        return Integer.parseInt(properties.getProperty(key).trim());
    }

    public int getIntValue(String key, int defaultVal) {
        if (StringUtils.isEmpty(properties.getProperty(key))) {
            return defaultVal;
        }
        return Integer.parseInt(properties.getProperty(key).trim());
    }

    public long getLongValue(String key) {

        return Long.parseLong(properties.getProperty(key).trim());
    }

    public long getLongValue(String key, long defaultVal) {
        if (StringUtils.isEmpty(properties.getProperty(key))) {
            return defaultVal;
        }
        return Long.parseLong(properties.getProperty(key).trim());
    }

    public boolean getBooleanValue(String key) {

        return properties.getProperty(key) == null ? false : Boolean.parseBoolean(properties.getProperty(key).trim());
    }

    public float getFloatValue(String key) {

        return Float.parseFloat(properties.getProperty(key).trim());
    }

    public float getFloatValue(String key, float defaultVal) {
        if (StringUtils.isEmpty(properties.getProperty(key))) {
            return defaultVal;
        }
        return Float.parseFloat(properties.getProperty(key).trim());
    }

    public String getEnvironmentSlug() {
        return properties.getProperty("environment").toLowerCase();
    }
}

