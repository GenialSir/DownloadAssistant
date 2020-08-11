package com.genialsir.downloadassistant.tools;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author genialzbl@163.com (GenialSir) on 2020/8/11.
 */
public class CloseTool {
    private CloseTool(){}

    public static void closeQuietly(Closeable closeable) throws IOException {
        if(null != closeable){
            closeable.close();
        }
    }
}
