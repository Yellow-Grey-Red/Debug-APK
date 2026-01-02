package com.nfym.apk.pages.apk;

import com.nfym.apk.common.Worker;
import com.nfym.apk.common.num;
import com.nfym.apk.pages.apk.Utils.Adb;
import com.nfym.apk.pages.apk.event.Apk_;

import java.util.HashMap;
import java.util.Random;

public class Dao {
    private static Dao Instance;

    public static String getEmulatorNum() {
        return emulatorNum;
    }

    public static void setEmulatorNum(String emulatorNum) {
        Dao.emulatorNum = emulatorNum;
    }

    private static String emulatorNum=null;
    private static HashMap<String,Object> hashMap;
    
    // 为apk、jks、jksname、jkspass、command创建的私有变量
    private static String text1; // 对应apk
    private static String text2; // 对应jks
    private static String text3; // 对应jksname
    private static String text4; // 对应jkspass
    private static String text5; // 对应command

    public static int finish=num.None;
    public static String path=null;

    private Dao(){
        if(hashMap == null) {
            hashMap = new HashMap<>();
        }
    }

    public static Dao getInstance(){
        if(Instance==null){
            Instance=new Dao();
        }
        return Instance;
    }

    public Object  get(String str) {
        return hashMap.get(str);
    }

    public void set(String str,Object ob) {
        hashMap.put(str,ob);
    }
    
    // text1 getter and setter (对应apk)
    public String getText1() {
        return text1;
    }
    
    public void setText1(String text1) {
        this.text1 = text1;
    }
    
    // text2 getter and setter (对应jks)
    public String getText2() {
        return text2;
    }
    
    public void setText2(String text2) {
        this.text2 = text2;
    }
    
    // text3 getter and setter (对应jksname)
    public String getText3() {
        return text3;
    }
    
    public void setText3(String text3) {
        this.text3 = text3;
    }
    
    // text4 getter and setter (对应jkspass)
    public String getText4() {
        return text4;
    }
    
    public void setText4(String text4) {
        this.text4 = text4;
    }
    
    // text5 getter and setter (对应command)
    public String getText5() {
        return text5;
    }
    
    public void setText5(String text5) {
        this.text5 = text5;
    }
}
