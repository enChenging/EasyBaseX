package com.release.simplex.ui.act;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.widget.TextView;

import com.release.easybasex.base.BaseActivity;
import com.release.simplex.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DeviceInfoAcitivty extends BaseActivity {

    private TextView mPhoneInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_camera;
    }


    @Override
    public void initView() {
        mPhoneInfo = (TextView) findViewById(R.id.id_tv_phone_info);
        getAvailMemory();// 获取手机可用内存大小
        getTotalMemory();//获取总内存大小
        getHeightAndWidth();//获取屏幕宽高
        getInfo();//获取IMEI号，IESI号，手机型号
        getMacAddress();//获取IMEI号，IESI号，手机型号
        getCpuInfo();//手机CPU信息
        getPackage();//获取软件包名,版本名，版本号
        isRoot();//手机是否root
        String text = getHeightAndWidth() +
                "\n" + getTotalMemory() +
                "\n" + getAvailMemory() +
                "\n" + getInfo() +
                "\n" + getMacAddress() +
                "\n" + getCpuInfo() +
                "\n" + getPackage() +
                "\n" + isRoot();
        mPhoneInfo.setText(text);

    }


    /**
     * 获取软件包名,版本名，版本号
     */
    private String getPackage() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = this.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return "Package: " + pkName + "\nversionName: " + versionName + "\nversionCode: " + versionCode;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取手机是否root信息
     *
     * @return
     */
    private String isRoot() {
        String bool = "Root: false";
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = "Root: false";
            } else {
                bool = "Root: true";
            }
        } catch (Exception e) {
        }
        return bool;
    }

    /**
     * 获取android当前可用内存大小
     */
    private String getAvailMemory() {// 获取android当前可用内存大小
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return "当前可用内存：" + Formatter.formatFileSize(DeviceInfoAcitivty.this, blockSize * availableBlocks);
    }

    /**
     * 获得系统总内存
     */
    private String getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return "总内存大小：" + Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 获得手机屏幕宽高
     *
     * @return
     */

    public String getHeightAndWidth() {
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int heigth = getWindowManager().getDefaultDisplay().getHeight();
        String str = "Width:" + width + "\nHeight: " + heigth + "";
        return str;
    }

    /**
     * 获取IMEI号，IESI号，手机型号
     */

    private String getInfo() {
        TelephonyManager mTm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String imei = null;
        String imsi = null;
        String numer = null;
        try {
            imei = mTm.getDeviceId();
            imsi = mTm.getSubscriberId();
            numer = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得
        } catch (Exception e) {
            e.printStackTrace();
        }
        String mtype = android.os.Build.MODEL; // 手机型号
        String mtyb = android.os.Build.BRAND;//手机品牌
        return "手机IMEI号：" + imei + "\n手机IESI号：" + imsi + "\n手机型号：" + mtype + "\n手机品牌：" + mtyb + "\n手机号码: " + numer;
    }

    /**
     * 获取手机MAC地址
     * 只有手机开启wifi才能获取到mac地址
     */
    private String getMacAddress() {
        String result = "";
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        return "手机macAdd: " + result;
    }

    /**
     * 手机CPU信息
     */
    private String getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};  //1-cpu型号  //2-cpu频率
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return "CPU型号: " + cpuInfo[0] + "\nCPU频率：" + cpuInfo[1];
    }


}
