package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;

import java.util.List;


/**
 * Created by ibrahimfouad on 04/10/16.
 */
public class ActivtyAdapter extends ArrayAdapter<ActivityModel> {
    protected Context mContext;
    protected List<ActivityModel> mActivities;
    public static final String LoginPREFERENCES = "LoginPrefs" ;
    public static final String UserName = "username";
    public static final String PassWord = "password";

    SharedPreferences sharedpreferencesLogin;
    String uname="";
    String passw="";
    String userId="";


    public ActivtyAdapter(Context context, List<ActivityModel> activities, JCGSQLiteHelper db) {
        super(context, R.layout.lead_row, activities);
        mActivities = activities;
        mContext = context;
        sharedpreferencesLogin = context.getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
        uname = sharedpreferencesLogin.getString("uName", null);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lead_row,null);
            holder = new ViewHolder();
            holder.activitySubject=(TextView)convertView.findViewById(R.id.Name);
            holder.assignedto=(TextView)convertView.findViewById(R.id.Description);


        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        final ActivityModel activityModel = mActivities.get(position);
//        if(MainActivity.listFlag==true && MainActivity.fabFlag==false){
//            holder.activitySubject.setText(acitvity.actname.getText()+"");
//        }else {
            holder.activitySubject.setText(activityModel.getActivitySubject());
      //  }
        holder.assignedto.setText(uname);


        holder.action=(LinearLayout)convertView.findViewById(R.id.linearAction);

        return convertView;

    }
    private static class ViewHolder{
        TextView activitySubject;
        TextView assignedto;
        LinearLayout action;
    }
}
