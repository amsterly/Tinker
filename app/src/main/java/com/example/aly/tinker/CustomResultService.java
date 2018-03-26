package com.example.aly.tinker;

import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.TinkerServiceInternals;

import java.io.File;

/**
 * Created by fashionaly on 2018/3/26.
 */

/**
 * 决定在patch安装完以后的后续操作 默认是实现杀进程
 */
public class CustomResultService extends DefaultTinkerResultService {
    private static final String TAG="Tingker.SampleResultService";

    /**
     * 返回patch文件的最终安装结果 注释掉杀进程的代码提升用户体验
     * @param result
     */
    @Override
    public void onPatchResult(PatchResult result) {
        if (result == null) {
            TinkerLog.e(TAG, "DefaultTinkerResultService received null result!!!!");
            return;
        }
        TinkerLog.i(TAG, "DefaultTinkerResultService received a result:%s ", result.toString());

        //first, we want to kill the recover process
        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());

        // if success and newPatch, it is nice to delete the raw file, and restart at once
        // only main process can load an upgrade patch!
        if (result.isSuccess) {
            deleteRawPatchFile(new File(result.rawPatchFilePath));
//            if (checkIfNeedKill(result)) {
//                android.os.Process.killProcess(android.os.Process.myPid());
//            } else {
//                TinkerLog.i(TAG, "I have already install the newly patch version!");
//            }
        }
    }
}
