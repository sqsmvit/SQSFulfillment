package com.sqsmv.sqsfulfillment.database.lens;

import com.sqsmv.sqsfulfillment.database.XMLFileContract;

public class LensContract implements XMLFileContract
{
	private static final String TABLE_NAME = "Lens";
    private static final String XML_FILE_NAME = "flens.xml";
	protected static final String COLUMN_NAME_PKLENSID = "pklensid";
    protected static final String COLUMN_NAME_LENSNAME = "lensname";

    //Matches order of SQLite Table and XML
    protected static final String[] LENS_COLUMNS =
            {
                    COLUMN_NAME_PKLENSID,
                    COLUMN_NAME_LENSNAME,
                    COLUMN_NAME_SHA
            };

    protected static final String CREATE_TABLE_LENS = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_NAME_PKLENSID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_LENSNAME + " TEXT, " +
            COLUMN_NAME_SHA + " TEXT);";

    protected static final String DROP_TABLE_LENS = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_LENS;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_LENS;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return COLUMN_NAME_PKLENSID;
    }

    @Override
    public String[] getColumnNames()
    {
        return LENS_COLUMNS;
    }

    @Override
    public String getXMLFileName()
    {
        return XML_FILE_NAME;
    }
}
