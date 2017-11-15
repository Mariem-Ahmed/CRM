package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by enterprise on 05/11/16.
 */

public class insertUrl extends AppCompatActivity {


    EditText url;
    Button btn;

    public static boolean arflagURL, enflag=false;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String URL = "URL";

    SharedPreferences sharedpreferences;

    Button englishButton, arabicButton;
    Locale myLocale;

    String uRl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserturl);
        setTitle("System Settings");

        url= (EditText) findViewById(R.id.url);
        if (URL!=null){
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            uRl= sharedpreferences.getString("URL",null);
            url.setText(uRl);
        }
        btn= (Button) findViewById(R.id.confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String x= url.getText().toString();

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(URL, x);

                editor.commit();

                Intent intent1= new Intent(getApplicationContext(), LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();


                arflagURL=false;
                enflag=false;


            }
        });

        // ----------------------------language -----------------------------------
        englishButton = (Button) findViewById(R.id.en_button);
        englishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               setLocale("en");

                arflagURL=false;
                enflag=true;
                LoginActivity.arabicflag=false;

                if (URL!=null){
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    uRl= sharedpreferences.getString("URL",null);
                    url.setText(uRl);
                }
            }
        });


        arabicButton = (Button) findViewById(R.id.ar_button);
        arabicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setLocale("ar");
                arflagURL=true;
                enflag=false;
                LoginActivity.arabicflag=true;

                if (URL!=null){
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    uRl= sharedpreferences.getString("URL",null);
                    url.setText(uRl);
                }
            }
        });

        // -------------------------------------------------------------------------
        if(LoginActivity.arabicflag==true){
            setTitle("الاعدادات");
        }else if(insertUrl.arflagURL==true ){
            setTitle("الاعدادات");
        }
        else {
            setTitle("System Settings");
        }
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, insertUrl.class);
        startActivity(refresh);
        finish();
    }
    @Override
    public void onBackPressed() {
        if (arflagURL==true) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else if (enflag==true) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

     // if (MainActivity.listFlag==true){
           if (id == android.R.id.home) {
               if (arflagURL==true) {
                   Intent intent = new Intent(this, MainActivity.class);
                   startActivity(intent);
                   finish();
               }else if (enflag==true) {
                   Intent intent = new Intent(this, MainActivity.class);
                   startActivity(intent);
                   finish();
               }
               else {
                   Intent intent = new Intent(this, MainActivity.class);
                   startActivity(intent);
                   finish();
               }
            }

        return true;
    }

}
