package com.genialsir.downloadassistant.tools;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author genialzbl@163.com (GenialSir) on 2020/8/11.
 */
public class LocalObjectTool {

    public static void saveObjectToLocal(String absolutePath, Object obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(absolutePath));
            oos.writeObject(obj);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Object getLocalObject(String absolutePath) {
        Object obj = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(absolutePath));
            obj = ois.readObject();
            ois.close();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }


}
