package com.genialsir.downloadassistant.service;

import android.util.Log;

import com.genialsir.downloadassistant.bean.DownloadAssistantBean;
import com.genialsir.downloadassistant.tools.CloseTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


/**
 * @author genialsir@163.com (GenialSir) on 2020/8/11
 */
public class DownloadAssistantThread implements Runnable {
    private static final String TAG = "DAThread";

    private DownloadAssistantBean mDABean;


    public DownloadAssistantThread(DownloadAssistantBean downloadAssistantBean) {
        mDABean = downloadAssistantBean;
    }

    @Override
    public void run() {
        //下载文件时的起始位置。
        long startBytesIndex = 0;
        String userAgent = "User-Agent";
        String headerParam = "pan.baidu.com";
        try {
            URL url = new URL(getRedirectUrl());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(60 * 1000);
            urlConnection.setConnectTimeout(60 * 1000);
            //配置头信息。
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty(userAgent, headerParam);
            //设置分段下载的头信息，Range:做分段。
            //startByteIndex下载文件的起始位置以后的字节范围。
            String rangeProperty = "bytes=" + startBytesIndex + "-";
            urlConnection.setRequestProperty("Range", rangeProperty);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            int responseCode = urlConnection.getResponseCode();

            //校验responseCode.
            if (!(responseCode == HttpURLConnection.HTTP_OK
                    || responseCode == HttpURLConnection.HTTP_PARTIAL)) {
                Log.e(TAG, "HttpURLConnection responseCode is " + responseCode);
                downloadFailed();
                return;
            }

            long totalLength = urlConnection.getContentLength();
            byte[] buf = new byte[10240];
            FileOutputStream fileOutputStream;
            InputStream inputStream = urlConnection.getInputStream();

            File saveDirPath = new File(mDABean.getSavePath());
            if (!saveDirPath.exists()) {
                boolean isMk = saveDirPath.mkdirs();
            }
            File saveFile = new File(mDABean.getSavePath(),
                    mDABean.getFileName());
            if (saveFile.exists()) {
                boolean isDelete = saveFile.delete();
            }

            fileOutputStream = new FileOutputStream(saveFile);
            int len;
            long sum = 0;
            while ((len = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, len);
                sum += len;
                int curProgress = (int) (sum * 1.0f / totalLength * 100);

                //下载状态通知。
                downloadUpdate(curProgress, sum, totalLength);
            }

            fileOutputStream.flush();
            CloseTool.closeQuietly(inputStream);
            CloseTool.closeQuietly(fileOutputStream);

            //下载成功通知。
            downloadSuccess(saveFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            downloadFailed();
        }

    }

    private void downloadUpdate(int curProgress, long sum, long totalLength) {
        mDABean.setDownloaded(false);
        mDABean.setDownloading(true);
        mDABean.setDownloadFailed(false);
        mDABean.setCurProgress(curProgress);
        mDABean.setCurLength(sum);
        mDABean.setTotalLength(totalLength);


        //写本地文件附属信息，存储当前文件状态，防止刷新数据丢失。
//        String temporaryFile = CommonHolder.BaiDuParams.PBB_WEB_DISK_SOURCE
//                + mBDFileListBean.getServerFilename()
//                + CommonHolder.FILE_EXTENSION.IF;
//        LocalObjectTool.saveObjectToLocal(temporaryFile, mDABean);


    }

    private void downloadSuccess(String absolutePath) {
        mDABean.setDownloaded(true);
        mDABean.setDownloading(false);
        mDABean.setDownloadFailed(false);
        mDABean.setCurDownloadAddress(absolutePath);

//        DownloadTool.getInstance().setSuccessStatus(mBDFileListBean);
//        DownloadTool.getInstance().updateDownloadStatus(mBDFileListBean);

//        String downloadMsg = mBDFileListBean.getServerFilename() + " - 已下载";
        //发送文件下载成功的通知。

    }

    private void downloadFailed() {
        mDABean.setDownloaded(false);
        mDABean.setDownloading(false);
        mDABean.setDownloadFailed(true);
        mDABean.setCurProgress(0);
//        DownloadTool.getInstance().updateDownloadStatus(mBDFileListBean);
//
//        //下载失败，删除掉文件和文件的附属信息。
//        //如果添加断点下载则不删除。
//        String curDownloadFile = CommonHolder.BaiDuParams.PBB_WEB_DISK_SOURCE
//                + mBDFileListBean.getServerFilename()
//                + CommonHolder.FILE_EXTENSION.TEMP;
//        String temporaryFile = CommonHolder.BaiDuParams.PBB_WEB_DISK_SOURCE
//                + mBDFileListBean.getServerFilename()
//                + CommonHolder.FILE_EXTENSION.IF;
//        FilesTool.deleteLocalFile(curDownloadFile);
//        FilesTool.deleteLocalFile(temporaryFile);
    }


    private String getRedirectUrl() {
        String userAgent = "User-Agent";
        String headerParam = "pan.baidu.com";
        String downloadLink = mDABean.getDownloadLink();
        try {
            URL url = new URL(downloadLink);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(60 * 1000);
            urlConnection.setConnectTimeout(60 * 1000);
            //配置头信息。
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty(userAgent, headerParam);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                List<String> location = headerFields.get("Location");
                //获取重定向后的下载地址。
                if (location != null) {
                    downloadLink = location.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return downloadLink;
    }
}
