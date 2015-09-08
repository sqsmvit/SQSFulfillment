package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

public interface DataRecord
{
    DataRecord newCopy();

    void setValueByKey(String key, String data);

    String getValueByKey(String key);

    boolean buildWithCursor(Cursor dbCursor);

    void clearRecord();
}
