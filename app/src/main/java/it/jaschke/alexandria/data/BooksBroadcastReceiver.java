package it.jaschke.alexandria.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import it.jaschke.alexandria.R;

public class BooksBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_BOOK_ADDED = "it.jaschke.alexandria.BOOK_ADDED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            switch (intent.getAction()) {
                case ACTION_BOOK_ADDED:
                    Toast.makeText(context, context.getString(R.string.book_added), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
