package com.nfym.apk.pages.apk.event;

import com.nfym.apk.common.Check;
import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.Utils.Adb;
import javafx.scene.control.Button;

import java.nio.file.Paths;

public class Uninstall_ {
    private Button uninstall;
    public Uninstall_(Button uninstall) {
        this.uninstall = uninstall;
    }

    public Button getUninstall() {
        return uninstall;
    }

    public void setUninstall(Button uninstall) {
        this.uninstall = uninstall;
    }
    private void task(){
        if(Dao.getEmulatorNum()==null){
            Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");
            textarea.setContent("无可调试设备");
            return;
        }
        String apkPath = Dao.getInstance().getText1();
        String localPackName=null;
        int i = Check.verifyAPKPath(apkPath);
        if(i==3){
            localPackName=Adb.getLocalPackNameFromXML(Paths.get(apkPath,"AndroidManifest.xml").toString());
        }
        if(i==4){
            try{
                localPackName=Adb.getLocalPackName(apkPath);
            }catch (Exception e){
                Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");
                textarea.setContent("获取APK信息失败");
                return;
            }
        }
        if(localPackName!=null){
            Adb.uninstall(localPackName);
        }
    }

    public void OnClick() {
        task();
    }
}
