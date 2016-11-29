package com.androidbelieve.materialnavigationdrawer;

/**
 * Created by Stas on 18/05/16.
 */
public class Integrated {

    //Tremp fields
    private String trempDriverId;
    private String trempID;
    private String trempSource;
    private String trempDest;
    private String trempFname;
    private String trempLname;
    private String trempPhone;
    private String trempOutTime;
    private String trempArriveTime;
    private String gender;
    private int smoke;
    private int raiting;
    private int num_of_ratings;
    private int trempPassangers;

    //Bus fields
    private String agency_id;
    private String route_short_name;
    private String agency_name;
    private String busArrival_time;
    private String finalTime;





    //Getters and Setters

    public String getTrempDriverId() {
        return trempDriverId;
    }

    public void setTrempDriverId(String trempDriverId) {
        this.trempDriverId = trempDriverId;
    }

    public String getTrempID() {
        return trempID;
    }

    public void setTrempID(String trempID) {
        this.trempID = trempID;
    }

    public String getTrempSource() {
        return trempSource;
    }

    public void setTrempSource(String trempSource) {
        this.trempSource = trempSource;
    }

    public String getTrempDest() {
        return trempDest;
    }

    public void setTrempDest(String trempDest) {
        this.trempDest = trempDest;
    }

    public String getTrempFname() {
        return trempFname;
    }

    public void setTrempFname(String trempFname) {
        this.trempFname = trempFname;
    }

    public String getTrempLname() {
        return trempLname;
    }

    public void setTrempLname(String trempLname) {
        this.trempLname = trempLname;
    }

    public String getTrempPhone() {
        return trempPhone;
    }

    public void setTrempPhone(String trempPhone) {
        this.trempPhone = trempPhone;
    }

    public String getTrempOutTime() {
        return trempOutTime;
    }

    public void setTrempOutTime(String trempOutTime) {
        this.trempOutTime = trempOutTime;
    }

    public String getTrempArriveTime() {
        return trempArriveTime;
    }

    public void setTrempArriveTime(String trempArriveTime) {
        this.trempArriveTime = trempArriveTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSmoke() {
        return smoke;
    }

    public void setSmoke(int smoke) {
        this.smoke = smoke;
    }

    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    public int getNum_of_ratings() {
        return num_of_ratings;
    }

    public void setNum_of_ratings(int num_of_ratings) {
        this.num_of_ratings = num_of_ratings;
    }

    public int getTrempPassangers() {
        return trempPassangers;
    }

    public void setTrempPassangers(int trempPassangers) {
        this.trempPassangers = trempPassangers;
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

    public String getBusArrival_time() {
        return busArrival_time;
    }

    public void setBusArrival_time(String busArrival_time) {
        this.busArrival_time = busArrival_time;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }



    //Constructors

    public Integrated( ) {

    }


    public Integrated(String trempDriverId, String trempID, String trempSource,
                      String trempDest, String trempFname, String trempLname, String trempPhone,
                      String trempOutTime, String trempArriveTime, String gender,
                      int smoke, int raiting, int num_of_ratings, int trempPassangers,
                      String agency_id, String route_short_name, String agency_name,
                      String busArrival_time, String finalTime) {

        this.trempDriverId = trempDriverId;
        this.trempID = trempID;
        this.trempSource = trempSource;
        this.trempDest = trempDest;
        this.trempFname = trempFname;
        this.trempLname = trempLname;
        this.trempPhone = trempPhone;
        this.trempOutTime = trempOutTime;
        this.trempArriveTime = trempArriveTime;
        this.gender = gender;
        this.smoke = smoke;
        this.raiting = raiting;
        this.num_of_ratings = num_of_ratings;
        this.trempPassangers = trempPassangers;
        this.agency_id = agency_id;
        this.route_short_name = route_short_name;
        this.agency_name = agency_name;
        this.busArrival_time = busArrival_time;
        this.finalTime = finalTime;
    }





}
