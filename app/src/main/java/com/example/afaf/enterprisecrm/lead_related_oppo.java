package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Oppo;
import com.example.afaf.enterprisecrm.Helper_Database.opportunityModedel;

import java.util.List;

/**
 * Created by enterprise on 25/01/17.
 */

public class lead_related_oppo extends ListFragment {


    List<opportunityModedel> list;
    JCGSQLiteHelper_Oppo db;


    String caseID=null;

    String LID=null;
    SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lead_oppo, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            final String s = lead_info_activity.leadid; // lead_form id
                                list = db.getAllOpportunityLead(s);
                                lead_oppo_adapter adapter = new lead_oppo_adapter(getListView().getContext(), list, db);
                                setListAdapter(adapter);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                list = db.getAllOpportunityLead(s);
                lead_oppo_adapter adapter = new lead_oppo_adapter(getListView().getContext(), list, db);
                setListAdapter(adapter);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                        swipeContainer.setRefreshing(false);
                    }
                }, 2000);

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


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
