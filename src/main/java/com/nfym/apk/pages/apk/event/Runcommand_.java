package com.nfym.apk.pages.apk.event;

import com.nfym.apk.Impl.Str;
import com.nfym.apk.common.End;
import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.Utils.Exec;
import javafx.scene.control.Button;

public class Runcommand_ {
    private Button runcommand;

    public Runcommand_(Button runcommand) {
        this.runcommand = runcommand;
    }

    public Button getRuncommand() {
        return runcommand;
    }

    public void setRuncommand(Button runcommand) {
        this.runcommand = runcommand;
    }

    private void main(){
        String command=Dao.getInstance().getText5();
        if(command==null){
            Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");
            textarea.setContent("无效的空命令");
            return;
        }
        End.end("正在执行命令",()->{
            Exec.execY(command.trim().split("\\s+"));
        },false,(e)->{

        }, ()->{

        });
    }

    public void OnClick() {
        main();
    }
}
