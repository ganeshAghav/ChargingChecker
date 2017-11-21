package com.everestadvanced.chargingchecker;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 11/20/2017.
 */

public class CheckBattryLevelService extends Service
{
    public static MyGlobal myGlobal;
    public static MediaPlayer player;
    public static SharedPreferences sharedPreferences;
    public static int SelectedValue;
    public static boolean result=false;


    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {

        myGlobal=new MyGlobal();
        sharedPreferences=getApplicationContext().getSharedPreferences("SelectedVlues",MODE_PRIVATE);
        SelectedValue=sharedPreferences.getInt("alermvalues", 0);

    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("SERVICE","Service started");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (Exception e)
                    {
                    }
                    CheckingData();
                }
            }
        }).start();
        return  START_STICKY;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }

    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }

    public void onPause() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        StopMusic();
        Log.e("SERVICE","Service Destroy");
    }

    @Override
    public void onLowMemory() {

    }

    public void CheckingData(){

        Log.e("SERVICE","CheckingData");
        int batteryLevel=myGlobal.getBatteryPercentage(getApplicationContext());
        Log.e("SERVICE",String.valueOf(batteryLevel));

        if (SelectedValue <=(batteryLevel))
        {
            StratMusic();
        }
    }

    public void StratMusic() {

        if(result==false)
        {
            try
            {
                player = MediaPlayer.create(this, R.raw.sond2);
                player.setLooping(true); // Set looping
                player.setVolume(100, 100);
                player.start();
                result = true;

            }
            catch (Exception e)
            {

            }
        }
    }

    public void StopMusic() {
        try
        {
            result = false;
            if (player.isPlaying())
            {
                player.stop();
                player.release();
            }
        }
        catch (Exception e)
        {

        }

    }

}