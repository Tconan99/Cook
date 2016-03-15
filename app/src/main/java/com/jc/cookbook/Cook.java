package com.jc.cookbook;

import java.util.Locale;

/**
 * Created by tconan on 16/3/15.
 */
public class Cook {

    String cookName;

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    @Override
    public String toString() {
        return "[Cook:{cookName:" + cookName + "}]";
    }
}
