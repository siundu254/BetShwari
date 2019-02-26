package co.ke.siundu254.betshwari.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import co.ke.siundu254.betshwari.adapter.RecyclerAdapter;
import co.ke.siundu254.betshwari.model.Regular;

public class MainActivity extends AppCompatActivity {
    List<Regular> list;
    RecyclerView mRegularList;
    LinearLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(co.ke.siundu254.betshwari.R.layout.activity_main);

        AdView mAdView = findViewById(co.ke.siundu254.betshwari.R.id.adView_Main);
        AdRequest mAdRequest = new AdRequest.Builder().build();
        mAdView.loadAd(mAdRequest);

        mRegularList = findViewById(co.ke.siundu254.betshwari.R.id.recyclerViewRegular);

        FirebaseMessaging.getInstance().subscribeToTopic("regular");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRegularRef = database.getReference().child("tips").child("regular");
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
                RecyclerAdapter adapter = new RecyclerAdapter(list, MainActivity.this);
                manager = new LinearLayoutManager(getParent());
                manager.setReverseLayout(true);
                manager.setStackFromEnd(true);
                mRegularList.setLayoutManager(manager);
                mRegularList.setItemAnimator(new DefaultItemAnimator());
                mRegularList.setHasFixedSize(true);
                mRegularList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(co.ke.siundu254.betshwari.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case co.ke.siundu254.betshwari.R.id.vip_tips_menu:
                openVIP();
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void openVIP() {
        startActivity(new Intent(MainActivity.this, VIPAccountLoginActivity.class));
    }
}
