package com.chen.stbus.Bean;

import com.socks.library.KLog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen on 2017/6/13.
 */

public class BusBean implements Serializable {
    public String FirstBus;
    public String IsUseGPS;
    public String LineId;
    public String LineName;
    public List<StateBean> List;
    public String Remark;
    public String SecondBus;
    public String SiteId;
    public String SiteName;
    public String UpDown;

    public double Center_Latitude;
    public double Center_Longitude;

    public void getCenterPosition() {
        if (List != null && List.size() > 1) {
            Center_Latitude = (Double.parseDouble(List.get(0).Latitude) + Double.parseDouble(List.get(List.size() - 1).Latitude)) / 2;
            Center_Longitude = (Double.parseDouble(List.get(0).Longitude) + Double.parseDouble(List.get(List.size() - 1).Longitude)) / 2;
            KLog.i(Center_Latitude + "--" + Center_Longitude);
        }
    }

}
