package com.example.locateme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AddedTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_tracker);
    }

    public void add_contact_bu_onclick(View view) {
        Intent main_list_activity=new Intent(getApplicationContext(),Main2Activity.class);
//        main_list_activity.putExtra("username_info",username_et.getText().toString());
//        main_list_activity.putExtra("phone_no_info",phone_no_et.getText().toString());
        startActivity(main_list_activity);
    }
}
