package com.example.aly.tinker;

import android.content.Context;

import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by fashionaly on 2018/3/23.
 */

public class TinkerManager {
    private static boolean isInstalled = false;
    private static ApplicationLike mAppLike;
    private static CustomPatchListner mCustomPatchListner;

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
        mCustomPatchListner = new CustomPatchListner(getApplicationContext());
        //监听补丁文件在加载过程中可能发生的异常回调
        LoadReporter loadReporter = new DefaultLoadReporter(getApplicationContext());
        //监听补丁文件在合成过程中可能发生的异常回调 官网有介绍 统计功能可能遇到
        PatchReporter patchReporter = new DefaultPatchReporter(getApplicationContext());
        AbstractPatch upgradePatchProcessor=new UpgradePatch();
        TinkerInstaller.install(applicationLike,loadReporter,patchReporter,mCustomPatchListner,CustomResultService.class
        ,upgradePatchProcessor);
//        TinkerInstaller.install(applicationLike);
        isInstalled = true;
    }

    public static void loadPatch(String path,String md5Value) {
        if (Tinker.isTinkerInstalled()) {
            mCustomPatchListner.setCurrentMD5(md5Value);
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
