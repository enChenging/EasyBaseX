package com.release.easybasex.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.release.easybasex.constance.BConstants;
import com.release.easybasex.event.NetworkChangeEvent;
import com.release.easybasex.utils.NetWorkUtil;
import com.release.easybasex.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Mr.release
 * @create 2019/8/2
 * @Describe
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private boolean hasNetwork = (boolean) SPUtil.getData(BConstants.HAS_NETWORK_KEY,true);
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = NetWorkUtil.isNetworkConnected(context);
        if (isConnected){
            if (hasNetwork){
                EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
            }
        }else {
            EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
        }
    }
}
