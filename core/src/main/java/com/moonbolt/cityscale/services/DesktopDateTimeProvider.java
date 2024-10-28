package com.moonbolt.cityscale.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.moonbolt.cityscale.interfaces.DateTimeProvider;

public class DesktopDateTimeProvider implements DateTimeProvider {
    @Override
    public String getCurrentDateTime() {
        
    	//return "";
         
        // Get the current date and time
        
    	Date now = new Date();

        // Define the format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Format the current date and time as a string
        return formatter.format(now);
        
    }
}