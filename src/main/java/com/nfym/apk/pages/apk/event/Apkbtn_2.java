package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Apkbtn_2 {
    private Button apkbtn;
    private File lastPath=null;

    public Apkbtn_2(Button apkbtn) {
        this.apkbtn = apkbtn;
    }

    public Button getApkbtn() {
        return apkbtn;
    }

    public void setApkbtn(Button apkbtn) {
        this.apkbtn = apkbtn;
    }

    private File chooseDirectory(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择APK解包后的文件夹");
        File path=lastPath==null?new File(System.getProperty("user.home")):lastPath;
        chooser.setInitialDirectory(path);
        Stage stage = (Stage) apkbtn.getScene().getWindow();
        File file = chooser.showDialog(stage);
        if(file==null){
            //未选择文件夹
            return null;
        }
        lastPath=new File(file.getParent());
        return file;
    }

    public void OnClick() {
        File Directory=chooseDirectory();
        if(Directory==null){
            return;
        }
        Apk_ apk = (Apk_) Dao.getInstance().get("apk");
        apk.setContent(Directory.getAbsolutePath());
    }
}
