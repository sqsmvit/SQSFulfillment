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
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.socketmobile.apiintegration.ScanAPIApplication;
import com.sqsmv.sqsfulfillment.database.ConfigDataAccess;
import com.sqsmv.sqsfulfillment.database.ConfigRecord;
import com.sqsmv.sqsfulfillment.database.FulfillmentScanDataAccess;
import com.sqsmv.sqsfulfillment.database.FulfillmentScanRecord;
import com.sqsmv.sqsfulfillment.database.InvoiceRecord;
import com.sqsmv.sqsfulfillment.database.LensDataAccess;
import com.sqsmv.sqsfulfillment.database.LensRecord;
import com.sqsmv.sqsfulfillment.database.PackDataAccess;
import com.sqsmv.sqsfulfillment.database.PackLineDataAccess;
import com.sqsmv.sqsfulfillment.database.PackLineRecord;
import com.sqsmv.sqsfulfillment.database.PackRecord;
import com.sqsmv.sqsfulfillment.database.PackagingDataAccess;
import com.sqsmv.sqsfulfillment.database.PackagingRecord;
import com.sqsmv.sqsfulfillment.database.ShipToRecord;

import org.cory.libraries.MoreDateFunctions;
import org.cory.libraries.QuickToast;

import java.util.ArrayList;
import java.util.List;


public class FulfillmentScanActivity extends Activity
{
    private static final String TAG = "FulfillmentScanActivity";

    private DroidConfigManager appConfig;

    private PackDataAccess packDataAccess;
    private PackLineDataAccess packLineDataAccess;
    private ConfigDataAccess configDataAccess;
    private LensDataAccess lensDataAccess;
    private PackagingDataAccess packagingDataAccess;
    private FulfillmentScanDataAccess fulfillmentScanDataAccess;
    private InvoiceRecord currentInvoiceRecord;
    private ShipToRecord currentShipToRecord;
    private PackRecord currentPackRecord;
    private FulfillmentScanRecord currentFulfillmentScanRecord;
    private FulfillmentScanHandler fulfillmentScanHandler;

    private Button resetButton;
    private LinearLayout basicScanLLayout;
    private LinearLayout doublePackLLayout;
    private TextView fullfillNumberTextView;
    private TextView shipToTextView;
    private TextView invoiceTextView;
    private TextView packTextView;
    private TextView initialsTextView;
    private ListView recommendedDoublePackListView;
    private ListView scannedPackListView;
    private ListView possibleConfigListView;

    private ArrayList<PackRecord> scannedPacks;
    private ArrayList<ConfigRecord> possibleConfigs;

