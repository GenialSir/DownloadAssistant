package com.genialsir.downloadassistantdemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genialsir.downloadassistant.bean.DownloadAssistantBean;
import com.genialsir.downloadassistant.constant.CommonHolder;
import com.genialsir.downloadassistant.tools.DownloadTool;
import com.genialsir.downloadassistant.tools.FilesTool;
import com.genialsir.downloadassistantdemo.R;
import com.genialsir.downloadassistantdemo.bean.DownloadShowBean;

import java.util.List;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/10
 */
public class DownloadShowAdapter extends RecyclerView.Adapter<DownloadShowAdapter
        .DownloadShowHolder> {

    private static final String TAG = "DownloadShowAdapter";
    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<DownloadShowBean> mData;

    public DownloadShowAdapter(RecyclerView recyclerView, List<DownloadShowBean> downloadShowBeans) {
        mRecyclerView = recyclerView;
        mData = downloadShowBeans;
    }

    @NonNull
    @Override
    public DownloadShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_download_show, parent, false);
        return new DownloadShowHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadShowHolder holder, int position) {
        DownloadShowBean downloadShowBean = mData.get(position);
        DownloadAssistantBean daBean = downloadShowBean.getDownloadAssistantBean();

        String curCreateTime = downloadShowBean.getCurCreateTime();
        long fileID = daBean.getFileID();
        String fileName = daBean.getFileName();

        //标记对应的itemView。
        holder.itemView.setTag(fileID);

        holder.tvFileTitle.setText(fileName);
        holder.tvFileCreationTime.setText(curCreateTime);

        DownloadTool.getInstance().setSuccessStatus(true, daBean);

        //下载View更新的核心就是将下载的数据变更和View的即时刷新一一对应，不能错乱。
        toDownloadFile(holder.itemView, daBean);
        updateDownloadView(holder.itemView, daBean);
    }

    //通过从Activity处调用，根据下载文件ID获取对应的ItemView对象。
    public void updateDownloadView(DownloadAssistantBean daBean) {
        View itemView = mRecyclerView.findViewWithTag(daBean.getFileID());
        updateDownloadView(itemView, daBean);

    }

    private void updateDownloadView(View itemView, DownloadAssistantBean daBean) {
        TextView tvFileSize = itemView.findViewById(R.id.tv_file_size);
        ImageView ivFileDownload = itemView.findViewById(R.id.iv_file_download);
        ProgressBar pbFileDownload = itemView.findViewById(R.id.pb_file_download);

        //处理数据复用展示错位的问题。
        if (daBean.isDownloaded()) {
            Log.d(TAG, "updateDownloadView is succeed.");
            pbFileDownload.setProgress(100);
            ivFileDownload.setImageResource(R.mipmap.ic_download_finish);
            tvFileSize.setTextColor(mContext.getResources()
                    .getColor(R.color.doder_blue));
            tvFileSize.setText(mContext.getString(R.string.downloaded));

            //刷新下载成功后的数据。
            toDownloadFile(itemView, daBean);
        } else if (daBean.isDownloading()) {
            String downloadingInfo = mContext.getString(R.string.downloading)
                    + FilesTool.formatSize(daBean.getCurLength())
                    + "/" + FilesTool.formatSize(daBean.getTotalLength());

            tvFileSize.setText(downloadingInfo);
            pbFileDownload.setProgress(daBean.getCurProgress());
            ivFileDownload.setImageResource(R.mipmap.ic_download_loading);
            tvFileSize.setTextColor(mContext.getResources().getColor(R.color.dark_gray));
        } else if (daBean.isDownloadFailed()) {
            pbFileDownload.setProgress(0);
            tvFileSize.setText(R.string.download_exception);
            tvFileSize.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            ivFileDownload.setImageResource(R.mipmap.ic_web_disk_blue_download);

            //刷新下载异常后的数据。
            toDownloadFile(itemView, daBean);
        } else {
            pbFileDownload.setProgress(0);
            ivFileDownload.setImageResource(R.mipmap.ic_web_disk_blue_download);
            tvFileSize.setTextColor(mContext.getResources()
                    .getColor(R.color.dark_gray));

        }
    }


    private void toDownloadFile(final View itemView, final DownloadAssistantBean daBean) {
        final TextView tvFileSize = itemView.findViewById(R.id.tv_file_size);
        final ImageView ivFileDownload = itemView.findViewById(R.id.iv_file_download);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daBean.isDownloaded()) {
                    Toast.makeText(mContext, daBean.getDownloadFilePath(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (daBean.isDownloading()) {
                    Toast.makeText(mContext, "正在下载，请稍等", Toast.LENGTH_SHORT).show();
                    return;
                }
                tvFileSize.setText("连接中");
                tvFileSize.setTextColor(mContext.getResources().getColor(R.color.dark_gray));
                ivFileDownload.setImageResource(R.mipmap.ic_download_loading);
                daBean.setDownloading(true);

                //开始执行下载请求。
                executeDownload(daBean);

            }
        });
    }

    private void executeDownload(DownloadAssistantBean daBean) {
        String savePath = CommonHolder.FileDirectory.FILE_SOURCE;
        daBean.setSavePath(savePath);
        daBean.setTempFileName(daBean.getFileName() + CommonHolder.FileExtension.TEMP);
        DownloadTool.getInstance().toDownloadFile(mContext, daBean);
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
