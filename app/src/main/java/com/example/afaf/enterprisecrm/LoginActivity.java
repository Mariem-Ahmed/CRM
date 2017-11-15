package com.example.afaf.enterprisecrm;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Leads;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Oppo;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_cases;

import org.json.JSONArray;
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
import java.util.Locale;
import java.util.StringTokenizer;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Object> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    public static Boolean sendFlag, recieveflag, arabicflag=false;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mNameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    Boolean loginFlag=false;
//    final String AuthuserName ="Openbravo";
//    final String Authpassword = "openbravo";
Button englishButton, arabicButton;
    Locale myLocale;



    public static final String LoginPREFERENCES = "LoginPrefs" ;
    public static  String name = "";
    public static  String password = "";

    SharedPreferences sharedpreferencesLogin;
    JCGSQLiteHelper db;
    JCGSQLiteHelper_Leads db1;
    JCGSQLiteHelper_cases db2;
    JCGSQLiteHelper_Oppo db3;


    public static final String MyPREFERENCES = "MyPrefs" ;
    //  public static final String URL = "nameKey";

    SharedPreferences sharedpreferences;
    String uRl="";
    String activityName = "";
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
    public static String activityId = "";
    public static String actRelatedLead = "";
    // leads
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
    // cases
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
    // opp
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new JCGSQLiteHelper(this);
        db1= new JCGSQLiteHelper_Leads(this);
        db2= new JCGSQLiteHelper_cases(this);
        db3 = new JCGSQLiteHelper_Oppo(this);
        // Set up the login form.
        mNameView = (AutoCompleteTextView) findViewById(R.id.userName);
        populateAutoComplete();
        // url
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl= sharedpreferences.getString("URL",null);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();


            }
        });


        // ----------------------------language -----------------------------------
        englishButton = (Button) findViewById(R.id.english_button);
        englishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setLocale("en");
                arabicflag=false;
                finish();

            }
        });


        arabicButton = (Button) findViewById(R.id.arabic_button);
        arabicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setLocale("ar");

                arabicflag=true;
                finish();



            }
        });

        // -------------------------------------------------------------------------

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, LoginActivity.class);
        startActivity(refresh);
    }

    @Override
    public void onBackPressed() {

    }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mNameView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
         name = mNameView.getText().toString();
        password = mPasswordView.getText().toString();

        sharedpreferencesLogin = getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        } else if (!isNameValid(name)) {
            mNameView.setError(getString(R.string.error_invalid_email));
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            int counter = 0;
            counter++;

      //  if(name==AuthuserName && password==Authpassword) {

            AsyncCallWS t = new AsyncCallWS(name, password);
            t.execute();
            if(!name.isEmpty()){
            if (loginFlag == true) {
                showProgress(true);
                SharedPreferences.Editor editor = sharedpreferencesLogin.edit();
                editor.putString("uName", name);
                editor.putString("passWord", password);
                editor.commit();
                AsyncCall task = new AsyncCall();
                task.execute();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();

            }else {

                if (counter == 2) {
                    if (LoginActivity.arabicflag == false) {
                        new AlertDialog.Builder(this)
                                .setTitle("Warning")
                                .setMessage("Server Is Offline ! Or Password IS Incorrect ! Or UserName IS Incorrect !")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                })
                                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent1 = new Intent(getApplicationContext(), inserturl_noparent.class);
                                        startActivity(intent1);
                                        finish();

                                    }
                                })
                                .show();
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("تحذير")
                                .setMessage("تعذر الاتصال بالخادم ! أو كلمة المرور غير صحيحه ! أو اسم المستخدم غير صحيح !")
                                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                })
                                .setNegativeButton("رجوع", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent1 = new Intent(getApplicationContext(), inserturl_noparent.class);
                                        startActivity(intent1);
                                        finish();

                                    }
                                })
                                .show();
                    }
                }
            }
        }
            else{
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                loginFlag=false;
            }

        }

    }

    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        boolean valid = true;
        if (name.isEmpty() || name.length() < 3) {
            mNameView.setError("at least 3 characters");
            valid = false;
        } else {
            mNameView.setError(null);
        }

        return valid;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        boolean valid = true;

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordView.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPasswordView.setError(null);
        }

        return valid;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    public class AsyncCallWS extends AsyncTask<Void, Void, Boolean> {

        private final String musername;
        private final String mPassword;

        AsyncCallWS(String username, String password) {
            musername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            HttpURLConnection hc = null;
            try {
                // Simulate network access.
                hc = createConnection();
                hc.connect();
                final InputStream is = hc.getInputStream();
                final StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {

                    sb.append(line).append("\n");
                }

                converter(sb.toString());
                // -----------------------------------------------------------------------------------------
                loginFlag=true;


        } catch (InterruptedException e) {
                return false;
            } catch (IOException e) {

            } catch (Exception e) {

            }



            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }
    public HttpURLConnection createConnection() throws Exception {

        Authenticator.setDefault(new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(name,password.toCharArray());
            }
        });

        final URL url = new URL(uRl+"/ws/it.openia.crm.getuser?username="+name);
        final HttpURLConnection hc = (HttpURLConnection) url.openConnection();
        hc.setRequestMethod("POST");
        hc.setAllowUserInteraction(false);
        hc.setDefaultUseCaches(false);
        hc.setDoOutput(true);
        hc.setDoInput(true);
        hc.setInstanceFollowRedirects(true);
        hc.setUseCaches(false);
        hc.setRequestProperty("Content-Type", "text/xml");
      //  loginFlag=true;

        return hc;
    }
    public void converter(String content)throws XmlPullParserException, IOException {
        String activityName = null;
        String userId;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        // Log.d("aaa",content);

        xpp.setInput(new StringReader(content));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                 activityName = xpp.getName();
                if (activityName.equals("ADUser") ) {
                    userId = xpp.getAttributeValue(0);
                    SharedPreferences.Editor editor = sharedpreferencesLogin.edit();
                    editor.putString("userId", userId);
                    editor.commit();

                  break;
                }

            }

            eventType = xpp.next();
        }
    }
    private class AsyncCall extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            sharedpreferencesLogin = getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
            String  userId = sharedpreferencesLogin.getString("userId", null);
            doTestGetRequest("/ws/it.openia.crm.getactivity?userid="+userId);
            doTestGetRequestLeads("/ws/it.openia.crm.getlead?leadid="+userId);
            doTestGetRequestCases("/ws/it.openia.crm.getcase?userid="+userId);
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

    // -------------------------------------Start of spinners------------------------------------------

    public HttpURLConnection createConnection(String wsPart, String method) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(name, password.toCharArray());
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
            converter1(sb.toString());
            // -----------------------------------------------------------------------------------------

        } catch ( Exception e) {

        }

        return null;
    }
    public void converter1(String content)throws XmlPullParserException, IOException {
        db.onUpgrade(db.getWritableDatabase(), 1, 2);
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
                        startHour = "";
                    }

                }
                if (activityName.equals("startMinute") && !xpp.getText().startsWith("\n")) {

                    if (!xpp.getText().startsWith("\n")) {
                        startMinute = xpp.getText();
                    }else{
                        startMinute = "";
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
                    activityName="";

                }
                //if(activityName.equals("activitySubject") || activityName.startsWith("relatedLead"))

            }


            eventType = xpp.next();
        }

    }
