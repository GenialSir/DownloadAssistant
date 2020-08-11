package com.genialsir.downloadassistant.bean;

import java.io.Serializable;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/11
 */
public class DownloadAssistantBean implements Serializable {

    //对应的文件ID
    private String fileID;
    //当前文件名称。
    private String fileName;
    //下载地址链接。
    private String downloadLink;
    //文件是否已下载。
    private boolean isDownloaded;
    //文件是否正在下载。
    private boolean isDownloading;
    //文件是否下载失败。
    private boolean isDownloadFailed;
    //当前文件的下载进度。
    private int curProgress;
    //当前下载的字节大小。
    private long curLength;
    //该文件的总字节大小。
    private long totalLength;
    //当前文件下载地址。
    private String downloadFilePath;
    //当前文件下载地址。
    private String curDownloadAddress;
    //文件下载保存的地址。
    private String savePath;

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setDownloading(boolean downloading) {
        isDownloading = downloading;
    }

    public boolean isDownloadFailed() {
        return isDownloadFailed;
    }

    public void setDownloadFailed(boolean downloadFailed) {
        isDownloadFailed = downloadFailed;
    }

    public int getCurProgress() {
        return curProgress;
    }

    public void setCurProgress(int curProgress) {
        this.curProgress = curProgress;
    }

    public long getCurLength() {
        return curLength;
    }

    public void setCurLength(long curLength) {
        this.curLength = curLength;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public String getDownloadFilePath() {
        return downloadFilePath;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.downloadFilePath = downloadFilePath;
    }

    public String getCurDownloadAddress() {
        return curDownloadAddress;
    }

    public void setCurDownloadAddress(String curDownloadAddress) {
        this.curDownloadAddress = curDownloadAddress;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
