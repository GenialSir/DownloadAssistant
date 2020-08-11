package com.genialsir.downloadassistant.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.genialsir.downloadassistant.bean.DownloadAssistantBean;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/11
 */
public abstract class DownloadAssistantReceiver extends BroadcastReceiver {

    public static final String DOWNLOAD_ASSISTANT_FLAG = "downloadAssistantFlag";
    public static final String DOWNLOAD_ASSISTANT_DATA = "downloadAssistantData";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null){
            return;
        }
        String action = intent.getAction();
        if(!DOWNLOAD_ASSISTANT_FLAG.equals(action)){
            return;
        }
        DownloadAssistantBean daBean = (DownloadAssistantBean) intent
                .getSerializableExtra(DOWNLOAD_ASSISTANT_DATA);

        if(daBean == null){
            throw new NullPointerException("DownloadAssistantBean is null.");
        }
        refreshDownloadView(daBean);

    }

    protected abstract void refreshDownloadView(DownloadAssistantBean downloadStatusBean);
}
