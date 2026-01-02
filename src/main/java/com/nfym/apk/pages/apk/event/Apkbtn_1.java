package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Apkbtn_1 {
    private Button apkbtn;
    private File lastPath=null;

    public Apkbtn_1(Button apkbtn) {
        this.apkbtn = apkbtn;
    }

    public Button getApkbtn() {
        return apkbtn;
    }

    public void setApkbtn(Button apkbtn) {
        this.apkbtn = apkbtn;
    }

    public File chooseAPKFile(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择APK文件");
        File path=lastPath==null?new File(System.getProperty("user.home")):lastPath;
        chooser.setInitialDirectory(path);


        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("APK 文件", "*.apk"));

        Stage stage = (Stage) apkbtn.getScene().getWindow();
        File file = chooser.showOpenDialog(stage);
        if(file==null){
            //未选择文件
            return null;
        }
        lastPath=new File(file.getParent());
        return file;
    }

    public void OnClick() {

        File file=chooseAPKFile();
        if(file==null){
            return;
        }
        Apk_ apk = (Apk_) Dao.getInstance().get("apk");
        apk.setContent(file.getAbsolutePath());
    }
}
