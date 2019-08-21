package com.bulletpointgames.timedodge.utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ToolbarMgr
{
    public boolean makeToolBar(AppCompatActivity activity, int view, boolean backButton, Runnable onBackClicked)
    {
        // Add toolbar and remove the title
        Toolbar toolbar = activity.findViewById(view);
        activity.setSupportActionBar(toolbar);
        if (toolbar != null)
        {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (backButton)
            {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
                toolbar.setNavigationOnClickListener(v -> onBackClicked.run());
            }
            return true;
        }
        else
            return false;
    }
}
