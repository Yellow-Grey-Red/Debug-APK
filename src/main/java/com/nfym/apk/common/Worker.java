package com.nfym.apk.common;

import com.nfym.apk.Impl.Call;

public class Worker {
    public static void run(Call call){
        new Thread(()->{
            try {
                call.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void sleep(int time){
        try{
            Thread.sleep(time);
        }catch (Exception e){}
    }
}
