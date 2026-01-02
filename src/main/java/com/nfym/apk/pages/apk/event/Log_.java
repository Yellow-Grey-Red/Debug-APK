package com.nfym.apk.pages.apk.event;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class Log_ {
    private TextField log_;

    public Log_(TextField log_) {
        this.log_ = log_;
        log_.setDisable(true);
    }

    public TextField getLog() {
        return log_;
    }

    public void setLog(TextField log) {
        this.log_ = log_;
    }

    public void setContent(String str){
        Platform.runLater(()->{
            log_.setText(str);
        });
    }
    public void clearContent(){
        Platform.runLater(()->{
            log_.clear();
        });
    }
}
