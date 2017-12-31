package mif50.com.onlinequizapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Random;

import mif50.com.onlinequizapp.common.Common;

public class Home extends AppCompatActivity {
    // init view in home_activity.xml
    BottomNavigationView btnView;
    //
    BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        registrationNotification();//
        // find view in home_activity.xml
        btnView=findViewById(R.id.navigation);
        // action btnView (star or category) selected
        btnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId(); // get id of view of btnView (BottomNavigationView)
                Fragment select =null; // obj of fragment that hold which fragment appear
                switch (id){
                    case R.id.category: // if select category in menu in btnView
                        select=CategoryFragment.newInstance(); // create object of Fragment Category
                        break;
                    case R.id.star: // if select star in menu in btnView
                        select=RankingFragment.newInstance();// create object of Fragment Ranking
                        break;
                }
                // show Fragment in home_activity.xml Frame
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction(); // transaction used to add fragment
                transaction.replace(R.id.frame_layout,select); // show Fragment that user selected in home_activity.xml
                transaction.commit();
                return true;
            }
        });
        setDefaultFragment(); // show Fragment and create and show Home and user not selected nothing
    }
    /**/
    private void registrationNotification() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Common.STR_PUSH)){
                    String message=intent.getStringExtra(Common.MESSAGE_KEY);
                    showNotification("MIF50",message);
                }
            }


        };
    }
    /**/
    private void showNotification(String title, String message) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(),builder.build());
    }
    /*this method used to show CategoryFragment when create and shoe Home Activity */
    private void setDefaultFragment() {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction(); // transaction used to add Fragment
        transaction.replace(R.id.frame_layout,CategoryFragment.newInstance());// show Category Fragment in Home Activity
        transaction.commit();
    }


    /* this method used to move to this Activity Home*/
    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,Home.class);
        return intent;
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,new IntentFilter("registrationComplete"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,new IntentFilter(Common.STR_PUSH));
    }
}
