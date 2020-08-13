package com.genialsir.downloadassistantdemo.activity;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genialsir.downloadassistant.bean.DownloadAssistantBean;
import com.genialsir.downloadassistant.receiver.DownloadAssistantReceiver;
import com.genialsir.downloadassistantdemo.R;
import com.genialsir.downloadassistantdemo.adapter.DownloadShowAdapter;
import com.genialsir.downloadassistantdemo.bean.DownloadShowBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/10
 */
public class DownloadShowActivity extends AppCompatActivity {

    private List<DownloadShowBean> downloadShowData;
    private DownloadShowAdapter downloadShowAdapter;
    private LocalBroadcastManager localBroadcastManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_show);
        initData();
        initView();
    }

    private void initData() {
        String fileName = "test.jpg";
        //想明确展现下载进度请替换大文件链接或修改下载线程类中的缓存大小。
        String downloadLink = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1906469856,4113625838&fm=26&gp=0.jpg";
        downloadLink = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597298753711&di=a686605e1c44b3a0a0a2e205b73b137f&imgtype=0&src=http%3A%2F%2Fpic2.cxtuku.com%2F00%2F08%2F26%2Fb235fd4470d9.jpg";

        downloadShowData = new ArrayList<>();
        DownloadShowBean downloadShowBean;
        DownloadAssistantBean assistantBean;
        for (int i = 0; i < 6; i++) {
            downloadShowBean = new DownloadShowBean();
            assistantBean = new DownloadAssistantBean();
            downloadShowBean.setDownloadAssistantBean(assistantBean);

            downloadShowBean.setCurCreateTime("20200" + i);
            downloadShowBean.getDownloadAssistantBean().setDownloadLink(downloadLink);
            downloadShowBean.getDownloadAssistantBean().setFileID(i);
            downloadShowBean.getDownloadAssistantBean().setFileName("文件" + i + fileName);
            downloadShowData.add(downloadShowBean);
        }

        initNotificationBroadcast();
    }

    private void initView() {
        RecyclerView rvDownloadShow = findViewById(R.id.rv_download_show);
        downloadShowAdapter = new DownloadShowAdapter(rvDownloadShow, downloadShowData);

        rvDownloadShow.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
        rvDownloadShow.setAdapter(downloadShowAdapter);

    }

    private void initNotificationBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadAssistantReceiver.DOWNLOAD_ASSISTANT_FLAG);
        localBroadcastManager.registerReceiver(downloadAssistantReceiver, intentFilter);

    }

    private DownloadAssistantReceiver downloadAssistantReceiver = new DownloadAssistantReceiver() {

        @Override
        protected void refreshDownloadView(DownloadAssistantBean daBean) {
            downloadShowAdapter.updateDownloadView(daBean);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(downloadAssistantReceiver);
    }
}
