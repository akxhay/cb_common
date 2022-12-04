package com.cb.cb_permission;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    public static void s(Context c, String message) {

        Toast.makeText(c, message + "akshay", Toast.LENGTH_SHORT).show();

    }
}
