package com.example.user.connectwifidemo.lib.wifi.model;

/**
 * 功能：扫描结果实体模型
 * 文件名：com.example.user.goviewdemo.device.wifi.model.ScanResultModel.java
 * 作者：yql
 * 创建时间：2016/3/11
 */
public class ScanResultModel {
    private String SSID; //WIFI名称
    private String BSSID; //MAC地址
    private int level; //WIFI强度
    private int frequency; //频率
    private double distance; //距离
    private String capabilities; //描述了身份验证、密钥管理和访问点支持的加密方案
    private long timestamp; //Wifi同步时间

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
