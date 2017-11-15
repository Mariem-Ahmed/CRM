package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_cases;
import com.example.afaf.enterprisecrm.Helper_Database.casesModel;

import java.util.List;

/**
 * Created by enterprise on 02/02/17.
 */

public class activity_related_case extends ListFragment {
    List<casesModel> list;
    JCGSQLiteHelper_cases db;

    SwipeRefreshLayout swipeContainer;


    @Nullable
    @Override



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_related_case, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


             final String ss = activity_info.actID; // case activity id
             list = db.getAllCasesActivities(ss);
             activity_cases_adapter adapter = new activity_cases_adapter(getListView().getContext(), list, db);
             setListAdapter(adapter);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                list = db.getAllCasesActivities(ss);
                activity_cases_adapter adapter = new activity_cases_adapter(getListView().getContext(), list, db);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

                }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db = new JCGSQLiteHelper_cases(getActivity());
    }


}
