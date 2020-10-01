package com.example.locateme;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main2Activity extends AppCompatActivity {

    String number;
    String Name;
    String Mode;


    int PERMISSION_MODE=0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        CheckUserPermsions();
        startLocation();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        SearchView sv=(SearchView)menu.findItem(R.id.menu_search).getActionView();
        SearchManager sm=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                read_contacts_data("search",query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                read_contacts_data("search",newText);


                return true;
            }
        });

        return true;
    }

    void startLocation(){
        myLocationListner mll = new myLocationListner(getApplicationContext());
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        assert lm != null;
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 0, mll);
//        if (mll.location != null) {
//            LatLng sydney = new LatLng(mll.location.getLatitude(), mll.location.getLongitude());
//            mMap.addMarker(new MarkerOptions()
//                    .position(sydney)
//                    .title("Marker of live location")
//                    .snippet("Hy Home")
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_icon)));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
//        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_help:
                AlertDialog.Builder info=new AlertDialog.Builder(this);
                info.setMessage("Project started on 07-09-2020\nby AKASH MANNA\nFEATURES:\nTRACK your friends anytime, anywhere.\n* Condition applied they must have to be present on Planet Earth!")
                        .setTitle("Information")
                        .setPositiveButton("Source Code", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent webintent=new Intent(getApplicationContext(),Main3Activity.class);
                                startActivity(webintent);
//                                Toast.makeText(getApplicationContext(),"Hi!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

                return true;
            case R.id.menu_settings:
                Toast.makeText(getApplicationContext(),"Settings is currently under development!",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void CheckUserPermsions(){
        if ( Build.VERSION.SDK_INT >= 23){
            if ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED)||
                    (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED)||
                    (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) !=
                            PackageManager.PERMISSION_GRANTED)){
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_CONTACTS},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        getContacts();// init the contact list

    }
    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 129;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PERMISSION_MODE=1;
                }
                else {
                    // Permission Denied
                    Toast.makeText( this,"Allow access to location to continue!" , Toast.LENGTH_SHORT)
                            .show();
                }
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    PERMISSION_MODE=2;
                }
                else {
                    // Permission Denied
                    Toast.makeText( this,"Allow access to location to continue!" , Toast.LENGTH_SHORT)
                            .show();
                }
                if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();// init the contact list
                }
                else {
                    // Permission Denied
                    Toast.makeText( this,"Allow access to contacts to continue!" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    public void getContacts(){
        // Code for reading contacts
//        Toast.makeText( this,"hii" , Toast.LENGTH_SHORT).show();
        read_contacts_data("ignore","ignore");
    }

    public void read_contacts_data(String to_find,String search_st){
        ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
        MyCustomAdapter myadapter;
        String mode1="ONLINE";


        Cursor cursor;
        if(to_find.equalsIgnoreCase("search")){
            String   whereString = "display_name LIKE ?";
            String[] whereParams = new String[]{ "%" + search_st + "%" };


//            String selection= ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+ " like ?";
//            String[] selectionArgs={search_st};
            cursor=getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                    null,whereString,whereParams,"upper("+ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");
        }
        else{
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,"upper("+ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");
        }



        Set<String> phonenumbersList = new HashSet<String>();
        ArrayList<String> online_folks = new ArrayList<String>();
        online_folks.add("8478864722");
        online_folks.add("+918478864722");
        online_folks.add("9830249729");
        online_folks.add("+919830249729");
        online_folks.add("9477281710");
        online_folks.add("+919477281710");
        online_folks.add("+918918939060");
        online_folks.add("8918939060");


        assert cursor != null;
        while(cursor.moveToNext()){
            String name1=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number1=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if ( number1 != null && name1 != null && !phonenumbersList.contains(number1)) {
                phonenumbersList.add(number1);
                /*
                if(online_folks.contains(number1)) {
                    listnewsData.add(new AdapterItems(name1, number1, mode1));
                }
                */

                listnewsData.add(new AdapterItems(name1, number1, mode1));

            }
        }
        cursor.close();




        myadapter=new MyCustomAdapter(listnewsData);
        final ListView lsNews=(ListView)findViewById(R.id.contact_list_view);
        lsNews.setAdapter(myadapter);//intisal with data
    }


    public void bu_add_checkpoint(View view) {
        Toast.makeText(getApplicationContext(), "Checkpoints are currently under development!", Toast.LENGTH_SHORT).show();
        // Code for adding checkpoints
    }

    public void bu_street_special(View view) {
        Toast.makeText(getApplicationContext(), "Street View coming soon!", Toast.LENGTH_SHORT).show();
    }



    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        TextView name_view,number_view,mode_view;
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_ticket, null);

            final   AdapterItems s = listnewsDataAdpater.get(position);

            ArrayList<String> online_folks1 = new ArrayList<String>();
            online_folks1.add("9830249729");
            online_folks1.add("+919830249729");
            online_folks1.add("+918918939060");
            online_folks1.add("8918939060");
            online_folks1.add("+918478864722");
            online_folks1.add("8478864722");


            name_view=(TextView)myView.findViewById(R.id.name_view);
            name_view.setSelected(true);

            number_view=(TextView)myView.findViewById(R.id.number_view);
            mode_view=(TextView)myView.findViewById(R.id.mode_view);

            number=s.number;
            Name=s.Name;
            Mode=s.Mode;

            if(!online_folks1.contains(number)) {
                mode_view.setBackgroundColor(Color.parseColor("#F40404"));
                mode_view.setTextColor(Color.parseColor("#FFFFFF"));
                mode_view.setText("OFFLINE");
            }
            else{
                mode_view.setText(Mode);
            }




            name_view.setText(Name);
            number_view.setText(number);


            number_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    androidx.fragment.app.FragmentManager fm=getSupportFragmentManager();
                    PopInfo popInfo=new PopInfo();

                    Bundle bundle1 = new Bundle();
                    bundle1.putString("number_val", s.number);
                    bundle1.putString("Name_val", s.Name);
                    bundle1.putString("Mode_val", s.Mode);

                    popInfo.setArguments(bundle1);

                    popInfo.show(fm,"Show Fragment");



                }
            });
            return myView;
        }

    }

}
