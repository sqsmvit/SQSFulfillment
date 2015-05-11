package com.sqsmv.sqsfulfillment;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.ConfigRecord;
import com.sqsmv.sqsfulfillment.database.FulfillmentScanDataAccess;
import com.sqsmv.sqsfulfillment.database.FulfillmentScanRecord;
import com.sqsmv.sqsfulfillment.database.InvoiceDataAccess;
import com.sqsmv.sqsfulfillment.database.InvoiceRecord;
import com.sqsmv.sqsfulfillment.database.PackDataAccess;
import com.sqsmv.sqsfulfillment.database.PackRecord;
import com.sqsmv.sqsfulfillment.database.ShipToDataAccess;
import com.sqsmv.sqsfulfillment.database.ShipToRecord;

import org.cory.libraries.MoreDateFunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FulfillmentScanHandler
{
    private static final Pattern invoicePattern = Pattern.compile("^[vV](\\d{7,8})$");
    private static final Pattern packPattern = Pattern.compile("^[kK](\\d{1,5})$");

    private InvoiceDataAccess invoiceDataAccess;
    private ShipToDataAccess shipToDataAccess;
    private PackDataAccess packDataAccess;
    private FulfillmentScanDataAccess fulfillmentScanDataAccess;

    private InvoiceRecord currentInvoiceRecord;
    private ShipToRecord currentShipToRecord;
    private PackRecord currentPackRecord;
    private FulfillmentScanRecord currentFulfillmentScanRecord;

    public FulfillmentScanHandler(Context activityContext, InvoiceRecord invoiceRecord, ShipToRecord shipToRecord, PackRecord packRecord, FulfillmentScanRecord fulfillmentScanRecord)
    {
        invoiceDataAccess = new InvoiceDataAccess(activityContext);
        shipToDataAccess = new ShipToDataAccess(activityContext);
        packDataAccess = new PackDataAccess(activityContext);
        fulfillmentScanDataAccess = new FulfillmentScanDataAccess(activityContext);

        currentInvoiceRecord = invoiceRecord;
        currentShipToRecord = shipToRecord;
        currentPackRecord = packRecord;
        currentFulfillmentScanRecord = fulfillmentScanRecord;
    }

    public void openDBs()
    {
        invoiceDataAccess.open();
        shipToDataAccess.read();
        packDataAccess.read();
        fulfillmentScanDataAccess.open();
    }

    public void closeDBs()
    {
        invoiceDataAccess.close();
        shipToDataAccess.close();
        packDataAccess.close();
        fulfillmentScanDataAccess.close();
    }

    public int handleRegularScan(String scanData)
    {
        int response = 0;

        if(!currentFulfillmentScanRecord.accessScannerInitials(null).isEmpty() && currentFulfillmentScanRecord.accessScannerInitials(null).length() >= 2)
        {
            Matcher invoiceMatcher = invoicePattern.matcher(scanData);
            Matcher packMatcher = packPattern.matcher(scanData);

            if(invoiceMatcher.find())
            {
                String invoiceId = invoiceMatcher.group(1);
                if(currentInvoiceRecord.buildWithCursor(invoiceDataAccess.selectByPk(invoiceId)))
                {
                    if(buildInvoiceData())
                    {
                        if(currentPackRecord.accessIsValid(null).isEmpty())
                        {
                            currentInvoiceRecord.accessFkPackId("");
                            response = 5; //Pack needs validation
                        }
                        else if(currentPackRecord.accessIsDouble(null).equals("1"))
                            response = 6; //Double pack
                    }
                    else
                        response = 4; //Pack not found
                }
                else
                {
                    currentInvoiceRecord.clearRecord();
                    response = 3; //Bad invoice
                }
            }
            else if(packMatcher.find())
            {
                String packId = packMatcher.group(1);
                if(currentInvoiceRecord.accessFkPackId(null).isEmpty())
                {
                    //Invoice not scanned
                    if(!currentPackRecord.buildWithCursor(packDataAccess.selectByPk(packId)))
                    {
                        currentInvoiceRecord.clearRecord();
                        currentShipToRecord.clearRecord();
                        currentPackRecord.clearRecord();
                        currentPackRecord.accessPackName("Pack not found. ID: " + packId);
                    }
                }
                else
                {
                    //Invoice is scanned
                    if(packId.equals(currentInvoiceRecord.accessFkPackId(null)))
                    {
                        insertRegularScan();
                        response = -1;
                    }
                    else
                        response = 7; //Wrong pack
                }
            }
            else
                response = 2; //Bad barcode
        }
        else
            response = 1; //No initials

        return response;
    }

    public int handleDoubleScan(String scanData, ArrayList<PackRecord> scannedPacks, ArrayList<ConfigRecord> possibleConfigs)
    {
        int response = 0;

        if(!currentFulfillmentScanRecord.accessScannerInitials(null).isEmpty() && currentFulfillmentScanRecord.accessScannerInitials(null).length() >= 2)
        {
            Matcher packMatcher = packPattern.matcher(scanData);

            if(packMatcher.find())
            {
                String packId = packMatcher.group(1);

                PackRecord tempPackRecord = new PackRecord();
                if(tempPackRecord.buildWithCursor(packDataAccess.selectByPk(packId)))
                {
                    if(!tempPackRecord.accessIsValid(null).isEmpty())
                    {
                        ConfigRecord fulfilledConfig = null;
                        ArrayList<ConfigRecord> tempConfigList = new ArrayList<>(possibleConfigs);
                        ArrayList<String> scannedPackIds = parsePackIdList(scannedPacks);
                        scannedPackIds.add(packId);
                        Collections.sort(scannedPackIds);
                        for(ConfigRecord config : possibleConfigs)
                        {
                            if(!config.hasConfigPackId(packId))
                                tempConfigList.remove(config);
                            else if(config.matchesConfigPackIds(scannedPackIds))
                                fulfilledConfig = config;

                        }
                        if(fulfilledConfig != null)
                        {
                            if(!fulfilledConfig.accessIsValid(null).isEmpty())
                            {
                                currentFulfillmentScanRecord.accessFkConfigId(fulfilledConfig.accessPkConfigId(null));
                                insertRegularScan();
                                response = -1;
                            }
                            else
                            {
                                response = 6; //Invalid config
                                scannedPacks.add(tempPackRecord);
                                possibleConfigs.clear();
                                possibleConfigs.add(fulfilledConfig);
                            }
                        }
                        else if(tempConfigList.size() >= 1)
                        {
                            scannedPacks.add(tempPackRecord);

                            possibleConfigs.clear();
                            possibleConfigs.addAll(tempConfigList);
                        }
                        else
                            response = 5; //Wrong pack
                    }
                    else
                        response = 4; //Invalid pack
                }
                else
                    response = 3; //Pack not found
            }
            else
                response = 2; //Bad barcode
        }
        else
            response = 1; //No initials

        return response;
    }

    public ArrayList<String> parsePackIdList(ArrayList<PackRecord> packList)
    {
        ArrayList<String> packIDList = new ArrayList<>();
        for(PackRecord pack : packList)
            packIDList.add(pack.accessPkPackId(null));
        return packIDList;
    }

    private boolean buildInvoiceData()
    {
        boolean success = true;
        currentShipToRecord.clearRecord();
        currentPackRecord.clearRecord();
        if(!currentShipToRecord.buildWithCursor(shipToDataAccess.selectByPk(currentInvoiceRecord.accessFkShipToId(null))))
            currentShipToRecord.accessShipName("Shipping Address not found. ID: " + currentInvoiceRecord.accessFkShipToId(null));
        if(!currentPackRecord.buildWithCursor(packDataAccess.selectByPk(currentInvoiceRecord.accessFkPackId(null))))
        {
            success = false;
            currentPackRecord.clearRecord();
            currentShipToRecord.clearRecord();
            currentInvoiceRecord.clearRecord();
        }
        return success;
    }

    private void insertRegularScan()
    {
        currentFulfillmentScanRecord.access_Id("null");
        currentFulfillmentScanRecord.accessFkInvoiceId(currentInvoiceRecord.accessPkInvoiceId(null));
        currentFulfillmentScanRecord.accessScanDate(MoreDateFunctions.getNowFMTimestamp());
        currentFulfillmentScanRecord.accessScannedPackName(currentPackRecord.accessPackName(null));
        fulfillmentScanDataAccess.insert(currentFulfillmentScanRecord);
        invoiceDataAccess.deleteByPk(currentInvoiceRecord.accessPkInvoiceId(null));
        clearForNextScan();
    }

    private void clearForNextScan()
    {
        currentInvoiceRecord.clearRecord();
        currentShipToRecord.clearRecord();
        currentPackRecord.clearRecord();
        currentFulfillmentScanRecord.clearForNextScan();
    }
}
