package com.androidbelieve.materialnavigationdrawer;

/**
 * Created by stas on 17/04/16.
 */
public class Tremps {               //Tremp Class,just getters and seters.

    private String trempID;
    private String trempDriverId;
    private String trempFname;
    private String trempLname;
    private String trempPhone;
    private String gender;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
    private int smoke;
    //
    private int pay;
    private int raiting;
    private int num_of_ratings;
    private String trempSource;
    private String trempDest;
    private String trempOutTime;
    private String trempArriveTime;
    private int trempPassangers;

    public String getTrempID() {
        return trempID;
    }

    public void setTrempID(String trempID) {
        this.trempID = trempID;
    }

    public String getTrempDriverId() {
        return trempDriverId;
    }

    public void setTrempDriverId(String trempDriverId) {
        this.trempDriverId = trempDriverId;
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

    public int getTrempPassangers() {
        return trempPassangers;
    }

    public void setTrempPassangers(int trempPassangers) {
        this.trempPassangers = trempPassangers;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public Tremps() {

    }
    public Tremps(String trempID,String trempSource,String trempDest,String trempOutTime,String date,String gender) {
        this.trempID = trempID;
        this.trempSource = trempSource;
        this.trempDest = trempDest;
        this.trempOutTime = trempOutTime;
        this.date=date;
        this.gender=gender;

    }

    public Tremps(String trempID,String trempDriverId,String trempPhone,String trempFname,String trempLname) {
        this.trempID = trempID;
        this.trempDriverId = trempDriverId;
        this.trempPhone = trempPhone;
        this.trempFname=trempFname;
        this.trempLname=trempLname;

    }


    public Tremps(String trempPhone,String trempFname,String trempLname) {
        this.trempPhone = trempPhone;
        this.trempFname=trempFname;
        this.trempLname=trempLname;

    }



    public Tremps(String trempID, String trempDriverId,
                  String trempFname, String trempLname, String trempPhone,
                  String gender, int smoke, int raiting, int num_of_ratings,int pay,
                  String trempSource, String trempDest, String trempOutTime,
                  String trempArriveTime, int trempPassangers) {

        this.trempID = trempID;
        this.trempDriverId = trempDriverId;
        this.trempFname = trempFname;
        this.trempLname = trempLname;
        this.trempPhone = trempPhone;
        this.gender = gender;
        this.smoke = smoke;
        this.raiting = raiting;
        this.num_of_ratings = num_of_ratings;
        this.trempSource = trempSource;
        this.trempDest = trempDest;
        this.trempOutTime = trempOutTime;
        this.trempArriveTime = trempArriveTime;
        this.trempPassangers = trempPassangers;
        this.pay=pay;
    }
}



