module com.nfym.apk {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.dongliu.apkparser;
    requires java.xml;
    requires apktool.lib;
    requires java.logging;


    opens com.nfym.apk to javafx.fxml;
    opens com.nfym.apk.pages.apk to javafx.fxml;
    exports com.nfym.apk;
    exports com.nfym.apk.common;
    opens com.nfym.apk.common to javafx.fxml;
}