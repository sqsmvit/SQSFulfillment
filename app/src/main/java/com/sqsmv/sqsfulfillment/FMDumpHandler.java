package com.sqsmv.sqsfulfillment;

import android.os.Environment;
import android.util.Log;

import com.sqsmv.sqsfulfillment.database.XMLDataAccess;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class FMDumpHandler extends Thread
{
	private static final String TAG = "FMDumpHandler";

    XMLDataAccess xmlDataAccess;

    FMDumpHandler(XMLDataAccess fileDataAccess)
    {
        xmlDataAccess = fileDataAccess;
    }

    @Override
    public void run()
    {
        Log.d(TAG, "Running FMDumpHandler");
        File xmlFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + xmlDataAccess.getXMLFileName());
        try
        {
            InputStreamReader fileInput = new InputStreamReader(new FileInputStream(xmlFile));
            try
            {
                XmlPullParser xpp = createXmlPullParser(fileInput);
                buildTableFromXML(xpp);
                xmlFile.delete();
            }
            catch(XmlPullParserException | IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                fileInput.close();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    private XmlPullParser createXmlPullParser(InputStreamReader fileInput) throws XmlPullParserException
    {
        XmlPullParser xpp;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);

        xpp = factory.newPullParser();

        xpp.setInput(fileInput);

        return xpp;
    }

	private void buildTableFromXML(XmlPullParser xpp) throws XmlPullParserException, IOException
    {
        xmlDataAccess.open();
        HashMap<String, String> dbItems = xmlDataAccess.getSha();

        int eventType = xpp.getEventType();

        ArrayList<ArrayList<String>> batch = new ArrayList<>();
        String shaVal = "";
        while(eventType != XmlPullParser.END_DOCUMENT)
        {
            //record
            eventType = xpp.next();
            switch(eventType)
            {
                case XmlPullParser.START_TAG:
                    if(xpp.getName().equals("record"))
                    {
                        int recordEvent = xpp.getEventType();
                        ArrayList<String> objVars = new ArrayList<>();
                        while(recordEvent != XmlPullParser.END_TAG && xpp.getName().equals("record"))
                        {
                            for(String tag : xmlDataAccess.getTableColumns())
                            {
                                //1st field
                                eventType = xpp.nextTag();
                                if(eventType == XmlPullParser.END_TAG)
                                {
                                    xpp.nextTag();
                                }

                                String value;
                                if(xpp.getName().equals(tag))
                                {
                                    value = xpp.nextText();
                                    if(tag.toUpperCase().equals("SHA"))
                                    {
                                        shaVal = value;
                                    }
                                }
                                else
                                {
                                    return;
                                }
                                objVars.add(value);
                            }//end for

                            if(canInsert(objVars, shaVal, dbItems))
                            {
                                batch.add(objVars);
                            }

                            if(batch.size() == 500)
                            {
                                insertBatch(batch);
                            }
                        }//end while
                    }//end if
            }//end switch
        }// end while
        if(!batch.isEmpty())
        {
            insertBatch(batch);
        }
        xmlDataAccess.close();
	}

    private boolean canInsert(ArrayList<String> objVars, String shaVal, HashMap<String, String> dbItems)
    {
        boolean canInsert = false;
        if(!dbItems.containsKey(objVars.get(0)))
        {
            canInsert = true;
        }
        else if(!dbItems.get(objVars.get(0)).equals(shaVal))
        {
            canInsert = true;
        }

        return canInsert;
    }

    private void insertBatch(ArrayList<ArrayList<String>> batch)
    {
        xmlDataAccess.insertBatch(batch);
        System.out.println(xmlDataAccess.getTableName() + ": Inserted " + Integer.toString(batch.size()) + " records");
        batch.clear();
    }
}