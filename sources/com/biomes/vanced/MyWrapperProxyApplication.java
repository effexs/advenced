package com.biomes.vanced;

import android.content.Context;
import android.os.Process;
import com.wrapper.proxyapplication.Util;
import com.wrapper.proxyapplication.WrapperProxyApplication;
import java.io.IOException;
import java.util.zip.ZipFile;

public class MyWrapperProxyApplication extends WrapperProxyApplication {
    public void onCreate() {
        super.onCreate();
    }

    /* access modifiers changed from: protected */
    public void initProxyApplication(Context ctx) {
        ZipFile apkzf = null;
        try {
            apkzf = new ZipFile(ctx.getApplicationInfo().sourceDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (apkzf == null) {
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
        Util.PrepareSecurefiles(ctx, apkzf);
        try {
            apkzf.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (Util.CPUABI == "86" || Util.CPUABI == "86_64") {
            System.load(ctx.getFilesDir().getAbsolutePath() + "/prodexdir/" + Util.libname);
            return;
        }
        System.loadLibrary(Util.simplelibname);
    }
}
