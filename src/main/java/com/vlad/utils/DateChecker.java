package com.vlad.utils;

import org.apache.commons.validator.routines.DateValidator;

import java.util.ArrayList;
import java.util.List;

public class DateChecker {

    private static final List<String> dateFormat = new ArrayList<String>(){{
        add("dd-MM-yy");
        add("dd-MM-yyyy");
        add("MM-dd-yyyy");
        add("yyyy-MM-dd");
        add("yyyy-MM-dd HH:mm:ss");
        add("yyyy-MM-dd HH:mm:ss.SSS");
        add("yyyy-MM-dd HH:mm:ss.SSSZ");
        add("EEEEE MMMMM yyyy HH:mm:ss.SSSZ");
        add("dd/MM/yy");
        add("dd/MM/yyyy");
        add("MM/dd/yyyy");
        add("yyyy/MM/dd");
        add("yyyy/MM/dd HH:mm:ss");
        add("yyyy/MM/dd HH:mm:ss.SSS");
        add("yyyy/MM/dd HH:mm:ss.SSSZ");
    }};

    public boolean isDate(String value) {
        DateValidator validator = new DateValidator(false, -1);
        for (String datePattern : dateFormat) {
            if (validator.isValid(value, datePattern)) {
                return true;
            }
        }
        return false;
    }


}
