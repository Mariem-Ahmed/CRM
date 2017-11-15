package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.Intent;
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
 * Created by enterprise on 07/02/17.
 */

public class lead_activity_adapter extends ArrayAdapter<ActivityModel> {
    protected Context mContext;
    protected List<ActivityModel> mActivities;
    public static final String LoginPREFERENCES = "LoginPrefs" ;
    SharedPreferences sharedpreferencesLogin;
    String uname="";

    public lead_activity_adapter(Context context, List<ActivityModel> activities, JCGSQLiteHelper db) {
        super(context, R.layout.lead_row, activities);
        mActivities = activities;
        mContext = context;
        sharedpreferencesLogin = context. getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
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
            holder.action=(LinearLayout)convertView.findViewById(R.id.linearAction);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        final ActivityModel activityModel = mActivities.get(position);
        holder.activitySubject.setText(activityModel.getActivitySubject());

        holder.assignedto.setText(uname);
        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lead_related_activity.listflag1=true;
                Main4Activity.fabcasesFlag=true;

                Intent i = new Intent(getContext(), Main5Activity.class);

                String subName = activityModel.getActivitySubject();
                String startdate = activityModel.getActivityStartdate();
                String hour = activityModel.getStartHour();
                String minute = activityModel.getStartMinute();
                String duration = activityModel.getDurationHours();
                String acttype = activityModel.getActivityType();
                String actstatus = activityModel.getActivityStatus();
                String relatedlead = activityModel.getRelatedLead();
                String actID = activityModel.getActivityId();
                String LID = activityModel.getActRelatdLead();
                String lStatus = activityModel.getLeadStatus();
                String desc = activityModel.getDescription();

                //  i.putExtra("id",in + "");
                i.putExtra("subName", subName);
                i.putExtra("startDate", startdate);
                i.putExtra("hour", hour);
                i.putExtra("minute", minute);
                i.putExtra("duration", duration);
                i.putExtra("acttype", acttype);
                i.putExtra("actstatus", actstatus);
                i.putExtra("relatedlead", relatedlead);
                i.putExtra("activityId", actID);
                i.putExtra("LeadId", LID);
                i.putExtra("leadStatus", lStatus);
                i.putExtra("description", desc);

                i.putExtra("leadidid", lead_info_activity.leadid);
                i.putExtra("ComName", lead_info_activity.comName);
//                            // ------------------------------------------
                getContext().startActivity(i);

            }
        });
        return convertView;

    }
    private static class ViewHolder{
        TextView activitySubject;
        TextView assignedto;
        LinearLayout action;
    }
}
