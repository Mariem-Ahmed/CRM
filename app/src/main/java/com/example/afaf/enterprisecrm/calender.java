package com.example.afaf.enterprisecrm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * Created by enterprise on 25/09/16.
 */
public class calender extends Fragment {
    private ListView listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calender, container, false);

        String[] array = new String[] {"item1", "item21", "item3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array);

        listview = (ListView)v.findViewById(R.id.listView1);
        listview.setAdapter(adapter);
        return v;
    }



}
