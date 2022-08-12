package com.omnitech.covidproject.Fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omnitech.covidproject.Model.CovidAll;
import com.omnitech.covidproject.Model.CovidStatistics;
import com.omnitech.covidproject.R;
import com.omnitech.covidproject.Interfaces.ApiCallBack;
import com.omnitech.covidproject.Utils.Constants;
import com.omnitech.covidproject.Utils.CovidApi;
import com.omnitech.covidproject.Utils.ProgressDialogManager;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.omnitech.covidproject.Utils.Constants.COVID_BASE_URL;

public class StatisticsFragment extends Fragment implements ApiCallBack, View.OnClickListener {

    com.github.mikephil.charting.charts.BarChart barChart;

    TextView txtMyCountry;
    TextView txtGlobal;
    TextView txtTotalCases;
    TextView txtDeaths;
    TextView txtRecovered;
    TextView txtActive;
    TextView txtCritical;

    TextView txtTotal;
    TextView txtToday;
    TextView txtYesterday;


    CovidApi covidApi;


    AlertDialog mprogressDialog;
    Gson mGson;

    int Current_Code;
    int Current_Data_Type;
    List<CovidStatistics> covidStatisticsList=new ArrayList<>();
    SpinKitView spin_kit;
    com.hbb20.CountryCodePicker ccp;

    String COVID_URL_COUNTRY_ALL=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=true&twoDaysAgo=true&strict=false";
    String COVID_URL_COUNTRY_TODAY=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=false&twoDaysAgo=false&strict=false";
    String COVID_URL_COUNTRY_YESTERDAY=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=true&twoDaysAgo=false&strict=false";




    String COVID_URL_COUNTRY_ALL_PREV=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=true&twoDaysAgo=true&strict=false";
    String COVID_URL_COUNTRY_TODAY_PREV=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=false&twoDaysAgo=false&strict=false";
    String COVID_URL_COUNTRY_YESTERDAY_PREV=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=true&twoDaysAgo=false&strict=false";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_statistics, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        initViews(view);

