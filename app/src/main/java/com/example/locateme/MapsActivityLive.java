package com.example.locateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

public class MapsActivityLive extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    String number_received, label_received, mode_received, name_received;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_live);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        Bundle b1=getIntent().getExtras();
        assert b1 != null;
        number_received=b1.getString("number_pop");
        label_received=b1.getString("label_pop");
        mode_received=b1.getString("mode_pop");
        name_received=b1.getString("name_pop");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                Toast.makeText(getApplicationContext(), "Map mode changed to Hybrid View", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                Toast.makeText(getApplicationContext(), "Map mode changed to Normal View", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                Toast.makeText(getApplicationContext(), "Map mode changed to Satellite View", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                Toast.makeText(getApplicationContext(), "Map mode changed to Terrain View", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    void getLocation() {
        myThread mytthod = new myThread();
        mytthod.start();
    }
    int count=0;
    int max_request_count=40;
    boolean flag1=true;
    class myThread extends Thread {
        public void run() {
            while (true) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Hi from thread1");

                        if (myLocationListner.location != null) {
                            System.out.println("Hi from thread");

                            if(count<max_request_count) {

                                // start background task
                                JSONObject json = new JSONObject();
//        p2 = requests.post("https://www.axelr8.com/_functions/vendorDatabaseUpload", data=jsonfile)

                                try {
//                                    json.put("vendorName", "Please"+count);

                                    json.put("number",label_received);
                                    json.put("longitude",String.valueOf(myLocationListner.location.getLongitude()));
                                    json.put("latitude",String.valueOf(myLocationListner.location.getLatitude()));
                                    json.put("mode","LIVE");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONObject json1 = new JSONObject();
//        p2 = requests.post("https://www.axelr8.com/_functions/vendorDatabaseUpload", data=jsonfile)

                                try {

                                    String number_to_send="~"+number_received+"~";
                                    json1.put("number",number_to_send);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                                new MyAsyncTaskgetNews().execute(json.toString(), json1.toString());
                                flag1=false;
                                count+=1;
                            }


                        }
                        if(flag1){

                            Toast.makeText(MapsActivityLive.this, " Connecting . . . ", Toast.LENGTH_SHORT).show();


                        }

                        if(count==max_request_count){
//                            AlertDialog.Builder info=new AlertDialog.Builder(getApplicationContext());
//                            info.setMessage("Maximum requests reached\nPlease restart the link to reconnect.")
//                                    .setTitle("Disconnected")
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            finish();
////                                Toast.makeText(getApplicationContext(),"Hi!",Toast.LENGTH_SHORT).show();
//                                        }
//                                    })
//                                    .show();

                            Toast.makeText(getApplicationContext(), "Disconnected!\nMaximum requests reached\nPlease restart the link to reconnect!", Toast.LENGTH_LONG).show();
                            finish();

                        }

                    }
                });

                try {
                    Thread.sleep(12000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(count==max_request_count){
                    break;
                }
            }
        }
    }

    // get news from server
    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                System.out.println("Hey there please respond");

                String url_post="https://kaynizami71.wixsite.com/mysite/_functions/LocateMeDatabaseUpload";
                String url_get_post="https://kaynizami71.wixsite.com/mysite/_functions/GetLocateMeDatabaseUpload";

                //define the url we have to connect with
                URL url = new URL(url_post);
                //make connect with url and send request
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds
                System.out.println("Hey there please respond3");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                System.out.println("Hey there please respond1");

                //define the url we have to connect with
                URL url1 = new URL(url_get_post);
                //make connect with url and send request
                HttpsURLConnection urlConnection1 = (HttpsURLConnection) url1.openConnection();
                //waiting for 7000ms for response
                urlConnection1.setConnectTimeout(7000);//set timeout to 5 seconds
                System.out.println("Hey there please respond3");
                urlConnection1.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection1.setDoOutput(true);
                urlConnection1.setDoInput(true);
                urlConnection1.setRequestMethod("POST");
                System.out.println("Hey there please respond1");


                OutputStreamWriter wr= new OutputStreamWriter(urlConnection.getOutputStream());
                System.out.println("Hey there please respond2");

                OutputStreamWriter wr1= new OutputStreamWriter(urlConnection1.getOutputStream());
                System.out.println("Hey there please respond2");

                try {
                    System.out.println("Hey there please respond4");
                    wr.write(params[0]);
                    wr.close();

                    System.out.println("Hey there param 1 value"+ params[1]);

                    wr1.write(params[1]);
                    wr1.close();
                    System.out.println("Hey there please respond5");

                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    System.out.println("Hey there please respon6");
                    //convert the stream to string
                    String result = ConvertInputToStringNoChange(in);

                    //getting the response data
                    InputStream in1 = new BufferedInputStream(urlConnection1.getInputStream());
                    System.out.println("Hey there please respon6");
                    //convert the stream to string
                    String result1 = ConvertInputToStringNoChange(in1);

                    // Publishing progress so as to access it via UI from on_progress_update
                    publishProgress(result,result1);

                    System.out.println("Hey there please respond7"+" "+result+" "+result1);
//                    JSONObject jsonObject = new JSONObject(result);

                } finally {
                    //end connection
                    urlConnection.disconnect();
                    urlConnection1.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {

                mMap.clear();
                System.out.println("Hey there clearing map");

//                //display response data
//                Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();
                System.out.println("Hey there normal post "+progress[0]);

                LatLng myloc = new LatLng(myLocationListner.location.getLatitude(), myLocationListner.location.getLongitude());

//                Toast.makeText(MapsActivityLive.this, "My location : "+ myLocationListner.location.getLatitude()+ " : "+ myLocationListner.location.getLongitude(), Toast.LENGTH_SHORT).show();
                System.out.println("Hey there my coordinates "+"My location : "+ myLocationListner.location.getLatitude()+ " : "+ myLocationListner.location.getLongitude());

                String[] rowss = progress[1].split(",");
                String[] last_known_row = rowss[0].split("-");
                String lat_data = last_known_row[1];
                String long_data = last_known_row[2];
                System.out.println("Hey there getting response from server "+progress[1]+" "+lat_data+" : "+long_data);

                LatLng friend_loc = new LatLng(Double.parseDouble(lat_data),Double.parseDouble(long_data));

                mMap.addMarker(new MarkerOptions()
                        .position(friend_loc)
                        .title(name_received)
                        .snippet(number_received)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.target_red)));
                System.out.println("Hey there setting friends location to "+lat_data+" : "+long_data);

                float[] results_disc = new float[1];
                Location.distanceBetween(friend_loc.latitude, friend_loc.longitude,
                        myloc.latitude, myloc.longitude,
                        results_disc);
                System.out.println("Hey there distance from your friend is : "+ Arrays.toString(results_disc));
                Toast.makeText(MapsActivityLive.this, "Horizontal displacement from target location : "+Arrays.toString(results_disc)+" meters", Toast.LENGTH_SHORT).show();


                mMap.addMarker(new MarkerOptions()
                        .position(myloc)
                        .title("You")
                        .snippet("Distance from target : "+Arrays.toString(results_disc)+" meters")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_icon)));
                System.out.println("Hey there setting your location now");

                mMap.addPolyline(new PolylineOptions()
                        .add(myloc,friend_loc)
                        .width(18)
                        .color(Color.parseColor("#000000"))
                        .geodesic(true));
                System.out.println("Hey there setting line between you and your friend");

                if(count==1){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 14));
                    System.out.println("Hey there zooming to your loc first time");
                }
                else if(count==2){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(friend_loc, 14));
                    System.out.println("Hey there zooming to your friends loc first time");
                }
                else if(count==3){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 14));
                    System.out.println("Hey there zooming to your loc again");
                }

            } catch (Exception ex) {
            }
//            System.out.println("Hey there some error above");

        }

        protected void onPostExecute(String  result2){


        }




    }

    // this method convert any stream to string
    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String linereultcal="";

        try{
            while((line=bureader.readLine())!=null) {

                linereultcal+=line;

            }
            inputStream.close();


        }catch (Exception ex){}

        return linereultcal;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);


//        System.out.println("Hi from threaddfsdf1");
//        LocationManager lm1 = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }


//        LatLng sydney1 = new LatLng(-33, 121);
//
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
////        mMap.addMarker(new MarkerOptions()
////                .position(sydney)
////                .title("Marker in Sydney")
////                .snippet("Hy teherere")
////                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_icon)));
//
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney1)
//                .title("Marker in Sydney2")
//                .snippet("Hy teherere target")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.target_red)));

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        getLocation();

    }



}
