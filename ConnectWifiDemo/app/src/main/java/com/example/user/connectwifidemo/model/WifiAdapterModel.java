package com.example.user.connectwifidemo.model;

import android.net.wifi.WifiInfo;

import com.example.user.connectwifidemo.lib.wifi.model.ScanResultModel;

/**
 * 功能：WifiAdapter数据源实体模型
 * 文件名：com.example.user.connectwifidemo.model.WifiAdapterModel.java
 * 作者：yql
 * 创建时间：2016/3/12
 */
public class WifiAdapterModel {
    private int markType; //listItem显示类型
    private int wifiStatus; //Wifi是否开启
    private WifiInfo curWifiInfo; //当前连接wifi
    private ScanResultModel scanResultModel; //扫描对象

    public int getMarkType() {
        return markType;
    }

    public void setMarkType(int markType) {
        this.markType = markType;
    }

    public int getWifiStatus() {
        return wifiStatus;
    }

    public void setWifiStatus(int wifiStatus) {
        this.wifiStatus = wifiStatus;
    }

    public WifiInfo getCurWifiInfo() {
        return curWifiInfo;
    }

    public void setCurWifiInfo(WifiInfo curWifiInfo) {
        this.curWifiInfo = curWifiInfo;
    }

    public ScanResultModel getScanResultModel() {
        return scanResultModel;
    }

    public void setScanResultModel(ScanResultModel scanResultModel) {
        this.scanResultModel = scanResultModel;
    }
}
