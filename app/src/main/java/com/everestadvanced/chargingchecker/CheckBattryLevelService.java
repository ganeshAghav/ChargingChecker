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


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {

        player = MediaPlayer.create(this, R.raw.sond2);
        myGlobal=new MyGlobal();
        sharedPreferences=getApplicationContext().getSharedPreferences("SelectedVlues",MODE_PRIVATE);
        SelectedValue=sharedPreferences.getInt("alermvalues", 0);
    }

    @Override
    public void onStart(Intent intent, int startId) {
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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        StopMusic();
        Log.e("SERVICE","Service Destroy");
    }

    public void CheckingData(){

        Log.e("SERVICE","CheckingData");
        int batteryLevel=myGlobal.getBatteryPercentage(getApplicationContext());

        if (SelectedValue <= (batteryLevel))
        {
            StratMusic();
        }
        if(myGlobal.CheckBatteryIsCharging(getApplicationContext())==false)
        {
            Intent svc4=new Intent(getApplicationContext(), CheckBattryLevelService.class);
            stopService(svc4);
        }
    }

    public void StratMusic() {

        if(result==false)
        {
            try {

                if (!player.isPlaying()) {
                    player.setLooping(true); // Set looping
                    player.setVolume(100, 100);
                    player.start();
                    player.prepare();
                    result = true;
                }
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
            if (player.isPlaying()) {
                player.stop();
                player.release();
            }
        }
        catch (Exception e)
        {

        }

    }

}