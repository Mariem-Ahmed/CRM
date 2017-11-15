package com.example.afaf.enterprisecrm;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONException;

public class Main5Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public static String idactlocal;
    public static boolean openedit;
    // ----------------------fab button ------------------------------------

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    public static Boolean fabactivitiesFlag=false,editflag =false, neweditflag=false;

    public static String lid;
    public static String lidname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

              Intent ii3 = getIntent();
              lid = ii3.getStringExtra("leadidid");
              lidname = ii3.getStringExtra("ComName");





        // ------------ set title of activiy- -------------------
        Intent i = getIntent();
        String subName = i.getStringExtra("subName");
        if(subName!=null) {
            this.setTitle(subName);
        }else if (fabactivitiesFlag==true || activity_cases_adapter.tabbedcaseactivity==true){
            String activityName = i.getStringExtra("activityname");
            this.setTitle(activityName);
        }else if (neweditflag==true){
            this.setTitle(acitvity.actname.getText().toString());
        }

        // ----------------------------------------------------------------

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        // --------------------------- fab button -----------------------------------------------------
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab1.setOnClickListener(this);


    }
    // ------------------ fab button action ----------------------------------------------------------------

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
//            case R.id.fab:
//
//                animateFAB();
//
//                break;
            case R.id.fab1:
                Intent intent2 = new Intent(this,cases.class);
                intent2.putExtra("actidid", activity_info.actID);
                intent2.putExtra("subName", activity_info.subName);
                startActivity(intent2);
                finish();
                fabactivitiesFlag=true;
                MainActivity.fabFlag=true;
                MainActivity.listFlag=false;
                MainActivity.casesflag=true;
                MainActivity.navFlag=false;
                break;

        }
    }

    public void animateFAB(){

        if(isFabOpen){

         //   fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab1.setClickable(false);

            isFabOpen = false;

        } else {

       //     fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab1.setClickable(true);

            isFabOpen = true;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main5, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


      if (id == R.id.edit) {

          Intent intent = getIntent();

          if ( openedit==true) {
              idactlocal = intent.getStringExtra("id");
          }else{
              String s = idactlocal;
              if (idactlocal==null || !idactlocal.equals(s)) {
                  idactlocal = intent.getStringExtra("id");
              }

          }

          neweditflag=true;
          editflag=true;
          Main4Activity.fabcasesFlag=false;
          Main5Activity.fabactivitiesFlag=false;
          MainActivity.fabFlag=false;
          MainActivity.listFlag=false;
          MainActivity.navFlag=false;

          openedit=false;
            JCGSQLiteHelper dbact = new JCGSQLiteHelper(this);

            ActivityModel model = null;
          int t;

          if (idactlocal != null  ) {
                  t   = Integer.parseInt(idactlocal);
              try {
                  model = dbact.readActivity(t);
              } catch (JSONException e) {
              }


              //   int id1= model.getId();
            String subName = model.getActivitySubject();
            String startdate = model.getActivityStartdate();
            String hour = model.getStartHour();
            String minute = model.getStartMinute();
            String duration = model.getDurationHours();
            String acttype = model.getActivityType();
            String actstatus = model.getActivityStatus();
            String relatedlead = model.getRelatedLead();
            String  actID= model.getActivityId();
            String LID= model.getActRelatdLead();
            String lStatus = model.getLeadStatus();
            String desc = model.getDescription();
            String ass = model.getAssigeTo();


            Intent i = new Intent(this, acitvity.class);
              i.putExtra("id", idactlocal);
              i.putExtra("actidid", activity_info.actID);
              i.putExtra("subName", activity_info.subName);
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

            startActivity(i);

              finish();
        }
      }
      else if (id == android.R.id.home) {

          if (lead_related_activity.listflag1 == true) {
              Intent i = new Intent(this, Main4Activity.class);
              i.putExtra("leadid", lid);
              i.putExtra("leadname", lidname);
              startActivity(i);
              finish();
              Main5Activity.editflag=false;

          } else {
              Intent i = new Intent(this, MainActivity.class);
              i.putExtra("Window", "2");
              startActivity(i);
              finish();
              openedit=true;
              Main5Activity.editflag=false;
              return true;
          }
      }


        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_info, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = null;
            if (position == 0) {
                fragment = new activity_info();
            }
            else if (position == 1) {
                fragment = new activity_related_case();
            }

//            else if (position == 2) {
//                fragment = new activity_related_document();
//            }

            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    if(LoginActivity.arabicflag==true ){
                        return "بيانات";
                    }
                    else if(insertUrl.arflagURL==true ) {
                        return "بيانات";
                    }else{
                        return "Info";
                    }
                case 1:
                    if(LoginActivity.arabicflag==true ){
                        return "الحالات";
                    }
                    else if(insertUrl.arflagURL==true ) {
                        return "الحالات";
                    }else {
                        return "Cases";
                    }
//                case 2:
//                    return "Document";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if (lead_related_activity.listflag1 == true) {
            Intent i = new Intent(this, Main4Activity.class);
            i.putExtra("leadid", lid);
            i.putExtra("leadname", lidname);
            startActivity(i);
            finish();
            Main5Activity.editflag=false;
        } else {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window", "2");
            startActivity(i);
            finish();
            Main5Activity.editflag=false;
        }
    }
}
