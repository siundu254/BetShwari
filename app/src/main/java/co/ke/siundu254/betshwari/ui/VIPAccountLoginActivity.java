package co.ke.siundu254.betshwari.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class VIPAccountLoginActivity extends AppCompatActivity {
    private ProgressDialog mLoginDialog;
    String number = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(co.ke.siundu254.betshwari.R.layout.activity_vipaccount_login);

        mLoginDialog = new ProgressDialog(this);

        Button buttonVIP = findViewById(co.ke.siundu254.betshwari.R.id.buttonViewTips);
        buttonVIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mNumber = findViewById(co.ke.siundu254.betshwari.R.id.editTextVIPNumber);
                number = mNumber.getText().toString();

                if (!TextUtils.isEmpty(number)) {

                    mLoginDialog.setTitle("Authenticating...");
                    mLoginDialog.setMessage("Please wait while we Authenticate your Account");
                    mLoginDialog.setCanceledOnTouchOutside(false);
                    mLoginDialog.show();

                    authenticateClient();

                } else {
                    Toast.makeText(VIPAccountLoginActivity.this, "Dear Customer, Please enter the Phone number you wish to use to pay for VIP Tips", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void authenticateClient() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRegularRef = database.getReference().child("vip").child("accounts");
        mRegularRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(number)) {
                    mLoginDialog.dismiss();
                    Intent mIntent = new Intent(VIPAccountLoginActivity.this, VIPActivity.class);
                    mIntent.putExtra("number", number);
                    startActivity(mIntent);
                } else {
                    mLoginDialog.hide();
                    HashMap<String, String> mUserMap = new HashMap<>();
                    mUserMap.put("number", number);
                    mUserMap.put("status", "false");

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference mRegularRef = database.getReference().child("vip").child("accounts").child(number);
                    mRegularRef.setValue(mUserMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent mIntent = new Intent(VIPAccountLoginActivity.this, VIPActivity.class);
                            mIntent.putExtra("number", number);
                            startActivity(mIntent);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
