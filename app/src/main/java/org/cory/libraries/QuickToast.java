//Library of quick Toast message calls

package org.cory.libraries;

import android.content.Context;
import android.widget.Toast;

public final class QuickToast
{
    //Displays a short toast message
    public static void makeToast(Context callingContext, String message)
    {
        Toast.makeText(callingContext, message, Toast.LENGTH_SHORT).show();
    }

    //Displays a long toast message
    public static void makeLongToast(Context callingContext, String message)
    {
        Toast.makeText(callingContext, message, Toast.LENGTH_LONG).show();
    }
}
