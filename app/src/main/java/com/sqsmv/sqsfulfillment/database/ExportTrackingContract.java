package com.sqsmv.sqsfulfillment.database;

public class ExportTrackingContract implements DBContract
{
	private static final String TABLE_NAME = "ExportTracking";
	protected static final String COLUMN_NAME_PKDATE = "pkdate";
    protected static final String COLUMN_NAME_NUMFULFILLMENTFILES = "numfulfillmentfiles";
    //protected static final String COLUMN_NAME_NUMPACKRECREATEFILES = "numpackrecreatefiles";

    //Matches order of SQLite Table and XML
    protected static final String[] EXPORTTRACKING_COLUMNS =
            {
                    COLUMN_NAME_PKDATE,
                    COLUMN_NAME_NUMFULFILLMENTFILES//,
                    //COLUMN_NAME_NUMPACKRECREATEFILES
            };

    protected static final String CREATE_TABLE_EXPORTTRACKING = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_NAME_PKDATE + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_NUMFULFILLMENTFILES + " TEXT);";// +
            //COLUMN_NAME_NUMPACKRECREATEFILES + " TEXT);";

    protected static final String DROP_TABLE_EXPORTTRACKING = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_EXPORTTRACKING;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_EXPORTTRACKING;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return COLUMN_NAME_PKDATE;
    }

    @Override
    public String[] getColumnNames()
    {
        return EXPORTTRACKING_COLUMNS;
    }
}
