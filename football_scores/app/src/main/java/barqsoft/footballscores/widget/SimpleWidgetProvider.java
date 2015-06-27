package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class SimpleWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_DATA_UPDATE = "barqsoft.footballscores.ACTION_DATA_UPDATE";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        context.startService(new Intent(context, WidgetIntentService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_DATA_UPDATE.equals(intent.getAction())) {
            context.startService(new Intent(context, WidgetIntentService.class));
            return;
        }
        super.onReceive(context, intent);
    }
}
