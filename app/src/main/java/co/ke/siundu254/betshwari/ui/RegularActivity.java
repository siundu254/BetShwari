package co.ke.siundu254.betshwari.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class RegularActivity extends AppCompatActivity {
    String pushId, pushTips, pushTitle,pushStatus;
    TextView mTitle, mStatus, mTips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(co.ke.siundu254.betshwari.R.layout.activity_regular);

        AdView mAdView = findViewById(co.ke.siundu254.betshwari.R.id.adView_Regular);
        AdRequest request = new AdRequest.Builder().build();
        mAdView.loadAd(request);

        pushId = getIntent().getStringExtra("pushId");
        pushTitle = getIntent().getStringExtra("pushTitle");
        pushStatus = getIntent().getStringExtra("pushStatus");
        pushTips = getIntent().getStringExtra("pushTips");

        mTitle = findViewById(co.ke.siundu254.betshwari.R.id.regularTipsTitle);
        mStatus = findViewById(co.ke.siundu254.betshwari.R.id.regularTipsStatus);
        mTips = findViewById(co.ke.siundu254.betshwari.R.id.regularTipsList);

        mTitle.setText(pushTitle);
        mStatus.setText(pushStatus);
        mTips.setText(pushTips);

    }

}
