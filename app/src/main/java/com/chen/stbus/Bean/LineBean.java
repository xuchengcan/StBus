package com.chen.stbus.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen on 2017/6/10.
 */

public class LineBean implements Serializable {

    public String BusCompany;
    public String BusModifyTime;
    public String DownEndTime;
    public String DownMileage;
    public String DownSiteNum;
    public String DownStartTime;
    public String Extend;
    public String Fare;
    public String Id;
    public String IsResolved;
    public String IsUnityFare;
    public String LineName;
    public String ModifyTime;
    public String PointModifyTime;
    public String Remark;
    public String SiteModifyTime;
    public List<StartEndSite> StartEndSites;
    public String UpEndTime;
    public String UpMileage;
    public String UpSiteNum;
    public String UpStartTime;
    public String Visible;

    public String UpStartState;
    public String UpEndState;
    public String DownStartState;
    public String DownEndState;

    public void getStateName(){
        for (int i = 0;i < StartEndSites.size();i++){
            if ("1".equals(StartEndSites.get(i).UpDown)){
                if ("1".equals(StartEndSites.get(i).Seq)){
                    UpStartState = StartEndSites.get(i).SiteName;
                }else {
                    UpEndState = StartEndSites.get(i).SiteName;
                }
            }else {
                if ("1".equals(StartEndSites.get(i).Seq)){
                    DownStartState = StartEndSites.get(i).SiteName;
                }else {
                    DownEndState = StartEndSites.get(i).SiteName;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "LineBean{" +

                ", BusCompany='" + BusCompany + '\'' +
                ", BusModifyTime='" + BusModifyTime + '\'' +
                ", DownEndTime='" + DownEndTime + '\'' +
                ", DownMileage='" + DownMileage + '\'' +
                ", DownSiteNum='" + DownSiteNum + '\'' +
                ", DownStartTime='" + DownStartTime + '\'' +
                ", Extend='" + Extend + '\'' +
                ", Fare='" + Fare + '\'' +
                ", Id='" + Id + '\'' +
                ", IsResolved='" + IsResolved + '\'' +
                ", IsUnityFare='" + IsUnityFare + '\'' +
                ", LineName='" + LineName + '\'' +
                ", ModifyTime='" + ModifyTime + '\'' +
                ", PointModifyTime='" + PointModifyTime + '\'' +
                ", Remark='" + Remark + '\'' +
                ", SiteModifyTime='" + SiteModifyTime + '\'' +
                ", StartEndSites=" + StartEndSites +
                ", UpEndTime='" + UpEndTime + '\'' +
                ", UpMileage='" + UpMileage + '\'' +
                ", UpSiteNum='" + UpSiteNum + '\'' +
                ", UpStartTime='" + UpStartTime + '\'' +
                ", Visible='" + Visible + '\'' +
                '}';
    }
}
