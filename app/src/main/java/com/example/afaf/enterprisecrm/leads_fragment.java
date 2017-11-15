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

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Leads;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by enterprise on 26/09/16.
 */
public class leads_fragment extends ListFragment {


    String leadid=null;
    JCGSQLiteHelper_Leads db;
    List<leadsModel> list;
    public static boolean dialoglead = true;
    String LeadName;
    String CommertialName="";
    String Phone="";
    String Email="";
    String Comment="";
    String InterestedIn="";
    String AssignedTo="";
    String LeadSource="";
    String Status="";
    String Current_Address="";
    String leadID="";
    String title="";
    String roundid="";
    String projectid="";
    String nationality="";
    String AssignedToID="";
    //------------------- url -------------------------------------------------------------
    public static final String MyPREFERENCES = "MyPrefs" ;
    //  public static final String URL = "nameKey";

    SharedPreferences sharedpreferences;
    String uRl="";
    public static final String LoginPREFERENCES = "LoginPrefs" ;
    public static final String UserName = "username";
    public static final String PassWord = "password";

    SharedPreferences sharedpreferencesLogin;
    String uname="";
    String passw="";
    String userId="";
    String mKey ="";

    SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.leads_fragment, container, false);

        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl= sharedpreferences.getString("URL",null);

        sharedpreferencesLogin = getActivity(). getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
        uname = sharedpreferencesLogin.getString("uName", null);
        passw = sharedpreferencesLogin.getString("passWord", null);
        userId =  sharedpreferencesLogin.getString("userId", null);
        if(mKey!=null) {
            if (!mKey.equals(""))
                list = db.getAllLeads(mKey);
                if(list!=null) {
                    leadsAdapter adapter = new leadsAdapter(getListView().getContext(), list);
                    setListAdapter(adapter);
                }else{
                    list = db.getAllLeads();
                    leadsAdapter adapter = new leadsAdapter(getListView().getContext(), list);
                    setListAdapter(adapter);
                }

        }
        else {
            if (isNetworkAvailable() == true) {
                try {
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();
                    list = db.getAllLeads();
                    if (list != null) {
                        leadsAdapter adapter = new leadsAdapter(getListView().getContext(), list);
                        setListAdapter(adapter);
                    }
                } catch (Exception ex) {

                }
            } else {
                list = db.getAllLeads();
                if (list != null) {
                    leadsAdapter adapter = new leadsAdapter(getListView().getContext(), list);
                    setListAdapter(adapter);
                }
            }
        }

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                        list = db.getAllLeads(mKey);
                        if(list!=null){
                        leadsAdapter adapter = new leadsAdapter(getListView().getContext(), list);
                        setListAdapter(adapter);
                    }

                    if (isNetworkAvailable() == true) {
                        AsyncCallWS task = new AsyncCallWS();
                        task.execute();
                        list = db.getAllLeads();
                        leadsAdapter adapter = new leadsAdapter(getListView().getContext(), list);
                        setListAdapter(adapter);

                    } else {
                        list = db.getAllLeads();
                        leadsAdapter adapter = new leadsAdapter(getListView().getContext(), list);
                        setListAdapter(adapter);

                        if (LoginActivity.arabicflag == false) {
                            Toast.makeText(getActivity(), "Could not update leads", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "تعذر تحديث العملاء المحتملين", Toast.LENGTH_LONG).show();
                        }

                    Main4Activity.openedit=true;


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
    public leads_fragment (String type,String key){
        mKey=key;

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
    public class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        public Void doInBackground(String... params) {
            doTestGetRequestLeads("/ws/it.openia.crm.getlead?leadid="+userId);
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

    // ----------------------------------Start Of Leads ----------------------------------

    public HttpURLConnection createConnectionLeads(String wsPart3, String method3) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname, passw.toCharArray());
            }
        });

        final URL url = new URL(uRl + wsPart3);
        final HttpURLConnection hc2 = (HttpURLConnection) url.openConnection();
        hc2.setRequestMethod(method3);
        hc2.setAllowUserInteraction(false);
        hc2.setDefaultUseCaches(false);
        hc2.setDoOutput(true);
        hc2.setDoInput(true);
        hc2.setInstanceFollowRedirects(true);
        hc2.setUseCaches(false);
        hc2.setRequestProperty("Content-Type", "text/xml");
        return hc2;
    }

    public String doTestGetRequestLeads(String wsPart2) {
        try {
            final HttpURLConnection hc3 = createConnectionLeads(wsPart2, "GET");
            hc3.connect();
            db.onUpgrade(db.getWritableDatabase(),1,2);
            //  final SAXReader sr = new SAXReader();
            final InputStream is2 = hc3.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is2, "UTF-8"));
            String line=reader.readLine();

            JSONArray newarray;
            String s;
            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();

                newarray = new JSONArray(s);

                if(newarray!=null){
                    StringTokenizer st0 = new StringTokenizer(newarray.getString(0),"$");
                    while (st0.hasMoreTokens()) {
                        leadID = st0.nextToken();
                    }
                    StringTokenizer st1 = new StringTokenizer(newarray.getString(1),"$");
                    while (st1.hasMoreTokens()) {
                        Email = st1.nextToken();
                    }
                    StringTokenizer st2 = new StringTokenizer(newarray.getString(2),"$");
                    while (st2.hasMoreTokens()) {
                        Comment = st2.nextToken();
                    }
                    StringTokenizer st3 = new StringTokenizer(newarray.getString(3),"$");
                    while (st3.hasMoreTokens()) {
                        Phone = st3.nextToken();
                    }
                    StringTokenizer st4 = new StringTokenizer(newarray.getString(4),"$");
                    while (st4.hasMoreTokens()) {
                        AssignedTo = st4.nextToken();
                    }
                    StringTokenizer st5 = new StringTokenizer(newarray.getString(5),"$");
                    while (st5.hasMoreTokens()) {
                        LeadSource = st5.nextToken();
                    }
                    StringTokenizer st6 = new StringTokenizer(newarray.getString(6),"$");
                    while (st6.hasMoreTokens()) {
                        CommertialName = st6.nextToken();
                    }
                    StringTokenizer st7 = new StringTokenizer(newarray.getString(7),"$");
                    while (st7.hasMoreTokens()) {
                        Status = st7.nextToken();
                    }
                    StringTokenizer st8 = new StringTokenizer(newarray.getString(8),"$");
                    while (st8.hasMoreTokens()) {
                        title = st8.nextToken();
                    }
                    StringTokenizer st9 = new StringTokenizer(newarray.getString(9),"$");
                    while (st9.hasMoreTokens()) {
                        projectid = st9.nextToken();
                    }
                    StringTokenizer st10 = new StringTokenizer(newarray.getString(10),"$");
                    while (st10.hasMoreTokens()) {
                        roundid = st10.nextToken();
                    }
                    StringTokenizer st11 = new StringTokenizer(newarray.getString(11),"$");
                    while (st11.hasMoreTokens()) {
                        nationality = st11.nextToken();
                    }
                    StringTokenizer st12 = new StringTokenizer(newarray.getString(12),"$");
                    while (st12.hasMoreTokens()) {
                        InterestedIn = st12.nextToken();
                    }
                    StringTokenizer st13 = new StringTokenizer(newarray.getString(13),"$");
                    while (st13.hasMoreTokens()) {
                        AssignedToID = st13.nextToken();
                    }

                }

                db.createLead(CommertialName,Phone,Email,Comment,LeadSource
                    ,Current_Address,AssignedTo,Status,InterestedIn, leadID,title, projectid, roundid, nationality, AssignedToID);
        }


        } catch (final Exception e) {
            //  e.printStackTrace();
        }

        return null;
    }

    // ----------------------------------End Of Leads ----------------------------------------------
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db = new JCGSQLiteHelper_Leads(getActivity());
    }


    // ----------------------------------------------------------------------------------------




}