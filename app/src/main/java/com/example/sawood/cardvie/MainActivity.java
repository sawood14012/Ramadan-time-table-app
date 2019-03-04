package com.example.sawood.cardvie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Myadapter adapter;
    private List<Recycleritem> listit;
    String currentdate = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault()).format(new Date());
    private TextView textView1,textView2,textView3;
   // String s = currentdate "<font color=\\\"blue\\\">This text is blue.</font>";
    RamdanCountDownTimer timer = new RamdanCountDownTimer(0, 0, 0, 17, 4, 2018);


    Calendar calendar = Calendar.getInstance();

    int thisYear = calendar.get(Calendar.YEAR);

    int thisMonth = calendar.get(Calendar.MONTH);

    int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
    int hrofday = calendar.get(Calendar.HOUR_OF_DAY);
    int minofday=calendar.get(Calendar.MINUTE);
    int rozano,leftroz;


    iftartimer iftar = new iftartimer(00,40,18,thisDay,thisMonth,thisYear);
    String date = "17/May/2018";
    String cal_ift;
    String time_lft;
    String tod_ift;
    String tod_seh;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a");
    Date d =null;
    private static final String TAG = "MyActivity";
    ProgressBar prg;
    int hr,min;

    FirebaseAuth firebaseAuth;
    private BroadcastReceiver mRegistrationBroadcastReceiver;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // startService(new Intent(this,NotificationService.class));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // getActionBar().show();



        Resources res= getResources();
        String[] str = res.getStringArray(R.array.dateseng);
        String[] str1 = res.getStringArray(R.array.days);
        String[] seh = res.getStringArray(R.array.sehr);
        String[] ift = res.getStringArray(R.array.iftar_time);
        String[] mar = res.getStringArray(R.array.mark);
        recyclerView = findViewById(R.id.recview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listit = new ArrayList<>();
        textView1 = findViewById(R.id.count);
        textView2= findViewById(R.id.tod_seh);
        textView3= findViewById(R.id.time_left);
        prg = findViewById(R.id.progressBar);
        prg.setMax(100);
        String s= "*";
       // currentdate="01/Jun/2018";
        textView2.setTextSize(16);

        for (int i=0;i<30;i++){


            if(str[i].equals(currentdate)){

                mar[i]=s;

            }


            listit.add(new Recycleritem(" "+(i+1),str[i],str1[i],seh[i],ift[i],mar[i]));

            if(str[i].equals(currentdate)){
                rozano = i+1;
               tod_seh = listit.get(i).getSeh_time();
                cal_ift = tod_ift=listit.get(i).getIft_time();
               // textView2.setTextColor(R.color.colorAccent);
                textView2.setText("Today's Sehri:" +tod_seh+ " & "+"Iftar:" +tod_ift);
                leftroz = 30 - rozano;


            }




        }


        adapter=new Myadapter(listit,this);
        recyclerView.setAdapter(adapter);


        new CountDownTimer(timer.getIntervalMillis(), 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int days = (int) ((millisUntilFinished / 1000) / 86400);
                int hours = (int) (((millisUntilFinished / 1000)
                        - (days * 86400)) / 3600);
                int minutes = (int) (((millisUntilFinished / 1000)
                        - (days * 86400) - (hours * 3600)) / 60);
                int seconds = (int) ((millisUntilFinished / 1000) % 60);

                String countdown = String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, seconds);
                textView1.setText(countdown+" left.");
            }

            @Override
            public void onFinish() {
               //.setVisibility(View.GONE);
                if(date.equals(currentdate)) {
                    textView1.setTextSize(25);
                    textView1.setText("Welcome Ramadan 2018");
                }


                else if(thisDay==15 && thisMonth==5 ){
                    textView1.setText("EID MUBARAK");
                }

                else {
                    textView1.setTextSize(20);
                    String s = String.valueOf(rozano);
                     textView1.setText("Roza: "+s+" going on "+leftroz+" left");

                    
                }



            }
        }.start();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strtimenow = mdformat.format(calendar.getTime());
        String strtimeift = "18:40";

        Date d1;
        Date d2;
        try{
            d1=mdformat.parse(strtimenow);
            d2=mdformat.parse(strtimeift);
            long diff = d2.getTime() - d1.getTime();

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;

             time_lft = diffHours+ ":"+diffMinutes;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //textView3.setText(time_lft);

        hr = 0;
        min=40;
       // long milliseconds = TimeUnit.SECONDS.toMillis(TimeUnit.HOURS.toSeconds(hr) + TimeUnit.MINUTES.toSeconds(min));

   // if(hrofday>04&&minofday>30) {


        new CountDownTimer(iftar.getIntervalMillis(), 1000) {

        @Override
            public void onTick(long millisUntilFinished) {
                int days = (int) ((millisUntilFinished / 1000) / 86400);
                 int hours = (int) (((millisUntilFinished / 1000)
                    - (days * 86400)) / 3600);
                int minutes = (int) (((millisUntilFinished / 1000)
                    - (days * 86400) - (hours * 3600)) / 60);
                int seconds = (int) ((millisUntilFinished / 1000) % 60);

                String countdown1 = String.format(" %02dh %02dm %02ds", hours, minutes, seconds);
                textView3.setText(countdown1 + "Time left to Iftar.");
             if(thisDay>=16 && thisMonth >=5){
                textView3.setText("Ramzan's over..");
            }
            }

        @Override
        public void onFinish() {
            //.setVisibility(View.GONE);
            //if(date.equals(currentdate)) {

            textView3.setVisibility(View.GONE);
           // textView3.setText("Contdown For next Iftar will Begin Shortly");
            //}

            //else {

            //  textView1.setVisibility(View.GONE);
            //}


            }
        }.start();

  // }
    //else {
       // textView3.setText("Next countdown will Begin after sehri");
   // }







      prg.setProgress(1);




         final Handler  handler = new Handler();
        Thread thread = new Thread() {
            @Override
            public void run() {

                if(hrofday==4 && minofday<31){
                    prg.setProgress(5);
                }
                else if(hrofday==5 && minofday <30){
                    prg.setProgress(10);
                }
                else if(hrofday==6 && minofday <30){
                    prg.setProgress(20);
                }
                else if(hrofday==7 && minofday <30){
                    prg.setProgress(25);
                }
                else if(hrofday==8 && minofday <30){
                    prg.setProgress(30);
                }
                else if(hrofday==9 && minofday <30){
                    prg.setProgress(35);
                }
                else if(hrofday==10 && minofday <30){
                    prg.setProgress(40);
                }
                else if(hrofday==11 && minofday <30){
                    prg.setProgress(45);
                }
                else if(hrofday==12 && minofday <30){
                    prg.setProgress(50);
                }
                else if(hrofday==13 && minofday <30){
                    prg.setProgress(55);
                }
                else if(hrofday==14 && minofday <30){
                    prg.setProgress(60);
                }
                else if(hrofday==15 && minofday <30){
                    prg.setProgress(75);
                }
                else if(hrofday==16 && minofday <30){
                    prg.setProgress(80);
                }
                else if(hrofday==17 && minofday <30){
                    prg.setProgress(90);
                }
                else if(hrofday==18 && minofday <45){
                    prg.setProgress(98);
                }


                if (hrofday ==4 && minofday >30){
                    prg.setProgress(5);
                }
                else if (hrofday ==5 && minofday >30){
                    prg.setProgress(10);
                }
                else if (hrofday ==6 && minofday >30){
                    prg.setProgress(20);
                }

                else if (hrofday ==7 && minofday >30){
                    prg.setProgress(25);
                }

                else if (hrofday ==8 && minofday >30){
                    prg.setProgress(30);
                }

                else if (hrofday ==9 && minofday >30){
                    prg.setProgress(35);
                }

                else if (hrofday ==10 && minofday >30){
                    prg.setProgress(40);
                }

                else if (hrofday ==11 && minofday >30){
                    prg.setProgress(45);
                }
                else if (hrofday ==12 && minofday >30){
                    prg.setProgress(50);
                }

                else if (hrofday ==13 && minofday >30){
                    prg.setProgress(55);
                }

                else if (hrofday ==14 && minofday >30){
                    prg.setProgress(60);
                }

                else if (hrofday ==15 && minofday >30){
                    prg.setProgress(75);
                }

                else if (hrofday ==16 && minofday >30){
                    prg.setProgress(80);
                }

                else if (hrofday ==17 && minofday >30){
                    prg.setProgress(90);
                }
                else if (hrofday ==18 && minofday >45){
                    prg.setProgress(100);
                }






            }
        };

        thread.start();




        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    // displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(),  message, Toast.LENGTH_LONG).show();

                    // txtMessage.setText(message);
                }
            }
        };












    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.refresh_scr:
                refresh();
                break;
            case R.id.dev_info:
                dev_if();
                break;
            case R.id.logout:
                Logout();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }



    public void  refresh(){
        finish();
        startActivity(getIntent());
    }

    public void dev_if(){
        Intent i = new Intent(getApplicationContext(),Dev_info.class);
        startActivity(i);
    }


    private void Logout(){
       // LoginManager.getInstance().logOut();
       firebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), Login.class));







      //  displayFirebaseRegId();




    }

    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotifcationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }














}







