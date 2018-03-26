package com.example.aly.tinker;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;

/**
 * Created by fashionaly on 2018/3/26.
 */

/**
 * 1.校验patch文件是否合法
 * 2.启动Service去安装patch文件
 */
public class CustomPatchListner extends DefaultPatchListener {

    public String getCurrentMD5() {
        return currentMD5;
    }

    public void setCurrentMD5(String currentMD5) {
        this.currentMD5 = currentMD5;
    }

    private String currentMD5;

    @Override
    protected int patchCheck(String path, String patchMd5) {
        //这里可添加md5校验 这里偷懒取消校验实体
//        if(!MD5Utils.isFileMD5Matched(path,currentMD5))
//            return ShareConstants.ERROR_PATCH_DISABLE;
        return super.patchCheck(path, patchMd5);
    }

    public CustomPatchListner(Context context) {
        super(context);
    }
}
