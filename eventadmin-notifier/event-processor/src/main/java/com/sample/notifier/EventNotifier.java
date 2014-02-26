/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.notifier;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.management.EventNotifierSupport;
import org.apache.camel.management.event.AbstractExchangeEvent;
import org.apache.camel.management.event.ExchangeCompletedEvent;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

/**
 * This is the common notifier that can be used by other bundles to register
 * to receive camel events and send out their own event.
 * This is configured in each camel context that should be monitored. 
 */
public class EventNotifier extends EventNotifierSupport {

    private EventAdmin eventAdmin;
    private static final Logger LOG = Logger.getLogger(EventNotifier.class.getName());
    
    private String myName;
    private int count = 0;

    public EventNotifier()
    {
    }
    
    public void init()
    {
        LOG.info("I've been created.  My name is: "+myName);
    }
    
    @Override
    public boolean isEnabled(EventObject event) {
        return true;
    }
    
    public void setName(String name)
    {
        myName = name;
    }
    public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

    @Override
    public void notify(EventObject event) throws Exception {
        if (event instanceof ExchangeCompletedEvent) {
            count = count+1;
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("NAME", myName);
            props.put("CLASS", event.getClass());
            LOG.info(myName+" received event #"+count+" of type: "+event.getClass());
            String topic = "diane/testing/events/"+myName;
            Event camelEvent = new Event(topic, props);
            eventAdmin.sendEvent(camelEvent);
            LOG.info(myName+" sent event to "+topic);
        }
    }

    @Override
    protected void doStart() throws Exception {
        LOG.log(Level.INFO, "{0} starting", myName);
    }

    @Override
    protected void doStop() throws Exception {
        LOG.log(Level.INFO, "{0} stopping", myName);
    }
}


