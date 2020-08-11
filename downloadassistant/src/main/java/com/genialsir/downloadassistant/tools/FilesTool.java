package com.genialsir.downloadassistant.tools;

import android.text.TextUtils;

import java.io.File;

/**
 * @author genialsir@163.com (GenialSir) on 2019/12/20
 */
public class FilesTool {
    public static String getFileName(String filePath) {
        return TextUtils.isEmpty(filePath) ? "unknown file" : filePath
                .substring(filePath.lastIndexOf(File.separator) + 1);
    }

    public static boolean deleteLocalFile(String absolutePath) {
        File file = new File(absolutePath);
        if(file.exists()){
            return file.delete();
        }
        return false;
    }

    public static boolean replaceFileName(String originalFileName, String newFileName){
        return new File(originalFileName)
                .renameTo(new File(newFileName));
    }
}
