package io.github.xdiamond.client;

import io.github.xdiamond.client.annotation.AllKeyListener;
import io.github.xdiamond.client.event.ConfigEvent;
import io.github.xdiamond.client.event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author hengyunabc
 *
 */
@Component
public class DynamicConfigListener {
  private static final Logger logger = LoggerFactory.getLogger(DynamicConfigListener.class);

  @AllKeyListener
  public void allKeyListener(ConfigEvent event) {
    logger.info("testAllKeyListener invoked....,key = "+event.getKey() +",oldValue = "+event.getOldValue()+",newValue = "+event.getValue()+",opType="+event.getEventType());

    if(event.getEventType() == EventType.ADD){
      XDConfigData.getIns().putValue(event.getKey(),event.getValue());
    }else if(event.getEventType() == EventType.DELETE){
      XDConfigData.getIns().delValue(event.getKey());
    }else if(event.getEventType() == EventType.UPDATE){
      XDConfigData.getIns().updateValue(event.getKey(),event.getValue());
    }
  }
}
