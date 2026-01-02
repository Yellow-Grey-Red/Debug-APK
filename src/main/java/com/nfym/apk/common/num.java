package com.nfym.apk.common;

import java.nio.file.Paths;

public class num {
    public static final int CommandError=-999;
    public static final int Error=-88;
    public static final int Normal=423;

    public static final int MaxItem=1000;
    public static final int None=99999;



    public static final String adbPath= Paths.get(System.getProperty("user.dir"),"utils","adb","adb.exe").toAbsolutePath().toString();
}
