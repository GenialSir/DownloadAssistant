package com.genialsir.downloadassistant.tools;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.genialsir.downloadassistant.bean.DownloadAssistantBean;
import com.genialsir.downloadassistant.constant.CommonHolder;
import com.genialsir.downloadassistant.receiver.DownloadAssistantReceiver;
import com.genialsir.downloadassistant.service.DownloadAssistantService;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/12
 */
public class DownloadTool {

    //发送数据更改通知。
    private LocalBroadcastManager localBroadcastManager;

    private DownloadTool() {
    }

    public static DownloadTool getInstance() {
        return DownloadTool.VariableHolder.instance;
    }

    private static class VariableHolder {
        private static final DownloadTool instance = new DownloadTool();
    }

    public void toDownloadFile(Context context, DownloadAssistantBean daBean) {
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        Intent downloadIntent = new Intent(context, DownloadAssistantService.class);
        downloadIntent.putExtra(DownloadAssistantService.DOWNLOAD_DATA, daBean);
        context.startService(downloadIntent);
    }

    public void updateDownloadStatus(DownloadAssistantBean daBean) {
        Intent updateIntent = new Intent(DownloadAssistantReceiver.DOWNLOAD_ASSISTANT_FLAG);
        updateIntent.putExtra(DownloadAssistantReceiver.DOWNLOAD_ASSISTANT_DATA, daBean);
        localBroadcastManager.sendBroadcast(updateIntent);
    }

    /**
     * 采用将对象存储到本地文件来进行当前下载进度的记录，相比本地数据库更轻量一些。
     *
     * @param isRefreshShow 如果初次进入列表页，则不进行删除配置文件操作，只有在下载成功时处理。
     * @param daBean        下载助手的数据Bean。
     */
    public void setSuccessStatus(boolean isRefreshShow, DownloadAssistantBean daBean) {
        String temporaryFile;
        //如果是刷新View处调用，不执行删除临时文件的操作，
        //防止下载服务突然中断导致删除掉判断为成功的问题。
        if (!isRefreshShow) {
            String fileAbsolutePath = CommonHolder.FileDirectory.FILE_SOURCE + daBean.getFileName();

            //下载成功，去除temp的后缀并清除掉文件附属信息。
            FilesTool.replaceFileName(fileAbsolutePath
                    + CommonHolder.FileExtension.TEMP, fileAbsolutePath);

            //删除掉附属信息文件。
            temporaryFile = fileAbsolutePath + CommonHolder.FileExtension.IF;
            FilesTool.deleteLocalFile(temporaryFile);
        }
        //存储对应下载的本地路径。
        String pbbWebDiskPath = CommonHolder.FileDirectory.FILE_SOURCE;
        File pbbWebDiskFile = new File(pbbWebDiskPath);
        String[] pbbWebDiskFileNames = pbbWebDiskFile.list();
        if (pbbWebDiskFileNames != null && pbbWebDiskFile.length() > 0) {
            //存储对应的文件名称。
            List<String> pbbWebDiskFileNameData = Arrays.asList(pbbWebDiskFileNames);
            for (int i = 0; i < pbbWebDiskFileNameData.size(); i++) {
                //根据文件名与附属文件判断文件是否下载完毕。
                temporaryFile = CommonHolder.FileDirectory.FILE_SOURCE
                        + daBean.getFileName() + CommonHolder.FileExtension.IF;
                if (pbbWebDiskFileNameData.contains(daBean.getFileName()
                        + CommonHolder.FileExtension.TEMP) ||
                        pbbWebDiskFileNameData.contains(daBean.getFileName())) {
                    try {
                        DownloadAssistantBean tempBDFileData = (DownloadAssistantBean)
                                LocalObjectTool.getLocalObject(temporaryFile);

                        //将本地附属文件存储的下载信息的对象赋给当前下载信息的对象。
                        if (tempBDFileData != null) {
                            daBean = tempBDFileData;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //以当前对象的文件名与本地文件名匹配来判定当前文件是否下载成功。
                if (daBean.getFileName().equals(pbbWebDiskFileNameData.get(i))) {
                    daBean.setDownloaded(true);
                    daBean.setDownloadFilePath(pbbWebDiskPath + pbbWebDiskFileNameData.get(i));
                }
            }
        }

    }

}
