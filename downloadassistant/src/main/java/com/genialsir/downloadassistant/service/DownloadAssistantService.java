package com.genialsir.downloadassistant.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.genialsir.downloadassistant.bean.DownloadAssistantBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/11
 */
public class DownloadAssistantService extends Service {

    private static final String TAG = "DAService";

    public static final String DOWNLOAD_DATA = "downloadData";

    private static final int POOL_SIZE = 3;


    private ExecutorService executorService;


    {
        executorService = Executors.newFixedThreadPool(POOL_SIZE);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"DownloadAssistantService onStartCommand.");
        try {
            DownloadAssistantBean daBean = (DownloadAssistantBean)
                    intent.getSerializableExtra(DOWNLOAD_DATA);
            if (daBean != null) {
                startDownload(daBean);
            } else {
                Log.e(TAG,"Start download error.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload(DownloadAssistantBean downloadAssistantBean) {
        DownloadAssistantThread daThread = new DownloadAssistantThread(downloadAssistantBean);
        executorService.execute(daThread);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
