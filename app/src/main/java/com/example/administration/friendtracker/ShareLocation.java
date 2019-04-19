package com.example.administration.friendtracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShareLocation extends AppCompatActivity  implements LocationListener,View.OnClickListener {
    content db = new content(this);
    //Context mycontext;

    EditText e2 ;
    EditText e1;
    Button b1;
    Button b2;
    Button b3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent implicit=new Intent(Intent.ACTION_SEND);
        implicit.setType("text/plain");
        String msg="Latitude = " +lat+"  Longitude = "+log;
        implicit.putExtra(Intent.EXTRA_TEXT,msg);
        Intent chooser=Intent.createChooser(implicit,"Complete Task using:");
        {
            startActivity(chooser);
        }
        return super.onOptionsItemSelected(item);
    }

    String placename;
    String KEY_ID;

    public ShareLocation() {

    }

    public ShareLocation(String placename, String Latitude, String longitude, String keyid) {

        placename = this.placename;
        Latitude = this.lat;
        longitude = this.log;
        keyid = this.KEY_ID;

    }

    public String getplacename() {
        return placename;
    }

    public String getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(String KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public void setplacename(String placename) {
        this.placename = placename;

    }

    public String getlat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getlog() {
        return log;
    }

    public void setlog(String log) {
        this.log = log;

    }


    String lat;
    String log;
    TextView t1;
    TextView t2;

    @Override
    public void onLocationChanged(Location location) {
        double Latitude = location.getLatitude();
        double longitude = location.getLongitude();
        // textView.setText("Latitude= " + Latitude+ "Longitude = "+ longitude);
        lat = Double.toString(Latitude);
        log = Double.toString(longitude);
        t1.setText(lat);
        t2.setText(log);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_location);

        e2 = (EditText) findViewById(R.id.editText4);
        e1 = (EditText) findViewById(R.id.editText);
        b1 = (Button) findViewById(R.id.button3);
        b2 = (Button) findViewById(R.id.button4);
        b3 = (Button) findViewById(R.id.button5);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);


        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView5);

        LocationManager locationManager;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;

        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
    }


    public  void showMsg(String title,String msg)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }


    public void onClick(View view) {

        final int id_field;

        switch (view.getId()) {
            case R.id.button3:
                Log.d("Insert: ", "Inserting ..");
                boolean isinserted=db.addLocation(e1.getText().toString(), t1.getText().toString(), t2.getText().toString(), e2.getText().toString());
                if(isinserted=true)
                {
                    Toast.makeText(ShareLocation.this,"Data Inserted",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(ShareLocation.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                break;
            case R.id.button4:
                Integer deleterow=db.deleteContact(e2.getText().toString());
                if(deleterow>0)
                {
                    Toast.makeText(ShareLocation.this,"Data deleted",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(ShareLocation.this,"Could'nt delete",Toast.LENGTH_LONG).show();

                break;



               /* Log.d("Reading: ", "Reading all contacts..");
                int size = db.getContactsCount();
                String[] mylist = new String[size];
                int i = 0;
                List<ShareLocation> contacts = db.getAllContacts();
                for (ShareLocation cn : contacts) {
                    String show1 = String.valueOf(cn.getKEY_ID());
                    String log = " Place Name: " + cn.getplacename() + " ,Latitude " + cn.getlat() + "Longititude" + cn.getlog() + "Id: " + cn.getKEY_ID();
                    // Writing Contacts to log
                    mylist[i] = log;
                    i++;


                    //char s1 = log.charAt(4);
                    Log.d("Name: ", log);
                    // Log.d("Name: ", String.valueOf(s1));
                }




                rrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item,mylist);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                s1.setAdapter(adapter);

                s1.setOnItemSelectedListener(new AdapterView().OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
                {
                    myContext=view.getContext();
                    Toast myToast=new Toast(myContext);

                    // tv1.setText(((TextView)view).getText());
                    myToast.makeText(myContext,((TextView)view).getText(),Toast.LENGTH_LONG). show();
                    //tv1.setText(((TextView)view).getText());

                    String s1= ((TextView)view).getText().toString();
                    String id1 =s1.substring(4,5);
                    Log.d("hello",s1);
                    Log.d("hello",id1);




                    Contact c = db.getContact(Integer.parseInt(id1));
                    eN.setText(c.getName());
                    eP.setText(c.getPhoneNumber());
                    t1.setText(String.valueOf(c.getID()));


                }

                public void onNothingSelected(AdapterView<?> parent)
                {
                }
            });
            break;*/
            case R.id.button5:
            Cursor res = db.getAllLocation();
                if(res.getColumnCount()==0)
                {
                    showMsg("Error","Nothing found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append("Place Name : "+ res.getString(0) +"\n");
                    buffer.append("Latitude : "+ res.getString(1)+ "\n");
                    buffer.append("Longitude : "+ res.getString(2)+ "\n");
                    buffer.append("key_id : "+ res.getString(3)+"\n\n");

                }
                showMsg("Data",buffer.toString());
                break;


            default:
                break;
        }
    }
}