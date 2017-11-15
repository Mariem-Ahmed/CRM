package com.example.afaf.enterprisecrm;

/**
 * Created by ibrahimfouad on 01/02/17.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Leads;
import com.example.afaf.enterprisecrm.Helper_Database.activity_Type_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.callRecord_helper;
import com.example.afaf.enterprisecrm.Helper_Database.leadStatus_spinner_helper;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PhonecallReceiver extends BroadcastReceiver {

    //The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations


    MediaRecorder m_recorder;
    TelephonyManager t_manager ;
    PhoneStateListener p_listener ;
    String mFileName="";
    boolean  recordstarted = false;
    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;
    String uRl="";
    // ----------------------- log in ------------------------------------------------------
    public static final String LoginPREFERENCES = "LoginPrefs" ;
    public static final String CallPREFERENCES = "CallPrefs" ;
    String activityName ="";
    String startDate="";
    String startHour="";
    String startMin ="";
    String Duration="";
    String relatedLead="";
    String activityName_ ="";
    String startDate_="";
    String startHour_="";
    String startMin_ ="";
    String Duration_="";
    String relatedLead_ ="";
    String uuid_="";



    SharedPreferences sharedpreferencesLogin;
    SharedPreferences sharedpreferencesCall;
    String uname="";
    String passw="";
    String userId="";
    static final int BUFFER_SIZE = 4096;

    public static String leadID="";
    List<leadsModel> leadsModels;
    Date date;
    Date date1;
    static long start_time, end_time;
    activity_Type_spinner_helper db1 ;
    leadStatus_spinner_helper dbLS ;


    @Override
    public void onReceive(Context context, Intent intent) {
       db1 =  new activity_Type_spinner_helper(context);
        dbLS  = new leadStatus_spinner_helper(context);
        if (!lead_info_activity.callFlag.equals("outcome")) {
            try {
                sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                sharedpreferencesCall = context.getSharedPreferences(CallPREFERENCES, Context.MODE_PRIVATE);
                uRl = sharedpreferences.getString("URL", null);

                if (uRl != null) {
                    sharedpreferencesLogin = context.getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
                    uname = sharedpreferencesLogin.getString("uName", null);
                    passw = sharedpreferencesLogin.getString("passWord", null);
                    userId = sharedpreferencesLogin.getString("userId", null);
                    if (uname == null && passw == null) {
                        Intent i1 = new Intent(context.getApplicationContext(), LoginActivity.class);
                        context.startActivity(i1);

                    }
                } else {
                    Intent i = new Intent(context.getApplicationContext(), insertUrl.class);
                    context.startActivity(i);
                }
                SharedPreferences.Editor editor1 = sharedpreferencesCall.edit();
                editor1.putString("Duration", null);
                editor1.commit();
                JCGSQLiteHelper_Leads leadHelper = new JCGSQLiteHelper_Leads(context);

                mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();

                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);


                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    List<leadsModel> leadsModels = leadHelper.getLeadByPhone(incomingNumber);
                    if (leadsModels.size() != 0) {
                        Toast.makeText(context, " " + leadsModels.get(0).getCommertialName(), Toast.LENGTH_SHORT).show();
                        //      mFileName = mFileName +"/"+"Incoming Call State From " + leadsModels.get(0).getCommertialName()+ ".mp3" ;
                        Toast.makeText(context, "Ringing State Number is -" + incomingNumber, Toast.LENGTH_SHORT).show();

                    }


                }
                state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-m-dd");
                    DateFormat hourFormat = new SimpleDateFormat("HH");
                    DateFormat minFormat = new SimpleDateFormat("mm");
                    final callRecord_helper rc = new callRecord_helper(context);
                    List<leadsModel> leadsModels = leadHelper.getLeadByPhone(incomingNumber);
                    if (leadsModels.size() != 0) {
                        date = new Date();

                        int x = rc.getAllRecords().size() + 1;
                        activityName = "Income Call From " + leadsModels.get(0).getCommertialName() + " " + x;
                        startDate = dateFormat.format(date);
                        startHour = hourFormat.format(date);
                        startMin = minFormat.format(date);
                        relatedLead = leadsModels.get(0).getLeadId();
                        mFileName = mFileName + "/" + activityName + ".mp3";
                        SharedPreferences.Editor editor = sharedpreferencesCall.edit();
                        editor.putString("activityName", activityName);
                        editor.putString("startDate", startDate);
                        editor.putString("startHour", startHour);
                        editor.putString("startMin", startMin);
                        editor.putString("relatedLead", relatedLead);
                        editor.putString("mFile", mFileName);
                        editor.commit();


                        Toast.makeText(context, "Call Received State", Toast.LENGTH_SHORT).show();
                        m_recorder = new MediaRecorder();
                        m_recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        m_recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        m_recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                        m_recorder.setOutputFile(mFileName);
                        //editor.put
                        try {
                            m_recorder.prepare();

                        } catch (IllegalStateException e) {

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            m_recorder.start();

                        } catch (Exception e) {

                        }


                    }

                    recordstarted = true;
                }
                state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {

                    leadsModels = leadHelper.getLeadByPhone(incomingNumber);
                    if (leadsModels.size() != 0) {
                        date1 = new Date();
                        Toast.makeText(context, "Call Idle State", Toast.LENGTH_SHORT).show();
                        end_time = System.currentTimeMillis();
                        //Total time talked =
                        long total_time = end_time - start_time;
                        long minutes = (total_time / 1000) / 60;
                        Duration = total_time + "";
                        Uri file = Uri.fromFile(
                                new File(mFileName));
                        Toast.makeText(context,
                                "record stored at " + file.toString(),
                                Toast.LENGTH_LONG).show();

                        String str = Math.random() + "";
                        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                        SharedPreferences.Editor editor = sharedpreferencesCall.edit();
                        editor.putString("Duration", Duration);
                        editor.putString("uuid", uuid.toString());
                        editor.commit();


                    }


                }

            } catch (Exception e) {

            } finally {

                activityName_ = sharedpreferencesCall.getString("activityName", null);
                startDate_ = sharedpreferencesCall.getString("startDate", null);
                startHour_ = sharedpreferencesCall.getString("startHour", null);
                startMin_ = sharedpreferencesCall.getString("startMin", null);
                Duration_ = sharedpreferencesCall.getString("Duration", null);
                uuid_ = sharedpreferencesCall.getString("uuid", null);
                if (Duration_ != null) {
                    JCGSQLiteHelper h = new JCGSQLiteHelper(context);
                    h.createActivity(activityName_, startDate_, startHour_, startMin_, Duration + "", "CALL", "answer", leadsModels.get(0).getLeadId(), uuid_, null, null, null, userId);
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();
                    AsyncCallWSRecord task1 = new AsyncCallWSRecord();
                    task1.execute();

                }

            }

        }
    }
    // insert connection
    public String testInsert(String content) {
        try {
            activityName_ = sharedpreferencesCall.getString("activityName", null);
            startDate_ = sharedpreferencesCall.getString("startDate", null);
            startHour_ = sharedpreferencesCall.getString("startHour", null);
            startMin_ = sharedpreferencesCall.getString("startMin", null);
            Duration_ = sharedpreferencesCall.getString("Duration", null);
            relatedLead_ = sharedpreferencesCall.getString("relatedLead", null);
            uuid_ = sharedpreferencesCall.getString("uuid",null);
            long number = Long.parseLong(Duration_);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(number);
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            Duration_=formatter.format(calendar.getTime())+"";
            HttpURLConnection conn = createConnection( content,"POST");
            conn.connect();
            ArrayList<Object> list=new ArrayList<Object>();
            list.add(0,activityName_);
            list.add(1,relatedLead_);
            list.add(2,startDate_);
            list.add(3,startHour_+":"+startMin_);
            list.add(4,Duration_);
            list.add(5,"CALL");
            list.add(6,"answer");
            list.add(7,"opcrmContacted");
            list.add(8,"");
            list.add(9,userId);
            list.add(10,uuid_);
            JSONArray jsArray = new JSONArray(list);
            OutputStream os = conn.getOutputStream();

            String s=jsArray.toString();
            os.write(s.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {

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

         ;

        } catch (IOException e) {



        } catch (Exception e) {

        }

        return null;


    }
    public HttpURLConnection createConnection(String wsPart, String method) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname,passw.toCharArray());
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
    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            testInsert("/ws/it.openia.crm.insertActivity?");

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
    // ---------------------------------- record ----------------------------------
    private class AsyncCallWSRecord extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            // ----- sending a record -----------
            sendRecord("/ws/it.openia.crm.savingRecord?");
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
    public HttpURLConnection createConnectionRecord(String wsPart, String method) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname,passw.toCharArray());
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
    public String sendRecord(String content) {
        try {
            String fileName = sharedpreferencesCall.getString("mFile",null);
            uuid_ = sharedpreferencesCall.getString("uuid",null);

            File uploadFile = new File(fileName);
            HttpURLConnection conn = createConnectionRecord(content, "POST");
            conn.setRequestProperty("pathName", uploadFile.getPath());
            // sending  an id of this object
            conn.setRequestProperty("activityID", uuid_);

            String str = new String(uploadFile.getName().getBytes(), "UTF-8");

            System.out.println(str);
            conn.connect();
            // -------------------------------------------
            ArrayList<Object> list=new ArrayList<Object>();
            list.add(0,str);
            JSONArray jsArray = new JSONArray(list);

            String s=jsArray.toString();
            //-----------------------------------------

            FileInputStream inputStream = new FileInputStream(uploadFile);



            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(s.getBytes());
            // Opens input stream of the file for reading data
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            System.out.println("Start writing data...");

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Data was written.");
            inputStream.close();
            outputStream.flush();
            outputStream.close();

            //-----------------------------------------
            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {

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

}