package co.ke.siundu254.betshwari.helper;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rahmak on 3/3/18
 */

public class BetShwari extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        MobileAds.initialize(this, getString(co.ke.siundu254.betshwari.R.string.ad_unit_id));

    }
}
