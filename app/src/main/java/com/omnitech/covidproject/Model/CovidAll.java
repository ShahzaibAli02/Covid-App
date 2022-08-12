
package com.omnitech.covidproject.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CovidAll {

    @SerializedName("updated")
    @Expose
    private Long updated;
    @SerializedName("cases")
    @Expose
    private Long cases;
    @SerializedName("todayCases")
    @Expose
    private Long todayCases;
    @SerializedName("deaths")
    @Expose
    private Long deaths;
    @SerializedName("todayDeaths")
    @Expose
    private Long todayDeaths;
    @SerializedName("recovered")
    @Expose
    private Long recovered;
    @SerializedName("todayRecovered")
    @Expose
    private Long todayRecovered;
    @SerializedName("active")
    @Expose
    private Long active;
    @SerializedName("critical")
    @Expose
    private Long critical;
    @SerializedName("casesPerOneMillion")
    @Expose
    private Long casesPerOneMillion;
    @SerializedName("deathsPerOneMillion")
    @Expose
    private Double deathsPerOneMillion;
    @SerializedName("tests")
    @Expose
    private Long tests;
    @SerializedName("testsPerOneMillion")
    @Expose
    private Double testsPerOneMillion;
    @SerializedName("population")
    @Expose
    private Long population;
    @SerializedName("oneCasePerPeople")
    @Expose
    private Object oneCasePerPeople;
    @SerializedName("oneDeathPerPeople")
    @Expose
    private Object oneDeathPerPeople;
    @SerializedName("oneTestPerPeople")
    @Expose
    private Object oneTestPerPeople;
    @SerializedName("activePerOneMillion")
    @Expose
    private Double activePerOneMillion;
    @SerializedName("recoveredPerOneMillion")
    @Expose
    private Double recoveredPerOneMillion;
    @SerializedName("criticalPerOneMillion")
    @Expose
    private Double criticalPerOneMillion;

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public Long getCases() {
        return cases;
    }

    public void setCases(Long cases) {
        this.cases = cases;
    }

    public Long getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(Long todayCases) {
        this.todayCases = todayCases;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public Long getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(Long todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public Long getRecovered() {
        return recovered;
    }

    public void setRecovered(Long recovered) {
        this.recovered = recovered;
    }

    public Long getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(Long todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public Long getActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }

    public Long getCritical() {
        return critical;
    }

    public void setCritical(Long critical) {
        this.critical = critical;
    }

    public Long getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(Long casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public Double getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(Double deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public Long getTests() {
        return tests;
    }

    public void setTests(Long tests) {
        this.tests = tests;
    }

    public Double getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(Double testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Object getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public void setOneCasePerPeople(Object oneCasePerPeople) {
        this.oneCasePerPeople = oneCasePerPeople;
    }

    public Object getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public void setOneDeathPerPeople(Object oneDeathPerPeople) {
        this.oneDeathPerPeople = oneDeathPerPeople;
    }

    public Object getOneTestPerPeople() {
        return oneTestPerPeople;
    }

    public void setOneTestPerPeople(Object oneTestPerPeople) {
        this.oneTestPerPeople = oneTestPerPeople;
    }

    public Double getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public void setActivePerOneMillion(Double activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public Double getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(Double recoveredPerOneMillion) {
        this.recoveredPerOneMillion = recoveredPerOneMillion;
    }

    public Double getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(Double criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    public Long getAffectedCountries() {
        return affectedCountries;
    }

    public void setAffectedCountries(Long affectedCountries) {
        this.affectedCountries = affectedCountries;
    }

    @SerializedName("affectedCountries")
    @Expose
    private Long affectedCountries;


}
