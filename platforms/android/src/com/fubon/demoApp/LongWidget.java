package com.fubon.demoApp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class LongWidget extends AppWidgetProvider {

    private static final String MyOnClick = "longClickTag";

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, MainActivity.thisApp.getClass());
        intent.setAction(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("param", "YoMan");
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.long_widget);

        views.setOnClickPendingIntent(R.id.longWidget_button, getPendingSelfIntent(context, MyOnClick));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

