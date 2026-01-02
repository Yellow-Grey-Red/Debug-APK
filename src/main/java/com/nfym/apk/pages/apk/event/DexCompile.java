package com.nfym.apk.pages.apk.event;

import brut.androlib.smali.SmaliBuilder;
import com.nfym.apk.common.End;
import com.nfym.apk.pages.apk.Dao;
import javafx.scene.control.Button;
import java.io.File;

public class DexCompile {

    private Button dexCompile;

    public Button getDexCompile() {
        return dexCompile;
    }

    public void setDexCompile(Button dexCompile) {
        this.dexCompile = dexCompile;
    }

    private Textarea_ textarea;
    public DexCompile(Button dexCompile) {
        this.dexCompile = dexCompile;
        textarea = (Textarea_) Dao.getInstance().get("textarea");
    }

    private void main() {
        String trim = Dao.getInstance().getText1().trim();
        if (!trim.endsWith("smali")) {
            textarea.setContent("请选择smali文件夹");
            return;
        }
        File file = new File(trim);
        if (!file.exists()) {
            textarea.setContent("所选择的smali文件夹不存在");
            return;
        }
        End.end("编译生成dex文件中",()->{
            int jobs = Math.min(Runtime.getRuntime().availableProcessors(), 8);
            SmaliBuilder smaliBuilder = new SmaliBuilder(file, jobs);
            String outputDexPath = file.getParentFile().getAbsolutePath() + File.separator + "classes.dex";
            smaliBuilder.build(new File(outputDexPath));
            textarea.setContent("Built dex into:" + outputDexPath);
        },false,(e)->{
            textarea.setContent(e.getMessage());
            textarea.setContent("编译smali失败");
        },()->{});
    }


    public void OnClick() {
        main();
    }
}
