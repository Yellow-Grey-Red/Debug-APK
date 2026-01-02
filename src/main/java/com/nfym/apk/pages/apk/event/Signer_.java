package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.Utils.APKSigner;
import javafx.scene.control.Button;

public class Signer_ {
    private Button signer;

    public Signer_(Button signer) {
        this.signer = signer;
    }

    public Button getSigner() {
        return signer;
    }

    public void setSigner(Button signer) {
        this.signer = signer;
    }

    public void OnClick() {
        new APKSigner().main();
    }
}
