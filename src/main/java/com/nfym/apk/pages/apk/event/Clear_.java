package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import javafx.scene.control.Button;

public class Clear_ {
    private Button clear;

    public Clear_(Button clear) {
        this.clear = clear;
    }

    public Button getClear() {
        return clear;
    }

    public void setClear(Button clear) {
        this.clear = clear;
    }

    public void OnClick() {
        Textarea_ textarea = (Textarea_) Dao.getInstance().get("textarea");
        textarea.clearContent();
    }
}
