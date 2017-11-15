package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;

import java.util.List;

/**
 * Created by enterprise on 02/11/16.
 */

public class ExampleAdapter  extends CursorAdapter {

    private List<ActivityModel> items;

    private TextView text;

    public ExampleAdapter(Context context, Cursor cursor, List<ActivityModel> items) {

        super(context, cursor, false);

        this.items = items;

    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        text.setText((CharSequence) items.get(cursor.getPosition()));
       // text.setText(items.get());

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item, parent, false);

        text = (TextView) view.findViewById(R.id.text);

        return view;

    }
}
