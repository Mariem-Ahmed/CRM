package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Oppo;
import com.example.afaf.enterprisecrm.Helper_Database.opportunityModedel;

import org.json.JSONException;

import java.util.List;

/**
 * Created by enterprise on 02/02/17.
 */

public class activity_related_oppo extends ListFragment{

    List<opportunityModedel> list;
    JCGSQLiteHelper_Oppo db;


    String caseID=null;

    String LID=null;


    @Nullable
    @Override



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_related_oppo, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        JCGSQLiteHelper dbact = new JCGSQLiteHelper(getActivity());
        List<ActivityModel> Lists = dbact.getAllActivities();
        ActivityModel l = null;
        for (int i=0; i < Lists.size(); i++){
            try {
                l= dbact.readActivity(i+1);
            } catch (JSONException e) {
            }
            String s = l.getActivityId(); // lead_form id
            if (s.equals(activity_info.actID)){
                JCGSQLiteHelper_Oppo dboppo = new JCGSQLiteHelper_Oppo(getActivity());
                List<opportunityModedel> oppoLists = dboppo.getAllOpportunity();
                opportunityModedel c ;
                for (int ii=0; ii < oppoLists.size(); ii++){
                    c= dboppo.readOpportunity(ii+1);

                    String a = c.getOppoID();
                    if (a!=null)
                        if(a.equals(s))
                          //  if(lead_info_activity.leadflag == true) {
                                list = db.getAllOppoActivities(s);
                                OppoAdapter adapter = new OppoAdapter(getListView().getContext(), list, db);
                                setListAdapter(adapter);
                          //  }
                }

            }

        }



    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db = new JCGSQLiteHelper_Oppo(getActivity());
    }

}
