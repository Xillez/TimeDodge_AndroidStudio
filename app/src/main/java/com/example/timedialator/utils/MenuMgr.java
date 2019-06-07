package com.example.timedialator.utils;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MenuMgr
{
    AppCompatActivity activity;

    public boolean createMenu(AppCompatActivity activity, Menu menu)
    {
        this.activity = activity;

        // Inflate the menu; this adds items to the action bar if it is present.
        //activity.getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    public boolean handleMenuEvent(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            /*case R.id.action_new:
            {
                Log.i(Logging.LOG_INFO_TAG, "New clicked!");
                return true;
            }

            case R.id.action_save:
            {
                Log.i(Logging.LOG_INFO_TAG, "Save clicked!");
                return true;
            }*/
        }

        // Couldn't handle event, indicate relaying to super class.
        return true;
    }

    /*public void closeMenu(int featureId, Menu menu)
    {
        //
    }*/
}
