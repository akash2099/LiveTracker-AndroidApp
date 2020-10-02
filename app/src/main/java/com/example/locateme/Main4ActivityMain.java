package com.example.locateme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main4ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4_main2);
    }

    public void tracker_icon_click_function(View view) {
        Intent tracker_list_activity=new Intent(getApplicationContext(),AddedTrackerActivity.class);
//        tracker_list_activity.putExtra("titlefrom","ignore");
//        tracker_list_activity.putExtra("descriptionfrom","ignore");
//        tracker_list_activity.putExtra("add_or_update","ADD");
////        tracker_list_activity.putExtra("recordno",RecordID_string);.
//        tracker_list_activity.putExtra("rem_time","ignore");
//        tracker_list_activity.putExtra("rem_date","ignore");
        startActivity(tracker_list_activity);
    }
}
