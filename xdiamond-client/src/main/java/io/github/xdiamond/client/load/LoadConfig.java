package io.github.xdiamond.client.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class LoadConfig {
    private static final Logger logger = LoggerFactory.getLogger(LoadConfig.class);
    private static ConfigSource cs = ConfigSource.PROPERTIES;
    private String configSourceKey;

    public String getConfigSourceKey() {
        return configSourceKey;
    }

    public void setConfigSourceKey(String configSourceKey) {
        logger.info("LoadConfig.setConfigSourceKey invoke...configSourceKey={}",configSourceKey);
        this.configSourceKey = configSourceKey;
        if(configSourceKey == null || "".equals(configSourceKey.trim())){
            logger.error("not configured the config source,using properties file default(not use Config Server)"
                    ,new RuntimeException("not configured the config source"));
            cs = ConfigSource.PROPERTIES;
            return;
        }
        if(configSourceKey.trim().equalsIgnoreCase("properties") || configSourceKey.trim().equalsIgnoreCase("property")){
            cs = ConfigSource.PROPERTIES;
        }else if(configSourceKey.trim().equalsIgnoreCase("xdiamond") || configSourceKey.trim().equalsIgnoreCase("diamond")){
            cs = ConfigSource.XDIAMOND;
        }else{
            logger.error("LoadConfig config error,configSourceKey value is invalid,please check it , using properties file default",
                    new RuntimeException("configured invalid"));
            cs = ConfigSource.PROPERTIES;
        }
    }

    public static ConfigSource getConfigSource(){
        return cs;
    }
}
