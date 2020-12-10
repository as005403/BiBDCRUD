package com.foxrider.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public boolean validateDate(String date){
        Pattern p = Pattern.compile("((0[1-9])|([1-2][0-9])|(3[01]))-((0[1-9])|(1[0-2]))-(\\d\\d\\d\\d)");
        Matcher m = p.matcher(date);

        return m.matches();
    }
    public boolean validateTime(String time){
        Pattern p = Pattern.compile("(([0-1][0-9])|(2[0-3])):[0-5][0-9]");
        Matcher m = p.matcher(time);
        return m.matches();
    }
}
