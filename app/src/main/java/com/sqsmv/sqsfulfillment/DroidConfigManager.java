package com.sqsmv.sqsfulfillment;

import android.content.Context;
import android.content.SharedPreferences;

public class DroidConfigManager
{
    public final static String LAST_UPDATED = "lastUpdated";
    public final static String CURRENT_SCANNER_INITIALS = "currentScannerInitials";
    public final static String SCANNER_LOCK = "scannerLock";
    public final static String UPDATE_LOCK = "updateLock";

    private SharedPreferences config;
    private final static String prefName = "fulfillmentConfig";

    DroidConfigManager(Context activityContext)
    {
        config = activityContext.getSharedPreferences(prefName, 0);
    }

    public String accessString(String key, String value, String defaultVal)
    {
        if(value != null)
            config.edit().putString(key, value).apply();
        return config.getString(key, defaultVal);
    }

    public int accessInt(String key, Integer value, int defaultVal)
    {
        if(value != null)
            config.edit().putInt(key, value).apply();
        return config.getInt(key, defaultVal);
    }

    public float accessFloat(String key, Float value, float defaultVal)
    {
        if(value != null)
            config.edit().putFloat(key, value).apply();
        return config.getFloat(key, defaultVal);
    }

    public boolean accessBoolean(String key, Boolean value, boolean defaultVal)
    {
        if(value != null)
            config.edit().putBoolean(key, value).apply();
        return config.getBoolean(key, defaultVal);
    }
}
