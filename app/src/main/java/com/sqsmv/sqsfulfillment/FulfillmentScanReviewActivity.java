package com.sqsmv.sqsfulfillment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sqsmv.sqsfulfillment.database.FulfillmentScanContract;
import com.sqsmv.sqsfulfillment.database.FulfillmentScanDataAccess;

import org.cory.libraries.QuickToast;

import java.io.File;
import java.io.IOException;


public class FulfillmentScanReviewActivity extends Activity
{
    private FulfillmentScanDataAccess fulfillmentScanDataAccess;

    private ListView fulfilledInvoiceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfillment_scan_review);

        fulfillmentScanDataAccess = new FulfillmentScanDataAccess(this);

        Button reviewBackButton = (Button)findViewById(R.id.ReviewBackButton);
        Button commitScansButton = (Button)findViewById(R.id.CommitScansButton);

        fulfilledInvoiceListView = (ListView)findViewById(R.id.FulfilledInvoiceList);

        reviewBackButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        commitScansButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                commitScans();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        fulfillmentScanDataAccess.open();
        updateFulfilledInvoiceList();
    }

    @Override
    protected void onPause()
    {
        fulfillmentScanDataAccess.close();
        super.onPause();
    }

    private void commitScans()
    {
        DroidConfigManager appConfig = new DroidConfigManager(this);
        Cursor fulfilledInvoicesCursor = fulfillmentScanDataAccess.selectAll();
        if(fulfilledInvoicesCursor.getCount() > 0)
        {
            //ScanWriter scanWriter = new ScanWriter(this, fulfilledInvoicesCursor);
            try
            {
                File exportFile = ScanWriter.writeExportFile(this, fulfilledInvoicesCursor);
                fulfillmentScanDataAccess.deleteAll();
                DropboxManager dropboxManager = new DropboxManager(this);
                dropboxManager.writeToDropbox(exportFile, File.separator + "fulfillments" + File.separator + exportFile.getName(), true);
                updateFulfilledInvoiceList();
                appConfig.accessString(DroidConfigManager.CURRENT_SCANNER_NAME, "", "");
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else
            QuickToast.makeToast(this, "No scans to commit");
    }

    private void updateFulfilledInvoiceList()
    {
        Cursor fulfilledInvoicesCursor = fulfillmentScanDataAccess.selectAll();
        FulfillmentScanCursorAdapter adapter = new FulfillmentScanCursorAdapter(this, fulfilledInvoicesCursor);
        fulfilledInvoiceListView.setAdapter(adapter);
    }

    private class FulfillmentScanCursorAdapter extends CursorAdapter
    {
        public FulfillmentScanCursorAdapter(Context context, Cursor cursor)
        {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent)
        {
            return LayoutInflater.from(context).inflate(R.layout.row_fulfilled_invoice, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor)
        {
            // Find fields to populate in inflated template
            TextView invoiceNum = (TextView)view.findViewById(R.id.InvoiceNum);
            TextView packName = (TextView)view.findViewById(R.id.PackName);
            // Populate fields with extracted properties
            invoiceNum.setText(cursor.getString(cursor.getColumnIndexOrThrow(FulfillmentScanContract.COLUMN_NAME_FKINVOICEID)));
            packName.setText(cursor.getString(cursor.getColumnIndexOrThrow(FulfillmentScanContract.COLUMN_NAME_SCANNEDPACKNAME)));
        }
    }
}
