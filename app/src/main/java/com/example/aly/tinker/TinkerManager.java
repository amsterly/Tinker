package com.example.aly.tinker;

import android.content.Context;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by fashionaly on 2018/3/23.
 */

public class TinkerManager {
    private static boolean isInstalled = false;
    private static ApplicationLike mAppLike;

    private TinkerManager() {
    }

    public TinkerManager getInstance() {
        return TinkerManagerHolder.TINKER_MANAGER;
    }

    public static class TinkerManagerHolder {
        private final static TinkerManager TINKER_MANAGER = new TinkerManager();
    }

    /**
     * 完成tinker初始化
     *
     * @param applicationLike
     */
    public static void installTinker(ApplicationLike applicationLike) {
        mAppLike = applicationLike;
        if (isInstalled)
            return;
        TinkerInstaller.install(applicationLike);
        isInstalled = true;
    }

    public static void loadPatch(String path) {
        if (Tinker.isTinkerInstalled()) {
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    /**
     * 通过ApplicationLike获取Context
     * @return
     */
    private static Context getApplicationContext() {
        if (mAppLike != null) {
            return mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }

}
