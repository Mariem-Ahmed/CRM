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

import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;

import java.util.List;

/**
 * Created by enterprise on 24/09/16.
 */
public class lead_related_activity extends ListFragment {


    List<ActivityModel> list;
    JCGSQLiteHelper db;


      String actID=null;

    SwipeRefreshLayout swipeContainer;

      String LID=null;
public static boolean listflag1= false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lead_related_activity, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


            final String s = lead_info_activity.leadid; // lead_form id
            list = db.getActivityWithLeadID(s);
            lead_activity_adapter adapter = new lead_activity_adapter(getListView().getContext(), list, db);
        setListAdapter(adapter);


        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list = db.getActivityWithLeadID(s);
                lead_activity_adapter adapter = new lead_activity_adapter(getListView().getContext(), list, db);
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
        db = new JCGSQLiteHelper(getActivity());
    }
}

