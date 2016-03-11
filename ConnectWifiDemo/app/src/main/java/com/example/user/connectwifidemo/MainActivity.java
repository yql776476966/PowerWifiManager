package com.example.user.connectwifidemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.user.connectwifidemo.lib.wifi.PowerWifiManager;
import com.example.user.connectwifidemo.lib.wifi.model.ScanResultModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //---------------基本View控件---------------
    private ListView wifiListView;

    //---------------基本变量---------------
    private Context context;
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
        List<ScanResultModel> scanResultModels = powerWifiManager.getScanResultModel();
        int a = 1;
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
