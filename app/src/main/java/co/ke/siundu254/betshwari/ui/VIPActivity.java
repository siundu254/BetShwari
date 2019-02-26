package co.ke.siundu254.betshwari.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VIPActivity extends AppCompatActivity {
    ProgressDialog mVipDialog;
    String number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(co.ke.siundu254.betshwari.R.layout.activity_vip);

        mVipDialog = new ProgressDialog(this);
        mVipDialog.setTitle("VIP Status");
        mVipDialog.setMessage("Please wait while we verify your Account Status");
        mVipDialog.setCanceledOnTouchOutside(false);
        mVipDialog.show();

        number = getIntent().getStringExtra("number");

        TextView textStepOne = findViewById(co.ke.siundu254.betshwari.R.id.textStepOne);
        textStepOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout textInstructOne = findViewById(co.ke.siundu254.betshwari.R.id.linearLayoutVip);
                if (textInstructOne.getVisibility() == View.GONE) {
                    textInstructOne.setVisibility(View.VISIBLE);
                } else if (textInstructOne.getVisibility() == View.VISIBLE) {
                    textInstructOne.setVisibility(View.GONE);
                }
            }
        });

        TextView textStepTwo = findViewById(co.ke.siundu254.betshwari.R.id.textStepTwo);
        textStepTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textInstructTwo = findViewById(co.ke.siundu254.betshwari.R.id.textInstructionTwo);
                if (textInstructTwo.getVisibility() == View.GONE) {
                    textInstructTwo.setVisibility(View.VISIBLE);
                } else if (textInstructTwo.getVisibility() == View.VISIBLE) {
                    textInstructTwo.setVisibility(View.GONE);
                }
            }
        });

        TextView textStepThree = findViewById(co.ke.siundu254.betshwari.R.id.textStepThree);
        textStepThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textInstructionThree = findViewById(co.ke.siundu254.betshwari.R.id.textInstructionThree);
                if (textInstructionThree.getVisibility() == View.GONE) {
                    textInstructionThree.setVisibility(View.VISIBLE);
                } else if (textInstructionThree.getVisibility() ==View.VISIBLE) {
                    textInstructionThree.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRegularRef = database.getReference().child("vip").child("accounts").child(number);
        mRegularRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child("status").getValue().toString();

                if (status.equals("true")) {
                    mVipDialog.dismiss();
                    startActivity(new Intent(VIPActivity.this, VIPTipsActivity.class));
                } else {
                    mVipDialog.hide();
                    Toast.makeText(VIPActivity.this, "Dear Customer, renew your subscription to enjoy VIP Tips.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
