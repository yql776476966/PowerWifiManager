package com.example.user.connectwifidemo;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.user.connectwifidemo.adapter.WifiAdapter;
import com.example.user.connectwifidemo.lib.wifi.PowerWifiManager;
import com.example.user.connectwifidemo.lib.wifi.model.ScanResultModel;
import com.example.user.connectwifidemo.model.WifiAdapterModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //---------------基本View控件---------------
    private ListView wifiListView;

    //---------------基本变量---------------
    private Context context;
    private WifiAdapter adapter; //适配器
    private PowerWifiManager powerWifiManager; //Wifi管理器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_activity_main);
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        initFields();
        initListener();
        //封装一个数据源，
        // 1、wifi是否开启，
        // 2、连接的WLAN，
        // 3、当前wifi，
        // 2、选中附近wifi
        // 4、wifi列表

        //----------------构造WifiAdapter数据源-------------------
        List<WifiAdapterModel> wifiAdapterModels = new ArrayList<WifiAdapterModel>(); //adapter数据源
        int wifiStatus = powerWifiManager.getWifiStatus(); //WifiManager.WIFI_STATE_ENABLED
        WifiInfo curWifiInfo = powerWifiManager.getCurConnectedWifiInfo();
        List<ScanResultModel> scanResultModels = powerWifiManager.getScanResultModel();

        if(wifiStatus == WifiManager.WIFI_STATE_ENABLED) {
            //wifi开启状态
            WifiAdapterModel wifiStatusModel = new WifiAdapterModel();
            wifiStatusModel.setMarkType(0);
            wifiStatusModel.setWifiStatus(wifiStatus);
            wifiAdapterModels.add(wifiStatusModel);

            //提示1
            WifiAdapterModel hintModel = new WifiAdapterModel();
            hintModel.setMarkType(1);
            wifiAdapterModels.add(hintModel);

            //当前wifi状态
            WifiAdapterModel curInfoModel = new WifiAdapterModel();
            curInfoModel.setMarkType(2);
            curInfoModel.setCurWifiInfo(curWifiInfo);
            curInfoModel.setKeyType(powerWifiManager.getKeyType(curWifiInfo.getSSID()));
            wifiAdapterModels.add(curInfoModel);

            //提示2
            WifiAdapterModel hintModelTwo = new WifiAdapterModel();
            hintModelTwo.setMarkType(1);
            wifiAdapterModels.add(hintModelTwo);

            //当前扫描结果
            String curWifiSSID = curWifiInfo.getSSID().replace("\"", "");
            for(ScanResultModel scanResultModel : scanResultModels) {
                if (!scanResultModel.getSSID().equals(curWifiSSID)) {
                    WifiAdapterModel scanResultAdapterModel = new WifiAdapterModel();
                    scanResultAdapterModel.setScanResultModel(scanResultModel);
                    scanResultAdapterModel.setMarkType(3);
                    scanResultAdapterModel.setKeyType(powerWifiManager.getKeyType(scanResultModel.getSSID()));
                    wifiAdapterModels.add(scanResultAdapterModel);
                }
            }

            adapter = new WifiAdapter(context, wifiAdapterModels);
            wifiListView.setAdapter(adapter);
        }
    }

    /**
     * 初始化字段
     */
    private void initFields() {
        wifiListView = (ListView) findViewById(R.id.wifi_list);
        context = MainActivity.this;
        powerWifiManager = PowerWifiManager.getInstance();
        powerWifiManager.initConfig(context); //初始化配置


    }

    /**
     * 初始化监听器
     */
    private void initListener() {

    }
}
