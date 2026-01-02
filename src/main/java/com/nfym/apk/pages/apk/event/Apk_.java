package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import javafx.scene.control.TextField;

public class Apk_ {
    private TextField apk;

    public Apk_(TextField apk) {
        this.apk = apk;
        
        // 添加鼠标焦点失去事件监听
        this.apk.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                OnMouseOut();
            }
        });
    }

    public TextField getApk() {
        return apk;
    }

    public void setApk(TextField apk) {
        this.apk = apk;
    }

    public void OnClick() {
    }
    
    // 失去鼠标焦点时的处理函数
    public void OnMouseOut() {
        String content = apk.getText();
        apk.setText(content);
        Dao.getInstance().setText1(content.isEmpty() ? null : content);
    }

    public void setContent(String str){
        apk.setText(str);
        Dao.getInstance().setText1(str);
    }
}
