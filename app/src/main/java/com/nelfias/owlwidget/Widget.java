package com.nelfias.owlwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.RemoteViews;

import java.util.Objects;
import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */

public class Widget extends AppWidgetProvider {

    private static String WIDGET_SYNC = "WIDGET_SYNC_OWL";

    private static MediaPlayer owlSoundPlayer;

    private static int[] soundpool = {R.raw.owl_whoo, R.raw.owl2, R.raw.owl3, R.raw.owl4, R.raw.owl5};

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, Widget.class);
        intent.setAction(WIDGET_SYNC);
        intent.putExtra("appWidgetId", appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.btn_owl, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.requireNonNull(intent.getAction()).equals(WIDGET_SYNC)) {
            int rnd = new Random().nextInt(soundpool.length);
            owlSoundPlayer = MediaPlayer.create(context, soundpool[rnd]);
            //owlSoundPlayer.stop();
            owlSoundPlayer.start();
        }
        super.onReceive(context, intent);
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
        owlSoundPlayer = MediaPlayer.create(context, R.raw.owl_whoo);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

