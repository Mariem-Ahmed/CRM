package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_cases;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.casesModel;

import org.json.JSONException;

import java.util.List;

/**
 * Created by enterprise on 21/02/17.
 */

public class activity_cases_adapter extends ArrayAdapter<casesModel> {
    protected Context mContext;
    protected List<casesModel> mCases;

    public  static boolean tabbedcaseactivity = false;



    public activity_cases_adapter(Context context, List<casesModel> cases, JCGSQLiteHelper_cases db) {
        super(context, R.layout.lead_row, cases);
        mCases = cases;
        mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lead_row,null);
            holder = new ViewHolder();
            holder.caseSubName=(TextView)convertView.findViewById(R.id.Name);
            holder.casesRelatedLead=(TextView)convertView.findViewById(R.id.Description);
            holder.action=(LinearLayout)convertView.findViewById(R.id.linearAction);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        final casesModel casesModel = mCases.get(position);
        //holder.caseLeadName.setText(casesModel.getLeadName());
        holder.caseSubName.setText(casesModel.getSubjectName());

       // holder.casesRelatedLead.setText(casesModel.getLeadName());
        assignedTo_spinner_helper db1 = new assignedTo_spinner_helper(getContext());
        assignedTo_spinner_model assignedLists = null;
        for (int ii = 0; ii < db1.getAllAssignedTo().size(); ii++) {
            try {
                assignedLists = db1.readAssignedToSpinner(ii+1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (assignedLists.getUserId().equals(casesModel.getAssignedTo()))
                holder.casesRelatedLead.setText(assignedLists.getUserName());

        }

        //     holder.hidden.setText(casesModel.getId()+"");
        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tabbedcaseactivity=true;
                if ( lead_related_activity.listflag1==true){
                    Main4Activity.fabcasesFlag=true;
                }else {
                    Main4Activity.fabcasesFlag=false;
                }

                Main5Activity.fabactivitiesFlag=false;
                Main5Activity.editflag=false;
                Main4Activity.editleadflag=false;
                MainActivity.listFlag=true;
                MainActivity.fabFlag=false;
                lead_oppo_adapter.tabbedoppolead=false;

                Intent i = new Intent(getContext(), cases.class);

                String subName= casesModel.getSubjectName();
                String deadline= casesModel.getDeadLine();
                String priority= casesModel.getPriority();
                String status = casesModel.getStatus();
                String relatedLead=casesModel.getLeadName();
                String assignedTo= casesModel.getAssignedTo();
                String timeSpent= casesModel.getTimeSpent();

                String cID= casesModel.getCaseID();
                String relatedLeadid=casesModel.getCaseRelatedLead();
                String spenthour=casesModel.getSpenthours();
                String spentmin=casesModel.getSpentmintues();
                String caseact=casesModel.getCaseActivity();
                String caseactid=casesModel.getCaseActivityId();


                //  i.putExtra("id", position +1 +"");
                i.putExtra("assignedTo",assignedTo);
                i.putExtra("relatedLead",relatedLead);
                //  i.putExtra("timeSpent",timeSpent);
                i.putExtra("priority",priority);
                i.putExtra("subName",subName);
                i.putExtra("caseID",cID);
                i.putExtra("deadline",deadline);
                i.putExtra("status",status);
                i.putExtra("relatedLeadid",relatedLeadid);
                i.putExtra("spenthour",spenthour);
                i.putExtra("spentmin",spentmin);
                i.putExtra("caseact",caseact);
                i.putExtra("caseactid",caseactid);


                i.putExtra("actidid", activity_info.actID);
                i.putExtra("subName", activity_info.subName);
//                            // ------------------------------------------
                getContext().startActivity(i);

            }
        });
        return convertView;

    }
    private static class ViewHolder{
        TextView caseSubName;
        TextView casesRelatedLead;
        LinearLayout action;
    }
}
