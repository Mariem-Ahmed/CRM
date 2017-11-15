package com.example.afaf.enterprisecrm;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_cases;
import com.example.afaf.enterprisecrm.Helper_Database.casesModel;

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
public class cases_fragment extends ListFragment {
    JCGSQLiteHelper_cases db;
    List<casesModel> list;
    public static boolean dialogcase = true;

    //* url
    public static final String MyPREFERENCES = "MyPrefs" ;
    //  public static final String URL = "nameKey";

    SharedPreferences sharedpreferences;
    String uRl="";

    String cID=null;
    // -------------------------Cases ----------------------------------------------
    String CaseName;
   // JCGSQLiteHelper_cases dbcase ;
    String deadline="";
    String subName="";
    String priority="";
    String status="";
    String CaseLead="";
    String assignedTo="";
    String timeSpentminutes="";
    String timeSpenthours= "";
    String caseActivity= "";
    String caseActivityId= "";
    public static String caseID="";
    String businessPartner ="";
    public static String caseRelatedLead="";
    public static final String LoginPREFERENCES = "LoginPrefs" ;

    SharedPreferences sharedpreferencesLogin;
    String uname="";
    String passw="";
    String userId="";
    String mKey=null;

    SwipeRefreshLayout swipeContainer;


    public cases_fragment ( ){


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cases_fragment, container, false);
        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl= sharedpreferences.getString("URL",null);
        sharedpreferencesLogin = getActivity().getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
        uname = sharedpreferencesLogin.getString("uName", null);
        passw = sharedpreferencesLogin.getString("passWord", null);

        userId =sharedpreferencesLogin.getString("userId", null);
        if(mKey!=null) {
            if (!mKey.equals(""))
                list = db.getAllCases(mKey);
            if (list != null) {
                casesAdapter adapter = new casesAdapter(getListView().getContext(), list);
                setListAdapter(adapter);
            } else {
                list = db.getAllCases();
                casesAdapter adapter = new casesAdapter(getListView().getContext(), list);
                setListAdapter(adapter);
            }

        }
        else {
                if (isNetworkAvailable() == true) {
                    try {
                        AsyncCallWS task = new AsyncCallWS();
                        task.execute();
                        list = db.getAllCases();
                        if(list!=null) {
                            casesAdapter adapter = new casesAdapter(getListView().getContext(), list);
                            setListAdapter(adapter);
                        }
                    }catch (Exception ex){

                    }
                } else {
                    list = db.getAllCases();
                    casesAdapter adapter = new casesAdapter(getListView().getContext(), list);
                    setListAdapter(adapter);
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
                    list = db.getAllCases();
                    casesAdapter adapter = new casesAdapter(getListView().getContext(), list);
                    setListAdapter(adapter);
                    cases.updateflag=false;

                } else {

                    list = db.getAllCases();
                    casesAdapter adapter = new casesAdapter(getListView().getContext(), list);
                    setListAdapter(adapter);
                    if (LoginActivity.arabicflag==false) {
                        Toast.makeText(getActivity(), "Could not update cases", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getActivity(), "تعذر تحديث الحالات", Toast.LENGTH_LONG).show();
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
    @SuppressLint("ValidFragment")
    public cases_fragment (String type,String key){

        mKey=key;
//    db = new JCGSQLiteHelper(context);
//    list = db.getAllActivities(type);
//    ActivtyAdapter adapter = new ActivtyAdapter(context, list, db);
//    setListAdapter(adapter);
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db = new JCGSQLiteHelper_cases(getActivity());
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------Start Of Cases ----------------------------------
    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            doTestGetRequestCases("/ws/it.openia.crm.getcase?userid="+userId);
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
    public HttpURLConnection createConnectionCases(String wsPart2, String method2) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname, passw.toCharArray());
            }
        });

        final URL url = new URL(uRl + wsPart2);
        final HttpURLConnection hc2 = (HttpURLConnection) url.openConnection();
        hc2.setRequestMethod(method2);
        hc2.setAllowUserInteraction(false);
        hc2.setDefaultUseCaches(false);
        hc2.setDoOutput(true);
        hc2.setDoInput(true);
        hc2.setInstanceFollowRedirects(true);
        hc2.setUseCaches(false);
        hc2.setRequestProperty("Content-Type", "text/xml");
        return hc2;
    }
    public String doTestGetRequestCases(String wsPart2) {
        try {


            final HttpURLConnection hc2 = createConnectionCases(wsPart2, "GET");
            hc2.connect();
            //  final SAXReader sr = new SAXReader();
            final InputStream is2 = hc2.getInputStream();
            final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is2, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            //);
            converterCases(sb.toString());


        } catch (final Exception e) {

        }

        return null;
    }
    public void converterCases(String content2)throws XmlPullParserException, IOException {
        db.onUpgrade(db.getWritableDatabase(),1,2);
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        //  Log.d("ccc",content2);
        xpp.setInput(new StringReader(content2));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {

            }  else if (eventType == XmlPullParser.TEXT) {

                if (CaseName.equals("caseStatus") && !xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();

                    status = xpp.getText();

                }

                if (CaseName.equals("id") && !xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();
                    caseID = xpp.getText();

                }
                if (CaseName.equals("deadlineDate") && !xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();
                    deadline = xpp.getText();


                }
                if (CaseName.equals("casePriority") && !xpp.getText().startsWith("\n")) {
                    //  xx[i] = xpp.getText();
                    priority = xpp.getText();


                }
                if (CaseName.equals("caseSubject") && !xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();
                    subName = xpp.getText();
                    //  System.out.print(subName +" caseee");

                }
                if (CaseName.equals("timeSpenthours") && !xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();
                    timeSpenthours = xpp.getText();

                } if (CaseName.equals("timeSpentminutes") && !xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();
                    timeSpentminutes = xpp.getText();

                }
               // opcrmActivity



            }else if (eventType == XmlPullParser.START_TAG) {
                CaseName = xpp.getName();

                if (CaseName.startsWith("assignedTo")) {
                    if (xpp.getAttributeCount()!=1) {
                        assignedTo = xpp.getAttributeValue(0);
                    }else {
                        assignedTo = null;
                    }
                }

                if (CaseName.startsWith("relatedLead")) {
                    if (xpp.getAttributeCount()!=1) {
                        CaseLead = xpp.getAttributeValue(2);
                        caseRelatedLead = xpp.getAttributeValue(0);
                    }else {
                        CaseLead = null;
                        caseRelatedLead = null;
                    }


                }
                if (CaseName.startsWith("opcrmActivity")) {

                    if (xpp.getAttributeCount()!=1) {
                        caseActivity = xpp.getAttributeValue(2);
                        caseActivityId=xpp.getAttributeValue(0);

                    }else{
                        caseActivity = "";
                        caseActivityId="";

                    }
                    db.createCases(assignedTo, CaseLead, businessPartner, priority, subName
                            , caseID, deadline, status, caseRelatedLead,
                            timeSpenthours, timeSpentminutes, caseActivity, caseActivityId);


                }


            }
            eventType = xpp.next();
        }



    }
    // ----------------------------------End Of cases ------------------------------------

}
