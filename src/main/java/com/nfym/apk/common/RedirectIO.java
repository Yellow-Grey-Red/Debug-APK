package com.nfym.apk.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RedirectIO {

    private  PrintStream originalOut;
    private  ByteArrayOutputStream captureStream;
    private  PrintStream redirectedOut;


    public  void startRIO(){
        originalOut = System.err;
        captureStream = new ByteArrayOutputStream(8192); // 设置初始容量，避免频繁扩容
        redirectedOut = new PrintStream(captureStream);
        System.setErr(redirectedOut);
    }

    public  String RIO(){
        redirectedOut.flush();
        byte[] bytes = captureStream.toByteArray();
        if (bytes.length == 0) {
            return null;
        }
        String output = new String(bytes);
        captureStream.reset();
        return output;
    }


    public  void endRIO(){
        System.setErr(originalOut);
        
        // 安全关闭资源
        try {
            if (redirectedOut != null) {
                redirectedOut.close();
                redirectedOut = null;
            }
            if (captureStream != null) {
                captureStream.close();
                captureStream = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

