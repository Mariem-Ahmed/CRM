package com.example.afaf.enterprisecrm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;

import com.example.afaf.enterprisecrm.Helper_Database.notification_helper;
import com.example.afaf.enterprisecrm.Helper_Database.Actnotification_Model;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by enterprise on 22/12/16.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    NotificationManager notificationManager;
    Notification myNotification;

    private static final int MY_NOTIFICATION_ID=1;
    Actnotification_Model actN= null;
  public notification_helper dbNotif;

    // ----------------------------------------
    //------------------- url -------------------------------------------------------------
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    String uRl="";
    public static final String LoginPREFERENCES = "LoginPrefs" ;

    SharedPreferences sharedpreferencesLogin;
    String uname="";
    String passw="";



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        // Vibrate the mobile phone
//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(2000);


       sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl= sharedpreferences.getString("URL",null);
        if (uRl!=null){
            sharedpreferencesLogin = context.getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
            uname = sharedpreferencesLogin.getString("uName", null);
            passw = sharedpreferencesLogin.getString("passWord", null);
        }

  // -------------------------------------------------------------------------------------------
//
       dbNotif = new notification_helper(context);

        try {

           if(dbNotif.readActNotif(MY_NOTIFICATION_ID)!=null)

            actN = dbNotif.readActNotif(MY_NOTIFICATION_ID);
        } catch (JSONException e) {

        }
        Boolean b=false;
        String bb= String.valueOf(b);
       if(actN.getId()!=0 )
         //  if(!&& actN.getNotifSentFlag().equals("true"))
          if(actN.getNotifSentFlag().equals(bb)) {
              try {
                  intent = new Intent(context, Class.forName("com.example.afaf.enterprisecrm.MainActivity"));
              } catch (ClassNotFoundException e) {

              }
              // "com.example.afaf.enterprisecrm.Main3Activity"
         PendingIntent pendingIntent = PendingIntent.getBroadcast(
                 context, 2000000000, intent, 0);

         myNotification = new Notification.Builder(context)
                 .setContentTitle("Opentus Notification!")
                 .setContentText(actN.getNotifSubject() + ":" + actN.getNotifDesc())
                 .setTicker("Opentus Notification!")
                 .setWhen(System.currentTimeMillis())
                 .setContentIntent(pendingIntent)
                 .setDefaults(Notification.DEFAULT_SOUND)
                 .setAutoCancel(true)
                 .setSmallIcon(R.mipmap.ic_launcher)
                 .build();

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
         notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
              actN.setNotifSentFlag("true");
             dbNotif.updateEvent(actN);

              if(actN.getNotifSentFlag().equals("true")) {
                  AsyncCallWSNotif task1 = new AsyncCallWSNotif();
                  task1.execute();
              }

          }

// update connectio

    }

    public class AsyncCallWSNotif extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            testUpdateNotif("/ws/it.openia.crm.activityNotification_update?");

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {


        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
    public HttpURLConnection createConnectionnotifUpdate(String wsPart, String method) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname, passw.toCharArray());
            }
        });

        final URL url = new URL(uRl + wsPart);
        final HttpURLConnection hc = (HttpURLConnection) url.openConnection();
        hc.setRequestMethod(method);
        hc.setAllowUserInteraction(false);
        hc.setDefaultUseCaches(false);
        hc.setDoOutput(true);
        hc.setDoInput(true);
        hc.setInstanceFollowRedirects(true);
        hc.setUseCaches(false);
        hc.setRequestProperty("Content-Type", "text/xml");
        return hc;
    }
    public String testUpdateNotif(String content) {
        try {

            HttpURLConnection conn = createConnectionnotifUpdate( content,"POST");
            conn.connect();
          //  notification_helper db = new notification_helper();
            Actnotification_Model a= null;
            JSONArray jsArray = null;
           // for (int i=1; i<dbNotif.getAllActNotif().size();i++){
                try {
                    a = dbNotif.readActNotif(1);
                } catch (JSONException e) {

                }
                if (actN.getNotifID().equals(a.getNotifID())){

                    ArrayList<Object> list=new ArrayList<Object>();
                    list.add(0,a.getNotifID());
                    list.add(1,a.getNotifSentFlag());

                     jsArray = new JSONArray(list);
                }
         //   }

            OutputStream os = conn.getOutputStream();

            String s=jsArray.toString();


            os.write(s.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {



        } catch (IOException e) {


        } catch (Exception e) {

        }

        return null;


    }
    // ----------------------------------------------------------------------------------------------------

}
