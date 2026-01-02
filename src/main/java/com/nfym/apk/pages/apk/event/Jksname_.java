package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import javafx.scene.control.TextField;

public class Jksname_ {
    private TextField jksname;

    public Jksname_(TextField jksname) {
        this.jksname = jksname;
        
        // 添加鼠标焦点失去事件监听
        this.jksname.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                OnMouseOut();
            }
        });
    }

    public TextField getJksname() {
        return jksname;
    }

    public void setJksname(TextField jksname) {
        this.jksname = jksname;
    }

    public void OnClick() {
    }
    
    // 失去鼠标焦点时的处理函数
    public void OnMouseOut() {
        String content = jksname.getText();
        Dao.getInstance().setText3(content.isEmpty() ? null : content);
    }
}
