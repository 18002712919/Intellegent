package com.asus.intellegent.open;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.asus.intellegent.ui.adapter.FeedAdapter;
import com.asus.intellegent.ui.activity.MainActivity;
import com.asus.intellegent.voice.MessageHandler;
import com.asus.intellegent.voice.StaticDate;

import java.util.Collections;
import java.util.List;

/**
 * Created by ASUS on 2017/7/28.
 */

public class Application implements Open {

    private MainActivity main;
    private MessageHandler handler;
    private String service;
    private String pkgName;
    private String name;
    private Intent intent;
    private static String TAG = Application.class.getSimpleName();

    public Application(Context context, final MessageHandler handler) {
        main = (MainActivity) context;
        this.handler = handler;
        handler.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                start();
            }
        };
    }

    @Override
    public int open(String resultString) {
        boolean b = openApp(main);
        if (b) {
            return StaticDate.APPLICATION;
        } else {
            return 0;
        }
    }


    private boolean openApp(Context context) {
        boolean flag = false;
        service = handler.textJson.getService();
        name = handler.textJson.getSemantic().getSlots().getName();
        if ("app".equals(service)) {
            Log.e(TAG, "" + name);
            pkgName = queryAppInfao(context, name);
            if (pkgName != null) {
                main.updateView("正在为你打开" + name, FeedAdapter.FeedItem.TYPE_RECEIVE);
                //main.app.setTts("正在为你打开" + name);
                //packageName(main, pkgName);
                flag = true;
            } else {
                main.updateView("您没有安装" + name, FeedAdapter.FeedItem.TYPE_RECEIVE);
                //main.app.setTts("您没有安装" + name);
                flag = false;
            }
        } else if ("website".equals(service)) {
            String url = handler.textJson.getSemantic().getSlots().getUrl();
            Uri uri = Uri.parse(url);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            //context.startActivity(intent);
            main.updateView("正在为你打开" + name + "网", FeedAdapter.FeedItem.TYPE_RECEIVE);
            //main.app.setTts("正在为你打开" + name + "网");
            flag = true;
        }
        return flag;
    }
    private void start() {

        if ("app".equals(service) && pkgName != null) {
            packageName(main, pkgName);
        } else if ("website".equals(service)) {
            main.startActivity(intent);
        }
    }

    // 获得所有启动Activity的信息，类似于Launch界面
    public String queryAppInfao(Context context, String appName) {
        PackageManager pm = context.getPackageManager(); // 获得PackageManager对象
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 通过查询，获得所有ResolveInfo对象.
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
        // 调用系统排序 ， 根据name排序
        // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
        for (ResolveInfo reInfo : resolveInfos) {
            //String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
            String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
            String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
            //Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标

            Log.e("" + appLabel, pkgName);
            Log.e(TAG, appLabel.compareToIgnoreCase(appName) + "");
            Log.e("appLable=" + appLabel, "appName=" + appName);
            int temp = appName.compareToIgnoreCase(appLabel);
            if (temp == 0) { //比较label
                return pkgName;
            }
        }
        return null;
    }

    public void packageName(Context context, String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }
}
