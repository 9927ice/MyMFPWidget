package com.fubon.demoApp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class FubonWidget extends AppWidgetProvider {


    private static final String MyOnClick = "myClickTag";

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fubon_widget);

        views.setOnClickPendingIntent(R.id.appwidget_button, getPendingSelfIntent(context, MyOnClick));
        views.setTextViewText(R.id.appwidget_text, "YoMan");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.v("YOOOOOOO", "Upate Trigger!!!!");

        MainActivity.containerAppMethod();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fubon_widget);
        views.setOnClickPendingIntent(R.id.appwidget_button, getPendingSelfIntent(context, MyOnClick) );


        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        Log.v("YOOOOOOO", "onEnabled Trigger!!!!");
    }

    @Override
    public void onDisabled(Context context) {
        Log.v("YOOOOOOO", "onDisabled Trigger!!!!");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);//add this line
        Log.v("MyOnClick", "onReceiveonReceive Trigger!!!!");
        if (MyOnClick.equals(intent.getAction())){
            MainActivity.containerAppMethod();
        }
    };
}



