/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sample.sender;

import java.util.Calendar;
import java.util.logging.Logger;

/**
 *
 * @author te16554
 */
public class DianeBean {
    private static final Logger LOG = Logger.getLogger(DianeBean.class.getName());
    
    
    Calendar calendar = Calendar.getInstance();
    
    public void sayHello()
    {
        LOG.info("Hello! It's "+calendar.getTime());
    }
}
