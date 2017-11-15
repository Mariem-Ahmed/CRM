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

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Oppo;
import com.example.afaf.enterprisecrm.Helper_Database.opportunityModedel;

import java.util.List;

/**
 * Created by enterprise on 07/02/17.
 */

public class lead_oppo_adapter extends ArrayAdapter<opportunityModedel> {
    protected Context mContext;
    protected List<opportunityModedel> mOpportunities;
    public static final String LoginPREFERENCES = "LoginPrefs" ;
    SharedPreferences sharedpreferencesLogin;
    String uname="";

    public static boolean tabbedoppolead=false;

    public lead_oppo_adapter(Context context, List<opportunityModedel> opportunities, JCGSQLiteHelper_Oppo db) {
        super(context, R.layout.lead_row, opportunities);
        mOpportunities = opportunities;
        mContext = context;
        sharedpreferencesLogin = context. getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
        uname = sharedpreferencesLogin.getString("uName", null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lead_row,null);
            holder = new ViewHolder();
            holder.opposubject=(TextView)convertView.findViewById(R.id.Name);
            holder.oppocloseddate=(TextView)convertView.findViewById(R.id.Description);
            holder.action=(LinearLayout)convertView.findViewById(R.id.linearAction);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        final opportunityModedel oppoModel = mOpportunities.get(position);
        holder.opposubject.setText(oppoModel.getOppoSubject());

        holder.oppocloseddate.setText(uname);
        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tabbedoppolead=true;
                Main4Activity.fabcasesFlag=true;
                lead_cases_adapter.tabbedcaselead=false;
                Main5Activity.fabactivitiesFlag=false;
                Main5Activity.editflag=false;
                Main4Activity.editleadflag=false;
                MainActivity.listFlag=true;

                Intent i = new Intent(getContext(), opportunity.class);

                String subName= oppoModel.getOppoSubject();
                String closedate= oppoModel.getOppoCloseDate();
                String amount= oppoModel.getOppoAmount();
                String probablity = oppoModel.getOppoProbablity();
                String relatedLead = oppoModel.getOppoRelatedLead();
                String assignto = oppoModel.getOppoAssignedto();
                String oppid= oppoModel.getOppoID();
                String oppleadid= oppoModel.getOppoRelatedLeadID();
                String oppAssignedToid= oppoModel.getOppoAssignedToID();

                // i.putExtra("id", position +1 +"");
                i.putExtra("subName",subName);
                i.putExtra("closedate",closedate);
                i.putExtra("amount",amount);
                i.putExtra("probablity",probablity);
                i.putExtra("relatedLead",relatedLead);
                i.putExtra("assignto",assignto);
                i.putExtra("oppleadid",oppleadid);
                i.putExtra("oppid",oppid);
                i.putExtra("oppAssignedToid",oppAssignedToid);

                i.putExtra("leadidid", lead_info_activity.leadid);
                i.putExtra("ComName", lead_info_activity.comName);

                getContext().startActivity(i);

            }
        });
        return convertView;

    }
    private static class ViewHolder{
        TextView opposubject;
        TextView oppocloseddate;
        LinearLayout action;

    }
}
