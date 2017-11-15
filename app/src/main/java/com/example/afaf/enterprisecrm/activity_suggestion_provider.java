package com.example.afaf.enterprisecrm;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enterprise on 02/11/16.
 */

public class activity_suggestion_provider extends ContentProvider {

    List<String> activities;
    List<ActivityModel> activity;
   // List<ActivityModel> list;

   // private static final String AUTHORITY = "ngvl.android.demosearch.activitysuggestion";
    private static final int TYPE_ALL_SUGGESTIONS = 1;
    private static final int TYPE_SINGLE_SUGGESTION = 2;

    private UriMatcher mUriMatcher;

    @Override
    public boolean onCreate() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        return false;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (activities == null || activities.isEmpty()){
           // activity = new ArrayList<>();
            activities = new ArrayList<>();
            JCGSQLiteHelper db = new JCGSQLiteHelper(getContext());

            try {
               // activity = db.getAllActivities();
              //  ActivtyAdapter adapter = new ActivtyAdapter(getListView().getContext(), activity, db);
              //  setListAdapter(adapter);
                activity = db.getAllActivities();

                db.close();

                int lenght = activity.size();
                for (int i = 0; i < lenght; i++) {
                    String act = activity.get(i).getActivitySubject();
                    activities.add(act);
                }

            } catch (Exception e) {

            }
        }

        MatrixCursor cursor = new MatrixCursor(
                new String[] {
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
                }
        );
        if (activities != null) {
            String query = uri.getLastPathSegment().toUpperCase();
            int limit = Integer.parseInt(uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT));

            int lenght = activities.size();
            for (int i = 0; i < lenght && cursor.getCount() < limit; i++) {
                String city = activities.get(i);
                if (city.toUpperCase().contains(query)){
                    cursor.addRow(new Object[]{ i, city, i });
                }
            }
        }
        return cursor;
    }
}
