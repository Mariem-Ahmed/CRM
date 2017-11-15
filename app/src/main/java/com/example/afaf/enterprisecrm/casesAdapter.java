package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.casesModel;

import org.json.JSONException;

import java.util.List;

/**
 * Created by enterprise on 11/10/16.
 */

public class casesAdapter extends ArrayAdapter<casesModel> {
    protected Context mContext;
    protected List<casesModel> mCases;

    public casesAdapter(Context context, List<casesModel> cases) {
        super(context, R.layout.lead_row, cases);
        mCases = cases;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        casesAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lead_row,null);
            holder = new ViewHolder();
            holder.caseSubName=(TextView)convertView.findViewById(R.id.Name);
            holder.casesAssignedTo=(TextView)convertView.findViewById(R.id.Description);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        final casesModel casesModel = mCases.get(position);
        //holder.caseLeadName.setText(casesModel.getLeadName());

            holder.caseSubName.setText(casesModel.getSubjectName());



        assignedTo_spinner_helper db1 = new assignedTo_spinner_helper(getContext());
        assignedTo_spinner_model assignedLists = null;
        for (int ii = 0; ii < db1.getAllAssignedTo().size(); ii++) {
            try {
                assignedLists = db1.readAssignedToSpinner(ii+1);
            } catch (JSONException e) {

            }
            if (assignedLists.getUserId().equals(casesModel.getAssignedTo()))
                holder.casesAssignedTo.setText(assignedLists.getUserName());

        }
        holder.action=(LinearLayout)convertView.findViewById(R.id.linearAction);
        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), cases.class);
                String id = casesModel.getId()+"";
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


                i.putExtra("id", id);
                i.putExtra("assignedTo",assignedTo);
                i.putExtra("relatedLead",relatedLead);
                i.putExtra("timeSpent",timeSpent);
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

                getContext().startActivity(i);

            }
        });


        return convertView;

    }
    private static class ViewHolder{
        TextView caseSubName;
        TextView casesAssignedTo;
        LinearLayout action;
    }
}
