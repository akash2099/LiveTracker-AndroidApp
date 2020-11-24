package com.example.locateme;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedRef {
    SharedPreferences ShredRef;

    public SharedRef(Context context){
        ShredRef=context.getSharedPreferences("myRef",Context.MODE_PRIVATE);
    }

    public  void SaveDataSharedRef(String UserName,String PhoneNumber){
        SharedPreferences.Editor editor=ShredRef.edit();
        editor.putString("Username",UserName);
        editor.putString("PhoneNumber",PhoneNumber);
        editor.commit();
    }

    public String LoadDataUsername(){
        return ShredRef.getString("Username","no username");
    }

    public String LoadDataPhoneNumber(){
        return ShredRef.getString("PhoneNumber","no phone number");
    }

}