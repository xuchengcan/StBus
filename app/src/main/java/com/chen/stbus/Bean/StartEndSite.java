package com.chen.stbus.Bean;

import java.io.Serializable;

/**
 * Created by chen on 2017/6/10.
 */

public class StartEndSite implements Serializable{
    public String BusList;
    public String Latitude;
    public String LineId;
    public String LineName;
    public String Longitude;
    public String Seq;
    public String SiteId;
    public String SiteName;
    public String UpDown;

    @Override
    public String toString() {
        return "StartEndSite{" +
                "BusList='" + BusList + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", LineId='" + LineId + '\'' +
                ", LineName='" + LineName + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Seq='" + Seq + '\'' +
                ", SiteId='" + SiteId + '\'' +
                ", SiteName='" + SiteName + '\'' +
                ", UpDown='" + UpDown + '\'' +
                '}';
    }
}
