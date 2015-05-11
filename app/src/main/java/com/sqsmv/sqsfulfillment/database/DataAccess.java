package com.sqsmv.sqsfulfillment.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
//Data Access controls all actual interaction with the database
public abstract class DataAccess
{
    protected SQLiteDatabase db;
    protected DBAdapter dbAdapter;
    protected DBContract contract;

    public DataAccess(Context activityContext)
    {
        dbAdapter = new DBAdapter(activityContext);
        initContract();
    }

    public abstract void initContract();

    public abstract DataRecord getDataRecordInstance();

    public void open()
    {
        db = dbAdapter.getWritableDatabase();
    }

    public void read()
    {
        db = dbAdapter.getReadableDatabase();
    }

    public void close()
    {
        dbAdapter.close();
    }

    public void reset()
    {
        db.execSQL(contract.getTableDropString());
        db.execSQL(contract.getTableCreateString());
    }

    public Cursor selectAll()
    {
        String selectQuery = QueryBuilder.buildSelectQuery(contract.getTableName(), new String[]{"*"}, new String[]{});
        return db.rawQuery(selectQuery, null);
    }

    public Cursor selectByPk(String pKey)
    {
        String selectQuery = QueryBuilder.buildSelectQuery(contract.getTableName(), new String[]{"*"}, new String[]{contract.getPrimaryKeyName()});
        return db.rawQuery(selectQuery, new String[]{pKey});
    }

    public Cursor selectColumnsByPk(String pKey, String columns[])
    {
        String selectQuery = QueryBuilder.buildSelectQuery(contract.getTableName(), columns, new String[]{contract.getPrimaryKeyName()});
        return db.rawQuery(selectQuery, new String[]{pKey});
    }

    public void insert(DataRecord record)
    {
        String INSERT_QUERY = QueryBuilder.buildInsertQuery(contract.getTableName(), getTableColumns());
        SQLiteStatement query = db.compileStatement(INSERT_QUERY);
        db.beginTransaction();
        for(int count = 0; count < getTableColumns().length; count++)
        {
            String insertValue = record.getValueByKey(getTableColumns()[count]);
            if(insertValue.equals("null"))
                query.bindNull(count + 1);
            else
                query.bindString(count + 1, insertValue);
        }
        query.executeInsert();
        query.close();
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void deleteAll()
    {
        db.delete(contract.getTableName(), null, null);
    }

    public void insertBatch(ArrayList<ArrayList<String>> batch)
    {
        String insertQuery = new QueryBuilder().buildInsertQuery(getTableName(), getTableColumns());
        SQLiteStatement query = db.compileStatement(insertQuery);
        db.beginTransaction();

        query.clearBindings();

        for(ArrayList<String> prod : batch)
        {
            for(int i = 0; i < getTableColumns().length; i++)
            {
                query.bindString(i+1, prod.get(i));
            }
            query.executeInsert();
        }
        query.close();
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public String getTableName()
    {
        return contract.getTableName();
    }

    public String getPKeyName()
    {
        return contract.getPrimaryKeyName();
    }

    public String[] getTableColumns()
    {
        return contract.getColumnNames();
    }
}
