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
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Oppo;
import com.example.afaf.enterprisecrm.Helper_Database.opportunityModedel;

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
/**
 * Created by enterprise on 27/09/16.
 */
public class oppotunity_fragment extends ListFragment {

    String oppid= null;
    String oppleadid= null;
    String oppAssignedToid= null;
    String mKey="";

    JCGSQLiteHelper_Oppo db;
    List<opportunityModedel> list;
    public static boolean dialogoppo = true;
    String oppoName;
    String oppoSubject="";
    String oppoCloseDate="";
    String oppoAmount="";
    String oppoProbablity="";
    String opporelatedLead="";
    String oppoAssignedTo="";
    String oppoAssignedToID="";
    public  static String oppoID="";
    public  static String oppoRelatedLeadID="";
    //* url
    public static final String MyPREFERENCES = "MyPrefs" ;
    //  public static final String URL = "nameKey";

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.oppo_fragment, container, false);
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
             list = db.getAllOpportunity(mKey);
                 if(list!=null) {
                     OppoAdapter adapter = new OppoAdapter(getListView().getContext(), list, db);
                     setListAdapter(adapter);
                 }
            else{
                     list = db.getAllOpportunity();
                     OppoAdapter adapter = new OppoAdapter(getListView().getContext(), list, db);
                     setListAdapter(adapter);
                 }

        }
        else {
            if (isNetworkAvailable() == true) {
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
                list = db.getAllOpportunity();
                if(list!=null) {
                    OppoAdapter adapter = new OppoAdapter(getListView().getContext(), list, db);
                    setListAdapter(adapter);
                }
            } else {
                list = db.getAllOpportunity();
                if(list!=null) {
                    OppoAdapter adapter = new OppoAdapter(getListView().getContext(), list, db);
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


                    if (isNetworkAvailable() == true) {
                        AsyncCallWS task = new AsyncCallWS();
                        task.execute();
                        list = db.getAllOpportunity();
                        OppoAdapter adapter = new OppoAdapter(getListView().getContext(), list, db);
                        setListAdapter(adapter);


                    } else {
                        list = db.getAllOpportunity();
                        OppoAdapter adapter = new OppoAdapter(getListView().getContext(), list, db);
                        setListAdapter(adapter);

                        if (LoginActivity.arabicflag == false) {
                            Toast.makeText(getActivity(), "Could not update opportunities", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "تعذر تحديث الفرص", Toast.LENGTH_LONG).show();
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
    public oppotunity_fragment (String type,String key){

        mKey=key;

    }

    @Override
    public void onResume() {



        super.onResume();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
            int idint= (int) (id+1);
            opportunityModedel model = db.readOpportunity(idint);

            String subName= model.getOppoSubject();
        String closedate= model.getOppoCloseDate();
        String amount= model.getOppoAmount();
        String probablity = model.getOppoProbablity();
        String relatedLead = model.getOppoRelatedLead();
        String assignto = model.getOppoAssignedto();
        oppid= model.getOppoID();
        oppleadid= model.getOppoRelatedLeadID();
        oppAssignedToid= model.getOppoAssignedToID();

        Intent i = new Intent(getActivity(),opportunity.class);
            i.putExtra("iddd",model.getId()+"");
        i.putExtra("subName",subName);
        i.putExtra("closedate",closedate);
        i.putExtra("amount",amount);
        i.putExtra("probablity",probablity);
        i.putExtra("relatedLead",relatedLead);
        i.putExtra("assignto",assignto);
        i.putExtra("oppleadid",oppleadid);
        i.putExtra("oppid",oppid);
        i.putExtra("oppAssignedToid",oppAssignedToid);
        MainActivity. listFlag=true;
      MainActivity.opportunityflag= true;

        startActivity(i);
            getActivity().finish();
        }catch (Exception ex){

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db = new JCGSQLiteHelper_Oppo(getActivity());
    }
    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            doTestGetRequestOppo("/ws/it.openia.crm.getopportunity?userid="+userId);
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

    public HttpURLConnection createConnectionoppo(String wsPart1, String method1) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname, passw.toCharArray());
            }
        });

        final URL url = new URL(uRl+ wsPart1);
        final HttpURLConnection hc1 = (HttpURLConnection) url.openConnection();
        hc1.setRequestMethod(method1);
        hc1.setAllowUserInteraction(false);
        hc1.setDefaultUseCaches(false);
        hc1.setDoOutput(true);
        hc1.setDoInput(true);
        hc1.setInstanceFollowRedirects(true);
        hc1.setUseCaches(false);
        hc1.setRequestProperty("Content-Type", "text/xml");
        return hc1;
    }
    public String doTestGetRequestOppo(String wsPart1) {
        try {
            final HttpURLConnection hc1 = createConnectionoppo(wsPart1, "GET");
            hc1.connect();
            //  final SAXReader sr = new SAXReader();
            final InputStream is1 = hc1.getInputStream();
            final StringBuilder sb1 = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb1.append(line).append("\n");
            }
            //);
            converterOppo(sb1.toString());


        } catch (final Exception e) {

        }

        return null;
    }
    public void converterOppo(String content1)throws XmlPullParserException, IOException {
        db.onUpgrade(db.getWritableDatabase(),1,2);

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        //   Log.d("ttt",content1);

        xpp.setInput( new StringReader( content1) );
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {

            }  else if(eventType == XmlPullParser.TEXT) {


                if (oppoName.equals("expectedCloseDate")  && !xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();
                        oppoCloseDate=xpp.getText();

                }
                if (oppoName.equals("opportunityAmount") && !xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();

                        oppoAmount =xpp.getText();

                }
                if (oppoName.equals("probability") && !xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();

                        oppoProbablity=xpp.getText();


                }
                if (oppoName.equals("id") && !xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();

                        oppoID=xpp.getText();


                }
                if (oppoName.equals("opportunityName")  && !xpp.getText().startsWith("\n")) {
                    //  xx[i] = xpp.getText();
                     oppoSubject=xpp.getText();

                }

            }else if(eventType == XmlPullParser.START_TAG) {
                oppoName =xpp.getName();

                if (oppoName.startsWith("assignedTo")) {
                    if (xpp.getAttributeCount()!=1) {
                        oppoAssignedTo = xpp.getAttributeValue(2);
                        oppoAssignedToID = xpp.getAttributeValue(0);
                    }else {
                        oppoAssignedTo = null;
                        oppoAssignedToID = null;
                    }
                }
                if (oppoName.startsWith("relatedLead")) {
                    if (xpp.getAttributeCount()!=1) {
                        opporelatedLead = xpp.getAttributeValue(2);
                        oppoRelatedLeadID = xpp.getAttributeValue(0);

                    }else{
                        opporelatedLead = null;
                        oppoRelatedLeadID = null;

                    }
                    db.createOpportunity(oppoSubject,oppoCloseDate,oppoAmount,oppoProbablity,opporelatedLead,oppoAssignedTo, oppoID, oppoRelatedLeadID , oppoAssignedToID);

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
