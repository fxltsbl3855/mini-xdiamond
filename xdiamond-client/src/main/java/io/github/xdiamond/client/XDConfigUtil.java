package io.github.xdiamond.client;

import io.github.xdiamond.client.exception.XDException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class XDConfigUtil {
    private static final Logger logger = LoggerFactory.getLogger(XDConfigUtil.class);

    public static <T> T get(String key,Class<T> clazz){
        String value = get(key,"");
        if (String.class == clazz) {
            return (T) value;
        } else if (Integer.class == clazz || int.class == clazz) {
            return (T) Integer.valueOf(value);
        } else if (Long.class == clazz || long.class == clazz) {
            return (T) Long.valueOf(value);
        } else if (Double.class == clazz || double.class == clazz) {
            return (T) Double.valueOf(value);
        } else if (Short.class == clazz || short.class == clazz) {
            return (T) Short.valueOf(value);
        } else {
            return (T) value;
        }
    }

    public static <T> T get(String key,Class<T> clazz,T t){
        try {
            return get(key,clazz);
        }catch (Exception e){
            logger.error("get error",e);
            return t;
        }
    }

    public static String get(String key) {
       return get(key,"");
    }

    public static String get(String key,String defaultValueWhenError) {
        try {
            return XDConfigData.getIns().getValue(key);
        } catch (XDException e) {
            logger.error(e.getMessage(),e);
            return defaultValueWhenError;
        }catch (Exception e){
            logger.error("get config error",e);
            return defaultValueWhenError;
        }
    }

    public static Map<String,String> getByKeyPrefix(String keyPrefix) {
        try {
            return XDConfigData.getIns().getEntryByKeyPrefix(keyPrefix);
        } catch (XDException e) {
            logger.error(e.getMessage(),e.getCause());
            return Collections.emptyMap();
        }
    }
}
