package com.example.user.connectwifidemo.adapter;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.connectwifidemo.R;
import com.example.user.connectwifidemo.lib.wifi.model.ScanResultModel;

import java.util.List;

/**
 * Created by yql on 2016/3/12.
 */
public class WifiAdapter extends BaseAdapter {

    private LayoutInflater inflater; //布局填充器

    //-----------------基本View变量------------------
    //三种不同的布局
    private static final int WIFI_SWITCH_TYPE = 0;
    private static final int WIFI_HINT_TYPE = 1;
    private static final int WIFI_CUR_CONNECTED_TYPE = 2;
    private static final int WIFI_BODY_TYPE = 3;
    //数据源
    private int wifiStatus; //wifi是否开启
    private WifiInfo curWifiInfo; //当前连接wifi
    private List<ScanResultModel> scanResultModels; //扫描范围内wifi列表
    private int dataSourceSize; //数据源大小

    public WifiAdapter(Context context, int wifiStatus, WifiInfo curWifiInfo, List<ScanResultModel> scanResultModels) {
        inflater = LayoutInflater.from(context);
        this.wifiStatus = wifiStatus;
        this.curWifiInfo = curWifiInfo;
        this.scanResultModels = scanResultModels;
        this.dataSourceSize = scanResultModels.size() + 4;
    }


    @Override
    public int getCount() {
        return dataSourceSize;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderWifiSwitch wifiSwitch = null;
        ViewHolderWifiBody wifiBody = null;

        int type = 0;
        if(convertView ==null) {
            switch (type) {
                case WIFI_SWITCH_TYPE:
                    wifiSwitch = new ViewHolderWifiSwitch();
                    convertView = inflater.inflate(R.layout.wifi_list_item_switch, null);
                    wifiSwitch.openWifi = (ImageView) convertView.findViewById(R.id.open_wifi);
                    convertView.setTag(wifiSwitch);
                    break;
                case WIFI_HINT_TYPE:
                    convertView = inflater.inflate(R.layout.wifi_list_item_hint, null);
                    break;
                case WIFI_CUR_CONNECTED_TYPE:
                    wifiBody = new ViewHolderWifiBody();
                    convertView = inflater.inflate(R.layout.wifi_list_item, null);
                    wifiBody.connectiveStatus = (TextView) convertView.findViewById(R.id.connective_status);
                    wifiBody.lockMark = (ImageView) convertView.findViewById(R.id.lock_mark);
                    wifiBody.signalLevel = (ImageView) convertView.findViewById(R.id.signal_level);
                    wifiBody.wifiDetail = (ImageView) convertView.findViewById(R.id.wifi_detail);
                    convertView.setTag(wifiBody);
                    break;
                case WIFI_BODY_TYPE:
                    wifiBody = new ViewHolderWifiBody();
                    convertView = inflater.inflate(R.layout.wifi_list_item, null);
                    wifiBody.connectiveStatus = (TextView) convertView.findViewById(R.id.connective_status);
                    wifiBody.lockMark = (ImageView) convertView.findViewById(R.id.lock_mark);
                    wifiBody.signalLevel = (ImageView) convertView.findViewById(R.id.signal_level);
                    wifiBody.wifiDetail = (ImageView) convertView.findViewById(R.id.wifi_detail);
                    convertView.setTag(wifiBody);
                    break;
                default:
                    break;
            }
        } else {
            switch (type) {
                case WIFI_SWITCH_TYPE:
                    wifiSwitch = (ViewHolderWifiSwitch) convertView.getTag();
                    break;
                case WIFI_HINT_TYPE:
                    break;
                case WIFI_CUR_CONNECTED_TYPE:
                    break;
                case WIFI_BODY_TYPE:
                    wifiBody = (ViewHolderWifiBody) convertView.getTag();
                    break;
                default:
                    break;
            }
        }

        return convertView;
    }


    class ViewHolderWifiSwitch {
        ImageView openWifi; //开启Wifi
    }

    class ViewHolderWifiBody {
        TextView connectiveStatus; //Wifi连接状态
        ImageView lockMark; //是否需要密码
        ImageView signalLevel; //信号水平
        ImageView wifiDetail; //Wifi详情
    }
}
