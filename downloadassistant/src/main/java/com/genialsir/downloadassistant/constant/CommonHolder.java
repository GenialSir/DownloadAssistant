package com.genialsir.downloadassistant.constant;

import android.os.Environment;

/**
 * @author genialsir@163.com (GenialSir) on 2020/8/12
 */
public interface CommonHolder {

    interface ParamKey {
        String USER_AGENT = "User-Agent";
        String HEADER_PARAM = "pan.baidu.com";
    }

    interface DownloadConfiguration{
        int DOWNLOAD_BUFFER = 10240;
    }

    interface FileDirectory {
        String FILE_SOURCE = Environment.getExternalStorageDirectory()
                + "/download_assistant_source/";
    }

    interface FileExtension {
        //下载内容的信息文件。
        String IF = ".if";
        String TEMP = ".temp";
    }

}
