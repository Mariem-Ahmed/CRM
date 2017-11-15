package com.example.afaf.enterprisecrm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;


/**
 * Created by enterprise on 27/09/16.
 */
public class activity_fragment extends ListFragment {


    List<ActivityModel> list;
    JCGSQLiteHelper db;
    String activityName;
    String activitySubject = "";
    String activityStartdate = "";
    String startHour = "";
    String startMinute = "";
    String durationHours = "";
    String activityType = "";
    String activityStatus = "";
    String relatedLead = "";
    String leadStatus = "";
    String description="";
    String assigeTo="";

    String mType="";
    String mKey=null;
    public static String activityId = "";
    public static String actRelatedLead = "";
    public static boolean dialogactivity = true;

   public static String actID=null;

    public static String LID=null;
    //* url
    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;
    String uRl="";
    // login
    public static final String LoginPREFERENCES = "LoginPrefs" ;
    public static final String UserName = "username";
    public static final String PassWord = "password";
    SharedPreferences sharedpreferencesLogin;
    String uname="";
    String passw="";
    String userId="";

    SwipeRefreshLayout swipeContainer;

    public activity_fragment ( ){


    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.act_fragment, container, false);

        return v;
    }

    // ---------------------------------------------------------------------------------------
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedpreferencesLogin = getActivity().getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
        uname = sharedpreferencesLogin.getString("uName", null);
        passw = sharedpreferencesLogin.getString("passWord", null);
        userId = sharedpreferencesLogin.getString("userId", null);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl= sharedpreferences.getString("URL",null);
        if(!mType.equals("")){
            if(isNetworkAvailable()==true){
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
                list = db.getAllActivities(mType);
                ActivtyAdapter adapter = new ActivtyAdapter(getListView().getContext(), list, db);
                setListAdapter(adapter);
                Main5Activity.openedit=true;
                Main5Activity.editflag=false;
            }
            else{
                list = db.getAllActivities(mType);
                ActivtyAdapter adapter = new ActivtyAdapter(getListView().getContext(), list, db);
                setListAdapter(adapter);
                Main5Activity.openedit=true;
                Main5Activity.editflag=false;
            }

        }else {
            if (isNetworkAvailable() == true) {
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
                list = db.getAllActivities();
                ActivtyAdapter adapter = new ActivtyAdapter(getListView().getContext(), list, db);
                setListAdapter(adapter);
                Main5Activity.openedit=true;
                Main5Activity.editflag=false;

            } else {
                list = db.getAllActivities();
                ActivtyAdapter adapter = new ActivtyAdapter(getListView().getContext(), list, db);
                setListAdapter(adapter);
                Main5Activity.openedit=true;
                Main5Activity.editflag=false;
            }

        }
        if(mKey!=null){
            if(!mKey.equals(""))
                try {
                    list = db.getActivitiesByKey(mKey);
                    ActivtyAdapter adapter = new ActivtyAdapter(getListView().getContext(), list, db);
                    setListAdapter(adapter);
                    Main5Activity.openedit=true;
                    Main5Activity.editflag=false;
                }catch(Exception e){

                }
        }


        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (isNetworkAvailable() == true) {
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();
                    list = db.getAllActivities();
                    ActivtyAdapter adapter = new ActivtyAdapter(getListView().getContext(), list, db);
                    setListAdapter(adapter);
                    Main5Activity.openedit=true;
                    Main5Activity.editflag=false;

                } else {
                    // Configure the refreshing colors

                    list = db.getAllActivities();
                    ActivtyAdapter adapter = new ActivtyAdapter(getListView().getContext(), list, db);
                    setListAdapter(adapter);
                    Main5Activity.openedit=true;
                    Main5Activity.editflag=false;

                    if (LoginActivity.arabicflag==false) {
                        Toast.makeText(getActivity(), "Could not update activities", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getActivity(), "تعذر تحديث الانشطة", Toast.LENGTH_LONG).show();
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(false);
                    }
                }, 2000);

            }
        });


        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


        @Override
    public void onResume() {

        super.onResume();


    }
    @SuppressLint("ValidFragment")
    public activity_fragment (String type,String key){

            dialogactivity = true;
            mType=type;
            mKey=key;
//    db = new JCGSQLiteHelper(context);
//    list = db.getAllActivities(type);
//    ActivtyAdapter adapter = new ActivtyAdapter(context, list, db);
//    setListAdapter(adapter);
        }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        int idint= (int) (id+1);
        ActivityModel model = null;
        try {
            model = db.readActivity(idint);
        } catch (JSONException e) {

        }
       //  int id1= model.getId();
        String subName = model.getActivitySubject();
        String startdate = model.getActivityStartdate();
        String hour = model.getStartHour();
        String minute = model.getStartMinute();
        String duration = model.getDurationHours();
        String acttype = model.getActivityType();
        String actstatus = model.getActivityStatus();
        String relatedlead = model.getRelatedLead();
        actID= model.getActivityId();
        LID= model.getActRelatdLead();
        String lStatus = model.getLeadStatus();
        String desc = model.getDescription();
        String ass = model.getAssigeTo();


        Intent i = new Intent(getActivity(), Main5Activity.class);
        i.putExtra("id", idint +"");
      //  i.putExtra("id",id1);
        i.putExtra("subName", subName);
        i.putExtra("startDate", startdate);
        i.putExtra("hour", hour);
        i.putExtra("minute", minute);
        i.putExtra("duration", duration);
        i.putExtra("acttype", acttype);
        i.putExtra("actstatus", actstatus);
        i.putExtra("relatedlead", relatedlead);
        i.putExtra("activityId",actID);
        i.putExtra("LeadId",LID);
        i.putExtra("leadStatus",lStatus);
        i.putExtra("description",desc);
        i.putExtra("AssignedTo",ass);

        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db = new JCGSQLiteHelper(getActivity());
    }



    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            doTestGetRequest("/ws/it.openia.crm.getactivity?userid="+userId);
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

    // -------------------------------------Start of spinners------------------------------------------

    public HttpURLConnection createConnection(String wsPart, String method) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname, passw.toCharArray());
            }
        });

        final URL url = new URL(uRl+ wsPart);
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
    public String doTestGetRequest(String wsPart) {
        try {


            final HttpURLConnection hc = createConnection(wsPart, "GET");
            hc.connect();
            //  final SAXReader sr = new SAXReader();
            final InputStream is = hc.getInputStream();
            final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            converter(sb.toString());
            // -----------------------------------------------------------------------------------------

        } catch ( Exception e) {

        }

        return null;
    }
    public void converter(String content)throws XmlPullParserException, IOException {
            db.onUpgrade(db.getWritableDatabase(), 1, 2);
        int count = 0;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            // Log.d("aaa",content);

            xpp.setInput(new StringReader(content));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
               if (eventType == XmlPullParser.START_TAG) {
                    activityName = xpp.getName();
                    if (activityName.equals("activityStatus") ) {
                        //   xx[i] = xpp.getText();

                        if (xpp.getAttributeCount()!=1) {
                            activityStatus = xpp.getAttributeValue(0);
                        }else{
                            activityStatus = null;
                        }


                    }
                    if (activityName.startsWith("relatedLead")) {

                        if (xpp.getAttributeCount()!=1) {
                            relatedLead = xpp.getAttributeValue(2);
                            actRelatedLead = xpp.getAttributeValue(0);

                        }else{
                            relatedLead = "";
                            actRelatedLead="";

                        }

                    }

                    if (activityName.startsWith("assignedTo")) {

                        if (xpp.getAttributeCount()!=1) {
                            assigeTo = xpp.getAttributeValue(0);
                        }else{
                            assigeTo = null;
                        }
                    }


                }

                else if (eventType == XmlPullParser.TEXT) {
                    if (activityName.equals("id") && !xpp.getText().startsWith("\n")) {

                        if (!xpp.getText().startsWith("\n")) {
                            activityId = xpp.getText();
                        }else{
                            activityId = "";
                        }
                    }
                    if (activityName.equals("activitySubject") && !xpp.getText().startsWith("\n")) {
                        //  xx[i] = xpp.getText();


                        if (!xpp.getText().startsWith("\n")) {
                            activitySubject = xpp.getText();
                        }else{
                            activitySubject = "";
                        }

                    }
                    if (activityName.equals("activityStartdate") && !xpp.getText().startsWith("\n")) {
                        //   xx[i] = xpp.getText();
                        if (!xpp.getText().startsWith("\n")) {
                            activityStartdate = xpp.getText();
                        }else{
                            activityStartdate = "";
                        }


                    }
                    if (activityName.equals("startHour") && !xpp.getText().startsWith("\n")) {
                        //   xx[i] = xpp.getText();

                        if (!xpp.getText( ).startsWith("\n")) {
                            startHour = xpp.getText();
                        }else{
                            startHour = "0";
                        }

                    }
                    if (activityName.equals("startMinute") && !xpp.getText().startsWith("\n")) {

                        if (!xpp.getText().startsWith("\n")) {
                            startMinute = xpp.getText();
                        }else{
                            startMinute = "0";
                        }

                    }


                    if (activityName.equals("activityType") && !xpp.getText().startsWith("\n")) {
                        //   xx[i] = xpp.getText();

                        if (!xpp.getText().startsWith("\n")) {
                            activityType = xpp.getText();
                        }else{
                            activityType = "";
                        }


                    }

                    if (activityName.equals("durationHours") && !xpp.getText().startsWith("\n")) {
                        //   xx[i] = xpp.getText();

                        if (!xpp.getText().startsWith("\n")) {
                            durationHours = xpp.getText();
                        }else{
                            durationHours = "";
                        }

                    }
                   if (activityName.equals("durationMinutes") && !xpp.getText().startsWith("\n")) {
                       //   xx[i] = xpp.getText();

                       if (!xpp.getText().startsWith("\n")) {
                           durationHours +=":"+ xpp.getText();
                       }

                   }

                    if (activityName.equals("leadstatus") ) {
                        //   xx[i] = xpp.getText();
                      //  leadStatus = xpp.getText();   xpp.getAttributeCount()!=0 ||
                        if (!xpp.getText().startsWith("\n")) {
                            leadStatus = xpp.getText();
                        }else{
                            leadStatus = "";
                        }

                    }

                    if (activityName.equals("description") ) {
                        //   xx[i] = xpp.getText();
                        //xpp.getAttributeCount()!=0 ||

                            if (!xpp.getText().startsWith("\n")) {
                                description = xpp.getText();
                            } else {
                                description = "";
                            }

                            db.createActivity(activitySubject, activityStartdate, startHour
                                    , startMinute, durationHours, activityType, activityStatus,
                                    relatedLead, activityId, actRelatedLead, leadStatus, description, assigeTo);
                        activityName = "";


                    }
                }


                eventType = xpp.next();
            }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

