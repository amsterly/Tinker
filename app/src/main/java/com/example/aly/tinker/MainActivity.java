package com.example.aly.tinker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_END=".apk";
    private String mPatchDir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPatchDir=getExternalCacheDir().getAbsolutePath()+"/tpatch/";
        File file=new File(mPatchDir);
        if(file==null||!file.exists())
        {
            file.mkdir();
        }
    }
    public void loadPatch(View view)
    {
        TinkerManager.loadPatch(getPatchName());
    }
    private String getPatchName()
    {
        return mPatchDir.concat("aly").concat(FILE_END);
    }
}