        Current_Code=Constants.COVID_CODE_COUNTRY;
        Current_Data_Type=Constants.COVID_DATA_ALL;
        covidApi.getData(Constants.COVID_URL_COUNTRY_ALL,Constants.COVID_DATA_ALL);
        covidApi.getStatistics(Constants.COVID_DATA_STATISTICS);
        mprogressDialog.show();
    }

    private void initViews(View view)
    {
        ccp=view.findViewById(R.id.ccp);



        String myCountry=ccp.getSelectedCountryEnglishName();
        COVID_URL_COUNTRY_ALL=COVID_URL_COUNTRY_ALL.replace("COUNTRY",myCountry);
        COVID_URL_COUNTRY_TODAY=COVID_URL_COUNTRY_TODAY.replace("COUNTRY",myCountry);
        COVID_URL_COUNTRY_YESTERDAY=COVID_URL_COUNTRY_YESTERDAY.replace("COUNTRY",myCountry);

        mGson=new Gson();
        covidApi=new CovidApi(getActivity(), this);
        mprogressDialog= ProgressDialogManager.getProgressDialog(getActivity());






        barChart = view.findViewById(R.id.chart1);
        txtMyCountry=view.findViewById(R.id.txtMyCountry);
        txtGlobal=view.findViewById(R.id.txtGlobal);
        spin_kit=view.findViewById(R.id.spin_kit);


        txtTotalCases=view.findViewById(R.id.txtTotalCases);
        txtDeaths=view.findViewById(R.id.txtDeaths);
        txtRecovered=view.findViewById(R.id.txtRecovered);
        txtActive=view.findViewById(R.id.txtActive);
        txtCritical=view.findViewById(R.id.txtCritical);


        txtTotal=view.findViewById(R.id.txtTotal);
        txtToday=view.findViewById(R.id.txtToday);
        txtYesterday=view.findViewById(R.id.txtYesterday);







        txtTotal.setOnClickListener(this);
        txtToday.setOnClickListener(this);
        txtYesterday.setOnClickListener(this);
        txtMyCountry.setOnClickListener(this);
        txtGlobal.setOnClickListener(this);



        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected()
            {
                String selectedCountryEnglishName = ccp.getSelectedCountryEnglishName();


                selectMC_G(txtMyCountry);
                Current_Code=Constants.COVID_CODE_COUNTRY;
                mprogressDialog.show();

                String URL=null;

                 COVID_URL_COUNTRY_ALL=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=true&twoDaysAgo=true&strict=false".replace("COUNTRY",selectedCountryEnglishName);
                 COVID_URL_COUNTRY_TODAY=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=false&twoDaysAgo=false&strict=false".replace("COUNTRY",selectedCountryEnglishName);;
                 COVID_URL_COUNTRY_YESTERDAY=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=true&twoDaysAgo=false&strict=false".replace("COUNTRY",selectedCountryEnglishName);;

                if(Current_Data_Type==Constants.COVID_DATA_ALL)
                    URL=COVID_URL_COUNTRY_ALL;
                else
                if(Current_Data_Type==Constants.COVID_DATA_TODAY)
                    URL=COVID_URL_COUNTRY_TODAY;
                else
                if(Current_Data_Type==Constants.COVID_DATA_YESTERDAY)
                    URL=COVID_URL_COUNTRY_YESTERDAY;


                covidApi.getData(URL,Current_Data_Type);
            }
        });

    }


    @Override
    public void onCovidResult(String response, int Result_Code, int DataType)
    {

        mprogressDialog.dismiss();
        if(Result_Code==Constants.SUCCESS_CODE)
        {



            if(DataType==Constants.COVID_DATA_ALL || DataType==Constants.COVID_DATA_TODAY || DataType==Constants.COVID_DATA_YESTERDAY)
            {

                COVID_URL_COUNTRY_ALL_PREV=COVID_URL_COUNTRY_ALL;
                COVID_URL_COUNTRY_TODAY_PREV=COVID_URL_COUNTRY_TODAY;
                COVID_URL_COUNTRY_YESTERDAY_PREV=COVID_URL_COUNTRY_YESTERDAY;

                showData(response,DataType);
            }

            if(DataType==Constants.COVID_DATA_STATISTICS )
            {
                showStatistics(response);
            }


        }
        else
        {
            if(DataType==Constants.COVID_DATA_ALL || DataType==Constants.COVID_DATA_TODAY || DataType==Constants.COVID_DATA_YESTERDAY)
            {

                COVID_URL_COUNTRY_ALL=COVID_URL_COUNTRY_ALL_PREV;
                COVID_URL_COUNTRY_TODAY=COVID_URL_COUNTRY_TODAY_PREV;
                COVID_URL_COUNTRY_YESTERDAY=COVID_URL_COUNTRY_YESTERDAY_PREV;

                Snackbar.make(getActivity().findViewById(android.R.id.content),"Country not found or doesn't have any cases", BaseTransientBottomBar.LENGTH_LONG).show();


            }
        }


    }

    private void showStatistics(String response)
    {

        try
        {
            covidStatisticsList.clear();

          JSONArray array=  new JSONArray(response);
          for(int i=0;i<array.length();i++)
          {
              String value= String.valueOf(array.get(i));
              Type type = new TypeToken<CovidStatistics>() {}.getType();
              CovidStatistics covidStatistics = mGson.fromJson(value, type);
              covidStatisticsList.add(covidStatistics);
          }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        try
        {

            covidStatisticsList.sort(new Comparator<CovidStatistics>() {
                @Override
                public int compare(CovidStatistics o1, CovidStatistics o2) {

                    SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault());
                    Date date1;
                    try
                    {
                        date1 = dateFormat.parse(o1.getDate());
                        Date date2=dateFormat.parse(o2.getDate());
                        return date1.compareTo(date2);
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                        System.out.println("PARSE_ERROR");
                    }

                    return 0;

                }
            });
            
        }catch (Exception e)
        {
            e.printStackTrace();
        }




        populateGraph();

    }

    private void showData(String response,int COVID_DATA_TYPE)
    {

        Type type = new TypeToken<CovidAll>() {}.getType();
        CovidAll covidAll = mGson.fromJson(response, type);



        if(COVID_DATA_TYPE==Constants.COVID_DATA_ALL)
        {
            txtTotalCases.setText(converttoM(covidAll.getCases()));
            txtDeaths.setText(converttoM(covidAll.getDeaths()));
            txtRecovered.setText(converttoM(covidAll.getRecovered()));
            txtActive.setText(converttoM(covidAll.getActive()));
            txtCritical.setText(converttoM(covidAll.getCritical()));
        }
        else
        if(COVID_DATA_TYPE==Constants.COVID_DATA_TODAY || COVID_DATA_TYPE==Constants.COVID_DATA_YESTERDAY)
        {
            txtTotalCases.setText(converttoM(covidAll.getTodayCases()));
            txtDeaths.setText(converttoM(covidAll.getTodayDeaths()));
            txtRecovered.setText(converttoM(covidAll.getTodayRecovered()));
            txtActive.setText(converttoM(covidAll.getActive()));
            txtCritical.setText(converttoM(covidAll.getCritical()));
        }

    }


    public  String converttoM(Long cases)
    {


        if(cases<=1000000)
            return convertToK(cases);

        Double val= cases /1000.0;


        return String.format("%.2f K",val);


    }

    private String convertToK(Long todayCases)
    {


        if(todayCases<=1000)
            return String.valueOf(todayCases);

        float val=((float)todayCases)/1000.0f;


        return String.format("%.2f K",val);


    }

    @Override
    public void onClick(View v)
    {

        if(v==txtMyCountry)
        {
            selectMC_G(txtMyCountry);
            Current_Code=Constants.COVID_CODE_COUNTRY;
            mprogressDialog.show();

            String URL=null;
            if(Current_Data_Type==Constants.COVID_DATA_ALL)
                URL=COVID_URL_COUNTRY_ALL;
            else
            if(Current_Data_Type==Constants.COVID_DATA_TODAY)
                URL=COVID_URL_COUNTRY_TODAY;
            else
            if(Current_Data_Type==Constants.COVID_DATA_YESTERDAY)
                URL=COVID_URL_COUNTRY_YESTERDAY;


            covidApi.getData(URL,Current_Data_Type);
        }
        if(v==txtGlobal)
        {
            selectMC_G(txtGlobal);
            mprogressDialog.show();
            Current_Code=Constants.COVID_CODE_ALL;

            String URL=null;
            if(Current_Data_Type==Constants.COVID_DATA_ALL)
                URL=Constants.COVID_URL_ALL;
            else
            if(Current_Data_Type==Constants.COVID_DATA_TODAY)
                URL=Constants.COVID_URL_ALL_TODAY;
            else
            if(Current_Data_Type==Constants.COVID_DATA_YESTERDAY)
                URL=Constants.COVID_URL_ALL_YESTERDAY;

            covidApi.getData(URL,Current_Data_Type);
        }

        if(v==txtTotal)
        {
            selectToday_Yesterday(txtTotal);
            mprogressDialog.show();
            String URl;
            if(Current_Code==Constants.COVID_CODE_ALL)
                URl=Constants.COVID_URL_ALL;
            else
                URl=COVID_URL_COUNTRY_ALL;


            Current_Data_Type=Constants.COVID_DATA_ALL;
            covidApi.getData(URl,Current_Data_Type);
        }

        if(v==txtToday)
        {
            selectToday_Yesterday(txtToday);
            mprogressDialog.show();
            String URl;
            if(Current_Code==Constants.COVID_CODE_ALL)
                URl=Constants.COVID_URL_ALL_TODAY;
            else
                URl=COVID_URL_COUNTRY_TODAY;


            Current_Data_Type=Constants.COVID_DATA_TODAY;

            covidApi.getData(URl,Current_Data_Type);
        }

        if(v==txtYesterday)
        {
            selectToday_Yesterday(txtYesterday);
            mprogressDialog.show();
            String URl;
            if(Current_Code==Constants.COVID_CODE_ALL)
                URl=Constants.COVID_URL_ALL_YESTERDAY;
            else
                URl=COVID_URL_COUNTRY_YESTERDAY;

            Current_Data_Type=Constants.COVID_DATA_YESTERDAY;

            covidApi.getData(URl,Current_Data_Type);
        }




    }





    public  void selectMC_G(TextView textView) //Select MyCountry Or Global
    {


        unSelect(new TextView[]{txtMyCountry,txtGlobal});
        select(textView);

    }
    private void select(TextView txtView)
    {
        txtView.setTextColor(getActivity().getResources().getColor(R.color.purple_200));
        txtView.setBackgroundResource(R.drawable.back_default);
    }

    public  void unSelect(TextView [] textViews)
    {
        for(TextView textView:textViews)
        {
            textView.setTextColor(getActivity().getResources().getColor(R.color.white));
            textView.setBackground(null);
        }
    }

    public  void selectToday_Yesterday(TextView textView) //Select Total , Today Or Yesterday
    {


        unSelect(new TextView[]{txtTotal,txtToday,txtYesterday});
        select(textView);

    }


    public  void populateGraph()
    {



        spin_kit.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);
        ArrayList<BarEntry> entries = new ArrayList<>();


        int index=0;

        for(CovidStatistics covidStatistics:getFirst6())
        {
            Long inK=covidStatistics.getNewConfirmed()/1000;
            entries.add(new BarEntry(++index,inK));
        }


        BarDataSet bardataset = new BarDataSet(entries, "Daily New Cases");



        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(bardataset);

        BarData data = new BarData(dataSets);
        barChart.setDrawGridBackground(false);
        barChart.setData(data); // set the data and list of labels into chart
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.animateY(3000);
        ValueFormatter custom = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value)
            {


                return ((int)value)+" K ";

            }
        };
        barChart.getXAxis().setSpaceMax(1);
        barChart.getXAxis().setEnabled(false);

        YAxis leftAxis = barChart.getAxisLeft();
        //  leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        Legend l = barChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);


        bardataset.setColor(Color.parseColor("#AE160A"));


    }

    private List<CovidStatistics> getFirst6()
    {
        List<CovidStatistics> first6=new ArrayList<>();
        int count=0;
        Collections.reverse(covidStatisticsList);
        for(CovidStatistics covidStatistics:covidStatisticsList)
        {
            if(count<=6)
            {
                first6.add(covidStatistics);
            }
            else
            {
                break;
            }
            count++;

        }

        return first6;

    }
}





