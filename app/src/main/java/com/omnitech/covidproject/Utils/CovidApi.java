package com.omnitech.covidproject.Utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.omnitech.covidproject.Interfaces.ApiCallBack;

import java.util.HashMap;
import java.util.Map;



public class CovidApi
{


    RequestQueue queue;

    ApiCallBack staticsResult;
    Context mContext;
    public  CovidApi(Context context, ApiCallBack staticsResult)
    {
        queue = Volley.newRequestQueue(context);
        this.staticsResult=staticsResult;
        mContext=context;
    }


    public   void getData(String URL,int DATA_CODE)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                response -> {
                    staticsResult.onCovidResult(response, Constants.SUCCESS_CODE, DATA_CODE);
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        staticsResult.onCovidResult(null, Constants.FAIL_CODE, DATA_CODE);
                    }
                }) { //no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                if(URL.equals(Constants.COVID_URL_NEWS))
                         params.put("User-Agent" , "Mozilla/5.0");
                return params;
            }
        };;
        queue.add(stringRequest);
    }

    public   void getStatistics(int DATA_CODE)
    {



        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.COVID_URL_STATISTICS,
                response ->
                {
                    staticsResult.onCovidResult(response, Constants.SUCCESS_CODE, DATA_CODE);
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        staticsResult.onCovidResult(null, Constants.FAIL_CODE, DATA_CODE);
                    }
                }) ;
        queue.add(stringRequest);


    }



}
