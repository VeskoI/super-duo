package barqsoft.footballscores.widget;

import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

import static barqsoft.footballscores.scoresAdapter.COL_AWAY;
import static barqsoft.footballscores.scoresAdapter.COL_AWAY_GOALS;
import static barqsoft.footballscores.scoresAdapter.COL_HOME;
import static barqsoft.footballscores.scoresAdapter.COL_HOME_GOALS;
import static barqsoft.footballscores.scoresAdapter.COL_ID;
import static barqsoft.footballscores.scoresAdapter.COL_MATCHTIME;

public class CollectionWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor cursor = null;

            @Override
            public void onCreate() {
                // Nothing to do here
            }

            @Override
            public void onDataSetChanged() {
                if (cursor != null) {
                    cursor.close();
                }

                cursor = getContentResolver()
                        .query(DatabaseContract.BASE_CONTENT_URI, null, null, null, null);
            }

            @Override
            public void onDestroy() {
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }

            @Override
            public int getCount() {
                return cursor != null ? cursor.getCount() : 0;
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION || cursor == null
                        || !cursor.moveToPosition(position)) {
                    // No valid cursor :(
                    return null;
                }

                RemoteViews rv = new RemoteViews(getPackageName(), R.layout.scores_list_item);

                String homeTeam = cursor.getString(COL_HOME);
                rv.setTextViewText(R.id.home_name, homeTeam);
                rv.setContentDescription(R.id.home_name, getString(R.string.cd_home_team_name, homeTeam));
                rv.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(getApplicationContext(), homeTeam));
                rv.setContentDescription(R.id.home_crest, getApplicationContext().getString(R.string.cd_team_crest, homeTeam));

                String awayTeam = cursor.getString(COL_AWAY);
                rv.setTextViewText(R.id.away_name, awayTeam);
                rv.setContentDescription(R.id.away_name, getString(R.string.cd_away_team_name, awayTeam));
                rv.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(getApplicationContext(), awayTeam));
                rv.setContentDescription(R.id.away_crest, getApplicationContext().getString(R.string.cd_team_crest, awayTeam));

                String matchTime = cursor.getString(COL_MATCHTIME);
                rv.setTextViewText(R.id.data_textview, matchTime);
                rv.setContentDescription(R.id.data_textview, getString(R.string.cd_match_time, matchTime));

                String score = Utilies.getScoreText(getApplicationContext(), cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS));
                rv.setTextViewText(R.id.score_textview, score);
                rv.setContentDescription(R.id.score_textview, getString(R.string.cd_score, score));

                return rv;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.scores_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (cursor != null && cursor.moveToPosition(position)) {
                    return cursor.getLong(COL_ID);
                }

                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
