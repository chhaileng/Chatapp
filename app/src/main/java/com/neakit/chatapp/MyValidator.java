package com.neakit.chatapp;

/**
 * Created by Chhaileng on 3/9/17.
 */

public class MyValidator {
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
