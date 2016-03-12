package com.example.user.connectwifidemo.lib.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


import com.example.user.connectwifidemo.lib.wifi.model.ScanResultModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：wifi管理器
 * 文件名：com.example.user.goviewdemo.device.wifi.PowerWifiManager.java
 * 作者：yql
 * 创建时间：2016/3/11
 * 描述：这是有里程碑意义的封装，意义重大，所以请不要打扰、终止、劝我放弃做重要的事情。
 */
public class PowerWifiManager {

    //-------------类内部相关变量-------------
    private static PowerWifiManager instance; //强大的wifi管理器

    //------------wif相关变量-------------
    private WifiManager rawWifiManager; //SDK原生Wifi管理器


    private void PowerWifiManager() {} //禁止instance对象

    //-------单例模式，懒汉，线程安全--------
    public static synchronized PowerWifiManager getInstance() {
        if(instance == null) {
            instance = new PowerWifiManager();
        }
        return instance;
    }

    /**
     * 强大的WifiManager初始化工作
     */
    public void initConfig(Context context) {
        rawWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获得强大的WifiManager管理器
     * @return
     */
    public WifiManager getRawWifiManager() {
        return rawWifiManager;
    }

    /**
     * 检查WIFI开启状态
     * @return 返回WIFI_STATE_DISABLED, //wifi关闭状态
     * WIFI_STATE_DISABLING , //wifi正在关闭状态
     * WIFI_STATE_ENABLED, //wifi开启状态
     * WIFI_STATE_ENABLING, //wifi正在开启状态
     * WIFI_STATE_UNKNOWN //未知状态，不在以上四种状态时未知状态
     * 这五种状态中的一种
     */
    public int getWifiStatus() {
        return rawWifiManager.getWifiState();
    }

    /**
     * 开启或者关闭wifi
     * @param isOpenWifi true开启wifi，false关闭wifi
     * @return 操作成功返回true
     */
    public boolean openOrCloseWifi(boolean isOpenWifi) {
        return rawWifiManager.setWifiEnabled(isOpenWifi);
    }

    /**
     * 开启wifi扫描
     */
    public boolean startWifiScan() {
        //判断wifi是否开启
        if(getWifiStatus() == WifiManager.WIFI_STATE_ENABLED) {
            return  rawWifiManager.startScan();
        }
        return false;
    }

    /**
     * 获得扫描结果
     * @return
     */
    public List<ScanResult> getScanResults() {
        //先开启扫描获得最新wifi结果集
        if(startWifiScan()) {
            return rawWifiManager.getScanResults();
        }
        return null;
    }

    /**
     * 获取扫描结果ScanResultModel实体模型集合
     * @return
     */
    public List<ScanResultModel> getScanResultModel() {
        List<ScanResultModel> scanResultModels = new ArrayList<ScanResultModel>();
        List<ScanResult> scanResults = getScanResults();
        if(scanResults != null) {
            for (ScanResult scanResult : scanResults) {
                ScanResultModel scanResultModel = new ScanResultModel();
                scanResultModel.setSSID(scanResult.SSID);
                scanResultModel.setBSSID(scanResult.BSSID);
                scanResultModel.setLevel(scanResult.level);
                scanResultModel.setCapabilities(scanResult.capabilities); //是否需要密码
                scanResultModel.setFrequency(scanResult.frequency);
                scanResultModels.add(scanResultModel);
            }
            return scanResultModels;
        }
        return null;
    }

    /**
     * 获取当前环境下所有网络配置信息
     * @return
     */
    public List<WifiConfiguration> getConfiguredNetworks() {
        return rawWifiManager.getConfiguredNetworks();
    }

    /**
     * 检查之前是否配置过该网，并返回该网络配置
     * @param SSID 网络名称（WIFI名称）
     * @return 存在则返回网络配置对象WifiConfiguration，否则返回null
     */
    public WifiConfiguration checkPreWhetherConfigured(String SSID) {
        List<WifiConfiguration> existingConfigs = getConfiguredNetworks();
        for(WifiConfiguration configuration : existingConfigs) {
            if(configuration.SSID.equals(SSID)) {
                return configuration;
            }
        }
        return null;
    }

    /**
     * 添加一个新网络到本地网络列表，添加完成后默认是不激活状态，需要掉用enableNetwork函数连接
     * @param SSID wifi名称
     * @param password 需要连接wifi密码
     * @return 添加成功返回网络标示(networkId)
     */
    public int addWifiConfig(String SSID, String password) {
        //检查本地配置列表是否存在，不存在进行添加
        if(checkPreWhetherConfigured(SSID) == null) {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + SSID + "\""; //为什么要这样，看源码注释
            wifiConfiguration.preSharedKey = "\"" + password + "\"";
            wifiConfiguration.hiddenSSID = false;
            wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
            return rawWifiManager.addNetwork(wifiConfiguration);
        }
        return -1;
    }

    /**
     * 通过指定networkId连接wifi
     * @param networkId
     * @return 连接成功返回true，否则返回false
     */
    public boolean connectWifByNetWorkId(int networkId) {
        List<WifiConfiguration> wifiConfigurations = getConfiguredNetworks();
        for(WifiConfiguration configuration : wifiConfigurations) {
            if(configuration.networkId == networkId) {
                return rawWifiManager.enableNetwork(networkId, true); //激活该ID Wifi连接
            }
        }
        return false;
    }

    //-----------------------------当前连接wifi信息-----------------------------------
    /**
     * 得到当前连接Wifi信息
     * @return
     */
    public WifiInfo getCurConnectedWifiInfo() {
        return rawWifiManager.getConnectionInfo();
    }

    /**
     * 得到连接的MAC地址
     * @return
     */
    public String getConnectedMacAddress() {
        WifiInfo wifiInfo = getCurConnectedWifiInfo();
        return (wifiInfo == null) ? "null" : wifiInfo.getMacAddress();
    }

    /**
     * 得到连接的名称SSID
     * @return
     */
    public String getConnectedSSID() {
        WifiInfo wifiInfo = getCurConnectedWifiInfo();
        return (wifiInfo == null) ? "null" : wifiInfo.getSSID();
    }

    //得到连接的IP地址
    public int getConnectedIPAddress(){
        WifiInfo wifiInfo = getCurConnectedWifiInfo();
        return (wifiInfo == null) ?  0 : wifiInfo.getIpAddress();
    }

    //得到连接的networkId
    public int getConnectedID(){
        WifiInfo wifiInfo = getCurConnectedWifiInfo();
        return (wifiInfo == null) ?  0 : wifiInfo.getNetworkId();
    }
}
