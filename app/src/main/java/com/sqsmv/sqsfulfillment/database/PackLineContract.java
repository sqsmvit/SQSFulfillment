package com.sqsmv.sqsfulfillment.database;

public class PackLineContract implements XMLFileContract
{
    private static final String TABLE_NAME = "PackLine";
    private static final String XML_FILE_NAME = "packline.xml";
    protected static final String COLUMN_NAME_PKPACKLINEID = "pkpacklineid";
    protected static final String COLUMN_NAME_FKPACKID = "fkpackid";
    protected static final String COLUMN_NAME_PRODUCTNAME = "productname";
    protected static final String COLUMN_NAME_QUANTITY = "quantity";
    protected static final String COLUMN_NAME_PRICEPOINT = "pricepoint";

    //Matches order of SQLite Table and XML
    protected static final String[] PACKLINE_COLUMNS =
            {
                    COLUMN_NAME_PKPACKLINEID,
                    COLUMN_NAME_FKPACKID,
                    COLUMN_NAME_PRODUCTNAME,
                    COLUMN_NAME_QUANTITY,
                    COLUMN_NAME_PRICEPOINT,
                    COLUMN_NAME_SHA
            };

    protected static final String CREATE_TABLE_PACKLINE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_NAME_PKPACKLINEID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_FKPACKID + " TEXT, " +
            COLUMN_NAME_PRODUCTNAME + " TEXT, " +
            COLUMN_NAME_QUANTITY + " TEXT, " +
            COLUMN_NAME_PRICEPOINT + " TEXT, " +
            COLUMN_NAME_SHA + " TEXT);";

    protected static final String DROP_TABLE_PACKLINE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_PACKLINE;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_PACKLINE;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return COLUMN_NAME_PKPACKLINEID;
    }

    @Override
    public String[] getColumnNames()
    {
        return PACKLINE_COLUMNS;
    }

    @Override
    public String getXMLFileName()
    {
        return XML_FILE_NAME;
    }
}
