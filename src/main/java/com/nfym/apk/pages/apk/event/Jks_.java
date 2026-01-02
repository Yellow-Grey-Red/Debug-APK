package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import javafx.scene.control.TextField;

public class Jks_ {
    private TextField jks;

    public Jks_(TextField jks) {
        this.jks = jks;
        
        // 添加鼠标焦点失去事件监听
        this.jks.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                OnMouseOut();
            }
        });
    }

    public TextField getJks() {
        return jks;
    }

    public void setJks(TextField jks) {
        this.jks = jks;
    }

    public void OnClick() {
    }
    
    // 失去鼠标焦点时的处理函数
    public void OnMouseOut() {
        String content = jks.getText();
        jks.setText(content);
        Dao.getInstance().setText2(content.isEmpty() ? null : content);
    }

    public void setContent(String str){
        jks.setText(str);
        Dao.getInstance().setText2(str);
    }
}
