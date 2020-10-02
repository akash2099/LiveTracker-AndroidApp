package com.example.locateme;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username_et,phone_no_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        username_et= findViewById(R.id.username_et);
        phone_no_et= findViewById(R.id.phone_no_et);

        username_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str_value=s.toString();

                if(str_value.length()>=4){
                    username_et.setTextColor(getColor(R.color.correct_color_str));
                }
                else{
                    username_et.setTextColor(getColor(R.color.invalid_color_str));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phone_no_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str_value=s.toString();
                if(str_value.length()==10){
                    phone_no_et.setTextColor(getColor(R.color.correct_color_str));
                }
                else {
                    phone_no_et.setTextColor(getColor(R.color.invalid_color_str));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void login_onclick(View view) {

        // store/replace the login credentials to the sharedPreference storage
        String user_name_received=username_et.getText().toString();
        String phone_no_received=phone_no_et.getText().toString();
        if((user_name_received.length()>=4)&&(phone_no_received.length()==10)){
            Intent main_list_activity=new Intent(getApplicationContext(),Main4ActivityMain.class);
//        main_list_activity.putExtra("username_info",username_et.getText().toString());
//        main_list_activity.putExtra("phone_no_info",phone_no_et.getText().toString());
            startActivity(main_list_activity);
            finish();
            Toast.makeText(this, "Login Successful with "+user_name_received+" and "+phone_no_received+" !"
                    , Toast.LENGTH_SHORT).show();
        }
        else {
            if (!(user_name_received.length() >= 4)) {
                Toast.makeText(this, "Invalid Username!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Invalid Mobile Number!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void sign_up_onclick(View view) {
        Toast.makeText(this, "No sign up required right now!", Toast.LENGTH_SHORT).show();
    }

    public void forget_password_onclick(View view) {
        Toast.makeText(this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
    }
}
