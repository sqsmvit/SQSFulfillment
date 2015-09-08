package com.sqsmv.sqsfulfillment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sqsmv.sqsfulfillment.database.pack.PackContract;
import com.sqsmv.sqsfulfillment.database.packaging.PackagingContract;
import com.sqsmv.sqsfulfillment.database.config.ConfigContract;
import com.sqsmv.sqsfulfillment.database.fulfillmentscan.FulfillmentScanContract;
import com.sqsmv.sqsfulfillment.database.invoice.InvoiceContract;
import com.sqsmv.sqsfulfillment.database.lens.LensContract;
import com.sqsmv.sqsfulfillment.database.packline.PackLineContract;
import com.sqsmv.sqsfulfillment.database.packresetscan.PackResetScanContract;
import com.sqsmv.sqsfulfillment.database.scanner.ScannerContract;
import com.sqsmv.sqsfulfillment.database.shipto.ShipToContract;

public class DBAdapter extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "FulfillmentDB";
	private static final int DATABASE_VERSION = 1;

    private static final DBContract[] xmlContracts = {new InvoiceContract(), new ShipToContract(), new PackContract(), new PackLineContract(), new PackagingContract(),
                                                     new LensContract(), new ConfigContract(), new ScannerContract()};
    private static final DBContract[] scanContracts = {new FulfillmentScanContract(), new PackResetScanContract()};

    public DBAdapter(Context ctx)
    {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
    {
        createTables(db, xmlContracts);
        createTables(db, scanContracts);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        resetTables(db, xmlContracts);
        resetTables(db, scanContracts);
    }

    public void resetAll()
    {
        SQLiteDatabase db = getWritableDatabase();
        resetTables(db, xmlContracts);
        db.close();
    }

    private void createTables(SQLiteDatabase db, DBContract[] dbContracts)
    {
        for(DBContract dbContract : dbContracts)
        {
            db.execSQL(dbContract.getTableCreateString());
        }
    }

    private void resetTables(SQLiteDatabase db, DBContract[] dbContracts)
    {
        for(DBContract dbContract : dbContracts)
        {
            db.execSQL(dbContract.getTableDropString());
            db.execSQL(dbContract.getTableCreateString());
        }
    }
}
