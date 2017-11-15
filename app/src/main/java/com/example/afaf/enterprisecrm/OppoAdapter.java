package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Oppo;
import com.example.afaf.enterprisecrm.Helper_Database.opportunityModedel;

import java.util.List;

/**
 * Created by enterprise on 11/10/16.
 */

public class OppoAdapter extends ArrayAdapter<opportunityModedel> {
    protected Context mContext;
    protected List<opportunityModedel> mOpportunities;
    public static final String LoginPREFERENCES = "LoginPrefs" ;
    SharedPreferences sharedpreferencesLogin;
    String uname="";

    public OppoAdapter(Context context, List<opportunityModedel> opportunities, JCGSQLiteHelper_Oppo db) {
        super(context, R.layout.lead_row, opportunities);
        sharedpreferencesLogin = context. getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
        uname = sharedpreferencesLogin.getString("uName", null);
        mOpportunities = opportunities;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OppoAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lead_row,null);
            holder = new ViewHolder();
            holder.opposubject=(TextView)convertView.findViewById(R.id.Name);
            holder.oppocloseddate=(TextView)convertView.findViewById(R.id.Description);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        final opportunityModedel oppoModel = mOpportunities.get(position);
        holder.opposubject.setText(oppoModel.getOppoSubject());
        holder.oppocloseddate.setText(uname);

        return convertView;

    }
    private static class ViewHolder{
        TextView opposubject;
        TextView oppocloseddate; TextView hidden;

    }
}
