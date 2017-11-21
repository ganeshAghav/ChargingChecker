package com.everestadvanced.chargingchecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public static MyGlobal myGlobal;
    public static TextView txtCharging;
    public static Button btnSetlevel,btnSetAlarm,btnStopAlerm;
    public static Spinner spinChargePercentage;
    public static BatteryLevelAdapter adapter;
    public static pl.droidsonroids.gif.GifImageView imageView;
    public static String SelectVlues;
    public static int SelectedVlues;
    public static SharedPreferences sharedPreferences;
    public static String arraySpinner[]={"1","2","3","4","5","6","7","8","9","10",
            "11","12","13","14","15","16","17","18","19","20",
            "21","22","23","24","25","26","27","28","29","30",
            "31","32","33","34","35","36","37","38","39","40",
            "41","42","43","44","45","46","47","48","49","50",
            "51","52","53","54","55","56","57","58","59","60",
            "61","62","63","64","65","66","67","68","69","70",
            "71","72","73","74","75","76","77","78","79","80",
            "81","82","83","84","85","86","87","88","89","90",
            "91","92","93","94","95","96","97","98","99","100"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        myGlobal=new MyGlobal();

        txtCharging=(TextView) findViewById(R.id.charging);
        int batLevel=myGlobal.getBatteryPercentage(getApplicationContext());
        txtCharging.setText("Battery Charging: "+ String.valueOf(batLevel)+"%");

        sharedPreferences = getApplicationContext().getSharedPreferences("SelectedVlues", MODE_PRIVATE);

        //set gif image
        imageView=(pl.droidsonroids.gif.GifImageView)findViewById(R.id.gifimagecharge);
        if(myGlobal.CheckBatteryIsCharging(getApplicationContext())==true)
        {
            imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            imageView.setVisibility(View.GONE);
        }

        spinChargePercentage=(Spinner) findViewById(R.id.parcentCahrging);
        spinChargePercentage.setOnItemSelectedListener(this);

        btnSetlevel=(Button) findViewById(R.id.setelvel);
        btnSetlevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinChargePercentage.setVisibility(View.VISIBLE);
                spinChargePercentage.performClick();

            }
        });


        ArrayList<String> aList = new ArrayList<String>();
        aList.addAll(Arrays.asList(arraySpinner));

        adapter = new BatteryLevelAdapter(MainActivity.this,aList);
        spinChargePercentage.setAdapter(adapter);

        btnSetAlarm=(Button) findViewById(R.id.setalarms);
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelectedVlues=Integer.parseInt(SelectVlues);

                if(myGlobal.CheckBatteryIsCharging(getApplicationContext())==true)
                {
                    if(SelectedVlues>(myGlobal.getBatteryPercentage(getApplicationContext())))
                    {
                        Editor editor = sharedPreferences.edit();
                        editor.putInt("alermvalues", SelectedVlues);
                        editor.commit();

                        startService(new Intent(MainActivity.this, CheckBattryLevelService.class));

                        Toast.makeText(getApplicationContext(),"Alarm set successfully !!!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please select charging level grater than your mobile chargin !!!",Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please connect charger plugin !!!",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnStopAlerm=(Button) findViewById(R.id.stopalarms);
        btnStopAlerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent svc4=new Intent(MainActivity.this, CheckBattryLevelService.class);
                stopService(svc4);
                Toast.makeText(getApplicationContext(),"Alarm Stop successfully !!!",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SelectVlues=String.valueOf(adapter.getItem(position));
        //;Toast.makeText(getApplicationContext(),String.valueOf(adapter.getItem(position)),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
