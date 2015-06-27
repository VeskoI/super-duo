package barqsoft.footballscores;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by yehya khaled on 2/26/2015.
 */
public class scoresAdapter extends CursorAdapter {
    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_DATE = 1;
    public static final int COL_LEAGUE = 5;
    public static final int COL_MATCHDAY = 9;
    public static final int COL_ID = 8;
    public static final int COL_MATCHTIME = 2;
    public double detail_match_id = 0;

    public scoresAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View mItem = LayoutInflater.from(context).inflate(R.layout.scores_list_item, parent, false);
        ViewHolder mHolder = new ViewHolder(mItem);
        mItem.setTag(mHolder);
        //Log.v(FetchScoreTask.LOG_TAG,"new View inflated");
        return mItem;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final ViewHolder mHolder = (ViewHolder) view.getTag();


        String homeTeam = cursor.getString(COL_HOME);
        mHolder.home_name.setText(homeTeam);
        mHolder.home_name.setContentDescription(context.getString(R.string.cd_home_team_name, homeTeam));
        mHolder.home_crest.setImageResource(Utilies.getTeamCrestByTeamName(context, homeTeam));
        mHolder.home_crest.setContentDescription(context.getString(R.string.cd_team_crest, homeTeam));

        String awayTeam = cursor.getString(COL_AWAY);
        mHolder.away_name.setText(awayTeam);
        mHolder.away_name.setContentDescription(context.getString(R.string.cd_away_team_name, awayTeam));
        mHolder.away_crest.setImageResource(Utilies.getTeamCrestByTeamName(context, awayTeam));
        mHolder.away_crest.setContentDescription(context.getString(R.string.cd_team_crest, awayTeam));

        String matchTime = cursor.getString(COL_MATCHTIME);
        mHolder.date.setText(matchTime);
        mHolder.date.setContentDescription(context.getString(R.string.cd_match_time, matchTime));

        String score = Utilies.getScoreText(context, mHolder.score, cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS));
        mHolder.score.setText(score);
        mHolder.score.setContentDescription(context.getString(R.string.cd_score, score));
        mHolder.match_id = cursor.getDouble(COL_ID);


        //Log.v(FetchScoreTask.LOG_TAG,mHolder.home_name.getText() + " Vs. " + mHolder.away_name.getText() +" id " + String.valueOf(mHolder.match_id));
        //Log.v(FetchScoreTask.LOG_TAG,String.valueOf(detail_match_id));
        LayoutInflater vi = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.detail_fragment, null);
        ViewGroup container = (ViewGroup) view.findViewById(R.id.details_fragment_container);
        if (mHolder.match_id == detail_match_id) {
            //Log.v(FetchScoreTask.LOG_TAG,"will insert extraView");

            container.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT));
            TextView match_day = (TextView) v.findViewById(R.id.matchday_textview);
            String matchDay = Utilies.getMatchDay(context, cursor.getInt(COL_MATCHDAY), cursor.getInt(COL_LEAGUE));
            match_day.setText(matchDay);
            match_day.setContentDescription(context.getString(R.string.cd_match_day, matchDay));

            TextView league = (TextView) v.findViewById(R.id.league_textview);
            String leagueText = Utilies.getLeague(context, cursor.getInt(COL_LEAGUE));
            league.setText(leagueText);
            league.setContentDescription(context.getString(R.string.cd_league, leagueText));

            Button share_button = (Button) v.findViewById(R.id.share_button);
            share_button.setContentDescription(context.getString(R.string.cd_share_button));
            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String shareText = context.getString(R.string.share_text,
                            mHolder.home_name.getText(),
                            mHolder.score.getText(),
                            mHolder.away_name.getText());

                    //add Share Action
                    context.startActivity(createShareForecastIntent(shareText));
                }
            });
        } else {
            container.removeAllViews();
        }

    }

    public Intent createShareForecastIntent(String shareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        return shareIntent;
    }

}
