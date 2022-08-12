package com.omnitech.covidproject.Utils;

public class Constants
{



    public  static  int SUCCESS_CODE=200;
    public  static  int FAIL_CODE=100;


    public  static int COVID_CODE_ALL=1;
    public  static int COVID_CODE_COUNTRY=2;


    public  static int COVID_DATA_ALL=3;
    public  static int COVID_DATA_TODAY=4;
    public  static int COVID_DATA_YESTERDAY=5;
    public  static int COVID_DATA_STATISTICS=6;
    public  static int COVID_DATA_NEWS=7;

    public  static  String COVID_BASE_URL="https://disease.sh";
    public  static  String COVID_URL_ALL=COVID_BASE_URL+"/v3/covid-19/all?yesterday=true&twoDaysAgo=true&allowNull=true";
    public  static  String COVID_URL_ALL_TODAY=COVID_BASE_URL+"/v3/covid-19/all?yesterday=false&twoDaysAgo=false&allowNull=true";
    public  static  String COVID_URL_ALL_YESTERDAY=COVID_BASE_URL+"/v3/covid-19/all?yesterday=true&twoDaysAgo=false&allowNull=true";




    public  static  String COVID_URL_COUNTRY_ALL=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=true&twoDaysAgo=true&strict=false";
    public  static  String COVID_URL_COUNTRY_TODAY=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=false&twoDaysAgo=false&strict=false";
    public  static  String COVID_URL_COUNTRY_YESTERDAY=COVID_BASE_URL+"/v3/covid-19/countries/COUNTRY?yesterday=true&twoDaysAgo=false&strict=false";





    public  static  String COVID_URL_NEWS="https://newsapi.org/v2/everything?q=Covid&apiKey=235868cd3a2546a39e01f97606317d8e";

    public  static String  COVID_URL_STATISTICS="https://api.covid19api.com/world";


    public  static  String Hospitals_URL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=LAT,LONG&radius=1500&type=hospital&key=AIzaSyDioYd868cHeeF5TgZEDYzDvSqD2gaMb6g";


}
