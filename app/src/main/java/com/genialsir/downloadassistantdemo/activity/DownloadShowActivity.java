package com.genialsir.downloadassistantdemo.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.genialsir.downloadassistantdemo.R;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/10
 */
public class DownloadShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_show);
        RecyclerView rvDownloadShow = findViewById(R.id.rv_download_show);


    }
}
