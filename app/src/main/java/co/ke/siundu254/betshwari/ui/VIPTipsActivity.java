package co.ke.siundu254.betshwari.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.ke.siundu254.betshwari.adapter.VIPRecyclerAdapter;
import co.ke.siundu254.betshwari.model.Regular;

public class VIPTipsActivity extends AppCompatActivity {
    List<Regular> list;
    LinearLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(co.ke.siundu254.betshwari.R.layout.activity_viptips);

        AdView mVIPAd = findViewById(co.ke.siundu254.betshwari.R.id.adView_VIP);
        AdRequest mAdRequest = new AdRequest.Builder().build();
        mVIPAd.loadAd(mAdRequest);

        final RecyclerView mVIPList = findViewById(co.ke.siundu254.betshwari.R.id.recyclerViewVIP);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRegularRef = database.getReference().child("tips").child("vip");
        mRegularRef.keepSynced(true);
        Query query = mRegularRef.limitToFirst(30);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Regular value = dataSnapshot1.getValue(Regular.class);
                    Regular fire = new Regular();

                    if (value != null) {
                        String title = value.getTitle();
                        String status = value.getStatus();
                        String date = value.getDate();
                        String tips = value.getTips();

                        fire.setTitle(title);
                        fire.setStatus(status);
                        fire.setDate(date);
                        fire.setTips(tips);

                        list.add(fire);
                    }

                }
                VIPRecyclerAdapter adapter = new VIPRecyclerAdapter(list, VIPTipsActivity.this);
                manager = new LinearLayoutManager(getParent());
                manager.setReverseLayout(true);
                manager.setStackFromEnd(true);
                mVIPList.setLayoutManager(manager);
                mVIPList.setItemAnimator(new DefaultItemAnimator());
                mVIPList.setHasFixedSize(true);
                mVIPList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
