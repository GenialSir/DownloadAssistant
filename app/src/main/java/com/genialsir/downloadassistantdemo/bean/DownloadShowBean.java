package com.genialsir.downloadassistantdemo.bean;

import com.genialsir.downloadassistant.bean.DownloadAssistantBean;

import java.io.Serializable;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/11
 */
public class DownloadShowBean implements Serializable {
    private DownloadAssistantBean downloadAssistantBean;

    public DownloadAssistantBean getDownloadAssistantBean() {
        return downloadAssistantBean;
    }

    public void setDownloadAssistantBean(DownloadAssistantBean downloadAssistantBean) {
        this.downloadAssistantBean = downloadAssistantBean;
    }
}
