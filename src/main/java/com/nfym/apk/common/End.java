package com.nfym.apk.common;

import com.nfym.apk.Impl.Call;
import com.nfym.apk.Impl.Err;
import com.nfym.apk.Impl.Str;
import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.Utils.Exec;
import com.nfym.apk.pages.apk.event.Log_;
import com.nfym.apk.pages.apk.event.Process_;
import com.nfym.apk.pages.apk.event.Textarea_;
import javafx.scene.control.Alert;

public class End {
    public static void end(String title, Call call0, boolean is, Err call, Call callX){
        Log_ log = (Log_)Dao.getInstance().get("log");
        log.setContent(title);
        Process_ process = (Process_)Dao.getInstance().get("process");
        process.start();

        Worker.run(()->{
            boolean success=false;
            try{
                call0.call();
                Thread.sleep(500);
                log.clearContent();
                process.stop();
                success=true;
            }catch (Exception e){
                e.printStackTrace();
                call.call(e);
                Thread.sleep(500);
                log.clearContent();
                process.stop();
                success=false;
            }
            if(success){
                callX.call();
            }
        });





    }

    public static void endl(Call call, Call call0, Call call1){

        try {
            if(Exec.getStatus()==num.CommandError){
                call.call();
                return;
            }

            if(Exec.getStatus()==num.Error){
                call0.call();
                return;
            }

            if(Exec.getStatus()==num.Normal){
                call1.call();
            }
        }catch (Exception e){
            e.printStackTrace();
            Textarea_ textarea = (Textarea_) Dao.getInstance().get("textarea");
            textarea.setContent("文件可能损坏");
        }
    }

}
