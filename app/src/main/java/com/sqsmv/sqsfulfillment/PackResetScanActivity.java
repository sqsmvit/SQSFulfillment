package com.sqsmv.sqsfulfillment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.socketmobile.apiintegration.ScanAPIApplication;
import com.sqsmv.sqsfulfillment.database.pack.PackRecord;
import com.sqsmv.sqsfulfillment.database.packresetscan.PackResetScanContract;
import com.sqsmv.sqsfulfillment.database.packresetscan.PackResetScanDataAccess;

import org.cory.libraries.MoreDateFunctions;
import org.cory.libraries.QuickToast;

public class PackResetScanActivity extends Activity
{
    private DroidConfigManager appConfig;

    private PackResetScanDataAccess packResetScanDataAccess;
    private PackRecord packRecord;
    private PackResetInputHandler packResetInputHandler;

    private TextView packNameView;
    private EditText packIdEntry;
    private EditText quantityEntry;
    private TextView scanIdView;
    private TextView scannerInitialsView;

    private PackResetScanCursorAdapter packResetScanCursorAdapter;

    private boolean isSinglePackScanMode;
    private String scanId;
    private String scannerInitials;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_reset);

        appConfig = new DroidConfigManager(this);

        packRecord = new PackRecord();

        packResetScanDataAccess = new PackResetScanDataAccess(this);
        packResetInputHandler = new PackResetInputHandler(this, packRecord);

        packNameView = (TextView)findViewById(R.id.PackNameView);
        packIdEntry = (EditText)findViewById(R.id.PackIDEntry);
        quantityEntry = (EditText)findViewById(R.id.QuantityEntry);
        scanIdView = (TextView)findViewById(R.id.ScanIdView);;
        scannerInitialsView = (TextView)findViewById(R.id.ScannerInitialsView);;

        isSinglePackScanMode = false;

        Button packResetBackButton = (Button)findViewById(R.id.PackResetBackButton);
        Button packResetCommitButton = (Button)findViewById(R.id.PackResetCommitButton);
        ToggleButton manualScanModeToggle = (ToggleButton)findViewById(R.id.ScanTypeToggle);

        packResetBackButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        packResetCommitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                commitScans();
            }
        });

        manualScanModeToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toggleScanMode();
            }
        });

        quantityEntry.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE && !packIdEntry.getText().toString().isEmpty())
                {
                    handleScanResponse(packResetInputHandler.handleManualInput(packIdEntry.getText().toString(), quantityEntry.getText().toString(), scanId, scannerInitials));
                }
                return false;
            }
        });

        scanIdView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                promptScanId();
            }
        });

        scannerInitialsView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                promptScannerInitials();
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        regBroadCastReceivers();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        openDataAccesses();

        scanId = appConfig.accessString(DroidConfigManager.RESET_SCAN_ID, null, "");
        displayScanId();

        ListView packResetList = ((ListView)findViewById(R.id.PackResetList));
        packResetScanCursorAdapter = new PackResetScanCursorAdapter(this, null);
        packResetList.setAdapter(packResetScanCursorAdapter);
        displayScanInfo();

        scannerInitials = appConfig.accessString(DroidConfigManager.RESET_SCANNER_INITIALS, null, "");
        displayScannerInitials();

        String lastUpdatedString = appConfig.accessString(appConfig.LAST_UPDATED, null, "");
        if(!MoreDateFunctions.getTodayYYMMDD().equals(lastUpdatedString))
        {
            finish();
        }
    }

    @Override
    protected void onPause()
    {
        closeDataAccesses();

        appConfig.accessString(DroidConfigManager.RESET_SCAN_ID, scanId, "");
        appConfig.accessString(DroidConfigManager.RESET_SCANNER_INITIALS, scannerInitials, "");

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_pack_reset, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.AdminOption:
                Navigation.launchAdminActivity(this);
                return true;
            case R.id.SMPairOption:
                Navigation.launchSMPairActivity(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void commitScans()
    {
        Cursor packResetCursor = packResetScanDataAccess.selectAll();
        if(packResetCursor.getCount() > 0)
        {
            if(ScanWriter.exportFile(this, packResetCursor, ScanSource.ResetScans))
            {
                updateAfterCommit();
            }
        }
        else
        {
            QuickToast.makeToast(this, "No scans to commit");
        }
    }

    private void updateAfterCommit()
    {
        packResetScanDataAccess.deleteAll();
        displayScanInfo();
        scanId = "";
        scannerInitials = "";
        appConfig.accessString(DroidConfigManager.RESET_SCAN_ID, "", "");
        appConfig.accessString(DroidConfigManager.RESET_SCANNER_INITIALS, "", "");
        displayScanId();
        displayScannerInitials();
    }

    private void toggleScanMode()
    {
        if(isSinglePackScanMode)
        {
            isSinglePackScanMode = false;
            packIdEntry.setEnabled(true);
            quantityEntry.setEnabled(true);
        }
        else
        {
            isSinglePackScanMode = true;
            packIdEntry.setText("");
            quantityEntry.setText("");
            packIdEntry.setEnabled(false);
            quantityEntry.setEnabled(false);
        }
    }

    private void openDataAccesses()
    {
        packResetScanDataAccess.read();
        packResetInputHandler.openDBs();
    }

    private void closeDataAccesses()
    {
        packResetScanDataAccess.close();
        packResetInputHandler.closeDBs();
    }

    private void displayScanInfo()
    {
        packResetScanCursorAdapter.changeCursor(packResetScanDataAccess.selectReverseOrder());
        TextView packResetTotalView = (TextView)findViewById(R.id.PackResetTotalView);
        packResetTotalView.setText(Integer.toString(packResetScanDataAccess.getTotalPacks()));
    }

    private void displayScanId()
    {
        if(!scanId.isEmpty())
        {
            scanIdView.setText(scanId);
        }
        else
        {
            scanIdView.setText("No Scan ID");
        }
    }

    private void promptScanId()
    {
        AlertDialog.Builder idDialog = new AlertDialog.Builder(this);

        idDialog.setTitle("Scan ID Entry");
        idDialog.setMessage("Enter Scan ID");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setGravity(Gravity.CENTER);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(scanId);
        idDialog.setView(input);

        idDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                scanId = input.getText().toString();
                displayScanId();
            }
        });

        idDialog.setNegativeButton("Cancel", null);
        idDialog.show();
    }

    private void displayScannerInitials()
    {
        if(!scannerInitials.isEmpty() && scannerInitials.length() > 1)
        {
            scannerInitialsView.setText(scannerInitials);
            scannerInitialsView.setBackgroundColor(Color.TRANSPARENT);
        }
        else
        {
            scannerInitialsView.setText("Input Initials");
            scannerInitialsView.setBackgroundColor(Color.RED);
            promptScannerInitials();
        }
    }

    private void promptScannerInitials()
    {
        AlertDialog.Builder initialsDialog = new AlertDialog.Builder(this);

        initialsDialog.setTitle("Scanner Initials Entry");
        initialsDialog.setMessage("Enter Initials");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setGravity(Gravity.CENTER);
        input.setText(scannerInitials);
        initialsDialog.setView(input);

        initialsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                scannerInitials = input.getText().toString();
                displayScannerInitials();
            }
        });
        initialsDialog.show();
    }

    private void handleScanResponse(ScanReturn scanResponse)
    {
        packNameView.setText(packRecord.accessPackName(null));
        switch(scanResponse)
        {
            case SCAN_INSERTED:
                packIdEntry.setText("");
                quantityEntry.setText("");
                displayScanInfo();
                break;
            case NEEDS_QUANTITY:
                packIdEntry.setText(packRecord.accessPkPackId(null));
                QuickToast.makeToast(this, "Enter Quantity");
                if(quantityEntry.requestFocus())
                {
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(quantityEntry, 0);
                }
                break;
            case NO_INITIALS:
                promptScannerInitials();
                break;
        }
    }

    private void promptDeleteRow(int position)
    {
        Cursor tempCursor = packResetScanCursorAdapter.getCursor();
        tempCursor.moveToPosition(position);
        final String scanPk = tempCursor.getString(tempCursor.getColumnIndex(PackResetScanContract._ID));

        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setTitle(tempCursor.getString(tempCursor.getColumnIndex(PackResetScanContract.COLUMN_NAME_SCANNEDPACKNAME)) + ": " +
                                                           tempCursor.getString(tempCursor.getColumnIndex(PackResetScanContract.COLUMN_NAME_QUANTITY)));
        deleteDialog.setMessage("Delete this scan?");
        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                packResetScanDataAccess.deleteByPk(scanPk);
                displayScanInfo();
            }
        });

        deleteDialog.setNegativeButton("No", null);
        deleteDialog.show();
    }

    private class PackResetScanCursorAdapter extends CursorAdapter
    {
        public PackResetScanCursorAdapter(Context context, Cursor cursor)
        {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent)
        {
            return LayoutInflater.from(context).inflate(R.layout.row_pack_reset, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor)
        {
            // Find fields to populate in inflated template
            TextView rowPackNameView = (TextView)view.findViewById(R.id.RowPackName);
            TextView rowPackQuantity = (TextView)view.findViewById(R.id.RowPackQuantity);
            ImageButton rowDeleteButton = (ImageButton)view.findViewById(R.id.RowDeleteButton);
            // Populate fields with extracted properties
            rowPackNameView.setText(cursor.getString(cursor.getColumnIndexOrThrow(PackResetScanContract.COLUMN_NAME_SCANNEDPACKNAME)));
            rowPackQuantity.setText(cursor.getString(cursor.getColumnIndexOrThrow(PackResetScanContract.COLUMN_NAME_QUANTITY)));
            final int position = cursor.getPosition();
            rowDeleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    promptDeleteRow(position);
                }
            });
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context c, Intent intent)
        {
            if(intent.getAction().equalsIgnoreCase(ScanAPIApplication.NOTIFY_DECODED_DATA))
            {
                String scanData = new String(intent.getCharArrayExtra(ScanAPIApplication.EXTRA_DECODEDDATA));
                if(!isSinglePackScanMode)
                {
                    handleScanResponse(packResetInputHandler.handleRegularScan(scanData, quantityEntry.getText().toString(), scanId, scannerInitials));
                }
                else
                {
                    handleScanResponse(packResetInputHandler.handleSingleScan(scanData, scanId, scannerInitials));
                }
            }
            else if(intent.getAction().equalsIgnoreCase(ScanAPIApplication.NOTIFY_SCANNER_ARRIVAL))
            {
                QuickToast.makeToast(c, intent.getStringExtra(ScanAPIApplication.EXTRA_DEVICENAME) + " Connected");
            }
            else if(intent.getAction().equalsIgnoreCase(ScanAPIApplication.NOTIFY_ERROR_MESSAGE))
            {
                QuickToast.makeLongToast(c, intent.getStringExtra(ScanAPIApplication.EXTRA_ERROR_MESSAGE));
            }
        }
    };

    private void regBroadCastReceivers()
    {
        IntentFilter filter;
        filter = new IntentFilter(ScanAPIApplication.NOTIFY_SCANNER_ARRIVAL);
        registerReceiver(receiver, filter);

        filter = new IntentFilter(ScanAPIApplication.NOTIFY_DECODED_DATA);
        registerReceiver(receiver, filter);

        filter = new IntentFilter(ScanAPIApplication.NOTIFY_ERROR_MESSAGE);
        registerReceiver(receiver, filter);
    }
}
