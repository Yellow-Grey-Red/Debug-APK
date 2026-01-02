package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.Utils.APKDecompile;
import javafx.scene.control.Button;

public class Decompile_ {
    private Button decompile;

    public Decompile_(Button decompile) {
        this.decompile = decompile;
    }

    public Button getDecompile() {
        return decompile;
    }

    public void setDecompile(Button decompile) {
        this.decompile = decompile;
    }

    public void OnClick() {
        new APKDecompile().main();
    }
}
