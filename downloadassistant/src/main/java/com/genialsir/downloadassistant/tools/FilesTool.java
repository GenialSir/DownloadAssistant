package com.genialsir.downloadassistant.tools;

import android.text.TextUtils;

import java.io.File;
import java.util.Locale;

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

    /**
     * 格式化显示文件大小 eg:2G;2.3M（小数位不为0显示一位小数，小数位为0不显示小数位）
     */
    public static String formatSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        long _10gb = 10 * gb;

        if (size == 0) {
            return "";
        }

        if (size > _10gb) {
            return "大于10G";
        } else if (size >= gb) {
            return String.format(Locale.getDefault(), "%.1fG", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(Locale.getDefault(), f > 100 ? "%.0fM" : "%.1fM", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(Locale.getDefault(), f > 100 ? "%.0fKB" : "%.1fKB", f);
        } else {
            return String.format(Locale.getDefault(), "%dB", size);
        }
    }
}
