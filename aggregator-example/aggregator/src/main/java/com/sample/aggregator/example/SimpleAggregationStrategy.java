/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sample.aggregator.example;

import java.util.Date;
import java.util.logging.Logger;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;


/**
 *
 * @author te16554
 */
public class SimpleAggregationStrategy implements AggregationStrategy  {
    private static final Logger LOG = Logger.getLogger(SimpleAggregationStrategy.class.getName());

    public Exchange aggregate(Exchange first, Exchange second) {
        LOG.info("Exchange 1: "+first);
        LOG.info("Exchange 2: "+second);
        if( first != null)
        {
        Long time1 = first.getIn().getHeader("TIMESTAMP", Long.class);
        Long time2 = second.getIn().getHeader("TIMESTAMP", Long.class);
        Long diff = time2 - time1;
        LOG.info("\n=====MATCH!!!!\n=====\n on id: "+first.getIn().getHeader("CORRELATION_ID")+" time diff: "+diff);

        }else
        {
            Long time2 = second.getIn().getHeader("TIMESTAMP", Long.class);
            LOG.info("first is null....second is sent at "+time2);
        }
        
        return second;
    }
    
    
}
