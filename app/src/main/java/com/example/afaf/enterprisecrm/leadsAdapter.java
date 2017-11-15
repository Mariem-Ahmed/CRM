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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enterprise on 11/10/16.
 */

public class leadsAdapter extends ArrayAdapter<leadsModel> {
    protected Context mContext;
    protected List<leadsModel> mLeads;
    public static final String LoginPREFERENCES = "LoginPrefs" ;
    SharedPreferences sharedpreferencesLogin;
    String uname="";



    public leadsAdapter(Context context, List<leadsModel> leads) {
        super(context, R.layout.lead_row, leads);
        mLeads = leads;
        sharedpreferencesLogin = context. getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
        uname = sharedpreferencesLogin.getString("uName", null);

        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        leadsAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lead_row,null);
            holder = new ViewHolder();
            holder.leadCommertialName=(TextView)convertView.findViewById(R.id.Name);
            holder.leadphone=(TextView)convertView.findViewById(R.id.Description);
            holder.action=(LinearLayout)convertView.findViewById(R.id.linearAction);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        final leadsModel leadModel = mLeads.get(position);
        holder.leadCommertialName.setText(leadModel.getCommertialName());
        holder.leadphone.setText(leadModel.getAssignedTo());

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   leadsModel model = db.readLead(position);
                String CommertialName= leadModel.getCommertialName();
                String phone= leadModel.getPhone();
                String email= leadModel.getEmail();
                String comment = leadModel.getComment();
                String leadsource = leadModel.getLeadSource();
                String currentaddress= leadModel.getCurrentAddress();
                String assignedto = leadModel.getAssignedTo();
                String leadstatus= leadModel.getStatus();
                String interestIn= leadModel.getInterestedIn();
                String  leadid= leadModel.getLeadId();
                String title= leadModel.getTitle();
                String round= leadModel.getEm_Opcrm_Round_id();
                String project= leadModel.getEm_Opcrm_Project_id();
                String nationalty= leadModel.getNationality();
                String assignedtoid= leadModel.getAssignedToID();
                int id = leadModel.getId();


                Intent i = new Intent(getContext(),Main4Activity.class);

                i.putExtra("id", id +"");
                i.putExtra("CommertialName",CommertialName);
                i.putExtra("phone",phone);
                i.putExtra("email",email);
                i.putExtra("comment",comment);
                i.putExtra("leadsource",leadsource);
                i.putExtra("currentaddress",currentaddress);
                i.putExtra("assignedto",assignedto);
                i.putExtra("leadstatus",leadstatus);
                i.putExtra("interestIn",interestIn);
                i.putExtra("leadid",leadid);
                i.putExtra("title",title);
                i.putExtra("round",round);
                i.putExtra("project",project);
                i.putExtra("nationalty",nationalty);
                i.putExtra("AssignedToID",assignedtoid);

                getContext().startActivity(i);



            }
        });

        return convertView;

    }
    private static class ViewHolder{
        TextView leadCommertialName;
        TextView leadphone;
        LinearLayout action;
    }
}
