package com.wrapper.proxyapplication;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public abstract class WrapperProxyApplication extends Application {
    static Context baseContext;
    static String className = "com.biomes.vanced.vooapp.OfApp";
    static int counter = 0;
    static String dynconfclass = "xxxx3";
    static String dynconfmethod = "xxxx4";
    static String errormsg = null;
    static ClassLoader mLoader = null;
    static Toast mtoast = null;
    static String pkgName = "com.biomes.vanced";
    static String protectlib = "libshell-super.com.biomes.vanced.so";
    static String protectlibx86 = "libshellx-super.com.biomes.vanced.so";
    static String reportapiclass = "xxxx1";
    static String reportapimethod = "xxxx2";
    static Application shellApp = null;
    static String shellsuperversion = "4.6.2.2";
    static String tinkerApp = "tinker not support";
    static String waringmsg = "Your protected app is trial version";
    static int yaqdate = 0;
    Timer actiontimer = new Timer();
    TimerTask errortoasttask = new TimerTask() {
        public void run() {
            WrapperProxyApplication.this.mHandler.post(new Runnable() {
                public void run() {
                    if (WrapperProxyApplication.errormsg != null) {
                        Toast.makeText(WrapperProxyApplication.this, WrapperProxyApplication.errormsg, 1).show();
                    }
                    WrapperProxyApplication.errormsg = null;
                }
            });
        }
    };
    Handler mHandler = null;
    Timer timer = new Timer();
    TimerTask toasttask = new TimerTask() {
        public void run() {
            WrapperProxyApplication.this.mHandler.post(new Runnable() {
                public void run() {
                    Toast.makeText(WrapperProxyApplication.this, WrapperProxyApplication.waringmsg, 1).show();
                }
            });
        }
    };

    static native void unsetenv(String str);

    /* access modifiers changed from: package-private */
    public native void Ooo0ooO0oO();

    /* access modifiers changed from: protected */
    public abstract void initProxyApplication(Context context);

    static Context getWrapperProxyAppBaseContext() {
        return baseContext;
    }

    private synchronized boolean Fixappname() {
        if (className.startsWith(".")) {
            className = super.getPackageName() + className;
        } else if (className.indexOf(".") < 0) {
            className = super.getPackageName() + "." + className;
        }
        return true;
    }

    public static void fixAndroid(Context context, Application application) {
        if (Build.VERSION.SDK_INT == 28) {
            try {
                mLoader = AndroidNClassLoader.inject(context.getClassLoader(), application);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private static String getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "0";
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        baseContext = getBaseContext();
        if (shellApp == null) {
            shellApp = this;
        }
        Fixappname();
        initProxyApplication(context);
    }

    public void onCreate() {
        super.onCreate();
        Ooo0ooO0oO();
        this.mHandler = new Handler(getMainLooper());
        if (yaqdate != 0) {
            this.timer.schedule(this.toasttask, 0, 3000);
        }
        this.actiontimer.schedule(this.errortoasttask, 0, 3000);
    }
}
