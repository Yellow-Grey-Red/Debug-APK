package com.nfym.apk.pages.apk.Utils;

import brut.androlib.ApkBuilder;
import brut.androlib.ApkDecoder;
import brut.androlib.Config;
import brut.directory.ExtFile;
import com.nfym.apk.common.*;
import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.event.Apk_;
import com.nfym.apk.pages.apk.event.Log_;
import com.nfym.apk.pages.apk.event.Textarea_;
import javafx.scene.control.Alert;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class APKCompile {

    private Logger_ logger_;
    private Logger targetLogger;
    private String built_into;
    private AtomicBoolean is_end= new AtomicBoolean(true);
    private void doCompile(String path){
        Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");

        if(!Check.verify(1,path,null,null,null)){
            Dao.finish= num.Error;
            return;
        }
        if(!Paths.get(path,"AndroidManifest.xml").toFile().exists()){
            textarea.setContent("缺少AndroidManifest.xml文件");
            Dao.finish= num.Error;
            return;
        }
        if(!Paths.get(path,"apktool.yml").toFile().exists()){
            textarea.setContent("缺少apktool.yml文件");
            Dao.finish= num.Error;
            return;
        }
        is_end.set(true);
        addStream();

        Config config = new Config("");
        config.setJobs(Math.min(Runtime.getRuntime().availableProcessors(), 8));
        config.setVerbose(true);
        ApkBuilder apkBuilder = new ApkBuilder(new ExtFile(new File(path)), config);



        Log_ log = (Log_) Dao.getInstance().get("log");
        End.end("编译启动",()->{
            apkBuilder.build(null);
            textarea.setContent("Built apk into(Without Signer):");
            built_into=built_into.substring(built_into.indexOf(":")+1).trim();
            textarea.setContent(built_into);
            textarea.setContent("");
            log.setContent("编译完成");
            textarea.setContent("编译完成");
            removeStream();
        },false,(e)->{
            log.setContent("缺少编译文件");
            removeStream();
            textarea.setContent("文件可能损坏");
            Dao.finish=num.Error;
        }, this::remain);

    }
    private void removeStream(){
        is_end.set(false);
        CapNorRest();
    }

    private void addStream(){
        CapErr();
        CapNor();
    }

    private void CapErr(){
        Worker.run(()->{
            RedirectIO redirectIO = new RedirectIO();
            redirectIO.startRIO();
            Textarea_ textarea = (Textarea_) Dao.getInstance().get("textarea");
            while (is_end.get()){
                String rio = redirectIO.RIO();
                if(rio==null){
                    continue;
                }
                textarea.setContent(rio);
            }
            redirectIO.endRIO();
        });
    }
    private void CapNor(){
        logger_= new Logger_();
        targetLogger = Logger.getLogger("brut.androlib");
        targetLogger.setLevel(Level.ALL);

        targetLogger.addHandler(logger_);
        targetLogger.setUseParentHandlers(false);
    }

    private void CapNorRest(){
        targetLogger.setUseParentHandlers(true);
        targetLogger.removeHandler(logger_);
    }
    public void main(){
        Dao instance = Dao.getInstance();
        doCompile(instance.getText1());
    }

    private void remain(){
        APKSigner apkSigner = new APKSigner();
        apkSigner.main(built_into);
    }


     class Logger_ extends Handler {

         Log_ log;
         Textarea_ textarea;

        public Logger_() {
            textarea = (Textarea_) Dao.getInstance().get("textarea");
            log = (Log_) Dao.getInstance().get("log");
        }

        @Override
        public void publish(LogRecord record) {
            if (isLoggable(record)) {
                String message = record.getMessage();
                log.setContent(message);
                built_into=message;
            }
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    }
}
