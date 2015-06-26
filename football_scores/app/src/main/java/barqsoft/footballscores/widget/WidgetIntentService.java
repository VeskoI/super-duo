package barqsoft.footballscores.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.scoresAdapter;

public class WidgetIntentService extends IntentService {

    private static final String TAG = WidgetIntentService.class.getSimpleName();

    public WidgetIntentService() {
        super("WidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ScoresWidgetProvider.class));

        Cursor c = getScoreData();
        if (c == null || !c.moveToFirst()) {
            // TODO notify user for the error?
            return;
        }

        String homeTeam = c.getString(scoresAdapter.COL_HOME);
        String awayTeam = c.getString(scoresAdapter.COL_AWAY);
        // Note that we should account for LTR or RTL
        String result = Utilies.getScoreText(
                c.getInt(scoresAdapter.COL_HOME_GOALS),
                c.getInt(scoresAdapter.COL_AWAY_GOALS));

        for (int appWidgetId : appWidgetIds) {
            Log.d(TAG, "updating widget: " + appWidgetId);
            RemoteViews remoteViews = initRemoteViews(homeTeam, awayTeam, result);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    private RemoteViews initRemoteViews(String homeTeam, String awayTeam, String result) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);

        remoteViews.setTextViewText(R.id.widget_team_home, homeTeam);
        remoteViews.setTextViewCompoundDrawables(
                R.id.widget_team_home,
                0,
                Utilies.getTeamCrestByTeamName(homeTeam),
                0,
                0);

        remoteViews.setTextViewText(R.id.widget_team_away, awayTeam);
        remoteViews.setTextViewCompoundDrawables(
                R.id.widget_team_away,
                0,
                Utilies.getTeamCrestByTeamName(awayTeam),
                0,
                0);

        remoteViews.setTextViewText(R.id.widget_score, result);

        Intent launchIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);

        return remoteViews;
    }

    private Cursor getScoreData() {
        Date now = new Date(System.currentTimeMillis());
        String today = DatabaseContract.DATE_FORMAT.format(now);

        return getContentResolver().query(
                DatabaseContract.scores_table.buildScoreWithDate(),
                null, // projection
                null, // selection
                new String[]{today}, // selection args - date will be read here
                null);
    }
}
