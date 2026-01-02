package com.nfym.apk.pages.apk.event;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

public class Process_ {
    private ProgressBar process;

    public Process_(ProgressBar process) {
        this.process = process;
        stop();
    }

    public ProgressBar getProcess() {
        return process;
    }

    public void setProcess(ProgressBar process) {
        this.process = process;
    }
    public void stop(){
        Platform.runLater(()->{
            process.setProgress(0);
        });
    }
    public void start(){
        Platform.runLater(()->{
            process.setProgress(-1);
        });

    }
}
