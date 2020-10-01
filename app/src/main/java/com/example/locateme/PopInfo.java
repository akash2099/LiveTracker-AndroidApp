package com.example.locateme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class PopInfo extends DialogFragment{
    View view;
    String name_val1,number_val1,mode_val1;
    ImageButton back_rem;
    Button live_bu, checkpoint_bu;
    EditText et_label_point;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.pop_info, container, false);
//        back_rem=(ImageButton)view.findViewById(R.id.back_rem);
        live_bu=(Button) view.findViewById(R.id.live_bu);
        checkpoint_bu=(Button) view.findViewById(R.id.checkpoint_bu);
        et_label_point=(EditText) view.findViewById(R.id.et_label_point);




        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("number_val")))
            number_val1=getArguments().getString("number_val");
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("Name_val")))
            name_val1=getArguments().getString("Name_val");
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("Mode_val")))
            mode_val1=getArguments().getString("Mode_val");


//        back_rem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getContext(),"back_rem",Toast.LENGTH_SHORT).show();
//                dismiss();
//            }
//        });


        live_bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"live_bu",Toast.LENGTH_SHORT).show();


                Intent intent1=new Intent(getContext(),MapsActivityLive.class);

                intent1.putExtra("number_pop",number_val1);
                intent1.putExtra("name_pop",name_val1);
                intent1.putExtra("mode_pop",mode_val1);
                intent1.putExtra("label_pop",et_label_point.getText().toString());


                startActivity(intent1);
                dismiss();
            }
        });

        checkpoint_bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"History is currently under development!",Toast.LENGTH_SHORT).show();
//                Intent intent2=new Intent(getContext(),MapActivityCheckpoint.class);
//
//                intent2.putExtra("number_pop1",number_val1);
//                intent2.putExtra("name_pop1",name_val1);
//                intent2.putExtra("mode_pop1",mode_val1);
//
//                startActivity(intent2);
                dismiss();
            }
        });



        return view;
    }


}
