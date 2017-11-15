package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Leads;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Oppo;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_cases;
import com.example.afaf.enterprisecrm.Helper_Database.casesModel;
import com.example.afaf.enterprisecrm.Helper_Database.opportunityModedel;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.List;


public class Dashboard extends Fragment {

    JCGSQLiteHelper db1;
    JCGSQLiteHelper_cases db2;
    JCGSQLiteHelper_Leads db3;
    JCGSQLiteHelper_Oppo db4;
    public Dashboard() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PieChart mPieChart = (PieChart) getView().findViewById(R.id.piechart);
        List<ActivityModel>activityModels = db1.getAllActivities();
        List<casesModel>casesModels = db2.getAllCases();
        List<leadsModel>leadsModels = db3.getAllLeads();
        List<opportunityModedel>opportunityModels= db4.getAllOpportunity();

        if(leadsModels!=null)
        mPieChart.addPieSlice(new PieModel("Leads",leadsModels.size(), Color.parseColor("#FE6DA8")));
        if(activityModels!=null)
        mPieChart.addPieSlice(new PieModel("Activities", activityModels.size(), Color.parseColor("#56B7F1")));
        if(casesModels!=null)
        mPieChart.addPieSlice(new PieModel("Cases", casesModels.size(), Color.parseColor("#CDA67F")));
        if(opportunityModels!=null)
        mPieChart.addPieSlice(new PieModel("Opportunities", opportunityModels.size(), Color.parseColor("#FED70E")));

        mPieChart.startAnimation();
        ValueLineChart mCubicValueLineChart = (ValueLineChart) getView().findViewById(R.id.cubiclinechart);

        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        List<ActivityModel>chartActivity1 = db1.getChartActivity("01");
        List<ActivityModel>chartActivity2 = db1.getChartActivity("02");
        List<ActivityModel>chartActivity3 = db1.getChartActivity("03");
        List<ActivityModel>chartActivity4 = db1.getChartActivity("04");
        List<ActivityModel>chartActivity5 = db1.getChartActivity("05");
        List<ActivityModel>chartActivity6 = db1.getChartActivity("06");
        List<ActivityModel>chartActivity7 = db1.getChartActivity("07");
        List<ActivityModel>chartActivity8 = db1.getChartActivity("08");
        List<ActivityModel>chartActivity9 = db1.getChartActivity("09");
        List<ActivityModel>chartActivity10 = db1.getChartActivity("10");
        List<ActivityModel>chartActivity11 = db1.getChartActivity("11");
        List<ActivityModel>chartActivity12 = db1.getChartActivity("12");

        series.addPoint(new ValueLinePoint("Jan", chartActivity1.size()));
        series.addPoint(new ValueLinePoint("Feb", chartActivity2.size()));
        series.addPoint(new ValueLinePoint("Mar", chartActivity3.size()));
        series.addPoint(new ValueLinePoint("Apr", chartActivity4.size()));
        series.addPoint(new ValueLinePoint("Mai",chartActivity5.size()));
        series.addPoint(new ValueLinePoint("Jun", chartActivity6.size()));
        series.addPoint(new ValueLinePoint("Jul",chartActivity7.size()));
        series.addPoint(new ValueLinePoint("Aug",chartActivity8.size()));
        series.addPoint(new ValueLinePoint("Sep", chartActivity9.size()));
        series.addPoint(new ValueLinePoint("Oct", chartActivity10.size()));
        series.addPoint(new ValueLinePoint("Nov", chartActivity11.size()));
        series.addPoint(new ValueLinePoint("Dec", chartActivity12.size()));

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db1 = new JCGSQLiteHelper(context);
        db2 = new JCGSQLiteHelper_cases(context);
        db3 = new JCGSQLiteHelper_Leads(context);
        db4 = new JCGSQLiteHelper_Oppo(context);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
