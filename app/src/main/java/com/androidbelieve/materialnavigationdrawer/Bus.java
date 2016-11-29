package com.androidbelieve.materialnavigationdrawer;

/**
 * Created by USER on 08/05/2016.
 */
public class Bus {


    private String route_short_name;
    private String agency_name;
    private String arrival_time;
    private String agency_id;
    private String final_time;

    public String getFinal_time() {
        return final_time;
    }

    public void setFinal_time(String final_time) {
        this.final_time = final_time;
    }

    public Bus(){

    }

    public Bus(String route_short_name, String agency_name, String arrival_time,String agency_id,String final_time)
    {
        this.route_short_name=route_short_name;
        this.agency_name=agency_name;
        this.arrival_time=arrival_time;
        this.agency_id=agency_id;
        this.final_time=final_time;



    }



    public String getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(String agency_id) {
        this.agency_id = agency_id;
    }

    public String getRoute_short_name() {
        return route_short_name;
    }

    public void setRoute_short_name(String route_short_name) {
        this.route_short_name = route_short_name;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }


}
