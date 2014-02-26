/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sample.aggregator.example;

import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author te16554
 */
public class RandomMessageSender implements Processor{
    private static final Logger LOG = Logger.getLogger(RandomMessageSender.class.getName());

    public void init()
    {
        LOG.info("HELLO FROM RANDOM MESSAGE SENDER!!!");
    }
    
    private static Random random =  new Random();
    int limit = 5000;
    int current = 0;
    public void process(Exchange exchng) throws Exception {
//        LOG.info("starting processor");
        // Random match
        int nextInt = random.nextInt(limit);
        
        // Never match. 
//        current = current +1;
//        int nextInt = current;
        Date time = new Date();
        exchng.getIn().setHeader("CORRELATION_ID", nextInt);
        exchng.getIn().setHeader("TIMESTAMP", time.getTime());
        
        exchng.getIn().setBody("I'm a super cool message - "+nextInt+" - sent at "+ time);
//        LOG.info("Correlation id: "+nextInt);
    }
    
    
}