    private boolean scannerLock;
    private boolean doubleMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfillment_scan);

        appConfig = new DroidConfigManager(this);

        packDataAccess = new PackDataAccess(this);
        packLineDataAccess = new PackLineDataAccess(this);
        configDataAccess = new ConfigDataAccess(this);
        lensDataAccess = new LensDataAccess(this);
        packagingDataAccess = new PackagingDataAccess(this);
        fulfillmentScanDataAccess = new FulfillmentScanDataAccess(this);
        currentInvoiceRecord = new InvoiceRecord();
        currentShipToRecord = new ShipToRecord();
        currentPackRecord = new PackRecord();
        currentFulfillmentScanRecord = new FulfillmentScanRecord();

        fulfillmentScanHandler = new FulfillmentScanHandler(this, currentInvoiceRecord, currentShipToRecord, currentPackRecord, currentFulfillmentScanRecord);

        Button launchReviewButton = (Button)findViewById(R.id.LaunchReviewButton);
        Button launchAdminButton = (Button)findViewById(R.id.LaunchAdminButton);
        TextView scannedPackListHeader = (TextView)findViewById(R.id.ScannedPackListHeader);

        resetButton = (Button)findViewById(R.id.ResetButton);
        basicScanLLayout = (LinearLayout)findViewById(R.id.BasicScanArea);
        doublePackLLayout = (LinearLayout)findViewById(R.id.DoublePackArea);
        fullfillNumberTextView = (TextView)findViewById(R.id.FulfillNumber);
        shipToTextView = (TextView)findViewById(R.id.ShipToText);
        invoiceTextView = (TextView)findViewById(R.id.InvoiceText);
        packTextView = (TextView)findViewById(R.id.PackText);
        initialsTextView = (TextView)findViewById(R.id.InitialsText);
        recommendedDoublePackListView = (ListView)findViewById(R.id.RecommendedPackList);
        scannedPackListView = (ListView)findViewById(R.id.ScannedPackList);
        possibleConfigListView = (ListView)findViewById(R.id.PossibleConfigList);
        scannedPacks = new ArrayList<>();
        possibleConfigs = new ArrayList<>();
        doubleMode = false;

        launchReviewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchReviewActivity();
            }
        });
        launchAdminButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchAdminActivity();
            }
        });
        scannedPackListHeader.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                scannedPacks.clear();
                listScannedPacks();
                listAllPossibleConfigs();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetScans();
            }
        });
        initialsTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                promptInitials();
            }
        });
        packTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                displayPackLines();
            }
        });

        readyForNextScan();
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
        displayTotalFulfillments();

        scannerLock = appConfig.accessBoolean(DroidConfigManager.SCANNER_LOCK, null, false);
        String scannerInitials = appConfig.accessString(DroidConfigManager.CURRENT_SCANNER_INITIALS, null, "");
        currentFulfillmentScanRecord.accessScannerInitials(scannerInitials);
        if(currentFulfillmentScanRecord.accessScannerInitials(null).length() < 2)
            displayInitialsWarning();
        else
            displayScannerInitials();

        String lastUpdatedString = appConfig.accessString(appConfig.LAST_UPDATED, null, "");
        if(!MoreDateFunctions.getTodayYYMMDD().equals(lastUpdatedString))
            finish();
    }

    @Override
    protected void onPause()
    {
        closeDataAccesses();

        super.onPause();
    }

    @Override
    protected void onStop()
    {
        // unregister the scanner
        unregisterReceiver(receiver);

        if(!scannerLock)
            ScanAPIApplication.getApplicationInstance().forceRelease();

        super.onStop();
    }

    private void launchReviewActivity()
    {
        Intent intent = new Intent(this, FulfillmentScanReviewActivity.class);
        startActivity(intent);
    }

    private void launchAdminActivity()
    {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    private void displayPackLines()
    {
        if(!currentPackRecord.accessPkPackId(null).isEmpty())
        {
            ArrayList<PackLineRecord> packLineRecords = PackLineRecord.buildPackLineListWithCursor(packLineDataAccess.selectByPackId(currentPackRecord.accessPkPackId(null)));
            String packLineString = "";
            for(int count = 0; count < packLineRecords.size(); count++)
            {
                packLineString += "(" + packLineRecords.get(count).accessPricePoint(null) + ") " + packLineRecords.get(count).accessProductName(null) + " - " +
                        packLineRecords.get(count).accessQuantity(null);
                if(count != packLineRecords.size() - 1)
                    packLineString += "\n";
            }
            AlertDialog.Builder packLineDialogBuilder = new AlertDialog.Builder(this);

            packLineDialogBuilder.setTitle("Pack Lines");

            packLineDialogBuilder.setMessage(packLineString);
            AlertDialog packLineDialog = packLineDialogBuilder.create();
            packLineDialog.show();
            TextView msgTxt = (TextView) packLineDialog.findViewById(android.R.id.message);
            msgTxt.setTextSize(16);
        }
    }

    private void openDataAccesses()
    {
        packDataAccess.read();
        packLineDataAccess.read();
        configDataAccess.read();
        lensDataAccess.read();
        packagingDataAccess.read();
        fulfillmentScanHandler.openDBs();
        fulfillmentScanDataAccess.read();
    }

    private void closeDataAccesses()
    {
        packDataAccess.close();
        packLineDataAccess.close();
        configDataAccess.close();
        lensDataAccess.close();
        packagingDataAccess.close();
        fulfillmentScanHandler.closeDBs();
        fulfillmentScanDataAccess.close();
    }

    private void resetScans()
    {
        currentInvoiceRecord.clearRecord();
        currentShipToRecord.clearRecord();
        currentPackRecord.clearRecord();
        readyForNextScan();
        displayTotalFulfillments();
        resetButton.setVisibility(View.GONE);
        if(doubleMode)
        {
            toggleDoubleMode();
            scannedPacks.clear();
            possibleConfigs.clear();
        }
    }

    private void handleRegularScanResponse(int scanResponse)
    {
        switch(scanResponse)
        {
            case -1:
                //Scan inserted
                resetButton.setVisibility(View.GONE);
                readyForNextScan();
                displayTotalFulfillments();
                break;
            case 0:
                //Good scan, display info
                resetButton.setVisibility(View.VISIBLE);
                displayData();
                if(currentShipToRecord.accessIsActive(null).isEmpty() && !currentShipToRecord.accessPkShipToId(null).isEmpty())
                    alertError("Error: Customer not active");
                break;
            case 1:
                //No initials
                alertError("Error: No initials");
                promptInitials();
                break;
            case 2:
                //Bad barcode
                alertError("Error: Bad barcode");
                break;
            case 3:
                //Bad invoice
                alertError("Error: Bad invoice");
                break;
            case 4:
                //Pack not found
                alertError("Error: Pack not found");
                break;
            case 5:
                //Pack needs validation
                alertError("Error: Pack needs validation");
                displayData();
                break;
            case 6:
                //Double Pack scanned
                resetButton.setVisibility(View.VISIBLE);
                displayData();
                toggleDoubleMode();
                break;
            case 7:
                //Wrong pack
                alertError("Error: Wrong pack scanned");
                break;
        }
    }

    private void handleDoubleScanResponse(int scanResponse)
    {
        switch(scanResponse)
        {
            case -1:
                //Scan inserted
                toggleDoubleMode();
                scannedPacks.clear();
                readyForNextScan();
                displayTotalFulfillments();
                break;
            case 0:
                //Good scan, display info
                listScannedPacks();
                listConfigs();
                break;
            case 1:
                //No initials
                alertError("Error: No initials");
                promptInitials();
                break;
            case 2:
                //Bad barcode
                alertError("Error: Not a pack");
                break;
            case 3:
                //Pack not found
                alertError("Error: Pack not found");
                break;
            case 4:
                //Pack needs validation
                alertError("Error: Pack needs validation");
                break;
            case 5:
                //Wrong pack, but display info
                alertError("Error: Pack is not in any config");
                break;
            case 6:
                //Config needs validation
                listScannedPacks();
                listConfigs();
                alertError("Error: Config needs validation");
                break;
        }
    }

    private void readyForNextScan()
    {
        invoiceTextView.setText("Waiting for invoice scan...");
        shipToTextView.setText("");
        packTextView.setText("");
    }

    private void displayTotalFulfillments()
    {
        fullfillNumberTextView.setText(Integer.toString(fulfillmentScanDataAccess.getTotalRows()));
    }

    private void displayData()
    {
        invoiceTextView.setText(currentInvoiceRecord.formatDisplay() + getInvoiceLensString());
        shipToTextView.setText(currentShipToRecord.formatAddress());
        packTextView.setText(getPackPackagingString() + currentPackRecord.formatDisplay());
    }

    private String getInvoiceLensString()
    {
        String lensNames = "";
        String[] lensIds = currentInvoiceRecord.accessFkLensIds(null).split(",");
        for(int count = 0; count < lensIds.length && !currentInvoiceRecord.accessFkLensIds(null).isEmpty() ; count++)
        {
            LensRecord tempLensRecord = new LensRecord();
            String tempLensName;
            if(tempLensRecord.buildWithCursor(lensDataAccess.selectByPk(lensIds[count])))
                tempLensName = tempLensRecord.accessLensName(null);
            else
                tempLensName = "Lens ID: " + lensIds[count];

            if(count == 0)
                lensNames += "\nLens: ";
            if(count < (lensIds.length - 1))
                lensNames += tempLensName + ", ";
            else
                lensNames += tempLensName;
        }
        return lensNames;
    }

    private String getPackPackagingString()
    {
        String packagingString = "";
        PackagingRecord tempPackagingRecord = new PackagingRecord();
        tempPackagingRecord.buildWithCursor(packagingDataAccess.selectByPk(currentPackRecord.accessFkBoxMasnum(null)));
        if(!tempPackagingRecord.accessPackagingName(null).isEmpty())
            packagingString = tempPackagingRecord.accessPackagingName(null) + "\n";
        return packagingString;
    }

    private void alertError(String errorMessage)
    {
        //Set volume to 1/4 max if under
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int minVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)/3;
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        if(minVolume > currentVolume)
            audioManager.setStreamVolume(AudioManager.STREAM_RING, minVolume, AudioManager.FLAG_ALLOW_RINGER_MODES);

        //Ring and vibrate
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringTone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        ringTone.play();
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        long[] vibratePattern = {0, 500, 250, 500};
        vibrator.vibrate(vibratePattern, -1);

        QuickToast.makeLongToast(this, errorMessage);
    }

    private void toggleDoubleMode()
    {
        if(!doubleMode)
        {
            doubleMode = true;
            basicScanLLayout.setVisibility(View.GONE);
            doublePackLLayout.setVisibility(View.VISIBLE);
            setRecommendedConfigListView();
            listAllPossibleConfigs();
            listScannedPacks();
        }
        else
        {
            doubleMode = false;
            basicScanLLayout.setVisibility(View.VISIBLE);
            doublePackLLayout.setVisibility(View.GONE);
        }
    }

    private void setRecommendedConfigListView()
    {
        ArrayList<String> recommendedDoublePackNames = new ArrayList<>();
        ConfigRecord tempConfigRecord = new ConfigRecord();

        if(tempConfigRecord.buildWithCursor(configDataAccess.selectByPk(currentInvoiceRecord.accessFkConfigId(null))))
        {
            ArrayList<String> configPackIds = tempConfigRecord.getConfigPackIdList();

            for(String id : configPackIds)
            {
                PackRecord tempPackRecord = new PackRecord();
                if(!tempPackRecord.buildWithCursor(packDataAccess.selectByPk(id)))
                    recommendedDoublePackNames.add("Pack not found. ID: " + id);
                else
                    recommendedDoublePackNames.add(tempPackRecord.accessPackName(null));
            }
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.row_simple_string, recommendedDoublePackNames);

            recommendedDoublePackListView.setAdapter(adapter);
        }
        else
            alertError("Error: Cannot find Recommended Config.\nPlease see Cory or Rich.");
    }

    private void listScannedPacks()
    {
        ArrayList<String> scannedPackNames = new ArrayList<>();
        for(PackRecord scannedPack : scannedPacks)
        {
            scannedPackNames.add(scannedPack.accessPackName(null));
        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.row_simple_string, scannedPackNames);

        scannedPackListView.setAdapter(adapter);
    }

    private void listAllPossibleConfigs()
    {
        Cursor possibleConfigQuery = configDataAccess.selectByDoublePackId(currentInvoiceRecord.accessFkPackId(null));
        possibleConfigs.clear();
        possibleConfigs.addAll(ConfigRecord.buildConfigListWithCursor(possibleConfigQuery));

        listConfigs();
    }

    private void listConfigs()
    {
        ConfigRecordArrayAdapter adapter = new ConfigRecordArrayAdapter(this, R.layout.row_config, possibleConfigs);
        possibleConfigListView.setAdapter(adapter);
    }

    private void displayInitialsWarning()
    {
        initialsTextView.setText("No initials");
        initialsTextView.setBackgroundColor(Color.RED);
        promptInitials();
    }

    private void displayScannerInitials()
    {
        initialsTextView.setText(currentFulfillmentScanRecord.accessScannerInitials(null));
        initialsTextView.setBackgroundColor(Color.TRANSPARENT);
    }

    private void promptInitials()
    {
        AlertDialog.Builder initialDialog = new AlertDialog.Builder(this);

        initialDialog.setTitle("Scanner Initials Entry");
        initialDialog.setMessage("Enter initials");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setGravity(Gravity.CENTER);
        input.setText(currentFulfillmentScanRecord.accessScannerInitials(null));
        initialDialog.setView(input);

        initialDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                String scannerInitials = input.getText().toString();
                currentFulfillmentScanRecord.accessScannerInitials(scannerInitials);
                if(currentFulfillmentScanRecord.accessScannerInitials(null).length() < 2)
                {
                    displayInitialsWarning();
                }
                else
                {
                    appConfig.accessString(DroidConfigManager.CURRENT_SCANNER_INITIALS, currentFulfillmentScanRecord.accessScannerInitials(null), "");
                    displayScannerInitials();
                }
            }
        });
        initialDialog.show();
    }

    private class ConfigRecordArrayAdapter extends ArrayAdapter<ConfigRecord>
    {
        private List<ConfigRecord> list;
        int rowResourceId;

        public ConfigRecordArrayAdapter(Context context, int layoutResourceId, List<ConfigRecord> objects)
        {
            super(context, layoutResourceId, objects);
            list = objects;
            rowResourceId = layoutResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            convertView = inflater.inflate(rowResourceId, parent, false);

            TextView configNumTextView = (TextView)convertView.findViewById(R.id.ConfigNum);
            LinearLayout configPackLinearLayout = (LinearLayout)convertView.findViewById(R.id.ConfigPackList);

            ConfigRecord tempConfigRecord = list.get(position);
            configNumTextView.setText(tempConfigRecord.accessConfigNum(null));
            ArrayList<String> configPackIds = tempConfigRecord.getConfigPackIdList();
            String packName;

            for(String id : configPackIds)
            {
                PackRecord tempPackRecord = new PackRecord();
                if(!tempPackRecord.buildWithCursor(packDataAccess.selectByPk(id)))
                    packName = "Pack not found. ID: " + id;
                else
                    packName = tempPackRecord.accessPackName(null);
                TextView packNameTextView = (TextView)inflater.inflate(R.layout.row_simple_string, null);
                packNameTextView.setText(packName);
                configPackLinearLayout.addView(packNameTextView);
            }
            if(tempConfigRecord.accessIsValid(null).isEmpty())
                convertView.setBackgroundColor(Color.RED);
            return convertView;
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver()
    {
        private static final String TAG = "BroadcastReceiver";

        @Override
        public void onReceive(Context c, Intent intent)
        {
            String message = String.format("in onReceive");
            Log.d(TAG, message);

            if (intent.getAction().equalsIgnoreCase(ScanAPIApplication.NOTIFY_DECODED_DATA))
            {
                String scanData = new String(intent.getCharArrayExtra(ScanAPIApplication.EXTRA_DECODEDDATA));

                //Scanned stuff
                if(!doubleMode)
                    handleRegularScanResponse(fulfillmentScanHandler.handleRegularScan(scanData));
                else
                    handleDoubleScanResponse(fulfillmentScanHandler.handleDoubleScan(scanData, scannedPacks, possibleConfigs));
            }
            else if (intent.getAction().equalsIgnoreCase(ScanAPIApplication.NOTIFY_SCANNER_ARRIVAL))
            {
                QuickToast.makeToast(c, intent.getStringExtra(ScanAPIApplication.EXTRA_DEVICENAME) + " Connected");
            }
            else if (intent.getAction().equalsIgnoreCase(ScanAPIApplication.NOTIFY_SCANPI_INITIALIZED))
            {
            }
            else if (intent.getAction().equalsIgnoreCase(ScanAPIApplication.NOTIFY_CLOSE_ACTIVITY))
            {
            }
            else if (intent.getAction().equalsIgnoreCase(ScanAPIApplication.NOTIFY_ERROR_MESSAGE))
            {
                QuickToast.makeLongToast(c, intent.getStringExtra(ScanAPIApplication.EXTRA_ERROR_MESSAGE));
            }
        }// end on Recieve
    };

    private void regBroadCastReceivers()
    {
        IntentFilter filter;
        filter = new IntentFilter(ScanAPIApplication.NOTIFY_SCANPI_INITIALIZED);
        registerReceiver(this.receiver, filter);

        filter = new IntentFilter(ScanAPIApplication.NOTIFY_SCANNER_ARRIVAL);
        registerReceiver(this.receiver, filter);

        filter = new IntentFilter(ScanAPIApplication.NOTIFY_SCANNER_REMOVAL);
        registerReceiver(this.receiver, filter);

        filter = new IntentFilter(ScanAPIApplication.NOTIFY_DECODED_DATA);
        registerReceiver(this.receiver, filter);

        filter = new IntentFilter(ScanAPIApplication.NOTIFY_ERROR_MESSAGE);
        registerReceiver(this.receiver, filter);

        filter = new IntentFilter(ScanAPIApplication.NOTIFY_CLOSE_ACTIVITY);
        registerReceiver(this.receiver, filter);

        filter = new IntentFilter(ScanAPIApplication.SET_SOUND_CONFIG_COMPLETE);
        registerReceiver(this.receiver, filter);

        filter = new IntentFilter(ScanAPIApplication.GET_SOUND_CONFIG_COMPLETE);
        registerReceiver(this.receiver, filter);

        // increasing the Application View count from 0 to 1 will
        // cause the application to open and initialize ScanAPI
        ScanAPIApplication.getApplicationInstance().increaseViewCount();
    }
}
