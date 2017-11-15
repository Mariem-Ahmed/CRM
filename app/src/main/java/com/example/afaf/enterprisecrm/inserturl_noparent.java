package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

/**
 * Created by enterprise on 28/12/16.
 */

public class inserturl_noparent extends AppCompatActivity {


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
        setTitle("Configration System");

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
//                System.out.println(x);
//                db.createURL(x);
//                Main3Activity.urlFlag=true;
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(URL, x);

                editor.commit();

                Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

                Intent i= new Intent(getApplicationContext(), MainActivity.class);
                finish();
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
            setTitle("Configration System");
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
        refresh.putExtra("urlText", URL);
        startActivity(refresh);
        finish();
    }

    @Override
    public void onBackPressed() {

    }




}