/// leads
public HttpURLConnection createConnectionLeads(String wsPart3, String method3) throws Exception {
    Authenticator.setDefault(new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(name, password.toCharArray());
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
        db1.onUpgrade(db1.getWritableDatabase(),1,2);
        //  final SAXReader sr = new SAXReader();
        final InputStream is2 = hc3.getInputStream();
        final StringBuilder sb = new StringBuilder();
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

            db1.createLead(CommertialName,Phone,Email,Comment,LeadSource
                    ,Current_Address,AssignedTo,Status,InterestedIn, leadID,title,
                    projectid, roundid, nationality, AssignedToID);
        }


    } catch (final Exception e) {
        //  e.printStackTrace();
    }

    return null;
}
/// cases
public HttpURLConnection createConnectionCases(String wsPart2, String method2) throws Exception {
    Authenticator.setDefault(new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(name, password.toCharArray());
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
        db2.onUpgrade(db2.getWritableDatabase(),1,2);
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
                        caseActivity = null;
                        caseActivityId=null;

                    }
                    db2.createCases(assignedTo, CaseLead, businessPartner, priority, subName
                            , caseID, deadline, status, caseRelatedLead,
                            timeSpenthours, timeSpentminutes, caseActivity, caseActivityId);


                }


            }
            eventType = xpp.next();
        }



    }
    /// opportunities

    public HttpURLConnection createConnectionoppo(String wsPart1, String method1) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(name, password.toCharArray());
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
        db3.onUpgrade(db3.getWritableDatabase(),1,2);
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
                    db3.createOpportunity(oppoSubject,oppoCloseDate,oppoAmount,oppoProbablity,opporelatedLead,oppoAssignedTo, oppoID, oppoRelatedLeadID , oppoAssignedToID);

                }


            }
            eventType = xpp.next();
        }


    }
}

