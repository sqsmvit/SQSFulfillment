package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

public interface DataRecord
{
    public DataRecord newCopy();

    public void setValueByKey(String key, String data);

    public String getValueByKey(String key);

    public boolean buildWithCursor(Cursor dbCursor);

    public void clearRecord();
}
