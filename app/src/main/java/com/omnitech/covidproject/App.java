package com.omnitech.covidproject;

import android.app.Application;

import com.omnitech.covidproject.Utils.Constants;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Constants.COVID_URL_COUNTRY_ALL=Constants.COVID_URL_COUNTRY_ALL.replace("COUNTRY",getResources().getConfiguration().getLocales().get(0).getDisplayCountry());
        Constants.COVID_URL_COUNTRY_TODAY=Constants.COVID_URL_COUNTRY_TODAY.replace("COUNTRY",getResources().getConfiguration().getLocales().get(0).getDisplayCountry());
        Constants.COVID_URL_COUNTRY_YESTERDAY=Constants.COVID_URL_COUNTRY_YESTERDAY.replace("COUNTRY",getResources().getConfiguration().getLocales().get(0).getDisplayCountry());

      /*  Constants.COVID_URL_COUNTRY_ALL=Constants.COVID_URL_COUNTRY_ALL.replace("COUNTRY","Malaysia");
        Constants.COVID_URL_COUNTRY_TODAY=Constants.COVID_URL_COUNTRY_TODAY.replace("COUNTRY","Malaysia");
        Constants.COVID_URL_COUNTRY_YESTERDAY=Constants.COVID_URL_COUNTRY_YESTERDAY.replace("COUNTRY","Malaysia");


       */


    }
}
