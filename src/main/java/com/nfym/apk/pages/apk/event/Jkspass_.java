package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import javafx.scene.control.TextField;

public class Jkspass_ {
    private TextField jkspass;

    public Jkspass_(TextField jkspass) {
        this.jkspass = jkspass;
        
        // 添加鼠标焦点失去事件监听
        this.jkspass.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                OnMouseOut();
            }
        });
    }

    public TextField getJkspass() {
        return jkspass;
    }

    public void setJkspass(TextField jkspass) {
        this.jkspass = jkspass;
    }

    public void OnClick() {
    }
    
    // 失去鼠标焦点时的处理函数
    public void OnMouseOut() {
        String content = jkspass.getText();
        Dao.getInstance().setText4(content.isEmpty() ? null : content);
    }
}
