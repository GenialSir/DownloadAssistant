package com.genialsir.downloadassistantdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genialsir.downloadassistantdemo.R;
import com.genialsir.downloadassistantdemo.bean.DownloadShowBean;

import java.util.List;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/10
 */
public class DownloadShowAdapter extends RecyclerView.Adapter<DownloadShowAdapter
        .DownloadShowHolder> {

    private List<DownloadShowBean> mData;

    public DownloadShowAdapter(List<DownloadShowBean> downloadShowBeans) {
        mData = downloadShowBeans;
    }

    @NonNull
    @Override
    public DownloadShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_download_show, parent, false);
        return new DownloadShowHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadShowHolder holder, int position) {
        DownloadShowBean downloadShowBean = mData.get(position);

    }

    @Override
    public int getItemCount() {
        try {
            return mData.size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    class DownloadShowHolder extends RecyclerView.ViewHolder {

        ImageView ivFileFlag;
        TextView tvFileTitle;
        TextView tvFileCreationTime;
        ImageView ivFileDownload;
        TextView tvFileSize;
        ProgressBar pbFileDownload;

        DownloadShowHolder(@NonNull View itemView) {
            super(itemView);
            ivFileFlag = itemView.findViewById(R.id.iv_file_flag);
            tvFileTitle = itemView.findViewById(R.id.tv_file_title);
            tvFileCreationTime = itemView.findViewById(R.id.tv_file_creation_time);
            ivFileDownload = itemView.findViewById(R.id.iv_file_download);
            tvFileSize = itemView.findViewById(R.id.tv_file_size);
            pbFileDownload = itemView.findViewById(R.id.pb_file_download);
        }
    }
}
