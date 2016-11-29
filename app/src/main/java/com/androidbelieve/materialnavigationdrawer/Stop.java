package com.androidbelieve.materialnavigationdrawer;

/**
 * Created by stas on 05/03/2016.
 */
public class Stop {
    private String stop_id;
    private String stop_name;
    private String stop_desc;
    public String stop_code;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String direction;


    //This var will use us  to differentiate between source and dest

    public Stop(String stop_id, String stop_name, String stop_desc,String stop_code,String direction) {
        this.stop_id = stop_id;
        this.stop_name = stop_name;
        this.stop_desc = stop_desc;
        this.stop_code=stop_code;
        this.direction =direction;
    }


    public String getStop_id() { return this.stop_id;}

    public String getStop_name() {
        return this.stop_name;
    }

    public String getStop_desc() {
        return this.stop_desc;
    }

    public String getStop_code() {return this.stop_code;

    }
}



