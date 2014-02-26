/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sample.notifier;

import java.util.logging.Logger;
import org.apache.camel.Exchange;

/**
 * This is the processor that recieves all of the custom messages sent 
 * by EventNotifier. This is configured only once, and there should NOT
 * be a notifier defined in the same camel context or you'll get a stack
 * overflow within seconds...
 * 
 */
public class Processor implements org.apache.camel.Processor {
    private static final Logger LOG = Logger.getLogger(Processor.class.getName());

    
    private int count = 0;
	@Override
	public void process(Exchange exchange) throws Exception {
            count = count+1;
            StringBuilder b = new StringBuilder();
            b.append("Processor received message.  Currently processed "+count+" messages");
            b.append("\nExchange: "+exchange.toString());
            b.append("\nProperties: "+exchange.getProperties());
            b.append("\nRouteID: "+exchange.getFromRouteId());
            b.append("\nExchangeID: "+exchange.getExchangeId());
            
            LOG.info(b.toString());
        }
}
