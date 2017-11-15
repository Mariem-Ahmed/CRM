package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;
import com.example.afaf.enterprisecrm.Helper_Database.activity_Status_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.leadStatus_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.activity_Status_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.leadStatus_spinner_model;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by enterprise on 02/02/17.
 */

public class activity_info  extends Fragment {
    public static ActivityModel Em= new ActivityModel();

    // ---------------------------------
    public static String  actID= null;
    public static   String subName;
  //  public  static boolean activiyid= false;

    //------------------- url -------------------------------------------------------------
    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;
    String uRl="";
    // ----------------------- log in ------------------------------------------------------
    public static final String LoginPREFERENCES = "LoginPrefs" ;

    SharedPreferences sharedpreferencesLogin;
    String uname="";
    String passw="";
    String userId="";
    // ----------------------------call record -----------------------------------------

    public static leadsModel l= null;
    android.media.MediaRecorder m_recorder;
    TelephonyManager t_manager ;
    PhoneStateListener p_listener ;

    private static String idName;
    static final int BUFFER_SIZE = 4096;

    public boolean wasRinging = false;
    public boolean recordstarted = false;



    // ------------------------------------------------------------------------------------
   public static EditText sub ,leadN,type,actStatus,desc, startdate, starttime, durationtime, assignedto,leadstatus;
    ImageView telephone, mail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {// TODO Auto-generated method stub
        View rootview = inflater.inflate(R.layout.activity_info, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //-----------------------------url &  login  ------------------------------------------
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl = sharedpreferences.getString("URL", null);
        if (uRl != null) {
            sharedpreferencesLogin = getActivity().getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
            uname = sharedpreferencesLogin.getString("uName", null);
            passw = sharedpreferencesLogin.getString("passWord", null);
            userId = sharedpreferencesLogin.getString("userId", null);
            if (uname == null && passw == null) {
                Intent i1 = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(i1);
                getActivity().finish();

            }
        } else {
            Intent i = new Intent(getActivity().getApplicationContext(), insertUrl.class);
            startActivity(i);
            getActivity().finish();
        }

        // --------------------------------------------------------------------------
        sub = (EditText) view.findViewById(R.id.editText4);
        leadN = (EditText) view.findViewById(R.id.editText3);
        type = (EditText) view.findViewById(R.id.editText2);
        actStatus = (EditText) view.findViewById(R.id.editText1);
        startdate = (EditText) view.findViewById(R.id.editText5);

        starttime = (EditText) view.findViewById(R.id.editText8);
        assignedto = (EditText) view.findViewById(R.id.editText7);
        durationtime = (EditText) view.findViewById(R.id.editText9);
        leadstatus = (EditText) view.findViewById(R.id.editText6);
        desc = (EditText) view.findViewById(R.id.editText22);

// ----------------------- activity type -----------------------------------


        Intent i = getActivity().getIntent();
        idName = i.getStringExtra("activityId");
        actID = idName;


        List<ActivityModel> emlist;
        ActivityModel em = null;
        JCGSQLiteHelper dbAct = new JCGSQLiteHelper(getActivity());
        emlist = dbAct.getActivityWithActivityID(idName);

        if (Main5Activity.editflag == true) {

            type.setText(acitvity.actspinner.getSelectedItem() + "");

            actStatus.setText(acitvity.statusspinner.getSelectedItem() + "");

            leadN.setText(acitvity.relead.getSelectedItem() + "");

            leadstatus.setText(acitvity.leadstatusSpinner.getSelectedItem() + "");

            sub.setText(acitvity.actname.getText() + "");

            desc.setText(acitvity.desc.getText() + "");


            startdate.setText(acitvity.startdate.getText() + "");

            starttime.setText(acitvity.starttime.getText() + "");

            durationtime.setText(acitvity.durationtime.getText() + "");


        } else if (emlist.size() > 0) {

            em = emlist.get(0);

            String acttype = em.getActivityType();
            type.setText(acttype);


// ------------------------------- activity status spinner--------------------------------------------------------------------
            String actstatus = em.getActivityStatus();
            if(actstatus!=null&&actstatus!="") {
                activity_Status_spinner_helper helper = new activity_Status_spinner_helper(getActivity());
                List<activity_Status_spinner_model> list = helper.getAllActivityStatus(actstatus);
                if (list.size() != 0)
                    actStatus.setText(list.get(0).getActivityStatusname());
                else actStatus.setText("");
            }else actStatus.setText("");


// ------------------------------- related Lead spinner--------------------------------------------------------------------
            String leadName = em.getRelatedLead();
            leadN.setText(leadName);

// ------------------------------- related Lead status spinner--------------------------------------------------------------------
            String leadStatus = em.getLeadStatus();
            leadStatus_spinner_helper db = new leadStatus_spinner_helper(getActivity());
            leadStatus_spinner_model leadStatusLists1 = null;
            for (int ii = 0; ii <db.getAllLeadStatus().size(); ii++) {
                try {
                    leadStatusLists1= db.readLeadStatusSpinner(ii+1);
                } catch (JSONException e) {

                }
                if (leadStatusLists1.getLeadStatusValue().equals(leadStatus)) {
                    leadstatus.setText(leadStatusLists1.getLeadStatusName());
                    break;
                }
            }


// --------------------------------------- set data -----------------------------
            subName = em.getActivitySubject();
            sub.setText(subName);

            String description = em.getDescription() + "";
            desc.setText(description);


            String sDate = em.getActivityStartdate();

            if (!sDate.isEmpty()) {

                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                Date d = null;
                try {
                    d = output.parse(sDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millisecond = d.getTime();

                String currentDate = getDate(millisecond, "yyyy/MM/dd");

                startdate.setText(currentDate);
            } else {
                startdate.setText(sDate);
            }


            String houemin = em.getStartHour();
            String min = em.getStartMinute();

            if (houemin!=null && min!=null) {
                starttime.setText(houemin + ":" + min);
            } else {
                starttime.setText("0:0");
            }


            String dura = em.getDurationHours();
            durationtime.setText(dura);

        }
    }


    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

        }


