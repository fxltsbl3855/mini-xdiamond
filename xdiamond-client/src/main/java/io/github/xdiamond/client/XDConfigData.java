package io.github.xdiamond.client;

import io.github.xdiamond.client.exception.XDException;
import io.github.xdiamond.client.load.ConfigSource;
import io.github.xdiamond.client.load.LoadConfig;
import io.github.xdiamond.common.ResolvedConfigVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class XDConfigData {
    private static final Logger logger = LoggerFactory.getLogger(XDConfigData.class);
    private static XDConfigData ourInstance = new XDConfigData();
    private ConcurrentHashMap<String,String> datamap = new ConcurrentHashMap<String,String>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    public static XDConfigData getIns() {
        return ourInstance;
    }

    private XDConfigData() {
    }



    public String getValue(String key) throws XDException {
        if(key == null || "".equals(key.trim())){
            throw new XDException("key is empty, check it");
        }
        lock.readLock().lock();
        logger.debug("XDConfig.getValue lock");
        try {
            if(!datamap.containsKey(key)){
                throw new XDException("key is not configured on Server, check it");
            }
            return datamap.get(key);
        }finally {
            lock.readLock().unlock();
            logger.debug("XDConfig.getValue unlock");
        }
    }

    public Map<String,String> getEntryByKeyPrefix(String keyPrefix)throws XDException{
        if(keyPrefix == null || "".equals(keyPrefix.trim())){
            throw new XDException("key is empty, check it");
        }
        lock.readLock().lock();
        logger.debug("XDConfig.getEntryByKeyPrefix lock");
        try {
            if(datamap == null || datamap.size()==0){
                return Collections.emptyMap();
            }
            Map<String,String> map = new HashMap<String,String>(4);
            for(Map.Entry<String,String> entry : datamap.entrySet()){
                if(entry.getKey().indexOf(keyPrefix) >= 0){
                    map.put(entry.getKey(),entry.getValue());
                }
            }
            return map;
        }finally {
            lock.readLock().unlock();
            logger.debug("XDConfig.getEntryByKeyPrefix unlock");
        }
    }

    public void copyData(Map<String, ResolvedConfigVO> dataVo1){
        if(LoadConfig.getConfigSource()!= ConfigSource.XDIAMOND){
            logger.debug("copyData not invoke,configSource={}",LoadConfig.getConfigSource());
            return;
        }
        lock.writeLock().lock();
        logger.debug("XDConfig.copyData lock");
        try {
            if (datamap == null) {
                datamap = new ConcurrentHashMap<>();
            }
            this.datamap.clear();
            if(dataVo1!=null && dataVo1.size()>0) {
                for (Map.Entry<String, ResolvedConfigVO> entry : dataVo1.entrySet()) {
                    this.datamap.put(entry.getValue().getConfig().getKey(), entry.getValue().getConfig().getValue());
                }
            }
        }finally {
            lock.writeLock().unlock();
            logger.debug("XDConfig.copyData unlock");
        }
        logger.info(toString());
    }

    public void putValue(String key,String value){
        if(LoadConfig.getConfigSource()!= ConfigSource.XDIAMOND){
            logger.debug("putValue not invoke,configSource={}",LoadConfig.getConfigSource());
            return;
        }
        lock.readLock().lock();
        logger.debug("XDConfig.putValue lock");
        try {
            datamap.put(key,value);
        }finally {
            lock.readLock().unlock();
            logger.debug("XDConfig.putValue unlock");
        }
    }

    public void putValueFromProperties(String key,String value){
        if(LoadConfig.getConfigSource()!= ConfigSource.PROPERTIES){
            logger.debug("putValueFromProperties not invoke,configSource={}",LoadConfig.getConfigSource());
            return;
        }
        lock.readLock().lock();
        logger.debug("XDConfig.putValueFromProperties lock");
        try {
            datamap.put(key,value);
        }finally {
            lock.readLock().unlock();
            logger.debug("XDConfig.putValueFromProperties unlock");
        }
    }

    public void updateValue(String key,String value){
        if(LoadConfig.getConfigSource()!= ConfigSource.XDIAMOND){
            logger.debug("updateValue not invoke,configSource={}",LoadConfig.getConfigSource());
            return;
        }
        lock.readLock().lock();
        logger.debug("XDConfig.updateValue lock");
        try {
            if(!datamap.containsKey(key)) {
                logger.error("update key,but not find key,key="+key+",value="+value);
                return;
            }
            datamap.put(key,value);
        }finally {
            lock.readLock().unlock();
            logger.debug("XDConfig.updateValue unlock");
        }

    }
    public String delValue(String key){
        if(LoadConfig.getConfigSource()!= ConfigSource.XDIAMOND){
            logger.debug("delValue not invoke,configSource={}",LoadConfig.getConfigSource());
            return null;
        }
        lock.readLock().lock();
        logger.debug("XDConfig.delValue lock");
        try {
            return datamap.remove(key);
        }finally {
            lock.readLock().unlock();
            logger.debug("XDConfig.delValue unlock");
        }
    }

    @Override
    public String toString(){

        if(datamap == null || datamap.size() ==0){
            return "empty";
        }
        StringBuilder sb = new StringBuilder();
        for(Map.Entry entry : datamap.entrySet()){
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(", ");
        }
        return sb.toString();
    }
}
