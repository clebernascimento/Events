package com.institutotransire.events.util;

import android.content.res.Resources;

public class ScreenWidth {
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
