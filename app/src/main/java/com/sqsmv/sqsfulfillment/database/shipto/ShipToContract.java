package com.sqsmv.sqsfulfillment.database.shipto;

import com.sqsmv.sqsfulfillment.database.XMLFileContract;

public class ShipToContract implements XMLFileContract
{
	private static final String TABLE_NAME = "ShipTo";
    private static final String XML_FILE_NAME = "shipto.xml";
	protected static final String COLUMN_NAME_PKSHIPTOID = "pkshiptoid";
    protected static final String COLUMN_NAME_SHIPNAME = "shipname";
    protected static final String COLUMN_NAME_SHIPADDRESS = "shipaddress";
    protected static final String COLUMN_NAME_SHIPCITY = "shipcity";
    protected static final String COLUMN_NAME_SHIPSTATE = "shipstate";
    protected static final String COLUMN_NAME_SHIPZIP = "shipzip";
    protected static final String COLUMN_NAME_ISACTIVE = "isactive";

    //Matches order of SQLite Table and XML
    protected static final String[] SHIPTO_COLUMNS =
            {
                    COLUMN_NAME_PKSHIPTOID,
                    COLUMN_NAME_SHIPNAME,
                    COLUMN_NAME_SHIPADDRESS,
                    COLUMN_NAME_SHIPCITY,
                    COLUMN_NAME_SHIPSTATE,
                    COLUMN_NAME_SHIPZIP,
                    COLUMN_NAME_ISACTIVE,
                    COLUMN_NAME_SHA
            };

    protected static final String CREATE_TABLE_SHIPTO = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_NAME_PKSHIPTOID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_SHIPNAME + " TEXT, " +
            COLUMN_NAME_SHIPADDRESS + " TEXT, " +
            COLUMN_NAME_SHIPCITY + " TEXT, " +
            COLUMN_NAME_SHIPSTATE + " TEXT, " +
            COLUMN_NAME_SHIPZIP + " TEXT, " +
            COLUMN_NAME_ISACTIVE + " TEXT, " +
            COLUMN_NAME_SHA + " TEXT);";

    protected static final String DROP_TABLE_SHIPTO = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_SHIPTO;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_SHIPTO;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return COLUMN_NAME_PKSHIPTOID;
    }

    @Override
    public String[] getColumnNames()
    {
        return SHIPTO_COLUMNS;
    }

    @Override
    public String getXMLFileName()
    {
        return XML_FILE_NAME;
    }
}
