package com.fubon.demoApp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.util.Log;
import android.widget.RemoteViews;

import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;

import static java.security.AccessController.getContext;

public class MyInvokeListener implements WLResponseListener {

    MainActivity thisApp;

    MyInvokeListener(MainActivity myApp){
        thisApp = myApp;
    }

    public void onSuccess(WLResponse response) {
        String responseText = response.getResponseText();
        Log.v("Adapter Successfuly", responseText);

        RemoteViews views = new RemoteViews(thisApp.getPackageName(), R.layout.fubon_widget);
        views.setTextViewText(R.id.appwidget_text, responseText);

        AppWidgetManager.getInstance( thisApp ).updateAppWidget( new ComponentName(thisApp, FubonWidget.class), views );
    }

    public void onFailure(WLFailResponse response) {
        String responseText = response.getResponseText();
        Log.v("Adapter Failed", responseText);
    }

}
