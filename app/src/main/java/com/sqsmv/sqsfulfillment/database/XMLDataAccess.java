package com.sqsmv.sqsfulfillment.database;

import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;

public abstract class XMLDataAccess extends DataAccess
{
    protected XMLFileContract xmlContract;

    public XMLDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    public HashMap<String, String> getSha()
    {
        HashMap<String, String> mapIds= new HashMap<>();

        String tempPK;
        String tempSha;

        String[] queryColumns = {contract.getPrimaryKeyName(), xmlContract.COLUMN_NAME_SHA};

        Cursor dbCursor = db.query(contract.getTableName(), queryColumns, null, null, null, null, null);

        if(dbCursor.moveToFirst())
        {
            int pkCol = dbCursor.getColumnIndex(contract.getPrimaryKeyName());
            int shaCol = dbCursor.getColumnIndex(xmlContract.COLUMN_NAME_SHA);
            do
            {
                tempPK = dbCursor.getString(pkCol);
                tempSha = dbCursor.getString(shaCol);

                mapIds.put(tempPK, tempSha);
            }
            while(dbCursor.moveToNext());
        }
        dbCursor.close();
        return mapIds;
    }

    public String getXMLFileName()
    {
        return xmlContract.getXMLFileName();
    }
}
