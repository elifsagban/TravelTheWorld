package com.elif.traveltheworld;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.junit.Test;

import static androidx.core.content.ContextCompat.getSystemService;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public boolean checkOnlineState() {
        boolean flag=false;
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        flag=true;

                    }

        }
        if(flag==true)
        {
            Log.e("TAG","Internet Is Connected");
        }
        else
        {
            Log.e("TAG","Internet Is Not Connected");
        }
        return flag;
    }

    private Context getApplicationContext() {

        return null;
    }


}