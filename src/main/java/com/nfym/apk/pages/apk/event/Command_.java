package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import javafx.scene.control.TextField;

public class Command_ {
    private TextField command;

    public Command_(TextField command) {
        this.command = command;
        
        // 添加鼠标焦点失去事件监听
        this.command.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                OnMouseOut();
            }
        });
    }

    public TextField getCommand() {
        return command;
    }

    public void setCommand(TextField command) {
        this.command = command;
    }

    public void OnClick() {
    }
    
    // 失去鼠标焦点时的处理函数
    public void OnMouseOut() {
        String content = command.getText();
        Dao.getInstance().setText5(content.isEmpty() ? null : content);
    }
}
