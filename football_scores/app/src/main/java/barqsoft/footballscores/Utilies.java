package barqsoft.footballscores;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;
    public static String getLeague(Context context, int league_num)
    {
        switch (league_num)
        {
            case SERIE_A : return context.getString(R.string.seriaa);
            case PREMIER_LEGAUE : return context.getString(R.string.premierleague);
            case CHAMPIONS_LEAGUE : return context.getString(R.string.champions_league);
            case PRIMERA_DIVISION : return context.getString(R.string.primeradivison);
            case BUNDESLIGA : return context.getString(R.string.bundesliga);
            default: return context.getString(R.string.league_unknown_report);
        }
    }
    public static String getMatchDay(Context context, int match_day, int league_num)
    {
        if(league_num == CHAMPIONS_LEAGUE)
        {
            if (match_day <= 6)
            {
                return context.getString(R.string.group_stages_day, match_day);
            }
            else if(match_day == 7 || match_day == 8)
            {
                return context.getString(R.string.first_knockout_round);
            }
            else if(match_day == 9 || match_day == 10)
            {
                return context.getString(R.string.quarter_final);
            }
            else if(match_day == 11 || match_day == 12)
            {
                return context.getString(R.string.semi_final);
            }
            else
            {
                return context.getString(R.string.final_text);
            }
        }
        else
        {
            return context.getString(R.string.matchday_n, match_day);
        }
    }

    public static int getTeamCrestByTeamName (Context context, String teamName)
    {
        if (TextUtils.isEmpty(teamName)) {
            return R.drawable.no_icon;
        }
        else if (teamName.equals(context.getString(R.string.team_arsenal_london))) {
            return R.drawable.arsenal;
        }
        else if (teamName.equals(context.getString(R.string.team_manchester_united))) {
            return R.drawable.manchester_united;
        }
        else if (teamName.equals(context.getString(R.string.team_swansea_city))) {
            return R.drawable.swansea_city_afc;
        }
        else if (teamName.equals(context.getString(R.string.team_leichester_city))) {
            return R.drawable.leicester_city_fc_hd_logo;
        }
        else if (teamName.equals(context.getString(R.string.team_everton))) {
            return R.drawable.everton_fc_logo1;
        }
        else if (teamName.equals(context.getString(R.string.team_westham))) {
            return R.drawable.west_ham;
        }
        else if (teamName.equals(context.getString(R.string.team_tottenham))) {
            return R.drawable.tottenham_hotspur;
        }
        else if (teamName.equals(context.getString(R.string.team_west_bromwich))) {
            return R.drawable.west_bromwich_albion_hd_logo;
        }
        else if (teamName.equals(context.getString(R.string.team_sunderland))) {
            return R.drawable.sunderland;
        }
        else if (teamName.equals(context.getString(R.string.team_stoke_city))) {
            return R.drawable.stoke_city;
        }
        else {
            return R.drawable.no_icon;
        }
    }

    public static String getScoreText(Context context, int homeGoals, int awayGoals) {
        return getScoreText(context, null, homeGoals, awayGoals);
    }

    public static String getScoreText(Context context, TextView scoreView, int homeGoals, int awayGoals) {
        if(homeGoals < 0 || awayGoals < 0) {
            return context.getString(R.string.empty_score);

        }

        boolean isRTL = scoreView != null ? isRTL(scoreView) : isRTL();
        if (isRTL) {
            return context.getString(R.string.score_text, awayGoals, homeGoals);
        } else {
            // standard LTR
            return context.getString(R.string.score_text, homeGoals, awayGoals);
        }
    }

    public static boolean isRTL() {
        return isRTL(Locale.getDefault());
    }

    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    /**
     * Best approach to finding out if it's RTL. Also works when
     * forcing RTL in Developer options.
     */
    public static boolean isRTL(View view) {
        return ViewCompat.getLayoutDirection(view) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }
}
