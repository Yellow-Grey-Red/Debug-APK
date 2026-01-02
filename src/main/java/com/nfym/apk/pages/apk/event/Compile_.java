package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.Utils.APKCompile;
import javafx.scene.control.Button;

public class Compile_ {
    private Button compile;

    public Compile_(Button compile) {
        this.compile = compile;
    }

    public Button getCompile() {
        return compile;
    }

    public void setCompile(Button compile) {
        this.compile = compile;
    }

    public void OnClick() {
        new APKCompile().main();
    }
}
