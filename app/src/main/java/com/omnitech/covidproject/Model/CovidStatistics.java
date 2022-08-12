package com.omnitech.covidproject.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CovidStatistics {

    @SerializedName("NewConfirmed")
    @Expose
    private Long newConfirmed;
    @SerializedName("TotalConfirmed")
    @Expose
    private Long totalConfirmed;
    @SerializedName("NewDeaths")
    @Expose
    private Long newDeaths;
    @SerializedName("TotalDeaths")
    @Expose
    private Long totalDeaths;
    @SerializedName("NewRecovered")
    @Expose
    private Long newRecovered;
    @SerializedName("TotalRecovered")
    @Expose
    private Long totalRecovered;

    public Long getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(Long newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public Long getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(Long totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public Long getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(Long newDeaths) {
        this.newDeaths = newDeaths;
    }

    public Long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(Long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public Long getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(Long newRecovered) {
        this.newRecovered = newRecovered;
    }

    public Long getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(Long totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @SerializedName("Date")
    @Expose
    private String date;

}