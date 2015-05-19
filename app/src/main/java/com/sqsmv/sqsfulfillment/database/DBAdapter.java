package com.sqsmv.sqsfulfillment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper
{
	public static final String DATABASE_NAME = "FulfillmentDB";
	
	public static final int DATABASE_VERSION = 1;

    public DBAdapter(Context ctx)
    {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
    {
		db.execSQL(InvoiceContract.CREATE_TABLE_INVOICE);
        db.execSQL(ShipToContract.CREATE_TABLE_SHIPTO);
        db.execSQL(PackContract.CREATE_TABLE_PACK);
        db.execSQL(PackLineContract.CREATE_TABLE_PACKLINE);
        db.execSQL(PackagingContract.CREATE_TABLE_PACKAGING);
        db.execSQL(LensContract.CREATE_TABLE_LENS);
        db.execSQL(ConfigContract.CREATE_TABLE_CONFIG);
        db.execSQL(ScannerContract.CREATE_TABLE_SCANNER);
        //db.execSQL(PackRecreateRulesContract.CREATE_TABLE_PACKRECREATERULES);
        db.execSQL(FulfillmentScanContract.CREATE_TABLE_FULFILLMENTSCAN);
        //db.execSQL(PackRecreateScanContract.CREATE_TABLE_PACKRECREATESCAN);
        //db.execSQL(ExportTrackingContract.CREATE_TABLE_EXPORTTRACKING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(InvoiceContract.DROP_TABLE_INVOICE);
        db.execSQL(InvoiceContract.CREATE_TABLE_INVOICE);

        db.execSQL(ShipToContract.DROP_TABLE_SHIPTO);
        db.execSQL(ShipToContract.CREATE_TABLE_SHIPTO);

        db.execSQL(PackContract.DROP_TABLE_PACK);
        db.execSQL(PackContract.CREATE_TABLE_PACK);

        db.execSQL(PackLineContract.DROP_TABLE_PACKLINE);
        db.execSQL(PackLineContract.CREATE_TABLE_PACKLINE);

        db.execSQL(PackagingContract.DROP_TABLE_PACKAGING);
        db.execSQL(PackagingContract.CREATE_TABLE_PACKAGING);

        db.execSQL(LensContract.DROP_TABLE_LENS);
        db.execSQL(LensContract.CREATE_TABLE_LENS);

        db.execSQL(ConfigContract.DROP_TABLE_CONFIG);
        db.execSQL(ConfigContract.CREATE_TABLE_CONFIG);

        db.execSQL(ScannerContract.DROP_TABLE_SCANNER);
        db.execSQL(ScannerContract.CREATE_TABLE_SCANNER);

        //db.execSQL(PackRecreateRulesContract.DROP_TABLE_PACKRECREATERULES);
        //db.execSQL(PackRecreateRulesContract.CREATE_TABLE_PACKRECREATERULES);

        db.execSQL(FulfillmentScanContract.DROP_TABLE_FULFILLMENTSCAN);
        db.execSQL(FulfillmentScanContract.CREATE_TABLE_FULFILLMENTSCAN);

        //db.execSQL(PackRecreateScanContract.DROP_TABLE_PACKRECREATESCAN);
        //db.execSQL(PackRecreateScanContract.CREATE_TABLE_PACKRECREATESCAN);

        //db.execSQL(ExportTrackingContract.DROP_TABLE_EXPORTTRACKING);
        //db.execSQL(ExportTrackingContract.CREATE_TABLE_EXPORTTRACKING);
	}

    public void resetAll()
    {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(InvoiceContract.DROP_TABLE_INVOICE);
        db.execSQL(InvoiceContract.CREATE_TABLE_INVOICE);

        db.execSQL(ShipToContract.DROP_TABLE_SHIPTO);
        db.execSQL(ShipToContract.CREATE_TABLE_SHIPTO);

        db.execSQL(PackContract.DROP_TABLE_PACK);
        db.execSQL(PackContract.CREATE_TABLE_PACK);

        db.execSQL(PackLineContract.DROP_TABLE_PACKLINE);
        db.execSQL(PackLineContract.CREATE_TABLE_PACKLINE);

        db.execSQL(PackagingContract.DROP_TABLE_PACKAGING);
        db.execSQL(PackagingContract.CREATE_TABLE_PACKAGING);

        db.execSQL(LensContract.DROP_TABLE_LENS);
        db.execSQL(LensContract.CREATE_TABLE_LENS);

        db.execSQL(ConfigContract.DROP_TABLE_CONFIG);
        db.execSQL(ConfigContract.CREATE_TABLE_CONFIG);

        db.execSQL(ScannerContract.DROP_TABLE_SCANNER);
        db.execSQL(ScannerContract.CREATE_TABLE_SCANNER);

        db.close();
    }
}
