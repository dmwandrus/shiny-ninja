/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.simplebundle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author te16554:q
 */
public class StringExtractor {

    public static final String tail = "C:\\Users\\te16554\\logs-from-prod\\thread_dumps_from_prod\\tail100000.out";
    
    public static void main(String[] args) {
        StringExtractor se = new StringExtractor();
        Set<String> extractStrings = se.extractStringsBetweenTags(tail, "destinationEndpoint");
        System.out.println("Unique Strings: "+extractStrings);
        Map<String, Integer> extractUniqueValues = se.extractUniqueValues(tail, "correlationId=");
        System.out.println("unique correlation ids: "+extractUniqueValues);
        System.out.println("Number of correlationIds: "+extractUniqueValues.size());
        int matched = 0;
        int overmatched = 0;
        String firstOvermatched = null;
        for(Entry<String, Integer> entry:extractUniqueValues.entrySet())
        {
            if(entry.getValue() == 2)
            {
                matched = matched+1;
            }else if (entry.getValue() >2)
            {
                overmatched = overmatched + 1;
                if(firstOvermatched == null)
                {
                    firstOvermatched = entry.getKey();
                }
            }
        }
        System.out.println("Matched: "+matched);
        System.out.println("overMatched: "+overmatched);
        System.out.print("first: "+firstOvermatched+"\n");
    }

    
    public Set<String> extractStringsBetweenTags(String filename, String tag) {
        Set<String> uniqueStrings = new HashSet<String>();

        InputStream fis = null;
        BufferedReader br;
        String line;

        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";

        try {

            fis = new FileInputStream(filename);
            br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
            while ((line = br.readLine()) != null) {
                // Deal with the line
                if (line.contains(tag)) {
                    int indexOf = line.indexOf(startTag);
                    // probably multiple per line
                    while (indexOf != -1) {
//                        System.out.println("Index : "+indexOf);
                        int end = line.indexOf(endTag, indexOf);
                        int start = indexOf + startTag.length();
                        String between = line.substring(start, end);
//                        System.out.println(between);
                        uniqueStrings.add(between);
                        indexOf = line.indexOf(startTag, indexOf+1);
//                        System.out.println("New Index: "+indexOf);
                    }

                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StringExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StringExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(StringExtractor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return uniqueStrings;
    }

    
    public Map<String, Integer> extractUniqueValues(String filename, String param) {
        Map<String, Integer> uniqueStrings = new HashMap<String, Integer>();

        InputStream fis = null;
        BufferedReader br;
        String line;

        try {

            fis = new FileInputStream(filename);
            br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
            while ((line = br.readLine()) != null) {
                // Deal with the line
                if (line.contains(param)) {
                    int indexOf = line.indexOf(param);
                    // probably multiple per line
                    while (indexOf != -1) {
//                        System.out.println("Index : "+indexOf);
                        int end = line.indexOf(", ", indexOf);
                        if(end == -1)
                        {
                            break;
                        }
                        int start = indexOf + param.length();
                        String between = line.substring(start, end);
//                        System.out.println(between);
                        if(uniqueStrings.containsKey(between))
                        {
                            Integer oldCount = uniqueStrings.get(between);
                            uniqueStrings.put(between, oldCount+1);
                        }else{
                            uniqueStrings.put(between, 1);
                        }
                        indexOf = line.indexOf(param, indexOf+1);
//                        System.out.println("New Index: "+indexOf);
                    }

                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StringExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StringExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(StringExtractor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return uniqueStrings;
    }
}
