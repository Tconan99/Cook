package com.jc.cookbook;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        int a = 0;
        int b = a + 3;
        int c = a + b + 7;
        Log.i("c=", "" + c);
    }
}