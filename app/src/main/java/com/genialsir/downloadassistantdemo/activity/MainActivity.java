package com.genialsir.downloadassistantdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.genialsir.downloadassistantdemo.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void toDownloadShow(View view) {
        startActivity(new Intent(this, DownloadShowActivity.class));
    }
}
