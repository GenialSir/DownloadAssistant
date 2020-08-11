package com.genialsir.downloadassistant;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/10
 */
public class DownloadAssistant {

    private static DownloadAssistant downloadAssistant = new DownloadAssistant();

    private DownloadAssistant(){}

    public static DownloadAssistant getInstance(){
        return downloadAssistant;
    }



}
