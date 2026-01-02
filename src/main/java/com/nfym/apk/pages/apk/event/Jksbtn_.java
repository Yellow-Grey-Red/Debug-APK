package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Jksbtn_ {
    private Button jksbtn;
    private File lastPath=null;

    public Jksbtn_(Button jksbtn) {
        this.jksbtn = jksbtn;
    }

    public Button getJksbtn() {
        return jksbtn;
    }

    public void setJksbtn(Button jksbtn) {
        this.jksbtn = jksbtn;
    }

    public File chooseAPKFile(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择jks文件");
        File path=lastPath==null?new File(System.getProperty("user.home")):lastPath;
        chooser.setInitialDirectory(path);


        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("签名 jks文件", "*.jks"));

        Stage stage = (Stage) jksbtn.getScene().getWindow();
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
        Jks_ jks = (Jks_) Dao.getInstance().get("jks");
        jks.setContent(file.getAbsolutePath());
    }
}
