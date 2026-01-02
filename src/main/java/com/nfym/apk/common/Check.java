package com.nfym.apk.common;

import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.event.Textarea_;
import javafx.scene.control.Alert;

import java.io.File;

public class Check {

    public static boolean verify(int type, String path0, String path1, String path2, String path3){
        Textarea_ textarea = (Textarea_) Dao.getInstance().get("textarea");
        int K=verifyAPKPath(path0);
        switch (type){
            case 0:
                //反编译
                if(K==3){
                    textarea.setContent("文件夹不能反编译");
                }
                return K==4;
            case 1:
                //编译
                if(!verifyJKS(path1,path2,path3)){
                    return false;
                }
                if(K==4){
                    textarea.setContent("APK文件不能编译");
                }
                return K==3;
            case 2:
                //签名
                if(!verifyJKS(path1,path2,path3)){
                    return false;
                }
                if(K==3){
                    textarea.setContent("文件夹不能签名");
                }
                return K==4;
            case 3:
                //卸载
                break;
            case 4:
                //运行
                break;
        }

        return true;
    }

    public static int verifyAPKPath(String path0) {
        int sum=0;
        Textarea_ textarea = (Textarea_) Dao.getInstance().get("textarea");
        if(path0==null){
            textarea.setContent("请指定文件路径");
            return 1;
        }

        File file = new File(path0);

        if(!file.exists()){
            textarea.setContent("文件不存在");
            return 2;
        }

        if(file.isDirectory()){
            sum=3;
        }

        if(path0.toLowerCase().endsWith(".apk")){
            sum=4;
        }
        return sum;
    }

    private static boolean verifyJKS(String path0,String path2,String path3){

        if(path0==null){
            return true;
        }

        File file = new File(path0);
        Textarea_ textarea = (Textarea_) Dao.getInstance().get("textarea");
        if(!file.exists()){
            textarea.setContent("签名文件（.jks）不存在");
            return false;
        }

        if(file.isDirectory()){
            textarea.setContent("签名文件（.jks）不能是文件夹");
            return false;
        }

        if(path0.toLowerCase().endsWith(".jks")){
            String str = "";
            if(path2==null){
                str="别名不能为空\n";
            }
            if(path3==null){
                str+="密码不能为空";
            }
            if(!str.isEmpty()){
                textarea.setContent(str);
                return false;
            }
        }
        return true;
    }
}
