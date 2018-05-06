
/*
 * Copyright (c) 2016 VDROP OPERATIONS PTE LTD
 */

package com.foodpark.Utils;


import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains the validations required for signIn and SignUp input fields.
 */
public class Validation {

    public static boolean isValidName(String name) {
        String NAME_PATTERN = "^[a-zA-Z0-9_.]{3,32}$";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidText(String text) {
        return (text!=null && !text.isEmpty());
    }

    public static boolean isValidPhoneNumber(String number){

        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(number).matches();
        }
    }



}