package com.example.afaf.enterprisecrm;

import android.content.Context;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


public class CalenderFragment extends Fragment implements MonthLoader.MonthChangeListener, WeekView.EventClickListener{
    private WeekView mWeekView;
    private ArrayList<WeekViewEvent> mNewEvents;
    JCGSQLiteHelper db ;





    public CalenderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calender, container, false);

        // define a listener to receive callbacks when certain events happen.

        return  v;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWeekView = (WeekView) getView().findViewById(R.id.weekView);
        // Get a reference for the week view in the layout.


        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);
        mWeekView.setOnEventClickListener(this);

        // Set up empty view click listener.
//        mWeekView.setEmptyViewClickListener(this);

        // Initially, there will be no events on the week view because the user has not tapped on
        // it yet.
        mNewEvents = new ArrayList<WeekViewEvent>();

//        // Show a toast message about the touched event.
//        mWeekView.setOnEventClickListener(this);
//
//        // The week view has infinite scrolling horizontally. We have to provide the events of a
//        // month every time the month changes on the week view.
//        mWeekView.setMonthChangeListener(this);
//
//        // Set long press listener for events.
//        mWeekView.setEventLongPressListener(this);
//
//        // Set long press listener for empty view
//        mWeekView.setEmptyViewLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
    //    setupDateTimeInterpreter(false);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db = new JCGSQLiteHelper(context);

    }


    /**
     * Get events that were added by tapping on empty view.
     * @param year The year currently visible on the week view.
     * @param month The month currently visible on the week view.
     * @return The events of the given year and month.
     */
    private ArrayList<WeekViewEvent> getNewEvents(int year, int month)  {

        // Get the starting point and ending point of the given month. We need this to find the
        // events of the given month.
        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, year);
        startOfMonth.set(Calendar.MONTH, month - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        startOfMonth.set(Calendar.MILLISECOND, 0);
        Calendar endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND, 59);
        List<ActivityModel>modelList = db.getAllActivities();
        for(int i=0;i<modelList.size();i++){
            String subject = modelList.get(i).getActivitySubject();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String startDate = modelList.get(i).getActivityStartdate();
            int hour=0, minute = 0;
            if(modelList.get(i).getStartHour()!=null)
            hour = Integer.parseInt(modelList.get(i).getStartHour());
            if(modelList.get(i).getStartMinute()!=null)
            minute =Integer.parseInt(modelList.get(i).getStartMinute());
            String D = modelList.get(i).getDurationHours();
            String H, M;
            int h =0, m = 0;
            StringTokenizer tokenizer = new StringTokenizer(D,":");
            while (tokenizer.hasMoreTokens()){
                H = tokenizer.nextToken();
                if(H!=null)
                h = Integer.parseInt(H);
                else h = 0;
                M = tokenizer.nextToken();
                if(M!=null)m = Integer.parseInt(M);
                else m =0;
            }
            int duration = (int) Math.round(h * 60)+m;
            Calendar startCal = Calendar.getInstance();
            Calendar todayCal = Calendar.getInstance();


            try {
                Date date = sdf.parse(startDate);

                startCal.setTime(date);
                startCal.add(Calendar.HOUR, hour);
                startCal.add(Calendar.MINUTE,minute);
                Calendar endTime = (Calendar) startCal.clone();
                endTime.add(Calendar.MINUTE, duration);

                WeekViewEvent event = new WeekViewEvent(0, subject, startCal, endTime);
                if(todayCal.getTimeInMillis()>startCal.getTimeInMillis()){
                    event.setColor(getResources().getColor(R.color.red));
                }
                mNewEvents.add(event);
            } catch (ParseException e) {

            }

        }

        // Find the events that were added by tapping on empty view and that occurs in the given
        // time frame.
        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : mNewEvents) {
            if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                    event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                events.add(event);
            }
        }
        return events;
    }
    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with the events that was added by tapping on empty view.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        ArrayList<WeekViewEvent> newEvents = getNewEvents(newYear, newMonth);
        events.addAll(newEvents);
        return events;
    }


    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        String subject = event.getName();
    List<ActivityModel> model =    db.geEventActivity(subject);
        if(model.size()!=0){
            String subName = model.get(0).getActivitySubject();
            String startdate = model.get(0).getActivityStartdate();
            String hour = model.get(0).getStartHour();
            String minute = model.get(0).getStartMinute();
            String duration = model.get(0).getDurationHours();
            String acttype = model.get(0).getActivityType();
            String actstatus = model.get(0).getActivityStatus();
            String relatedlead = model.get(0).getRelatedLead();
            String  actID= model.get(0).getActivityId();
            String  LID= model.get(0).getActRelatdLead();
            String lStatus = model.get(0).getLeadStatus();
            String desc = model.get(0).getDescription();
            String ass = model.get(0).getAssigeTo();
            Intent i = new Intent(getActivity(), Main5Activity.class);
            i.putExtra("id", model.get(0).getId() +"");
            i.putExtra("subName", subName);
            i.putExtra("startDate", startdate);
            i.putExtra("hour", hour);
            i.putExtra("minute", minute);
            i.putExtra("duration", duration);
            i.putExtra("acttype", acttype);
            i.putExtra("actstatus", actstatus);
            i.putExtra("relatedlead", relatedlead);
            i.putExtra("activityId",actID);
            i.putExtra("LeadId",LID);
            i.putExtra("leadStatus",lStatus);
            i.putExtra("description",desc);
            i.putExtra("AssignedTo",ass);


            // ------------------------------------------


            startActivity(i);
        }

    }
}




