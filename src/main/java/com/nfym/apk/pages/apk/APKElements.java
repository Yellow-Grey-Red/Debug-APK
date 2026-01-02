package com.nfym.apk.pages.apk;

import com.nfym.apk.pages.apk.Utils.Adb;
import com.nfym.apk.pages.apk.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;



public class APKElements {
    @FXML
    private ComboBox<String> choices;

    @FXML
    private Button fresh;

    @FXML
    private TextField apk;

    @FXML
    private Button apkbtn1;

    @FXML
    private Button apkbtn2;

    @FXML
    private TextField jks;

    @FXML
    private Button jksbtn;

    @FXML
    private TextField jksname;

    @FXML
    private TextField jkspass;

    @FXML
    private TextField command;

    @FXML
    private Button runcommand;

    @FXML
    private Button decompile;

    @FXML
    private Button compile;

    @FXML
    private Button signer;

    @FXML
    private Button uninstall;

    @FXML
    private Button install;

    @FXML
    private TextArea textarea;

    @FXML
    private Button clear;

    @FXML
    private ProgressBar process;

    @FXML
    private TextField log_;

    @FXML
    private Button dexCompile;
    @FXML
    public void initialize() {
        store();
        events();
        Adb.detectADB();
    }


    private void events() {
        Dao map = Dao.getInstance();
        // 1. ComboBox选择事件（原有规则示例）
        choices.setOnAction(event -> {
            Choices_ choices1 = (Choices_) map.get("choices");
            choices1.OnClick();
        });

        // 2. fresh按钮点击事件（按choices规则修正）
        fresh.setOnAction(event -> {
            Fresh_ fresh1 = (Fresh_) map.get("fresh");
            fresh1.OnClick();
        });

        // 3. apk文本框文本变化事件（按choices规则修正）
//        apk.textProperty().addListener((observable, oldValue, newValue) -> {
//            Apk_ apk1 = (Apk_) map.get("apk");
//            apk1.OnClick();
//        });

        // 4. apkbtn按钮点击事件（按choices规则修正）
        apkbtn1.setOnAction(event -> {
            Apkbtn_1 apkbtn1 = (Apkbtn_1) map.get("apkbtn1");
            apkbtn1.OnClick();
        });

        apkbtn2.setOnAction(event -> {
            Apkbtn_2 apkbtn2 = (Apkbtn_2) map.get("apkbtn2");
            apkbtn2.OnClick();
        });

        // 5. jks文本框文本变化事件（按choices规则修正）
//        jks.textProperty().addListener((observable, oldValue, newValue) -> {
//            Jks_ jks1 = (Jks_) map.get("jks");
//            jks1.OnClick();
//        });

        // 6. jksbtn按钮点击事件（按choices规则修正）
        jksbtn.setOnAction(event -> {
            Jksbtn_ jksbtn1 = (Jksbtn_) map.get("jksbtn");
            jksbtn1.OnClick();
        });

        // 7. jksname文本框文本变化事件（按choices规则修正）
//        jksname.textProperty().addListener((observable, oldValue, newValue) -> {
//            Jksname_ jksname1 = (Jksname_) map.get("jksname");
//            jksname1.OnClick();
//        });

        // 8. jkspass文本框文本变化事件（按choices规则修正）
//        jkspass.textProperty().addListener((observable, oldValue, newValue) -> {
//            Jkspass_ jkspass1 = (Jkspass_) map.get("jkspass");
//            jkspass1.OnClick();
//        });

        // 9. command文本框文本变化事件（按choices规则修正）
//        command.textProperty().addListener((observable, oldValue, newValue) -> {
//            Command_ command1 = (Command_) map.get("command");
//            command1.OnClick();
//        });

        // 10. runcommand按钮点击事件（按choices规则修正）
        runcommand.setOnAction(event -> {
            Runcommand_ runcommand1 = (Runcommand_) map.get("runcommand");
            runcommand1.OnClick();
        });

        // 11. decompile按钮点击事件（按choices规则修正）
        decompile.setOnAction(event -> {
            Decompile_ decompile1 = (Decompile_) map.get("decompile");
            decompile1.OnClick();
        });

        // 12. compile按钮点击事件（按choices规则修正）
        compile.setOnAction(event -> {
            Compile_ compile1 = (Compile_) map.get("compile");
            compile1.OnClick();
        });

        // 13. signer按钮点击事件（按choices规则修正）
        signer.setOnAction(event -> {
            Signer_ signer1 = (Signer_) map.get("signer");
            signer1.OnClick();
        });

        // 14. uninstall按钮点击事件（按choices规则修正）
        uninstall.setOnAction(event -> {
            Uninstall_ uninstall1 = (Uninstall_) map.get("uninstall");
            uninstall1.OnClick();
        });

        // 15. install按钮点击事件（按choices规则修正）
        install.setOnAction(event -> {
            System.out.println(map.get("install"));
            Install_ install1 = (Install_) map.get("install");
            install1.OnClick();
        });

        // 16. clear按钮点击事件（按choices规则修正）
        clear.setOnAction(event -> {
            Clear_ clear1 = (Clear_) map.get("clear");
            clear1.OnClick();
        });


        dexCompile.setOnAction(event -> {
            System.out.println(map.get("dexCompile"));
            DexCompile DexCompile = (DexCompile) map.get("dexCompile");
            DexCompile.OnClick();
        });


    }


    private void store(){
        //apk jks jksname jkspass command
        Dao map = Dao.getInstance();
        map.set("choices", new Choices_(choices));
        map.set("fresh", new Fresh_(fresh));
        map.set("apk", new Apk_(apk));
        map.set("apkbtn1", new Apkbtn_1(apkbtn1));
        map.set("apkbtn2", new Apkbtn_2(apkbtn2));
        map.set("jks", new Jks_(jks));
        map.set("jksbtn", new Jksbtn_(jksbtn));
        map.set("jksname", new Jksname_(jksname));
        map.set("jkspass", new Jkspass_(jkspass));
        map.set("command", new Command_( command));
        map.set("runcommand", new Runcommand_(runcommand));
        map.set("decompile", new Decompile_(decompile));
        map.set("compile", new Compile_( compile));
        map.set("signer", new Signer_(signer));
        map.set("uninstall", new Uninstall_(uninstall));
        map.set("install", new Install_(install));
        map.set("textarea", new Textarea_(textarea));
        map.set("clear", new Clear_(clear));
        map.set("process", new Process_(process));
        map.set("log", new Log_(log_));
        map.set("dexCompile", new DexCompile(dexCompile));


    }


}
