package com.nfym.apk.pages.apk.event;

import com.nfym.apk.common.*;
import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.Utils.APKCompile;
import com.nfym.apk.pages.apk.Utils.APKSigner;
import com.nfym.apk.pages.apk.Utils.Adb;
import javafx.scene.control.Button;

public class Install_ {
    private Button install;

    public Install_(Button install) {
        this.install = install;
    }

    public Button getInstall() {
        return install;
    }

    public void setInstall(Button install) {
        this.install = install;
    }

    private void await(){
        Worker.run(()->{
            boolean flag=true;
            while (flag){
                switch (Dao.finish) {
                    case num.CommandError -> {
                        System.out.println("A");
                        flag = false;
                    }
                    case num.Error -> {
                        System.out.println("B");
                        flag = false;
                    }
                    case num.Normal -> {
                        System.out.println("C");
                        Worker.sleep(500);
                        Adb.install(Dao.path);
                        flag = false;
                    }
                    default -> Worker.sleep(100);
                }
            }
        });
    }

    private void noSignerAPK(){
        Dao.finish=num.None;
        new APKSigner().main();
        await();
    }
    private void ifDirectory(){
        Dao.finish=num.None;
        APKCompile apkCompile = new APKCompile();
        apkCompile.main();
        await();
    }
    private void task(){
        Textarea_ textarea=(Textarea_) Dao.getInstance().get("textarea");
        if(Dao.getEmulatorNum()==null){
            textarea.setContent("无可调试设备");
            return;
        }

        String apkPath = Dao.getInstance().getText1();

        int i = Check.verifyAPKPath(apkPath);

        if(i==3){
            ifDirectory();
        }
        if(i==4){
            boolean signer=false;
            try{
                signer= Adb.isSigner(apkPath);
            }catch (Exception e){
                textarea.setContent("无法获取apk签名信息");
                return;
            }
            if(signer){
                //未签名
                textarea.setContent("检测到apk签名，签名中...");
                noSignerAPK();
            }else {
                Adb.install(apkPath);
            }


        }
    }

    public void OnClick() {
        task();
    }
}
