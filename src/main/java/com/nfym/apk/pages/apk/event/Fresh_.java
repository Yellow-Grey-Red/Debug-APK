package com.nfym.apk.pages.apk.event;

import com.nfym.apk.common.End;
import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.Utils.Adb;
import com.nfym.apk.pages.apk.Utils.Exec;
import javafx.scene.control.Button;

public class Fresh_ {
    private Button fresh;

    public Fresh_(Button fresh) {
        this.fresh = fresh;
    }

    public Button getFresh() {
        return fresh;
    }

    public void setFresh(Button fresh) {
        this.fresh = fresh;
    }

    public void OnClick() {
        Adb.detectDevices();
    }
}
