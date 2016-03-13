package com.example.user.connectwifidemo.adapter;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.connectwifidemo.R;
import com.example.user.connectwifidemo.lib.wifi.PowerWifiManager;
import com.example.user.connectwifidemo.lib.wifi.model.ScanResultModel;
import com.example.user.connectwifidemo.model.WifiAdapterModel;

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
    private static final int MAX_COUNT_TYPE = 4; //四种布局

    private static final int NUMBER_LEVELS = 5; //wifi分为5级

    PowerWifiManager powerWifiManager; //wifi管理器
    List<WifiAdapterModel> wifiAdapterModels; //数据源


    public WifiAdapter(Context context, List<WifiAdapterModel> wifiAdapterModels) {
        inflater = LayoutInflater.from(context);
        this.wifiAdapterModels = wifiAdapterModels;
        this.powerWifiManager = PowerWifiManager.getInstance();
    }


    @Override
    public int getCount() {
        return wifiAdapterModels.size();
    }

    @Override
    public Object getItem(int position) {
        return wifiAdapterModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderWifiSwitch wifiSwitch = null;
        ViewHolderWifiBody wifiBody = null;
        ViewHolderWifiHint wifiHint = null;

        WifiAdapterModel wifiAdapterModel = wifiAdapterModels.get(position);
        int showType = getItemViewType(position);

        if(convertView ==null) {
            switch (showType) {
                case WIFI_SWITCH_TYPE:
                    wifiSwitch = new ViewHolderWifiSwitch();
                    convertView = inflater.inflate(R.layout.wifi_list_item_switch, null);
                    wifiSwitch.openWifi = (ImageView) convertView.findViewById(R.id.open_wifi);
                    convertView.setTag(wifiSwitch);
                    wifiSwitch.openWifi.setOnClickListener(new WifiOnClickListener());
                    break;
                case WIFI_HINT_TYPE:
                    wifiHint = new ViewHolderWifiHint();
                    convertView = inflater.inflate(R.layout.wifi_list_item_hint, null);
                    wifiHint.operateHint = (TextView) convertView.findViewById(R.id.operation_hint);
                    convertView.setTag(wifiHint);
                    break;
                case WIFI_CUR_CONNECTED_TYPE:
                    wifiBody = new ViewHolderWifiBody();
                    convertView = inflater.inflate(R.layout.wifi_list_item_current, null);
                    wifiBody.wifiName = (TextView) convertView.findViewById(R.id.wifi_name);
                    wifiBody.connectiveStatus = (TextView) convertView.findViewById(R.id.connective_status);
                    wifiBody.lockMark = (ImageView) convertView.findViewById(R.id.lock_mark);
                    wifiBody.signalLevel = (ImageView) convertView.findViewById(R.id.signal_level);
                    wifiBody.wifiDetail = (ImageView) convertView.findViewById(R.id.wifi_detail);
                    convertView.setTag(wifiBody);

                    break;
                case WIFI_BODY_TYPE:
                    wifiBody = new ViewHolderWifiBody();
                    convertView = inflater.inflate(R.layout.wifi_list_item, null);
                    wifiBody.wifiName = (TextView) convertView.findViewById(R.id.wifi_name);
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
            switch (showType) {
                case WIFI_SWITCH_TYPE:
                    wifiSwitch = (ViewHolderWifiSwitch) convertView.getTag();
                    break;
                case WIFI_HINT_TYPE:
                    wifiHint = (ViewHolderWifiHint) convertView.getTag();
                    break;
                case WIFI_CUR_CONNECTED_TYPE:
                    wifiBody = (ViewHolderWifiBody) convertView.getTag();
                    break;
                case WIFI_BODY_TYPE:
                    wifiBody = (ViewHolderWifiBody) convertView.getTag();
                    break;
                default:
                    break;
            }
        }

        switch (showType) {
            case WIFI_SWITCH_TYPE:
                if(wifiAdapterModel.getWifiStatus() == WifiManager.WIFI_STATE_ENABLED) {
                    wifiSwitch.openWifi.setImageResource(R.mipmap.wifi_open);
                    wifiSwitch.openWifi.setTag(1); //1-打开状态
                } else {
                    wifiSwitch.openWifi.setImageResource(R.mipmap.wifi_close);
                    wifiSwitch.openWifi.setTag(0); //0-关闭状态
                }
                break;
            case WIFI_HINT_TYPE:
                if(position == 1) {
                    wifiHint.operateHint.setText("连接的WLAN");
                } else if(position == 3) {
                    wifiHint.operateHint.setText("选取附近的WLAN");
                }
                break;
            case WIFI_CUR_CONNECTED_TYPE:
                WifiInfo cWifiInfo = wifiAdapterModel.getCurWifiInfo();
                wifiBody.wifiName.setText(cWifiInfo.getSSID().replace("\"", ""));
                int level = powerWifiManager.getSignalLevelByScanResults(cWifiInfo.getBSSID());
                showWifiLevel(wifiBody.signalLevel, level);
                wifiBody.connectiveStatus.setText("已连接");
                break;
            case WIFI_BODY_TYPE:
                ScanResultModel scanResultModel = wifiAdapterModel.getScanResultModel();
                wifiBody.wifiName.setText(scanResultModel.getSSID());
                showWifiLevel(wifiBody.signalLevel, scanResultModel.getLevel());
                wifiBody.connectiveStatus.setText(wifiAdapterModel.getKeyType());
                break;
            default:
                break;
        }

        return convertView;
    }

    /**
     * 返回所有的layout的数量
     * */
    @Override
    public int getViewTypeCount() {
        return MAX_COUNT_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        WifiAdapterModel wifiAdapterModel = wifiAdapterModels.get(position);
        int type = wifiAdapterModel.getMarkType();
        return type;
    }

    private void showWifiLevel(ImageView imageView, int level) {

        int signal = powerWifiManager.calculateSignalLevel(level, NUMBER_LEVELS);
        switch (signal) {
            case 0: //0格信号
                imageView.setImageResource(R.mipmap.wifi_level_0);
                break;
            case 1: //1格信号
                imageView.setImageResource(R.mipmap.wifi_level_1);
                break;
            case 2: //2格信号
                imageView.setImageResource(R.mipmap.wifi_level_2);
                break;
            case 3: //3格信号
                imageView.setImageResource(R.mipmap.wifi_level_3);
                break;
            case 4: //4格信号
                imageView.setImageResource(R.mipmap.wifi_level_4);
                break;
            default:
                break;
        }
    }

    class ViewHolderWifiSwitch {
        ImageView openWifi; //开启Wifi
    }

    class ViewHolderWifiHint {
        TextView operateHint; //操作提示
    }

    class ViewHolderWifiBody {
        TextView wifiName; //名称
        TextView connectiveStatus; //Wifi连接状态
        ImageView lockMark; //是否需要密码
        ImageView signalLevel; //信号水平
        ImageView wifiDetail; //Wifi详情
    }

    class WifiOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.open_wifi: //打开wifi
                    ImageView imageView = ((ImageView)v);
                    int status = (Integer) imageView.getTag();
                    if(status == 1) {
                        //关闭wifi
                        imageView.setImageResource(R.mipmap.wifi_close);
                        imageView.setTag(0);
                        powerWifiManager.openOrCloseWifi(false);
                    } else {
                        //开启wifi
                        imageView.setImageResource(R.mipmap.wifi_open);
                        imageView.setTag(1);
                        powerWifiManager.openOrCloseWifi(true);
                    }
                    break;
            }
        }
    }

}
